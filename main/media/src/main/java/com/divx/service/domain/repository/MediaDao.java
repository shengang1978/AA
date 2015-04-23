package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.*;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.MediaBaseType;

public interface MediaDao {
	int CreateMedia(DcpMedia obj);
	
	DcpMedia GetMedia(int mediaId);
	
	List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(List<Integer> contentType, int userId, int startPos, int endPos);
	
	List<DcpMedia> GetPublicMedias(int startPos, int endPos);
	
	int CreateKeywords(List<DcpMediaKeywords> words);
	
	int UpdateMedia(DcpMedia obj);
	
	int DeleteMedia(DcpMedia obj);
	
	int UpdateUploadInfo(DcpOriginalasset obj);
	
	DcpOriginalasset GetUploadInfo(int mediaId);
	
	DcpOriginalasset GetOriginalasset(int originalassetId);
	
	int CreateDivxAsset(DcpDivxassets obj);
	
	List<DcpDivxassets> GetDivxAsset(int mediaId, MediaBaseType.eFileType fileType);
}
