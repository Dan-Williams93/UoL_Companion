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
-- Table structure for table `posted_status`
--

CREATE TABLE IF NOT EXISTS `posted_status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_name` text NOT NULL,
  `status` text NOT NULL,
  `post_date` date NOT NULL,
  `num_comments` int(11) NOT NULL DEFAULT '0',
  `course` text NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `posted_status`
--

INSERT INTO `posted_status` (`status_id`, `poster_name`, `status`, `post_date`, `num_comments`, `course`) VALUES
(11, 'Daniel Williams', 'test', '2016-02-24', 1, 'computer science'),
(12, 'Daniel Williams', '', '2016-03-02', 0, 'computer science'),
(13, 'Daniel Williams', '', '2016-03-02', 0, 'computer science');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
