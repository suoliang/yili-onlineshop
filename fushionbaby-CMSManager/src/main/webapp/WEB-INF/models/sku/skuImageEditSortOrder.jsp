<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品图片管理</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body class="backwhite">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 " >
			  
				   <form class="form-horizontal" method="post" 
				       	action="${contextPath }/skuImage/editSortOrder" id="sortOrderForm">
				 		<input type="hidden" name="id" value="${id }"/>
				 		<input type="hidden" name="skuCode" value="${skuCode }"/>
				 		<input type="hidden" name="uniqueCode" value="${skuCode }"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">显示顺序：</label>
							<div class="col-sm-2">
						 		<input type="number"  class="form-control" id="sortOrder" name="sortOrder" value="${sortOrder }"/>
							</div>
						</div>
						
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>