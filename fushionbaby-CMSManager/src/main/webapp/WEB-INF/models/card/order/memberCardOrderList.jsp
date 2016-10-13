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
        <title>阿拉丁卡订单记录列表</title>
        <script type="text/javascript">
        function change_orderType(orderCode,memberId,orderType){
			var o1="1"==orderType?"selected":"";
			var o2="2"==orderType?"selected":"";
			var o3="3"==orderType?"selected":"";
			var orderTypeSelect=
			 	 "<select  style='width: 100px' name='orderType' id='newOrderType_"+orderCode+"_"+memberId+"'" + " class='form-control lg-select'>"+
	             "	<option value='1' "+o1+">正常单</option>"+
	             "	<option value='2' "+o2+">测试单</option>"+
	             "	<option value='3' "+o3+">问题单</option>"+
       		     "</select>";
	  		$('#orderType_'+orderCode+'_'+memberId).html(orderTypeSelect);
	  		$('#orderTypeUpdate_'+orderCode+'_'+memberId).html('<a class="btn btn-danger btn-xs" href="javascript:update_orderType(\''+orderCode+"\',"+memberId+')" title="修改订单类型">确定修改</a>');
	  	}
	  	function update_orderType(orderCode,memberId){
	  		var orderType=$("#newOrderType_"+orderCode+"_"+memberId).val();
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				$.post('${pageContext.request.contextPath}/memberCard/updateOrderType',{orderCode:orderCode,memberId:memberId,orderType:orderType,time:new Date().getTime()},
	  	  					 function(data){
	  	  					   if(data.result=="success"){
	  	  						    jBox.tip(data.msg);
		  	  						window.setTimeout(function () {  
		  	  							$("#findForm").submit();
		  							}, 500);
		  	  						   
	  	  					   }else{
	  	  							jBox.tip(data.msg);
	  	  					   }
	  	  				});//post
	  			}else{
	  	  			 $("#findForm").submit();
	  	  		}
	  			return true;
	  		} 
	  		$.jBox.confirm("确定修改订单类型吗？", "修改订单类型提示",submit);
	  		
	  	}
	  	
	  	function deleteOrder(orderCode,memberId){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				$.post('${pageContext.request.contextPath}/memberCard/deleteOrder',{orderCode:orderCode,memberId:memberId,time:new Date().getTime()},
	  	  					 function(data){
	  	  					   if(data.result=="success"){
	  	  						    jBox.tip(data.msg);
		  	  						window.setTimeout(function () {  
		  	  							$("#findForm").submit();
		  							}, 500);
		  	  						   
	  	  					   }else{
	  	  							jBox.tip(data.msg);
	  	  					   }
	  	  				});//post
	  			}else{
	  	  			 $("#findForm").submit();
	  	  		}
	  			return true;
	  		} 
	  		$.jBox.confirm("确定删除订单吗？", "删除订单提示",submit);
	  		
	  	}
        </script>
    </head>
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 阿拉丁卡订单记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">会员名：</label>
                                    <input type="text" class="form-control" id="memberName" name="memberName" value="${yiDuoBaoOrderDto.memberName}" >
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉丁卡订单号：</label> 
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${yiDuoBaoOrderDto.orderCode}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">财务状态：</label> 
                                    <select name="financeStatus" class="form-control lg-select" >
                                    <option value="">---请选择---</option>
                                    <option value="y" ${yiDuoBaoOrderDto.financeStatus eq 'y'?'selected':'' }>已付款</option>
                                    <option value="n" ${yiDuoBaoOrderDto.financeStatus eq 'n'?'selected':'' }>未付款</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">订单创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${yiDuoBaoOrderDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">订单创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${yiDuoBaoOrderDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
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
											<th>会员名</th>
											<th>阿拉丁卡订单号</th>
											<th>订单状态</th>
											<th>订单总金额</th>
											<th>支付方式</th>
											<th>支付完成时间</th>
											<th>财务状态</th>
											<th>订单来源</th>
											<th>创建时间</th>
											<th>更新时间</th>
											<th>更新id</th>
											<th>订单说明</th>
											<th>订单类型</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${memberCardOrderlist}" var="memberCardOrder" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${memberCardOrder.memberName}</td>
											<td>${memberCardOrder.orderCode}</td>
											<td><c:out value="${orderStatusMap[memberCardOrder.orderStatus]}"></c:out></td>
											
											<td>${memberCardOrder.totalActual }</td>
											<td><c:out value="${paymentStateMap[memberCardOrder.payType]}"></c:out></td>
											<td><fmt:formatDate value="${memberCardOrder.payCompleteTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											
											<td>
											<c:choose>
	                                            		<c:when test="${memberCardOrder.financeStatus =='y'}">已付款</c:when> 
													  	<c:otherwise>未付款</c:otherwise> 
	                                            	</c:choose>
											</td> 
											
											<td><c:out value="${sourceMap[memberCardOrder.sourceCode]}"></c:out></td> 
											<td><fmt:formatDate value="${memberCardOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
											<td><fmt:formatDate value="${memberCardOrder.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td> 
											<td>${memberCardOrder.updateId }</td> 
											<td>${memberCardOrder.memo }</td> 
											<td id="orderType_${memberCardOrder.orderCode}_${memberCardOrder.memberId}">
                                               	<c:choose>
                                            		<c:when test="${memberCardOrder.orderType =='1'}">正常单</c:when>
                                            		<c:when test="${memberCardOrder.orderType =='2'}">测试单</c:when>
                                            		<c:when test="${memberCardOrder.orderType =='3'}">问题单</c:when> 
                                             	</c:choose>
											</td>
											<td>
												<div id="orderTypeUpdate_${memberCardOrder.orderCode}_${memberCardOrder.memberId}">
													<a class="btn btn-info btn-xs" href="javascript:change_orderType('${memberCardOrder.orderCode}',${memberCardOrder.memberId},'${memberCardOrder.orderType}')" title="修改订单类型">	
														修改类型
													</a>
												</div>
												<c:if test="${memberCardOrder.orderType =='2'}">
													<a class="btn btn-info btn-xs" href="javascript:deleteOrder('${memberCardOrder.orderCode}',${memberCardOrder.memberId})" title="修改订单类型">	
														删除订单
													</a>
												</c:if>
											</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/memberCard/orderList"></tags:page>
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