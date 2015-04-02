package com.divx.service.domain.dao;

import java.util.List;

import com.divx.service.domain.model.DcpTranscodeJob;

public interface TranscodeJobDao {
	List<DcpTranscodeJob> GetTranscodeJobByTranscodeId(int transcodeId);
	
	public int CreateTranscodeJob(DcpTranscodeJob obj);
	
	public DcpTranscodeJob GetTranscodeJobByJobName(String jobname);
	
	public int UpdateTranscodeJob(DcpTranscodeJob obj);
}
