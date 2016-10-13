/**注册页面的JS验证*/
$(document).ready(function(){
	$("#agree_register").attr("disabled",false);
	//同意注册按钮的点击事件
	$("#agree_register").click(function(){
		//获得隐藏域的值
		var who=$("#reg_who").val();
		if(who == "ph"){
			/**手机*/
			var register_mobile = $.trim($("#register_mobile").val());
			var register_smscode = $.trim($("#register_smscode").val());
			var register_setpassword = $("#register_setpassword").val();
			var register_confirmpassword = $("#register_confirmpassword").val();
			//获得隐藏域的值
			var register_verification = $("#register_verification").val();
			/************手机验证****************/
			if (!/^1\d{10}$/.test(register_mobile)) {
				showValidation("register_mobile","请输入正确的手机号");
				return;
			}
			
			if (register_smscode.length == 0) {
				showValidation("register_smscode","请输入验证码");
				return;
			}
			
			if (register_setpassword.length == 0) {
				showValidation("register_setpassword","请设置密码");
				return;
			}
			//输入的密码不能小于6位
			if (register_setpassword.length < 6) {
				showValidation("register_setpassword","密码不少于6位");
				return;
			}
			
			//输入的密码不能超过16位
			if (register_setpassword.length > 16) {
				showValidation("register_setpassword","密码不超过16位");
				return;
			}
			
			if (register_confirmpassword.length == 0) {
				showValidation("register_confirmpassword","请输入确认密码");
				return;
			}
			
			if (register_setpassword != register_confirmpassword) {
				showValidation("register_setpassword","两次输入的密码不一致");
				return;
			}
			
			//手机注册
			$.ajax({
	            type: "POST",
	            url:_ContextPath+'/register/registerMobile.do',
	            data: {'telephone':register_mobile,
					 'password':register_confirmpassword,
					 'register_smscode':register_smscode,//用户输入的验证码
					 'register_verification':register_verification,//唯一标识码
					 },
				async:false,
		        dataType: "json",
	            success: function (data) {
	            	if (data.responseCode == "500") {
	            		showValidation("register_smscode",data.msg);
						return;
					} else {
						fun();//注册成功,5s后跳转到个人中心
						$("#agree_register").attr("disabled",true);
					}
	            }
	        });	
		}else if(who == "po"){
			/**邮箱*/
			var register_email = $.trim($("#register_email").val());
			var register_setEmailPassword = $("#register_setEmailPassword").val();
			var register_confirmEmailPassword = $("#register_confirmEmailPassword").val();
			var register_emailcode = $.trim($("#register_emailcode").val());
			/************邮箱验证****************/
			if (register_email.length == 0) {
				showValidation("register_email","请输入电子邮箱");
				return;
			}
			
			if (register_setEmailPassword.length == 0) {
				showValidation("register_setEmailPassword","请设置密码");
				return;
			}
			//输入的密码不少于6位
			if (register_setEmailPassword.length < 6) {
				showValidation("register_setEmailPassword","密码不少于6位");
				return;
			}
			//输入的密码不超过16位
			if (register_setEmailPassword.length > 16) {
				showValidation("register_setEmailPassword","密码不超过16位");
				return;
			}
			
			if (register_confirmEmailPassword.length == 0) {
				showValidation("register_confirmEmailPassword","请输入确认密码");
				return;
			}
			
			if (register_setEmailPassword != register_confirmEmailPassword) {
				showValidation("register_setEmailPassword","两次输入的密码不一致");
				return;
			}
			
			if (register_emailcode.length == 0) {
				showValidation("register_emailcode","请输入验证码");
				return;
			}
			//邮箱的正则匹配
			if (!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/.test(register_email)) {
				showValidation("register_email","请输入正确的电子邮箱");
				return;
			}
			//邮箱的长度
			if (register_email.length > 60) {
				showValidation("register_email","电子邮箱长度不能超过60位");
				return;
			}
			
			//邮箱注册
			$.post(_ContextPath+'/register/registerEmail.do',
				{
				email:register_email,
				password:register_confirmEmailPassword,
				register_emailcode:register_emailcode,
				time:new Date().getTime()
				}
				,function(data){
					if (data.responseCode == "500") {
						if (data.msg.length == 7) {//验证码输入不正确的长度
							showValidation("register_emailcode",data.msg);//验证码输入不正确
							return;
						} else {
							showValidation("register_email",data.msg);//提示用户此邮箱已注册
							return;
						}
					} else {
						fun();//注册成功,5s后跳转到个人中心
						$("#agree_register").attr("disabled",true);
					}
				}
			);
		}//ph or po if else end
	});//同意注册click end
	
});



/********************换一张验证码******************************/
function changeImage(picid){
	$("#change_picture").attr("src",$("#change_picture").attr("src")+"?time = " + new Date().getTime());
}

/**校验后显示的信息*/
function showValidation(name,msg){
	$("#check_"+name).html(msg);
	$("#show_"+name).show();
	$("#show_"+name).fadeOut(5000);
}

/************************register 信息填写正确跳转********************/
//5S后跳转到新的页面
var num;
function fun() {	
	num=parseInt($("#go").html());
	num--;
	$("#go").html(num);
	if(typeof($(".ar-wrap").css('display', 'block')))
	{	
		console.log(typeof($(".ar-wrap").css('display', 'block')));
		if(num>0)
		{
			setTimeout(fun,1000);
		}
		else
		{
			jumpMemberCenter();//跳转到个人中心
		}
	}
}			

//注册成功页面确定按钮的跳转
function jumpMemberCenter(){
	// -- 手机
	var register_mobile = $.trim($("#register_mobile").val());
	var register_confirmpassword = $("#register_confirmpassword").val();
	// -- 邮箱
	var register_email = $.trim($("#register_email").val());
	var register_confirmEmailPassword = $("#register_confirmEmailPassword").val();
	//手机
	if (/^1\d{10}$/.test(register_mobile)) {
		$.ajax({
            type: "POST",
            url: _ContextPath + '/login/login.do',
            data: {'login_name':register_mobile,'password':register_confirmpassword},
			async:false,
	        dataType: "json",
            success: function (data) {
            	if (data.responseCode == "0") {
            		window.location.href= _ContextPath +"/membercenter/center.do?base";//跳转到个人中心的基础信息
				} 
            }
        });	
	} 
	//邮箱
	if (/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/.test(register_email)) {
		$.ajax({
            type: "POST",
            url: _ContextPath + '/login/login.do',
            data: {'login_name':register_email,'password':register_confirmEmailPassword},
			async:false,
	        dataType: "json",
            success: function (data) {
            	if (data.responseCode == "0") {
            		window.location.href= _ContextPath +"/membercenter/center.do?base";//跳转到个人中心的基础信息
				} 
            }
        });	
	}
}


/********用户在注册页点击立即登录按钮********/
function clickLogin(){
	window.location.href = _ContextPath +"/login/index.do";
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
/******手机注册 ----- 点击获取验证码*/
function dotime(obj){
	var register_mobile = $("#register_mobile").val();
	if (!/^1\d{10}$/.test(register_mobile)) {
		showValidation("register_mobile","请输入正确的手机号");
	} else {
		$.post(_ContextPath+'/register/registerSendMobileCode.do',
			{telephone:register_mobile,time : new Date().getTime()}
			,function(data){
				if (data.responseCode == "500") {
					showValidation("register_mobile",data.msg);//提示手机号码已被注册
				} else {
					$("#register_verification").val(data.data);//将标识码设置到隐藏域
					time(obj);
				}
			}
		);
	}
}

//手机注册时给隐藏域设值
function reg_mobile(){
	$("#reg_who").val("ph");
}

//邮箱注册时给隐藏域设值
function reg_email(){
	$("#reg_who").val("po");
}