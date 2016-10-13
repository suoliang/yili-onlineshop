<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
  	<script type="text/javascript">
	  	 function query(){
	   		$('#findForm').submit();
	   		
	   	}
  	
  		function edit(id){
	  		
	  		window.location.href = "${contextPath}/sku/skuProductDetail/"+id;
	  	}
	  	
	  	function delSku(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				var url ="${contextPath}/sku/"+id+"/skuDelete"; 
	  				$.post(url,function(msg){
	  					
	  					if(msg!="SUCCESS"){
	  						jBox.tip("删除商品操作失败！！", 'error');
		  		    	 	return;
	  					}
	  					jBox.tip("删除商品操作成功。", 'info');
 		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
		  		      
	  				});
	  			}else{
	  				jBox.tip("删除失败", 'info');
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该商品吗？", "删除提示",submit);

	  	}
	  	
	  	function modifySkuStatus(id,status){
	  		
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
			  		var url = "${contextPath}/sku/modifySkuStatus";
			  		$.ajax({
			  		   type: "get",
			  		   url: url,
			  		   data: "id="+id+"&status="+status,
			  		   success: function(msg){
			  			   if(msg.responseCode == "500"){
			  				 	jBox.tip(msg.msg, 'error');
			  		    	 	return;
			  			   }
			  			   
			  		       if(msg == false){
			  		    	 	jBox.tip("修改商品状态操作失败！！", 'error');
			  		    	 	return;
			  		       }
			  		       jBox.tip("修改商品状态操作成功。", 'info');
			  		       window.setTimeout(function () {  location.reload(); }, 1500);
			  		   }
			  		});
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要修改商品状态吗？", "操作提示",submit);
	  	}
	  	
	  	function importPriceExcel(){
	  		var url = "iframe:${contextPath}/sku/batchSkuPrice";
			$.jBox(url, {
	  		    title: "EXCEL批量导入商品价格", width: 600,height: 400,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              
	  		  	}
			});
	  	}
	  	
	  	function importImages(){
	  		var url = "iframe:${contextPath}/sku/initBatchImages";
			$.jBox(url, {
	  		    title: "批量导入商品图片", width: 400,height: 200,
	  		    buttons: {'开始上传':true, '关闭': false },
	  		  	submit: function (v, h, f) {
	  		  	if(v){
	            	  var contents = h.find("iframe").contents();
	            	  var purl = "${contextPath}/sku/batchImages";
		              var rootPath = contents.find("input[name='rootPath']").val();
		              $.post(purl, {rootPath:rootPath},function(result){
		            	  if(result.otherError != null && result.otherError != "" ){
		            		  alert(result.otherError);
		            		  return;
		            	  }
		            	  showResult(result);
		              });
	              }
	  		  	}
			});
	  	}
	  		  	
	  	function validImages(){
	  		var url = "iframe:${contextPath}/batchValid/initValidImages";
			$.jBox(url, {
	  		    title: "批量导入商品图片预检测", width: 400,height: 200,
	  		    buttons: {'开始检测':true, '关闭': false },
	  		  	submit: function (v, h, f) {
	  		  	if(v){
	            	  var contents = h.find("iframe").contents();
	            	  var purl = "${contextPath}/batchValid/validImg";
		              var rootPath = contents.find("input[name='rootPath']").val();
		              $.post(purl, {rootPath:rootPath},function(result){
		            	  if(result.otherError != null && result.otherError != "" ){
		            		  alert(result.otherError);
		            		  return;
		            	  }
		            	  showValidResult(result);
		              });
	              }
	  		  	}
			});
	  	}
	  	
	  	function showResult(result){
	  		 //obj转json
            var json_data = JSON.stringify(result);
            
            var url = "iframe:${contextPath}/sku/showUploadImageResult?jsonStr="+encodeURIComponent(encodeURIComponent(json_data));
			$.jBox(url, {
	  		    title: "批量导入商品图片结果", width: 600,height: 400,
	  		    buttons: {'关闭': false }
			});
	  	}
	  	
	  	function showValidResult(result){
	  		 //obj转json
           var json_data = JSON.stringify(result);
           
           var url = "iframe:${contextPath}/batchValid/showUploadImageValidResult?jsonStr="+encodeURIComponent(encodeURIComponent(json_data));
			$.jBox(url, {
	  		    title: "批量导入商品图片检测结果", width: 600,height: 400,
	  		    buttons: {'关闭': false }
			});
	  	}

	  	
	  //批量更新
		function updateAllSkuStatus(status) {
			var checkedLen = $("input[name='checkAll']:checked").length;
			if(checkedLen == 0) {
				jBox.tip("请先选中后，操作!");
				return false;
			}
			
			var result = confirm("确定提交吗?");
			if(result == false) {
				return ;
			}
			
			var tempSkuIds=[];
			
			$("input[name='checkAll']").each(function(index) {
				if($(this).is(":checked")) {
					var tempSkuId = $("#roleTable tbody tr").eq(index).find('.theSkuId').val();
					var tempstat = $("#roleTable tbody tr").eq(index).find('.theSkuStatus').val();
					if(status == 1 && "可编辑" != tempstat) {
						jBox.tip("存在不合法状态商品,操作失败!", 'error');
						result = false;
						return false;
					}
					
					if(status == 2 && "已上架" != tempstat) {
						jBox.tip("存在不合法状态商品,操作失败!", 'error');
						result = false;
						return false;
					}
					
					if(status == 3 && "已下架" != tempstat) {
						jBox.tip("存在不合法状态商品,操作失败!", 'error');
						result = false;
						return false;
					}
					
					tempSkuIds.push(tempSkuId);
				}
			});				
			
			if(result == false) {
				return ;
			}
			$.post('modifySkusStatus',
					{tempSkuIds : tempSkuIds.join(","),status : status, time : new Date().getTime()},
					
					function(data) {
						if (data.result == "success") {
							alert(data.msg);
							query();// 重新查询
	  						
						} else {
							alert(data.msg);
						}
			});
		}

	  	function updateSolr(){
	  		var url = "${contextPath}/sku/updateSolr";
	  		$.ajax({
		  		   type: "post",
		  		   url: url,
		  		   success: function(msg){
	  		    	 	alert(msg);
		  		   }
		  		});
	  	}
	  	/** 导出商品*/
	  	function exportSkuInfoExcel(){
			$("#findForm").attr("action","${contextPath}/sku/exportSkuInfoExcel");
			$("#findForm").submit();
			$("#findForm").attr("action","${contextPath}/sku/findSkuList");
	  	}
		/** 导入商品*/
		function importSku(){
			
			var url = "iframe:${contextPath}/sku/batchProductModel";
			$.jBox(url, {
	  		    title: "使用EXCEL批量导入产品信息", width: 600,height: 400,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	  		  	}
			});
			
			
		}
		
		
  	function batchProductAttr(){
	  		
	  		var url = "iframe:${contextPath}/sku/batchProductAttrModel";
	  		$.jBox(url, {
	  		    title: "批量导入产品属性", width: 400,height: 200,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	  		  	}
			});
	  		
	  	}
  	</script>
	
</head>
<body>
		<c:set var="now" value="<%=new java.util.Date().getTime()%>" />
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sku/findSkuList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="skuNo" class="col-label">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control" value="${skuDto.skuNo}"
	      						 placeholder="商品编码">
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="barCode" class="col-label">商品条形码：</label>
	      					<input type="text" id="barCode" name="barCode" class="form-control" value="${skuDto.barCode}"
	      						 placeholder="商品条形码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">商品名称：</label>
	      					<input type="text" id="name" name="skuName" class="form-control" value="${skuDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">产品编码：</label>
	      					<input type="text" id="productCode" name="productCode" class="form-control" value="${skuDto.productCode}"  
	      						placeholder="产品编码" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">商品状态：</label>
      						<select name="status" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					           	<option value="">所有</option>
					            <c:forEach items="${dic:getDictByType('SKU_STATUS')}" var="dict">
					            	<option value="${dict.value }" <c:if test="${dict.value==skuDto.status }">selected="selected"</c:if>  >${dict.label }</option>
					            </c:forEach>
					        </select>
    					</div>
    					
    				<%-- 	<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">门店编码：</label>
	      					<input type="text" id="storeCode" name="storeCode" class="form-control" value="${skuDto.storeCode}"  
	      						placeholder="产品编码" >
	  					</div> --%>
	  					   <div class="form-group col-md-4 mB15">
                               <label for="a" class="col-label">门店名称：</label>
                                 <select id="storeCode" name="storeCode" class="form-control lg-select" >
                                   
                                        <option value="">阿拉丁商城</option>
                                     <c:forEach items="${storeList }" var="store">
                                        <option value="${store.code}" <c:if test="${store.code == skuDto.storeCode }"> selected="selected" </c:if>  > ${store.name}</option>
									  </c:forEach>  
                                 </select>
                          </div>
	  					
	  					
	  					
	  					
	  					
    					
					    <div class="form-group col-md-6 mB15">
					    	
	    					<label class="col-label">创建起止时间：</label>
	    					<div class="input-group">
								<input class="timeS form-control form_datetime" readonly name="createTimeFrom"
								 	 type="text" value="${skuDto.createTimeFrom}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div> -
							<div class="input-group">
								<input class="timeE form-control form_datetime"  readonly name="createTimeTo" 
									 type="text" value="${skuDto.createTimeTo}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>
						</div>
						
	    				
	    				
	    				 <div class="form-group col-md-6 mB15">
					    	
	    					<label class="col-label">修改起止时间：</label>
	    					<div class="input-group">
								<input class="timeS form-control form_datetime" readonly name="updateTimeFrom"
								 	 type="text" value="${skuDto.updateTimeFrom}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
								
							</div> -
							<div class="input-group">
								
								<input class="timeE form-control form_datetime"  readonly name="updateTimeTo" 
									 type="text" value="${skuDto.updateTimeTo}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>
						</div>
						
	    				
	    				
	    				
	                        <button type="submit" class="btn btn-info">确认查询</button>
	                        
	                        <button type="button" class="btn btn-info" onclick="importSku()">导入商品</button>
	                        <button type="button" class="btn btn-info" onclick="downLoadExcel('sku-model.xls')">下载导入商品模板</button>
	                        <button type="button" class="btn btn-info" onclick="importPriceExcel()">导入商品价格</button>
	                        <button type="button" class="btn btn-info" onclick="downLoadExcel('price-model.xls')">下载导入商品价格模板</button>
	                        <button type="button" class="btn btn-info" onclick="validImages()">导入商品图片预检测</button>
	                        <button type="button" class="btn btn-info" onclick="importImages()">导入商品图片</button>
	                        <button type="button" class="btn btn-info" onclick="exportSkuInfoExcel()">导出商品信息</button>
	                        <button type="button" class="btn btn-info" onclick="updateAllSkuStatus(1)">批量上架</button>
	                        <button type="button" class="btn btn-info" onclick="updateAllSkuStatus(2)">批量下架</button>
	                        <button type="button" class="btn btn-info" onclick="updateAllSkuStatus(3)">批量进入编辑状态</button>
	                        <button type="button" class="btn btn-info" onclick="batchProductAttr()">导入产品属性</button>
	                        <button type="button" class="btn btn-info" onclick="downLoadExcel('sku-attr.xls')">下载导入产品属性模板</button>
	                      <!--   <button type="button" class="btn btn-info" onclick="updateSolr()">更新索引库</button> -->

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
                           	  <th>产品编号</th>
							  <th>商品编码</th>
							  <th>条形码</th>
							  <th>门店号</th>
							  <th>商品名称</th>
							  <th>分类</th>
							  <th>品牌</th>
							  <th>状态</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuList}" var="tmp" varStatus="status">
								<tr>
								  <th>
                                       <label class="check-label">
                                           <input type="checkbox" name="checkAll">
                                       </label>
                                   </th>
								   <td>${status.count}</td>	
								   <td>${tmp.productCode }</td>
								   <td>${tmp.skuNo}<input type="hidden" class="theSkuId" value="${tmp.id}"></td>	
								   <td>${tmp.barCode}</td>	
								   <td align="center">${ empty tmp.storeCode ? '-' :  tmp.storeCode}</td>
								   <td>${tmp.skuName}</td>	
								   <td align="center">${empty tmp.categoryName ? '-' : tmp.categoryName}</td>
								   <td align="center">${empty tmp.brandName ? '-' : tmp.brandName}</td>
								   <td>
								   		${dic:getDictLabel(tmp.status, 'SKU_STATUS', '-')}
								   		<input type="hidden" class="theSkuStatus" value="${dic:getDictLabel(tmp.status, 'SKU_STATUS', '-')}">
								   </td>	
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${tmp.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
								   <c:if test="${tmp.status =='1' }">
								   	 <a class="btn btn-default btn-xs edit-role" href="${contextPath}/sku/${tmp.id}/0/skuDetailPage?currentPage=${page.currentPage }&limit=${page.limit }&total=${page.total }&time=${now}" title="编辑">	
										编辑
									 </a>	
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delSku(${tmp.id})"   title="删除">	
									          删除
									 </a>	
								   </c:if>
								   											
									 <a class="btn btn-info btn-xs" href="${contextPath}/sku/skuDetails?uniqueCode=${tmp.uniqueCode }" title="查看详情">	
										商品详情
									 </a>
									 <a class="btn btn-success btn-xs edit-role" href="${contextPath}/skuImage/findSkuImage/${tmp.uniqueCode}" title="图片" >
									 	图片
									 </a>
									 
									 <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  
									 	 onclick="modifySkuStatus(${tmp.id},'${tmp.status }')">
	
									 	<c:if test="${tmp.status==1}">上架</c:if>
									 	<c:if test="${tmp.status==3}">进入编辑状态</c:if>
									 	<c:if test="${tmp.status==2}">下架</c:if>
									 </a>
									 
								   </td>	
								  </tr>
<!-- 								  <input type="hidden" name="sku" value="{\"skuNo\":\"${tmp.skuNo }\"}" /> -->
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sku/findSkuList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
