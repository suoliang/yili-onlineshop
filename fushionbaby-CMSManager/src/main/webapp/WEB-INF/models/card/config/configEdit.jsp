<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁卡修改 - CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改阿拉丁卡配置</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath}/memberCardConfig/configEdit" method="post">
                                <input name="isDisabled" value="${memberCardConfig.isDisabled }"  type="hidden" class="form-control">
                                <input name="id" value="${memberCardConfig.id }"  type="hidden" class="form-control">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">阿拉丁卡类型：</label>
                                    <div class="col-sm-5">
                                    <select name="type"  class="form-control lg-select">
                                       <option value="1" ${memberCardConfig.type=='1'?'selected':'' }>季卡</option>
                                       <option value="2" ${memberCardConfig.type=='2'?'selected':'' }>双季卡</option>
                                       <option value="3" ${memberCardConfig.type=='3'?'selected':'' }>年卡</option>
                                    </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">赠劵利率：</label>
                                    <div class="col-sm-5">
                                        <input name="rebate" value="${memberCardConfig.rebate }" type="text" class="form-control">
                                    </div>
                                </div>
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">最低起充金额：</label>
                                    <div class="col-sm-5">
                                        <input name="lowestMoney" value="${memberCardConfig.lowestMoney }"  type="text" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">时间期限(月)：</label>
                                    <div class="col-sm-5">
                                        <input name="timeLine" value="${memberCardConfig.timeLine }"  type="text" class="form-control">
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
                /*表单验证*/
                var validator = $('#addRoleForm').validate({
                	rules: {
                   	 rate: {
                           required: true,
                           number: true
                       },
                       lowestMoney: {
                           required: true,
                           number: true
                       },
                       timeLine: {
                       	required: true,
                       	digits:true
                       }
                   },
                   messages: {
                   	rate: {
                           required: "请填写利率！",
                           number: "必须输入合法的数字"   
                       },
                       lowestMoney: {
                       	required: "请填写最低起充金额！",
                        number: "必须输入合法的数字"   
                       },
                       timeLine: {
                       	required: "请填写时间期限！",
                       	digits: "必须为整数"
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
