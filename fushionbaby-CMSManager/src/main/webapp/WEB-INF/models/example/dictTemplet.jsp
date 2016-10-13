<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 字典自定义标签 -->
	<!-- 根据类型获取全部数据  返回list-->
	<c:forEach items="${dic:getDictByType('DISABLE')}" var="dict">
		<input type="button" value="${dict.label}" lang="${dict.value}">
	</c:forEach>
	<br/>
	<!-- 根据类型和值获取默认值 -->
	<span>${dic:getDictLabel('1', 'DISABLE', '')}</span>
	<br/>
	<!-- 根据类型和值获取描述-->
	<span>${dic:getDictDesc('1', 'DISABLE', '')}</span>
	<br/>
	<!-- 根据标签和类型获取值-->
	<span>${dic:getDictValue('启用', 'DISABLE', '')}</span>
</body>
</html>