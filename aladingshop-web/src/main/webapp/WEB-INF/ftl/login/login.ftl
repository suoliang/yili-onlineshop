<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特商城首页</title>
         <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body>
	    <script type="text/javascript"> 
		     if(top!=self){
		         if(top.location != self.location)
		            top.location=self.location; 
		     }
		</script>
        <div class="container oh login-box">
            <a class="login-logo" href="http://www.aladingshop.com" title="返回首页">
                <span></span>
            </a>
            <div class="login-left fl">
                <h3>
                    <p>登录</p>
                    <a href="${rc.contextPath}/login/toRegister.htm">注册</a>
                </h3>
              <!--  <form id="loginForm" >-->
                    <div class="input-wrap user">
                        <input name="userName" id="userName"  class="default-input" type="text" placeholder="邮箱 / 手机号" value="${userName}">
                        <span></span>
                    </div>
                    <div class="input-wrap lock" id="password_div">
                        <input name="password" id="password" class="default-input" type="password" placeholder="请输入密码" value="${password}">
                        <span></span>
                        <div id="error_message"></div>
                    </div>
                    
                    <div class="checkbox-wrap fl wp100">
                        <label for="remember">
                            <input id="remember" type="checkbox" >
                        	记住我
                        </label>
                        <a class="fr" href="${rc.contextPath}/forget/toResetPassword.htm">忘记密码</a>
                    </div>
                    <button class="btn-theme" id="login">登&emsp;录</button>
               <!-- </form>-->
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
                        <li class="alipay" id="ZFB_login">
                            <a href="${rc.contextPath}/otherLogin/loginAsZFB.htm"></a>
                            <p>支付宝</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="login-right fl">
                <a href="http://www.aladingshop.com"><img src="${rc.contextPath}/static/shop/picture/login.jpg" height="500" width="455" alt=""></a>
            </div>
            <p class="copyright">版权所有@2015 上海一里网络科技有限公司</p>
        </div>

        <!-- javascript -->
        <script src="${rc.contextPath}/static/shop/js/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script src="${rc.contextPath}/static/shop/js/velocity.min.js"></script>
        <script src="${rc.contextPath}/static/shop/js/velocity.ui.min.js"></script>
       <script>
          $("#login").click(function(){
           $('.default-input.error').parents('.input-wrap').velocity('callout.shake');
           var userName=$.trim($("#userName").val());
           var password=$("#password").val();
           
           if (userName.length == 0) {
             $("#error_message").html('<p class="red">请输入用户名</p>');
             $("#error_message").show();
             return false;
		     }
           
			if (password.length == 0) {
            $("#error_message").html('<p class="red">请输入密码</p>');
             $("#error_message").show();
             return false;
			}
		$.ajax({
            type: "POST",
            url:'${rc.contextPath}/login/main',
            data: {'userName':userName,'password':password,'time':new Date().getTime()},
			async:false,
	        dataType: "json",
            success: function (data) {
            	if (data.responseCode == "500") {
                   $("#error_message").html('<p class="red">'+data.msg+'</p>');
                   $("#error_message").show();
                   return false;
				} else {
				    var url=data.data;
					window.location.href ="${rc.contextPath}"+url;
				}
            }
        });	
          });
       </script>
       
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#testloginForm').validate({
                    rules: {
                        userName: {
                            required: true,
                            rangelength: [6, 20],  
                            remote: {
	                            type:"Post",
	                            url:"${rc.contextPath}/login/checkIsExit.htm",
	                            data:{
	                              userName:function(){return $("#userName").val();},
	                              status:"login",
	                              time:function(){return new Date().getTime();}
	                              }/** end  data*/
                            }/** end remote*/
                        },/** end userName*/
                        password: {
                            required: true,
                            rangelength: [6, 16],
                            remote: {
	                            type:"Post",
	                            url:"${rc.contextPath}/login/checkPassword.htm",
	                            data:{
	                              password:function(){return $("#password").val();},
	                              time:function(){return new Date().getTime();},
	                              userName:function(){return $("#userName").val();}
	                            }/** end  data*/
                            }/** end remote*/
                        }/** end password*/
                    },/** end rules*/
                    
                    messages: {
                        userName: {
                            required: "请填写用户名！",
                            rangelength: "账户名长度应该为6-20位！",
                            remote:jQuery.validator.format("该用户不存在！")
                          
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-16位！",
                            remote:jQuery.validator.format("密码输入有误，或者登录用户不合法！")
                        }
                    },/** end messages*/
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    },/** end  submitHandler*/
                    
                });/**end validator*/
            });/** end ready*/
        </script>
    </body>
</html>
