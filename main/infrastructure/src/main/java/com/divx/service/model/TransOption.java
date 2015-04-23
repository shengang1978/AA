package com.divx.service.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TransOption")
public class TransOption {
	private int assetId;
	private MediaBaseType.eContentType contentType;
	private String v2gOption;
	private List<String> locations;
	public MediaBaseType.eContentType getContentType() {
		return contentType;
	}
	public void setContentType(MediaBaseType.eContentType contentType) {
		this.contentType = contentType;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}

	public String getV2gOption() {
		return v2gOption;
	}
	public void setV2gOption(String v2gOption) {
		this.v2gOption = v2gOption;
	}
	public List<String> getLocations() {
		return locations;
	}
	public void setLocations(List<String> locations) {
		this.locations = locations;
	}
}
