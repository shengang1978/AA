USE divx_user;

CREATE TABLE IF NOT EXISTS `divx_user`.`dcp_oauth_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `access_token` varchar(255) NOT NULL,
  `open_id` varchar(255) NOT NULL,
  `oauth_type` int(11) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_dcpoauthuser_openid` (`open_id`) USING BTREE,
  KEY `fk_dcpoauthusers_userid` (`user_id`) USING BTREE,
  CONSTRAINT `fk_dcpoauthusers_userid` FOREIGN KEY (`user_id`) REFERENCES `divx_user`.`osf_users` (`_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

