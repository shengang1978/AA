package com.divx.service.domain.manager;

import java.io.InputStream;

import com.divx.service.model.FileUpload;

public class UploadManager {
	
	
	public void UploadFile(FileUpload fu, InputStream in)
	{
		if (fu.getTotalSize() <= 0)
		{
			// Realtime uploading
		}
		else
		{
			// Resume upload
		}
	}
}
