<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品导入</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  
				      <form class="form-horizontal" method="post" enctype="multipart/form-data"
				       	action="${contextPath }/store/sku/importSkuExcel" id="productForm">
				 		
						<div class="form-group">
							<label class="col-sm-2 control-label">上传Excel路径：</label>
							<div class="col-sm-2">
						 		<input type="file"  id="excelPath" name="excelPath"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-2">
						 		<button class="btn btn-info" type="submit">提交</button>
							</div>
						</div>
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>