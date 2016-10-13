<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品分类管理</title>
  	<script type="text/javascript">
  		function changeRelation(categoryCode,isChecked){
  			var labelCode = $("#labelCode").val();
  			var maxNumber = $("#maxNumber").val();
  			if (maxNumber.length <= 0 ){
  				$(".checkCurrentBtn").each(function(){ 
  			         $(this).click(function(){
  			             if($(this).attr('checked') == "checked"){
  			                 $(this).removeAttr("checked");
  			             } else {
  			                 $(this).attr('checked','checked');
  			             }
  			         });
  				});
  				window.setTimeout(function () {  location.reload(); }, 500);
				jBox.tip("分类最大数量不能为空", 'info');
				return;
			}
  			if (isChecked == 'y') {
        		$.post("${contextPath}/skuLabel/deleteRelationLabelCategory",{labelCode:labelCode,categoryCode:categoryCode},function(result){
  		  			if(result =="SUCCESS"){	
	  		  			jBox.tip("取消关联成功", 'info');
	  		  			window.setTimeout(function () {  location.reload(); }, 500);
  		  			}else{
  		  				jBox.tip("取消关联失败", 'info');
  		  			}
  		          });
			}
  			if (isChecked == 'n') {
  				var listLen = $("#labelCategoryLen").val();
  	  			if (listLen >= maxNumber) {
  	  				$(".checkCurrentBtn").each(function(){ 
  	 			         $(this).click(function(){
  	 			        	 if($(this).attr('checked') == "checked"){
  	  			                 $(this).removeAttr("checked");
  	  			             } else {
  	  			                 $(this).attr('checked','checked');
  	  			             }
  	 			         });
  	 				});
  	 				window.setTimeout(function () {  location.reload(); }, 500);
  	  				jBox.tip("分类数量超过最大数量", 'info');
  					return;
  				}
        		$.post("${contextPath}/skuLabel/addNotRelationLabelCategory",{labelCode:labelCode,categoryCode:categoryCode},function(result){
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
<body id="index">
		<tags:message content="${message}"></tags:message>

        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">分类编码：</label>
	      					<input type="text" id="brandCode" name="code" class="form-control" value="${skuCategoryDto.code}"
	      						 placeholder="分类编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">分类名称：</label>
	      					<input type="text" id="brandName" name="name" class="form-control" value="${skuCategoryDto.name}"  
	      						placeholder="分类名称" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">是否显示：</label>
      						<select name="isShow" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="y" ${skuCategoryDto.isShow =='y'?'selected':''}>显示</option>
					            <option value="n" ${skuCategoryDto.isShow =='n'?'selected':''}>不显示</option>
					        </select>
    					</div>
					    
					    <div class="form-group col-md-4 mB15">
    						<label class="col-label">分类级别：</label>
      						<select name="categoryLevel" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="1" ${skuCategoryDto.categoryLevel =='1'?'selected':''}>一级</option>
					            <option value="2" ${skuCategoryDto.categoryLevel =='2'?'selected':''}>二级</option>
					            <option value="3" ${skuCategoryDto.categoryLevel =='3'?'selected':''}>三级</option>
					        </select>
    					</div>
					    
					    <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">父分类编码：</label>
	      					<input type="text" id="grandcategoryCode" name="grandcategoryCode" class="form-control" value="${skuCategoryDto.grandcategoryCode}"
	      						 placeholder="父分类编码">
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
    						<label class="col-label">关联类型：</label>
      						<select name="isLabelRelation" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="y" ${skuCategoryDto.isLabelRelation =='y'?'selected':''}>已关联</option>
					            <option value="n" ${skuCategoryDto.isLabelRelation =='n'?'selected':''}>未关联</option>
					        </select>
    					</div>
	  					
					    <div class="col-md-4">
	                        <button type="submit" class="btn btn-info" >确认查询</button>
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
                              <th>#</th>
                              <th>序号</th>
							  <th>分类编码</th>
							  <th>分类名称</th>
							  <th>分类图片</th>
							  <th>英文名称</th>
							  <th>父分类编码</th>
							  <th>分类级别</th>
							  <th>是否显示</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="skuCategoryList" varStatus="status">
								<tr>
								   <c:set var="isDoing" value="0"/> 
                                   <td>
                                   	<c:if test="${not empty labelCategoryList}">
                                    	<c:forEach items="${labelCategoryList}" var="labelCategoryList">
	                                    	<c:if test="${labelCategoryList.categoryCode == skuCategoryList.code}">
	                                    		<input checked type="checkbox" name="checkAll" class="checkCurrentBtn" onclick="changeRelation('${skuCategoryList.code}','y');">
	                                    		<c:set var="isDoing" value="1"/> 
	                                    	</c:if>
                                    	</c:forEach>
                                   </c:if>
                                   <c:if test="${isDoing!='1'}">
                                   		<input type="checkbox" name="checkAll" class="checkCurrentBtn" onclick="changeRelation('${skuCategoryList.code}','n');">
                                   </c:if>
                                  </td>
								   <td scope="row">${status.count}</td>
								   <td>${skuCategoryList.code}</td>	
								   <td>${skuCategoryList.name}</td>	
								   <td><a href="${imagePath}${skuCategoryList.categoryLogoUrl}"  class="fancybox" rel="gallery">
								   	<img src="${imagePath}${skuCategoryList.categoryLogoUrl}"
								   		kesrc ="${imagePath}${skuCategoryList.categoryLogoUrl}" width="50" height="50" /></a>
								   </td>
								   <td>${skuCategoryList.englishName}</td>
								   <td>${skuCategoryList.grandcategoryCode}</td>
								   <td>${skuCategoryList.categoryLevel}</td>
								   <td>
								   	 <c:if test="${skuCategoryList.isShow=='y'}">显示</c:if>
								   	 <c:if test="${skuCategoryList.isShow=='n'}">不显示</c:if>
								   </td>
								  </tr>
								 </c:forEach>
								 <input type="hidden" value="${fn:length(labelCategoryList)}" id="labelCategoryLen" />
								 <input type="hidden" name="labelCode" id="labelCode" value="${CURRENT_LABEL_CODE}">
								 <input type="hidden" name="maxNumber" id="maxNumber" value="${maxNumber}">
                                </tbody>
                              </table>
                           </div>
                           <tags:page formId="findForm" url="${contextPath}/skuCategory/selectCheckCategoryList"></tags:page>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
