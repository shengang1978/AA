package com.divx.service.domain.manager;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpLesson;
import com.divx.service.domain.model.DcpLessonAsset;
import com.divx.service.domain.model.DcpScore;
import com.divx.service.domain.model.DcpScorestat;
import com.divx.service.domain.model.DcpTotalstat;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.Score;
import com.divx.service.model.edu.ScoreStat;

public class ScoreHelper {
	public static Score ToScore(DcpScore obj)
	{
		Score score = new Score();
		
		score.setLessonId(obj.getDcpLesson().getLessonId());
		score.setScoreId(obj.getScoreId());
		score.setListen(obj.getListen());
		score.setRead(obj.getRead());
		score.setRecord(obj.getRecord());
		score.setListenDuration(obj.getListenduration());
		score.setReadDuration(obj.getReadduration());
		score.setRecordDuration(obj.getRecordduration());
		score.setReadCount(obj.getReadcount());
		score.setTotalScore(obj.getScore());
		score.setDate(obj.getDatecreated());
		return score;
	}
	
	public static DcpScore ToScore(Score src, DcpScore dst)
	{
		dst.setListen(src.getListen());
		dst.setRead(src.getRead());
		dst.setRecord(src.getRecord());
		
		dst.setListenduration(src.getListenDuration());
		dst.setReadduration(src.getReadDuration());
		dst.setRecordduration(src.getRecordDuration());
		dst.setReadcount(src.getReadCount());
		dst.setScore(src.getListen() * 1 + src.getRead() * 2 + src.getRecord() * 3);
		
		return dst;
	}
	
	public static ScoreStat ToScoreStat(List<DcpScorestat> src, ScoreStat dst)
	{
		Map<Integer, KeyValueFourPair<Integer, Integer, Integer, Integer>> stat = new HashMap<Integer, KeyValueFourPair<Integer, Integer, Integer, Integer>>();
		
		for(DcpScorestat ss: src)
		{
			if (!stat.containsKey(ss.getDcpScore().getScoreId()))
			{
				stat.put(ss.getDcpScore().getScoreId(), 
						new KeyValueFourPair<Integer, Integer, Integer, Integer>(ss.getDcpScore().getListen(),
								ss.getDcpScore().getRead(),
								ss.getDcpScore().getRecord(),
								ss.getDcpScore().getScore()));
			}
			else
			{
				KeyValueFourPair<Integer, Integer, Integer, Integer> kvp = stat.get(ss.getDcpScore().getScoreId());
				if (ss.getDcpScore().getListen() > kvp.getKey())
					kvp.setKey(ss.getDcpScore().getListen());
				if (ss.getDcpScore().getRead() > kvp.getValue1())
					kvp.setValue1(ss.getDcpScore().getRead());
				if (ss.getDcpScore().getRecord() > kvp.getValue2())
					kvp.setValue2(ss.getDcpScore().getRecord());
				if (ss.getDcpScore().getScore() > kvp.getValue3())
					kvp.setValue3(ss.getDcpScore().getScore());
			}
			
			dst.setListenDuration(dst.getListenDuration() + ss.getListenduration());
			dst.setReadDuration(dst.getReadDuration() + ss.getReadduration());
			dst.setRecordDuration(dst.getRecordDuration() + ss.getRecordduration());
		}
		
		for(KeyValueFourPair<Integer, Integer, Integer, Integer> obj: stat.values())
		{
			dst.setListen(dst.getListen() + obj.getKey());
			dst.setRead(dst.getRead() + obj.getValue1());
			dst.setRecord(dst.getRecord() + obj.getValue2());
			dst.setTotalScore(dst.getTotalScore() + obj.getValue3());
		}
		
		dst.setStatType(ScoreStat.eStatDuration.Today);

		return dst;
	}
	
	public static ScoreStat ToScoreStat(DcpTotalstat src, ScoreStat dst)
	{
		dst.setListen(src.getListen());
		dst.setListenDuration(src.getListenduration());
		dst.setRead(src.getRead());
		dst.setReadDuration(src.getReadduration());
		dst.setRecord(src.getRecord());
		dst.setRecordDuration(src.getRecordduration());
		dst.setTotalScore(src.getScore());
		dst.setStatType(ScoreStat.eStatDuration.Total);

		return dst;
	}
	
	public static Lesson ToLesson(KeyValuePair<DcpLesson, DcpLessonAsset> src, Lesson dst)
	{
		dst.setCategory(src.getKey().getCategory());
		dst.setLessonId(src.getKey().getLessonId());
		dst.setMediaId(src.getKey().getDcpMedia().getMediaId());
		dst.setNumber(src.getKey().getNumber());
		dst.setSnapshotUrl(src.getKey().getSnapshoturl());
		dst.setTitle(src.getKey().getTitle());
		dst.setCategoryTitle(src.getKey().getCategoryTitle());
		dst.setConfig(src.getKey().getConfig());
		if(src.getKey().getSnapshoturl() != null && !src.getKey().getSnapshoturl().isEmpty())
			dst.setSnapshotUrl(Util.UrlWithHttp(src.getKey().getSnapshoturl()));
		else
			dst.setSnapshotUrl("");
		
		if (src.getValue() != null)
			dst.setVideoUrl(Util.UrlWithHttp(src.getValue().getContent()));
		
		return dst;
	}
	public static Lesson ToLesson(DcpLesson src, Lesson dst, boolean needAssetInfo)
	{
		dst.setCategory(src.getCategory());
		dst.setLessonId(src.getLessonId());
		dst.setMediaId(src.getDcpMedia().getMediaId());
		dst.setNumber(src.getNumber());
		dst.setSnapshotUrl(src.getSnapshoturl());
		dst.setTitle(src.getTitle());
		dst.setCategoryTitle(src.getCategoryTitle());
		dst.setConfig(src.getConfig());
		if(src.getSnapshoturl() != null && !src.getSnapshoturl().isEmpty())
			dst.setSnapshotUrl(Util.UrlWithHttp(src.getSnapshoturl()));
		else
			dst.setSnapshotUrl("");
		
		if (src.getDcpLessonAssets() != null && !src.getDcpLessonAssets().isEmpty())
		{
			Iterator it = src.getDcpLessonAssets().iterator();
			
			while (it.hasNext())
			{
				DcpLessonAsset obj = (DcpLessonAsset)it.next();
				if (!needAssetInfo)
				{
					if (obj.getAssettype() == MediaBaseType.eFileType.EduLessonVideo.ordinal())
					{
						dst.setVideoUrl(Util.UrlWithHttp(obj.getContent()));
					}
					else if (obj.getAssettype() == MediaBaseType.eFileType.EduLessonPhoto.ordinal())
					{
						if (dst.getSnapshotUrl() == "")
						{
							if (obj.getContent().indexOf("P0.jpg") > 0)
							{
								dst.setSnapshotUrl(Util.UrlWithHttp(obj.getContent()));
								//break;
							}
						}
						else
						{
							//break;
						}
					}
					
					continue;
				}
				
				switch(MediaBaseType.eFileType.values()[obj.getAssettype()])
				{
				case EduLessonPhoto:
					List<String> urls = dst.getPhotoUrls();
					if (urls == null)
					{
						urls = new LinkedList<String>();
						dst.setPhotoUrls(urls);
					}
					urls.add(Util.UrlWithHttp(obj.getContent()));
					break;
				case EduLessonAudio:
					dst.setAudioUrl(Util.UrlWithHttp(obj.getContent()));
					break;
				case EduLessonVideo:
					dst.setVideoUrl(Util.UrlWithHttp(obj.getContent()));
					break;
				case EduLessonConfig:
					dst.setPlistUrl(Util.UrlWithHttp(obj.getContent()));
					break;
				default:
					continue;
				}
			}
			
			if (dst.getPhotoUrls() != null && dst.getPhotoUrls().size() > 0)
			{
				Collections.sort(dst.getPhotoUrls(), new ComparatorPhotoUrl());
				if (dst.getPhotoUrls().get(0).indexOf("P0.jpg") > 0)
				{
					dst.getPhotoUrls().remove(0);
				}
				
			}
		}
		
		return dst;
	}
	
	public static DcpLesson ToLesson(Lesson src, DcpLesson dst)
	{
		dst.setCategory(src.getCategory());
		dst.setCategoryTitle(src.getCategoryTitle());
		dst.setDatemodified(new Date());
		if (src.getLessonId() > 0)
		{
			dst.setLessonId(src.getLessonId());
		}
		else
		{
			dst.setDatecreated(new Date());
		}
		dst.setNumber(src.getNumber());
		dst.setTitle(src.getTitle());
		
		if(src.getSnapshotUrl() != null)
			dst.setSnapshoturl(src.getSnapshotUrl());
		else
			dst.setSnapshoturl("");
//		if(src.getLessonZipUrl() != null)
//			dst.setLessonZipUrl(src.getLessonZipUrl());
//		else
//			dst.setLessonZipUrl("");
		return dst;
	}
}
