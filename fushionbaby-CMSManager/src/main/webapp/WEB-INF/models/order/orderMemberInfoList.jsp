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
        <title>订单客户信息列表</title>
    </head>
    <body id="index">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 订单客户信息列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a">订单编码：</label>
                                    <input type="text" name="orderCode" value="${soSoMemberDto.orderCode}" class="form-control" id="" placeholder="请输入订单编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b">收货人姓名：</label>
                                    <input type="text" name="receiver" value="${soSoMemberDto.receiver}" class="form-control" id="" placeholder="请输入收货人姓名">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b">收货人手机号：</label>
                                    <input type="text" name="receiverMobile" value="${soSoMemberDto.receiverMobile}" class="form-control" id="" placeholder="请输入收货人手机号">
                                </div>  
                                              
                                                                                             
								<div class="form-group col-md-4 mB15">
		                            <label for="d" class="col-label">创建开始时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${soSoMemberDto.createTimeFrom}">
		                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                             </div>
		                         </div>
		                         <div class="form-group col-md-4 mB15">
		                             <label for="e" class="col-label">创建结束时间：</label>
		                             <div class="input-group">
		                                 <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${soSoMemberDto.createTimeTo}">
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
                                            <th>收货人姓名</th>
                                            <th>收货人手机号</th>
                                            <th>收货人地址</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${orderMemberAddressList}" var="orderMemberAddress" varStatus="status">
	                                        <tr>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${orderMemberAddress.orderCode}</td>
	                                            <td>${orderMemberAddress.receiver}</td>
	                                            <td>${orderMemberAddress.receiverMobile}</td>
	                                            <td>${orderMemberAddress.address}</td>
	                                            <td><fmt:formatDate value="${orderMemberAddress.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>
	                                            	<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${orderMemberAddress.memberId}/${orderMemberAddress.orderCode}" title="订单详情">	
														订单详情
													</a>
												</td>
	                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <tags:page formId="findForm" url="${contextPath}/order/queryOrderMemberInfoList"></tags:page>
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
