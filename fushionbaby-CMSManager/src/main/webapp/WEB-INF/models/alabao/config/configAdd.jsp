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
        <title>新增权限 - CMS</title>
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
            <%-- <div id="menu">
             <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
            </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增如意宝配置</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/alabaoConfig/configAdd" method="post">
                               <div class="form-group">
                                    <label class="col-sm-2 control-label">日利率：</label>
                                    <div class="col-sm-5">
                                        <input name="rate" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">返利时间：</label>
                                    <div class="col-sm-5">
                                     <div class="input-group">
                                        <input class="form-control form_datetime "  name="rebateTime" type="text" />
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
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
                    	 rate: {
                            required: true,
                            number: true
                        },
                        rebateTime: {
                            required: true,
                        }
                    },
                    messages: {
                    	rate: {
                            required: "请填写利率！",
                            number: "必须输入合法的数字"   
                        },
                        rebateTime: {
                        	required: "请选择时间！",
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
