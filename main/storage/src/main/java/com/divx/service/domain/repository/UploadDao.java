package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.DcpOriginalasset;
import com.divx.service.model.MediaBaseType;

public interface UploadDao {
	List<DcpOriginalasset> GetUploadInfo(int mediaId, MediaBaseType.eFileType fileType);
	DcpOriginalasset GetOriginalasset(int originalassetId);
	int UpdateUploadInfo(DcpOriginalasset obj);
	List<DcpOriginalasset> GetUploads();
}
