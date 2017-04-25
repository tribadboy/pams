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
      
  <div class="container">
    <div class="row">
      <form id="J_Form" class="form-horizontal span24" 
      		action="<%=path %>/web/authc/consumption/record/add"  method="POST" >
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 消费类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="consumptionId" class="input-normal"> 
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
          <div class="control-group span15 ">
            <label class="control-label"><span class="redText">*</span> 消费日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="spendTime" class="calendar" data-cfg="{datePicker :{maxDate : '${currentDate }'}}"   data-rules="{required:true}"  type="text">
            </div>
          </div>
        </div>
        <div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 花费金额：</label>
            <div class="controls">
              <input name="cost" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15">
            <label class="control-label"><span class="redText">*</span> 备注：</label>
            <div class="controls control-row4">
              <textarea name="message" class="input-large" data-tip="{text:'请填写备注信息！'}" 
              data-rules="{required:true,maxlength:30}"></textarea>
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
  <script type="text/javascript">
    BUI.use('common/page');
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