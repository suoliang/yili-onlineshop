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
	<title>结算列表</title>
  
  	<script type="text/javascript">
  		function settlementDetails(id){
  			window.location.href = "${contextPath }/storeSettleApply/applyDetails/"+ id + "/"+ new Date().getTime();
  		}
  	
  	</script>
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i>已结算列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/storeSettleApply/storeSettlementList">
						
						<div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">门店编号：</label>
                             <input type="text" class="form-control"  name="storeNumber" value="${storeApplySettleDto.storeNumber}" placeholder="请输入门店名称">
                         </div>
                         
                        <div class="form-group col-md-4 mB15">
							<label class=" control-label">门店名称：</label>
							<select name="storeCode" class="form-control lg-select">
								<option value="">全部</option>
								<c:forEach items="${list }" var="list">
									<option value="${list.storeCode }"  <c:if test="${storeApplySettleDto.storeCode == list.storeCode}">selected="selected" </c:if> >${list.storeName }</option>
								</c:forEach>
							</select>
						</div>
                       
				     <div class="form-group col-md-4 mB15">
		                <label for="a">创建开始时间：</label>
		                  <div class="input-group">
		                     <input type="text" name="createTimeFrom" value="${storeApplySettleDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                     <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                 </div>
		              </div>
		              <div class="form-group col-md-4 mB15">
		                 <label for="a">创建结束时间：</label>
		               		<div class="input-group">
		                      <input type="text" name="createTimeTo" value="${storeApplySettleDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
		                      <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                    </div>
		              </div>
                       <div class="form-group col-md-4 mB15">
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
                           	  <th>门店编号</th>
                           	  <th>门店名称</th>
							  <th>金额</th>
							  <th>申请编码</th>
							  <th>申请时间</th>
							  <th>结算时间</th>
							  <th>状态</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${list}" var="list" varStatus="status">
									<tr>
	                                   <td scope="row">${status.count}</td>
									   <td>${list.storeNumber}</td>	
									   <td>${list.storeName}</td>	
									   <td>${list.applyTotalAmount}</td>	
									   <td>${list.applyNumber}</td>	
									   <td><fmt:formatDate value="${list.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									   <td><fmt:formatDate value="${list.settlementTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									   <td>
									   		<c:if test="${list.settlementStatus == 2}">待结算</c:if>
									   		<c:if test="${list.settlementStatus == 3}">已结算</c:if>
									   </td>	
									   <td> <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  
										 	 onclick="settlementDetails('${list.id}')">详情</a>
										 	 </td>	
									  </tr>
								  </c:forEach>
                                </tbody>
                              </table>
                              
                               <!-- 分页 -->
	                            <tags:page formId="findForm" url="${contextPath }/storeSettleApply/storeSettlementList"></tags:page>
	                            <!-- 分页 end -->
                           </div>
                            
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
