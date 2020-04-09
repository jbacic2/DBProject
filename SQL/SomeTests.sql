INSERT INTO postal_zone
VALUES 'WC1B 3DP', 'London', 'England';

INSERT INTO publisher
VALUES 'Bloomsbury Publishing PLC', 50, 'Bedford Square', 'WC1B 3DP', 'contact@bloomsbury.com', '93824552879';

INSERT INTO phone 
VALUES 4402076315600, 'Bloomsbury Publishing PLC';

INSERT INTO phone 
VALUES 4402076315800, 'Bloomsbury Publishing PLC';

INSERT INTO book 
VALUES '0-7475-3269-9','Harry Potter and the Philosopher\'s Stone', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Philosopher%27s_Stone#/media/File:Harry_Potter_and_the_Philosopher%22'. 'Harry Potter and the Philosopher\'s Stone is a fantasy novel written by British author J. K. Rowling. The first novel in the Harry Potter series and Rowling\'s debut novel, it follows Harry Potter, a young wizard who discovers his magical heritage on his eleventh birthday, when he receives a letter of acceptance to Hogwarts School of Witchcraft and Wizardry.', 223, 21.25, 55, 0.1;

INSERT INTO author
VALUES 'J. K. Rowling', '0-7475-3269-9';

INSERT INTO book 
VALUES '0-7475-3849-2','Harry Potter and the Chamber of Secrets', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Chamber_of_Secrets#/media/File:Harry_Potter_and_the_Chamber_of_Secrets.jpg'. 'Harry Potter and the Chamber of Secrets is a fantasy novel written by British author J. K. Rowling and the second novel in the Harry Potter series. The plot follows Harry\'s second year at Hogwarts School of Witchcraft and Wizardry, during which a series of messages on the walls of the school\'s corridors warn that the "Chamber of Secrets" has been opened and that the "heir of Slytherin" would kill all pupils who do not come from all-magical families.', 251, 22.00, 35, 0.1;

INSERT INTO author
VALUES 'J. K. Rowling', '0-7475-3849-2';

INSERT INTO book 
VALUES '0-7475-4215-5','Harry Potter and the Prisoner of Azkaban', 'Fantasy', 'https://en.wikipedia.org/wiki/Harry_Potter_and_the_Prisoner_of_Azkaban#/media/File:Harry_Potter_and_the_Prisoner_of_Azkaban.jpg', 'Harry Potter and the Prisoner of Azkaban is a fantasy novel written by British author J.K. Rowling and is the third in the Harry Potter series. The book follows Harry Potter, a young wizard, in his third year at Hogwarts School of Witchcraft and Wizardry. Along with friends Ronald Weasley and Hermione Granger, Harry investigates Sirius Black, an escaped prisoner from Azkaban, the wizard prison, believed to be one of Lord Voldemort\'s old allies.', 317, 23.10, 40, 0.1;

INSERT INTO author
VALUES 'J. K. Rowling', '0-7475-4215-5';

isbn				varchar(17),
title				varchar(60) not null,
genere				varchar(40) not null,
cover_image			varchar(50),
sysnopsis       	varchar(750),
num_pages			int check(num_pages > 0),
price				numeric(5,2) check(price>0),
stock				int check(stock>0),
percent_of_sales


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