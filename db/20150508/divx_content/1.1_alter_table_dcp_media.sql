ALTER TABLE `divx_content`.`dcp_media` 
	ADD COLUMN `ispublic` BIT(1) NULL DEFAULT 0 AFTER `parent_id`;
