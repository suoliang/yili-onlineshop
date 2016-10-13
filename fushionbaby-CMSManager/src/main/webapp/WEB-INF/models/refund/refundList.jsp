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
        <title>退款列表</title>
        <script type="text/javascript">
	       
	    
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 退款列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">订单号：</label>
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${refundDto.orderCode}" placeholder="请输入订单号">
                                </div>    
                                <div class="form-group col-md-4 mB15">
			    					<label for="e" class="col-label">付款方式：</label>
		                            <select name="paymentType" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <c:forEach var="state" items="${paymentStateMap}">
		                                     <option value="${state.key}" <c:if test="${state.key == refundDto.paymentType }"> selected="selected" </c:if> > ${state.value}</option>
									    </c:forEach>   
		                            </select>
			  					</div>
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">订单来源：</label>
                                    <select name="sourceCode" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <c:forEach var="source" items="${sourceMap}">
		                                     <option value="${source.key}" <c:if test="${source.key == refundDto.sourceCode }"> selected="selected" </c:if> > ${source.value}</option>
									    </c:forEach>   
		                            </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="d" class="col-label">退款开始时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${refundDto.createTimeFrom}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">退款结束时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${refundDto.createTimeTo}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                    
                                </div>
                                
                                <div class="col-md-8">
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
                                            <th>序号</th>
                                            <th>退款批次号</th>
                                            <th>订单号</th>
                                            <th>用户名</th>
                                            <th>退款金额</th>
                                            <th>红包金额</th>
                                            <th>付款方式 </th>
                                            <th>订单来源</th>
                                            <th>退款时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.result}" var="refund" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${refund.batchNo}</td>
                                            <td>${refund.orderCode}</td>
                                            <td>${refund.memberName}</td>
                                            <td>${refund.settleAmount}</td>
                                            <td>${refund.redEnvlopeAmount}</td>
                                            <td>${paymentStateMap[refund.paymentType]}</td>
                                            <td>${sourceMap[refund.sourceCode]}</td>
                                            <td><fmt:formatDate value="${refund.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        	<td>
                                        		<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${refund.memberId}/${refund.orderCode}" title="订单详情">	
													订单详情
												</a>
											</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/refund/refundList"></tags:page>
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