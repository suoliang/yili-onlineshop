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
  	
	  	/** 编辑会员商品*/ 
	  	 function editMemberSku(isMemberSku){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				
	  				var chks = $(".checkAllSku");
	  				var len = chks.length;
	  				if(len==null||len==""||len<=0){
	  					jBox.tip("请至少选中一条记录");
	  					return;
	  				}
	  				var codes=[];
	  				for(i=0;i<len;i++){
	  					if(chks[i].checked){
	  						codes.push(chks[i].value);
	  					}
	  				}
	  				var selectedCodes =codes.join(",");
	  				if(selectedCodes==''){
	  					jBox.tip("请至少选中一条记录");
	  					return;
	  				}
	  				
	  				var url="${contextPath}/sku/extra/editMemberSku?selectedSkuList="+selectedCodes+"&isMemberSku="+isMemberSku;
	  				
					$.post(url,function(msg){
	  					
	  					if(msg!=true){
	  						jBox.tip("操作失败！！", 'error');
		  		    	 	return;
	  					}
	  					jBox.tip("操作成功。", 'info');
 		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
		  		      
	  				});
	  				
	  			}else{
	  				jBox.tip("修改失败", 'info');
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要修改选中的商品设置吗？", "提示",submit);
	  		 
	  	 }
	  	 
	/**批量导入图文详情*/  	 
	function batctProductImage(){
	  		
	  		var url = "iframe:${contextPath}/sku/batchProductImageModel";
	  		$.jBox(url, {
	  		    title: "批量导入产品图文详情", width: 400,height: 200,
	  		    buttons: {'开始上传':true, '关闭': false },
	  		  	submit: function (v, h, f) {
	  		  	if(v){
	            	  var contents = h.find("iframe").contents();
		              var rootPath = contents.find("input[name='rootPath']").val();
		              var purl = "${contextPath}/sku/batchUploadProductImage";
		              $.post(purl, {rootPath:rootPath},function(result){
		            	  if(result.otherError != null && result.otherError != "" ){
		            		  $.jBox.info(result.otherError);
		            		  return;
		            	  }
		            	  showResult(result);
		              });
	              }
	  		  	}
			});
	  	}
	/** 显示结果*/
	  	function showResult(result){
	  		 //obj转json
           var json_data = JSON.stringify(result);
           
           var url = "iframe:${contextPath}/sku/showUploadProductImageResult?jsonStr="+encodeURIComponent(encodeURIComponent(json_data));
			$.jBox(url, {
	  		    title: "批量导入产品图文详情结果", width: 600,height: 400,
	  		    buttons: {'关闭': false }
			});
	  	}
	  	/** 编辑关键字*/ 
	  	function editKeywords(code){
	  		var url ="iframe:${contextPath}/sku/findProKeywords/"+code;
	  		$.jBox(url, {
	  		    title: "关键字编辑", width: 400,height: 400,
	  		    buttons: {'确定':true,'关闭': false },
	  			submit: function (v, h, f) {
		  		  	if(v){
		  		  	    var contents = h.find("iframe").contents();
		  		  		
		  		  		var keywords = contents.find("input[name='keyWords']");
		  		  		var skuKeywords = [];
		  		  		for(var i=0;i< keywords.length;i++){
		  		  			skuKeywords.push(keywords[i].value);
		  		  		}
		  		  		 
		  		  	   var purl = "${contextPath}/sku/editProKeywords";
		  		  	   $.post(purl, {keywords:skuKeywords.join(","),productCode:code},function(result){
		  		  		   if(result==true){
	            		   	   $.jBox.info("编辑关键字操作成功！");
		  		  		   }else{
		  		  			   $.jBox.error("编辑关键字操作失败！");  
		  		  		   }
	            		   return;
		  		  	   });
		  		  		 
		  		  	}
	  			}
			});
	  		
	  	}
	  	
	  	/** 导入关键字*/
	  	function importKeyWordsExcel(){
	  		
	  		var url = "iframe:${contextPath}/sku/initBatchKeyWords";
			$.jBox(url, {
	  		    title: "EXCEL批量导入关键词", width: 400,height: 200,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              
	  		  	}
			});
	  		
	  	}
	  	/** 编辑图文详情页*/
	  	function editProductImage(productCode){
	  		
	  		window.location.href="${contextPath }/sku/extra/goGraphicDetails?productCode="+productCode;
	  	}
	  	/** 导入产品属性页*/
		function batctProductAttr(){
	  		
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
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sku/extra/findSkuList">
				     
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
    						<label class="col-label">是否会员商品：</label>
      						<select name="isMemberSku" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					           	<option value="">所有</option>
					            <c:forEach items="${dic:getDictByType('FLAG')}" var="dict">
					            	<option value="${dict.value }" <c:if test="${dict.value==skuDto.isMemberSku }">selected="selected"</c:if>  >${dict.label }</option>
					            </c:forEach>
					        </select>
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
    					
					    <div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">创建时间：</label>
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
						
	    				
	    			<%-- 	<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">门店编号：</label>
	      					<input type="text" id="storeCode" name="storeCode" class="form-control" value="${skuDto.storeCode}"  
	      						placeholder="门店编号" >
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
	    				
	    				<div class="form-group col-md-4 mB15">
							<label class="col-label">分类：</label>
							<div class="input-group">
								<select name="categoryCode" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
						           	<option value="">所有</option>
						            <c:forEach items="${categoryList}" var="category">
						            	<option value="${category.code }"  >${category.name }</option>
						            </c:forEach>
						        </select>
	    				    </div>
	    				</div>
	    				
                        <button type="submit" class="btn btn-info">确认查询</button>
                        <button type="button" class="btn btn-info" onclick="editMemberSku('y')">设置会员商品</button>
  						<button type="button" class="btn btn-info" onclick="editMemberSku('n')">取消会员商品</button>
  						<button type="button" class="btn btn-info" onclick="importKeyWordsExcel()">导入关键词</button>
  						<button type="button" class="btn btn-info" onclick="batctProductImage()">导入图文详情</button>
   						   
				    
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
                                      <input type="checkbox" name="checkAllBtn" id="checkAllBtn" >
                                  </label>
                              </th>
                           	  <th>序号</th>
							  <th>商品编码</th>
							  <th>条形码</th>
							  <th>产品编号</th>
							  <th>门店号</th>
							  <th>商品名称</th>
							  <th>分类</th>
							  <th>商品状态</th>
							  <th>会员商品</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${skuDtos}" var="tmp" varStatus="status">
								<tr>
								  <th>
                                       <label class="check-label">
                                           <input type="checkbox" class="checkAllSku" name="checkAll" value="${tmp.uniqueCode}">
                                       </label>
                                   </th>
								   <td>${status.count}</td>	
								   <td>${tmp.skuNo}</td>	
								   <td>${tmp.barCode}</td>	
								   <td>${tmp.productCode}</td>
								   <td align="center">${ empty tmp.storeCode ? '-' :  tmp.storeCode}</td>
								   <td>${tmp.skuName}</td>	
								   
								   <td align="center">${empty tmp.categoryName ? '-' : tmp.categoryName}</td>
								   
								   <td align="center">
								   		${dic:getDictLabel(tmp.status, 'SKU_STATUS', '-')}
								   		<input type="hidden" class="theSkuStatus" value="${dic:getDictLabel(tmp.status, 'SKU_STATUS', '-')}">
								   </td>
								   <td align="center"> <c:if test="${tmp.isMemberSku=='y'}">是</c:if>
								   	    <c:if test="${tmp.isMemberSku=='n'}">否</c:if>
								    </td>	
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${tmp.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td> 
								     <a class="btn btn-info btn-xs" href="${contextPath}/sku/skuDetails?uniqueCode=${tmp.uniqueCode }" title="查看详情">	
										商品详情
									 </a>
									 
									 <a class="btn btn-info btn-xs"	href="javascript:editKeywords('${tmp.productCode}')" title="关键字">
									 	关键字
									 </a>
									 <a class="btn btn-info btn-xs"	href="javascript:editProductImage('${tmp.productCode}')" title="图文详情">
									 	图文详情
									 </a>
									
								   </td>
								  </tr>
<!-- 								  <input type="hidden" name="sku" value="{\"skuNo\":\"${tmp.skuNo }\"}" /> -->
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sku/extra/findSkuList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
