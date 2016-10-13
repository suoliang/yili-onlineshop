<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>新增图片类型- CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增图片类型</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/skuImageType/addSkuImageType" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图片类型名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图片类型编码：</label>
                                    <div class="col-sm-5">
                                        <input name="code" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                  <div class="form-group">
	                                    <label class="col-sm-2 control-label">是否为多图片：</label>
	                                    <div class="col-sm-3 "  >
		                                    <select name="isMultiple" class="form-control lg-select">
		                                       <option value="y">是</option>
		                                       <option value="n">否</option>
		                                    </select>
	                                    </div>
                                 </div>
                                 
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">是否开启：</label>
                                    <div class="col-sm-3  " >
                                    <select name="disable" class="form-control lg-select">
                                       <option value="n">开启</option>
                                       <option value="y">禁用</option>
                                    </select>
                                    </div>
                                </div>
                               
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认新建</button>
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
                        },
                        code: {
                            required: true
                        }
                    },
                    messages: {
                        name: {
                            required: "请填写图片类型名！",
                        },
                        code: {
                            required: "请填写图片类型编码！"
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
