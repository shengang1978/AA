package com.divx.service.domain.repository;

import java.util.List;
import java.util.Map;

import com.divx.service.domain.model.*;

public interface ConfigDao {
	List<DcpConfig>	GetConfigs();
	DcpEmailTemplate getEmailTemplate(int id);
	OsfMailSettings getEmailSetting(int id);
}
