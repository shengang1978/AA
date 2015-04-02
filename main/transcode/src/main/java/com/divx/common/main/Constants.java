package com.divx.common.main;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;

public final class Constants {
	public enum eProcessStatus {
		Uploaded,					//0
		Analyzing,					//1
		FinishAnalysis,				//2
		FinishCreateTranscode,		//3
		CreatedTranscode,			//4
		Transcoding,				//5
		FinishTranscode,			//6
		FinishSMILCreation,			//7
		FinsihEndPublish,			//8
		Error,						//9
		MediaServiceConnectionError	//10
	}
	
	public enum eJobType {
		Analyze,				//0
		H265,					//1
		H264					//2
	}
	
	public enum eJobFormat {
		Unknown,
		Analyze,
		VIDEO_RESOLUTION_4K,
		VIDEO_RESOLUTION_1080,
		VIDEO_RESOLUTION_720,
		VIDEO_RESOLUTION_480,
		AUDIO_AAC_2CH_192
	}
	
	public enum eProcessErrorCode {
		InvalidInputFile,		//0
		TranscodeError,			//1
		SubmitAnalysisJobError,	//2
		GetPresetGroupError,	//3
		SubmitTransocdeJobError,//4
		GenerateSMILError,		//5
		EndPublishError,		//6
		GetVideoResolutionError,//7
		CreateFilterError,		//8
		CreateTranscodeError,	//9
		WatchAnalyzingJobError,	//10
		WatchTranscodingJobError//11
	}
	
	static{
		ConfigurationManager mgr = ConfigurationManager.GetInstance(); 
		TCE_ETRANSCODE 				= mgr.TCE_ETRANSCODE();
		TCE_URI 					= mgr.TCE_URI();
		TCE_LOGIN_USER 				= mgr.TCE_LOGIN_USER();
		TCE_LOGIN_PASSWORD 			= mgr.TCE_LOGIN_PASSWORD();
		TCE_TRANSCODING_PRESET_H264_4K 	= mgr.TCE_TRANSCODING_PRESET_H264_4K();
		TCE_TRANSCODING_PRESET_H264_1080 = mgr.TCE_TRANSCODING_PRESET_H264_1080();
		TCE_TRANSCODING_PRESET_H264_720 	= mgr.TCE_TRANSCODING_PRESET_H264_720();
		TCE_TRANSCODING_PRESET_H264_480 	= mgr.TCE_TRANSCODING_PRESET_H264_480();
		TCE_SERVER_GROUPS          	= mgr.TCE_SERVER_GROUPS();
		TCE_LOCATION_OUT           	= Util.UrlWithSlashes(mgr.TCE_LOCATION_OUT());
		SMIL_GEN_PATH              	= mgr.SMIL_GEN_PATH();
		SMIL_OUT_FILE_PREFIX       	= Util.UrlWithSlashes(mgr.SMIL_OUT_FILE_PREFIX());
		THUMBNAIL_GEN_PATH         	= mgr.THUMBNAIL_GEN_PATH();
		THUMBNAIL_OUTPUT_PATH      	= Util.UrlWithSlashes(mgr.THUMBNAIL_OUTPUT_PATH());
		THUMBNAIL_OUTPUT_PREFIX    	= Util.UrlWithSlashes(mgr.THUMBNAIL_OUTPUT_PREFIX());
		MEDIAINFO_PATH             	= mgr.MEDIAINFO_PATH();
		TCE_TRANSCODING_PRESET_HEVC_4K 	= mgr.TCE_TRANSCODING_PRESET_HEVC_4K();
		TCE_TRANSCODING_PRESET_HEVC_1080 = mgr.TCE_TRANSCODING_PRESET_HEVC_1080();
		TCE_TRANSCODING_PRESET_HEVC_720 	= mgr.TCE_TRANSCODING_PRESET_HEVC_720();
		TCE_TRANSCODING_PRESET_HEVC_480 	= mgr.TCE_TRANSCODING_PRESET_HEVC_480();
		ERROR_RETRY_COUNT			= mgr.ERROR_RETRY_COUNT();
		TCE_LOCATION_IN				= mgr.UploadDestFolder();
		TCE_TRANSCODING_PRESET_AAC_2CH_192  = mgr.TCE_TRANSCODING_PRESET_AAC_2CH_192();
	}
	
	public static String TCE_ETRANSCODE;
	public static String TCE_URI;
	public static String TCE_LOGIN_USER;
	public static String TCE_LOGIN_PASSWORD;
	public static String TCE_TRANSCODING_PRESET_H264_4K;
	public static String TCE_TRANSCODING_PRESET_H264_1080;
	public static String TCE_TRANSCODING_PRESET_H264_720;
	public static String TCE_TRANSCODING_PRESET_H264_480;
	public static String TCE_SERVER_GROUPS;
	public static String TCE_LOCATION_OUT;
		
	public static String SMIL_GEN_PATH;
	public static String SMIL_OUT_FILE_PREFIX;
	
	public static String THUMBNAIL_GEN_PATH;
	public static String THUMBNAIL_OUTPUT_PATH;
	public static String THUMBNAIL_OUTPUT_PREFIX;
	
	public static String MEDIAINFO_PATH;
	
	public static String TCE_TRANSCODING_PRESET_HEVC_4K;
	public static String TCE_TRANSCODING_PRESET_HEVC_1080;
	public static String TCE_TRANSCODING_PRESET_HEVC_720;
	public static String TCE_TRANSCODING_PRESET_HEVC_480;
	
	public static String ERROR_RETRY_COUNT;
	public static String TCE_LOCATION_IN;
	public static String  TCE_TRANSCODING_PRESET_AAC_2CH_192;
	public static boolean Enable_RemoveJob(){
		return ConfigurationManager.GetInstance().TCE_Enable_RemoveJob();
	}
//	public static String TCE_ETRANSCODE = SysConfig.getValue("TCE_ETRANSCODE");
//	public static String TCE_URI = SysConfig.getValue("TCE_URI");
//	public static String TCE_LOGIN_USER = SysConfig.getValue("TCE_LOGIN_USER");
//	public static String TCE_LOGIN_PASSWORD = SysConfig.getValue("TCE_LOGIN_PASSWORD");
//	public static String TCE_TRANSCODING_PRESET_4K = SysConfig.getValue("TCE_TRANSCODING_PRESET_4K");
//	public static String TCE_TRANSCODING_PRESET_1080 = SysConfig.getValue("TCE_TRANSCODING_PRESET_1080");
//	public static String TCE_TRANSCODING_PRESET_720 = SysConfig.getValue("TCE_TRANSCODING_PRESET_720");
//	public static String TCE_TRANSCODING_PRESET_480 = SysConfig.getValue("TCE_TRANSCODING_PRESET_480");
//	public static String TCE_SERVER_GROUPS = SysConfig.getValue("TCE_SERVER_GROUPS");
//	public static String TCE_LOCATION_OUT = SysConfig.getValue("TCE_LOCATION_OUT");
////		
//	public static String SMIL_GEN_PATH = SysConfig.getValue("SMIL_GEN_PATH");
//	public static String SMIL_OUT_FILE_PREFIX = SysConfig.getValue("SMIL_OUT_FILE_PREFIX");
//	
//	public static String THUMBNAIL_GEN_PATH = SysConfig.getValue("THUMBNAIL_GEN_PATH");
//	public static String THUMBNAIL_OUTPUT_PATH = SysConfig.getValue("THUMBNAIL_OUTPUT_PATH");
//	public static String THUMBNAIL_OUTPUT_PREFIX = SysConfig.getValue("THUMBNAIL_OUTPUT_PREFIX");
//	
//	public static String MEDIAINFO_PATH = SysConfig.getValue("MEDIAINFO_PATH");
	
	public static final String TCE_JOB_STATUS_EXT_PENGING = "pending";
	public static final String TCE_JOB_STATUS_EXT_PAUSED = "paused";
	public static final String TCE_JOB_STATUS_EXT_FINISHED = "finished";
	public static final String TCE_JOB_STATUS_EXT_ERROR = "error";
	public static final String TCE_JOB_STATUS_EXT_ABORTED = "aborted";
	public static final String TCE_JOB_STATUS_EXT_RUNNING = "running";
	
	public static final String TCE_JOB_STATUS_QUEUE = "queue";
	public static final String TCE_JOB_STATUS_WATCHQ = "watchq";
	public static final String TCE_JOB_STATUS_WATCH = "watch";
	public static final String TCE_JOB_STATUS_STOP = "stop";
	public static final String TCE_JOB_STATUS_ERROR = "error";
	public static final String TCE_JOB_STATUS_FINISH = "finish";
	public static final String TCE_JOB_STATUS_ABORT = "abort";
	public static final String TCE_JOB_STATUS_TASKWAIT = "taskwait";
	public static final String TCE_JOB_STATUS_PAUSED = "paused";
	public static final String TCE_JOB_STATUS_POST = "post";
}
