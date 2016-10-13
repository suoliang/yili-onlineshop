<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品品牌修改</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	brandName: {
	                required: true,
	                rangelength: [2, 20]
	            },
	            brandPrefix: {
	                rangelength: [0, 1]
	            },
	            brandCode: {
	                required: true,
	                rangelength: [2, 20],
	                remote :{
	                	url:"${contextPath}/skuBrand/existBrandCode",
	                	type:"get",
	                	dataType:"json",
	                	data: {                     //要传递的数据
	                		 brandCode: function() {
	                             return $("#brandCode").val();
	                         },
	                         oldBrandCode: function() {
	                             return $("#oldBrandCode").val();
	                         }
	                    }
	                }
	            }
	            
	        },
	        messages: {
	        	brandName: {
	                required: "请填写商品编号！",
	                rangelength: "商品编号长度在2-20之间"
	            },
	            brandPrefix: {
	                rangelength: "品牌长度最大长度为1"
	            },
	            brandCode: {
	                required: "请输入商品名称！",
	                rangelength: "商品编号长度在2-20之间",
	                remote:"品牌编号已存在"
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
	            
	            $.post("${contextPath }/skuBrand/modifyBrand",{jsonStr:jsonStr },function(result){
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
					<label class="col-sm-4 control-label">品牌名称：</label>
					<div class="col-sm-4">
						<input name="brandName" id="brandName" type="text" class="form-control" value="${brand.brandName}" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌编码：</label>
					<div class="col-sm-4">
						<input name="brandCode" id="brandCode" type="text" class="form-control" value="${brand.brandCode}" />
						<input id="oldBrandCode" type="hidden" value="${brand.brandCode}" >
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌前缀：</label>
					<div class="col-sm-4">
						<input id="brandPrefix" name="brandPrefix"  class="form-control" type="text" value="${brand.brandPrefix}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌描述：</label>
					<div class="col-sm-4">
						<input id="brandDesc" name="brandDesc" class="form-control" type="text" value="${brand.brandDesc}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌网址：</label>
					<div class="col-sm-4">
						<input id="brandSiteUrl" name="brandSiteUrl" class="form-control" type="text" value="${brand.brandSiteUrl}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌公司logo图片：</label>
					<div class="col-sm-4">
						<input type="hidden" id="brandLogoWebUrl" name="brandLogoWebUrl"  value="${brand.brandLogoWebUrl}"/>
								<input type="hidden" id="oldBrandLogoWebUrl" name="oldBrandLogoWebUrl" value="${brand.brandLogoWebUrl}" >
				
						<tags:ckfinder input="brandLogoWebUrl" type="images" 
								uploadPath="/brand/" selectMultiple="false" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">显示顺序：</label>
					<div class="col-sm-4">
						<input id="sortOrder" name="sortOrder"  class="form-control" type="text" value="${brand.sortOrder}" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌是否显示：</label>
					<div class="col-sm-4">
						<select name="isShow" id="isShow" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="y" <c:if test="${brand.isShow == 'y'}">selected = "selected"</c:if> >显示</option>
							<option value="n" <c:if test="${brand.isShow == 'n'}">selected = "selected"</c:if> >不显示</option>
						</select>
					</div>
				</div>
				
			    <div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">品牌公司app端logo图片：</label>
					<div class="col-sm-4">
						<input type="hidden" id="brandLogoAppUrl" name="brandLogoAppUrl"  value="${brand.brandLogoAppUrl}"/>
								<input type="hidden" id="oldBrandLogoAppUrl" name="oldBrandLogoAppUrl" value="${brand.brandLogoAppUrl}" >
								<input type="hidden" id="id" name="id" value="${brand.id}" >
						
						<tags:ckfinder input="brandLogoAppUrl" type="images"   
								uploadPath="/brand/" selectMultiple="false" />
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