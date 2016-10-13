<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>修改角色 - CMS</title>
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 修改角色</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal" id="addRoleForm" action="${contextPath }/role/updateRole" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色名称：</label>
                                    <div class="col-sm-5">
                                        <input name="name" type="text" class="form-control" value="${role.name }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">角色描述：</label>
                                    <div class="col-sm-5">
                                        <input name="description" type="text" class="form-control" value="${role.description }">
                                    </div>
                                </div>
                                <input name="id" type="hidden" value="${role.id }">

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">赋予权限：</label>
                                    <div class="col-sm-7">
                                     <input id="privilegeIds" class="form-control" type="hidden"  name="privilegeIds"/> 
									 <ul id="privilegeTree" class="ztree"></ul>
                                     <div id="addRoleAlert"></div>
                                    </div>
                                </div>
                            
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button type="submit" class="btn btn-info">确认修改角色</button>
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
        var setting = {
				view: {
					dblClickExpand: true
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: null
				},  
				check: {
			        enable: true,
			        chkStyle: "checkbox",
			        chkboxType: { "Y": "s", "N": "ps" }
			      },
			};
        debugger;
			var zNodes =${privilegeJson};
			
            $(document).ready(function() {
            	   // 初始化树
                zTreeObj= $.fn.zTree.init($("#privilegeTree"), setting, zNodes);	
                /*表单验证*/
                var validator = $('#addRoleForm').validate({
                    rules: {
                        name: {
                            required: true,
                            rangelength: [2, 10],
                        },
                        access: {
                            required: true
                        }
                    },
                    messages: {
                        name: {
                            required: "请填写角色名！",
                            rangelength: "角色名长度应该为2-10位"
                        },
                        access: {
                            required: "请至少分配一项权限！"
                        }
                    },
                    errorPlacement: function(error, element) {
                        if ( element.is(":checkbox") ){
                            error.appendTo($("#addRoleAlert") );
                        } else {
                            error.appendTo(element.parent());
                        }
                    }, 
                    submitHandler: function(form) {
                    	  /** 得到树中选中的数组*/
                    	var treeObj = $.fn.zTree.getZTreeObj("privilegeTree");
                    	var nodes = treeObj.getCheckedNodes(true);
                    	for(var item in nodes) {
                    	var ids= $("#privilegeIds").val();
                    	    ids=ids+","+nodes[item].id;
                    	 $("#privilegeIds").val(ids);
                    }
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    }
                });

            });
        </script>
    </body>
</html>
