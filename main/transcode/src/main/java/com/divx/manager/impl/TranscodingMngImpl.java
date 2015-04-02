package com.divx.manager.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.divx.common.main.Constants;
import com.divx.common.main.MediaInfo;
import com.divx.common.main.TCESOAPUtils;
import com.divx.common.main.Constants.eProcessErrorCode;
import com.divx.manager.TranscodingMng;
import com.divx.service.ProcessWithTimeout;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.dao.TranscodeOutputDao;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.service.domain.model.DcpTranscodeOutput;
import com.divx.service.model.ProcessResponse;
import com.divx.service.model.ProcessResponse.Status;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.Thumbnail;
import com.divx.service.model.ThumbnailsResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TranscodingMngImpl implements TranscodingMng {
	
	private static final Logger log = Logger.getLogger(TranscodingMngImpl.class);
	
	static WatchMonitor monitor;
	static Thread threadMonitor;
	
	static {
		monitor = new WatchMonitor();
		
		threadMonitor = new Thread(monitor);
		threadMonitor.start();
	}
	
	private static String CMD_THUMBNAIL_GEN = "%s -i %s -ss 00:00:00 -an -r 1 -vframes 1 -y -s %s %s %s";
	private static String THUMBNAIL_RESOLUTION = "640x360";
	private TranscodeDao transcodeDao;
	private TranscodeOutputDao transcodeOutputDao;
	
	@Autowired
	public void setTranscodeDao(TranscodeDao transcodeDao)
	{
		this.transcodeDao = transcodeDao;
	}

	@Autowired
	public void setTranscodeOutputDao(TranscodeOutputDao transcodeOutputDao)
	{
		this.transcodeOutputDao = transcodeOutputDao;
	}
	
	@Override
	public ProcessResponse getTranscodingStatus(int assetId){
		ProcessResponse res = new ProcessResponse();
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		
		DcpTranscode objTrans = transcodeDao.GetTranscodeByAssetId(assetId);
		if(null == objTrans) {
			log.error("Failed to get DcpTranscode: asset id is " + assetId);
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage("Cannot find transcode record for asset id " + assetId);
			return res;
		}
		
		int status =  objTrans.getStatus();
		if(Status.finished.ordinal() == status || Status.error.ordinal() == status || Status.aborted.ordinal() == status) {
			DcpTranscodeOutput transcodeOutput = transcodeOutputDao.GetByTranscodeId(objTrans.getTranscodeId());
			if(null == transcodeOutput) {
				log.error("Failed to get DcpTranscodeOutput: transcode id is " + objTrans.getTranscodeId());
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Cannot find transcode output record for transcode id " + objTrans.getTranscodeId());
				return res;
			}
			
			res.setPercent(100);
			res.setSmilUrl(transcodeOutput.getFileurl());
			res.setStatus(transcodeOutput.getStatus());
		} 
		else {
			res.setPercent(50);
			res.setSmilUrl("");
			res.setStatus(objTrans.getStatus());
		}
	
		return res;
	}

	@Override
	public ServiceResponse startTranscoding(int assetId, String filePath) {
		if(!threadMonitor.isAlive()) {
			threadMonitor = new Thread(monitor);
			threadMonitor.start();
		}
		
		ServiceResponse res = new ServiceResponse();
		if(ProcessUtils.addTranscodeJob(assetId, filePath) > 0) {
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		} else {
			res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
			res.setResponseMessage("Error to create transcode job!");
		}
		
		return res;
	}
	
	private String GenerateFileName(File file)
	{
		String thumbnailName = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		String nowDate = formatter.format(new Date());
					
		String fileName = file.getName();
		thumbnailName = fileName.replace('.', '_') + "_" + nowDate + "_" + RandomStringUtils.randomAlphanumeric(5) + ".jpg";
		
		return thumbnailName;
	}
	
	@Override
	public ThumbnailsResponse generateThumbnails(int assetId, String filePath) {
		ThumbnailsResponse res = new ThumbnailsResponse();
		res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
		res.setResponseMessage("Failed to generate thumbnail");
		
		File file = new File(filePath);
		
		String thumbnailName = GenerateFileName(file);

		String thumbnailOut = Constants.THUMBNAIL_OUTPUT_PATH + thumbnailName;

		String cmdline = "";
		
		String fileExt = "";
		int nPos = filePath.lastIndexOf(".");
		if (nPos > 0)
		{
			fileExt = filePath.substring(nPos +1);
		}
		
		if (fileExt.compareToIgnoreCase("gif") == 0)
		{
			cmdline = String.format("gm convert \"%s[0]\" %s", filePath, thumbnailOut);
		}
		else
		{
			MediaInfo mediainfo= new MediaInfo(filePath);
			
			int iRotation = (int)mediainfo.getRotation();
			String transpose_setting = "";
			switch (iRotation)
	        {
		        case 90: {
					String pad=TCESOAPUtils.ffmpgPadFilter(mediainfo.getHeight(), mediainfo.getWidth(), 16.0/9);
					if (pad.isEmpty())
						transpose_setting = "-vf transpose=1";
					else 
						transpose_setting = "-vf transpose=1," + pad;
					
		        	break;
		        }
		        case 180: { 
					String pad=TCESOAPUtils.ffmpgPadFilter(mediainfo.getWidth(), mediainfo.getHeight(), 16.0/9);
					if (pad.isEmpty())
			        	transpose_setting = "-vf vflip";
					else 
			        	transpose_setting = "-vf vflip," + pad;
		        	break;
		        }
		        case 270: { 
					String pad=TCESOAPUtils.ffmpgPadFilter(mediainfo.getHeight(), mediainfo.getWidth(), 16.0/9);
					if (pad.isEmpty())
			        	transpose_setting = "-vf transpose=2";
					else 
			        	transpose_setting = "-vf transpose=2," + pad;
		        	break;
		        }
		        case 0:
		        default: {
					String pad=TCESOAPUtils.ffmpgPadFilter(mediainfo.getWidth(), mediainfo.getHeight(), 16.0/9);
					transpose_setting = pad.isEmpty()? "" : "-vf " + pad;
	
		        	break;
		        }
	        }
	
			// Create command line
			cmdline = String.format(CMD_THUMBNAIL_GEN, Constants.THUMBNAIL_GEN_PATH, filePath, THUMBNAIL_RESOLUTION, transpose_setting, thumbnailOut);
		}
		
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmdline);
			
			ProcessWithTimeout pwt = new ProcessWithTimeout(pr);
			int exitCode = pwt.waitForProcess(30000);
			
			if (exitCode == Integer.MIN_VALUE)
			{	
				log.error(String.format("Generate thumbnail Timeout: %s", cmdline));
				return res;
			}
			
			File fThumb = new File(thumbnailOut);
			if (!fThumb.exists())
			{
				return res;
			}
//			Runtime rt = Runtime.getRuntime();
//			Process pr = rt.exec(cmdline);
//			
//			String s;
//			BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//			while((s=bufferedReader.readLine()) != null)
//				//log.info(s);
//			
//			pr.waitFor();
			
		}
		catch(Exception e)
		{
			String error = e.getMessage();
			log.error("Failed to generate thumbnail: " + error);
			return res;
		}

		
		List<Thumbnail> thumbs = new ArrayList<Thumbnail>();
		
		Thumbnail t = new Thumbnail();
		t.setAssetId(assetId);
		t.setFilename(thumbnailName);
		t.setOutputFolder(Constants.THUMBNAIL_OUTPUT_PATH);
		t.setResolution(THUMBNAIL_RESOLUTION);
		t.setFormat("jpg");
		
		thumbs.add(t);
		
		res.setResponseCode(ResponseCode.SUCCESS);
		res.setResponseMessage("Success");
		res.setThumbnails(thumbs);
		return res;
	}
}