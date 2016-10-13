<!DOCTYPE html>
<html>
    <head>
        <title>修改密码</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="resetPassword">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>重置密码</p>
            </div>

            <form id="resetForm" class="form-group">
                <div class="input-group">
                    <input id="password" name="password" type="password" placeholder="请输入密码" autocomplete="off">
                    <div class="ios-btn-wrap">
                        <div class="ios-btn">
                            <input type="checkbox">
                            <i></i>
                        </div>
                    </div>
                </div>

                <p class="password-tip">密码由6-20位字母，数字或符号组成</p>

                <div class="btn-wrap">
                    <button id="resetBtn" type="submit">完成</button>
                </div>
            </form>

        </div><!-- /.container -->

        <a class="registering-new" href="javascript:toRegister();">注册新用户</a>

        <script src="${rc.contextPath}/static/shop/farmwork/validate/jquery.validate.min.js"></script><!-- 表单验证 -->
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/

                var validator = $('#resetForm').validate({
                    focusInvalid: false,
                    rules: {
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    submitHandler: function(form) {
                        var password = $("#password").val();
                        $.ajax({
						    type: "POST",
						    url: _ContextPath + '/member/modifyPassword',
						    data: {'password':password,'time':new Date().getTime()},
							async:false,
						    dataType: "json",
						    success: function (data) {
						    	if(data.responseCode=="201"){
						    		 window.location.href = _ContextPath + "/login/index?time="+new Date().getTime();
						    	}else if (data.responseCode == "500") {
						    		alert(data.msg);
						    		return;
								} else {
							        /*  成功后的操作 开始  */
							        alert("重置密码成功！");
							        window.location.href = _ContextPath + "/login/index?time="+new Date().getTime();
							        /*  成功后的操作 结束  */
								}
						     }
						});
                    },
                });

                $('#resetBtn').click(function(event) {
                    $('input.error').parents('.input-group').velocity('callout.shake');
                });

                /*显示/隐藏密码*/
                $('#resetForm').find('.ios-btn-wrap').click(function(event) {
                    var thisStatus = $(this).find('input').attr('checked');
                    if (thisStatus) {
                        $('#password').attr('type', 'text');
                    } else {
                        $('#password').attr('type', 'password');
                    }
                });

            });
            
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
