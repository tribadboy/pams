<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    int countOfInform = (Integer)request.getAttribute("countOfInform");
%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
 <head>
  <title>通知与公告</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
    <div class="container">
    <div class="row" align="center">
        <div class="tips tips-large tips-success" align="center">
          <span class="x-icon x-icon-success">!</span>
          <div class="tips-content">
            <h2 style="font-size:20px">公告</h2>
            <p class="auxiliary-text" align="center" style="font-size:15px">
              ${noticeMessage }
            </p>
            <p align="right">
              <a class="page-action" data-type="setTitle" title="帮助与反馈" href="<%=path%>/web/authc/home/about/help">帮助与反馈</a>
            </p>
          </div>
        </div>
    </div>
    <br>
    <br>
    <hr>
    <br>
    <br>
    <div class="row" align="center">
    <div class="span60 offset">
    	<h2 style="font-size:20px" align="left">通知:</h2>
    </div>
     <div class="span60 offset2">
          <div class="tips-content">  
          <c:forEach var="item" items="${informList}">    
            <p class="auxiliary-text" align="left" style="font-size:15px">
              <label>${item.recordDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.message}</label>
            </p><br><br>
          </c:forEach>
          </div>
      </div>
    </div>
    <br>
    <br>
    
    
  </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<body>
</html>  