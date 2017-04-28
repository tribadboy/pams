<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>编辑用户权限</title>
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
            <div class="control-group span30">
            <div class="control-group span8">
            	<label class="control-label">权限类型：</label>
            	<div class="controls">
              		<select  data-rules="{required:true}"  name="roleIndex" class="input-normal"> 
                		<option value="0" selected="selected">用户模块</option>
                		<option value="1">系统模块</option>
                		<option value="2">金融模块</option>
                		<option value="3">资产模块</option>
                		<option value="4">消费模块</option>
              		</select>
            	</div>
          		</div>
             	<div class="control-group span8">
            		<label class="control-label">模糊查询：</label>
            		<div class="controls">
              			<input type="text" id="name" name="name" value="" data-rules="{maxlength:30}">
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
    <hr>
    <h1 align="center" style="color:gray;font-size:25px">编辑用户相关权限：</h1>
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
    var roleIndex = $("#roleIndex").val();
    BUI.use(['bui/form','bui/grid','bui/data'],function(Form,Grid,Data){
    	//创建表单，表单中的日历，不需要单独初始化
        var form = new Form.HForm({
          srcNode : '#searchForm'
        }).render();
    	
         var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '用户名', elCls : 'center', width: 100,  sortable: false, dataIndex: 'username'},
            { title: '状态', elCls : 'center', width: 100, sortable: false, dataIndex: 'status'},
            { title: '权限', elCls : 'center', width: 100, sortable: false, dataIndex: '', renderer:function(value,obj){     
            	if(obj.flagName == "否") {
           			 return '  <span style=\"color:red;font-size:20px\" class="grid-command">&Chi;</span>';
           		} else {
           		 	return '  <span style=\"color:green;font-size:20px\" class="grid-command">&radic;</span>';
           		}     
             }},
            { title: '操作', elCls : 'center', width: 200, sortable: false, dataIndex: '', renderer:function(value,obj){     
            	if(obj.flagName == "否") {
            		 return '  <span style=\"font-size:18px\" class="grid-command btn-setYes">指定</span>';
            	} else {
            		 return '  <span style=\"font-size:18px\"class="grid-command btn-setNo">取消</span>';
            	}     
            }}
          ];

	   var store = new Store({        
            autoLoad:false,
            pageSize:8,
            url : "<%=path%>/web/authc/user/permission/searchUserPermissionInfo",
            params : { 
                nameKey : function() {
                	return encodeURIComponent($("#name").val());
                },
                roleIndex : "#roleIndex"
            },
       }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
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
        
        function setYesItem(item){
            if(null != item){
              BUI.Message.Confirm('确认赋予用户该权限么？',function(){
                $.ajax({
                  url : '<%=path%>/web/authc/user/permission/setYesForUser',
                  type: "post",
                  dataType : 'json',
                  data : "roleIndex="+roleIndex+"&targetUserId="+item.userId,
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
          
          function setNoItem(item){
              if(null != item){
                BUI.Message.Confirm('确认取消用户该权限么？',function(){
                  $.ajax({
                    url : '<%=path%>/web/authc/user/permission/setNoForUser',
                    type: "post",
                    dataType : 'json',
                    data : "roleIndex="+roleIndex+"&targetUserId="+item.userId,
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
          if(sender.hasClass('btn-setYes')){
              var record = ev.record;
              setYesItem(record);
            } else if(sender.hasClass('btn-setNo')) {
          	  var record = ev.record;
          	  setNoItem(record);
            }
        });
      });
    </script>
   </div>
<body>
</html>  