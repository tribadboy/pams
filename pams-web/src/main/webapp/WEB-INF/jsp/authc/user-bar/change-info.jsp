<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>修改信息</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
    <style type="text/css">
    .redText {
      color: red;
    }
    h2 {
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
 	<div class="detail-page" align="center">
 		<h2>更改用户图片</h2>
 		<div class="row">
 				<input type="file" id="file" name="file" style="display:none;" onchange="filechange(event)">
				<img src="<%=path %>/web/authc/user/account/getUserPhoto?photoName=${photoName}" width="200px" height="200px" id="img-change">
 		</div>
 		<div class="row">
 			<label style="font-size:15px">点击图片进行更换</label>
 		</div>
 		<br>
 		<div class="row">
 				<button id="btn">保存图片</button>
 		</div>
 	</div>
  	<hr>
   
 	<div class="detail-page" align="center">
      <h2>修改用户信息</h2>
         <form action="<%=path %>/web/authc/user/account/update" class="form-horizontal" method="POST" id="updateUserInfoForm">
         <div class="row" align="center">
			<div class="control-group span20 offset9">
            <label class="control-label">用户名：</label>
            <div class="controls">
            	 <input type="text" name="username" class="username" id="username" value="${user.username}" readonly/> 
            </div>
      		</div>
         </div>
         <div class="row">
           <div class="control-group span20 offset9">
            <label class="control-label">密码：</label>
            <div class="controls">
            	  <input type="password" name="password" class="password" id="password" value="${user.password}" oncontextmenu="return false"/>  
            </div>
          </div>
         </div>
         <div class="row">
           <div class="control-group span20 offset9">
            <label class="control-label">密码验证：</label>
            <div class="controls">
            	 <input type="password" name="confirm_password" class="confirm_password" value="${user.password}" oncontextmenu="return false"/>  
            </div>
          </div>
         </div>
         <div class="row">
           <div class="control-group span20 offset9">
            <label class="control-label">电话号码：</label>
            <div class="controls">
            	<input type="text" name="phone" class="phone" id="phone" value="${user.phone}" autocomplete="off"/>  
            </div>
          </div>
         </div>
         <div class="row">
           <div class="control-group span20 offset9">
            <label class="control-label">电子邮箱：</label>
            <div class="controls">
            	 <input type="text" name="email" class="email" id="email" value="${user.mail}" oncontextmenu="return false"/> 
            </div>
          </div>
         </div>		 
         <hr>
         <div class="form-actions span5 offset11">
            <button id="submit" type="submit" class="button button-primary">提交</button>
         </div>
         <br>
         <h1><span class="redText">${message}</span></h1>
      </form> 
    </div>
    <br><hr><hr><br><br>
      
 
    <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>    
    <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
    <script> 
   		var path = "<%=path%>"; 
   		var basePath = "<%=basePath%>";
	</script>
	<script src="<%=basePath%>static/js/jquery.validate.js"></script>  
	<script src="<%=basePath%>static/js/userInfoValidate.js"></script>	
	<script src="<%=basePath%>static/js/ajaxfileupload.js"></script> 
	
	<script type="text/javascript">
	$("#img-change").click(function () {
	    $("#file").click();
	})
	var filechange=function(event){
   		var files = event.target.files, file;
    	if (files && files.length > 0) {
        	// 获取目前上传的文件
        	file = files[0];// 文件大小校验的动作
        	if(file.size > 1024 * 1024 * 5) {
            	alert('图片大小不能超过 5MB!');
            	return false;
        	}
        	// 获取 window 的 URL 工具
        	var URL = window.URL || window.webkitURL;
        	// 通过 file 生成目标 url
       	 	var imgURL = URL.createObjectURL(file);
        	//用attr将img的src属性改成获得的url
        	$("#img-change").attr("src",imgURL);
    	}
	};
	$(function () {
	    $("#btn").click(function () {
	    	if ($("#file").val().length > 0) {
    			$.ajaxFileUpload({
        			url: '/pams-web/web/authc/user/account/userPhotoUpload',
        			type: 'POST',
        			secureuri: false,
        			fileElementId:'file',
        			dataType:'JSON',
        			success: function (data){
        				data = eval('(' + data + ')');
            			if(data.status == 0){
            				alert("上传图片成功");
                			$("#img-alert").css("display","block");               			
            			} else {
            				alert(data.message);
            			}
        			},
        			error:function(data,status,e){
        				alert(e);     			
        			}
    			})
	    	} else {
                alert("请选择新的图片");
            }
    		return false;
	    })
	})
	</script>
	
<body>
</html>  