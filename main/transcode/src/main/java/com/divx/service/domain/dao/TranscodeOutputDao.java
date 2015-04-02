package com.divx.service.domain.dao;

import com.divx.service.domain.model.DcpTranscodeOutput;

import java.util.List;

public interface TranscodeOutputDao {
	DcpTranscodeOutput GetByTranscodeOutputId(int transcodeOutputId);
	
	DcpTranscodeOutput GetByTranscodeId(int transcodeId);

	int CreateTranscodeOutput(DcpTranscodeOutput obj);
	
	int UpdateTranscodeOutput(DcpTranscodeOutput obj);
}
