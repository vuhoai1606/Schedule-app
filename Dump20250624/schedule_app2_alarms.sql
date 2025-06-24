-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: schedule_app2
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
-- Table structure for table `alarms`
--

DROP TABLE IF EXISTS `alarms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarms` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `schedule_id` char(36) NOT NULL,
  `alarm_time` timestamp NULL DEFAULT NULL,
  `sound_url` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `schedule_id` (`schedule_id`),
  CONSTRAINT `alarms_ibfk_1` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarms`
--

LOCK TABLES `alarms` WRITE;
/*!40000 ALTER TABLE `alarms` DISABLE KEYS */;
INSERT INTO `alarms` VALUES ('002268a6-5040-11f0-bb52-088fc3561349','81c0e93e-4d20-4cd9-bbcf-7e0cf632b586','2025-06-23 14:42:00','src/main/resources/samsung_alarm_1.wav',1,'2025-06-23 14:40:25'),('583d3360-504f-11f0-bb52-088fc3561349','ea62386c-adcd-41aa-bab5-b9fafde2bae2',NULL,NULL,0,'2025-06-23 16:30:16'),('83d67820-504f-11f0-bb52-088fc3561349','0714fd30-8f42-4d27-8eb0-cd14aebe91af',NULL,NULL,0,'2025-06-23 16:31:29'),('af25200b-504f-11f0-bb52-088fc3561349','507da49b-169a-47c4-a3cb-837d6008d516','2025-06-29 11:42:00','src/main/resources/samsung_alarm_1.wav',1,'2025-06-23 16:32:41');
/*!40000 ALTER TABLE `alarms` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-24 11:31:05
