<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>搜索与编辑消费记录</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
  <div class="container">  
    <div class="row">
      <div class="span24">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label">消费类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="consumptionId" id="consumptionId" class="input-normal"> 
                <option value="0">不限种类</option>
                <option value="1">饮食消费</option>
                <option value="2">服装消费</option>
                <option value="3">住房消费</option>
                <option value="4">交通消费</option>
                <option value="5">电话消费</option>
                <option value="6">日用品消费</option>
                <option value="7">书籍消费</option>
                <option value="8">旅行消费</option>
                <option value="9">生活消费(水电煤)</option>
                <option value="10">其他消费</option>
              </select>
            </div>
          </div>
        </div>
          <div class="row">
			<div class="control-group span10">
  				<label class="control-label">开销范围：</label>
  				<div class="bui-form-group controls" data-rules="{numberRange : true}" >
    				<input name="minCost" type="number" id="minCost" class="input-small" data-tip="{text : '下限额度'}" data-rules="{number:true,required:true,min:0}"/>
    				<label>&nbsp;-&nbsp;</label>
    				<input name="maxCost" type="number" id="maxCost" class="input-small" data-tip="{text : '上限额度'}" data-rules="{number:true,required:true,min:0}"/>
  				</div>
			</div>
		  </div>
          <div class="row">
            <div class="control-group span20">
              <label class="control-label">日期：</label>
              <div class="controls bui-form-group" data-rules="{dateRange : true}">
                <input name="startDate" id="startDate" style="width:150px" data-tip="{text : '起始日期(包括)'}" data-rules="{required:true}" data-messages="{required:'起始日期不能为空'}" class="input-small calendar" type="text">
                <label>&nbsp;-&nbsp;</label>
                <input name="endDate" id="endDate" style="width:150px" data-tip="{text : '结束日期(不包括)'}" data-rules="{required:true}" data-messages="{required:'结束日期不能为空'}" class="input-small calendar" type="text">
              </div>
               <div class="control-group span5">
               <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               </div>
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
            { title: '账目id',width: 100,  sortable: false, dataIndex: 'accountId'},
            { title: '用户名',width: 100,  sortable: false, dataIndex: 'username'},
            { title: '账目类别', width: 150, sortable: true, dataIndex: 'consumptionName'},
            { title: '开销', width: 100, sortable: true, dataIndex: 'cost'},
            { title: '消费时间',width: 150, sortable: true,  dataIndex: 'spendTime'},
            { title: '备注', width: 300,sortable: false,  dataIndex: 'message'},
            { title: '操作', width: 100, sortable: false, dataIndex: '',renderer:function(value,obj){         
              return '<span class="grid-command btn-del">删除</span>';
            }}
          ];
         
       var store = new Store({
           url : 'searchConsumptionInfo',
           autoLoad:true,
            params : { 
                consumptionId : '#consumptionId',
                minCost : '#minCost',
                maxCost : '#maxCost',
                startDate : '#startDate',
                endDate : '#endDate'
              },
            pageSize:10
          }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            plugins : [Grid.Plugins.CheckSelection,Grid.Plugins.AutoFit], //勾选插件、自适应宽度插件
            plugins : [BUI.Grid.Plugins.CheckSelection], // 插件形式引入多选表格,
            // 顶部工具栏
            tbar:{
             items:[{
                    btnCls : 'button button-primary button-small',
                    text:'删除',
                    handler : delFunction
                }],
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
        
        //删除操作
        function delFunction(){
          var selections = grid.getSelection();
          delItems(selections);
        }
     
        function delItems(items){
          var ids = [];
          BUI.each(items,function(item){
        	  ids.push(item.accountId);
          });
          if(ids.length > 0){
            BUI.Message.Confirm('确认要删除选中的记录么？',function(){
              $.ajax({
                url : '/pams-web/web/authc/consumption/record/deleteConsumptionInfo',
                type: "post",
                dataType : 'json',
                data : "accountIds="+ids,
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
     
        //监听事件，删除一条记录
        grid.on('cellclick',function(ev){
          var sender = $(ev.domTarget); //点击的Dom
          if(sender.hasClass('btn-del')){
            var record = ev.record;
            delItems([record]);
          }
        });
      });

    </script>
  </div>
  </div>
</body>
</html>  