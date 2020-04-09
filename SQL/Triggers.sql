CREATE TRIGGER change_quantity_in_cart AFTER UPDATE OF quantity ON book_ordered OR INSERT ON book_ordered
	EXECUTE PROCEDURE check_quantity_in_cart ();
	

-- when order status is changed from 'Cart' to 'Awaiting Fulfillment' this is called to change the amount in stock,
-- to set the date details and check that no one else has a larger quantity of a book than there are in stock
CREATE TRIGGER update_order_status AFTER UPDATE OF status ON cust_order 
	DECLARE books_in_stock INT;
	REFERENCING NEW ROW AS nrow
	REFERENCING OLD ROW AS orow
	FOR EACH ROW 
	WHEN(orow.status = 'Cart' AND nrow.status = 'Awaiting Fulfillment') 
		EXECUTE PROCEDURE update_stock(nrow.order_num);
		UPDATE cust_order
			SET purchase_day = SELECT EXTRACT(DAY FROM DATE current_date), 
			purchase_month = SELECT EXTRACT(MONTH FROM DATE current_date),
			purchase_year = SELECT EXTRACT(YEAR FROM DATE current_date)
			WHERE cust_order.order_num = update_order_status.order_num
	END LOOP;
	EXECUTE PROCEDURE check_quantity_in_cart ();	


/*create trigger change_quantity_in_cart before update of quantity on book_ordered or insert on 
	declare books_in_stock int;
	referencing new row as nrow
	for each row 
	select stock from book where book.isbn = nrow.isbn into books_in_stock
	when (rnow.quantity > books_in_stock) 
	begin atomic
		set nrow.quantity = books_in_stock
	end


create trigger change_quantity_in_cart after update of quantity on book_ordered or insert on 
	declare books_in_stock int;
	for each row 
	select stock from book where book.isbn = row.isbn into books_in_stock
	when (row.quantity > books_in_stock) 
	begin atomic
		update book_ordered
		set quantity = books_in_stock
		when 
	end
*/