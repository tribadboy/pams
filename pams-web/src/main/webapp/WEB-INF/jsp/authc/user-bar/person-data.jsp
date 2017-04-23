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
   
 </head>
 <body>
      
  <div class="container">
  <div class="detail-page">
  <hr>
  <h2>账户信息</h2>
   <div class="detail-section" align="center">  
   	<img src="<%=path %>/web/authc/user/account/getUserPhoto?photoName=${photoName}" 
   		width="200px" height="200px" id="img-change">
   </div>
  </div>
    <div class="detail-page">
      
      <div class="detail-section">  
        <h3>基本信息</h3>
        <div class="row detail-row" align="center">
            <label>用户名：</label><span class="detail-text">${user.username }</span>
        </div>
        <div class="row detail-row" align="center">
            <label>手机：</label><span class="detail-text">${user.phone }</span>
        </div>
        <div class="row detail-row" align="center">
            <label>邮箱：</label><span class="detail-text">${user.mail }</span>
        </div>
      </div>
      <hr>
      <div class="detail-section">
        <h3>系统使用信息</h3> 
        <div class="row detail-row" align="center">
            <label>注册时间：</label><span class="detail-text">${registerTime }</span>
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
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<script type="text/javascript">
  BUI.use('bui/grid',function (Grid) {
	  
       var data = ${loginInfoList} ;
 
        grid = new Grid.SimpleGrid({
          render : '#grid', //显示Grid到此处
          width : 950,      //设置宽度
          columns : [
            {title:'用户名',dataIndex:'username',width:80},
            {title:'ip地址',dataIndex:'ip',width:100},
            {title:'登录时间',dataIndex:'loginTime',width:100,renderer:Grid.Format.dateRenderer}
          ]
        });
      grid.render();
      grid.showData(data);
  });
</script>

<body>
</html>  