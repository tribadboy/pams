<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>hello</title>
</head>
<body>

欢迎${username}登录

<a href="<%=path %>/web/anon/logout.html"><button>退出登录</button></a>

<a href="<%=path %>/web/authc/role"><button>访问需要角色的页面</button></a>

<a href="<%=path %>/web/authc/auth"><button>访问需要权限的页面</button></a>

<a href="<%=path %>/web/authc/userInfo"><button>用户信息修改页面</button></a>

<a href="<%=path %>/web/download/excel?fileModel=test_excel"><button>下载测试</button></a>

</body>
</html>