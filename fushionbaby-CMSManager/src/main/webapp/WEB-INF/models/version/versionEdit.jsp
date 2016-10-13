<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>版本修改 - CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 版本修改</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/version/updateVersion" method="post">
                                <input type="hidden" value="${sysmgrVersion.id}" name="id">
                                <input type="hidden" value="${user.id}" name="createId">
								<input type="hidden" value="${loginUser.id}" name="updateId">
								
								<input type="hidden" value="${sysmgrVersion.createTime}" name="updateId">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">创建者：</label>
                                    <div class="col-sm-5">
                                        <input disabled="disabled" type="text" class="form-control" value="${user.loginName}">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">版本名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" class="form-control" value="${sysmgrVersion.name }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">版本号：</label>
                                    <div class="col-sm-5">
                                        <input name="versionNum" type="text" class="form-control" value="${sysmgrVersion.versionNum }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">版本url：</label>
                                    <div class="col-sm-5">
                                        <input name="url" type="text" class="form-control" value="${sysmgrVersion.url }">
                                    </div>
                                </div>
                                
                                
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">版本来源：</label>
                                    <div class="col-sm-5">
                                    <select name="sourceCode" class="form-control lg-select">
	                                      <c:forEach items="${sysmgrSourceList}" var="sourceList" >
											 <option value="${sourceList.code}" ${sysmgrVersion.sourceCode==sourceList.code?'selected':''} >${sourceList.name}</option>
										  </c:forEach>
                                    </select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否强制更新：</label>
                                    <div class="col-sm-5">
                                    <select name="isFource" class="form-control lg-select">
                                       <option value="1" ${sysmgrVersion.isFource==1?'selected':''}>强制更新</option>
                                       <option value="2" ${sysmgrVersion.isFource==2?'selected':''}>非强制更新</option>
                                    </select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">版本特色：</label>
                                    <div class="col-sm-5">
                                        <textarea name="feature"  class="form-control" >${sysmgrVersion.feature}</textarea>
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认修改版本</button>
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
                           /*  rangelength: [2, 20], */
                        },
                        url: {
                            required: true
                        }
                    },
                    messages: {
                        name: {
                            required: "请填写版本名称！",
                           /*  rangelength: "版本名称长度应该为2-20位" */
                        },
                        url: {
                            required: "请填写版本url！"
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
