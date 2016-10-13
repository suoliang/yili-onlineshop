<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>如意宝详情页</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 更新如意宝转出限制</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath}/alabaoLimit/updateAlabaoLimit" method="post">
                                
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">阿拉宝账户：</label>
                                    <div class="col-sm-5">
                                        <input name="account" type="text" class="form-control" placeholder="阿拉宝账户" value="${alabaoLimit.account }" readonly>
                                    </div>
                                </div>
                                	<div class="form-group">
                                    <label class="col-sm-2 control-label">转出限额：</label>
                                    <div class="col-sm-5">
                                        <input name="moneyLimit" type="text" class="form-control" placeholder="转出限额"  value="${alabaoLimit.moneyLimit }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">次数限制：</label>
                                    <div class="col-sm-5">
                                        <input name="numberLimit" type="text" class="form-control"  placeholder="次数限制"  value="${alabaoLimit.numberLimit }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">账号等级：</label>
                                    <div class="col-sm-5">
                                        <select  name="accountLevel" class="form-control lg-select">
	                                       <option value="1"  ${alabaoLimit.accountLevel eq '1'?'selected':'' }>普通用户</option>
	                                       <option value="2"  ${alabaoLimit.accountLevel eq '2'?'selected':'' }>测试用户</option>
	                                       <option value="3"  ${alabaoLimit.accountLevel eq '3'?'selected':'' }>理财VIP用户</option>
	                                    </select>
                                    </div>
                                </div>
                               <div class="form-group">
                                    <label class="col-sm-2 control-label">描述：</label>
                                    <div class="col-sm-5">
                                        <textarea rows="3" cols="70" name="remark"> ${alabaoLimit.remark }</textarea>
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
                		account: {
                           required: true
                       },
                       moneyLimit: {
                           required: true,
                           number:true
                       },
                       numberLimit: {
                       	required: true,
                       	digits:true
                       },
                       
                   },
                   messages: {
                	   account: {
                           required: "请填写阿拉宝账户！"
                       },
                       moneyLimit: {
                       	required: "请填写转出限额！",
                       	number:"转出限额必须为数字！"
                       },
                       numberLimit: {
                       	required: "请填写次数限制！",
                       	digits:"次数限制必须为正整数！"
                       },
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
