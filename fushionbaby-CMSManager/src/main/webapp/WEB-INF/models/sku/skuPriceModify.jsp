<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品价格编辑</title>
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
    var validator = $('#editForm').validate({
        rules: {
        	costPrice: {
                required: true
            },
            currentPrice: {
                required: true
            },
            aladingPrice: {
                required: true
            }
            
        },
        messages: {
        	costPrice: {
                required: "请填写商品成本价！"
            },
            currentPrice: {
                required: "请输入商品当前价！"
            },
            aladingPrice: {
                required: "请输入商品阿拉丁会员价！"
            }
           
        },
        
        submitHandler: function(form) {
            // 验证成功后操作
            console.log($(form).serialize());
            
            $(form).ajaxSubmit();
        }
    });
});


</script>
</head>
<body id="index">

       <div class="container-fluid">
           <div class="row">
		  <div class="col-md-12 content">
		     <div class="panel panel-info">
                <div class="panel-heading">
                   <h3 class="panel-title"><i class="fa fa-cog"></i> 商品编辑</h3>
                </div>
			 <div class="panel-body">
			     <form class="form-horizontal" id="editForm" method="post" action="${contextPath }/sku/modifySkuPrice">
			     	<div class="form-group">
						<label class="col-sm-3 control-label">商品编号：</label>
						<div class="col-sm-5">
							<input name="uniqueCode" id="uniqueCode" type="hidden" class="form-control" value="${sku.uniqueCode}"/>
							<input name="skuNo" id="skuNo" type="text" class="form-control" value="${sku.skuNo}" readonly/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品名称：</label>
						<div class="col-sm-5">
							<input name="skuName" id="skuName" type="text" class="form-control" value="${sku.skuName}" readonly/>
						</div>
					</div>
			     
					

					<div class="form-group">
						<label class="col-sm-3 control-label txt-zero" for="sku-costPrice">成本价：</label>
						<div class="col-sm-5">
							<input id="sku-costPrice" class="form-control" name="costPrice" type="text"
										onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
   										value="${(empty sku.skuPriceDto.costPrice)?'0':sku.skuPriceDto.costPrice }" />
						</div>
						<div class="error col-sm-3  textL">必填项</div>
					</div>

					

					<div class="form-group">

						<label class="col-sm-3 control-label" for="sku-currentPrice">现价：</label>

						<div class="col-sm-5">

								<input id="sku-currentPrice" class="form-control txt-zero" name="currentPrice" type="text"
									onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   									onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
   									value="${(empty sku.skuPriceDto.currentPrice)?'0':sku.skuPriceDto.currentPrice }"/>
						</div>
						<div class="error col-sm-3  textL">必填项</div>
							
					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label txt-zero" for="sku-aladingPrice">阿拉丁会员价：</label>

							<div class="col-sm-5">

								<input id="sku-aladingPrice" class="form-control" name="aladingPrice" type="text"
										onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" 
										maxlength="19"
   										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
   										value="${(empty sku.skuPriceDto.aladingPrice)?'0':sku.skuPriceDto.aladingPrice }"/>

							</div>
							
							<div class="error col-sm-3  textL">必填项</div>

					</div>
						
					
				<div class="form-group">
			    	<div class="col-sm-offset-2 col-sm-10">
			      	
			      		<button class="btn btn-success " type="submit">确定</button>
						<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
			      	
			   	 	</div>
				</div>
				
			</form>
		    </div>
		   </div>
		  </div>
       </div>
     </div>
</body>
</html>