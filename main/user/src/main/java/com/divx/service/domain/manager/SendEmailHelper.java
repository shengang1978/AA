package com.divx.service.domain.manager;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.jetty.util.security.Credential.MD5;

import com.divx.service.ConfigurationManager;
import com.divx.service.Email;
import com.divx.service.Util;
import com.divx.service.domain.model.DcpEmailJob;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.impl.DivxUserDaoImpl;
import com.divx.service.model.EmailTemplate;
import com.divx.service.model.MailSetting;



public class SendEmailHelper {
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
			divxUserDao = new DivxUserDaoImpl();

		}

		private DivxUserDao divxUserDao;

		@Override
		public void DoTask() {
			List<DcpEmailJob> emailJobs = divxUserDao.GetUnSendEmailJobs();
			if (emailJobs == null) {
				return;
			}
			String resetPasswordEmailTemplate = ConfigurationManager
					.GetInstance().ResetPasswordEmailTemplate();
			EmailTemplate emailtemplate = Util.JsonToObject(
					resetPasswordEmailTemplate, EmailTemplate.class);

			String mailSettings = ConfigurationManager.GetInstance()
					.MailSetting();
			MailSetting mailsetting = Util.JsonToObject(mailSettings,
					MailSetting.class);
			boolean status = false;
			if (mailsetting != null && emailtemplate != null) {

				String servletBaseUrl = ConfigurationManager.GetInstance()
						.ResetPasswort_ServletBaseUrl();
				Email.EmailModel emailmdel = new Email.EmailModel();
				emailmdel.setSmtp(mailsetting.getHost());
				emailmdel.setFrom(mailsetting.getEmail());
				emailmdel.setSubject(emailtemplate.getEmailType());
				emailmdel.setUsername(mailsetting.getUsername());
				emailmdel.setPassword(mailsetting.getPassword());

				for (DcpEmailJob obj : emailJobs) {
					try {
						emailmdel.setTo(obj.getEmailAddress());
						OsfUsers user = divxUserDao.GetUser(obj.getUserId());
						if (user != null) {
							String usernameParameter = DatatypeConverter
									.printBase64Binary((user.getUsername()
											+ "|" + user.getOrganizationId()
											+ "|" + new Date().toString())
											.getBytes());
							String userParameter = MD5.digest(user.getId()
									.toString() + new Date().toString());
							String parameter = java.net.URLEncoder.encode(
									usernameParameter + "|" + userParameter,
									"UTF-8");
							String name = user.getNickname().isEmpty() ?
									obj.getEmailAddress().substring(0, obj.getEmailAddress().indexOf('@')) : user.getNickname();
							emailmdel.setContent(String.format(
									emailtemplate.getEmailContent(),name,
									servletBaseUrl, "?p=" + parameter,
									servletBaseUrl, "?p=" + parameter));
							status = Email.send(emailmdel);
						}

						if (status) {
							obj.setStatus(true);
							divxUserDao.SaveEmailJob(obj);
						} else {
							obj.setAttempts(obj.getAttempts() + 1);
							divxUserDao.SaveEmailJob(obj);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
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
