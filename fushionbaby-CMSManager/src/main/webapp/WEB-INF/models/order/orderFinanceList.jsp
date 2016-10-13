<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>订单财务信息列表</title>
  	<script type="text/javascript">
  	function query(){
  		$('#findForm').submit();
  		
  	}
  	
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 订单财务信息列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/orderFinance/queryOrderFinanceList">
				     	<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">会员名：</label>
	      					<input type="text" id="code" name="memberName" value="${orderFinanceDto.memberName}" class="form-control"
	      						 placeholder="会员名">
	  					</div>
					    <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">订单编码：</label>
	      					<input type="text" id="code" name="orderCode" value="${orderFinanceDto.orderCode}" class="form-control"
	      						 placeholder="产品编码">
	  					</div>
				        <div class="form-group col-md-4 mB15">
	    					<label for="e" class="col-label">财务状态：</label>
                            <select name="financeStatus" class="form-control lg-select">
                                <option value="">所有</option>
                                <option value="y" ${"y"==orderFinanceDto.financeStatus?'selected':''}>已付款</option>
                                <option value="n" ${"n"==orderFinanceDto.financeStatus?'selected':''}>未付款</option>
                            </select>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="e" class="col-label">付款方式：</label>
                            <select name="paymentType" class="form-control lg-select">
                                <option value="">所有</option>
                                <c:forEach var="state" items="${paymentStateMap}">
                                     <option value="${state.key}" <c:if test="${state.key == orderFinanceDto.paymentType }"> selected="selected" </c:if> > ${state.value}</option>
							    </c:forEach>   
                            </select>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">最终金额大于：</label>
	      					<input type="text" id="code" name="paymentTotalActualFrom" value="${orderFinanceDto.paymentTotalActualFrom}" class="form-control"
	      						 placeholder="最终金额大于">
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">最终金额小于：</label>
	      					<input type="text" id="code" name="paymentTotalActualTo" value="${orderFinanceDto.paymentTotalActualTo}" class="form-control"
	      						 placeholder="最终金额小于">
	  					</div>
					   <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">付款结束开始时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="paymentCompleteTimeFrom" value="${orderFinanceDto.paymentCompleteTimeFrom}" class="timeS form-control form_datetime" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                	</div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">付款结束结束时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="paymentCompleteTimeTo" value="${orderFinanceDto.paymentCompleteTimeTo}" class="timeE form-control form_datetime" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
					    <div class="col-md-8">
	                        <button type="button" class="btn btn-info" onclick="query()">查询</button>
	                        
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
                              		<th>会员名</th>
									<th>订单编码</th>
									<th>财务状态</th>
									<th>付款方式</th>
									<th>最终金额</th>
									<th>付款时间</th>
									<th>是否需要发票</th>
									<th>发票类型</th>
									<th>订单详情</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${orderFinanceDtoList}" var="finance" varStatus="status">
								<tr>
								  	<td>${status.count }</td>

									<td>${finance.memberName}</td>

									<td>${finance.orderCode}</td>
									
									<td>
										<c:if test="${'y' == finance.financeStatus }"> 已付款 </c:if>
										<c:if test="${'n' == finance.financeStatus }"> 未付款 </c:if>
									</td>

									<td>${paymentStateMap[finance.paymentType]}</td>
									
									<td>${finance.paymentTotalActual}</td>

									<td><fmt:formatDate value="${finance.paymentCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									
									<td>
										<c:if test="${'y' == finance.isInvoice }"> 需要发票 </c:if>
										<c:if test="${'n' == finance.isInvoice }"> 不需要发票 </c:if>
									</td>

									<td>
										<c:if test="${'1' == finance.invoiceType }"> 个人 </c:if>
										<c:if test="${'2' == finance.invoiceType }"> 公司 </c:if>
									</td>
									<td>
                                        <a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${finance.memberId}/${finance.orderCode}" title="订单详情">	
											订单详情
										</a>
									</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
							<tags:page formId="findForm" url="${contextPath }/orderFinance/queryOrderFinanceList"></tags:page>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
