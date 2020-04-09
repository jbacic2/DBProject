create trigger change_quantity_in_cart before update of quantity on book_ordered or insert on 
	declare books_in_stock int;
	referencing new row as nrow
	for each row 
	select stock from book where book.isbn = nrow.isbn into books_in_stock
	when (rnow.quantity > books_in_stock) 
	begin atomic
		set nrow.quantity = books_in_stock
	end

//this would prevent ordering too much 
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