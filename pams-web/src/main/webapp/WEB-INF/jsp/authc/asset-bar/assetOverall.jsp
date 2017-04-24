<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>资产概要</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   	<style type="text/css">
   	 h1 {
    	color:gray;
    	font-size:18px;
    }
     label {
    	color:gray;
    	font-size:15px;
    }
    p {
      	margin:10px 0;
    	padding:10px 0;
    	//height:90px;
    	line-height:30px;
    	color:gray;
    	font-size:16px;
    	text-indent: 2em;
    }
   </style>
 </head>
 <body>
      
  <div class="container">
    <div class="detail-page">
      <h1 align="center">消费概要</h1><br>
      <div class="detail-section">  
        <div class="row detail-row">
          <div class="span8">
            <label>记录笔数：</label><span class="detail-text">${consumptionOverall.count }</span>
          </div>
          <div class="span8">
            <label>最小日期：</label><span class="detail-text">${consumptionOverall.minDate }</span>
          </div>
           <div class="span8">
            <label>最大日期：</label><span class="detail-text">${consumptionOverall.maxDate }</span>
          </div>
          <div class="span8">
            <label>总开销：</label><span class="detail-text">${consumptionOverall.sum }</span>
          </div>
        </div>
      </div>     
    </div>
    <hr>
    <div class="detail-page">
      <h1 align="center">固定资产概要</h1><br>
      <div class="detail-section">  
        <div class="row detail-row">
          <div class="span8">
            <label>记录笔数：</label><span class="detail-text">${fixedAssetOverall.count }</span>
          </div>
          <div class="span8">
            <label>最小日期：</label><span class="detail-text">${fixedAssetOverall.minDate }</span>
          </div>
           <div class="span8">
            <label>最大日期：</label><span class="detail-text">${fixedAssetOverall.maxDate }</span>
          </div>
          <div class="span8">
            <label>总价值：</label><span class="detail-text">${fixedAssetOverall.sum }</span>
          </div>
        </div>
      </div>     
    </div>
    <hr>
    <div class="detail-page">
      <h1 align="center">常规收入概要</h1><br>
      <div class="detail-section">  
        <div class="row detail-row">
          <div class="span8">
            <label>记录笔数：</label><span class="detail-text">${regularIncomeOverall.count }</span>
          </div>
          <div class="span8">
            <label>最小日期：</label><span class="detail-text">${regularIncomeOverall.minDate }</span>
          </div>
           <div class="span8">
            <label>最大日期：</label><span class="detail-text">${regularIncomeOverall.maxDate }</span>
          </div>
          <div class="span8">
            <label>总收入：</label><span class="detail-text">${regularIncomeOverall.sum }</span>
          </div>
        </div>
      </div>     
    </div>
    <hr>
    <div class="detail-page">
      <h1 align="center">存款概要</h1><br>
      <div class="detail-section">  
        <div class="row detail-row">
          <div class="span8">
            <label>进行中的存款笔数：</label><span class="detail-text">${depositOverall.countOfValid }</span>
          </div>
          <div class="span8">
            <label>进行中存款的累积投入：</label><span class="detail-text">${depositOverall.payValue }</span>
          </div>
           <div class="span8">
            <label>截止今日预期的总收益：</label><span class="detail-text">${depositOverall.exceptValue }</span>
          </div>
        </div>
        <div class="row detail-row">
          <div class="span8">
            <label>已结束的存款笔数：</label><span class="detail-text">${depositOverall.countOfInvalid }</span>
          </div>
          <div class="span8">
            <label>已结束的存款总收益：</label><span class="detail-text">${depositOverall.invalidValue }</span>
          </div>
        </div>
      </div>     
    </div>
    <hr>
    <div class="detail-page">
      <h1 align="center">贷款概要</h1><br>
      <div class="detail-section">  
        <div class="row detail-row">
          <div class="span6">
            <label>进行中的贷入贷款笔数：</label><span class="detail-text">${loanOverall.countOfValidInflow }</span>
          </div>
          <div class="span6">
            <label>进行中的贷出贷款笔数：</label><span class="detail-text">${loanOverall.countOfValidOutflow }</span>
          </div>
          <div class="span6">
            <label>进行中的贷款总差值：</label><span class="detail-text">${loanOverall.validValue }</span>
          </div>
          <div class="span6">
            <label>预期的剩余差值：</label><span class="detail-text">${loanOverall.exceptValue }</span>
          </div>
        </div>
        <div class="row detail-row">
          <div class="span6">
            <label>已结束的贷入贷款笔数：</label><span class="detail-text">${loanOverall.countOfInvalidInflow }</span>
          </div>
          <div class="span6">
            <label>已结束的贷出贷款笔数：</label><span class="detail-text">${loanOverall.countOfInvalidOutflow }</span>
          </div>
          <div class="span6">
            <label>已结束的贷款的总差值：</label><span class="detail-text">${loanOverall.invalidValue }</span>
          </div>
        </div>
      </div>     
    </div>
    <hr>
  </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>

<body>
</html>  