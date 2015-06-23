ALTER TABLE `divx_storage`.`dcp_originalasset` 
	ADD COLUMN `filetype` INT NULL DEFAULT 3 COMMENT '	public enum eFileType\n	{\n		H264,\n		H265,\n		V2G,			//����Gif�ļ�\n		Auto,			// ����ContentType�Զ��ж�\n		EduBook,		//Ӣ�Ĺ� - �鼮 zip�ļ�\n		EduStoryAudio,	//Ӣ�Ĺ� - Story��¼���ļ�\n		EduStoryZip,	//Ӣ�Ĺ� - Story��Pic��video��zip�ļ�\n		EduStoryConfig	//Ӣ�Ĺ� - Story�����ļ�\n	}' AFTER `v2gjson`;

ALTER TABLE `divx_storage`.`dcp_originalasset` 
	ADD INDEX `idx_storage_oa_media_id` USING BTREE (`media_id` ASC);
