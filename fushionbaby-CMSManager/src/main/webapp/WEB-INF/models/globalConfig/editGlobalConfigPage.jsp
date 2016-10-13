<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>全局字段配置管理</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body class="backwhite">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 " >
			  
				   <form class="form-horizontal" method="post" 
				       	action="${contextPath }/globalConfig/editGlobalValue" id="globalForm">
				 		<input type="hidden" name="id" value="${sysGlobalConfig.id }"/>
				 		<input type="hidden" name="code" value="${sysGlobalConfig.code }"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">全局设置值：</label>
							<div class="col-sm-2">
						 		<input type="text"  class="form-control" id="sortOrder" name="value" value="${sysGlobalConfig.value }"/>
							</div>
						</div>
						
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>