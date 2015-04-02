package com.divx.service.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;
import com.divx.service.model.ResponseCode;

/**
 * Servlet implementation class RespondInviteUserServlet
 */
@WebServlet("/RespondInviteUserServlet")
public class respondInviteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public respondInviteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String usernameParameter = request.getParameter("u");
			String passwordParameter = request.getParameter("p");
			String baseUrl = ConfigurationManager.GetInstance().SocialServiceBaseUrl();
			String reqUrl = baseUrl + "/friend/RespondInviteUser/" + usernameParameter +"/"+passwordParameter;
			String ret = Util.HttpPost(reqUrl,"");
			String servletBaseUrl = ConfigurationManager.GetInstance().InviteUser_ServletBaseUrl();
			if (ret.isEmpty())
			{
				response.sendRedirect(servletBaseUrl+request.getContextPath()+"/error.jsp");
				
			}else{
				response.sendRedirect(servletBaseUrl+request.getContextPath()+"/success.jsp");
			}
		}catch(Exception ex){
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
