//忘记密码操作
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

/**校验后显示信息*/
function showValidation(name,msg){
	$("#check_"+name).html(msg);
	$("#show_"+name).show();
	$("#show_"+name).fadeOut(5000);
}

/**点击"获取验证码"时的操作*/
function getBackPassword(obj){
	//输入手机号或邮箱
	var back_password = $("#back_password").val();
	
	if (back_password.length == 0) {
		showValidation("back_password","请输入手机号或邮箱");
		return;
	}
	//手机或邮箱正则匹配
	var n1 = /^1\d{10}$/.test(back_password);
	var n2 = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/.test(back_password);
	if(!((n1==true && n2 ==false) || (n1==false && n2 == true))){
		showValidation("back_password","请输入正确的手机号或邮箱格式");
		return;
	}
	
	$.post(_ContextPath+'/forget/getForgetCode.do',
			{phoneOrEmail:back_password,time:new Date().getTime()},
		function(data){
			if (data.responseCode == "500") {
				showValidation("back_password",data.msg);
				return;
			} else {
				time(obj);//按钮60s后可以操作
			}
		}
	);
}

/***用户点击"下一步"时的操作*/
function nextStep(){
	//输入手机号或邮箱
	var back_password = $("#back_password").val();
	//输入验证码
	var back_password_code = $("#back_password_code").val();
	
	if (back_password.length == 0) {
		showValidation("back_password","请输入手机号或邮箱");
		return;
	}
	
	if (back_password_code.length == 0) {
		showValidation("back_password_code","请输入验证码");
		return;
	}
	//判断用户输入的验证码是否正确
	$.post(_ContextPath+'/forget/checkVerfiyCode.do',
			{	phoneOrEmail:back_password,
				input_code:back_password_code,
				time:new Date().getTime()
			},
		function(data){
				if (data.responseCode == "500") {
					showValidation("back_password_code",data.msg);
					return;
				} else {
					window.location.href= _ContextPath + "/forget/toConfirmPassword.do";
				}
		}
	);//Ajax end
}

/************找回Fushionbaby密码页面操作************/
function getbackPassword(){
	var new_password = $("#new_password").val();
	var confirm_password = $("#confirm_password").val();
	
	if (new_password.length == 0) {
		showValidation("new_password","请输入新的密码");
		return;
	}
	//输入的密码不能小于6位
	if (new_password.length < 6) {
		showValidation("new_password","新密码不少于6位");
		return;
	}
	//输入的密码不能超过16位
	if (new_password.length > 16) {
		showValidation("new_password","新密码不超过16位");
		return;
	}
	
	if (confirm_password.length == 0) {
		showValidation("confirm_password","请输入确认密码");
		return;
	}
	
	if (new_password != confirm_password) {
		showValidation("new_password","两次输入的密码不一致");
		return;
	}
	
	$.post(_ContextPath+'/forget/setNewPassword.do', 
			{
				password:confirm_password,
				time:new Date().getTime()
			},
			function(data){
				if (data.responseCode == "0") {
					$(".popup").show();
				} else {
					showValidation("new_password",data.msg);
				}
			}
	);
}

//密码修改完成跳转到登录页面
function toLogin(){
	window.location.href = _ContextPath + "/login/index.do";
}
