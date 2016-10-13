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
				       	action="${contextPath }/skuLabel/editLabelRelation" id="editForm">
				 		<input type="hidden" name="id" value="${relation.id }"/>
				 		<input type="hidden" name="skuCode" value="${relation.skuCode }"/>
				 		<input type="hidden" name="labelCode" value="${relation.labelCode }"/>
						<div class="form-group ">
							<label class="col-sm-2 control-label">显示顺序：</label>
							<div class="col-sm-2">
						 		<input type="number"  class="form-control" id="showOrder" name="showOrder" value="${relation.showOrder }"/>
							</div>
						
						</div>
						<div class="form-group ">
							<label class="control-label col-sm-2">是否使用：</label>
							<div class="col-sm-2">
	      						<select name="disabled" class="form-control lg-select col-sm-2" data-placeholder="Choose a Category" tabindex="1">
						            <c:forEach items="${dic:getDictByType('DISABLE')}" var="dict">
						            	<option value="${dict.value }" <c:if test="${dict.value==relation.disabled }">selected="selected"</c:if>  >${dict.label }</option>
						            </c:forEach>
						        </select>
					        </div>
						</div>
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>