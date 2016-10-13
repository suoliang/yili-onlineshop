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
        <title>广告修改- CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i>广告修改</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/ad/adEdit" method="post" enctype="multipart/form-data">
                                
                                <input name="id" type="hidden" value="${ad.id }">
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告名称：</label>
                                    <div class="col-sm-5">
                                    	<select name="adCode" class="form-control lg-select">
												<c:forEach items="${adConfigList}" var="adconfig">
												  <option value="${adconfig.adCode}" ${ad.adCode==adconfig.adCode?'selected':'' }>${adconfig.adName}</option>
												</c:forEach>
											</select>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告url：</label>
                                    <div class="col-sm-5">
                                        <input name="url" type="text" class="form-control" value="${ad.url }">
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">开始时间：</label>
                                    <div class="col-sm-5">
                                     <div class="input-group">
                                            <input class="form-control form_datetime "  name="beginTime" type="text" value="<fmt:formatDate value="${ad.beginTime}" pattern="yyyy-MM-dd"/>" />
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
                                    </div>
                                </div>
                              
                                
                                  <div class="form-group">
                                    <label class="col-sm-2 control-label">结束时间：</label>
                                    <div class="col-sm-5">
                                     <div class="input-group">
                                            <input class="form-control form_datetime" name="endTime"  type="text"  value="<fmt:formatDate value="${ad.endTime}" pattern="yyyy-MM-dd"/>"/>
									        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告来源：</label>
                                    <div class="col-sm-5">
                                        <select name="sourceCode" class="form-control lg-select">
											<c:forEach items="${sysSourceList}" var="sourceList">
												<option value="${sourceList.code}"
													${ad.sourceCode==sourceList.code?'selected':'' }>${sourceList.name}</option>
											</c:forEach>
										</select> 
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">是否开启使用：</label>
                                    <div class="col-sm-5">
                                          <select name="isShow" class="form-control lg-select">
											 <option value="y" ${ad.isShow=='y'?'selected':'' }>开启</option>
											 <option value="n" ${ad.isShow=='n'?'selected':'' }>不开启</option>
										  </select> 
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">广告图片：</label>
                                    <div class="col-sm-5">
                                    <input name="picturePath" id="picturePath" type="hidden" value="${adPath}${ad.picturePath }">
                                    <tags:ckfinder input="picturePath" type="images" uploadPath="/ad/" selectMultiple="false" />
                                       <!--  <input  type="file" name="adLogoUrl" id="adLogoUrl" onchange="fileChange(this)" />  -->
										<!-- <span id="logoUrl_Wrning" style="color:red;width:5px;height:28px;line-height:28px">*</span> -->
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">展示顺序：</label>
                                     <div class="col-sm-5">
                                      <input name="showOrder"  type="number" class="form-control" min=1 value="${ad.showOrder }">
                                     </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">说明：</label>
                                     <div class="col-sm-5">
                                      <textarea name="remark"   class="form-control">${ad.remark}</textarea>
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
                    	adCode: {
                            required: true,
                           /*  rangelength: [2, 10], */
                        },
                       /*  level: {
                            required: true
                        } */
                    },
                    messages: {
                    	adCode: {
                            required: "请填写权限名！"
                            /* rangelength: "角色名长度应该为2-10位" */
                        },
                       /*  level: {
                            required: "请选择权限等级！"
                        } */
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
