package com.divx.manager.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.divx.common.main.Constants;
import com.divx.common.main.TCESOAPUtils;
import com.divx.common.main.Constants.eJobType;
import com.divx.common.main.Constants.eProcessStatus;
import com.divx.common.main.Constants.eProcessErrorCode;
import com.divx.service.ConfigurationManager;
import com.divx.service.ProcessWithTimeout;
import com.divx.service.StorageServiceHelper;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.dao.TranscodeJobDao;
import com.divx.service.domain.dao.TranscodeOutputDao;
import com.divx.service.domain.dao.impl.TranscodeDaoImpl;
import com.divx.service.domain.dao.impl.TranscodeJobDaoImpl;
import com.divx.service.domain.dao.impl.TranscodeOutputDaoImpl;
import com.divx.service.domain.model.DcpFilter;
import com.divx.service.domain.model.DcpPresetgroup;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.service.domain.model.DcpTranscodeJob;
import com.divx.service.domain.model.DcpTranscodeOutput;
import com.divx.service.model.AuthHelperModel;
import com.divx.service.model.EndPublishOptionShell;
import com.divx.service.model.MediaBaseType;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.divx.service.model.TransOption;
import com.divx.manager.impl.MediaServiceHelper;
import com.divx.common.main.MFSGenerator;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mainconcept.rewsservice.Job;
import com.mainconcept.rewsservice.MediaInfo;
import com.divx.service.model.EndPublishOption;


public class ProcessUtils {
	private static final Logger log = Logger.getLogger(ProcessUtils.class);
	
	private static String CMD_TRANSCODE = "java -jar %1s -uri %2s -u %3s -p %4s -outfileloc Out -sgname %5s -pgname %6s -submitjob test -streams 2 In::%7s:video:0 In::%8s:audio:0 -outfilepath %9s -job-name %10s -outfilename %11s";
	private static String CMD_SMIL_GEN = "%1s -d %2s -p %3s -f %4s";
	private static String SMIL_FILE_NAME = "smil_out.smil";
	private static String INPUT_STREAM_VIDEO = "In::%1s:video:0";
	private static String INPUT_STREAM_AUDIO = "In::%1s:audio:%2d";
	private static String VIDEO_FILTER_NAME = "filter";
	
	private static TranscodeDao transcodeDao;
	private static TranscodeJobDao transcodeJobDao;
	private static TranscodeOutputDao transcodeOutputDao;
	private static PresetGroupManager	presetGroupManager;
	
	static
	{
		transcodeDao = new TranscodeDaoImpl();
		transcodeJobDao = new TranscodeJobDaoImpl();
		transcodeOutputDao = new TranscodeOutputDaoImpl();
		presetGroupManager = new PresetGroupManager();
	}

	public static int addTranscodeJob(int assetId, String filePath) {
		TransOption opt = new TransOption();
		opt.setContentType(MediaBaseType.eContentType.SMIL);
		opt.setAssetId(assetId);
		List<String> locations = new LinkedList<String>();
		locations.add(filePath);
		opt.setLocations(locations);
		return addTranscodeJob(opt);
	}
	
	public static int addTranscodeJob(TransOption option) {
		try {
			DcpTranscode dcpTrans = new DcpTranscode();
			dcpTrans.setAssetId(option.getAssetId());
			if (option.getContentType() == MediaBaseType.eContentType.SMIL)
			{
				dcpTrans.setJobtype(eJobType.Analyze.ordinal());
				dcpTrans.setAsseturl(option.getLocations().get(0));
				dcpTrans.setTransoptionjson("");
			}
			else
			{
				dcpTrans.setJobtype(eJobType.V2G.ordinal());
				dcpTrans.setAsseturl("");
				dcpTrans.setTransoptionjson(Util.ObjectToJson(option));
			}
			dcpTrans.setStatus(eProcessStatus.Uploaded.ordinal());
			dcpTrans.setDatecreated(new Date());
			dcpTrans.setDatemodified(new Date());
			dcpTrans.setTrycount(0);
			
			return transcodeDao.CreateTranscode(dcpTrans);
		} catch(Exception ex) {
			Util.LogError(log, String.format("addTranscodeJob(%s) exception", Util.ObjectToJson(option)), ex);
			return 0;
		}
	}
	
	public static void analyzeInputFile(DcpTranscode dcpInput) {
		try {
			File file = new File(dcpInput.getAsseturl());
			String fileName = file.getName();
			//log.info("analyzeInputFile :" + Util.ObjectToJson(dcpInput));
			// Check if axml exists
			List<String> fileNames = new LinkedList<String>();
			fileNames.add(fileName);
			
			List<String> filePaths = new LinkedList<String>();
			filePaths.add("");
	
			List<MediaInfo> mediaInfos = TCESOAPUtils.getFileInfos("In", fileNames, filePaths);
			if(null != mediaInfos && !(mediaInfos.size() == 1 && mediaInfos.get(0) == null)) {
				// Update into database
				dcpInput.setDatemodified(new Date());
				dcpInput.setStatus(eProcessStatus.FinishAnalysis.ordinal());
				transcodeDao.UpdateTranscode(dcpInput);
				return;
			}
			
			// Submit job
			Job jobanalysis = TCESOAPUtils.submitAnalysisSingleFile("In", fileName, "");
			if(null == jobanalysis) {
				log.info("submitAnalysisSingleFile is null " + fileName);
				checkRetryCount(dcpInput, eProcessErrorCode.SubmitAnalysisJobError, "Error to submit analysis job for asset id " + dcpInput.getAssetId());
				return;
			}
			// create transcode job
			DcpTranscodeJob transJob = new DcpTranscodeJob();
			transJob.setDatecreated(new Date());
			transJob.setDatemodifed(new Date());
			//analyze job set format is -1
			transJob.setFormat(-1);
			transJob.setJobname(jobanalysis.getName());
			transJob.setStatus(eProcessStatus.Analyzing.ordinal());
			transJob.setTranscodeId(dcpInput.getTranscodeId());
			if(transcodeJobDao.CreateTranscodeJob(transJob) < 0) {
				log.error(String.format("transcodeJobDao.CreateTranscodeJob() fail. %s", Util.ObjectToJson(transJob)));
			}
			
			// Update into database
			dcpInput.setDatemodified(new Date());
			dcpInput.setStatus(eProcessStatus.Analyzing.ordinal());
			dcpInput.setTrycount(0);
			dcpInput.setJobname(jobanalysis.getName());
			transcodeDao.UpdateTranscode(dcpInput);
		} catch(Exception ex) {
			checkRetryCount(dcpInput, eProcessErrorCode.SubmitAnalysisJobError, "Error to submit analysis job for asset id " + dcpInput.getAssetId());
			Util.LogError(log, String.format("analyzeInputFile(%s) exception", Util.ObjectToJson(dcpInput)) , ex);
		}
	}
	
	public static void watchAnalyzingJob(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;
		
		try {
			List<DcpTranscodeJob> listTransJob = transcodeJobDao.GetTranscodeJobByTranscodeId(dcpInput.getTranscodeId());
			
			DcpTranscodeJob transJob = null;
			int maxJobId = -1;
			for (Iterator<DcpTranscodeJob> it = listTransJob.iterator(); it.hasNext(); )
			{
				DcpTranscodeJob objTemp = (DcpTranscodeJob)it.next();
				
				// Get the last transcode job record
				if(maxJobId < objTemp.getJobId())
					transJob = objTemp;
			}
			
			if(transJob.getStatus() == eProcessStatus.FinishAnalysis.ordinal()) {
				dcpInput.setStatus(eProcessStatus.FinishAnalysis.ordinal());
				dcpInput.setDatemodified(new Date());
				transcodeDao.UpdateTranscode(dcpInput);
				return;
			}
			
			if(transJob.getStatus() == eProcessStatus.Error.ordinal()) {
				dcpInput.setStatus(eProcessStatus.Error.ordinal());
				dcpInput.setDatemodified(new Date());
				dcpInput.setErrorstatus(eProcessErrorCode.InvalidInputFile.ordinal());
				dcpInput.setLog("Error to analyze the asset for asset id " + dcpInput.getAssetId());
				transcodeDao.UpdateTranscode(dcpInput);
				return;
			}
		} catch(Exception ex) {
			checkRetryCount(dcpInput, eProcessErrorCode.WatchAnalyzingJobError, "Error to watch analyzing job for asset id " + dcpInput.getAssetId());
			//log.error("Exception to watch analyzing job for asset id " + dcpInput.getAssetId());
			Util.LogError(log, String.format("watchAnalyzingJob(%s) exception", Util.ObjectToJson(dcpInput)), ex);
		}
	}

	public static void createTrancodes(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;
		
		try {
			boolean enableH264Transcode = ConfigurationManager.GetInstance().GetConfigValue("Transcode_Enable_AVC", true);
			if (enableH264Transcode)
			{
				DcpTranscode dcpTransH264 = new DcpTranscode();
				dcpTransH264.setAssetId(dcpInput.getAssetId());
				dcpTransH264.setAsseturl(dcpInput.getAsseturl());
				dcpTransH264.setDatecreated(new Date());
				dcpTransH264.setDatemodified(new Date());
				dcpTransH264.setJobtype(eJobType.H264.ordinal());
				dcpTransH264.setStatus(eProcessStatus.CreatedTranscode.ordinal());
				dcpTransH264.setTrycount(0);
				
				if(transcodeDao.CreateTranscode(dcpTransH264) < 0) {
					log.error("Failed to create H264 transocde for asset id " + dcpInput.getAssetId());
				}
			}
			
			boolean enableH265Transcode = ConfigurationManager.GetInstance().GetConfigValue("Transcode_Enable_HEVC", true);
			
			if (enableH265Transcode)
			{
				DcpTranscode dcpTransH265 = new DcpTranscode();
				dcpTransH265.setAssetId(dcpInput.getAssetId());
				dcpTransH265.setAsseturl(dcpInput.getAsseturl());
				dcpTransH265.setDatecreated(new Date());
				dcpTransH265.setDatemodified(new Date());
				dcpTransH265.setJobtype(eJobType.H265.ordinal());
				dcpTransH265.setStatus(eProcessStatus.CreatedTranscode.ordinal());
				dcpTransH265.setTrycount(0);
				
				if(transcodeDao.CreateTranscode(dcpTransH265) < 0) {
					log.error("Failed to create H265 transocde for asset id " + dcpInput.getAssetId());
				}
			}
			
			dcpInput.setDatemodified(new Date());
			dcpInput.setStatus(eProcessStatus.FinishCreateTranscode.ordinal());
			dcpInput.setTrycount(0);
			transcodeDao.UpdateTranscode(dcpInput);
		} catch(Exception ex) {
			checkRetryCount(dcpInput, eProcessErrorCode.CreateTranscodeError, "Error to create transcode for asset id " + dcpInput.getAssetId());

			Util.LogError(log, String.format("createTrancodes(%s) exception", Util.ObjectToJson(dcpInput)), ex);
		}
	}
	
	
	public static DcpFilter getFilter(int width,int height,int iRotation,DcpTranscode dcpInput){
		String mfs = "";
		eJobType filterType = eJobType.values()[dcpInput.getJobtype()];
		DcpFilter filter = transcodeDao.getDcpFilter(width, height, iRotation ,filterType);
		if(null == filter){
			mfs = filterType == eJobType.H265 ? 
						MFSGenerator.CreatHEVCMFS(width, height, iRotation).Createmfs() : 
						MFSGenerator.CreatAVCMFS(width, height, iRotation).Createmfs();
			String filterName = String.format("%s_%dx%d_%d", filterType.toString(),width,height,iRotation);

			DcpFilter dcpfilter =  new DcpFilter();
			dcpfilter.setWidth(width);
			dcpfilter.setHeight(height);
			dcpfilter.setFormat(filterType.ordinal());
			dcpfilter.setRotation(iRotation);
			dcpfilter.setFilterName(filterName);
			dcpfilter.setFilterContent(mfs);
			dcpfilter.setDateCreate(new Date());
			dcpfilter.setDateModify(new Date());
			dcpfilter.setEnabled(true);
			int filterId = transcodeDao.CreateFilter(dcpfilter);
			if(filterId > 0){
				filter = dcpfilter;
			}
		}

		if(filter.getFilterContent() != null && !filter.getFilterContent().trim().isEmpty()){
			if(!TCESOAPUtils.checkFilterIsExist(filter.getFilterName())){
				if(!TCESOAPUtils.addFilter(filter.getFilterName(), filter.getFilterContent())) {
					checkRetryCount(dcpInput, eProcessErrorCode.CreateFilterError, "Error to create filter for asset id " + dcpInput.getAssetId() + filter.getFilterContent());
				}
			}		
		}
		else
		{
			// empty content
			filter = null;
		}
			
		return filter;
	}
		
	public static final Cache<String, com.divx.common.main.MediaInfo> cacheMediaInfo = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(10, TimeUnit.MINUTES).build();
	public static void submitTransocodeJob(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;

		try {
			File file = new File(dcpInput.getAsseturl());
			String fileName = file.getName();
			String key = String.format(Constants.TCE_LOCATION_IN + File.separator + fileName);
			com.divx.common.main.MediaInfo	mediainfo = cacheMediaInfo.getIfPresent(key);
			if(mediainfo == null){
				mediainfo= new com.divx.common.main.MediaInfo(key);	
				cacheMediaInfo.put(key, mediainfo);
			}
			
		
			int iRotation = (int)mediainfo.getRotation();
			int width = mediainfo.getWidth();
			int height = mediainfo.getHeight();
			float framerate = (float)mediainfo.getFrameRate();
			
			MFSGenerator mfsg = null;
			eJobType jobType = eJobType.values()[dcpInput.getJobtype()];
			mfsg = jobType == eJobType.H265 ? MFSGenerator.CreatHEVCMFS(width, height, iRotation) :
											  MFSGenerator.CreatAVCMFS(width, height, iRotation);

			// transcode for video
			List<DcpPresetgroup> listPGs = getVideoPresetGroup(mfsg.getOutputWidth(), mfsg.getOutputHeight(), framerate, dcpInput.getJobtype());
			if(listPGs.isEmpty()){
				checkRetryCount(dcpInput, eProcessErrorCode.GetPresetGroupError, "Error to get preset group for asset id " + dcpInput.getAssetId() + width + height + framerate + iRotation);
				log.info(String.format("Fail to get the presetGroup(%d, %d, %d, %f, %d)", dcpInput.getAssetId(), width, height, framerate, iRotation));
				return;
			}
			
			// Output path
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String nowDateOut = formatter.format(new Date());
//			String outPath = String.format("Job_%s_%d_%s", 
//												Constants.eJobType.values()[dcpInput.getJobtype()].toString(),
//												dcpInput.getAssetId(),
//												fileName.replace('.', '_'));
			String outPath = "Job_" + fileName.replace('.', '_') + "_" + nowDateOut + "_" + RandomStringUtils.randomAlphanumeric(5);;
				
			for (DcpPresetgroup pg : listPGs)
			{
				String pgname = pg.getPgname();
				
				// Get date now
				String nowDate = formatter.format(new Date());
				
				String jobName = "Job_" + fileName.replace('.', '_') + "_" + nowDate + "_" + RandomStringUtils.randomAlphanumeric(5);
			
				String outFileName = "OutFile_" + fileName.replace('.', '_') + "_$PresetName";
				
				
				ArrayList<String> paramStreams	= new ArrayList<String>();
				String videoInput = String.format(INPUT_STREAM_VIDEO, fileName);
				paramStreams.add("1"); // number of input streams
				paramStreams.add(videoInput);
				
				
				try {
					//getPGFromTCE(pgname);
					DcpFilter filter = getFilter(width, height, iRotation, dcpInput);
					String filterName = null; 
					String videoFilterName = null; 
					if(filter != null && filter.getFilterName() != null){
						filterName = filter.getFilterName();
						videoFilterName = VIDEO_FILTER_NAME;
					}
	
					if(!TCESOAPUtils.submitJob("In", "Out", outPath, outFileName, null, jobName, 
							null, Constants.TCE_SERVER_GROUPS, pgname, null, null, null, null, 
							null, null, null, null, filterName, null, videoFilterName, null, null, null, null, null, null, 
							paramStreams, null, null, null, null, null, null)) {
						checkRetryCount(dcpInput, eProcessErrorCode.SubmitTransocdeJobError, "Error to submit transcode job for asset id " + dcpInput.getAssetId());
						//log.error("Error to submit transcode job for transcode id " + dcpInput.getTranscodeId());
						log.error(String.format("Fail to call TCESOAPUtils.submitJob(In, Out, %s, %s, null, %s, null, %s, %s, null, null, null, null,null, null, null, null, %s, null, %s, null, null, null, null, null, null,%s, null, null, null, null, null, null", 
								outPath, 
								outFileName, 
								jobName, 
								Constants.TCE_SERVER_GROUPS, 
								pgname,
								filterName,
								videoFilterName,
								Util.ObjectToJson(paramStreams)));
						return;
					}
				} catch (Exception ex) {
					checkRetryCount(dcpInput, eProcessErrorCode.SubmitTransocdeJobError, "Error to submit transcode job for asset id " + dcpInput.getAssetId());
					//log.error("Exception to submit transcode job for transcode id " + dcpInput.getTranscodeId());
					Util.LogError(log, String.format("Exception to TCESOAPUtils.submitJob(%s) video", jobName), ex);
					return;
				}
	
				// create transcode job
				DcpTranscodeJob transJob = new DcpTranscodeJob();
				transJob.setDatecreated(new Date());
				transJob.setDatemodifed(new Date());
				//media job set format is Presetgroup id
				transJob.setFormat(pg.getPgId());
				transJob.setJobname(jobName);
				transJob.setStatus(eProcessStatus.Transcoding.ordinal());
				transJob.setTranscodeId(dcpInput.getTranscodeId());
				if(transcodeJobDao.CreateTranscodeJob(transJob) < 0) {
					log.error("Failed to create transocde job for asset id " + dcpInput.getAssetId());
				}
			}
			
	
			// transcode for audio
			List<String> fileNames = new LinkedList<String>();
			fileNames.add(fileName);
			
			List<String> filePaths = new LinkedList<String>();
			filePaths.add("");
			
			
			List<MediaInfo> mediaInfos = TCESOAPUtils.getFileInfos("In", fileNames, filePaths);
			if(null == mediaInfos) {
				checkRetryCount(dcpInput, eProcessErrorCode.InvalidInputFile, "Failed to get stream info for asset id " + dcpInput.getAssetId());
				//log.error("Failed to get stream info for transcode id " + dcpInput.getTranscodeId());
				log.info(String.format("TCESOAPUtils.getFileInfos(\"In\", %s, %s) == null", Util.ObjectToJson(fileNames), Util.ObjectToJson(filePaths)));
				return;
			}
			
			if(mediaInfos != null && mediaInfos.size() > 0 && mediaInfos.get(0) != null && mediaInfos.get(0).getAss() != null){	
				for(int i = 0; i < mediaInfos.get(0).getAss().size(); i++) {
					
					String nowDate = formatter.format(new Date());
					
					String jobName = "Job_" + fileName.replace('.', '_') + "_" + nowDate + "_" + RandomStringUtils.randomAlphanumeric(5);
					String outFileName = "OutFile_" + fileName.replace('.', '_') + "_$PresetName_" + RandomStringUtils.randomAlphanumeric(5);
					
					ArrayList<String> paramStreams	= new ArrayList<String>();
					String audioInput = String.format(INPUT_STREAM_AUDIO, fileName, i);
					paramStreams.add("1"); // number of input streams
					paramStreams.add(audioInput);
					
					try {
						if(!TCESOAPUtils.submitJob("In", "Out", outPath, outFileName, null, jobName, 
								null, Constants.TCE_SERVER_GROUPS, Constants.TCE_TRANSCODING_PRESET_AAC_2CH_192, null, null, null, null, 
								null, null, null, null, null, null, null, null, null, null, null, null, null, 
								paramStreams, null, null, null, null, null, null)) {
							checkRetryCount(dcpInput, eProcessErrorCode.SubmitTransocdeJobError, "Error to submit transcode job for asset id " + dcpInput.getAssetId());
							log.error("Error to submit transcode job for transcode id " + dcpInput.getTranscodeId());
							return;
						}
					} catch (Exception ex) {
						checkRetryCount(dcpInput, eProcessErrorCode.SubmitTransocdeJobError, "Error to submit transcode job for asset id " + dcpInput.getAssetId());
						//log.error("Exception to submit transcode job for transcode id " + dcpInput.getTranscodeId());
						Util.LogError(log, String.format("TCESOAPUtils.submitAudioJob(%s) Exception", jobName), ex);
						return;
					}
					
					// create transcode job
					DcpTranscodeJob transJob = new DcpTranscodeJob();
					transJob.setDatecreated(new Date());
					transJob.setDatemodifed(new Date());
					//audio job set format is 0
					transJob.setFormat(0);
					transJob.setJobname(jobName);
					transJob.setStatus(eProcessStatus.Transcoding.ordinal());
					transJob.setTranscodeId(dcpInput.getTranscodeId());
					if(transcodeJobDao.CreateTranscodeJob(transJob) < 0) {
						log.error("Failed to create transocde job for asset id " + dcpInput.getAssetId());
					}
				}
			}
	
			dcpInput.setDatemodified(new Date());
			dcpInput.setStatus(eProcessStatus.Transcoding.ordinal());
			dcpInput.setTrycount(0);
			dcpInput.setJobname(outPath);
			transcodeDao.UpdateTranscode(dcpInput);
		} catch(Exception ex) {
			checkRetryCount(dcpInput, eProcessErrorCode.SubmitTransocdeJobError, "Error to submit transcode job for asset id " + dcpInput.getAssetId());
			//log.error("Exception to submit transcode job for transcode id " + dcpInput.getTranscodeId());
			Util.LogError(log, String.format("submitTransocodeJob(%s) exception", Util.ObjectToJson(dcpInput)), ex);
		}
	}
	
	public static void watchTransocodingJob(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;
		
		try {
			List<DcpTranscodeJob> listTransJob = transcodeJobDao.GetTranscodeJobByTranscodeId(dcpInput.getTranscodeId());
			
			boolean isComplete = true;
			boolean hasError = false;
			for (Iterator<DcpTranscodeJob> it = listTransJob.iterator(); it.hasNext(); )
			{
				DcpTranscodeJob objTemp = (DcpTranscodeJob)it.next();
				if(eProcessStatus.FinishTranscode.ordinal() == objTemp.getStatus()) {
					//TCESOAPUtils.deletePG(objTemp.getJobname());
					continue;
				}
	
				if(eProcessStatus.Error.ordinal() == objTemp.getStatus()) {
					hasError = true;
					//TCESOAPUtils.deletePG(objTemp.getJobname());
					break;
				}
				
				isComplete = false;
			}
			
			if(hasError) {
				dcpInput.setStatus(eProcessStatus.Error.ordinal());
				dcpInput.setDatemodified(new Date());
				dcpInput.setErrorstatus(eProcessErrorCode.TranscodeError.ordinal());
				dcpInput.setLog("error to transcode for asset id " + dcpInput.getAssetId());
				dcpInput.setTrycount(0);
				transcodeDao.UpdateTranscode(dcpInput);
			}
			
			if(isComplete) {
				dcpInput.setStatus(eProcessStatus.FinishTranscode.ordinal());
				dcpInput.setDatemodified(new Date());
				dcpInput.setTrycount(0);
				transcodeDao.UpdateTranscode(dcpInput);
			}
		} catch(Exception ex) {
			checkRetryCount(dcpInput, eProcessErrorCode.WatchTranscodingJobError, "Error to watch transcoding job for asset id " + dcpInput.getAssetId());
			//log.error("Exception to watch transcoding job for asset id " + dcpInput.getAssetId());
			Util.LogError(log, String.format("watchTransocodingJob(%s) exception", Util.ObjectToJson(dcpInput)), ex);
		}
	}

	public static void generateSMILFile(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;

		String InDir = Constants.TCE_LOCATION_OUT + dcpInput.getJobname();
		String smilOut = InDir + File.separatorChar + SMIL_FILE_NAME;
		String prefix = Constants.SMIL_OUT_FILE_PREFIX + dcpInput.getJobname() + "/";
	
		// Create command line
		String cmdline = String.format(CMD_SMIL_GEN, Constants.SMIL_GEN_PATH, InDir, prefix, smilOut);
		
		try {
			Runtime rt = Runtime.getRuntime();
			Process pr = rt.exec(cmdline);
			
			ProcessWithTimeout pwt = new ProcessWithTimeout(pr);
			int exitCode = pwt.waitForProcess(30000);
			
			if (exitCode == Integer.MIN_VALUE)
			{
				checkRetryCount(dcpInput, eProcessErrorCode.GenerateSMILError, 
						String.format("Generate SMIL Timeout: %s", cmdline));
				
				log.error(String.format("Generate SMIL Timeout: %s", cmdline));
				return;
			}
			
			//log.info("generatorSMIL: " + cmdline);
			
			File fSMIL = new File(smilOut);
			if(fSMIL.exists()) {
				
				// Add record for transcode output
				DcpTranscodeOutput transOutput = new DcpTranscodeOutput();
				transOutput.setDatecreated(new Date());
				transOutput.setDatemodified(new Date());
				
				if(eJobType.H264.ordinal() == dcpInput.getJobtype())
					transOutput.setFiletype(MediaBaseType.eFileType.H264.ordinal());
				else
					transOutput.setFiletype(MediaBaseType.eFileType.H265.ordinal());
				
				transOutput.setFileurl(prefix + SMIL_FILE_NAME);
				transOutput.setStatus(dcpInput.getStatus());
				transOutput.setTranscodeId(dcpInput.getTranscodeId());
				if(0 >= transcodeOutputDao.CreateTranscodeOutput(transOutput)) {
					checkRetryCount(dcpInput, eProcessErrorCode.GenerateSMILError, "Error to generate SMIL file for asset id " + dcpInput.getAssetId());
					log.error(String.format("transcodeOutputDao.CreateTranscodeOutput(%s) fail", Util.ObjectToJson(transOutput)));
					return;
				}
				
				dcpInput.setDatemodified(new Date());
				dcpInput.setStatus(eProcessStatus.FinishSMILCreation.ordinal());
				dcpInput.setTrycount(0);
				transcodeDao.UpdateTranscode(dcpInput);
				
				return;
			}
			else
			{
				log.error(String.format("fail to generate SMILFile(%s) gen %s", dcpInput.getJobname(), smilOut));
			}
		}
		catch(Exception e)
		{
			Util.LogError(log, String.format("generateSMILFile(%s) exception", Util.ObjectToJson(dcpInput)), e);
			
		}
		
		checkRetryCount(dcpInput, eProcessErrorCode.GenerateSMILError, "Error to generate SMIL file for asset id " + dcpInput.getAssetId());
	}

	public static void callEndPublishForDone(DcpTranscode dcpInput) {
		if(null == dcpInput)
			return;
		
		try {
			if(eProcessStatus.FinishSMILCreation.ordinal() == dcpInput.getStatus()) {
				DcpTranscodeOutput transOutput = transcodeOutputDao.GetByTranscodeId(dcpInput.getTranscodeId());
				
				EndPublishOptionShell endOpt = new EndPublishOptionShell();
				endOpt.EndPublishOption.setAssetId(dcpInput.getAssetId());
				endOpt.EndPublishOption.setSmilPath(transOutput.getFileurl());
				endOpt.EndPublishOption.setStatus(EndPublishOption.eStatus.success);
				endOpt.EndPublishOption.setMessage("Success!");
				
				if(dcpInput.getJobtype() == eJobType.H264.ordinal())
					endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.H264);
				else if (dcpInput.getJobtype() == eJobType.H265.ordinal())
					endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.H265);
				else if (dcpInput.getJobtype() == eJobType.V2G.ordinal())
				{
					endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.Gif);
				}
				
				ServiceResponse  ret = StorageServiceHelper.EndPublish(Util.ObjectToJson(endOpt));
				if(ret.getResponseCode() == ResponseCode.SUCCESS) {
					dcpInput.setDatemodified(new Date());
					dcpInput.setStatus(eProcessStatus.FinsihEndPublish.ordinal());
					dcpInput.setTrycount(0);
					transcodeDao.UpdateTranscode(dcpInput);
					return;
				}
				
				checkRetryCount(dcpInput, eProcessErrorCode.EndPublishError, "Error to call end publish for asset id " + dcpInput.getAssetId());
					
			} 
			else if (eProcessStatus.Error.ordinal() == dcpInput.getStatus()) 
			{
				DcpTranscodeOutput transOutput = new DcpTranscodeOutput();
				transOutput.setDatecreated(new Date());
				transOutput.setDatemodified(new Date());
				
				if(eJobType.H264.ordinal() == dcpInput.getJobtype())
					transOutput.setFiletype(MediaBaseType.eFileType.H264.ordinal());
				else if (eJobType.H265.ordinal() == dcpInput.getJobtype())
					transOutput.setFiletype(MediaBaseType.eFileType.H265.ordinal());
				else
					transOutput.setFiletype(MediaBaseType.eFileType.Gif.ordinal());				
				
				transOutput.setFileurl("");
				transOutput.setStatus(dcpInput.getStatus());
				transOutput.setTranscodeId(dcpInput.getTranscodeId());
				if(0 >= transcodeOutputDao.CreateTranscodeOutput(transOutput)) {
					checkRetryCount(dcpInput, eProcessErrorCode.EndPublishError, "Error to call end publish for asset id " + dcpInput.getAssetId());
					return;
				}
				
				EndPublishOptionShell endOpt = new EndPublishOptionShell();
				endOpt.EndPublishOption.setAssetId(dcpInput.getAssetId());
				endOpt.EndPublishOption.setSmilPath("");
				endOpt.EndPublishOption.setStatus(EndPublishOption.eStatus.error);
				endOpt.EndPublishOption.setMessage(dcpInput.getLog());
				
				if(dcpInput.getJobtype() == eJobType.H264.ordinal())
					endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.H264);
				else if (dcpInput.getJobtype() == eJobType.H265.ordinal())
					endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.H265);
				
				if(MediaServiceHelper.EndPublish(endOpt)) {
					dcpInput.setDatemodified(new Date());
					dcpInput.setStatus(eProcessStatus.FinsihEndPublish.ordinal());
					dcpInput.setTrycount(0);
					transcodeDao.UpdateTranscode(dcpInput);
					return;
				}
				
				checkRetryCount(dcpInput, eProcessErrorCode.EndPublishError, "Error to call end publish for asset id " + dcpInput.getAssetId());
			}
			else if (eProcessStatus.V2gJpg2GifDone.ordinal() == dcpInput.getStatus())
			{
				DcpTranscodeOutput transOutput = transcodeOutputDao.GetByTranscodeId(dcpInput.getTranscodeId());
				
				EndPublishOptionShell endOpt = new EndPublishOptionShell();
				endOpt.EndPublishOption.setAssetId(dcpInput.getAssetId());
				endOpt.EndPublishOption.setSmilPath(transOutput.getFileurl());
				endOpt.getEndPublishOption().setStatus(EndPublishOption.eStatus.success);
				endOpt.EndPublishOption.setMessage("Success!");
				
				endOpt.EndPublishOption.setFileType(MediaBaseType.eFileType.Gif);
				
				if(MediaServiceHelper.EndPublish(endOpt)) {
					dcpInput.setDatemodified(new Date());
					dcpInput.setStatus(eProcessStatus.FinsihEndPublish.ordinal());
					dcpInput.setTrycount(0);
					transcodeDao.UpdateTranscode(dcpInput);
					return;
				}
				
				checkRetryCount(dcpInput, eProcessErrorCode.EndPublishError, "Error to call end publish for asset id " + dcpInput.getAssetId());
			}
		} catch(Exception ex){
			checkRetryCount(dcpInput, eProcessErrorCode.EndPublishError, "Error to call end publish for asset id " + dcpInput.getAssetId());
			//log.error("Exception to call end publish for asset id " + dcpInput.getAssetId());
			Util.LogError(log, String.format("callEndPublishForDone(%s) exception", Util.ObjectToJson(dcpInput)), ex);
		}
	}
	
	private static List<DcpPresetgroup> getVideoPresetGroup(int iWidth, int iHeight, float framerate, int jobtype){
		return presetGroupManager.GetPresetGroups(iWidth, iHeight, framerate, eJobType.values()[jobtype]);
	}

	public static void checkRetryCount(DcpTranscode dcpInput, eProcessErrorCode eError, String errorMsg) {
		if(null == dcpInput)
			return;
		
		int retryCount = dcpInput.getTrycount() == null ? 0 : dcpInput.getTrycount();
		int limitCount = Integer.parseInt(Constants.ERROR_RETRY_COUNT);
		if(retryCount >= limitCount) {
			dcpInput.setDatemodified(new Date());
			dcpInput.setTrycount(0);
			dcpInput.setErrorstatus(eError.ordinal());
			dcpInput.setLog(errorMsg);
			
			eProcessStatus procStatus = eProcessStatus.Error;
			if(eProcessErrorCode.EndPublishError == eError)
				procStatus = eProcessStatus.MediaServiceConnectionError;
			
			dcpInput.setStatus(procStatus.ordinal());
			transcodeDao.UpdateTranscode(dcpInput);
			return;
		}

		dcpInput.setDatemodified(new Date());
		dcpInput.setTrycount(retryCount + 1);
		transcodeDao.UpdateTranscode(dcpInput);
	}
	
	public static Constants.eJobFormat getJobFormatByPreset(String presetName) {
		if(presetName.equals(Constants.TCE_TRANSCODING_PRESET_HEVC_4K)){
			return Constants.eJobFormat.VIDEO_RESOLUTION_4K;
			
		} else if(presetName.equals(Constants.TCE_TRANSCODING_PRESET_HEVC_1080)){
			return Constants.eJobFormat.VIDEO_RESOLUTION_1080;
			
		} else if(presetName.equals(Constants.TCE_TRANSCODING_PRESET_H264_720) || presetName.equals(Constants.TCE_TRANSCODING_PRESET_HEVC_720)){
			return Constants.eJobFormat.VIDEO_RESOLUTION_720;
			
		} else if(presetName.equals(Constants.TCE_TRANSCODING_PRESET_H264_480) || presetName.equals(Constants.TCE_TRANSCODING_PRESET_HEVC_480)){
			return Constants.eJobFormat.VIDEO_RESOLUTION_480;
			
		} else if(presetName.equals(Constants.TCE_TRANSCODING_PRESET_AAC_2CH_192)) {
			return Constants.eJobFormat.AUDIO_AAC_2CH_192;
		}
			
		
		return Constants.eJobFormat.Unknown;
	}
}
