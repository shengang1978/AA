package com.divx.manager.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.divx.common.main.Constants.eJobType;
import com.divx.common.main.Constants.eProcessErrorCode;
import com.divx.common.main.Constants.eProcessStatus;
import com.divx.service.ConfigurationManager;
import com.divx.service.ProcessWithTimeout;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.dao.TranscodeJobDao;
import com.divx.service.domain.dao.TranscodeOutputDao;
import com.divx.service.domain.dao.impl.TranscodeDaoImpl;
import com.divx.service.domain.dao.impl.TranscodeJobDaoImpl;
import com.divx.service.domain.dao.impl.TranscodeOutputDaoImpl;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.service.domain.model.DcpTranscodeOutput;
import com.divx.service.model.EndPublishOptionShell;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.TransOption;
import com.divx.service.model.V2GOption;

public class V2GManager {
	
	public static void DoProcessExtractJpg(DcpTranscode dcpTrans)
	{
		new Thread(new V2GManager().new ThreadExtractJpg(transcodeDao, dcpTrans)).run();
	}
	
	public static void DoProcessJpg2Gif(DcpTranscode dcpTrans)
	{
		new Thread(new V2GManager().new ThreadJpg2Gif(transcodeDao, dcpTrans)).run();
	}
	
	private static TranscodeDao transcodeDao;
	private static TranscodeJobDao transcodeJobDao;
	private static TranscodeOutputDao transcodeOutputDao;
	static
	{
		transcodeDao = new TranscodeDaoImpl();
		transcodeJobDao = new TranscodeJobDaoImpl();
		transcodeOutputDao = new TranscodeOutputDaoImpl();
	}

	public class ThreadExtractJpg implements Runnable
	{
		private final Logger log = Logger.getLogger(ThreadExtractJpg.class);
		
		public ThreadExtractJpg(TranscodeDao dao, DcpTranscode trans)
		{
			transcodeDao = dao;
			dcpTrans = trans;
			
			if (dcpTrans.getStatus() == eProcessStatus.Uploaded.ordinal() || 
					dcpTrans.getJobtype() == eJobType.V2G.ordinal())
			{
				dcpTrans.setStatus(eProcessStatus.V2GVideo2JpgDoing.ordinal());
				if (dcpTrans.getJobname() == null || dcpTrans.getJobname().isEmpty())
				{
					GenerateCmdLine();
					
					dcpTrans.setJobname(GetFolderName(""));
				}
			}
			dcpTrans.setDatemodified(new Date());
			transcodeDao.UpdateTranscode(dcpTrans);		
		}
		
		@Override
		public void run() {
			
			try
			{
				if (dcpTrans.getJobtype() == eJobType.V2G.ordinal() && 
						dcpTrans.getTransoptionjson() != null && !dcpTrans.getTransoptionjson().isEmpty() &&
						(dcpTrans.getStatus() == eProcessStatus.V2GVideo2JpgDoing.ordinal() ||
						 dcpTrans.getStatus() == eProcessStatus.Uploaded.ordinal()))
				{	
					String cmdline = GenerateCmdLine();
					
					//log.info("Begin to call: " + cmdline);

					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec(cmdline);
					
					ProcessWithTimeout pwt = new ProcessWithTimeout(pr);
					int exitCode = pwt.waitForProcess(2*3600000);
					
					if (exitCode == Integer.MIN_VALUE)
					{
						ProcessUtils.checkRetryCount(dcpTrans, eProcessErrorCode.V2GExtractJpgError, 
								String.format("Extract Jpg timeout: %s", cmdline));
						
						log.error(String.format("Extract Jpg timeout: %s", cmdline));
						return;
					}
					
					//log.info(String.format("End to call %d. %s", exitCode, cmdline));
					
					dcpTrans.setDatemodified(new Date());
					dcpTrans.setStatus(eProcessStatus.V2GVideo2JpgDone.ordinal());
					dcpTrans.setTrycount(0);
					transcodeDao.UpdateTranscode(dcpTrans);
				}
			}
			catch(Exception ex)
			{
				Util.LogError(log, String.format("ThreadExtractJpg.DoTask(%s) Exception", Util.ObjectToJson(dcpTrans)), ex);
				ProcessUtils.checkRetryCount(dcpTrans, eProcessErrorCode.V2GExtractJpgError, 
						String.format("ThreadExtractJpg.DoTask Exception"));
			}
			
		}
		
		String GenerateCmdLine()
		{
			/*
			 * ffmpeg -y -i "./test.mkv" -r 5 -s 640x480 -an -f image2 "./test_%05d.png"
				-y 覆盖原有文件
				-ss 开始位置（秒数或 hh:mm:ss[.xxx]）
				-t 总秒数（秒数或 hh:mm:ss[.xxx]）
				-r 设置每秒提取图片的帧数
				-i 输入视频
				-s 生成文件大小
				-an 忽略声音
				-f image2 图像文件流合并写入视频帧的图像文件 在win系统中必须[%%]才可以
			 * */
			if (cmdLine == null || cmdLine.isEmpty())
			{
				TransOption tnsOpt = Util.JsonToObject(dcpTrans.getTransoptionjson(), TransOption.class);
				V2GOption v2g = Util.JsonToObject(new String(DatatypeConverter.parseBase64Binary(tnsOpt.getV2gOption())), V2GOption.class);
				//String start = FormatTimeString(v2g.getStart());
				int framePerSecond = 5;
				String resolution = "640:360";
				String folder = "";
				
				if (dcpTrans.getJobname() == null || dcpTrans.getJobname().isEmpty())
				{
					folder = String.format("%s%s", Util.UrlWithSlashes(ConfigurationManager.GetInstance().TCE_LOCATION_OUT()), 
								GetFolderName(new File(tnsOpt.getLocations().get(0)).getName()));
				}
				else
				{
					folder = String.format("%s%s", Util.UrlWithSlashes(ConfigurationManager.GetInstance().TCE_LOCATION_OUT()), 
								dcpTrans.getJobname());
					folderName = dcpTrans.getJobname();
				}
				File fd = new File(folder);
				if (!fd.exists())
					fd.mkdir();
				
				if (!new File(tnsOpt.getLocations().get(0)).exists())
				{
					log.info(String.format("%s doesn't exist!", tnsOpt.getLocations().get(0)));
				}
				String outPath = String.format("%s/v2g_%%05d.jpg", folder);
				
				String param_duration = "";
				String param_start = "";
				String param_crop = "";
				if (v2g.getDuration() > 0)
				{
					param_duration = String.format("-t %s", FormatTimeString(v2g.getDuration()));
				}
				if (v2g.getStart() >= 0)
				{
					param_start = String.format("-ss %s", FormatTimeString(v2g.getStart()));
				}
				if (v2g.getWidth() > 0 && v2g.getHeight() > 0)
				{
					param_crop = String.format("-vf crop=%d:%d:%d:%d,scale=%s", 
											v2g.getWidth(), 
											v2g.getHeight(), 
											v2g.getX(), 
											v2g.getY(), 
											resolution);
				}
				else
				{
					param_crop = String.format("-vf scale=%s", resolution);
				}
				//if (v2g.getDuration() > 0)
				{
					cmdLine = String.format("%s -y %s %s -i %s %s -r %d -an -f image2 %s",
							"ffmpeg",
							param_start,
							param_duration,
							tnsOpt.getLocations().get(0),
							param_crop,
							framePerSecond,
							outPath
							);
//					String duration = FormatTimeString(v2g.getDuration());
//					cmdLine = String.format("%s -y -ss %s -t %s -i %s -r %d -s %s -an -f image2 %s",
//							//ConfigurationManager.GetInstance().THUMBNAIL_GEN_PATH(),
//							"ffmpeg",
//							start,
//							duration,
//							tnsOpt.getLocations().get(0),
//							framePerSecond,
//							resolution,
//							outPath
//							);
				}
//				else
//				{
//					cmdLine = String.format("%s -y -ss %s -i %s -r %d -s %s -an -f image2 %s", 
//							ConfigurationManager.GetInstance().THUMBNAIL_GEN_PATH(),
//							start,
//							tnsOpt.getLocations().get(0),
//							framePerSecond,
//							resolution,
//							outPath
//							);
//				}
			}
			return cmdLine;
		}
	
		String FormatTimeString(int ms)
		{
			return String.format("%02d:%02d:%02d.%03d", 
									ms/1000/3600, 
									(ms/1000/60)%60, 
									(ms/1000)%60,
									ms%1000);
		}
		
		String GetFolderName(String fileName)
		{
			if (folderName == null || folderName.isEmpty())
			{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
				String nowDateOut = formatter.format(new Date());
				folderName = "V2G_" + fileName.replace('.', '_') + "_" + nowDateOut + "_" + RandomStringUtils.randomAlphanumeric(5);
			}
			return folderName;
		}
		
		private String cmdLine;
		private String folderName;
		private DcpTranscode dcpTrans;
		private TranscodeDao transcodeDao;
	}
	
	public class ThreadJpg2Gif implements Runnable
	{
		private final Logger log = Logger.getLogger(ThreadJpg2Gif.class);
		
		public ThreadJpg2Gif(TranscodeDao dao, DcpTranscode trans)
		{
			transcodeDao = dao;
			dcpTrans = trans;
			
			if (dcpTrans.getStatus() == eProcessStatus.V2GVideo2JpgDone.ordinal() || 
					dcpTrans.getJobtype() == eJobType.V2G.ordinal())
			{
				dcpTrans.setStatus(eProcessStatus.V2GJpg2GifDoing.ordinal());
			}
			dcpTrans.setDatemodified(new Date());
			transcodeDao.UpdateTranscode(dcpTrans);		
		}
		
		@Override
		public void run() {
			
			try
			{
				if (dcpTrans.getJobtype() == eJobType.V2G.ordinal() && 
						dcpTrans.getTransoptionjson() != null && !dcpTrans.getTransoptionjson().isEmpty() &&
						(dcpTrans.getStatus() == eProcessStatus.V2GJpg2GifDoing.ordinal() ||
						 dcpTrans.getStatus() == eProcessStatus.V2GVideo2JpgDone.ordinal()))
				{	
					String cmdline = GenerateCmdLine();
					
					Runtime rt = Runtime.getRuntime();
					Process pr = rt.exec(cmdline);
					
					ProcessWithTimeout pwt = new ProcessWithTimeout(pr);
					int exitCode = pwt.waitForProcess(2*3600000);
					
					if (exitCode == Integer.MIN_VALUE)
					{
						ProcessUtils.checkRetryCount(dcpTrans, eProcessErrorCode.V2GJpg2GifError, 
								String.format("Jpg 2 Gif timeout: %s", cmdline));
						
						log.error(String.format("Jpg 2 Gif timeout: %s", cmdline));
						return;
					}
					
					DcpTranscodeOutput transOutput = new DcpTranscodeOutput();
					transOutput.setDatecreated(new Date());
					transOutput.setDatemodified(new Date());
					transOutput.setFiletype(MediaBaseType.eFileType.Gif.ordinal());
//					if(eJobType.H264.ordinal() == dcpTrans.getJobtype())
//						transOutput.setFiletype(EndPublishOptionShell.eFileType.H264.ordinal());
//					else
//						transOutput.setFiletype(EndPublishOptionShell.eFileType.H265.ordinal());
					
					transOutput.setFileurl(Util.UrlWithSlashes(ConfigurationManager.GetInstance().SMIL_OUT_FILE_PREFIX()) + 
											dcpTrans.getJobname() + 
											"/v2g_out.gif");
					transOutput.setStatus(eProcessStatus.V2gJpg2GifDone.ordinal());
					transOutput.setTranscodeId(dcpTrans.getTranscodeId());
					if(0 >= transcodeOutputDao.CreateTranscodeOutput(transOutput)) {
						ProcessUtils.checkRetryCount(dcpTrans, eProcessErrorCode.GenerateSMILError, "Error to generate SMIL file for asset id " + dcpTrans.getAssetId());
						log.error(String.format("transcodeOutputDao.CreateTranscodeOutput(%s) fail", Util.ObjectToJson(transOutput)));
						return;
					}
					
					dcpTrans.setDatemodified(new Date());
					dcpTrans.setStatus(eProcessStatus.V2gJpg2GifDone.ordinal());
					dcpTrans.setTrycount(0);
					transcodeDao.UpdateTranscode(dcpTrans);
				}
			}
			catch(Exception ex)
			{
				Util.LogError(log, String.format("ThreadExtractJpg.DoTask(%s) Exception", Util.ObjectToJson(dcpTrans)), ex);
				ProcessUtils.checkRetryCount(dcpTrans, eProcessErrorCode.V2GJpg2GifError, 
						String.format("ThreadJpg2Gif.DoTask Exception"));
			}
			
		}
		
		String GenerateCmdLine()
		{
			/*
			 * gm convert -delay 20 ./*png ./test_4.gif
			 * */
			String src = String.format("%s%s/*.jpg", 
							Util.UrlWithSlashes(ConfigurationManager.GetInstance().TCE_LOCATION_OUT()),
							dcpTrans.getJobname());
			String dst = GetGifPath();
			
			int delay = 20;
			return String.format("gm convert -delay %d %s %s", 
									delay,
									src,
									dst);

		}
		
		String GetGifPath()
		{
			if (gifOutPath == null || gifOutPath.isEmpty())
			{
				gifOutPath = String.format("%s%s/v2g_out.gif", 
						Util.UrlWithSlashes(ConfigurationManager.GetInstance().TCE_LOCATION_OUT()),
						dcpTrans.getJobname());
			}
			return gifOutPath;
		}
		private String gifOutPath;
		private DcpTranscode dcpTrans;
		private TranscodeDao transcodeDao;
	}
}

