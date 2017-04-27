<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>个人信息</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   	<style type="text/css">
    h3 {
    	color:black;
    	font-size:25px;
    }
   	 label {
    	color:gray;
    	font-size:20px;
    }
   
   </style>
 </head>
 <body>
      
  <div class="container">
  <div class="detail-page">
  <hr>
  <h3>账户信息</h3>
   <div class="detail-section" align="center">  
   	<img src="<%=path %>/web/authc/user/data/getUserPhoto?photoName=${photoName}" 
   		width="200px" height="200px" id="img-change">
   </div>
  </div>
    <div class="detail-page">
      <br>
      <div class="detail-section">  
        <div class="row detail-row" align="center">
            <label>用户名：<span class="detail-text">${user.username }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>手机：<span class="detail-text">${user.phone }</span></label>
        </div>
        <div class="row detail-row" align="center">
            <label>邮箱：<span class="detail-text">${user.mail }</span></label>
        </div>
      </div>
      <hr>
      <div class="detail-section">
        <h3>系统使用信息</h3> 
        <div class="row detail-row" align="center">
            <label>注册时间：<span class="detail-text">${registerTime }</span></label>
        </div>
        <label>最近的登录：</label><br>
        <div class="row detail-row">
          <div class="span24">
            <div id="grid"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <hr><hr><br><br>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<script type="text/javascript">
  BUI.use('bui/grid',function (Grid) {
	  
       var data = ${loginInfoList} ;
 
        grid = new Grid.Grid({
          render : '#grid', //显示Grid到此处
          width : 950,      //设置宽度
          forceFit : true,         
          columns : [
            {title:'用户名', elCls : 'center', dataIndex:'username',width:80},
            {title:'ip地址', elCls : 'center', dataIndex:'ip',width:100},
            {title:'登录时间', elCls : 'center', dataIndex:'loginTime',width:100,renderer:Grid.Format.dateRenderer}
          ],
          plugins : [Grid.Plugins.RowNumber,Grid.Plugins.AutoFit]  // 插件形式引入自适应宽度
        });
      grid.render();
      grid.showData(data);
  });
</script>

<body>
</html>  