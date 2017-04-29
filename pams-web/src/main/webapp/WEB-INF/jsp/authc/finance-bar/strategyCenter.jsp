<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>策略中心</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    .redText {
      color: red;
    }
   </style>
 </head>
 <body>
  <div class="container">  
  <h2>投资策略中心：</h2> 
    <hr><br>
    <div class="row">
      <div class="span24">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 策略类型：</label>
           <div class="controls">
              <select  data-rules="{required:true}"  name="strategyType" class="input-normal"> 
                <option value="0" selected="selected">全部策略</option>
                <option value="1">短期策略(10-15天)</option>
                <option value="2">中期策略(30-40天)</option>
                <option value="3">长期策略(80-100天)</option>
              </select>
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 策略状态：</label>
           <div class="controls">
              <select  data-rules="{required:true}"  name="status" class="input-normal"> 
                <option value="0" selected="selected">不限</option>
                <option value="1">未确认</option>
                <option value="2">未开始</option>
                <option value="3">进行中</option>
                <option value="4">收尾中</option>
                <option value="5">已结束</option>
              </select>
            </div>
          </div>
               <div class="control-group span6">
               <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
          </div>                
        </form>      
      </div>
    </div> 
    <div class="search-grid-container">
      <div id="grid">
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
            { title: '投资时间',width: 100, sortable: true,  dataIndex: 'startDate'},
            { title: '策略类型',width: 200, sortable: false,  dataIndex: 'strategyType'},
            { title: '所属用户(点击查看用户策略空间)', width: 150, sortable: false, dataIndex: '',renderer:function(value,obj){  
            	var str =    ' <span class="grid-command btn-user">'+obj.targetUsername +'</span>';
            	return str;
            }},
            { title: '策略状态',width: 100, sortable: false,  dataIndex: 'statusName'},        
            { title: '综合收益率',width: 80, sortable: false,  dataIndex: 'avgProfit'},
            { title: '操作', width: 150, sortable: false, dataIndex: '',renderer:function(value,obj){  
            	var str =  '  <span class="grid-command btn-info">查看投资组合</span>';
            	return str;
            }}
          ];
         
         
       var store = new Store({
           url : 'searchUserStrategyInfo',
           autoLoad:false,
           params : { 
        	   strategyType : '#strategyType',
        	   status : '#status'
             },
            pageSize:20
          }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.RowNumber,Grid.Plugins.AutoFit],  // 插件形式引入自适应宽度
            // 顶部工具栏
            tbar:{             
            },
            // 底部工具栏
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
        		    id : 'strategyInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/finance/strategy/getStrategyInfo?strategyId='
        		    		+record.strategyId,
        		 	title : '投资策略详情'
        		  });
        		}
          } else if(sender.hasClass('btn-user')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  top.topManager.openPage({
        		    id : 'strategyUserInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/finance/strategy/getUserStrategyPicture?targetUserId='
        		    		+record.targetUserId+"&targetUsername="+record.targetUsername,
        		 	title : '用户投资策略气泡图'
        		  });
        		}
          }
        });
      });

    </script>
  </div>
  </div>
</body>
</html>  