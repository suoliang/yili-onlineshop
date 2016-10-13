<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>会员批量导入管理</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index" style="background:#fff">
   
        <div class="container-fluid"  >
            <div class="row" >

			  <div class="col-md-10 content" >
			  			<c:if test="${info eq 'success' }"><h3>导入成功</h3></c:if>
				      	<c:if test="${info eq 'error' }"><h3>导入失败</h3></c:if>
				      	<div><c:if test="${not empty totalSize }">Excel导入的记录有${totalSize }条。</c:if></div>
				      	<div><c:if test="${not empty successSize }">导入成功的记录有${successSize }条。</c:if></div>
				      	
				      	<div> 有${blankSize }条快递单号为空</div>
				      		
				      	
				      	<c:if test="${not empty possibleErrorTransCode}">
				      		<div> 从快递单号${possibleErrorTransCode}开始出错，请检查并重新导入后面的数据</div>
				      		
				      	</c:if>
				      	
       		  </div>
       	  </div>
       	</div>
   </body>
</html>