<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>  
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加新书籍</title>
<link href="<%=path%>/css/uploadify.css" rel="stylesheet" type="text/css" />  
<script type="text/javascript" src="<%=path%>/js/jquery-1.7.2.min.js"></script>  
<script type="text/javascript" src="<%=path%>/js/jquery.uploadify.js"></script>  
<script type="text/javascript">  
        $(document).ready(function() {  
            
              $("#uploadify").uploadify( {//初始化函数
                'swf'            : '<%=path%>/js/uploadify.swf', 
                'uploader' : '${storageServletBaseUrl}Upload',
                'queueID'        : 'fileQueue',//与下面的id对应  
               // 'progressData'   :'percentage',
                'multi' : false,
                'buttonText' : '浏览',
                'uploadLimit' : 10,
                'fileTypeDesc'   : 'rar文件或zip文件',  
                'fileTypeExts'   : '*.rar;*.zip', //控制可上传文件的扩展名，启用本项时需同时声明fileDesc  
                onUploadSuccess : function(file,data,response) {
                    // 所有文件上传成功后触发
                      //alert("上传成功:"+ file.name+"---"+data);
                     // $("#filepath").val(data);
                      $("#fileurl").val('${fileServerUrl}'+data);
                },  
                
                      
            });  
             
                  
            
        }); 

        function checkInputIsEmpty(){
            var title = $("input[name='title']").val().trim();
            var desc = $("input[name='desc']").val().trim();
            var keyWord = $("input[name='keywords']").val().trim();
            var fileName = $("input[name='fileName']").val().trim();
            if( "".equals(title)){
            	alert("标题不能为空");
                }
           
            if( "".equals(desc)){
            	alert("描述不能为空");
                }
           
             
            if( "".equals(keyWord)){
            	alert("关键字不能为空");
                }
           
            
            if( "".equals(fileName)){
            	alert("请先上传文件");
                }
        
       
        } 
         
</script>
</head>
<body>
<form action="<%=path%>/book/bookAdd.do" method="post">
<input type="hidden" name="mediaId" value="0">
	<div>
		<span>标题</span>
		<input type="text" name="title">
		
	</div><br>
	<div>
		<span>描述</span>
		<input type="text" name="desc">
	</div><br>
	<div>
		<span>关键字</span>
		<input type="text" name="keywords">
	</div><br>
	<div>
		 <div id="fileQueue"></div>  
        <input type="file" name="uploadify" id="uploadify" />  
	</div><br>
	<div>
		<span>文件下载路径</span>
		<input type="text" name="smileUrl" id="fileurl" readonly="readonly">
	</div><br>
	<div>
	<input type="submit" value="创建书籍" onclick="checkInputIsEmpty();">&nbsp;	&nbsp;<input type="reset" value="取消">
	</div>
</form>

</body>
</html>