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
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `user_id` char(36) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `is_all_day` tinyint(1) DEFAULT '0',
  `repeat_pattern` enum('none','daily','weekly','monthly','yearly') DEFAULT 'none',
  `color` varchar(20) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
INSERT INTO `schedules` VALUES ('0714fd30-8f42-4d27-8eb0-cd14aebe91af','6b52d582-dc8d-47c1-96dc-b65042589270','thv','vux','2025-06-25 15:42:00','2025-06-25 16:43:00',0,'weekly','#5ecc89','2025-06-23 16:31:29','2025-06-23 16:31:29'),('507da49b-169a-47c4-a3cb-837d6008d516','6b52d582-dc8d-47c1-96dc-b65042589270','W','w','2025-06-29 11:42:00','2025-06-30 12:43:00',0,'daily','#985df6','2025-06-23 16:32:41','2025-06-23 16:32:41'),('81c0e93e-4d20-4cd9-bbcf-7e0cf632b586','6b52d582-dc8d-47c1-96dc-b65042589270','ABC','abc','2025-06-23 14:42:00','2025-06-23 14:43:00',0,'none','#f4be40','2025-06-23 14:40:25','2025-06-23 14:40:25'),('ea62386c-adcd-41aa-bab5-b9fafde2bae2','6b52d582-dc8d-47c1-96dc-b65042589270','XYZ','xyz','2025-06-23 14:42:00','2025-06-25 14:43:00',0,'none','#fd7941','2025-06-23 16:30:16','2025-06-23 16:30:16'),('fccff650-5014-11f0-bb52-088fc3561349','6b52d582-dc8d-47c1-96dc-b65042589270','ABC','abc','2025-06-23 09:35:00','2025-06-23 09:40:00',0,'none','#f4be40','2025-06-23 09:32:31','2025-06-23 09:32:31');
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
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
