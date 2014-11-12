<%@ page language="java" import="java.util.*" import="com.minws.sso.frame.util.ProsMap" import="com.minws.sso.frame.util.Identities" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String queryString = request.getQueryString();
	queryString = (null == queryString) ? "" : "?" + queryString;
	String appId = ProsMap.getStrPro("sso.qqauth.appId");
	String appKey = ProsMap.getStrPro("sso.qqauth.appKey");
	String scope = ProsMap.getStrPro("sso.qqauth.scope");
	String redirectUrl = ProsMap.getStrPro("sso.qqauth.redirectUrl");
	Cookie[] cookies = request.getCookies();
	String state = "";
	for(Cookie cookie:cookies ){
		if("sso_state".equals(cookie.getName()) ){
			state = cookie.getValue();
			break;
		}
	}
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
	<title>登录</title>
	<link rel="stylesheet" href="<%=basePath%>/static/css/login.css">
	<script src="<%=basePath%>/static/js/jquery/jquery.2.0.1.min.js"></script>
	<script src="<%=basePath%>/static/js/modernizr.custom.20819.js"></script>
</head>
<body>
	<div class="head">
	    <div class="container">
	        <div class="logo"><!--这里插入Img标签大小188*60px--></div>
	        <div class="menu">
	            <!-- <a href="" id="menu1"></a>
	            <span>|</span>
	            <a href="" id="menu2"></a>
	            <span>|</span>
	            <a href="" id="menu3"></a>
	            <span>|</span>
	            <a href="" id="menu4"></a>
	            <span>|</span> -->
	        </div>
	    </div>
	</div>
	<div class="body">
		<div class="container">
		  	<div class="main_box">
		    	<div class="main_cont">
		        	<div class="wrapper">
		            <dl class="login_info clearfix">
		                <dt class="login_title">登录</dt>
		                <dd><input type="text" class="srh" placeholder="用户名" required=""></dd>
		                <dd><input type="text" class="srh" placeholder="密码" required=""></dd>
		                <dd><input class="button white radius4 dl" type="button" value="登录" /></dd>
		                <dd><a class="button_qq" href="https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=<%=appId%>&redirect_uri=<%=redirectUrl%>&scope=<%=scope%>&state=<%=state%>"></a></dd>
		            </dl>
		    		</div>
		            <div class="box_border"></div>
				</div>
		    </div>
		</div>
	</div>
	<div class="foot">
	    <div class="footer_line"></div>
	    <div class="footer_body">
		<p class="font_w7">底部</p>
	    <p><a href="">copyright</a>|<a href="">copyright</a></p>
	    </div>
	</div>
</body>
</html>
