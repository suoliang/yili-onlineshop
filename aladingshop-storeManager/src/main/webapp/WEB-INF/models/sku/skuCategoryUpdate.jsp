<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品分类修改</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	name: {
	                required: true,
	                rangelength: [1, 20]
	            },
	            grandcategoryCode: {
	            	required: true,
	                rangelength: [1, 20],
	                remote :{
	                	url:"${contextPath}/storeSkuCategory/isExistParentCategoryCode",
	                	type:"get",
	                	dataType:"json",
	                	data: { /**要传递的数据*/
	                		parentCategoryCode: function() {
	                             return $("#grandcategoryCode").val();
	                         }
	                    }
	                }
	            }
	        },
	        messages: {
	        	name: {
	                required: "请填写商品分类名称！",
	                rangelength: "商品分类名称长度在1-20之间"
	            },
	            grandcategoryCode:{
	            	required: "请输入父分类编号！",
	                rangelength: "父分类编号长度在1-20之间",
	                remote:"父分类编号不存在,一级分类请输入0"
	            }
	        },
	        
	        submitHandler: function(form) {
	            // 验证成功后操作
	            console.log($(form).serialize());
	            
	           /**表单转json*/
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
	            /**json转字符串*/
	            var jsonStr=JSON.stringify(o);
	            $.post("${contextPath}/storeSkuCategory/updateSkuCategory",{jsonStr:jsonStr},function(result){
	                if(result.responseCode == "0"){
	                	parent.location.reload();
	                	window.parent.window.jBox.close();
	                }else{
	                	jBox.tip("修改失败", 'info');
	                }
	              }); 
	        }
	    });
		
	    $('#grandcategoryCode').change(function(){
	    	if($("#code").val() == $("#grandcategoryCode").val()){
	    		jBox.tip("父分类编码不能和分类编码相同", 'info');
	    		$("#grandcategoryCode").val("");
	    		return;
	    	}
            $.ajax({
            	type:"post",
            	url:"${contextPath}/storeSkuCategory/getCategoryLevelByParentCode",
            	data:{'parentCategoryCode':$("#grandcategoryCode").val()},
            	dateType:"json",
            	async:false,
            	success:function(data){
            		if (data.responseCode == '0') {
            			$('#categoryLevel').val(data.data);
					}
            	}
            });
        });
	});
	</script>
</head>
 <body class="backWhite">
		<div class="pT30 mB0">
              <form class="form-horizontal" method="post" id="handleForm">
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">父分类编码：</label>
					<div class="col-sm-4">
						<input id="grandcategoryCode" name="grandcategoryCode" class="form-control" type="text" readonly="readonly" value="${skuCategory.grandcategoryCode}"/>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">父分类名称：</label>
					<div class="col-sm-4">
						<input id="grandcategoryName"  class="form-control" type="text" readonly="readonly" value="${skuCategory.name}"/>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
			
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类级别：</label>
					<div class="col-sm-4">
						<input id="categoryLevel" name="categoryLevel" class="form-control" type="text" readonly="readonly" value="${skuCategory.categoryLevel}"/>
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类名称：</label>
					<div class="col-sm-4">
						<input name="code" id="code" type="hidden" class="form-control" value="${skuCategory.code}"/>
					
						<input name="name" id="name" type="text" class="form-control" value="${skuCategory.name}"/>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
		
				
			
				
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">是否显示：</label>
					<div class="col-sm-4">
						<select name="isShow" id="isShow" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="y" <c:if test="${skuCategory.isShow == 'y'}">selected = "selected"</c:if> >显示</option>
							<option value="n" <c:if test="${skuCategory.isShow == 'n'}">selected = "selected"</c:if>>不显示</option>
						</select>
					</div>
				</div>
				
					<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">显示顺序：</label>
					<div class="col-sm-4">
						<input id="showOrder" name="showOrder"  class="form-control" type="text" value="${skuCategory.showOrder}"/>
					</div>
				</div>
				
				
				<input type="hidden" name="id" value="${skuCategory.id}"/>
			
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" type="submit">修改</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>
</html>