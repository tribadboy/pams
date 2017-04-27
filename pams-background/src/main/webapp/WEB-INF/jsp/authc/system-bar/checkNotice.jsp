<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询公告</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
  <div class="container">  
  <h2>当前公告板内容：</h2>
   <div class="row" align="center">
        <div class="tips tips-large tips-success" align="center">
          <span class="x-icon x-icon-success">!</span>
          <div class="tips-content">
            <h2 style="font-size:20px">公告</h2>
            <p class="auxiliary-text" align="center" style="font-size:15px">
              ${noticeMessage }
            </p>
          </div>
        </div>
    </div>
    <hr><br>
    <div class="row">
      <div class="span24">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
              <div class="control-group span30">
            		<input type="radio" value="0" name="type" checked> <label>所有公告</label>
            		<input type="radio" value="1" name="type"> <label>当前未结束的公告</label>
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
            { title: '记录时间',width: 100, sortable: true,  dataIndex: 'recordDate'},
            { title: '当前状态',width: 100, sortable: false,  dataIndex: 'statusName'},
            { title: '公告内容',width: 300,  sortable: false, dataIndex: 'message'},
            { title: '操作', width: 100, sortable: false, dataIndex: '',renderer:function(value,obj){         
            	if(obj.statusName == "进行中") {
              		return '  <span class="grid-command btn-del">删除公告</span>'
              		+ '  <span class="grid-command btn-fin">设置为已结束</span>';
            	} else {
            		return '  <span class="grid-command btn-del">删除公告</span>';
            	}
            }}
          ];
         
         
       var store = new Store({
           url : 'searchNoticeInfo',
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
     
        function delItem(item){
          if(null != item){
            BUI.Message.Confirm('确认要删除该条公告么？',function(){
              $.ajax({
                url : '<%=path%>/web/authc/system/notice/deleteNoticeInfo',
                type: "post",
                dataType : 'json',
                data : "noticeId="+item.noticeId,
                success : function(data){
                  if(data.status == 0){ 
                	  BUI.Message.Alert('删除成功！');
                	  top.topManager.reloadPage();
                  }else{ 
                    BUI.Message.Alert('删除失败！');
                  }
                }
            });
            },'question');
          }
        }
        
        function finItem(item){
            if(null != item){
              BUI.Message.Confirm('确认要将该条公告设置成已结束么？',function(){
                $.ajax({
                  url : '<%=path%>/web/authc/system/notice/finishNoticeInfo',
                  type: "post",
                  dataType : 'json',
                  data : "noticeId="+item.noticeId,
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('设置成功！');
                  	  top.topManager.reloadPage();
                    }else{ 
                      BUI.Message.Alert('设置失败！');
                    }
                  }
              });
              },'question');
            }
          }
     
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItem(record);
          } else if(sender.hasClass('btn-fin')) {
        	  var record = ev.record;
        	  finItem(record);
          }
        });
      });

    </script>
  </div>
  </div>
</body>
</html>  