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
                                            <strong>商品编号：</strong>
                                            <span>${sku.skuNo}</span>
                                        </li>
                             
                                        <li class="list-group-item">

                                            <strong>商品名称：</strong>
                                            <span>${sku.name}</span>
                                        </li>
                                          <li class="list-group-item">
                                            <strong>商品库存：</strong>
                                            <span>${skuInventory.availableQty}</span>
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
                   
                      
                    
                  
                <!-- /.content -->

            </div>
        </div>
        <!-- /.container-fluid -->

    </body>
</html>
