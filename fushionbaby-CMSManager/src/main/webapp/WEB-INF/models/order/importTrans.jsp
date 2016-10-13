<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>快递单号批量导入</title>
	 <script type="text/javascript">
	 	function importSubmit(){
	 		if($("#member_excel").val()==""){
	 			jBox.tip("你未选择任何文件");
	 			return false;
	 		}else{
	 			$("#orderTransExcel").submit();
	 			return true;
	 		}
	 	}
	 </script>
</head>
 <body id="index"  style="background:#fff">
   	<tags:message content="${message }"></tags:message>
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  
				      <form class="form-horizontal" method="post" enctype="multipart/form-data" 
				      		 id="orderTransExcel" action="${contextPath}/order/importExcel">
				 		
						<div class="form-group">
							<label class="col-sm-2 control-label">上传Excel路径：</label>
							<div class="col-sm-2">
						 		<input type="file"  name="member_excel" id="member_excel"/>
							</div>
						</div>
						<button type="button" class="btn btn-success" onclick="importSubmit()">提交</button> 
						
					</form>
       		</div>
       	
       	  </div>
       	</div>
   </body>
</html>