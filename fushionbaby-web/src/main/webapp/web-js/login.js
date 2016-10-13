/**登录页面的JS验证*/
$(document).ready(function(){
	/** 登录页面的广告图片加载*/
	var login_ad_url=$("#login_ad_url").val();
	var ad_link=$("#ad_link").val();
	if(login_ad_url!=null && login_ad_url!=""&&login_ad_url!="null"){
		$("#ad_url").attr("src",login_ad_url);
		$("#ad_link").attr("href",ad_link);
	}
	/**登录按钮*/
	$("#login").click(function() {
		var loginName = $.trim($("input[name='email']").val());
		var password = $("input[name='password']").val();
		
		if (loginName.length == 0) {
			showValidation("login_name","请输入用户名");
			return;
		}
		
		if (password.length == 0) {
			showValidation("login_password","请输入密码");
			return; 
		}
		
		$.ajax({
            type: "POST",
            url: _ContextPath + '/login/login.do',
            data: {'login_name':loginName,'password':password,'time':new Date().getTime()},
			async:false,
	        dataType: "json",
            success: function (data) {
            	if (data.responseCode == "500") {
            		showValidation("login_name",data.msg);
				} else {
					window.location.href = _ContextPath +"/index/index.do?time="+new Date().getTime();
				}
            }
        });	
	});
	
	//IE，火狐都支持  -- 回车登录
	 $("input[name='password']").keydown(function(e){ 
         var curKey = e.which; 
         if(curKey == 13){ 
             $("#login").click(); 
             return false; 
         } 
     }); 
	
	
	/***忘记密码按钮*/
	$(".fn").click(function(){
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
	
	/**注册按钮*/
	$("#register").click(function(){
	
		$.post(_ContextPath+'/login/clickRegister.do',
			{time:new Date().getTime()},
			function(data){
				if (data.responseCode == "0"){
					window.location.href=_ContextPath +"/login/toRegister.do?time="+new Date().getTime();
				} else {
					alert(data.msg);
				}
			}
		);
	});
});

/**校验后显示的信息*/
function showValidation(name,msg){
	$("#check_"+name).html(msg);
	$("#show_"+name).show();
}

/** qq登录*/
$("#qq_login_picture").click(function (){
	window.document.location.href=_ContextPath+"/login/loginAsQQ.do?time="+new Date().getTime();
 });

/** weixin*/ 
$("#weixin_login_picture").click(function (){
	var appid=$("#wx_appid").val();
	var redirectUrl=$("#wx_url").val();
	var scope="snsapi_login";
	var state=new Date().getTime();
window.document.location.href="https://open.weixin.qq.com/connect/qrconnect?appid="+appid+"&redirect_uri="+redirectUrl+"&response_type=code&scope="+scope+"&state="+state+"#wechat_redirect";
});
/** sina*/
$("#sina_login_picture").click(function (){
window.location.href=_ContextPath+"/login/loginAsSina.do?time="+new Date().getTime();
});

/** sina*/
$("#ZFB_login_picture").click(function (){
window.location.href=_ContextPath+"/login/loginAsZFB.do?time="+new Date().getTime();
});



