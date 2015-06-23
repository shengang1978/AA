<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
<table cellspacing="1" cellpadding="0" border="1" align="center">
	
	<tr>
		<td style="width:100; height:100;">ID</td>
		<td style="width:200; height:100;">书名</td>
		<td style="width:500; height:100;">下载地址</td>
		<td style="width:300; height:100;">封面</td>
		<td style="width:300; height:100;">MD5</td>
		<td style="width:300; height:100;">更新</td>
	</tr>
	<c:forEach items="${mediaList}" var="l" >
	<tr onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'" bgcolor="#ffffff">
		<td>${l.mediaId}</td>
		<td>${l.title}</td>
		<td>${l.smileUrl}</td>
		<td>${l.snapshotUrl}</td>
		<td>${l.sign}</td>
		<td><a href="<%=request.getContextPath()%>/EditBookInfo?mediaId=${l.mediaId}">修改</a></td>
	</tr>
	</c:forEach>
	<tr>
		<td colspan="5">
		<a href="<%=request.getContextPath()%>/Login?startPos=0&pageSize=0&page=0">首页</a>
		<c:if test="${pageSize > 0}">
		<a href="<%=request.getContextPath()%>/Login?startPos=${startPos}&pageSize=${pageSize - 1}&page=${page}">上一页</a>
		</c:if>
		<a href="<%=request.getContextPath()%>/Login?startPos=${startPos}&pageSize=${pageSize + 1}&page=${page}">下一页</a>
		
		</td>
	</tr>
</table>
<br>
 <div align="center"><a href="<%=request.getContextPath()%>/EditBookInfo?mediaId=0">添加</a></div>>
</form>

</body>
</html>