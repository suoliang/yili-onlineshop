<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>活动商品管理</title>
	 <script type="text/javascript">
		$(document).ready(function() {
			
			/*表单验证*/
		    var validator = $('#editForm').validate({
		        rules: {
		        	specialPrice: {
		                required: true,
		                number:true
		            },
		            
		            sort: {
		                required: true,
		                digits:true
		            },
		            limitCount: {
		                required: true,
		                digits:true
		            }
		        },
		        messages: {
		        	specialPrice: {
		                required: "请填写售价！",
		                number:"售价必须为数字！"
		            },
		            
		            sort: {
		                required: "请输入显示顺序！",
		                digits:"显示顺序必须为整数！"
		                
		            },
		            limitCount: {
		                required:"请输限制数量！",
		                digits:"限制数量必须为整数！"
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
		            $.post("${contextPath }/skuPromotions/editPromotionsSkuRelation",{jsonStr:jsonStr},function(result){
		                if(result == "SUCCESS"){
		                	jBox.tip("修改成功", 'info');
		                	window.setTimeout(function () {  
		                		parent.location.reload();
			                	window.parent.window.jBox.close();
	  						}, 500);
		                	
		                }else{
		                	jBox.tip("添加失败", 'info');
		                }
		                
		              });
		           
		        }
		    });
		});
	</script>
</head>
 <body class="backwhite">
   	<tags:message content="${message}"></tags:message>
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 " >
			  
				   <form class="form-horizontal" method="post" 
				       	action="${contextPath }/skuPromotions/editPromotionsSkuRelation" id="editForm">
				 		<input type="hidden" name="id" value="${promotionsSkuDto.id }"/>
				 		<input type="hidden" name="skuCode" value="${promotionsSkuDto.uniqueCode }"/>
				 		<input type="hidden" name="pmCode" value="${promotionsSkuDto.pmCode }"/>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品名称：</label>
							<div class="col-sm-2">
								<input name="skuName" id="skuName" type="text" class="form-control"  value="${promotionsSkuDto.skuName }" readonly/>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">售价：</label>
							<div class="col-sm-2">
								<input name="specialPrice" id="specialPrice" type="text" class="form-control"  value="${promotionsSkuDto.specialPrice }"/>
							</div>
							<div  class="error col-sm-4  textL">必填项</div>
						</div>
						
						
						<div class="form-group ">
							<label class="col-sm-2 control-label">显示顺序：</label>
							<div class="col-sm-2">
						 		<input type="number"  class="form-control" id="sort" name="sort" value="${promotionsSkuDto.sort }"/>
							</div>
							<div class="error col-sm-4  textL">必填项</div>
						</div>
						
						<div class="form-group ">
							<label class="col-sm-2 control-label">限制数量：</label>
							<div class="col-sm-2">
						 		<input type="limitCount"  class="form-control" id="limitCount" name="limitCount" value="${promotionsSkuDto.limitCount }"/>
							</div>
							<div class="error col-sm-4  textL">必填项</div>
						</div>
						
						<div class="form-group ">
							<label class="control-label col-sm-2">是否启用：</label>
							<div class="col-sm-2">
	      						<select name="skuPromotionsStatus" class="form-control lg-select col-sm-2" data-placeholder="Choose a Category" tabindex="1">
						            <c:forEach items="${dic:getDictByType('DISABLE')}" var="dict">
						            	<option value="${dict.value }" <c:if test="${dict.value==promotionsSkuDto.skuPromotionsStatus }">selected="selected"</c:if>  >${dict.label }</option>
						            </c:forEach>
						        </select>
					        </div>
						</div>
						 <button type="submit" class="btn btn-success speBtn">确定</button>
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>