<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品品牌管理</title>
  	<script type="text/javascript">
	  	$(document).ready(function() {
	  		var isShowAddSuc = $("#addRemark").val();
	  		var isShowModifySuc = $("#modifyRemark").val();
	  		if(isShowAddSuc == '0'){
	  			jBox.tip("添加成功", 'info');
	  		}
	  		
	  		if(isShowModifySuc == '0'){
	  			jBox.tip("修改成功", 'info');
	  		}
	  	});
	  	
	  	function delBrand(id){
	  		var submit =  function(v,h,f){
	  			if(v == "ok"){
	  				$.post("${contextPath }/skuBrand/deleteById",{brandId:id },function(result){
	  		  			if(result =="SUCCESS"){	
	  		  			jBox.tip("删除成功", 'info');
	  		  				
	  		  			// 3秒后完成操作
	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
	  		  			}else{
	  		  				jBox.tip("删除失败", 'info');
	  		  			}
	  		  			
	  		          });	
	  			}
	 
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该品牌吗？", "删除提示",submit);
	  	}
	  	
		function showAddLabel(){
	  		var url = "iframe:${contextPath }/skuBrand/addBrandPage";
			$.jBox(url, {
	  		    title: "品牌新增页面", width: 800,height: 600,
	  		    buttons: { '关闭': false }
			});
	  	}
		
		function showModifyLabel(brandId){
	  		var url = "iframe:${contextPath }/skuBrand/modifyBrandPage?bid="+ brandId;
			$.jBox(url, {
	  		    title: "品牌修改页面", width: 800,height: 600,
	  		    buttons: { '关闭': false }
			});
	  	}
	 	function importSkuBrand(){
	  		var url = "iframe:${contextPath}/skuBrand/batchSkuBrand";
			$.jBox(url, {
	  		    title: "EXCEL批量导入商品品牌牌", width: 400,height: 200,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ; }
	               	}
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品品牌列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">品牌编码：</label>
	      					<input type="text" id="brandCode" name="brandCode" class="form-control" value="${skuBrandDto.brandCode}"
	      						 placeholder="请输入品牌编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">品牌名称：</label>
	      					<input type="text" id="brandName" name="brandName" class="form-control" value="${skuBrandDto.brandName}"  
	      						placeholder="请输入品牌名称" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">是否显示：</label>
      						<select name="isShow" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="y" ${skuBrandDto.isShow =='y'?'selected':''}>显示</option>
					            <option value="n" ${skuBrandDto.isShow =='n'?'selected':''}>不显示</option>
					        </select>
    					</div>
					    
					    
	                        <button type="submit" class="btn btn-info" >确认查询</button>
	                        <button type="button" class="btn btn-primary" onclick="showAddLabel()">新增品牌</button>
	                        <button type="button" class="btn btn-warning" onclick="importSkuBrand()">批量导入品牌表格</button>
	                        <button type="button" class="btn btn-success" onclick="downLoadExcel('批量导入品牌表格模板\.xls')">下载批量导入品牌表格模板</button>
				    
				    	
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
                              <th>
                                <label for="checkAllBtn" class="check-label">
                                   <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                </label>
                              </th>
                              <th>序号</th>
							  <th>品牌编码</th>
							  <th>品牌名称</th>
							  <th>品牌前缀</th>
							  <th>创建时间</th>
							  <th>是否显示</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="skuBrandList" varStatus="status">
								<tr>
								  <td>
									<label for="checkAllBtn" class="check-label">
                                      <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                     </label>
								   </td>	
								   <td scope="row">${status.count}</td>
								   <td>${skuBrandList.brandCode}</td>	
								   <td>${skuBrandList.brandName}</td>	
								   <td>${skuBrandList.brandPrefix}</td>
								   <td><fmt:formatDate value="${skuBrandList.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								   <td>
								   	 <c:if test="${skuBrandList.isShow=='y'}">显示</c:if>
								   	 <c:if test="${skuBrandList.isShow=='n'}">不显示</c:if>
								   </td>
								   <td>													
									 <a class="btn btn-default btn-xs edit-role" href="javascript:showModifyLabel('${skuBrandList.id}');" title="修改">	
										修改
									 </a>	
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delBrand(${skuBrandList.id})"   title="删除">	
									          删除
									 </a>
									 <input type="hidden" id="addRemark" value="${ADD_REMARK}">
									 <input type="hidden" id="modifyRemark" value="${MODIFY_REMARK}">
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
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
