package com.divx.service.domain.manager;

import java.util.LinkedList;
import java.util.List;

import com.divx.service.domain.model.DcpDivxassets;
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.Upload;

public class UploadInfoHelper {
	private static List<eFileType> storyFileTypes;
	
	static {
		storyFileTypes = new LinkedList<eFileType>();
		storyFileTypes.add(eFileType.EduStoryAudio);
		storyFileTypes.add(eFileType.EduStoryZip);
		storyFileTypes.add(eFileType.EduStoryConfig);
	}
	
	public static boolean DoesStoryHaveAllFiles(List<DcpDivxassets> assets, eFileType ftNew)
	{
		boolean ret = true;
		
		for(eFileType ft: storyFileTypes)
		{
			boolean hasFt = false;
			if (ft == ftNew)
			{
				hasFt = true;
				continue;
			}
			for(DcpDivxassets a: assets)
			{
				if (a.getVideoformat() == ft.ordinal())
				{
					hasFt = true;
					break;
				}
			}
			if (!hasFt)
			{
				ret = false;
				break;
			}
		}
		
		return ret;
	}
	
	public static boolean NeedGenerateThumbnail(Upload info)
	{
		if (info.getStatus() != Upload.eUploadStatus.done)
			return false;
		
		boolean ret = false;
		switch(info.getContentType())
		{
		case EduBook:
			break;
		case EduStory:
			if (info.getFileType() == eFileType.EduStoryZip)
				ret = true;
			break;
		default:
			//SMIL, Gif, Video
			ret = true;
		}

		return ret;
	}
}
