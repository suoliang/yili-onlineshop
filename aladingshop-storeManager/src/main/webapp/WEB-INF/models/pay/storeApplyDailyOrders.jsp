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
	<title>申请结算明细</title>
  
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 申请结算订单明细</h3>
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
                           	  <th>订单号</th>
                           	  <th>支付方式</th>
							  <th>支付平台流水号</th>
							  <th>订单金额</th>
							  <th>实际金额</th>
							  <th>支付时间</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
	                                   <td scope="row">${status.count}</td>
									   <td>${list.orderCode}</td>	
									   <td>${paymentType[list.paymentType] }</td>	
									   <td>${list.tradeSerialNumber}</td>	
									   <td>${list.settleAmount}</td>	
									   <td>${list.realIncomeAmount}</td>	
									   <td><fmt:formatDate value="${list.paymentCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                              
                               <!-- 分页 -->
	                            <tags:page formId="findForm" url="${contextPath}/storeSponorsApplySettle/storeApplyOrderInfo/${dailyNumber}/${time}"></tags:page>
	                            <!-- 分页 end -->
                           </div>
                            
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
