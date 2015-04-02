package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.divx.service.model.ServiceResponse;

@XmlRootElement(name = "FriendResquests")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FriendResquests")
public class FriendResquests extends ServiceResponse {
	private List<FriendRequestOption>	reqList;
	
	public List<FriendRequestOption> getRequestList()
	{
		return reqList;
	}
	
	public void setRequestList(List<FriendRequestOption> reqs)
	{
		reqList = reqs;
	}	
}
