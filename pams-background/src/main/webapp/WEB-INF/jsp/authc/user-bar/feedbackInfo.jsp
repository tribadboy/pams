<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>反馈详细信息</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   	<style type="text/css">
    h3 {
    	color:black;
    	font-size:25px;
    }
   	 label {
    	color:gray;
    	font-size:18px;
    }
   
   </style>
 </head>
 <body>
      
  <div class="container">
  <h3>反馈信息</h3>
   <hr>
    <div class="detail-page">
      <br>
      <div class="detail-section">  
        <div class="row detail-row offset6">
            <label>反馈标题：<span class="detail-text">&nbsp;&nbsp;&nbsp;${title }</span></label>
        </div>
        <div class="row detail-row offset6">
            <label>用户名：<span class="detail-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${username }</span></label>
        </div>
        <div class="row detail-row offset6">
            <label>反馈日期：<span class="detail-text">&nbsp;&nbsp;&nbsp;${date }</span></label>
        </div>
        <div class="row detail-row offset6">
            <label>状态：<span class="detail-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${status }</span></label>
        </div>
        <div class="row detail-row offset6">
            <label>反馈类型：<span class="detail-text">&nbsp;&nbsp;&nbsp;${feedtypeName }</span></label>
        </div>
       <div class="row detail-row offset6">
            <label>反馈内容：<span class="detail-text">&nbsp;&nbsp;&nbsp;${message }</span></label>
        </div>
    </div>
    </div>
    </div>
  <hr><hr><br><br>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>

<body>
</html>  