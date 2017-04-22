<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>帮助与反馈</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
      
  <div class="container">
  	<div class="detail-page">
  		<h2>咨询渠道</h2>
  		<p>如您有任何的问题，可以咨询邮箱 *******@**.com</p>
  	</div>
    <div class="detail-page">
      <h2>反馈信息</h2>
        <form id="J_Form" class="form-horizontal" method="post" action="#">
         <div class="row">
           <div class="control-group span8">
            <label class="control-label">反馈信息题目：</label>
            <div class="controls">
              <input name="recordName" type="text" data-rules="{required:true,maxlength:10}" class="input-normal control-text">
            </div>
          </div>
         </div>
         <div class="row">
            <div class="control-group">
              <label class="control-label">反馈原因：</label>
              <div class="controls">
                <label class="checkbox">
                  <input type="checkbox" value="1">系统卡顿
                </label>
                <label class="checkbox">
                  <input type="checkbox" value="2">功能太少
                </label>
                <label class="checkbox">
                  <input type="checkbox" value="3">操作不佳
                </label>
                <label class="checkbox">
                  <input type="checkbox" value="4">建议与意见
                </label>
              </div>
            </div>
 		 </div>
 		 <div class="row">
            <div class="control-group">
              <label class="control-label">内容：</label>
              <div class="controls control-row4">
                <textarea class="input-large" data-rules="{required:true}"></textarea>
              </div>
            </div>
         </div>
            <hr>
            <div class="form-actions span5 offset3">
              <button id="btnSearch" type="submit" class="button button-primary">提交</button>
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