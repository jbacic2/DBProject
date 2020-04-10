INSERT INTO postal_zone
VALUES ('WC1B 3DP', 'London', 'England');





INSERT INTO publisher
VALUES ('Bloomsbury', 50, 'Bedford Square', 'WC1B 3DP', 'contact@bloomsbury.com', '93824552879');

INSERT INTO phone 
VALUES (4402076315600, 'Bloomsbury');

INSERT INTO phone 
VALUES (4402076315800, 'Bloomsbury');

INSERT INTO book 
VALUES ('0-7475-3269-9','Harry Potter and the Philosophers Stone', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Philosopher%27s_Stone#/media/File:Harry_Potter_and_the_Philosopher%22', 'Harry Potter and the Philosophers Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowlings debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.', 223, 21.25, 55, 'Bloomsbury', 0.1);

INSERT INTO author
VALUES ('J. K. Rowling', '0-7475-3269-9');

INSERT INTO book 
VALUES ('0-7475-3849-2','Harry Potter and the Chamber of Secrets', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Chamber_of_Secrets#/media/File:Harry_Potter_and_the_Chamber_of_Secrets.jpg', 'Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harrys second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the schools corridors warn that the "Chamber of Secrets" has been opened and that the "heir of Slytherin" would kill all pupils who do not come from all-magical families.', 251, 22.00, 35, 'Bloomsbury', 0.1);

INSERT INTO author
VALUES ('J. K. Rowling', '0-7475-3849-2');

INSERT INTO book 
VALUES ('0-7475-4215-5','Harry Potter and the Prisoner of Azkaban', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Prisoner_of_Azkaban#/media/File:Harry_Potter_and_the_Prisoner_of_Azkaban.jpg', 'Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J.K. Rowling and is the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban, the wizard prison, believed to be one of Lord Voldemorts old allies.', 317, 23.10, 40, 'Bloomsbury', 0.1);

INSERT INTO author
VALUES ('J. K. Rowling', '0-7475-4215-5');



INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20000, 1, 2, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20070.5, 1, 3, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20075.5, 1, 4, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20075.58, 1, 5, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20895.35, 1, 6, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20075.9, 1, 7, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20122.58, 1, 8, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20300, 1, 9, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20895.35, 1, 10, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20275.91, 1, 11, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20182.33, 1, 12, 2019);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20303.3, 1, 1, 2020);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20232.03, 1, 2, 2020);

INSERT INTO expense (type,amount,exp_day,exp_month,exp_year)
VALUES ('Labor', 20344.43, 1, 3, 2020);

INSERT INTO postal_zone
VALUES ('K1N 7A1', 'Ottawa', 'Canada');

INSERT INTO customer
VALUES ('someone@hello.com', 'pword', 4567456745674567, 12, 2020, 122, 12, 'Byward Market Square', 'K1N 7A1');

INSERT INTO postal_zone
VALUES ('K1S 5B6', 'Ottawa', 'Canada');

INSERT INTO customer
VALUES ('carl@cmail.ca', 'abc123', 1234567891234561, 11, 2022, 665, 1125, 'Colonel By Dr,', 'K1S 5B6');

INSERT INTO cust_order 
VALUES (1, 'Delivered', 3, 09, 2019, 12, 'Byward Market Square', 'K1N 7A1', 12, 'Byward Market Square', 'K1N 7A1', 'someone@hello.com');

INSERT INTO cust_order 
VALUES (2, 'Delivered', 12, 11, 2019, 12, 'Byward Market Square', 'K1N 7A1', 12, 'Byward Market Square', 'K1N 7A1', 'someone@hello.com');

INSERT INTO cust_order 
VALUES (3, 'Delivered', 9, 3, 2020, 1125, 'Colonel By Dr,', 'K1S 5B6', 1125, 'Colonel By Dr,', 'K1S 5B6', 'carl@cmail.ca');

INSERT INTO cust_order 
VALUES (4, 'En Route', 1, 4, 2020, 1125, 'Colonel By Dr,', 'K1S 5B6', 1125, 'Colonel By Dr,', 'K1S 5B6', 'carl@cmail.ca');


INSERT INTO cust_order (order_num, cust_email)
VALUES (5, 'someone@hello.com');

INSERT INTO book_ordered
values (1, '0-7475-3269-9', 2);

INSERT INTO book_ordered
values (2, '0-7475-4215-5', 2);

INSERT INTO book_ordered
values (3, '0-7475-3269-9', 1);

INSERT INTO book_ordered
values (4, '0-7475-4215-5', 1);

	