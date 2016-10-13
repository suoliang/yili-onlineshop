<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>修改用户密码 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    
    <body id="" class="Cog">
    <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改密码</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/userInfo/authUserModify" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称：</label>
                                    <div class="col-sm-5">
                                        <input name="Name" type="text" disabled="disabled" id="user_name" class="form-control" value="${loginName }" >
                                        <input name="loginName" type="hidden" disabled="disabled" id="user_name" class="form-control" value="${loginName }" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">原始密码：</label>
                                    <div class="col-sm-5">
                                        <input name="oldpassword" id="oldpassword" type="password" class="form-control" value="">
                                    </div>
                                </div>
                                   <div class="form-group">
                                    <label class="col-sm-2 control-label">新密码：</label>
                                    <div class="col-sm-5">
                                      <input name="newpassword" id="newpassword" type="password" class="form-control" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">确认密码：</label>
                                    <div class="col-sm-5">
                                        <input name="newpasswords" id="newpasswords" type="password" class="form-control" value="">
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认修改密码</button>
                                        <button type="button" class="btn btn-default" onclick="history.go(-1);">取消</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->
    
        <script type="text/javascript">
            $(document).ready(function() {
                /*表单验证*/
                var validator = $('#addRoleForm').validate({
                    rules: {/* 
                        loginName: {
                            required: true,
                            rangelength: [2, 10],
                        }, */
                        oldpassword:{
                            required: true
                        },
                        newpassword:{
                        	required: true,
                            rangelength: [6, 50]
                        },
                        newpasswords:{
                        	required: true,
                            rangelength: [6, 50]
                        }
                    },
                    messages: {
                        oldpassword:{
                        	required:"请输入原始密码！"
                        },
                        newpassword:{
                        	required: "请输入新密码！",
                        	rangelength: "密码不能小于6位大于50位"
                        },
                        newpasswords:{
                        	required: "请输入确认密码！",
                        	rangelength: "密码不能小于6位大于50位"
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    }
                });
            });
        </script>
    </body>
</html>
