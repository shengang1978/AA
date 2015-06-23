/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.5.38-0ubuntu0.12.04.1-log : Database - divx_social
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`divx_social` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `divx_social`;

/* Procedure structure for procedure `f_share_user` */

/*!50003 DROP PROCEDURE IF EXISTS  `f_share_user` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `f_share_user`(IN `shareId` int,IN `userId` int,IN `groupId` int)
BEGIN

	
			insert into dcp_share_user(share_id,user_id,friend_id,group_id,create_date,modify_date,status) select shareId,userId,tm._user_id,groupId,NOW(),NOW(),TRUE from osf_team_members tm

								where tm._project_id = groupId;


	select ROW_COUNT() as AffectedRows;



END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
