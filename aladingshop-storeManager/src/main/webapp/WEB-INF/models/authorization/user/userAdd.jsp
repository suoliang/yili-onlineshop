<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>新增用户 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    
    <script type="text/javascript">
	    function checkIsUnique(){
	    	var name=$("#user_name").val();
	    	  $.post("${pageContext.request.contextPath}/user/userUnique.do",{name:name,time:new Date().getTime()},
	    			  function(data){
	    		  if(data!=true)
	    				{
	    				    alert("用户  "+name+" 已存在，请重新输入");
	    				    $("#user_name").val("");
	    				}
	    		  });
	    }
    </script>
    
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增用户</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/user/addUser" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" id="user_name" class="form-control" value="${name }" onblur="javascript:checkIsUnique();">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户密码：</label>
                                    <div class="col-sm-5">
                                        <input name="password" type="text" class="form-control" value="${password }">
                                    </div>
                                </div>
                                <!-- 
                                <div class="form-group" >
                                    <label class="col-sm-2 control-label">用户类型：</label>
                                    <div class="col-sm-5">
                                      <select name="userType" class="form-control lg-select">
                                        <option value="1">系统用户</option>
                                        <option value="2">普通用户</option>
                                      </select>
                                    </div>
                                </div>
                                 -->
                                <input type="hidden" name="userType" value="2">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机号码：</label>
                                    <div class="col-sm-5">
                                        <input name="phone" type="text" class="form-control" value="${phone }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">邮箱：</label>
                                    <div class="col-sm-5">
                                        <input name="email" type="text" class="form-control" value="${email }">
                                    </div>
                                </div>
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">备注：</label>
                                    <div class="col-sm-5">
                                        <input name="memo" type="text" class="form-control" value="${memo }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">赋予角色：</label>
                                    <div class="col-sm-7">
                                    <c:forEach items="${authRoleList }" var="list" varStatus="status">
                                        <div class="col-md-3">
                                            <div class="checkbox-inline">
                                                <label>
                                                    <input name="access" type="checkbox" value="${list.id}">${list.name }
                                                </label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                        
                                        <div id="addRoleAlert"></div>
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认新建用户</button>
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
                    rules: {
                        loginName: {
                            required: true,
                            rangelength: [2, 20],
                        },
                        access: {
                            required: true
                        }
                    },
                    messages: {
                    	loginName: {
                            required: "请填写用户名！",
                            rangelength: "用户名长度应该为2-20位"
                        },
                        access: {
                            required: "请至少分配一个角色！"
                        }
                    },
                    errorPlacement: function(error, element) {
                        if ( element.is(":checkbox") ){
                            error.appendTo($("#addRoleAlert") );
                        } else {
                            error.appendTo(element.parent());
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
