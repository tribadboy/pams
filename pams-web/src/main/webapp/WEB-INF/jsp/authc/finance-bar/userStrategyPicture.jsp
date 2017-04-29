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
      <h1 align="center" style="font-size:30px;color:silvery">用户投资策略概要</h1>
     <hr>
    <div class="container">
    <h2 align="left" style="font-size:20px;color:gray">已结束的投资策略:</h2><br>
	<h2 align="left" style="font-size:15px;color:gray">
		<label>短期投资：${shortNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${shortPercent }% </label>
		&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label>中期投资：${mediumNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${mediumPercent }% </label>
		&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<label>长期投资：${longNum }笔   &nbsp;&nbsp;&nbsp;&nbsp;  平均综合收益率： ${longPercent }s% </label>
		<br>
	</h2>
    <br><hr>
    <h2 align="left" style="font-size:20px;color:gray">用户策略气泡图:</h2>
		<div class="detail-section">
    		<div id="canvas">
 
    		</div>
		</div>
    </div>
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
            }
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
          series: [{
            name : '短期策略(10-15天)',
            data:
            	/* [[1147651200000,7.00,5 * 30],
              [1147737600000,8.13,4 * 30]] */
              shortData
        },
        {
            name : '中期策略(30-40天)',
            data: 
            	/* [[1147996800000,3.13,3 * 30],
              [1148256000000,-3.56,5 * 30],
              [1148342400000,9.34,4 * 30]] */
              mediumData
        },{
            name : '长期策略(80-100天)',
            data: 
            	/* [[1148515200000,2.17,6 * 30],
              [1148601600000,7.84,2 * 30]] */
              longData
        }]
        });
 
        chart.render();
  </script>
<body>
</html>  