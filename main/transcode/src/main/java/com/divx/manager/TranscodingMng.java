package com.divx.manager;

import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ProcessResponse;
import com.divx.service.model.ThumbnailsResponse;
import com.divx.service.model.TransOption;

public interface TranscodingMng {
	public ProcessResponse getTranscodingStatus(int assetId);
	
	public ServiceResponse startTranscoding(int assetId, String filePath);
	
	public ThumbnailsResponse generateThumbnails(int assetId, String filePath);
	
	public ServiceResponse startTranscoding(TransOption option);
}

