<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<script type="text/javascript">

function success(){
	$("#skuCategoryExcel").submit();
	$("#subSuccess").attr("disabled", "disabled");	
	
}
</script>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>上传商品</title>
	 <script type="text/javascript">
	 </script>
</head>
 <body id="index"  style="background:#fff">
        <div class="container-fluid" >
            <div class="row">
			  <div class="col-md-10 content" >
				      <form class="form-horizontal" method="post" enctype="multipart/form-data" 
				      		 id="skuCategoryExcel" action="${contextPath}/store/sku/batchAddSku">
						<input type="hidden" name="storeCode"  value="${storeCode }"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">上传Excel路径：</label>
							<div class="col-sm-2">
						 		<input type="file"  id="excelFile" name="excelPath"/>
							</div>
							
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">添加商品最大数量：</label>
							<div class="col-sm-2">
						 		<input type="text"  id="addNum" name="addNum"  
						 				onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11" min=10 
						   				onafterpaste="this.value=this.value.replace(/\D/g,'')"  value="100"  />
							</div>
							
						</div>
						<a href="javascript:success()" class="btn btn-success" id="subSuccess" >提交</a> 
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>