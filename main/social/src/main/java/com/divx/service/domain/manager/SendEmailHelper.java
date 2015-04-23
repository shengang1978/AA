package com.divx.service.domain.manager;
import com.divx.service.model.User;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import com.divx.service.ConfigurationManager;
import com.divx.service.Email;
import com.divx.service.Util;
import com.divx.service.domain.dao.FriendDao;
import com.divx.service.domain.dao.impl.FriendDaoImpl;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.model.EmailTemplate;
import com.divx.service.model.MailSetting;



public class SendEmailHelper {
	private static final Logger log =  Logger.getLogger(SendEmailHelper.class);
	public interface IWatch {
		void Stop();
		void DoTask();	
	}
	public class BaseWatch implements Runnable, IWatch {
		protected boolean exit = false;

		@Override
		public void Stop() {
			exit = true;
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
			while(!exit)
			{
				try
				{
					DoTask();
					Thread.sleep(1000);
				}
				catch(Exception ex)
				{
					
				}
			}
		}

		@Override
		public void DoTask() {
			
			
		}
	}

	public class ProcessWatch extends BaseWatch {
		public ProcessWatch() {
			friendDao = new FriendDaoImpl();

		}

		private FriendDao friendDao;

		@Override
		public void DoTask() {
			List<DcpEmailJob> emailJobs = friendDao.GetUnSendEmailJobs();
			if (emailJobs == null) {
				return;
			}
			String inviteUserEmailTemplate = ConfigurationManager.GetInstance()
					.InviteUserEmailTemplate();
			EmailTemplate emailtemplate = Util.JsonToObject(
					inviteUserEmailTemplate, EmailTemplate.class);
			String mailSettings = ConfigurationManager.GetInstance()
					.MailSetting();
			MailSetting mailsetting = Util.JsonToObject(mailSettings,
					MailSetting.class);

			if (mailsetting != null && emailtemplate != null) {
				boolean status = false;
			
				
				Email.EmailModel emailmdel = new Email.EmailModel();
				emailmdel.setSmtp(mailsetting.getHost());
				emailmdel.setFrom(mailsetting.getEmail());
				
				emailmdel.setUsername(mailsetting.getUsername());
				emailmdel.setPassword(mailsetting.getPassword());
				
				for (DcpEmailJob obj : emailJobs) {
					try {
						User u = new UserHelper().GetUser(obj.getUserId());
						
						emailmdel.setSubject(String.format(emailtemplate.getEmailType(),u.getNickname()));
						emailmdel.setTo(obj.getEmailAddress());
						emailmdel.setContent(String.format(emailtemplate.getEmailContent(),obj.getEmailAddress(),u.getNickname(),obj.getContent()));
						
						status = Email.send(emailmdel);
						if (status) {
							obj.setStatus(true);
							friendDao.SaveEmailJob(obj);
						} else {
							obj.setAttempts(obj.getAttempts() + 1);
							friendDao.SaveEmailJob(obj);
						}
					} catch (Exception e) {
						Util.LogError(log, "send  email exception", e);
					}
				}

			}

		}

	}
	 
	 public class WatchMonitor implements Runnable{

			private Thread processWacth;
			public WatchMonitor(){
				processWacth = new Thread(new ProcessWatch());
				processWacth.start();
			}
			@Override
			public void run() {
				while(true)
				{
					try
					{
						if(!processWacth.isAlive()) {
							processWacth = new Thread(new ProcessWatch());
							processWacth.start();
						}
						Thread.sleep(120000);	//Check the thread status every 2 min.
					}
					catch(Exception ex)
					{
						
					}			
				}
			}	
			 
	}
	
	
}
