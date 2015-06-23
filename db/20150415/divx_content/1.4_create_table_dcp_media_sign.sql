USE divx_content;

CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_media_sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `media_id` int(11) unsigned NOT NULL,
  `sign` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_media_sign_media_id` (`media_id`),
  CONSTRAINT `fk_media_sign_media_id` FOREIGN KEY (`media_id`) REFERENCES `dcp_media` (`media_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

