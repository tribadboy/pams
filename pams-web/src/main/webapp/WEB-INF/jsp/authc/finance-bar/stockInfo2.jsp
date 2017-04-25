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
    <h1 align="center" style="color:gray;font-size:25px">股票数据详情</h1>
    <h5 align="right" style="color:gray;font-size:12px">${symbolName } &nbsp;&nbsp;${symbolCode }&nbsp;&nbsp;  ${symbolTypeName }</h5>
    <div class="doc-content">
    <ul class="nav-tabs">
      <li> <a href="<%=path%>/web/authc/finance/stockData/getStockInfo?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">趋势图</a></li>
      <li class="active"><a href="#" style="font-size:15px">k线图</a></li>
      <li><a href="<%=path%>/web/authc/finance/stockData/getStockInfo3?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">列表展示</a></li>
    </ul>
  </div>  
    <div class="detail-section">
    	<div id="canvas">
 
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
  	//数据格式依次为 ['时间',开盘','最高','最低','收盘']
	/*   var data = [
        [1318597860000,417.78,417.87,417.7,417.81],
        [1318597920000,417.785,417.82,417.7,417.75],
        [1318597980000,417.72,417.82,417.72,417.7915],
        [1318598040000,417.81,417.95,417.79,417.9288],
        [1318598100000,417.93,418.04,417.82,417.97],
        [1318598160000,417.97,418.08,417.881,418.03],
    ]; */
    	var data = ${data};
        var stock = new AStock({
            theme : AChart.Theme.Base,
            id : 'canvas',
            width : 950,
            height : 400,
            plotCfg : {
                margin : [10,50,20,70] //画板的边距
            },
            xAxis : {
                //type : 'timeCategory' ,
                type : 'time',
                formatter : function(value)   {
                    return Chart.Date.format(new Date(value),'yyyy-mm-dd');	//yyyy-MM-dd
                },
                labels : {
                  label : {
                    'font-size': '10'
                  }
                },
                animate : false
            },
            yAxis : [{
                position: 'left',
                grid : {
                    animate : false
                },
                animate : false,
                labels :{
                    label : {
                        fill : '#333',
                        'text-anchor' : 'end',
                        x: -30
                    }
                }
            }],
            rangeSelectorOption: {
                //指定最小选择范围
                minZoomInterval: 86400000 * 100
            },
            legend: null,
            xTickCounts : [1,8],//设置x轴tick最小数目和最大数目 
            tooltip : {
                valueSuffix : '￥',
                shared: true,
                crosshairs:true
            },
            series : [{
                type: 'candlestick',
                name: '股票',
                tipNames: ['开盘','最高','最低','收盘'],
                suffix: '元',
                data: data
            }]
        });
        stock.render();
  </script>
   </div>
<body>
</html>  