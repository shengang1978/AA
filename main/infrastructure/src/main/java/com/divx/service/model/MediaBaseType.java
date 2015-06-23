package com.divx.service.model;

public class MediaBaseType {
	public enum eContentType
	{
		SMIL,		//0  	divx smil video file
		Gif,		//1  	gif
		Video4Gif,	//2		Video. convert it to gif file.
		EduBook,	//3 	zip book
		EduBookURL,	//4 	book URL
		EduStory,	//5		Recorded story
	}
	
	// FileType: 
	//	In db: divx_content.dcp_divxasset.videoformat
	//		   divx_storage.dcp_originalasset.filetype
	public enum eFileType
	{
		H264,			//0
		H265,			//1
		Gif,			//2
		Auto,			//3
		EduBook,		//4
		EduStoryAudio,	//5
		EduStoryZip,	//6
		EduStoryConfig,	//7
		EduLessonAudio,	//8
		EduLessonVideo,	//9
		EduLessonPhoto,	//10
		EduLessonConfig,//11
		other			//12
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
//	public enum LessonAssetType{
//		jpg,
//		mp3,
//		plist,
//		other
//		
//	}
	
}
