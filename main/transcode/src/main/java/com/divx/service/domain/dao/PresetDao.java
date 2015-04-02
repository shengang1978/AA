package com.divx.service.domain.dao;

import java.util.List;
import java.util.Set;

import com.divx.service.domain.model.DcpPresetgroup;
import com.divx.service.domain.model.DcpRadio;

public interface PresetDao {
	List<DcpRadio> GetRadios();
	
	List<DcpPresetgroup> GetPresetgroups(int radioId, int format);
}
