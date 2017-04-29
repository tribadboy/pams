<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>查询存款</title>
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
            		<input type="radio" value="0" name="type" checked> <label>所有存款</label>
            		<input type="radio" value="1" name="type"> <label>尚未完全结转的存款</label>
            	</div>
               <div class="control-group span6">
               <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">搜索</button>
               </div>     
               
          		<div class="control-group span40">
          			<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
            		<label> 注：当需要点击“全部转出”按钮时，请先确定全部转出日期：</label>
            		<div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              			<input name="closeTime" id="closeTime" class="calendar"   data-cfg="{datePicker :{maxDate : '${currentDate }'}}" 
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
            { title: '存款名称',width: 100,  elCls : 'center', sortable: false, dataIndex: 'depositName'},
            { title: '存款类型', width: 100, elCls : 'center', sortable: false, dataIndex: 'depositTimeName'},
            { title: '活期利率(%)', width: 80, elCls : 'center', sortable: false,  dataIndex: 'currentProfitPercent'},
            { title: '定期利率(%)', width: 80, elCls : 'center', sortable: false,  dataIndex: 'fixedProfitPercent'},
            { title: '今日数额(含利息)',width: 100,  elCls : 'center', sortable: false,  dataIndex: 'currentAmount'},
            { title: '状态',width: 60, sortable: false,  dataIndex: 'statusName'},
            { title: '备注信息',width: 200, sortable: false,  dataIndex: 'message'},
            { title: '操作', width: 180, sortable: false, dataIndex: '',renderer:function(value,obj){         
              var str =  '<span class="grid-command btn-del">删除</span>'
              	+ '  <span class="grid-command btn-info">出入账目详情</span>';
              if(obj.statusName != "已结束") {
            	  return str + '  <span class="grid-command btn-fin">全部转出</span>';
              } else {
            	  return str;
              }
            }}
          ];
         
       var store = new Store({
           url : 'searchDepositRecordInfo',
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
/*              items:[{
                    btnCls : 'button button-primary button-small',
                    text:'删除',
                    handler : delFunction
                }], */
                
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
     
        function delItems(items){
          var ids = [];
          BUI.each(items,function(item){
        	  ids.push(item.depositId);
          });
          if(ids.length > 0){
            BUI.Message.Confirm('确认要删除记录么？',function(){
              $.ajax({
                url : '/pams-web/web/authc/asset/deposit/deleteDepositRecordInfo',
                type: "post",
                dataType : 'json',
                data : "depositIds="+ids,
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
          	  ids.push(item.depositId);
            });
            if(ids.length > 0){
              BUI.Message.Confirm('确认要完全转出存款么？',function(){
                $.ajax({
                  url : '/pams-web/web/authc/asset/deposit/closeDepositRecordInfo',
                  type: "post",
                  dataType : 'json',
                  data : "depositIds="+ids+"&closeTime="+closeTime,
        //          data : {"depositIds" : ids, "closeTime" : "#closeTime"},
                  success : function(data){
                    if(data.status == 0){ 
                  	  BUI.Message.Alert('结转成功！');
                  	  store.load();
                    }else{ 
                      BUI.Message.Alert('结转失败，请检查结转时间和存款状态！');
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
          } else if(sender.hasClass('btn-fin')){
              var record = ev.record;
              finItems([record]);
          } else if(sender.hasClass('btn-info')) {
        	  var record = ev.record;
        	  if(top.topManager){
        		  //打开左侧菜单中配置过的页面
        		  top.topManager.openPage({
        		    id : 'inflowAndOutflow',
        		    reload : true,
        		    search : 'depositId='+record.depositId
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