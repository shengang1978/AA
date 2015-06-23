package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "QueryOption")
public class QueryOption {
		public enum QueryType
		{
			all,
			mine,
			friend,
			pseudo,
			story
		}
		public enum MediaType
		{
			book,
			story
		}
		public enum SearchType
		{
			newest,
			hottest
		}
		private QueryType queryType;
		//private int targetId;
		private MediaType mediaType;
		private SearchType searchType;
		public QueryType getQueryType() {
			return queryType;
		}

		public void setQueryType(QueryType queryType) {
			this.queryType = queryType;
		}

		public MediaType getMediaType() {
			return mediaType;
		}

		public void setMediaType(MediaType mediaType) {
			this.mediaType = mediaType;
		}

		public SearchType getSearchType() {
			return searchType;
		}

		public void setSearchType(SearchType searchType) {
			this.searchType = searchType;
		}

		/*public int getTargetId() {
			return targetId;
		}

		public void setTargetId(int targetId) {
			this.targetId = targetId;
		}*/
		
}
