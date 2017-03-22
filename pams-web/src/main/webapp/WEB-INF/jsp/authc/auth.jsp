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

欢迎${pamsUser.username}登录

你有权限访问该页面

<a href="<%=path %>/web/anon/logout.html"><button>退出登录</button></a>

<shiro:hasRole name="vip">
    <a href="user/create">创建用户</a>
</shiro:hasRole>

<shiro:hasRole name="normal">
   <a href="user/show">查看用户</a>
</shiro:hasRole>

</body>
</html>