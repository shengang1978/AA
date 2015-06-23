package com.divx.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.divx.service.repository.ConfigServiceSource;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;

public class ConfigurationManager {
	private Logger	log = Logger.getLogger(ConfigurationManager.class);
	static {
		instance = new ConfigurationManager();
		
	}
	
	public static ConfigurationManager GetInstance()
	{
		return instance;
	}
	
	private String checkTokenURL = "";
	public String CheckTokenURL()
	{
		String val = UserServiceBaseUrl() + "/userInner/CheckUser/";	//GetConfigValue("CheckTokenURL", checkTokenURL);
		if (!val.isEmpty())
		{
			checkTokenURL = val;
		}
		
		return checkTokenURL;
	}
	
	private String uploadTempFolder = "";
	public String UploadTempFolder()
	{
		String val = GetConfigValue("UploadTempFolder", uploadTempFolder);
		if (!val.isEmpty())
		{
			uploadTempFolder = val;
		}
		
		return val;
	}
	
	private String uploadDestFolder = "";
	public String UploadDestFolder()
	{
		String val = GetConfigValue("UploadDestFolder", uploadDestFolder);
		if (!val.isEmpty())
		{
			uploadDestFolder = val;
		}
		
		return val;
	}
	
	private String userServiceBaseUrl = "";
	public String UserServiceBaseUrl()
	{
		String val = GetConfigValue("UserServiceBaseUrl", userServiceBaseUrl);
		if (!val.isEmpty())
		{
			userServiceBaseUrl = val;
		}
		
		return val;
	}
	
	private String mediaServiceBaseUrl = "";
	public String MediaServiceBaseUrl()
	{		
		String val = GetConfigValue("MediaServiceBaseUrl", mediaServiceBaseUrl);
		if (!val.isEmpty())
		{
			mediaServiceBaseUrl = val;
		}
		
		return val;
	}
	
	private String transcodeServiceBaseUrl = "";
	public String TranscodeServiceBaseUrl()
	{
		String val = GetConfigValue("TranscodeServiceBaseUrl", transcodeServiceBaseUrl);
		if (!val.isEmpty())
		{
			transcodeServiceBaseUrl = val;
		}
		
		return val;
	}
	
	private String socialServiceBaseUrl = "";
	public String SocialServiceBaseUrl()
	{
		String val = GetConfigValue("SocialServiceBaseUrl", socialServiceBaseUrl);
		if (!val.isEmpty())
		{
			socialServiceBaseUrl = val;
		}
		
		return val;
	}
	
	private String messageServiceBaseUrl = "";
	public String MessageServiceBaseUrl()
	{
		String val = GetConfigValue("MessageServiceBaseUrl", messageServiceBaseUrl);
		if (!val.isEmpty())
		{
			messageServiceBaseUrl = val;
		}
		
		return val;
	}
	private String storageServiceBaseUrl = "";
	public String StorageServiceBaseUrl()
	{
		String val = GetConfigValue("StorageServiceBaseUrl", storageServiceBaseUrl);
		if (!val.isEmpty())
		{
			storageServiceBaseUrl = val;
		}
		
		return val;
	}
	private String inviteUserEmailTemplate = "";
	public String InviteUserEmailTemplate()
	{
		String val = GetConfigValue("InviteUserEmailTemplate", inviteUserEmailTemplate);
		if (!val.isEmpty())
		{
			inviteUserEmailTemplate = val;
		}
		
		return val;
	}
	private String welcomeEmailTemplate = "";
	public String WelcomeEmailTemplate()
	{
		String val = GetConfigValue("WelcomeEmailTemplate", welcomeEmailTemplate);
		if (!val.isEmpty())
		{
			welcomeEmailTemplate = val;
		}
		
		return val;
	}
	private String resetPasswordEmailTemplate = "";
	public String ResetPasswordEmailTemplate()
	{
		String val = GetConfigValue("ResetPasswordEmailTemplate", resetPasswordEmailTemplate);
		if (!val.isEmpty())
		{
			resetPasswordEmailTemplate = val;
		}
		
		return val;
	}
	private String resetPasswort_ServletBaseUrl = "";
	public String ResetPasswort_ServletBaseUrl()
	{
		String val = GetConfigValue("ResetPasswort_ServletBaseUrl", resetPasswort_ServletBaseUrl);
		if (!val.isEmpty())
		{
			resetPasswort_ServletBaseUrl = val;
		}
		
		return val;
	}
	private String inviteUser_ServletBaseUrl = "";
	public String InviteUser_ServletBaseUrl()
	{
		String val = GetConfigValue("InviteUser_ServletBaseUrl",inviteUser_ServletBaseUrl);
		if (!val.isEmpty())
		{
			inviteUser_ServletBaseUrl = val;
		}
		
		return val;
	}
	private String mailSetting = "";
	public String MailSetting()
	{
		String val = GetConfigValue("MailSetting", mailSetting);
		if (!val.isEmpty())
		{
			mailSetting = val;
		}
		
		return val;
	}
	
	private String transcode_TCE_ETRANSCODE = "";
	public String TCE_ETRANSCODE()
	{
		String val = GetConfigValue("Transcode_TCE_ETRANSCODE", transcode_TCE_ETRANSCODE);
		if (!val.isEmpty())
		{
			transcode_TCE_ETRANSCODE = val;
		}
		
		return val;
	}
	private String transcode_TCE_URI = "";
	public String TCE_URI()
	{
		String val = GetConfigValue("Transcode_TCE_URI", transcode_TCE_URI);
		if (!val.isEmpty())
		{
			transcode_TCE_URI = val;
		}
		
		return val;
	}
	private String transcode_TCE_LOGIN_USER = "";
	public String TCE_LOGIN_USER()
	{
		String val = GetConfigValue("Transcode_TCE_LOGIN_USER", transcode_TCE_LOGIN_USER);
		if (!val.isEmpty())
		{
			transcode_TCE_LOGIN_USER = val;
		}
		
		return val;
	}
	private String transcode_TCE_LOGIN_PASSWORD = "";
	public String TCE_LOGIN_PASSWORD()
	{
		String val = GetConfigValue("Transcode_TCE_LOGIN_PASSWORD", transcode_TCE_LOGIN_PASSWORD);
		if (!val.isEmpty())
		{
			transcode_TCE_LOGIN_PASSWORD = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_H264_4K = "";
	public String TCE_TRANSCODING_PRESET_H264_4K()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_H264_4K", transcode_TCE_TRANSCODING_PRESET_H264_4K);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_H264_4K = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_H264_1080 = "";
	public String TCE_TRANSCODING_PRESET_H264_1080()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_H264_1080", transcode_TCE_TRANSCODING_PRESET_H264_1080);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_H264_1080 = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_H264_720 = "";
	public String TCE_TRANSCODING_PRESET_H264_720()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_H264_720", transcode_TCE_TRANSCODING_PRESET_H264_720);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_H264_720 = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_H264_480 = "";
	public String TCE_TRANSCODING_PRESET_H264_480()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_H264_480", transcode_TCE_TRANSCODING_PRESET_H264_480);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_H264_480 = val;
		}
		
		return val;
	}
	private String transcode_TCE_SERVER_GROUPS = "";
	public String TCE_SERVER_GROUPS()
	{
		String val = GetConfigValue("Transcode_TCE_SERVER_GROUPS", transcode_TCE_SERVER_GROUPS);
		if (!val.isEmpty())
		{
			transcode_TCE_SERVER_GROUPS = val;
		}
		
		return val;
	}
	private String transcode_TCE_LOCATION_OUT = "";
	public String TCE_LOCATION_OUT()
	{
		String val = GetConfigValue("Transcode_TCE_LOCATION_OUT", transcode_TCE_LOCATION_OUT);
		if (!val.isEmpty())
		{
			transcode_TCE_LOCATION_OUT = val;
		}
		
		return val;
	}
	private String transcode_SMIL_GEN_PATH = "";
	public String SMIL_GEN_PATH()
	{
		String val = GetConfigValue("Transcode_SMIL_GEN_PATH", transcode_SMIL_GEN_PATH);
		if (!val.isEmpty())
		{
			transcode_SMIL_GEN_PATH = val;
		}
		
		return val;
	}
	private String transcode_SMIL_OUT_FILE_PREFIX = "";
	public String SMIL_OUT_FILE_PREFIX()
	{
		String val = GetConfigValue("Transcode_SMIL_OUT_FILE_PREFIX", transcode_SMIL_OUT_FILE_PREFIX);
		if (!val.isEmpty())
		{
			transcode_SMIL_OUT_FILE_PREFIX = val;
		}
		
		return val;
	}
	private String transcode_THUMBNAIL_GEN_PATH = "";
	public String THUMBNAIL_GEN_PATH()
	{
		String val = GetConfigValue("Transcode_THUMBNAIL_GEN_PATH", transcode_THUMBNAIL_GEN_PATH);
		if (!val.isEmpty())
		{
			transcode_THUMBNAIL_GEN_PATH = val;
		}
		
		return val;
	}
	private String transcode_THUMBNAIL_OUTPUT_PATH = "";
	public String THUMBNAIL_OUTPUT_PATH()
	{
		String val = GetConfigValue("Transcode_THUMBNAIL_OUTPUT_PATH", transcode_THUMBNAIL_OUTPUT_PATH);
		if (!val.isEmpty())
		{
			transcode_THUMBNAIL_OUTPUT_PATH = val;
		}
		
		return val;
	}
	private String transcode_THUMBNAIL_OUTPUT_PREFIX = "";
	public String THUMBNAIL_OUTPUT_PREFIX()
	{
		String val = GetConfigValue("Transcode_THUMBNAIL_OUTPUT_PREFIX", transcode_THUMBNAIL_OUTPUT_PREFIX);
		if (!val.isEmpty())
		{
			transcode_THUMBNAIL_OUTPUT_PREFIX = val;
		}
		
		return val;
	}
	
	private String transcode_MEDIAINFO_PATH = "";
	public String MEDIAINFO_PATH()
	{
		String val = GetConfigValue("Transcode_MEDIAINFO_PATH", transcode_MEDIAINFO_PATH);
		if (!val.isEmpty())
		{
			transcode_MEDIAINFO_PATH = val;
		}
		
		return val;
	}
	private String uploadMediaUrl = "";
	public String UploadMediaUrl()
	{
		String val = GetConfigValue("UploadMediaUrl", uploadMediaUrl);
		if (!val.isEmpty())
		{
			uploadMediaUrl = val;
		}
		
		return val;
	}
	private String userPhotoUploadUrl = "";
	public String UserPhotoUploadUrl()
	{
		String val = GetConfigValue("UserPhotoUploadUrl", userPhotoUploadUrl);
		if (!val.isEmpty())
		{
			userPhotoUploadUrl = val;
		}
		
		return val;
	}
	private String groupPhotoUplaodUrl = "";
	public String GroupPhotoUplaodUrl()
	{
		String val = GetConfigValue("GroupPhotoUplaodUrl", groupPhotoUplaodUrl);
		if (!val.isEmpty())
		{
			groupPhotoUplaodUrl = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_HEVC_4K = "";
	public String TCE_TRANSCODING_PRESET_HEVC_4K()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_HEVC_4K", transcode_TCE_TRANSCODING_PRESET_HEVC_4K);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_HEVC_4K = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_HEVC_1080 = "";
	public String TCE_TRANSCODING_PRESET_HEVC_1080()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_HEVC_1080", transcode_TCE_TRANSCODING_PRESET_HEVC_1080);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_HEVC_1080 = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_HEVC_720 = "";
	public String TCE_TRANSCODING_PRESET_HEVC_720()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_HEVC_720", transcode_TCE_TRANSCODING_PRESET_HEVC_720);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_HEVC_720 = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_HEVC_480 = "";
	public String TCE_TRANSCODING_PRESET_HEVC_480()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_HEVC_480", transcode_TCE_TRANSCODING_PRESET_HEVC_480);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_HEVC_480 = val;
		}
		
		return val;
	}
	private String transcode_ERROR_RETRY_COUNT = "";
	public String ERROR_RETRY_COUNT()
	{
		String val = GetConfigValue("Transcode_ERROR_RETRY_COUNT", transcode_ERROR_RETRY_COUNT);
		if (!val.isEmpty())
		{
			transcode_ERROR_RETRY_COUNT = val;
		}
		
		return val;
	}
	private String transcode_TCE_TRANSCODING_PRESET_AAC_2CH_192 = "";
	public String TCE_TRANSCODING_PRESET_AAC_2CH_192()
	{
		String val = GetConfigValue("Transcode_TCE_TRANSCODING_PRESET_AAC_2CH_192", transcode_TCE_TRANSCODING_PRESET_AAC_2CH_192);
		if (!val.isEmpty())
		{
			transcode_TCE_TRANSCODING_PRESET_AAC_2CH_192 = val;
		}
		
		return val;
	}
	private String iosP12FilePath = "";
	public String IosP12FilePath()
	{
		String val = GetConfigValue("IosP12FilePath", iosP12FilePath);
		if (!val.isEmpty())
		{
			iosP12FilePath = val;
		}
		
		return val;
	}
	private String iosP12Password = "";
	public String IosP12Password()
	{
		String val = GetConfigValue("IosP12Password", iosP12Password);
		if (!val.isEmpty())
		{
			iosP12Password = val;
		}
		
		return val;
	}
	
	private String Message_Androidpn_URL = "";
	public String Message_Androidpn_URL()
	{
		String val = GetConfigValue("Message_Androidpn_URL", Message_Androidpn_URL);
		if (!val.isEmpty())
		{
			Message_Androidpn_URL = val;
		}
		
		return val;
	}
	private boolean transcode_Enable_RemoveJob = true;
	public boolean TCE_Enable_RemoveJob()
	{
		String val = GetConfigValue("Transcode_Enable_RemoveJob", "1");
		if (!val.isEmpty())
		{
			transcode_Enable_RemoveJob = "1".equals(val) ?  true : false;
		}
		
		return transcode_Enable_RemoveJob;
	}
	private String storageAdapterType = "";
	public String StorageAdapterType()
	{
		String val = GetConfigValue("StorageAdapterType", storageAdapterType);
		if (!val.isEmpty())
		{
			storageAdapterType = val;
		}
		
		return val;
	}
	private String awsAccessKeyId = "";
	public String AwsAccessKeyId()
	{
		String val = GetConfigValue("AwsAccessKeyId", awsAccessKeyId);
		if (!val.isEmpty())
		{
			awsAccessKeyId = val;
		}
		
		return val;
	}
	private String awsSecretKey = "";
	public String AwsSecretKey()
	{
		String val = GetConfigValue("AwsSecretKey", awsSecretKey);
		if (!val.isEmpty())
		{
			awsSecretKey = val;
		}
		
		return val;
	}
	private String awsBucketName = "";
	public String AwsBucketName()
	{
		String val = GetConfigValue("AwsBucketName", awsBucketName);
		if (!val.isEmpty())
		{
			awsBucketName = val;
		}
		
		return val;
	}
	private String awsRegions = "";
	public String AwsRegions()
	{
		String val = GetConfigValue("AwsRegions", awsRegions);
		if (!val.isEmpty())
		{
			awsRegions = val;
		}
		
		return val;
	}
	private String checkWeixinTokenUrl = "";
	public String CheckWeixinTokenUrl()
	{
		String val = GetConfigValue("CheckWeixinTokenUrl", checkWeixinTokenUrl);
		if (!val.isEmpty())
		{
			checkWeixinTokenUrl = val;
		}
		
		return val;
	}
	private String getWeixinUserInfoUrl = "";
	public String GetWeixinUserInfoUrl()
	{
		String val = GetConfigValue("GetWeixinUserInfoUrl", getWeixinUserInfoUrl);
		if (!val.isEmpty())
		{
			getWeixinUserInfoUrl = val;
		}
		
		return val;
	}
	private String getQQUserInfoUrl = "";
	public String GetQQUserInfoUrl()
	{
		String val = GetConfigValue("GetQQUserInfoUrl", getQQUserInfoUrl);
		if (!val.isEmpty())
		{
			getQQUserInfoUrl = val;
		}
		
		return val;
	}
	
	private String getOauth_consumer_key = "";
	public String GetOauth_consumer_key()
	{
		String val = GetConfigValue("GetOauth_consumer_key", getOauth_consumer_key);
		if (!val.isEmpty())
		{
			getOauth_consumer_key = val;
		}
		
		return val;
	}
	private String storageServletBaseUrl = "";
	public String StorageServletBaseUrl()
	{
		String val = GetConfigValue("StorageServletBaseUrl", storageServletBaseUrl);
		if (!val.isEmpty())
		{
			storageServletBaseUrl = val;
		}
		
		return val;
	}
	public String GetConfigValue(String key, String defaultVal)
	{
		return DynamicPropertyFactory.getInstance().getStringProperty(key, defaultVal).getValue();
	}
	
	public int GetConfigValue(String key, int defaultVal)
	{
		return DynamicPropertyFactory.getInstance().getIntProperty(key, defaultVal).getValue();
	}
	
	public boolean GetConfigValue(String key, boolean defaultVal)
	{
		return DynamicPropertyFactory.getInstance().getBooleanProperty(key, defaultVal).getValue();
	}
	
	private HashSet<String>	serviceTokens;
	public boolean IsServiceToken(String token)
	{
		if (serviceTokens == null)
		{
			serviceTokens = new HashSet<String>();
			serviceTokens.add("02c70159-822f-467c-9567-82e23dd90dce");
		}
		
		return serviceTokens.contains(token);
	}
	
	public String ServiceToken()
	{
		return "02c70159-822f-467c-9567-82e23dd90dce";
	}

	private Map<String, String> mapConfigs;
	private static ConfigurationManager instance;
	private ConfigurationManager(){
		String propertyFile = getClass().getResource("/").getFile() + "application.properties";
		
		Properties properties = new Properties();
	    mapConfigs = new HashMap<String, String>();
		try
		{
		    InputStream in = new BufferedInputStream (new FileInputStream(propertyFile));
		    properties.load(in);
		    
		    Enumeration<?> en = properties.propertyNames();
		    
		    while(en.hasMoreElements())
		    {
		    	String key = (String)en.nextElement();
		    	String value = properties.getProperty(key);
		    	mapConfigs.put(key, value);
		    }
		    
		    PolledConfigurationSource source = new ConfigServiceSource(mapConfigs.get("CfmServiceBaseUrl"));
			DynamicConfiguration configuration = new DynamicConfiguration(source, new FixedDelayPollingScheduler(30000, 3600000, true));
			com.netflix.config.ConfigurationManager.install(configuration);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			Util.LogError(log, "ConfigurationManager() Exception: ", ex);
		}
	}
}
