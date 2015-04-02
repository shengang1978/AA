package com.divx.service.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ScopeQueryOption")
public class ScopeQueryOption {
		private int startPos;
		private int endPos;
		public int getStartPos() {
			return startPos;
		}
		public void setStartPos(int startPos) {
			this.startPos = startPos;
		}
		public int getEndPos() {
			return endPos;
		}
		public void setEndPos(int endPos) {
			this.endPos = endPos;
		}		

		

	
		
}
