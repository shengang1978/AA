<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
</head>
<body>
<form method="post" action="<%=request.getContextPath()%>/book/login.do">

<table align="center">
	
	<tr><td><span>用户名:</span></td><td><input type="text" name="username"/></td></tr>
	<tr><td><span>密&nbsp;码:</span></td><td><input type="password" name="password"/></td></tr>
	<tr><td><input type="submit" value="登录"/></td><td><input type="reset" value="取消"/></td></tr>
</table>
 </form>
</body>
</html>