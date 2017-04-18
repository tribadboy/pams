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
  <title>PAMS 个人消费与财务管理系统</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   
  <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
  <link href="<%=basePath%>static/view/assets/css/main-min.css" rel="stylesheet" type="text/css" />

 </head>
 <body>

  <div class="header">
    
	<div class="dl-title">PAMS 个人消费与财务管理系统</div>
    <div class="dl-log">欢迎您，
    	<span class="dl-log-user" id="username"><%=username%></span>
    	<a href="<%=path %>/web/anon/logout.html" title="退出系统" class="dl-log-quit" id="quitMenu">[退出]</a>
    </div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">系统首页</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-order">消费管理</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-inventory">资产管理</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-marketing">金融理财</div></li>
        <li class="nav-item"><div class="nav-item-inner nav-supplier">个人主页</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>

  <script>
    BUI.use('common/main',function(){
      var config = [{
          id:'home-bar', 
          homePage : 'welcome',
          menu:[{
              text:'系统',
              items:[
                {id:'welcome',text:'欢迎页面',href:'<%=path %>/web/authc/home/system/welcome',closeable : false},
                {id:'notice',text:'通知与公告',href:'<%=path %>/web/authc/home/system/notice'},
                {id:'news',text:'最新资讯',href:'<%=path %>/web/authc/home/system/news'}
              ]
            },{
              text:'账户',
              items:[
                {id:'person-data',text:'个人信息',href:'<%=path %>/web/authc/home/account/person-data'},
                {id:'change-info',text:'修改信息',href:'<%=path %>/web/authc/home/account/change-info'}  
              ]
            },{
              text:'关于',
              items:[
                {id:'introduce',text:'系统介绍',href:'<%=path %>/web/authc/home/about/introduce'},
                {id:'help',text:'帮助与反馈',href:'<%=path %>/web/authc/home/about/help'}  
              ]
            }]
          },{
            id:'consumption-bar',
            menu:[{
                text:'消费账目',
                items:[
                   {id:'addConsumption',text:'添加账目',href:'<%=path %>/web/authc/consumption/record/addConsumption'},
                   {id:'searchAndEdit',text:'搜索账目',href:'<%=path %>/web/authc/consumption/record/searchAndEditConsumption'}  
                ]
              },{
                text:'消费报表',
                items:[
                   {id:'consumptionDateExcel',text:'日期报表',href:'<%=path %>/web/authc/consumption/excel/dateExcel'},
                   {id:'consumptionMonthExcel',text:'月份报表',href:'<%=path %>/web/authc/consumption/excel/monthExcel'}, 
                   {id:'consumptionYearExcel',text:'年度报表',href:'<%=path %>/web/authc/consumption/excel/yearExcel'}              
                ]
              }]
          },{
            id:'asset-bar',
            menu:[{
                text:'搜索页面',
                items:[
                  {id:'code',text:'搜索页面代码',href:'<%=basePath%>static/view/search/code.html'},
                  {id:'example',text:'搜索页面示例',href:'<%=basePath%>static/view/search/example.html'},
                  {id:'example-dialog',text:'搜索页面编辑示例',href:'<%=basePath%>static/view/search/example-dialog.html'},
                  {id:'introduce',text:'搜索页面简介',href:'<%=basePath%>static/view/search/introduce.html'}, 
                  {id:'config',text:'搜索配置',href:'<%=basePath%>static/view/search/config.html'}
                ]
              },{
                text : '更多示例',
                items : [
                  {id : 'tab',text : '使用tab过滤',href : '<%=basePath%>static/view/search/tab.html'}
                ]
              }]
          },{
            id:'finance-bar',
            menu:[{
                text:'详情页面',
                items:[
                  {id:'code',text:'详情页面代码',href:'<%=basePath%>static/view/detail/code.html'},
                  {id:'example',text:'详情页面示例',href:'<%=basePath%>static/view/detail/example.html'},
                  {id:'introduce',text:'详情页面简介',href:'<%=basePath%>static/view/detail/introduce.html'}
                ]
              }]
          },{
            id : 'user-bar',
            menu : [{
              text : '图表',
              items:[
                  {id:'code',text:'引入代码',href:'<%=basePath%>static/view/chart/code.html'},
                  {id:'line',text:'折线图',href:'<%=basePath%>static/view/chart/line.html'},
                  {id:'area',text:'区域图',href:'<%=basePath%>static/view/chart/area.html'},
                  {id:'column',text:'柱状图',href:'<%=basePath%>static/view/chart/column.html'},
                  {id:'pie',text:'饼图',href:'<%=basePath%>static/view/chart/pie.html'}, 
                  {id:'radar',text:'雷达图',href:'<%=basePath%>static/view/chart/radar.html'}
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