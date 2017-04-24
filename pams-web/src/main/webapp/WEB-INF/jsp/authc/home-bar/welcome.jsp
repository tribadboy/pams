<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>欢迎页面</title>
    
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/view/assets/css/style.css" />
	<script src="<%=basePath%>static/view/assets/js/move.js" type="text/javascript"></script>
	<script src="<%=basePath%>static/view/assets/js/index.js" type="text/javascript"></script>
   	<style type="text/css">
   	 h1 {
    	color:white;
    	font-size:30px;
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
    <div class="wrapper">        
    </div>
    <h1 align="center">欢迎使用PAMS 个人消费与财务管理系统</h1>
    <hr><hr>
    <div id="pxs_container" class="pxs_container">
         <div class="pxs_bg">
            <div class="pxs_bg1"></div>
            <div class="pxs_bg2"></div>
            <div class="pxs_bg3"></div>
        </div>
        <!-- <div class="pxs_loading">...</div> -->
        <div class="pxs_slider_wrapper">
            <ul class="pxs_slider">
                <li><img src="<%=basePath%>static/view/assets/img/1.png" alt="First Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/2.png" alt="Second Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/3.png" alt="Third Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/4.png" alt="Forth Image" /></li>
            </ul>
            <div class="pxs_navigation">
                <span class="pxs_next"></span>
                <span class="pxs_prev"></span>
            </div>
            <hr>
            <ul class="pxs_thumbnails">
                <li><img src="<%=basePath%>static/view/assets/img/thumbs/1.png" alt="First Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/thumbs/2.png" alt="Second Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/thumbs/3.png" alt="Third Image" /></li>
                <li><img src="<%=basePath%>static/view/assets/img/thumbs/4.png" alt="Forth Image" /></li>
            </ul>
        </div>
    </div>
    
  <script>
	var oLoad = getByClass(document.body, 'pxs_loading')[0];
	var oImgBox = getByClass(document.body,'pxs_slider_wrapper')[0];
	//var oEvent=ev||event;
	//var obj=oEvent.srcElement||oEvent.target;
	var imgs = document.getElementsByTagName('img');
	for(var i=0;i<imgs.length;i++){
		imgs[i].onload = function(){
			oLoad.style.display = 'none';
		}
		oImgBox.style.display = 'block';
	}
  </script>    

</body>
</html>  