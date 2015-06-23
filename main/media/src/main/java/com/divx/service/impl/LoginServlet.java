package com.divx.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.divx.service.AuthHelper;
import com.divx.service.ConfigurationManager;
import com.divx.service.UserServiceHelper;
import com.divx.service.UserServiceHelper.AuthResponse;
import com.divx.service.Util;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.model.DcpDivxassets;
import com.divx.service.domain.model.DcpDownload;
import com.divx.service.domain.model.DcpLesson;
import com.divx.service.domain.model.DcpMedia;
import com.divx.service.domain.model.DcpMediaKeywords;
import com.divx.service.domain.model.DcpMediaSign;
import com.divx.service.domain.model.DcpOriginalasset;
import com.divx.service.domain.model.DcpScore;
import com.divx.service.domain.model.DcpScorestat;
import com.divx.service.domain.model.DcpTotalstat;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.domain.repository.impl.MediaDaoImpl;
import com.divx.service.model.DcpBaseType.eAppType;
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.edu.ScoreStat.eStatType;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.Media;
import com.divx.service.model.MediasResponse;
import com.divx.service.model.ResponseCode;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String startPos = request.getParameter("startPos");
		startPos =  startPos.isEmpty() ? "0" : startPos;
		//String endPos = request.getParameter("endPos");
		String pageSize = request.getParameter("pageSize");
		pageSize =  pageSize.isEmpty() ? "0" : pageSize;
		String page = request.getParameter("page");
		page =  page.isEmpty() ? "0" : page;
		MediaManager mediaManager = new MediaManager();
		mediaManager.setMediaDao(new MediaDaoImpl());
		MediasResponse mr = null;
		if(!"0".equals(pageSize)){
			if(Integer.parseInt(pageSize) > Integer.parseInt(page)){
				if(Integer.parseInt(pageSize) == 1){
					startPos = startPos + 10;
				}
				mr = mediaManager.GetPublicMedias(eAppType.Yingyueguan, Integer.parseInt(startPos) < 0 ? 0 : Integer.parseInt(startPos),Integer.parseInt(startPos) + 10);
				request.setAttribute("startPos", Integer.parseInt(startPos) + 10);
			}else{
				mr = mediaManager.GetPublicMedias(eAppType.Yingyueguan, Integer.parseInt(startPos) - 10 < 0 ? 0 : Integer.parseInt(startPos) -10,Integer.parseInt(startPos));
				request.setAttribute("startPos", Integer.parseInt(startPos) - 10);	
			}
			
		}else{
			mr = mediaManager.GetPublicMedias(eAppType.Yingyueguan, 0,10);
			request.setAttribute("startPos", 0);	
		}
		List<Media> mediaList = mr.getMedias();
		request.setAttribute("mediaList", mediaList);
		request.setAttribute("page", pageSize);
		request.setAttribute("pageSize", pageSize);
		request.getRequestDispatcher("/books.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		AuthResponse res = new UserServiceHelper().AuthResponse;
		//String servletBaseUrl = ConfigurationManager.GetInstance().ResetPasswort_ServletBaseUrl();
		String userServiceBaseUrl = ConfigurationManager.GetInstance().UserServiceBaseUrl();
		String username =request.getParameter("username");
		String password = request.getParameter("password");
		String reqUrl = String.format("%s/user/Login/%s/%s", userServiceBaseUrl,username,password);
		List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
		headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
		headers.add(new KeyValuePair<String, String>("Deviceuniqueid", UUID.randomUUID().toString()));
		headers.add(new KeyValuePair<String, String>("Devicetype", "0"));
		try{
			String ret = Util.HttpPut(reqUrl, "", headers);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call Login.");
				
			}
			res = Util.JsonToObject(ret, AuthResponse.class);
			if(res.getResponseCode() == ResponseCode.SUCCESS && !res.getToken().isEmpty()){
				MediaManager mediaManager = new MediaManager();
				mediaManager.setMediaDao(new MediaDaoImpl());
				MediasResponse mr = mediaManager.GetPublicMedias(eAppType.Yingyueguan, 0, 10);
				
				List<Media> mediaList = mr.getMedias();
				request.setAttribute("mediaList", mediaList);
				HttpSession session = request.getSession();
			    session.setAttribute("token", res.getToken());
			    request.setAttribute("startPos", "0");
				request.setAttribute("pageSize", "0");
				request.setAttribute("page", "0");
				//request.setAttribute("token",res.getToken());
				request.getRequestDispatcher("/books.jsp").forward(request,response);
			}
		}catch(Exception ex){
			
		}
		
		
		
	}

}
