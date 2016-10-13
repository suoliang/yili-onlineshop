<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品管理</title>
	 <script type="text/javascript">
	 $(document).ready(function() {
		 $("#showPanel").hide();
	  	});
	 
 	 function show(){
 		 var content = $("#btnShow").html();
 		 if(content == '↑收起'){
 			$("#btnShow").html("↓点击查看全部失败数据");
 			$("#showPanel").hide();
 		 }else{
 			$("#btnShow").html("↑收起");
 			$("#showPanel").show(); 
 		 }
	 }
	 </script>
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  	<div>共${result.count}数据，成功上传${result.successCount}条，失败${result.failureCount}条。<c:if test="${not empty result.failureResults}"><button type="button" class="btn btn-info" onclick="show()" id="btnShow" lang="0">↓点击查看全部失败数据</button></c:if> </div>
			  	<div id="showPanel">
			  	<c:if test="${not empty result.failureResults}">
				  	<c:forEach items="${result.failureResults}" var="errorObj" varStatus="state">
				  		<span>【序号：&nbsp;${state.count}&nbsp;●&nbsp;失败原因：&nbsp;${errorObj.errorDesc}&nbsp;●&nbsp;本地上传路径：&nbsp;${errorObj.loaclRootPath}&nbsp;●&nbsp;文件名称：&nbsp;${errorObj.fileNameBefore}】</span><br>
				  	</c:forEach>
			  	</c:if>
			  	</div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>