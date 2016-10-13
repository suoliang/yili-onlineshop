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
        <title>权限列表</title>
    </head>
    <script type="text/javascript">
    function updatePrivilege(id){
    	window.location.href="${contextPath}/storePrivilege/goEdit/"+id+"/"+new Date().getTime();
    }
    
    function addPrivilege(){
    	
    /* 	var storeCode=$("#storeCode").val();
    	if(storeCode == null || storeCode == ""){
    		jBox.tip("您还没有选择门店！");
    		return ;
    	} 
    	window.location.href="${contextPath}/storePrivilege/goAdd/"+storeCode+"/"+new Date().getTime();
    	*/
    	window.location.href="${contextPath}/storePrivilege/goAdd/"+new Date().getTime();
    }
    
    function  deletePrivilege(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/storePrivilege/deletePrivilege/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该权限吗？", "删除提示",submit);
    }
    </script>
    
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
                            <h3 class="panel-title"><i class="fa fa-user"></i> 门店权限列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">权限名称：</label>
                                    <input type="text" class="form-control" id="name" name="name" value="${name}" placeholder="请输入权限名称">
                                </div>
                                
                        <%--         <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">门店名称：</label>
                                    <select id="storeCode" name="storeCode" class="form-control lg-select" >
                                       <option value="">所有</option>
                                       <c:forEach items="${storeList }" var="store">
                                       		<option value="${store.code}" <c:if test="${store.code == storeCode }"> selected="selected" </c:if>  > ${store.name}</option>
									   </c:forEach>  
                                    </select>
                                </div> --%>
                                
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <button type="button" class="btn btn-info speBtn"  onclick="javascript:addPrivilege();">新增</button>
                                </div>
                               
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
											<th>权限名称</th>
											<th>权限url</th>
											<th>权限等级</th>
											<th>权限sortNo</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.name}</td>
											<td>${ list.url}</td>
											<td>
												<c:if test="${list.level eq '1' }">一级</c:if>
											    <c:if test="${list.level eq '2' }">二级</c:if>
											</td>
											<th>${list.sortNo }</th>
                                            <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:updatePrivilege('${list.id }');">修改</button>
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deletePrivilege('${list.id}');">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/storePrivilege/privilegeList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

    </body>
</html>