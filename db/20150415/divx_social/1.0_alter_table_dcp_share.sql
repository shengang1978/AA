ALTER TABLE `divx_social`.`dcp_share` 
	ADD COLUMN `parent_id` INT NULL DEFAULT 0 AFTER `content`;