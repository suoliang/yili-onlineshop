<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 找回密码</title>
       <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body id="resetPassword">

        <div class="oh login-box">
            <div class="container">
                <a class="login-logo" href="http://www.aladingshop.com" title="返回首页">
                    <span></span>
                </a>
            </div>
        </div>

        <div id="resetPassword-wrap" class="container oh">
            <div class="tit">
                <span>找回密码</span>
                <ul>
                    <li>1.输入您的账号<span></span></li>
                    <li class="active">2.账户确认及密码重置<span></span></li>
                    <li>3.重置密码成功</li>
                </ul>
            </div>
            <form id="resetPasswordForm" method="post" action="${rc.contextPath}/forget/resetFinish.htm">
                <div class="input-wrap lock">
                    <input id="password" name="password" class="default-input" type="password" placeholder="密码由6-20位字母，数字或符号组成" autocomplete="off">
                    <span></span>
                    <input name="userName" value="${userName}" type="hidden">
                </div>
                <div class="input-wrap lock">
                    <input name="passwordAgain" class="default-input" type="password" placeholder="请确认密码" autocomplete="off">
                    <span></span>
                </div>
                <button class="btn-theme">下一步</button>
            </form>
        </div>
        <p class="copyright">版权所有@2015 上海一里网络科技有限公司</p>

        <!-- javascript -->
        <script src="${rc.contextPath}/static/shop/js/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/

                var validator = $('#resetPasswordForm').validate({
                    rules: {
                        password: {
                            required: true,
                            rangelength: [6, 16]
                        },
                        passwordAgain: {
                            equalTo:"#password"
                        }
                    },
                    messages: {
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码由6-16位字母，数字或符号组成！"
                        },
                        passwordAgain: {
                            equalTo:"两次输入密码不一致！"
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                        //window.location.href = "";
                        console.log($(form).serialize());
                         $(form).ajaxSubmit();
                    },
                });
            });
        </script>
    </body>
</html>
