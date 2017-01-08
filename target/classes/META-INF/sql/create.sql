
--
-- Host: localhost Database: PaymentSystem
-- ------------------------------------------------------
-- Server version 5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `iban` varchar(40) NOT NULL,
  `type` varchar(32) NOT NULL,
  `balance` decimal(16,3) DEFAULT '0.000',
  `status` varchar(7) NOT NULL,
  `currencyCode` varchar(3) DEFAULT NULL,
  `Rule` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`iban`),
  KEY `currencyCode` (`currencyCode`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`currencyCode`) REFERENCES `currency` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('AZ21NABZ00000000137010001944','Investment',70000000.500,'ACTIVE','JOD','five.years.ahead'),('JO94CBJO0010000000000131000302','TYPE',310.628,'ACTIVE','USD','five.months.ahead'),('JO94CBJO0010000000000131000321','investment',500.000,'ACTIVE','JOD','five.days.ahead'),('RO49AAAA1B31007593840000','TYPE',0.000,'ACTIVE','USD','five.days.ahead');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currency` (
  `code` varchar(3) NOT NULL,
  `name` varchar(32) NOT NULL,
  `coinsName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currency`
--

LOCK TABLES `currency` WRITE;
/*!40000 ALTER TABLE `currency` DISABLE KEYS */;
INSERT INTO `currency` VALUES ('JOD','Jordanian Dinar','Piasters'),('USD','US Dollars','Cents');
/*!40000 ALTER TABLE `currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentPurpose`
--

DROP TABLE IF EXISTS `paymentPurpose`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymentPurpose` (
  `code` varchar(4) NOT NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentPurpose`
--

LOCK TABLES `paymentPurpose` WRITE;
/*!40000 ALTER TABLE `paymentPurpose` DISABLE KEYS */;
INSERT INTO `paymentPurpose` VALUES ('GHAD','Ahmadburghol'),('SALA','GHADEER');
/*!40000 ALTER TABLE `paymentPurpose` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentRequest`
--

DROP TABLE IF EXISTS `paymentRequest`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paymentRequest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ordIban` varchar(40) DEFAULT NULL,
  `benefIban` varchar(40) DEFAULT NULL,
  `benefName` varchar(32) NOT NULL,
  `amount` decimal(16,3) DEFAULT NULL,
  `currencyCode` varchar(3) DEFAULT NULL,
  `purposeCode` varchar(4) DEFAULT NULL,
  `paymentDate` date NOT NULL,
  `amountInWords` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `currencyCode` (`currencyCode`),
  KEY `purposeCode` (`purposeCode`),
  CONSTRAINT `paymentRequest_ibfk_1` FOREIGN KEY (`currencyCode`) REFERENCES `currency` (`code`),
  CONSTRAINT `paymentRequest_ibfk_2` FOREIGN KEY (`purposeCode`) REFERENCES `paymentPurpose` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentRequest`
--

LOCK TABLES `paymentRequest` WRITE;
/*!40000 ALTER TABLE `paymentRequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentRequest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-05 17:25:05

