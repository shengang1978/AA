package com.divx.service.domain.manager;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpOriginalasset;
import com.divx.service.domain.repository.UploadDao;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.Upload;
import com.divx.service.model.UploadInfoResponse;

@Service
public class UploadManager {
	private Logger log = Logger.getLogger(UploadManager.class);
	
	private UploadDao uploadDao;
	
	@Autowired
	public void setUploadDao(UploadDao uploadDao)
	{
		this.uploadDao = uploadDao;
	}
	static com.divx.service.domain.manager.StorageHelper.WatchMonitor monitor;
	static Thread threadMonitor;
	
	static{
		StorageHelper helper = new StorageHelper();
		monitor = helper.new WatchMonitor();
		
		threadMonitor = new Thread(monitor);
		threadMonitor.start();
	  }
	public UploadInfoResponse GetUploadInfo(int mediaId, MediaBaseType.eFileType fileType)
	{
		if(!threadMonitor.isAlive()) {
			threadMonitor = new Thread(monitor);
			threadMonitor.start();
		}
		UploadInfoResponse  res= new UploadInfoResponse();
		try{
			Upload info = new Upload();
			List<DcpOriginalasset> assets = uploadDao.GetUploadInfo(mediaId, fileType);
			if (assets != null && assets.size() > 0)
			{		
				DcpOriginalasset asset = assets.get(0);
				info.setUploadId(asset.getOriginalassetId());
				info.setMediaId(asset.getMediaId());
				info.setTotalSize(asset.getTotalsize());
				info.setEndPosition(asset.getUploadpos());
				info.setStatus(asset.getStatus());
				info.setFileurl(asset.getFileurl());
				info.setContentType(eContentType.values()[asset.getContenttype()]);
				info.setFileType(MediaBaseType.eFileType.values()[asset.getFiletype()]);
			}
			else
			{
				info.setMediaId(mediaId);
				info.setContentType(eContentType.SMIL);
				info.setStatus(Upload.eUploadStatus.none);
				info.setFileType(MediaBaseType.eFileType.Auto);
			}

			res.setUploadInfo(info);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("GetUploadInfo(%d) exception. ", mediaId), ex);
		}
		
		return res;
	}
	
	public ServiceResponse SetUploadInfo(String token, Upload info)
	{
		ServiceResponse res = new ServiceResponse();
		try{
			DcpOriginalasset obj = null;
			if (info.getUploadId() <= 0)
			{
				obj = new DcpOriginalasset();
				obj.setDatecreated(new Date());
				obj.setOriginalassetId(info.getUploadId());
				obj.setDeleted(false);
				obj.setCompleted(false);
				obj.setMediaId(info.getMediaId());
			}
			else
			{
				obj = uploadDao.GetOriginalasset(info.getUploadId());
			}
			
			obj.setDatemodified(new Date());
			obj.setStatus(info.getStatus().ordinal());
			obj.setTotalsize(info.getTotalSize());
			obj.setFileurl(info.getFileurl());
			obj.setUploadpos(info.getEndPosition());
			obj.setFilename(info.getFilename());
			obj.setProcessed(false);
			obj.setAttempts(0);
			obj.setContenttype(info.getContentType().ordinal());
			obj.setSharejson(info.getShareJson());
			obj.setV2gjson(info.getV2gJson());
			obj.setLessonid(info.getLessonId());
			obj.setContentSettings(info.getContentSettings());
			if (info.getFileType() == null)
				obj.setFiletype(MediaBaseType.eFileType.Auto.ordinal());
			else
				obj.setFiletype(info.getFileType().ordinal());
			int uiId = uploadDao.UpdateUploadInfo(obj);
			if(uiId > 0){
				new StorageHelper().new ProcessWatch().NotifyMediaStatus(obj,token);

				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_UPDATE_MEDIA);
				res.setResponseMessage("Fail to Upload media");
			}
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
			Util.LogError(log, String.format("SetUploadInfo(%s) exception. ", Util.ObjectToJson(info)), ex);
		}
		return res;
	}
	
	public ServiceResponse CancelUpload(int mediaId){
		ServiceResponse res = new ServiceResponse();
		try{
			List<DcpOriginalasset> objs = uploadDao.GetUploadInfo(mediaId, null);

			if(objs == null || objs.size() == 0){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to get Upload media info.");
				return res;
			}
			
			int uiId = 0;
			for(DcpOriginalasset obj: objs)
			{
				obj.setDeleted(true);
				obj.setDatemodified(new Date());
				uiId = uploadDao.UpdateUploadInfo(obj);
			}
			if(uiId > 0){
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("Success");
			}else{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to CancleUpload media");	
			}
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
		
	}
	
	public ServiceResponse SetShareInfo(ShareOption shareOption){
		ServiceResponse res = new ServiceResponse();
		try{
			

			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		return res;
	}
}
