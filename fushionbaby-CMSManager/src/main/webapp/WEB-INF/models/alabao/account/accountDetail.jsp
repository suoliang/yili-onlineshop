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
        <title>账户详情</title>  
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
                            <h3 class="panel-title"><i class="fa fa-cog"></i>如意宝账户信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>姓名：</strong>
                                            <span>${alabaoAccount.trueName}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>身份证号：</strong>
                                            <span>${alabaoAccount.identityCardNo}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>手机号码：</strong>
                                            <span>${alabaoAccount.mobilePhone}</span>
                                        </li>
                                        
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>创建时间：</strong>
                                            <span><fmt:formatDate value="${alabaoAccount.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
                                        </li>
                                         <li class="list-group-item">
                                            <strong>状态：</strong>
                                            <span>
                                            	<c:if test="${alabaoAccount.status == '1'}">待审核</c:if>
                                            	<c:if test="${alabaoAccount.status == '2'}">审核通过</c:if>
                                            	<c:if test="${alabaoAccount.status == '3'}">审核不通过</c:if>
                                            	<c:if test="${alabaoAccount.status == '4'}">注销</c:if>
                                            </span>
                                        </li>
                                    	
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>

                    <!-- 账户资金表 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 资金概况</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" >
                                    <thead>
                                        <tr>
                                            <th>余额</th>
                                            <th>昨日收益</th>
                                            <th>累计收益</th>
                                            <th>累计转入</th>
                                            <th>累计转出</th>
                                            <th>累计消费</th> 
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
	                                        <tr>
	                                            <td>${alabaoAccount.balance}</td>
	                                            <td>${alabaoAccount.yesterdayIncome}</td>
	                                            <td>${alabaoAccount.totalIncome}</td>
	                                            <td>${totalShiftTo}</td>
	                                            <td>${totalRollOff}</td>
	                                            <td>${totalConsume}</td>
	                                        </tr>
                                    </tbody>
                                    
                                </table>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 订单金额 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>银行卡信息</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" >
                                    <thead>
                                        <tr>
                                            <th>银行</th>
                                            <th>卡号</th>
                                            <th>持卡人</th>
                                            <th>分行信息</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${alabaoAccountBankList}" var="list" varStatus="status">
	                                        <tr>
	                                            <td>= ${list.bankName}</td>
	                                            <td>- ${list.cardNo}</td>
	                                            <td>- ${list.cardHolder}</td>
	                                            <td>- ${list.bankBranchName}</td>
	                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div><!-- 订单金额部分 -->
    
                    <!-- 收货地址 -->
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i> 账目明细</h3>
                        </div>
                        <div class="panel-body">
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>资金流动类型</th>
                                            <th>转移金额</th>
                                            <th>操作时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                       <c:forEach items="${billList}" var="list" varStatus="status">
                                       	<tr>
                                            <td>${status.count}</td>
                                            <td>${list.tradeType}</td>
                                            <td>${list.tradeMoney}</td>
                                            <td>${list.createTime}</td>
                                        </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                            
                    

        <!-- javascript -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/public.js"></script>
    </body>
</html>
