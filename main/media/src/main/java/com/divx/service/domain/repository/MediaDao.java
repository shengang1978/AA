package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.AuthHelper;
import com.divx.service.domain.model.*;
import com.divx.service.model.DcpBaseType.eAppType;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.edu.ScoreStat;

public interface MediaDao {
	int CreateMedia(DcpMedia obj);
	
	DcpMedia GetMedia(int mediaId);
	
	DcpMedia GetAncestorMedia(int mediaId);
	
	List<KeyValuePair<DcpMedia,DcpDivxassets>> GetMyMedias(List<Integer> contentType, int userId, int startPos, int endPos);
	
	List<KeyValuePair<DcpMedia,DcpDivxassets>> GetPublicMedias(List<Integer> contentType);
	
	Integer IsBookInMyLibrary(int userId, int mediaId);
	
	int CreateKeywords(List<DcpMediaKeywords> words);
	
	int UpdateMedia(DcpMedia obj);
	
	int DeleteMedia(DcpMedia obj);
	
	int UpdateUploadInfo(DcpOriginalasset obj);
	
	DcpOriginalasset GetUploadInfo(int mediaId);
	
	DcpOriginalasset GetOriginalasset(int originalassetId);
	
	int CreateDivxAsset(DcpDivxassets obj);
	
	List<DcpDivxassets> GetDivxAsset(int mediaId, MediaBaseType.eFileType fileType);

	
	int createMediaSign(DcpMediaSign mediaSign);
	
	DcpMediaSign GetMediaSign(String sign);
	
	DcpMediaSign GetMediaSign(int mediaId);
	

	DcpLesson GetLesson(int lessonId);
	DcpLesson GetLesson(int mediaId, String category, int number);
	
	List<DcpLesson>	GetLessons(int mediaId);
	List<KeyValuePair<DcpLesson, DcpLessonAsset>> GetLessonsCovers(int mediaId);
	int SetLessons(int mediaId, List<DcpLesson> lessons);
	int DeleteLesson(int userId, int lessonId);
	int SetLesson(DcpLesson lesson);
	
	
	List<DcpScore> GetScoresByUser(int userId);
	List<DcpScore> GetScores(int userId, int mediaId);	
	int SetScore(DcpScore score);
	DcpScore GetScore(int scoreId);
	DcpScore GetScore(int userId, int lessonId);
	
	List<DcpScorestat> GetStats(int userId, ScoreStat.eStatDuration st);
	DcpTotalstat GetTotalStat(int userId);
	
	List<DcpDownload> GetAllDownloadCount();
	DcpDownload GetDownloadCount(int mediaId);
	int UpdateDownloadCount(DcpDownload download);
	
	int CreateDcpLessonAsset(List<DcpLessonAsset> lessonAsset);
	
	int DeleteDcpLessonAsset(List<DcpLessonAsset> lessonAsset);
	
	int AddStoryPlayHit(int mediaId);
	
	List<DcpStoryplayTotalstat> GetStoryPlayTotalstat(int nStartPos, int nEndPos, boolean bDesc);
	
	DcpStoryplayTotalstat GetStoryPlayStat(int mediaId);
	List<KeyValuePair<DcpMedia,DcpDivxassets>> GetAllMedias(List<Integer> contentType);
	int SetMediaIsPublic(boolean isPublic,List<String> mediaIds);
	
	List<DcpLesson> GetAllLessons();
}
