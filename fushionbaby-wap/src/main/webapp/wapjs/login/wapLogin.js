$(function(){
	/*  表单提交，最终验证  */
	$("#login_btn").click(function() {
	  $(".required").trigger('blur');
	  var numError = $('form .has-error').length;
	  if (numError) {
	    return false;
	  }
	  var loginName = $.trim($("input[name='loginName']").val());
	  var password = $("#passwards_input").val();
	  $.ajax({
	    type: "POST",
	    url: _ContextPath + '/login/login.do',
	    data: {'loginName':loginName,'inputPassword':password,'time':new Date().getTime()},
		async:false,
	    dataType: "json",
	    success: function (data) {
	    	if (data.responseCode == "500") {
	    		$("#showErrorMsg").text("用户名密码不匹配");
	    		$("#showErrorMsg").show();
	    		$("#showErrorMsg").fadeOut(5000);
	    		return;
			} else {
				 /*  alert("成功.");  */
		          $(this).addClass('public_loading');
		
		          /*  成功后的操作 开始  */
		          window.location=_ContextPath +"/index/indexList.do?time="+new Date().getTime();
		          /*  成功后的操作 结束  */
			}
	      }
	  });	
	});
	/**注册按钮*/
	$(".public_btn_r").click(function(){
		$.post(_ContextPath+'/login/clickRegister.do',
		{time:new Date().getTime()},
		function(data){
				if (data.responseCode == "0"){
					window.location.href=_ContextPath +"/login/toRegister.do?time="+new Date().getTime();
				} else {
					$("#showErrorMsg").text(data.msg);
		    		$("#showErrorMsg").show();
		    		$("#showErrorMsg").fadeOut(5000);
				}
			}
		);
	});
	/**忘记密码按钮*/
	$("#forgotPassword").click(function(){
		$.post(_ContextPath+'/login/clickForgetPassword.do',
				{time:new Date().getTime()}
			,function(data){
				if (data.responseCode == "0"){
					window.location.href= _ContextPath+"/login/toForgetPassword.do?time="+new Date().getTime();
				} else {
					alert(data.msg);
				}
			}
		);
	});
})