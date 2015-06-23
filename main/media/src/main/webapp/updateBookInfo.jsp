<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script language="JavaScript">
function selectChange(value) {
	var books = document.getElementById("book"); 

	//var bookName;
	var title;
	var smileUrl;
	var snapshotUrl;
	var sign;
	for(i=0;i<books.length;i++){ 
		if(books[0].selected==true){ 
			//bid = document.getElementById("bid").style.visibility = "visible";   
			title = "";
			smileUrl = "";
			snapshotUrl = "";
			sign = "";
			
		}else{
			//bid = document.getElementById("bid").style.visibility = "hidden"; 
			title = books[0].innerText;
			smileUrl = "";
			snapshotUrl = "";
			sign = "";
		}	
			
		
		
	}
	
}
</script>
</head>
<body>

<form method="post" action="<%=request.getContextPath()%>/UpdateBookInfo">
	<input type="hidden" name="mediaId" value="${mediaId}" >
	<div>
		<span>书名:</span><input type="text"  name="bookName" value="${title}"  style="width:600; height:400;">
	</div><br>
	<div>
		<span>下载URL:</span><input type="text"  name="bookUrl" value="${smileUrl}" style="width:600; height:400;">
	</div><br>
	<div><span>封面:</span><input type="text" name="snapshotUrl" value="${snapshotUrl}"  style="width:600; height:400;">
	</div><br>
	<div><span>MD5:</span><input type="text" name="sign" value="${sign}" style="width:600; height:400;">
	</div><br>
	<div><input type="submit" value="提交"/>&nbsp;&nbsp;<input type="reset" value="取消"/></div>
</form>

</body>
</html>