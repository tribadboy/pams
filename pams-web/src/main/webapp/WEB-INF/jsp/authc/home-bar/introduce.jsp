<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>系统介绍</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   <style type="text/css">
   	 h1 {
    	color:black;
    	font-size:30px;
    }
    p {
      	margin:10px 0;
    	padding:10px 0;
    	//height:90px;
    	line-height:30px;
    	color:gray;
    	font-size:16px;
    	text-indent: 2em;
    }
   </style>
 </head>
 <body>
      
  <div class="container">
    <div class="detail-page">
      <h1>系统背景</h1>
      <div class="detail-section">  
        <p>  如今，随着经济水平的发展，人们进入了一个消费和理财的时代。2016年万众期待的“双十一”刚过，其交易额达到了惊人的一千两百多亿，而消费欲的提高体现了民众资产和收入的增加。互联网的发展也进一步促进了人们开始投资于互联网金融服务之中，如消费、信贷、资产、金融这些概念都与互联网交织在一起。</p>
		<p>  本项目针对互联网+环境下的个人理财需求，设计并实现一款可以帮助人们进行消费与资产管理的系统。该系统不仅涵盖了生活中所需的消费分类与统计功能，还囊括了收支和信贷的计算功能，以及资产变动、理财分析与评估等一些金融服务功能。</p>
      </div>
      <br>
      <h1>技术框架</h1>
      <div class="detail-section"> 
		<p>项目英文名称：  Personal Asset Management System – PAMS </p>
		<p>数据库名称：    Mysql 5.7</p>
		<p>开发技术：		 J2EE、Spring MVC、Maven、Git</p>
      </div> 
     
    </div>
  </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<body>
</html>  