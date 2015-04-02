package com.divx.service.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.divx.service.ConfigurationManager;
import com.divx.service.model.EndPublishOptionShell.EndPublishOption;
import com.divx.service.model.KeyValuePair;

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.transform.Transformer;  
import javax.xml.transform.TransformerFactory;  
import javax.xml.transform.dom.DOMSource;  
import javax.xml.transform.stream.StreamResult;  
  

import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.NodeList;

public class CloudAdapter implements StorageAdapter {
	protected static String InUrl = ConfigurationManager.GetInstance().UploadDestFolder();
	
	protected static String OutUrl = ConfigurationManager.GetInstance().TCE_LOCATION_OUT();
	
	protected static String smilBaseUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
	@Override
	public String process(EndPublishOption endPublishOption) {
	
		
		return "";
	}
	public KeyValuePair<String,List<String>> getAllFile(EndPublishOption endPublishOption){
		String smilUrl  = endPublishOption.getSmilPath();
		String smilFilePath = smilUrl.replace(smilBaseUrl, OutUrl);
		String parentFilePath = smilFilePath.substring(0, smilFilePath.lastIndexOf('/'));
		
		File filePath = new File(parentFilePath);
		File[] fileList = filePath.listFiles();
		String parentFolder = parentFilePath.replace(OutUrl,"");
		List<String> fileNames = new LinkedList<>();
		for(File file : fileList){
			if(file.getName().endsWith(".mkv")){
				fileNames.add(file.getName());
			}
			
		}
		KeyValuePair<String,List<String>> fileInfo = new KeyValuePair<>();
		fileInfo.setKey(parentFolder);
		fileInfo.setValue(fileNames);
		return fileInfo;
	} 
	public void updateSmilFile(String smilFilePath, List<KeyValuePair<String, String>> cloudUrls){
		Element ele =null;
	    Document document = load(smilFilePath); 
	    try{
	    	String parentFolder =  smilFilePath.substring(OutUrl.length(), smilFilePath.lastIndexOf('/'));
	    		NodeList videoList = document.getElementsByTagName("video");
	    		for(int i = 0; i < videoList.getLength(); i++){
	    			ele = (Element) videoList.item(i);
	    			for(int j=0;j<cloudUrls.size();j++){
	    				String oldFileUrl = smilBaseUrl + parentFolder+ "/" +cloudUrls.get(j).getKey();
	    				if(ele.getAttribute("src").equals(oldFileUrl)){
		    				 ele.setAttribute("src", cloudUrls.get(j).getValue());
		    			}	
	    			}
	    			
	 	    	   
	 	    	    
	    		}
	    		NodeList audioList = document.getElementsByTagName("audio");
	    		for(int i = 0; i < audioList.getLength(); i++){
	 	    	    ele = (Element) audioList.item(i);
	 	    	   for(int j=0;j<cloudUrls.size();j++){
	 	    		  String oldFileUrl = smilBaseUrl + parentFolder+ "/" +cloudUrls.get(j).getKey();
	    				if(ele.getAttribute("src").equals(oldFileUrl)){
		    				 ele.setAttribute("src", cloudUrls.get(j).getValue());
		    			}	
	    			}
	 	    	    
	    		}
	 	        
	       }catch(Exception ex){
	    	   
	       }
	     
	       
	    saveXmlFile(document,smilFilePath);   
	}

	public static boolean saveXmlFile(Document document,String filename)   
    {   
      boolean flag = true;   
      try   
       {   
          
             TransformerFactory tFactory = TransformerFactory.newInstance();      
             Transformer transformer = tFactory.newTransformer();    
             
             DOMSource source = new DOMSource(document);    
             StreamResult result = new StreamResult(new File(filename));      
             transformer.transform(source, result);    
         }catch(Exception ex)   
         {   
             flag = false;   
             ex.printStackTrace();   
         }   
        return flag;         
    }  
   
	 public static Document load(String filename)   
	    {   
	       Document document = null;   
	      try    
	       {    
	            DocumentBuilderFactory   factory = DocumentBuilderFactory.newInstance();      
	            DocumentBuilder builder=factory.newDocumentBuilder();      
	            document=builder.parse(new File(filename));      
	            document.normalize();   
	       }   
	      catch (Exception ex){   
	           ex.printStackTrace();   
	       }     
	      return document;   
	    }  
	 

}
