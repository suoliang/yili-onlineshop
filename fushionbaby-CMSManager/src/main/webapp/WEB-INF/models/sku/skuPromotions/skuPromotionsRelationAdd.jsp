<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>活动关联商品新增页</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="renderer" content="webkit">
        <meta name="description" content="阿拉丁后台管理系统">
        <meta name="keywords" content="阿拉丁后台管理系统">
        <script type="text/javascript">
	        function checkAll(o){
	   		 var skus = $("input[name='checkSku']");
	   		 for(var i=0;i< skus.length;i++){
	   			skus[i].checked=o.checked;
	   		 }
	   	}
        </script>
    </head>
    <body class="backwhite">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-3 mB15">
                                    <label for="a">商品编码：</label>
                                    <input type="text" class="form-control" id="skuNo" name="skuNo" value="${SkuDto.skuNo}" placeholder="请输入商品编码">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="a">商品条形码：</label>
                                    <input type="text" class="form-control" id="barCode" name="barCode" value="${SkuDto.barCode}" placeholder="请输入商品条形码">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">商品名称：</label>
                                    <input type="text" class="form-control" id="skuName" name="skuName" value="${SkuDto.skuName}" placeholder="请输入商品名称">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="e">商品状态：</label>
                                    <select name="status" class="form-control lg-select">
                                        <option value="">所有</option>
                                        <option value="1" <c:if test="${SkuDto.status == '1'}">selected="selected"</c:if> >未上架</option>
                                        <option value="2" <c:if test="${SkuDto.status == '2'}">selected="selected"</c:if> >已上架</option>
                                        <option value="3" <c:if test="${SkuDto.status == '3'}">selected="selected"</c:if> >已下架</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">产品编码：</label>
                                    <input type="text" class="form-control" id="productCode" name="productCode" value="${SkuDto.productCode}" placeholder="请输入产品编码">
                                </div>
                                <c:if test="${not empty categorys}"> 
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">二级分类：</label>
                                    <select name="grandCategoryCode" class="form-control lg-select">
                                    	<option value="">所有</option>
                                    	<c:forEach items="${categorys}" var="category">
                                    		<option value="${category.code}" <c:if test="${SkuDto.grandCategoryCode == category.code}">selected="selected"</c:if> >${category.name}</option>
                                    	</c:forEach>
                                    </select>
                                </div>
                                </c:if>
                                <div class="form-group col-md-3 mB15">
			                        <button type="submit" class="btn btn-info" >确认查询</button>
				    			</div>
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
                                            <th>
                                                <label for="checkAllBtn" class="check-label">
                                                    <input type="checkbox" name="checkAllBtn" id="checkAllBtn" onchange="checkAll(this)">
                                                </label>
                                            </th>
                                            <th>序号</th>
                                            <th>商品编码</th>
                                            <th>商品条形码</th>
                                            <th>商品名称</th>
                                            <th>产品编码</th>
                                            <th>商品状态</th>
                                            <th>创建时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="sku" varStatus="status">
                                    		 <tr>
                                    		 	<td><input type="checkbox" name="checkSku" id="checkSku" value="${sku.uniqueCode}"></td>
	                                           	<td scope="row">${status.count}</td>
	                                            <td>${sku.skuNo}</td>
	                                            <td>${sku.barCode}</td>
	                                            <td>${sku.name}</td>
	                                            <td>${sku.productCode}</td>
	                                            <td>${dic:getDictLabel(sku.status, 'SKU_STATUS', '-')}</td>
	                                            <td>
	                                            	<fmt:formatDate value="${sku.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                            	<input type="hidden" name="pmCode" id="pmCode" value="${CURRENT_PROMOTIONS_CODE}">
	                                            </td>
                                        	</tr>
                                    	</c:forEach>
                                   </tbody>
                                </table>
                            </div>
                            	<tags:page formId="findForm" url="${contextPath}/skuPromotions/addNoRelationSku"></tags:page>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>