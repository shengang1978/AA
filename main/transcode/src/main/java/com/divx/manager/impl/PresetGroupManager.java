package com.divx.manager.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.divx.common.main.Constants.eJobType;
import com.divx.service.Util;
import com.divx.service.domain.dao.PresetDao;
import com.divx.service.domain.dao.impl.PresetDaoImpl;
import com.divx.service.domain.model.DcpPresetgroup;
import com.divx.service.domain.model.DcpRadio;

public class PresetGroupManager {
	private static final Logger log = Logger.getLogger(PresetGroupManager.class);
	private PresetDao presetDao;
	
	public PresetGroupManager()
	{
		presetDao = new PresetDaoImpl();
	}
	public List<DcpPresetgroup> GetPresetGroups(int width, int height, float framerate, eJobType format)
	{
		List<DcpPresetgroup> result = new LinkedList<DcpPresetgroup>();
		try
		{			
			List<DcpRadio> radios = presetDao.GetRadios();
			
			DcpRadio dr = null;
			
			float radio = (float)width / height;
			
			for(DcpRadio obj: radios)
			{
				if (obj.getRangestart() <= radio && radio < obj.getRangeend())
				{
					dr = obj;
					break;
				}
			}
			
			DcpPresetgroup minPG = null;
			if (dr != null)
			{	
				List<DcpPresetgroup> pgs = presetDao.GetPresetgroups(dr.getRadioId(), format.ordinal());
				for(DcpPresetgroup pg: pgs)
				{
					if (pg.getEnable() 
							&& pg.getFormat() == format.ordinal() 
							&& pg.getFramerate() >= framerate - 0.001)
					{
						if (minPG == null || minPG.getHeight() > pg.getHeight())
						{
							minPG = pg;
						}
						
						if (pg.getHeight() <= height)
						{
							if (result.isEmpty())
								result.add(pg);
							else
							{
								float frGap = result.get(0).getFramerate() - pg.getFramerate();
								if (Math.abs(frGap) <= 0.001)
								{//equal
									result.add(pg);
								}
								else if (frGap > 0.001)
								{
									result.clear();
									result.add(pg);
								}
							}
						}
					}
				}
			}
			if (result.size() == 0 && minPG != null)
			{
				result.add(minPG);
			}
		}
		catch(Exception e)
		{
			result.clear();
			Util.LogError(log, String.format("GetPresetGroups(%d*%d, %f, %s) exception", width, height, framerate, format.toString()), e);
		}
		
		return result;
	}
}
