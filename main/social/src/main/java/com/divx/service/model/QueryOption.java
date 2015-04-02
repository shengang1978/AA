package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QueryOption")
public class QueryOption {
		public enum QueryType
		{
			all,
			mine,
			friend,
			pseudo
			
		}
		
		private QueryType queryType;
		//private int targetId;

		public QueryType getQueryType() {
			return queryType;
		}

		public void setQueryType(QueryType queryType) {
			this.queryType = queryType;
		}

		/*public int getTargetId() {
			return targetId;
		}

		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}*/
		
}
