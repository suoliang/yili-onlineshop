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
			  		
				      	<h3>${info}</h3>
				      	<div><c:if test="${not empty totalSize }">Excel导入的记录有${totalSize }条。</c:if></div>
				      	<div><c:if test="${not empty successSize }">导入成功的记录有${successSize }条。</c:if></div>
				      	<c:if test="${not empty exitTelephoneCodes }">
				      		<div> 有${fn:length(errorTelephoneCodes) }条用户记录添加失败，添加失败的用户编号如下（该用户编号不是手机号）：</div>
				      		<ul>
				      		<c:forEach items="${errorTelephoneCodes }" var="tcode">
				      			<li>${tcode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	<c:if test="${not empty exitTelephoneCodes }">
				      		<div> 有${fn:length(exitTelephoneCodes) }条用户记录添加失败，添加失败的用户编号如下（该用户编号可能已存在）：</div>
				      		<ul>
				      		<c:forEach items="${exitTelephoneCodes }" var="ecode">
				      			<li>${ecode };</li>
				      		</c:forEach>
				      		</ul>
				      	</c:if>
				      	
       		  </div>
       	  </div>
       	</div>
   </body>
</html>