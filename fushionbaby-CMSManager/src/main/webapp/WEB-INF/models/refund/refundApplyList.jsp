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
        <title>退款申请列表</title>
        <script type="text/javascript">
        function refund(paymentType,memberId,orderCode,pageFrom){
        	
			$.ajax({
 				   type: "GET",
 				   url: "${contextPath}/cmsRefund/checkRefund/"+memberId+"/"+orderCode,
 				   success: function(msg){
 						if(msg=="true"){
 							jBox.tip("已执行退款，请刷新页面取消退款按钮");
 						}else{
 							var submit =  function(v,h,f){
 					  			if(v=="ok"){
 					  				if(paymentType=='ZFB_APP'){
 					  					window.open("${contextPath}/cmsRefund/cmsRefund/"+memberId+"/"+orderCode+"/"+pageFrom);
 					  				}else if(paymentType=='WX_APP'){
 					  					location.href="${contextPath}/wxRefund/wxRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
 					  				}else if(paymentType=='ZXYL_APP'){
 					  					location.href="${contextPath}/unionRefund/unionRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
 					  				}else if(paymentType=='ZERO'){
 					  					location.href="${contextPath}/zeroRefund/zeroRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
 					  				}
 					  				
 					  			}
 					  			return true;
 					  		} 
 					  		$.jBox.confirm("你确定要退款吗？", "退款提示",submit);
 						}
 					}
 				}
 			);
	  		
	  	}
	    
        
        
     	function alabaoRefund(memberId,orderCode){
    		var url = "iframe:${contextPath}/alabaoRefund/refundCheck?memberId="+memberId+"&orderCode="+orderCode;
    		$.jBox(url, {
			    title: "确定如意消费卡退款", width: 800,height: 350,
			    buttons: { '关闭': false },
		    });
    	} 
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 退款申请列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">订单号：</label>
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${refundApplyDto.orderCode}" placeholder="请输入订单号">
                                </div>   
                                <div class="form-group col-md-4 mB15">
			    					<label for="e" class="col-label">操作状态：</label>
		                            <select name="status" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <option value="1" <c:if test="${refundApplyDto.status eq '1' }">selected</c:if>>未处理</option>
		                                <option value="2" <c:if test="${refundApplyDto.status eq '2' }">selected</c:if>>已处理</option>
		                            </select>
			  					</div> 
                                <div class="form-group col-md-4 mB15">
			    					<label for="e" class="col-label">付款方式：</label>
		                            <select name="orderPayType" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <c:forEach var="state" items="${paymentStateMap}">
		                                     <option value="${state.key}" <c:if test="${state.key == refundApplyDto.orderPayType }"> selected="selected" </c:if> > ${state.value}</option>
									    </c:forEach>   
		                            </select>
			  					</div>
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">订单来源：</label>
                                    <select name="orderSource" class="form-control lg-select">
		                                <option value="">所有</option>
		                                <c:forEach var="source" items="${sourceMap}">
		                                     <option value="${source.key}" <c:if test="${source.key == refundApplyDto.orderSource }"> selected="selected" </c:if> > ${source.value}</option>
									    </c:forEach>   
		                            </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="d" class="col-label">退款开始时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${refundApplyDto.createTimeFrom}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">退款结束时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${refundApplyDto.createTimeTo}">
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
                                            <th>订单号</th>
                                            <th>用户名</th>
                                            <th>付款方式 </th>
                                            <th>订单来源</th>
                                            <th width="30%">退款原因</th> 
                                            <th>申请退款时间</th>
                                            <th>退款时间</th>
                                            <th>状态</th>
                                            <th>退款执行人</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.result}" var="refund" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${refund.orderCode}</td>
                                            <td>${refund.memberName}</td>
                                            <td>${paymentStateMap[refund.orderPayType]}</td>
                                            <td>${sourceMap[refund.orderSource]}</td>
                                            <td>${refund.refundReason}</td>
                                            <td><fmt:formatDate value="${refund.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td><fmt:formatDate value="${refund.dealTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        	<td>${refund.status==1?'未处理':'已处理'}</td>
                                        	<td>${refund.dealName}</td>
                                        	<td>
                                        		<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${refund.memberId}/${refund.orderCode}" title="订单详情">	
													订单详情
												</a>
												<c:if test="${refund.orderPayType=='ZFB_APP'&&refund.status==1}">
													<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${refund.orderPayType}',${refund.memberId},'${refund.orderCode}','refundApplyList')" title="退款">	
														支付宝退款
													</a>
												</c:if>
												<c:if test="${refund.orderPayType=='WX_APP'&&refund.status==1}">
													<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${refund.orderPayType}',${refund.memberId},'${refund.orderCode}','refundApplyList')" title="退款">	
														微信退款
													</a>
												</c:if>
												<c:if test="${refund.orderPayType=='ZXYL_APP'&&refund.status==1}">
													<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${refund.orderPayType}',${refund.memberId},'${refund.orderCode}','refundApplyList')" title="退款">	
														银联退款
													</a>
												</c:if>
												<c:if test="${refund.orderPayType=='ALABAO_APP'&&refund.status==1}">
													<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:alabaoRefund(${refund.memberId},'${refund.orderCode}')" title="退款">	
														如意消费卡退款
													</a>
												</c:if>
												<c:if test="${refund.paymentTotalActual=='0.00 '&& refund.status==1}">
													<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('ZERO',${refund.memberId},'${refund.orderCode}','refundApplyList')" title="退款">	
														0元退款
													</a>
												</c:if>
											</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/refund/refundApplyList"></tags:page>
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