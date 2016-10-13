<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>CMS</title>
	<script src="${pageContext.request.contextPath}/media/js/jquery-1.10.1.min.js" type="text/javascript"></script>
</head>
<body>
	<h2>Hello CMS</h2>
	<img alt="测试图片" src="http://filepic.fushionbaby.com/img/test.jpg" width="300" height="200">
	
	<a href="http://cms.fushionbaby.com/user/login.do">测试数据</a>
	
<%-- 	<a href="${pageContext.request.contextPath}/cms/skuBatch.do">移植app图文</a> --%>
<%-- 	<a href="${pageContext.request.contextPath}/cms/productBatch.do">移植产品属性值</a> --%>
	
	<script type="text/javascript">
		$(function(){  		
			$.ajax({
				   type: "POST",
				   url: "${pageContext.request.contextPath}/cms/test.do",
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
