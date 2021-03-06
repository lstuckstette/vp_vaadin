-- MySQL Script generated by MySQL Workbench
-- Mon Jul  3 13:59:01 2017
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Subject` (
  `idSubject` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(45) NULL,
  PRIMARY KEY (`idSubject`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Room` (
  `idRoom` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `abbreviation` VARCHAR(45) NULL,
  PRIMARY KEY (`idRoom`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Event`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Event` (
  `idEvent` INT NOT NULL AUTO_INCREMENT,
  `eventName` VARCHAR(45) NULL,
  `startTime` TIME NOT NULL,
  `endTime` TIME NOT NULL,
  `date` DATE NULL,
  `roomFK` INT NOT NULL,
  PRIMARY KEY (`idEvent`),
  INDEX `fk_Event_Room1_idx` (`roomFK` ASC),
  CONSTRAINT `fk_Event_Room1`
    FOREIGN KEY (`roomFK`)
    REFERENCES `mydb`.`Room` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Lesson`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Lesson` (
  `idClass` INT NOT NULL AUTO_INCREMENT,
  `subjectFK` INT NOT NULL,
  `eventFK` INT NOT NULL,
  PRIMARY KEY (`idClass`),
  INDEX `fk_Class_Subject1_idx` (`subjectFK` ASC),
  INDEX `fk_Lesson_Event1_idx` (`eventFK` ASC),
  CONSTRAINT `fk_Class_Subject1`
    FOREIGN KEY (`subjectFK`)
    REFERENCES `mydb`.`Subject` (`idSubject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lesson_Event1`
    FOREIGN KEY (`eventFK`)
    REFERENCES `mydb`.`Event` (`idEvent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Class` (
  `idClass` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `lessonFK` INT NOT NULL,
  PRIMARY KEY (`idClass`),
  INDEX `fk_Class_Lesson1_idx` (`lessonFK` ASC),
  CONSTRAINT `fk_Class_Lesson1`
    FOREIGN KEY (`lessonFK`)
    REFERENCES `mydb`.`Lesson` (`idClass`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Student` (
  `idStudent` INT NOT NULL AUTO_INCREMENT,
  `enrollmentDate` DATE NULL,
  `classFK` INT NOT NULL,
  PRIMARY KEY (`idStudent`),
  INDEX `fk_Student_Class1_idx` (`classFK` ASC),
  CONSTRAINT `fk_Student_Class1`
    FOREIGN KEY (`classFK`)
    REFERENCES `mydb`.`Class` (`idClass`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Teacher` (
  `idTeacher` INT NOT NULL AUTO_INCREMENT,
  `hireDate` DATE NULL,
  `lessonFK` INT NULL,
  PRIMARY KEY (`idTeacher`),
  INDEX `fk_Teacher_Lesson1_idx` (`lessonFK` ASC),
  CONSTRAINT `fk_Teacher_Lesson1`
    FOREIGN KEY (`lessonFK`)
    REFERENCES `mydb`.`Lesson` (`idClass`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Staff` (
  `idStaff` INT NOT NULL AUTO_INCREMENT,
  `hireDate` DATE NULL,
  PRIMARY KEY (`idStaff`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Role` (
  `idRole` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `role_UNIQUE` (`role` ASC),
  PRIMARY KEY (`idRole`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User_Setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User_Setting` (
  `idUser_Settings` INT NOT NULL AUTO_INCREMENT,
  `option1` TINYINT ZEROFILL NULL,
  `option2` TINYINT ZEROFILL NULL,
  `option3` TINYINT ZEROFILL NULL,
  PRIMARY KEY (`idUser_Settings`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `idUserEmail` VARCHAR(255) NOT NULL,
  `notificationEmail` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `create_time` TIMESTAMP NULL,
  `roleFK` INT NOT NULL,
  `usersettingFK` INT NOT NULL,
  PRIMARY KEY (`idUserEmail`),
  INDEX `fk_User_Role1_idx` (`roleFK` ASC),
  INDEX `fk_User_User_Settings1_idx` (`usersettingFK` ASC),
  CONSTRAINT `fk_User_Role1`
    FOREIGN KEY (`roleFK`)
    REFERENCES `mydb`.`Role` (`idRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_User_Settings1`
    FOREIGN KEY (`usersettingFK`)
    REFERENCES `mydb`.`User_Setting` (`idUser_Settings`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Person` (
  `idPerson` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `birthdate` DATE NULL,
  `address` VARCHAR(255) NULL,
  `postalCode` INT NULL,
  `city` VARCHAR(255) NULL,
  `gender` ENUM('M', 'F', 'O') NULL,
  `teacherFK` INT NULL,
  `staffFK` INT NULL,
  `studentFK` INT NULL,
  `userFK` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idPerson`),
  INDEX `fk_Person_Teacher1_idx` (`teacherFK` ASC),
  INDEX `fk_Person_Staff1_idx` (`staffFK` ASC),
  INDEX `fk_Person_Student1_idx` (`studentFK` ASC),
  INDEX `fk_Person_User1_idx` (`userFK` ASC),
  CONSTRAINT `fk_Person_Teacher1`
    FOREIGN KEY (`teacherFK`)
    REFERENCES `mydb`.`Teacher` (`idTeacher`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Staff1`
    FOREIGN KEY (`staffFK`)
    REFERENCES `mydb`.`Staff` (`idStaff`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_Student1`
    FOREIGN KEY (`studentFK`)
    REFERENCES `mydb`.`Student` (`idStudent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Person_User1`
    FOREIGN KEY (`userFK`)
    REFERENCES `mydb`.`User` (`idUserEmail`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Room_Feature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Room_Feature` (
  `idRoom_Feature` INT NOT NULL AUTO_INCREMENT,
  `feature` VARCHAR(45) NOT NULL,
  `roomFK` INT NOT NULL,
  UNIQUE INDEX `feature_UNIQUE` (`feature` ASC),
  PRIMARY KEY (`idRoom_Feature`),
  INDEX `fk_Room_Feature_Room1_idx` (`roomFK` ASC),
  CONSTRAINT `fk_Room_Feature_Room1`
    FOREIGN KEY (`roomFK`)
    REFERENCES `mydb`.`Room` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TeacherWishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TeacherWishlist` (
  `idTeacherWishlist` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP NULL,
  `periodicAbsenceComment` VARCHAR(255) NULL,
  `teacherFK` INT NOT NULL,
  PRIMARY KEY (`idTeacherWishlist`),
  INDEX `fk_Teacher_Wishlist_Teacher1_idx` (`teacherFK` ASC),
  CONSTRAINT `fk_Teacher_Wishlist_Teacher1`
    FOREIGN KEY (`teacherFK`)
    REFERENCES `mydb`.`Teacher` (`idTeacher`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Message` (
  `idMessage` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NULL,
  `subject` VARCHAR(255) NULL,
  `timestamp` TIMESTAMP NULL,
  `sender` VARCHAR(255) NOT NULL,
  `recipient` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idMessage`),
  INDEX `fk_Message_User1_idx` (`sender` ASC),
  INDEX `fk_Message_User2_idx` (`recipient` ASC),
  CONSTRAINT `fk_Message_User1`
    FOREIGN KEY (`sender`)
    REFERENCES `mydb`.`User` (`idUserEmail`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Message_User2`
    FOREIGN KEY (`recipient`)
    REFERENCES `mydb`.`User` (`idUserEmail`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Student_Wishlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Student_Wishlist` (
  `idStudent_Wishlist` INT NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(255) NULL,
  `changeRoom` VARCHAR(255) NULL,
  `preferredLessons` VARCHAR(255) NULL,
  `prefferedLessonTime` VARCHAR(255) NULL,
  `timestamp` TIMESTAMP NULL,
  `studentFK` INT NOT NULL,
  PRIMARY KEY (`idStudent_Wishlist`),
  INDEX `fk_Student_Wishlist_Student1_idx` (`studentFK` ASC),
  CONSTRAINT `fk_Student_Wishlist_Student1`
    FOREIGN KEY (`studentFK`)
    REFERENCES `mydb`.`Student` (`idStudent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Absence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Absence` (
  `idAbsence` INT NOT NULL AUTO_INCREMENT,
  `start` DATE NOT NULL,
  `end` DATE NOT NULL,
  `comment` VARCHAR(255) NULL,
  `teacherwishlistFK` INT NOT NULL,
  PRIMARY KEY (`idAbsence`),
  INDEX `fk_Absence_Teacher_Wishlist1_idx` (`teacherwishlistFK` ASC),
  CONSTRAINT `fk_Absence_Teacher_Wishlist1`
    FOREIGN KEY (`teacherwishlistFK`)
    REFERENCES `mydb`.`TeacherWishlist` (`idTeacherWishlist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PeriodicAbsenceTimeslot`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PeriodicAbsenceTimeslot` (
  `idPeriodicAbsenceTimeslot` INT NOT NULL AUTO_INCREMENT,
  `weekday` INT NULL,
  `timeSlotNumber` INT NULL,
  `teacherwishlistFK` INT NOT NULL,
  PRIMARY KEY (`idPeriodicAbsenceTimeslot`),
  INDEX `fk_PeriodicAbsenceTimeslot_Teacher_Wishlist1_idx` (`teacherwishlistFK` ASC),
  CONSTRAINT `fk_PeriodicAbsenceTimeslot_Teacher_Wishlist1`
    FOREIGN KEY (`teacherwishlistFK`)
    REFERENCES `mydb`.`TeacherWishlist` (`idTeacherWishlist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
