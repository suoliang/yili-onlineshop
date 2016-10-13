<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>订单分派</title>
	<script type="text/javascript">
	$(document).ready(function() {
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	userId: {
	                required: true,
	                rangelength: [1, 10]
	            }
	        },
	        messages: {
	        	userId: {
	                required: "请选择客服名称！",
	                rangelength: ""
	            }
	           
	        },
	        
	        submitHandler: function(form) {
	         	//表单转json
	   /*          var o = {};  
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
	            var json=JSON.stringify(o);  */
	            var orderCode=$("#orderCode").val();
	            var memberId=$("#memberId").val();
	            var userId=$("#userId").val();
	            
	         
             	$.post("${contextPath}/userOrder/submitDistribution",{orderCode:orderCode,memberId:memberId,userId:userId,time:new Date().getTime()},function(result){
                	if(result == true){
                		parent.location.reload();
                		window.parent.window.jBox.close();
                	}else{
                		jBox.tip("订单分派失败", 'info');
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
						<input name="orderCode" id="orderCode" readonly type="text" class="form-control" value="${orderBase.orderCode}" />
						<input name="memberId" id="memberId" readonly type="hidden" class="form-control" value="${orderBase.memberId}" />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">下单客户账号：</label>
					<div class="col-sm-4">
						<input name="memberName" id="memberName" readonly type="text" class="form-control" value="${orderBase.memberName}" />
					</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">请选择要分派的客服姓名：</label>
					<div class="col-sm-5">
                          <select name="userId" class="form-control lg-select" id="userId">
								<c:forEach items="${userList}" var="user">
									<option value="${user.id}" ${userId==user.id?'selected':'' }>${user.loginName}</option>
								</c:forEach>
						  </select>
                    </div>
				</div>
				
			<div class="form-group mL0 mR0">
		    	<div class="col-sm-offset-4 col-sm-8">
		      	    <button class="btn btn-primary" type="submit" >确定</button>
		   	 	</div>
			</div>
		</form>
	 </div>
   </body>	
</html>