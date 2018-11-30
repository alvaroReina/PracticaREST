drop table Sketch;
drop table Serie;
drop table UserInfo;

-----------------------
-- Create tables
-----------------------
create table UserInfo (
id integer not null,
name varchar(200) not null,
email varchar(256) unique not null,
role varchar(16) default 'USER',
CONSTRAINT UserInfo_pk PRIMARY KEY (id)
);

create table Serie (
id integer not null generated always as identity,
title varchar (50) not null, 
score integer default 0,
author integer not null,
FOREIGN KEY (author) REFERENCES UserInfo(id),
CONSTRAINT Serie_pk PRIMARY KEY (id)
);

create table Sketch (
idserie integer not null,
id integer not null generated always as identity (START WITH 1, INCREMENT BY 1),
title varchar(50) not null,
createdAt date default CURRENT_DATE,
score integer default 0,
CONSTRAINT Sketch_pk PRIMARY KEY (id),
FOREIGN KEY (idserie) REFERENCES Serie(id) ON DELETE CASCADE
);

----------------------
-- Create users info
----------------------
INSERT INTO UserInfo(id, name, email, role) VALUES (10, 'Thomas Smith', 'thomas_shmith@gmail.com', 'USER');
INSERT INTO UserInfo(id, name, email, role) VALUES (11, 'admin', 'admin', 'ADMIN');
INSERT INTO UserInfo(id, name, email, role) VALUES (12, 'Anne Watson', 'aw@outlook.com', 'USER');

---------------------------------------------------------
-- Create Serie 'La familia Trapisonda' and its Sketches
---------------------------------------------------------
INSERT INTO Serie (title, score, author)VALUES ('La familia Trapisonda', 5, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-0', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '1.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-1', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '2.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-2', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '3.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-3', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '4.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-4', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '5.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-5', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '6.10.1980');

INSERT INTO  Serie (title, score, author) VALUES ('Mortadelo y Filemon', 9, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-1', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '7.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-2', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '8.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-3', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '9.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-4', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '10.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-5', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '11.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-6', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '12.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-7', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '13.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-8', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '14.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-9', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '15.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-10', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '16.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-11', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '17.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-12', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '18.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-13', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '19.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-14', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '20.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Especial navidad', (SELECT id FROM Serie WHERE title ='Mortadelo y Filemon'), '21.10.1980');

INSERT INTO  Serie (title, score, author) VALUES ('Rompetechos', 6, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-6', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.01.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-9', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.02.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-12', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.03.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-15', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.04.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-18', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.05.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-21', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.06.1990');

INSERT INTO  Serie (title, score, author) VALUES ('Los cuatro fantasticos', 6, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-0', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.06.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-1', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.07.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-2', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.08.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-3', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.09.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-4', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.10.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-5', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.11.2004');


INSERT INTO  Serie (title, score, author) VALUES ('El asombroso Spiderman', 10, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-1', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-2', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1981');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-3', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1982');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-4', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1983');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-5', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1984');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-6', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1985');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-7', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1986');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-8', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1987');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-9', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1988');

INSERT INTO  Serie (title, score, author) VALUES ('El espectacular Spiderman', 8, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.10', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1989');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.11', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.12', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1991');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.13', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1992');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.14', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1993');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.15', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1994');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.16', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1995');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.17', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1996');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.18', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1997');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.19', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1998');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.20', (SELECT id FROM Serie WHERE title ='El espectacular Spiderman'), '01.12.1999');


INSERT INTO  Serie (title, score, author) VALUES ('Civil War', 6, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Edicion original', (SELECT id FROM Serie WHERE title ='Civil War'), '01.02.1985');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Edicion remasterizada', (SELECT id FROM Serie WHERE title ='Civil War'), '01.02.2014');
-- Current date
INSERT INTO Sketch (title, idserie) VALUES ('Edicion coleccionista', (SELECT id FROM Serie WHERE title ='Civil War'));

INSERT INTO  Serie (title, score, author) VALUES ('El Spiderman que parece no dormir nunca', 3, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.20', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2003');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.21', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.22', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2005');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.23', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2006');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.24', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2007');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.25', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2008');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.26', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2009');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.27', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2010');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.28', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2011');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No.29', (SELECT id FROM Serie WHERE title ='El Spiderman que parece no dormir nunca'), '13.05.2012');


INSERT INTO  Serie (title, score, author) VALUES ('Fiestas alrededor del mundo', 5, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Navidad', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2015');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Pascua', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2016');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Hanuka', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2017');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Kwanzaa', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2018');


/**/
INSERT INTO  Serie (title, score, author) VALUES ('Harry Potter, el manga', 5, 10);
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Piedra Filosofal', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2013');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Camara Secreta', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2014');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el prisionero de Azkaban', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2015');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el caliz de fuego', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2016');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Orden del Fenix', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2017');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el misterio dl principe', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2018');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y las reliquias de la muerte', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2019');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Animales fantasticos y donde encontrarlos', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2020');

INSERT INTO Serie (title, score, author) VALUES ('The Slim Shady Show', 6, 10);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Slim Shady', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.1.2018', 10);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Eminem', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.2.2018', 8);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Demminem', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.3.2018', 3);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('SumUP', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.4.2018', 1);
