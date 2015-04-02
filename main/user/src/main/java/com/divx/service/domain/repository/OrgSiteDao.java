package com.divx.service.domain.repository;

import java.util.List;

import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpOrgsite;

public interface OrgSiteDao {
	List<DcpOrgsite> findOrgSite(String siteName);
	
	int setOrgSite(DcpOrgsite site);
	
	DcpOrganization findOrgByTitle(String orgTitle);
}
