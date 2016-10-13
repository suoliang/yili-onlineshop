<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品标签修改</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	name: {
	                required: true,
	                rangelength: [2, 50]
	            }
	        },
	        messages: {
	        	name: {
	                required: "请填写商品标签名称！",
	                rangelength: "商品标签名称长度在2-50之间"
	            }
	        },
	        
	        submitHandler: function(form) {
	            // 验证成功后操作
	            console.log($(form).serialize());
	            
	            //表单转json
	            var o = {};  
	            var a = $(form).serializeArray();  
	            $.each(a, function() {  
	                if (o[this.name]) {  
	                    if (!o[this.name].push) {  
	                        o[this.name] = [o[this.name]];  
	                    }  
	                    o[this.name].push(this.value || '');  
	                } else {  
	                    o[this.name] = this.value || '';  
	                }  
	            });  
	            //json转字符串
	            var jsonStr=JSON.stringify(o);
	            
	            $.post("${contextPath }/skuLabel/modifySkuLabel",{jsonStr:jsonStr },function(result){
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
              <form class="form-horizontal" method="post"  id="handleForm">
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">标签名称：</label>
					<div class="col-sm-4">
						<input name="name" id="name" type="text" class="form-control" value="${editLabel.name}" />
						<input name="code" id="code" type="hidden" class="form-control" value="${editLabel.code}" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">标签类型：</label>
					<div class="col-sm-4">
						<input name="type" id="type" type="text" class="form-control" value="${editLabel.type}" />
					</div>
				</div>
				
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">添加分类数量：</label>
					<div class="col-sm-4">
						<input name="maxCategoryNum" id="maxCategoryNum" type="number" class="form-control" value="${editLabel.maxCategoryNum}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">备注：</label>
					<div class="col-sm-4">
						<input name="memo" id="memo" type="text" class="form-control" value="${editLabel.memo}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">标签是否禁用：</label>
					<div class="col-sm-4">
						<select name="disable" id="disable" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="y" <c:if test="${editLabel.disable == 'y'}">selected = "selected"</c:if> >启用</option>
							<option value="n" <c:if test="${editLabel.disable == 'n'}">selected = "selected"</c:if> >禁用</option>
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