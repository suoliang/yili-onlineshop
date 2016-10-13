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
        <title>修改实体卡配置- CMS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <link rel="shortcut icon" href="${contextStatic}/images/favicon.ico" />
    </head>
    <body id="" class="Cog">
		<tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改实体卡配置</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath}/actEntityCardConfig/actEntityCardConfigEdit" method="post">
                                <input name="id" value="${actEntityCardConfig.id}" type="hidden">
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" value="${actEntityCardConfig.name}" type="text" class="form-control">
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">生效时间：</label>
                                    <div class="col-sm-5">
                                    	<div class="input-group">
                                        <input class="form-control form_datetime "  name="beginTime" value="<fmt:formatDate value="${actEntityCardConfig.beginTime}" pattern="yyyy-MM-dd" />" type="text" />
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">失效时间：</label>
                                    <div class="col-sm-5">
                                     <div class="input-group">
                                        <input class="form-control form_datetime "  name="expiration"  value="<fmt:formatDate value="${actEntityCardConfig.expiration}" pattern="yyyy-MM-dd" />" type="text" />
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">出售金额：</label>
                                    <div class="col-sm-5">
                                        <input name="sellMoney" value="${actEntityCardConfig.sellMoney}" type="text" class="form-control">
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">面值：</label>
                                    <div class="col-sm-5">
                                        <input name="faceMoney" value="${actEntityCardConfig.faceMoney}" type="text" class="form-control">
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
                //表单验证
                var validator = $('#addRoleForm').validate({
                	rules: {
                   	 name: {
                           required: true
                       },
                       beginTime: {
                           required: true
                       },
                       expiration: {
                       	required: true
                       },
                       sellMoney: {
                       	required: true,
                           number: true
                       },
                       faceMoney: {
                       	required: true,
                           number: true
                       }
                   },
                   messages: {
                   	
                   	name: {
                           required: "名称不能为空"
                       },
                       beginTime: {
                           required: "生效时间不能为空"
                       },
                       expiration: {
                       	required: "失效时间不能为空"
                       },
                       sellMoney: {
                       	required: "出售金额不能为空",
                           number: "出售金额只能是数字"
                       },
                       faceMoney: {
                       	required: "面值不能为空",
                           number: "面值只能是数字"
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
