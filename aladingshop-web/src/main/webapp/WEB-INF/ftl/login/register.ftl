<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 注册</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
  
 <body>
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
     
     </script>
        <div class="container oh login-box">
            <a class="login-logo" href="http://www.aladingshop.com" title="返回首页">
                <span></span>
            </a>
            <div class="login-left fl">
                <h3>
                    <p>注册</p>
                    <a href="${rc.contextPath}/login/index.htm">登录</a>
                </h3>
                <form id="registeredForm" action="${rc.contextPath}/register/register.htm" method="post">
                    <div class="input-wrap user">
                        <input name="userName" id="userName" class="default-input" type="text" placeholder="请输入手机号码" >
                        <span></span>
                    </div>
                    <div class="input-wrap lock">
                        <input id="password" name="password" class="default-input" type="password" placeholder="请设置密码">
                        <span></span>
                    </div>
                    <div class="input-wrap lock">
                    
                        <input name="passwordAgain"  class="default-input" type="password" placeholder="请确认密码">
                        <span></span>
                    </div>
                    
                    <div class="input-wrap code">
	                       <!-- <td>验证码</td>-->
	                        <input id="index_code" class="default-input"  name="number" type="text" placeholder="输入右侧验证码" />
	                       
		                        <img id="imgObj" alt="验证码" src="${rc.contextPath}/code/code.htm" onclick="javascript:changeImg()" />
		                         <!-- <a onclick="javascript:changeImg()">换一张</a>-->
	                    
                    </div>
                    
                    <div class="input-wrap code">
                        <input name="code" class="default-input" type="text" id="code" placeholder="输入手机验证码" >
                        <input  type="button" id="send-code" onclick="sendSecurityCode(this)"  value="获取手机验证码">
                        <!--<a  onclick="javascript:sendSecurityCode();" id="send-code">发送验证码</a>
                   -->
                    </div>
                    
                    
                    <div class="checkbox-wrap fl">
                        <label for="agreen">
                            <input id="agreen" type="checkbox" checked="checked">
                            同意阿拉丁协议
                        </label>
                    </div>
                    <button class="btn-theme" id="registeredBtn">立 即 注 册</button>
                </form>
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
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                /*添加自定义验证 邮箱或手机号*/
                jQuery.validator.addMethod("phoneOrEmail", function(value, element) {
                    var tel =/^1([34578])\d{9}$/; 
                    return this.optional(element) || (tel.test(value));
                }, "请填写正确的手机号");

                var validator = $('#registeredForm').validate({
                    rules: {
                        userName: {
                            required: true,
                            phoneOrEmail:true,
                            rangelength: [11,11],
                            remote: {
                                type:"Post",
	                            url:"${rc.contextPath}/login/checkIsExit.htm",
	                            data:{
	                              userName:function(){return $("#userName").val();},
	                              status:"register",
	                              time:function(){return new Date().getTime();}
                                  }/** end data*/
                               }/** end remote*/
                        },/** end userName*/
                        password: {
                            required: true,
                            rangelength: [6, 16]
                        },
                        passwordAgain: {
                            equalTo:"#password"
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
                            remote: {
                                type:"Post",
	                            url:"${rc.contextPath}/register/checkSecurityCode",
	                            data:{
	                              code:function(){return $("#code").val();},
	                              status:"register",
	                              time:function(){return new Date().getTime();}
                                  }
                               }
                        }
                    },/** end rules*/
                    
                    messages: {
                        userName: {
                            required: "请填写手机号！",
                            rangelength: "手机号应该为11位！",
                            remote:jQuery.validator.format("该用户已存在")
                        },/**end userName */
                        
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-16位！"
                        },/** end password*/
                        
                        passwordAgain: {
                            equalTo:"两次输入密码不一致！"
                        },/** end passwordAgain */
                       number:{
                            required: "请输入图片验证码",
                            rangelength: "验证码为4位",
                            remote:jQuery.validator.format("验证码不正确，请重新输入")
                        },
                        code:{
                            required: "请输入验证码",
                            digits:"请输入6位数字",
                            rangelength: "验证码为6位数字",
                            remote:jQuery.validator.format("验证码不正确，请重新输入")
                        }
                    },/** end messages*/
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    },/**end submitHandler*/
                    
                });/**end validator*/
                
                $('#registeredBtn').click(function(event) {
                    $('.default-input.error').parents('.input-wrap').velocity('callout.shake');
                });
                
            });/** end ready*/
        </script>
    </body>
</html>
