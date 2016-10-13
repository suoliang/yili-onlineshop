<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>字典管理</title>
	 <script type="text/javascript">
            $(document).ready(function() {
            	
            	/*表单验证*/
                var validator = $('#dictForm').validate({
                    rules: {
                    	label: {
                            required: true,
                            rangelength: [1, 50],
                           
                        },
                        value: {
                            required: true,
                            rangelength: [1, 300],
                        },
                        type: {
                            required: true,
                            rangelength: [1, 50],
                           
                        },
                        description: {
                            required: true,
                            rangelength: [1, 300],
                        },
                        sort: {
                            required: true,
                            digits:true,
                            min:1,
                            max:99999999999
                            
                        }
                        
                    },
                    messages: {
                    	label: {
                            required: "请填写字典标签！",
                            rangelength: "字典标签长度在1-50之间！",
                        },
                        value: {
                        	required: "请填写字典值！",
                        	rangelength: "字典值长度在1-300之间！"
                        },
                        type: {
                        	required: "请填写字典类型！",
                        	rangelength: "字典类型长度在1-50之间！"
                           
                        },
                        description: {
                        	required: "请填写字典描述！",
                        	rangelength: "字典描述长度在1-300之间！"
                        },
                        sort: {
                        	required: "请填写字典顺序！",
                        	digits:"字典顺序必须是整数！",
                        	min:"字典顺序必须是大于1的整数！",
                        	max:"字典顺序必须是小于99999999999的整数！"
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
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 字典信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/sysmgr/saveDict" id="dictForm" >
				 
				 		<input name="id" id="id" type="hidden" class="form-control"
						 			value="${sysmgrDictConfig.id}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">字典标签：</label>
							<div class="col-sm-2">
								<input name="label" id="label" type="text" class="form-control"
						 			value="${sysmgrDictConfig.label}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">字典值：</label>
							<div class="col-sm-2">
								<input name="value" id="value" type="text" class="form-control" 
								 	value="${sysmgrDictConfig.value}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">字典类型：</label>
							<div class="col-sm-2">
								<input name="type" id="type" type="text" class="form-control"
						 			value="${sysmgrDictConfig.type}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">类型描述：</label>
							<div class="col-sm-2">
								<input name="description" id="description" type="text" class="form-control" 
								 	value="${sysmgrDictConfig.description}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">字典顺序：</label>
							<div class="col-sm-2">
								<input name="sort" id="sort" type="text" class="form-control" 
								 	value="${sysmgrDictConfig.sort}"/>
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