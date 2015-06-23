package com.divx.service.impl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.divx.service.ImageFileUpload;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.model.FileUploadResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;



/**
 * Servlet implementation class UserPhotoUploadServlet
 */
@WebServlet("/UserPhotoUploadServlet")
public class UserPhotoUploadServlet extends HttpServlet {
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
		FileUploadResponse res = new FileUploadResponse();
		ImageFileUpload.PhotoUrlResponse sr = new ImageFileUpload().Upload(request);
		if(sr.getResponseCode() == ResponseCode.SUCCESS){
			ServiceResponse uupRes = new UserServiceHelper().UpdateUserPhoto(sr.getTargetId(), sr.getPhotoUrl());
			
				if(uupRes.getResponseCode() == ResponseCode.SUCCESS){
					res.setFileurl(Util.UrlWithHttp(sr.getPhotoUrl()));
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
				}else{
					
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to UpdateUserPhoto");	
					
				} 
			
			
		}else{
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		out.println(Util.ObjectToJson(res));
	}

}
