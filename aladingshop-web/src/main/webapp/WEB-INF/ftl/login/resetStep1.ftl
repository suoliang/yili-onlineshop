<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 找回密码</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body id="resetPassword">
    
      <script >
	    function sendSecurityCode(obj){
	        var userName=$("#userName").val();
	        var smsType="register";
	        var number=$("#index_code").val();
	        var userNameClass=$("#userName").prop("class");
	        if(userName!=""&&userName!=null&&userNameClass.indexOf("error")<0){
	        if(number!=""&&number!=null){
	          $.post("${rc.contextPath}/register/sendSecurityCode",{userName:userName,smsType:smsType,number:number,time:new Date().getTime()},function(data){
	             if(data.responseCode==0){
	              timeOut(obj);
	             }else{
	                $("#send-code").val("发送失败，请刷新重试");
	                location.reload();
	                }
	            })
	        }else{
	           alert("请输入正确的验证码！");
	        }
	        }else{
	           alert("请输入正确的用户名！");
	        }
	     }
	     
	        var wait = 60;
		function timeOut(btn) {
		    if (wait == 0) {
		        btn.removeAttribute("disabled");
		        btn.value = "获取验证码";
		        wait = 60;
		    } else {
		        btn.setAttribute("disabled", true);
		        btn.value = wait + "秒后重新获取";
		        wait--;
		        setTimeout(function () {
		            timeOut(btn);
		        },
		        1000);
		    }
		}
     </script>
     <script type="text/javascript">
       function changeImg() {
		    var imgSrc = $("#imgObj");
		    var src = imgSrc.attr("src");
		    imgSrc.attr("src", chgUrl(src));
		  }
		  //时间戳   
		  //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳   
		  function chgUrl(url) {
		    var timestamp = (new Date()).valueOf();
		    url = url.substring(0, 30);
		    if ((url.indexOf("&") >= 0)) {
		      url = url + "×tamp=" + timestamp;
		    } else {
		      url = url + "?timestamp=" + timestamp;
		    }
		    return url;
		  }
      </script>
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
                    <li class="active">1.输入您的账号<span></span></li>
                    <li>2.账户确认及密码重置<span></span></li>
                    <li>3.重置密码成功</li>
                </ul>
            </div>
            <form id="resetPasswordForm" method="post" action="${rc.contextPath}/forget/toResetPassword2.htm">
                <div class="input-wrap user">
                    <input name="userName" id="userName" class="default-input" type="text" placeholder="请输入您的手机号" autocomplete="off">
                    <span></span>
                </div>
                <div class="input-wrap code">
	                    
	                       <!-- <td>验证码</td>-->
	                        <input id="index_code" name="number" type="text" class="default-input"  placeholder="输入右侧验证码"/>
	                       
		                        <img id="imgObj" alt="验证码" src="${rc.contextPath}/code/code.htm" onclick="javascript:changeImg()"/>
		                          <!--<a onclick="javascript:changeImg()">换一张</a>-->
	                        
                    </div>
                <div class="input-wrap code">
                    <input name="code" id="code" class="default-input" type="text" autocomplete="off" placeholder="输入手机验证码">
                    <!--<a onclick="javascript:sendSecurityCode();" id="send-code">发送验证码</a>
                    -->
                    <input  type="button" id="send-code" onclick="sendSecurityCode(this)"  value="获取手机验证码">
                </div>
                <button class="btn-theme">下一步</button>
            </form>
        </div>
        <p class="copyright">版权所有@2015 上海一里网络科技有限公司</p>

        <!-- javascript -->
        <script src="${rc.contextPath}/static/shop/js/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script src="${rc.contextPath}/static/shop/js/velocity.min.js"></script>
        <script src="${rc.contextPath}/static/shop/js/velocity.ui.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/

                /*添加自定义验证 邮箱或手机号*/
                jQuery.validator.addMethod("phoneOrEmail", function(value, element) {
                    var tel = /^1([34578])\d{9}$/;
                    return this.optional(element) || (tel.test(value));
                }, "请填写正确的手机号");

                var validator = $('#resetPasswordForm').validate({
                    rules: {
                        userName: {
                            required: true,
                            phoneOrEmail:true,
                            remote: {
	                            type:"Post",
	                            url:"${rc.contextPath}/login/checkIsExit.htm",
	                            data:{
	                              userName:function(){return $("#userName").val();},
	                              status:"login",
	                              time:function(){return new Date().getTime();}
	                              }/** end  data*/
                            }/** end remote*/
                        },
                         number:{
                            required: true,
                            remote: {
                                type:"Post",
	                            url:"${rc.contextPath}/code/codeCheck.htm",
	                            data:{
	                              number:function(){return $("#index_code").val();},
	                              time:function(){return new Date().getTime();}
                                  }
                               }
                        },
                        code:{
                            required: true,
                            digits:true,
                            rangelength: [6, 6],
                            remote: {
                                type:"Post",
	                            url:"${rc.contextPath}/register/checkSecurityCode.htm",
	                            data:{
	                              code:function(){return $("#code").val();},
	                              status:"forget",
	                              time:function(){return new Date().getTime();}
                                  }/** end data*/
                               }/** end remote*/
                        }
                    },
                    messages: {
                        userName: {
                            required: "请填写用户名！",
                            remote:jQuery.validator.format("该用户不存在!")
                        }, 
                        number:{
                            required: "请输入图片验证码",
                            rangelength: "验证码为4位",
                            remote:jQuery.validator.format("验证码不正确，请重新输入")
                        },
                        code:{
                            required: "请输入验证码",
                            digits:"请输入6位数字",
                            rangelength: "验证码为6位数字",
                            remote:jQuery.validator.format("验证码输入错误")
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                         $(form).ajaxSubmit();
                    },
                });
            });
        </script>
    </body>
</html>
