<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>权限修改 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic }/images/favicon.ico" />
    </head>
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 权限修改</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/storePrivilege/updatePrivilege" method="post">
                                <input type="hidden" value="${privilege.id }" name="id">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">权限名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" class="form-control" value="${privilege.name }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">权限url：</label>
                                    <div class="col-sm-5">
                                        <input name="url" type="text" class="form-control" value="${privilege.url }">
                                    </div>
                                </div>
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">权限等级：</label>
                                    <div class="col-sm-5">
                                    <select name="level">
                                       <option value="2" ${privilege.level==2?'selected':''}>二级权限</option>
                                       <option value="1" ${privilege.level==1?'selected':''}>一级权限</option>
                                    </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">权限sortNo：</label>
                                    <div class="col-sm-5">
                                        <input name="sortNo" type="text" class="form-control" value="${privilege.sortNo }">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">选择父权限：</label>
                                    <div class="col-sm-7">
                                    
                                    <select  name="parentId" id="select_parentId">
		                                <option value="">请选择</option>
										    <c:forEach items="${firstList }" var="f" >
											  <option value="${f.id}" ${privilege.parentId==f.id?'selected':''}>${f.name }</option>
									        </c:forEach>
							        </select>
                                    <%-- <c:forEach items="${first }" var="f" varStatus="status">
                                        <div class="col-md-3">
                                            <div class="checkbox-inline">
                                                <label>
                                                
                                                    <input name="access" type="checkbox" value="${f.id}">${f.name }
                                                </label>
                                            </div>
                                        </div>
                                    </c:forEach> --%>
                                        
                                      <!--   <div id="addRoleAlert"></div> -->
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认修改权限</button>
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
                        name: {
                            required: true,
                            rangelength: [2, 10],
                        },
                        level: {
                            required: true
                        }
                    },
                    messages: {
                        name: {
                            required: "请填写权限名！",
                            rangelength: "角色名长度应该为2-10位"
                        },
                        level: {
                            required: "请选择权限等级！"
                        }
                    },
                 /*    errorPlacement: function(error, element) {
                        if ( element.is(":checkbox") ){
                            error.appendTo($("#addRoleAlert") );
                        } else {
                            error.appendTo(element.parent());
                        }
                    },  */
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
