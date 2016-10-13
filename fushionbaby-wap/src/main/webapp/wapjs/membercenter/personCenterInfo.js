$(function(){
	/****加载登录信息**/
	$.ajax({
		type:"POST",
		url: _ContextPath + '/login/getUserInfo.do?time='+new Date().getTime(),
		async:false,
		dataType:"json",
		data:new Date().getTime(),
		success: function(data){
			if (data.responseCode == "0") {
				if (data.data.login_name != null) {
					$(".p1").html(data.data.login_name);
					$("#userPoints").html(data.data.epoints);
					$("#gradeName").html(data.data.gradeName);
				}
			}
		}
	});
});

/**签到*/
function signIn() {
	$.ajax({
		type:"POST",
		url: _ContextPath+'/memberSign/sign.do?time='+new Date().getTime(),
		async:false,
		data:new Date().getTime(),
		dataType:"json",
		success: function(data){
			if (data.responseCode == "0") {
				$("#signMsg").html("签到成功");
				$("#signMsg").show();
				$("#signMsg").fadeOut(5000);
				$("#userPoints").html(data.data);
			}else {
				$("#signMsg").html(data.msg);
				$("#signMsg").show();
				$("#signMsg").fadeOut(5000);
			}
		}
	});
}

/**用户点击退出登录*/
function loginOut(){
	$.ajax({
		type:"POST",
		url:_ContextPath + '/login/loginOut.do',
		async:false,
		dataType:"json",
		success: function(data){
			if (data.responseCode == "0") {
				window.location.href = _ContextPath + '/login/index.do';
			}
		}
	});
}
