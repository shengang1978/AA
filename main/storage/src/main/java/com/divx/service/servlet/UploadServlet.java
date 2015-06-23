package com.divx.service.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.divx.service.ConfigurationManager;
import com.divx.service.Util;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  /*String savePath = this.getServletConfig().getServletContext()  
	                .getRealPath("");  */
		  String savePath = ConfigurationManager.GetInstance().THUMBNAIL_OUTPUT_PATH();  
	        File f1 = new File(savePath);  
	        //System.out.println(savePath);  
	        if (!f1.exists()) {  
	            f1.mkdirs();  
	        }  
	        DiskFileItemFactory fac = new DiskFileItemFactory();  
	        ServletFileUpload upload = new ServletFileUpload(fac);  
	        upload.setHeaderEncoding("utf-8");  
	        List fileList = null;  
	        try {  
	            fileList = upload.parseRequest(request);  
	        } catch (FileUploadException ex) {  
	            return;  
	        }  
	  
	        Iterator<FileItem> it = fileList.iterator();  
	        String name = "";  
	        String extName = "";  
	        while (it.hasNext()) {  
	            FileItem item = it.next();  
	            if (!item.isFormField()) {  
	                name = item.getName();  
	                long size = item.getSize();  
	                String type = item.getContentType();  
	               // System.out.println(size + " " + type);  
	                if (name == null || name.trim().equals("")) {  
	                    continue;  
	                }  
	  
	                //扩展名格式：   
	                if (name.lastIndexOf(".") >= 0) {  
	                    extName = name.substring(name.lastIndexOf("."));  
	                }  
	  
	                File file = null;  
	                do {  
	                    //生成文件名：  
	                    //name = UUID.randomUUID().toString(); 
	                	long date = new Date().getTime();
	                    name = name.replace(".", "_") + "_" + date ;
	                    file = new File(savePath + name + extName);  
	                } while (file.exists());  
	                File saveFile = new File(Util.UrlWithSlashes(savePath) + name + extName);  
	                try {  
	                    item.write(saveFile); 
	                    
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	       response.getWriter().print(name + extName);  
	     
	      
	    } 
	

}
