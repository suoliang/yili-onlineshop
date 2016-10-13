<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>库存管理</title>
  	<script type="text/javascript">
  
	  	function querySkuInventoryList(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	function loadInventory(skuCode){
			var url = "iframe:${contextPath}/sku/loadSkuInventory/"+skuCode;
			$.jBox(url, {
	  		    title: "库存编辑", width: 800,height:300,top:"30%",
	  		    buttons: { '确定':true,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	            	  
	              }

	            var contents = h.find("iframe").contents();
	           	var skuCode = contents.find("input[name='skuCode']").val();
	           	var availableQty = contents.find("input[name='availableQty']").val();
	           	
	           	$.ajax({
	                type: "POST",
	                url: "${contextPath}/sku/modifyInventory/"+skuCode+"/"+availableQty,
	                success: function(data){
	                			$.jBox.tip('库存数量修改成功');
	                			setTimeout(function() {  
	                				$("#findForm").submit(); 
	                		      }, 500);
	                			
	                }
	            });
	          }

	  		});
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品库存列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sku/skuInventoryList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="a" class="col-label">产品编码：</label>
	      					<input type="text" id="productCode" name="productCode" class="form-control" value="${skuInventoryDto.productCode}"
	      						 placeholder="产品编码">
	  					</div>
				    
					<%--     <div class="form-group col-md-4 mB15">
	    					<label for="a" class="col-label">门店编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control" value="${skuInventoryDto.skuNo}"  
	      						placeholder="门店编码" >
	  					</div> --%>
	  					
	  					  <div class="form-group col-md-4 mB15">
                               <label for="a" class="col-label">门店名称：</label>
                                 <select id="storeCode" name="storeCode" class="form-control lg-select" >
                                     <option value="">阿拉丁商城</option>
                                     <c:forEach items="${storeList }" var="store">
                                        <option value="${store.code}" <c:if test="${store.code == skuInventoryDto.storeCode }"> selected="selected" </c:if>  > ${store.name}</option>
									  </c:forEach>  
                                 </select>
                          </div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="a" class="col-label">商品条形码：</label>
	      					<input type="text" id="barCode" name="barCode" class="form-control" value="${skuInventoryDto.skuNo}"  
	      						placeholder="商品条形码" >
	  					</div>
					    
					     <div class="form-group col-md-4 mB15">
	    					<label for="a" class="col-label">商品名称：</label>
	      					<input type="text" id="skuName" name="skuName" class="form-control" value="${skuInventoryDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					
						
						<div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">创建时间：</label>
                             <div class="input-group">
                                   <input type="text" name="createTimeFrom" class="timeS form-control form_datetime" value="${skuInventoryDto.createTimeFrom}" readonly>
                                   <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                       		 </div> -
                       		  <div class="input-group">
                                <input type="text" name="createTimeTo" class="timeE form-control form_datetime" value="${skuInventoryDto.createTimeTo}" readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
                      	</div>
	  					
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="querySkuInventoryList()">查询</button>
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
							  <th>产品编码</th>
							  <th>门店编码</th>
							  <th>商品条形码</th>
							  <th>商品名称</th>
							  <th>颜色</th>
							  <th>尺寸</th>
							  <th>可用量</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuInventoryList}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count }</td>
								   <td>${tmp.productCode}</td>	
								   <td>${tmp.storeName}</td>	
								   <td>${tmp.barCode}</td>	
								   <td>${tmp.skuName}</td>	
								   <td>${tmp.skuColor}</td>
								   <td>${tmp.skuSize}</td>
								   <td>${tmp.availableQty}</td> 
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>													
									 <a class="btn btn-default btn-xs edit-role" href="javascript:loadInventory('${tmp.skuCode}')" title="修改">	
										修改
									 </a>	
									 <a class="btn btn-info btn-xs" href="${contextPath}/sku/skuDetails?uniqueCode=${tmp.skuCode }" title="查看详情">	
										商品详情
									 </a>
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sku/skuInventoryList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
