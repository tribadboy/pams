<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>login page</title>


    <link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">
    <!-- 其他css文件下载下面 -->   
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>这是登录页面</h1>
    
	<form action="<%=path %>/web/anon/doLogin"  method="POST">
        <label>username</label>
        <input type="text" name="username" maxLength="32"/>
        <label>password</label>
        <input type="password" name="password"/> <br>
        <img src="<%=path %>/web/code/kaptcha-img" width="200" id="kaptchaImage" 
        	title="点击更换图片" alt="点击更换图片" />  
        <input type="text" name="kaptcha" class="kaptcha-code" value=""/> 
        <div class="tips" style="color:red"></div> <br>
        <input type="submit" value="login" class="submitBtn"/>
    </form>  
    
    <a href="<%=path %>/web/user/registerPage"><button>用户注册</button></a>
       
	<!-- 从model中获取错误信息 -->
	<P>${message}</P>

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
