<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>订单支付信息</title>
	
</head>
<body>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 订单支付信息</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" " ">
				   		<div class="clearfix"></div>
					  </form>
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                           	  <th>订单号</th>
                           	  <th>交易流水号</th>
                           	  <th>订单金额</th>
							  <th>交易开始日期</th>
							  <th>支付状态</th>
							  <th>支付流水号</th>
							  <th>创建时间</th>
							  <th>备注</th>
							 </tr>
                            </thead>
                            
                            <tbody>
								<tr>
								   <td>${AlabaoOrderPaymentDto.orderCode }</td>
								   <td>${AlabaoOrderPaymentDto.orderNumber}</td>	
								   <td>${AlabaoOrderPaymentDto.settleAmount}</td>	
								   <td>${AlabaoOrderPaymentDto.tradeTime}</td>	
								   <td><c:if test="${AlabaoOrderPaymentDto.status == '1'}">未支付</c:if>
								   	   <c:if test="${AlabaoOrderPaymentDto.status == '2'}">处理中</c:if>
								       <c:if test="${AlabaoOrderPaymentDto.status == '3'}">支付成功</c:if>
								       <c:if test="${AlabaoOrderPaymentDto.status == '4'}">处理失败</c:if>
								   </td>	
								   <td>${AlabaoOrderPaymentDto.tradeNo}</td>
								   <td><fmt:formatDate value="${AlabaoOrderPaymentDto.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>${AlabaoOrderPaymentDto.orderDes}</td>
								   	
								  </tr>
                                </tbody>
                              </table>
                           </div>
                            <button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
