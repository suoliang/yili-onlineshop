<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
  	<script type="text/javascript">
  		function editSortOrder(id,sortOrder){
  			
  			var url = "iframe:${contextPath}/skuImage/editSortOrderModel/"+id+"?sortOrder="+sortOrder;
	  		$.jBox(url, {
	  		    title: "编辑显示顺序", width: 400,height: 200,
	  		    buttons: { '确定':true,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              var contents = h.find("iframe").contents();
	              var url = contents.find("#sortOrderForm").attr("action");
	              var id = contents.find("input[name='id']").val();
	              var sortOrder = contents.find("input[name='sortOrder']").val();
	              var uniqueCode = contents.find("input[name='uniqueCode']").val();
	              $.post(url, {id:id,sortOrder:sortOrder,uniqueCode:uniqueCode,datatime:new Date().getTime()},function(){
	            	  window.location.href="${contextPath}/skuImage/findSkuImage/"+uniqueCode+"?time="+new Date().getTime();
	              });

	  		  	}
			});
  			
  		}
  	
  		function toSkuList(){
  			 window.location.href="${contextPath}/sku/findSkuList/?time="+new Date().getTime();
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
                      		<h3 class="panel-title"><i class="fa fa-cog"></i> 商品图片上传</h3>
                   		</div>
                   		<div class="panel-body">
				     		<form class="form-horizontal page" id="findForm" method="post" action="${contextPath }/skuImage/uploadSkuImage">
							   <div class="form-group col-md-2 mB15">
			    					<label for="skuNo">商品编码：</label>
			    					<input type="hidden" name="skuCode" value="${sku.uniqueCode }" />
			      					<input type="text" id="skuNo" class="form-control" value="${sku.skuNo}" readonly="readonly"
			      						 placeholder="商品编码">
			  					</div>
				    
							    <div class="form-group col-md-8 mB15">
			    					<label for="name">商品名称：</label>
			      					<input type="text" id="name" name="skuName" class="form-control" value="${sku.name}" readonly="readonly"
			      						placeholder="商品名称" >
			  					</div>
			  					
			  					<div class="form-group">
								    	<div class="col-sm-offset-2 col-sm-10">
								      		<button class="btn btn-primary col-md-2" type="button" onclick="javascript:toSkuList();">返回上一页</button>
								   	 	</div>
								</div>
				    	
				   				<div class="clearfix"></div>
	
						  		<div class="panel panel-default">
						  		<div class="panel-heading">
		                      		<h3 class="panel-title"> 上传图片</h3>
		                   		</div>
						  			
							  		<div class="form-group">
										<div class="col-sm-5">
											<input type="hidden" id="imgUrl" name="imgUrl"  value="${picsb }"/>
									
											<tags:ckfinder input="imgUrl" type="images"
				 								uploadPath="/sku/" selectMultiple="true" />
										</div>
									</div>
									
									
									<div class="form-group">
								    	<div class="col-sm-offset-2 col-sm-10">
								      		<button class="btn btn-success col-md-2" type="submit">确认上传</button>
								   	 	</div>
									</div>
								</div>
								</form>
								
								
								  <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
							  <th>图片展示</th>
							  <th>显示顺序</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuImageList}" var="tmp" varStatus="status">
								<tr>
								   <td>
								   	<a href="${tmp.imgUrl}"  class="fancybox" rel="gallery">
								  	 <img alt="" src="${tmp.imgUrl}" kesrc ="${tmp.imgUrl}" width="70" height="50"  />
								    </a>
								   </td>	
								   
								   <td>${ (empty tmp.sortOrder)?'-':tmp.sortOrder } </td>	
								   <td>${tmp.createTime}</td>
								   <td>		
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:editSortOrder(${tmp.id},${ (empty tmp.sortOrder)?0:tmp.sortOrder })"   title="设置显示顺序">	
									         设置顺序
									 </a>
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
							</div>	
							</div>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
       
        <!-- /.container-fluid -->
</body>
</html>
