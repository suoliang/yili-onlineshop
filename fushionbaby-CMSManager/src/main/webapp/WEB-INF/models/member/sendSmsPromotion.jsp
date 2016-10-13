<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>促销短信</title>
	 <script type="text/javascript">
		 function validate_content(content){
				if (content.length == 0) {
					jBox.tip("必须输入短信内容","error");
					return false;
				}
				if (content.length > 300) {
					jBox.tip("短信内容尽量不要超过300字，如果必须请联系后台开发","error");
					return false;
				}
				var scheduled_status = content.indexOf("【阿拉丁玛特】");
				if (scheduled_status != 0) {
					jBox.tip("短信内容请以【阿拉丁玛特】开头","error");
					return false;
				}
				var s = content.replace('【阿拉丁玛特】','');
				if (s.length == 0) {
					jBox.tip("除了短信头，短信内容必须输入","error");
					return false;
				}
				return true;
			}
		
		//促销短信的发送
			function sms_Promotion(){
				//促销短信的内容
				var promotion_content = $.trim($("#get_promotion_content").val());
				var flag = validate_content(promotion_content);
				if (flag == false) {
					return;
				}
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/memberTelephone/sms_promotion.do",
					async:false,
					dataType : "json",
					data:'content='+promotion_content,
					success: function(data){
						if (data.responseCode == '0') {
							$("#content").text("短信发送成功"+data.data.sendSuccess+"条！失败"+data.data.sendFail+"条！");
						} else {
							$("#content").text(data.msg);
						}		
					}
				});
			}
		</script>
	 
</head>
 <body id="index"  style="background:#fff">
        <div class="container-fluid" >
            <div class="row">
			  <div class="col-md-10 content" id="content">
				      <form class="form-horizontal" method="post">
						<div class="form-group">
							<label class="col-sm-2 control-label">短信内容：</label>
							<div class="col-sm-2">
								<textarea rows="3" cols="58" id="get_promotion_content">【阿拉丁玛特】</textarea>
							</div>
							<div class="col-sm-2">
	  							<strong>你选择了${smsLength}条记录，点击确定就会发送短信，是否确认发送?</strong>
	  						</div>
						</div>
						<div class="col-md-4">
	                        <button type="button" class="btn btn-success"  onclick="sms_Promotion()">确定</button> 
				    	</div>
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>
