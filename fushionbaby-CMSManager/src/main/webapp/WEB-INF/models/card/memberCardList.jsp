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
        <title>阿拉丁卡购买记录列表</title>
    </head>
    <script type="text/javascript">
/*     function  cardCostRecordList(code){
  		window.location.href = "${contextPath}/memberCardIncomeRecord/findByCardCode/"+code+"/"+new Date().getTime();
    }
    function  incomeRecordList(code){
  		window.location.href = "${contextPath}/cardCostRecord/findByCardCode/"+code+"/"+new Date().getTime();
    } */
    
    function change_type(code,type){
		var o1="1"==type?"selected":"";
		var o2="2"==type?"selected":"";
		var o3="3"==type?"selected":"";
		var typeSelect=
		 	 "<select  style='width: 100px' name='type' id='newType_"+code+"'" + " class='form-control lg-select'>"+
             "	<option value='1' "+o1+">正常卡</option>"+
             "	<option value='2' "+o2+">测试卡</option>"+
             "	<option value='3' "+o3+">问题卡</option>"+
   		     "</select>";
  		$('#type_'+code).html(typeSelect);
  		$('#typeUpdate_'+code).html('<a class="btn btn-danger btn-xs" href="javascript:update_type(\''+code+'\')" title="修改卡类型">确定修改</a>');
  	}
  	function update_type(code){
  		var type=$("#newType_"+code).val();
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.post('${pageContext.request.contextPath}/memberCard/updateType',{code:code,type:type,time:new Date().getTime()},
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
  		$.jBox.confirm("确定修改阿拉丁卡使用类型吗？", "修改阿拉丁卡使用类型提示",submit);
  		
  	}
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 阿拉丁卡购买记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post" action="${contextPath}/memberCard/memberCardList">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">会员账号：</label>
                                    <input type="text" class="form-control" id="memberName" name="memberName" value="${memberCardDto.memberName}" >
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉丁卡卡号：</label> 
                                    <input type="text" class="form-control" id="cardNo" name="cardNo" value="${memberCardDto.cardNo}" >
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝账号：</label> 
                                    <input type="text" class="form-control" id="acount" name="acount" value="${memberCardDto.acount}" >
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">购买开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${memberCardDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">购买结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${memberCardDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
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
											<th>会员账号</th>
											<th>如意宝账号</th>
											<th>阿拉丁卡卡号</th>
											<th>阿拉丁卡面值（元）</th>
											<th>阿拉丁卡总赠券（元）</th>
											<th>阿拉丁卡类型</th>
											<th>使用类型</th>
											<th>购卡时间</th>
											<th>操作</th>
										<!-- 	<th>操作</th> -->
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${list.memberName}</td>
											<td>${list.acount}</td>
											<td>${list.cardNo}</td>
											<td>${list.faceValue }</td>
											<td>${list.totalRebate }</td>
											<td>
												<c:if test="${list.cardType eq '1' }">季卡</c:if>
											    <c:if test="${list.cardType eq '2' }">双季卡</c:if>
											    <c:if test="${list.cardType eq '3' }">年卡</c:if>
											</td>
											<td id="type_${list.code}">
												<c:if test="${list.type eq '1' }">正常卡</c:if>
											    <c:if test="${list.type eq '2' }">测试卡</c:if>
											    <c:if test="${list.type eq '3' }">问题卡</c:if>
											</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>
												<div id="typeUpdate_${list.code}">
													<a class="btn btn-info btn-xs" href="javascript:change_type('${list.code}','${list.type}')" title="修改订单类型">	
														修改使用类型
													</a>
												</div>
											</td>
										<%-- 	<td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:cardCostRecordList('${list.code }');">收益记录</button>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:incomeRecordList('${list.code }');">消费记录</button>
											</td> --%>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/memberCard/memberCardList"></tags:page>
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