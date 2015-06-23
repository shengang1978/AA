USE divx_content;

CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_score` (
  `score_id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `listen` int(11) DEFAULT '0',
  `read` int(11) DEFAULT '0',
  `record` int(11) DEFAULT '0',
  `score` int(11) DEFAULT '0',
  `datecreated` datetime DEFAULT NULL,
  `datemodified` datetime DEFAULT NULL,
  `listenduration` int(11) DEFAULT '0',
  `readduration` int(11) DEFAULT '0',
  `recordduration` int(11) DEFAULT '0',
  PRIMARY KEY (`score_id`),
  UNIQUE KEY `score_id_UNIQUE` (`score_id`),
  KEY `fk_store_id_idx` (`lesson_id`),
  CONSTRAINT `fk_lesson_id` FOREIGN KEY (`lesson_id`) REFERENCES `dcp_lesson` (`lesson_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
