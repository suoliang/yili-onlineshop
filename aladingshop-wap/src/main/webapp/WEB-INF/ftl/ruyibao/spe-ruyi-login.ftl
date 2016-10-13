<!DOCTYPE html>
<html>
    <head>
        <title>如意消费卡</title>
         <!-- 公共样式js引用-->
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
                <p>如意消费卡</p>
                <a href="javascript:toRegister();" class="a-right">
                    <span class="">注册</span>
                </a>
            </div>

            <form id="ruyiLoginForm" class="form-group">
                <div class="input-group">
                    <span class="phone"></span>
                    <input name="userPhone" type="number" placeholder="请输入手机号码" id="userPhone" autocomplete="off">
                </div>
                <div class="input-group">
                    <span class="lock"></span>
                    <input id="password" name="password" type="password" id="password" placeholder="请输入密码" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input type="checkbox">
                            <i></i>
                        </div>
                    </div>
                </div>

                <div class="btn-wrap">
                    <button id="loginBtn" type="submit">登录</button>
                </div>
            </form>

        </div><!-- /.container -->

        <script src="${rc.contextPath}/static/shop/farmwork/validate/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#ruyiLoginForm').validate({
                    focusInvalid: false,
                    rules: {
                        userPhone: {
                            required: true,
                            rangelength: [11, 11]
                        },
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        userPhone: {
                            required: "请填写手机号码！",
                            rangelength: "请输入11位手机号码！"
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    submitHandler: function(form) {
                         $.post("${rc.contextPath}/ruyibao/login",{acount:$('#userPhone').val(),password:$('#password').val(),time: new Date().getTime()},function(result){
					         if(result.responseCode==201){
					         	 if(confirm("登录如意宝需先登录阿拉丁账号，是否立即登录？")){
								 	location.href="${rc.contextPath}/login/index";
								 	return;
								 }
					         }else if(result.responseCode !=0){
					         	alert(result.msg);
					         	return;
					         }
					         
					         location.href="${rc.contextPath}/ruyibao/mainShow?time="+new Date().getTime();
						});
                    },
                });

                $('#loginBtn').click(function(event) {
                    $('input.error').parents('.input-group').velocity('callout.shake');
                });

                /*显示/隐藏密码*/
                $('#ruyiLoginForm').find('.ios-btn-wrap').click(function() {
                    var thisStatus = $(this).find('input').attr('checked');
                    if (thisStatus) {
                        $('#password').attr('type', 'text');
                    } else {
                        $('#password').attr('type', 'password');
                    }
                });

            });
            
            /**到如意宝注册页*/
            function toRegister(){
            	$.post(_ContextPath+'/login/clickRegister',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/ruyibao/toRuyiRegister?time="+new Date().getTime();
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
