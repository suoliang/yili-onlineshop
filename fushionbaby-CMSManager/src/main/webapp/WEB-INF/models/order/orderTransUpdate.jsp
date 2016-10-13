<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>修改物流</title>
	<script type="text/javascript">
	$(document).ready(function() {
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        
	            transCode: {
	                required: true,
	                rangelength: [2, 20],
	            }
	            
	        },
	        messages: {
	        
	            transCode: {
	            	required: "请填写物流单号！",
	                rangelength: "物流单号长度在2-20之间"
	            }
	           
	        },
	        
	        submitHandler: function(form) {
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
             	$.post("${contextPath}/order/updateOrderTrans",{jsonStr:jsonStr},function(result){
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
              <form class="form-horizontal" method="post" id="handleForm" >
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">订单编号：</label>
					<div class="col-sm-4">
						<input name="orderCode" id="orderCode" readonly type="text" class="form-control" value="${orderTrans.orderCode}" />
						<input name="memberId" id="memberId" readonly type="hidden" class="form-control" value="${orderTrans.memberId}" />
					</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">物流状态：</label>
					<div class="col-sm-4">
						<select name="transStatus" id="transStatus" class="form-control"
						data-placeholder="Choose a Category" tabindex="1">
							<option value="n" <c:if test="${orderTrans.transStatus == 'n'}">selected = "selected"</c:if> >未发货</option>
							<option value="y" <c:if test="${orderTrans.transStatus == 'y'}">selected = "selected"</c:if> >已发货</option>
						</select>
					</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">物流单号：</label>
					<div class="col-sm-4">
						<input name="transCode" id="transCode" type="text" class="form-control" value="${orderTrans.transCode}" />
					</div>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				 
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">物流名称：</label>
					<select class="lg-select" name="transName">
						<c:forEach items="${orderExpressMap}" var="orderExpress" >
							<option value="${orderExpress.expressCompanyName}"   ${orderTrans.transName==orderExpress.expressCompanyName?'selected':'' }> ${orderExpress.expressCompanyName}</option>
						</c:forEach>
					</select>
					<div class="error col-sm-4  textL">必填项</div>
				</div>
				
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" type="submit" >修改</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>	
</html>