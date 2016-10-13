<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>新增权限 - CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic}/images/favicon.ico" />
    </head>
    <body id="" class="Cog">
        <div class="container-fluid">
            <div class="row">
            <%-- <div id="menu">
             <script src="${contextStatic}/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
            </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改银行配置</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath}/alabaoBankConfig/alabaoBankConfigEdit" method="post">
                                        <input name="id" value="${alabaoBankConfig.id}" type="hidden" class="form-control">
                                	
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">银行编码：</label>
                                    <div class="col-sm-5">
                                        <input name="bankCode" value="${alabaoBankConfig.bankCode}" type="text" class="form-control" placeholder="银行编码">
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">银行名称：</label>
                                    <div class="col-sm-5">
                                        <input name="bankName" value="${alabaoBankConfig.bankName}" type="text" class="form-control" placeholder="银行名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">描述：</label>
                                    <div class="col-sm-5">
                                        <input name="bankDesc" value="${alabaoBankConfig.bankDesc}" type="text" class="form-control" placeholder="描述">
                                    </div>
                                </div>
                                <div class="form-group">
                                    	<label class="col-sm-2 control-label">银行的icon图：</label>
										<div class="col-sm-4">
											<input type="hidden" id="bankIconUrl" name="bankIconUrl"  value="${imagePath}${alabaoBankConfig.bankIconUrl}"/>
											<input type="hidden" id="oldbankIconUrl" name="oldbankIconUrl" value="${imagePath}${alabaoBankConfig.bankIconUrl}" >
											<!-- <input type="hidden" id="id" name="id" > -->
											
											<tags:ckfinder input="bankIconUrl" type="images" 
													uploadPath="/bank/" selectMultiple="false" />
										</div>
									</div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认修改</button>
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
                var validator = $('#addRoleForm').validate({
                	rules: {
                      	 bankCode: {
                              required: true
                          },
                          bankName: {
                              required: true
                          },
                          bankDesc: {
                          	required: true
                          },
                          bankIconUrl: {
                       	   required: true
                          }
                      },
                      messages: {
                      	bankCode: {
                              required: "请填写银行编码！"
                          },
                          bankName: {
                          	required: "请填写银行名称！"
                          },
                          bankDesc: {
                          	required: "请填描述！"
                          },
                          bankIconUrl: {
                       	   required: "请选择银行icon图片"
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
