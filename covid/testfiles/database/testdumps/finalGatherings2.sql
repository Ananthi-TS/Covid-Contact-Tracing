/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.22 : Database - finalproject
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`finalproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `finalproject`;

/*Table structure for table `fp_covid_interaction_dtls` */

DROP TABLE IF EXISTS `fp_covid_interaction_dtls`;

CREATE TABLE `fp_covid_interaction_dtls` (
  `interaction_id` int NOT NULL AUTO_INCREMENT,
  `log_id` int NOT NULL,
  `identified_date` int NOT NULL,
  PRIMARY KEY (`interaction_id`),
  KEY `fp_lod_dtls` (`log_id`),
  CONSTRAINT `fp_lod_dtls` FOREIGN KEY (`log_id`) REFERENCES `fp_device_log` (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2060 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_covid_interaction_dtls` */

insert  into `fp_covid_interaction_dtls`(`interaction_id`,`log_id`,`identified_date`) values 
(2056,3,5);

/*Table structure for table `fp_device_log` */

DROP TABLE IF EXISTS `fp_device_log`;

CREATE TABLE `fp_device_log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `mobile_hash` varchar(50) NOT NULL,
  `neighbour_hash` varchar(50) NOT NULL,
  `date` int NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `fp_mobile_hash` (`mobile_hash`),
  KEY `fp_neighbour_hash` (`neighbour_hash`),
  CONSTRAINT `fp_mobile_hash` FOREIGN KEY (`mobile_hash`) REFERENCES `fp_mobile_dtls` (`mobile_name`),
  CONSTRAINT `fp_neighbour_hash` FOREIGN KEY (`neighbour_hash`) REFERENCES `fp_mobile_dtls` (`mobile_name`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_device_log` */

insert  into `fp_device_log`(`log_id`,`mobile_hash`,`neighbour_hash`,`date`,`duration`) values 
(3,'1','2',10,15),
(4,'1','3',10,15),
(5,'1','4',10,12),
(6,'1','5',11,11),
(7,'2','1',10,15),
(8,'2','6',10,13),
(9,'2','7',10,9),
(11,'2','3',10,4),
(12,'3','1',10,15),
(13,'3','2',10,4),
(14,'3','8',10,14),
(15,'3','9',11,12),
(16,'4','1',10,15),
(17,'5','9',10,11),
(18,'5','1',9,11),
(19,'6','2',10,13),
(20,'7','2',10,9),
(21,'8','3',10,14),
(22,'1','2',10,12),
(23,'2','1',10,12),
(28,'7','6',10,13),
(29,'8','6',10,3),
(30,'6','7',10,13),
(31,'6','8',10,3),
(32,'6','8',10,10),
(33,'7','8',10,18),
(34,'8','7',10,18);

/*Table structure for table `fp_mobile_dtls` */

DROP TABLE IF EXISTS `fp_mobile_dtls`;

CREATE TABLE `fp_mobile_dtls` (
  `mobile_id` int NOT NULL AUTO_INCREMENT,
  `mobile_name` varchar(50) NOT NULL,
  `test_hash` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`mobile_id`),
  UNIQUE KEY `mobile_name` (`mobile_name`),
  KEY `fp_test_hash` (`test_hash`),
  CONSTRAINT `fp_test_hash` FOREIGN KEY (`test_hash`) REFERENCES `fp_test_dtls` (`test_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_mobile_dtls` */

insert  into `fp_mobile_dtls`(`mobile_id`,`mobile_name`,`test_hash`) values 
(7,'1',NULL),
(8,'2','asd'),
(9,'3','sad'),
(10,'4','we35'),
(11,'5','aaa'),
(12,'6',NULL),
(13,'7',NULL),
(14,'8',NULL),
(15,'9',NULL),
(16,'10',NULL),
(17,'11',NULL),
(18,'12',NULL),
(19,'13',NULL),
(20,'14',NULL),
(21,'15',NULL),
(22,'16',NULL),
(23,'17',NULL),
(24,'18',NULL),
(25,'19',NULL),
(26,'20',NULL);

/*Table structure for table `fp_test_dtls` */

DROP TABLE IF EXISTS `fp_test_dtls`;

CREATE TABLE `fp_test_dtls` (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `test_date` int NOT NULL,
  `test_hash` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `test_result` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`test_id`),
  UNIQUE KEY `test_hash` (`test_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_test_dtls` */

insert  into `fp_test_dtls`(`test_id`,`test_date`,`test_hash`,`test_result`) values 
(1,15,'we35','negative'),
(2,11,'asd','positive'),
(3,16,'sad','negative'),
(5,14,'aaa','positive'),
(6,300,'zzzzzzz','positive'),
(12,300,'aq','positive');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
