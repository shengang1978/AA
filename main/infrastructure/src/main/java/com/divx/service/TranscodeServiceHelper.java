package com.divx.service;


import java.util.List;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;




public class TranscodeServiceHelper extends ServiceResponse {
	
	public enum Format
	{
		jpg,
		png
	}
	public class ThumbnailOptions {
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
	ThumbnailOptions ThumbnailOptions;
	public class ThumbnailsResponse extends ServiceResponse {
		private List<Thumbnail>	thumbnails;

		public List<Thumbnail> getThumbnails() {
			return thumbnails;
		}

		public void setThumbnails(List<Thumbnail> thumbnails) {
			this.thumbnails = thumbnails;
		}
	}
		public class Thumbnail {
			private int assetId;
			private String outputFolder;
			private String filename;
			private String format;	//jpg, png, ...
			private String resolution; //1920*1080, 188*122, ...
			public int getAssetId() {
				return assetId;
			}
			public void setAssetId(int assetId) {
				this.assetId = assetId;
			}
			public String getOutputFolder() {
				return outputFolder;
			}
			public void setOutputFolder(String outputFolder) {
				this.outputFolder = outputFolder;
			}
			public String getFilename() {
				return filename;
			}
			public void setFilename(String filename) {
				this.filename = filename;
			}
			public String getFormat() {
				return format;
			}
			public void setFormat(String format) {
				this.format = format;
			}
			public String getResolution() {
				return resolution;
			}
			public void setResolution(String resolution) {
				this.resolution = resolution;
			}
		}
	public ThumbnailsResponse GenerateThumbnails(int assetId,String fileUrl){
		TranscodeServiceHelper.ThumbnailsResponse res = new TranscodeServiceHelper.ThumbnailsResponse();
		try{
			String baseUrl = ConfigurationManager.GetInstance().TranscodeServiceBaseUrl();
			String reqUrl = baseUrl + "/transcode/thumbnail";
			TranscodeServiceHelper ts = new TranscodeServiceHelper();
			ThumbnailOptions tos = new ThumbnailOptions();
			tos.setAssetId(assetId);
			tos.setVideoLocation(fileUrl);
			tos.setFormat(Format.jpg);
			tos.setOutputPath("");
			tos.setResolutions("");
			ts.ThumbnailOptions = tos;
			String ret = Util.HttpPostJson(reqUrl, ts);
			if (ret.isEmpty())
			{
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call Register()");
				return res;
			}
			
			TranscodeServiceHelper.ThumbnailsResponse  tsh = Util.JsonToObject(ret, TranscodeServiceHelper.ThumbnailsResponse.class);
			
			res.setResponseCode(tsh.getResponseCode());
			res.setResponseMessage(tsh.getResponseMessage());
			res.setThumbnails(tsh.getThumbnails());
			
		}catch(Exception ex){
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
			res.setResponseMessage(ex.getMessage());
		}
		
		
		return res;
	}
	
	
	
}
