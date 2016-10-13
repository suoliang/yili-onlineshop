<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>新增门店超级管理员 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    
    <script type="text/javascript">
	    function checkIsUnique(){
	    	var userName=$("#name").val();
	    	var storeCode=$("#storeCode").val();
	    	  $.post("${pageContext.request.contextPath}/store/storeSysUserUnique",{userName:userName,storeCode:storeCode,time:new Date().getTime()},
	    	  function(data){
	    		  if(data!=true)
	    				{
	    				    jBox.tip("该门店此用户名已存在，请重新输入");
	    				    $("#name").val("");
	    				}
	    		  });
	    }
	    
	  
    </script>
    
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
         <%--    <div id="menu">
             <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
            </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增门店超级管理员</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addStoreUserForm" action="${contextPath }/store/addStoreSysUser" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店编号：</label>
                                    <div class="col-sm-5">
                                    	<input name="storeCode" id="storeCode" type="hidden" class="form-control" value="${store.code }">
                                        <input name="storeNumber" id="storeNumber" type="text" class="form-control" value="${store.number }" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店名称：</label>
                                    <div class="col-sm-5">
                                        <input name="storeName" type="text" class="form-control" value="${store.name }" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户名：</label>
                                    <div class="col-sm-5">
                                        <input name="name" id="name" type="text" class="form-control" value="${name }" onblur="checkIsUnique();">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">密码：</label>
                                    <div class="col-sm-5">
                                        <input name="password" type="text" class="form-control" value="${password }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机号：</label>
                                    <div class="col-sm-5">
                                        <input name="phone" id="phone" type="text" class="form-control" value="${phone }" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">邮箱：</label>
                                    <div class="col-sm-5">
                                        <input name="email" id="email" type="text" class="form-control" value="${email }" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">备注：</label>
                                    <div class="col-sm-5">
                                        <input name="memo" id="memo" type="text" class="form-control" value="${memo }" >
                                    </div>
                                </div>
                               
                                
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认</button>
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
                var validator = $('#addStoreUserForm').validate({
                    rules: {
                       
                        name: {
                            required: true,
                            rangelength: [2, 20],
                        },
                        password: {
                            required: true,
                            rangelength: [6, 20]
                        }
                    },
                    messages: {
                        name: {
                            required: "请填写用户名！",
                            rangelength: "用户名长度应该为2-20位！"
                        },
                        password: {
                            required: "请填写密码！",
                            rangelength: "密码长度应该为6-20位！"
                        }
                    },
                    errorPlacement: function(error, element) {
                         error.appendTo(element.parent());
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
