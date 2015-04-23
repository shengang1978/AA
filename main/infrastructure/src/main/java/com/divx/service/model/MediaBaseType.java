package com.divx.service.model;

public class MediaBaseType {
	public enum eContentType
	{
		SMIL,	//divx smil video file
		Gif,	//gif
		Video4Gif,	//Video. convert it to gif file.
		EduBook,	// 英阅馆. 书籍
		EduBookURL,	// 英阅馆. 书籍 URL
		EduStory,	// 英阅馆，Store分享单位
	}
	
	// FileType: 
	//	In db: divx_content.dcp_divxasset.videoformat
	//		   divx_storage.dcp_originalasset.filetype
	public enum eFileType
	{
		H264,
		H265,
		Gif,			//漫视Gif文件
		Auto,			// 根据ContentType自动判断
		EduBook,		//英阅馆 - 书籍 zip文件
		EduStoryAudio,	//英阅馆 - Story的录音文件
		EduStoryZip,	//英阅馆 - Story的Pic、video的zip文件
		EduStoryConfig	//英阅馆 - Story配置文件
	}
	
	public enum eMediaStatus
	{
		creating, 	// media is created
		uploading,	// uploading the asset
		uploaded,	// finish to upload the asset for the media
		editing,	// editing the asset of the media
		edited,		// finish to edit
		publishing,	// publishing the media (transcoding and uploading the asset)
		done,		// done.		
		
		error_transcode_fail	// fail to transcode
	}
	
}
