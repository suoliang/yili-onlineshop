<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>${title }</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index" style="background:#fff">
   
        <div class="container-fluid"  >
            <div class="row" >

			  <div class="col-md-10 content" >
			  		
				      	<h3>${info}</h3>
				      	<div><c:if test="${not empty rowNum }">Excel导入的记录有${rowNum }条。</c:if></div>
				      	<div><c:if test="${not empty count }">导入成功的记录有${count }条。</c:if></div>
       		  </div>
       	  </div>
       	</div>
   </body>
</html>