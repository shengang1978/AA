CREATE TABLE IF NOT EXISTS `divx_social`.`dcp_party` (
  `party_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `title` VARCHAR(128) NOT NULL,
  `description` VARCHAR(512) NULL,
  `datecreated` DATETIME NULL,
  `datemodified` DATETIME NULL,
  `partydate` DATE NULL,
  `address` VARCHAR(512) NULL,
  `partytype` INT NULL,
  PRIMARY KEY (`party_id`),
  UNIQUE INDEX `party_id_UNIQUE` (`party_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
