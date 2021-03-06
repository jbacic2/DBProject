--checks that a given date is valid
create function check_date (day int, month int, year int)
	returns boolean as $$
	declare valid_date boolean;
	declare leap_year boolean;
	begin
		valid_date = FALSE;
		if month = 2 then
			if day <= 28 then 
				valid_date = TRUE;
			end if;

			leap_year = FALSE;
			if year % 4 = 0 and year % 100 != 0 then
				leap_year = TRUE;
			end if;
			if year % 400 = 0 then
				leap_year = TRUE;
			end if;
			
			if leap_year and day = 29 then
				valid_date = TRUE;
			end if;
		end if;
		if month = 4 or month = 6 or month = 9 or month = 11 then
			if day <=30 then
				valid_date = TRUE;
			end if;
		else
			if month <> 2 then 
				valid_date = TRUE;
			end if;
		end if;
	return valid_date;
	end; $$ language plpgsql;

--date checking trigger functions 
CREATE FUNCTION check_date_exp_tf ()
	RETURNS TRIGGER AS $$
	BEGIN
		IF NOT check_date(NEW.exp_day,NEW.exp_month,NEW.exp_year) THEN
		RAISE EXCEPTION 'Invalid Date: %, %, % is not a real day', NEW.exp_day,NEW.exp_month,NEW.exp_year;
		END IF;
	RETURN NEW;
	END; $$ LANGUAGE plpgsql;

CREATE FUNCTION check_date_restock_tf ()
	RETURNS TRIGGER AS $$
	BEGIN
		IF NOT check_date(NEW.day,NEW.month,NEW.year) THEN
		RAISE EXCEPTION 'Invalid Date: %, %, % is not a real day', NEW.exp_day,NEW.exp_month,NEW.exp_year;
		END IF;
	RETURN NEW;
	END; $$ LANGUAGE plpgsql;
	
CREATE FUNCTION check_date_order_tf ()
	RETURNS TRIGGER AS $$
	BEGIN
		IF NOT check_date(NEW.purchase_day,NEW.purchase_month,NEW.purchase_year) THEN
		RAISE EXCEPTION 'Invalid Date: %, %, % is not a real day', NEW.purchase_day,NEW.purchase_month,NEW.purchase_year;
		END IF;
	RETURN NEW;
	END; $$ LANGUAGE plpgsql;

-- this function will calculate the number of books to be ordered which will be the number of books
-- sold in the last month or be 10 if the number of books sold is less then 10
CREATE FUNCTION calc_books_to_order (isbn varchar(17))
	RETURNS INTEGER AS $$
	DECLARE book_sum INTEGER;
	DECLARE last_month INTEGER;
	DECLARE last_month_year INTEGER;
	BEGIN 
		book_sum = 0;
		last_month = CAST (EXTRACT(MONTH FROM CAST(current_date AS DATE)) AS INTEGER) - 1;
		last_month_year = CAST (EXTRACT(YEAR FROM CAST(current_date AS DATE)) AS INTEGER);
		IF last_month = 0 THEN
			last_month = 12;
			last_month_year = last_month_year -1;
		END IF;
		SELECT SUM(quantity)
		FROM cust_order JOIN book_ordered ON cust_order.order_num = book_ordered.order_num
		WHERE book_ordered.isbn = calc_books_to_order.isbn AND purchase_month = last_month AND purchase_year = last_month_year INTO book_sum;
		IF book_sum < 10 OR book_sum IS NULL THEN
			book_sum = 10;
		END IF;
	RETURN book_sum;
	END; $$ LANGUAGE plpgsql;

--used to make sure that a cart doesn't have more books than there are in stock
CREATE FUNCTION check_quantity_in_cart() 
  RETURNS VOID 
AS $$
DECLARE 
	 r books_in_carts%rowtype;
BEGIN
    FOR r in SELECT * 
	FROM books_in_carts
	LOOP
        IF r.quantity > r.stock THEN
				UPDATE book_ordered
				SET quantity = r.stock 
				WHERE order_num = r.order_num AND isbn=r.isbn;
		END IF;
    END LOOP;
END;
$$ 
LANGUAGE plpgsql;

--statement level trigger
CREATE FUNCTION check_quantity_in_cart_tf()
	RETURNS TRIGGER AS $$
	BEGIN
		PERFORM check_quantity_in_cart();
	RETURN NULL;
	END; $$ LANGUAGE plpgsql;
	

-- will add the reduce the stock and if stock is less than 10 will send email 
CREATE FUNCTION expense_royalties (order_num INT)
  RETURNS VOID 
AS $$
DECLARE 
	 royalties NUMERIC(9,2);
BEGIN
    SELECT SUM(price*percent_of_sales*quantity)
	FROM book NATURAL JOIN book_ordered 
	WHERE book_ordered.order_num = expense_royalties.order_num INTO royalties;
	
	INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
	VALUES ('Royalties', royalties, CAST(EXTRACT(DAY FROM CAST(current_date AS DATE)) AS INTEGER), CAST(EXTRACT(MONTH FROM CAST(current_date AS DATE)) AS INTEGER), CAST(EXTRACT(YEAR FROM CAST(current_date AS DATE)) AS INTEGER));
END;
$$ 
LANGUAGE plpgsql;	

-- will reduce the stock and if stock is less than 10 will send email 
CREATE FUNCTION update_stock (order_num INT)
  RETURNS VOID 
AS $$
DECLARE 
	 r book_ordered%rowtype;
	 new_stock INTEGER;
BEGIN
    FOR r in SELECT * 
	FROM book_ordered
	WHERE book_ordered.order_num = update_stock.order_num
	LOOP
        UPDATE book
			SET stock = stock - r.quantity
			WHERE book.isbn = r.isbn;
    END LOOP;
END;
$$ 
LANGUAGE plpgsql;

--triggered when cart status is changed from 'Cart' to 'Awaiting Fulfillment'
--updates the purchased books' stock levels and records publisher cut of sales as expenses
CREATE FUNCTION cart_ordered_tf() 
   RETURNS trigger AS
$$
BEGIN
	IF (OLD.status = 'Cart' AND NEW.status = 'Awaiting Fulfillment') THEN
		PERFORM update_stock(NEW.order_num);
		PERFORM expense_royalties(NEW.order_num);
		UPDATE cust_order
			SET purchase_day = CAST (EXTRACT(DAY FROM CAST(current_date AS DATE)) AS INTEGER), 
			purchase_month = CAST (EXTRACT(MONTH FROM CAST(current_date AS DATE)) AS INTEGER),
			purchase_year = CAST (EXTRACT(YEAR FROM CAST(current_date AS DATE)) AS INTEGER)
			WHERE cust_order.order_num = NEW.order_num;
	END IF;
	RETURN NULL;
END;
$$ 
LANGUAGE plpgsql;

--triggered if the stock level of a book drops below 10
--orders new books based on previous sales and sends an email
CREATE FUNCTION change_in_stock_tf()
   RETURNS trigger AS
$$
BEGIN
	IF(NEW.stock <10) THEN
		NEW.stock = NEW.stock + calc_books_to_order(NEW.isbn);
		INSERT INTO restock_email (day, month, year, isbn)
		VALUES(CAST(EXTRACT(DAY FROM CAST(current_date AS DATE)) AS INTEGER), CAST(EXTRACT(MONTH FROM CAST(current_date AS DATE)) AS INTEGER), CAST(EXTRACT(YEAR FROM CAST(current_date AS DATE)) AS INTEGER),NEW.isbn);
	END IF;
	RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE FUNCTION change_in_stock_check_carts_tf()
   RETURNS trigger AS
$$
BEGIN
	PERFORM check_quantity_in_cart();
	RETURN NULL;
END;
$$
LANGUAGE plpgsql;

--triggered when a new book is added
--refreshes the materialized view used for searching books
CREATE FUNCTION refresh_search_index() RETURNS trigger AS $$
BEGIN
    REFRESH MATERIALIZED VIEW CONCURRENTLY search_index;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

