package com.divx.service.impl;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.Util;
import com.divx.service.transcode;
import com.divx.service.model.*;
import com.divx.manager.TranscodingMng;

public class transcodeimpl implements transcode {

	private TranscodingMng transcodingMng;
	
	@Autowired
	public void setTranscodingMng(TranscodingMng transcodingMng)
	{
		this.transcodingMng = transcodingMng;
		
	}
	
	@Override
	public Response BeginTranscode(int assetId, String location) {
		return Util.ServiceResponseToResponse(transcodingMng.startTranscoding(assetId, location));
	}

	@Override
	public Response GetPrecess(int assetId) {
		return Util.ServiceResponseToResponse(transcodingMng.getTranscodingStatus(assetId));
	}

	@Override
	public Response GenerateThumbnails(ThumbnailOptions option) {
		return Util.ServiceResponseToResponse(transcodingMng.generateThumbnails(option.getAssetId(), option.getVideoLocation()));
	}

	@Override
	public Response TranscodeExt(TransOption option) {
		return Util.ServiceResponseToResponse(transcodingMng.startTranscoding(option));
	}


}
