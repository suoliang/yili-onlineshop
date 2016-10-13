<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>修改门店额外信息</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改门店额外信息</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/storeExtraInfo/updateStoreExtraInfo" method="post">
                                <input type="hidden" value="${storeExInfoDto.id}" name="id">
								
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店编号：</label>
                                    <div class="col-sm-5">
                                        <input name="storeNumber"  type="text" class="form-control" value="${storeExInfoDto.storeNumber}" readonly="readonly">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店名称：</label>
                                    <div class="col-sm-5">
                                        <input name="storeName"  type="text" class="form-control" value="${storeExInfoDto.storeName}" readonly="readonly">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">店主姓名：</label>
                                    <div class="col-sm-5">
                                        <input name="linkMan"  type="text" class="form-control" value="${storeExInfoDto.linkMan}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">身份证号：</label>
                                    <div class="col-sm-5">
                                        <input name="identityCardNo"  type="text" class="form-control" value="${storeExInfoDto.identityCardNo}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店固定电话：</label>
                                    <div class="col-sm-5">
                                        <input name="telephone"  type="text" class="form-control" value="${storeExInfoDto.telephone}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">手机号码：</label>
                                    <div class="col-sm-5">
                                        <input name="mobile"  type="text" class="form-control" value="${storeExInfoDto.mobile}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">邮编：</label>
                                    <div class="col-sm-5">
                                        <input name="zip"  type="text" class="form-control" value="${storeExInfoDto.zip}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店地址：</label>
                                    <div class="col-sm-5">
                                        <input name="address"  type="text" class="form-control" value="${storeExInfoDto.address}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">门店描述：</label>
                                    <div class="col-sm-5">
                                        <input name="description"  type="text" class="form-control" value="${storeExInfoDto.description}">
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
    

    </body>
</html>
