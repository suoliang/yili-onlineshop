<!DOCTYPE html>
<html>
    <head>
        <title>重置密码</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="forgotPassword">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>重置密码</p>
            </div>

            <form id="forgotForm" class="form-group">
                <div class="input-group key-btn-wrap">
                    <input name="userPhoneEmail" id="userPhoneEmail" type="text" placeholder="请输入手机号码/邮箱" autocomplete="off">
                    <button type="button" onclick="dotime(this)" class="key-btn">获取验证码</button>
                </div>
                <div class="input-group">
                    <input name="key" id="key" type="number" placeholder="请输入验证码" autocomplete="off">
                </div>

                <p class="password-tip">如有问题，请<a class="red" href="tel:4000021060">联系客服</a></p>

                <div class="btn-wrap">
                    <button id="forgotBtn" type="submit">下一步</button>
                </div>
                <input type="hidden" id="forgetpasswordNum">
            </form>

        </div><!-- /.container -->

        <a class="registering-new" href="javascript:toRegister();">注册新用户</a>

        <script src="${rc.contextPath}/static/shop/farmwork/validate/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/

                /*添加自定义验证 邮箱或手机号*/
                jQuery.validator.addMethod("phoneOrEmail", function(value, element) {
                    var tel = /(^1[0-9]{10}$)|(^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
                    return this.optional(element) || (tel.test(value));
                }, "请填写正确的手机号或邮箱");

                var validator = $('#forgotForm').validate({
                    focusInvalid: false,
                    rules: {
                        userPhoneEmail: {
                            required: true,
                            phoneOrEmail:true
                        },
                        key: {
                            required: true,
                            rangelength: [6,6]
                        }
                    },
                    messages: {
                        userPhoneEmail: {
                            required: "请填写邮箱或手机号！"
                        },
                        key: {
                            required: '请填写验证码！',
                            rangelength: '验证码为6位数字！'
                        }
                    },
                    submitHandler: function(form) {
                        var forgetpasswordNum = $("#forgetpasswordNum").val();
                        var userPhoneEmail = $.trim($("#userPhoneEmail").val());
                        var key = $.trim($("#key").val());
                        $.ajax({
						    type: "POST",
						    url: _ContextPath + '/forgot/checkVerfiyCode',
						    data: {'phoneOrEmail':userPhoneEmail,'inputCode':key,'forgotNum':forgetpasswordNum,'time':new Date().getTime()},
							async:false,
						    dataType: "json",
						    success: function (data) {
						    	if (data.responseCode == "500") {
						    		alert(data.msg);
						    		return;
								} else {
							        /*  成功后的操作 开始  */
							        window.location.href = _ContextPath + "/forgot/toReset?time="+new Date().getTime();
							        /*  成功后的操作 结束  */
								}
						     }
						});
                    },
                });

                $('#forgotBtn').click(function(event) {
                    $('input.error').parents('.input-group').velocity('callout.shake');
                });

            });
            
            /**获取验证码操作*/
			function dotime(obj){
				var userPhoneEmail = $.trim($('#userPhoneEmail').val());
				var tel = /(^1[0-9]{10}$)|(^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$)/;
				if(userPhoneEmail.length == 0 || !tel.test(userPhoneEmail)){
					alert("请填写正确的手机号或邮箱");
					return;
				}
				$.post(_ContextPath+'/forgot/getForgetCode',
					{phoneOrEmail:userPhoneEmail,time:new Date().getTime()}
					,function(data){
						if (data.responseCode == "500") {
						    alert(data.msg);
							return;
						} else {
							/**将标识码设置到隐藏域*/
							$("#forgetpasswordNum").val(data.data);
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
            
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
