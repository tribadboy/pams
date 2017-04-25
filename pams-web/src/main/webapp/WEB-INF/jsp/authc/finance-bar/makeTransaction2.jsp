<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>添加消费账目</title>
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
  <div class="doc-content">
    <ul class="nav-tabs">
      <li><a href="<%=path%>/web/authc/finance/position/makeTransaction" style="font-size:15px">创建买入与卖出记录</a></li>
      <li class="active"><a href="#" style="font-size:15px">创建转入与转出记录</a></li>
    </ul>
  </div>  
  <div class="container">
    <div class="row">
      <form id="J_Form" class="form-horizontal span24" 
      		action="<%=path %>/web/authc/finance/position/addInflowAndOutflow"  method="POST" >
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 交易类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="changeTypeId" class="input-normal"> 
                <option value="2">转入</option>
                <option value="3">转出</option>
              </select>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15 ">
            <label class="control-label"><span class="redText">*</span> 交易日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="spendTime" class="calendar calendar-time" data-cfg="{datePicker :{maxDate : '${currentDate }'}}"   data-rules="{required:true}"  type="text">
            </div>
          </div>
        </div>
        <div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 金额：</label>
            <div class="controls">
              <input name="cost" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row form-actions actions-bar">
            <div class="span13 offset3 ">
              <button type="submit" class="button button-primary">保存</button>
              <button type="reset" class="button">重置</button>
            </div>
            <h1><span class="redText">${msg }</span></h1>
        </div>
      </form>
    </div>
    

  </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/sea.js"></script>
  <script type="text/javascript">
        BUI.use('bui/calendar',function(Calendar){
          var datepicker = new Calendar.DatePicker({
            trigger:'.calendar',
            showTime : true,
            lockTime : { //可以锁定时间，hour,minute,second
            	minute:30,
              second : 00
            },
            autoRender : true
          });
        });
    </script>
<script type="text/javascript">
  BUI.use('bui/form',function (Form) {
    var form = new Form.HForm({
      srcNode : '#J_Form'
    });

    form.render();
  });
</script>
 
<body>
</html>  