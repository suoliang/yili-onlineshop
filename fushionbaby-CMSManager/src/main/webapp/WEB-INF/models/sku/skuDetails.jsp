<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>商品详情</title>
    </head>
    <body id="orderDetail">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                	<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
                	<br>
                	<br>
                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品基本信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    
                                    	<li class="list-group-item">
                                            <strong>产品编号：</strong>
                                            <span>${sku.productCode}</span>
                                        </li>
                                    
                                        <li class="list-group-item">
                                            <strong>商品编号：</strong>
                                            <span>${sku.skuNo}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>商品条形码：</strong>
                                            <span>
                                            	<c:out value="${sku.barCode}"></c:out>
                                           </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>商品名称：</strong>
                                            <span>${sku.name}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>商品颜色：</strong>
                                            <span>${sku.color}</span>
                                        </li>
                                         
                                       
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    
                                    	<li class="list-group-item">
                                            <strong>门店编号：</strong>
                                            <span>${sku.storeCode}</span>
                                        </li>
                                    
                                    
                                    	<li class="list-group-item">
                                            <strong>商品描述：</strong>
                                            <span>${sku.description}</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>商品状态：</strong>
                                            <span>${dic:getDictLabel(sku.status, 'SKU_STATUS', '-')}</span>
                                        </li>
                                       	<li class="list-group-item">
                                            <strong>显示顺序：</strong>
                                            <span>${(empty sku.showorder)?'0':sku.showorder }</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>商品规格：</strong>
                                            <span>
                                            	${sku.size}
                                            </span>
                                        </li>
                                    </ul>
                                </div>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>

                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品价格信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>成本价：</strong>
                                            <span>${(empty skuPrice.costPrice)?'0':skuPrice.costPrice }</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>零售价：</strong>
                                            <span>
                                            	${(empty skuPrice.retailPrice)?'0':skuPrice.retailPrice }
                                           </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>现价：</strong>
                                            <span>${(empty skuPrice.currentPrice)?'0':skuPrice.currentPrice }</span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    	<li class="list-group-item">
                                            <strong>阿拉丁会员价：</strong>
                                            <span>${(empty skuPrice.aladingPrice)?'0':skuPrice.aladingPrice }</span>
                                        </li>
                                         <li class="list-group-item">
                                            <strong>预售价：</strong>
                                            <span>${(empty skuPrice.prePrice)?'0':skuPrice.prePrice }</span>
                                        </li>
                                         <li class="list-group-item">
                                            <strong>市场价：</strong>
                                            <span>
                                            	${(empty skuPrice.marketPrice)?'0':skuPrice.marketPrice }
                                            </span>
                                        </li>
                                       
                                        
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品库存信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>商品库存：</strong>
                                            <span>${skuInventory.availableQty}</span>
                                        </li>
                                    </ul>
                                </div>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                   
                   <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品额外信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <strong>是否有视频：</strong>
                                            <span>${(skuExtraInfo.isVideo)=="y"?'是':'否' }</span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>视频路径：</strong>
                                            <span>
                                            	${skuExtraInfo.videoUrl }
                                           </span>
                                        </li>
                                        <li class="list-group-item">
                                            <strong>是否有优惠券：</strong>
                                            <span>
                                            	${(skuExtraInfo.hasDiscount)=="y"?'是':'否' }
                                            </span>
                                        </li>
                                       
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    	 <li class="list-group-item">
                                            <strong>是否为赠品：</strong>
                                            <span>${(skuExtraInfo.isGift)=="y"?'是':'否' }</span>
                                        </li>
                                    	<li class="list-group-item">
                                            <strong>是否有赠品：</strong>
                                            <span>${(skuExtraInfo.hasGift)=="y"?'是':'否' }</span>
                                        </li>
                                         <li class="list-group-item">
                                            <strong>是否是会员商品：</strong>
                                            <span>${(skuExtraInfo.isMemberSku)=="y"?'是':'否' }</span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                      <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品额外信息</h3>
                        </div>
                        <div class="panel-body">
                             <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                           	  <th>序号</th>
							  <th>属性名称</th>
							  <th>属性值</th>
							  <th>显示顺序</th>
							 </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${skuProductAttrList }" var="attr" varStatus="s">
                            		<tr>
                            		<td>${s.count }</td>
                            		<td>${attr.attrName }</td>
                            		<td>${attr.attrValue }</td>
                            		<td>${attr.showOrder }</td>
                            		</tr>
                            	</c:forEach>
                            	<c:if test="${ fn:length(skuProductAttrList)==0}">
                            		<tr>
                            			<td colspan="4">暂无属性</td>
                            		</tr>
                            	</c:if>
                            </tbody>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    

                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

        <!-- javascript -->
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/public.js"></script>
    </body>
</html>
