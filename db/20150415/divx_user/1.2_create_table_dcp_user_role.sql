USE divx_user;

CREATE TABLE  IF NOT EXISTS `divx_user`.`dcp_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT '2',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dcpuserrole_userid` (`user_id`),
  KEY `fk_dcpuserrole_roleid` (`role_id`),
  CONSTRAINT `fk_dcpuserrole_roleid` FOREIGN KEY (`role_id`) REFERENCES `divx_user`.`dcp_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_dcpuserrole_userid` FOREIGN KEY (`user_id`) REFERENCES `divx_user`.`osf_users` (`_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

