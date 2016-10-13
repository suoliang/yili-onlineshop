<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>商品分类新增</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	name: {
	                required: true,
	                rangelength: [1, 20]
	            },
	            code: {
	                required: true,
	                rangelength: [2, 20],
	                remote :{
	                	url:"${contextPath}/skuCategory/isExistCategoryCode",
	                	type:"get",
	                	dataType:"json",
	                	data: { /**要传递的数据*/
	                		categoryCode: function() {
	                             return $("#code").val();
	                         },
	                         oldCategoryCode: function() {
	                             return null;
	                         }
	                    }
	                }
	            },
	            grandcategoryCode: {
	            	required: true,
	                rangelength: [1, 20],
	                remote :{
	                	url:"${contextPath}/skuCategory/isExistParentCategoryCode",
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
	        messages:{
	        	name: {
	                required: "请填写商品分类名称！",
	                rangelength: "商品分类名称长度在1-20之间"
	            },
	            code: {
	                required: "请输入商品分类编号！",
	                rangelength: "商品分类编号长度在2-20之间",
	                remote:"分类编号已存在"
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
	            if($("#categoryLevel").val()=="4"){
	            	jBox.tip("分类级别不能大于3");
	            	return;
	            }
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
	            $.post("${contextPath}/skuCategory/addSkuCategory",{jsonStr:jsonStr},function(result){
	                if(result == "SUCCESS"){
	                	parent.location.reload();
	                	window.parent.window.jBox.close();
	                }else{
	                	jBox.tip("添加失败", 'info');
	                }
	              }); 
	        }
	    });
		
	    $('#grandcategoryCode').change(function(){
            $.ajax({
            	type:"post",
            	url:"${contextPath}/skuCategory/getCategoryLevelByParentCode",
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
					<label class="col-sm-4 control-label">分类名称：</label>
					<div class="col-sm-4">
						<input name="name" id="name" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类编码：</label>
					<div class="col-sm-4">
						<input name="code" id="code" type="text" class="form-control" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类顺序：</label>
					<div class="col-sm-4">
						<input id="showOrder" name="showOrder"  class="form-control" type="text" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">父分类编码：</label>
					<div class="col-sm-4">
						<input id="grandcategoryCode" name="grandcategoryCode" class="form-control" type="text" value="0"/>
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类级别：</label>
					<div class="col-sm-4">
						<input id="categoryLevel" name="categoryLevel" class="form-control" type="text" value="1" readonly="readonly"/>
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">英文名称：</label>
					<div class="col-sm-4">
						<input id="englishName" name="englishName" class="form-control" type="text" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">关键字：</label>
					<div class="col-sm-4">
						<input id="keywords" name="keywords" class="form-control" type="text" />
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">是否显示：</label>
					<div class="col-sm-4">
						<select name="isShow" id="isShow" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="y">显示</option>
							<option value="n">不显示</option>
						</select>
					</div>
				</div>
				
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">分类logo图片：</label>
					<div class="col-sm-4">
						<input type="hidden" id="categoryLogoUrl" name="categoryLogoUrl"  value=""/>
						<input type="hidden" id="oldCategoryLogoUrl" name="oldCategoryLogoUrl" value="" >
						<input type="hidden" id="id" name="id" >
						
						<tags:ckfinder input="categoryLogoUrl" type="images" 
								uploadPath="/category/" selectMultiple="false" />
					</div>
				</div>
			
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" type="submit">添加</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>
</html>