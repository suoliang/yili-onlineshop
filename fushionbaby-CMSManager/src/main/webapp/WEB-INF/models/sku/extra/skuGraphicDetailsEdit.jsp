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
  	
  		
  		function editSortOrder(id){
  			
  			$("#td-"+id+" input").show();
  			$("#td-"+id+" span").html("");
  			$("#submitBtn"+id).show();
  			$("#editBtn"+id).hide();
  		}
  		function submitSortOrder(id){
  			$("#submitBtn"+id).hide();
  			$("#editBtn"+id).show();
  			var sortOrder = $("#td-"+id+" input").val();
  			$("#td-"+id+" span").html(sortOrder);
  			$("#td-"+id+" input").hide();
  			var url = "${contextPath}/sku/extra/editGraphicDetailsSort";
	  		$.post(url, {id:id,sortOrder:sortOrder,datatime:new Date().getTime()},function(data){
	  			
	  			if(data==false){
	  				$.jBox.info("修改失败！");
	  				return ;
	  			}
	  			$.jBox.info("修改成功！");
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
                      		<h3 class="panel-title"><i class="fa fa-cog"></i> 商品图文详情上传</h3>
                   		</div>
                   		<div class="panel-body">
				     		<form class="form-horizontal page" id="findForm" method="post" action="${contextPath }/sku/extra/uploadGraphicDetails">
							  <div class="form-group col-md-2 mB15">
			    					<label for="skuNo">产品编码：</label>
			      					<input type="text" id="productCode" name="productCode" class="form-control" value="${productCode}" readonly="readonly"
			      						 placeholder="商品编码">
			  					</div>
							  
							   <div class="form-group col-md-2 mB15">
			    					<label for="skuNo">商品编码：</label>
			      					<input type="text" id="skuNo" class="form-control" value="${skuNos}" readonly="readonly"
			      						 placeholder="商品编码">
			  					</div>
				    
							    <div class="form-group col-md-8 mB15">
			    					<label for="name">商品名称：</label>
			      					<input type="text" id="name" name="skuName" class="form-control" value="${skuNames}" readonly="readonly"
			      						placeholder="商品名称" >
			  					</div>
			  					
			  					<div class="form-group">
								    	<div class="col-sm-offset-2 col-sm-10">
								      		<button class="btn btn-primary col-md-2" type="button" onclick="javascript:history.go(-1);">返回上一页</button>
								   	 	</div>
								</div>
				    	
				   				<div class="clearfix"></div>
	
						  		<div class="panel panel-default">
						  		<div class="panel-heading">
		                      		<h3 class="panel-title"> 上传图片</h3>
		                   		</div>
						  			
							  		<div class="form-group">
										<div class="col-sm-5">
				 								
				 							<input type="hidden"  id="graphicDetails" name="graphicDetails"  value="${picsb }"/>
					
											<tags:ckfinder input="graphicDetails" type="images" 
				 								uploadPath="/sku/products/" selectMultiple="true" />
				 								
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
							  <th width="30%">显示顺序</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${productImages}" var="tmp" varStatus="status">
								<tr>
								   <td>
								   	<a href="${tmp.imgPath}"  class="fancybox" rel="gallery">
								  	 <img alt="" src="${tmp.imgPath}" kesrc ="${tmp.imgPath}" width="70" height="50"  />
								    </a>
								   </td>	
								   
								   <td id="td-${tmp.id }">
								   	  <span  style="line-height:40px"> ${ (empty tmp.sortOrder)?'-':tmp.sortOrder }</span>
								   	  <input style="display:none;line-height:40px" type="number" value="${(empty tmp.sortOrder)?'':tmp.sortOrder }" />
								   </td>	
								   <td>${tmp.createTime}</td>
								   <td>		
									 <a class="btn btn-danger btn-xs delete-role"  style="line-height:40px"
									 	id="editBtn${tmp.id }"  href="javascript:editSortOrder(${tmp.id})"   title="设置显示顺序">	
									         设置顺序
									 </a>
									 <a class="btn btn-danger btn-xs delete-role"  style="display:none;line-height:40px"
									 	id="submitBtn${tmp.id }"   href="javascript:submitSortOrder(${tmp.id})"   title="设置显示顺序">	
									       确定修改
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
