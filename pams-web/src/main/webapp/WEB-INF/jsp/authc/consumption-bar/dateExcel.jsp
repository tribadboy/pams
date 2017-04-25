<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>每日消费报表</title>
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
            <div class="control-group span20">
              <label class="control-label">日期：</label>
              <div class="controls bui-form-group" data-rules="{dateRange : true}">
                <input name="startDate" id="startDate" style="width:150px" data-tip="{text : '起始日期(包括)'}" data-rules="{required:true}" data-messages="{required:'起始日期不能为空'}" class="input-small calendar" type="text">
                <label>&nbsp;-&nbsp;</label>
                <input name="endDate" id="endDate" style="width:150px" data-tip="{text : '结束日期(不包括)'}" data-rules="{required:true}" data-messages="{required:'结束日期不能为空'}" class="input-small calendar" type="text">
              </div>
              <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              <button id="btnSearch" type="submit" class="button button-primary">搜索</button>   
            </div>       
          </div>       
        </form>  
              	
              	<button onclick="Click()">下载每日报表</button>
               		<script type="text/javascript">
						function Click(){
						    window.location.href="<%=path %>/web/download/excel?fileCode=11&startDate="
								+$("#startDate").val()+"&endDate="+$("#endDate").val();
						}
					</script> 
      </div>
    </div> 
    <div class="search-grid-container">
      <div id="grid">
    </div>
    <hr><hr>
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
            { title: '日期',width: 150, sortable: false,  dataIndex: 'spendTime'},
            { title: '账目类别', width: 150, sortable: false, dataIndex: 'consumptionName'},
            { title: '花费', width: 100, sortable: false, dataIndex: 'cost'},
            { title: '备注', width: 300,sortable: false,  dataIndex: 'message'}
          ];
         
       var store = new Store({
           url : 'searchConsumptionInfoForDateExcel',
           autoLoad:true,
            params : { 
                startDate : '#startDate',
                endDate : '#endDate'
              },
            pageSize:15
          }), 
         
          grid = new Grid.Grid({
            render:'#grid',
            loadMask: true,
            forceFit:true,
            columns : columns,
            store: store,
            tbar:{pagingBar:true},
            bbar : {}
          });
 
        grid.render();

        form.on('beforesubmit',function(ev) {
          //序列化成对象
          var obj = form.serializeToObject();
          obj.start = 0; //返回第一页
          store.load(obj);
          return false;
        }); 

      });

    </script>
  </div>
  </div>
</body>
</html>  