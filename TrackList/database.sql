-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema GroupProject
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema GroupProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `GroupProject` DEFAULT CHARACTER SET latin1 ;
USE `GroupProject` ;

-- -----------------------------------------------------
-- Table `GroupProject`.`MyWorklists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GroupProject`.`MyWorklists` (
  `Username` VARCHAR(50) NOT NULL,
  `Task` VARCHAR(100) NOT NULL,
  `ParentList` VARCHAR(50) NOT NULL,
  `TaskDeadlineYear` INT(4) NOT NULL,
  `TaskDeadlineMonth` INT(2) NOT NULL,
  `TaskDeadlineDay` INT(2) NOT NULL,
  `TaskDeadlineHour` INT(2) NOT NULL,
  `TaskDeadlineMinute` INT(2) NOT NULL DEFAULT '0',
  `Completed` TINYINT(1) NOT NULL DEFAULT '0')
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GroupProject`.`Notifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GroupProject`.`Notifications` (
  `Notification` VARCHAR(300) NOT NULL,
  `MarkRead` TINYINT(1) NOT NULL DEFAULT '0',
  `AssignedTo` VARCHAR(50) NOT NULL,
  `Index` INT(2) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Index`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GroupProject`.`ToDoListMembers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GroupProject`.`ToDoListMembers` (
  `ParentList` VARCHAR(50) NOT NULL,
  `Username` VARCHAR(50) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GroupProject`.`ToDoLists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GroupProject`.`ToDoLists` (
  `ParentList` VARCHAR(50) NOT NULL,
  `Task` VARCHAR(100) NOT NULL,
  `TaskDeadlineYear` INT(4) NOT NULL,
  `TaskDeadlineMonth` INT(2) NOT NULL,
  `TaskDeadlineDay` INT(2) NOT NULL,
  `TaskDeadlineHour` INT(2) NOT NULL,
  `TaskDeadlineMinute` VARCHAR(2) NOT NULL DEFAULT '00',
  `Completed` TINYINT(1) NOT NULL DEFAULT '0',
  `AssignedBy` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`ParentList`, `Task`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `GroupProject`.`UserAccounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `GroupProject`.`UserAccounts` (
  `Username` VARCHAR(50) NOT NULL,
  `Password` VARCHAR(50) NOT NULL,
  `FirstName` VARCHAR(50) NOT NULL,
  `LastName` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE INDEX `idUserAccounts_UNIQUE` (`Username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
