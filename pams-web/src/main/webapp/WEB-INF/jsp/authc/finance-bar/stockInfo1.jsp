<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>历史数据</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
  <div class="container">
    <div class="detail-page">   
 		<form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
 		<button type="submit" class="button button-primary" id="btnSearch" style="display:none;">刷新</button>
 		</form>
    </div>
    <h1 align="center" style="color:gray;font-size:25px">股票数据详情</h1>
    <h5 align="right" style="color:gray;font-size:12px">${symbolName } &nbsp;&nbsp;${symbolCode }&nbsp;&nbsp;  ${symbolTypeName }</h5>
    <div class="doc-content">
    <ul class="nav-tabs">
      <li class="active"><a href="#" style="font-size:15px">趋势图</a></li>
      <li><a href="<%=path%>/web/authc/finance/stockData/getStockInfo2?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">k线图</a></li>
      <li><a href="<%=path%>/web/authc/finance/stockData/getStockInfo3?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">列表展示</a></li>
    </ul>
  </div>  
  	<div class="detail-section">
    	<div id="canvas" style="width : 950px;height : 400px;">

    	</div>
	</div>
    
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  	<script type="text/javascript" src="<%=basePath%>static/view/assets/js/acharts-min.js"></script>
	<script type="text/javascript" src="<%=basePath%>static/view/assets/js/astock-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>
    <script type="text/javascript">
    
   /*   var data = [
                120.71,
                null,
                155.59,
                100.48,
                198.45,
                127.39,
                null,
                199.91,
                118.34,
                174.12,
                159.27
            ];   */ 
    var data = ${data};
    var year = ${year};			//2006
    var month = ${month};		//0
    var day = ${day};			//1
    var stock = new AStock({ 
        theme : AChart.Theme.Base,
        id : 'canvas',
        width : 950,
        height : 400,
        plotCfg : {
            margin : [60,50,60,50] //画板的边距
        },
        xAxis : {//格式化时间
            type : 'time' ,
            formatter : function(value)   {
                return Chart.Date.format(new Date(value),'yyyy-mm-dd');
            },
            labels : {
              label : {
                'font-size': '10'
              }
            },
            animate : false
        },
        yAxis : {
            animate : false
        },
        xTickCounts : [1,8],//设置x轴tick最小数目和最大数目 
        rangeSelectorOption: {
            //指定最小选择范围
            minZoomInterval: 86400000 * 100
        },
        seriesOptions : { //设置多个序列共同的属性
        	areaCfg : { //如果数据序列未指定类型，则默认为指定了xxCfg的类型，否则都默认是line
                markers : {
                    single: true
                },
                animate : false
            }
        }, 
        legend: null,
        tooltip : {
            valueSuffix : '￥',
        },
        series : [{
        	name: '股票',
        	type : 'area',
        	line : {
                'stroke-width' : 1,
                'stroke' : 'rgb(47,126,216)'
            },
            lineActived : {
                'stroke-width' : 1
            },
            area : {
              fill : '90-#fff-rgb(47,126,216)',
              stroke : 'none'
            },
            pointInterval: 24 * 3600 * 1000,
           // pointStart: Date.UTC(2006, 0, 1),    
           pointStart: Date.UTC(year, month, day),    
            suffix: '元',
            data: data
        }]
    });
    stock.render();
  
  </script>
   </div>
<body>
</html>  