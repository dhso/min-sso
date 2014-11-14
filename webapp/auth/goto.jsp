<%@ page language="java" import="java.util.*" import="java.net.*" contentType="text/html; charset=UTF-8" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
	String gotoUrl = (String) request.getAttribute("gotoUrl");
	String qqUserInfoStr = URLEncoder.encode(request.getAttribute("qqUserInfoStr").toString(), "UTF-8");
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
	<title>goto</title>
	<script src="<%=basePath%>/static/js/jquery/jquery.2.0.1.min.js"></script>
</head>
<body>
	<form action="<%=gotoUrl%>" method="post" style="display: none;">
		<input type="text" name="qqUserInfoStr" value="<%=qqUserInfoStr%>">
		<button type="submit" id="submit" value="Submit">Submit</button>
	</form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#submit").click();
		});
	</script>
</body>
</html>
