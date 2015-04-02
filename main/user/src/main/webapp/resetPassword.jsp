<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/resetPassword" method="post">
<table>
	<tr>
		<td><input type="hidden" name="urlParameter" value="<%=java.net.URLEncoder.encode(request.getParameter("p"),"UTF-8") %>"/></td></tr>
<tr><td><span>newPassword:</span></td><td><input type="password" name="newPassword"/></td></tr>
<tr><td><span>repeatNewPassword:</span></td><td><input type="password" name="repeatNewPassword"/></td></tr>
<tr><td><input type="submit" value="submit"/></td><td><input type="reset" value="reset"/></td></tr>
</table>
</form>
</body>
</html>