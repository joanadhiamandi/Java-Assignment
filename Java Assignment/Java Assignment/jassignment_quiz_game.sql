-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 21, 2024 at 06:26 PM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jassignment_quiz_game`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `api_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`category_id`, `category`, `api_url`) VALUES
(1, 'General knowledge', '&category=9'),
(2, 'Entertainment: Books', '&category=10'),
(3, 'Entertainment: Film', '&category=11'),
(4, 'Entertainment: Music', '&category=12'),
(5, 'Entertainment: Musicals & Theatres', '&category=13'),
(6, 'Entertainment: Television', '&category=14'),
(7, 'Entertainment: Video Games', '&category=15'),
(8, 'Entertainment: Board Games', '&category=16'),
(9, 'Entertainment: Comics', '&category=29'),
(10, 'Entertainment: Japanese Anime & Manga', '&category=31'),
(11, 'Entertainment: Cartoon & Animations', '&category=32'),
(12, 'Science & Nature', '&category=17'),
(13, 'Science: Computers', '&category=18'),
(14, 'Science: Mathematics', '&category=19'),
(15, 'Science: Gadgets', '&category=30'),
(16, 'Mythology', '&category=20'),
(17, 'Sports', '&category=21'),
(18, 'Geography', '&category=22'),
(19, 'History', '&category=23'),
(20, 'Politics', '&category=24'),
(21, 'Art', '&category=25'),
(22, 'Celebrities', '&category=26'),
(23, 'Animals', '&category=27'),
(24, 'Vehicles', '&category=28');

-- --------------------------------------------------------

--
-- Table structure for table `gamehistory`
--

DROP TABLE IF EXISTS `gamehistory`;
CREATE TABLE IF NOT EXISTS `gamehistory` (
  `gameID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `date` datetime NOT NULL,
  `playerScore` int NOT NULL,
  `playerID` int NOT NULL,
  PRIMARY KEY (`gameID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `gamehistory`
--

INSERT INTO `gamehistory` (`gameID`, `username`, `date`, `playerScore`, `playerID`) VALUES
(1, 'jojo', '2024-05-18 20:06:04', 0, 1),
(2, 'ioana', '2024-05-19 23:45:23', 1, 2),
(3, 'samantha', '2024-05-21 06:00:11', 0, 3),
(4, 'jojo', '2024-05-21 06:42:16', 1, 1),
(5, 'jojo', '2024-05-21 08:24:54', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
CREATE TABLE IF NOT EXISTS `players` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(200) NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `players`
--

INSERT INTO `players` (`id`, `username`, `password`, `score`) VALUES
(1, 'jojo', '$2a$12$lzXaKoaDC0TTIgNBC/NLf.OAY8Ol2bx86zHgUd3fmXIXm.lMAS/Hu', 2),
(2, 'ioana', '$2a$12$qwszeX7YkNi9/U4DPp17MOLQ8C5EedapatWjZ9fIIF9FGhDwxjeoG', 1),
(3, 'samantha', '$2a$12$HjrWz5Jlr6JIhFUkj4fVo.EIepTiBKI/eUWkp029OfjZowFJ7HiVu', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
