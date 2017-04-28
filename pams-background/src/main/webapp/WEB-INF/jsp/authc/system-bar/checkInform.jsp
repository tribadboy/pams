<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询通知</title>
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
  <h2>通知的查询与设置：</h2> 
    <hr><br>
    <div class="row">
      <div class="span24">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 范围：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="type" class="input-normal"> 
                <option value="0" selected="selected">全部通知</option>
                <option value="1">进行中的通知</option>
              </select>
            </div>
          </div>
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 通知类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="informTypeId" class="input-normal"> 
                <option value="0" selected="selected">针对全体用户</option>
                <option value="1">针对特定用户</option>
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
            { title: '记录时间',width: 80, sortable: true,  dataIndex: 'recordDate'},
            { title: '当前状态',width: 80, sortable: false,  dataIndex: 'statusName'},
            { title: '通知类型',width: 80, sortable: false,  dataIndex: 'typeName'},
            { title: '通知内容',width: 300,  sortable: false, dataIndex: 'message'},
            { title: '操作', width: 200, sortable: false, dataIndex: '',renderer:function(value,obj){  
            	var str = '  <span class="grid-command btn-del">删除</span>';
            	if(obj.statusName == "进行中") {
            		str = str + '  <span class="grid-command btn-fin">设置为已结束</span>';
            	} 
            	if(obj.statusName == "进行中" && obj.typeName == "特定用户") {
            		str = str + '  <span class="grid-command btn-info">选择用户</span>';
            	}
            	return str;
            }}
          ];
         
         
       var store = new Store({
           url : 'searchInformInfo',
           autoLoad:false,
           params : { 
               type : '#type',
               informTypeId : 'informTypeId'
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
            BUI.Message.Confirm('确认要删除该条通知么？',function(){
              $.ajax({
                url : '<%=path%>/web/authc/system/inform/deleteInformInfo',
                type: "post",
                dataType : 'json',
                data : "informId="+item.informId,
                success : function(data){
                  if(data.status == 0){ 
                	  BUI.Message.Alert('删除成功！');
                	  store.load();
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
              BUI.Message.Confirm('确认要将该条通知设置成已结束么？',function(){
                $.ajax({
                  url : '<%=path%>/web/authc/system/inform/finishInformInfo',
                  type: "post",
                  dataType : 'json',
                  data : "informId="+item.informId,
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('设置成功！');
                  	  store.load();
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
          } else if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  top.topManager.openPage({
        		    id : 'userInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/system/inform/getUserInfo?informId='
        		    		+record.informId,
        		 	title : '编辑特定通知用户'
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