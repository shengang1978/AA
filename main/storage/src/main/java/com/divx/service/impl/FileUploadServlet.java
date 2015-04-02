
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

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.AuthHelper;
import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.FileUpload;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.ShareOption;
import com.divx.service.model.Upload;
import com.divx.service.model.UploadInfoResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.log4j.Logger;

import com.divx.service.domain.manager.StorageHelper;
import com.divx.service.domain.manager.UploadManager;
import com.divx.service.domain.repository.UploadDao;
import com.divx.service.domain.repository.impl.*;

public class FileUploadServlet extends HttpServlet {

	private Logger log = Logger.getLogger(FileUploadServlet.class);
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
		
		UploadInfoResponse res = new UploadInfoResponse();
		RandomAccessFile raf = null;
		try
		{
			if (uploadManager == null)
			{
				uploadManager = new UploadManager();
				UploadDao uploadDao = new UploadDaoImpl();
				uploadManager.setUploadDao(uploadDao);
			}			
			
			AuthHelper helper = null;
			FileUpload fu = null;
			ShareOption shareOption = null;
			
			String assetTmpLocation = "";		
			String assetDestLocation = "";
			String fileExt = ".mp4";
			InputStream in = null;
			
			String strToken = request.getParameter("Token");
			String strFileUpload = request.getParameter("FileUpload");
			String strShareOption = request.getParameter("ShareOption");
			if (strToken != null && !strToken.isEmpty() && strFileUpload != null && !strFileUpload.isEmpty())
			{
				in = request.getInputStream();
				helper = new AuthHelper(strToken);
				fu = Util.JsonToObject(strFileUpload, FileUpload.class);
				if(strShareOption != null && !strShareOption.isEmpty()){
					shareOption = Util.JsonToObject(strShareOption, ShareOption.class);
				}
				
				int nIndex = fu.getFilename().lastIndexOf("."); 
				if (nIndex >= 0)
					fileExt = fu.getFilename().substring(nIndex);
			}
			else
			{
				if (!ServletFileUpload.isMultipartContent(request))
				{
					return;
				}
				
				DiskFileItemFactory factory = new DiskFileItemFactory(); 
				factory.setSizeThreshold(4096);
				factory.setRepository(new File(ConfigurationManager.GetInstance().UploadTempFolder()));
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> fields = upload.parseRequest(request);
	
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
							case "ShareOption":
								shareOption = Util.JsonToObject(fi.getString(), ShareOption.class);
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
									fileExt = fi.getName().substring(fi.getName().lastIndexOf("."));	
								}						
							}
						}
					}
				}
			}
			
			if (fu == null || fileExt == "" || in == null)
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			    res.setResponseMessage("Invalid Parameter.");
			}
			else if (helper == null || helper.isGuest())
			{
				res.setResponseCode(ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN);
			    res.setResponseMessage("Token is invalid or not login.");
			}
			else
			{
				fileExt = GetFileExtByFileUpload(fileExt, fu);
				
				UploadInfoResponse uploadinfores = uploadManager.GetUploadInfo(fu.getMediaId());
				Upload info = null;
				if(uploadinfores.getResponseCode() == 0 && uploadinfores.getUploadInfo() != null){
					info = uploadinfores.getUploadInfo();
				}
				
				if (fu.getContentType() != null)
					info.setContentType(fu.getContentType());

				int nStartPosition = 0;
				if (info != null && info.getStatus() != Upload.eUploadStatus.none)
				{
					nStartPosition = info.getEndPosition();
				}
				
				if (nStartPosition < fu.getUploadPos())
				{
					res.setUploadInfo(info);
					res.setResponseCode(ResponseCode.RESULT_WRONG_UPLOAD_START_POSITION);
				    res.setResponseMessage("Invalid upload start position.");
				}
				else if (info != null && info.getStatus() == Upload.eUploadStatus.done && info.getTotalSize() > 0)
				{
					res.setUploadInfo(info);
					res.setResponseCode(ResponseCode.RESULT_FILE_HAS_UPLOADED);
				    res.setResponseMessage("Asset has been uploaded fully.");
				}
				else
				{
					if (fu.getTotalSize() > 0)
					{
						nStartPosition = fu.getUploadPos();
					}
					
					String filename = String.format("OA_%d_%d_%d%s", 
							helper.getUserId(),
							fu.getMediaId(),
							new Date().getTime(),
							fileExt);
					assetTmpLocation = ConfigurationManager.GetInstance().UploadTempFolder() + "/" + filename;
					if (info == null || info.getStatus() == Upload.eUploadStatus.none)
					{						
						if (fu.getContentType() == MediaBaseType.eContentType.Gif ||
							fu.getContentType() == MediaBaseType.eContentType.File)
							assetDestLocation = Util.UrlWithSlashes(ConfigurationManager.GetInstance().TCE_LOCATION_OUT()) + filename;
						else
							assetDestLocation = Util.UrlWithSlashes(ConfigurationManager.GetInstance().UploadDestFolder()) + filename;
					}
					else
					{
						assetDestLocation = info.getFileurl();
					}
					
					OutputStream os = new FileOutputStream(new File(assetTmpLocation));
	
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
				    
				    if (info == null)
				    {
				    	info = new Upload();
				    }
				    
				    info.setTotalSize(fu.getTotalSize());
				    info.setMediaId(fu.getMediaId());
				    int nEndPosition = nStartPosition + totalWrite;
				    if (fu.getTotalSize() <= 0 || fu.getTotalSize() <= nEndPosition)
				    	info.setStatus(Upload.eUploadStatus.predone.ordinal());
				    else
				    	info.setStatus(Upload.eUploadStatus.uploading.ordinal());
				    
				    info.setEndPosition(nEndPosition);
				    info.setFilename(fu.getFilename());
				    info.setFileurl(assetDestLocation);
				    
				    boolean bWriteToDst = false;
				    if (nStartPosition > 0)
				    {
				    	FileInputStream fis = null;
				    	FileOutputStream fos = null;
				    	try
				    	{
					    	//Resume the uploading at the breakpoint
					    	// or uploading the being recorded asset
					    	raf = new RandomAccessFile(assetDestLocation, "rw");
					    	raf.seek(nStartPosition);
					    	fis = new FileInputStream(new File(assetTmpLocation));
					    	fos = new FileOutputStream(raf.getFD());
					    	bytesRead = 0;
						    totalWrite = 0;
			
						    while ((bytesRead = fis.read(b)) != -1) {
						    	fos.write(b, 0, bytesRead);
						    	totalWrite += bytesRead;
						    }						    
						    
						    bWriteToDst = true;
				    	}
				    	catch(Exception ex)
				    	{
				    		Util.LogError(log, String.format("Append file from (%s) to (%s) exception", assetTmpLocation, assetDestLocation), ex);
				    	}
				    	finally
				    	{
				    		if (fis != null)
				    		{
				    			fis.close();
				    		}
				    		
				    		if (fos != null)
				    		{
				    			fos.flush();
				    			fos.close();
				    		}
				    	}
				    }
				    else
				    {
					    File srcFile = new File(assetTmpLocation);
					    File dstFile = new File(assetDestLocation);
					    bWriteToDst = srcFile.renameTo(dstFile);
					    if (!bWriteToDst)
					    {
					    	log.error(String.format("Uploading Error. Fail to rename file (%s to %s)", 
										    			assetTmpLocation, 
										    			assetDestLocation));
					    }
				    }
				    
				    if (bWriteToDst)
				    {	
				    	ServiceResponse pubRes = uploadManager.SetUploadInfo(info,helper.getToken(),shareOption);
				    	if (pubRes.getResponseCode() == ResponseCode.SUCCESS)
					   	{
					   		if (fu.getTotalSize() <= 0 || (info.getStatus() != Upload.eUploadStatus.done && info.getStatus() != Upload.eUploadStatus.predone)){

					   			res.setUploadInfo(info);
					   			res.setResponseCode(ResponseCode.SUCCESS);
					   			if (fu.getTotalSize() == 0)
					   				res.setResponseMessage("Success. Recording and Uploading.");
					   			else
					   				res.setResponseMessage("Success. Uploading isn't completed.");
					   		}
					   	}
				    }
				   	else
				   	{
					    res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					    res.setResponseMessage("Fail to move the upload file to dest folder.");
				   		info.setStatus(Upload.eUploadStatus.none);
				   		log.info(String.format("Fail to copy/move the %s to %s", assetTmpLocation, assetDestLocation));
				   	}
				   	
				    res.setUploadInfo(info);
				}
			}
		}
		catch(Exception ex)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
		    res.setResponseMessage("Exception. " + ex.getMessage());
		    //ex.printStackTrace();
		    Util.LogError(log, String.format("Upload Exception"), ex);
		}
		finally
		{
			if (raf != null)
				raf.close();
		}
		
		out.println(Util.ObjectToJson(res));
	}

	private String GetFileExtByFileUpload(String ext, FileUpload fu)
	{
		String result = ext;
		if (fu != null)
		{
			switch(fu.getContentType())
			{
			case Gif:
				result = ".gif";
				break;
			default:
				break;
			}
		}
		
		return result;
	}
}
