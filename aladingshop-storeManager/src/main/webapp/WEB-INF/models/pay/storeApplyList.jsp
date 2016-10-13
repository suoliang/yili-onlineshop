<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>申请列表</title>
  
  	<script type="text/javascript">
  		function showDetails(applyNumber){
  			window.location.href = "${contextPath }/storeSponorsApplySettle/storeApplyDetails/"+ applyNumber + "/"+ new Date().getTime();
  		}
  	
  	</script>
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 申请列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" >

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
                           	  <th>申请编号</th>
                           	  <th>申请时间</th>
							  <th>总金额</th>
							  <th>结算天数</th>
							  <th>结算方式</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
	                                   <td scope="row">${status.count}</td>
									   <td>${list.applyNumber}</td>	
									   <td><fmt:formatDate value="${list.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									   <td>${list.applyTotalAmount}</td>	
									   <td>${list.applyDayNumber}</td>	
									   
									   <td>${list.settleMethod}</td>	
									   <td> <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  
										 	 onclick="showDetails('${list.applyNumber}')">详情</a>
										 	 </td>	
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                              
                               <!-- 分页 -->
	                            <tags:page formId="findForm" url="${contextPath }/storeSponorsApplySettle/storeApplyList"></tags:page>
	                            <!-- 分页 end -->
                           </div>
                            
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
