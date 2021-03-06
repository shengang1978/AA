package com.divx.service.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.EndPublishOption;
import com.divx.service.model.KeyValuePair;

public class AmazonS3Adapter extends CloudAdapter {
	private Logger log = Logger.getLogger(AmazonS3Adapter.class);
	@Override
	public String process(EndPublishOption endPublishOption) {
		KeyValuePair<String, List<String>>  infos= getAllFile(endPublishOption);
		
		List<KeyValuePair<String, String>> urls = uploatToAmazonS3(infos);
		if(urls == null){
			return "";
		}
		String smilFilePath = endPublishOption.getSmilPath().replace(smilBaseUrl, OutUrl);
		updateSmilFile(smilFilePath, urls);
		KeyValuePair<String, List<String>> smilInfo = new KeyValuePair<>();
		smilInfo.setKey(infos.getKey());
		List<String> smilName = new LinkedList<String>();
		smilName.add("smil_out1.smil");
		smilInfo.setValue(smilName);
		List<KeyValuePair<String, String>> smilUrl = uploatToAmazonS3(smilInfo);
		if(smilUrl == null || "".equals(smilUrl.get(0).getValue().toString())){
			return "";
		}
		deleteDirectory(Util.UrlWithSlashes(OutUrl) + Util.UrlWithSlashes(smilInfo.getKey()));
		return smilUrl.get(0).getValue().toString();
	}

	@Override
	public KeyValuePair<String, List<String>> getAllFile(EndPublishOption endPublishOption) {
		
		return super.getAllFile(endPublishOption);
	}


	@Override
	public void updateSmilFile(String smilFilePath,
			List<KeyValuePair<String, String>> cloudUrls) {
		// TODO Auto-generated method stub
		super.updateSmilFile(smilFilePath, cloudUrls);
	}

	public List<KeyValuePair<String, String>> uploatToAmazonS3(KeyValuePair<String, List<String>> infos){
		 
		final String awsAccessKeyId = ConfigurationManager.GetInstance().AwsAccessKeyId();
		final String awsSecretKey =  ConfigurationManager.GetInstance().AwsSecretKey();
		final String bucketName =  ConfigurationManager.GetInstance().AwsBucketName();
		final Regions regions = (Regions) Enum.valueOf(Regions.class, ConfigurationManager.GetInstance().AwsRegions());
		
		//String OutUrl = ConfigurationManager.GetInstance().TCE_LOCATION_OUT();
		
        AWSCredentials credentials = null;
    	List<KeyValuePair<String, String>> fileUrls = new LinkedList<>();
        try {
            //credentials = new ProfileCredentialsProvider().getCredentials();
        	 credentials = new AWSCredentials() {
    			
    			@Override
    			public String getAWSSecretKey() {
    				
    				return awsSecretKey;
    			}
    			
    			@Override
    			public String getAWSAccessKeyId() {
    				
    				return awsAccessKeyId;
    			}
    		};
        } catch (Exception e) {
        	Util.LogError(log, "error awsAccessKeyId or awsSecretKey  for credentials :", e);
        	return null;
           
        }
        AmazonS3 s3 = null;
        try {
        	 ClientConfiguration  conf = new ClientConfiguration();
             conf.setConnectionTimeout(1000000);
             conf.setProtocol(Protocol.HTTP);
            
             s3 = new AmazonS3Client(credentials,conf);
             Region usWest2 = Region.getRegion(regions);
             s3.setRegion(usWest2);
            
            
           
             if(!s3.doesBucketExist(bucketName)){
             	s3.createBucket(bucketName);
             }
            
             String key = "";
        
        	  AccessControlList acl = new AccessControlList();
              acl.grantPermission(GroupGrantee.AllUsers,Permission.Read);
             
            
           for(int i = 0; i < infos.getValue().size(); i++){
        	   key = Util.UrlWithSlashes(infos.getKey()) + infos.getValue().get(i);
        	   File file = new File(Util.UrlWithSlashes(OutUrl) + key);
        	   
        	   s3.putObject(new PutObjectRequest(bucketName, key, file));
        	   acl.setOwner(s3.getObjectAcl(bucketName, key).getOwner());
               s3.setObjectAcl(bucketName, key, acl);
             
               S3Object obj=s3.getObject(new GetObjectRequest(bucketName, key));
               KeyValuePair<String, String> urlInfo = new KeyValuePair<>();
               urlInfo.setKey(infos.getValue().get(i));
               urlInfo.setValue(obj.getObjectContent().getHttpRequest().getURI().toString());
               fileUrls.add(urlInfo);
        	  
           }
            
       
        } catch (AmazonServiceException ase) {
        	Util.LogError(log, String.format("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.Error Message: %s--HTTP Status Code: %s--"
                    + "AWS Error Code: %s--Error Type: %s--Request ID: %s", ase.getMessage(),ase.getStatusCode(),ase.getErrorCode(),
                    ase.getErrorType(),ase.getRequestId()), ase);
        	s3.deleteObject(bucketName, Util.UrlWithSlashes(infos.getKey()));
        	return null;
           
        } catch (AmazonClientException ace) {
        	Util.LogError(log,"Caught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with S3, "
        			+ "such as not being able to access the network.",ace);
        	s3.deleteObject(bucketName, Util.UrlWithSlashes(infos.getKey()));
        	return null;
            
        }
		return fileUrls;
		
	}
	
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	public boolean deleteDirectory(String sPath) {  
	   
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    boolean flag = true;  
	     
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	          
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }   
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  

}
