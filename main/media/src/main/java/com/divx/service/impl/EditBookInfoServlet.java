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
import com.divx.service.model.MediaBaseType.eFileType;
import com.divx.service.model.MediasResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.DcpBaseType.eAppType;

/**
 * Servlet implementation class UpdateBookInfo
 */
@WebServlet("/EditBookInfoServlet")
public class EditBookInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBookInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		try{
			String token =(String)request.getSession().getAttribute("token");
			//String token = request.getParameter("token");
			AuthHelper helper = new AuthHelper(token);
			if (helper.isGuest())
			{	
				request.getRequestDispatcher("/login.jsp").forward(request,response);
			}
			String mediaId = request.getParameter("mediaId");
			MediaManager mediaManager = new MediaManager();
			MediaDao mediaDao = new MediaDaoImpl();
			mediaManager.setMediaDao(mediaDao);
			if("0".equals(mediaId.trim())){
				request.setAttribute("mediaId", "0");
				request.setAttribute("title", "");
				request.setAttribute("smileUrl", "");
				request.setAttribute("snapshotUrl", "");
				request.setAttribute("sign","");
			}else{
				
				DcpMedia media = mediaDao.GetMedia(Integer.parseInt(mediaId));
				DcpMediaSign mediaSign = null;
				List<DcpDivxassets> asset = null;
				if(media != null && media.getMediaId() > 0){
					mediaSign = mediaDao.GetMediaSign(media.getMediaId());
					asset = mediaDao.GetDivxAsset(media.getMediaId(),null);
				}
				request.setAttribute("mediaId", media.getMediaId());
				request.setAttribute("title", media.getTitle());
				request.setAttribute("smileUrl", asset.get(0).getLocation());
				request.setAttribute("snapshotUrl", media.getSnapshoturl());
				request.setAttribute("sign",mediaSign.getSign());
	
			}
			request.setAttribute("token", token);
			request.getRequestDispatcher("/updateBookInfo.jsp").forward(request,response);
			
			
		}catch(Exception ex){
			
		}
		
	}

}
