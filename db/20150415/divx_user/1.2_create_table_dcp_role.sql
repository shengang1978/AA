USE divx_user;

CREATE TABLE IF NOT EXISTS `divx_user`.`dcp_role` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `divx_user`.`dcp_role` VALUES ('1', 'admin', now(), now());
INSERT INTO `divx_user`.`dcp_role` VALUES ('2', 'user', now(), now());
