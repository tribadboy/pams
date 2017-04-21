<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>新建存款</title>
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
      		action="<%=path %>/web/authc/asset/deposit/addDepositRecord"  method="POST" >
      	<div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 存款名称：</label>
            <div class="controls">
              <input name="depositName" type="text" data-rules="{required:true,maxlength:10}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 存款类型：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="depositTimeId" class="input-normal"> 
                <option value="1">活期：无时间限制</option>
                <option value="2">定期：3个月(90天)</option>
                <option value="3">定期：6个月(180天)</option>
                <option value="4">定期：1年(360天)</option>
                <option value="5">定期：3年(1080天)</option>
                <option value="6">定期：5年(1800天)</option>
              </select>
            </div>
          </div>
        </div>
        <div class="row">
           <div class="control-group span20">
            <label class="control-label"><span class="redText">*</span> 活期利率：</label>
            <div class="controls">
              <input name="currentProfitPercent" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
            <label>%  （注：定期存款未到期结转的部分将按照活期利率计算）</label>
          </div>
        </div>
        <div class="row">
           <div class="control-group span20">
            <label class="control-label"><span class="redText">*</span> 定期利率：</label>
            <div class="controls">
              <input name="fixedProfitPercent" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
            <label>%  （注：活期存款请输入0）</label>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15 ">
            <label class="control-label"><span class="redText">*</span> 记录日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="changeTime" class="calendar"   data-rules="{required:true}"  type="text">
            </div>
          </div>
        </div>
        <div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 存储数额：</label>
            <div class="controls">
              <input name="changeAmount" type="text" data-rules="{number:true,required:true,min:0}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15">
            <label class="control-label"><span class="redText">*</span> 备注：</label>
            <div class="controls control-row4">
              <textarea name="message" class="input-large" data-tip="{text:'请填写该存款的相关信息！'}" 
              data-rules="{required:true}"></textarea>
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