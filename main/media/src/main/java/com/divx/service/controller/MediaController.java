package com.divx.service.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.divx.service.AuthHelper;
import com.divx.service.ConfigurationManager;
import com.divx.service.MD5Util;
import com.divx.service.UserServiceHelper;
import com.divx.service.Util;
import com.divx.service.UserServiceHelper.AuthResponse;
import com.divx.service.domain.manager.MediaManager;
import com.divx.service.model.CreateMediaResponse;
import com.divx.service.model.DcpBaseType.eDeviceType;
import com.divx.service.model.KeyValuePair;
import com.divx.service.model.Media;
import com.divx.service.model.MediaBase;
import com.divx.service.model.MediaBaseType.eContentType;
import com.divx.service.model.MediasResponse;
import com.divx.service.model.ResponseCode;
import com.divx.service.model.DcpBaseType.eAppType;
import com.divx.service.model.ServiceResponse;

@Controller
@SessionAttributes("token") 
@RequestMapping("/book")
public class MediaController {
	private MediaManager mediaManager;
	
	@Autowired
	public void setMediaManager(MediaManager mediaManager)
	{
		this.mediaManager = mediaManager; 
	}
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ModelAndView userLogin(String username,String password,HttpSession session)
	{
		return userView(username,password,session);
	}
	
	@RequestMapping(value = "/bookList.do", method = RequestMethod.GET)
	public ModelAndView bookList(int startPos,int pageSize)
	{
		return bookListView(startPos,pageSize);
	}
	@ResponseBody
	@RequestMapping(value = "/setPublic.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String setMediasToPublic(boolean isPublic,String mediaIds,HttpServletRequest request)
	{
		return setMediasToPublicView(isPublic,mediaIds,request);
	}
	@RequestMapping(value = "/config.do", method = RequestMethod.GET)
	public ModelAndView getConfig()
	{
		return getConfigView();
	}
	@ResponseBody
	@RequestMapping(value = "/bookAdd.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String bookAdd(Media media,HttpServletRequest request)
	{
		return bookAddView(media,request);
	}
	
	@RequestMapping(value = "/editBook.do", method = RequestMethod.GET)
	public ModelAndView editBook(int mediaId)
	{
		return editBookView(mediaId);
	}
	@ResponseBody
	@RequestMapping(value = "/bookUpdate.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String bookUpdate(Media media,HttpServletRequest request)
	{
		return bookUpdateView(media,request);
	}
	@ResponseBody
	@RequestMapping(value = "/plist2String.do", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String plist2String(String mediaIds,HttpServletRequest request)
	{
		return plist2StringView(mediaIds,request);
	}
	public ModelAndView userView(String username,String password,HttpSession session){
		ModelAndView mav = new ModelAndView("/book/bookIndex");
		try{
			AuthResponse res = new UserServiceHelper().AuthResponse;
			String userServiceBaseUrl = ConfigurationManager.GetInstance().UserServiceBaseUrl();
			String reqUrl = String.format("%s/user/Login/%s/%s", userServiceBaseUrl,username,password);
			List<KeyValuePair<String, String>> headers = new ArrayList<KeyValuePair<String, String>>();
			headers.add(new KeyValuePair<String, String>("Content-Type", "application/json"));
			headers.add(new KeyValuePair<String, String>("Deviceuniqueid", UUID.randomUUID().toString()));
			headers.add(new KeyValuePair<String, String>("Devicetype", "0"));
			String ret = Util.HttpPut(reqUrl, "", headers);
			if(ret.isEmpty()){
				res.setResponseCode(ResponseCode.ERROR_INTERNAL_ERROR);
				res.setResponseMessage("Fail to call Login.");
				
					
			}
			res = Util.JsonToObject(ret, AuthResponse.class);
			if(res.getResponseCode() == ResponseCode.SUCCESS && !res.getToken().isEmpty()){
				mav.addObject("success",res.getResponseMessage());
				session.setAttribute("token", res.getToken());
				return mav;
			}
		}catch(Exception ex){
			
		}
		return new ModelAndView();
	}
	
	public ModelAndView bookListView(int startPos,int pageSize){
		ModelAndView mav = new ModelAndView("/book/bookList");
		try{
			int nStartPos = startPos;
			int nCount = pageSize;
			
			if (pageSize < 10)
			{
				nCount = 10;
			}
			List<Media> medias = mediaManager.GetAllMedias(eAppType.Yingyueguan, nStartPos, startPos + nCount - 1);
			mav.addObject("startPos", startPos);
			mav.addObject("pageSize", nCount);

			mav.addObject("mediaList", medias);
			
		   
			
		}catch(Exception ex){
			
		}
		return mav;
	}
	
	public String setMediasToPublicView(boolean isPublic,String mediaIds,HttpServletRequest request){
		String message = "";
		String messageTemplate = "<script type=\"text/javascript\">alert(\"%s\");%s</script>";
		try{
			if(mediaIds == null){
				message = String.format(messageTemplate, "请选中要设置的书籍","history.back();");
				return message;
			}
			String[] ids = mediaIds.split(","); 
			List<String> mIds = Arrays.asList(ids);
			
			int	ret = mediaManager.SetMediaPublic(isPublic, mIds);
			if(ret >= 0){
				//"<script type=\"text/javascript\">alert(\"设置书籍为Public成功\");window.location.href=\"" + request.getContextPath() + "/book/bookList.do?startPos=0&pageSize=0\";</script>";
				message = String.format(messageTemplate,isPublic ? "设置书籍为Public成功" : "设置书籍为非Public成功","window.location.href=\"" + request.getContextPath() + "/book/bookList.do?startPos=0&pageSize=0\"");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			message =  String.format(messageTemplate,isPublic ? "设置书籍为Public失败" + Util.getStackTrace(ex) : "设置书籍为非Public失败" + Util.getStackTrace(ex),"history.back()");
		}
		return message;
	}
	
	public ModelAndView getConfigView(){
		ModelAndView mav = new ModelAndView("/book/bookAdd");
		try{
			String fileServerUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
			String storageServletBaseUrl = ConfigurationManager.GetInstance().StorageServletBaseUrl();
			mav.addObject("storageServletBaseUrl",Util.UrlWithSlashes(storageServletBaseUrl));
			mav.addObject("fileServerUrl",Util.UrlWithSlashes(fileServerUrl));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return mav;
	}
	
	public String bookAddView(MediaBase media,HttpServletRequest request){
		String message = "";
		String messageTemplate = "<script type=\"text/javascript\">alert(\"%s\");%s</script>";
		try{
			if(media != null){
				media.setContentType(eContentType.EduBookURL);
				String fileServerUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
				String fileOutPath = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH();
				String fileName = media.getSmileUrl().replace(Util.UrlWithSlashes(fileServerUrl), "");
				String filePath = Util.UrlWithSlashes(fileOutPath) + fileName;
				File file = new File(filePath);
				if(!file.exists()){
					message = String.format(messageTemplate, "文件不存在，请重新上传!","history.back();");
					return message;
				}
				String sign = MD5Util.getMd5ByFile(filePath);
				media.setSign(sign);
				String token = (String)request.getSession().getAttribute("token");
				AuthHelper helper = new AuthHelper(token);
				if (helper.isGuest())
				{	
					message = String.format(messageTemplate, "Token失效，请重新登录!","window.location.href=\"" + request.getContextPath() + "/book/login.jsp\"");
					return message;
				}
				CreateMediaResponse cmr = mediaManager.CreateMedia(media, helper.getUserId(), eAppType.Yingyueguan);
				if(ResponseCode.SUCCESS == cmr.getResponseCode() && cmr.getMediaId() > 0){
					ServiceResponse res = mediaManager.SetBookInfo(cmr.getMediaId(), filePath);
					if(ResponseCode.SUCCESS == res.getResponseCode()){
						message = String.format(messageTemplate, "添加新书籍成功!","window.location.href=\"" + request.getContextPath() + "/book/bookList.do?startPos=0&pageSize=0\"");
					}else{
						message = String.format(messageTemplate, "添加新书籍失败，错误信息:" + res.getResponseMessage(),"history.back();");
					}
				}else{
					message = String.format(messageTemplate, "添加新书籍失败，错误信息:" + cmr.getResponseMessage(),"history.back();");
				}
			}
		}catch(Exception ex){
			
		}
		return message;
	}
	
	public ModelAndView editBookView(int mediaId){
		ModelAndView mav = new ModelAndView("/book/bookUpdate");
		try{
			Media media = mediaManager.GetMedia(0, mediaId, eDeviceType.iOS);
			if(media != null){
				mav.addObject("media",media);
				String fileServerUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
				String storageServletBaseUrl = ConfigurationManager.GetInstance().StorageServletBaseUrl();
				mav.addObject("storageServletBaseUrl",Util.UrlWithSlashes(storageServletBaseUrl));
				mav.addObject("fileServerUrl",Util.UrlWithSlashes(fileServerUrl));
			}else{
				mav.addObject("message","获取该书籍信息失败");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return mav;
	}
	
	public String bookUpdateView(MediaBase media,HttpServletRequest request){
		String message = "";
		String messageTemplate = "<script type=\"text/javascript\">alert(\"%s\");%s</script>";
		try{
			if(media != null){
				media.setContentType(eContentType.EduBookURL);
				String fileServerUrl = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PREFIX();
				String fileOutPath = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH();
				String fileName = media.getSmileUrl().replace(Util.UrlWithSlashes(fileServerUrl), "");
				String filePath = Util.UrlWithSlashes(fileOutPath) + fileName;
				File file = new File(filePath);
				if(!file.exists()){
					message = String.format(messageTemplate, "文件不存在，请重新上传!","history.back();");
					return message;
				}
				String sign = MD5Util.getMd5ByFile(filePath);
				media.setSign(sign);
				String token = (String)request.getSession().getAttribute("token");
				AuthHelper helper = new AuthHelper(token);
				if (helper.isGuest())
				{	
					message = String.format(messageTemplate, "Token失效，请重新登录!","window.location.href=\"" + request.getContextPath() + "/book/login.jsp\"");
					return message;
				}
				ServiceResponse cmr = mediaManager.UpdateMedia(media, helper.getUserId());
				if(ResponseCode.SUCCESS == cmr.getResponseCode()){
					ServiceResponse res = mediaManager.SetBookInfo(media.getMediaId(), filePath);
					if(ResponseCode.SUCCESS == res.getResponseCode()){
						message = String.format(messageTemplate, "更新书籍成功!","window.location.href=\"" + request.getContextPath() + "/book/bookList.do?startPos=0&pageSize=0\"");
					}else{
						message = String.format(messageTemplate, "更新书籍失败，错误信息:" + res.getResponseMessage(),"history.back();");
					}
				}else{
					message = String.format(messageTemplate, "更新书籍失败，错误信息:" + cmr.getResponseMessage(),"history.back();");
				}
			}
		}catch(Exception ex){
			
		}
		return message;
	}
	
	public String plist2StringView(String mediaIds,HttpServletRequest request){
		String message = "";
		String messageTemplate = "<script type=\"text/javascript\">alert(\"%s\");%s</script>";
		try{
			if(mediaIds == null){
				message = String.format(messageTemplate, "请选中要设置的书籍","history.back();");
				return message;
			}
			String[] ids = mediaIds.split(","); 
			List<String> mIds = Arrays.asList(ids);
			ServiceResponse res = mediaManager.setPlist2String(mIds);
			if(ResponseCode.SUCCESS == res.getResponseCode()){
				
				message = String.format(messageTemplate,"更新LessonConfig成功","window.location.href=\"" + request.getContextPath() + "/book/bookList.do?startPos=0&pageSize=0\"");
			}else{
				message =  String.format(messageTemplate, "更新LessonConfig失败:" + res.getResponseMessage(),"history.back()");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			message =  String.format(messageTemplate, "更新LessonConfig失败:" + Util.getStackTrace(ex),"history.back()");
		}
		return message;
	}
	
}
