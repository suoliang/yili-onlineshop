<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>分类关联品牌新增页</title>
        <script type="text/javascript">
	        function changeRelation(brandCode,isChecked){
	        	var categoryCode = $("#categoryCode").val();
	        	if (isChecked == 'y') {
	        		$.post("${contextPath}/skuCategory/deleteCategoryBrandRelation",{categoryCode: categoryCode, brandCode: brandCode},function(result){
	  		  			if(result =="SUCCESS"){	
		  		  			jBox.tip("取消关联成功", 'info');
		  		  			window.setTimeout(function () {  location.reload(); }, 500);
	  		  			}else{
	  		  				jBox.tip("取消关联失败", 'info');
	  		  			}
	  		          });
				}
	        	if (isChecked == 'n') {
	        		$.post("${contextPath}/skuCategory/addNotRelationCategoryBrand",{categoryCode: categoryCode, brandCode: brandCode},function(result){
		  		  		if(result =="SUCCESS"){	
		  		  			jBox.tip("关联成功", 'info');
		  		  			window.setTimeout(function () {  location.reload(); }, 500);
	  		  			}else{
	  		  				jBox.tip("关联失败", 'info');
	  		  			}
	  		          });
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
                                    <label for="a">品牌编码：</label>
                                    <input type="text" class="form-control" id="brandCode" name="brandCode" value="${skuBrandDto.brandCode}" placeholder="请输入品牌编码">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">品牌名称：</label>
                                    <input type="text" class="form-control" id="brandName" name="brandName" value="${skuBrandDto.brandName}" placeholder="请输入品牌名称">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="e">是否显示：</label>
                                    <select name="isShow" class="form-control lg-select">
                                        <option value="" selected="selected">所有</option>
							            <option value="y" ${skuBrandDto.isShow =='y'?'selected':''}>显示</option>
							            <option value="n" ${skuBrandDto.isShow =='n'?'selected':''}>不显示</option>
                                    </select>
                                </div>
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
                                            <th>#</th>
                                            <th>序号</th>
											<th>品牌编码</th>
											<th>品牌名称</th>
											<th>品牌前缀</th>
											<th>创建时间</th>
											<th>是否显示</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${page.result}" var="brand" varStatus="status">
                                    		 <tr>
                                    			<c:set var="isDoing" value="0"/> 
                                    		 	<td>
                                    		 		<c:if test="${not empty categoryList}">
	                                    		 		<c:forEach items="${categoryList}" var="categoryList">
		                                    		 		<c:if test="${categoryList.brandCode == brand.brandCode}">
		                                    		 			<input checked type="checkbox" name="checkSku" id="checkSku" value="${brand.brandCode}" onclick="changeRelation('${brand.brandCode}','y');">
		                                    		 			<c:set var="isDoing" value="1"/> 
		                                    		 		</c:if>
	                                    		 		</c:forEach>
                                    		 		</c:if>
                                    		 		<c:if test="${isDoing!='1'}">
                                    		 			<input type="checkbox" name="checkSku" id="checkSku" value="${brand.brandCode}" onclick="changeRelation('${brand.brandCode}','n');">
                                    		 		</c:if>
                                    		 	</td>
	                                           	<td scope="row">${status.count}</td>
	                                            <td>${brand.brandCode}</td>
	                                            <td>${brand.brandName}</td>
	                                            <td>${brand.brandPrefix}</td>
	                                            <td><fmt:formatDate value="${brand.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>
	                                            	<c:if test="${brand.isShow=='y'}">显示</c:if>
								   	 				<c:if test="${brand.isShow=='n'}">不显示</c:if>
	                                            	<input type="hidden" name="categoryCode" id="categoryCode" value="${CURRENT_CATEGORY_CODE}">
	                                            </td>
                                        	</tr>
                                    	</c:forEach>
                                   </tbody>
                                </table>
                            </div>
                            	<tags:page formId="findForm" url="${contextPath}/skuBrand/skuBrandList"></tags:page>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>