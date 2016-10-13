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
                <a href="${rc.contextPath}/ruyibao/index" class="a-right">
                    <span class="">登录</span>
                </a>
            </div>

            <form id="registeringForm" class="form-group">
                <div class="input-group key-btn-wrap">
                    <span class="phone"></span>
                    <input name="userPhone" id="userPhone" type="number" placeholder="请输入手机号码" autocomplete="off">
                </div>
                <div class="input-group">
                    <span class="lock"></span>
                    <input id="loginPassword" name="password" type="password" placeholder="请输入登录密码" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input type="checkbox">
                            <i></i>
                        </div>
                    </div>
                </div>
                <div class="input-group mB10">
                    <span class="lock"></span>
                    <input id="payPassword" name="password_2" type="password" placeholder="请输入支付密码" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input type="checkbox">
                            <i></i>
                        </div>
                    </div>
                </div>
                <div class="input-group">
                    <span class="user"></span>
                    <input name="userName" id="userName" type="text" placeholder="请输入您的真实姓名" autocomplete="off">
                </div>
                <div class="input-group">
                    <input name="userCardId" id="userCardId" type="number" placeholder="请输入身份证号码" autocomplete="off">
                </div>
                <div class="input-group key-btn-wrap mB10">
                    <input name="key" id="key" type="number" placeholder="请输入验证码" autocomplete="off">
                    <button type="button" onclick="dotime(this)" class="key-btn">获取验证码</button>
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
                        },
                        password_2: {
                            required: true,
                            rangelength: [6,6]
                        },
                        userName: {
                            required: true,
                            rangelength: [2, 8]
                        },
                        userCardId: {
                            required: true
                        }
                    },
                    messages: {
                        userPhone: {
                            required: "请填写手机号！",
                            rangelength: "请输入11位手机号码！"
                        },
                        key: {
                            required: '请填写用验证码！',
                            rangelength: '验证码为6位数字！'
                        },
                        password: {
                            required: "请填写登录密码！",
                            rangelength: "密码长度应该为6-20位！"
                        },
                        password_2: {
                        	required: "请填写支付密码！",
                            rangelength: "密码长度应该为6位！"
                        },
                        userName: {
                            required: "请填写姓名！",
                            rangelength: "用户名长度应该为2-8位！"
                        },
                        userCardId: {
                            required: "请填写身份证号！"
                        }
                    },
                    submitHandler: function(form) {
                        var userPhone=$.trim($("#userPhone").val());
           				var loginPassword=$("#loginPassword").val();
           				var payPassword=$("#payPassword").val();
           				var userName=$.trim($("#userName").val());
           				var userCardId=$.trim($("#userCardId").val());
           				var registerNum=$("#registerNum").val();
           				var key=$.trim($("#key").val());
           				
					    var regex = /^([1-9]\d*|[0]{1,1})$/;
				        if (!regex.test(payPassword)) {
				            alert("支付密码请输入6位数字");
				         	return;
					    }
           				$.ajax({
						    type: "POST",
						    url: _ContextPath + '/ruyibao/openAlabao',
						    data: {'userPhone':userPhone,
						    		'loginPassword':loginPassword,
						    		'payPassword':payPassword,
						    		'userName':userName,
						    		'userCardId':userCardId,
						    		'registerNum':registerNum,
						    		'key':key,
						    		'time':new Date().getTime()},
							async:false,
						    dataType: "json",
						    success: function (data) {
						    	if (data.responseCode == "500") {
						    		alert(data.msg);
						    		return;
								} else {
							        /*  成功后的操作 开始  */
							        window.location=_ContextPath +"/ruyibao/index?time="+new Date().getTime();
							        /*  成功后的操作 结束  */
								}
						      }
						  });
                    },
                });

                /*显示/隐藏密码*/
                $('#registeringForm').find('.ios-btn-wrap').each(function(index, el) {
                    $(this).click(function(event) {
                        var thisStatus = $(this).find('input').attr('checked');
                        if (thisStatus) {
                            $(this).siblings('input').attr('type', 'text');
                        } else {
                            $(this).siblings('input').attr('type', 'password');
                        }
                    });
                });

            });
            
            
            /**获取验证码操作*/
			function dotime(obj){
				var userPhone = $.trim($('#userPhone').val());
				if(userPhone.length == 0 || !/^1\d{10}$/.test(userPhone)){
					alert("手机号不正确");
					return;
				}
				$.post(_ContextPath+'/ruyibao/getAlabaoSmsCode',
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
