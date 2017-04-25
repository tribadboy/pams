<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>搜索股票</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
  <div class="container">
    <div class="detail-page">   
 		<form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
          <div class="row">
            <div class="control-group span20">
             	<div class="control-group span8">
            		<label class="control-label">模糊查询：</label>
            		<div class="controls">
              			<!-- <input  type="text" data-rules="{maxlength:30}"  data-tip="{text:'请填写股票代码或名称！'}"
              				name="key" id="key" class="input-normal control-text" width="30px">  -->
              			<input type="text" id="name" name="name" value="">
            		</div>
          		</div>         
                <div class="control-group span5">
               		<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              		<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               	</div>
            </div>       
          </div>      
        </form>        
    </div>
    <h1 align="center" style="color:gray;font-size:25px">查询股票信息</h1>
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
		 //初始化后默认自动点击按钮
		 document.getElementById("btnSearch").click();
	}
  </script>
    <script type="text/javascript">
    BUI.use(['bui/form','bui/grid','bui/data'],function(Form,Grid,Data){
    	//创建表单，表单中的日历，不需要单独初始化
        var form = new Form.HForm({
          srcNode : '#searchForm'
        }).render();
    	
         var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '股票代码',width: 100,  sortable: false, dataIndex: 'symbolCode'},
            { title: '股票名称', width: 230, sortable: false, dataIndex: 'symbolName'},
            { title: '证券市场', width: 230, sortable: false, dataIndex: 'type'},
            { title: '当前状态', width: 230, sortable: false, dataIndex: 'status'},
            { title: '详情', width: 180, sortable: false, dataIndex: '',renderer:function(value,obj){         
              return '  <span class="grid-command btn-info">查看股票详情</span>'
              ;
            }}
          ];

	   var store = new Store({        
            autoLoad:false,
            pageSize:8,
            url : "<%=path%>/web/authc/finance/stockData/searchStockDataInfo",
            params : { 
                stockKey : function() {
                	return encodeURIComponent($("#name").val());
                }
            },
       }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            //plugins : [Grid.Plugins.CheckSelection,Grid.Plugins.AutoFit], //勾选插件、自适应宽度插件
            //plugins : [BUI.Grid.Plugins.CheckSelection], // 插件形式引入多选表格,
            tbar:{             	
            },
            bbar : {
            	pagingBar:true
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
        
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  top.topManager.openPage({
        		    id : 'stockInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/finance/stockData/getStockInfo?symbolCode='
        		    		+record.symbolCode+"&symbolType="+record.symbolType,
        		 	title : '历史数据'
        		  });
        		}
          }
        });
      });
    </script>
   </div>
<body>
</html>  