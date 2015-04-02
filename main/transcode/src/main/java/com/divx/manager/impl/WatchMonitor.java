package com.divx.manager.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class WatchMonitor implements Runnable {
	private static final Logger log = Logger.getLogger(WatchMonitor.class);
	private Thread threadTranscode;
	private BaseWatch	transcodeWatch; 
	private Thread threadProcessUploaded;
	private BaseWatch	processWatchUploaded; 
	private Thread threadProcessAnalyzing;
	private BaseWatch	processWatchAnalyzing; 
	private Thread threadProcessFinishAnalysis;
	private BaseWatch	processWatchFinishAnalysis;
	private Thread threadProcessCreatedTranscode;
	private BaseWatch	processWatchCreatedTranscode; 
	private Thread threadProcessTranscoding;
	private BaseWatch	processWatchTranscoding; 
	private Thread threadProcessFinishTranscode;
	private BaseWatch	processWatchFinishTranscode;
	private Thread threadProcessFinishSMILCreation;
	private BaseWatch	processWatchFinishSMILCreation; 
	private Thread threadProcessError;
	private BaseWatch	processWatchError; 

	
	public WatchMonitor()
	{
		transcodeWatch = new TranscodeWatch();
		threadTranscode = new Thread(transcodeWatch);
		threadTranscode.start();
		
		processWatchUploaded = new ProcessWatchUploaded();
		threadProcessUploaded = new Thread(processWatchUploaded);
		threadProcessUploaded.start();
		
		processWatchAnalyzing = new ProcessWatchAnalyzing();
		threadProcessAnalyzing = new Thread(processWatchAnalyzing);
		threadProcessAnalyzing.start();
		
		processWatchFinishAnalysis = new ProcessWatchFinishAnalysis();
		threadProcessFinishAnalysis = new Thread(processWatchFinishAnalysis);
		threadProcessFinishAnalysis.start();
		
		processWatchCreatedTranscode = new ProcessWatchCreatedTranscode();
		threadProcessCreatedTranscode = new Thread(processWatchCreatedTranscode);
		threadProcessCreatedTranscode.start();
		
		processWatchTranscoding = new ProcessWatchTranscoding();
		threadProcessTranscoding = new Thread(processWatchTranscoding);
		threadProcessTranscoding.start();
		
		processWatchFinishTranscode = new ProcessWatchFinishTranscode();
		threadProcessFinishTranscode = new Thread(processWatchFinishTranscode);
		threadProcessFinishTranscode.start();
		
		processWatchFinishSMILCreation = new ProcessWatchFinishSMILCreation();
		threadProcessFinishSMILCreation = new Thread(processWatchFinishSMILCreation);
		threadProcessFinishSMILCreation.start();
		
		processWatchError = new ProcessWatchError();
		threadProcessError = new Thread(processWatchError);
		threadProcessError.start();
		
		
	}
	
	@Override
	public void run() {
		while(true)
		{
			try
			{
				try
				{
					if(!threadTranscode.isAlive()) {
						transcodeWatch = new TranscodeWatch();
						threadTranscode = new Thread(transcodeWatch);
						threadTranscode.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check TranscodeWatch active status exception.", ex);
				}
				
				
				try
				{
					if(!threadProcessUploaded.isAlive()) {
						processWatchUploaded = new ProcessWatchUploaded();
						threadProcessUploaded = new Thread(processWatchUploaded);
						threadProcessUploaded.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchUploaded active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessAnalyzing.isAlive()) {
						processWatchAnalyzing = new ProcessWatchAnalyzing();
						threadProcessAnalyzing = new Thread(processWatchAnalyzing);
						threadProcessAnalyzing.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchAnalyzing active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessFinishAnalysis.isAlive()) {
						processWatchFinishAnalysis = new ProcessWatchFinishAnalysis();
						threadProcessFinishAnalysis = new Thread(processWatchFinishAnalysis);
						threadProcessFinishAnalysis.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchFinishAnalysis active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessCreatedTranscode.isAlive()) {
						processWatchCreatedTranscode = new ProcessWatchCreatedTranscode();
						threadProcessCreatedTranscode = new Thread(processWatchCreatedTranscode);
						threadProcessCreatedTranscode.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchCreatedTranscode active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessTranscoding.isAlive()) {
						processWatchTranscoding = new ProcessWatchTranscoding();
						threadProcessTranscoding = new Thread(processWatchTranscoding);
						threadProcessTranscoding.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchTranscoding active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessFinishTranscode.isAlive()) {
						processWatchFinishTranscode = new ProcessWatchFinishTranscode();
						threadProcessFinishTranscode = new Thread(processWatchFinishTranscode);
						threadProcessFinishTranscode.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchFinishTranscode active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessFinishSMILCreation.isAlive()) {
						processWatchFinishSMILCreation = new ProcessWatchFinishSMILCreation();
						threadProcessFinishSMILCreation = new Thread(processWatchFinishSMILCreation);
						threadProcessFinishSMILCreation.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchFinishSMILCreation active status exception.", ex);
				}
				
				try
				{
					if(!threadProcessError.isAlive()) {
						processWatchError = new ProcessWatchError();
						threadProcessError = new Thread(processWatchError);
						threadProcessError.start();
					}
				}
				catch(Exception ex)
				{
					log.error("WatchMonitor.run->check ProcessWatchError active status exception.", ex);
				}
				
				try
				{
					
					if (transcodeWatch.IsThreadExpired())
					{
						transcodeWatch.Stop();
						log.warn("Force kill thread transcodeWatch for this thread isn't active for a long time.");
					}
					
					if (processWatchUploaded.IsThreadExpired())
					{
						processWatchUploaded.Stop();
						log.warn("Force kill thread processWatchUploaded for this thread isn't active for a long time.");
					}
					
					if (processWatchAnalyzing.IsThreadExpired())
					{
						processWatchAnalyzing.Stop();
						log.warn("Force kill thread processWatchAnalyzing for this thread isn't active for a long time.");
					}
					if (processWatchFinishAnalysis.IsThreadExpired())
					{
						processWatchFinishAnalysis.Stop();
						log.warn("Force kill thread processWatchFinishAnalysis for this thread isn't active for a long time.");
					}
					if (processWatchCreatedTranscode.IsThreadExpired())
					{
						processWatchCreatedTranscode.Stop();
						log.warn("Force kill thread processWatchCreatedTranscode for this thread isn't active for a long time.");
					}
					if (processWatchTranscoding.IsThreadExpired())
					{
						processWatchTranscoding.Stop();
						log.warn("Force kill thread processWatchTranscoding for this thread isn't active for a long time.");
					}
					if (processWatchFinishTranscode.IsThreadExpired())
					{
						processWatchFinishTranscode.Stop();
						log.warn("Force kill thread processWatchFinishTranscode for this thread isn't active for a long time.");
					}
					if (processWatchFinishSMILCreation.IsThreadExpired())
					{
						processWatchFinishSMILCreation.Stop();
						log.warn("Force kill thread processWatchFinishSMILCreation for this thread isn't active for a long time.");
					}
					if (processWatchError.IsThreadExpired())
					{
						processWatchError.Stop();
						log.warn("Force kill thread processWatchError for this thread isn't active for a long time.");
					}
					
				}
				catch(Exception ex)
				{		
					log.error("WatchMonitor.run->force stop thread exception.", ex);
				}
				
				Thread.sleep(120000);	//Check the thread status every 2 min.
			}
			catch(Exception ex)
			{
				log.error("WatchMonitor.run Exception.", ex);
			}			
		}
	}
}
