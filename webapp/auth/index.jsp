<%@ page language="java" import="java.util.*" import="com.minws.sso.auth.QQUserInfo" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<title>SSO - 单点登录</title>
	<link rel="stylesheet" href="<%=basePath%>/static/css/client.css">
	<script src="<%=basePath%>/static/js/jquery/jquery.2.0.1.min.js"></script>
	<script src="<%=basePath%>/static/js/modernizr.custom.20819.js"></script>
</head>
<body>

</body>
</html>