package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QueryUserOption")
public class QueryUserOption {
	public enum QueryType
	{
		all,
		friend,
		admin,
		guest
	}
	
	private QueryType queryType;

	public QueryType getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
}
