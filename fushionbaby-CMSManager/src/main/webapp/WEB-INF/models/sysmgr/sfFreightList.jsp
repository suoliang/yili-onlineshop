<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>运费列表</title>
  	<script type="text/javascript">
  	function query(){
  		$('#findForm').submit();
  		
  	}

  	function change_amount(areaCode,amount){
  		 $('#amount_'+areaCode).html("<input name='amount' value='"+amount+"' id='new_amount_"+areaCode+"' />"); 
  		 $('#update_amount_'+areaCode).html("<button type='button' id='disount_sort_update' class='btn btn-danger btn-xs delete-role'  onclick='javascript:update_amount("+areaCode+");'>确定修改</button>");
  	}
  	function update_amount(areaCode){
  		var amount=$('#new_amount_'+areaCode).val();
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.post('${pageContext.request.contextPath}/sfFreight/updateAmount',{areaCode:areaCode,amount:amount,time:new Date().getTime()},
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
  		$.jBox.confirm("确定修改运费吗？", "修改运费提示",submit);
  		
  	}
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 运费列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sfFreight/findAll">
				     
					    <div class="col-md-8">
	                        <button type="button" class="btn btn-info" onclick="query()">查询</button>
	                        
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
                              		<th>省市</th>
									<th>运费</th>
									<th>修改运费</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="sfFreight" varStatus="status">
								<tr>
								  	<td>${status.count }</td>

									<td>${sfFreight.city}</td>
									
									<td id="amount_${sfFreight.areaCode}">${sfFreight.amount}</td>

									<td class="hidden-480" id="update_amount_${sfFreight.areaCode}">
										<button type="button" id="amount_update" class="btn btn-success btn-xs delete-role"  onclick="javascript:change_amount('${sfFreight.areaCode}','${sfFreight.amount}');">修改运费</button>
									</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sfFreight/findAll"></tags:page>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
