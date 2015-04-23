package com.divx.service.model;

public class MediaBaseType {
	public enum eContentType
	{
		SMIL,	//divx smil video file
		Gif,	//gif
		Video4Gif,	//Video. convert it to gif file.
		EduBook,	// Ӣ�Ĺ�. �鼮
		EduBookURL,	// Ӣ�Ĺ�. �鼮 URL
		EduStory,	// Ӣ�Ĺݣ�Store����λ
	}
	
	// FileType: 
	//	In db: divx_content.dcp_divxasset.videoformat
	//		   divx_storage.dcp_originalasset.filetype
	public enum eFileType
	{
		H264,
		H265,
		Gif,			//����Gif�ļ�
		Auto,			// ����ContentType�Զ��ж�
		EduBook,		//Ӣ�Ĺ� - �鼮 zip�ļ�
		EduStoryAudio,	//Ӣ�Ĺ� - Story��¼���ļ�
		EduStoryZip,	//Ӣ�Ĺ� - Story��Pic��video��zip�ļ�
		EduStoryConfig	//Ӣ�Ĺ� - Story�����ļ�
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
