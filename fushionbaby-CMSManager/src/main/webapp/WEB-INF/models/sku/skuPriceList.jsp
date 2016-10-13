<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品价格管理</title>
  	<script type="text/javascript">
  		
	  	
	  	
	  	function importPriceExcel(){
	  		var url = "iframe:${contextPath}/sku/batchSkuPrice";
			$.jBox(url, {
	  		    title: "EXCEL批量导入商品价格", width: 600,height: 400,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              
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
                   <h3 class="panel-title"><i class="fa fa-cog"></i> 商品价格列表</h3>
                </div>

                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sku/findSkuPriceList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="skuNo" class="col-label">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control" value="${skuDto.skuNo}"
	      						 placeholder="商品编码">
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="skuNo" class="col-label">商品条形码：</label>
	      					<input type="text" id="barCode" name="barCode" class="form-control" value="${skuDto.barCode}"
	      						 placeholder="商品条形码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">商品名称：</label>
	      					<input type="text" id="name" name="skuName" class="form-control" value="${skuDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">产品编码：</label>
	      					<input type="text" id="productCode" name="productCode" class="form-control" value="${skuDto.productCode}"  
	      						placeholder="产品编码" >
	  					</div>
	  					
	  				<%-- 	<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">门店编码：</label>
	      					<input type="text" id="productCode" name="storeCode" class="form-control" value="${skuDto.storeCode}"  
	      						placeholder="门店编码" >
	  					</div> --%>
	  						   <div class="form-group col-md-4 mB15">
                               <label for="a" class="col-label">门店名称：</label>
                                 <select id="storeCode" name="storeCode" class="form-control lg-select" >
                                     <option value="">阿拉丁商城</option>
                                     <c:forEach items="${storeList }" var="store">
                                        <option value="${store.code}" <c:if test="${store.code == skuDto.storeCode }"> selected="selected" </c:if>  > ${store.name}</option>
									  </c:forEach>  
                                 </select>
                          </div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">商品状态：</label>
      						<select name="status" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					           	<option value="">所有</option>
					            <c:forEach items="${dic:getDictByType('SKU_STATUS')}" var="dict">
					            	<option value="${dict.value }" <c:if test="${dict.value==skuDto.status }">selected="selected"</c:if>  >${dict.label }</option>
					            </c:forEach>
					        </select>
    					</div>
    					
					    <div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">创建时间：</label>
	    					<div class="input-group">
								<input class="timeS form-control form_datetime" readonly name="createTimeFrom"
								 	 type="text" value="${skuDto.createTimeFrom}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div> -
							<div class="input-group">
								<input class="timeE form-control form_datetime"  readonly name="createTimeTo" 
									 type="text" value="${skuDto.createTimeTo}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
	    				    </div>
						</div>
						
	    				
					    <div class="col-md-4">
	                        <button type="submit" class="btn btn-info">确认查询</button>
	                        <button type="button" class="btn btn-warning" onclick="importPriceExcel()">批量导入商品价格</button>
	                        <button type="button" class="btn btn-success" onclick="downLoadExcel('批量导入商品价格模板\.xls')">下载批量导入商品价格模板</button>
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
							  <th>商品编码</th>
							  <th>商品条形码</th>
							  <th>产品编码</th>
							  <th>门店编码</th>
							  <th>商品名称</th>
							  <th>商品状态</th>
							  
							  <th>成本价</th>
							  <th>现价</th>
							  <th>阿拉丁会员价</th>
							  
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
								   <td>${tmp.skuNo}</td>	
								   <td>${tmp.barCode}</td>	
								   <td>${tmp.productCode}</td>	
								   <td align="center">${empty tmp.storeCode?'-':tmp.storeCode}</td>	
								   <td>${tmp.skuName}</td>	
								   <td>
								   		${dic:getDictLabel(tmp.status, 'SKU_STATUS', '-')}
								   </td>
								   <td>${tmp.skuPriceDto.costPrice}</td>	
								   <td>${tmp.skuPriceDto.currentPrice}</td>	
								   <td>${tmp.skuPriceDto.aladingPrice}</td>	
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${tmp.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
								   	 <a class="btn btn-info btn-xs" title="修改价格" href="${contextPath }/sku/skuPriceModify?uniqueCode=${tmp.uniqueCode}">	
										修改价格
									 </a>	
									 <a class="btn btn-info btn-xs" href="${contextPath}/sku/skuDetails?uniqueCode=${tmp.uniqueCode }" title="查看详情">	
										商品详情
									 </a>										
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sku/findSkuPriceList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
</body>
</html>
