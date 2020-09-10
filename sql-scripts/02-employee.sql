CREATE DATABASE  IF NOT EXISTS `employee` ;
USE `employee`;

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) NOT NULL unique ,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) DEFAULT NULL,
  `email` varchar(45) NOT NULL unique,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;


INSERT INTO `employee` VALUES (1,'1' ,'Nagy Mohamed','114 Nasr City ','islam@gmail.com'),
							 (2,'2265','Ramy Sayed ','33 KSA','Ramy@luv2code.com');


