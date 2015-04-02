package com.divx.service.impl;

import org.apache.log4j.Logger;

import com.divx.service.model.EndPublishOptionShell.EndPublishOption;

public class FileAdapter implements StorageAdapter {
	private Logger log = Logger.getLogger(FileAdapter.class);
	
	@Override
	public String process(EndPublishOption endPublishOption) {
		
		log.error("Should not come here");
		return "";
	}

}
