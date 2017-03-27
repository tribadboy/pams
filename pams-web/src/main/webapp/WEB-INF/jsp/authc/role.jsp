<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>hello</title>
</head>
<body>

欢迎${username}登录

您有角色访问该页面

<a href="<%=path %>/web/anon/logout.html"><button>退出登录</button></a>


<shiro:hasPermission name="vip:create">
     <a href="admin.jsp">创建用户</a>
</shiro:hasPermission>

</body>
</html>