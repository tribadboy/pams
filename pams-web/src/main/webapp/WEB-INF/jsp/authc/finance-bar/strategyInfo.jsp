<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>策略详情</title>
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
    	font-size:20px;
    }
   
   </style>
 </head>
 <body>
      
  <div class="container">
  <div class="detail-page">
  <hr>
  <h3>策略详情：</h3>
  </div>
    <div class="detail-page">
      <br>
      <div class="detail-section">  
        <div class="row detail-row" align="center">
            <label>策略名称：<span class="detail-text">${strategyName }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>策略状态：<span class="detail-text">${statusName }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>策略类型：<span class="detail-text">${strategyType }</span></label>
        </div>
          <div class="row detail-row" align="center">
            <label>投资起始日期：<span class="detail-text">${startDate }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>综合收益率：<span class="detail-text">${avgProfit }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>策略备注：<span class="detail-text">${message }</span></label>
        </div>
      </div>
      <hr>
      <div class="detail-section">
        <h3>投资的股票组合：</h3> 
        <div class="row detail-row">
          <div class="span24">
            <div id="grid"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <hr><hr><br><br>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<script type="text/javascript">
  BUI.use('bui/grid',function (Grid) {
	  
       var data = ${data} ;
 		console.log(data);
        grid = new Grid.Grid({
          render : '#grid', //显示Grid到此处
          width : 950,      //设置宽度
          forceFit : true,         
          columns : [
            {title:'股票代码', sortable: false, elCls : 'center', dataIndex:'symbolCode',width:80},
            {title:'股票类型', sortable: false, elCls : 'center', dataIndex:'symbolType',width:100},
            {title:'投资比例（%）',sortable: false,  elCls : 'center', dataIndex:'percent',width:100}
          ],
          plugins : [Grid.Plugins.RowNumber,Grid.Plugins.AutoFit]  // 插件形式引入自适应宽度
        });
      grid.render();
      grid.showData(data);
  });
</script>

<body>
</html>  