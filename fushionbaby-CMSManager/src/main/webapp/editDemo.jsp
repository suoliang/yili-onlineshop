<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title>富文本编辑器demo</title>
	<meta charset="utf-8" />
	<jsp:include page="/common-js.jsp" flush="true"></jsp:include>
	<link href="${pageContext.request.contextPath}/fushionbaby/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/fushionbaby/umeditor/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/fushionbaby/umeditor/umeditor.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/fushionbaby/umeditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
	<!--style给定宽度可以影响编辑器的最终宽度-->
	<script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
    <p>这里我可以写一些输入提示</p>
	</script>
</body>
<script type="text/javascript">
//实例化编辑器
var um = UM.getEditor('myEditor');
</script>
</html>
