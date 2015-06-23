package com.divx.service.controller;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.util.concurrent.TimeUnit;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import org.apache.log4j.Logger;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class TxHelper {
	private static final Cache<String, Object>	cacheToken = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(2, TimeUnit.HOURS).build();
	private static final Logger log = Logger.getLogger(TxHelper.class);
	
    public static WxTicket sign(String url) {
    	WxTicket ret = new WxTicket();
    	
		String appId = ConfigurationManager.GetInstance().GetConfigValue("Weixin_gzh_AppId", "");
		String secret = ConfigurationManager.GetInstance().GetConfigValue("Weixin_gzh_AppSecret", "");
		String jsapi_ticket = GetTikcet(GetAccessToken(appId, secret));
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.setAppId(appId);
        ret.setUrl(url);
        ret.setJsapi_ticket(jsapi_ticket);
        ret.setNonceStr(nonce_str);
        ret.setTimestamp(timestamp);
        ret.setSignature(signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
	class TxToken
	{
		public int getErrcode() {
			return errcode;
		}
		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
		public String getAccess_token() {
			return access_token;
		}
		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}
		public long getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(long expires_in) {
			this.expires_in = expires_in;
		}
		
		public boolean IsExpired()
		{
			return System.currentTimeMillis() / 1000 > expires_in;
		}
		private int errcode;
		private String errmsg;
		private String access_token;
		private long expires_in;
	}
	
	class TxTicket
	{
		private int errcode;
		private String errmsg;
		private String ticket;
		private long expires_in;
		
		public boolean IsExpired()
		{
			return System.currentTimeMillis()/1000 > expires_in;
		}
		
		public int getErrcode() {
			return errcode;
		}
		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
		public String getTicket() {
			return ticket;
		}
		public void setTicket(String ticket) {
			this.ticket = ticket;
		}
		public long getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(long expires_in) {
			this.expires_in = expires_in;
		}
	}
	private static String GetTikcet(String token)
	{
		String key = "wx_ticket";
		TxTicket t = (TxTicket)cacheToken.getIfPresent(key);
		if (t != null && !t.IsExpired())
		{
			return t.getTicket();
		}
		
		String reqUrl = String.format(ConfigurationManager.GetInstance().GetConfigValue("Weixin_gzh_requestTicket", ""), 
							token);
		
		try
		{
			String ret = Util.HttpGet(reqUrl);
			t = Util.JsonToObject(ret, TxTicket.class);
			if (t != null && t.getErrcode() == 0)
			{
				cacheToken.put(key, t);
				return t.getTicket();
			}
		}
		catch(Exception ex)
		{
			
		}
		
		return "";
	}
	private static String GetAccessToken(String appId, String secret)
	{
		String key = "wx_at";
		TxToken t = (TxToken)cacheToken.getIfPresent(key);
		if (t != null && !t.IsExpired())
		{
			return t.access_token;
		}
		String reqUrl = String.format(ConfigurationManager.GetInstance().GetConfigValue("Weixin_gzh_requestToken", ""), 
							appId, 
							secret);
		
		try {
			String ret = Util.HttpGet(reqUrl);
			t = Util.JsonToObject(ret, TxToken.class);
			if (t != null && t.errcode == 0)
			{
				cacheToken.put(key, t);
				return t.access_token;
			}
			else if (t != null && t.errcode > 0)
			{
				log.error(String.format("WX GetAccessToken() fail. ret(%s), errcode(%d), errmessage(%s)", ret, t.errcode, t.errmsg));
			}
			else
			{
				log.error(String.format("WX GetAccessToken() ret = %s", ret));
			}
		} catch (Exception e) {
			Util.LogError(log, String.format("Exception GetAccessToken(%s)", reqUrl), e);
		}
		return "";
	}
}
