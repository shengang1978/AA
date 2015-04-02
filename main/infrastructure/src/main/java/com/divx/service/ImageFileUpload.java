package com.divx.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;






import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;


public class ImageFileUpload {
	public class FileUpload {
		private int totalSize;
		private String filename;
		private int targetId;
		
		public int getTotalSize() {
			return totalSize;
		}
		public void setTotalSize(int totalSize) {
			this.totalSize = totalSize;
		}
		
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public int getTargetId() {
			return targetId;
		}
		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}
	}
	public class PhotoUrlResponse extends ServiceResponse{
		private String photoUrl;
		private int targetId;
		public String getPhotoUrl() {
			return photoUrl;
		}

		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}

		public int getTargetId() {
			return targetId;
		}

		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}
		
	}
	public PhotoUrlResponse Upload(HttpServletRequest request) throws IOException{
		PhotoUrlResponse res = new PhotoUrlResponse();
		RandomAccessFile raf = null;
		try{
			
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			factory.setSizeThreshold(4096);
			factory.setRepository(new File(ConfigurationManager.GetInstance().UploadTempFolder()));
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> fields = upload.parseRequest(request);
			AuthHelper helper = null;
			FileUpload fu = null;
		
			String assetDestLocation = "";
			String fileExt = ".dcp";
			
			InputStream in = null;
			if (fields != null && fields.size() > 0)
			{
				for(int i = 0; i < fields.size(); ++i)
				{
					FileItem fi = (FileItem)fields.get(i);
					if (fi.isFormField())
					{
						switch(fi.getFieldName())
						{
						case "Token":
							helper = new AuthHelper(fi.getString());
							break;
						case "FileUpload":
							fu = Util.JsonToObject(fi.getString(), FileUpload.class);
							break;
						}
					}
					else
					{
						in = fi.getInputStream();

						if (fi.getName() != null)
						{
							int nIndex = fi.getName().lastIndexOf(".");
							if (nIndex >= 0)
							{
								fileExt = fi.getName().substring(nIndex);	
							}						
						}
					}
				}
			}
			
			if (helper == null || fu == null || fileExt == "" || in == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			    res.setResponseMessage("Invalid Parameter.");
			   
			}
			else if (helper.isGuest()/* || helper.getUserId() != fu.getUserId()*/)
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			    res.setResponseMessage("Token is invalid or not login.");
			   
			}else{

					String filename = String.format("OA_%d_%d%s", helper.getUserId(),new Date().getTime(),fileExt);
						
					
					assetDestLocation = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH()) + filename;
					
					OutputStream os = new FileOutputStream(new File(assetDestLocation));
	
				    int bufSize = 100000;
				    byte[] b = new byte[bufSize];
				    int bytesRead = 0;
				    int totalWrite = 0;
	
				    while ((bytesRead = in.read(b)) != -1) {
				    	os.write(b, 0, bytesRead);
				    	totalWrite += bytesRead;
				    }
				    os.flush();
				    os.close();
				    in.close();		   
				  
					if (fu.getTotalSize() > 0 )
					   {
						   	if(fu.getTargetId() == 0){
						   		res.setTargetId(helper.getUserId());
						   		res.setPhotoUrl(filename);
						   	}else{
						   		res.setTargetId(fu.getTargetId());
						   		res.setPhotoUrl(String.format("%s|%s", helper.getToken(),filename));
						   	}
								res.setResponseCode(ResponseCode.SUCCESS);
							    res.setResponseMessage("Success");
								  
						}else
						    {
						   		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
								res.setResponseMessage("fail to upload photo");
								   
						   	}
					   		
				   
			}	
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
		    res.setResponseMessage(ex.getMessage());
		    ex.printStackTrace();
		}
		finally
		{
			if (raf != null)
				raf.close();
		}
		return res;
	}
	
}
