package com.divx.service;

import java.io.File;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;

public class PListFileUtil {
	
	public static String PListFile2String(String filePath){
		String value = "";
		try {
			  File file = new File(filePath);
			  NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(file);
			  String[] keys = rootDict.allKeys();
			  List<Integer> keylist = new LinkedList<>();
			  for(String key : keys){
				  keylist.add(Integer.parseInt(key));
			  }
			  Collections.sort(keylist);
			  StringBuffer values = new StringBuffer();
			  for(Integer key : keylist){
				  values.append(rootDict.objectForKey(key.toString()).toString() + ",");	  
			  }
			  value = values.substring(0, values.lastIndexOf(","));
			} catch(Exception ex) {
			  ex.printStackTrace();
			}
		return value;
		
	}

}
