package com.divx.manager.impl;

import static org.junit.Assert.*;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.divx.common.main.Constants.eJobType;
import com.divx.service.domain.model.DcpPresetgroup;

public class PresetGroupManagerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetPresetGroups() {
		PresetGroupManager pgm = new PresetGroupManager();
		
		int width = 320;
		int height = 240;
		float framerate = (float) 29.749001;
		eJobType format = eJobType.H265;
		
		List<DcpPresetgroup> pgs = pgm.GetPresetGroups(width, height, framerate, format);
		org.junit.Assert.assertTrue(pgs.size() > 0);
		
		for(DcpPresetgroup pg: pgs)
		{
			//Assert.assertTrue("Output height should be less than the source.", pg.getHeight() <= height);
			//Assert.assertTrue("Video format should be " + format.toString(), pg.getFormat() == format.ordinal());
		}
		
//		framerate = 23;
//		pgs = pgm.GetPresetGroups(width, height, framerate, format);
//		for(DcpPresetgroup pg: pgs)
//		{
//			Assert.assertTrue(pg.getFramerate() - 24 < 0.001);
//		}
		
		format = eJobType.H264;
		pgs = pgm.GetPresetGroups(width, height, framerate, format);
		org.junit.Assert.assertTrue(pgs.size() > 0);
		for(DcpPresetgroup pg: pgs)
		{
			//Assert.assertTrue("Output height should be less than the source.", pg.getHeight() <= height);
			//Assert.assertTrue("Video format should be " + format.toString(), pg.getFormat() == format.ordinal());
		}
	}

}
