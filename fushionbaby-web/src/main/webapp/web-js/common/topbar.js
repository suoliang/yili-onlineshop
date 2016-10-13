//页面最上面的头文件
$(document).ready(function(){
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
					$("#get_userName").html(data.data.login_name);
					$(".st-bdl").hide();//已经登录隐藏登录注册板块
					$(".uesrname-wrapper").show(); //显示欢迎您,用户名
				}
			}
		}
	});
});

//用户点击快速注册
function quickRegister(){
	$.ajax({
		type:"POST",
		url: _ContextPath+'/login/clickRegister.do',
		async:false,
		dataType:"json",
		success: function(data){
			if (data.responseCode == "0"){
				window.location.href=_ContextPath +"/login/toRegister.do";
			} else {
				alert(data.msg);
			}
		}
	});
}

//用户点击退出登录
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
