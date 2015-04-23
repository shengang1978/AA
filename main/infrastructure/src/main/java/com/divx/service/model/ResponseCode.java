package com.divx.service.model;

// Response code of the web service
public abstract class ResponseCode {
	// Success
	public static final int SUCCESS = 0;
	
	//Internal error such as the exception...
	public static final int ERROR_INTERNAL_ERROR = -1;
	
	//Parameter error.
	public static final int ERROR_INVALID_PARAMETER = -2;
	
	public static final int ERROR_NO_PERMISSION = -3;
	
	public static final int ERROR_NOT_IMPLEMENTED = -4;
	
	// Login Error: Wrong username or password
	public static final int AUTH_ERROR_USERNAME_OR_PASSWORD_INVALID	= -1000;
	
	// The token is invalid. It means the token is expired or not login.
	public static final int AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN = -1001;
	
	// Password doesn't match the rule.
	//   Or password and repassword are not same.
	public static final int ERROR_REGISTER_USER_INVALID_PASSWORD = -1002;
	
	//change password: new password cannot be same with the original password
	public static final int ERROR_NEW_PASSWORD_CANNOT_BE_SAME_ORIGINAL_PASSWORD = -1003;
	
	// The token is expired.
	public static final int AUTH_ERROR_TOKEN_IS_EXPIRED = -1004;
	
	// Register User: username has existed.
	public static final int ERROR_REGISTER_USER_USERNAME_EXIST = -2001;
	
	// Register User: email has existed.
	public static final int ERROR_REGISTER_USER_EMAIL_EXIST = -2002;
	
	// Register User: mobile has existed.
	public static final int ERROR_REGISTER_USER_MOBILE_EXIST = -2003;
	
	// Update User: username has been set.
	public static final int ERROR_UPDATE_USER_USERNAME_HAVE_SET = -2101;
	
	// Update User email/mobile: username isn't set. Cannot update the email or the mobile.
	public static final int ERROR_UPDATE_USER_USERNAME_NOT_SET = -2102;
	
	//ResetPassword:user is not set email
	public static final int ERROR_USER_EMAIL_NOT_EXIST  = -2103;
	
	//Send Email Error
	public static final int ERROR_SEND_EMAIL  = -2200;
	
	//InviteUser Email has been sent
	public static final int EMAIL_HAS_BEEN_SENT  = -2201;
	
	// Only two status (uploaded, edited) are allowed to publish.
	public static final int ERROR_MEDIA_STATUS_TO_PUBLISH = -3001;
	
	// Error status to share the media
	public static final int ERROR_MEDIA_STATUS_TO_SHARE = -3002;
	
	// Find Users:
	// 	Not find.
	public static final int RESULT_USER_NOT_FOUND = -3100;
	
	// User exists in the group when adding a friend to group.
	public static final int RESULT_USER_EXIST_IN_GROUP = -3200;
	
	// Upload errors: wrong upload start position such as the position is larger than the end position.
	public static final int RESULT_WRONG_UPLOAD_START_POSITION = -3300;
	
	// The media asset has been uploaded. 
	// We don't allow to reupload if the asset has been uploaded.
	public static final int RESULT_FILE_HAS_UPLOADED = -3301;
	
	//update media error
	public static final int ERROR_UPDATE_MEDIA = -3302;
	
	//the media is not existed
	public static final int MEDIA_NOT_EXISTED = -3303;
	
	// Transcode failed when publishing the media.
	public static final int ERROR_PUBLISH_MEDIA_FAILED = -3400;
	
	public static final int ERROR_TRANSCODE_GENERATE_THUMB_TIMEOUT = -3401;
	
	public static final int ERROR_TRANSCODE_THUMB_NOT_EXIST = -3402;
	
	public static final int ERROR_TRANSCODE_GENERATE_THUMB_FAIL = -3403;
	
	// Transcode failed. No smil file is generated.
	public static final int ERROR_PUBLISH_MEDIA_INVALID_SMIL = -3401;
	
	//the group is not existed
	public static final int ERROR_GROUP_NOT_EXIST = -3501;
}
