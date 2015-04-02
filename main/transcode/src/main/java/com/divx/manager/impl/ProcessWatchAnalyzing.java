package com.divx.manager.impl;


import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

import com.divx.manager.impl.ProcessUtils;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.dao.TranscodeOutputDao;
import com.divx.service.domain.dao.impl.TranscodeDaoImpl;
import com.divx.service.domain.dao.impl.TranscodeOutputDaoImpl;
import com.divx.service.domain.model.DcpTranscode;
import com.divx.common.main.Constants;
import com.divx.common.main.Constants.eProcessStatus;

public class ProcessWatchAnalyzing extends BaseWatch {
	private static final Logger log = Logger.getLogger(ProcessWatchAnalyzing.class);
	
	public enum eNotifyStatus
	{
		unsent,
		sendOut
	}
	
	public ProcessWatchAnalyzing()
	{
		transcodeDao = new TranscodeDaoImpl();
		
	}
	private TranscodeDao transcodeDao;
	
	// Do the task once
	@Override
	public void DoTask()
	{
		try{
			super.DoTask();
			updateThreadActiveTime();
			List<DcpTranscode> listTranscode = transcodeDao.GetUndoneTranscode(eProcessStatus.Analyzing.ordinal());
			if(null == listTranscode)
				return;
			for(DcpTranscode dcpTrans : listTranscode) {
				try {
					if(eProcessStatus.Analyzing.ordinal() == dcpTrans.getStatus()){
							ProcessUtils.watchAnalyzingJob(dcpTrans);				
							
					} 
				}
				catch (Exception e) {	
					Util.LogError(log, "Exception in Process watch Analyzing. Error: ", e);
				}
			}
			
				
			
		} catch(Exception ex){
            log.error("Catch exception for do task in process watch Analyzing. " + Util.getStackTrace(ex));
		}
	}
}
