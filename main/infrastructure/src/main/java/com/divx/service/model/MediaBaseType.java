package com.divx.service.model;

public class MediaBaseType {
	public enum eContentType
	{
		SMIL,	//divx smil video file
		Gif,	//gif
		Video4Gif,	//Video. convert it to gif file.
		File,	//normal file
		URL		// URL content
	}
	public enum eFileType
	{
		H264,
		H265
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
	
	public enum eDeviceType
	{
		Android,
		iOS,
		CE
	}
}
