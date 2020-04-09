create table expense
(expense_id		serial,
type		 	varchar(20),
amount 		  	numeric(9,2) check(amount>0),
exp_day			int check(exp_day>0 and exp_day<=31),
exp_month		int check(exp_month>=1 and exp_month<=12),
exp_year		int check(exp_year>=2000 and exp_year <=2100),
primary key (expense_id)
);
	
create table customer
(cust_email		varchar(40),
password		varchar(40),
card_num		numeric(16,0) check(card_num>=1000000000000000),
expiry_month	numeric(2,0) check(expiry_month>=1 and expiry_month<=12),
expiry_year		numeric(4,0) check(expiry_year>=2000 and expiry_year <=2100),
cvc_code		numeric(3,0) check(cvc_code>0),
street_num		numeric(6,0),
street_name		varchar(40),
city			varchar(40),
country			varchar(40),
postal_code		varchar(6),
primary key (cust_email)
);

create table publisher
(pub_name		varchar(40),
street_num		numeric(6,0),
street_name		varchar(40),
city			varchar(40),
country			varchar(40),
postal_code		varchar(6),
pub_email		varchar(40),
bank_acc		numeric(12,0),
primary key (pub_email)
);

create table phone
(phone_num		numeric(13,0),
pub_name		varchar(40),
primary key (phone_num),
foreign key (pub_name) references publisher
);

create table book
(isbn				varchar(17),
title				varchar(60) not null,
genere				varchar(40) not null,
cover_image			bytea,
sysnopsis       	varchar(750),
num_pages			numeric(6,0) check(num_pages > 0),
price				numeric(5,2) check(price>0),
stock				numeric(6,0) check(stock>0),
percent_of_sales	numeric(3,2) check(percent_of_sales>=0 and percent_of_sales <=1),
primary key (isbn)
);

create table author
(author_name		varchar(40),
isbn				varchar(17),
primary key (author_name, isbn),
foreign key (isbn) references book
);

create table restock_email
(email_id		serial,
exp_day			int check(exp_day>0 and exp_day<=31),
exp_month		int check(exp_month>=1 and exp_month<=12),
exp_year		int check(exp_year>=2000 and exp_year <=2100),
isbn			varchar(17),
primary key (email_id),
foreign key (isbn) references book
);

create table cust_order
(order_num			serial,
status				varchar(20) check (status in ('Cart', 'Awaiting Fulfillment', 'Awaiting Shipment', 'En Route', 'Delivered')),
purchase_day		int check(purchase_day>0 and purchase_day<=31),
purchase_month		int check(purchase_month>=1 and purchase_month<=12),
purchase_year		int check(purchase_year>=2000 and purchase_year <=2100),
bill_street_num		numeric(6,0),
bill_street_name	varchar(40),
bill_city			varchar(40),
bill_country		varchar(40),
bill_postal_code	varchar(6),
ship_street_num		numeric(6,0),
ship_street_name	varchar(40),
ship_city			varchar(40),
ship_country		varchar(40),
ship_postal_code	varchar(6),
cust_email			varchar(40),
primary key (order_num),
foreign key (cust_email) references customer
);

create table book_ordered
(order_num			int not null,
isbn				varchar(17),
quantity			numeric(4,0) check(quantity>=1),
primary key (order_num,isbn),
foreign key (isbn) references book,
foreign key (order_num) references cust_order
);

create view monthly_sales as
select purchase_year as year, purchase_month as month, sum(book.price*book_ordred.quantity) as sales
from cust_order natural join  book_ordred natural join book
where status not in ('Cart')
group by cust_order.purchase_month, cust_order.purchase_year;

create view yearly_sales as
select purchase_year as year, sum(book.price*book_ordred.quantity) as sales
from cust_order natural join  book_ordred natural join book
where status not in ('Cart')
group by cust_order.purchase_year;

create view monthly_expense as
select exp_year as year, exp_month as month, sum(amount) as expense
from expense 
where status not in ('Cart')
group by exp_month, exp_year;

create view yearly_expense as
select exp_year as year, sum(amount) as expense
from expense 
where status not in ('Cart')
group by exp_year;

create view monthly_sales_vs_expense as
select *
from monthly_sales natural join monthly_expense;

create view yearly_sales_vs_expense as
select *
from yearly_sales natural join yearly_expense;

create view sales_by_author as 
select author_name, sum(book.price*book_ordred.quantity) as sales
from book_ordred natural join book natural join author
where status not in ('Cart')
group by author.author_name;

create view sales_by_genere as 
select genere, sum(book.price*book_ordred.quantity) as sales
from book_ordred natural join book
where status not in ('Cart')
group by author.author_name;