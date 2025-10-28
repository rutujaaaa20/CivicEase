-- ==========================================================
-- 🏙️ CivicEase Database Schema
-- ==========================================================

-- Drop existing tables (in correct order to avoid FK issues)
DROP TABLE IF EXISTS `report_technician`;
DROP TABLE IF EXISTS `reports`;
DROP TABLE IF EXISTS `technicians`;
DROP TABLE IF EXISTS `departments`;
DROP TABLE IF EXISTS `citizens`;

-- ==========================================================
-- 👤 Citizens
-- ==========================================================
CREATE TABLE `citizens` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(200),
  `created_at` DATETIME(6) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_citizens_email` (`email`)
) ENGINE=InnoDB;

-- ==========================================================
-- 🏢 Departments
-- ==========================================================
CREATE TABLE `departments` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contact_email` VARCHAR(100),
  `contact_phone` VARCHAR(20),
  `created_at` DATETIME(6) NOT NULL,
  `description` VARCHAR(500),
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_departments_name` (`name`)
) ENGINE=InnoDB;

-- ==========================================================
-- 🧰 Technicians
-- ==========================================================
CREATE TABLE `technicians` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `available` BIT NOT NULL,
  `created_at` DATETIME(6) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(20),
  `specialization` VARCHAR(100),
  `department_id` BIGINT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_technicians_email` (`email`),
  CONSTRAINT `FK_technicians_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ==========================================================
-- 📝 Reports
-- ==========================================================
CREATE TABLE `reports` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NOT NULL,
  `description` TEXT NOT NULL,
  `location` VARCHAR(300) NOT NULL,
  `priority` ENUM('LOW','MEDIUM','HIGH','CRITICAL') NOT NULL,
  `status` ENUM('NEW','IN_PROGRESS','RESOLVED','CLOSED','CANCELLED') NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `updated_at` DATETIME(6),
  `citizen_id` BIGINT,
  `department_id` BIGINT,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_reports_citizen` FOREIGN KEY (`citizen_id`) REFERENCES `citizens` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FK_reports_department` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ==========================================================
-- 🔗 Report-Technician Mapping (Many-to-Many)
-- ==========================================================
CREATE TABLE `report_technician` (
  `report_id` BIGINT NOT NULL,
  `technician_id` BIGINT NOT NULL,
  PRIMARY KEY (`report_id`, `technician_id`),
  CONSTRAINT `FK_rt_report` FOREIGN KEY (`report_id`) REFERENCES `reports` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_rt_technician` FOREIGN KEY (`technician_id`) REFERENCES `technicians` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB;
