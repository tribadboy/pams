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
  <div class="row">
     <form id="J_Form" class="form-horizontal span24" 
      		action="<%=path %>/web/authc/consumption/data/getAllDataOfTime"  method="POST" >
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">消费类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  id="consumptionId" name="consumptionId" class="input-normal"> 
                <option value="0">全部</option>
                <option value="1">饮食消费</option>
                <option value="2">服装消费</option>
                <option value="3">住房消费</option>
                <option value="4">交通消费</option>
                <option value="5">电话消费</option>
                <option value="6">日用品消费</option>
                <option value="7">书籍消费</option>
                <option value="8">旅行消费</option>
                <option value="9">生活消费(水电煤)</option>
                <option value="10">其他消费</option>
              </select>
            </div>
          </div>
          <button type="submit" class="button button-primary">查询</button>
        </div>    
      </form>
      </div>
      <hr>
    <h1 align="center" style="color:gray;font-size:25px">平台数据与用户数据的时间分布--区域对比图</h1>
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
  		var selectIndex = ${selectIndex};
		window.onload = function addOption(){
			document.getElementById("consumptionId")[selectIndex].selected=true;
		}
		
  		var year = ${year};
  		var month = ${month};
  		var day = ${day};
  		var avgData = ${avgData};
  		var userData = ${userData};
  		
        var chart = new AChart({
          theme : AChart.Theme.Base,
          id : 'canvas',
          width : 950,
          height : 500,
          plotCfg : {
            margin : [50,50,80] //画板的边距
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
          xTickCounts : [1,8],//设置x轴tick最小数目和最大数目 
          rangeSelectorOption: {
              //指定最小选择范围
              minZoomInterval: 86400000 * 100
          },
          seriesOptions : { //设置多个序列共同的属性
            areaCfg : { //如果数据序列未指定类型，则默认为指定了xxCfg的类型，否则都默认是line
              markers : {
                single : true
              },
              pointInterval: 24 * 3600 * 1000,
              //pointStart: Date.UTC(2006, 0, 1),    
              pointStart: Date.UTC(year, month, day), 
            }
          },
          yAxis : {
            
            formatter : function  (value) {
              return value;
            }
          },
          tooltip : {
            valueSuffix : '元',
            shared : true //共享栅格
          },
          series : [{ //中间存在断点，断点不做处理，不进行忽略
            name: '平台数据平均值',
            connectNulls : true, //此配置项会将null的数据忽略掉,平台数据保持平滑
            /* data: [null, null, null, null, null, 6 , 11, 32, 110, 235, 369, 640,1005, 1436,
                    2063, 3057, 4618, 6444, 9822, 15468, 20434, 24126,27387, 29459, 31056, 31982,
                    32040, 31233, 29224, 27342, 26662,26956, 27912, 28999, 28965, 27826, 25579,
                    25722, 24826, 24605,24304, null, null, 24099, 24357, 24237, 24401, 24344,
                    23586,22380, 21004, 17287, 14747, 13076, 12555, 12144, 11009, 10950,
                    10871, 10824, 10577, 10527, 10475, 10421, 10358, 10295, 10104
                ] */
            data : avgData
            }, {
                name: '用户',
                /* data: [null, null, null, null, null, null, null , null , null ,null,5, 25, 50,
                  120, 150, 200, 426, 660, 869, 1060, 1605, 2471, 3322,4238, 5221, 6129,
                  7089, 8339, 9399, 10538, 11643, 13092, 14478,15915, 17385, 19055, 21205,
                  23044, 25393, 27935, 30062, null,33952, 35804, 37431, 39197, 45000, 43000,
                  41000, 39000, 37000,35000, 33000, 31000, 29000, 27000, 25000, 24000, 23000,
                  22000,21000, 20000, 19000, 18000, 18000, 17000, 16000
                ] */
                data : userData
            }]
 
        });
 
        chart.render();
      </script>
   </div>
<body>
</html>  