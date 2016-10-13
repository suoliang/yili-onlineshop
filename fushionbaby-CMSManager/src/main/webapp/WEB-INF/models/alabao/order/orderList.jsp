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
        <title>如意消费卡订单列表</title>
    </head>
    <script type="text/javascript">
	  	function exportExcel(){
			$("#findForm").attr("action","${contextPath}/alabaoOrder/export_alabao_order");
			$("#findForm").submit();
			$("#findForm").attr("action","${contextPath}/alabaoOrder/orderList");
		}
	  	/*订单支付详情*/
	  	function listOrderPayDetail(id){
	  		window.location.href = "${contextPath}/alabaoOrder/orderPayInfo?id="+id+"&time="+new Date().getTime();
	  	}
    </script>
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 如意消费卡订单列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                            
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">订单号：</label>
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${alabaoOrderCMSDto.orderCode}">
                                </div>
                               
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">会员名：</label>
                                    <input type="text" class="form-control" id=memberName name="memberName" value="${alabaoOrderCMSDto.memberName}">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意消费卡账户名：</label>
                                    <input type="text" class="form-control" id=account name="account" value="${alabaoOrderCMSDto.account}">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝付款方式：</label>
                                    
                                    <select  name="paymentType" class="form-control lg-select">
                                       <option value="" >---请选择---</option>
                                       <option value="ZFB_WEB_DBJY" ${alabaoOrderCMSDto.paymentType eq 'ZFB_WEB_DBJY'?'selected':'' }>WEB支付宝担保交易</option>
                                       <option value="ZFB_WEB_JSDZ" ${alabaoOrderCMSDto.paymentType eq 'ZFB_WEB_JSDZ'?'selected':'' }>WEB支付宝即时到帐</option>
                                       <option value="ZFB_APP" ${alabaoOrderCMSDto.paymentType eq 'ZFB_APP'?'selected':'' }>APP支付宝</option>
                                       <option value="WX_WEB" ${alabaoOrderCMSDto.paymentType eq 'WX_WEB'?'selected':'' }>WEB微信支付</option>
                                       <option value="WX_APP" ${alabaoOrderCMSDto.paymentType eq 'WX_APP'?'selected':'' }>APP微信支付</option>
                                       <option value="ZXYL_WEB" ${alabaoOrderCMSDto.paymentType eq 'ZXYL_WEB'?'selected':'' }>WEB银联支付</option>
                                       <option value="ZXYL_APP" ${alabaoOrderCMSDto.paymentType eq 'ZXYL_APP'?'selected':'' }>APP银联支付</option>
                                       <option value="HDFK_WEB" ${alabaoOrderCMSDto.paymentType eq 'HDFK_WEB'?'selected':'' }>WEB货到付款</option>
                                       <option value="HDFK_APP" ${alabaoOrderCMSDto.paymentType eq 'HDFK_APP'?'selected':'' }>APP货到付款</option>
                                       <option value="ALABAO_APP" ${alabaoOrderCMSDto.paymentType eq 'ALABAO_APP'?'selected':'' }>APP如意宝支付</option>
                                       <option value="ZERO" ${alabaoOrderCMSDto.paymentType eq 'ZERO'?'selected':'' }>0元支付</option>
                                       <option value="ZFB_WAP_JSDZ" ${alabaoOrderCMSDto.paymentType eq 'ZFB_WAP_JSDZ'?'selected':'' }>WAP支付宝即时到帐</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${alabaoOrderCMSDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${alabaoOrderCMSDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        
		                        <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转入总金额：</label>
                                    <input type="text" class="form-control" id="listPageTotalActual" name="listPageTotalActual" value="${listPageTotalActual}" readonly="readonly">
                                </div>
                               
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                 <button type="button" onclick="exportExcel()"  class="btn btn-success speBtn">导出转入订单表</button> 
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
											<th>会员名</th>
											<th>如意消费卡账户名</th>
											<th>最终金额</th>
											<th>阿拉宝订单状态</th>
											<th>付款方式</th>
											<th>订单来源</th>
											<th>创建时间</th>
											<th>操作时间</th>
											<th>支付详情</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.orderCode}</td>
											<td>${ list.memberName}</td>
											<td>${ list.account}</td>
											<td>${ list.totalActual}</td>
											<td>
												<c:if test="${list.alabaoStatus eq 'n' }">未付款</c:if>
											    <c:if test="${list.alabaoStatus eq 'y' }">已付款</c:if>
											</td>
											<td><c:out value="${paymentStateMap[list.paymentType]}"></c:out>
											</td>
											<td><c:out value="${sourceMap[list.sourceCode]}"/>
											</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td><fmt:formatDate value="${list.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>
												 <c:if test="${list.alabaoStatus eq 'y' }">
													<a class="btn btn-info btn-xs" href="javascript:listOrderPayDetail(${list.id })">详情</a>
												</c:if>
											</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoOrder/orderList"></tags:page>
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