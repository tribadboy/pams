<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>修改信息</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      <form action="<%=path %>/web/user/update"  method="POST" id="updateUserInfoForm">
        <div>
        	<label>用户名</label>  
            <input type="text" name="username" class="username" id="username"
            value="${user.username}" readonly/>  
        </div> 
        <div>  
        	<label>密码</label>
            <input type="password" name="password" class="password" id="password"
            value="${user.password}" oncontextmenu="return false"/>  
        </div>  
        <div>  
        	<label>密码验证</label>
            <input type="password" name="confirm_password" class="confirm_password" 
            value="${user.password}" oncontextmenu="return false"/>  
        </div>  
        <div>  
        	<label>电话号码</label>
            <input type="text" name="phone" class="phone" id="phone"
             value="${user.phone}" autocomplete="off"/>  
        </div>  
        <div>  
        	<label>电子邮箱</label>
            <input type="text" name="email" class="email" id="email"
            value="${user.mail}" oncontextmenu="return false"/>  
        </div>  
        <button id="submit" type="submit">修改信息</button>  
    </form>  
       
	<!-- 从model中获取错误信息 -->
	<P>${message}</P>
      
 
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>
  	<script> 
   		var path = "<%=path%>"; 
   		var basePath = "<%=basePath%>";
	</script>
	<script src="<%=basePath%>static/js/jquery.validate.js"></script>  
	<script src="<%=basePath%>static/js/userInfoValidate.js"></script>

<body>
</html>  