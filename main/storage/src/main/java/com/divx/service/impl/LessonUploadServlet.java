
package com.divx.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.ConfigurationManager;
import com.divx.service.MediaServiceHelper;
import com.divx.service.Util;
import com.divx.service.model.FileUpload;
import com.divx.service.model.LessonFileUpload;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.Upload;
import com.divx.service.model.UploadInfoResponse;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.edu.Lesson;
import com.divx.service.model.edu.LessonsResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.divx.service.ImageFileUpload.PhotoUrlResponse;
import com.divx.service.domain.manager.StorageHelper;
import com.divx.service.domain.manager.UploadManager;
import com.divx.service.domain.repository.UploadDao;
import com.divx.service.domain.repository.impl.*;

public class LessonUploadServlet extends HttpServlet {

	private Logger log = Logger.getLogger(LessonUploadServlet.class);
	private UploadManager uploadManager;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ServiceResponse res = new ServiceResponse();
		RandomAccessFile raf = null;
		try{
			
			DiskFileItemFactory factory = new DiskFileItemFactory(); 
			factory.setSizeThreshold(4096);
			factory.setRepository(new File(ConfigurationManager.GetInstance().UploadTempFolder()));
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> fields = upload.parseRequest(request);
			AuthHelper helper = null;
			LessonFileUpload fu = null;
			Lesson lessonifo = new Lesson();
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
						case "LessonFileUpload":
							fu = Util.JsonToObject(fi.getString(), LessonFileUpload.class);
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
			else if (helper.isGuest())
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			    res.setResponseMessage("Token is invalid or not login.");
			   
			}else{
				   boolean bWriteToDst = false;
				   OutputStream os = null;
				   lessonifo.setCategory(fu.getCategory());
				   lessonifo.setMediaId(fu.getMediaId());
				   lessonifo.setNumber(fu.getNumber());
				   lessonifo.setTitle(fu.getTitle());
				   try{
					   
				   
					String filename = String.format("OA_%d_%d%s", helper.getUserId(),new Date().getTime(),fileExt);
						
					
					assetDestLocation = Util.UrlWithSlashes(ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH()) + filename;
					
					os = new FileOutputStream(new File(assetDestLocation));
	
				    int bufSize = 100000;
				    byte[] b = new byte[bufSize];
				    int bytesRead = 0;
				    int totalWrite = 0;
	
				    while ((bytesRead = in.read(b)) != -1) {
				    	os.write(b, 0, bytesRead);
				    	totalWrite += bytesRead;
				    }
				    
				    bWriteToDst = true;
				    //lessonifo.setLessonZipUrl(assetDestLocation);
				    
				   }catch(Exception Ex){
					   
				   }
				   	os.flush();
				    os.close();
				    in.close();		  
				  
					if (bWriteToDst){
						ServiceResponse	sr = MediaServiceHelper.UpdateLessonInfo(lessonifo);
						res.setResponseCode(sr.getResponseCode());
						res.setResponseMessage(sr.getResponseMessage());	
								  
					}else{
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
		out.println(Util.ObjectToJson(res));
	}
	
	
}
