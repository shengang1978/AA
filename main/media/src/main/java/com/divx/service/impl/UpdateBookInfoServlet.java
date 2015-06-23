package com.divx.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;

import com.divx.service.AuthHelper;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.domain.model.DcpDivxassets;
import com.divx.service.domain.model.DcpMedia;
import com.divx.service.domain.model.DcpMediaSign;
import com.divx.service.domain.repository.MediaDao;
import com.divx.service.domain.repository.impl.MediaDaoImpl;
import com.divx.service.model.CreateMediaResponse;
import com.divx.service.model.Media;
import com.divx.service.model.MediaBase;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.MediasResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.DcpBaseType.eAppType;

/**
 * Servlet implementation class UpdateBookInfo
 */
@WebServlet("/UpdateBookInfoServlet")
public class UpdateBookInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");	
		PrintWriter out = response.getWriter();
		try{
			String token =(String)request.getSession().getAttribute("token");
			AuthHelper helper = new AuthHelper(token);
			if (helper.isGuest())
			{	
				request.getRequestDispatcher("/login.jsp").forward(request,response);
			}
			String mediaId =request.getParameter("mediaId");
			String bookName = request.getParameter("bookName");
			String bookUrl = request.getParameter("bookUrl");
			String snapshotUrl = request.getParameter("snapshotUrl");
			String sign = request.getParameter("sign");
			MediaManager mediaManager = new MediaManager();
			MediaDao mediaDao = new MediaDaoImpl();
			mediaManager.setMediaDao(mediaDao);
			if(!snapshotUrl.trim().isEmpty() && !sign.trim().isEmpty() && !bookName.trim().isEmpty() && !bookUrl.trim().isEmpty()){
				if("0".equals(mediaId.trim())){
					MediaBase mediaBase = new MediaBase();
					mediaBase.setContentType(eContentType.EduBookURL);
					mediaBase.setPublic(true);
					mediaBase.setTitle(bookName);
					mediaBase.setDescription(bookName);
					mediaBase.setKeywords(bookName);
					mediaBase.setSmileUrl(bookUrl);
					mediaBase.setSign(sign);
					CreateMediaResponse res = mediaManager.CreateMedia(mediaBase, helper.getUserId(), eAppType.Yingyueguan);
					if(res.getResponseCode() == ResponseCode.SUCCESS && res.getMediaId() > 0){
						DcpMedia media = mediaDao.GetMedia(res.getMediaId());
						media.setSnapshoturl(snapshotUrl);
						int mid = mediaDao.UpdateMedia(media);
						if(mid > 0){
							out.print("<script type=\"text/javascript\">alert(\"添加新书籍成功\");window.location.href=\"" + request.getContextPath() + "/Login?startPos=0&pageSize=0&page=0\";</script>");
						}
						
					}else{
						out.print("<script type=\"text/javascript\">alert(\"添加新书籍失败\");history.back();</script>");
					}
					
				}else{
					DcpMedia media = mediaDao.GetMedia(Integer.parseInt(mediaId));
					media.setSnapshoturl(snapshotUrl);
					int mid = mediaDao.UpdateMedia(media);
					List<DcpDivxassets> asset = mediaDao.GetDivxAsset(media.getMediaId(),null);
					asset.get(0).setLocation(bookUrl);
					mediaDao.CreateDivxAsset(asset.get(0));
					DcpMediaSign mediaSign = mediaDao.GetMediaSign(media.getMediaId());
					mediaSign.setSign(sign);
					int sid = mediaDao.createMediaSign(mediaSign);
					if(sid > 0){
						mediaManager.cleanPublishMediaCache();
						out.print("<script type=\"text/javascript\">alert(\"修改成功\");window.location.href=\"" + request.getContextPath() + "/Login?startPos=0&pageSize=0&page=0\";</script>");
					}else{
						out.print("<script type=\"text/javascript\">alert(\"修改失败\");history.back();</script>");
					}
					
					
				}
				
					
					
					
				
			}else{
				
				out.print("<script type=\"text/javascript\">alert(\"书名、下载URL、封面、MD5都不能为空\");history.back();</script>");
				
			}
			
			out.close();
			//response.sendRedirect(request.getContextPath()+"/Login?startPos=0&pageSize=0");
			
			
		}catch(Exception ex){
			
		}
		
	}

}
