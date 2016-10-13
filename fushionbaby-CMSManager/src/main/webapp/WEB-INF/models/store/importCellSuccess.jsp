<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>小区添加</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index" style="background:#fff">
   
        <div class="container-fluid"  >
            <div class="row" >

			  <div class="col-md-10 content" >
			  		
				      	<h3>${info}</h3>
				      	<c:if test="${not empty storeCellList }">
				      		<div> 有${fn:length(storeCellList) }条记录添加成功</div>	
				      	</c:if>
				      	<c:if test="${ storeCellList.size()==0}">
				      		<div style="color:red">无添加记录。</div>
				      	</c:if>
				      	
				      	<div><c:if test="${not empty rowNum }">Excel导入的记录有${rowNum }条。</c:if></div>
				      	<div><c:if test="${not empty storeCellList }">导入成功的记录有${storeCellList.size() }条。</c:if></div>
				      	<c:if test="${not empty count and count ne '0' }">
				      		<div> 有${count }条小区记录添加失败，该条目存在数据未填写</div>
				      	</c:if>
       		  </div>
       	  </div>
       	</div>
   </body>
</html>