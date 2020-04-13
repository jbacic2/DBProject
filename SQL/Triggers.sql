--This will make sure that no cart is holding more books than in stock when adding to cart
CREATE TRIGGER check_quantity_in_cart_update AFTER UPDATE OF quantity ON book_ordered 
	EXECUTE PROCEDURE check_quantity_in_cart_tf();

CREATE TRIGGER change_quantity_in_cart_insert BEFORE INSERT ON book_ordered
	EXECUTE PROCEDURE check_quantity_in_cart_tf();
	
--When book stock changes make sure the the quantity is cart is ok
CREATE TRIGGER change_in_stock BEFORE UPDATE OF stock ON book
	FOR EACH ROW
	EXECUTE PROCEDURE change_in_stock_tf();
	
CREATE TRIGGER change_in_stock_check_carts AFTER UPDATE OF stock ON book
	EXECUTE PROCEDURE change_in_stock_check_carts_tf();
	
-- When an order status is changes this trigger will check if the order has be purchased and 
--will set the date accordingly 
CREATE TRIGGER cart_ordered AFTER UPDATE OF status ON cust_order
	FOR EACH ROW
	EXECUTE PROCEDURE cart_ordered_tf();
	
--date checking expense
CREATE TRIGGER check_date_exp_update AFTER UPDATE ON expense
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_exp_tf();
	
CREATE TRIGGER check_date_exp_insert AFTER INSERT ON expense 
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_exp_tf();

--date checking restock_email
CREATE TRIGGER check_date_restock_update AFTER UPDATE ON restock_email
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_restock_tf();
	
CREATE TRIGGER check_date_restock_insert AFTER INSERT ON restock_email
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_restock_tf();

--date checking cust_order
/*CREATE TRIGGER check_date_order_update AFTER UPDATE ON cust_order
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_order_tf();
	
CREATE TRIGGER check_date_order_insert AFTER INSERT ON cust_order
	FOR EACH ROW
	EXECUTE PROCEDURE check_date_order_tf();*/
