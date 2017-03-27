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
    <title>user information page</title>


    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- 其他css文件下载下面 -->   
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>这是用户信息显示页面</h1>
    
	<form action="<%=path %>/web/user/update"  method="POST" id="updateUserInfoForm">
        <div>
        	<label>用户名</label>  
            <input type="text" name="username" class="username" id="username"
            value="${pamsUser.username}" readonly/>  
        </div> 
        <div>  
        	<label>密码</label>
            <input type="password" name="password" class="password" id="password"
            value="${pamsUser.password}" oncontextmenu="return false"/>  
        </div>  
        <div>  
        	<label>密码验证</label>
            <input type="password" name="confirm_password" class="confirm_password" 
            value="${pamsUser.password}" oncontextmenu="return false"/>  
        </div>  
        <div>  
        	<label>电话号码</label>
            <input type="text" name="phone" class="phone" id="phone"
             value="${pamsUser.phone}" autocomplete="off"/>  
        </div>  
        <div>  
        	<label>电子邮箱</label>
            <input type="text" name="email" class="email" id="email"
            value="${pamsUser.mail}" oncontextmenu="return false"/>  
        </div>  
        <button id="submit" type="submit">修改信息</button>  
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
	<script src="<%=basePath%>static/js/userInfoValidate.js"></script>
	
  </body>
</html>
