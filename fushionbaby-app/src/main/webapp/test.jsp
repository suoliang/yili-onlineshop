<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>移动test</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<h2>Hello APP</h2>
	<img alt="测试图片" src="http://filepic.fushionbaby.com/img/test.jpg" width="300" height="200">
	
	<a href="http://app.fushionbaby.com/home/homeList.do">测试数据</a>
	
	<script type="text/javascript">
		$(function(){  		
			$.ajax({
				   type: "POST",
				   url: "${pageContext.request.contextPath}/app/test.do",
				   dataType:"json",
				   success: function(msg){
						var data = eval(msg).data;
						if(data.redis == 'y'){
							$("body").append("<p>redis 正常</p>");
						}else{
							$("body").append("<p style='color:red'>redis 连接失败</p>");
						}
						if(data.memcached =='y'){
							$("body").append("<p>memcached 正常</p>");
						}else{
							$("body").append("<p style='color:red'>memcached 连接失败</p>");
						}
					}
				}
			);
		});

	
	
	</script>
	
</body>
</html>
