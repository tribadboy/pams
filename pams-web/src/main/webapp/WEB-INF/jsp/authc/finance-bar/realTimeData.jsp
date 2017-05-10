<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>实时数据</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
  <div class="container">
    <h1 align="center" style="color:gray;font-size:25px">最新股票数据</h1>
    <div class="detail-page">
    	<div class="search-grid-container">
      		<div id="grid">
    		</div>
  		</div>
  	</div>
  	<hr>
  	<hr>
  	<hr>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>
    <script type="text/javascript">
          BUI.use(['bui/grid','bui/data'],function(Grid,Data){
            var Grid = Grid,
          Store = Data.Store,
          columns = [
            {title : '股票代码', elCls : 'center', dataIndex :'symbolCode', sortable: false, width:100},
            {title : '股票名称', elCls : 'center', dataIndex :'symbolName', sortable: false,width:100},
            {title : '最新时间', elCls : 'center', dataIndex :'originTime', sortable: false,width:200},
            {title : '趋势', elCls : 'center', dataIndex :'arrow', sortable: false,width:50},
            {title : '开盘价', elCls : 'center', dataIndex :'open', sortable: true,width:100},
            {title : '目前最高价', elCls : 'center', dataIndex :'high', sortable: true,width:100},
            {title : '目前最低价', elCls : 'center', dataIndex :'low', sortable: true,width:100},
            {title : '当前价格', elCls : 'center', dataIndex :'price', sortable: true,width:100},
            {title : '日成交量', elCls : 'center', dataIndex :'volume', sortable: true,width:150},
            {title : '涨跌(¥)', elCls : 'center', dataIndex :'updown', sortable: true,width:100},
            {title : '涨跌幅 (%)', elCls : 'center', dataIndex :'percent', sortable: true,width:200,renderer:function(value,obj){     
            	if(obj.percent > 0) {
          			 return '  <span style=\"color:red;font-size:15px\" class="grid-command">'+obj.percent+'</span>';
          		} else if(obj.percent < 0) {
          		 	return '  <span style=\"color:green;font-size:15px\" class="grid-command">'+obj.percent+'</span>';
          		} else {
          			return '  <span style=\"color:black;font-size:15px\" class="grid-command">'+obj.percent+'</span>';
          		}    }},
            {title : '状态', elCls : 'center', dataIndex :'statusName', sortable: true,width:150}
          ],
          data = ${dataList};
 
        var store = new Store({
            data : data,
            autoLoad:true
          }),
          grid = new Grid.Grid({
            render:'#grid',
            columns : columns,
            forceFit : true,
            store: store,
            plugins : [Grid.Plugins.RowNumber,Grid.Plugins.AutoFit]  // 插件形式引入自适应宽度
          });
 
        grid.render();
      });
    </script>
   </div>
<body>
</html>  