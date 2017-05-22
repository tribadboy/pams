<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    String webPath = request.getScheme() + "://" + request.getServerName() + ":" 
			+ request.getServerPort() + "/pams-web/";
%> 
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>login page</title>


    <link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/login.css" rel="stylesheet">
    <!-- 其他css文件下载下面 -->   
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
  <div id="login">
    <h1>后台登录</h1>
	<form action="<%=path %>/web/anon/doLogin"  method="POST">
        <input type="text" name="username" maxLength="32" placeholder="请输入用户名"/>
        <input type="password" name="password" maxLength="32" placeholder="请输入密码"/> <br>
        <img src="<%=path %>/web/code/kaptcha-img" id="kaptchaImage" 
        	title="点击更换图片" alt="点击更换图片" class="kaptchaImage" />  
        <input type="text" name="kaptcha" class="kaptcha-code" value=""
             placeholder="请输入验证码"/>   
        <div class="tips" style="color:red"></div>  
        <input type="submit" value="login" class="submitBtn"/><br>
		<a href="<%=webPath %>"><label style="font-size:15px;color:#cccccc">项目地址</label></a>
        <h5>${message}</h5>
    </form>  
           
</div>
	<!-- js文件依次放在下面 -->
	<script> 
   		var path = "<%=path%>"; 
   		var basePath = "<%=basePath%>";
	</script>
	<script src="<%=basePath%>static/js/jquery.min.js"></script>
	<script src="<%=basePath%>static/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/js/login.js"></script>
  </body>
</html>
