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
        <title>新增账户 - CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增如意消费卡账户</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/alabaoAccount/addAccount" method="post">
                              
                              <input type="hidden" name="id" value="${alabaoAccount.id }" />
								
								<div class="form-group">
                                    <label class="col-sm-2 control-label">手机号：</label>
                                    <div class="col-sm-5">
                                        <input name="mobilePhone" id="mobilePhone" type="text" class="form-control"  >
                                    </div>
                                </div>
                                
                                
                               <div class="form-group">
                                    <label class="col-sm-2 control-label">登录密码：</label>
                                    <div class="col-sm-5">
                                        <input name="loginPassword" id="loginPassword" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">支付密码：</label>
                                    <div class="col-sm-5">
                                        <input name="payPassword" id="payPassword" type="text" class="form-control">
                                    </div>
                                </div>
                                
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">身份证：</label>
                                    <div class="col-sm-5">
                                        <input class="form-control  " id="identityCardNo"  name="identityCardNo" type="text"  />
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">真实姓名：</label>
                                    <div class="col-sm-5">
                                        <input class="form-control  " id="trueName" name="trueName" type="text"   />
                                    </div>
                                </div>
                                
                                 <div class="form-group">
                                    <label class="col-sm-2 control-label">账户等级：</label>
                                    <div class="col-sm-5">
                                        
                                        <select name="level" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <c:forEach items="${dic:getDictByType('ALABAO_ACCOUNT_LEVEL')}" var="dict">
								            	<option value="${dict.value }"  <c:if test="${dict.value==alabaoAccount.level }"></c:if> >${dict.label }</option>
								            </c:forEach>
								        </select>
                                        
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否发短息通知：</label>
                                    <div class="col-sm-5">
                                        
                                        <select name="isInform" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <c:forEach items="${dic:getDictByType('FLAG')}" var="dict">
								            	<option value="${dict.value }"  <c:if test="${dict.value=='n' }">selected</c:if> >${dict.label }</option>
								            </c:forEach>
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
                
                
             <script type="text/javascript">
            $(document).ready(function() {
                var validator = $('#addRoleForm').validate({
                	rules: {
                	   mobilePhone: {
                           required: true,
                           isMobile:true,
                           remote:{
                        	   url:"${contextPath}/alabaoAccount/exitAccount",
                        	   type:"get",
	       	                   dataType:"json",
	       	                   data: {                     //要传递的数据
	       	                	mobilePhone: function() {
	       	                             return $("#mobilePhone").val();
	       	                         }
	       	                    }
                           }//end remote
                       },
                       payPassword: {
                           required: true,
                           rangelength:[6,6],
                           isDigits:true,
                       },
                       loginPassword: {
                       	   required: true,
                       	   isPwd:true
                       	   
                       },
                       identityCardNo: {
                    	   required: true,
                    	   isIdCardNo:true
                       },
                       trueName:{
                    	   required:true,
                    	   minlength:2,
                    	   isChineseChar:true
                       }
                       
                   },
                   messages: {
                	   mobilePhone: {
                           required: "请填写电话号码！",
                           isMobile:"请输入正确格式的电话号码",
                           remote:"该账号已注册过。"
                       },
                       payPassword: {
                       	required: "请填写支付密码！",
                       	rangelength:"必须输入六位。",
                       	isDigits:"只能输入0-9的整数。"
                       },
                       loginPassword: {
                       	required: "请填写登录密码！",
                       	isPwd:"以数字或字母开头，长度在7-12之间，只能包含字符、数字和下划线。"
                       },
                       identityCardNo: {
                    	   required: "请输入身份证号码",
                    	   isIdCardNo:"请输入正确的身份证号码。"
                       },
                       trueName:{
                    	   required:"请输入真实姓名",
                    	   minlength:"请输入正确的姓名",
                    	   isChineseChar:"请输入正确的姓名"
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
            </div>
        </div>
    </body>
</html>
