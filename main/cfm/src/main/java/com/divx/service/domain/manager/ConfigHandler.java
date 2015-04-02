package com.divx.service.domain.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;










import com.divx.service.domain.model.DcpEmailTemplate;
import com.divx.service.domain.model.OsfMailSettings;
import com.divx.service.domain.repository.ConfigDao;
import com.divx.service.domain.repository.impl.ConfigDaoImpl;
import com.divx.service.model.Config;
import com.divx.service.model.EmailTemplate;
import com.divx.service.model.MailSetting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

class ConfigHandler {
	public boolean IsMyConfig(String key){
		return true;
	}
	public Config GetValue(String key, String value){
		return new Config(key, value);
	}
	public static String ObjectToJson(Object obj)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create(); 
		return gson.toJson(obj);
	}
}

class EmailTemplateConfigHandler extends ConfigHandler{

	public boolean IsMyConfig(String key)
	{
		if ("InviteUserEmailTemplate".equals(key))
			return true;
		if("ResetPasswordEmailTemplate".equals(key))
			return true;
		return false;
	}
	
	public Config GetValue(String key, String value) {
		// read data from email template table
		ConfigDao confDao = new ConfigDaoImpl();
		DcpEmailTemplate dcpEmailTemplate = confDao.getEmailTemplate(Integer.parseInt(value));
		EmailTemplate emailTemplate = new EmailTemplate();
		emailTemplate.setEmailType(dcpEmailTemplate.getEmailType());
		emailTemplate.setEmailContent(dcpEmailTemplate.getEmailContent());
		value  = ObjectToJson(emailTemplate);
		return super.GetValue(key, value);
	}
	
}

class MailSettingConfigHandler extends ConfigHandler{
	public boolean IsMyConfig(String key)
	{
		if ("MailSetting".equals(key))
			return true;
		
		return false;
	}
	
	public Config GetValue(String key, String value) {
		// read data from email template table
		ConfigDao confDao = new ConfigDaoImpl();
		OsfMailSettings mailsetting = confDao.getEmailSetting(Integer.parseInt(value));
		MailSetting mail = new MailSetting();
		mail.setHost(mailsetting.getHost());
		mail.setEmail(mailsetting.getEmail());
		mail.setPort(mailsetting.getPort());
		mail.setUsername(mailsetting.getUsername());
		mail.setPassword(mailsetting.getPassword());
		value  = ObjectToJson(mail);
		return super.GetValue(key, value);
	}
}

class ConfigHandlerFactory{
	private static List<ConfigHandler> listObjs;
	
	static 
	{
		listObjs = new LinkedList<ConfigHandler>();
		//Add new config handle here
		listObjs.add(new MailSettingConfigHandler());
		listObjs.add(new EmailTemplateConfigHandler());
		listObjs.add(new ConfigHandler());
	}
	
	public static ConfigHandler CreateProduct(String key){
		//List<BaseConfig> listObjs;
		for(ConfigHandler obj:listObjs){
			if (obj.IsMyConfig(key))
			{
				return obj;
			}
		}
		return new ConfigHandler();		
	}
	
}

 
 