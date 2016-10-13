<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>新增广告配置 - CMS</title>
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
           <%--  <div id="menu">
             <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
            </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增广告配置</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/adConfig/addAdConfig" method="post" enctype="multipart/form-data">
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告名称：</label>
                                    <div class="col-sm-5">
                                    
                                       <input name="adName" type="text" class="form-control" value="${adName }">
                                    	<%-- <select name="adCode">
												<c:forEach items="${adConfigList}" var="adconfig">
												  <option value="${adconfig.adCode}" ${adCode==adconfig.adCode?'selected':'' }>${adconfig.adName}</option>
												</c:forEach>
											</select> --%>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告编码：</label>
                                    <div class="col-sm-5">
                                        <input name="adCode" type="text" class="form-control" value="${adCode }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告来源：</label>
                                    <div class="col-sm-5">
                                      <select name="sourceCode" class="form-control lg-select">
										<c:forEach items="${sysSourceList}" var="sourceList">
										  <option value="${sourceList.code}"	${sourceCode==sourceList.code?'selected':'' }>${sourceList.name}</option>
										</c:forEach>
									  </select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否使用：</label>
                                    <div class="col-sm-5">
                                       <select  class="form-control lg-select" name="isUse">
											<option value="y">开启</option>
											<option value="n">不开启</option>
									  </select>
                                    </div>
                                </div>
                              
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">说明：</label>
                                     <div class="col-sm-5">
                                      <textarea name="remark"   class="form-control">${remark}</textarea>
                                     </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认新建广告配置</button>
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
                    	adCode: {
                            required: true,
                           /*  rangelength: [2, 10], */
                        },
                        adName: {
                            required: true
                        } 
                    },
                    messages: {
                    	adCode: {
                            required: "请填写广告编码！"
                            /* rangelength: "角色名长度应该为2-10位" */
                        },
                        adName: {
                            required: "请填写广告名！"
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
