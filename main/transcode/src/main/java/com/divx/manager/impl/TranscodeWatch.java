package com.divx.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.divx.common.main.Constants.eProcessErrorCode;
import com.divx.common.main.Constants.eProcessStatus;
import com.divx.common.main.TCESOAPUtils;
import com.divx.service.domain.dao.TranscodeJobDao;
import com.divx.service.domain.dao.impl.TranscodeJobDaoImpl;
import com.divx.service.domain.model.DcpTranscodeJob;
import com.divx.service.model.ProcessResponse.Status;
import com.mainconcept.rewsservice.Job;


public class TranscodeWatch extends BaseWatch{
	
	private static final Logger log = Logger.getLogger(TranscodeWatch.class);
	public TranscodeWatch()
	{
		transcodeJobDao = new TranscodeJobDaoImpl();
	}
	private TranscodeJobDao transcodeJobDao;
	
	//Do the watch task once
	@Override
	public void DoTask()
	{
		try{
			super.DoTask();
			List<Job> jobs = null;
			int startPos  = 0;
			int pageSize = 100;
			do{
				jobs = TCESOAPUtils.getJobs(startPos,pageSize);
				if ((0 != jobs.size()) && (null != jobs.get(0))) {
					//log.info("Found jobs: " + jobs.size());
					for (Job job: jobs) {
						updateJobStatus(job);									
					}
				}
				startPos ++;
			}while(jobs.size() >= 100);
			/*List<Job> jobs = TCESOAPUtils.getJobs();
			if ((0 != jobs.size()) && (null != jobs.get(0))) {
				//log.info("Found jobs: " + jobs.size());
				for (Job job: jobs) {
					updateJobStatus(job);									
				}
			}*/
		}catch(Exception ex){
            log.error("Catch exception for gettig jobs ", ex);
		}
	}
	
	private void updateJobStatus(Job job) {
		String jobName = job.getName();
		
		DcpTranscodeJob dcpTransJob = transcodeJobDao.GetTranscodeJobByJobName(jobName);
		if(dcpTransJob == null) {
			//log.error("Cannot get DcpTranscode for job name " + jobName);
			return;
		}

		// Analyze job
		Status status = TCESOAPUtils.convert2Status(job.getState());
		if(job.getType().equals("A")) {
			if(eProcessStatus.Analyzing.ordinal() == dcpTransJob.getStatus()) {
				if(Status.finished == status) {
					dcpTransJob.setStatus(eProcessStatus.FinishAnalysis.ordinal());
					dcpTransJob.setDatemodifed(new Date());
					updateDcpTranscodeJob(dcpTransJob);
				} else if (Status.error == status || Status.aborted == status) {
					dcpTransJob.setStatus(eProcessStatus.Error.ordinal());
					dcpTransJob.setDatemodifed(new Date());
					updateDcpTranscodeJob(dcpTransJob);
				}
				
				return;
			}
			else if(eProcessStatus.FinishAnalysis.ordinal() == dcpTransJob.getStatus()) {
				if(Status.finished == status) {
					//if(compareToNow(job.getTsFinish()) > 7) {
						TCESOAPUtils.removeJob(job.getId());
					//	log.info("Remove analyze job " + jobName);
					//}
				}
			}

			return;
		}
		
		// Transcode job
		if(eProcessStatus.Transcoding.ordinal() == dcpTransJob.getStatus()) {
			if(Status.finished == status) {
				dcpTransJob.setStatus(eProcessStatus.FinishTranscode.ordinal());
				dcpTransJob.setDatemodifed(new Date());
				updateDcpTranscodeJob(dcpTransJob);
			} else if (Status.error == status || Status.aborted == status) {
				dcpTransJob.setStatus(eProcessStatus.Error.ordinal());
				dcpTransJob.setDatemodifed(new Date());
				updateDcpTranscodeJob(dcpTransJob);
			}
			
			return;
		}
		else if(eProcessStatus.FinishTranscode.ordinal() == dcpTransJob.getStatus()) {
			if(Status.finished == status) {
				//if(compareToNow(job.getTsFinish()) > 7) {
					TCESOAPUtils.removeJob(job.getId());
				//	log.info("Remove analyze job " + jobName);
				//}
			}
		}
	}
	
	private int compareToNow(String strDate) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date tsfinish = df.parse(strDate);
			
			return getIntervalDays(tsfinish, new Date());
		} catch (ParseException pe) {
			return 0;
		}
	}
		
	private int getIntervalDays(Date date1, Date date2) {
		if(null == date1 || null == date2)
			return 0;
		
		long intervalMilli = date2.getTime() - date1.getTime();
		return (int)(intervalMilli /(24*60*60*1000));
	}
	
	private void updateDcpTranscodeJob(DcpTranscodeJob dcpTransJob) {
		if(0 >= transcodeJobDao.UpdateTranscodeJob(dcpTransJob)) {
			log.error("Failed to update DcpTranscodeJob for job name" + dcpTransJob.getJobname());
		}
	}
}
