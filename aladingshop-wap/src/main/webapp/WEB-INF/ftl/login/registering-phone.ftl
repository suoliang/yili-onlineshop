<!DOCTYPE html>
<html>
    <head>
        <title>注册</title>
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
                <p>注册</p>
                <a href="${rc.contextPath}/login/index" class="a-right">
                    <span class="edit">登录</span>
                </a>
            </div>

            <form id="registeringForm" class="form-group">
                <div class="input-group key-btn-wrap">
                    <span class="phone"></span>
                    <input name="userPhone" id="userPhone" type="number" placeholder="请输入手机号码" autocomplete="off">
                    <button type="button" onclick="dotime(this)" class="key-btn">获取验证码</button>
                </div>
                <div class="input-group">
                    <span class="key"></span>
                    <input name="key" id="key" type="number" placeholder="请输入验证码" autocomplete="off">
                </div>
                <div class="input-group">
                    <span class="lock"></span>
                    <input id="password" name="password" type="password" placeholder="请输入密码" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input type="checkbox">
                            <i></i>
                        </div>
                    </div>
                </div>
                <div class="input-group bonus">
                    <input type="text" id="bonusNum" value="${code!''}" placeholder="(选填)请填写您的邀请码">
                    <a href="${rc.contextPath}/register/toBonus" class="question"></a>
                </div>
                <p class="password-tip">密码由6-20位字母，数字或符号组成</p>
                <div class="agreen">
                    <input type="checkbox" id="agreen">
                    <label for="agreen">同意阿拉丁用户协议</label>
                </div>

                <div class="btn-wrap">
                    <button id="registeringBtn" type="submit">注册</button>
                </div>
                <input type="hidden" id="registerNum">
            </form>

        </div><!-- /.container -->

        <script src="${rc.contextPath}/static/shop/farmwork/validate/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#registeringForm').validate({
                    focusInvalid: false,
                    rules: {
                        userPhone: {
                            required: true,
                            rangelength: [11,11]
                        },
                        key: {
                            required: true,
                            rangelength: [6,6]
                        },
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        userPhone: {
                            required: "请填写手机号！",
                            rangelength: "请输入11位手机号码！"
                        },
                        key: {
                            required: '请填写验证码！',
                            rangelength: '验证码为6位数字！'
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    submitHandler: function(form) {
                    	var userPhone=$.trim($("#userPhone").val());
           				var key=$.trim($("#key").val());
           				var password=$("#password").val();
           				var registerNum=$("#registerNum").val();
           				var bonusNum = $.trim($("#bonusNum").val());
           				if(bonusNum.length == 0){
	           				$.ajax({
							    type: "POST",
							    url: _ContextPath + '/register/registerMobile',
							    data: {'userPhone':userPhone,'key':key,'password':password,'registerNum':registerNum,'time':new Date().getTime()},
								async:false,
							    dataType: "json",
							    success: function (data) {
							    	if (data.responseCode == "500") {
							    		alert(data.msg);
							    		return;
									} else {
								        /*  成功后的操作 开始  */
								        window.location=_ContextPath +"/home?time="+new Date().getTime();
								        /*  成功后的操作 结束  */
									}
							      }
							  });
           				} else {
           					$.ajax({
							    type: "get",
							    url: _ContextPath + '/activity/share/shareRegister',
							    data: {'phone':userPhone,'smsCode':key,'password':password,'registerCode':registerNum,'inviteCode':bonusNum,'time':new Date().getTime()},
								async:false,
							    dataType: "json",
							    success: function (data) {
							    	if (data.responseCode == "500") {
							    		alert(data.msg);
							    		return;
									} else {
								        /*  成功后的操作 开始  */
								        window.location=_ContextPath +"/home?time="+new Date().getTime();
								        /*  成功后的操作 结束  */
									}
							      }
							  });
           				}
                    },
                });

                $('#registeringBtn').click(function(event) {
                    $('input.error').parents('.input-group').velocity('callout.shake');
                });

                /*显示/隐藏密码*/
                $('#registeringForm').find('.ios-btn-wrap').click(function(event) {
                    var thisStatus = $(this).find('input').attr('checked');
                    if (thisStatus) {
                        $('#password').attr('type', 'text');
                    } else {
                        $('#password').attr('type', 'password');
                    }
                });

            });
            
            /**获取验证码操作*/
			function dotime(obj){
				var userPhone = $.trim($('#userPhone').val());
				if(userPhone.length == 0 || !/^1\d{10}$/.test(userPhone)){
					alert("手机号不正确");
					return;
				}
				$.post(_ContextPath+'/register/sendCode',
					{userPhone:userPhone,time:new Date().getTime()}
					,function(data){
						if (data.responseCode == "500") {
						    alert(data.msg);
							return;
						} else {
							/**将标识码设置到隐藏域*/
							$("#registerNum").val(data.data);
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
			        $(btn).html("获取验证码");
			        wait = 60;
			    } else {
			        btn.setAttribute("disabled", true);
			        $(btn).html(wait + "秒");
			        wait--;
			        setTimeout(function () {
			            time(btn);
			        },
			        1000);
			    }
			}

        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
