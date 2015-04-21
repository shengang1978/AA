CREATE TABLE IF NOT EXISTS `dcp_party_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `party_id` int(11) NOT NULL,
  `datecreated` datetime NOT NULL,
  `datemodified` datetime NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `qq` varchar(45) DEFAULT NULL,
  `weixin` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `snapshoturl` varchar(255) DEFAULT NULL,
  `usertype` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_party_id_idx` (`party_id`),
  KEY `idx_party_id` (`party_id`),
  CONSTRAINT `fk_party_id` FOREIGN KEY (`party_id`) REFERENCES `dcp_party` (`party_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
