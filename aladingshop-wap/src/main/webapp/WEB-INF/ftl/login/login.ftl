<!DOCTYPE html>
<html>
    <head>
        <title>登录</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>登录</p>
                <a href="javascript:toRegister();" class="a-right">
                    <span class="edit">注册</span>
                </a>
            </div>

            <form id="loginForm" class="form-group">
                <div class="input-group">
                    <span class="user"></span>
                    <input name="userName" id="userName" type="text" placeholder="请输入您的账号" autocomplete="off">
                </div>
                <div class="input-group">
                    <span class="lock"></span>
                    <input name="password" id="password" type="password" placeholder="请输入密码" autocomplete="off">
                </div>
                <div class="auto-login">
                    <input type="checkbox" id="auto-login">
                    <label for="auto-login">自动登录</label>
                </div>
                <a href="javascript:toForget();" class="forgot-password">忘记密码？</a>

                <div class="btn-wrap">
                    <button id="loginBtn" type="submit">立即登录</button>
                </div>
            </form>
        </div><!-- /.container -->


        <script src="${rc.contextPath}/static/shop/farmwork/validate/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#loginForm').validate({
                    focusInvalid: false,
                    rules: {
                        userName: {
                            required: true,
                        },
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        userName: {
                            required: "请填写用户名！",
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    submitHandler: function(form) {
                    	var userName=$.trim($("#userName").val());
           				var password=$("#password").val();
           				$.ajax({
						    type: "POST",
						    url: _ContextPath + '/login/login',
						    data: {'userName':userName,'password':password,'time':new Date().getTime()},
							async:false,
						    dataType: "json",
						    success: function (data) {
						    	if (data.responseCode == "500") {
						    		alert(data.msg);
						    		return;
								} else {
							        /*  成功后的操作 开始  */
							        window.location=_ContextPath + data.data;
							        /*  成功后的操作 结束  */
								}
						      }
						  });
                    },
                });

                $('#loginBtn').click(function(event) {
                    $('input.error').parents('.input-group').velocity('callout.shake');
                });

            });
            
            function toRegister(){
            	$.post(_ContextPath+'/login/clickRegister',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/login/toRegister?time="+new Date().getTime();
						} else {
							alert(data.msg);
						}
					}
				);
            }
            
            function toForget(){
            	$.post(_ContextPath+'/login/clickForget',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/login/toForget?time="+new Date().getTime();
						} else {
							alert(data.msg);
						}
					}
				);
            }
            
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
