package com.divx.service.model;

public abstract class MessageCategory {
	
	public static final int NORMAL_MESSAGE = 0;
	
	// User Message Category
	//	1xxxx
	public static final int USER_MESSAGE_TO_FRIEND = 10001;
	public static final int USER_MESSAGE_TO_GROUP = 10002;
	public static final int USER_MESSAGE_TO_ALLFRIENDS = 10003;
	
	// System Message Category
	//	user service message
	//		2xxxx
	
	//	social service message
	//		3xxxx
	public static final int SOCIAL_SERVICE_MESSAGE_TO_PERSON = 30001;
	public static final int SOCIAL_SERVICE_MESSAGE_TO_GROUP = 30002;
	
	public static final int SOCIAL_SERVICE_MESSAGE_REQUEST_FRIEND = 31000;
	//	media service message
	//		4xxxx
	public static final int MEDIA_SERVICE_MESSAGE_PUBLISH_COMPLETED = 40000;
	public static final int MEDIA_SERVICE_MESSAGE_TRANSCODE_FAILED = 40001;
	
	//	transcode service message
	//		5xxxx
}
