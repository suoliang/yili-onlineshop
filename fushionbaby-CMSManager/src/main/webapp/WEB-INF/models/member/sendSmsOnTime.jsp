<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->    


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>定时短信</title>
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
		
		 	/**定时短信*/
			function timing_sms(){
				var time = $.trim($("#get_send_time").val());
				if (time.length == 0) {
					jBox.tip("请选择输入发送时间","error");
					return;
				}
				//定时短信的内容
				var scheduled_content = $.trim($("#get_scheduled_content").val());
				var flag = validate_content(scheduled_content);
				if (flag == false) {
					return;
				}
				$.ajax({
					type:'post',
					url:'${pageContext.request.contextPath}/memberTelephone/sms_Scheduled.do',
					async:false,
					dataType:'json',
					data:'time='+time+'&content='+scheduled_content,
					success:function(data){
						if (data.responseCode == '0') {
							$("#content").text("短信将会在指定的时间发送");
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
							<label class="col-sm-2 control-label">发送时间：</label>
							<div class="col-sm-2">
								<div class="input-group" style="width:50%;">
                                    <input type="text" class="form-control" readonly id="get_send_time" style="background:url(${pageContext.request.contextPath}/static/bootstrap/images/calendar.png) no-repeat right center;">
                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                </div>
                                <strong style="color: red">时间格式为:年年年年月月日日时时分分秒秒</strong>
							</div>
							<BR>	  							
							<label class="col-sm-2 control-label">短信内容：</label>
							<div class="col-sm-2">
								<textarea rows="3" cols="58" id="get_scheduled_content">【阿拉丁玛特】</textarea>
							</div>
							<BR>
							<strong>你选择了${smsLength}条记录，定时时间请设置在30分钟以后，点击确定之后短信会在你设置的时间提前一段时间发送，点击确定之后不能取消发送</strong>
						</div>
						<div class="col-md-4">
	                        <button type="button" class="btn btn-success"  onclick="timing_sms()">确定</button> 
				    	</div>
					</form>
       		</div>
       	  </div>
       	</div>
 	<script>
 	$(function(){
		 $("#get_send_time").datetimepicker({
		        format: "yyyymmddhhiiss",
		        autoclose: true,
		        /*选择完自动关闭弹窗*/
		        todayBtn: true,
		        /*显示今日按钮*/
		        pickerPosition: "bottom-left",
		        /*弹窗位置*/
		        startDate: "2013-02-14",
		        /*开始时间*/
		        language: "zh-CN",
		        /*中文*/
		        minView: "0" /*精确度:天*/
		    }); 
		 
		 $('.form_datetime_addon').click(function(){
			 $(this).siblings('input').val("");
		 })
	 })
 	</script>
   </body>
</html>
