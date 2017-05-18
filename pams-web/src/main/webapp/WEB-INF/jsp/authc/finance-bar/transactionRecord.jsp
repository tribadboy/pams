<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>交易记录</title>
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
    <h1 align="center" style="color:gray;font-size:25px">交易记录列表</h1>
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
            { title: '交易日期',width: 250,   elCls : 'center', sortable: false, dataIndex: 'tradeTime'},
            { title: '交易类型', width: 100,  elCls : 'center', sortable: false, dataIndex: 'changeTypeName'},
            { title: '股票代码',width: 100,   elCls : 'center', sortable: false, dataIndex: 'symbolCode'},
            { title: '股票类型',width: 100,   elCls : 'center', sortable: false, dataIndex: 'symbolTypeName'},
            { title: '股票名称',width: 150,   elCls : 'center', sortable: false, dataIndex: 'symbolName'},
            { title: '每股单价',width: 100,   elCls : 'center', sortable: false, dataIndex: 'price'},
            { title: '交易数量',width: 100,   elCls : 'center', sortable: false, dataIndex: 'quantity'},
            { title: '手续费',width: 100,   elCls : 'center', sortable: false, dataIndex: 'fee'},
            { title: '总价值',width: 200,   elCls : 'center', sortable: false, dataIndex: 'total'},
            { title: '操作', width: 180,  elCls : 'center', sortable: false, dataIndex: '',renderer:function(value,obj){  
            	if(obj.flag == true) {
              		return '  <span class="grid-command btn-del">撤销该条记录</span>'
            	}
              ;
            }}
          ];
         
       var store = new Store({
           url : 'searchTransactionRecordInfo',
           autoLoad:false,
            pageSize:8
          }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            //plugins : [Grid.Plugins.CheckSelection,Grid.Plugins.AutoFit], //勾选插件、自适应宽度插件
            //plugins : [BUI.Grid.Plugins.CheckSelection], // 插件形式引入多选表格,
            // 顶部工具栏
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
     
        
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItem(record);
          }
        });
        
        function delItem(item){
            if(item != null){
              BUI.Message.Confirm('确认要撤销该条记录么？用户资产与持仓信息均会被修改',function(){
                $.ajax({
                  url : '/pams-web/web/authc/finance/position/deleteStockChangeInfo',
                  type: "post",
                  dataType : 'json',
                  data : "changeId="+item.changeId,
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('删除成功！'); 
                  	  top.topManager.reloadPage();
                    }else{ 
                      BUI.Message.Alert('删除失败！请检查用户操作');
                    }
                  }
              });
              },'question');
            }
          }
        
      });

    </script>
   </div>
<body>
</html>  