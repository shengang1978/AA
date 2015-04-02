package com.divx.service.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ThumbnailOptions")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ThumbnailOptions")
public class ThumbnailOptions {
	public enum Format
	{
		jpg,
		png
	}
	
	private int assetId;
	private String videoLocation;
	private String outputPath;
	private Format format;
	private String resolutions;
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public String getVideoLocation() {
		return videoLocation;
	}
	public void setVideoLocation(String videoLocation) {
		this.videoLocation = videoLocation;
	}
	public String getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	public Format getFormat() {
		return format;
	}
	public void setFormat(Format format) {
		this.format = format;
	}
	public String getResolutions() {
		return resolutions;
	}
	public void setResolutions(String resolutions) {
		this.resolutions = resolutions;
	}
}
