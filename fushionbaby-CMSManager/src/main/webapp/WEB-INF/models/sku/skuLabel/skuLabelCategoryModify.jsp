<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>标签分类关联修改</title>
	<script type="text/javascript">
		function keyUp(){
			var sortOrder = $("#sortOrder").val();
			var regex = /^([1-9]\d*|[0]{1,1})$/;
			if (!regex.test(sortOrder)) {
				jBox.tip("请输入正整数", 'info');
				return;
			}
		}
		$(document).ready(function() {
			/*表单验证*/
		    var validator = $('#handleForm').validate({
		        rules: {
		        	sortOrder: {
		                required: true,
		                rangelength: [1, 8]
		            }
		        },
		        messages: {
		        	sortOrder: {
		                required: "请填写显示顺序",
		                rangelength: "显示顺序长度在1-8之间"
		            }
		        },
		        submitHandler: function(form) {
		            var id = $("#id").val();
		            var sortOrder = $("#sortOrder").val();
		            var disable = $("#disable").val();
		            
		            $.post("${contextPath}/skuLabel/editLabelCate",{id:id,sortOrder:sortOrder,disable:disable},function(result){
		                if(result == "SUCCESS"){
		                	parent.location.reload();
		                	window.parent.window.jBox.close();
		                }else{
		                	jBox.tip("修改失败", 'info');
		                }
		           });
		        }
		    });
		});
	</script>
</head>
     <body class="backWhite">
		<div class="panel pT30 mB0">
              <form class="form-horizontal" method="post" id="handleForm">
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">显示顺序:</label>
					<div class="col-sm-4">
						<input name="sortOrder" id="sortOrder" onkeyup="keyUp();" type="text" class="form-control" value="${labelCategoryRelation.sortOrder}" />
						<input name="id" id="id" type="hidden" class="form-control" value="${labelCategoryRelation.id}" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">备注：</label>
					<div class="col-sm-4">
						<input name="memo" id="memo" type="text" class="form-control" value="${editLabel.memo}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">是否禁用:</label>
					<div class="col-sm-4">
						<select name="disable" id="disable" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="n" <c:if test="${labelCategoryRelation.disable == 'n'}">selected = "selected"</c:if> >启用</option>
							<option value="y" <c:if test="${labelCategoryRelation.disable == 'y'}">selected = "selected"</c:if> >禁用</option>
						</select>
					</div>
				</div>
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" type="submit">修改</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>	
</html>