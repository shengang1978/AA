package com.divx.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.log4j.Logger;

import com.divx.service.model.AuthHelperModel;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

public class Util {
	//
	// field: 
	//		Calendar.MONTH
	//		Calendar.DAY_OF_MONTH
	//		...
	// duration:
	//		
	public static Date GetDate(Date baseDate, int field, int duration)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(baseDate);
		cal.add(field, duration);
		return cal.getTime();
	}
	
	public static String HttpGet(String reqUrl) throws Exception
	{
		return HttpGet(reqUrl, null);
//		InputStream in = null;
//		try
//		{
//			URL url = new URL(reqUrl);
//		
//			in = url.openStream();
//			return getStringFromInputStream(in);			
//		}
//		finally
//		{
//			if (in != null)
//				in.close();
//		}
	}
	
	public static String HttpGet(String reqUrl, List<KeyValuePair<String, String>> headers) throws Exception
	{
		//InputStream in = null;
		BufferedReader in = null;
        String result = "";
		try
		{
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            
            if (headers != null)
            {
	            for(KeyValuePair<String, String> kvp : headers)
	            {
	            	conn.setRequestProperty(kvp.getKey(), kvp.getValue());
	            }
            }
            
			/*in = url.openStream();
			return getStringFromInputStream(in);	*/	
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
		}
		finally
		{
			if (in != null)
				in.close();
		}
		  return result;
	}
	
	public static String HttpPost(String reqUrl, String body, List<KeyValuePair<String, String>> headers) throws Exception
	{
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            
            if (headers != null)
            {
	            for(KeyValuePair<String, String> kvp : headers)
	            {
	            	conn.setRequestProperty(kvp.getKey(), kvp.getValue());
	            }
            }

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(body);

            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        finally{
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        return result;
	}
	public static String HttpPut(String reqUrl, String body, List<KeyValuePair<String, String>> headers) throws Exception
	{
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(reqUrl);

            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            
            conn.setRequestMethod("PUT");
            
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            
            if (headers != null)
            {
	            for(KeyValuePair<String, String> kvp : headers)
	            {
	            	conn.setRequestProperty(kvp.getKey(), kvp.getValue());
	            }
            }

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());
            if(!body.isEmpty()){
            	out.print(body);
            }
           

            out.flush();

            if (conn.getResponseCode() >= 400)
            	in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            else
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        finally{
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
        }
        return result;
	}

	public static String HttpPost(String reqUrl, String body) throws Exception
	{
		List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
		headers.add(new KeyValuePair<String, String>("DeviceUniqueId", UUID.randomUUID().toString()));
		headers.add(new KeyValuePair<String, String>("DeviceType", "0"));
		headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
		return HttpPost(reqUrl, body, headers);
    }
	
	public static String HttpPutJson(String url, Object obj, List<KeyValuePair<String, String>> headers) throws Exception
	{
		return HttpPut(url, ObjectToJson(obj), headers);
	}
	public static String HttpPostJson(String url, Object obj) throws Exception
	{
		return HttpPost(url, ObjectToJson(obj));
	}
	
	public static <T> T JsonToObject(String json, Class<T> classOfT)
	{
		return new Gson().fromJson(json, classOfT);
	}
	
	public static String ObjectToJson(Object obj)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		return gson.toJson(obj);
	}
	
	public static boolean MoveFile(String srcPath, String dstPath)
	{
		File srcFile = new File(srcPath);
	    File dstFile = new File(dstPath);
	    return srcFile.renameTo(dstFile);
	}
	
	public static boolean CopyFile(String srcPath, String dstPath)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
	    try {
			inStream = new FileInputStream(new File(srcPath));
		    outStream = new FileOutputStream(new File(dstPath));
		    
		    byte[] buffer = new byte[1024];
		    
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length); 
    	    }
 
    	    inStream.close();
    	    outStream.close();
    	    
    	    return true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	    
		return false;
	}
	
	public static String EncryptDeviceUniqueId(String deviceUniqueId, long userId)
	{
		String encStr = String.format("%d|%s", userId, deviceUniqueId);		
		
		return DatatypeConverter.printBase64Binary(encStr.getBytes());		
	}
	
	public static KeyValuePair<Integer, String> DecryptDeviceGuid(String deviceGuid)
	{
		KeyValuePair<Integer, String> ret = new KeyValuePair<Integer, String>();

		byte[] byDec = DatatypeConverter.parseBase64Binary(deviceGuid);
		String decStr = new String(byDec);
		int nSplitPos = decStr.indexOf("|");
		//String[] ary = decStr.split("|");
		if (nSplitPos >= 0)
		{
			try
			{
				ret.setKey(Integer.parseInt(decStr.substring(0, nSplitPos)));
				ret.setValue(decStr.substring(nSplitPos + 1));
				return ret;
			}
			catch(Exception ex)
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static String EncryptBase64(String content) throws UnsupportedEncodingException
	{
		return java.net.URLEncoder.encode(DatatypeConverter.printBase64Binary(content.getBytes()), "UTF-8");
	}
	
	public static String DecryptBase64(String content)throws UnsupportedEncodingException
	{
		return java.net.URLDecoder.decode(new String(DatatypeConverter.parseBase64Binary(content)), "UTF-8");
	}
	public static <T extends ServiceResponse> Response ServiceResponseToResponse(T obj)
	{
		if (obj.getResponseCode() == ResponseCode.SUCCESS)
		{
			return Response.ok().entity(obj).build();
		}
		else if (obj.getResponseCode() == ResponseCode.AUTH_ERROR_TOKEN_INVALID_OR_NOT_LOGIN)
		{
			return Response.status(Status.UNAUTHORIZED).entity(obj).build();
		}
		else
		{
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(obj).build();
		}
	}
	
	private static String getStringFromInputStream(InputStream in) throws Exception {
        CachedOutputStream bos = new CachedOutputStream();
        IOUtils.copy(in, bos);
        in.close();
        bos.close();
        return bos.getOut().toString();
    }
	
	public static String UrlWithSlashes(String value){
		return value.endsWith("/") ? value : value + "/";
	}
	
	public static String getStackTrace(Exception e)
	{
		StackTraceElement[] error = e.getStackTrace();  
		StringBuffer errorString = new StringBuffer();
		for (int i = 0; i < error.length; i++) { 
			errorString.append(error[i].toString());
			errorString.append("\n\r");
		} 	
		return e.toString() + "---" + errorString.toString();		
	}
	
	public static void LogError(Logger log, String message, Exception e)
	{
		log.error(message + "\n\r" + getStackTrace(e));
	}
}
