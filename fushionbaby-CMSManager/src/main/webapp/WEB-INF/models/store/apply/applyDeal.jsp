<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>开店申请处理</title>
	<script type="text/javascript">
	$(document).ready(function() {
		/*表单验证*/
	    var validator = $('#handleForm').validate({
	        rules: {
	        	intention: {
	                required: true
	            },
	            memo: {
	                required: true
	            }
	        },
	        messages: {
	        	intention: {
	                required: "请选择开店意向"
	            },
	            memo: {
	                required: "请填写备注"
	            }
	        },
	        
	        submitHandler: function(form) {
	     
	            var id=$("#id").val();
	            var memo=$("#memo").val();
	            var intention=$("#intention").val();
             	$.post("${contextPath}/sysStoreApp/submitUpdate",{id:id,memo:memo,intention:intention,time:new Date().getTime()},function(result){
                	if(result == true){
                		parent.location.reload();
                		window.parent.window.jBox.close();
                	}else{
                		jBox.tip("开店申请处理失败或您还未登录", 'info');
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
					<label class="col-sm-4 control-label">开店申请人姓名：</label>
					<div class="col-sm-4">
						<input name="name" id="name" readonly type="text" class="form-control" value="${storeApply.name}" />
						<input name="id"  id="id" type="hidden" class="form-control" value="${storeApply.id}"/>
					</div>
				</div>
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">联系人电话：</label>
					<div class="col-sm-4">
						<input name="phone" id="phone" readonly type="text" class="form-control" value="${storeApply.phone}" />
					</div>
				</div>
			
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">备注：</label>
					<div class="col-sm-4">
						<input name="memo" id="memo"  type="text" class="form-control" value="${storeApply.memo}" />
					</div>
				</div>
			
		
				<div class="form-group mL0 mR0">
					<label class="col-sm-4 control-label">开店意向：</label>
					<div class="col-sm-5">
                          <select name="intention" class="form-control lg-select" id="intention">
							    <!-- 	1 测试  2有意向（考虑中）  3 已开店 4  废弃  -->
								<option value="1" <c:if test="${storeApply.intention == '1' }"> selected="selected" </c:if> >测试数据</option>
								<option value="2" <c:if test="${storeApply.intention == '2' }"> selected="selected" </c:if> >有意向（考虑中）</option>
								<option value="3" <c:if test="${storeApply.intention == '3' }"> selected="selected" </c:if> >已开店</option>
								<option value="4" <c:if test="${storeApply.intention == '4' }"> selected="selected" </c:if> >废弃</option>
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