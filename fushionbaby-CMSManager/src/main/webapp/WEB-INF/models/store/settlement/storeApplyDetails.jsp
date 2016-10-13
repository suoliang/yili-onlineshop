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
	<title>结算明细</title>
	
	<script type="text/javascript">
	  	function settlement(applyNumber){
	  		
	  		debugger;
	  		
			var result = confirm("确定提交吗?");
			if(result == false) {
				return ;
			}
			window.location.href = "${contextPath}/storeSettleApply/settlement/"+ applyNumber +"/"+new Date().getTime();
	  	}
	
	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
				
				<div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 银行卡信息</h3>
                  </div>
				<div class="panel-body">
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable" style="width:80%;margin:20px auto">
                           <thead>
	                             <tr>
		                              <th>银行</th>
		                           	  <th>卡号</th>
		                           	  <th>姓名</th>
									  <th>分行</th>
								 </tr>
                            </thead>
                            <tbody>
								<tr>
									<td>${storeSponsorsBank.bankName}</td>	
									<td>${storeSponsorsBank.cardNo}</td>	
									<td>${storeSponsorsBank.cardHolder}</td>	
									<td>${storeSponsorsBank.bankBranchName}</td>	
								</tr>
                                </tbody>
                              </table>
                           </div>
                        </div>
				
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 结算明细</h3>
                   </div>
                   
                   <div class="panel-body">
                      <!-- table -->
                      <div class="table-responsive">
                      
	                   <div class="form-group col-md-4 mB15 mT15">
		                    <label for="a"  style="width:250px; font-size:120%">门店名称：${store.name}</label>
		               </div>
	                   <div class="form-group col-md-4 mB15 mT15">
		                    <label for="a"  style="width:250px; font-size:120%">结算状态：<font color="red">
		                    	<c:if test="${settlementStatus==2 }">申请中</c:if>
		                    	<c:if test="${settlementStatus==3 }">已结算</c:if>
		                    </font></label>
		               </div>
                   
                   <div class="clearfix"></div>
                   
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                              <th>序号</th>
                           	  <th>结算周期</th>
							  <th>订单金额</th>
							  <th>实收金额</th>
                           	  <th>订单数量</th>
							  <th>结算状态</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
	                                   <td scope="row">${status.count}</td>
									   <td><fmt:formatDate value="${list.settlePeriod}" pattern="yyyy-MM-dd"/></td>
									   <td>${list.settleOrderAmount}</td>	
									   <td>${list.realIncomeAmount}</td>	
									   <td>${list.settleOrderCount}</td>	
									   <td>
									   		<c:if test="${list.settlementStatus == 2}">申请中</c:if>
									   		<c:if test="${list.settlementStatus == 3}">已结算</c:if>
									   </td>	
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                              
                           </div>
                            <div class="form-group mB15 mT15 " style="text-align:right;margin-right:20px;">
			                    <label for="a" class="col-label " style=" font-size:120%;width:100%">订单总金额：${totalAmount}</label>
			               
		               		</div>
		               		  <div class="form-group mB15 mT15" style="text-align:right;margin-right:20px;">
			                    <label for="a" class="col-label" style="font-size:120%;width:100%">结算总金额：${totalIncome}</label>
			                    
		               		</div>
		               		<c:if test="${settlementStatus==2 }">
		               		 	<span class="pull-right" style="margin-right:20px;" >
                              		<a class="btn btn-primary  edit-role " href="javascript:void(0)"  
										onclick="settlement('${applyNumber}')">确认转出</a>
                              </span>
		               		</c:if>
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
