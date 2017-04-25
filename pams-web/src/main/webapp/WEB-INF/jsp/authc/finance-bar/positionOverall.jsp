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
  <title>用户持仓概要</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
 	<br><br>
      <h1 align="center" style="font-size:30px;color:silvery">用户持仓概要</h1>
     <hr>
    <div class="container">
	<h2 align="left" style="font-size:20px;color:gray">
		<label>用户累计投入：  ${investment }</label>
		<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<label>&lt;===&gt;</label>
		<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<label>当前仓位剩余金额：  ${remain }</label>
		<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<label>预期股票价值：  ${stockValue }</label>
	</h2>
    <br><hr>
    <h2 align="left" style="font-size:20px;color:gray">用户持有的股票:</h2>
    <br>
     <div class="detail-page">
    	<div class="search-grid-container">
      		<div id="grid">
    		</div>
  		</div>
  	</div>
    </div>
    <br>
    <br>
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
            {title : '股票代码',dataIndex :'symbolCode', elCls : 'center', sortable: false, width:100},
            {title : '股票名称',dataIndex :'symbolName', elCls : 'center', sortable: false,width:100},
            {title : '股票类型',dataIndex :'symbolTypeName', elCls : 'center', sortable: false,width:100},
            {title : '持有股数',dataIndex :'quantity', elCls : 'center', sortable: false,width:100},
            {title : '最新历史时间节点',dataIndex :'time', elCls : 'center', sortable: false,width:200},
            {title : '每股价值',dataIndex :'price', elCls : 'center', sortable: true,width:100},
            {title : '预期价值',dataIndex :'value', elCls : 'center', sortable: true,width:100},
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
<body>
</html>  