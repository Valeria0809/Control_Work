CREATE DATABASE Human_Friends;
CREATE TABLE Animal (
  id INT PRIMARY KEY AUTO_INCREMENT,
  species VARCHAR(50)
);
insert into Animal (species)
values ('Pets'),
		('Pack');
CREATE TABLE Pets (
  id INT PRIMARY KEY AUTO_INCREMENT,
  species_name VARCHAR(50),
  class_id INT,
  FOREIGN KEY (class_id) REFERENCES Animal(id)
);
insert into Pets (species_name, class_id)
values ('Dogs', 1),
	   ('Cats', 1),
	   ('Hamsters', 1); 
CREATE TABLE Pack (
  id INT PRIMARY key AUTO_INCREMENT,
  species_name VARCHAR(50),
  class_id INT,
  FOREIGN KEY (class_id) REFERENCES Animal(id)
);
insert into Pack (species_name, class_id)
values ('Horses', 2),
	   ('Camels', 2),
	   ('Donkeys', 2);
       
       
