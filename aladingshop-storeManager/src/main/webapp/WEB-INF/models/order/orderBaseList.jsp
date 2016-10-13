<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.fushionbaby.common.enums.OrderConfigServerEnum"%>
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
	    
	    	//批量更新
			function updateAll(status) {
	    		debugger;
	    		
				var checkedLen = $("input[name='checkAll']:checked").length;
				if(checkedLen == 0) {
					jBox.tip("请先选中后，操作!");
					return false;
				}
				
				var result = confirm("确定提交吗?");
				if(result == false) {
					return ;
				}
				
				var tempMemberIds=[];
				var tempOrderCodes=[];
				
				$("input[name='checkAll']").each(function(index) {
					if($(this).is(":checked")) {
						
						var tempMemberId = $("#roleTable tbody tr").eq(index).find('.theMemberId').val();
						var tempstat = $("#roleTable tbody tr").eq(index).find('.theOrderstatus').val();
						var tempOrderCode = $("#roleTable tbody tr").eq(index).find('.theOrderCode').val();
						
						if( tempstat==1  ) {
							jBox.tip("存在不合法类型订单,操作失败!");
							result = false;
							return false;
						}
						
						if(status == 5 && "审核通过" != tempstat) {
							jBox.tip("选中订单,无法进行发货操作!");
							result = false;
							return false;
						}
						
						//if(status == 6 && "已发货" != tempstat) {
						//	jBox.tip("选中订单,无法进行确认完成!");
						//	result = false;
						//	return false;
						//}
						
						
						
						tempMemberIds.push(tempMemberId);
						tempOrderCodes.push(tempOrderCode);
					}
				});				
				
				if(result == false) {
					return ;
				}
				$.post('updateOrdersStatus',
						{tempMemberIds : tempMemberIds.join(","),tempOrderCodes : tempOrderCodes.join(","),orderStatus : status, time : new Date().getTime()},
						
						function(data) {
							if (data.result == "success") {
								alert(data.msg);
								query();// 重新查询
		  						
							} else {
								alert(data.msg);
							}
						});
			}
	    	
			function exportExcel(){
				$("#findForm").attr("action","${contextPath}/order/export_excel_order_list");
				$("#findForm").submit();
				$("#findForm").attr("action","${contextPath}/order/queryOrderBaseList");
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
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 订单列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
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
                                        <c:if test=""></c:if>
                                       		<option value="1" <c:if test="${orderBaseDto.orderStatus == '1' }"> selected="selected" </c:if> > 等待支付</option>
                                       		<option value="3" <c:if test="${orderBaseDto.orderStatus == '3' }"> selected="selected" </c:if> > 待发货</option>
                                       		<option value="5" <c:if test="${orderBaseDto.orderStatus == '5' }"> selected="selected" </c:if> >  已发货</option>
                                       		<option value="6" <c:if test="${orderBaseDto.orderStatus == '6' }"> selected="selected" </c:if> > 会员确认收货</option>
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
                                <div class="form-group col-md-12 mB15">
                                	<button type="submit" class="btn btn-info speBtn">查 询</button>
                                    
                                    <button type="button" onclick="exportExcel()"  class="btn btn-success speBtn">导出订单表</button> 
									
									
									<button type="button" onclick="updateAll('5')"  class="btn btn-info speBtn">批量发货</button> 
									
									<button type="button" onclick="updateAll('6')"  class="btn btn-info speBtn">批量确认收货</button>
                                </div>
                                <div class="clearfix"></div>
                                
                                <input type="hidden" name="storeCode" value="${orderBaseDto.storeCode}"/>
                                
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
                                            <th>订单状态</th>
                                            <th>付款状态</th>
                                            <th>收货人</th>
                                            <th>联系电话</th>
                                            <th>会员留言</th>
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
	                                             	<c:if test="${order.orderStatus == '1'}">待支付</c:if>
	                                            	<c:if test="${order.orderStatus == '2'|| order.orderStatus == '3'}">待发货</c:if>
	                                            	<c:if test="${order.orderStatus == '5'}">已发货</c:if>
	                                            	<c:if test="${order.orderStatus == '6'||order.orderStatus == '7'||order.orderStatus == '8'}">交易完成</c:if>
	                                            	<c:if test="${order.orderStatus == '9'}">会员取消</c:if>
	                                            	<c:if test="${order.orderStatus == '10'}">系统取消</c:if>
	                                            	<c:if test="${order.orderStatus == '11'}">用户拒收</c:if>
	                                            	<c:if test="${order.orderStatus == '12'}">已退款</c:if>
	                                            	<input class="theOrderStatus" type="hidden" value="${order.orderStatus}"> 
	                                            	
	                                            <%-- <c:out value="${orderStateMap[order.orderStatus]}"></c:out> --%>
	                                            <input type="hidden" class="theOrderStatus" value="${orderStateMap[order.orderStatus]}">
	                                            	
												</td>
	                                            <td>
	                                            	<c:choose>
	                                            		<c:when test="${order.financeStatus =='y'}">已付款</c:when> 
													  	<c:otherwise>未付款</c:otherwise> 
	                                            	</c:choose>
	                                            </td>
	                                            <td>${order.receiver}</td>
	                                            <td>${order.receiverMobile}</td>
	                                            <td>${order.memo}</td>
	                                         
	                                            <td>
	                                            	<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${order.orderCode}" title="订单详情">	
														订单详情
													</a>
													
													<a class="btn btn-info btn-xs" target="_blank" href="${contextPath}/order/orderBasePrint/${order.memberId}/${order.orderCode}" title="订单打印">	
														订单打印
	                                            </td>
	                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <tags:page formId="findForm" url="${contextPath}/order/queryOrderBaseList"></tags:page>
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
