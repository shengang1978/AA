package com.divx.service.auth.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import com.divx.service.domain.model.DcpToken;
import com.divx.service.domain.model.OsfUsers;


public class DivXAuthToken {
	public static final String MAGIC_KEY = "divx_token_key";

	private String username;
	private int orgId;
	private int userId;
	private Date expireDate;
	private String dcpToken;
	private String sign;
	private String password;

	public DivXAuthToken(OsfUsers user, DcpToken token)
	{
		username = user.getUsername();
		dcpToken = token.getToken();
		orgId = user.getOrganizationId();
		expireDate = token.getExpiredate();
		setPassword(user.getPassword());
		setUserId(user.getId().intValue());
	}

	public DivXAuthToken()
	{
	}
	
	public String getDcpToken() {
		return dcpToken;
	}
	public void setDcpToken(String dcpToken) {
		this.dcpToken = dcpToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}	

	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public static DivXAuthToken FromAuthToken(String authToken)
	{
		if (authToken == null || authToken.isEmpty())
			return null;
		
		String[] parts = authToken.split(":");
		if (parts.length >= 4)
		{
			try
			{
				DivXAuthToken token = new DivXAuthToken();
				//token.username = parts[0];
				//token.orgId = Integer.parseInt(parts[1]);
				token.userId = Integer.parseInt(parts[0]);
				token.expireDate = new Date(Long.parseLong(parts[1])*1000);
				token.dcpToken = parts[2];
				token.setSign(parts[3]);
				return token;
			}
			catch(Exception e)
			{	
			}
		}
		return null;
	}
	public static String ComputeSignature(DivXAuthToken token, UserDetails user)
	{
		StringBuilder sb = new StringBuilder();
		DivXAuthUser au = (DivXAuthUser)user;
		sb.append(au.getUser().getId());
		sb.append("|");
		sb.append(au.getUser().getOrganizationId());
		sb.append(":");
		sb.append(token.expireDate.getTime()/1000);
		sb.append(":");
		sb.append(token.getDcpToken());
		sb.append(":");
		sb.append(DivXAuthToken.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(sb.toString().getBytes())));
	}
	
	public static boolean IsAuthTokenExpired(DivXAuthToken token)
	{
		if (token != null && token.expireDate.getTime() < System.currentTimeMillis())
			return true;
		
		return false;
	}
	
	public static boolean IsValidAuthToken(DivXAuthToken token, UserDetails user)
	{
		if (IsAuthTokenExpired(token))
			return false;

		return token.sign.equals(ComputeSignature(token, user));
	}
	
	public static boolean IsValidAuthToken(String authToken, UserDetails user)
	{
		DivXAuthToken token = DivXAuthToken.FromAuthToken(authToken);
		
		return IsValidAuthToken(token, user);
	}
	
	public String GetAuthTokenString(UserDetails user)
	{
		StringBuilder sb = new StringBuilder();
		
//		sb.append(getUsername());
//		sb.append(":");
//		sb.append(orgId);
//		sb.append(":");
		sb.append(userId);
		sb.append(":");
		sb.append(getExpireDate().getTime()/1000);
		sb.append(":");
		sb.append(getDcpToken());
		sb.append(":");
		sb.append(ComputeSignature(this, user));
		
		return sb.toString();
	}
	
	public String GetAuthUsername()
	{
		return String.format("%d|%s", userId, dcpToken);
	}
	
	public static DivXAuthToken ParseAuthUsername(String authUsername)
	{
		String[] parts = authUsername.split("\\|");
		if (parts.length != 2)
			return null;
		
		DivXAuthToken token = new DivXAuthToken();
//		token.username = parts[0];
//		token.orgId = Integer.parseInt(parts[1]);
		token.userId = Integer.parseInt(parts[0]);
		token.dcpToken = parts[1];
		
		return token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
