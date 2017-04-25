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
    </div>
    <h1 align="center" style="color:gray;font-size:25px">股票数据详情</h1>
    <h5 align="right" style="color:gray;font-size:12px">${symbolName } &nbsp;&nbsp;${symbolCode }&nbsp;&nbsp;  ${symbolTypeName }</h5>
    <div class="doc-content">
    <ul class="nav-tabs">
      <li><a href="<%=path%>/web/authc/finance/stockData/getStockInfo?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">趋势图</a></li>
      <li><a href="<%=path%>/web/authc/finance/stockData/getStockInfo2?symbolCode=${symbolCode }&symbolType=${symbolType }" style="font-size:15px">k线图</a></li>
      <li class="active"><a href="#" style="font-size:15px">列表展示</a></li>
    </ul>
  </div>  
  <div class="detail-page">  
  		<br><br> 
 		<form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
 			<input type="text" style="display:none" name="symbolCode" id="symbolCode" value="${symbolCode }">
 			<input type="text" style="display:none" name="symbolType" id="symbolType" value="${symbolType }">
          <div class="row">
            <div class="control-group span20">
              <label class="control-label">日期：</label>
              <div class="controls bui-form-group" data-rules="{dateRange : true}">
                <input name="startDate" id="startDate" style="width:150px" data-tip="{text : '起始日期(包括)'}" value="${minDate }"
                	data-rules="{required:true}" data-messages="{required:'起始日期不能为空'}" class="input-small calendar" type="text">
                <label>&nbsp;-&nbsp;</label>
                <input name="endDate" id="endDate" style="width:150px" data-tip="{text : '结束日期(不包括)'}" value="${maxDate }"
                	data-rules="{required:true}" data-messages="{required:'结束日期不能为空'}" class="input-small calendar" type="text">
              </div>
               <div class="control-group span5">
               <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               </div>
            </div>          
          </div>      
        </form>        
    </div>
    <div class="detail-page">
    	<div class="search-grid-container">
      		<div id="grid">
    		</div>
  		</div>
  	</div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>
    <script type="text/javascript">
    window.onload = function addOption(){
		 //初始化后自动点击按钮
		 document.getElementById("btnSearch").click();
	}
    BUI.use(['bui/form','bui/grid','bui/data'],function(Form,Grid,Data){
    	//创建表单，表单中的日历，不需要单独初始化
        var form = new Form.HForm({
          srcNode : '#searchForm'
        }).render();
    	
         var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '日期',width: 100,  sortable: false, dataIndex: 'symbolDate'},
            { title: '开盘价', width: 100, sortable: false, dataIndex: 'open'},
            { title: '收盘价', width: 100, sortable: false, dataIndex: 'close'},
            { title: '最高价', width: 100, sortable: false, dataIndex: 'high'},
            { title: '最低价', width: 100, sortable: false, dataIndex: 'low'},
            { title: '涨幅(%)', width: 100, sortable: false, dataIndex: 'risePercent'},
            { title: '成交量', width: 100, sortable: false, dataIndex: 'volume'}
          ];
       
  	   var store = new Store({        
           autoLoad:false,
           pageSize:10,
           url : "<%=path%>/web/authc/finance/stockData/searchHistoryStockDataInfo"
      }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            tbar:{    
            	pagingBar:true
            },
            // 底部工具栏
            bbar : {          	
            }
          });
 
        grid.render();

        form.on('beforesubmit',function(ev) {
          //序列化成对象
          var obj = form.serializeToObject();
          obj.start = 0; //返回第一页
          store.load(obj);
          return false;
        }); 
     
      });

    </script>
   </div>
<body>
</html>  