<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>功能栏配置</title>
	<script type="text/javascript">
	$(document).ready(function() {
		
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	name: {
	                required: true,
	                rangelength: [2, 50]
	            },
	            code: {
	                required: true,
	                rangelength: [2, 20],
	                remote :{
	                	url:"${contextPath}/sysmgr/functionalBlock/existCode",
	                	type:"get",
	                	dataType:"json",
	                	data: {                     //要传递的数据
	                	   code: function() {
	                             return $("#code").val();
	                        },
	                		id:function(){
	                			return $("#id").val();
	                		}
	                    }
	                }
	            },
	            startTime: {
	                required: true
	                
	            },
	            endTime: {
	                required: true
	               
	            },
	            sortOrder:{
	            	digits:true
	            }
	            
	        },
	        messages: {
	        		name: {
		                required: "请填写活动名称！",
		                rangelength: "活动名称长度在2-50之间"
		            },
		            code: {
		                required: "请输入编号！",
		                rangelength: "编号长度在2-20之间",
		                remote:"编号已存在"
		            },
		            startTime: {
		                required: "请输入开始时间！"
		                
		            },
		            endTime: {
		                required:"请输入结束时间！"
		               
		            },
		            sortOrder:{
		            	digits:"必须输入整数"
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
	            
	            var oldIcon = $(form).find("input[name='oldIcon']").val();
	            
	            $.post("${contextPath }/sysmgr/functionalBlock/edit",{jsonStr:jsonStr,oldIcon:oldIcon },function(result){
	                if(result == "SUCCESS"){
	                	parent.location.reload();
	                	window.parent.window.jBox.close();
	                	
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
   
        <div class="container-fluid" >
            <div class="row">

			 	<div class="pT30 mB0">
			  
				   <form class="form-horizontal" method="post" 
				       	action="${contextPath }/sysmgr/functionalBlock" id="handleForm">
				       	
				 		<input type="hidden" name="id" id="id" value="${result.id }"/>
				 		
				 		<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">编号：</label>
							<div class="col-sm-4">
						 		<input type="text"  class="form-control" id="code" name="code" value="${result.code }"/>
							</div>
						</div>
				 		
				 		
				 		<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">名称：</label>
							<div class="col-sm-4">
						 		<input type="text"  class="form-control" id="name" name="name" value="${result.name }"/>
							</div>
						</div>
						
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">链接地址：</label>
							<div class="col-sm-4">
						 		<input type="text"  class="form-control" id="linkUrl" name="linkUrl" value="${result.linkUrl }"/>
							</div>
						</div>
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">排序：</label>
							<div class="col-sm-4">
						 		<input type="text"  class="form-control" id="sortOrder" name="sortOrder" value="${result.sortOrder }"/>
							</div>
						</div>
				 		
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">是否显示：</label>
							<div class="col-sm-4">
						 		<select name="disable" id="disable" class="form-control"
								data-placeholder="Choose a Category" tabindex="1">
									<option value="y" ${result.disable=='y'?'selected':'' }>使用</option>
									<option value="n" ${result.disable=='n'?'selected':'' }>禁用</option>
								</select>
							</div>
						</div>
						
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">使用范围：</label>
							<div class="col-sm-4">
						 		<select name="useType" id="useType" class="form-control"
								data-placeholder="Choose a Category" tabindex="1">
<%-- 									<option value="y" ${result.disable=='y'?'selected':'' }>使用</option> --%>
<%-- 									<option value="n" ${result.disable=='n'?'selected':'' }>禁用</option> --%>

	 								<c:forEach items="${dic:getDictByType('BLOCK_USE_TYPE')}" var="dict">
						            	<option value="${dict.value }" <c:if test="${dict.value==result.useType }">selected="selected"</c:if>  >${dict.label }</option>
						            </c:forEach>
					            
								</select>
							</div>
						</div>
						
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">开始时间：</label>
							<div class="col-sm-4">
							  <div class="input-group">
								<input class="form-control form_datetime"  readonly name="startTime"  
										 type="text" value="<fmt:formatDate value="${result.startTime}" pattern="yyyy-MM-dd"/>">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								
							  </div>
							  <div class="error">（选填）</div>
							</div>
						</div>
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">结束时间：</label>
							<div class="col-sm-4">
							   <div class="input-group">
								<input class="form-control form_datetime"  readonly name="endTime" 
										 type="text" value="<fmt:formatDate value="${result.endTime}" pattern="yyyy-MM-dd"/>">
							    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							   
							  </div>
							   <div class="error">（选填）</div>
							</div>
						</div>
						
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">图标：</label>
							<div class="col-sm-4">
							
								<input type="hidden" id="icon" name="icon"  value="${result.icon }"/>
								<input type="hidden" id="oldIcon" name="oldIcon"  value="${result.icon }"/>
						
								<tags:ckfinder input="icon" type="images"   
									uploadPath="/functional/" selectMultiple="false" />
							</div>
						</div>
						
						
						<div class="form-group mL0 mR0">
					    	<div class="col-sm-offset-4 col-sm-8">
					      	    <button class="btn btn-primary" type="submit">提交</button>
					   	 	</div>
						</div>
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>