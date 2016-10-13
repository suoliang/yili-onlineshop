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
        <title>版本列表</title>
    </head>
    <script type="text/javascript">
    function addVersion(id){
    	window.location.href="${contextPath}/version/goEdit/"+id+"/"+new Date().getTime();
    }
    function  deleteVersion(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/version/deleteVersion/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该版本吗？", "删除提示",submit);
    }
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
              <%--   <div id="menu">
                <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 版本列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">版本号：</label>
                                    <input type="text" class="form-control" id="name" name="versionNum" value="${versionNum}" placeholder="请输入版本号">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">版本来源：</label>
                                    <select name="sourceCode" class="form-control lg-select">
                                      <option value="" >所有</option>
	                                  <c:forEach items="${sysmgrSourceList}" var="sourceList" >
										<option value="${sourceList.code}" ${sourceCode==sourceList.code?'selected':''} >${sourceList.name}</option>
									  </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/version/goAdd" class="btn btn-info speBtn">新增</a>
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
											<th>版本号</th>
											<th>版本名称</th>
											<th>版本内容</th>
											<th>发布时间</th>
											<th>版本链接</th>
											<th>版本来源</th>
											<th>是否强制</th>
											<th>状态</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${sysmgrVersionList}" var="versionList" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${versionList.versionNum }</td>
											<td>${versionList.name }</td>
											<td>${versionList.feature }</td>
											<td><fmt:formatDate value="${versionList.createTime }" pattern="yyyy-MM-dd"/></td>
											<td>${versionList.url }</td>
											  <c:forEach items="${sysmgrSourceList}" var="list" >
	                                              <c:choose >
	                                                 <c:when test="${list.code eq versionList.sourceCode}"><td >${list.name }</td></c:when>
	                                              </c:choose>
	                                           </c:forEach>
										       <c:if test="${versionList.isFource==1}">
	                                                <td>强制更新</td>
	                                           </c:if>
	                                           <c:if test="${versionList.isFource==2}">
	                                              <td>不强制更新</td>
	                                           </c:if>
	                                        <td>
	                                        	<c:choose>
                                            		<c:when test="${versionList.isDeleted == 'y'}">已删除</c:when> 
												  	<c:otherwise>未删除</c:otherwise> 
                                            	</c:choose>
	                                        </td>
                                            <td>
                                          		<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:addVersion('${versionList.id }');">修改</button>
										  		<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteVersion('${versionList.id}');">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/version/versionList"></tags:page>
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