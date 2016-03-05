-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2016 at 02:48 PM
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
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `staff_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `description` text NOT NULL,
  `department` text NOT NULL,
  `phone_number` text NOT NULL,
  `email` text NOT NULL,
  PRIMARY KEY (`staff_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=102 ;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`staff_id`, `name`, `description`, `department`, `phone_number`, `email`) VALUES
(2, 'David Cobham', 'Head of School of Computer Science', 'Computer Science', '01522886120 ', 'dcobham@lincoln.ac.uk'),
(3, 'Tom Duckett', 'Professor of Computer Science', 'Computer Science', '01522837113', 'tduckett@lincoln.ac.uk'),
(4, 'Shigang Yue', 'Professor of Computer Science', 'Computer Science', '01522837397 ', 'syue@lincoln.ac.uk'),
(5, 'Matthew Ashton', 'Computing Technician', 'Computer Science', '01522886232 ', 'mashton@lincoln.ac.uk'),
(6, 'Massoud Zolgharni', 'Senior Lecturer in Computer Science (Medical Imaging)', 'Computer Science', 'Not Specified', 'mzolgharni@lincoln.ac.uk '),
(7, 'Heriberto Cuayahuitl', 'Senior Lecturer in Computer Science', 'Computer Science', 'Not Specified', 'hcuayahuitl@lincoln.ac.uk'),
(8, 'Mark Doughty', 'Principal Lecturer', 'Computer Science', '01522886832', 'mdoughty@lincoln.ac.uk '),
(9, 'Kevin Jacques', 'Principal Teaching Fellow/Director of Academic Affairs', 'Computer Science', '01522837372', 'kjacques@lincoln.ac.uk'),
(10, 'Duncan Rowland', 'Reader', 'Computer Science', '01522886254 ', 'drowland@lincoln.ac.uk'),
(11, 'Amr Ahmed', 'Senior Lecturer', 'Computer Science', '01522837376 ', 'aahmed@lincoln.ac.uk'),
(12, 'Bashir Al-Diri', 'Senior Lecturer', 'Computer Science', '01522837111 ', 'baldiri@lincoln.ac.uk'),
(13, 'Grzegorz Cielniak', 'Senior Lecturer', 'Computer Science', '01522837398', 'gcielniak@lincoln.ac.uk '),
(14, 'Patrick Dickinson', 'Principal Lecturer (Enterprise)', 'Computer Science', '01522886946 ', 'pdickinson@lincoln.ac.uk'),
(15, 'Derek Foster', 'Lecturer', 'Computer Science', '01522886925 ', 'defoster@lincoln.ac.uk '),
(16, 'John Murray', 'Principal Lecturer (Teaching)', 'Computer Science', '01522886903 ', 'jomurray@lincoln.ac.uk '),
(17, 'Nicola Bellotto', 'Reader', 'Computer Science', '01522886903 ', 'nbellotto@lincoln.ac.uk '),
(18, 'Olivier Szymanezyk', 'Research Fellow (Location-Based Services for Mobiles)', 'Computer Science', '01522886858 ', 'oszymanezyk@lincoln.ac.uk '),
(19, 'Mel Chapman', 'Associate Lecturer', 'Computer Science', '01522835712 ', 'mchapman@lincoln.ac.uk '),
(20, 'Sarah Williams', 'Senior Administrator/PA', 'Computer Science', '01522837925 ', 'sarawilliams@lincoln.ac.uk'),
(21, 'Nigel Allinson', 'Distinguished Professor of Image Engineering', 'Computer Science', '01522837710', 'nallinson@lincoln.ac.uk'),
(22, 'Grainne Riley', 'Senior Project Manager', 'Computer Science', 'Not Specified', 'griley@lincoln.ac.uk'),
(23, 'Marc Hanheide', 'Reader', 'Computer Science', '01522886966', 'mhanheide@lincoln.ac.uk '),
(24, 'Wenting Duan', 'Lecturer', 'Computer Science', '01522886955 ', 'wduan@lincoln.ac.uk '),
(25, 'Chris Waltham', 'Senior Technician', 'Computer Science', '01522886229 ', 'cwaltham@lincoln.ac.uk '),
(26, 'Xujiong Ye', 'Reader', 'Computer Science', '01522837344 ', 'xye@lincoln.ac.uk '),
(27, 'Tryphon Lambrou', 'Senior Lecturer', 'Computer Science', '01522886149 ', 'tlambrou@lincoln.ac.uk'),
(28, 'Michela Esposito', 'Research Fellow', 'Computer Science', '01522886229 ', 'mesposito@lincoln.ac.uk'),
(29, 'Tomas Krajnik', 'Research Fellow', 'Computer Science', '01522837118 ', 'tkrajnik@lincoln.ac.uk '),
(30, 'Jaime Pulido Fentanes', 'Research Assistant', 'Computer Science', '01522837466', 'jpulidofentanes@lincoln.ac.uk'),
(31, 'Georgios Leontidis', 'Marie Curie ITN Early Stage Researcher', 'Computer Science', '01522886873 ', 'gleontidis@lincoln.ac.uk '),
(32, 'Guopeng Zhang', 'Research Assistant', 'Computer Science', 'Not Specified', 'gzhang@lincoln.ac.uk '),
(33, 'Bruce Hargrave', 'Senior Lecturer', 'Computer Science', '01522837312 ', 'bhargrave@lincoln.ac.uk'),
(34, 'Saddam Bekhet', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'sbekhet@lincoln.ac.uk'),
(35, 'Sean Oxspring', 'Hourly Paid Demonstrator', 'Computer Science', '07541288777 ', 'soxspring@lincoln.ac.uk '),
(36, 'Kwamena Appiah-Kubi', 'Hourly Paid Demonstrator', 'Computer Science', '01522837468 ', 'kappiahkubi@lincoln.ac.uk '),
(37, 'Kathrin Gerling', 'Senior Lecturer', 'Computer Science', 'Not Specified', 'kgerling@lincoln.ac.uk '),
(38, 'John Shearer', 'Senior Lecturer', 'Computer Science', 'Not Specified', 'jshearer@lincoln.ac.uk '),
(39, 'Christian Dondrup', 'Hourly Paid Demonstrator', 'Computer Science', '01522837453 ', 'cdondrup@lincoln.ac.uk '),
(40, 'Andy Cowe', 'Lecturer', 'Computer Science ', '01522837318 ', 'acowe@lincoln.ac.uk '),
(41, 'Mohammadreza Soltaninejad', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'msoltaninejad@lincoln.ac.uk '),
(42, 'Yi Gao', 'Research Assistant', 'Computer Science', 'Not Specified', 'ygao@lincoln.ac.uk '),
(43, 'Francesco Caliva', 'Marie Curie ITN Early Stage Researcher', 'Computer Science', 'Not Specified', 'fcaliva@lincoln.ac.uk '),
(44, 'Lei Zhang', 'Postdoctoral Research Fellow', 'Computer Science', 'Not Specified', 'lzhang@lincoln.ac.uk '),
(45, 'Ji Ni', 'Research Fellow', 'Computer Science', 'Not Specified', 'jni@lincoln.ac.uk '),
(46, 'Victoria Bryant', 'Clerical Officer', 'Computer Science', '01522837302 ', 'vibryant@lincoln.ac.uk '),
(47, 'Farhad Bazyari', 'Research Fellow', 'Computer Science', 'Not Specified', 'fbazyari@lincoln.ac.uk '),
(48, 'Kieran Hicks', 'Research Assistant', 'Computer Science', 'Not Specified', 'khicks@lincoln.ac.uk '),
(49, 'Jamie Hutton', 'Hourly Paid Demonstrator', 'Computer Science', '01522837722 ', 'jhutton@lincoln.ac.uk '),
(50, 'Helen Webster', 'Associate Lecturer', 'Computer Science', 'Not Specified', 'hwebster@lincoln.ac.uk '),
(51, 'Deema Abdal Hafeth', 'Staff Member', 'Computer Science', 'Not Specified', 'dabdalhafeth@lincoln.ac.uk '),
(52, 'Farshad Arvin', 'Phd Student', 'Computer Science', 'Not Specified', 'farvin@lincoln.ac.uk '),
(53, 'Mriganka Biswas', 'MPhil/PHd Student', 'Computer Science', 'Not Specified', 'mrbiswas@lincoln.ac.uk '),
(54, 'Daqi Liu', 'PhD Student', 'Computer Science', 'Not Specified', 'dliu@lincoln.ac.uk '),
(55, 'Alaa Al-Zoubi', 'MPhil/PhD Student', 'Computer Science', 'Not Specified', 'aalzoubi@lincoln.ac.uk '),
(56, 'Belal Al-Daradkah', 'MPhil/PhD Research', 'Computer Science', 'Not Specified', 'baldaradkah@lincoln.ac.uk '),
(57, 'Talal Albacha', 'PhD/MPhil international student', 'Computer Science', 'Not Specified', 'talbacha@lincoln.ac.uk '),
(58, 'Mowda Abdalla', 'Postgraduate student', 'Computer Science', 'Not Specified', 'mabdalla@lincoln.ac.uk '),
(59, 'Cheng Hu', 'PhD student', 'Computer Science', 'Not Specified', 'chu@lincoln.ac.uk '),
(60, 'Ibraheem Alnejaidi', 'PhD student', 'Computer Science', 'Not specified', 'ialnejaidi@lincoln.ac.uk '),
(61, 'Piotr Lukasz', 'PhD Student', 'Computer Scinece', 'Not Specified', 'plukasz@lincoln.ac.uk '),
(62, 'Ernest Gyebi', 'PhD Student', 'Computer Science', 'Not Specified', 'ergyebi@lincoln.ac.uk '),
(63, 'Noha Ghatwary', 'MPhil/PhD Student', 'Computer Science', 'Not Specified', 'nghatwary@lincoln.ac.uk '),
(64, 'Hussein Alahmer', 'MPhil/PhD Student', 'Computer Science', 'Not Specified', 'halahmer@lincoln.ac.uk '),
(65, 'Joao Santos', 'PhD Student', 'Computer Science', '01522837428 ', 'jsantos@lincoln.ac.uk '),
(66, 'Evangelia Kotsiliti', 'PhD Student', 'Computer Science', 'Not Specified', 'ekotsiliti@lincoln.ac.uk '),
(67, 'Rahman Attar', 'PhD Student', 'Computer Science', 'Not Specified', 'rattar@lincoln.ac.uk '),
(68, 'Effie Le Moignan', 'Post Graduate Studen', 'Computer Science', 'Not Specified', 'elemoignan@lincoln.ac.uk '),
(69, 'Giovanni Ometto', 'Researcher', 'Computer Science', 'Not Specified', 'gometto@lincoln.ac.uk '),
(70, 'Casmir Maazure', 'MPhil/PhD Student', 'Computer Science', 'Not Specified', 'cmaazure@lincoln.ac.uk '),
(71, 'Peter Lightbody', 'PhD Student', 'Computer Science', 'Not Specified', 'plightbody@lincoln.ac.uk '),
(72, 'Xuqiang Zheng', 'PhD student', 'Computer Science', 'Not Specified', 'xzheng@lincoln.ac.uk '),
(73, 'Ashiqur Rahman', 'Research student', 'Computer Scinece', 'Not Specified', 'arahman@lincoln.ac.uk '),
(74, 'Alyaa Amer', 'MPhil/PhD student', 'Computer Science', 'Not Specified', 'aamer@lincoln.ac.uk '),
(75, 'Mu Hua', 'Intern for maintaining CIL project hazcept website', 'Computer Science', 'Not Specified', 'mhua@lincoln.ac.uk '),
(76, 'Touseef Ahmad Qureshi', 'Research Assistant', 'Computer Science', 'Not Specified', 'tqureshi@lincoln.ac.uk '),
(77, 'George Broughton', 'Research student', 'Computer Science', 'Not Specified', 'gbroughton@lincoln.ac.uk '),
(78, 'James Brown', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'jabrown@lincoln.ac.uk '),
(79, 'Daniel Durand', 'Visiting Researcher', 'Computer Science', 'Not Specified', 'ddurand@lincoln.ac.uk '),
(80, 'Jamie Mahoney', 'Research Assistant', 'Computer Science', '01522886178 ', 'jmahoney@lincoln.ac.uk '),
(81, 'Chris Headleand', 'Lecturer', 'Computer Science', 'Not Specified', 'cheadleand@lincoln.ac.uk '),
(82, 'Paul Baxter', 'Senior Lecturer', 'Computer Science', 'Not Specified', 'pbaxter@lincoln.ac.uk '),
(83, 'Richard Wetzel', 'Lecturer', 'Computer Science', 'Not Specified', 'rwetzel@lincoln.ac.uk '),
(84, 'Can Huang', 'Research Assistant', 'Computer Science', 'Not Specified', 'chunag@lincoln.ac.uk '),
(85, 'Maged Habib', 'Honorary Lecturer', 'Computer Science', 'Not Specified', 'mhabib@lincoln.ac.uk '),
(86, 'Liyun Gong', 'Charlotte Angas Scott Research Fellowship', 'Computer Scinece', 'Not Specified', 'lgong@lincoln.ac.uk '),
(87, 'Wai Lo', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'wlo@lincoln.ac.uk '),
(88, 'Feng Zhao', 'Post Doctoral Research Fellow', 'Computer Science', 'Not Specified', 'fezhao@lincoln.ac.uk '),
(89, 'Jamie Lord', 'Hourly Paid Demonstrator', 'Computer Scinece', 'Not Specified', 'jlord@lincoln.ac.uk '),
(90, 'Jack Gallacher', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'jgallacher@lincoln.ac.uk'),
(91, 'Jason Burgon', 'Senior Technician', 'Computer Science', 'Not Specified', 'jburgon@lincoln.ac.uk '),
(92, 'Serhan Cosar', 'Postdoctoral Research Fellow in Assistive Robotics', 'Computer Science', 'Not Specified', 'scosar@lincoln.ac.uk '),
(93, 'Shyamala C doraisamy', 'Visitor Research Professor', 'Computer Scinece', 'Not Specified', 'scdoraisamy@lincoln.ac.uk '),
(94, 'Meng Wen', 'Research Assistant', 'Computer Science', 'Not Specified', 'mwen@lincoln.ac.uk '),
(95, 'Yingtong Chen', 'Research Assistant', 'Computer Science', 'Not Specified', 'ychen@lincoln.ac.uk '),
(96, 'Yongchao Yu', 'Research Assistant', 'Computer Science', 'Not Specified', 'yyu@lincoln.ac.uk '),
(97, 'Bowei Chen', 'Lecturer', 'Computer Science', '01522886781 ', 'bchen@lincoln.ac.uk '),
(98, 'Chris Buckingham', 'Hourly Paid Demonstrator', 'Computer Science', 'Not Specified', 'cbuckingham@lincoln.ac.uk '),
(99, 'Mingzhu Long', 'Visiting Researcher', 'Computer Science', 'Not Specified', 'mlong@lincoln.ac.uk '),
(100, 'Robert Evans', 'Hourly Paid Demonstrator', 'Computer Scinece', 'Not Specified', 'roevans@lincoln.ac.uk '),
(101, 'Sinjun Strydom', 'Hourly Paid Demonstrator', 'Computer Sciecne', 'Computer Science', 'sstrydom@lincoln.ac.uk ');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
