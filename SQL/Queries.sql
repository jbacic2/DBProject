-- for search by isbn, title, genre, pub_name, author_name where length is <=17
-- this is for general display 
SELECT * FROM book WHERE isbn = CAST (? AS VARCHAR(17)) AND NOT legacy_item UNION SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT * FROM book WHERE genre = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT * FROM book WHERE pub_name = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item

-- when length is less than 40
-- search by title, genre, pub_name, author_name
SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT * FROM book WHERE genre = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT * FROM book WHERE pub_name = CAST (? AS VARCHAR(40)) AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item;

-- for any length
-- search by title, author_name
SELECT * FROM book WHERE title = ? AND NOT legacy_item UNION SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, percent_of_sales, legacy_item  FROM book NATURAL JOIN author WHERE author_name = ? AND NOT legacy_item;


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
