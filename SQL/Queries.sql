--search for books using FTS
--search is performed across ISBN, title, genre, author name, publisher, and synopsis and ranked by how well the query parameter matches the results
select book.isbn as isbn, book.title as title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item from search_index join book on search_index.isbn = book.isbn where document @@ to_tsquery(?) order by ts_rank(document, to_tsquery(?)) DESC

-- get authors for a book
SELECT author_name
FROM author
WHERE isbn = ?

-- to get all info on one book to display to the user based on the isbn 
SELECT isbn, title, genre, synopsis, num_pages, price, stock, pub_name, author_name
FROM book NATURAL JOIN authour
WHERE isbn = CAST (? AS VARCHAR(17)), NOT legacy_item;

-- to get user name and password (used in auth and new_user)
SELECT *
FROM customer 
WHERE cust_email = CAST(? AS VARCHAR(40))

--adding postal zone 
INSERT INTO postal_zone
VALUES (CAST(? AS VARCHAR(8)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(40)))
ON CONFLICT DO NOTHING

--adding new user
INSERT INTO customer 
VALUES (CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(20)), CAST(? AS INT), CAST(? AS INT), CAST(? AS INT), CAST(? AS VARCHAR(20)), CAST(? AS VARCHAR(40)), CAST(? AS VARCHAR(8)))

--check if the user has a 'Cart'
SELECT order_num
FROM cust_order
WHERE status = 'Cart' AND cust_email = ?

--Adding a cart for a user
INSERT INTO cust_order (status, cust_email)
VALUES ('Cart', ?)

-- getting user's current cart 
SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item, order_num, quantity 
FROM book NATURAL JOIN book_ordered NATURAL JOIN cust_order 
WHERE status = 'Cart' AND cust_email = ?

-- Adding to a cart
INSERT INTO book_ordered 
VALUES (CAST(? AS INT), CAST(? AS VARCHAR(17)), CAST(? AS INT)) 
ON CONFLICT (order_num, isbn) DO UPDATE SET quantity = book_ordered.quantity + CAST(? AS INT);

--Remove from cart
DELETE FROM book_ordered WHERE order_num = CAST(? AS INT) AND isbn = CAST(? AS VARCHAR(17))

-- update cart to make order_num
UPDATE cust_order
SET status = 'Awaiting Fulfillment', bill_street_num = CAST(? AS VARCHAR(20)), bill_street_name = CAST(? AS VARCHAR(40)), bill_postal_code = CAST(? AS VARCHAR(8)), ship_street_num = CAST(? AS VARCHAR(20)), ship_street_name = CAST(? AS VARCHAR(40)), ship_postal_code = CAST(? AS VARCHAR(8))
WHERE status = 'Cart' AND cust_email = ?

-- 



--adding a new book
INSERT INTO book VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
--insert related authors for book
INSERT INTO author VALUES (?, ?)

--"remove" a book
UPDATE book SET legacy_item = true WHERE isbn = ?

--get a monthly report of sales vs. expenses
SELECT * FROM monthly_sales_vs_expense WHERE month = ? AND year = ?
--get sales by genre for a specific month ordered by sales
SELECT * FROM sales_by_genre WHERE month = ? AND year = ? ORDER BY sales
--get sales by author for a specific month ordered by sales
SELECT * FROM sales_by_author WHERE month = ? AND year = ? ORDER BY sales
--get sales by publisher for a specific month ordered by sales
SELECT * FROM sales_by_publisher WHERE month = ? AND year = ? ORDER BY sales
