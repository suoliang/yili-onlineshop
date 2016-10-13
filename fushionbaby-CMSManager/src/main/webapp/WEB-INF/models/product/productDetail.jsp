<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->

<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>产品管理</title>
	<script type="text/javascript">
</script>
</head>
 <body>
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 产品信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/sku/saveProduct" id="productForm" >
				 		<input type="hidden"  id="pageType" value="${pageType }" />
						<div class="form-group">
							<label class="col-sm-2 control-label">产品编码：</label>
							<div class="col-sm-2">
								<input name="id" type="hidden" value="${product.id }" />
								<input name="oldCode" id="oldCode" type="hidden" value="${product.code}"/>
								<input name="code" id="code" type="text" class="form-control"
						 			value="${product.code}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">产品名称：</label>
							<div class="col-sm-2">
								<input name="name" id="name" type="text" class="form-control" 
								 	value="${product.name}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品分类：</label>
							<div class="col-sm-2">
								<input id="categorySel" class="form-control" type="text" readonly value="${product.categoryName}"  />
							</div>
						</div>
						
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品品牌：</label>
							<div class="col-sm-2">
								<input id="categorySel" class="form-control" type="text" readonly value="${product.brandName }"  />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">使用类型：</label>
							<div class="col-sm-2">
								<select name="disable" id="disable" class="form-control"  disabled="disabled"
								data-placeholder="Choose a Category" tabindex="1">
									<option value="y" ${product.disable =='y'?'selected':''}>启用</option>
									<option value="n" ${product.disable =='n'?'selected':''}>禁用</option>
								</select>
							</div>
						</div>
			

						<div class="form-group">
					
							<label class="col-sm-2 control-label">添加商品属性：</label>
					
							<div class="col-sm-5">
						
								<button type="button" class="btn btn-default" aria-label="Left Align"
									id="add-sku-product-attr" onclick="addAttr()" disabled="disabled" >
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
								</button>
						
						</div>
			
						<!--追加属性时从这个标签开始，这样可以保证不乱 -->
						<input id="sku-product-attr-input" value="" name="attrInput" type="hidden">
					
					</div>
					<div class="form-group">
						<div class="table-responsive col-sm-7 col-sm-offset-2" id="sku-product-attr">
							
							<table id="sku-product-attr-table"  class="table table-bordered table-hover" >
								<thead>
								<tr>
							 		<th id="attrName">属性名</th>
							 		<th id="attrValue">属性值</th>
							 		<th id="attrValue">显示顺序</th>
		
								</tr>
								</thead>
								<tbody id="attr-tbody">
										<c:forEach items="${attrs }" var="attr" varStatus="s">
									<tr id="attr-${attr.productCode }${s.index }">
										<td><input name='attrName' value="${attr.attrName }" 
											 /></td>
											
										<td><input name='attrValue'  value="${attr.attrValue }" 
											 /></td>
											
										<td><input name='showOrder' value="${attr.showOrder }"  
											 /></td>
										
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>	
				
					<div class="form-group">
						<label class="col-sm-2 control-label">web图文：</label>
						<div class="col-sm-2">
							<c:forEach items="${productImages }" var="img">
								<a href="${img.imgPath }"  class="fancybox" rel="gallery">
								<img alt="${img.imgPath }" src="${img.imgPath }"  kesrc ="${img.imgPath }" class="img-responsive" width="100" height="100"   />
								</a>
							</c:forEach>
						</div>
					</div>
				
					<div class="form-group">
						<label class="col-sm-2 control-label">商品集合：</label>
						<div class="col-sm-2">
							<button type="button" class="btn btn-default" aria-label="Left Align" disabled="disabled"
							onclick="addSku()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
							</button>
						</div>
					</div>
					<div class="form-group">
						<div class="table-responsive col-sm-8 col-sm-offset-2" id="sku" >
							<table id="sku-table"  class="table table-bordered table-condensed" >
								<thead>
									<tr>
								 		<th>商品编号</th>
								 		<th>商品名称</th>
								 		<th>描述</th>
 									 		
								 	</tr>
								 </thead>
								<tbody id="sku-tbody">
									<c:forEach items="${skus }" var="sku" varStatus="s">
									<tr id="skutr-${sku.skuNo }${s.index}">
								 		<td>${sku.skuNo }</td>
								 		<td>${sku.skuName }</td>
								 		<td>${sku.skuDescription }</td>
							
	 							 	</tr>
	 							 	
	 							 	</c:forEach>
								</tbody>
				
							</table>
							<input type="hidden" name="skus" value="" />
							<input type="hidden" name="attrs" value="" />
							<input type="hidden" name="skuNos" id="removeSkuNo" />
						</div>
					</div>
				
				  
					<div class="form-group">
				    	<div class="col-sm-offset-2 col-sm-10">
				      	
							<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
				   	 	</div>
					</div>
				  
				</form>
			  </div>
			  </div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>