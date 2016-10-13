<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.fushionbaby.common.enums.OrderConfigServerEnum"%>
<!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>订单详情</title>  
    </head>
    <body id="orderDetail">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                	<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
                	
                	<br>
                	<br>
                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>订单详细信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>会员名：</strong>
                                            <span>${orderBase.memberName}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>订单来源：</strong>
                                            <span>
                                            	<c:out value="${sourceMap[orderBase.sourceCode]}"></c:out>
                                           </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>优惠券码：</strong>
                                            <span>${orderFee.cardno}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>使用积分：</strong>
                                             <td>${orderBase.totalPointUsed}</td>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>下单时间：</strong>
                                            <span><fmt:formatDate value="${orderBase.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                        </li>
                                         <li class="list-group-item">
                                            <strong>支付方式：</strong>
                                            <span>
                                            	<c:out value="${paymentStateMap[orderBase.paymentType]}"></c:out>
                                            </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>付款时间：</strong>
                                            <span><fmt:formatDate value="${orderFinance.paymentCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    	<li class="list-group-item">
                                            <strong>订单号：</strong>
                                            <span>${orderBase.orderCode}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>订单状态：</strong>
                                            <span>
                                            	<c:out value="${orderStateMap[orderBase.orderStatus]}"></c:out>
                                            </span>
                                        </li>
                                       
                                        <li class="list-group-item">
                                            <strong>获得积分：</strong>
                                            <span>${orderBase.orderPointGained}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>用户备注：</strong>
                                            <span>${orderBase.memo}</span>
                                        </li>
                                        <li class="list-group-item"> 
                                            <strong>删除标记：</strong>
                                            <span>
                                            	<c:choose>
                                            		<c:when test="${orderBase.isDelete =='y'}">已删除</c:when>
                                            		<c:otherwise>未删除</c:otherwise> 
                                            	</c:choose>
                                            </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>订单是否被拒绝：</strong>
                                            <span>
                                            	<c:choose>
                                            		<c:when test="${orderBase.isRefused =='y'}">订单被客户拒收(是)</c:when>
                                            		<c:otherwise>正常(否)</c:otherwise> 
                                            	</c:choose>
                                            </span>
                                        </li>
                                       
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>

                    <!-- 购买商品列表 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 购买商品列表</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>商品编码</th>
                                            <th>商品条形码</th>
                                            <th>商品名称</th>
                                            <th>商品成本</th>
                                            <th>商品吊牌价</th> 
                                            <th>销售单价</th>
                                            <th>购买数量</th>
                                            <th>所选尺码</th> 
                                            <th>类型（卖/赠）</th>
                                            <th>颜色</th>
                                            <th>购买时间</th>
                                            <th>总金额</th>
                                            <th>商品总计</th>
                                        </tr>
                                    </thead>
                                    <tbody>
	                                    <c:forEach items="${orderLineList}" var="orderLineList" varStatus="status">
	                                        <tr>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${orderLineList.skuCode}</td>
	                                            <td>${orderLineList.skuNo}</td>
	                                            <td>${orderLineList.skuName}</td>
	                                            <td>${orderLineList.costPrice}</td>
	                                            <td>${orderLineList.skuRetailPrice}</td>
	                                            <td>${orderLineList.unitPrice}</td>
	                                            <td>${orderLineList.quantity}</td>
	                                            <td>${orderLineList.size}</td>
	                                            <td>
	                                            	<c:choose>
	                                            		<c:when test="${orderLineList.lineType == 'n'}">主卖</c:when>
	                                            		<c:otherwise>赠品</c:otherwise> 
	                                            	</c:choose>
	                                            </td>
	                                            <td>${orderLineList.color}</td> 
	                                            <td><fmt:formatDate value="${orderLineList.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>${orderLineList.totalPrice}</td>
	                                            <td>${orderLineList.totalActual}</td>
	                                        </tr>
	                                    </c:forEach>
                                          
                                    </tbody>
                                    
                                      <strong>用户备注：</strong><span>${orderBase.memo}</span>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    <!-- 订单金额 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>订单金额明细</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>实际付款总计</th>
                                            <th>订单商品总价格</th>
                                            <th>优惠券折扣金额</th>
                                            <th>如意消费卡优惠金额</th>
                                            <th>使用红包金额</th>
                                            <th>积分折扣金额</th>
                                            <th>运费</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                        <!--客户实付金额=商品总金额-优惠券优惠金额-红包使用金额-积分折扣金额+运费  -->

                                            <td>
                                             <%--  <c:if test="${orderBase.paymentType eq 'ALABAO_APP' }">${orderFee.totalActual-orderFee.alabaoCheapAmount}（实付）=${orderFee.totalActual}（应付）-${ orderFee.alabaoCheapAmount}（优惠） </c:if>
                                              <c:if test="${orderBase.paymentType != 'ALABAO_APP' }">${orderFee.totalActual}</c:if> --%>
                                              ${orderFee.totalActual}
                                            </td>
                                            <td>= ${orderBase.totalAfDiscount}</td><!--商品总金额  -->
                                            
                                            <td>- ${orderFee.cardAmount}</td>
                                            <td>- <c:if test="${orderBase.paymentType eq 'ALABAO_APP' }">${orderFee.alabaoCheapAmount}</c:if></td>
                                            <td>- ${orderFee.redEnvelopeAmountTotal}</td>
                                            <td>- ${orderFee.epointsRedeemMoney}</td>
                                            <td>+ ${orderFee.actualTransferFee}</td>
 

                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div><!-- 订单金额部分 -->
    
                    <!-- 收货地址 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 收货地址</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>会员名</th>
                                            <th>收货人</th>
                                            <th>联系电话</th>
                                            <th>地址</th>
                                            <th>邮编</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${orderMemberAddress.memberName}</td>
                                            <td>${orderMemberAddress.receiver}</td>
                                            <td>${orderMemberAddress.receiverMobile}</td>
                                            <td>${province}${city}${district}${orderMemberAddress.address}</td>
                                            <td>${orderMemberAddress.zipcode}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                              <!-- 订单发票信息 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>订单税务发票信息</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>是否需要发票</th>
                                            <th>发票类型</th>
                                            <th>发票抬头</th>
                                            <th>税费</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
	                                            <c:choose>
	                                            		<c:when test="${orderBase.isInvoice =='y'}">是</c:when> 
													  	<c:otherwise>否</c:otherwise> 
	                                            </c:choose>
                                            </td>
                                            <td>${orderFinance.invoiceType == 1 ? '个人' : '公司'}</td>
                                            <td>${orderFinance.invoiceTitle}</td>
                                            <td>${orderFee.taxPrice}</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div><!-- 订单发票信息部分 -->
                    
					<!-- 支付详情 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>支付详情</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>支付方式</th>
                                            <th>商户订单号</th>
                                            <th>交易流水号</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>${paymentStateMap[orderBase.paymentType]}</td>
                                            <td>
                                             	<c:if test="${! empty paymentDetailDto}">
													${paymentDetailDto.orderNumber}
												</c:if>
											</td>
                                            <td>
                                            	<c:if test="${! empty paymentDetailDto}">
													${paymentDetailDto.tradeNo}
												</c:if>
											</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>

                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

        <!-- javascript -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/public.js"></script>
    </body>
</html>
