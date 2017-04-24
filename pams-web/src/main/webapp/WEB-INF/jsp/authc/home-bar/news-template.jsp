<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" 
    				+ request.getServerPort() + path + "/";
    boolean picFlag = (Boolean)request.getAttribute("picFlag");
%> 
<!DOCTYPE HTML>
<html>
 <head>
  <title>新闻模版页</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<%=basePath%>static/view/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>static/view/assets/css/page-min.css" rel="stylesheet" type="text/css" />
   	<style type="text/css">
   	 h1 {
    	color:black;
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
      
  <div class="container">
    <div class="detail-page">     
  		<%-- <h1 align="center">电影双周刊：《无间道三：终极无间》评论专题${pamsNews.title }</h1>  --%>
  		<h1 align="center">${news.title }</h1>
  		<%-- <h6 align="right">源自：网易财经${pamsNews.origin }</h6> --%>
  		<h6 align="right">源自：${news.origin }</h6>
  		<%-- <h6 align="right">2017-04-23${pamsNews.recordDate }</h6> --%>
  		<h6 align="right">${news.recordDate }</h6>
  		<hr width="100%" size="100" /> 
  		<%if(picFlag) {%>
  		<div align="center">
  			<img src="<%=path %>/web/authc/home/system/getNewsPhoto?pictureName=${news.pictureName}" width="300px" height="200px" id="img-change">
  		</div>
  		<%} %>
　		<!-- 
		<p>落幕后刚刚开始</p>　　 
　		<p>2002年岁末，神话开始。2003年秋冬，走向高潮和终极。《无间道》即自承取乎佛家理念，光明与黑暗、生存与消亡、存在与虚无纠结于混然一体，愕然分崩离析，亦是丰富兼统一的过程。有因就有果，有孽就有缘，境与相的妙处在于言诠的限度，不可道破，又不可沉默，相互依托。心音始终在，拷问自我，拷问时空。这是最好的时代，这是最坏的时代，几乎每个时代的人都在这么说。然而《维摩诘经·观众生品第七》中说：无住则无本。从身体的言行开始，经过辩难言说的层面，初步达到存有自视境界，最后不过是归结为生存的态度。在我看来，刘健明、陈永仁、韩琛、黄志诚、倪永孝、陆启昌、杨锦荣、沈澄等人其实是宿命中的镜像人物，都可以从对方身上看到自我，抗争、迸发、沉雄、挥洒，仿佛的劫数、扭曲的生命，于是乎“无间道”。</p>　　 
　		<p>顾准说从理想主义到经验主义，王小波说要警惕僭主和英雄，谁是英雄？他们从哪里来？他们来了又怎样？我们这个时代还要不要英雄？我在编辑这期E论坛时，有一些欣慰，从文字中可以看出大家都在思考，毕竟我们不再是盲从的一代。在我看来，娱乐从来就不仅仅是娱乐本身，从一滴水、一朵花、一粒沙可以看到一个世界，从一场电影中我们想到的注定更多，理解定可变奏无穷。我们每个人就构成了这个世界，无论这个世界是好是坏，我们都有份。我们每个人都有自己的生活，都有优点都有缺点，这是我们共同进步的基础。</p> 
    	<p>顾准说从理想主义到经验主义，王小波说要警惕僭主和英雄，谁是英雄？他们从哪里来？他们来了又怎样？我们这个时代还要不要英雄？我在编辑这期E论坛时，有一些欣慰，从文字中可以看出大家都在思考，毕竟我们不再是盲从的一代。在我看来，娱乐从来就不仅仅是娱乐本身，从一滴水、一朵花、一粒沙可以看到一个世界，从一场电影中我们想到的注定更多，理解定可变奏无穷。我们每个人就构成了这个世界，无论这个世界是好是坏，我们都有份。我们每个人都有自己的生活，都有优点都有缺点，这是我们共同进步的基础。</p> 
    	<p>顾准说从理想主义到经验主义，王小波说要警惕僭主和英雄，谁是英雄？他们从哪里来？他们来了又怎样？我们这个时代还要不要英雄？我在编辑这期E论坛时，有一些欣慰，从文字中可以看出大家都在思考，毕竟我们不再是盲从的一代。在我看来，娱乐从来就不仅仅是娱乐本身，从一滴水、一朵花、一粒沙可以看到一个世界，从一场电影中我们想到的注定更多，理解定可变奏无穷。我们每个人就构成了这个世界，无论这个世界是好是坏，我们都有份。我们每个人都有自己的生活，都有优点都有缺点，这是我们共同进步的基础。</p> 
    	 -->
    	 ${news.content }
    	 <hr width="100%" size="100" />
    </div>
  </div>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="<%=basePath%>static/view/assets/js/config-min.js"></script>
  
<body>
</html>  