<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script language="JavaScript">
function setPublic() {
	document.form1.action ="<%=request.getContextPath()%>/book/setPublic.do?isPublic=true";   
	 document.form1.submit(); 
	
};
function setUnPublic() {
	document.form1.action ="<%=request.getContextPath()%>/book/setPublic.do?isPublic=false";   
	 document.form1.submit(); 
		
};
function changeRedirect(){
	window.location.href = "<%=request.getContextPath()%>/book/config.do";
}; 
function setlessonConfig() {
	document.form1.action ="<%=request.getContextPath()%>/book/plist2String.do";   
	 document.form1.submit(); 
		
};
</script>
<body>
<form name="form1" method="post">
<table cellspacing="1" cellpadding="0" border="1" align="center">
	
	<tr>
		<td style="width:100; height:100;">选择</td>
		<td style="width:100; height:100;">ID</td>
		<td style="width:300; height:100;">isPublic</td>
		<td style="width:200; height:100;">Title</td>
		<td style="width:500; height:100;">Description</td>
		<td style="width:300; height:100;">FileUrl</td>
		<td style="width:300; height:100;">封面</td>
		<td style="width:300; height:100;">MD5</td>
		<td style="width:300; height:100;">更新</td>
	</tr>
	<c:forEach items="${mediaList}" var="l" >
	<tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'" bgcolor="#ffffff">
		<td><input type="checkbox" name="mediaIds" value="${l.mediaId}"></td>
		<td>${l.mediaId}</td>
		<td><c:choose><c:when test="${l.isPublic}">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
		<td>${l.title}</td>
		<td>${l.desc}</td>
		<td>${l.smileUrl}</td>
		<td>${l.snapshotUrl}</td>
		<td>${l.sign}</td>
		<td><a href="<%=request.getContextPath()%>/book/editBook.do?mediaId=${l.mediaId}">更新</a></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="9">
		<a href="<%=request.getContextPath()%>/book/bookList.do?startPos=0&pageSize=0">首页</a>
		<c:if test="${startPos >= 10}">
		<a href="<%=request.getContextPath()%>/book/bookList.do?startPos=${startPos-pageSize}&pageSize=${pageSize}">上一页</a>
		</c:if>
		<a href="<%=request.getContextPath()%>/book/bookList.do?startPos=${startPos+pageSize}&pageSize=${pageSize}">下一页</a>
		
		</td>
	</tr>
</table>
<br>

 <div align="center">
 <input type="button" value="更新lessonConfig" onclick="setlessonConfig();">&nbsp;&nbsp;&nbsp;&nbsp;
 	<input type="button" value="设为Public" onclick="setPublic();">&nbsp;&nbsp;&nbsp;&nbsp;
 	<input type="button" value="设为非Public" onclick="setUnPublic();">&nbsp;&nbsp;&nbsp;&nbsp;
 	<input type="button" value="添加新书籍" onclick="changeRedirect();">
 </div>
</form>

</body>
</html>