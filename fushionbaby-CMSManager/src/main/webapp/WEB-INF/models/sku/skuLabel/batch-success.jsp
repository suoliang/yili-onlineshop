<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>批量导入标签商品结果</title>
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
				      	<c:if test="${not empty notExitSkuNos }">
				      		<div> 有${fn:length(notExitSkuNos) }条记录添加失败，添加失败的记录如下（该商品标签或商品编码可能不存在或者显示顺序不为整数）：</div>
				      		<ul>
				      		<c:forEach items="${notExitSkuNos }" var="notExitSkuNo">
				      			<li>${notExitSkuNo };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	<c:if test="${not empty relationExitLabelSkuNos }">
				      		<div> 有${fn:length(relationExitLabelSkuNos) }条记录添加失败，添加失败的记录如下（该标签和商品关联已存在）：</div>
				      		<ul>
				      		<c:forEach items="${relationExitLabelSkuNos }" var="relationExitLabelSkuNo">
				      			<li>${relationExitLabelSkuNo };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
       		  </div>
       	  </div>
       	</div>
   </body>
</html>