/**获取验证码操作*/
function dotime(obj){
	$('.phone_input').focus();
  	var numError = $('form .has-error').length;
  	if (numError) {
   		return false;
  	}
	var phone = $.trim($('.phone_input').val());
	$.post(_ContextPath+'/register/registerSendMobileCode.do',
		{telephone:phone,time:new Date().getTime()}
		,function(data){
			if (data.responseCode == "500") {
			    $('.phone_registered').text(data.msg);
			    $('.phone_registered').show();
				$('.phone_registered').fadeOut(5000);
				return;
			} else {
				/**将标识码设置到隐藏域*/
				$("#registerVerification").val(data.data);
				time(obj);
			}
		}
	);
}
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
            time(btn);
        },
        1000);
    }
}

$(function(){
	$("#registered_btn").attr("disabled",false);
    /*  表单提交，最终验证  */
    $("#registered_btn").click(function() {
	      $(".required").trigger('blur');
	      var numError = $('form .has-error').length;
	      if (numError) {
	        return false;
	      }
	      var phone = $.trim($('.phone_input').val());
	      var registerConfirmPassword = $("#passwards_repeat_2").val();
	      var userInputSmscode = $.trim($(".validation_input").val());
	      /**获得隐藏域的标识码值*/
	      var registerVerification = $("#registerVerification").val();
	      //手机注册
		  $.ajax({
		      type: "POST",
		      url:_ContextPath+'/register/registerMobile.do',
		      data: {'telephone':phone,
					 'password':registerConfirmPassword,
					 'userInputSmscode':userInputSmscode,
					 'registerVerification':registerVerification,
					 },
				async:false,
		        dataType: "json",
		        success: function (data) {
		        	if (data.responseCode == "500") {
		        		$('.phone_registered').text(data.msg);
					    $('.phone_registered').show();
						$('.phone_registered').fadeOut(5000);
						return;
					} else {
						/*  alert("成功.");  */
					    $(this).addClass('public_loading');
					
					    /*  成功后的操作 开始  */
					    $('.public_modal_backup').fadeIn();/** 显示对话框*/
					    /*  确定按钮  */
					    $(".modal_confirm").click(function() {
					       /*  do something  */
					       window.location=_ContextPath +"/index/indexList.do?time="+new Date().getTime();
					    });
					    /*  成功后的操作 结束  */
						$("#registered_btn").attr("disabled",true);
					}
		      	}
		   });	
	  });
  });