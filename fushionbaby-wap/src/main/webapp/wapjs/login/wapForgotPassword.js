/***************60s后重新获取验证码********************/
var wait = 60;
function time(btn) {
    if (wait == 0) {
        btn.removeAttribute("disabled");
        btn.value = "获取验证码";
        wait = 60;
    } else {
        btn.setAttribute("disabled", true);
        btn.value = wait + "秒后重新获取验证码";
        wait--;
        setTimeout(function () {
            time(btn);//按钮60s后可以操作
        },
        1000);
    }
}

/**点击"获取验证码"时的操作*/
function getForgotCode(obj){
	$('.user_input').focus();
  	var numError = $('form .has-error').length;
  	if (numError) {
   		return false;
  	}
  	var userInputLoginName = $.trim($('.user_input').val());
	$.post(_ContextPath+'/forget/getForgetCode.do',
			{phoneOrEmail:userInputLoginName,time:new Date().getTime()},
		function(data){
			if (data.responseCode == "500") {
				$(".showErrorMsg").text(data.msg); 
				$(".showErrorMsg").show();         
				$(".showErrorMsg").fadeOut(5000);  
				return;
			} else {
				/**将标识码放在隐藏域*/
				$("#forgetVerification").val(data.data);
				time(obj);//按钮60s后可以操作
			}
		}
	);
}

$(function(){
    /*  表单提交，最终验证  --用户点击"下一步"的操作*/
    $("#forgot_passward_btn").click(function() {
	      $(".required").trigger('blur');
	      var numError = $('form .has-error').length;
	      if (numError) {
	        return false;
	      }
	      var phoneOrEmail = $.trim($(".user_input").val());
	      var inputCode = $.trim($(".validation_input").val());
	      var forgetVerification = $("#forgetVerification").val();
	      /**判断用户输入的验证码是否正确*/
	  	  $.post(_ContextPath+'/forget/checkVerfiyCode.do',
	  			{	phoneOrEmail:phoneOrEmail,
	  				inputCode:inputCode,
	  				forgetVerification:forgetVerification,
	  				time:new Date().getTime()
	  			},
	  		function(data){
  				if (data.responseCode == "500") {
  					$(".showErrorMsg").text(data.msg);
  					$(".showErrorMsg").show();
  					$(".showErrorMsg").fadeOut(5000);
  					return;
  				} else {
  					 /*  alert("成功.");  */
  					$(this).addClass('public_loading');

  					/*  成功后的操作 开始  */
  					window.location= _ContextPath + "/forget/toConfirmPassword.do";
  					/*  成功后的操作 结束  */
  				}
	  		}
	  	);//Ajax end
    });
});
