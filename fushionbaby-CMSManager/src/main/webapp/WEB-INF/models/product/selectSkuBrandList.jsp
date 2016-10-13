<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品品牌列表</title>
  	 <script type="text/javascript">
      	function selectSkuBrand(){
      		if($("input[name='checkAllBtn']").is(":checked")){
      			$("#checkSkuBrandName").val($("input[name='checkAllBtn']:checked").val());
      			$("#checkSkuBrandCode").val($("input[name='checkAllBtn']:checked").attr("lang"));
      			$.jBox.tip('已选择'+$("input[name='checkAllBtn']:checked").val());
      	   }
      	}
      </script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
       
	
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-10 content">
				<div class="panel-body">
                  

				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">品牌编码：</label>
	      					<input type="text" id="brandCode" name="brandCode" class="form-control" value="${skuBrandDto.brandCode}"
	      						 placeholder="品牌编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">品牌名称：</label>
	      					<input type="text" id="brandName" name="brandName" class="form-control" value="${skuBrandDto.brandName}"  
	      						placeholder="品牌名称" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">是否显示：</label>
      						<select name="isShow" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="y" ${skuBrandDto.isShow =='y'?'selected':''}>显示</option>
					            <option value="n" ${skuBrandDto.isShow =='n'?'selected':''}>不显示</option>
					        </select>
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
							  <th>品牌编码</th>
							  <th>品牌名称</th>
							  <th>品牌前缀</th>
							  <th>创建时间</th>
							  <th>是否显示</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="skuBrandList" varStatus="status">
								<tr>
								  <td>
									<label for="checkAllBtn" class="check-label">
                                      <input type="radio" onclick="selectSkuBrand();" value="${skuBrandList.brandName}" lang="${skuBrandList.brandCode}" name="checkAllBtn" >
                                    </label>
								   </td>	
								   <td scope="row">${status.count}</td>
								   <td>${skuBrandList.brandCode}</td>	
								   <td>${skuBrandList.brandName}</td>	
								   <td>${skuBrandList.brandPrefix}</td>
								   <td><fmt:formatDate value="${skuBrandList.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								   <td>
								   	 <c:if test="${skuBrandList.isShow=='y'}">显示</c:if>
								   	 <c:if test="${skuBrandList.isShow=='n'}">不显示</c:if>
								   </td>
								 </tr>
								</c:forEach>
                               </tbody>
                              </table>
                           </div>
                            <input id="checkSkuBrandName" type="hidden">
                            <input id="checkSkuBrandCode" type="hidden">
							<tags:page formId="findForm" url="${contextPath}/skuBrand/skuBrandList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
	
       
        <!-- /.container-fluid -->
</body>
</html>
