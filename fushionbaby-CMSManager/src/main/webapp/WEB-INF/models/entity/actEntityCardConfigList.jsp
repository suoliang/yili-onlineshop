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
        <title>实体卡配置列表</title>
    </head>
    <script type="text/javascript">
    function updateStatus(id){
    	var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/actEntityCardConfig/updateStatus/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要修改实体卡配置吗？", "修改提示",submit);
    }

    function Edit(id){
    	window.location.href = "${contextPath}/actEntityCardConfig/actEntityCardConfigEditJsp/"+id+"/"+new Date().getTime();
    }
    
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>实体卡配置列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                
							    <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">面值：</label>
			      					<input type="text" id="faceMoney" name="faceMoney" class="form-control" value="${faceMoney}"  
			      						placeholder="面值" >
			  					</div>
								
							    <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">名称：</label>
			      					<input type="text" id="name" name="name" class="form-control" value="${name}"  
			      						placeholder="名称" >
			  					</div>
								
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/actEntityCardConfig/actEntityCardConfigAddJsp" class="btn btn-info speBtn">新增</a>
                                </div>
                               
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}"/>  
								<input type="hidden" name="limit" value="${page.limit}"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>名称</th>
                                            <th>出售金额</th>
                                            <th>面值</th>
                                            <th>生效时间</th>
	                                        <th>失效时间</th>
                                            <th>创建时间</th>
										<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            
	                                            <td>${list.name}</td>
	                                            <td>${list.sellMoney}</td>
	                                            <td>${list.faceMoney}</td>
                                            	<td><fmt:formatDate value="${list.beginTime }" pattern="yyyy-MM-dd" /></td>
                                            	<td><fmt:formatDate value="${list.expiration }" pattern="yyyy-MM-dd" /></td>
                                            	<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:Edit('${list.id}');">修改</button>
                                            	<%-- <button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:Del('${list.id}');">删除</button> --%>
                                            	
                                            	<c:choose>
													<c:when test="${list.isDisabled == 'n'}">
														<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >
															禁用	
														</a>
													</c:when>
													<c:when test="${list.isDisabled == 'y'}">
														<a class="btn btn-warning btn-xs" href="javascript:updateStatus('${list.id}');" >
															解禁	
														</a>
													</c:when>
												</c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/actEntityCardConfig/actEntityCardConfigList"></tags:page>
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