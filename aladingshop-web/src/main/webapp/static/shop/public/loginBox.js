var loginBox = ''+
'<div id="loginBox">'+
    '<div class="login-left fl">'+
        '<h1>您尚未登录 <span id="close"></span></h1>'+
        '<h3>'+
            '<a href="registered.html">注册</a>'+
        '</h3>'+
        '<form id="loginForm">'+
            '<div class="input-wrap user">'+
                '<input name="userName" class="default-input" type="text" placeholder="邮箱 / 手机号">'+
                '<span></span>'+
            '</div>'+
            '<div class="input-wrap lock">'+
                '<input name="password" class="default-input" type="password" placeholder="请输入密码">'+
                '<span></span>'+
            '</div>'+
            '<div class="checkbox-wrap fl">'+
                '<label for="remember">'+
                    '<input id="remember" type="checkbox">'+
                    '记住我'+
                '</label>'+
                '<a href="reset-password-step1.html">忘记密码</a>'+
            '</div>'+
            '<button class="btn-theme">登&emsp;录</button>'+
        '</form>'+
        '<div class="other-login fl">'+
            '<h5>第三方登录</h5>'+
            '<ul>'+
                '<li class="qq">'+
                    '<a href=""></a>'+
                    '<p>QQ</p>'+
                '</li>'+
                '<li class="weichat">'+
                    '<a href=""></a>'+
                    '<p>微信</p>'+
                '</li>'+
                '<li class="sina">'+
                    '<a href=""></a>'+
                    '<p>新浪微博</p>'+
                '</li>'+
                '<li class="alipay">'+
                    '<a href=""></a>'+
                    '<p>支付宝</p>'+
                '</li>'+
            '</ul>'+
        '</div>'+
    '</div>'+
'</div>'+
'<script src="js/jquery.validate.min.js"></script>';
document.write(loginBox);

$(document).ready(function() {
    /*表单验证*/
    var validator = $('#loginForm').validate({
        rules: {
            userName: {
                required: true,
                rangelength: [2, 10]/*,
                remote: {
                    url: "remote.json",
                    type: "post",
                    data: {
                        loginTime: function() {
                            return +new Date;
                        }
                    }
                }*/
            },
            password: {
                required: true,
                rangelength: [2, 10]
            }
        },
        messages: {
            userName: {
                required: "请填写用户名！",
                rangelength: "账户名长度应该为2-10位！"/*,
                remote: "用户名不存在！"*/
            },
            password: {
                required: "请填写密码！",
                rangelength: "密码长度应该为2-10位！"
            }
        },
        submitHandler: function(form) {
            // 验证成功后操作
            console.log($(form).serialize());
            $(form).ajaxSubmit();
        },
    });
});

