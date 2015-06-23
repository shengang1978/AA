ALTER TABLE `divx_storage`.`dcp_originalasset` 
	ADD COLUMN `filetype` INT NULL DEFAULT 3 COMMENT '	public enum eFileType\n	{\n		H264,\n		H265,\n		V2G,			//漫视Gif文件\n		Auto,			// 根据ContentType自动判断\n		EduBook,		//英阅馆 - 书籍 zip文件\n		EduStoryAudio,	//英阅馆 - Story的录音文件\n		EduStoryZip,	//英阅馆 - Story的Pic、video的zip文件\n		EduStoryConfig	//英阅馆 - Story配置文件\n	}' AFTER `v2gjson`;

ALTER TABLE `divx_storage`.`dcp_originalasset` 
	ADD INDEX `idx_storage_oa_media_id` USING BTREE (`media_id` ASC);
