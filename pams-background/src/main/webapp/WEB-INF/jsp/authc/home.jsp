<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    String username = (String)request.getSession().getAttribute("username");
%> 
<!DOCTYPE html>
<html>
 <head>
  <title>PAMS 后台</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   
  <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath%>static/view/assets/css/main-min.css" rel="stylesheet" type="text/css" />

 </head>
 <body>

  <div class="header">
    
	<div class="dl-title">PAMS 个人消费与财务管理系统 -- 后台</div>
    <div class="dl-log">欢迎您，
    	<span class="dl-log-user" id="username"><%=username%></span>
    	<a href="<%=path %>/web/anon/logout.html" title="退出系统" class="dl-log-quit" id="quitMenu">[退出]</a>
    </div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统服务</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-order">用户管理</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-order">金融管理</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
<script type="text/javascript">
  BUI.use('common/page');
</script>
  <script>
    BUI.use('common/main',function(){
      var config = [{
          id:'system-bar', 
          homePage : 'welcome',
          menu:[{
              text:'首页',
              items:[
                {id:'welcome',text:'欢迎页面',href:'<%=path %>/web/authc/system/home/welcome',closeable : false}
              ]
            },{
              text:'公告管理',
              items:[
                {id:'introduce',text:'当前公告',href:'<%=path %>/web/authc/home/about/introduce'},
                {id:'help',text:'帮助与反馈',href:'<%=path %>/web/authc/home/about/help'}  
              ]
            },{
              text:'通知管理',
              items:[
                {id:'introduce',text:'系统介绍',href:'<%=path %>/web/authc/home/about/introduce'},
                {id:'help',text:'帮助与反馈',href:'<%=path %>/web/authc/home/about/help'}  
                ]
              },{
              text:'资讯管理',
              items:[
                {id:'introduce',text:'系统介绍',href:'<%=path %>/web/authc/home/about/introduce'},
                {id:'help',text:'帮助与反馈',href:'<%=path %>/web/authc/home/about/help'}  
                ]
              }]
          },{
            id:'user-bar',
            menu:[{
                text:'用户管理',
                items:[
                   {id:'searchUser',text:'用户信息',href:'<%=path %>/web/authc/user/data/searchUser'}
                ]
              },{
                text:'反馈处理',
                items:[
                   {id:'checkFeedback',text:'查看反馈',href:'<%=path %>/web/authc/user/feedback/checkFeedback'}           
                ]
              },{
                  text:'权限管理',
                  items:[
                     {id:'allDataOfType',text:'分类占比',href:'<%=path %>/web/authc/consumption/data/getAllDataOfType'},
                     {id:'allDataOfTime',text:'时间分布',href:'<%=path %>/web/authc/consumption/data/getAllDataOfTime'}              
                  ]
                }]
          },{
              id:'finance-bar',
              menu:[{
                  text:'股票信息',
                  items:[
                     {id:'updataHistoryData',text:'更新历史数据',href:'<%=path %>/web/authc/finance/stockInfo/updataHistoryData'}
                  ]
                }]
            }];
      new PageUtil.MainPage({
        modulesConfig : config
      });
    });
  </script>
</body>
</html>