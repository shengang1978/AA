package com.divx.service.domain.manager;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.activemq.filter.function.splitFunction;
import org.apache.log4j.Logger;

import com.divx.service.ConfigurationManager;
import com.divx.service.MediaServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.model.DcpOriginalasset;
import com.divx.service.domain.repository.UploadDao;
import com.divx.service.domain.repository.impl.UploadDaoImpl;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.Upload;


public class StorageHelper {
	private Logger log = Logger.getLogger(StorageHelper.class);
	
	public interface IWatch {
		void Stop();
		void DoTask();	
	}
	public class BaseWatch implements Runnable, IWatch {
		protected boolean exit = false;

		@Override
		public void Stop() {
			exit = true;
			try
			{
				if (Thread.currentThread().getState() != Thread.State.TERMINATED)
				{
					Thread.currentThread().join(60000);
					
					if (Thread.currentThread().getState() != Thread.State.TERMINATED)
					{
						Thread.currentThread().interrupt();
					}
				}
			}
			catch(Exception ex)
			{
				
			}
		}

		@Override
		public void run() {
			while(!exit)
			{
				try
				{
					DoTask();
					Thread.sleep(1000);
				}
				catch(Exception ex)
				{
					
				}
			}
		}

		@Override
		public void DoTask() {
			
			
		}
	}
	
	public class ProcessWatch extends BaseWatch {
		public ProcessWatch() {
			uploadDao = new UploadDaoImpl();		

		}

		@Override
		public void DoTask() {
			try{
				List<DcpOriginalasset> objs = uploadDao.GetUploads();
				if(null == objs){
					return ;
				}
				for(DcpOriginalasset obj : objs){
					NotifyMediaStatus(obj, "");
				}
				
			}catch(Exception ex){
				//ex.printStackTrace();
				Util.LogError(log, "ProcessWatch.DoTask Excption: ", ex);
			}
			
		}
		
		public void NotifyMediaStatus(DcpOriginalasset obj,String token)
		{
			try
			{
				if (obj.getStatus() == Upload.eUploadStatus.predone.ordinal())
					obj.setStatus(Upload.eUploadStatus.done.ordinal());
				Upload up = new Upload();
				up.setContentType(MediaBaseType.eContentType.values()[obj.getContenttype()]);
				up.setMediaId(obj.getMediaId());
				up.setUploadId(obj.getOriginalassetId());
				up.setStatus(obj.getStatus());
				up.setFileurl(obj.getFileurl());
				up.setShareJson(obj.getSharejson());
				up.setV2gJson(obj.getV2gjson());
				up.setLessonId(obj.getLessonid());
				up.setContentSettings(obj.getContentSettings());
				
				ServiceResponse sr = new MediaServiceHelper().UpdateMediaStatus(token, up);
				
				if(sr.getResponseCode() == ResponseCode.SUCCESS){
					obj.setProcessed(true);
					obj.setErrorMessage("");
				}else{
					obj.setAttempts(obj.getAttempts() + 1);
					obj.setErrorMessage(String.format("code:%d. message:%s", sr.getResponseCode(), sr.getResponseMessage()));
				}
				obj.setDatemodified(new Date());
				uploadDao.UpdateUploadInfo(obj);
			}
			catch(Exception e)
			{
				if (obj.getStatus() == Upload.eUploadStatus.predone.ordinal())
					obj.setStatus(Upload.eUploadStatus.done.ordinal());
				
				obj.setErrorMessage("NotifyMediaStatus Exception");
				uploadDao.UpdateUploadInfo(obj);
				throw e;
			}
		}
	}
	private UploadDao uploadDao;
	 
	 public class WatchMonitor implements Runnable{

			private Thread processWacth;
			public WatchMonitor(){
				processWacth = new Thread(new ProcessWatch());
				processWacth.start();
			}
			@Override
			public void run() {
				while(true)
				{
					try
					{
						if(!processWacth.isAlive()) {
							processWacth = new Thread(new ProcessWatch());
							processWacth.start();
						}
						Thread.sleep(120000);	//Check the thread status every 2 min.
					}
					catch(Exception ex)
					{
						
					}			
				}
			}	
			 
	}
	
}
