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
        <title>后台用户列表</title>
        <script type="text/javascript">
          function deleteUser(id){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/user/deleteUser/"+id+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要删除该用户吗？", "删除提示",submit);
        }; 
        </script>
        <script type="text/javascript">
        function updateUser(id){
        	window.location.href = "${contextPath}/user/goEdit/"+id+"/"+new Date().getTime();
        }
        </script>
        
        <script type="text/javascript">
        
        function updateStatus(id){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/user/updateUserStatus/"+id+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要修改该用户状态吗？", "修改提示",submit);
        }
        </script>
        
        
    </head>
    <body id="" class="Cog">
    
      <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
               <%--  <div id="menu">
                   <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>用户列表</h3>
                        </div>
                           <div class="panel-body">
	                          <form class="form-inline page" id="findForm" method="post">
	                                <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">用户姓名：</label>
	                                    <input type="text" class="form-control" id="name" name="loginName" value="${userDto.loginName}" placeholder="请输入用户名">
	                                </div>
	                                
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">手机号码：</label>
	                                    <input type="text" class="form-control" id="phone" name="phone" value="${userDto.phone}" placeholder="请输入电话">
	                                </div>
	                                <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">用户角色：</label>
	                                    <select name="roleId" class="form-control lg-select">
	                                      <option value="">所有</option>
	                                      <c:forEach items="${authRoleList}" var="authRole">
	                                      	<option value="${authRole.id }" ${authRole.id == userDto.roleId ?'selected':''}>${authRole.name}</option>
	                                      </c:forEach>
	                                    </select>
	                                </div>
	                               
	                                <div class="form-group col-md-4 mB15">
	                                   <button type="submit" class="btn btn-success speBtn">查 询</button>
	                                   <a href="${contextPath}/user/goAdd" class="btn btn-info speBtn">新增</a>
	                                </div>
	                                <div class="clearfix"></div>
	                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
									<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
									<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
									<input type="hidden" name="total" value="${page.total}"/>
	                            </form>
		                        <div class="panel-body">
		                            <!-- table -->
		                            <div class="table-responsive">
		                                <table class="table table-bordered ta ble-hover" id="roleTable">
		                                    <thead>
		                                        <tr>
				                                    <th>序号</th>
													<th>用户名称</th>
													<th>手机号码</th>
													<th>角色名</th>
													<th>用户类型</th>
													<th>备注</th>
													<th>操作</th>
		                                        </tr>
		                                    </thead>
		                                    <tbody>
		                                    <c:forEach items="${authUserDtoList}" var="list" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${list.loginName}</td>
													<td>${list.phone}</td>
													<td>${list.roleName}</td>
													<td>
														<c:choose>
															<c:when test="${list.userType == 2}">
																普通用户
															</c:when>
															<c:when test="${list.userType == 1}">
																系统用户
															</c:when>
														</c:choose>
													</td>	
													<td>${list.memo}</td>
		                                            <td>
		                                           <c:if test="${list.userType == 1}">
		                                                                                                                                                    系统用户
		                                           </c:if>
		                                           
		                                           <c:if test="${list.userType == 2}">
		                                               <button type="button"  class="btn btn-success btn-xs delete-role" onclick="updateUser(${list.id});">修改</button>
													   <button type="button" class="btn btn-danger btn-xs delete-role"  onclick="deleteUser(${list.id});">删除</button>
												       
												       <c:choose>
															<c:when test="${list.isDisabled == 'n'}">
																	<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >禁用	</a>
															</c:when>
															<c:when test="${list.isDisabled == 'y'}">
																	<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >解禁	</a>
															</c:when>
														</c:choose>
		                                           </c:if>
		                                           
		                                             
		                                            </td>
		                                        </tr>
		                                    </c:forEach>
		                                    </tbody>
		                                </table>
		                            </div>
		                              <!-- 分页 -->
		                             <tags:page formId="findForm" url="${contextPath}/user/authUserList"></tags:page>
		                            <!-- 分页 end -->
		                            
		                        </div>
                        <!-- /.panel-body -->
                           </div>
                        </div>
                <!-- /.content -->
                   </div>
               </div>
        <!-- /.container-fluid -->
           </div>
     
    </body>
</html>