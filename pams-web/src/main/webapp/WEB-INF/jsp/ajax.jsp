<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
%> 
<!DOCTYPE html>  
<html>  
	<head>  
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
		<title>ajax for springMVC</title>  
 		<script type="text/javascript" src="<%=basePath%>static/bootstrap/js/jquery-3.2.0.min.js"></script>  
		<script type="text/javascript">  
                $(function() {  
                    $("#click1").click(function() {  
                        $.ajax( {  
                            type : "GET",  
                            url : "getAjaxJson1",  
                            data : "a=1&b=2",  
                            dataType: "text",  
                            success : function(msg) {  
                                alert(msg);  
                            }  
                        });  
                    });  
                });  
            </script>  
            <script type="text/javascript">  
                $(function() {  
                    $("#click2").click(function() {  
                        $.ajax( {  
                            type : "GET",  
                            url : "getAjaxJson2",  
                            data : "a=xxx&b=yyy",  
                            dataType: "text",  
                            success : function(msg) {  
                                alert(msg);  
                            }  
                        });  
                    });  
                });  
            </script>  
        </head>  
        <body>  
            <input id="click1" type="text" value="click to show integer" /> 
            <input id="click2" type="text" value="click to show string" />  
            <h1>path:<%=path %></h1>
            <h1>bathPath:<%=basePath %></h1> 
        </body>  

    </html>  