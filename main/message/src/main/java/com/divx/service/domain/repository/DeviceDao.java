package com.divx.service.domain.repository;

import com.divx.service.domain.model.*;

public interface DeviceDao {
	int AddDevice(DcpUserdevice dev);
	int UpdateDevice(DcpUserdevice dev);
	DcpUserdevice GetDevice(String deviceGuid);
	
	int AddRecver(DcpRecver recver);
	int UpdateRecver(DcpRecver recver);	
	DcpRecver GetRecver(int userId);
}
