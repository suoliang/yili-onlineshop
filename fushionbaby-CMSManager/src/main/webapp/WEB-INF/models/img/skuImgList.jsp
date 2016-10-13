<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品图片列表</title>
  	<script type="text/javascript">
  	function query(){
  		$('#findForm').submit();
  		
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品图片列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/skuImage/findAll">
				     
					  	<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" value="${skuNo}" class="form-control"
	      						 placeholder="商品编码">
	  				    </div>
	  				    <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">商品条形码：</label>
	      					<input type="text" id="barCode" name="barCode" value="${barCode}" class="form-control"
	      						 placeholder="商品条形码">
	  				    </div>
				   		<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">图片类型编码：</label>
	      					<input type="text" id="code" name="imageTypeCode" value="${imageTypeCode}" class="form-control"
	      						 placeholder="图片类型编码">
	  					</div>
					    
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
                              		<th>商品编码</th>
                              		<th>商品条形码</th>
									<th>图片类型编码</th>
									<th>商品图片</th>
									<th>图片路径</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuImageList}" var="imageList" varStatus="status">
								<tr>
								  	<td>${status.count }</td>

									<td>${imageList.skuNo}</td>
									
									<td>${imageList.barCode}</td>

									<td>${imageList.imageTypeCode}</td>
									
									<td>
										<a href="${sku_path}${imageList.imgUrl}"  class="fancybox" rel="gallery">
											<img src="${sku_path}${imageList.imgUrl}"
									   			kesrc ="${sku_path}${imageList.imgUrl}" width="50" height="50" />
									   	</a>
									</td>
									<td>${imageList.imgUrl}</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
							<tags:page formId="findForm" url="${contextPath }/skuImage/findAll"></tags:page>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
