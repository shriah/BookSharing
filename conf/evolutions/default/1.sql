# DC schema
 
# --- !Ups
CREATE TABLE `books` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '',
  `edition` varchar(200) DEFAULT '',
  `binding` varchar(200) DEFAULT '',
  `published` date DEFAULT NULL,
  `price` double DEFAULT NULL,
  `media` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 
# --- !Downs

DROP TABLE books;
