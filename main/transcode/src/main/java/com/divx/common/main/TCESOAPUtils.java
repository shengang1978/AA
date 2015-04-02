package com.divx.common.main;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.divx.service.Util;
import com.divx.service.model.ProcessResponse.Status;
import com.divx.common.main.Constants;

import java.net.URL;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.cxf.endpoint.Client;  
import org.apache.cxf.frontend.ClientProxy;  

import com.mainconcept.rewsservice.BypassParameter;
import com.mainconcept.rewsservice.InputFile;
import com.mainconcept.rewsservice.InputStream;
import com.mainconcept.rewsservice.PresetGroup;
import com.mainconcept.rewsservice.REWS;
import com.mainconcept.rewsservice.REWSPortType;
import com.mainconcept.rewsservice.ObjectFactory;
import com.mainconcept.rewsservice.RemoveJob;
import com.mainconcept.rewsservice.SubmitJob;
import com.mainconcept.rewsservice.SubmitWatchJob;
import com.mainconcept.rewsservice.Task;
import com.mainconcept.rewsservice.TaskState;
import com.mainconcept.rewsservice.UVSettings;
import com.mainconcept.rewsservice.User;
import com.mainconcept.rewsservice.QSearchOpts;
import com.mainconcept.rewsservice.QSearchResult;
import com.mainconcept.rewsservice.Job;
import com.mainconcept.rewsservice.GetLastError;
import com.mainconcept.rewsservice.MediaInfo;
import com.mainconcept.rewsservice.VideoStream;

import org.apache.log4j.Logger;





public class TCESOAPUtils {
	
	public enum valJobStateExt {
		/**
		 * Job is pending.
		 */
		pending,
		/**
		 * Job is paused.
		 */
		paused, 
		/**
		 * job is finished.
		 */
		finished, 
		/**
		 * Job is in error.
		 */
		error, 
		/**
		 * Job is aborted.
		 */
		aborted, 
		/**
		 * Watchfolder job is stopped.
		 */
		stopped, 
		/**
		 * watchfolder job is running.
		 */
		running
	};
	
	public enum valJobState {
		/**
		 * Job is waiting on the queue.
		 */
		queue, 
		/**
		 * Watchfolder job is waiting on the queue, it's not started yet.
		 */
		watchq, 
		/**
		 * Watchfolder job is active.
		 */
		watch, 
		/**
		 * Job is stopped.
		 */
		stop, 
		/**
		 * Job is in error.h 
		 */
		error, 
		/**
		 * Job finished successful.
		 */
		finish, 
		/**
		 * Job is aborted.
		 */
		abort, 
		/**
		 * Job is in progress, waiting for its tasks to be processed.
		 */
		taskwait, 
		/**
		 * Job is paused.
		 */
		paused, 
		/**
		 * Job is waiting post-processing to finish.
		 */
		post 
	};
	
	private static String CMD_MEDIAINFO_ROTATION = "%1s --Inform=Video;%%Rotation%% %2s";
	
	private static final Logger log = Logger.getLogger(TCESOAPUtils.class);
	private static REWS mStub = null; 
	private static REWSPortType port = null;
	private static ObjectFactory objFactory = new ObjectFactory();

	public static List<Job> getJobs(int startPos,int pageSize){
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return null;
		}
		
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == true) {
			QSearchOpts qso = new QSearchOpts();
			List<Job> jobs = new LinkedList<Job>();
			try{
				
				int nTotalTask = 0;
				//do{
					qso.setStartRow(startPos * pageSize);
					qso.setNumRows(pageSize);
					QSearchResult result = new QSearchResult();
					result = port.queueSearch(qso, true);
					nTotalTask = result.getTotalNumTasks();
					if(nTotalTask > 0){
						jobs.addAll(result.getJobs());
											
					}
				//}while(nTotalTask > 0);
			
			}catch(Exception ex){
				log.error("QueueSearch failed: " + GetLastError());
			}
			/*if(null == jobs){
				//log.info("QueueSearch found no jobs matching criteria.");
				return null;
			}*/
			
			return jobs;
			
		}else{
			log.info("Login failed");
			return null;
		}


	}

	
	public static Boolean getJobStatus(String jobName, StringBuilder status) {
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return false;
		}
		
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == true) {
			QSearchOpts qso = new QSearchOpts();
			QSearchResult result = null;
	
			qso.setJobName(jobName);
			try{
				result = port.queueSearch(qso, true);
				
			}catch(Exception ex){
				log.error("QueueSearch failed: " + GetLastError());
			}
			if(null == result){
				//log.info("QueueSearch found no jobs matching criteria.");
				return null;
			}
			return true;
		}else{
			log.info("Login failed");
			return false;
		}
		
	}
	

	public static List<MediaInfo> getFileInfos(String locationName, List<String> fileNames, List<String> filePaths) {
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return null;
		}
		List<MediaInfo> miList = null;
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == true) {
			try{
				miList = port.getMediaInfo4Assets(locationName,filePaths,fileNames);
				if(null == miList){
					log.error("Get Meida info failed: " + GetLastError());
				}
			}catch(Exception ex){
				Util.LogError(log, String.format("getFileInfos(%s,%s,%s) exception", 
						locationName,
						Util.ObjectToJson(fileNames),
						Util.ObjectToJson(filePaths)),  ex);
			}
			
		}else{
			log.info(String.format("getFileInfos(%s) Login failed", locationName));
			
		}
		return miList;
	}
	
	
	private static Boolean loadStub(String serviceURI) {
		try{
			mStub = new REWS(new URL(serviceURI));
		}catch(Exception ex){
			//log.error("Fail to connect REWS service:" + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to connect REWS server (%s)", serviceURI), ex);
			return false;
		}
		port = mStub.getREWSHttpSoap12Endpoint();
		Client client = ClientProxy.getClient(port);
		Object tmp1 = client.getRequestContext().put(org.apache.cxf.message.Message.MAINTAIN_SESSION, Boolean.TRUE);
		return true;
	}
	
	/*private static String GetLastError(){
		
		return gler.get_return();
	}*/
	
	private static Boolean doLogin(String username, String password) {
		User loginUser = null;
		try {
			loginUser = port.loginUser(username, password, "12345678");
		} catch (Exception ex) {
			//log.error("Login failed:" + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to login REWS"), ex);
		}
		if (loginUser == null)
			return false;
		return true;
	}
	private static String GetLastError(){
		GetLastError getLastError = new GetLastError();
		getLastError.setReserved("Last error");
		try {
			return port.getLastError(getLastError.getReserved().toString());
		} catch (Exception ex) {
			log.error(Util.getStackTrace(ex));
		
		}
		return null;
	}
	private static String[] getSkipExtensions()    
    {
      String extSkip[] = new String[2];
      
      extSkip[0] = "axml";
      extSkip[1] = "tce-pkg";
      
      return extSkip;
    }
	
	public static Job submitAnalysisSingleFile( String inLocationName, String inFileName, String inFilePath){ 
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return null;
		}
			  
		Job jobSubmitted = null;
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == true){	
			// Create job:
			Job job = new Job();			      
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			String nowDate = formatter.format(new Date());
			String jobName = "Job_" + inFileName.replace('.', '_') + "_" + nowDate + "_" + RandomStringUtils.randomAlphanumeric(5);
					
			job.setName(jobName);
			job.setSubmitServerGroupName(Constants.TCE_SERVER_GROUPS);
			job.setType("A");
			job.setOwnerUsername("admin1");
			job.setOutFileLoc("Out"); // not needed, but submitJob reports an error?
			job.setOutputFileName(inFileName + ".axml");
			job.setOutputFilePath("");
			
			// Submit job:
			
			SubmitJob submitJob = new SubmitJob();
			submitJob.setJob(job);
		    submitJob.setPriority( 5 );
		    
		    List<InputStream> iss = new ArrayList<InputStream>();
			InputStream is = new InputStream();
			//is.setInputFiles(new REWSStub.InputFile[1]);
			is.getInputFiles().add(new InputFile());
			//is.getInputFiles()[0] = new InputFile();
			is.getInputFiles().get(0).setFileName(inFileName);
			is.getInputFiles().get(0).setLocName(inLocationName);
			iss.add(is);
			//
			submitJob.getInStreams().addAll(iss);

			// Something is missing...
			//submitJob.setPresets(null);
			submitJob.setMakeProxy(false);
			submitJob.setTmpPG(null);
			// ------------------------------//

			try {
				//SubmitJobResponse response = port.submitJob(submitJob, );
				jobSubmitted = port.submitJob(submitJob.getJob(), submitJob.getPresets(), submitJob.getInStreams(), submitJob.getPriority(),
						submitJob.isMakeProxy(), submitJob.getTmpPG(), submitJob.getUVSettings());
				log.info(String.format("submitAnalysisSingleFile->submitJob(%s) : %s", submitJob.getJob(), Util.ObjectToJson(jobSubmitted)));
			} catch (Exception e) {
				Util.LogError(log, "submitAnalysisJob exception: ", e);
			}

			if (jobSubmitted == null) {
				log.error("submitAnalysisJob == null." + GetLastError());
			}
		}
		return jobSubmitted;
    }
	
	public static void removeJob(Integer jobID){
		// Initialization
		if(!Constants.Enable_RemoveJob()){
			return;
		}
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
						return;
		}		
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == true) {
			RemoveJob removeJob = new RemoveJob();
			removeJob.setJobID(jobID);
			boolean result = false;
			try {
				result = port.removeJob(jobID);
				}catch (Exception ex) {
					log.error("RemoveJob failed:" +Util.getStackTrace(ex));
					return;
				}
			
			if (result == false){
				log.error("RemoveJob failed, job id: " + jobID + GetLastError());
			}//else
				//log.info("RemoveJob succeed, job id: " + jobID);
		}else {
			  log.info("Login failed");
			  
		}	
	}
	
	public static Status convert2Status(String valJobStateExt) {
		switch (valJobStateExt)
        {
			case Constants.TCE_JOB_STATUS_QUEUE: return Status.pending;
	        case Constants.TCE_JOB_STATUS_TASKWAIT: return Status.transcoding;
	        case Constants.TCE_JOB_STATUS_PAUSED: return Status.paused;
	        
	        case Constants.TCE_JOB_STATUS_FINISH: return Status.finished;
	        case Constants.TCE_JOB_STATUS_ABORT: return Status.aborted;
	        
	        case Constants.TCE_JOB_STATUS_ERROR:
	        case Constants.TCE_JOB_STATUS_STOP:
	        	return Status.error;
	        
	        case Constants.TCE_JOB_STATUS_WATCHQ:
	        case Constants.TCE_JOB_STATUS_WATCH:
	        case Constants.TCE_JOB_STATUS_POST:
	        	return Status.unkonwn;

	        default:return Status.unkonwn;
        }
	}
	
	private static Integer toNumber(String in) {
		Integer result = -1;
		try {
			result = Integer.parseInt(in);
		} catch (NumberFormatException ex) {
		}
		return result;
	}
	
	private static class Stream {
		public int strNum;
		public String loc;
		public String path;
		public String name;
		public String type;
		public String content;

		public Stream(String content) {
			this.content = content;
		}
		
		public boolean parse() {
			if (content == null)
				return false;
			//loc:path:name:type:num
			int index = 5;
			while (index > 0) {
				int last = content.lastIndexOf(":");
				if ((last < 0) && (index>1))
					return false;
				String text = content.substring(last+1, content.length());
				if (last > 0)
					content = content.substring(0, last);
				if (index == 5) {
					strNum = toNumber(text);
				} else if (index == 4) {
					type = text;
				} else if (index == 3) {
					name = text;
				} else if (index == 2) {
					path = text;
				} else if (index == 1) {
					loc = text;
				}
				--index;
			}
			return true;
		}
		
		public InputStream getData() {
			InputStream result = new InputStream();
			result.getInputFiles().add(new InputFile());
			result.getInputFiles().get(0).setLocName(loc);
			result.getInputFiles().get(0).setFilePath(path);
			result.getInputFiles().get(0).setFileName(name);
			result.setStreamType(type);
			result.setStreamNum(strNum);
			result.setIsMain(false);
			return result;
		}
	}
	
	private static PresetGroup loadPGFromFile(String paramPGFilename){
		StringBuffer sb = loadXMLFromFile(paramPGFilename);
		if (sb == null)
			return null;
		PresetGroup newPG = new PresetGroup();
		newPG.setRawxml(sb.toString());
		return newPG;
	}
	
	private static StringBuffer loadXMLFromFile(String filename){
		String lineSep = System.getProperty("line.separator");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			return null;
		}
		String nextLine = "";
		StringBuffer sb = new StringBuffer();
		try {
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine);
				sb.append(lineSep);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		} finally {
			try {br.close();} 
			catch (IOException e) {e.printStackTrace();}
		}
		System.out.println("Loaded " + Integer.toString(sb.length()) + " bytes from file: " + filename);
		return sb;
	}
	
	private static Boolean tasksDone(List<Task> tasks){
		for (Task task: tasks){
			if (task != null){
				String state = task.getState();
				if ( (state != null) && 
					 (!state.equals("finished")) && 
					 (!state.equals("error")))
					return false;
			}
		}
		return true;
	}
	
	private static Task UpdateTaskState(Task task){
		
		TaskState taskState = new TaskState();
		try {
			taskState = port.getTaskState(task.getId());
		} catch (Exception ex) {
			Util.LogError(log, String.format("UpdateTaskState(%s)", Util.ObjectToJson(task)), ex);
			return null;
		}
		if (taskState != null){
			task.setState(taskState.getState());
			task.setProgress(taskState.getProgress());
			task.setQPos(taskState.getQPos());
			task.setLastError(taskState.getLastError());
			task.setStartServerName(taskState.getStartServerName());
			task.setQCFF(taskState.getQCFF());
		}
		else{
			log.error(String.format("port.getTaskState() == null. UpdateTaskState(%s)", Util.ObjectToJson(task))); 
			return null;
		}
		return task;
	}
	
	public static boolean submitJob(	String paramInLoc,	String paramOutFileLoc, 
			String paramOutFilePath, String paramOutFileName, 
			ArrayList<String> paramPresets, 	
			String paramJobName,String paramSName, 		String paramSGName, 
			String paramPGName,	String paramLogFileLoc,	String paramProxy, 
			String paramPGFilename, String optIsWFJob,  String optAllowDuplicate, 
			String paramQCType, String paramQCThr, 		String paramWFSrcA, 
			String paramVFPG, 	String paramAFPG, 
			String paramVFName, String paramAFName, 
			String optIsAJob, String optIsSabet, String paramRuleName, 
			ArrayList<String> paramWFSrcExt, String isAutoStart, 
			ArrayList<String> paramStreams,  String paramMezz, String showProgress, 
			List<UVSettings> paramUVSet, String paramBypass, String optFuzz,
			String paramSABETMaxGroupMPixCnt){

		// Initialization
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return false;
		}
		
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == false) {
			log.error("Login failed - submitJob");
			return false;
		}
			
		SubmitJob submitJob = new SubmitJob();
		SubmitWatchJob submitWJob = new SubmitWatchJob();
		/*SubmitJobResponse sjr = null;
		SubmitWatchJobResponse swjr = null;*/
		Job job = new Job();
		job.setName(paramJobName);
		job.setPresetGroup(paramPGName);
		job.setSubmitServerName(paramSName);
		job.setSubmitServerGroupName(paramSGName);
		job.setVideoFilterPresetGroup(paramVFPG);
		job.setAudioFilterPresetGroup(paramAFPG);
		job.setVideoFilterPresetName(paramVFName);
		job.setAudioFilterPresetName(paramAFName);
		job.setIsMezzanineFormat(false);
		if (paramMezz != null)
			job.setIsMezzanineFormat(true);
		
		//PProc CL args, not sure how to fetch them from CL
		
		job.setPresetGroupProxy("proxy");
		job.setOutFileLoc(paramOutFileLoc);
		if(paramBypass!=null){
			job.setIsBypass(true);
			String[] bypass=paramBypass.split(":");
			BypassParameter bp=new BypassParameter();
			for(int i=0;i<bypass.length;i++){
			if("VF".equals(bypass[i])){
				bp.setVFramerate(true);
			}else if("R".equals(bypass[i])){
				bp.setVResolution(true);
			}else if("FA".equals(bypass[i])){
				bp.setVFrameAspect(true);
			}else{
				System.out.println("Error passthrough parameter: "+bypass[i]+",the right parameter should be:VF,R,FA.");
			}
		}
			
		if(optFuzz!=null){
			bp.setFuzz(true);
		}
			job.setBypass(bp);
		}

		if (optIsWFJob != null) {
			job.setType("W");
//			String[] exts = null;
//			if (paramWFSrcExt != null)
//				exts = paramWFSrcExt.toArray(new String[paramWFSrcExt.size()]);
			if (paramWFSrcExt != null)
				job.getWFextensions().addAll(paramWFSrcExt);
			job.setWFRefreshTime(32);
			job.setWFSourceAction(paramWFSrcA);
			job.setWFSourceActionLocID(37);
			job.setWFSubmitQueueLen(5);
			if (optAllowDuplicate != null)
				submitWJob.setAllowConcurrent(true);
		} else if (optIsAJob != null){
			job.setType("A");
		}

		if (optIsSabet != null) {
			job.setIsSabet(true);//SABET transcoding job
			
			if (paramSABETMaxGroupMPixCnt != null) {
				Integer temp = -1;
				try {
					temp = Integer.decode(paramSABETMaxGroupMPixCnt);
				} catch (NumberFormatException e) {
					System.out.println("Parameter decode fail: '-sabet-max-group-mpix-cnt' must be an non-negative integer");
					//System.exit(-1);
					Util.LogError(log, "submitJob Error: Parameter decode fail: '-sabet-max-group-mpix-cnt' must be an non-negative integer", e);
					return false;
				}
				if (temp < 0) {
					System.out.println("Parameter value fail: '-sabet-max-group-mpix-cnt' must be an non-negative integer");
					//System.exit(-1);
					log.error("submitJob Error: Parameter value fail: '-sabet-max-group-mpix-cnt' must be an non-negative integer");
					return false;
				} else 
					job.setSABETMaxGroupMPixelCount(temp);
			} else
				job.setSABETMaxGroupMPixelCount(-1);
		} else {
			job.setIsSabet(false);
			job.setSABETMaxGroupMPixelCount(-1);
		}

		//get input streams
		ArrayList <InputStream> alis = null;
		if (paramStreams != null) {
			alis = new ArrayList <InputStream>();
			
			for (int ii=0; ii<paramStreams.size(); ii++) {
				String test = paramStreams.get(ii);
				int numStreams = toNumber(test);
				if (numStreams == -1) {
					//System.out.println("Error parsing streams at position " + ii + ": " + test + "(Number of streams expected)");
					log.error("submitJob Error: parsing streams at position " + ii + ": " + test + "(Number of streams expected)");
					//System.exit(-1);
					return false;
				} else if (numStreams > 20) {
					//System.out.println("No more than 20 streams allowed per preset");
					log.error("submitJob Error: No more than 20 streams allowed per preset");
					//System.exit(-1);
					return false;
				} else {
					//load streams
					int jj = 0;
					for (jj=0; jj<numStreams; jj++) {
						String content = paramStreams.get(ii+jj+1);
						Stream stream = new Stream(content);
						if (!stream.parse()) {
							System.out.println("Error parsing stream info: " + content);
							log.error("Error parsing stream info: " + content);
							//System.exit(-1);
							return false;
						}
						alis.add(stream.getData());
					}
					ii += jj;
				}
			}
		}

		//fill presets[]
		int numPresets = 0;
		List<String> command2 = null;
		if (paramPresets != null){
			numPresets = paramPresets.size();
			command2 = new ArrayList<String>(numPresets);
			for (int i=0; i<numPresets; i++){
				command2.set(i, new String(paramPresets.get(i)));
			}
		}

		job.setOutputFilePath(paramOutFilePath);
		job.setOutputFileName(paramOutFileName);

		//tmpPG stuff
		PresetGroup newPG = null;
		if (paramPGFilename != null){
			newPG = loadPGFromFile(paramPGFilename);
			if (newPG == null){
				//System.out.println("Failed loading tmpPG file: " + paramPGFilename);
				log.error(String.format("submitJob error: Failed loading tmpPG file: %s", paramPGFilename));
				return false;
			}
			newPG.setDescription("temporary");
			newPG.setName("");
		}
		/*Client client = ClientProxy.getClient(port);
		//Options optionssc = port._getServiceClient().getOptions();
		Object option = client.getRequestContext().put(org.apache.cxf.message.Message.HTTP_REQUEST_METHOD, Boolean.TRUE);
		
		//optionssc.setProperty(org.apache.axis2.transport.http.HTTPConstants.MC_GZIP_REQUEST, Boolean.TRUE);		
*/
		if (paramUVSet != null) {
			UVSettings[] uvarr = paramUVSet.toArray(new UVSettings[paramUVSet.size()]);
			//submitJob.setUVSettings(uvarr);
		}

		job.setOutputFilePath(paramOutFilePath);
		job.setOutputFileName(paramOutFileName);
		
		if (command2 != null && command2.size() > 0)
			submitWJob.getPresets().addAll(command2);
		if (optIsWFJob != null){
			//submitWJob.setPresets(command2);
			//submitWJob.getPresets().addAll(command2);
			if (paramProxy != null)
				submitWJob.setMakeProxy(true);
			else 
				submitWJob.setMakeProxy(false);
			job.setWatchLoc(paramInLoc);
			
			if (optIsAJob != null)
				job.setType("WA");
			else if ((paramRuleName != null) && (paramRuleName.length() > 0))
				job.setType("WR");
			else
				job.setType("WT");
			
			submitWJob.setWatchJob(job);
			if(isAutoStart!=null)
				submitWJob.setIsAutoStart(true);
			else
				submitWJob.setIsAutoStart(false);
			
			submitWJob.setPriority(-1);
			submitWJob.setRuleName(paramRuleName);
			try {
				job = port.submitWatchJob(submitWJob.getWatchJob(), submitWJob.getPresets(), submitWJob.getPriority(),
						submitWJob.isMakeProxy(), submitWJob.isIsAutoStart(), submitWJob.getRuleName(), submitWJob.isAllowConcurrent());
			} catch (Exception ex) {
				//log.error("submitJob Error: Fail to submitWatchJob() \n" + Util.getStackTrace(ex));
				Util.LogError(log, "submitJob Error: Fail to submitWatchJob()", ex);
				return false; 
			}
			if (job == null){
				String errMsg = "submitJob Error: null = submitWatchJob(). LastError: " + GetLastError();
				System.out.println(errMsg);
				log.error(errMsg);
				return false; // to make eclipse happy 
			}
		} else {
			//submitJob.setPresets(command2);
			
			submitJob.setTmpPG(newPG);
			if (paramProxy != null)
				submitJob.setMakeProxy(true);
			else 
				submitJob.setMakeProxy(false);
			
			submitJob.setJob(job);
			if (alis == null) {
				//System.out.println("Input stream(s) must be defined for regular job");
				log.error(String.format("submitJob error: Input stream(s) must be defined for regular job"));
				return false; 
			}
			
			//submitJob.setInStreams(alis.toArray(new InputStream[alis.size()]));
			submitJob.getInStreams().addAll(alis);
			submitJob.setPriority(-1);
			try {
				job = port.submitJob(submitJob.getJob(), submitJob.getPresets(), submitJob.getInStreams(), submitJob.getPriority(),
						submitJob.isMakeProxy(), submitJob.getTmpPG(), submitJob.getUVSettings());
			} catch (Exception ex) {
				log.error("Fail to port.submitJob" + Util.getStackTrace(ex));
				return false; 
			}
			if (job == null){
				//System.out.println("submitJob failed: " + GetLastError());
				log.error(String.format("submitJob failed: %s", GetLastError()));
				return false; 
			}
		}

		System.out.println("-----------------------------------");
		System.out.println("Server: " + job.getSubmitServerName());
		System.out.println("Group   " + job.getSubmitServerGroupName());
		System.out.println("Job     " + job.getName());
		if (optIsWFJob != null)
			System.out.println("Watch   Yes");
		System.out.println("ID      " + job.getId());
		System.out.println("PG      " + job.getPresetGroup());
		System.out.println("PG      " + job.getOutputFileName());
		System.out.println("-----------------------------------");

		if (optIsWFJob == null){
			System.out.println("Submitjob successfully!");
		} else {
			System.out.println("Watch folder has been created successfully");
		}

		if(showProgress != null){
			List<Task> tasks = job.getTasks();
			if (job.getId() != 0){
				tasks = job.getTasks();
				if (tasks != null) {
					int taskCount = tasks.size();					
					System.out.println("tasks   " + taskCount);
					for (int i = 0; i < taskCount; i++){
						Task task = tasks.get(i);
						if (task.getId() != 0){
							System.out.println("-----------------------------------");
							System.out.println("ID    : " + task.getId());
							System.out.println("Preset  " + task.getPreset());
							if (optIsWFJob == null){
								System.out.println("Name    " + task.getName());
								System.out.println("qPos    " + task.getQPos());
							}
						} else {
							System.out.println("Error adding task: " + task.getName());
						}
					}
				}
			} else {
				System.out.println("Error adding job to queue: " + job.getLastError());
				System.out.println("                         : " + GetLastError());
				System.out.println("-----------------------------------");
				//System.exit(-1);
				log.error("Error adding job to queue: " + job.getLastError());
				return false;
			}			
			
			if ((optIsWFJob == null) && (tasks != null)) {
				//track task progress
				System.out.println("Task progress tracking in progress");
				double [] progress_ 	= new double[tasks.size()];
				double [] progressLast_ = new double[tasks.size()];
				String [] state_ 		= new String[tasks.size()];
				String [] stateLast_	= new String[tasks.size()];
				Integer[] queueLen_  	= new Integer[tasks.size()];
				Integer[] queueLenLast_ = new Integer[tasks.size()];
				while(!tasksDone(tasks)){
					String errMsg = "";
					
					for (int i = 0; i < tasks.size(); i++){
						Task task = tasks.get(i);
						String  taskName = task.getName();
						task = UpdateTaskState(task);
						if (task != null) {
							stateLast_[i] 	= state_[i];
							progressLast_[i]= progress_[i];
							queueLenLast_[i]= queueLen_[i];
							state_[i] 		= task.getState();
							progress_[i] 	= task.getProgress();
							queueLen_[i] 	= -1;
							
							double 	progress 	= progress_[i];
							double 	progressLast= progressLast_[i];
							String 	state 		= state_[i];
							String 	stateLast	= stateLast_[i];
							Integer queueLen  	= queueLen_[i];
							Integer	queueLenLast= queueLenLast_[i];
							
							//	Q state
							if ((state!=null) && !state.equals(stateLast)){
								if (state.equals("queue")){
									System.out.println("T: "+taskName+" Waiting on queue");
								}
								else if (state.equals("queue")){
									System.out.println("T: "+taskName+" submitted to server: " + task.getStartServerName());
								}
								else if (state.equals("finish")){
									System.out.println("T: "+taskName+" removed from server: " + task.getStartServerName());
									if ((task.getQCFF() & 1) == 1)
										log.error("submitJob Error: Failed on QC Avg");
									if ((task.getQCFF() & 2) == 2)
										log.error("submitJob Error: Failed on QC Min");
									if ((task.getQCFF() & 4) == 4)
										log.error("submitJob Error: Failed on QC Worst");
									continue;
								}
								else if (state.equals("error")){
									if (task.getLastError() == null)
										task = UpdateTaskState(task);
									if (task != null)
									{
										errMsg = ("Q state T: " + taskName + " transcoding in error: " + task.getLastError());
									}
									else
									{
										errMsg = String.format("Q state UpdateTaskState(task) == null. Unexpected error updating task state");
									}
									log.error(errMsg + "\nPlease check REWS/TWS log for details");
									continue;
								}
							}
							
							//Q position
							if ((queueLen!=null) && !queueLen.equals(queueLenLast)){
								System.out.println("T: "+taskName+" Queue length: " + queueLen);
							}
							
							//	Server state
							//	paused, error, finished
							if ((state!=null) && !state.equals(stateLast)){
								if (state.equals("run")){
									System.out.println("T: "+taskName+" transcoding started on server: " + task.getStartServerName());
								}
								else if (state.equals("hold")){
									System.out.println("T: "+taskName+" task put on hold on server " + task.getStartServerName());
								}
								else if (state.equals("error")){
									if (task.getLastError() == null)
										task = UpdateTaskState(task);
									if (task != null)
										errMsg = ("Server state T: "+taskName+" transcoding in error: " + task.getLastError());
									else
										errMsg = String.format("Server state UpdateTaskState(task) == null. Unexpected error updating task state");
									log.error(errMsg + "\nPlease check REWS/TWS log for details");
									continue;
								}
								else if (state.equals("finish")){
									System.out.println("T: "+taskName+" transcoding finished on server: " + task.getStartServerName());
								}
							}
							if ((state!=null) && state.equals("run")){
								if (progress != progressLast){
									System.out.println("T: "+taskName+" progress: " + progress);
								}
							}
						}else{
							System.out.println("T: "+taskName+" task lost: exiting");
							System.out.println("-----------------------------------");
							//System.exit(-1);
							log.error("T: "+taskName+" task lost: exiting");
							return false;
						}
					}
					try {Thread.sleep(1000);} catch (InterruptedException e) {}
				}		
			}
		}
		
		return true;	
	}
	
	public static boolean getVideoResolution(String fileName, int outWidth, int outHeight) {
		if(null == fileName)
			return false;
		
		// Initialization
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return false;
		}
		
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == false) {
			log.error("Login failed - getVideoResolution");
			return false;
		}
		
		// Check if axml exists
		List<String> fileNames = new LinkedList<String>();
		fileNames.add(fileName);
		
		List<String> filePaths = new LinkedList<String>();
		filePaths.add("");
		
		List<MediaInfo> mediaInfos = TCESOAPUtils.getFileInfos("In", fileNames, filePaths);
		if(null == mediaInfos) {
			return false;
		}

		MediaInfo inputMedia = mediaInfos.get(0);
		VideoStream vStream = inputMedia.getVss().get(0);
		if(null == vStream)
			return false;
		
		String height = vStream.getHeight();
		String width = vStream.getWidth();

		try {
			outWidth = Integer.parseInt(width);
			outHeight = Integer.parseInt(height);
		} catch(NumberFormatException ex) {
			return false;
		}
				
		return true;	
	}
	
	public static String ffmpgPadFilter(int srcWidth, int srcHeight, double outAspect){
		if (srcWidth == 0 || srcHeight == 0)
			return "";
		
		double inFrameAspect = (double)(srcWidth) / srcHeight;
		final String padFormat="pad=%d:%d:%d:%d:black";
		String result="";
		if (Math.abs(inFrameAspect - outAspect) > 0.01) {
			int outWidth;
			int outHeight;
			int x;
			int y;
			if (outAspect > inFrameAspect) {
				outWidth = (int)(srcHeight * outAspect);
				outHeight = srcHeight;
				y = 0;
				x= (int) ((outWidth - srcWidth) / 2);
				if (x % 2 == 1)
					x++;
			} else {
				outHeight =(int)( srcWidth/outAspect);
				outWidth=srcWidth;
				x=0;
				y = (int) ((outHeight - srcHeight) / 2);
				if (y % 2 == 1)
					y++;
			}
			result=String.format(padFormat, outWidth, outHeight, x,y);
			
		}
				
		return result;
	}
	
	public static boolean addFilter(String filterName, String filter) {
		if(null == filter || null == filterName)
			return false;
		
		// Initialization
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return false;
		}
				
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == false) {
			log.error("AddFilter -> Login failed");
			return false;
		}

		PresetGroup newPG = new PresetGroup();
		newPG.setRawxml(filter);

		newPG.setDescription("");
		newPG.setName(filterName);
		
		int result = 0;
		try {
			result = port.addPresetGroup(newPG);
		} catch (Exception ex) {
			//log.error("Exception to add filter " + filterName + ". Error " + Util.getStackTrace(ex));
			Util.LogError(log, String.format("Exception to add filter(%s)", filterName), ex);
			return false;
		}

		if (result <= 0){
			log.error("Failed to add filter " + filterName);
			return false;
		}else{
		 return true;
		}
	}
	
	public static void deletePG(String pgName){
		// Initialization
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return;
		}
				
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == false) {
			log.error("Login failed");
			return;
		}
		boolean result = false;
		try {
			result = port.deletePresetGroup(pgName);
		} catch (Exception ex) {
			log.error("Fail to deletePG:" + Util.getStackTrace(ex));				
		}
		if (result == false)
			log.error("DeletePresetGroup failed: " + pgName);
				
		return;
	}
	
	public static boolean checkFilterIsExist(String pgName){
		if(null == mStub) {
			// Connect to a service
			if (!loadStub(Constants.TCE_URI))
				return false;
		}
				
		if (doLogin(Constants.TCE_LOGIN_USER, Constants.TCE_LOGIN_PASSWORD) == false) {
			log.error("AddFilter -> Login failed");
			return false;
		}
		try{
			PresetGroup obj = port.getPresetGroup(pgName);
			if(obj != null && obj.getIsFilter().intValue() > 0){
				return true;
	
			}
		}catch (Exception ex) {
			log.error("Fail to listPresetGroups:" + Util.getStackTrace(ex));				
		}
		
		return false;
		
	}
	
}
