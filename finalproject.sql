CREATE DATABASE IF NOT EXISTS finalproject_covid;

USE finalproject_covid;

DROP TABLE IF EXISTS `fp_covid_interaction_dtls`;
DROP TABLE IF EXISTS `fp_device_log`;
DROP TABLE IF EXISTS `fp_mobile_dtls`;
DROP TABLE IF EXISTS `fp_test_dtls`;

CREATE TABLE `fp_test_dtls` 
(
 `test_id` INT(10) NOT NULL AUTO_INCREMENT, 
 `test_date` INT(10) NOT NULL, 
 `test_hash` VARCHAR(20) NOT NULL, 
 `test_result` VARCHAR(20) NOT NULL, 
 `registered_date` DATE,
 PRIMARY KEY (`test_id`),
	UNIQUE(test_hash)
 
 
 ); 


CREATE TABLE `fp_mobile_dtls` 
( 
`mobile_id` INT(10) NOT NULL AUTO_INCREMENT, 
`mobile_name` VARCHAR(20) NOT NULL, 
`test_hash` VARCHAR(20) , 
`registered_date` DATE,
PRIMARY KEY (`mobile_id`) ,
UNIQUE(mobile_name) ,
CONSTRAINT `fp_test_hash` FOREIGN KEY (`test_hash`) REFERENCES `fp_test_dtls`(`test_hash`)
)
;



CREATE TABLE `fp_device_log` 
(
 `log_id` INT(10) NOT NULL AUTO_INCREMENT, 
 `mobile_hash` VARCHAR(20) NOT NULL, 
 `neighbour_hash` VARCHAR(20) NOT NULL, 
 `date` INT(10) NOT NULL, 
 `duration` INT(10) NOT NULL, 
 `registered_date` DATE,
 PRIMARY KEY (`log_id`) , 
 CONSTRAINT `fp_mobile_hash` FOREIGN KEY (`mobile_hash`) REFERENCES `fp_mobile_dtls`(`mobile_name`), 
 CONSTRAINT `fp_neighbour_hash` FOREIGN KEY (`neighbour_hash`) REFERENCES `fp_mobile_dtls`(`mobile_name`) 
 ); 


CREATE TABLE `fp_covid_interaction_dtls` 
( `interaction_id` INT(10) NOT NULL AUTO_INCREMENT, 
`log_id` INT(10) NOT NULL, 
`registered_date` DATE, 
PRIMARY KEY (`interaction_id`) ,
 CONSTRAINT `fp_log_dtls` FOREIGN KEY (`log_id`) REFERENCES `fp_device_log`(`log_id`) 
 ); 
