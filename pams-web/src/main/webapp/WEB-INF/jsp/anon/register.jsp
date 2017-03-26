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
    <title>register page</title>


    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 其他css文件下载下面 -->   
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>这是注册页面</h1>
    
	<form action="<%=path %>/web/user/register"  method="POST" id="registerForm">
        <div>  
            <input type="text" name="username" class="username" id="username"
            placeholder="您的用户名" autocomplete="off"/>  
        </div> 
        <div>  
            <input type="password" name="password" class="password" id="password"
            placeholder="输入密码" oncontextmenu="return false"/>  
        </div>  
        <div>  
            <input type="password" name="confirm_password" class="confirm_password" 
            placeholder="再次输入密码" oncontextmenu="return false"/>  
        </div>  
        <div>  
            <input type="text" name="phone" class="phone" id="phone"
            placeholder="输入手机号码" autocomplete="off"/>  
        </div>  
        <div>  
            <input type="text" name="mail" class="mail" id="mail"
            placeholder="输入邮箱地址" oncontextmenu="return false"/>  
        </div>  
        <div>  
            <input type="text"  class="kaptcha" name="kaptcha" style="width:150px;" id="kaptcha"  
             placeholder="输入验证码" autocomplete="off"/>            
             <img src="<%=path %>/web/code/kaptcha-img" width="110" height="40" 
             id="kaptchaImage"  title="点击更换图片" alt="点击更换图片"/>     
        </div>  
        <button id="submit" type="submit">注 册</button>  
    </form>  
       
	<!-- 从model中获取错误信息 -->
	<P>${message}</P>

	<!-- js文件依次放在下面 -->
	<script> 
   		var path = "<%=path%>"; 
   		var basePath = "<%=basePath%>";
	</script>
	<script src="<%=basePath%>static/bootstrap/js/jquery-3.2.0.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
	<script src="<%=basePath%>static/bootstrap/js/jquery.validate.js"></script>  
	<script src="<%=basePath%>static/js/register.js"></script>
	
  </body>
</html>
