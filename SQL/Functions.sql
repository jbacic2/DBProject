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
		RAISE EXCEPTION 'Invalid Date: %, %, % is not a real day', NEW.exp_day,NEW.exp_month,NEW.exp_year;
		END IF;
	RETURN NEW;
	END; $$ LANGUAGE plpgsql;

--Satement level trigger 
CREATE FUNCTION check_quantity_in_cart_tf()
	RETURNS TRIGGER AS $$
	BEGIN
		PERFORM check_quantity_in_cart();
	RETURN NULL;
	END; $$ LANGUAGE plpgsql;
	
CREATE FUNCTION check_quantity_in_cart() 
  RETURNS VOID 
AS
$$
DECLARE 
	 r books_in_carts%rowtype;
BEGIN
    FOR r in SELECT * 
	FROM books_in_carts
	LOOP
        IF r.quantity > r.stock THEN
				UPDATE book_ordred
				SET quantity = r.stock 
				WHERE order_num = r.order_num AND isbn=r.isbn;
		END IF;
    END LOOP;
END;
$$ 
LANGUAGE plpgsql;


CREATE FUNCTION update_stock (order_num INT)
  RETURNS VOID 
AS
$$
DECLARE 
	 r book_ordered%rowtype;
BEGIN
    FOR r in SELECT * 
	FROM book_ordered
	WHERE book_ordred.order_num = update_stock.order_num
	LOOP
        UPDATE book
			SET stock = stock - r.quantity
			WHERE book.isbn = r.isbn;
    END LOOP;
END;
$$ 
LANGUAGE plpgsql;


CREATE FUNCTION change_quantity_in_cart_tf() 
   RETURNS trigger AS
$$
BEGIN
	PERFORM check_quantity_in_cart();
END;
$$ 
LANGUAGE plpgsql;



/*CREATE PROCEDURE check_quantity_in_cart ()
	BEGIN 
		FOR r IN
			SELECT * FROM cust_order NATURAL JOIN book_ordred NATURAL JOIN book
			WHERE cust_order.status IN ('Cart')
		LOOP
			IF r.quantity > r.stock THEN
				UPDATE book_ordred
				SET quantity = r.stock 
				WHERE order_num = r.order_num AND isbn=r.isbn
			END IF;
		END LOOP;
		RETURN;
	END; $$ LANGUAGE plpgsql;
	
CREATE PROCEDURE update_stock (order_num INT)
	BEGIN
		FOR r IN
			SELECT * FROM book_ordred 
			WHERE book_ordred.order_num = update_stock.order_num
		LOOP
			UPDATE book
			SET stock = stock - r.quantity
			WHERE book.isbn = r.isbn
		END LOOP;
	END; $$ LANGUAGE plpgsql;

*/

/*create function monthly_sales (year int)
	returns tabel (
		month int,
		year  int,
		sales numeric(9,2)
	)
    as $$
	begin
		select purchase_month as month, purchase_year as year, sum(book.price*book_ordred.quantity) as sales
		from cust_order natural join  book_ordred natural join book
		group by cust_order.purchase_year, cust_order.purchase_month 
		having cust_order.purchase_year = monthly_sales.year  
	end; $$ language plpgsql;

create function sales_by_auth (year int)
	returns tabel (
		author_name varchar(40)
		sales numeric(10,2)
	)
    as $$
	begin
		select purchase_month as month, purchase_year as year, sum(book.price*book_ordred.quantity) as sales
		from cust_order natural join  book_ordred natural join book
		group by cust_order.purchase_year, cust_order.purchase_month 
		having cust_order.purchase_year = monthly_sales.year  
	end; $$ language plpgsql;
	
	
	create function make_order(order_num int)
	returns tabel(
		
	)
	begin atomic
		update cust_order
			set purchase_day = select extract(day from date current_date);
			set purchase_month = select extract(month from date current_date);
			set purchase_year = select extract(year from date current_date);
			set status = 'Awaiting Fulfillment';
			where cust_order.order_num = make_order.order_num
	end;*/
