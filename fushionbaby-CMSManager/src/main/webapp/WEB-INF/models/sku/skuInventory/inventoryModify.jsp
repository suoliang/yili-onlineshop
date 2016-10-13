<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>库存编辑</title>
<script type="text/javascript">
$(document).ready(function() {
	$(".txt-zero").blur(function(){
		if(this.value == ''){
			this.value = 0;
		} 
	});
});



$(document).ready(function() {
	
	/*表单验证*/
    var validator = $('#ed itForm').validate({
        rules: {
        	
        	availableQty: {
                required: true,
                digits:true ,
                min:0,
                max:99999999
            }
            
        },
        messages: {
        	availableQty: {
                required: "请输入库存数量！",
                digits:"库存数量必须为整数！" ,
                min:"库存数量必须大于等于0！",
                max:"库存数量必须小于99999999!"
            }
           
        }
    });
});


</script>
</head>
<body id="index" class="backWhite">

       <div class="container-fluid">
           <div class="row">
         
		  <div class="col-md-12 ">
		     <div class="panel">
               
			 <div class="panel-body">
			     <form class="form-horizontal" id="editForm" method="post" action="${contextPath }/sku/modifyInventory">
			     
			     	<div class="form-group">
						<label class="col-sm-3 control-label">门店编码：</label>
						<div class="col-sm-5">
							<input name="skuCode" id="skuCode" type="hidden" value="${skuInventoryDto.skuCode}" />
							<input name="skuNo" id="skuNo" type="text" class="form-control" value="${skuInventoryDto.skuNo}" disabled/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品条形码：</label>
						<div class="col-sm-5">
							<input name="barCode" id="barCode" type="text" class="form-control" value="${skuInventoryDto.barCode}" disabled/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品库存：</label>
						<div class="col-sm-5">
							<input name="availableQty" id="availableQty" type="text" class="form-control txt-zero"
								onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="9" 
   									onafterpaste="this.value=this.value.replace(/\D/g,'')"
							 value="${skuInventoryDto.availableQty}"/>
						</div>
						<div class="error col-sm-3  textL">必填项</div>
					</div>
			     
			</form>
		    </div>
		   </div>
		  </div>
       </div>
     </div>
</body>
</html>