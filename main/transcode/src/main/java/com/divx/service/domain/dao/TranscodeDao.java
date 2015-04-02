package com.divx.service.domain.dao;

import java.util.List;

import com.divx.common.main.Constants.eJobType;
import com.divx.service.domain.model.DcpFilter;
import com.divx.service.domain.model.DcpTranscode;

public interface TranscodeDao {
	DcpTranscode GetTranscodeByTranscodeId(int transcodeId);
	
	DcpTranscode GetTranscodeByAssetId(int assetId);
	
	DcpTranscode GetTranscodeByJobName(String jobname);
	
	int CreateTranscode(DcpTranscode obj);
	
	int UpdateTranscode(DcpTranscode obj);
	
	List<DcpTranscode> GetUndoneTranscode(int status);
	
	int CreateFilter(DcpFilter obj);
	
	DcpFilter getDcpFilter(int width,int height,int rotation,eJobType type);
}
