SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema DBLabs
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `DBLabs` ;

-- -----------------------------------------------------
-- Schema DBLabs
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DBLabs` DEFAULT CHARACTER SET utf8 ;
USE `DBLabs` ;

-- -----------------------------------------------------
-- Table `DBLabs`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Role` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Role` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `name` VARCHAR(50) NOT NULL,
    `description` VARCHAR(45) NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`User` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`User` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `password` VARCHAR(45) NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    `nickname` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `picture` MEDIUMBLOB NULL,
    `Role_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_User_Role_idx` (`Role_id` ASC) VISIBLE,
    CONSTRAINT `fk_User_Role`
    FOREIGN KEY (`Role_id`)
    REFERENCES `DBLabs`.`Role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`Quiz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Quiz` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Quiz` (
                                               `id` INT NOT NULL AUTO_INCREMENT,
                                               `description` VARCHAR(45) NULL,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`Question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Question` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Question` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `type` VARCHAR(45) NOT NULL,
    `number` INT UNSIGNED NOT NULL,
    `description` VARCHAR(100) NULL,
    `Quiz_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Question_Quiz1_idx` (`Quiz_id` ASC) VISIBLE,
    CONSTRAINT `fk_Question_Quiz1`
    FOREIGN KEY (`Quiz_id`)
    REFERENCES `DBLabs`.`Quiz` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`Answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Answer` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Answer` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `text` VARCHAR(100) NULL,
    `option` VARCHAR(45) NULL,
    `file` MEDIUMBLOB NULL,
    `Question_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Answer_Question1_idx` (`Question_id` ASC) VISIBLE,
    CONSTRAINT `fk_Answer_Question1`
    FOREIGN KEY (`Question_id`)
    REFERENCES `DBLabs`.`Question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`Respondent`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Respondent` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Respondent` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `User_id` INT NOT NULL,
                                                     `Answer_id` INT NOT NULL,
                                                     PRIMARY KEY (`id`),
    INDEX `fk_Respondent_User1_idx` (`User_id` ASC) VISIBLE,
    INDEX `fk_Respondent_Answer1_idx` (`Answer_id` ASC) VISIBLE,
    CONSTRAINT `fk_Respondent_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `DBLabs`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Respondent_Answer1`
    FOREIGN KEY (`Answer_id`)
    REFERENCES `DBLabs`.`Answer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`Option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`Option` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`Option` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `description` VARCHAR(45) NULL,
    `number` INT UNSIGNED NOT NULL,
    `Question_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Option_Question1_idx` (`Question_id` ASC) VISIBLE,
    CONSTRAINT `fk_Option_Question1`
    FOREIGN KEY (`Question_id`)
    REFERENCES `DBLabs`.`Question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DBLabs`.`SelectedOption`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `DBLabs`.`SelectedOption` ;

CREATE TABLE IF NOT EXISTS `DBLabs`.`SelectedOption` (
                                                         `Answer_id` INT NOT NULL,
                                                         `Option_id` INT NOT NULL,
                                                         INDEX `fk_SelectedOption_Answer1_idx` (`Answer_id` ASC) VISIBLE,
    INDEX `fk_SelectedOption_Option1_idx` (`Option_id` ASC) VISIBLE,
    CONSTRAINT `fk_SelectedOption_Answer1`
    FOREIGN KEY (`Answer_id`)
    REFERENCES `DBLabs`.`Answer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_SelectedOption_Option1`
    FOREIGN KEY (`Option_id`)
    REFERENCES `DBLabs`.`Option` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `DBLabs`.`Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `DBLabs`;
INSERT INTO `DBLabs`.`Role` (`name`, `description`) VALUES ('Respondent', 'A user that taking a survey');
INSERT INTO `DBLabs`.`Role` (`name`, `description`) VALUES ('Editor', 'The user who edits the survey');
INSERT INTO `DBLabs`.`Role` (`name`, `description`) VALUES ('Admin', 'The user is the owner of the survey');

COMMIT;