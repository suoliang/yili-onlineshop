<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/fushionbaby/js/page.js"></script>
<title>Insert title here</title>
</head>
<body>
	<table width="100%" border="0" bordercolor="#c8c8c8" cellspacing="0" cellpadding="0" 
					 	style="border-collapse:collapse;color: black;">
	            <tr>
	     			<td align="right">总计${page.total}条记录 当前第
	     			<input value="${page.currentPage}" size="1" id="page_currentPage" type="text"
	     				style="width:25px;text-align:center;"
	     				onkeyup="this.value=this.value.replace(/\D/g,'')" 
	     				onafterpaste="this.value=this.value.replace(/\D/g,'')">
	     			/${page.totalPage}页,每页显示
	     			<input id="page_limit" type="text" name="page.limit" value="${page.limit}" size="3" 
	     				style="width:25px;text-align:center;"
	     				onkeyup="this.value=this.value.replace(/\D/g,'')" 
	     				onafterpaste="this.value=this.value.replace(/\D/g,'')" />条
		     			<button class="btn1" style="border:1px solid #ccc" onclick="pageUtil.goFirst()">首页</button>
		     			<button class="btn1" style="border:1px solid #ccc" onclick="pageUtil.goPrev()">上一页</button>
		     			<button class="btn1" style="border:1px solid #ccc" onclick="pageUtil.goNext()">下一页</button>
		     			<button class="btn1" style="border:1px solid #ccc" onclick="pageUtil.goLast()">末页</button>
	     			</td>
	            </tr>
	</table>
</body>
</html>