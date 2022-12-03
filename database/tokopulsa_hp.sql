-- phpMyAdmin SQL Dump
-- version 5.3.0-dev+20221029.4bd3a1c423
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 03, 2022 at 11:31 AM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tokopulsa_hp`
--

-- --------------------------------------------------------

--
-- Table structure for table `daftarharga`
--

CREATE TABLE `daftarharga` (
  `kodenomer` varchar(50) NOT NULL,
  `kodepulsa` varchar(20) NOT NULL,
  `hargasaldo` varchar(20) NOT NULL,
  `hargajual` varchar(20) NOT NULL,
  `income` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `daftarharga`
--

INSERT INTO `daftarharga` (`kodenomer`, `kodepulsa`, `hargasaldo`, `hargajual`, `income`) VALUES
('KN001', 'XL-5K', '5000', '6000', '1000'),
('KN002', 'AXIS-5K', '5000', '6500', '1500'),
('KN003', 'TSEL-5K', '5000', '7500', '2500');

-- --------------------------------------------------------

--
-- Table structure for table `stoksaldo`
--

CREATE TABLE `stoksaldo` (
  `pinsaldo` varchar(10) NOT NULL,
  `totalsaldo` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stoksaldo`
--

INSERT INTO `stoksaldo` (`pinsaldo`, `totalsaldo`) VALUES
('sal1234', 995000);

-- --------------------------------------------------------

--
-- Table structure for table `transaksipulsa`
--

CREATE TABLE `transaksipulsa` (
  `kodetransaksi` varchar(50) NOT NULL,
  `nomer` varchar(20) NOT NULL,
  `tanggal` varchar(20) NOT NULL,
  `kodepulsa` varchar(20) NOT NULL,
  `hargasaldo` varchar(20) NOT NULL,
  `hargajual` varchar(20) NOT NULL,
  `saldoawal` varchar(50) NOT NULL,
  `sisasaldo` varchar(50) NOT NULL,
  `income` varchar(50) NOT NULL,
  `pinsaldo` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksipulsa`
--

INSERT INTO `transaksipulsa` (`kodetransaksi`, `nomer`, `tanggal`, `kodepulsa`, `hargasaldo`, `hargajual`, `saldoawal`, `sisasaldo`, `income`, `pinsaldo`) VALUES
('KT001', '08123678712', '2022-11-10', 'AXIS-5K', '5000', '6500', '494000', '489000', '1500', 'sal1234'),
('KT002', '082345233', '2022-11-11', 'TSEL-5K', '5000', '7000', '484000', '479000', '2000', 'sal1234');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`) VALUES
('admin', 'admin'),
('indra', 'maulana');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `daftarharga`
--
ALTER TABLE `daftarharga`
  ADD PRIMARY KEY (`kodenomer`),
  ADD KEY `u_hs` (`kodepulsa`) USING BTREE;

--
-- Indexes for table `stoksaldo`
--
ALTER TABLE `stoksaldo`
  ADD PRIMARY KEY (`pinsaldo`);

--
-- Indexes for table `transaksipulsa`
--
ALTER TABLE `transaksipulsa`
  ADD PRIMARY KEY (`kodetransaksi`),
  ADD KEY `u_hs` (`kodepulsa`),
  ADD KEY `p_s` (`pinsaldo`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`) USING BTREE;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksipulsa`
--
ALTER TABLE `transaksipulsa`
  ADD CONSTRAINT `transaksipulsa_ibfk_1` FOREIGN KEY (`kodepulsa`) REFERENCES `daftarharga` (`kodepulsa`),
  ADD CONSTRAINT `transaksipulsa_ibfk_2` FOREIGN KEY (`pinsaldo`) REFERENCES `stoksaldo` (`pinsaldo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
