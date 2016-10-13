<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>如意宝账号编辑</title>
<script type="text/javascript">

$(document).ready(function() {
	
	/*表单验证*/
    var validator = $('#editForm').validate({
        rules: {
        	
        	trueName: {
                required: true,
               
            },
            identityCardNo: {
                required: true,
               
            },
        },
        messages: {
        	trueName: {
                required: "请输入真实姓名！",
                
            },
            identityCardNo: {
                required: "请输入身份证号！",
                
            },
        }
    });
});


</script>
</head>
<body id="index" class="backWhite">

       <div class="container-fluid">
           <div class="row">
         
		  <div class="col-md-12 ">
		     <div class="panel">
               
			 <div class="panel-body">
			     <form class="form-horizontal" id="editForm" method="post" >
			     
			     	<div class="form-group">
						<label class="col-sm-3 control-label">如意宝账号：</label>
						<div class="col-sm-5">
							<input name="account" id="account" class="form-control" value="${alabaoAccount.account}" readonly/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label">真实姓名：</label>
						<div class="col-sm-5">
							<input name="trueName" id="trueName" type="text" class="form-control" value="${alabaoAccount.trueName}"/>
							
						</div>
						<div class="error col-sm-3  textL">必填项</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">身份证号：</label>
						<div class="col-sm-5">
							<input name="identityCardNo" id="identityCardNo" type="text" class="form-control" value="${alabaoAccount.identityCardNo}"/>
						</div>
						<div class="error col-sm-3  textL">必填项</div>
					</div>
			     
			</form>
		    </div>
		   </div>
		  </div>
       </div>
     </div>
</body>
</html>