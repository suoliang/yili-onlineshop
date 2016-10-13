function showValidation(name,msg){
	$("#check_"+name).html(msg);
	$("#show_"+name).show();
	$("#show_"+name).fadeOut(5000);
}
/**修改密码*/
function change(){
	var oldPwd = $("#oldPwd").val();
	var newPwd1 = $("#newPwd1").val();
	var newPwd2 = $("#newPwd2").val();
	if (oldPwd.length == 0) {
		showValidation("old_pwd","请输入旧密码");
		return;
	}
	if (newPwd1.length == 0) {
		showValidation("new_pwd1","请输入新密码");
		return;
	}
	if (newPwd1.length < 6) {
		showValidation("new_pwd1","新密码请不少于6位");
		return;
	}
	if (newPwd1.length > 16) {
		showValidation("new_pwd1","新密码请不超过16位");
		return;
	}
	if (newPwd2.length == 0) {
		showValidation("new_pwd2","请输入确认新密码");
		return;
	}
	if (newPwd1 != newPwd2) {
		showValidation("new_pwd1","两次输入的密码不一致");
		return;
	}
	$.ajax({
		type:"post",
		async:false,
		url:_ContextPath+"/changePassword/confirmChange.do?time="+new Date().getTime(),
		data:{'oldPwd':oldPwd,'password':newPwd2},
		dataType:"json",
		success:function(data){
			if (data.responseCode == "500") {
				showValidation("old_pwd",data.msg);
				return;
			} else {
				$('#resMsg').html("密码修改成功");
				$('.pass-check').show();
			}
		}
	});
}
function myOk(){
    //window.parent.location.href = _ContextPath +"/index/index.do"; 
    window.parent.location.href = _ContextPath +"/login/index.do"; 
};