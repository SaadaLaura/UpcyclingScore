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
-- Table structure for table `reuseideas`
--

DROP TABLE IF EXISTS `reuseideas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reuseideas` (
  `ReuseIdeaID` int NOT NULL AUTO_INCREMENT,
  `PackagingTypeID` int DEFAULT NULL,
  `IdeaDescription` text NOT NULL,
  `Instructions` text,
  PRIMARY KEY (`ReuseIdeaID`),
  KEY `PackagingTypeID` (`PackagingTypeID`),
  CONSTRAINT `reuseideas_ibfk_1` FOREIGN KEY (`PackagingTypeID`) REFERENCES `packagingtypes` (`PackagingTypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reuseideas`
--

LOCK TABLES `reuseideas` WRITE;
/*!40000 ALTER TABLE `reuseideas` DISABLE KEYS */;
INSERT INTO `reuseideas` VALUES (6,1,'Fabriquer une mangeoire pour oiseaux','Coupez le fond de la bouteille et percez des trous pour attacher des bâtons en bois comme perchoirs. Remplissez la bouteille de graines pour oiseaux et suspendez-la à un arbre.'),(7,1,'Créer un arrosoir','Percez plusieurs petits trous dans le bouchon de la bouteille. Remplissez la bouteille d\'eau et utilisez-la comme un arrosoir pour vos plantes.'),(8,2,'Fabriquer un jouet de tri des couleurs pour enfants','Rassemblez des bouchons de différentes couleurs. Utilisez un marqueur pour dessiner des cercles de couleurs correspondantes sur une grande feuille de papier. Les enfants peuvent ensuite assortir les bouchons aux cercles correspondants.'),(9,2,'Créer un dessous de verre','Collez plusieurs bouchons ensemble en forme de cercle ou de carré. Utilisez de la colle chaude pour les fixer solidement. Laissez sécher avant d\'utiliser.'),(10,3,'Créer un pot de fleurs','Nettoyez le pot et percez quelques trous au fond pour le drainage. Remplissez-le de terre et plantez des fleurs ou des herbes.'),(11,3,'Organisateur de bureau','Nettoyez le pot et utilisez-le pour ranger des stylos, des crayons et d\'autres fournitures de bureau.'),(12,4,'Porte-bougies','Nettoyez la boîte de conserve et peignez-la ou décorez-la selon votre goût. Placez une bougie à l\'intérieur pour créer un porte-bougies unique.'),(13,4,'Distributeur de ficelle','Percez un trou dans le couvercle de la boîte de conserve. Placez la ficelle à l\'intérieur de la boîte et faites passer l\'extrémité de la ficelle par le trou.'),(14,5,'Lanternes décoratives','Coupez le haut de la cannette et percez des trous en forme de motifs décoratifs autour de la cannette. Placez une bougie à l\'intérieur pour créer une lanterne.'),(15,5,'Distributeur de monnaie','Coupez une fente dans le couvercle de la cannette. Utilisez-la pour stocker et distribuer de la monnaie.'),(16,6,'Organisateur de documents','Coupez le haut de la boîte de céréales en diagonale pour créer un organiseur de documents. Peignez ou décorez la boîte selon votre goût.'),(17,6,'Support pour chargeur de téléphone','Coupez la boîte pour qu\'elle puisse tenir votre téléphone et faites un trou pour passer le câble de charge. Décorez selon votre goût.'),(18,9,'Transformer en papier cadeau.',NULL),(19,9,'Utiliser comme sac de recyclage.',NULL),(20,10,'Transformer en lampe ou chandelier.',NULL),(21,10,'Utiliser comme carafe d\'eau.',NULL),(22,11,'Utiliser pour démarrer des semis.',NULL),(23,11,'Transformer en palette de peinture pour enfants.',NULL),(24,12,'Utiliser comme sac poubelle.',NULL),(25,12,'Transformer en fil pour crochet ou tricot.',NULL),(26,13,'Transformer en boîte de rangement pour outils.',NULL),(27,13,'Utiliser pour organiser des papiers et documents.',NULL),(28,14,'Utiliser comme vase à fleurs.',NULL),(29,14,'Transformer en contenant pour aliments secs.',NULL),(30,16,'Utiliser comme pots de semis pour les plantes.',NULL),(31,16,'Transformer en boîte de rangement pour petits objets.',NULL),(32,16,'Créer des maisons d\'oiseaux.',NULL),(33,16,'Utiliser pour des projets artistiques ou artisanaux.',NULL),(34,16,'Transformer en porte-crayons ou support de bureau.',NULL);
/*!40000 ALTER TABLE `reuseideas` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-03 13:42:46
