<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>童言同语</title>
</head>
<body style="overflow-x:hidden;text-align:center;margin:auto;">

    <script src="../js/jquery-1.11.3.min.js"></script>

    <script>

        $(window).load(function () {
        	var browseWidth = document.body.clientWidth;
            var browseHeight = Math.max(document.body.clientHeight,document.documentElement.clientHeight);
            if(browseHeight/browseWidth < 1.2){
                window.location.href="pad.do" + window.location.search;
            }
            else
            {
            	window.location.href="phone.do" + window.location.search;
            }
        });

    </script>
</body>
</html>
