<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>产品管理</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index" style="background:#fff">
   
        <div class="container-fluid"  >
            <div class="row" >

			  <div class="col-md-10 content" >
			  		
				      	<h3>${info}</h3>
				      	<c:if test="${not empty results }">
				      		<div> 有${fn:length(results) }条记录添加成功</div>	
				      	</c:if>
				      	<c:if test="${ results.size()==0}">
				      		<div style="color:red">无添加记录。</div>
				      	</c:if>
				      	
				      	<div><c:if test="${not empty rowNum }">Excel导入的记录有${rowNum }条。</c:if></div>
				      	<div><c:if test="${not empty count }">导入成功的记录有${count }条。</c:if></div>
				      	<c:if test="${not empty exitProductCodes }">
				      		<div> 有${fn:length(exitProductCodes) }条产品记录添加失败，添加失败的产品编号如下（该产品编号可能已存在）：</div>
				      		<ul>
				      		<c:forEach items="${exitProductCodes }" var="pcode">
				      			<li>${pcode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	<c:if test="${not empty exitSkuCodes }">
				      		<div> 有${fn:length(exitSkuCodes) }条商品记录添加失败，添加失败的商品编号如下（该商品编号可能已存在）：</div>
				      		<ul>
				      		<c:forEach items="${exitSkuCodes }" var="scode">
				      			<li>${scode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
				      	<c:if test="${not empty notExitProNos }">
				      		<div> 有${fn:length(notExitProNos) }条记录添加失败，该编号为下的产品可能不存在：</div>
				      		<ul>
				      		<c:forEach items="${notExitProNos }" var="pcode">
				      			<li>${pcode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
				      	<c:if test="${not empty validProNos }">
				      		<div> 有${fn:length(validProNos) }条记录添加失败，添加失败的商品编号如下（该产品添加的关键字不符合规范或为空）：</div>
				      		<ul>
				      		<c:forEach items="${validProNos }" var="pcode">
				      			<li>${pcode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	<c:if test="${not empty exitSkuNos }">
				      		<div> 有${fn:length(exitSkuNos) }条记录添加失败，添加失败的商品编号如下（该商品编号已存在）：</div>	
				      		<ul>
				      		<c:forEach items="${exitSkuNos }" var="skuCode">
				      			<li style="float:left;">${skuCode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
				      	<c:if test="${not empty exitBarCodes }">
				      		<div style="clear: both"> 有${fn:length(exitBarCodes) }条记录添加失败，添加失败的商品条形码如下（该商品条形码已存在）：</div>	
				      		<ul>
				      		<c:forEach items="${exitBarCodes }" var="barCode">
				      			<li style="float:left;">${barCode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
				      	
				      	<c:if test="${not empty notExitBarCodes }">
				      		<div style="clear: both"> 有${fn:length(notExitBarCodes) }条记录添加失败，添加失败的商品条形码如下（该商品条形码不存在）：</div>	
				      		<ul>
				      		<c:forEach items="${notExitBarCodes }" var="barCode">
				      			<li style="float:left;">${barCode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
       		  </div>
       	  </div>
       	</div>
   </body>
</html>