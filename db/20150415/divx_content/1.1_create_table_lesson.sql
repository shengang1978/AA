USE divx_content;

CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_lesson` (
  `lesson_id` INT NOT NULL AUTO_INCREMENT,
  `media_id` INT UNSIGNED NOT NULL,
  `category` VARCHAR(45) NULL,
  `number` INT NULL,
  `title` VARCHAR(255) NULL,
  `datecreated` DATETIME NULL,
  `datemodified` DATETIME NULL,
  `snapshoturl` VARCHAR(255) NULL,
  PRIMARY KEY (`lesson_id`),
  UNIQUE INDEX `lesson_id_UNIQUE` (`lesson_id` ASC),
  INDEX `fk_media_id_idx` (`media_id` ASC),
  CONSTRAINT `fk_lesson_media_media_id`
    FOREIGN KEY (`media_id`)
    REFERENCES `divx_content`.`dcp_media` (`media_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
