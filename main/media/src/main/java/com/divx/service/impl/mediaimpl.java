package com.divx.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.ConfigurationManager;
import com.divx.service.ServiceHeaderHelper;
import com.divx.service.Util;
import com.divx.service.media;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.model.*;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.Upload.eUploadStatus;

public class mediaimpl implements media {

	private MediaManager mediaManager;
	
	@Autowired
	public void setMediaManager(MediaManager mediaManager)
	{
		this.mediaManager = mediaManager;
	}
	
	@Override
	public Response CreateMedia(MediaBase media) {
		CreateMediaResponse res = new CreateMediaResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		}
		else
		{
			try
			{
				int mediaId = mediaManager.CreateMedia(media, helper.getUserId());
				if (mediaId > 0)
				{
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("Success");
					res.setMediaId(mediaId);
				}
				else
				{
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to create media");
				}
			}
			catch(Exception ex)
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage(ex.getMessage());
				ex.printStackTrace();
			}
		}
		
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response GetMedia(int mediaId) {
		AuthHelper helper = new AuthHelper();
		int userId = 0;
		DcpBaseType.eDeviceType deviceType = DcpBaseType.eDeviceType.Android;
		if (!helper.isGuest())
		{
			userId = helper.getUserId();
			deviceType = helper.getDeviceType();
		}
		else
		{
			ServiceHeaderHelper shh = new ServiceHeaderHelper();
			String strDeviceType = shh.getDeviceType();
			if (strDeviceType != null && !strDeviceType.isEmpty())
			{
				try
				{
					deviceType = DcpBaseType.eDeviceType.values()[Integer.parseInt(strDeviceType)];
				}
				catch(Exception ex)
				{
					
				}
			}
		}
		
		MediaResponse res = new MediaResponse();
		
		try
		{
			Media m = mediaManager.GetMedia(helper.getUserId(), mediaId, deviceType);
			
			if (m != null)
			{
				if (m.getContentType() == eContentType.EduBook ||
						m.getContentType() == eContentType.EduBookURL)
				{//英阅馆Media, set the Lesson & score.
					
				}
				res.setMedia(m);
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}
			else
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Cannot find the media");
			}		
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}

		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response GetUploadInfo(int mediaId) {
		UploadInfoResponse res = new UploadInfoResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		}

		return Util.ServiceResponseToResponse(mediaManager.GetUploadInfo(helper.getToken(), mediaId));
	}

	@Override
	public Response EditAndPublish(int mediaId) {
		ServiceResponse res = new ServiceResponse();
		
		AuthHelper helper = new AuthHelper();
		if (!helper.isGuest())
		{
			int reqUserId = helper.getUserId();
		}
		
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response CancelEdit(int mediaId) {
		ServiceResponse res = new ServiceResponse();
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response Publish(int mediaId) {
		
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		}
		
		return Util.ServiceResponseToResponse(mediaManager.Publish(helper.getUserId(), mediaId));
	}

	@Override
	public Response CancelPublish(int mediaId) {
		ServiceResponse res = new ServiceResponse();
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response GetMediaStatus(int mediaId) {
		MediaStatusResponse res = new MediaStatusResponse();
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		
		MediaStatus ms = new MediaStatus();
		ms.setMediaId(mediaId);
		ms.setPercent(60);
		ms.setStatus(1);
		
		res.setStatus(ms);
		
		return Util.ServiceResponseToResponse(res);
	}

	@Override
	public Response EndPublish(EndPublishOption option) {
		
		return Util.ServiceResponseToResponse(mediaManager.EndPublish(option));

	}

	@Override
	public Response EndEdit(int assetId, String assetLocation) {
		
		return Publish(assetId);
	}

	@Override
	public Response GetRecommendMedias(int startPos, int endPos) {
		
		return Util.ServiceResponseToResponse(mediaManager.GetPublicMedias(startPos, endPos));
	}

	

	@Override
	public Response MyMedias(int startPos, int endPos) {
		MediasResponse res = new MediasResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		}
		
		
		if (startPos > endPos || startPos < 0 || endPos < 0) {
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("Error startPos or endPos");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		
		List<Media> medias = mediaManager.GetMyMedias(helper.getAppType(), helper.getUserId(), startPos, endPos);
		res.setMedias(medias);
		res.setStartPos(startPos);
		res.setEndPos(startPos + medias.size() - 1);
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		
		return Util.ServiceResponseToResponse(res);
	}


	@Override
	public Response MyStories(int startPos, int endPos) {
		MediasResponse res = new MediasResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.UNAUTHORIZED).entity(res).build();
		}		
		
		if (startPos > endPos || startPos < 0 || endPos < 0) {
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("Error startPos or endPos");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		
		List<Media> medias = mediaManager.MyStories(helper.getAppType(), helper.getUserId(), startPos, endPos);
		res.setMedias(medias);
		res.setStartPos(startPos);
		res.setEndPos(startPos + medias.size() - 1);
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		
		return Util.ServiceResponseToResponse(res);
	}
	
	@Override
	public Response UpdateMedia(MediaBase media) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		
		return Util.ServiceResponseToResponse(mediaManager.UpdateMedia(media, helper.getUserId()));
	}

	@Override
	public Response DeleteMedia(int mediaId) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		
		return Util.ServiceResponseToResponse(mediaManager.DeleteMedia(mediaId, helper.getUserId()));
	}

	@Override
	public Response CancelUpload(int mediaId) {
		ServiceResponse res = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}
		return Util.ServiceResponseToResponse(mediaManager.CancelUpload(mediaId,helper.getToken()));
	}

	@Override
	public Response CompleteUpload(int mediaId) {
		return Response.status(Status.NOT_IMPLEMENTED).build();
	}

	@Override
	public Response UpdateUploadInfo(Upload uploadinfo) {
		/*ServiceResponse res  = new ServiceResponse();
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Token is invalid or not login.");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
		}*/
		AuthHelper helper = new AuthHelper();
		return Util.ServiceResponseToResponse(mediaManager.UpdateUploadInfo(helper.getToken(), uploadinfo));
	}
	
	@Override
	public Response TransferMedia(TransferOption option) {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}	
		

		return Util.ServiceResponseToResponse(mediaManager.TransferMedia(helper.getUserId(), option));
	}

	@Override
	public Response MyScores() {
		AuthHelper helper = new AuthHelper();
		if (helper.isGuest())
		{
			ServiceResponse res = new ServiceResponse();
			res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			res.setResponseMessage("Invalid Token or Not Login");
			return Util.ServiceResponseToResponse(res);
		}
		return Util.ServiceResponseToResponse(mediaManager.MyScores(helper.getUserId()));
	}


}
