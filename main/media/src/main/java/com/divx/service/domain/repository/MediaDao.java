package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.*;
import com.divx.service.model.KeyValuePair;

public interface MediaDao {
	int CreateMedia(DcpMedia obj);
	
	DcpMedia GetMedia(int mediaId);
	
	List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(int userId, int startPos, int endPos);
	
	List<DcpMedia> GetPublicMedias(int startPos, int endPos);
	
	int CreateKeywords(List<DcpMediaKeywords> words);
	
	int UpdateMedia(DcpMedia obj);
	
	int DeleteMedia(DcpMedia obj);
	
	int UpdateUploadInfo(DcpOriginalasset obj);
	
	DcpOriginalasset GetUploadInfo(int mediaId);
	
	DcpOriginalasset GetOriginalasset(int originalassetId);
	
	int CreateDivxAsset(DcpDivxassets obj);
	
	DcpDivxassets GetDivxAsset(int mediaId, int videoformat);
}
