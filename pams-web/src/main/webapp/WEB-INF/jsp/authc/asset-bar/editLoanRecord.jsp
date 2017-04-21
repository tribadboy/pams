<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询贷款</title>
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
            		<input type="radio" value="0" name="type" checked> <label>所有贷款</label>
            		<input type="radio" value="1" name="type"> <label>尚未结束的存款</label>
            	</div>
               <div class="control-group span">
               <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               </div>     
               
          		<div class="control-group span40">
          			<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
            		<label> 注：当需要点击“结束”按钮时，请先确定全部结束日期：</label>
            		<div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              			<input name="closeTime" id="closeTime" class="calendar"   
              				value="${todayStr }" data-rules="{}"  type="text">
            		</div> 		
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
    
    BUI.use(['bui/form','bui/grid','bui/data'],function(Form,Grid,Data){
    	//创建表单，表单中的日历，不需要单独初始化
        var form = new Form.HForm({
          srcNode : '#searchForm'
        }).render();
    	
         var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
            { title: '用户',width: 100,  sortable: false, dataIndex: 'username'},
            { title: '贷款类型', width: 100, sortable: false, dataIndex: 'direction'},
            { title: '预期还款数额', width: 80,sortable: false,  dataIndex: 'exceptRepayAmount'},
            { title: '实际累计还款数额', width: 80,sortable: false,  dataIndex: 'realRepayAmount'},
            { title: '状态',width: 60, sortable: false,  dataIndex: 'statusName'},
            { title: '备注信息',width: 200, sortable: false,  dataIndex: 'message'},
            { title: '操作', width: 180, sortable: false, dataIndex: '',renderer:function(value,obj){         
              return '<span class="grid-command btn-del">删除</span>'
              +'  <span class="grid-command btn-fin">结束</span>'
              +'  <span class="grid-command btn-info">还款详情</span>'
              ;
            }}
          ];
         
       var store = new Store({
           url : 'searchLoanRecordInfo',
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
     
        function delItems(items){
          var ids = [];
          BUI.each(items,function(item){
        	  ids.push(item.loanId);
          });
          if(ids.length > 0){
            BUI.Message.Confirm('确认要删除贷款么？',function(){
              $.ajax({
                url : '/pams-web/web/authc/asset/loan/deleteLoanRecordInfo',
                type: "post",
                dataType : 'json',
                data : "loanIds="+ids,
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
        
        function finItems(items){
            var ids = [];
            var closeTime = document.getElementById('closeTime').value;
            BUI.each(items,function(item){
          	  ids.push(item.loanId);
            });
            if(ids.length > 0){
              BUI.Message.Confirm('确认要结束贷款么？',function(){
                $.ajax({
                  url : '/pams-web/web/authc/asset/loan/closeLoanRecordInfo',
                  type: "post",
                  dataType : 'json',
                  data : "loanIds="+ids+"&closeTime="+closeTime,
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('结束成功！');
                  	  store.load();
                    }else{ 
                      BUI.Message.Alert('结束失败！请检查是否填写结束日期');
                    }
                  }
              });
              },'question');
            }
          }
     
        //监听事件
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItems([record]);
          } else if(sender.hasClass('btn-fin')){
              var record = ev.record;
              finItems([record]);
          } else if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  var loanId = record.loanId;
        	  if(top.topManager){
        		  //打开左侧菜单中配置过的页面
        		  console.log(loanId);
        		  top.topManager.openPage({
        		    //id : 'loanChange',
        		    //reload : true,
        		    //search : 'loanId='+record.loanId
        		    id : 'loanChange',
        		    href : '<%=path%>/web/authc/asset/loan/loanChange',
        		    reload : true,
        		    search : 'loanId=4',//+loanId,
        		    title : '贷入与贷出'
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