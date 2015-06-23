USE divx_content;

CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_totalstat` (
  `totalstat_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `listen` INT NULL DEFAULT 0,
  `read` INT NULL DEFAULT 0,
  `record` INT NULL DEFAULT 0,
  `score` INT NULL DEFAULT 0,
  `listenduration` INT NULL DEFAULT 0,
  `readduration` INT NULL DEFAULT 0,
  `recordduration` INT NULL DEFAULT 0,
  `datemodified` DATETIME NULL,
  `datecreated` DATETIME NULL,
  PRIMARY KEY (`totalstat_id`),
  UNIQUE INDEX `totalstat_id_UNIQUE` (`totalstat_id` ASC),
  INDEX `idx_totalstat_userId` (`user_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
