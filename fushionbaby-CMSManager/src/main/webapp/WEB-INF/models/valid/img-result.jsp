<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>上传图片检测结果页面</title>
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  	<div>当前共检测${result.count}条数据，其中正常记录为${result.successCount}条数据，异常条数为${result.failureCount}条数据。</div>
			  	<div>
			  	<p>失败详细描述如下</p>
			  	<c:if test="${not empty result.failureResults}">
				  	<c:forEach items="${result.failureResults}" var="errorObj" varStatus="state">
				  		<span>
				  			      【序号：&nbsp;${state.count}&nbsp;●&nbsp;
				  			        失败原因：&nbsp;
				  			   <c:forEach items="${errorObj.erroArray}" var="error">
				  			   ${error},
				  			   </c:forEach>&nbsp;●&nbsp;    
				  			        本地上传路径：&nbsp;${errorObj.loaclRootPath}&nbsp;●&nbsp;
				  			        文件名称：&nbsp;${errorObj.fileNameBefore}】
				     	</span>
				     	<br>
				  	</c:forEach>
			  	</c:if>
			  	</div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>