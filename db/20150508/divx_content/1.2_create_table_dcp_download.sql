CREATE TABLE IF NOT EXISTS `divx_content`.`dcp_download` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `media_id` int(10) unsigned NOT NULL,
  `download_count` int(10) DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pk_media_down` (`media_id`),
  CONSTRAINT `pk_media_down` FOREIGN KEY (`media_id`) REFERENCES `dcp_media` (`media_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

