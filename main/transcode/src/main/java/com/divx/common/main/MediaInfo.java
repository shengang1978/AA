package com.divx.common.main;
import org.apache.log4j.Logger;

import com.divx.service.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MediaInfo {
	private static String CMD_MEDIAINFO_ROTATION = "%s --Inform=Video;%s %s";
	private static final Logger log =  Logger.getLogger(MediaInfo.class);
	private static final String FRAMERATE_CFR="CFR";
	
	private boolean IsValid = false;
	private double Rotation=0;
	private int Width=0;
	private int Height=0;
	private double FrameRate;
	private boolean IsFrameRateCFR;
	private double FrameRateMaximum;
	private double FrameRateMinimum;
	private int Duration;
	
	private StringBuilder mPrameters = new StringBuilder();
	private int mParameterCount = 0;
	public MediaInfo(String filePath) {
		
		AddParameter("Rotation");
		AddParameter("Width");
		AddParameter("Height");
		AddParameter("FrameRate");
		AddParameter("FrameRate_Mode");
		AddParameter("FrameRate_Minimum");
		AddParameter("FrameRate_Maximum");
		AddParameter("Duration"); 
		
		String mediaCMD = String.format(CMD_MEDIAINFO_ROTATION, Constants.MEDIAINFO_PATH, mPrameters.toString(), filePath);
		try {
			Runtime rtMediaInfo = Runtime.getRuntime();
			Process prMediaInfo = rtMediaInfo.exec(mediaCMD);
			prMediaInfo.waitFor();
			String mediainfoItem;
			BufferedReader brMediaInfo  = new BufferedReader(new InputStreamReader(prMediaInfo.getInputStream()));
			
			List<String> mediaInfoOut = new ArrayList<String>();

			while((mediainfoItem=brMediaInfo.readLine()) != null) {
				mediaInfoOut.add(mediainfoItem);
			}
			
			if (mediaInfoOut.size() == mParameterCount+1){
				IsValid = true;
				int index = 0;
				// Rotation
				String value = mediaInfoOut.get(index++);
				setRotation(this.GetDouble(value, 0.));
				
				// Width
				value = mediaInfoOut.get(index++);
				setWidth(GetInt(value, 1));
								
				//Height
				value = mediaInfoOut.get(index++);
				setHeight(GetInt(value, 1));
				
				// Frame Rate
				value = mediaInfoOut.get(index++);
				setFrameRate(this.GetDouble(value, 1.));

				// Frame Rate Mode
				value = mediaInfoOut.get(index++);
				setIsFrameRateCFR(value.equals(FRAMERATE_CFR));
				
				// Max Frame Rate
				value = mediaInfoOut.get(index++);
				setFrameRateMaximum(GetDouble(value, getFrameRate()));
								
				// Min Frame Rate
				value = mediaInfoOut.get(index++);
				setFrameRateMinimum(GetDouble(value, getFrameRate()));

				// Duration
				value = mediaInfoOut.get(index++);
				this.setDuration(GetInt(value, 0));
			}
		}
		catch(Exception e) {
			//log.error("Failed to get rotation from: " + filePath + ". Error: " + e.getMessage());
			Util.LogError(log, String.format("Failed to get rotation from: %s", mediaCMD), e);
		}
	}
	private int GetInt(String value, int defaultvalue) {
		int rtn = defaultvalue;
		try {
		if(!value.isEmpty())
			rtn = Integer.parseInt(value);
		}
		catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		return rtn;
	}
	
	private double GetDouble(String value, double defaultValue) {
		double rtn = defaultValue;
		try {
		if(!value.isEmpty())
			rtn = Double.parseDouble(value);
		}
		catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		return rtn;
	}
	private void AddParameter(String parameter) {
		mParameterCount++;
		this.mPrameters.append("%");
		this.mPrameters.append(parameter);
		this.mPrameters.append("%\\n");
		
	}
	public boolean isIsValid() {
		return IsValid;
	}
	
	public boolean isIsFrameRateCFR() {
		return IsFrameRateCFR;
	}

	private void setIsFrameRateCFR(boolean isFrameRateCFR) {
		IsFrameRateCFR = isFrameRateCFR;
	}

	public double getFrameRate() {
		return FrameRate;
	}

	private void setFrameRate(double frameRate) {
		FrameRate = frameRate;
	}

	public double getFrameRateMaximum() {
		return FrameRateMaximum;
	}

	private void setFrameRateMaximum(double frameRateMaximum) {
		FrameRateMaximum = frameRateMaximum;
	}

	public double getFrameRateMinimum() {
		return FrameRateMinimum;
	}

	private void setFrameRateMinimum(double frameRateMinimum) {
		FrameRateMinimum = frameRateMinimum;
	}

	public double getRotation() {
		return Rotation;
	}

	private void setRotation(double rotation) {
		Rotation = rotation;
	}

	public int getWidth() {
		return Width;
	}

	private void setWidth(int width) {
		Width = width;
	}

	public int getHeight() {
		return Height;
	}

	private void setHeight(int height) {
		Height = height;
	}

	public int getDuration() {
		return Duration;
	}

	private void setDuration(int duration) {
		Duration = duration;
	}
}
