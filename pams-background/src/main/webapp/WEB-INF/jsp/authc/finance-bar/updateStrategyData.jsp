<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    int countOfInform = (Integer)request.getAttribute("countOfInform");
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>投资策略数据更新</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
 	<br><br>
      <h1 align="center" style="font-size:30px;color:silvery">投资策略数据更新</h1>
     <hr>
    <div class="container">
	<h2 align="left" style="font-size:20px;color:gray">
		<button id="btn">更新投资策略数据</button>
	</h2>
   
    </div>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
  $(function(){
		       $("#btn").click(function(){
		          $.ajax
		          ({
		              type:"POST",
		              url: "<%=path%>" + "/web/authc/finance/stockInfo/updateStrategyDataInfo",  
		              type : "POST",
		              dataType:"JSON",
		              async: true,
		              success:function(data)	{
		            	  if(data.status == 0) {
		            		  alert("更新历史数据成功");
		            		  top.topManager.reloadPage();
		            	  } else {
		            		  alert("更新失败");
		            	  } 	     			
		             },	  	 
		           });
		        });
		  });
  </script>
<body>
</html>  