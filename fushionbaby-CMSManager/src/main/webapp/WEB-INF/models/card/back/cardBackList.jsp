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
        <title>阿拉丁卡退卡记录列表</title>
    </head>
    <script type="text/javascript">
        function updateStatus(id,status){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/memberCardBack/updateStatus/"+id+"/"+status+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要修改该状态吗？", "修改提示",submit);
        }
        function cardBackListExport(){
    		$("#findForm").attr("action",contextPath+"/memberCardBack/cardBackListExportExcel");
    		$("#findForm").submit();
    		$("#findForm").attr("action",contextPath+"/memberCardBack/cardBackList");
    		
    	}
        
	    function change_cardType(cardNo,cardType){
				var o1="1"==cardType?"selected":"";
				var o2="2"==cardType?"selected":"";
				var o3="3"==cardType?"selected":"";
				var cardTypeSelect=
				 	 "<select  style='width: 100px' name='cardType' id='newCardType_"+cardNo+"'" + " class='form-control lg-select'>"+
		             "	<option value='1' "+o1+">正常卡</option>"+
		             "	<option value='2' "+o2+">测试卡</option>"+
		             "	<option value='3' "+o3+">问题卡</option>"+
	       		     "</select>";
		  		$('#cardType_'+cardNo).html(cardTypeSelect);
		  		$('#cardTypeUpdate_'+cardNo).html('<a class="btn btn-danger btn-xs" href="javascript:update_cardType(\''+cardNo+'\')" title="修改卡类型">确定修改</a>');
		  	}
		  	function update_cardType(cardNo){
		  		var cardType=$("#newCardType_"+cardNo).val();
		  		var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				$.post('${pageContext.request.contextPath}/memberCardBack/updateCardType',{cardNo:cardNo,cardType:cardType,time:new Date().getTime()},
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
		  		$.jBox.confirm("确定修改卡类型吗？", "修改卡类型提示",submit);
		  		
		  	}
		  	
		  	function deleteCardOrder(cardNo){
		  		var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				$.post('${pageContext.request.contextPath}/memberCardBack/deleteCardOrder',{cardNo:cardNo,time:new Date().getTime()},
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
		  		$.jBox.confirm("确定删除退卡记录吗？", "删除退卡记录提示",submit);
		  		
		  	}
        </script>
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 阿拉丁卡退卡记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉丁卡号：</label>
                                    <input type="text" class="form-control" id="cardNo" name="cardNo" value="${cardBackDto.cardNo}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">银行持卡人：</label> 
                                    <input type="text" class="form-control" id="bankCardHolder" name="bankCardHolder" value="${cardBackDto.bankCardHolder}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">银行卡卡号：</label> 
                                    <input type="text" class="form-control" id="bankCardNo" name="bankCardNo" value="${cardBackDto.bankCardNo}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">银行名称：</label> 
                                    <input type="text" class="form-control" id="bankName" name="bankName" value="${cardBackDto.bankName}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝账号：</label> 
                                    <input type="text" class="form-control" id="acount" name="acount" value="${cardBackDto.acount}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">交易状态：</label> 
                                    <select name="backStatus" class="form-control lg-select">
                                        <option value="" >全部</option>
                                        <option value="1" ${cardBackDto.backStatus eq '1'?'selected':'' }>待审核</option>
                                        <option value="2" ${cardBackDto.backStatus eq '2'?'selected':'' }>审核通过</option>
                                        <option value="3" ${cardBackDto.backStatus eq '3'?'selected':'' }>审核不通过</option>
                                        <option value="4" ${cardBackDto.backStatus eq '4'?'selected':'' }>交易完成</option>
                                        <option value="5" ${cardBackDto.backStatus eq '5'?'selected':'' }>已转入如意宝</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">退卡申请开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${cardBackDto.createTimeFrom}" class="form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">退卡申请结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${cardBackDto.createTimeTo}" class="form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
                                <div class="form-group col-md-4 mB15">
                                	<button type="submit" class="btn btn-success speBtn">查 询</button>
                                	<button type="button" onclick="cardBackListExport()" class="btn btn-success speBtn">导出退卡列表</button> 
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
											<th>如意宝账号</th>
											<th>阿拉丁卡号</th>
											<th>退卡面额</th>
											<th>银行卡卡号</th>
											<th>银行卡持卡人</th>
											<th>银行名称</th>
											<th>银行支行名称</th>
											<th>退卡申请时间</th>
											<th>处理状态</th>
											<th>订单类型</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${memberCardBacklist}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${list.acount}</td>
											<td>${list.cardNo}</td>
											<td>${list.money}</td>
											<td>${list.bankCardNo }</td>
											<td>${list.bankCardHolder }</td>
											<td>${list.bankName }</td>
											<td>${list.bankBranchName }</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td id="cardType_${list.cardNo}">
                                               	<c:choose>
                                            		<c:when test="${list.cardType =='1'}">正常卡</c:when>
                                            		<c:when test="${list.cardType =='2'}">测试卡</c:when>
                                            		<c:when test="${list.cardType =='3'}">问题卡</c:when> 
                                             	</c:choose>
											</td>
											<c:if test="${list.backStatus eq '1' }">
											       <td>待审核</td>
											       <td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:updateStatus('${list.id}','2');">审核通过</button>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:updateStatus('${list.id}','3');">审核不通过</button>
											     		<div id="cardTypeUpdate_${list.cardNo}">
															<a class="btn btn-info btn-xs" href="javascript:change_cardType('${list.cardNo}','${list.cardType}')" title="修改订单类型">	
																修改类型
															</a>
														</div>
														<c:if test="${list.cardType =='2'}">
															<a class="btn btn-info btn-xs" href="javascript:deleteCardOrder(${list.cardNo})" title="修改订单类型">	
																	删除退卡记录
															</a>
														</c:if>
											      </td>
											
											</c:if>
											<c:if test="${list.backStatus eq '2' }">
											       <td>审核通过</td>
											       <td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:updateStatus('${list.id}','4');">交易完成</button>
											      		<div id="cardTypeUpdate_${list.cardNo}">
															<a class="btn btn-info btn-xs" href="javascript:change_cardType('${list.cardNo}','${list.cardType}')" title="修改订单类型">	
																修改类型
															</a>
														</div>
														<c:if test="${list.cardType =='2'}">
															<a class="btn btn-info btn-xs" href="javascript:deleteCardOrder('${list.cardNo}')" title="修改订单类型">	
																	删除退卡记录
															</a>
														</c:if>
											      </td>
											</c:if>
											<c:if test="${list.backStatus eq '3' }">
											       <td>审核不通过</td>
											       <td>
											       		<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:updateStatus('${list.id}','2');">审核通过</button>
											       		<div id="cardTypeUpdate_${list.cardNo}">
															<a class="btn btn-info btn-xs" href="javascript:change_cardType('${list.cardNo}'},'${list.cardType}')" title="修改订单类型">	
																修改类型
															</a>
														</div>
														<c:if test="${list.cardType =='2'}">
															<a class="btn btn-info btn-xs" href="javascript:deleteCardOrder('${list.cardNo}')" title="修改订单类型">	
																	删除退卡记录
															</a>
														</c:if>
											       </td>
											</c:if>
											<c:if test="${list.backStatus eq '4' }">
											       <td>交易完成</td>
											       <td>
											     		  交易完成
											     		  <div id="cardTypeUpdate_${list.cardNo}">
															<a class="btn btn-info btn-xs" href="javascript:change_cardType('${list.cardNo}','${list.cardType}')" title="修改订单类型">	
																修改类型
															</a>
														</div>
														<c:if test="${list.cardType =='2'}">
															<a class="btn btn-info btn-xs" href="javascript:deleteCardOrder('${list.cardNo}')" title="修改订单类型">	
																	删除退卡记录
															</a>
														</c:if>
											       </td>
											</c:if>
											<c:if test="${list.backStatus eq '5' }">
											       <td>已转入如意宝</td>
											       <td>
											    		   已转入如意宝
											    		 <div id="cardTypeUpdate_${list.cardNo}">
															<a class="btn btn-info btn-xs" href="javascript:change_cardType('${list.cardNo}','${list.cardType}')" title="修改订单类型">	
																修改类型
															</a>
														</div>
														<c:if test="${list.cardType =='2'}">
															<a class="btn btn-info btn-xs" href="javascript:deleteCardOrder('${list.cardNo}')" title="修改订单类型">	
																	删除退卡记录
															</a>
														</c:if>
											       </td>
											</c:if>
											 
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/memberCardBack/cardBackList"></tags:page>
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