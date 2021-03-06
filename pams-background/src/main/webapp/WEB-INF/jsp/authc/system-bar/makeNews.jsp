<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>创建最新资讯</title>
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
      		action="<%=path %>/web/authc/system/news/add"  method="POST" >
      	<div class="row">
           <div class="control-group span20">
            <label class="control-label"><span class="redText">*</span> 新闻标题：</label>
            <div class="controls">
              <input name="title" type="text" data-rules="{required:true,maxlength:30}" class="input-large control-text">
            </div>
          </div>
        </div>
        <div class="row">
           <div class="control-group span8">
            <label class="control-label"><span class="redText">*</span> 新闻来源：</label>
            <div class="controls">
              <input name="origin" type="text" data-rules="{required:true,maxlength:30}" class="input-normal control-text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15 ">
            <label class="control-label"><span class="redText">*</span> 记录日期：</label>
            <div id="single_range" class="controls bui-form-group"  data-rules="{dateRange:true}">
              <input name="recordDate" class="calendar"  data-cfg="{datePicker :{maxDate : '${currentDate }'}}"  data-rules="{required:true}"  type="text">
            </div>
          </div>
        </div>
        <div class="row">
          <div class="control-group span15">
            <label class="control-label"><span class="redText">*</span> 新闻内容：</label>
            <div class="controls control-row8">
              <textarea name="content" class="input-large" data-tip="{text:'请输入新闻内容！'}" 
              data-rules="{required:true,maxlength:6000}"></textarea>
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