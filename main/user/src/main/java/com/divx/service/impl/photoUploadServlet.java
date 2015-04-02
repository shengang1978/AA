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
import com.divx.service.Util;
import com.divx.service.domain.manager.UserManager;
import com.divx.service.domain.model.OsfUsers;
import com.divx.service.domain.repository.DivxUserDao;
import com.divx.service.domain.repository.TokenDao;
import com.divx.service.domain.repository.impl.DivxUserDaoImpl;
import com.divx.service.domain.repository.impl.TokenDaoImpl;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.ServiceResponse;



/**
 * Servlet implementation class photoUploadServlet
 */
@WebServlet("/photoUploadServlet")
public class photoUploadServlet extends HttpServlet {
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
			DivxUserDao userDao = new DivxUserDaoImpl();	
			OsfUsers u = userDao.GetUser(sr.getTargetId());
			if(u != null){
				u.setPhotourl(sr.getPhotoUrl());
				int mid = userDao.UpdateUser(u);
				if(mid > 0){
					
					res.setResponseCode(ResponseCode.SUCCESS);
					res.setResponseMessage("success");
				}else{
					
					res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
					res.setResponseMessage("Fail to UpdateUserPhoto");	
					
				} 
			}else{
				res.setResponseCode(ResponseCode.ERROR_INVALID_PARAMETER);
				res.setResponseMessage("can not find the user");
			}
			
		}else{
			res.setResponseCode(sr.getResponseCode());
			res.setResponseMessage(sr.getResponseMessage());
		}
		out.println(Util.ObjectToJson(res));
	}

}
