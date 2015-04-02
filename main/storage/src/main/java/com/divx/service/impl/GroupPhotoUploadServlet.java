package com.divx.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divx.service.ImageFileUpload;
import com.divx.service.SocialServiceHelper;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;



/**
 * Servlet implementation class GroupPhotoUploadServlet
 */
@WebServlet("/GroupPhotoUploadServlet")
public class GroupPhotoUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ServiceResponse res = new ServiceResponse();
		ImageFileUpload.PhotoUrlResponse sr = new ImageFileUpload().Upload(request);
		if(sr.getResponseCode() == 0){
			String token = sr.getPhotoUrl().split("\\|")[0];//sr.getPhotoUrl().substring(0,sr.getPhotoUrl().indexOf("|"));
			String photoUrl = sr.getPhotoUrl().split("\\|")[1];//sr.getPhotoUrl().substring(sr.getPhotoUrl().indexOf("|") + 1);
			SocialServiceHelper.GroupResponse gr = new SocialServiceHelper().UpdateGroupPhotoUrl(token,sr.getTargetId(),photoUrl);
			if(gr.getResponseCode() == ResponseCode.SUCCESS){		
				res.setResponseCode(ResponseCode.SUCCESS);
				res.setResponseMessage("success");
			}else{
				res.setResponseCode(gr.getResponseCode());
				res.setResponseMessage(gr.getResponseMessage());	
			} 
			
		}else{
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		out.println(Util.ObjectToJson(res));
	}

}
