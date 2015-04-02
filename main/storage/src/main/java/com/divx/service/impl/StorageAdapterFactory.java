package com.divx.service.impl;

import com.divx.service.model.Adapter.AdapterType;

public class StorageAdapterFactory {
	public StorageAdapter create(AdapterType adapterType){
		switch(adapterType){
		case File:
			return new FileAdapter();
		case Hadoop:
			return new HadoopAdapter();
		case AmazonS3:
			return new AmazonS3Adapter();
		case OSS:
			return new OSSAdapter();
		}
		return null;
		
	}

}
