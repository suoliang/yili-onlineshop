<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>分类品牌列表</title>
  	<script type="text/javascript">
	 	function delRelationCategoryBrand(brandCode, categoryCode){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				$.post("${contextPath}/skuCategory/deleteCategoryBrandRelation",{categoryCode: categoryCode, brandCode: brandCode},function(result){
	  		  			if(result =="SUCCESS"){	
		  		  			jBox.tip("取消关联成功", 'info');
		  		  			window.setTimeout(function () {  location.reload(); }, 500);
	  		  			}else{
	  		  				jBox.tip("取消关联失败", 'info');
	  		  			}
	  		          });
	  			}
	  			return true;
	  		} 
	  		$.jBox.confirm("你确定要取消关联该品牌吗？", "删除提示",submit);
	  	}
	  	
	  	function showAddSkuBrand(categoryCode){
	  		var url = "iframe:${contextPath}/skuBrand/selectCheckSkuBrandList?categoryCode=" + categoryCode;
			$.jBox(url, {
	  		    title: "分类关联品牌", width: 980,height: 600,
	  		    buttons: {'关闭': true},
	  		  	submit: function (v, h, f) {
	  		  		if (v) {
	  		  			location.reload();
					}
	  		  	}
			});
	  	}
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i>分类关联品牌列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
	                    <button type="button" class="btn btn-info" onclick="showAddSkuBrand('${CURRENT_CATEGORY_CODE}')" >新增关联品牌</button>
				    	
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
							  <th>品牌编码</th>
							  <th>品牌名称</th>
							  <th>是否显示</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:if test="${not empty page.result}">	
	                              <c:forEach items="${page.result}" var="brand" varStatus="status">
									<tr>
									   <td scope="row">${status.count}</td>
									   <td>${brand.brandCode}</td>	
									   <td>${brand.brandName}</td>	
									   <td>
								   			<c:if test="${brand.isShow=='y'}">显示</c:if>
							   	 			<c:if test="${brand.isShow=='n'}">不显示</c:if>
									   </td>	
									   <td><fmt:formatDate value="${brand.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									   <td>
										 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delRelationCategoryBrand('${brand.brandCode}', '${CURRENT_CATEGORY_CODE}')" title="取消关联">	
										 	取消关联
										 </a>
										 <input type="hidden" id="labelCode" value="${CURRENT_CATEGORY_CODE}">
									   </td>	
									  </tr>
									 </c:forEach>
								</c:if> 
                                </tbody>
                              </table>
                           </div>
                           <div class="form-group mL0 mR0">
						      	    <button class="btn btn-primary" type="button" onclick="window.history.go(-1)">返回</button>
						   </div>
                           <tags:page formId="findForm" url="${contextPath}/skuCategory/brandListByCategory"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
</body>
</html>
