<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>WEB测试</title>
	<script src="${pageContext.request.contextPath}/views/js/jquery-1.11.1.min.js" type="text/javascript"></script>
</head>
<body>
	<h2>Hello WEB</h2>
	<img alt="测试图片" src="http://filepic.fushionbaby.com/img/test.jpg" width="300" height="200">
	
	<a href="http://www.fushionbaby.com">测试数据</a>
	
	<a href="${pageContext.request.contextPath}/web/del_static.do">删除全部静态化页面</a>
	
	<a href="${pageContext.request.contextPath}/web/del_index_static.do">只删除首页静态化</a>
	
	
	<script type="text/javascript">
		$(function(){  		
			$.ajax({
				   type: "POST",
				   url: "${pageContext.request.contextPath}/web/test.do",
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
