<div id="loginBox">
    <div class="login-left fl">
        <h1>您尚未登录 <span id="close"></span></h1>
        <h3>
            <a href="${rc.contextPath}/login/toRegister.htm">注册</a>
        </h3>
        <form id="loginForm">
            <div class="input-wrap user">
                <input name="userName" id="userName" class="default-input" type="text" placeholder="邮箱 / 手机号" autocomplete="off">
                <span></span>
            </div>
            <div class="input-wrap lock">
                <input name="password" id="password" class="default-input" type="password" placeholder="请输入密码">
                <span></span>
            </div>
            <div class="checkbox-wrap fl">
                <label for="remember">
                    <input id="remember" type="checkbox">记住我
                </label>
                <a href="${rc.contextPath}/forget/toResetPassword.htm">忘记密码</a>
            </div>
            <button class="btn-theme">登&emsp;录</button>
        </form>
        <div class="other-login fl">
            <h5>第三方登录</h5>
            <ul>
                <li class="qq">
                    <a href="${rc.contextPath}/otherLogin/loginAsQQ.htm"></a>
                    <p>QQ</p>
                </li>
                <li class="weichat">
                    <a href="${rc.contextPath}/otherLogin/loginAsWX.htm"></a>
                    <p>微信</p>
                </li>
                <li class="sina">
                    <a href="${rc.contextPath}/otherLogin/loginAsSina.htm"></a>
                    <p>新浪微博</p>
                </li>
                <li class="alipay">
                    <a href="${rc.contextPath}/otherLogin/loginAsZFB.htm"></a>
                    <p>支付宝</p>
                </li>
            </ul>
        </div>
    </div>
</div>

<script src="${rc.contextPath}/static/shop/js/jquery.validate.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
<script>
$(document).ready(function() {
    /*表单验证*/
    var validator = $('#loginForm').validate({
        rules: {
            userName: {
                required: true,
                rangelength: [6, 20],
                remote: {
                    url: "${rc.contextPath}/login/checkIsExit.htm?v=${EnvironmentConstant.DEPLOY_VERSION}",
                    type: "post",
                    data: {
	                	 userName: function() {
                            return $("#userName").val();
	                     },
	                     status : "login", 
	                     loginTime: function() {
	                        return +new Date;
	                     }
                    }
                }
            },
            password: {
                required: true,
                rangelength: [6, 16],
                 remote: {
                    url: "${rc.contextPath}/login/checkPassword.htm?v=${EnvironmentConstant.DEPLOY_VERSION}",
                    type: "post",
                    data: {
	                	 userName: function() {
                            return $("#userName").val();
	                     },
	                     password: function() {
                            return $("#password").val();
	                     },
	                     loginTime: function() {
	                        return +new Date;
	                     }
                    }
                }
            }
        },
        messages: {
            userName: {
                required: "请填写用户名！",
                rangelength: "账户名长度应该为6-20位！",
                remote: "用户名不存在！"
            },
            password: {
                required: "请填写密码！",
                rangelength: "密码长度应该为6-16位！",
                remote: "用户密码错误！"
            }
        },
        submitHandler: function(form) {
        	$('#loginBox').find('.login-left').animate({'margin-left': '-30%', 'opacity': '0'}, 300);
        	$('#loginBox').fadeOut(100);
            $.post("${rc.contextPath}/login/quickLogin",{userName:$("#userName").val(), password: $("#password").val()},function(result){
            		if(result.data.loginStatus == "2"){
            			$("#logined").html("");
            			var templete = "<li><a class='user-icon' href='' title='进入个人中心'>VIP "+result.data.loginName+"</a></li><li><a href='javascript:loginOut();'>&emsp;退出登录</a></li>";
					 	$("#logined").html(templete);
            			//登录成功后的后续操作，后续可执行一次控件click事件触发和一次函数执行
				        try{
							loginFun();
						}catch(err){
							/*当前无需执行后续fun*/;
						}
				        
            			if($('#opeationDom')){
            				var domName = $('#opeationDom').val();
	            			$('#opeationDom').val("");
	            			$(domName).click();	
            			}
            			
            			return;
            		}
            		
            		alert("登录异常，异常代码："+result.data.loginStatus+"，请稍后重新登录。");
            		console.log(result.data.errorMsg);
          	});
        },
    });
});
</script>