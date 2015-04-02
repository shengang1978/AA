package com.divx.service.model;

import java.util.List;

public class UploadOption {
	private int assetId;
	private List<String>	assetFiles;
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public List<String> getAssetFiles() {
		return assetFiles;
	}
	public void setAssetFiles(List<String> assetFiles) {
		this.assetFiles = assetFiles;
	}
}
