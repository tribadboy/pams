<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>贷入与贷出</title>
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
  	<div class="row">
  		<div class="control-group span8">
            <label class="control-label">贷款类型：${direction }</label>
         </div>
         <div class="control-group span8">
            <label class="control-label">预期还款数额：${exceptRepayAmount }</label>
         </div>
         <div class="control-group span8">
            <label class="control-label">累计还款数额：${realRepayAmount }</label>
         </div>
         <div class="control-group span8">
            <label class="control-label">状态：${statusName }</label>
         </div>
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
            <input type=text style="display:none" name="loanId" id="loanId" value="${loanId }">
         	<div class="control-group span5">
          		<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
          		<button type="submit" class="button button-primary" >搜索所有记录</button>
          	</div>  
        </form>    
  	</div>  
    <hr>
    <form id="J_Form" class="form-horizontal" action="<%=path %>/web/authc/asset/loan/addLoanChange"  method="POST">
    <div class="row">      
    <input type=text style="display:none" name="loanId2" id="loanId2" value="${loanId }">
          <div class="control-group span10 ">
            <label class="control-label"><span class="redText">*</span> 记录日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="changeTime" class="calendar"   data-rules="{required:true}"  type="text">
            </div>
          </div>
          <div class="control-group span15">
            <label class="control-label"><span class="redText">*</span> 还款金额：</label>
            <div class="controls">
              <input name="changeAmount" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span5">
          	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
          	<button type="submit" class="button button-primary" >创建还款记录</button>
          </div>      
      </div>   
    </form> 
    <br>
    <h1><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><span class="redText">${msg }</span></h1>
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
			 document .getElementById("btnSearch").click();
		}
      
    BUI.use(['bui/form','bui/grid','bui/data'],function(Form,Grid,Data){
    	//创建表单，表单中的日历，不需要单独初始化
        var form = new Form.HForm({
          srcNode : '#searchForm'
        }).render();
    	
       var form2 = new Form.HForm({
            srcNode : '#J_Form'
          }).render(); 
    	
         var Grid = BUI.Grid,
          Store = BUI.Data.Store,
          columns = [
			{ title: '类别',width: 100,  sortable: false, dataIndex: 'changeTypeName'},
            { title: '数额', width: 100, sortable: false, dataIndex: 'changeAmount'},
            { title: '时间',width: 10, sortable: false,  dataIndex: 'changeTime'},
            { title: '操作', width: 100, sortable: false, dataIndex: '',renderer:function(value,obj){         
                return '<span class="grid-command btn-del">删除</span>';
            }}
          ];
         
       var store = new Store({
           url : 'searchLoanChangeInfo',
           autoLoad:false,
            params : { 
                loanId : '#loanId'
              },
            pageSize:10
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
        
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItems([record]);
          }
        });
        
        function delItems(items){
            var ids = [];
            BUI.each(items,function(item){
          	  ids.push(item.changeId);
            });
            if(ids.length > 0){
              BUI.Message.Confirm('确认要删除记录么？',function(){
                $.ajax({
                  url : '/pams-web/web/authc/asset/loan/deleteLoanChange',
                  type: "post",
                  dataType : 'json',
                  data : "changeIds="+ids,
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
      });

    </script>
  </div>
  </div>
</body>
</html>  