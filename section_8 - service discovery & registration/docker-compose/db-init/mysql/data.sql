-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               9.1.0 - MySQL Community Server - GPL
-- Server OS:                    Win64
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

-- Dumping data for table banking.accounts: ~2 rows (approximately)
INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES
	(1, 1, 'Savings', '123 Main St, City A', '2024-10-01', 'admin', NULL, NULL),
	(2, 3, 'Checking', '456 Central Ave, City B', '2024-10-02', 'admin', NULL, NULL);

-- Dumping data for table banking.cards: ~3 rows (approximately)
INSERT INTO `cards` (`card_id`, `mobile_number`, `card_number`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES
	(1, '1234567890', 'CRD001', 'Credit Card', 50000, 15000, 35000, '2024-10-01', 'admin', NULL, NULL),
	(2, '0987654321', 'CRD002', 'Debit Card', 30000, 5000, 25000, '2024-10-02', 'admin', NULL, NULL),
	(3, '1234567890', 'CRD003', 'Credit Card', 70000, 30000, 40000, '2024-10-03', 'admin', NULL, NULL);

-- Dumping data for table banking.customer: ~2 rows (approximately)
INSERT INTO `customer` (`customer_id`, `name`, `email`, `mobile_number`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES
	(1, 'Alice Nguyen', 'alice@example.com', '1234567890', '2024-10-01', 'admin', NULL, NULL),
	(2, 'Bob Tran', 'bob@example.com', '0987654321', '2024-10-02', 'admin', NULL, NULL);

-- Dumping data for table banking.loans: ~23 rows (approximately)
INSERT INTO `loans` (`loan_id`, `mobile_number`, `loan_number`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES
	(1, '1234567890', 'LN001', 'Personal Loan', 50000, 10000, 40000, '2024-10-01', 'admin', NULL, NULL),
	(2, '0987654321', 'LN002', 'Home Loan', 200000, 50000, 150000, '2024-10-02', 'admin', NULL, NULL),
	(4, '0987654321', 'LN001', 'Personal Loan', 50000, 20000, 30000, '2024-01-10', 'admin', '2024-02-01', 'admin'),
	(5, '0912345678', 'LN002', 'Mortgage', 150000, 50000, 100000, '2024-01-15', 'admin', '2024-03-01', 'admin'),
	(6, '0976543210', 'LN003', 'Education Loan', 70000, 30000, 40000, '2024-01-20', 'admin', NULL, NULL),
	(7, '0901234567', 'LN004', 'Car Loan', 120000, 60000, 60000, '2024-01-25', 'admin', NULL, NULL),
	(8, '0981122334', 'LN005', 'Business Loan', 250000, 100000, 150000, '2024-02-01', 'admin', NULL, NULL),
	(9, '0955432109', 'LN006', 'Personal Loan', 60000, 25000, 35000, '2024-02-05', 'admin', NULL, NULL),
	(10, '0932145678', 'LN007', 'Education Loan', 80000, 30000, 50000, '2024-02-10', 'admin', '2024-03-05', 'admin'),
	(11, '0912345670', 'LN008', 'Mortgage', 180000, 70000, 110000, '2024-02-15', 'admin', NULL, NULL),
	(12, '0987654322', 'LN009', 'Car Loan', 100000, 50000, 50000, '2024-02-20', 'admin', '2024-03-10', 'admin'),
	(13, '0971234567', 'LN010', 'Business Loan', 300000, 100000, 200000, '2024-02-25', 'admin', NULL, NULL),
	(14, '0923456789', 'LN011', 'Personal Loan', 75000, 30000, 45000, '2024-03-01', 'admin', NULL, NULL),
	(15, '0945678901', 'LN012', 'Education Loan', 85000, 40000, 45000, '2024-03-05', 'admin', '2024-03-15', 'admin'),
	(16, '0967890123', 'LN013', 'Mortgage', 200000, 120000, 80000, '2024-03-10', 'admin', NULL, NULL),
	(17, '0989012345', 'LN014', 'Car Loan', 95000, 45000, 50000, '2024-03-15', 'admin', NULL, NULL),
	(18, '0911112233', 'LN015', 'Business Loan', 270000, 120000, 150000, '2024-03-20', 'admin', NULL, NULL),
	(19, '0931234567', 'LN016', 'Personal Loan', 64000, 24000, 40000, '2024-03-25', 'admin', NULL, NULL),
	(20, '0976543201', 'LN017', 'Education Loan', 93000, 38000, 55000, '2024-03-30', 'admin', '2024-04-01', 'admin'),
	(21, '0909876543', 'LN018', 'Mortgage', 190000, 90000, 100000, '2024-04-05', 'admin', NULL, NULL),
	(22, '0987654323', 'LN019', 'Car Loan', 110000, 60000, 50000, '2024-04-10', 'admin', '2024-04-15', 'admin'),
	(23, '0965432109', 'LN020', 'Business Loan', 320000, 150000, 170000, '2024-04-15', 'admin', NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
