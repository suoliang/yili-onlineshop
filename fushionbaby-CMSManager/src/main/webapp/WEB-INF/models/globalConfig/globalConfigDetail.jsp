<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>全局设置管理</title>
	 <script type="text/javascript">
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#globalConfigForm').validate({
                    rules: {
                    	name: {
                            required: true,
                            rangelength: [1, 100],
                           
                        },
                        code: {
                            required: true,
                            rangelength: [1, 100],
                        },
                        value: {
                            required: true,
                            rangelength: [1, 255],
                           
                        },
                        remark: {
                            rangelength: [0, 255],
                        }
                        
                    },
                    messages: {
                    	name: {
                            required: "请填写全局设置名称！",
                            rangelength: "全局设置名称长度在1-100之间！",
                        },
                        code: {
                        	required: "请填写全局设置编码！",
                        	rangelength: "全局设置编码长度在1-100之间！"
                        },
                        value: {
                        	required: "请填写全局设置值！",
                        	rangelength: "全局设置值在1-255之间！"
                           
                        },
                        remark: {
                        	rangelength: "全局设置备注长度在0-255之间！"
                        }
                    }
                    
                   ,
                    
                    submitHandler: function(form) {
                        // 验证成功后操作
                       console.log($(form).serialize());
                       form.submit();
                    }
                });
            });
            
        </script>
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 全局设置信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/globalConfig/saveGlobalConfig" id="globalConfigForm" >
						<div class="form-group">
							<label class="col-sm-2 control-label">全局设置名称：</label>
							<div class="col-sm-2">
								<input name="name" id="name" type="text" class="form-control"
						 			value="${sysmgrGlobalConfig.name}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">全局设置编码：</label>
							<div class="col-sm-2">
								<input name="code" id="code" type="text" class="form-control"
						 			value="${sysmgrGlobalConfig.code}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">全局设置值：</label>
							<div class="col-sm-2">
								<input name="value" id="value" type="text" class="form-control" 
								 	value="${sysmgrGlobalConfig.value}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						
				
						<div class="form-group">
							<label class="col-sm-2 control-label">备注：</label>
							<div class="col-sm-2">
								<input name="remark" id="remark" type="text" class="form-control" 
								 	value="${sysmgrGlobalConfig.remark}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      	
					      		<button class="btn btn-success " type="submit">确定</button>
								<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
					   	 	</div>
						</div>
				  
				</form>
			  </div>
			  </div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>