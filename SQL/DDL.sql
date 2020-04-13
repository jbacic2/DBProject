create table expense
(expense_id		serial,
type		 	varchar(20),
amount 		  	numeric(9,2) check(amount>0),
exp_day			int check(exp_day>0 and exp_day<=31),
exp_month		int check(exp_month>=1 and exp_month<=12),
exp_year		int check(exp_year>=2000 and exp_year <=2100),
primary key (expense_id)
);

CREATE TABLE postal_zone
(postal_code	varchar(8),
city			varchar(40),
country			varchar(40),
PRIMARY KEY(postal_code)
);

create table customer
(cust_email		varchar(40),
password		varchar(40),
card_num		varchar(20),
expiry_month	int check(expiry_month>=1 and expiry_month<=12),
expiry_year		int check(expiry_year>=2000 and expiry_year <=2100),
cvc_code		int check(cvc_code>0),
street_num		varchar(20),
street_name		varchar(40),
postal_code		varchar(8),
primary key (cust_email),
foreign key (postal_code) references postal_zone
);

create table publisher
(pub_name		varchar(40),
street_num		varchar(20),
street_name		varchar(40),
postal_code		varchar(8),
pub_email		varchar(40),
bank_acc		numeric(12,0),
primary key (pub_name),
foreign key (postal_code) references postal_zone
);

create table phone
(phone_num		numeric(13,0),
pub_name		varchar(40),
primary key (phone_num),
foreign key (pub_name) references publisher
);

create table book
(isbn				varchar(17),
title				text not null,
genre				varchar(40) not null,
cover_image			varchar(200),
synopsis       	    text,
num_pages			int check(num_pages > 0),
price				numeric(5,2) check(price>0),
stock				int check(stock>=0),
pub_name			varchar(40),
percent_of_sales	numeric(3,2) check(percent_of_sales>=0 and percent_of_sales <=1),
legacy_item			boolean default FALSE,
primary key (isbn),
foreign key (pub_name) references publisher
);

create table author
(author_name		text,
isbn				varchar(17),
primary key (author_name, isbn),
foreign key (isbn) references book
);

create table restock_email
(email_id		serial,
day				int check(day>0 and day<=31),
month			int check(month>=1 and month<=12),
year			int check(year>=2000 and year <=2100),
isbn			varchar(17),
primary key (email_id),
foreign key (isbn) references book
);

create table cust_order
(order_num			serial,
status				varchar(20) check (status in ('Cart', 'Awaiting Fulfillment', 'Awaiting Shipment', 'En Route', 'Delivered')) default  'Cart' not null,
purchase_day		int check(purchase_day>0 and purchase_day<=31),
purchase_month		int check(purchase_month>=1 and purchase_month<=12),
purchase_year		int check(purchase_year>=2000 and purchase_year <=2100),
bill_street_num		varchar(20),
bill_street_name	varchar(40),
bill_postal_code	varchar(8),
ship_street_num		varchar(20),
ship_street_name	varchar(40),
ship_postal_code	varchar(8),
cust_email			varchar(40),
primary key (order_num),
foreign key (cust_email) references customer,
foreign key (bill_postal_code) references postal_zone(postal_code),
foreign key (ship_postal_code) references postal_zone(postal_code)
);

create table book_ordered
(order_num			int not null,
isbn				varchar(17),
quantity			int check(quantity>=1),
primary key (order_num,isbn),
foreign key (isbn) references book,
foreign key (order_num) references cust_order
);


--views
create view monthly_sales as
select purchase_year as year, purchase_month as month, sum(book.price*book_ordered.quantity) as sales
from cust_order natural join  book_ordered natural join book
where status not in ('Cart')
group by cust_order.purchase_month, cust_order.purchase_year;

create view yearly_sales as
select purchase_year as year, sum(book.price*book_ordered.quantity) as sales
from cust_order natural join  book_ordered natural join book
where status not in ('Cart')
group by cust_order.purchase_year;

create view monthly_expense as
select exp_year as year, exp_month as month, sum(amount) as expense
from expense 
group by exp_month, exp_year;

create view yearly_expense as
select exp_year as year, sum(amount) as expense
from expense 
group by exp_year;

create view monthly_sales_vs_expense as
select coalesce(monthly_sales.month, monthly_expense.month) as month,
       coalesce(monthly_sales.year, monthly_expense.year) as year,
       sales, (expense * -1) as expense
from monthly_sales
full outer join monthly_expense
on monthly_expense.month = monthly_sales.month AND monthly_expense.year = monthly_sales.year;

create view yearly_sales_vs_expense as
select coalesce(yearly_sales.year, yearly_expense.year) as year, sales, (expense * -1) as expense
from yearly_sales
full outer join yearly_expense
on yearly_expense.year = yearly_sales.year;

create view sales_by_author as 
select purchase_year as year, purchase_month as month, author_name, sum(book.price * book_ordered.quantity) as sales
from cust_order
    natural join book_ordered
    natural join book
    natural join author
where status not in ('Cart')
group by author.author_name, cust_order.purchase_month, cust_order.purchase_year;

create view sales_by_genre as 
select purchase_year as year, purchase_month as month, genre, sum(book.price * book_ordered.quantity) as sales
from cust_order
         natural join book_ordered
         natural join book
where status not in ('Cart')
group by genre, cust_order.purchase_month, cust_order.purchase_year;

create view sales_by_publisher as
select purchase_year as year, purchase_month as month, pub_name, sum(book.price * book_ordered.quantity) as sales
from cust_order
         natural join book_ordered
         natural join book
where status not in ('Cart')
group by pub_name, cust_order.purchase_month, cust_order.purchase_year;

CREATE VIEW books_in_carts AS
SELECT order_num, quantity, isbn, stock
FROM cust_order NATURAL JOIN book_ordered NATURAL JOIN book
WHERE cust_order.status IN ('Cart');

--full text search
create materialized view search_index as
select book.isbn,
       book.title,
       setweight(to_tsvector(book.isbn), 'A') ||
       setweight(to_tsvector(book.title), 'A') ||
       setweight(to_tsvector(book.genre), 'B') ||
       setweight(to_tsvector(book.pub_name), 'C') ||
       setweight(to_tsvector(author.author_name), 'B') ||
       setweight(to_tsvector(coalesce(book.synopsis, '')), 'D') as document
from book
         join author on book.isbn = author.isbn
         where not legacy_item;
create index idx_fts_search on search_index using gin(document);
create unique index on search_index(isbn);
