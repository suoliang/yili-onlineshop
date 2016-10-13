<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>编辑余额</title>
	 <script type="text/javascript">
	 
	 </script>
	 
</head>
 <body id="index"  style="background:#fff">
   
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-6 content" >
			  
				      <form class="form-horizontal" method="post"  id="accountForm">
						
						
						<div class="form-group">
							<label class="col-sm-3 control-label">账号：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="account" name="account" disabled="disabled"
						 		 value="${alabaoAccount.account }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">真实姓名：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="trueName" name="trueName" disabled="disabled"
						 		 value="${alabaoAccount.trueName }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">证件号：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control" id="identityCardNo" name="identityCardNo" disabled="disabled"
						 		 value="${alabaoAccount.identityCardNo }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">锁定余额：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control txt-zero" id="lockedBalance" name="lockedBalance"
						 		 onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   								onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"  
						 		 value="${alabaoAccount.lockedBalance }">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label">当前余额：</label>
							<div class="col-sm-3">
						 		 <input type="text" class="form-control txt-zero" id="balance" name="balance" 
						 		 onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   								onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"  
						 		 value="${alabaoAccount.balance }">
							</div>
						</div>
						
						
						
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>