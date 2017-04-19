<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>月度消费报表</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   
 </head>
 <body>
 
   <div class="container">
     <div class="row">
      <div class="span24">
        <form action="<%=path %>/web/authc/consumption/excel/monthExcel"  method="POST" id="searchInfoForm">
          <div class="row">
            <div class="control-group span20">           
                <div class="span8">
             		<label class="control-label">选择月份：</label>
            		<input type="text" id="spendMonth" name="spendMonth" class="input-small calendar" value="${spendMonth }" data-tip="{text : '选定月份'}" data-rules="{required:true}">
          		</div>  
               <div class="control-group span5">
               	<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	<button id="btnSearch" type="submit" class="button button-primary">展示</button>   
              	<label>&nbsp;&nbsp;&nbsp;&nbsp;</label>
              	
               </div>
            </div>       
          </div>        
        </form>
        <button onclick="monthClick()">下载月度报表</button>
            <script type="text/javascript">
						function monthClick() {
						    window.location.href="<%=path %>/web/download/excel?fileCode=12&spendMonth="
								+$("#spendMonth").val();
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
BUI.use('common/page');

BUI.use('bui/chart',function (Chart) {
	var nameList = ${nameList};
	var valueList = ${valueList};
	var mapList = ${mapList};
  
    var chart1 = new Chart.Chart({
      render : '#canvas1',
      width : 950,
      height : 400,
      title : {
        text : '消费账目月度报表--柱状图',
        'font-size' : '16px'
      },
      xAxis : {
        categories:  nameList,
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
      series: [ {
              name: '消费类别',
              data:  valueList,
              labels : {
                  label : {
                    rotate : 0,
                    y : 10,
                    'fill' : '#000000',
                    'text-anchor' : 'end',
                    textShadow: '0 0 3px black',
                    'font-size' : '14px'
                  }
                } 
      }]
            
    });

    chart1.render();
    
    var chart2 = new Chart.Chart({
        render : '#canvas2',
        width : 950,
        height : 600,
        title : {
          text : '消费账目月度报表--饼图'
        },
        legend : null ,//不显示图例
        seriesOptions : { //设置多个序列共同的属性
          pieCfg : {
            allowPointSelect : true,
            labels : {
              distance : 40,
              label : {
                //文本信息可以在此配置
              },
              renderer : function(value,item){ //格式化文本
                return value + ' ' + (item.point.percent * 100).toFixed(2)  + '%'; 
              }
            }
          }
        },
        tooltip : {
          pointRenderer : function(point){
            return (point.percent * 100).toFixed(2)+ '%';
          }
        },
        series : [{
            type: 'pie',
            name: 'Browser share',
            data: mapList 
        }]
      });

      chart2.render();

});
</script>
<script type="text/javascript">
BUI.use('bui/calendar',function(Calendar){
    var inputEl = $('#spendMonth'),
  monthpicker = new BUI.Calendar.MonthPicker({
    trigger : inputEl,
   // month:1, //月份从0开始，11结束
    autoHide : true,
    align : {
      points:['bl','tl']
    },
    //year:2000,
    success:function(){
      var month = this.get('month'),
        year = this.get('year');
      month = month + 1;
      if(month < 10) {
    	  inputEl.val(year + '-0' + month);
      } else {
    	  inputEl.val(year + '-' + month);
      }  
      this.hide();
    }
  });
  monthpicker.render();
  monthpicker.on('show',function(ev){
    var val = inputEl.val(),
      arr,month,year;
    if(val){
      arr = val.split('-'); //分割年月
      year = parseInt(arr[0]);
      month = parseInt(arr[1]);
      monthpicker.set('year',year);
      monthpicker.set('month',month - 1);
    }
  });
});


</script>

<body>
</html>  