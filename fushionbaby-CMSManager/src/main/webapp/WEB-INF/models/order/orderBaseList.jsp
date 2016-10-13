<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.fushionbaby.common.enums.OrderConfigServerEnum"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>商城订单列表</title>
        <script type="text/javascript">
	        function query(){
	      		$('#findForm').submit();
	      		
	      	}
	        function import_excel(){
	        	var url = "iframe:${contextPath}/order/showImportTrans";
				$.jBox(url, {
		  		    title: "EXCEL批量导入快递单号", width: 400,height: 270,
		  		    buttons: { '关闭': false },
				});
				
			}
        
	        function updateOrderStatus(root,msg,state,memberId,orderCode){
	            var result;
	        	var submit =  function(v,h,f){
	        		if(v=="ok"){
	        			$.ajax({
	        				type : "get",
	        				url : root+"/order/updateOrderStatus",
	        				dataType:"text",
	        				data : {
	        					state: state, 
	        					memberId: memberId, 
	        					orderCode: orderCode
	        				},
	        				success : function(data) {
	        					result  = data;
	        					if(result == "SUCCESS"){
	        						location.reload();
	        	  					jBox.tip("操作成功", 'info');
	        	  				}else if(result=="transInfoRequired"){
	        	  					jBox.tip("请先输入快速快速公司和快递单号",'info');
	        	  				}else{
	        	  					jBox.tip("操作失败", 'info');
	        	  	  			}
	        				},
	        				error : function() {
	        					alert("请求后台数据异常");
	        				}
	        			});
	        		}
	        		return true;
	        	} 
	        	$.jBox.confirm(msg, "操作提示",submit);
	        }
	    	function updateOrderTrans(memberId,orderCode){
	    		var url = "iframe:${contextPath}/order/gotoOrderTransUpdate?memberId="+memberId+"&orderCode="+orderCode;
	    		$.jBox(url, {
    			    title: "修改物流", width: 800,height: 350,
    			    buttons: { '关闭': false },
			    });
	    	}
	    	function updateOrderAuditFail(memberId,orderCode){
	    		var url = "iframe:${contextPath}/order/gotoOrderAuditFail?memberId="+memberId+"&orderCode="+orderCode;
	    		$.jBox(url, {
    			    title: "审核不通过", width: 800,height: 350,
    			    buttons: { '关闭': false },
			    });
	    	}
	    	
	    	function exportExcel(){
				$("#findForm").attr("action","${contextPath}/order/export_excel_order_list");
				$("#findForm").submit();
				$("#findForm").attr("action","${contextPath}/order/queryOrderBaseList");
			}
	    	
	    	//批量更新
			function updateAll(status) {
				var checkedLen = $("input[name='checkAll']:checked").length;
				if(checkedLen == 0) {
					jBox.tip("请先选中后，操作!");
					return false;
				}
				
				var result = confirm("确定提交吗?");
				if(result == false) {
					return ;
				}
				
				var tempMemberIds=[];
				var tempOrderCodes=[];
				
				$("input[name='checkAll']").each(function(index) {
					if($(this).is(":checked")) {
						var tempMemberId = $("#roleTable tbody tr").eq(index).find('.theMemberId').val();
						var tempstat = $("#roleTable tbody tr").eq(index).find('.theOrderstatus').val();
						var tempOrderCode = $("#roleTable tbody tr").eq(index).find('.theOrderCode').val();
						if(status == 5 && "审核通过" != tempstat) {
							jBox.tip("存在不合法类型订单,操作失败!");
							result = false;
							return false;
						}
						
						if(status == 3 && "审核中" != tempstat) {
							jBox.tip("存在不合法类型订单,操作失败!");
							result = false;
							return false;
						}
						
						if(status == 7 && "已发货" != tempstat) {
							jBox.tip("存在不合法类型订单,操作失败!");
							result = false;
							return false;
						}
						tempMemberIds.push(tempMemberId);
						tempOrderCodes.push(tempOrderCode);
					}
				});				
				
				if(result == false) {
					return ;
				}
				$.post('updateOrdersStatus',
						{tempMemberIds : tempMemberIds.join(","),tempOrderCodes : tempOrderCodes.join(","),orderStatus : status, time : new Date().getTime()},
						
						function(data) {
							if (data.result == "success") {
								alert(data.msg);
								query();// 重新查询
		  						
							} else {
								alert(data.msg);
							}
						});
			}
	    	
			function refund(paymentType,memberId,orderCode,pageFrom){
				$.ajax({
	 				   type: "GET",
	 				   url: "${contextPath}/cmsRefund/checkRefund/"+memberId+"/"+orderCode,
	 				   success: function(msg){
	 						if(msg=="true"){
	 							jBox.tip("已执行退款，请刷新页面取消退款按钮");
	 						}else{
	 							var submit =  function(v,h,f){
	 					  			if(v=="ok"){
	 					  				if(paymentType=='ZFB_APP'){
	 					  					window.open("${contextPath}/cmsRefund/cmsRefund/"+memberId+"/"+orderCode+"/"+pageFrom);
	 					  				}else if(paymentType=='WX_APP'){
	 					  					location.href="${contextPath}/wxRefund/wxRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
	 					  				}else if(paymentType=='ZXYL_APP'){
	 					  					location.href="${contextPath}/unionRefund/unionRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
	 					  				}else if(paymentType=='ALABAO_APP'){
	 					  					location.href="${contextPath}/alabaoRefund/alabaoRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
	 					  				}else if(paymentType=='ZERO'){
	 					  					location.href="${contextPath}/zeroRefund/zeroRefund/"+memberId+"/"+orderCode+"/"+pageFrom;
	 					  				}
	 					  				
	 					  			}
	 					  			return true;
	 					  		} 
	 					  		$.jBox.confirm("你确定要退款吗？", "退款提示",submit);
	 						}
	 					}
	 				}
	 			);
		  		
		  	}
			
			
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
		  				$.post('${pageContext.request.contextPath}/order/updateOrderType',{orderCode:orderCode,memberId:memberId,orderType:orderType,time:new Date().getTime()},
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
		  	
		  	function updateDetail(memberId,orderCode){
		  		 window.location.href="${contextPath}/order/toUpdateOrderDetails/"+memberId+"/"+orderCode+"?time="+new Date().getTime();
		  	}
		  	
		  	function deleteOrder(orderCode,memberId){
		  		var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				$.post('${pageContext.request.contextPath}/order/deleteOrder',{orderCode:orderCode,memberId:memberId,time:new Date().getTime()},
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
		  		$.jBox.confirm("确定删除订单类型吗？", "删除订单类型提示",submit);
		  		
		  	}
		  	
			function distributionOrder(memberId,orderCode){
	    		var url = "iframe:${contextPath}/userOrder/distribution/"+memberId+"/"+orderCode;
	    		$.jBox(url, {
    			    title: "分配给客服", width: 800,height: 350,
    			    buttons: { '关闭': false },
			    });
	    	}
			   function withdrawOrder(memberId,orderCode){
	        	var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				window.location.href = "${contextPath}/userOrder/withdrawOrder/"+memberId+"/"+orderCode+"/"+new Date().getTime();
		  			}
		  			return true;
		  		}
		  		$.jBox.confirm("你确定要撤回该订单吗？", "撤回提示",submit);
	        };
		  	
		  	
        </script>
    </head>
    <body id="index">
    	 <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i> 商城订单列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <c:if test="${level==1 }">
	                                <div class="form-group col-md-4 mB15">
	                                    <label for="a"  class="col-label">订单编码：</label>
	                                    <input type="text" name="orderCode" value="${orderBaseDto.orderCode}" class="form-control" id="" placeholder="请输入订单编码">
	                                </div>
                                </c:if>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">用&ensp;户&ensp;名：</label>
                                    <input type="text" name="memberName" value="${orderBaseDto.memberName}" class="form-control" id="" placeholder="请输入用户名">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">订单状态：</label>
                                    <select name="orderStatus" class="form-control lg-select">
                                        <option value="">所有</option>
                                       <c:forEach var="state" items="${orderStateMap}">
                                       		<option value="${state.key}" <c:if test="${state.key == orderBaseDto.orderStatus }"> selected="selected" </c:if> > ${state.value}</option>
									   </c:forEach>  
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单开始时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeFrom" value="${orderBaseDto.createTimeFrom}" class="timeS form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                	</div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">下单结束时间：</label>
                                    <div class="input-group">
	                                    <input type="text" name="createTimeTo" value="${orderBaseDto.createTimeTo}" class="timeE form-control form_datetime_his" readonly>
	                                    <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                
                         
                                 
                                <div class="form-group col-md-12 mB15">
                                	<button type="submit" class="btn btn-info speBtn">查 询</button>
                                    
                                    <button type="button" onclick="exportExcel()"  class="btn btn-success speBtn">导出订单表</button> 
									
									<button type="button" onclick="import_excel()" class="btn btn-warning speBtn">批量导入快递单号</button> 
                                    <button type="button" class="btn btn-success" onclick="downLoadExcel('批量导入快递单号模板\.xls')">下载批量导入快递单号模板</button>
									<button id="bb" type="button" onclick="updateAll('<%=OrderConfigServerEnum.SUCCESS_AUDIT.getCode() %>')"  class="btn btn-info speBtn">批量审核</button> 
									
									<button type="button" onclick="updateAll('<%=OrderConfigServerEnum.SUCCESS_SHIPPED.getCode() %>')"  class="btn btn-info speBtn">批量发货</button> 
									
									<button type="button" onclick="updateAll('<%=OrderConfigServerEnum.CMS_CONFIRM_RECEIPT.getCode() %>')"  class="btn btn-info speBtn">批量确认收货</button>
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
                                            <th>
                                                <label for="checkAllBtn" class="check-label">
                                                    <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                                </label>
                                            </th>
                                            <th>序号</th>
                                            <th>会员名</th>
                                            <th>订单编码</th>
                                            <th>下单时间</th>
                                            <th>付款时间</th>
                                            <th>下单金额</th>
                                            <th>支付方式</th>
                                            <th>订单状态</th>
                                            <th>付款状态</th>
                                            <th>物流状态</th>
                                            <th>收货人</th>
                                            <th>联系电话</th>
                                            <th>会员留言</th>
                                            <th>订单处理状态</th>
                                            <th width="100px">订单类型</th>
                                            <th width="100px">门店名称</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${orderBaseList}" var="order" varStatus="status">
	                                        <tr>
	                                            <th>
	                                                <label class="check-label">
	                                                    <input type="checkbox" name="checkAll">
	                                                </label>
	                                            </th>
	                                            <td scope="row">${status.count}</td>
	                                            <td>${order.memberName}<input type="hidden" class="theMemberId" value="${order.memberId}"></td>
	                                            <td>${order.orderCode}<input type="hidden" class="theOrderCode" value="${order.orderCode}"></td>
	                                            <td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td><fmt:formatDate value="${order.paymentCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>${order.paymentTotalActual}</td>
	                                            <td>
	                                            	<c:out value="${paymentStateMap[order.paymentType]}"></c:out>
	                                            </td>
	                                            <td>
	                                            	<c:out value="${orderStateMap[order.orderStatus]}"></c:out><input type="hidden" class="theOrderStatus" value="${orderStateMap[order.orderStatus]}">
												</td>
	                                            <td>
	                                            	<c:choose>
	                                            		<c:when test="${order.financeStatus =='y'}">已付款</c:when> 
													  	<c:otherwise>未付款</c:otherwise> 
	                                            	</c:choose>
	                                            </td>
	                                            <td>
	                                               	<c:choose>
	                                            		<c:when test="${order.transStatus =='y'}">已发货</c:when> 
													  	<c:otherwise>未发货</c:otherwise> 
	                                             	</c:choose>
												</td>
	                                            
	                                            <td>${order.receiver}</td>
	                                            <td>${order.receiverMobile}</td>
	                                            <td>${order.memo}</td>
	                                            
	                                            <td>
	                                              <c:choose>
	                                            		<c:when test="${order.distribution ==1}">默认未分配单</c:when>
	                                            		<c:when test="${order.distribution ==2}">已分配单</c:when>
	                                            		<c:when test="${order.distribution ==3}">撤回单</c:when> 
	                                             	</c:choose>
	                                             </td>
	                                            
	                                            <td id="orderType_${order.orderCode}_${order.memberId}">
	                                               	<c:choose>
	                                            		<c:when test="${order.orderType =='1'}">正常单</c:when>
	                                            		<c:when test="${order.orderType =='2'}">测试单</c:when>
	                                            		<c:when test="${order.orderType =='3'}">问题单</c:when> 
	                                             	</c:choose>
												</td>
												  <td>${storeMap[order.storeCode]}</td>
												
	                                            <td>
	                                            	<a class="btn btn-info btn-xs" href="${contextPath}/order/orderBaseDetails/${order.memberId}/${order.orderCode}" title="订单详情">	
														订单详情
													</a>
													<a class="btn btn-info btn-xs" href="javascript:updateDetail(${order.memberId},${order.orderCode})" title="修改订单详情">	
														修改详情
													</a>
													<c:if test="${order.orderStatus == '2'}">
		                                            	<a class="btn btn-default btn-xs edit-role" href="javascript:updateOrderStatus('${contextPath}','订单确定要审核通过吗?','3',${order.memberId},'${order.orderCode}');" title="审核通过">	
															审核通过
														</a>
		                                            	<a class="btn btn-danger btn-xs edit-role" href="javascript:updateOrderAuditFail(${order.memberId},'${order.orderCode}');" title="审核不通过">	
															审核不通过
														</a>
													</c:if>
													<c:if test="${order.orderStatus == '3'}">
		                                            	<a class="btn btn-default btn-xs edit-role" href="javascript:updateOrderTrans(${order.memberId},'${order.orderCode}');" title="快递单号">	
															快递单号
														</a>
		                                            	<a class="btn btn-success btn-xs edit-role" href="javascript:updateOrderStatus('${contextPath}','订单确定要发货吗?','5',${order.memberId},'${order.orderCode}');" title="发货">	
															发货
														</a>
													</c:if>
													<c:if test="${order.orderStatus == '4'}">
		                                            	<a class="btn btn-default btn-xs edit-role" href="javascript:updateOrderStatus('${contextPath}','订单确定要审核通过吗?','3',${order.memberId},'${order.orderCode}');" title="审核通过">	
															审核通过
														</a>
													</c:if>
													<c:if test="${order.orderStatus == '5'}">
		                                            	<a class="btn btn-success btn-xs edit-role" href="javascript:updateOrderStatus('${contextPath}','确定用户收到货了?','7',${order.memberId},'${order.orderCode}');" title="确认收货">	
															确认收货
														</a>
		                                            	<a class="btn btn-default btn-xs edit-role" href="javascript:updateOrderStatus('${contextPath}','确定用户拒收了?','11',${order.memberId},'${order.orderCode}');" title="用户拒收">	
															用户拒收
														</a>
													</c:if>
													<a class="btn btn-info btn-xs" target="_blank" href="${contextPath}/order/orderBasePrint/${order.memberId}/${order.orderCode}" title="订单打印">	
														订单打印
													</a>
													
												<%-- 	<c:if test="${order.paymentType=='ZFB_APP' && order.financeStatus =='y' && order.orderStatus != '12' }">
														<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${order.paymentType}',${order.memberId},'${order.orderCode}','orderBaseList')" title="退款">	
															支付宝退款
														</a>
													</c:if>
													<c:if test="${order.paymentType=='WX_APP' && order.financeStatus =='y' && order.orderStatus != '12'}">
														<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${order.paymentType}',${order.memberId},'${order.orderCode}','orderBaseList')" title="退款">	
															微信退款
														</a>
													</c:if>
													<c:if test="${order.paymentType=='ZXYL_APP' && order.financeStatus =='y' && order.orderStatus != '12'}">
														<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${order.paymentType}',${order.memberId},'${order.orderCode}','orderBaseList')" title="退款">	
															银联退款
														</a>
													</c:if>
													<c:if test="${order.paymentType=='ALABAO_APP' && order.financeStatus =='y' && order.orderStatus != '12'}">
														<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('${order.paymentType}',${order.memberId},'${order.orderCode}','orderBaseList')" title="退款">	
															如意消费卡退款
														</a>
													</c:if> --%>
												<%-- 	<c:if test="${order.paymentTotalActual=='0.00' && order.financeStatus =='y' && order.orderStatus != '12'}">
														<a class="btn btn-info btn-xs"  href="javascript:void(0);" onclick="javascript:refund('ZERO',${order.memberId},'${order.orderCode}','orderBaseList')" title="退款">	
															0元退款
														</a>
													</c:if> --%>
													<div id="orderTypeUpdate_${order.orderCode}_${order.memberId}">
														<a class="btn btn-info btn-xs" href="javascript:change_orderType('${order.orderCode}',${order.memberId},'${order.orderType}')" title="修改订单类型">	
															修改类型
														</a>
													</div>
													
													<c:if test="${order.orderType =='2'}">
														<a class="btn btn-info btn-xs" href="javascript:deleteOrder('${order.orderCode}',${order.memberId})" title="修改订单类型">	
															删除订单
														</a>
													</c:if>
													
													<c:if test="${level==1 }">
														<a class="btn btn-info btn-xs" href="javascript:distributionOrder(${order.memberId},'${order.orderCode}')" title="分配给其他客服">	
															分配给其他客服
														</a>
														
													</c:if>
													<c:if test="${level==2 }">
														<a class="btn btn-info btn-xs" href="javascript:withdrawOrder(${order.memberId},'${order.orderCode}')" title="订单撤回处理">	
															订单分配撤回
														</a>
													</c:if>
	                                            </td>
	                                        </tr>
                                    	</c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                            <tags:page formId="findForm" url="${contextPath}/order/queryOrderBaseList"></tags:page>
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
