<%@ page language="java" import="java.util.*" import="com.minws.sso.frame.util.ProsMap" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String queryString = request.getQueryString();
	queryString = (null == queryString) ? "" : "?" + queryString;
	String appId = ProsMap.getStrPro("sso.qqauth.appId");
	String appKey = ProsMap.getStrPro("sso.qqauth.appKey");
	String scope = ProsMap.getStrPro("sso.qqauth.scope");
	String redirectUrl = ProsMap.getStrPro("sso.qqauth.redirectUrl");
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
	<meta property="qc:admins" content="454027633765167363757341564747716" />
	<title>登录</title>
	<link rel="stylesheet" href="<%=basePath%>/static/css/login.css">
	<script src="<%=basePath%>/static/js/jquery/jquery.2.0.1.min.js"></script>
	<script src="<%=basePath%>/static/js/modernizr.custom.20819.js"></script>
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101167626" charset="utf-8"></script>
</head>
<body>
QQ info
</body>
</html>
