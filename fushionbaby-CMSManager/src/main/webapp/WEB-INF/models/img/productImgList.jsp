<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品图片列表</title>
  	<script type="text/javascript">
  	function query(){
  		$('#findForm').submit();
  		
  	}
  	function deleteById(id){
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.post("${pageContext.request.contextPath}/productImage/deleteById.do",{id:id,time:new Date().getTime()},
  	  					function(data){
  	  				if(data.result=="success"){
  	  					jBox.tip(data.msg);
  	  					window.setTimeout(function () {  
  							$("#findForm").submit();
  						}, 500);
  	  				}
  	  				else {
  	  					jBox.tip(data.msg);
  	  				}
  	  			});
  			}else{
  	  			 $("#findForm").submit();
  	  		}
  			return true;
  		} 
  		$.jBox.confirm("确定删除该记录吗?", "删除提示",submit);
  		
  	}

  	function change_sort(id,sort){
  		 $('#image_sort_'+id).html("<input name='sortOrder' value='"+sort+"' id='new_sort_"+id+"' />"); 
  		 $('#update_area_'+id).html("<button type='button' id='disount_sort_update' class='btn btn-danger btn-xs delete-role'  onclick='javascript:update_sort("+id+");'>确定修改</button>");
  	}
  	function update_sort(id){
  		var sortOrder=$('#new_sort_'+id).val();
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.post('${pageContext.request.contextPath}/productImage/updateById.do',{id:id,sortOrder:sortOrder,time:new Date().getTime()},
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
  		$.jBox.confirm("确定修改显示顺序吗？", "修改显示顺序提示",submit);
  		
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 产品图片列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/productImage/findAll">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">产品编码：</label>
	      					<input type="text" id="code" name="productCode" value="${productCode}" class="form-control"
	      						 placeholder="产品编码">
	  					</div>
				    	<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">产品名称：</label>
	      					<input type="text" id="productName" name="productName" value="${productName}" class="form-control"
	      						 placeholder="产品名称">
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
                              		<th>产品编号</th>
									<th>产品名称</th>
									<th>产品图片</th>
									<th>图片路径</th>
									<th>显示顺序</th>
									<th>修改显示顺序</th>
									<th>删除</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuProductImageDtoList}" var="imageList" varStatus="status">
								<tr>
								  	<td>${status.count }</td>

									<td>${imageList.productCode}</td>

									<td>${imageList.productName}</td>
									
									<td>
										<a href="${product_path}/${imageList.imgPath}"  class="fancybox" rel="gallery">
											<img src="${product_path}/${imageList.imgPath}"
									   			kesrc ="${product_path}/${imageList.imgPath}" width="50" height="50" />
								   		</a>
									</td>
									<td>${imageList.imgPath}</td>
									<td id="image_sort_${imageList.id}">${imageList.sortOrder}</td>
									<td class="hidden-480" id="update_area_${imageList.id}">
										<button type="button" id="disount_sort_update" class="btn btn-success btn-xs delete-role"  onclick="javascript:change_sort('${imageList.id}','${imageList.sortOrder}');">修改显示顺序</button>
									</td>
									<td>
										<button type="button" id="disount_sort_update" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteById('${imageList.id }');">删除</button>
									</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
							<tags:page formId="findForm" url="${contextPath }/productImage/findAll"></tags:page>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
