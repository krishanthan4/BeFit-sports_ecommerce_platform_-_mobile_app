-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.41-0ubuntu0.24.04.1 - (Ubuntu)
-- Server OS:                    Linux
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for befit_db
CREATE DATABASE IF NOT EXISTS `befit_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `befit_db`;

-- Dumping structure for table befit_db.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `fname` varchar(45) DEFAULT NULL,
  `lname` varchar(45) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `vcode` varchar(20) DEFAULT NULL,
  `admin_img` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.admin: ~0 rows (approximately)
INSERT INTO `admin` (`fname`, `lname`, `email`, `vcode`, `admin_img`) VALUES
	('adminFname', 'adminLname', 'hacktf.academy@gmail.com', '67bba848bf02b', '../resources/profile_images/adminFname_67bbaad86b149.png');

-- Dumping structure for table befit_db.brand
CREATE TABLE IF NOT EXISTS `brand` (
  `brand_id` int NOT NULL AUTO_INCREMENT,
  `brand_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.brand: ~27 rows (approximately)
INSERT INTO `brand` (`brand_id`, `brand_name`) VALUES
	(1, 'Yonex'),
	(2, 'Wish'),
	(3, 'Wilson'),
	(4, 'Protech'),
	(5, 'Kawasaki'),
	(6, 'Yang Yang'),
	(7, 'Adidas'),
	(8, 'Speedo'),
	(9, 'Jade Swim'),
	(10, 'Nike'),
	(11, 'Arena'),
	(12, 'Life Fitness'),
	(13, 'FitLine'),
	(14, 'Reebok'),
	(15, 'Cybex'),
	(16, 'Viva Fitness'),
	(17, 'ThenX'),
	(18, 'Alien Workshop'),
	(19, 'BOOM'),
	(20, 'Zoo York'),
	(21, 'GAN'),
	(22, 'Moyu'),
	(23, 'Rubiks'),
	(24, 'Gravity Fitness'),
	(25, 'Iron Company'),
	(26, 'Pullup & Dip'),
	(27, 'Street Gains');

-- Dumping structure for table befit_db.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT,
  `qty` int DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `fk_cart_user1_idx` (`user_email`),
  KEY `fk_cart_product1_idx` (`product_id`),
  CONSTRAINT `fk_cart_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_cart_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.cart: ~11 rows (approximately)
INSERT INTO `cart` (`cart_id`, `qty`, `user_email`, `product_id`) VALUES
	(88, 1, 'lifaso6961@devncie.com', 105),
	(89, 1, 'yolixac932@joeroc.com', 5),
	(92, 1, 'feroj41049@devncie.com', 25),
	(93, 1, 'feroj41049@devncie.com', 6),
	(95, 1, 'feroj41049@devncie.com', 105);

-- Dumping structure for table befit_db.category
CREATE TABLE IF NOT EXISTS `category` (
  `cat_id` int NOT NULL AUTO_INCREMENT,
  `cat_name` varchar(50) DEFAULT NULL,
  `cat_img` text,
  `cat_icon` tinytext NOT NULL,
  PRIMARY KEY (`cat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.category: ~6 rows (approximately)
INSERT INTO `category` (`cat_id`, `cat_name`, `cat_img`, `cat_icon`) VALUES
	(1, 'Calisthenics', './resources/category1.jpeg', 'public/images/sort_icons/calisthenics.svg'),
	(2, 'Weight Training', './resources/category2.jpeg', 'public/images/sort_icons/gym.svg'),
	(3, 'Swimming', './resources/category3.jpg', 'public/images/sort_icons/swimming.svg'),
	(4, 'Badminton', './resources/category4.jpg', 'public/images/sort_icons/badminton.svg'),
	(5, 'Skateboard', './resources/category5.jpeg', 'public/images/sort_icons/skateboard.svg'),
	(6, 'Rubik Cubes', './resources/category6.jpg', 'public/images/sort_icons/rubik.svg');

-- Dumping structure for table befit_db.category_has_brand
CREATE TABLE IF NOT EXISTS `category_has_brand` (
  `category_cat_id` int NOT NULL,
  `brand_brand_id` int NOT NULL,
  PRIMARY KEY (`category_cat_id`,`brand_brand_id`),
  KEY `fk_category_has_brand_brand1_idx` (`brand_brand_id`),
  KEY `fk_category_has_brand_category1_idx` (`category_cat_id`),
  CONSTRAINT `fk_category_has_brand_brand1` FOREIGN KEY (`brand_brand_id`) REFERENCES `brand` (`brand_id`),
  CONSTRAINT `fk_category_has_brand_category1` FOREIGN KEY (`category_cat_id`) REFERENCES `category` (`cat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.category_has_brand: ~23 rows (approximately)
INSERT INTO `category_has_brand` (`category_cat_id`, `brand_brand_id`) VALUES
	(4, 1),
	(4, 2),
	(3, 7),
	(3, 8),
	(3, 9),
	(3, 10),
	(3, 11),
	(2, 12),
	(2, 13),
	(2, 14),
	(2, 15),
	(2, 16),
	(1, 17),
	(5, 18),
	(5, 19),
	(5, 20),
	(6, 21),
	(6, 22),
	(6, 23),
	(1, 24),
	(1, 25),
	(1, 26),
	(1, 27);

-- Dumping structure for table befit_db.city
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(45) DEFAULT NULL,
  `district_district_id` int NOT NULL,
  PRIMARY KEY (`city_id`),
  KEY `fk_city_district1_idx` (`district_district_id`),
  CONSTRAINT `fk_city_district1` FOREIGN KEY (`district_district_id`) REFERENCES `district` (`district_id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.city: ~127 rows (approximately)
INSERT INTO `city` (`city_id`, `city_name`, `district_district_id`) VALUES
	(1, 'Colombo', 28),
	(2, 'Dehiwala-Mount Lavinia', 28),
	(3, 'Sri Jayawardenepura Kotte', 28),
	(4, 'Moratuwa', 28),
	(5, 'Negombo', 28),
	(6, 'Peliyagoda', 28),
	(7, 'Kelaniya', 28),
	(8, 'Gampaha', 29),
	(9, 'Negombo', 29),
	(10, 'Kelaniya', 29),
	(11, 'Ja-Ela', 29),
	(12, 'Wattala', 29),
	(13, 'Minuwangoda', 29),
	(14, 'Attanagalla', 29),
	(15, 'Kalutara', 30),
	(16, 'Panadura', 30),
	(17, 'Horana', 30),
	(18, 'Beruwala', 30),
	(19, 'Matugama', 30),
	(20, 'Bandaragama', 30),
	(21, 'Aluthgama', 30),
	(22, 'Kandy', 1),
	(23, 'Peradeniya', 1),
	(24, 'Katugastota', 1),
	(25, 'Gampola', 1),
	(26, 'Akurana', 1),
	(27, 'Digana', 1),
	(28, 'Nawalapitiya', 1),
	(29, 'Matale', 2),
	(30, 'Dambulla', 2),
	(31, 'Rattota', 2),
	(32, 'Galewela', 2),
	(33, 'Palapathwela', 2),
	(34, 'Ukuwela', 2),
	(35, 'Nuwara Eliya', 8),
	(36, 'Hatton', 8),
	(37, 'Talawakele', 8),
	(38, 'Ginigathhena', 8),
	(39, 'Ragala', 8),
	(40, 'Lindula', 8),
	(41, 'Galle', 23),
	(42, 'Ambalangoda', 23),
	(43, 'Hikkaduwa', 23),
	(44, 'Karapitiya', 23),
	(45, 'Elpitiya', 23),
	(46, 'Baddegama', 23),
	(47, 'Matara', 25),
	(48, 'Weligama', 25),
	(49, 'Kamburugamuwa', 25),
	(50, 'Hakmana', 25),
	(51, 'Akuressa', 25),
	(52, 'Devinuwara', 25),
	(53, 'Hambantota', 24),
	(54, 'Ambalantota', 24),
	(55, 'Tangalle', 24),
	(56, 'Tissamaharama', 24),
	(57, 'Walasmulla', 24),
	(58, 'Weeraketiya', 24),
	(59, 'Jaffna', 14),
	(60, 'Point Pedro', 14),
	(61, 'Chavakachcheri', 14),
	(62, 'Nallur', 14),
	(63, 'Kilinochchi', 14),
	(64, 'Mannar', 16),
	(65, 'Talaimannar', 16),
	(66, 'Erukkulampiddi', 16),
	(67, 'Vavuniya', 18),
	(68, 'Vavuniya South', 18),
	(69, 'Omanthai', 18),
	(70, 'Mullaitivu', 17),
	(71, 'Puthukkudiyiruppu', 17),
	(72, 'Oddusuddan', 17),
	(73, 'Kilinochchi', 15),
	(74, 'Paranthan', 15),
	(75, 'Pooneryn', 15),
	(76, 'Batticaloa', 10),
	(77, 'Kattankudy', 10),
	(78, 'Valaichchenai', 10),
	(79, 'Eravur', 10),
	(80, 'Chenkalady', 10),
	(81, 'Ampara', 9),
	(82, 'Akkaraipattu', 9),
	(83, 'Kalmunai', 9),
	(84, 'Sammanthurai', 9),
	(85, 'Pottuvil', 9),
	(86, 'Trincomalee', 11),
	(87, 'Kinniya', 11),
	(88, 'Gomarankadawala', 11),
	(89, 'Nilaveli', 11),
	(90, 'Kurunegala', 19),
	(91, 'Kuliyapitiya', 19),
	(92, 'Nikaweratiya', 19),
	(93, 'Polgahawela', 19),
	(94, 'Chilaw', 19),
	(95, 'Puttalam', 20),
	(96, 'Anamaduwa', 20),
	(97, 'Chilaw', 20),
	(98, 'Wennappuwa', 20),
	(99, 'Anuradhapura', 12),
	(100, 'Medawachchiya', 12),
	(101, 'Talawa', 12),
	(102, 'Kekirawa', 12),
	(103, 'Kebithigollewa', 12),
	(104, 'Polonnaruwa', 13),
	(105, 'Kaduruwela', 13),
	(106, 'Hingurakgoda', 13),
	(107, 'Dimbulagala', 13),
	(108, 'Badulla', 26),
	(109, 'Bandarawela', 26),
	(110, 'Haputale', 26),
	(111, 'Diyatalawa', 26),
	(112, 'Ella', 26),
	(113, 'Monaragala', 27),
	(114, 'Wellawaya', 27),
	(115, 'Bibile', 27),
	(116, 'Buttala', 27),
	(117, 'Kataragama', 27),
	(118, 'Ratnapura', 22),
	(119, 'Embilipitiya', 22),
	(120, 'Balangoda', 22),
	(121, 'Kuruwita', 22),
	(122, 'Eheliyagoda', 22),
	(123, 'Kegalle', 21),
	(124, 'Mawanella', 21),
	(125, 'Deraniyagala', 21),
	(126, 'Ruwanwella', 21),
	(127, 'Dehiowita', 21),
	(128, 'Henveiru', 56),
	(129, 'Maafannu', 56),
	(130, 'Galolhu', 56),
	(131, 'Machangolhi', 56),
	(132, 'Vilimale', 56),
	(133, 'Hulhumalé Phase 1', 32),
	(134, 'Hulhumalé Phase 2', 32),
	(135, 'Nirolhu Magu', 32),
	(136, 'Reefside', 32),
	(137, 'Farukolhufushi', 32),
	(138, 'Villingili North', 33),
	(139, 'Villingili South', 33),
	(140, 'Villingili Central', 33),
	(141, 'Villingili Beachside', 33),
	(142, 'Villingili Harbor', 33),
	(143, 'Gulhi Beachside', 34),
	(144, 'Gulhi Central', 34),
	(145, 'Gulhi South', 34),
	(146, 'Gulhi West', 34),
	(147, 'Gulhi North', 34),
	(148, 'Maafushi East', 35),
	(149, 'Maafushi West', 35),
	(150, 'Maafushi South', 35),
	(151, 'Maafushi North', 35),
	(152, 'Maafushi Tourism Zone', 35),
	(153, 'Changzamtok', 36),
	(154, 'Motithang', 36),
	(155, 'Babesa', 36),
	(156, 'Lungtenzampa', 36),
	(157, 'Dechencholing', 36),
	(158, 'Jungshina', 37),
	(159, 'Langjopakha', 37),
	(160, 'Taba', 37),
	(161, 'Yangchenphug', 37),
	(162, 'Tshalu Bar', 37),
	(163, 'Semtokha', 38),
	(164, 'Changbangdu', 38),
	(165, 'Tendrelthang', 38),
	(166, 'Changlimithang', 38),
	(167, 'Norzin Lam', 38),
	(168, 'Khasadrapchu', 39),
	(169, 'Chamgang', 39),
	(170, 'Namseling', 39),
	(171, 'Tandin Ney', 39),
	(172, 'Bjemina', 39),
	(173, 'Naro Base', 40),
	(174, 'Naro Upper', 40),
	(175, 'Naro East', 40),
	(176, 'Naro Valley', 40),
	(177, 'Naro Monastery', 40),
	(178, 'Mohammadpur', 41),
	(179, 'Gulshan', 41),
	(180, 'Uttara', 41),
	(181, 'Banani', 41),
	(182, 'Dhanmondi', 41),
	(183, 'Tongi', 42),
	(184, 'Konabari', 42),
	(185, 'Chandona', 42),
	(186, 'Kaliakair', 42),
	(187, 'Sreepur', 42),
	(188, 'Narayanganj City', 43),
	(189, 'Fatullah', 43),
	(190, 'Siddhirganj', 43),
	(191, 'Sonargaon', 43),
	(192, 'Bandar', 43),
	(193, 'Tangail City', 44),
	(194, 'Madhupur', 44),
	(195, 'Basail', 44),
	(196, 'Sakhipur', 44),
	(197, 'Mirzapur', 44),
	(198, 'Manikganj Sadar', 45),
	(199, 'Shivalaya', 45),
	(200, 'Harirampur', 45),
	(201, 'Singair', 45),
	(202, 'Daulatpur', 45),
	(203, 'Kabul City', 46),
	(204, 'Shahr-e Naw', 46),
	(205, 'Deh Afghanan', 46),
	(206, 'Kart-e Parwan', 46),
	(207, 'Wazir Akbar Khan', 46),
	(208, 'Paghman Valley', 47),
	(209, 'Sanglakh', 47),
	(210, 'Darulaman', 47),
	(211, 'Maidan', 47),
	(212, 'Bagrami', 47),
	(213, 'Deh Sabz', 48),
	(214, 'Chahar Asyab Bazaar', 48),
	(215, 'Logar Road', 48),
	(216, 'Qalai Zaman Khan', 48),
	(217, 'Surobi', 48),
	(218, 'Deh Sabz East', 49),
	(219, 'Deh Sabz West', 49),
	(220, 'Pul-e-Charkhi', 49),
	(221, 'Afshar', 49),
	(222, 'Dasht-e-Barchi', 49),
	(223, 'Musahi Village', 50),
	(224, 'Sayedan', 50),
	(225, 'Guldara', 50),
	(226, 'Qarabagh', 50),
	(227, 'Khak Jabar', 50),
	(228, 'Colaba', 51),
	(229, 'Bandra', 51),
	(230, 'Andheri', 51),
	(231, 'Dadar', 51),
	(232, 'Borivali', 51),
	(233, 'Shivajinagar', 52),
	(234, 'Kothrud', 52),
	(235, 'Viman Nagar', 52),
	(236, 'Hinjewadi', 52),
	(237, 'Katraj', 52),
	(238, 'Panchavati', 53),
	(239, 'Satpur', 53),
	(240, 'Ambad', 53),
	(241, 'Indira Nagar', 53),
	(242, 'Adgaon', 53),
	(243, 'Sitabuldi', 54),
	(244, 'Dharampeth', 54),
	(245, 'Sadar', 54),
	(246, 'Manewada', 54),
	(247, 'Mahal', 54),
	(248, 'CIDCO', 55),
	(249, 'Chikalthana', 55),
	(250, 'Osmanpura', 55),
	(251, 'Shahganj', 55),
	(252, 'Kanchanwadi', 55);

-- Dumping structure for table befit_db.color
CREATE TABLE IF NOT EXISTS `color` (
  `clr_id` int NOT NULL AUTO_INCREMENT,
  `clr_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`clr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.color: ~9 rows (approximately)
INSERT INTO `color` (`clr_id`, `clr_name`) VALUES
	(1, 'red'),
	(2, 'green'),
	(3, 'gray'),
	(4, 'black'),
	(9, 'purple'),
	(10, 'yellow'),
	(11, 'pink'),
	(12, 'orange'),
	(21, 'tan');

-- Dumping structure for table befit_db.condition
CREATE TABLE IF NOT EXISTS `condition` (
  `condition_id` int NOT NULL AUTO_INCREMENT,
  `condition_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`condition_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.condition: ~2 rows (approximately)
INSERT INTO `condition` (`condition_id`, `condition_name`) VALUES
	(1, 'Brand New'),
	(2, 'Used');

-- Dumping structure for table befit_db.country
CREATE TABLE IF NOT EXISTS `country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT 'Sri Lanka',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.country: ~8 rows (approximately)
INSERT INTO `country` (`id`, `name`) VALUES
	(1, 'Sri Lanka'),
	(2, 'India'),
	(3, 'Afghanistan'),
	(4, 'Bangladesh'),
	(5, 'Butan'),
	(6, 'Maldives'),
	(7, 'Nepal'),
	(8, 'Pakistan');

-- Dumping structure for table befit_db.district
CREATE TABLE IF NOT EXISTS `district` (
  `district_id` int NOT NULL AUTO_INCREMENT,
  `district_name` varchar(45) DEFAULT NULL,
  `province_province_id` int NOT NULL,
  PRIMARY KEY (`district_id`),
  KEY `fk_district_province1_idx` (`province_province_id`),
  CONSTRAINT `fk_district_province1` FOREIGN KEY (`province_province_id`) REFERENCES `province` (`province_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.district: ~25 rows (approximately)
INSERT INTO `district` (`district_id`, `district_name`, `province_province_id`) VALUES
	(1, 'Kandy', 3),
	(2, 'Matale', 3),
	(8, 'Nuwara Eliya', 3),
	(9, 'Ampara', 4),
	(10, 'Batticaloa', 4),
	(11, 'Trincomalee', 4),
	(12, 'Anuradhapura', 5),
	(13, 'Polonnaruwa', 5),
	(14, 'Jaffna', 6),
	(15, 'Kilinochchi', 6),
	(16, 'Mannar', 6),
	(17, 'Mullaitivu', 6),
	(18, 'Vavuniya', 6),
	(19, 'Kurunegala', 7),
	(20, 'Puttalam', 7),
	(21, 'Kegalle', 8),
	(22, 'Ratnapura', 8),
	(23, 'Galle', 9),
	(24, 'Hambantota', 9),
	(25, 'Matara', 9),
	(26, 'Badulla', 10),
	(27, 'Monaragala', 10),
	(28, 'Colombo', 11),
	(29, 'Gampaha', 11),
	(30, 'Kalutara', 11),
	(31, 'Malé', 39),
	(32, 'Hulhumalé', 39),
	(33, 'Villingili', 39),
	(34, 'Gulhi', 39),
	(35, 'Maafushi', 39),
	(36, 'Thimphu', 22),
	(37, 'Kawang', 22),
	(38, 'Chang', 22),
	(39, 'Mewang', 22),
	(40, 'Naro', 22),
	(41, 'Dhaka', 3),
	(42, 'Gazipur', 3),
	(43, 'Narayanganj', 3),
	(44, 'Tangail', 3),
	(45, 'Manikganj', 3),
	(46, 'Kabul', 50),
	(47, 'Paghman', 50),
	(48, 'Chahar Asyab', 50),
	(49, 'Deh Sabz', 50),
	(50, 'Musahi', 50),
	(51, 'Mumbai', 51),
	(52, 'Pune', 51),
	(53, 'Nashik', 51),
	(54, 'Nagpur', 51),
	(55, 'Aurangabad', 51),
	(56, 'Malé', 39);

-- Dumping structure for table befit_db.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `feed_id` int NOT NULL AUTO_INCREMENT,
  `stars` int DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `feed` varchar(250) DEFAULT NULL,
  `product_id` int NOT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`feed_id`),
  KEY `fk_feedback_product1_idx` (`product_id`),
  KEY `fk_feedback_user1_idx` (`user_email`),
  CONSTRAINT `fk_feedback_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_feedback_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.feedback: ~5 rows (approximately)
INSERT INTO `feedback` (`feed_id`, `stars`, `date`, `feed`, `product_id`, `user_email`) VALUES
	(32, 4, '2024-06-27 15:01:52', 'Good Product', 2, 'krishanthan2022.4.4@gmail.com'),
	(34, 1, '2024-06-28 11:44:10', 'waste of money', 25, 'feroj41049@devncie.com'),
	(35, 3, '2024-06-28 11:44:36', 'OK', 6, 'feroj41049@devncie.com'),
	(37, 4, '2025-02-16 12:44:06', 'Good Product.I Really like this', 30, 'sarathmunasinghe07@gmail.com'),
	(38, 0, '2025-02-21 00:19:54', 'I bought this product ,but still not delivered yet. worst experience', 105, 'sarathmunasinghe07@gmail.com'),
	(39, 4, '2025-02-23 21:36:17', '.', 105, 'sarathmunasinghe07@gmail.com');

-- Dumping structure for table befit_db.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `gender_id` int NOT NULL AUTO_INCREMENT,
  `gender_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`gender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.gender: ~2 rows (approximately)
INSERT INTO `gender` (`gender_id`, `gender_name`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table befit_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `fk_invoice_user1_idx` (`user_email`),
  CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.invoice: ~14 rows (approximately)
INSERT INTO `invoice` (`invoice_id`, `order_id`, `date`, `total`, `user_email`) VALUES
	(33, '667d20e1064da', '2024-06-27 01:51:17', 11705, 'krishanthan2022.4.4@gmail.com'),
	(34, '667e2728d338a', '2024-06-28 08:30:33', 3911, 'yolixac932@joeroc.com'),
	(35, '667e4bfa93725', '2024-06-28 11:07:42', 13331, 'feroj41049@devncie.com'),
	(36, '667e4cffab6e9', '2024-06-28 11:11:47', 2210, 'feroj41049@devncie.com'),
	(37, '667e77dcc1413', '2024-06-28 02:14:43', 6728, 'sarathmunasinghe07@gmail.com'),
	(38, 'a3020caf-8f7', '2025-02-15 12:30:35', 8235, 'sarathmunasinghe07@gmail.com'),
	(39, '57998fd5-6cf', '2025-02-15 12:34:16', 8235, 'sarathmunasinghe07@gmail.com'),
	(40, '7d485a17-92a', '2025-02-15 12:50:46', 2936, 'sarathmunasinghe07@gmail.com'),
	(41, 'cb481542-27a', '2025-02-15 12:52:51', 2936, 'sarathmunasinghe07@gmail.com'),
	(42, '8511d747-c40', '2025-02-15 01:08:46', 2300, 'sarathmunasinghe07@gmail.com'),
	(43, '8e11c868-30d', '2025-02-15 01:13:38', 4936, 'sarathmunasinghe07@gmail.com'),
	(44, '23ab61e3-3b0', '2025-02-20 05:35:13', 12341, 'm@g.com'),
	(45, '0c7b5ee3-602', '2025-02-20 05:45:48', 9899, 'm@g.com'),
	(46, 'a6ee6779-13e', '2025-02-20 06:25:11', 6300, 'sarathmunasinghe07@gmail.com'),
	(47, 'ef10a28c-b04', '2025-02-23 11:29:54', 6300, 'd@gm.com');

-- Dumping structure for table befit_db.invoice_has_products
CREATE TABLE IF NOT EXISTS `invoice_has_products` (
  `invoice_id` int NOT NULL,
  `product_id` int NOT NULL,
  `bought_qty` int NOT NULL,
  `order_status` int NOT NULL,
  PRIMARY KEY (`invoice_id`,`product_id`),
  KEY `product_id` (`product_id`),
  KEY `fk_invoice_has_products_order_status1_idx` (`order_status`),
  CONSTRAINT `fk_invoice_has_products_order_status1` FOREIGN KEY (`order_status`) REFERENCES `order_status` (`order_status_id`),
  CONSTRAINT `invoice_has_products_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`),
  CONSTRAINT `invoice_has_products_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.invoice_has_products: ~26 rows (approximately)
INSERT INTO `invoice_has_products` (`invoice_id`, `product_id`, `bought_qty`, `order_status`) VALUES
	(33, 2, 1, 4),
	(33, 4, 1, 1),
	(33, 5, 1, 1),
	(34, 5, 1, 1),
	(35, 6, 1, 4),
	(35, 25, 1, 4),
	(36, 105, 1, 1),
	(37, 13, 1, 1),
	(37, 30, 1, 1),
	(38, 11, 1, 1),
	(39, 11, 1, 1),
	(40, 105, 1, 1),
	(41, 105, 1, 1),
	(42, 105, 1, 1),
	(43, 105, 2, 1),
	(44, 9, 1, 1),
	(44, 11, 1, 1),
	(45, 11, 1, 1),
	(45, 105, 1, 1),
	(46, 105, 3, 1),
	(47, 105, 3, 1);

-- Dumping structure for table befit_db.model
CREATE TABLE IF NOT EXISTS `model` (
  `model_id` int NOT NULL AUTO_INCREMENT,
  `model_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.model: ~44 rows (approximately)
INSERT INTO `model` (`model_id`, `model_name`) VALUES
	(1, 'Parallettes Pro'),
	(2, 'Pull-Up Bar Elite'),
	(3, 'Gymnastic Rigs'),
	(4, 'Dip Station'),
	(5, 'Resistance Bands Set'),
	(6, 'Adjustable Kettlebell'),
	(7, 'Ab Roler'),
	(8, 'Push-Up Bars'),
	(9, 'Battle Ropes'),
	(10, 'Speedo Fastskin LZR Racer X'),
	(11, 'Speedo Hydrosense Flowback Swimsuit'),
	(12, 'Speedo Vanquisher 2.0 Goggles'),
	(13, 'Speedo Nemesis Contour Paddle'),
	(14, 'Speedo Teamster Backpack'),
	(15, 'Alien Workshop Spectrum Skateboard'),
	(16, 'Alien Workshop Abduction Complete Skateboard'),
	(17, 'Alien Workshop Visitor Deck'),
	(18, 'Alien Workshop Spectrum Complete'),
	(19, 'Alien Workshop Logo Fade Skateboard'),
	(20, 'Yonex Astrox 99 Pro'),
	(21, 'Yonex Duora 10'),
	(22, 'Yonex Voltric Z-Force II'),
	(23, 'Yonex Nanoray 900'),
	(24, 'Yonex Arcsaber 11'),
	(25, 'Wish Carbon Pro 98'),
	(26, 'Wilson Blade BLX'),
	(27, 'Protech MaxPower'),
	(28, 'Kawasaki Super Light 6800'),
	(29, 'Yang Yang Nano Sensation 700'),
	(30, 'GAN 356 XS'),
	(31, 'Moyu Weilong GTS3'),
	(32, 'Rubik\'s Speed Cube Pro Pack'),
	(33, 'YJ MGC Elite'),
	(34, 'QiYi Valk 3 Elite M'),
	(35, 'MoYu AoLong GT'),
	(36, 'Gan 11 M Pro'),
	(37, 'X-Man Tornado V2'),
	(38, 'Yuxin Little Magic'),
	(39, 'QiYi Warrior W'),
	(40, 'Life Fitness Hammer Strength Power Rack'),
	(41, 'Life Fitness G7 Home Gym'),
	(42, 'FitLine Adjustable Dumbbells'),
	(43, 'Protech Olympic Barbell'),
	(44, 'Cybex Plate Loaded Leg Press');

-- Dumping structure for table befit_db.model_has_brand
CREATE TABLE IF NOT EXISTS `model_has_brand` (
  `model_model_id` int NOT NULL,
  `brand_brand_id` int NOT NULL,
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `fk_model_has_brand_brand1_idx` (`brand_brand_id`),
  KEY `fk_model_has_brand_model1_idx` (`model_model_id`),
  CONSTRAINT `fk_model_has_brand_brand1` FOREIGN KEY (`brand_brand_id`) REFERENCES `brand` (`brand_id`),
  CONSTRAINT `fk_model_has_brand_model1` FOREIGN KEY (`model_model_id`) REFERENCES `model` (`model_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.model_has_brand: ~16 rows (approximately)
INSERT INTO `model_has_brand` (`model_model_id`, `brand_brand_id`, `id`) VALUES
	(7, 24, 1),
	(6, 16, 3),
	(1, 1, 5),
	(1, 2, 6),
	(2, 2, 7),
	(3, 1, 8),
	(1, 4, 9),
	(18, 19, 10),
	(19, 20, 11),
	(6, 24, 12),
	(11, 9, 13),
	(20, 13, 14),
	(5, 3, 15),
	(8, 4, 16),
	(20, 11, 17),
	(22, 3, 18),
	(3, 6, 19),
	(27, 5, 20),
	(21, 3, 21);

-- Dumping structure for table befit_db.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `order_status_id` int NOT NULL AUTO_INCREMENT,
  `order_status_name` varchar(25) NOT NULL,
  PRIMARY KEY (`order_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.order_status: ~4 rows (approximately)
INSERT INTO `order_status` (`order_status_id`, `order_status_name`) VALUES
	(1, 'order placed'),
	(2, 'order success'),
	(3, 'shipped'),
	(4, 'delivery success');

-- Dumping structure for table befit_db.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `qty` int DEFAULT NULL,
  `description` text,
  `title` varchar(100) DEFAULT NULL,
  `datetime_added` datetime DEFAULT NULL,
  `delivery_fee` double DEFAULT NULL,
  `category_cat_id` int NOT NULL,
  `model_has_brand_id` int NOT NULL,
  `condition_condition_id` int NOT NULL,
  `status_status_id` int NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `standard_shipping_fee_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_product_category1_idx` (`category_cat_id`),
  KEY `fk_product_model_has_brand1_idx` (`model_has_brand_id`),
  KEY `fk_product_condition1_idx` (`condition_condition_id`),
  KEY `fk_product_status1_idx` (`status_status_id`),
  KEY `fk_product_user1_idx` (`user_email`),
  KEY `fk_product_standard_shipping_fee1_idx` (`standard_shipping_fee_id`),
  CONSTRAINT `fk_product_category1` FOREIGN KEY (`category_cat_id`) REFERENCES `category` (`cat_id`),
  CONSTRAINT `fk_product_condition1` FOREIGN KEY (`condition_condition_id`) REFERENCES `condition` (`condition_id`),
  CONSTRAINT `fk_product_model_has_brand1` FOREIGN KEY (`model_has_brand_id`) REFERENCES `model_has_brand` (`id`),
  CONSTRAINT `fk_product_standard_shipping_fee1` FOREIGN KEY (`standard_shipping_fee_id`) REFERENCES `standard_shipping_fee` (`id`),
  CONSTRAINT `fk_product_status1` FOREIGN KEY (`status_status_id`) REFERENCES `status` (`status_id`),
  CONSTRAINT `fk_product_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.product: ~32 rows (approximately)
INSERT INTO `product` (`id`, `price`, `qty`, `description`, `title`, `datetime_added`, `delivery_fee`, `category_cat_id`, `model_has_brand_id`, `condition_condition_id`, `status_status_id`, `user_email`, `standard_shipping_fee_id`) VALUES
	(1, 2598, 8, 'dskjddskjdsk ljdkljdkjldfkj ldsflkjsdlkjsdlkjdslkjd slkjdlkjdslkjdsdkl jdfffffffffgfd lkjlfjlkdjgl kjgelrjrteroreojt gkldjldfjflkgj dfldfjlkjflkflgk jdfklfdjdfjflkjfg jfjgkfjglkfdjlkg jdfkgjfkertoretre toriutotureoitureoitr uotiuroi', 'Purple Resistance Band', '2024-02-24 11:20:23', 189, 1, 1, 2, 2, 'sarathmunasinghe07@gmail.com', 1),
	(2, 3999, 497, 'A versatile tool for bodyweight exercises and resistance training, suitable for all fitness levels.', 'Resistance Bands Set', '2024-04-07 09:00:00', 500, 1, 1, 1, 2, 'sarathmunasinghe07@gmail.com', 1),
	(3, 4999, 29, 'Take your calisthenics training to the next level with our very own 18 inch long wooden parallettes. ', 'Parallettes', '2024-04-07 09:05:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(4, 2999, 17, 'HIGHLY FLEXIBLE, DURABLE TO USE AND WITH REMOVABLE WEIGHTS TO SCALE TO YOUR TRAINING LEVELS AND GOALS. AVAILABLE IN 35LB AND 28LB.', 'Weight Vest', '2024-04-07 09:10:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(5, 2499, 36, 'Perfect for improving core strength and stability, suitable for a variety of exercises.', 'Suspension Trainer Kit', '2024-04-07 09:15:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(6, 3499, 24, 'Great for upper body workouts, helps tone and strengthen arms, chest, and shoulders.', 'Push-Up Bars', '2024-04-07 09:20:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(7, 1999, 35, 'Designed for enhancing grip strength and forearm muscles, suitable for pull-up and hanging exercises.', 'Pull-Up Bars', '2024-04-07 09:25:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(8, 5999, 15, 'A versatile tool for bodyweight exercises and resistance training, helps improve overall fitness.', 'Dip Bar', '2024-04-07 09:30:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(9, 4499, 10, 'Perfect for core strengthening exercises, helps improve balance and stability.', 'Exercise Ball', '2024-04-07 09:35:00', 143, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(10, 3999, 5, 'A versatile tool for bodyweight exercises and resistance training, suitable for home and gym use.', 'Multi-Grip Pull-Up Bar', '2024-04-07 09:40:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(11, 6999, 8, 'Designed for strengthening upper body muscles, suitable for various pulling exercises.', 'Doorway Pull-Up Bar', '2024-04-07 09:45:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(12, 6999, 8, 'Push up Stand Parallel Bars Parallettes 12x24 inch Non-Slip with Integrated Knurling Grip - Supports Strength HIIT Yoga ROM Gymnastics Body Conditioning Exercise Workouts', 'Push up Stand Parallel Bars', '2024-04-07 09:45:00', 500, 1, 1, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(13, 3999, 3, 'Amazon Basics Rubber Encased Exercise & Fitness Hex Dumbbell, Single, Hand Weight For Strength Training', 'Hex Dumbbell', '2024-02-24 11:20:23', 189, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(14, 2359, 5, 'Amazon Basics Rubber Encased Exercise & Fitness Hex Dumbbell, Single, Hand Weight For Strength Training', 'Dumbbells Rack', '2024-02-24 11:20:23', 139, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(15, 5479, 6, 'Amazon Basics Rubber Encased Exercise & Fitness Hex Dumbbell, Single, Hand Weight For Strength Training', 'Adjustable Dumbles Set', '2024-02-24 11:20:23', 100, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(16, 5469, 6, 'Adjustable Weight Bench, Bench Press Rack with Squat Rack, Leg Exercises Preacher Curl Rack for Home Gym Full Body Workout', 'Bench Press Rack with Squat Rack', '2024-02-24 11:20:23', 150, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(17, 2139, 10, 'Adjustable Soft Wrist Support Bracers Weight Lifting Gym Sports Wristband Carpal Protector Breathable Wrap Wristbands Band Strap', 'Wristband Band Strap', '2024-02-24 11:20:23', 109, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(18, 3258, 10, 'Adjustable Soft Wrist Support Bracers Weight Lifting Gym Sports Wristband Carpal Protector Breathable Wrap Wristbands Band Strap', 'Wrist Support Strap', '2024-02-24 11:20:23', 100, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(19, 2001, 10, 'Magnetic/Water Rowing Machines for Home, Compact and Saves Space - Vertical/Folding Storage, 350 LB Weight Capacity with Bluetooth App Supported, Tablet Holder and Comfortable Seat Cushion', 'Magnetic/Water Rowing machine', '2024-02-24 11:20:23', 189, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(20, 4359, 10, 'Battle Rope Battle Ropes for Exercise Workout Rope Exercise Rope Battle Ropes for Home Gym Heavy Ropes for Exercise Training Ropes for Working Out Weighted Workout Rope Exercise Workout Equipment', 'Battle Rope', '2024-02-24 11:20:23', 150, 2, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(21, 3249, 10, 'Textured EVA foam creates a non-slick surface Side finger scallops promote proper hand position Underside finger grooves for easy-gripping', 'Speedo Swim Training Kickboard Adult', '2024-02-24 11:20:23', 189, 3, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(22, 2159, 0, 'Inner micro grid texture provides more comfort and stretchy silicone won\'t snag or pull hair Contoured shape reduces drag for outstanding hydrodynamic performance', 'Speedo Swim Cap Silicone Elastomeric', '2024-02-24 11:20:23', 128, 3, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(23, 599, 0, 'High quality adult goggles for swimming lovers. Elastic nose bridge for perfect adaptability to a large number of facial proﬁles. Slightly inclined lenses to increase field of view. Lenses are shatterproof, anti-fog treated and offer UV ray protection.', 'Swimming Goggles', '2024-02-24 11:20:23', 129, 3, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(24, 2359, 10, 'Badminton Rackets Set of 6 for Outdoor Backyard Games, Including 6 Rackets, 6 Nylon Badminton Shuttlecocks, Lightweight Badminton Racquets', 'Badminton Racquets', '2024-02-24 11:20:23', 199, 4, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(25, 7659, 8, 'Badminton Birdies Shuttlecocks Goose Feather Nylon Pack of 12, Stable and Sturdy High Speed Shuttles for Indoor and Outdoor Training Sports', 'Badminton Shuttlecock', '2024-02-24 11:20:23', 200, 4, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(26, 3459, 10, 'WhiteFang Skateboards for Beginners, Complete Skateboard 31 x 7.88, 7 Layer Canadian Maple Double Kick Concave Standard and Tricks Skateboards ', 'Whitefang Skateboards', '2024-02-24 11:20:23', 289, 5, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(27, 2999, 10, 'Minecraft 31 inch Skateboard, 9-ply Maple Deck Skate Board for Cruising, Carving, Tricks and Downhill', 'Minecraft 31 inch Skateboard', '2024-02-24 11:20:23', 180, 5, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(28, 1999, 10, 'Magneto Complete Skateboard | 27.5" x 7.5" | 6-Layer Canadian Maple Double Kick Concave Deck | Kids Skateboard Cruiser Skateboard | Skateboard for Beginners, Teens & Adults', 'Magneto Skateboard', '2024-02-24 11:20:23', 250, 5, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(29, 2500, 44, 'Speed Cube 3x3x3 Stickerless with Cube Tutorial - Turning Speedly Smoothly Magic Cubes 3x3 Puzzle Game Brain Toy for Kids and Adult', 'Rubik Cube 3x3', '2024-02-24 11:20:23', 250, 6, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(30, 1599, 9, 'Speed Cube 4x4x4 Stickerless with Cube Tutorial - Turning Speedly Smoothly Magic Cubes 4x4 Puzzle Game Brain Toy for Kids and Adult', 'Rubik Cube 4x4', '2024-02-24 11:20:23', 189, 6, 1, 2, 1, 'sarathmunasinghe07@gmail.com', 1),
	(105, 2000, 99, 'GOTRAX Gotrax Edge Hoverboard for Kids Adults, 6.5" Tires 6.2mph & 2.5 Miles Self Balancing ', 'Hoverboard Black', '2024-06-24 14:54:46', 200, 5, 10, 1, 1, 'sarathmunasinghe07@gmail.com', 1),
	(118, 4000, 33, 'Badminton for professionals.', 'Badminton code 67', '2025-02-24 05:03:54', 22, 4, 21, 2, 1, 'd@gm.com', 1);

-- Dumping structure for table befit_db.product_has_color
CREATE TABLE IF NOT EXISTS `product_has_color` (
  `product_id` int NOT NULL,
  `color_clr_id` int NOT NULL,
  KEY `fk_product_has_color_color1_idx` (`color_clr_id`),
  KEY `fk_product_has_color_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_has_color_color1` FOREIGN KEY (`color_clr_id`) REFERENCES `color` (`clr_id`),
  CONSTRAINT `fk_product_has_color_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.product_has_color: ~35 rows (approximately)
INSERT INTO `product_has_color` (`product_id`, `color_clr_id`) VALUES
	(1, 4),
	(2, 4),
	(26, 4),
	(27, 4),
	(28, 4),
	(29, 4),
	(30, 4),
	(3, 4),
	(4, 3),
	(5, 4),
	(6, 4),
	(7, 4),
	(8, 4),
	(9, 4),
	(10, 4),
	(11, 4),
	(12, 4),
	(13, 4),
	(14, 4),
	(15, 4),
	(16, 4),
	(17, 4),
	(18, 4),
	(19, 4),
	(20, 4),
	(21, 4),
	(22, 4),
	(23, 4),
	(24, 4),
	(25, 4),
	(105, 4),
	(105, 11),
	(118, 2);

-- Dumping structure for table befit_db.product_img
CREATE TABLE IF NOT EXISTS `product_img` (
  `img_path` varchar(100) NOT NULL,
  `product_id` int NOT NULL,
  PRIMARY KEY (`img_path`),
  KEY `fk_product_img_product1_idx` (`product_id`),
  CONSTRAINT `fk_product_img_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.product_img: ~33 rows (approximately)
INSERT INTO `product_img` (`img_path`, `product_id`) VALUES
	('../public/images/product_images/product1.jpeg', 1),
	('../public/images/product_images/product2.jpeg', 2),
	('../public/images/product_images/product3.jpeg', 3),
	('../public/images/product_images/product4.jpeg', 4),
	('../public/images/product_images/product5.jpeg', 5),
	('../public/images/product_images/product6.jpeg', 6),
	('../public/images/product_images/product7.jpeg', 7),
	('../public/images/product_images/product8.jpeg', 8),
	('../public/images/product_images/product9.jpeg', 9),
	('../public/images/product_images/product10.jpeg', 10),
	('../public/images/product_images/product11.jpeg', 11),
	('../public/images/product_images/product12.jpeg', 12),
	('../public/images/product_images/product13.jpeg', 13),
	('../public/images/product_images/product14.jpeg', 14),
	('../public/images/product_images/product15.jpeg', 15),
	('../public/images/product_images/product16.jpeg', 16),
	('../public/images/product_images/product17.jpeg', 17),
	('../public/images/product_images/product18.jpeg', 18),
	('../public/images/product_images/product19.jpeg', 19),
	('../public/images/product_images/product20.jpeg', 20),
	('../public/images/product_images/product21.jpeg', 21),
	('../public/images/product_images/product22.jpeg', 22),
	('../public/images/product_images/product23.jpeg', 23),
	('../public/images/product_images/product24.jpeg', 24),
	('../public/images/product_images/product25.jpeg', 25),
	('../public/images/product_images/product26.jpeg', 26),
	('../public/images/product_images/product27.jpeg', 27),
	('../public/images/product_images/product28.jpeg', 28),
	('../public/images/product_images/product29.jpeg', 29),
	('../public/images/product_images/product30.jpeg', 30),
	('../public/images/product_images/Hoverboard Black066793b5e2363d.jpeg', 105),
	('../public/images/product_images/Hoverboard Black166793b5e2537d.jpeg', 105),
	('../public/images/product_images/Hoverboard Black266793b5e26548.jpeg', 105),
	('../public/images/product_images/Badminton code 67067bbb0630759e.jpeg', 118),
	('../public/images/product_images/Badminton code 67167bbb06309c60.jpeg', 118),
	('../public/images/product_images/Badminton code 67267bbb0630bad4.jpeg', 118);

-- Dumping structure for table befit_db.profile_img
CREATE TABLE IF NOT EXISTS `profile_img` (
  `id` int NOT NULL AUTO_INCREMENT,
  `path` varchar(100) DEFAULT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_profile_img_user1_idx` (`user_email`),
  CONSTRAINT `fk_profile_img_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.profile_img: ~2 rows (approximately)
INSERT INTO `profile_img` (`id`, `path`, `user_email`) VALUES
	(3, '../resources/profile_images/Sarathf_67b4c6dade84a.jpeg', 'sarathmunasinghe07@gmail.com'),
	(7, '../resources/profile_images/fkdir_67b4d3766f8e4.jpeg', 'email@fkdlk.com'),
	(8, '../resources/profile_images/Super_67bbaf1f10101.jpeg', 'd@gm.com');

-- Dumping structure for table befit_db.province
CREATE TABLE IF NOT EXISTS `province` (
  `province_id` int NOT NULL AUTO_INCREMENT,
  `province_name` varchar(45) DEFAULT NULL,
  `country_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`province_id`),
  KEY `fk_province_country1_idx` (`country_id`),
  CONSTRAINT `fk_province_country1` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.province: ~9 rows (approximately)
INSERT INTO `province` (`province_id`, `province_name`, `country_id`) VALUES
	(1, 'Andhra Pradesh', 2),
	(2, 'Arunachal Pradesh', 2),
	(3, 'Central Province', 1),
	(4, 'Eastern Province', 1),
	(5, 'North Central Province', 1),
	(6, 'Northern Province', 1),
	(7, 'North Western Province', 1),
	(8, 'Sabaragamuwa Province', 1),
	(9, 'Southern Province', 1),
	(10, 'Uva Province', 1),
	(11, 'Western Province', 1),
	(12, 'Andhra Pradesh', 2),
	(13, 'Arunachal Pradesh', 2),
	(14, 'Assam', 2),
	(15, 'Bihar', 2),
	(16, 'Chhattisgarh', 2),
	(17, 'Goa', 2),
	(18, 'Gujarat', 2),
	(19, 'Haryana', 2),
	(20, 'Himachal Pradesh', 2),
	(21, 'Jharkhand', 2),
	(22, 'Karnataka', 2),
	(23, 'Kerala', 2),
	(24, 'Madhya Pradesh', 2),
	(25, 'Maharashtra', 2),
	(26, 'Manipur', 2),
	(27, 'Meghalaya', 2),
	(28, 'Mizoram', 2),
	(29, 'Nagaland', 2),
	(30, 'Odisha', 2),
	(31, 'Punjab', 2),
	(32, 'Rajasthan', 2),
	(33, 'Sikkim', 2),
	(34, 'Tamil Nadu', 2),
	(35, 'Telangana', 2),
	(36, 'Tripura', 2),
	(37, 'Uttar Pradesh', 2),
	(38, 'Uttarakhand', 2),
	(39, 'West Bengal', 2),
	(40, 'Andaman and Nicobar Islands', 2),
	(41, 'Chandigarh', 2),
	(42, 'Dadra and Nagar Haveli and Daman and Diu', 2),
	(43, 'Delhi', 2),
	(44, 'Lakshadweep', 2),
	(45, 'Puducherry', 2),
	(46, 'Badakhshan', 3),
	(47, 'Badghis', 3),
	(48, 'Baghlan', 3),
	(49, 'Balkh', 3),
	(50, 'Bamyan', 3),
	(51, 'Daykundi', 3),
	(52, 'Farah', 3),
	(53, 'Faryab', 3),
	(54, 'Ghazni', 3),
	(55, 'Ghor', 3),
	(56, 'Helmand', 3),
	(57, 'Herat', 3),
	(58, 'Jowzjan', 3),
	(59, 'Kabul', 3),
	(60, 'Kandahar', 3),
	(61, 'Kapisa', 3),
	(62, 'Barisal', 4),
	(63, 'Chittagong', 4),
	(64, 'Dhaka', 4),
	(65, 'Khulna', 4),
	(66, 'Mymensingh', 4),
	(67, 'Rajshahi', 4),
	(68, 'Rangpur', 4),
	(69, 'Sylhet', 4),
	(70, 'Bumthang', 5),
	(71, 'Chukha', 5),
	(72, 'Dagana', 5),
	(73, 'Gasa', 5),
	(74, 'Haa', 5),
	(75, 'Lhuntse', 5),
	(76, 'Mongar', 5),
	(77, 'Paro', 5),
	(78, 'Pemagatshel', 5),
	(79, 'Punakha', 5),
	(80, 'Samdrup Jongkhar', 5),
	(81, 'Samtse', 5),
	(82, 'Sarpang', 5),
	(83, 'Thimphu', 5),
	(84, 'Trashigang', 5),
	(85, 'Trashiyangtse', 5),
	(86, 'Trongsa', 5),
	(87, 'Tsirang', 5),
	(88, 'Wangdue Phodrang', 5),
	(89, 'Zhemgang', 5),
	(90, 'Alif Alif', 6),
	(91, 'Alif Dhaal', 6),
	(92, 'Baa', 6),
	(93, 'Dhaal', 6),
	(94, 'Faafu', 6),
	(95, 'Gaafu Alif', 6),
	(96, 'Gaafu Dhaal', 6),
	(97, 'Gnaviyani', 6),
	(98, 'Haa Alif', 6),
	(99, 'Haa Dhaal', 6),
	(100, 'Kaafu', 6),
	(101, 'Laamu', 6),
	(102, 'Lhaviyani', 6),
	(103, 'Malé', 6),
	(104, 'Meemu', 6),
	(105, 'Noonu', 6),
	(106, 'Raa', 6),
	(107, 'Seenu', 6),
	(108, 'Shaviyani', 6),
	(109, 'Thaa', 6),
	(110, 'Vaavu', 6);

-- Dumping structure for table befit_db.recent
CREATE TABLE IF NOT EXISTS `recent` (
  `r_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `user_email` varchar(100) NOT NULL,
  PRIMARY KEY (`r_id`),
  KEY `fk_recent_product1_idx` (`product_id`),
  KEY `fk_recent_user1_idx` (`user_email`),
  CONSTRAINT `fk_recent_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_recent_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.recent: ~0 rows (approximately)

-- Dumping structure for table befit_db.standard_shipping_fee
CREATE TABLE IF NOT EXISTS `standard_shipping_fee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shipping_fee` double NOT NULL DEFAULT (100),
  `country_id` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_standard_shipping_fee_country1_idx` (`country_id`),
  CONSTRAINT `fk_standard_shipping_fee_country1` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.standard_shipping_fee: ~8 rows (approximately)
INSERT INTO `standard_shipping_fee` (`id`, `shipping_fee`, `country_id`) VALUES
	(1, 100, 1),
	(2, 200, 2),
	(3, 300, 3),
	(4, 400, 4),
	(5, 600, 5),
	(6, 100, 6),
	(7, 150, 7),
	(8, 250, 8);

-- Dumping structure for table befit_db.status
CREATE TABLE IF NOT EXISTS `status` (
  `status_id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.status: ~2 rows (approximately)
INSERT INTO `status` (`status_id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Inactive');

-- Dumping structure for table befit_db.user
CREATE TABLE IF NOT EXISTS `user` (
  `fname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `lname` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `password` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `mobile` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `joined_date` datetime NOT NULL,
  `verification_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `gender_gender_id` int DEFAULT NULL,
  `status_status_id` int NOT NULL,
  PRIMARY KEY (`email`),
  KEY `fk_user_gender_idx` (`gender_gender_id`) USING BTREE,
  KEY `fk_user_status1_idx` (`status_status_id`) USING BTREE,
  CONSTRAINT `fk_user_gender` FOREIGN KEY (`gender_gender_id`) REFERENCES `gender` (`gender_id`),
  CONSTRAINT `fk_user_status1` FOREIGN KEY (`status_status_id`) REFERENCES `status` (`status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.user: ~13 rows (approximately)
INSERT INTO `user` (`fname`, `lname`, `email`, `password`, `mobile`, `joined_date`, `verification_code`, `gender_gender_id`, `status_status_id`) VALUES
	('cohics', 'colorus', 'cohicis586@coloruz.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233610', '2024-09-08 19:09:35', '8098klj', 1, 1),
	('Super', 'Man', 'd@gm.com', '$2y$10$S/EP3wMFQ2sA4MI0Hvzg5uAEwHrYbDhNPvoBHfL1EvzydiRas9OjW', '0718233622', '2025-02-24 04:53:26', NULL, 1, 1),
	('K', 'dammika', 'email@fkdlk.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233611', '2024-02-05 14:52:12', 'verified', 1, 1),
	('Kkarthik', 'naren', 'feroj41049@devncie.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233612', '2024-06-28 10:49:33', NULL, 1, 1),
	('upendra', 'narendra', 'krishanthan2022.4.4@gmail.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233613', '2024-01-24 10:50:40', '667d457878d6c', 1, 1),
	('yyash', 'kupendra', 'lifaso6961@devncie.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233614', '2024-03-28 07:58:08', NULL, 1, 1),
	('mazz', 'abdul', 'm@g.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233615', '2025-02-20 13:18:51', NULL, 1, 2),
	('senigomary', 'mia', 'sarathmunasinghe07@gmail.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233616', '2024-04-22 22:24:04', '6678fbc4976ab', 1, 1),
	('tamil', 'selvan', 'sarathmunasinghe08@gmail.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233617', '2025-02-15 23:19:07', NULL, 1, 1),
	('ttf', 'vaasan', 'sarathmunasinghe09@gmail.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233618', '2025-02-15 23:20:01', NULL, 1, 1),
	('muthu', 'latha', 'testCustomer@gmail.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233619', '2025-02-23 16:00:51', NULL, 2, 1),
	('kasun', 'bandara', 'wohiga2963@joeroc.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233620', '2024-06-28 12:03:42', NULL, 1, 2),
	('supun', 'charith', 'yilib46895@cutxsew.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233621', '2024-06-28 08:32:04', NULL, 1, 1),
	('deshan', 'senevirathna', 'yolixac932@joeroc.com', '$2y$10$vjHCWL9yqx9kNfpm5OLrOuoORj.f5tIcSr5sGj8zhKELtou4bF8sG', '0758233622', '2024-05-28 08:20:22', '667e24f75656f', 1, 1);

-- Dumping structure for table befit_db.user_has_address
CREATE TABLE IF NOT EXISTS `user_has_address` (
  `user_email` varchar(100) NOT NULL,
  `city_city_id` int NOT NULL,
  `address_id` int NOT NULL AUTO_INCREMENT,
  `line1` text,
  `line2` text,
  `postal_code` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`address_id`),
  KEY `fk_user_has_city_city1_idx` (`city_city_id`),
  KEY `fk_user_has_city_user1_idx` (`user_email`),
  CONSTRAINT `fk_user_has_city_city1` FOREIGN KEY (`city_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `fk_user_has_city_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.user_has_address: ~9 rows (approximately)
INSERT INTO `user_has_address` (`user_email`, `city_city_id`, `address_id`, `line1`, `line2`, `postal_code`) VALUES
	('email@fkdlk.com', 1, 3, 'Temple Street', 'Main Road', '445'),
	('sarathmunasinghe07@gmail.com', 83, 4, 'TTF Road', 'Main Road', ''),
	('email@fkdlk.com', 105, 6, 'AddressLine1', 'AddressLine2', '1102'),
	('krishanthan2022.4.4@gmail.com', 8, 7, 'AddressLine1', 'AddressLine2', '1101'),
	('lifaso6961@devncie.com', 100, 8, 'AddressLine2', 'AddressLine2', '1103'),
	('yolixac932@joeroc.com', 126, 9, 'AddressLine3', 'AddressLine3', '2001'),
	('feroj41049@devncie.com', 74, 10, 'AddressLine4', 'AddressLine4', '1103'),
	('email@fkdlk.com', 100, 11, 'Temple Street', 'Main Road', NULL),
	('email@fkdlk.com', 30, 12, 'Temple Street', 'Main Road', NULL),
	('m@g.com', 77, 13, 'd', 'd', NULL),
	('d@gm.com', 77, 14, 'kripton road', 'galaxy 4', NULL);

-- Dumping structure for table befit_db.wishlist
CREATE TABLE IF NOT EXISTS `wishlist` (
  `wId` int NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) NOT NULL,
  `product_id` int NOT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`wId`) USING BTREE,
  KEY `fk_watchlist_user1_idx` (`user_email`),
  KEY `fk_watchlist_product1_idx` (`product_id`),
  CONSTRAINT `fk_watchlist_product1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `fk_watchlist_user1` FOREIGN KEY (`user_email`) REFERENCES `user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table befit_db.wishlist: ~14 rows (approximately)
INSERT INTO `wishlist` (`wId`, `user_email`, `product_id`, `qty`) VALUES
	(74, 'krishanthan2022.4.4@gmail.com', 3, 1),
	(75, 'krishanthan2022.4.4@gmail.com', 4, 1),
	(76, 'krishanthan2022.4.4@gmail.com', 5, 1),
	(77, 'lifaso6961@devncie.com', 105, 1),
	(78, 'yolixac932@joeroc.com', 5, 1),
	(98, 'sarathmunasinghe07@gmail.com', 105, 1),
	(100, 'm@g.com', 11, 1),
	(101, 'm@g.com', 9, 1),
	(103, 'sarathmunasinghe07@gmail.com', 10, 1),
	(104, 'sarathmunasinghe07@gmail.com', 9, 1),
	(105, 'sarathmunasinghe07@gmail.com', 6, 1),
	(106, 'sarathmunasinghe07@gmail.com', 2, 1),
	(107, 'd@gm.com', 105, 3);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
