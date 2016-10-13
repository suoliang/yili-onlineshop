<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品列表</title>
  	 <script type="text/javascript">
      	function selectSku(){
      		if($("input[name='checkAllBtn']").is(":checked")){
      			$("#checkSkuName").val($("input[name='checkAllBtn']:checked").val());
      			$("#checkSkuCode").val($("input[name='checkAllBtn']:checked").attr("lang"));
      			$.jBox.tip('已选择'+$("input[name='checkAllBtn']:checked").val());
      	   }
      	}
      </script>
	
</head>
<body id="index" >
		<tags:message content="${message }"></tags:message>
       
	
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-10 content">
				<div class="panel ">
                  

				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="skuNo" class="col-label">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control" value="${skuDto.skuNo}"
	      						 placeholder="商品编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">商品名称：</label>
	      					<input type="text" id="skuName" name="skuName" class="form-control" value="${skuDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">创建开始时间：</label>
                             <div class="input-group">
                                   <input  style="width:144px" type="text" name="createTimeFrom" class="timeS form-control form_datetime" value="${skuDto.createTimeFrom}" readonly>
                                   <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                       		 </div>
                      	</div>
                        <div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">创建结束时间：</label>
                             <div class="input-group">
                                <input style="width:144px" type="text" name="createTimeTo" class="timeE form-control form_datetime" value="${skuDto.createTimeTo}"   readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
                        </div>
	  					
					    <div class="col-md-4">
	                        <button type="submit" class="btn btn-info" >确认查询</button>
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
                              <th>#</th>
                              <th>序号</th>
							  <th>商品编码</th>
							  <th>商品名称</th>
							  <th>创建时间</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="sku" varStatus="status">
								<tr>
								  <td>
									<label for="checkAllBtn" class="check-label">
                                      <input type="radio" onclick="selectSku();" value="${sku.name}" lang="${sku.code}" name="checkAllBtn" >
                                    </label>
								   </td>	
								   <td scope="row">${status.count}</td>
								   <td>${sku.code}</td>	
								   <td>${sku.name}</td>	
								   <td><fmt:formatDate value="${sku.createTime}" pattern="yyyy-MM-dd"/></td>
								 </tr>
								</c:forEach>
                               </tbody>
                              </table>
                           </div>
                            <input id="checkSkuName" type="hidden">
                            <input id="checkSkuCode" type="hidden">
							<tags:page formId="findForm" url="${contextPath}/skuTogether/findSkuList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
	
       
        <!-- /.container-fluid -->
</body>
</html>
