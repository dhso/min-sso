<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
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
	<meta property="qc:admins" content="454027633765167363757341564747716" />
	<title>SSO - 单点登录</title>
	<link rel="stylesheet" href="<%=basePath%>/static/css/client.css">
	<script src="<%=basePath%>/static/js/jquery/jquery.2.0.1.min.js"></script>
	<script src="<%=basePath%>/static/js/modernizr.custom.20819.js"></script>
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101167626" data-redirecturi="http://sso.minws.com/client/index" charset="utf-8"></script>
</head>
<body>
	<div class="container">
		<section id="content">
			<form action="">
				<h1>登录</h1>
				<div>
					<input type="text" placeholder="用户名" required="" id="username" />
				</div>
				<div>
					<input type="password" placeholder="密码" required="" id="password" />
				</div>
				<div>
					<input type="submit" value="登录" />
					<a href="#">忘记密码?</a>
					<a href="#">注册</a>
					<a href="<%=basePath%>/client/qqoauth">qqoauth</a>
				</div>
			</form><!-- form -->
			<div class="button">
				<span id="qqLoginBtn"></span>
				<script type="text/javascript">
				    QC.Login({
				       btnId:"qqLoginBtn",    //插入按钮的节点id
				       scope:"all",
				       size: "A_XL"
				});
				</script>
			</div><!-- button -->
		</section><!-- content -->
	</div>
	
</body>
</html>