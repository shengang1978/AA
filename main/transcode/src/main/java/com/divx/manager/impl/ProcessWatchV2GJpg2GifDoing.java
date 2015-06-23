package com.divx.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.divx.common.main.Constants.eJobType;
import com.divx.common.main.Constants.eProcessStatus;
import com.divx.service.Util;
import com.divx.service.domain.dao.TranscodeDao;
import com.divx.service.domain.dao.impl.TranscodeDaoImpl;
import com.divx.service.domain.model.DcpTranscode;

public class ProcessWatchV2GJpg2GifDoing extends BaseWatch{
private static final Logger log = Logger.getLogger(ProcessWatchV2GJpg2GifDoing.class);
	
	public ProcessWatchV2GJpg2GifDoing()
	{
		transcodeDao = new TranscodeDaoImpl();
		
	}
	private TranscodeDao transcodeDao;
	
	@Override
	public void DoTask()
	{
		try{
			super.DoTask();
			updateThreadActiveTime();
			List<Integer> statusList = new LinkedList<Integer>();
			statusList.add(eProcessStatus.V2GVideo2JpgDone.ordinal());
			statusList.add(eProcessStatus.V2GJpg2GifDoing.ordinal());
			List<DcpTranscode> listTranscode = transcodeDao.GetUndoneTranscode(eJobType.V2G.ordinal(), statusList);
			if(null == listTranscode)
				return;
			for(DcpTranscode dcpTrans : listTranscode) {
				try {					
					boolean bDoProcess = true;
					
					if (dcpTrans.getJobtype() == eProcessStatus.V2GJpg2GifDoing.ordinal())
					{
						Date expDate = Util.GetDate(dcpTrans.getDatemodified(), Calendar.HOUR_OF_DAY, 2);
						if (expDate.getTime() < new Date().getTime())
							bDoProcess = false;
					}
					
					if ( bDoProcess )
					{
						V2GManager.DoProcessJpg2Gif(dcpTrans);
					}
				}
				catch (Exception e) {	
					Util.LogError(log, String.format("Exception to DoProcess(%s)", Util.ObjectToJson(dcpTrans)), e);
				}
			}
		}
		catch(Exception ex)
		{
			Util.LogError(log, String.format("Exception to ProcessWatchV2GStep1ExtractJPG.DoTask()"), ex);
		}
	}
}
