<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>分类占比</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
  <div class="container">
  	<h1 align="center" style="color:gray;font-size:25px">平台数据与用户数据的分类占比--雷达对比图</h1>
    <div class="detail-section">
    <div id="canvas">
 
    </div>
</div>
    
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  	<script type="text/javascript" src="<%=basePath%>static/view/assets/js/acharts-min.js"></script>

    <script type="text/javascript">
    	var nameList = ${nameList};
    	var userDataList = ${userDataList};
    	var avgDataList = ${avgDataList};
    
        var chart = new AChart({
          theme : AChart.Theme.Base,
          id : 'canvas',
          width : 950,
          height : 500,
          plotCfg : {
            margin : [50,50,100]
 
          },
          xAxis : {
            type : 'circle',
            //tickInterval : 45,
            //ticks : ['a','b','c','d','e','f','g','h'],
            ticks : nameList,
            line : null //去除最外层的圆
          },
          yAxis : {
            title : '消费占比',
            type : 'radius',
            grid : {
              type : 'polygon' //圆形栅格，可以改成circle
            },
            labels : {
              label : {
                x : -12
              }
            },
 
            min : 0
          },
         tooltip : {
            shared : true //共享栅格
          },
          /*
          seriesOptions : {
              areaCfg : {
                stackType : 'normal'
              }
            }, 
            */
          series: [
            {
                name : '平台数据平均 (%)',
                type: 'area',
               // data: [44.56, 70.12,23.14, 54.32, 41.33, 32.54, 22.56, 10.32]
            	data : avgDataList
            },
            {
            	name: '用户数据 (%)',
                type: 'area',               
               // data: [12.23, 23.42, 35.32, 42.45, 55.21, 16.44, 37.56, 68.11]
            	data : userDataList
            }
            ]
        });
 
        chart.render();
      </script>
 
   </div>
<body>
</html>  