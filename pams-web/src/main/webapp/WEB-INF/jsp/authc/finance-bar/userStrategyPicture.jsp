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
  <title>用户投资策略概要</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
 	<br><br>
      <h1 align="center" style="font-size:30px;color:silvery">用户投资策略历史概要</h1>
     <hr>
    <div class="container">
    <h2 align="left" style="font-size:20px;color:gray">已结束的投资策略:</h2><br>
	<h2 align="left" style="font-size:15px;color:gray">
		<label>短期投资：${shortNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${shortPercent }% </label>
		&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label>中期投资：${mediumNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${mediumPercent }% </label>
		&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label>长期投资：${longNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${longPercent }% </label>
		<br>
	</h2>
    <br><hr>
    <h2 align="left" style="font-size:20px;color:gray">用户策略气泡图:</h2>
		<div class="detail-section">
    		<div id="canvas">
 
    		</div>
		</div>
    </div>
    <h6>&nbsp;&nbsp;&nbsp;&nbsp;注：气泡大小表示投资策略中股票选取的数量</h6>
    <br>
    <br>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/acharts-min.js"></script>
  <script type="text/javascript">
  
  		var shortData = ${shortData};
  		var mediumData = ${mediumData};
  		var longData = ${longData};
  		console.log(shortData);
        var chart = new AChart({
          theme : AChart.Theme.Base,
          id : 'canvas',
          width : 950,
          height : 500,
          plotCfg : {
            margin : [50,50,100]
          },
          xAxis : {
            type : 'time',
            formatter : function(value)   {
              return AChart.Date.format(new Date(value),'yyyy-mm-dd');
            },
            min : '2013-01-01'  
          },
          yAxis : {
            title : {
              text : '综合收益率 %'
            },
          //  min : -100
          },  
          tooltip : {
            shared : true,
            valueSuffix : '%',
          },
          seriesOptions : {
              bubbleCfg : {
                
              }
               
          },
          series: [
         	{
            name : '短期策略(10-15天)',
            data:
            	// [[1147651200000,7.00,5 * 30],
              //[1147737600000,8.13,4 * 30]] ,
              shortData,
              circle : {
                  fill : 'r(0.4, 0.3)rgba(255,255,255,0.5)-rgba(50,60,210,0.5)'
              }
        	},         	{
            name : '中期策略(30-40天)',
            data:  mediumData,
              circle : {
                  fill : 'r(0.4, 0.3)rgba(255,255,255,0.5)-rgba(40,40,50,0.5)'
              }
        	},
        	{
            name : '长期策略(80-100天)',
            data: longData,
            circle : {
                fill : 'r(0.4, 0.3)rgba(255,255,255,0.5)-rgba(100,190,70,0.5)'
            }
        	} ,       	
        ]
          
        });
        chart.render();
  </script>
<body>
</html>  