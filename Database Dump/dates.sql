-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2016 at 02:47 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `uol_companion`
--

-- --------------------------------------------------------

--
-- Table structure for table `dates`
--

CREATE TABLE IF NOT EXISTS `dates` (
  `date_id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `description` text NOT NULL,
  `course` text NOT NULL,
  `year` int(11) NOT NULL,
  `type` text NOT NULL,
  `module` text NOT NULL,
  `module_code` text NOT NULL,
  PRIMARY KEY (`date_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `dates`
--

INSERT INTO `dates` (`date_id`, `date`, `description`, `course`, `year`, `type`, `module`, `module_code`) VALUES
(2, '2016-04-14', 'Project Thesis 8k - 12k words', 'computer science', 3, 'Assignment', 'Project', 'CMP3060M'),
(3, '2016-04-14', 'Project Presentation', 'computer science', 3, 'Presentation', 'Project', 'CMP3060M'),
(4, '2016-03-17', 'Reactive Behavior modeled on a turtlebot robot', 'computer science', 3, 'Assignment', 'Autonomous Mobile Robotics', 'CMP3103M'),
(5, '2016-04-07', 'Statistical analysis of weather data performed in parallel', 'computer science', 3, 'Assignment', 'Parallel Computing', 'CMP3110M'),
(6, '2016-04-28', 'Mobile Services', 'computer science', 3, 'Assignment', 'Mobile Computing', 'CMP3109M'),
(7, '2016-05-09', 'Theoretical robotics exam', 'computer science', 3, 'Exam', 'Autonomous Mobile Robotics', 'CMP3103M'),
(8, '2016-05-09', 'Theoretical Parallel Computing exam', 'computer science', 3, 'Exam', 'Parallel Computing', 'CMP3110M');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
