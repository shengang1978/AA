package com.divx.service.domain.manager;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.Util;
import com.divx.service.domain.model.DcpOrganization;
import com.divx.service.domain.model.DcpOrgsite;
import com.divx.service.domain.repository.OrgSiteDao;
import com.divx.service.model.OrgSite;
import com.divx.service.model.OrgSitesResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;

@Service
public class SiteManager {
	private OrgSiteDao orgSiteDao;
	private static final Logger log =  Logger.getLogger(SiteManager.class);
	
	@Autowired
	public void setOrgSiteDao(OrgSiteDao orgSiteDao)
	{
		this.orgSiteDao = orgSiteDao;
	}
	
	public OrgSitesResponse findOrgSite(String siteName, int startPos, int endPos)
	{
		OrgSitesResponse res = new OrgSitesResponse();
		
		try
		{
			List<OrgSite> result = new LinkedList<OrgSite>();
			List<DcpOrgsite> objs = orgSiteDao.findOrgSite(siteName);
			
			if (objs != null && !objs.isEmpty())
			{
				if (endPos > objs.size())
					endPos = objs.size();
				
				if (startPos > endPos)
					startPos = endPos;
				
				objs = objs.subList(startPos, endPos);
				for(DcpOrgsite obj: objs)
				{
					OrgSite site = new OrgSite();
					site.setId(obj.getId());
					site.setOrgName(obj.getDcpOrganization().getTitle());
					site.setSiteUrl(obj.getSiteurl());
					site.setSiteId(obj.getSiteId());
					
					result.add(site);
				}
			}
			res.setStartPos(startPos);
			res.setCount(result.size());
			res.setSites(result);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			Util.LogError(log, String.format("findOrgSite(%s) exception", siteName), e);
		}
		
		return res;
	}
	
	public ServiceResponse setOrgSite(OrgSite site)
	{
		ServiceResponse res = new ServiceResponse();
		try
		{
			DcpOrganization org = orgSiteDao.findOrgByTitle(site.getOrgName());
			DcpOrgsite obj = new DcpOrgsite();
			obj.setDcpOrganization(org);
			obj.setSiteId(site.getSiteId());
			obj.setSitename(site.getSiteName());
			obj.setSiteurl(site.getSiteUrl());
			
			int ret = orgSiteDao.setOrgSite(obj);
			res.setResponseCode(ResponseCode.SUCCESS);
			res.setResponseMessage("Success");
		}
		catch(Exception e)
		{
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(e.getMessage());
			if (site != null)
				Util.LogError(log, String.format("setOrgSite(%s,%s) exception", site.getOrgName(), site.getSiteName()), e);
			else
				Util.LogError(log, String.format("setOrgSite(null) exception"), e);
		}
		return res;
	}
}
