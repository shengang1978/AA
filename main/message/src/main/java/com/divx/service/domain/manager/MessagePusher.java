package com.divx.service.domain.manager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.domain.repository.MessageDao;
import com.divx.service.domain.repository.impl.MessageDaoImpl;
import com.divx.service.domain.model.DcpMessage;
import com.divx.service.domain.model.DcpMessageRecver;
import com.divx.service.domain.repository.DeviceDao;
import com.divx.service.domain.repository.MessageDao;
import com.divx.service.domain.repository.impl.DeviceDaoImpl;
import com.divx.service.domain.repository.impl.MessageDaoImpl;
import com.divx.service.model.KeyValueFourPair;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.KeyValueTriplePair;


// Push the message to push servers (ios, android)
class IosPushWatch extends BaseWatch {
	private static final Logger log = Logger.getLogger(IosPushWatch.class);
	public IosPushWatch() {
		messageDao = new MessageDaoImpl();
		log.info("IosPushWatch is running.");
	}

	private MessageDao messageDao;

	@Override
	public void DoTask(int tasksize) {
		super.DoTask(tasksize);
		/*List<String> ss = IosMessagePushUtil.feedback();
		System.out.println(ss.toString());*/
		  List<KeyValueFourPair<Integer,Integer,String,String>> lists = messageDao.GetUnsentMessages(1, tasksize);
		  HashMap<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>> maps = new  HashMap<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>>();
		  if(lists != null && lists.size() > 0){
			  for(KeyValueFourPair<Integer, Integer, String, String> obj: lists)
			  {					 
				  if (!maps.containsKey(obj.getKey()))
				  {
					  KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>> kvp = new KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>();
						kvp.setKey(obj.getValue2());
						kvp.setValue1(new LinkedList<Integer>());
						kvp.setValue2(new LinkedList<String>());
						maps.put(obj.getKey(), kvp);
				  }
				  
				  maps.get(obj.getKey()).getValue1().add(obj.getValue1());
				  maps.get(obj.getKey()).getValue2().add(obj.getValue3());
			  }

		  }
		  if(maps != null && maps.size() > 0){
			  	
			  Iterator iterator = maps.entrySet().iterator();
			  while(iterator.hasNext()){
				 Entry<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>> entry = (Entry<Integer,KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>>) iterator.next();
				
				 if(entry.getValue() != null && entry.getValue().getValue2().size() > 0){
					 HashMap<Integer,KeyValuePair<List<Integer>, List<String>>>  res = IosMessagePushUtil.iosMessagePushToMore(entry.getValue().getKey(), entry.getValue().getValue1(),entry.getValue().getValue2());
					 if(res != null){	
						 Iterator it = res.keySet().iterator();
						 while(it.hasNext())
						 {
							 int k = (Integer)it.next();
							 if(k == 0)
							 {
								 messageDao.UpdateMessageStatus(1,res.get(0).getKey(),true);  
							 }else
							 {
								 messageDao.UpdateMessageStatus(1, res.get(1).getKey(),false);
							 }
						 }
					 }					 
					}				
			  } 
		  }
	}
		
}
class AndroidPushWatch extends BaseWatch {
	private static final Logger log = Logger.getLogger(AndroidPushWatch.class);
	public AndroidPushWatch() {
		messageDao = new MessageDaoImpl();
		log.info("AndroidPushWatch is running.");
	}

	private MessageDao messageDao;

	@Override
	public void DoTask(int tasksize) {
		super.DoTask(tasksize);
		
		boolean bEnableAndroidMessaging = ConfigurationManager.GetInstance().GetConfigValue("Message_Enable_AndroidPN", false);
		if (!bEnableAndroidMessaging)
		{
			return;
		}
		
		List<KeyValueFourPair<Integer, Integer, String, String>> lists = messageDao.GetUnsentMessages(0, tasksize);
		HashMap<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>> maps = new  HashMap<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>>();
		if(lists != null && lists.size() > 0){
			for(KeyValueFourPair<Integer, Integer, String, String> obj: lists)
			{
				if (!maps.containsKey(obj.getKey()))
				  {
					  KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>> kvp = new KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>();
						kvp.setKey(obj.getValue2());
						kvp.setValue1(new LinkedList<Integer>());
						kvp.setValue2(new LinkedList<String>());
						maps.put(obj.getKey(), kvp);
				  }
				  
				  maps.get(obj.getKey()).getValue1().add(obj.getValue1());
				  maps.get(obj.getKey()).getValue2().add(obj.getValue3());
			}
		}
		if(maps != null && maps.size() > 0){
			Iterator iterator = maps.entrySet().iterator();
			while(iterator.hasNext()){
				 Entry<Integer, KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>> entry = (Entry<Integer,KeyValueTriplePair<String,LinkedList<Integer>, LinkedList<String>>>) iterator.next();
					
				 if(entry.getValue() != null && entry.getValue().getValue2().size() > 0){

					List<String> tokens =  entry.getValue().getValue2();
					
					List<KeyValuePair<String,Integer>> res = AndroidMessagePushUtil.androidMessagePushToMore(entry.getValue().getKey(),tokens);

					if(res != null && res.size() > 0){
						List<Integer> successMrIds = new LinkedList<Integer>();
						List<Integer> failMrIds = new LinkedList<Integer>();
						
						Iterator<KeyValuePair<String,Integer>> it = res.iterator();
						Iterator<Integer> mrIt = entry.getValue().getValue1().iterator();
						while(it.hasNext())
						{
							KeyValuePair<String,Integer> obj = it.next();
							Integer id = mrIt.next();
							if (obj.getValue() == 0)
							{
								successMrIds.add(id);
							}
							else
							{
								failMrIds.add(id);
							}
						}

						if (successMrIds.size() > 0)
							messageDao.UpdateMessageStatus(0, successMrIds, true);
						if (failMrIds.size() > 0)
							messageDao.UpdateMessageStatus(0, failMrIds, false);
					}
				}				
			} 
		}
	}			
}
 
class WatchMonitor implements Runnable{
	private static final Logger log = Logger.getLogger(WatchMonitor.class);
	private boolean bInit;
	private Thread iosPushWatch;
	private Thread androidPushWatch;
	public WatchMonitor(){
		bInit = false;
		
		iosPushWatch = new Thread(new IosPushWatch());
		iosPushWatch.start();
		
		androidPushWatch = new Thread(new AndroidPushWatch());
		androidPushWatch.start();
	}
	@Override
	public void run() {
		while(true)
		{
			try
			{
				if (!bInit)
				{
					Thread.sleep(30000);
					bInit = true;
				}
				if(!iosPushWatch.isAlive()) {
					log.info(String.format("iOS Push Thread isn't active. Create a new one."));
					iosPushWatch = new Thread(new IosPushWatch());
					iosPushWatch.start();
				}
				
				if(!androidPushWatch.isAlive()) {
					log.info(String.format("Android Push Thread isn't active. Create a new one."));
					androidPushWatch = new Thread(new AndroidPushWatch());
					androidPushWatch.start();
				}

				Thread.sleep(120000);	//Check the thread status every 2 min.
			}
			catch(Exception ex)
			{
				Util.LogError(log, "WatchMonitor.run Exception", ex);
			}			
		}
	}	
		 
}
