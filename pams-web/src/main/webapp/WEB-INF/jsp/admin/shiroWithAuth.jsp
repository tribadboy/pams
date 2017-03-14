<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>hello</title>
    <c:catch var="importError0">
        <c:import url="../common/base.jsp" charEncoding="utf-8"></c:import>
    </c:catch>
    <c:out value="${importError0}"></c:out>
</head>
<body>

欢迎${user.userName}登录

你有权限访问该页面

<a href="<c:url value='/shiro/logout.html'/>"><button>退出登录</button></a>

<shiro:hasRole name="admin">
    <a href="user/show">查看用户</a><br>
    <a href="user/create">创建用户</a>
</shiro:hasRole>

<shiro:hasRole name="user">
   <a href="user/show">查看用户</a>
</shiro:hasRole>

</body>
</html>