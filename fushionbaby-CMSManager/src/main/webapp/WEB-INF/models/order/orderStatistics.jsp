<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.fushionbaby.common.enums.OrderConfigServerEnum"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>订单财务统计列表</title>
        <script type="text/javascript">
	        function exportOrderStatisticsExcel(){
				$("#findForm").attr("action",contextPath+"/order/export_excel_orderStatistics_list");
				$("#findForm").submit();
				$("#findForm").attr("action",contextPath+"/order/orderStatistics/n");
				
			}
        </script>
    </head>
    <body id="index">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>
                            	订单财务统计
							</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post" action="${contextPath}/order/orderStatistics/n">
                                <div class="form-group col-md-4 mB15">
		    					<label for="e" class="col-label">付款方式：</label>
		                            <select name="paymentType" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <c:forEach var="state" items="${paymentStateMap}">
		                                     <option value="${state.key}" <c:if test="${state.key == orderBaseDto.paymentType }"> selected="selected" </c:if> > ${state.value}</option>
									    </c:forEach>   
		                            </select>
			  					</div>
                               
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单开始时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeFrom" value="${orderBaseDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                	</div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单结束时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeTo" value="${orderBaseDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <input type="hidden" name="orderStatus" value="8">
                                <div class="form-group col-md-12 mB15">
                                	<button type="submit" class="btn btn-success speBtn">查 询</button>
                                	<button type="button" onclick="exportOrderStatisticsExcel()" class="btn btn-success speBtn">导出统计列表</button> 
                                </div>
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                             <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            
                                            <th>总金额</th>
                                            <th>商品总金额</th>
                                            <th>运费</th>
                                            <th>红包</th>
                                            <th>优惠券</th>
                                            <th>积分</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
	                                        <tr>
	                                            <td>${totalActualTotal}</td>
	                                            <td>${totalAfDiscountTotal}</td>
	                                            <td>${actualTransferFeeTotal}</td>
	                                            <td>${redEnvelopeAmountTotal}</td>
	                                            <td>${cardAmountTotal}</td>
	                                            <td>${epointsRedeemMoneyTotal}</td>
	                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>会员名</th>
                                            <th>订单编码</th>
                                            <th>下单时间</th>
                                            <th>支付方式</th>
                                            <th>总金额</th>
                                            <th>商品总金额</th>
                                            <th>运费</th>
                                            <th>红包 </th>
                                            <th>优惠券</th>
                                            <th>积分</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${orderBaseList}" var="order" varStatus="status">
	                                        <tr>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${order.memberName}</td>
	                                            <td>${order.orderCode}</td>
	                                            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td><c:out value="${paymentStateMap[order.paymentType]}"></c:out></td>
	                                            <td>${order.totalActual}</td>
	                                            <td>
	                                            	${order.totalAfDiscount}
	                                            </td>
	                                            <td>${order.actualTransferFee}</td>
	                                            <td>
	                                            	${order.redEnvelopeAmount}
	                                            </td>
	                                            <td>
	                                               	${order.cardAmount}
												</td>
	                                            <td>${order.epointsRedeemMoney}</td>
	                                           
	                                            <td>
	                                            	<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${order.memberId}/${order.orderCode}" title="订单详情">	
														订单详情
													</a>
	                                            </td>
	                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <tags:page formId="findForm" url="${contextPath}/order/orderStatistics/n"></tags:page>
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
