<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>创建策略</title>
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
  <h2 align="left" style="font-size:18px;color:gray">创建股票投资策略</h2><br>
      <form id="J_Form" class="form-horizontal span24" 
      		action="<%=path %>/web/authc/finance/strategy/add"  method="POST" >
        <div class="row">
            <div class="control-group span8">
          	<label class="control-label"><span class="redText">*</span>策略名称：</label>
          	<div class="controls">
            	<input name="strategyName" type="text" class="control-text" data-rules="{required:true,maxlength:20}">
          	</div>
        	</div>
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 策略类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="strategyType" class="input-normal"> 
                <option value="1">短期策略(10-15天)</option>
                <option value="2">中期策略(30-40天)</option>
                <option value="3">长期策略(80-100天)</option>
              </select>
            </div>
          </div>
        </div>
          <div class="control-group span8 ">
            <label class="control-label"><span class="redText">*</span> 策略开始日期:</label>
             <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="startDate" class="calendar"  data-rules="{required:true}"
               data-cfg="{datePicker :{minDate : '${currentDate }'}}"  type="text">
            </div>           
          </div>          
         <div class="row">
            <div class="control-group">
              <label class="control-label"><span class="redText">*</span>策略备注：</label>
              <div class="controls control-row4">
                <textarea class="input-large" data-rules="{required:true,maxlength:50}" name="message"></textarea>
              </div>
            </div>
         </div>
      <div class="row">
        <div class="span21 offset3 control-row-auto">
          <div id="grid"></div>
          <input type="hidden" name="element">
        </div>
      </div>
        <div class="row form-actions actions-bar">
            <div class="span13 offset3 ">
              <button type="submit" class="button button-primary">保存</button>
              <button type="reset" class="button">重置</button>         
            </div>         
            <h2><span class="redText">${msg }</span></h2> 
        </div>
      </form>  
       <hr>
    <div id="content" class="hide">
      <form id="J_Form" class="form-horizontal">
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span>股票代码：</label>
            <div class="controls">
              <input name="symbolCode" type="text" data-rules="{required:true}" class="input-normal control-text">
            </div>
          </div>
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span>股票类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="symbolType" class="input-normal"> 
                <option value="0">沪市</option>
              </select>
            </div>
          </div>
         <div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 投资占比(%)：</label>
            <div class="controls">
              <input name="percent" type="text" data-rules="{number:true,required:true,min:1}" class="input-normal control-text">
            </div>
          </div>
        </div>
        </div>
      </form>
    </div>
      

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/sea.js"></script>
  <script src="http://g.alicdn.com/bui/seajs/2.3.0/sea.js"></script>
  <script src="http://g.alicdn.com/bui/bui/1.1.21/config.js"></script>
    <script type="text/javascript">
    BUI.use('common/page');
  </script>
  <script type="text/javascript">
  BUI.use(['bui/grid','bui/data','bui/form'],function (Grid,Data,Form) {

    var columns = [{title : '股票代码',dataIndex :'symbolCode'},
            {title : '股票类型', sortable: false, dataIndex :'', renderer: function(value, obj) {
            	if(obj.symbolType == 0) {
            		return "沪市";
            	} else{
            		return "深市";
            	}
            }},
            {title : '投资占比(%)', sortable: false, dataIndex :'percent'},
            {title : '操作', sortable: false, renderer : function(){
              return '<span class="grid-command btn-edit">编辑</span>';
            }}
          ],
      //默认的数据
      data = [
/*         {symbolCode:'600001',symbolType:'1',percent:'34.56'},
        {symbolCode:'600002',symbolType:'2',percent:'55.32'} */
      ],
      store = new Data.Store({
        data:data
      }),
      editing = new Grid.Plugins.DialogEditing({
        contentId : 'content',
        triggerCls : 'btn-edit',
        eidtor : {
          focusable : false
        }
      }),
      grid = new Grid.Grid({
        render : '#grid',
        columns : columns,
        width : 700,
        forceFit : true,
        store : store,
        plugins : [Grid.Plugins.CheckSelection,editing],
        tbar:{
          items : [{
            btnCls : 'button button-small',
            text : '<i class="icon-plus"></i>添加',
            listeners : {
              'click' : addFunction
            }
          },
          {
            btnCls : 'button button-small',
            text : '<i class="icon-remove"></i>删除',
            listeners : {
              'click' : delFunction
            }
          }]
        }

      });
    grid.render();

    function addFunction(){
      var newData = {};
      editing.add(newData); //添加记录后，直接编辑
    }

    function delFunction(){
      var selections = grid.getSelection();
      store.remove(selections);
    }
    var form = new Form.HForm({
      srcNode : '#J_Form'
    });
    form.render();
     var field = form.getField('element'); 
    form.on('beforesubmit',function(){
      var records = store.getResult();
      field.set('value',BUI.JSON.stringify(records));
    });
  });
</script>

   </div>
<body>
</html>  