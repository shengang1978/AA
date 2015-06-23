package com.divx.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.eclipse.jetty.util.security.Credential.MD5;
import com.divx.service.ConfigurationManager;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.impl.DivxUserDaoImpl;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;


public class resetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		
		PrintWriter out = null;
		ServiceResponse res = new ServiceResponse();
		String servletBaseUrl = ConfigurationManager.GetInstance().ResetPasswort_ServletBaseUrl();
		try{
			out = response.getWriter();
			String urlParameter =request.getParameter("urlParameter");
			String newPassword = request.getParameter("newPassword");
			String repeatNewPassword = request.getParameter("repeatNewPassword");
		
			if(newPassword.trim().isEmpty() || repeatNewPassword.trim().isEmpty() || !newPassword.equals(repeatNewPassword))
			{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("Invalid Parameter");
				
			}
			else
			{
				if(!urlParameter.isEmpty())
				{
					String parameter = java.net.URLDecoder.decode(urlParameter, "UTF-8");
					String userParameter = parameter.substring(0,parameter.indexOf("|"));
					String checkParameter = parameter.substring(parameter.indexOf("|")+1);
					String userInfo = new String(DatatypeConverter.parseBase64Binary(userParameter));
					int symbolPos = userInfo.indexOf("|");
					String username = userInfo.substring(0, symbolPos);
					int symbol2Pos = userInfo.indexOf("|", symbolPos + 1);
					String strOrgId = userInfo.substring(userInfo.indexOf("|")+1, symbol2Pos);
					String userdate = userInfo.substring(symbol2Pos+1);
					int orgId = 0;
					try
					{
						orgId = Integer.parseInt(strOrgId);
					}
					catch(Exception ex)
					{	
					}
					SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
					Date cheeckdate = sfEnd.parse(sfEnd.format(sfStart.parse(userdate)));
					Date nowDate = new Date();	
					Long check =((nowDate.getTime()-cheeckdate.getTime())/60/60/1000);
					
					if(check.intValue() > 24){
						
						response.sendRedirect(servletBaseUrl+request.getContextPath()+"/linklose.jsp");
						
					}else{
						DivxUserDao userDao = new DivxUserDaoImpl();	
						OsfUsers u = userDao.GetUserByUsername(orgId, username);
						if(u != null){
							String md5userInfo = MD5.digest(u.getId().toString()+userdate);
							if(md5userInfo.equals(checkParameter)){
								u.setPassword(newPassword);							
								int mid = userDao.UpdateUser(u);
								if(mid > 0){
									
									res.setResponseCode(ResponseCode.SUCCESS);
									res.setResponseMessage("success");
								}else{
									
									res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
									res.setResponseMessage("Fail to UpdateUser");	
									
								} 
							}else{
							res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
							res.setResponseMessage("Invalid Parameter");	
							}
						}else{
							res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
							res.setResponseMessage("Your username is not exist");						
						}
					}
				}else{
					res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
					res.setResponseMessage("Invalid Parameter");
				}
			}
		}
		catch(Exception ex)
		{	
			res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
		    res.setResponseMessage(ex.getMessage());
		    ex.printStackTrace();
		}	
		if(res.getResponseCode() == 0){
			response.sendRedirect(servletBaseUrl+request.getContextPath()+"/success.jsp");
		}else{
			request.setAttribute("code", res.getResponseCode());
			request.setAttribute("message", res.getResponseMessage());
			request.getRequestDispatcher("/error.jsp").forward(request,response);
		}
	}
}
