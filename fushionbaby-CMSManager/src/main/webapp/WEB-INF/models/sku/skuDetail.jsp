<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品详情查看</title>
<script type="text/javascript">
function returnList(){
	
	window.location.href = "${contextPath }/sku/findSkuList";
}

</script>
</head>
<body id="index">

       <div class="container-fluid">
           <div class="row">
		  <div class="col-md-12 content">
		     <div class="panel panel-info">
                <div class="panel-heading">
                   <h3 class="panel-title"><i class="fa fa-cog"></i> 商品详情查看</h3>
                </div>
			 <div class="panel-body">
			     <form class="form-horizontal" id="editForm" >
			     	
			     	<div class="form-group">
						<label class="col-sm-3 control-label">商品编号：</label>
						<div class="col-sm-5">
							<input name="skuNo" id="skuNo" type="text"  readonly="readonly"
								class="form-control" value="${sku.skuNo}"/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品条形码：</label>
						<div class="col-sm-5">
							<input name="barCode" id="barCode" type="text"  readonly="readonly"
								class="form-control" value="${sku.barCode}"/>
						</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品名称：</label>
						<div class="col-sm-5">
							<input name="skuName" id="skuName" type="text" readonly="readonly"
							class="form-control" value="${sku.skuName}"/>
						</div>
					</div>
			     
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品颜色：</label>
						<div class="col-sm-5">
							<input name="color" id="color" type="text" readonly="readonly"
							 class="form-control" value="${sku.color}"/>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">商品规格：</label>
						<div class="col-sm-5">
							<input name="size" id="size" type="text" readonly="readonly"
							class="form-control" value="${sku.size}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label txt-zero" for="sku-quotaNum">限购数量：</label>
						<div class="col-sm-5">
							<input id="sku-quotaNum" class="form-control" name="quotaNum" type="text" min=0
									readonly="readonly"
   									value="${(empty sku.quotaNum)?'0':sku.quotaNum }">
						</div>
						

					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label txt-zero" for="sku-costPrice">成本价：</label>
						<div class="col-sm-5">
							<input id="sku-costPrice" class="form-control" name="costPrice" type="text"
										readonly="readonly"
   										value="${(empty sku.costPrice)?'0':sku.costPrice }" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label" for="sku-retailPrice">零售价：</label>
						<div class="col-sm-5">

							<input id="sku-retailPrice" class="form-control txt-zero" name="retailPrice" type="text"
								readonly="readonly"
   								value="${(empty sku.retailPrice)?'0':sku.retailPrice }"/>
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label" for="sku-currentPrice">现价：</label>

						<div class="col-sm-5">

								<input id="sku-currentPrice" class="form-control txt-zero" name="currentPrice" type="text"
									readonly="readonly"
   									value="${(empty sku.currentPrice)?'0':sku.currentPrice }"/>
						</div>
					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label txt-zero" for="sku-prePrice">预售价：</label>

							<div class="col-sm-5">

								<input id="sku-prePrice" class="form-control" name="prePrice" type="text"
										readonly="readonly"
   										value="${(empty sku.prePrice)?'0':sku.prePrice }"/>

							</div>
					</div>
					
					<div class="form-group">

						<label class="col-sm-3 control-label txt-zero" for="sku-marketPrice">市场价：</label>

							<div class="col-sm-5">

								<input id="sku-marketPrice" class="form-control" name="marketPrice" type="text"
										readonly="readonly"
   										value="${(empty sku.marketPrice)?'0':sku.marketPrice }"/>

							</div>
					</div>
					
					<div class="form-group">

						<label class="col-sm-3 control-label txt-zero" for="sku-aladingPrice">阿拉丁会员价：</label>

							<div class="col-sm-5">

								<input id="sku-aladingPrice" class="form-control" name="aladingPrice" type="text"
										readonly="readonly"
   										value="${(empty sku.aladingPrice)?'0':sku.aladingPrice }"/>

							</div>
					</div>
					

					<div class="form-group">

						<label class="col-sm-3 control-label" for="sku-isVideo">是否有视频：</label>

						<div class="col-sm-5">
													
							<select class="form-control lg-select" id="sku-isVideo" readonly="readonly" name="isVideo">
								<option value="n" selected="selected">否</option>
								<option value="y">是</option>
							</select>

						</div>

					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label" for="sku-videoUrl">视频路径：</label>

							<div class="col-sm-5">

								<input id="sku-videoUrl" class="form-control lg-select" name="videoUrl"  readonly="readonly"
										type="text" value="${sku.videoUrl}" />

							</div>

					</div>

					<div class="form-group">

						<label class="col-sm-3 control-label" for="sku-isGift">是否为赠品：</label>

							<div class="col-sm-5">
													
								<select class="form-control lg-select" id="sku-isGift" name="isGift" readonly="readonly">
									<option value="n" selected="selected">否</option>
									<option value="y">是</option>
								</select>

							</div>
					</div>

				<div class="form-group">

					<label class="col-sm-3 control-label" for="sku-hasGift">是否有赠品：</label>

						<div class="col-sm-5">

							<select  class="form-control lg-select" id="sku-hasGift" name="hasGift" readonly="readonly">
								<option value="n" selected="selected">否</option>
								<option value="y">是</option>
							</select>


						</div>

				</div>

				<div class="form-group">

					<label class="col-sm-3 control-label" for="sku-hasDiscount">是否有优惠券：</label>

						<div class="col-sm-5">

							<select  class="form-control lg-select" id="sku-hasDiscount" name="hasDiscount" readonly="readonly">
								<option value="n" selected="selected">否</option>
								<option value="y">是</option>
							</select>

							
						</div>

				</div>

				<div class="form-group">

					<label class="col-sm-3 control-label" for="sku-showorder">显示顺序：</label>

						<div class="col-sm-5">

							<input id="sku-showorder" class="form-control lg-select txt-zero" name="showorder" type="text"
									readonly="readonly"
   									value="${(empty sku.showorder)?'0':sku.showorder }"/>
						</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="sku-description">商品描述：</label>
					<div class="col-sm-5">
						<textarea id="sku-description" rows="5" cols="200" class="col-sm-5" name="skuDescription"
							readonly="readonly">${sku.skuDescription}</textarea>
						
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