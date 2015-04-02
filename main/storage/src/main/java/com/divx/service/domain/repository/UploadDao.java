package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.DcpOriginalasset;

public interface UploadDao {
	DcpOriginalasset GetUploadInfo(int mediaId);
	DcpOriginalasset GetOriginalasset(int originalassetId);
	int UpdateUploadInfo(DcpOriginalasset obj);
	List<DcpOriginalasset> GetUploads();
}
