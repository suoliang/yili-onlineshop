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
                                            <strong>用户备注：</strong>
                                            <span>${orderBase.memo}</span>
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
                                            <th>销售单价</th>
                                            <th>购买数量</th>
                                            <th>所选尺码</th> 
                                            <th>类型（卖/赠）</th>
                                            <th>颜色</th>
                                            <th>购买时间</th>
                                            <th>总金额</th>
                                           
                                        </tr>
                                    </thead>
                                    <tbody>
	                                    <c:forEach items="${orderLineList}" var="orderLineList" varStatus="status">
	                                        <tr>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${orderLineList.skuCode}</td>
	                                            <td>${orderLineList.skuNo}</td>
	                                            <td>${orderLineList.skuName}</td>
	                                            
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
	                                           
	                                        </tr>
	                                    </c:forEach>
                                    </tbody>
                                </table>
                                     <div>
						                    <ul class="list-group">
						                        <li class="list-group-item">
						                            <strong>订单总计：</strong>
						                            <span>${orderBase.totalAfDiscount}</span> 
						                        </li>
						                    </ul>
						                </div>
                                      <strong>用户备注：</strong><span>${orderBase.memo}</span>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    

    
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
