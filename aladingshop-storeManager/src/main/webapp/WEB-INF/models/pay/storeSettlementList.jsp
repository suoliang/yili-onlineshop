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
	<title>已结算列表</title>
  	<script type="text/javascript">
	  	function showDetails(id){
	  		window.location.href = "${contextPath }/storeDailyInfo/dailyOrderList/"+id+"/"+ new Date().getTime();
	  	}
	  	
  	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 已结算列表</h3>
                   </div>
                   <div class="panel-body">
				      <%-- <form class="form-inline page" id="findForm" method="post" action="${contextPath }/applyDailyDetails/applyDailyDetailsList"> --%> 
				  	 <form class="form-inline page" id="findForm" method="post" > 

							<div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单开始时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeFrom" value="${storeSponsorsDailyDetailsDto.createTimeFrom}" class="timeS form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                	</div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单结束时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeTo" value="${storeSponsorsDailyDetailsDto.createTimeTo}" class="timeE form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                
                                <div class="form-group col-md-12 mB15">
      								<span style="padding-right:15px">结算金额<b style="color:red;font-size:130%;padding:0 5px">${totalAmount }</b>元</span>
      								<span>结算订单总计<b style="color:red;font-size:130%;padding:0 5px">${totalOrderNum }</b>个</span>
   								</div>
                                <div class="form-group col-md-12 mB15">
                                	<button type="submit" class="btn btn-info speBtn">查 询</button>
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
                              <th>
                                  <label for="checkAllBtn" class="check-label">
                                      <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                  </label>
                              </th>
                              <th>序号</th>
                           	  <th>结算周期</th>
                           	  <th>订单金额</th>
							  <th>订单数</th>
							  <th>结算方式</th>
							  <th>结算时间</th>
							  <th>状态</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
									  <th>
	                                       <label class="check-label">
	                                           <input type="checkbox" name="checkAll"  value="${list.dailyNumber }">
	                                       </label>
	                                   </th>
	                                   <td scope="row">${status.count}</td>
									   <td><fmt:formatDate value="${list.settlePeriod}" pattern="yyyy-MM-dd"/></td>
									   <td>${list.realIncomeAmount}</td>	
									   <td>${list.settleOrderCount}</td>	
									   <td>${list.settleMethod}</td>	
									   <td>${list.settlementTime}</td>	
									   <td>已结算</td>	
									   <td>
										 <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  
										 	 onclick="showDetails('${list.id}')">详情</a>
									   </td>
									 	
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
                        </div>
                          <!-- 分页 -->
	                       <tags:page formId="findForm" url="${contextPath}/storeSponorsApplySettle/storeSettlementList"></tags:page>
	                     <!-- 分页 end --> 
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
