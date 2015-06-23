ALTER TABLE `divx_content`.`dcp_media` 
	ADD COLUMN `parent_id` INT NULL DEFAULT 0 AFTER `contenttype`;