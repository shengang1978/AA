package com.divx.service;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;

public class GetBookInfoUtil {

	private static final Logger log = Logger.getLogger(GetBookInfoUtil.class);

	public static List<KeyValueFourPair<String, String, String, String>>  RenameBook(String bookPath)
	  {
		  List<String> contentPaths = RenameChinesePath(bookPath);
		 
		  List<KeyValuePair<String,String>> categorys = RenameCategoryPath(contentPaths);
		  
		  List<KeyValueFourPair<String, String, String, String>> lessonPaths = RenameLessonPath(categorys);
		 
		  List<KeyValueFourPair<String, String, String, String>> lessonInfos = getLessonInfo(lessonPaths);
	
		  return lessonInfos;
	  }
	  
	  private static List<String> RenameChinesePath(String bookPath)
	  {
		  //KeyValuePair<String, String> ret = new KeyValuePair<String, String>();
		  List<String> ret = new LinkedList<>();
		  File file = new File(bookPath);
		  File[] files = file.listFiles();
		  
		  for(File f: files)
		  {
			  if("内容".equals(f.getName())){
				  boolean bRet = f.renameTo(new File(f.getPath().replace("内容", "content")));
				  if (!bRet)
				  {
					  log.error(String.format("Fail to rename %s to %s", f.getName(), "content"));
				  }
				 // ret.setValue(bookPath + "\\content");
				  ret.add(bookPath + "/content");
			  }
			  else if("配置".equals(f.getName())){
				  boolean bRet = f.renameTo(new File(f.getPath().replace("配置", "config")));
				  if (!bRet)
				  {
					  log.error(String.format("Fail to rename %s to %s", f.getName(), "content"));
				  }
				  //ret.setValue(bookPath + "\\config");
				  ret.add(bookPath + "/config");
			  }
			  else if ("content".equals(f.getName()))
			  {
				  ret.add(bookPath + "/content");
			  }
			  else if ("config".equals(f.getName())){
				  ret.add(bookPath + "/config");
			  }
		  }
		  return ret;
	  }
	  //return List<categry,categryPath>
	  private static List<KeyValuePair<String,String>> RenameCategoryPath(List<String> contentPaths)
	  {
		  List<KeyValuePair<String,String>> ret = new LinkedList<>();
		  for(String contentPath : contentPaths){
			 
			  File file = new File(contentPath);
			  File[] files=file.listFiles();
			  for(File f: files){
				 
				  if(f.isDirectory()){
					  KeyValuePair<String,String> keyValuePair = new KeyValuePair<>();

					  keyValuePair.setKey(f.getName().substring(1));

					  keyValuePair.setValue(contentPath + "/" + f.getName().substring(0, Util.getStringIndex(f.getName())));
					  ret.add(keyValuePair);
					  boolean ss = f.renameTo(new File(f.getPath().replace(f.getName(), f.getName().substring(0, Util.getStringIndex(f.getName()))))); 
				  }
			  }
		  }
		 
		  return ret;
	  }
	  //������LessonĿ¼
	  private static List<KeyValueFourPair<String, String, String, String>> RenameLessonPath(List<KeyValuePair<String,String>> categorys)
	  {
		  List<KeyValueFourPair<String, String, String, String>> ret = new LinkedList<>();
		  for(KeyValuePair<String,String> category : categorys){
			  File file = new File(category.getValue());
			  File[] files = file.listFiles();
			  for(File f: files){
				  if(f.isDirectory()){
					  KeyValueFourPair<String, String, String, String> lessonPath = new KeyValueFourPair<String, String, String, String>();
					  lessonPath.setKey(category.getKey());
					  lessonPath.setValue1(f.getName().substring(1, Util.getStringIndex(f.getName())));
					  lessonPath.setValue2(f.getName().substring(Util.getStringIndex(f.getName()) + 1));
					  lessonPath.setValue3(category.getValue() + "/" +f.getName().substring(0, Util.getStringIndex(f.getName())));
					  ret.add(lessonPath);
					  boolean ss = f.renameTo(new File(f.getPath().replace(f.getName(), f.getName().substring(0, Util.getStringIndex(f.getName())))));
				  }
				 
			  }
			  
		  }
		 
		  return ret;
	  }
	  //�����Lesson title���������ļ�·��
	  private static List<KeyValueFourPair<String, String, String, String>> getLessonInfo(List<KeyValueFourPair<String, String, String, String>> lessonPaths){
		  List<KeyValueFourPair<String, String, String, String>> ret = new LinkedList<>();
		
		  for(KeyValueFourPair<String, String, String, String> lessonPath : lessonPaths){
			  File file = new File(lessonPath.getValue3());
			  File[] files = file.listFiles();
			  for(File f: files){
				  KeyValueFourPair<String, String, String, String> lessonInfo = new KeyValueFourPair<String, String, String, String>();
				  lessonInfo.setKey(lessonPath.getKey());
				  lessonInfo.setValue1(lessonPath.getValue1());
				  lessonInfo.setValue2(lessonPath.getValue2());
				  lessonInfo.setValue3(f.getPath());
				  ret.add(lessonInfo);
				
			  }
		  }
		  return ret;
	  }
	  
	  public static String getBookPic(String bookPath){
		  File file = new File(bookPath + "/config");
			 File[] files = file.listFiles();
			 for(File f : files){
				 if(f.getName().endsWith(".jpg")){
					 boolean bRet = f.renameTo(new File(f.getPath().replace("封面图", "B0")));
					 return f.getPath().replace("封面图", "B0"); 
				 }
				
			 }
			 return "";
	  }
}
