package com.divx.manager.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.divx.service.Util;
import com.divx.service.model.KeyValuePair;

public class WatchMonitor implements Runnable {
	private static final Logger log = Logger.getLogger(WatchMonitor.class);
	
	private List<KeyValuePair<BaseWatch, Thread>>	threadList;

	public WatchMonitor()
	{
		threadList = new LinkedList<KeyValuePair<BaseWatch, Thread>>();

		AddWatchThread(new TranscodeWatch());
		AddWatchThread(new ProcessWatchUploaded());
		AddWatchThread(new ProcessWatchAnalyzing());
		AddWatchThread(new ProcessWatchFinishAnalysis());
		AddWatchThread(new ProcessWatchCreatedTranscode());
		AddWatchThread(new ProcessWatchTranscoding());
		AddWatchThread(new ProcessWatchFinishTranscode());
		AddWatchThread(new ProcessWatchFinishSMILCreation());
		AddWatchThread(new ProcessWatchError());
		AddWatchThread(new ProcessWatchV2GJpg2GifDoing());
		AddWatchThread(new ProcessWatchV2GVideo2JpgDoing());
	}
	
	@Override
	public void run() {
		while(true)
		{
			try
			{
				for(KeyValuePair<BaseWatch, Thread> t: threadList)
				{
					try
					{
						if (!t.getValue().isAlive())
						{
							t.setKey(t.getKey().getClass().newInstance());
							t.setValue(new Thread(t.getKey()));
							t.getValue().start();
						}
					}
					catch(Exception ex)
					{
						Util.LogError(log, String.format("WatchMonitor.run->check %s active status exception.", t.getKey().getClass().toString()), ex);
					}
				}
				
				Thread.sleep(120000);	//Check the thread status every 2 min.
			}
			catch(Exception ex)
			{
				log.error("WatchMonitor.run Exception.", ex);
			}			
		}
	}
	
	void AddWatchThread(BaseWatch watch)
	{
		KeyValuePair<BaseWatch, Thread> kvp = new KeyValuePair<BaseWatch, Thread>();
		kvp.setKey(watch);
		kvp.setValue(new Thread(watch));
		kvp.getValue().start();
		
		threadList.add(kvp);
	}

}
