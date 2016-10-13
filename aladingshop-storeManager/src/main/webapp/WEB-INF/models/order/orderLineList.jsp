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
        <title>订单行商品信息列表</title>
    </head>
    <body id="index">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 订单行商品信息列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a">订单编码：</label>
                                    <input type="text" name="orderCode" value="${orderLineLineDto.orderCode}" class="form-control" id="" placeholder="请输入订单编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b">商品编码：</label>
                                    <input type="text" name="skuNo" value="${orderLineLineDto.skuNo}" class="form-control" id="" placeholder="请输入商品编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b">商品名称：</label>
                                    <input type="text" name="skuName" value="${orderLineLineDto.skuName}" class="form-control" id="" placeholder="请输入商品名称">
                                </div>
                                
                                
                              
                                <div class="form-group col-md-4 mB15">
		                            <label for="d" class="col-label">创建开始时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${orderLineLineDto.createTimeFrom}">
		                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                             </div>
		                         </div>
		                         <div class="form-group col-md-4 mB15">
		                             <label for="e" class="col-label">创建结束时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${orderLineLineDto.createTimeTo}">
		                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                             </div>  
		                         </div>
		                         
		                         
                                
                                
                                <div class="form-group col-md-12 mB15">
                                	<button type="submit" class="btn btn-success speBtn">查 询</button>
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
                                            <th>订单编码</th>
                                            <th>商品编码</th>
                                            <th>商品名称</th>
                                            <th>吊牌价</th>
                                            <th>销售单价</th>
                                            <th>订购数量</th>
                                            <th>总金额</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${orderLine}" var="orderLine" varStatus="status">
	                                        <tr>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${orderLine.orderCode}</td>
	                                            <td>${orderLine.skuNo}</td>
	                                            <td>${orderLine.skuName}</td>
	                                            <td>${orderLine.skuRetailPrice}</td>
	                                            <td>${orderLine.unitPrice}</td>
	                                            <td>${orderLine.quantity}</td>
	                                            <td>${orderLine.totalPrice}</td>
	                                            <td><fmt:formatDate value="${orderLine.createTime}" pattern="yyyy-MM-dd"/> </td>
	                                            <td>
	                                            	<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${orderLine.memberId}/${orderLine.orderCode}" title="订单详情">	
														订单详情
													</a>
												</td>
	                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <tags:page formId="findForm" url="${contextPath}/order/queryOrderLineList"></tags:page>
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
