/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 8.0.22 : Database - finalproject_control
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`finalproject_control` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `finalproject_control`;

/*Table structure for table `fp_covid_interaction_dtls` */

DROP TABLE IF EXISTS `fp_covid_interaction_dtls`;

CREATE TABLE `fp_covid_interaction_dtls` (
  `interaction_id` int NOT NULL AUTO_INCREMENT,
  `log_id` int NOT NULL,
  `registered_date` date DEFAULT NULL,
  PRIMARY KEY (`interaction_id`),
  KEY `fp_log_dtls` (`log_id`),
  CONSTRAINT `fp_log_dtls` FOREIGN KEY (`log_id`) REFERENCES `fp_device_log` (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_covid_interaction_dtls` */

insert  into `fp_covid_interaction_dtls`(`interaction_id`,`log_id`,`registered_date`) values 
(1,4,'2020-12-14'),
(2,16,'2020-12-14'),
(3,28,'2020-12-14'),
(4,7,'2020-12-14'),
(5,19,'2020-12-14'),
(6,31,'2020-12-14'),
(7,10,'2020-12-14'),
(8,22,'2020-12-14'),
(9,34,'2020-12-14');

/*Table structure for table `fp_device_log` */

DROP TABLE IF EXISTS `fp_device_log`;

CREATE TABLE `fp_device_log` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `mobile_hash` varchar(20) NOT NULL,
  `neighbour_hash` varchar(20) NOT NULL,
  `date` int NOT NULL,
  `duration` int NOT NULL,
  `registered_date` date DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `fp_mobile_hash` (`mobile_hash`),
  KEY `fp_neighbour_hash` (`neighbour_hash`),
  CONSTRAINT `fp_mobile_hash` FOREIGN KEY (`mobile_hash`) REFERENCES `fp_mobile_dtls` (`mobile_name`),
  CONSTRAINT `fp_neighbour_hash` FOREIGN KEY (`neighbour_hash`) REFERENCES `fp_mobile_dtls` (`mobile_name`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_device_log` */

insert  into `fp_device_log`(`log_id`,`mobile_hash`,`neighbour_hash`,`date`,`duration`,`registered_date`) values 
(1,'1545486612','1421396827',345,45,'2020-12-14'),
(2,'1545486612','305621101',343,20,'2020-12-14'),
(3,'1545486612','-221149134',346,17,'2020-12-14'),
(4,'1421396827','1545486612',345,45,'2020-12-14'),
(5,'1421396827','305621101',340,13,'2020-12-14'),
(6,'1421396827','-221149134',346,14,'2020-12-14'),
(7,'305621101','1545486612',343,20,'2020-12-14'),
(8,'305621101','1421396827',340,13,'2020-12-14'),
(9,'305621101','-221149134',337,26,'2020-12-14'),
(10,'-221149134','1545486612',346,17,'2020-12-14'),
(11,'-221149134','1421396827',346,14,'2020-12-14'),
(12,'-221149134','305621101',337,26,'2020-12-14'),
(13,'1545486612','1421396827',345,45,'2020-12-14'),
(14,'1545486612','305621101',343,20,'2020-12-14'),
(15,'1545486612','-221149134',346,17,'2020-12-14'),
(16,'1421396827','1545486612',345,45,'2020-12-14'),
(17,'1421396827','305621101',340,13,'2020-12-14'),
(18,'1421396827','-221149134',346,14,'2020-12-14'),
(19,'305621101','1545486612',343,20,'2020-12-14'),
(20,'305621101','1421396827',340,13,'2020-12-14'),
(21,'305621101','-221149134',337,26,'2020-12-14'),
(22,'-221149134','1545486612',346,17,'2020-12-14'),
(23,'-221149134','1421396827',346,14,'2020-12-14'),
(24,'-221149134','305621101',337,26,'2020-12-14'),
(25,'1545486612','1421396827',345,45,'2020-12-14'),
(26,'1545486612','305621101',343,20,'2020-12-14'),
(27,'1545486612','-221149134',346,17,'2020-12-14'),
(28,'1421396827','1545486612',345,45,'2020-12-14'),
(29,'1421396827','305621101',340,13,'2020-12-14'),
(30,'1421396827','-221149134',346,14,'2020-12-14'),
(31,'305621101','1545486612',343,20,'2020-12-14'),
(32,'305621101','1421396827',340,13,'2020-12-14'),
(33,'305621101','-221149134',337,26,'2020-12-14'),
(34,'-221149134','1545486612',346,17,'2020-12-14'),
(35,'-221149134','1421396827',346,14,'2020-12-14'),
(36,'-221149134','305621101',337,26,'2020-12-14'),
(37,'1545486612','1421396827',320,45,'2020-12-14'),
(38,'1545486612','305621101',321,20,'2020-12-14'),
(39,'1545486612','-221149134',318,17,'2020-12-14'),
(40,'1421396827','1545486612',320,45,'2020-12-14'),
(41,'1421396827','305621101',340,13,'2020-12-14'),
(42,'1421396827','-221149134',346,14,'2020-12-14'),
(43,'305621101','1545486612',321,20,'2020-12-14'),
(44,'305621101','1421396827',340,13,'2020-12-14'),
(45,'305621101','-221149134',337,26,'2020-12-14'),
(46,'-221149134','1545486612',318,17,'2020-12-14'),
(47,'-221149134','1421396827',346,14,'2020-12-14'),
(48,'-221149134','305621101',337,26,'2020-12-14');

/*Table structure for table `fp_mobile_dtls` */

DROP TABLE IF EXISTS `fp_mobile_dtls`;

CREATE TABLE `fp_mobile_dtls` (
  `mobile_id` int NOT NULL AUTO_INCREMENT,
  `mobile_name` varchar(20) NOT NULL,
  `test_hash` varchar(20) DEFAULT NULL,
  `registered_date` date DEFAULT NULL,
  PRIMARY KEY (`mobile_id`),
  UNIQUE KEY `mobile_name` (`mobile_name`),
  KEY `fp_test_hash` (`test_hash`),
  CONSTRAINT `fp_test_hash` FOREIGN KEY (`test_hash`) REFERENCES `fp_test_dtls` (`test_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_mobile_dtls` */

insert  into `fp_mobile_dtls`(`mobile_id`,`mobile_name`,`test_hash`,`registered_date`) values 
(1,'1545486612','TEST##HASH##DEV1','2020-12-14'),
(2,'1421396827',NULL,'2020-12-14'),
(3,'305621101',NULL,'2020-12-14'),
(4,'-221149134',NULL,'2020-12-14');

/*Table structure for table `fp_test_dtls` */

DROP TABLE IF EXISTS `fp_test_dtls`;

CREATE TABLE `fp_test_dtls` (
  `test_id` int NOT NULL AUTO_INCREMENT,
  `test_date` int NOT NULL,
  `test_hash` varchar(20) NOT NULL,
  `test_result` varchar(20) NOT NULL,
  `registered_date` date DEFAULT NULL,
  PRIMARY KEY (`test_id`),
  UNIQUE KEY `test_hash` (`test_hash`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `fp_test_dtls` */

insert  into `fp_test_dtls`(`test_id`,`test_date`,`test_hash`,`test_result`,`registered_date`) values 
(1,341,'TEST##HASH##DEV1','positive','2020-12-14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
