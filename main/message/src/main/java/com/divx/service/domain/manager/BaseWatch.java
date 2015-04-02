package com.divx.service.domain.manager;

public class BaseWatch implements Runnable, IWatch {
	protected boolean exit = false;
	private static final int idleExpireDuration = 3600 * 1000; 
	protected long latestActiveTime = 0;
	protected static boolean bInited = false;
	
	protected boolean IsExit()
	{
		synchronized(this)
		{
			return exit;
		}
	}
	
	public boolean IsThreadExpired()
	{		
		synchronized(this)
		{
			return !exit && latestActiveTime != 0 && System.currentTimeMillis() - latestActiveTime > idleExpireDuration;
		}
	}
	
	protected void updateThreadActiveTime()
	{
		synchronized(this)
		{
			latestActiveTime = System.currentTimeMillis();
		}
	}

	@Override
	public void Stop() {
		synchronized(this)
		{
			exit = true;
		}
		try
		{
			if (Thread.currentThread().getState() != Thread.State.TERMINATED)
			{
				Thread.currentThread().join(60000);
				
				if (Thread.currentThread().getState() != Thread.State.TERMINATED)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
		catch(Exception ex)
		{
			
		}
	}

	@Override
	public void run() {
		
		while(!IsExit())
		{
			try
			{
				updateThreadActiveTime();
				DoTask(100);
				Thread.sleep(1000);
			}
			catch(Exception ex)
			{
				
			}
		}
	}

	@Override
	public void DoTask(int size) {
		if (!bInited)
		{
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bInited = true;
		}

	}
}
