<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>存款转入与转出</title>
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
      <div class="span24">
        <form id="searchForm" class="form-horizontal" tabindex="0" style="outline: none;">
          <div class="row">
            <div class="control-group span20">
             	<div class="control-group span8">
            		<label class="control-label">选择存款：</label>
            		<div class="controls">
              			<select  data-rules="{required:true}"  name="depositId" id="depositId" class="input-normal"> 
              			</select>
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
    </div> 
    <hr>
    <form id="J_Form" class="form-horizontal" action="<%=path %>/web/authc/asset/deposit/addDepositChange"  method="POST">
    <div class="row">
    	<div class="control-group span8">
            <label class="control-label">选择存款：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="depositId2" id="depositId2" class="input-normal"> 
              </select>
            </div>
          </div>       
          <div class="control-group span">
            <label class="control-label"><span class="redText">*</span> 交易类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name=changeTypeId class="input-normal"> 
                <option value="1">转入</option>
                <option value="2">转出</option>
              </select>
            </div>
          </div>
          <div class="control-group span10 ">
            <label class="control-label"><span class="redText">*</span> 记录日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="changeTime" class="calendar"   data-rules="{required:true}"  type="text">
            </div>
            <label>（注：交易日期必须为所有已存在的交易日期中最新的）</label>
          </div>
          <div class="control-group span15">
            <label class="control-label"><span class="redText">*</span> 交易金额：</label>
            <div class="controls">
              <input name="changeAmount" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
            <label>（注：转出金额不能超过周期内的本金，否则只能进行全部结转操作--“结算存款分栏中”）</label>
          </div>
          <div class="control-group span5">
          	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
          	<button type="submit" class="button button-primary" >创建转入或转出记录</button>
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
    	   
			var idList = ${idList};
			var nameList = ${nameList};
			var depositId = ${depositId};
			var length = ${length};
			for (var k = 0; k < length; k++) {
				var selectObj=document.getElementById("depositId");
				var selectObj2=document.getElementById("depositId2");
				if(idList[k] == depositId) {
     				selectObj.options[selectObj.length] = new Option(nameList[k], idList[k], false, true);
     				selectObj2.options[selectObj2.length] = new Option(nameList[k], idList[k], false, true);
				} else {
					selectObj.options[selectObj.length] = new Option(nameList[k], idList[k], false, false);
					selectObj2.options[selectObj2.length] = new Option(nameList[k], idList[k], false, false);
				}
			}   
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
            { title: '存款名称',width: 100,  sortable: false, dataIndex: 'depositName'},
            { title: '交易类型',width: 100,  sortable: false, dataIndex: 'changeTypeName'},
            { title: '数额', width: 100, sortable: false, dataIndex: 'changeAmount'},
            { title: '交易时间',width: 150, sortable: false,  dataIndex: 'changeTime'}
          ];
         
       var store = new Store({
           url : 'searchDepositChangeInfo',
           autoLoad:false,
            params : { 
                depositId : '#depositId'
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
      });

    </script>
  </div>
  </div>
</body>
</html>  