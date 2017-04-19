<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>年度消费报表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
  <body>
 
   <div class="container">
     <div class="row">
      <div class="span24">
        <form action="<%=path %>/web/authc/consumption/excel/yearExcel"  method="POST" id="searchInfoForm">
          <div class="row">
          <div class="control-group span8">
            <label class="control-label">选择年份：</label>
            <div class="controls">
              <select  data-rules="{required:true}"  name="spendYear" id="spendYear" class="input-normal"> 
              <%for(int year = 2004; year < 2024; year++) {%>
                <option value="<%=year%>"><%=year%></option>
                <%} %>
              </select>

              <label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">展示</button>   
              	<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
            </div>              	
              	
          </div>
        </div>        
        </form>
        <button onclick="monthClick()">下载年份报表</button>
            <script type="text/javascript">
						function monthClick() {
						    window.location.href="<%=path %>/web/download/excel?fileCode=13&spendYear="
								+$("#spendYear").val();
						}
		   </script> 
      </div>
    </div> 
   <br><br><br>
    <div class="row">
    <div class="span24" id="canvas1"></div>
    <div class="row">
    <div class="span24" id="canvas2"></div>
    </div>  
    </div>  
  </div>
 
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>

<script type="text/javascript">

BUI.use('bui/chart',function (Chart) {
	 $("#spendYear").val("${spendYear}");
  
    var chart1 = new Chart.Chart({
      render : '#canvas1',
      width : 950,
      height : 400,
      title : {
        text : '消费账目年度报表--柱状图',
        'font-size' : '16px'
      },
      xAxis : {
        categories: ${nameArray}
      },
      yAxis : {
        title : {
          text : '总花费'
        },
        min : 0
      },  
      tooltip : {
        shared : true
      },
      seriesOptions : {
          columnCfg : {
              
          }
      },
      series: ${resultArray}
            
    });
    chart1.render();
    
    var chart2 = new Chart.Chart({
        render : '#canvas2',
       
        height : 500,
        plotCfg : {
          margin : [50,50,80] //画板的边距
        },
        title : {
          text : '消费账目年度报表--折线图'
        },
        xAxis : {
          categories : ${nameArray}
        },
        yAxis : {
          title : {
            text : '花费',
            rotate : -90
          }
        },  
        tooltip : {
          valueSuffix : '¥',
          shared : true, //是否多个数据序列共同显示信息
          crosshairs : true //是否出现基准线
        },
        series : ${resultArray}

      });   
    
    chart2.render();
});
</script>
<body>
</html>  