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


    <link href="<%=basePath%>static/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/css/register.css" rel="stylesheet">
    <!-- 其他css文件下载下面 -->   
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
   <div id ="register">
	<form action="<%=path %>/web/user/register"  method="POST" id="registerForm">
        <h1>用户注册</h1>
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
            <input type="text" name="email" class="email" id="email"
            placeholder="输入邮箱地址" oncontextmenu="return false"/>  
        </div>  
        <div>  
            <img src="<%=path %>/web/code/kaptcha-img" class="kaptchaImage"
             id="kaptchaImage"  title="点击更换图片" alt="点击更换图片"/><br>
            <input type="text"  class="kaptcha" name="kaptcha" id="kaptcha"  
             placeholder="输入验证码" autocomplete="off"/>                
        </div>  
        <button id="submit" type="submit" class="submitBtn">注 册</button><br>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%=path %>/web/anon/login.html">用户登录</a> &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="<%=path %>/static/view/home.html">返回主页</a>

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
	<script src="<%=basePath%>static/js/jquery.validate.js"></script>  
	<script src="<%=basePath%>static/js/userInfoValidate.js"></script>
	
  </body>
</html>
