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
	<title>待结算列表</title>
  	<script type="text/javascript">
	  	function showDetails(id){
	  		window.location.href = "${contextPath }/storeDailyInfo/dailyOrderList/"+id+"/"+ new Date().getTime();
	  	}
  		
	  	
	  	//申请结算
	  	function applyPayment(){
	  		
	  		debugger;
	  		var checkedLen = $("input[name='checkAll']:checked").length;
			if(checkedLen == 0) {
				jBox.tip("请先选择您要结算的项!");
				return false;
			}
			
			var result = confirm("确定提交吗?");
			if(result == false) {
				return ;
			}
			
			var dailyNumber = "";
			
			$("input[name='checkAll']").each(function() {
				if($(this).is(":checked")) {
					dailyNumber = dailyNumber + "," +$(this).val();
				}
			});
			
			window.location.href = "${contextPath}/storeDailyInfo/storeApplySettlement/"+dailyNumber;
	  	}
  	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 待结算列表</h3>
                   </div>
           
                   <div class="panel-body">
				  		
					  <div>
		                <p style="margin:0;">注意：身边阿拉丁的微信，支付宝，银联支付均为第三方支付平台提供，收款费率为:
		                <span style="color:#FF5400">支付宝：<b>1.5%</b>&nbsp;&nbsp;微信：<b>0.6%</b>&nbsp;&nbsp;银联：<b>1% </b></span></p>
		                <p style="text-indent:3em;">实际金额=订单原金额-订单原金额*支付平台收款费率</p>
		             </div>
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
                           	  <th>结算周期</th>
                           	  <th>订单金额</th>
							  <th>实际金额</th>
							  <th>订单数</th>
							  <th>状态</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
									  <th>
	                                       <label class="check-label">
	                                           <input type="checkbox" name="checkAll"  value="${list.dailyNumber }">
	                                       </label>
	                                   </th>
	                                   <td scope="row">${status.count}</td>
									   <td><fmt:formatDate value="${list.settlePeriod}" pattern="yyyy-MM-dd"/></td>
									   <td>${list.settleOrderAmount}</td>	
									   <td>${list.realIncomeAmount}</td>	
									   <td>${list.settleOrderCount}</td>	
									   <td>待结算</td>	
									   <td>
										 <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  
										 	 onclick="showDetails('${list.id}')">详情</a>
									   </td>
									 	
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                              <span >
                              	<a class="btn btn-primary  edit-role pull-right" href="javascript:void(0)"  
										 	 onclick="applyPayment()">批量结算</a>
                              </span>
                           </div>
                            
                        </div>
                       <%-- <form class="form-inline page" id="findForm" method="post" action="${contextPath }/applyDailyDetails/applyDailyDetailsList"> --%> 
				  	 <form class="form-inline page" id="findForm" method="post" > 

				   		<div class="clearfix"></div>
				   		
		   				<input type="hidden" name="currentPage" value="${page.currentPage}"/>
						<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
						<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
						<input type="hidden" name="total" value="${page.total}"/>
					  </form>
                        
                               <!-- 分页 -->
	                            <tags:page formId="findForm" url="${contextPath}/storeDailyInfo/storeDailyList"></tags:page>
	                            <!-- 分页 end --> 
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
