<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>审核不通过</title>
	<script type="text/javascript">
	$(document).ready(function() {
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	memo: {
	                required: true,
	                rangelength: [2, 10]
	            }
	        },
	        messages: {
	        	memo: {
	                required: "请输入退款密码！",
	                rangelength: "原因长度在2-10之间"
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
	            var json=JSON.stringify(o); 
	         
             	$.post("${contextPath}/alabaoRefund/submitRefund",{json:json},function(result){
                	if(result == true){
                		parent.location.reload();
                		window.parent.window.jBox.close();
                	}else{
                		jBox.tip("如意消费卡退款失败", 'info');
                	}
              	}); 
	        }
	    });
	});
	
	
	function checkMoney(obj){
		var money=parseFloat(obj.value);
		var total='${orderBase.paymentTotalActual}';
		total=parseFloat(total);
		if(money>total){
			alert("您输入的金额不正确");
			$("#paymentTotalActual").val(total);
		}
	}
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
					<label class="col-sm-4 control-label">请输入退款金额：</label>
					<div class="col-sm-4">
						<input name="paymentTotalActual" id="paymentTotalActual" type="text" onblur="javascript:checkMoney(this);" class="form-control" value="${paymentTotalActual }" />
					</div>
					<div class="error col-sm-4 textL">金额不大于（${orderBase.paymentTotalActual}）元</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">输入密码：</label>
					<div class="col-sm-4">
						<input name="memo" id="memo" type="text" class="form-control" value="${memo }" />
					</div>
					<div class="error col-sm-4 textL">必填项</div>
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