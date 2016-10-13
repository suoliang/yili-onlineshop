<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>会员添加</title>
	 <script type="text/javascript">
	 
	 /**添加操作*/
		function addMember(){
			var member_name =$.trim($("#memberName").val());
			var member_password =$.trim($("#memberPassword").val());
			if (!/^1\d{10}$/.test(member_name)) {
				$("#memberName").parent().siblings('.warning').html('用户名必须是正确的手机号！');
				return;
			}
			if (member_password.length == 0) {
				$("#memberPassword").parent().siblings('.warning').html('初始密码请不要为空或有空格，以免发送短信给用户，用户不能确定！');
				return ;
			}
			//输入的密码不能小于6位
			if (member_password.length < 6) {
				$("#memberPassword").parent().siblings('.warning').html('密码不少于6位！');
				return ;
			}
			
			//输入的密码不能超过16位
			if (member_password.length > 16) {
				$("#memberPassword").parent().siblings('.warning').html('密码不超过16位！');
				return ;
			}
			$.post('${pageContext.request.contextPath}/member/addMember',
					{member_name:member_name,member_password:member_password,time:new Date().getTime()},
				function(data){
					if (data.result == "success") {
						jBox.tip("添加成功!", 'info');
						window.setTimeout(function () {  
							window.parent.window.location.reload();
							window.parent.window.jBox.close();
							
						}, 500);
					}else{
						jBox.tip("添加失败!", 'info');
						window.setTimeout(function () { 
							window.parent.window.location.reload();
							window.parent.window.jBox.close();
						}, 500);
					}
			});
			
		}
	 </script>
</head>
 <body id="index"  style="background:#fff">
   	<tags:message content="${message }"></tags:message>
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  
				      <form class="form-horizontal" method="post"  id="handleForm">
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">会员名(手机号)：</label>
							<div class="col-sm-4">
								<input name="member_name" id="memberName" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">会员密码：</label>
							<div class="col-sm-4">
								<input type="password" name="member_password" id="memberPassword" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      		<button class="btn btn-success col-md-2" type="button"  onclick="addMember()">确定</button>
					   	 	</div>
						</div>
						
					</form>
       		</div>
       	
       	  </div>
       	</div>
   </body>
</html>