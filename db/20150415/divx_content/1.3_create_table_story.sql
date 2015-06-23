CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_story` (
  `story_id` INT NOT NULL AUTO_INCREMENT,
  `lesson_id` INT NOT NULL,
  `title` VARCHAR(255) NULL,
  `snapshoturl` VARCHAR(255) NULL,
  `user_id` INT NULL,
  `zipfile` VARCHAR(255) NULL,
  `recordfile` VARCHAR(255) NULL,
  `configfile` VARCHAR(255) NULL,
  `datecreated` DATETIME NULL,
  `datemodified` DATETIME NULL,
  PRIMARY KEY (`story_id`),
  UNIQUE INDEX `story_id_UNIQUE` (`story_id` ASC),
  INDEX `fk_1story_lesson_id_idx` (`lesson_id` ASC),
  INDEX `idx_story_userId` (`user_id` ASC),
  CONSTRAINT `fk_story_lesson_id`
    FOREIGN KEY (`lesson_id`)
    REFERENCES `divx_content`.`dcp_lesson` (`lesson_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
