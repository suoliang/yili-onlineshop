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
        <title>新增分类图片 - CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 新增分类图片 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/skuCategory/addSkuCategoryImage" method="post" enctype="multipart/form-data">
                                

                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">分类编码：</label>
                                    <div class="col-sm-5">
                                        <input name="categoryCode" type="text" readonly="readonly" value="${categoryCode }"class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">图片类型名称：</label>
                                    <div class="col-sm-5">
                                        <select name="imageTypeCode" class="form-control lg-select"> 
                                        <c:forEach items="${imageTypeList }" var="imageType">
                                        <option value="${imageType.code }">${imageType.name }</option>
                                        </c:forEach>
                                        
                                        </select>
                                    </div>
                                </div>
                               <div class="form-group">
                                    <label class="col-sm-2 control-label">图片链接地址：</label>
                                    <div class="col-sm-5">
                                        <input name="linkUrl" type="text" value="${linkUrl }"class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">显示顺序：</label>
                                    <div class="col-sm-5">
                                        <input name="sortOrder" type="text" value="${sortOrder }"class="form-control">
                                    </div>
                                </div>
                                
                                <input name="createId" value="${user.id }" type="hidden">
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">分类图片：</label>
                                    <div class="col-sm-5">
                                    <input name="imgUrl" id="imgUrl" type="hidden">
                                    <tags:ckfinder input="imgUrl" type="images" uploadPath="/category/image/" selectMultiple="true" />
                                       <!--  <input  type="file" name="adLogoUrl" id="adLogoUrl" onchange="fileChange(this)" />  -->
										<!-- <span id="logoUrl_Wrning" style="color:red;width:5px;height:28px;line-height:28px">*</span> -->
                                    </div>
                                </div>
                                
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认新建分类图片</button>
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
                    	categoryCode: {
                            required: true
                        },
                     
                    },
                    messages: {
                    	categoryCode: {
                            required: "分类编码不能为空！"
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
