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
	                rangelength: [2, 30]
	            }
	        },
	        messages: {
	        	memo: {
	                required: "请填写不通过原因！",
	                rangelength: "原因长度在2-30之间"
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
             	$.post("${contextPath}/alabaoTurnOut/updateStatusToFail",{jsonStr:jsonStr},function(result){
                	if(result == "SUCCESS"){
                		parent.location.reload();
                		window.parent.window.jBox.close();
                	}else{
                		jBox.tip("审核不通过失败", 'info');
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
					<label class="col-sm-4 control-label">账户名称：</label>
					<div class="col-sm-4">
						<input name="account" id="account" readonly type="text" class="form-control" value="${alabaoTurnOut.account}" />
						<input name="id" id="id" readonly type="hidden" class="form-control" value="${alabaoTurnOut.id}" />
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">持卡人姓名：</label>
					<div class="col-sm-4">
						<input name="cardHolder" id="cardHolder" readonly type="text" class="form-control" value="${alabaoTurnOut.cardHolder}" />
					</div>
				</div>
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">原因：</label>
					<div class="col-sm-4">
						<input name="memo" id="memo" type="text" class="form-control" value="${alabaoTurnOut.memo}" />
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