<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->    
<!DOCTYPE html>
<html lang="zh-CN">
<!-- javascript -->
    <head>
        <meta charset="utf-8" />
        <title>后台用户角色列表</title>
        
        <script type="text/javascript">
          function deleteRole(id){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/role/deleteRole/"+id+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要删除该角色吗？", "删除提示",submit);
	  		
        }; 
        
        </script>
        
        
        <script type="text/javascript">
        function updateRole(id){
        	window.location.href = "${contextPath}/role/goEdit/"+id+"/"+new Date().getTime();
        }
        </script>
    </head>
    <body id="" class="Cog">
    
      <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
             <%--    <div id="menu">
                <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 角色列表</h3>
                        </div>
                         
	                           <div class="panel-body">
	                              <a href="${contextPath}/role/goAdd" class="btn btn-info speBtn">新增</a>
	                            </div>
                          
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered ta ble-hover" id="roleTable">
                                    <thead>
                                        <tr>
	                                        <th>序号</th>
											<th>角色名称</th>
											<th>角色描述</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
											<td>${list.name}</td>
											<td>${list.description}</td>
                                            <td>
                                            		    <button type="button" class="btn btn-success btn-xs delete-role"  onclick="updateRole(${list.id});">修改</button>
                                            			
                                            			<button type="button"  class="btn btn-danger btn-xs delete-role" onclick="deleteRole(${list.id});">删除</button>
												  	
												  
                                             
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        
        <!-- /.container-fluid -->

       
    </body>
</html>