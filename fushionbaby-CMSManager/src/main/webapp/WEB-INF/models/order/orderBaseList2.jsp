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
        <title>订单列表</title>
        <script type="text/javascript">
	        function query(){
	      		$('#findForm').submit();
	      	}
        </script>
    </head>
    <body id="index">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 订单列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post" action="${contextPath}/order/orderBaseList">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a"  class="col-label">订单编码：</label>
                                    <input type="text" name="orderCode" value="${orderBaseDto.orderCode}" class="form-control" id="" placeholder="请输入订单编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">用&ensp;户&ensp;名：</label>
                                    <input type="text" name="memberName" value="${orderBaseDto.memberName}" class="form-control" id="" placeholder="请输入用户名">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">订单状态：</label>
                                    <select name="orderStatus" class="form-control lg-select">
                                        <option value="">所有</option>
                                       <c:forEach var="state" items="${orderStateMap}">
                                       		<option value="${state.key}" <c:if test="${state.key == orderBaseDto.orderStatus }"> selected="selected" </c:if> > ${state.value}</option>
									   </c:forEach>  
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单开始时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeFrom" value="${orderBaseDto.createTimeFrom}" class="timeS form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                	</div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单结束时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeTo" value="${orderBaseDto.createTimeTo}" class="timeE form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                   <div class="form-group col-md-4 mB15">
	                               <label for="a" class="col-label">门店名称：</label>
	                                 <select id="storeCode" name="storeCode" class="form-control lg-select" >
	                                     <option value="">阿拉丁商城</option>
	                                   <%--   <c:forEach items="${storeList }" var="store">
	                                        <option value="${store.code}" <c:if test="${store.code == orderBaseDto.storeCode }"> selected="selected" </c:if>  > ${store.name}</option>
										  </c:forEach>  --%>
										   <c:forEach items="${storeMap }" var="storeMap">
	                                         <option value="${storeMap.key}" <c:if test="${storeMap.key == orderBaseDto.storeCode }"> selected="selected" </c:if>  > ${storeMap.value}</option>
										  </c:forEach> 
										   
	                                 </select>
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
                                            <th>会员名</th>
                                            <th>订单编码</th>
                                            <th>下单时间</th>
                                            <th>付款时间</th>
                                            <th>下单金额</th>
                                            <th>支付方式</th>
                                            <th>订单状态</th>
                                            <th>付款状态</th>
                                            <th>物流状态</th>
                                            <th>收货人</th>
                                            <th>联系电话</th>
                                            <th>会员留言</th>
                                            <th width="100px">订单类型</th>
                                            <th width="100px">门店名称</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${orderBaseList}" var="order" varStatus="status">
	                                        <tr>
	                                            <th>
	                                                <label class="check-label">
	                                                    <input type="checkbox" name="checkAll">
	                                                </label>
	                                            </th>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${order.memberName}<input type="hidden" class="theMemberId" value="${order.memberId}"></td>
	                                            <td>${order.orderCode}<input type="hidden" class="theOrderCode" value="${order.orderCode}"></td>
	                                            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td><fmt:formatDate value="${order.paymentCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>${order.paymentTotalActual}</td>
	                                            <td>
	                                            	<c:out value="${paymentStateMap[order.paymentType]}"></c:out>
	                                            </td>
	                                            <td>
	                                            	<c:out value="${orderStateMap[order.orderStatus]}"></c:out><input type="hidden" class="theOrderStatus" value="${orderStateMap[order.orderStatus]}">
												</td>
	                                            <td>
	                                            	<c:choose>
	                                            		<c:when test="${order.financeStatus =='y'}">已付款</c:when> 
													  	<c:otherwise>未付款</c:otherwise> 
	                                            	</c:choose>
	                                            </td>
	                                            <td>
	                                               	<c:choose>
	                                            		<c:when test="${order.transStatus =='y'}">已发货</c:when> 
													  	<c:otherwise>未发货</c:otherwise> 
	                                             	</c:choose>
												</td>
	                                            <td>${order.receiver}</td>
	                                            <td>${order.receiverMobile}</td>
	                                            <td>${order.memo}</td>
	                                            <td id="orderType_${order.orderCode}_${order.memberId}">
	                                               	<c:choose>
	                                            		<c:when test="${order.orderType =='1'}">正常单</c:when>
	                                            		<c:when test="${order.orderType =='2'}">测试单</c:when>
	                                            		<c:when test="${order.orderType =='3'}">问题单</c:when> 
	                                             	</c:choose>
												</td>
	                                            <td>${storeMap[order.storeCode]}</td>
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
                            <tags:page formId="findForm" url="${contextPath}/order/orderBaseList"></tags:page>
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
