CREATE DATABASE  IF NOT EXISTS `spectacle_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `spectacle_db`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: spectacle_db
-- ------------------------------------------------------
-- Server version	8.0.41

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
-- Table structure for table `billet`
--

DROP TABLE IF EXISTS `billet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `billet` (
  `idBillet` int NOT NULL AUTO_INCREMENT,
  `categorie` varchar(10) DEFAULT NULL,
  `prix` decimal(5,2) NOT NULL,
  `idSpec` int NOT NULL,
  `Vendu` tinyint(1) NOT NULL,
  `idPersonne` int DEFAULT NULL,
  `idUser` int DEFAULT NULL,
  PRIMARY KEY (`idBillet`),
  KEY `fk_billet_spec` (`idSpec`),
  KEY `fk_personne` (`idPersonne`),
  KEY `fk_billet_user` (`idUser`),
  CONSTRAINT `fk_billet_spec` FOREIGN KEY (`idSpec`) REFERENCES `spectacle` (`idSpec`),
  CONSTRAINT `fk_billet_user` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`),
  CONSTRAINT `fk_personne` FOREIGN KEY (`idPersonne`) REFERENCES `personne` (`id`),
  CONSTRAINT `chk_billet_PRIX` CHECK ((`prix` between 10 and 300))
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billet`
--

LOCK TABLES `billet` WRITE;
/*!40000 ALTER TABLE `billet` DISABLE KEYS */;
INSERT INTO `billet` VALUES (1,'Spectacle',30.00,21,1,NULL,NULL),(2,'Spectacle',20.00,14,1,55,NULL),(3,'Spectacle',30.00,21,1,56,NULL),(4,'Spectacle',30.00,23,1,58,NULL),(5,'Spectacle',40.00,33,1,60,NULL),(6,'Spectacle',25.00,35,1,61,NULL),(7,'Spectacle',30.00,34,1,62,NULL),(8,'Spectacle',30.00,34,1,63,NULL),(9,'Spectacle',50.00,36,1,64,NULL),(10,'Spectacle',60.00,22,1,65,NULL),(11,'Spectacle',50.00,17,1,66,NULL),(12,'Spectacle',60.00,38,1,67,NULL),(13,'Spectacle',30.00,23,1,68,NULL),(14,'Spectacle',25.00,15,1,69,NULL),(15,'Spectacle',25.00,27,1,70,NULL),(16,'Spectacle',25.00,35,1,71,NULL),(17,'Spectacle',25.00,17,1,72,NULL),(18,'Spectacle',20.00,32,1,73,NULL),(19,'Spectacle',30.00,38,1,74,NULL),(20,'Spectacle',30.00,37,1,75,NULL),(21,'Spectacle',25.00,27,1,76,NULL),(22,'Spectacle',25.00,27,1,77,NULL),(23,'Spectacle',50.00,30,1,78,NULL),(24,'Spectacle',25.00,27,1,79,NULL),(25,'Spectacle',40.00,33,1,80,NULL),(26,'Spectacle',50.00,30,1,82,NULL),(27,'Spectacle',50.00,29,1,83,NULL),(28,'Spectacle',25.00,29,1,84,NULL),(29,'Spectacle',50.00,29,1,85,NULL),(30,'Spectacle',25.00,15,1,87,NULL),(31,'Spectacle',60.00,34,1,89,NULL),(32,'Spectacle',60.00,23,1,90,NULL),(33,'Spectacle',25.00,29,1,91,NULL),(34,'Spectacle',20.00,14,1,94,NULL),(35,'Spectacle',20.00,18,1,95,NULL),(36,'Spectacle',25.00,17,1,96,NULL),(37,'Spectacle',90.00,21,1,97,NULL),(38,'Spectacle',20.00,20,1,98,NULL),(39,'Spectacle',50.00,28,1,99,NULL),(40,'Spectacle',25.00,28,1,100,NULL),(41,'Spectacle',30.00,25,1,101,NULL),(42,'Spectacle',25.00,27,1,102,NULL),(43,'Spectacle',25.00,30,1,103,NULL),(44,'Spectacle',20.00,33,1,104,NULL),(45,'Spectacle',25.00,36,1,105,NULL),(46,'Spectacle',25.00,15,1,106,NULL),(47,'Spectacle',30.00,34,1,107,NULL),(48,'Spectacle',50.00,31,1,108,NULL),(49,'Spectacle',25.00,17,1,109,NULL),(50,'Spectacle',40.00,12,1,110,NULL),(51,'Spectacle',30.00,23,1,111,NULL),(52,'Spectacle',30.00,23,1,112,NULL),(53,'Spectacle',25.00,24,1,113,NULL),(54,'Spectacle',30.00,25,1,114,NULL),(55,'Spectacle',20.00,16,1,115,NULL),(56,'Spectacle',30.00,21,1,116,NULL),(57,'Spectacle',50.00,30,1,117,NULL),(58,'Spectacle',25.00,30,1,118,NULL),(59,'Spectacle',25.00,15,1,119,NULL),(60,'Spectacle',25.00,13,1,120,NULL),(61,'Spectacle',30.00,23,1,121,NULL),(62,'Spectacle',20.00,14,1,122,NULL),(63,'Spectacle',20.00,19,1,123,NULL),(64,'Spectacle',25.00,15,1,124,NULL),(65,'Spectacle',30.00,25,1,125,NULL),(66,'Spectacle',20.00,20,1,126,NULL),(67,'Spectacle',30.00,22,1,127,NULL),(70,'Spectacle',20.00,32,1,128,NULL),(73,'Spectacle',40.00,12,1,133,NULL),(75,'Spectacle',20.00,12,1,133,NULL),(76,'Spectacle',20.00,12,1,148,NULL),(77,'Spectacle',50.00,36,1,133,2),(78,'Spectacle',25.00,28,1,133,2),(79,'Spectacle',40.00,12,1,133,2),(80,'Spectacle',25.00,17,1,133,1),(81,'Spectacle',60.00,22,1,133,NULL),(82,'Spectacle',30.00,22,1,149,NULL);
/*!40000 ALTER TABLE `billet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lieu`
--

DROP TABLE IF EXISTS `lieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lieu` (
  `idLieu` int NOT NULL AUTO_INCREMENT,
  `NomLieu` varchar(50) DEFAULT NULL,
  `Adresse` varchar(100) NOT NULL,
  `capacite` int NOT NULL,
  PRIMARY KEY (`idLieu`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lieu`
--

LOCK TABLES `lieu` WRITE;
/*!40000 ALTER TABLE `lieu` DISABLE KEYS */;
INSERT INTO `lieu` VALUES (17,'Théâtre Municipal de Tunis','Avenue Habib Bourguiba, Tunis',80),(18,'Théâtre de l\'Opéra','Cité de la Culture, Tunis',75),(19,'Théâtre Régional de Sfax','Rue Ali Bach Hamba, Sfax',60),(20,'Théâtre Régional de Kairouan','Rue de la République, Kairouan',50),(21,'Théâtre Régional de Gafsa','Rue Abderrahmane Mami, Gafsa',45),(22,'Théâtre Régional du Kef','Place des Arts, Le Kef',40),(23,'Théâtre Municipal de Hammamet','Centre Culturel International, Hammamet',70),(24,'Théâtre de plein air de Carthage','Site archéologique de Carthage',65),(25,'Théâtre El Menzah','Théâtre El Menzah',40),(26,'Théâtre de Carthage','Théâtre de Carthage',60),(27,'Théâtre Le Rio','Théâtre Le Rio',50),(28,'Maison de la Culture Ibn Rachiq','Maison de la Culture Ibn Rachiq',45),(29,'Théâtre Halfaouine','Théâtre Halfaouine',40),(30,'Cité de la Culture - Tunis','Cité de la Culture - Tunis',55);
/*!40000 ALTER TABLE `lieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personne`
--

DROP TABLE IF EXISTS `personne`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personne` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `nombreDePlace` int DEFAULT NULL,
  `idRubrique` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_personne_spec` (`idRubrique`),
  CONSTRAINT `fk_personne_spec` FOREIGN KEY (`idRubrique`) REFERENCES `spectacle` (`idSpec`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personne`
--

LOCK TABLES `personne` WRITE;
/*!40000 ALTER TABLE `personne` DISABLE KEYS */;
INSERT INTO `personne` VALUES (12,'ziri','eya','eyaziri2@gmail.com',17,18),(13,'zakraoui','ameni','ameni@gmail.com',18,21),(26,'aa','aa','a@gmail.com',1,12),(35,'messaadi','ouday','ouday@test.com',2,20),(36,'ziri','ezer','ezer@test.com',2,33),(37,'goutali','rim','rim@test.com',2,26),(38,'goutali','imed','imed@test.com',1,32),(39,'ziri','dhia','dh@test.com',2,34),(42,'a','aaaa','aaa@gmail.com',1,16),(43,'bb','bb','b@test.com',2,35),(44,'mm','m','m@test.com',1,34),(45,'zz','zz','z@gmail.ocm',1,31),(46,'hh','hh','h@gmail.com',1,17),(47,'yy','yy','y@test.com',1,19),(48,'tey','pizza','pizza@gmail.com',2,30),(50,'messaadi','ouday','oudou@gmail.com',1,14),(51,'mm','mm','oo@mail.com',1,22),(52,'ll','ll','ll@tedt.com',1,21),(53,'ziri','wiem','wa@test.com',2,23),(55,'zo','zo','zo@mail.com',1,14),(56,'mariem','mariem','m@g.com',1,21),(57,'rim','goutali','gout@mail.com',1,34),(58,'ziri','dhia','dhia@test.com',1,23),(60,'ezer','ezer','ezer@test.c',2,33),(61,'ouday','messaadi','mes@test.co',1,35),(62,'h','h','h@t.com',1,34),(63,'l','l','l@t.com',1,34),(64,'sami','sami','sam@mail.com',2,36),(65,'rim','rim','eim@test.c',2,22),(66,'ll','ll','l@c.t',2,17),(67,'messaadi','lin','lin@test.com',2,38),(68,'k','k','k@k.com',1,23),(69,'a','a','e@e.e',1,15),(70,'mm','mm','mm@y.com',1,27),(71,'ziri','ziri','ziri@ziri.com',1,35),(72,'ey','ey','ey@ey.com',1,17),(73,'n','n','n@n.n',1,32),(74,'j','j','j@j.com',1,38),(75,'hh','hh','hh@h.com',1,37),(76,'y','y','y@y.c',1,27),(77,'aa','aa','a@a.com',1,27),(78,'uu','uu','eya.ziri@enicar.ucar.tn',2,30),(79,'y','y','eyaziri@ieee.org',1,27),(80,'ll','ll','ouday.messaadi@etudiant-enit.utm.tn',2,33),(82,'aa','aa','aa@eya.com',2,30),(83,'eya','wiem','wi@g.com',2,29),(84,'ziri','eya','e@y.co',1,29),(85,'rim','gou','g@m.b',2,29),(86,'hh','kk','ll@l.com',1,15),(87,'hh','kk','eya@gmail.com',1,15),(89,'ziri','eya','eyya@gmail.com',2,34),(90,'ziri','eya','eyaziri2@gmail.com',2,23),(91,'ziri','eya','eyaziri2@gmail.com',1,29),(92,'ziri','eya','eyaziri2@gmail.com',3,27),(93,'ziri','eya','eyaziri2@gmail.com',3,27),(94,'ziri','eya','eyaziri2@gmail.com',1,14),(95,'goutali','rim','eyaziri2@gmail.com',1,18),(96,'ziri','eya','eyaziri2@gmail.com',1,17),(97,'ziri','ezer','eyaziri2@gmail.com',3,21),(98,'ziri','eya','eyaziri2@gmail.com',1,20),(99,'messaadi','lin','eyaziri2@gmail.com',2,28),(100,'ziti','wuw','eyaziri2@gmail.com',1,28),(101,'aa','aa','eyaziri2@gmail.com',1,25),(102,'aa','aa','eyaziri2@gmail.com',1,27),(103,'hh','hh','eyaziri2@gmail.com',1,30),(104,'aa','aa','eyaziri2@gmail.com',1,33),(105,'ziri','eya','eyaziri2@gmail.com',1,36),(106,'ziri','rim','eyaziri2@gmail.com',1,15),(107,'hh','hh','eyaziri2@gmail.com',1,34),(108,'imed','imed','eyaziri2@gmail.com',2,31),(109,'wya','jj','eyaziri2@gmail.com',1,17),(110,'ouday','messaadi','eya.ziri@enicar.ucar.tn',2,12),(111,'ziri','wiem','eyaziri2@gmail.com',1,23),(112,'rim','rim','eya.ziri@enicar.ucar.tn',1,23),(113,'hh','hh','ouday908070@gmail.com',1,24),(114,'hh','hh','ouday908070@gmail.com',1,25),(115,'hh','jj','ouday908070@gmail.com',1,16),(116,'hh','uuu','ouday908070@gmail.com',1,21),(117,'zakraoui','ameni','amenizakraoui@gmail.com',2,30),(118,'zak','amm','amenizakraoui@gmail.com',1,30),(119,'ziri','eya','eyaziri2@gmail.com',1,15),(120,'rim','goutali','amenizakraoui@gmail.com',1,13),(121,'ziri','eya','ouday.messaadi@etudiant-enit.utm.tn',1,23),(122,'rim','rim','eyaziri2@gmail.com',1,14),(123,'aa','aa','ouday908070@gmail.com',1,19),(124,'zak','ameni','amenizakraoui@gmail.com',1,15),(125,'ziri','eya','eyaziri2@gmail.com',1,25),(126,'ziri','eya','eyaziri2@gmail.com',1,20),(127,'ziri','eya','eyaziri2@gmail.com',1,22),(128,'ll','ll','eyaziri2@gmail.com',1,32),(129,'aa','aa','ziri@ziri.com',1,34),(130,'aa','aa','ziri@ziri.com',1,34),(131,'aa','aa','ziri@ziri.com',1,34),(132,'aa','aa','ziri@ziri.com',1,34),(133,'aa','aa','ouday908070@gmail.com',1,19),(134,'aa','aa','ouday908070@gmail.com',1,19),(135,'ziri','eya','eyaziri2@gmail.com',1,31),(136,'ziri','eya','eyaziri2@gmail.com',1,17),(137,'ziri','eya','eyaziri2@gmail.com',1,17),(138,'ziri','eya','eyaziri2@gmail.com',1,17),(139,'ziri','eya','eyaziri2@gmail.com',1,17),(140,'ziri','aa','eyaziri2@gmail.com',1,15),(144,'ziri','ameno','ameni@gmail.com',1,18),(145,'ziri','eya','eyazi',1,30),(146,'aa','aa','ameni@gmail.com',1,14),(147,'ameni','zak','amenizakraoui@gmail.com',2,12),(148,'eyq','yh','eyaziri2@gmail.com',1,12),(149,'gg','hh','eyaziri2@gmail.com',1,22);
/*!40000 ALTER TABLE `personne` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rubrique`
--

DROP TABLE IF EXISTS `rubrique`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rubrique` (
  `idRubrique` int NOT NULL AUTO_INCREMENT,
  `idSpec` int DEFAULT NULL,
  `dateRubrique` date DEFAULT NULL,
  `heureRubrique` time DEFAULT NULL,
  `idLieu` int DEFAULT NULL,
  `places_reservees` varchar(255) DEFAULT NULL,
  `nombreDeSpectateur` int DEFAULT NULL,
  PRIMARY KEY (`idRubrique`),
  KEY `idSpec` (`idSpec`),
  KEY `fk_idLieu` (`idLieu`),
  CONSTRAINT `fk_idLieu` FOREIGN KEY (`idLieu`) REFERENCES `lieu` (`idLieu`),
  CONSTRAINT `rubrique_ibfk_1` FOREIGN KEY (`idSpec`) REFERENCES `spectacle` (`idSpec`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rubrique`
--

LOCK TABLES `rubrique` WRITE;
/*!40000 ALTER TABLE `rubrique` DISABLE KEYS */;
INSERT INTO `rubrique` VALUES (1,12,'2025-05-01','20:00:00',17,'4,7,8,10,59',80),(2,12,'2025-05-15','20:00:00',25,'32,0,1,2,3,4,7,23,29,13',40),(3,12,'2025-06-30','19:30:00',30,'48,49,2,3,22,12',55),(4,13,'2025-05-02','21:00:00',26,'25,12,8,6',60),(5,13,'2025-05-16','21:00:00',27,'25,2,1,3',50),(6,13,'2025-06-15','20:30:00',28,'4,5,22,7,26',45),(7,14,'2025-05-03','20:30:00',17,'19,6,72,9,12,13,79',80),(8,14,'2025-06-20','21:00:00',29,'32,1,4,22,12',40),(9,14,'2025-08-10','20:00:00',26,'30,1,2,3,4',60),(10,15,'2025-05-04','20:30:00',25,'1,2,3,4,36,23,26',40),(11,15,'2025-05-25','19:45:00',30,'2,3,52,7,24,46,47',55),(12,15,'2025-07-05','20:00:00',26,'0,52,5,22,7,9,41,58,26,28,47',60),(13,16,'2025-05-06','21:00:00',17,'64,7,8,10,11',80),(14,16,'2025-06-22','20:30:00',25,'30,1,8,10,22',40),(15,16,'2025-08-25','21:00:00',27,'7,9,5,4,10',50),(16,17,'2025-05-07','19:00:00',28,'5,40,10,11,12',45),(17,17,'2025-06-12','19:30:00',26,'1,2,3,4,53,7,58',60),(18,17,'2025-08-15','20:00:00',17,'26,4,8,6,7',80),(19,18,'2025-05-08','20:00:00',25,'3,1,4,2,5,7',40),(20,18,'2025-06-18','20:30:00',30,'48,1,2,51,4,7,28',55),(21,18,'2025-08-22','21:00:00',26,'10,1,5,7',60),(22,19,'2025-05-10','21:30:00',17,'26,5,2,3,1',80),(23,19,'2025-06-23','21:00:00',29,'1,2,3,38,39,9',40),(24,19,'2025-08-28','21:30:00',25,'27,5,7,6',40),(25,20,'2025-05-11','22:00:00',26,'18,51,54,11,12,13',60),(26,20,'2025-06-25','22:00:00',30,'7,6,5,4',55),(27,20,'2025-08-30','22:00:00',17,'3,4,52,5,11',80),(28,21,'2025-05-12','20:00:00',25,'5,1,6,8,7',40),(29,21,'2025-06-26','20:30:00',26,'20,54,39,8,57,10,58,15',60),(30,21,'2025-08-02','20:00:00',17,'1,2,3,5,55,56,27,76,77',80),(31,22,'2025-05-13','20:30:00',17,'3,5,55,72,9,12',80),(32,22,'2025-06-28','20:00:00',25,'36,22,9,11,14',40),(33,22,'2025-08-03','20:30:00',30,'51,4,5,54,7,8',55),(34,23,'2025-05-14','21:00:00',29,'10,5,1,4',40),(35,23,'2025-06-29','21:00:00',17,'27,5,7,8,9',80),(36,23,'2025-08-04','21:00:00',26,'14,19,20,1',60),(37,24,'2025-05-15','21:30:00',25,'21,7,8,9',40),(38,24,'2025-06-30','21:00:00',26,'1,2,10,5,4',60),(39,24,'2025-08-05','21:30:00',17,'3,4,5,6,7,8',80),(40,25,'2025-05-16','20:00:00',26,'1,2,3,55,40,41,12',60),(41,25,'2025-07-01','20:00:00',25,'35,21,23,8,9,10',40),(42,25,'2025-08-06','20:00:00',28,'36,6,7,40,12,14',45),(43,26,'2025-05-17','20:30:00',17,'29,10,11,12,3',80),(44,26,'2025-07-02','20:30:00',30,'8,9,3,10,11',55),(45,26,'2025-08-07','21:30:00',26,'9,6,4,5,3',60),(46,27,'2025-05-18','19:00:00',25,'21,4,7,8,1',40),(47,27,'2025-07-03','21:00:00',17,'16,4,6,9,60,28,61',80),(48,27,'2025-08-08','20:00:00',26,'18,5,10,14',60),(49,28,'2025-05-19','19:30:00',25,'39,10,11,12,13',40),(50,28,'2025-07-04','20:30:00',28,'5,6,24,10,12,44',45),(51,28,'2025-08-09','20:00:00',17,'3,5,8,9,13',80),(52,29,'2025-05-20','18:00:00',26,'3,1,2,8,10',60),(53,29,'2025-07-05','21:30:00',30,'48,2,7,9,10',55),(54,29,'2025-08-10','21:00:00',25,'4,6,5,8',40),(55,30,'2025-05-21','15:00:00',17,'16,7,72,10,11,76,77',80),(56,30,'2025-07-06','20:00:00',26,'11,2,1,4,5',60),(57,30,'2025-08-11','20:30:00',28,'22,7,8,9',45),(58,31,'2025-05-22','21:30:00',30,'2,3,4,52,11,15',55),(59,31,'2025-07-07','21:00:00',17,'5,7,11,12,13,79',80),(60,31,'2025-08-12','18:00:00',26,'11,2,3,15',60),(61,32,'2025-05-23','20:30:00',17,'20,10,15,7,6',80),(62,32,'2025-07-08','10:00:00',25,'5,10,2,11,9',40),(63,32,'2025-08-13','20:30:00',30,'1,2,20,53,54,24,42,47',55),(64,33,'2025-05-24','21:00:00',25,'15,4,14,20',40),(65,33,'2025-07-09','21:30:00',26,'3,10,12,23',60),(66,33,'2025-08-14','21:00:00',17,'30,5,8,6',80),(67,34,'2025-05-25','20:30:00',28,'21,1,2,3',45),(68,34,'2025-07-10','20:00:00',25,'15,1,2,3',40),(69,34,'2025-08-15','19:30:00',26,'1,2,3,53,11',60),(70,35,'2025-05-26','20:00:00',30,'11,7,15,14',55),(71,35,'2025-07-11','18:00:00',17,'1,2,3,22,57',80),(72,35,'2025-08-16','21:00:00',25,'15,7,8,10',40),(73,36,'2025-05-27','20:30:00',26,'7,11,20,10',60),(74,36,'2025-07-12','21:30:00',30,'1,2,50,5,22',55),(75,36,'2025-08-17','21:00:00',17,'26,1,4,5',80),(76,37,'2025-05-28','21:00:00',17,'3,1,6,9,10',80),(77,37,'2025-07-13','20:00:00',25,'27,20,15,2',40),(78,37,'2025-08-18','21:00:00',26,'5,4,5,7',60),(79,38,'2025-05-29','20:30:00',28,'6,3,6,7',45),(80,38,'2025-07-14','20:30:00',17,'14,5,6,1,2',80),(81,38,'2025-08-19','21:00:00',30,'1,2,52,22,7,8',55);
/*!40000 ALTER TABLE `rubrique` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `before_insert_rubrique` BEFORE INSERT ON `rubrique` FOR EACH ROW BEGIN
  DECLARE cap INT;
  SELECT capacite INTO cap FROM lieu WHERE idLieu = NEW.idLieu;
  SET NEW.nombreDeSpectateur = cap;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `spectacle`
--

DROP TABLE IF EXISTS `spectacle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spectacle` (
  `idSpec` int NOT NULL AUTO_INCREMENT,
  `Titre` varchar(255) NOT NULL,
  `dureeS` varchar(50) DEFAULT NULL,
  `genre` varchar(50) DEFAULT NULL,
  `urlImage` varchar(255) DEFAULT NULL,
  `description` text,
  `prix` int DEFAULT NULL,
  PRIMARY KEY (`idSpec`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spectacle`
--

LOCK TABLES `spectacle` WRITE;
/*!40000 ALTER TABLE `spectacle` DISABLE KEYS */;
INSERT INTO `spectacle` VALUES (12,'Louzina','1h00min','Théâtre','img_5','Mise en scène : Haikel Rahali\n\nL\'espace de ce monde est clos par ses portes en fer... tout est rassemblé... ils sont aussi de ce monde, éparpillés ici et là... leurs mains bougent, leurs bouches sont contrariées et leur cœur est fermé... jusqu\'à ce que le vol ait lieu et que chacun porte sa part de responsabilité, à travers des années passées... Un groupe de travailleurs avec un superviseur, et le superviseur a son propre superviseur, et ça continue jusqu\'à ce que nous arrivions à l\'autorité suprême.\n\nInterprétation : Abdel Karim Benani, Zineb Maliki, Rahma El Jebri, Hicham Bouraoui, Amal Ayari, Doha Harzallah, Bilal Ben Ramadan, Souhir Khemir Mazyou, Lamine Hamzaoui et Mahmoud Saidi - Assistant réalisateur : Hadi Helal',20),(13,'Jranti laaziza','2 heures','Théâtre','img_6','\"Jranti laaziza\" du réalisateur Fadhel Jaziri est une nouvelle production cinématographique. Ce spectacle mêle jeu d’acteur et musique pour raconter l’histoire d’un violoniste qui rejoint l’orchestre de la radio, revisitant ainsi les moments marquants de sa vie artistique et politique dans une ambiance empreinte de nostalgie et de contrastes.',25),(14,'Kima Lyoum','2 heures','Théâtre','img_7','\"Kima Lyoum\", fruit d’une coproduction entre le Théâtre National Tunisien et la compagnie \"L’Art est Résistance\", est une œuvre signée par l’artiste Leila Toubel, qui en assure l’écriture, la mise en scène et la scénographie.\n\nL’histoire tourne autour de la petite Dunia, qui célèbre son cinquième anniversaire, lorsqu’elle entend une voix envoûtante l’invitant à quitter le monde des humains pour retourner dans les profondeurs de la terre. Alors qu’elle s’apprête à partir, elle disparaît dans des circonstances mystérieuses, laissant son entourage plongé dans un labyrinthe complexe à la recherche de Dunia et d’une issue.',20),(15,'Le Jardin des Hespérides','2 heures','Théâtre','img_8','\"Le Jardin des Hespérides\" de la réalisatrice espagnole Alicia Soto est une coproduction hispano-marocaine mêlant performance corporelle et narration théâtrale. L’œuvre s’inspire du mythe grec dans une vision contemporaine qui reflète les conflits intérieurs de l’être humain.',25),(16,'Sucre - an ice cream for a nice crime','2 heures','Théâtre','img_9','\"Sucre – Glaces pour un crime doux\" est une œuvre qui mêle théâtre et danse, conçue par le chorégraphe Abdoulaye Trésor Konaté de Côte d’Ivoire. À travers un style visuel singulier, la pièce aborde de manière originale la thématique du crime et de la justice dans un cadre théâtral novateur.',20),(17,'Sous pression','2 heures','Théâtre','img_10','\"Sous pression\" du réalisateur Rayan El Kairouani et produit par le Centre des Arts Dramatiques et Scéniques de Kasserine. La pièce raconte l’histoire d’un metteur en scène qui convainc un groupe d’acteurs amateurs de créer une œuvre politique, mais ils se retrouvent confrontés de manière inattendue aux autorités, transformant ainsi le spectacle en un combat existentiel sur la liberté d’expression et la censure.',25),(18,'Confession','2 heures','Théâtre','img_11','\"Confession\" du réalisateur Mohamed Ali Ben Saïd raconte l’histoire d’un tueur en série qui a violé et tué quinze filles sans le moindre remords. Pendant ce temps, l’une des mères des victimes fait face à ce traumatisme en prenant une décision inattendue : elle commence à lui rendre visite en prison. La pièce explore des dimensions psychologiques profondes et soulève des questions philosophiques sur les concepts de pardon et de vengeance.',20),(19,'Prometheus: The Blue Kangaroo','2 heures','Théâtre','img_12','Inspirée de la tragédie d’Eschyle, la pièce adopte une vision moderne qui reflète les crises environnementales et humaines en Méditerranée. Dans cette version, Prométhée n\'est pas puni par le feu, mais jeté dans la mer pour se retrouver emprisonné dans un espace stérile, où l’humanité a pris la place des dieux, dans un récit théâtral qui mêle philosophie et remise en question du destin de l\'humanité.',20),(20,'La Suivette','2 heures','Théâtre','img_13','\"La Suivette\" du grand réalisateur Tawfik Jebali et produit par le Théâtre Al Teatro, est un spectacle satirique et critique qui reflète l’interconnexion entre la réalité et la fiction avec un style unique. La pièce soulève à nouveau les grandes questions sur la société, le pouvoir et l\'art.',20),(21,'La route des belles histoires','2 heures','Concert','img_14','Un festival célébrant le jazz et les musiques du monde, avec des concerts en plein air et des événements culturels',30),(22,'Un concert inédit de Jazz tuniso-français','2 heures','Concert','img_15','Un concert inédit de jazz et musiques improvisées, fruit d\'une résidence artistique entre musiciens tunisiens et français.',30),(23,'Mazzika Orchestra & Guests','2 heures','Concert','img_16','Un concert mettant en avant des œuvres classiques arabes, avec des invités spéciaux.',30),(24,'Au violon – Spectacle théâtral et musical','2 heures','Concert','img_17','Un spectacle alliant théâtre et musique, signé par Fadhel Jaziri.',25),(25,'Trésors Lyriques : de Rameau à Fauré','2 heures','Concert','img_18','? ?? ????????? : ✨ Un voyage à travers les chefs-d’œuvre de la musique française pour chœur, ténor et orchestre, avec des œuvres de ??????, ??????, ?????, ?????????, ???????? ?? ?????́.',30),(26,'CONCERTS HISTORIQUES STRAUSS','2 heures','Concert','img_19','C’est une véritable immersion dans la magnificence de la musique viennoise que ces concerts offriront aux spectateurs tunisiens.',20),(27,'Festival de la Médina de Tunis','2 heures','Concert','img_20','Un festival transformant la médina en un espace de célébration culturelle, avec des concerts, des spectacles de danse et de théâtre, ainsi que des expositions artistiques.',25),(28,'Carmen – Ballet de l\'Opéra de Tunis','2 heures','Danse','img_21','Une adaptation dansée de l\'opéra de Bizet, mettant en lumière la passion et le drame de l\'histoire de Carmen.',25),(29,'Ainsi dansait le berger','2 heures','Danse','img_22','Un spectacle alliant danse contemporaine et éléments traditionnels tunisiens, explorant les racines culturelles à travers le mouvement.',25),(30,'Spectacle de danse classique','2 heures','Danse','img_23','Un programme de danse brillante et varié préparé par des élèves des écoles chorégraphiques : l\'école de danse classique.',25),(31,'Dabk Tabl','2 heures','Danse','img_24','Un spectacle fusionnant danse et musique populaire tunisienne, chorégraphié par Imed Amara et accompagné de compositions d\'Ibrahim Bahloul.',25),(32,'Illuminations d’El Halfaouine','1 heure','Danse','img_25','Des concerts et des spectacles de danse inspirés des traditions soufies et mystiques, organisés par le Théâtre National Tunisien en partenariat avec la Fondation Abdelwahab Ben Ayed et Microcred.',20),(33,'Look Look','1 heure','Standup','img_26','Un spectacle humoristique mettant en scène des comédiens tunisiens.',20),(34,'Sème le bonheur','1 heure','Standup','img_28','Lotfi Abdelli, l\'un des humoristes les plus célèbres en Tunisie, propose un spectacle où il aborde des sujets de la vie quotidienne, des problèmes politiques et sociaux en Tunisie, avec son style unique de satire et d\'autodérision. Ce spectacle a marqué une étape dans sa carrière, alliant humour et critique sociale dans une ambiance détendue et énergique.',30),(35,'Karr w Farr','2 heures','Standup','img_29','Dans son spectacle \"Karr w Farr\", Mehdi Mahjoub se moque des situations absurdes de la société tunisienne, notamment les relations familiales, la politique et l\'actualité. Ce jeune humoriste utilise un langage direct et sans détour pour aborder des thèmes qui touchent son public, créant un lien fort avec les spectateurs grâce à ses anecdotes personnelles et son humour incisif.',25),(36,'Tunisia Comedy Festival – 2ᵉ édition','2 heures','Standup','img_30','La deuxième édition du Tunisia Comedy Festival a rassemblé plusieurs humoristes tunisiens et internationaux. Le public a assisté à des sketchs variés, allant des imitations de personnalités publiques aux observations hilarantes sur la vie quotidienne tunisienne. Ce festival a mis en lumière l\'évolution de l\'humour tunisien, en offrant un mélange de stand-up, de sketchs et d\'improvisations.',25),(37,'Stand-Up Comedy Nights','3 heures','Standup','img_31','Ce spectacle a réuni deux figures majeures de la scène comique internationale. Bassem Youssef, le \"Jon Stewart arabe\", a fait rire le public avec des blagues sur la politique au Moyen-Orient et ses propres expériences. Mo Amer, quant à lui, a abordé les différences culturelles entre les États-Unis et le monde arabe, en mêlant humour de situation et critiques sociales. Une soirée où les rires étaient omniprésents, grâce à l\'humour décapant de ces deux comédiens.',30),(38,'Punchline Comedy – The Lights Coffee House','2 heures','Standup','img_32','Ce spectacle réunit une série de jeunes humoristes tunisiens qui présentent des performances de stand-up avec des blagues sur des sujets aussi variés que les relations amoureuses, les problèmes sociaux, et les absurdités de la vie en Tunisie. Les humoristes abordent ces sujets avec une grande spontanéité et une touche d\'autodérision, créant ainsi un spectacle dynamique et interactif.',30),(39,'Lilet Ennos','2 heures','Standup','img_33','Lilet Ennos est un spectacle de stand-up qui mêle humour et poésie. L\'humoriste principal présente une série de monologues qui se concentrent sur les situations absurdes du quotidien tunisien, avec des observations sur la politique, les coutumes et les contradictions de la société. Le spectacle, tout en étant comique, pousse également à la réflexion sur la condition humaine et la société tunisienne.',30);
/*!40000 ALTER TABLE `spectacle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `motdepasse` varchar(255) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ziri','eya','eyaziri2@gmail.com','123456'),(2,'messaadi','ouday','ouday908070@gmail.com','ouday'),(3,'goutali','rim','rim@gmail.com','rim'),(7,'hh','hh','ameni@gmail.com','ameni'),(8,'ameni','zakraoui','amenizakraoui@gmail.com','1234'),(9,'rim','goutali','eya.ziri@enicar.ucar.tn','rim'),(10,'rim','goutali','goutali@gmail.com','rim'),(11,'amira','zak','amira@gmail.com','1111');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'spectacle_db'
--

--
-- Dumping routines for database 'spectacle_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-01 16:12:59
