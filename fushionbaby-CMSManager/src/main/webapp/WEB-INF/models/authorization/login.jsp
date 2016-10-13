<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>登录 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
        <link rel="stylesheet" type="text/css" href="${contextStatic }/bootstrap/css/icheck/futurico/futurico.css">        
          <script type="text/javascript">
           $(document).ready(function() {
                //表单验证
                var validator = $('#loginForm').validate({
                    rules: {
                        userName: {
                            required: true,
                            rangelength: [2, 10],
                         /*    remote: {
                             //  url: "remote.json", 
                               url: "${contextPath }/user/login.do",
                                type: "post",
                                data: {
                                    loginTime: function() {
                                        return +new Date;
                                    }
                                }
                            } */
                        },
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        userName: {
                            required: "请填写用户名！",
                            rangelength: "账户名长度应该为2-10位！",
                            remote: "用户名不存在！"
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                       console.log($(form).serialize());
                        form.submit();
                    },
                });
            });
           
           if(top!=self){
		         if(top.location != self.location)
		            top.location=self.location; 
		     }
        </script>
        
    </head>
    <body id="login" class="backBlack">
       <tags:message content="${message }"></tags:message>
        <div class="container" id="loginBox">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <h1 class="color-white mB30"><i class="fa fa-medium text-info"></i> ALADing CMS</h1>
                </div>
                <div class="col-md-6 col-md-offset-3">
                    <form id="loginForm" action="${contextPath }/login/main" method="post" >
                        <div class="form-group">
                           
                                <div class="input-group-addon">
                                    <i class="fa fa-user"></i>
                                </div>
                                <input type="text" class="form-control" name="userName" placeholder="User name" value="${userName }">
                            
                        </div>
                        
                        <div class="form-group">
     
                                <div class="input-group-addon">
                                    <i class="fa fa-lock"></i>
                                </div>
                                <input type="password" class="form-control input-block" name="password" placeholder="Password" value="${password }">

                        </div>
                        
                       <!-- <div class="form-group">
                            <div class="checkbox">
                                <label class="color-white" for="rememberMe">
                                    <input type="checkbox" value="1" name="rememberMe" class="iCheck iCheck-futurico"> remember me
                                </label>
                            </div>
                        </div>  -->
                        
                        <div class="form-group">
                            <button type="submit"  class="btn btn-default btn-block btn-lg">登录</button>
                        </div>
                        
                        <div class="form-group">
                            <p class="color-grey-dark">忘记密码？请联系后台管理员</p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
