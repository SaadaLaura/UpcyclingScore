-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: upcycling
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `packagingtypes`
--

DROP TABLE IF EXISTS `packagingtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagingtypes` (
  `PackagingTypeID` int NOT NULL AUTO_INCREMENT,
  `TypeName` varchar(100) NOT NULL,
  `PracticalScore` int DEFAULT '0',
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`PackagingTypeID`),
  UNIQUE KEY `uc_TypeName` (`TypeName`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packagingtypes`
--

LOCK TABLES `packagingtypes` WRITE;
/*!40000 ALTER TABLE `packagingtypes` DISABLE KEYS */;
INSERT INTO `packagingtypes` VALUES (1,'Bouteille plastique',14,1),(2,'Bouchon plastique',10,1),(3,'Pot',16,1),(4,'Boîte de conserve',14,1),(5,'Cannette',12,1),(6,'Boîte de céréales',12,1),(9,'Sac en papier',10,1),(10,'Bouteille en verre',16,1),(11,'Carton d\'œufs',14,1),(12,'Sac en plastique',8,1),(13,'Boîte à chaussures',12,1),(14,'Bocal en verre',16,1),(16,'Brique en carton',12,1),(23,'Petite boite de conserve',16,1),(24,'Bouteille plastique 500ml',14,1),(25,'Petit Carton avec trous',10,1),(26,'Petits pots en verre',18,4),(27,'Opercules aluminium',0,4);
/*!40000 ALTER TABLE `packagingtypes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-09 16:50:03
