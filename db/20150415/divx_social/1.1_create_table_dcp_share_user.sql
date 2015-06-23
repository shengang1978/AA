USE divx_social;

CREATE TABLE IF NOT EXISTS `divx_social`.`dcp_share_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `share_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_shareuser_shareid` (`share_id`),
  CONSTRAINT `fk_shareuser_shareid` FOREIGN KEY (`share_id`) REFERENCES `dcp_share` (`share_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


