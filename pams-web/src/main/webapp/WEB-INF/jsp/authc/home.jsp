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
<script type="text/javascript">
  BUI.use('common/page');
</script>
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
                   {id:'searchAndEditConsumption',text:'搜索账目',href:'<%=path %>/web/authc/consumption/record/searchAndEditConsumption'}  
                ]
              },{
                text:'消费报表',
                items:[
                   {id:'consumptionDateExcel',text:'日期报表',href:'<%=path %>/web/authc/consumption/excel/dateExcel'},
                   {id:'consumptionMonthExcel',text:'月份报表',href:'<%=path %>/web/authc/consumption/excel/monthExcel'}, 
                   {id:'consumptionYearExcel',text:'年度报表',href:'<%=path %>/web/authc/consumption/excel/yearExcel'}              
                ]
              },{
                  text:'平台数据分析',
                  items:[
                     {id:'allDataOfType',text:'分类占比',href:'<%=path %>/web/authc/consumption/data/getAllDataOfType'},
                     {id:'allDataOfTime',text:'时间分布',href:'<%=path %>/web/authc/consumption/data/getAllDataOfTime'}              
                  ]
                }]
          },{
            id:'asset-bar',
            menu:[{
                text:'资产总揽',
                items:[
                  {id:'generalAsset',text:'资产概要',href:'<%=path %>/web/authc/asset/generalAsset/overall'}
                ]
              },{
                text : '固定资产',
                items : [
                   {id:'addFixedAsset',text:'创建固定资产',href:'<%=path %>/web/authc/asset/fixedAsset/addFixedAsset'},
                   {id:'searchAndEditFixedAsset',text:'查询固定资产',href:'<%=path %>/web/authc/asset/fixedAsset/searchAndEditFixedAsset'}
                ]
              },{
                  text : '常规收入',
                  items : [
                     {id:'addRegularIncome',text:'创建常规收入',href:'<%=path %>/web/authc/asset/regularIncome/addRegularIncome'},
                     {id:'searchAndEditRegularIncome',text:'查询常规收入',href:'<%=path %>/web/authc/asset/regularIncome/searchAndEditRegularIncome'}
                  ]
              },{
                  text : '带息存款',
                  items : [
					{id:'addDepositRecord',text:'创建存款',href:'<%=path %>/web/authc/asset/deposit/addDepositRecordPage'},
					{id:'editDepositRecord',text:'结算存款',href:'<%=path %>/web/authc/asset/deposit/editDepositRecordPage'},
					{id:'inflowAndOutflow',text:'转入与转出',href:'<%=path %>/web/authc/asset/deposit/inflowAndOutflow'},
                  ]
              },{
                  text : '个人信贷',
                  items : [
					{id:'addLoanRecord',text:'创建贷款',href:'<%=path %>/web/authc/asset/loan/addLoanRecordPage'},
					{id:'editLoanRecord',text:'结算贷款',href:'<%=path %>/web/authc/asset/loan/editLoanRecordPage'}
                  ]
              }]
          },{
            id:'finance-bar',
            menu:[{
                text:'股市资讯',
                items:[
                  {id:'realTimeData',text:'实时数据',href:'<%=path %>/web/authc/finance/stockData/getRealTimeData'},
                  {id:'searchStock',text:'股票详情',href:'<%=path %>/web/authc/finance/stockData/searchStock'}
                ]
              },{
                  text:'我的持仓',
                  items:[
                    {id:'positionOverall',text:'持仓概览',href:'<%=path %>/web/authc/finance/position/getPositionOverall'},
                    {id:'transactionRecord',text:'交易记录',href:'<%=path %>/web/authc/finance/position/getTransactionRecord'},
                    {id:'makeTransaction',text:'创建交易',href:'<%=path %>/web/authc/finance/position/makeTransaction'}
                  ]
             }]
          },{
            id : 'user-bar',
            menu : [{
              text : '个人账户',
              items:[
                  {id:'person-data',text:'个人信息',href:'<%=path %>/web/authc/user/account/person-data'},
                  {id:'change-info',text:'修改信息',href:'<%=path %>/web/authc/user/account/change-info'}  
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