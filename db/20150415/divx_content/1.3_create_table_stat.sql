USE divx_content;

CREATE TABLE if not exists `divx_content`.`dcp_scorestat` (
  `stat_id` int(11) NOT NULL AUTO_INCREMENT,
  `score_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `datecreated` datetime DEFAULT NULL,
  `listenduration` int(11) DEFAULT '0',
  `readduration` int(11) DEFAULT '0',
  `recordduration` int(11) DEFAULT '0',
  `stattype` int(11) DEFAULT '0',
  PRIMARY KEY (`stat_id`),
  UNIQUE KEY `stat_id_UNIQUE` (`stat_id`),
  KEY `idx_stat_user_id` (`user_id`),
  KEY `fk_stat_score_id_idx` (`score_id`),
  KEY `idx_stat_create_date` (`datecreated`),
  KEY `idx_stat_stattype` (`stattype`),
  CONSTRAINT `fk_stat_score_id` FOREIGN KEY (`score_id`) REFERENCES `dcp_score` (`score_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
