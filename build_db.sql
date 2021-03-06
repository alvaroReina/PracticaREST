drop table Sketch;
drop table Serie;
drop table UserInfo;

-----------------------
-- Create tables
-----------------------
create table UserInfo (
id integer not null generated always as identity (START WITH 1, INCREMENT BY 1),
fullName varchar(200) not null,
email varchar(256) unique not null,
userRole varchar(16) NOT NULL WITH default 'USER',
picture varchar(256) NOT NULL WITH default 'assets/img/incongnito.png',
CONSTRAINT UserInfo_pk PRIMARY KEY (id)
);

create table Serie (
id integer not null generated always as identity,
title varchar (50) not null, 
score integer default 0,
author integer not null,
views integer default 0,
picture varchar(256) NOT NULL WITH default 'assets/img/not-found.jpg',
FOREIGN KEY (author) REFERENCES UserInfo(id),
CONSTRAINT Serie_pk PRIMARY KEY (id)
);

create table Sketch (
idserie integer not null,
id integer not null generated always as identity (START WITH 1, INCREMENT BY 1),
title varchar(50) not null,
createdAt date not null with default CURRENT_DATE,
score integer default 0,
CONSTRAINT Sketch_pk PRIMARY KEY (id),
FOREIGN KEY (idserie) REFERENCES Serie(id) ON DELETE CASCADE
);

----------------------
-- Create users info
----------------------
INSERT INTO UserInfo(fullName, email) VALUES ('Ibañez', 'ibanyez@system.web');
INSERT INTO UserInfo(fullName, email) VALUES ('Marvel', 'marvel@system.web');
INSERT INTO UserInfo(fullName, email) VALUES ('Sabías que...','diduknow@system.web');
INSERT INTO UserInfo(fullName, email) VALUES ('J.K. Rowling','jkrowling@system.web');
INSERT INTO UserInfo(fullName, email) VALUES ('Dr. DRE','forget@about.dre');
INSERT INTO UserInfo(fullName, email) VALUES ('Thomas Smith', 'thomas_shmith@gmail.com');
INSERT INTO UserInfo(fullName, email) VALUES ('Anne Watson', 'aw@outlook.com');

----------------------
-- Admin Account
----------------------
INSERT INTO UserInfo(fullName, email, userRole) VALUES ('administrador', 'pruebaparaingweb@gmail.com', 'ADMIN');
INSERT INTO UserInfo(fullName, email, userRole) VALUES ('B4 Iweb', 'b4ingenieriaweb@gmail.com', 'ADMIN');
----------------------
-- Create some series for administrador
----------------------
INSERT INTO Serie(title, score, author, picture) VALUES ('Professor X', 10, 8, 'https://static.tvtropes.org/pmwiki/pub/images/professor_x.jpg');
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #1', (SELECT id FROM Serie WHERE title='Professor X'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #2', (SELECT id FROM Serie WHERE title='Professor X'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #3', (SELECT id FROM Serie WHERE title='Professor X'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #4', (SELECT id FROM Serie WHERE title='Professor X'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #5', (SELECT id FROM Serie WHERE title='Professor X'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #6', (SELECT id FROM Serie WHERE title='Professor X'));

INSERT INTO Serie(title, score, author, picture) VALUES ('Acontecimientos lamentables', 9, 8, 'https://static.vix.com/es/sites/default/files/styles/large/public/btg/comics.batanga.com/files/Batalla-Comparativa-Batman-vs-Superman-5.jpg?itok=OBcAod49');
INSERT INTO Sketch(title, idserie) VALUES ('Caída de Constantinopla', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('Caída de Estambul', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('Primer hombre en el espacio', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('Primer hombre en la Luna', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('La estación espacial internacional', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('Aterrizaje en Marte', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('Nacimiento Netscape', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('La era del web', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('La era de las apps', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));
INSERT INTO Sketch(title, idserie) VALUES ('La era de las APIs', (SELECT id FROM Serie WHERE title='Acontecimientos lamentables'));


----------------------
-- Create some series for cuenta pruebas
----------------------
INSERT INTO Serie(title, score, author, picture) VALUES ('Cuentos populares', 10, 9, 'https://images-eu.ssl-images-amazon.com/images/I/61XYMBHqrkL._SS500.jpg' );
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #1', (SELECT id FROM Serie WHERE title='Cuentos populares'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #2', (SELECT id FROM Serie WHERE title='Cuentos populares'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #3', (SELECT id FROM Serie WHERE title='Cuentos populares'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #4', (SELECT id FROM Serie WHERE title='Cuentos populares'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #5', (SELECT id FROM Serie WHERE title='Cuentos populares'));
INSERT INTO Sketch(title, idserie) VALUES ('Tomo #6', (SELECT id FROM Serie WHERE title='Cuentos populares'));

---------------------------------------------------------
-- Create Serie 'La familia Trapisonda' and its Sketches
---------------------------------------------------------
INSERT INTO Serie (title, score, author, picture) VALUES ('La familia Trapisonda', 5, 1, 'http://2.bp.blogspot.com/-W15sCPekkjE/TfUTK5y7B-I/AAAAAAAAChc/oQZhVMJffIY/s1600/trapison01.jpg');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-0', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '1.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-1', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '2.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-2', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '3.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-3', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '4.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-4', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '5.10.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-5', (SELECT id FROM Serie WHERE title ='La familia Trapisonda'), '6.10.1980');

---------------------------------------------------------
-- Create Serie 'Mortadelo y Filemon' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Mortadelo y Filemon', 9, 1, 'https://www.elnacional.cat/uploads/s1/13/77/06/6/mortadelo%20filemon_1_630x630.jpg');
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

---------------------------------------------------------
-- Create Serie 'Rompetechos' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Rompetechos', 6, 1, 'https://img2.rtve.es/im/4741298/?w=900');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-6', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.01.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-9', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.02.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-12', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.03.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-15', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.04.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-18', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.05.1990');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Entrega-21', (SELECT id FROM Serie WHERE title ='Rompetechos'), '1.06.1990');

---------------------------------------------------------
-- Create Serie 'Los cuatro fantasticos' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Los cuatro fantasticos', 6, 2, 'https://vignette.wikia.nocookie.net/marvel/images/4/42/Fantastic_Four_Vol_5_13_Sin_texto.png/revision/latest?cb=20150729164916&path-prefix=es');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-0', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.06.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-1', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.07.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-2', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.08.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-3', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.09.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-4', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.10.2004');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('No-5', (SELECT id FROM Serie WHERE title ='Los cuatro fantasticos'), '1.11.2004');

---------------------------------------------------------
-- Create Serie 'El asombroso Spiderman' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('El asombroso Spiderman', 10, 2, 'http://www.raccoongames.es/img/productos/2016/03/04/image_gallery4.jpg');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-1', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1980');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-2', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1981');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-3', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1982');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-4', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1983');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-5', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1984');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-6', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1985');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-7', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1986');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-8', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1987');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('#-9', (SELECT id FROM Serie WHERE title ='El asombroso Spiderman'), '01.12.1988');

---------------------------------------------------------
-- Create Serie 'El espectacular Spiderman' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('El espectacular Spiderman', 8, 2, 'https://pm1.narvii.com/6106/802c2e5c513567bbb5580a26030a3313ff1591cb_hq.jpg');
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


---------------------------------------------------------
-- Create Serie 'Civil War' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Civil War', 6, 2, 'https://http2.mlstatic.com/civil-war-comic-el-evento-completo-en-digital-D_NQ_NP_522221-MLV20751031166_062016-F.jpg');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Edicion original', (SELECT id FROM Serie WHERE title ='Civil War'), '01.02.1985');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Edicion remasterizada', (SELECT id FROM Serie WHERE title ='Civil War'), '01.02.2014');
-- Current date
INSERT INTO Sketch (title, idserie) VALUES ('Edicion coleccionista', (SELECT id FROM Serie WHERE title ='Civil War'));

---------------------------------------------------------
-- Create Serie 'El Spiderman que parece no dormir nunca' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('El Spiderman que parece no dormir nunca', 3, 2, 'https://as00.epimg.net/meristation/imagenes/2018/08/28/noticias/1535484280_227077_1535484315_noticia_normal.jpg');
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

---------------------------------------------------------
-- Create Serie 'La familia Trapisonda' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Fiestas alrededor del mundo', 5, 3, 'http://sracricket.com/wp-content/uploads/2017/04/decoracion-fiesta-abanicos-comic.jpg');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Navidad', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2015');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Pascua', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2016');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Hanuka', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2017');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Kwanzaa', (SELECT id FROM Serie WHERE title ='Fiestas alrededor del mundo'), '27.4.2018');

---------------------------------------------------------
-- Create Serie 'Harry Potter, el manga' and its Sketches
---------------------------------------------------------
INSERT INTO  Serie (title, score, author, picture) VALUES ('Harry Potter, el manga', 5, 4, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYu3Lw0HhrXp8m8Jp-WK1QX8W8ceQRHv3hq7KgzOwsIwME0fGS');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Piedra Filosofal', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2013');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Camara Secreta', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2014');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el prisionero de Azkaban', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2015');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el caliz de fuego', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2016');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y la Orden del Fenix', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2017');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y el misterio dl principe', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2018');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Harry Potter y las reliquias de la muerte', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2019');
INSERT INTO Sketch (title, idserie, createdAt) VALUES ('Animales fantasticos y donde encontrarlos', (SELECT id FROM Serie WHERE title ='Harry Potter, el manga'), '01.09.2020');

---------------------------------------------------------
-- Create Serie 'The Slim Shady Show' and its Sketches
---------------------------------------------------------
INSERT INTO Serie (title, score, author, picture) VALUES ('The Slim Shady Show', 6, 5, 'https://images-na.ssl-images-amazon.com/images/I/51hZQuKUlbL._SY445_.jpg');
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Slim Shady', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.1.2018', 10);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Eminem', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.2.2018', 8);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('Demminem', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.3.2018', 3);
INSERT INTO Sketch (title, idserie, createdAt, score) VALUES ('SumUP', (SELECT id FROM Serie WHERE title ='The Slim Shady Show'), '01.4.2018', 1);
