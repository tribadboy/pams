<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询反馈</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
  <div class="container">  
    <div class="row">
      <div class="span50">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
              <div class="control-group span30">
            		<input type="radio" value="0" name="type" checked> <label>所有反馈</label>
            		<input type="radio" value="1" name="type"> <label>尚未处理的反馈</label>
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
            { title: '反馈标题',width: 150,  elCls : 'center', sortable: false, dataIndex: 'recordTitle'},
            { title: '用户', width: 100, elCls : 'center', sortable: false, dataIndex: 'username'},
            { title: '日期', width: 100, elCls : 'center', sortable: false,  dataIndex: 'recordDate'},
            { title: '反馈类型', width: 250, elCls : 'center', sortable: false,  dataIndex: 'feedTypeName'},
            { title: '状态',width: 100,  elCls : 'center', sortable: false,  dataIndex: 'statusName'},
            { title: '操作', width: 180, sortable: false, dataIndex: '',renderer:function(value,obj){       
            	if(obj.statusName == "未查看") {
              		return '  <span class="grid-command btn-info">反馈详情</span>'
              		+ '  <span class="grid-command btn-fin">设置为已查看</span>';
            	} else {
            		return '  <span class="grid-command btn-info">反馈详情</span>';
            	}
              ;
            }}
          ];
         
       var store = new Store({
           url : 'searchFeedbackInfo',
           autoLoad:false,
            params : { 
                type : '#type'
              },
            pageSize:10
          }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.RowNumber,Grid.Plugins.AutoFit] , // 插件形式引入自适应宽度
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
     
        
        function finItem(item){
            if(null != item){
              BUI.Message.Confirm('确认要将该反馈设置为已查看么？',function(){
                $.ajax({
                  url : '/pams-background/web/authc/user/feedback/setCheckedFeedback',
                  type: "post",
                  dataType : 'json',
                  data : "backId="+item.backId,
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('设置成功！');
                  	  store.load();
                    }else{ 
                      BUI.Message.Alert('设置失败，请检查操作！');
                    }
                  }
              });
              },'question');
            }
          }
     
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-fin')){
              var record = ev.record;
              finItem(record);
          } else if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  //打开左侧菜单中配置过的页面
        		  top.topManager.openPage({
        		    id : 'feedbackInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/user/feedback/getFeedbackInfo?backId='
        		    		+record.backId,
        		 	title : '反馈详细信息'
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