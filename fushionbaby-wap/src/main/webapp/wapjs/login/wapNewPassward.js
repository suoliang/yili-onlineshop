$(function(){
   /*  表单提交，最终验证  */
   $("#new_passward_btn").click(function() {
	    $(".required").trigger('blur');
	    var numError = $('form .has-error').length;
	    if (numError) {
	      return false;
	    }
	    var confirmPassword = $("#passwards_repeat_2").val();   
	    $.post(_ContextPath+'/forget/setNewPassword.do', 
				{
					password:confirmPassword,
					time:new Date().getTime()
				},
			function(data){
				if (data.responseCode == "500") {
					$(".showErrorMsg").text(data.msg);
					$(".showErrorMsg").show();
					$(".showErrorMsg").fadeOut(5000);
					return;
				} 
				/*  alert("成功.");  */
			    $(this).addClass('public_loading');
			
			    /*  成功后的操作 开始  */
			    $('.public_modal_backup').fadeIn();/* 显示对话框  */
			    /*  确定按钮  */
			    $(".modal_confirm").click(function() {
			       $('.public_modal_backup').fadeOut();
			       $("#new_passward_btn").removeClass('public_loading');
			       window.location.href = _ContextPath + "/login/index.do";
			    });
			   /*  成功后的操作 结束  */
			}
		);
   });
});