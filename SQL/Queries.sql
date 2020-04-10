-- for search by isbn, title, genre, pub_name, author_name where length is <=17
-- this is for general display 
SELECT isbn, title, cover_image, price
FROM book
WHERE isbn = CAST (? AS VARCHAR(17)), legacy_item = FALSE
UNION
SELECT isbn, title, cover_image, price
FROM book
WHERE title = CAST (? AS TEXT), legacy_item = FALSE
UNION 
SELECT isbn, title, cover_image, price
FROM book
WHERE genre = CAST (? AS VARCHAR(40)), legacy_item = FALSE
UNION 
SELECT isbn, title, cover_image, price
FROM book
WHERE pub_name = CAST (? AS VARCHAR(40)), legacy_item = FALSE
UNION
SELECT isbn, title, cover_image, price
FROM book NATURAL JOIN authour 
WHERE author_name = AST (? AS TEXT), legacy_item = FALSE;


-- when length is less than 40
-- search by title, genre, pub_name, author_name
SELECT isbn, title, cover_image, price
FROM book
WHERE title = CAST (? AS TEXT), legacy_item = FALSE
UNION 
SELECT isbn, title, cover_image, price
FROM book
WHERE genre = CAST (? AS VARCHAR(40)), legacy_item = FALSE
UNION 
SELECT isbn, title, cover_image, price
FROM book
WHERE pub_name = CAST (? AS VARCHAR(40)), legacy_item = FALSE
UNION
SELECT isbn, title, cover_image, price
FROM book NATURAL JOIN authour 
WHERE author_name = AST (? AS TEXT), legacy_item = FALSE;

-- for any length
-- search by isbn, title, genre, pub_name, author_name
SELECT isbn, title, cover_image, price
FROM book
WHERE title = CAST (? AS TEXT), legacy_item = FALSE
UNION 
SELECT isbn, title, cover_image, price
FROM book NATURAL JOIN authour 
WHERE author_name = AST (? AS TEXT), legacy_item = FALSE;

-- to get all info on one book to display to the user based on the isbn 
SELECT isbn, title, genre, cover_image, synopsis, num_pages, price, stock, pub_name, author_name
FROM book NATURAL JOIN authour
WHERE isbn = CAST (? AS VARCHAR(17)), legacy_item = FALSE;
