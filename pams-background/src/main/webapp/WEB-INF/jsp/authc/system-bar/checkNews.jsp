<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询新闻记录</title>
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
            { title: '新闻标题',width: 300,  sortable: false, dataIndex: 'title'},
            { title: '新闻来源', width: 100, sortable: false, dataIndex: 'origin'},
            { title: '记录时间',width: 100, sortable: false,  dataIndex: 'recordDate'},
            { title: '操作', width: 100, sortable: false, dataIndex: '',renderer:function(value,obj){         
              return '  <span class="grid-command btn-info">查看新闻内容</span>'
              + '<span class="grid-command btn-del">删除</span>';
            }}
          ];
         
         
       var store = new Store({
           url : 'searchFinancialNewsInfo',
           autoLoad:false,
            params : { 
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
            BUI.Message.Confirm('确认要删除该条新闻么？',function(){
              $.ajax({
                url : '<%=path%>/web/authc/system/news/deleteNewsInfo',
                type: "post",
                dataType : 'json',
                data : "newsId="+item.newsId,
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
            delItem(record);
          } else if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  top.topManager.openPage({
        		    id : 'newsInfo',
        		    reload : true,
        		    href : '<%=path%>/web/authc/system/news/getNewsInfo?newsId='
        		    		+record.newsId,
        		 	title : '新闻详细内容'
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