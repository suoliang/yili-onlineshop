<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
  	<script type="text/javascript">
	  
	  	function edit(id){
	  		
	  		window.location.href = "${contextPath}/sku/skuProductDetail/"+id;
	  	}
	  	function delProduct(id){
	  		
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/sku/delProduct/"+id;
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该产品吗？", "删除提示",submit);

	  	}
	  	
	  	function queryProduct(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	function disableInfo(id,disable){
	  		
	  		var info = "";
	  		if(disable == 'y'){
	  			info = "禁用";
	  		}else{
	  			info = "启用";
	  			
	  		}
	  		
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				var url =  "${contextPath}/sku/disableProduct/"+id;
	  				
	  				$.post(url,{disable:disable,datatime:new Date().getTime()},function(msg){
	  					if(msg!="SUCCESS"){
	  						jBox.tip("修改状态操作失败！！", 'error');
		  		    	 	return;
	  					}
	  					jBox.tip("修改状态操作成功。", 'info');
 		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
	  				});
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要"+info+"该产品吗？", "提示",submit);
	  		
	  	}
	  	
	  	
	  	function batchExcel(){
	  		var url = "iframe:${contextPath}/sku/batchProductModel";
			$.jBox(url, {
	  		    title: "使用EXCEL批量导入产品信息", width: 400,height: 200,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	  		  	}
			});
	  	}
	  	
	
	  	
	 
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
	  	function showResult(result){
	  		 //obj转json
           var json_data = JSON.stringify(result);
           
           var url = "iframe:${contextPath}/sku/showUploadProductImageResult?jsonStr="+encodeURIComponent(encodeURIComponent(json_data));
			$.jBox(url, {
	  		    title: "批量导入产品图文详情结果", width: 600,height: 400,
	  		    buttons: {'关闭': false }
			});
	  	}
	  	
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
	  	
	  	function importKeyWordsExcel(){
	  		var url = "iframe:${contextPath}/sku/initBatchKeyWords";
			$.jBox(url, {
	  		    title: "EXCEL批量导入产品关键词", width: 400,height: 200,
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
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 ">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 产品列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sku/skuProductList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">产品编码：</label>
	      					<input type="text" id="code" name="code" class="form-control" value="${productDto.code}"
	      						 placeholder="产品编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">产品名称：</label>
	      					<input type="text" id="name" name="name" class="form-control" value="${productDto.name}"  
	      						placeholder="产品名称" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">使用类型：</label>
      						<select name="disable" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="">所有</option>
					            <c:forEach items="${dic:getDictByType('DISABLE')}" var="dict">
									<option value="${dict.value }" <c:if test="${dict.value== productDto.disable}">selected="selected"</c:if> >${dict.label }</option>
					            </c:forEach>
					        </select>
					        
    					</div>
    					
					    <div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">创建开始：</label>
	    					<div class="input-group">
								<input class="form-control form_datetime" readonly name="createTimeFrom"
								 	 type="text" value="${productDto.createTimeFrom}">
								<div class="timeS input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>
						</div>
						
						<div class="form-group col-md-4 mB15">
							<label class="col-label">创建结束：</label>
							<div class="input-group">
								<input class="form-control form_datetime"  readonly name="createTimeTo" 
									 type="text" value="${productDto.createTimeTo}">
								<div class="timeE input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
	    				    </div>
                        </div>	    				
	    				<div class="form-group col-md-4 mB15">
							<label  class="col-label">扩展字段</label>
	      					<input type="text"  class="form-control" readonly value="" placeholder="扩展字段"  />
	    				</div>
	    				
					    
					  
	                        <button type="button" class="btn btn-info" onclick="queryProduct()">确认查询</button>
	                        <a href="${contextPath }/sku/productAddPage" class="btn btn-info" >新增产品</a>
	                        <button type="button" class="btn btn-info" onclick="batchExcel()">批量导入产品</button>
	                        <button type="button" class="btn btn-info" onclick="importKeyWordsExcel()">导入产品关键词</button>
	                        <button type="button" class="btn btn-info" onclick="downLoadExcel('批量导入产品模板\.xls')">下载批量导入产品模板</button>
	                        <button type="button" class="btn btn-info" onclick="batctProductImage()">批量导入产品图文详情</button>
	                        <button type="button" class="btn btn-info" onclick="batctProductAttr()">批量导入产品属性</button>
	                        <button type="button" class="btn btn-info" onclick="downLoadExcel('批量导入产品属性模板\.xls')">下载批量导入产品属性模板</button>
				    
				    	
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
<!--                               <th> -->
<!--                                 <label for="checkAllBtn" class="check-label"> -->
<!--                                    <input type="checkbox" name="checkAllBtn" id="checkAllBtn"> -->
<!--                                 </label> -->
<!--                               </th> -->
                              <th>序号</th>
							  <th>产品编码</th>
							  <th>产品名称</th>
							  <th>产品分类</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>是否启用</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${result}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
<!-- 								  <td> -->
<!-- 									<label for="checkAllBtn" class="check-label"> -->
<!--                                       <input type="checkbox" name="checkAllBtn" id="checkAllBtn"> -->
<!--                                      </label> -->
<!-- 								   </td>	 -->
								   <td>${tmp.code}</td>	
								   <td>${tmp.name}</td>	
								   <td>${tmp.categoryName}</td>	
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${tmp.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
										${dic:getDictLabel(tmp.disable, 'DISABLE', '-')}
								   </td>
								   <td>													
									 <a class="btn btn-default btn-xs edit-role" href="${contextPath}/sku/productDetail/${tmp.id}/edit/${page.currentPage }/${page.limit }/${page.total }" title="修改">	
										修改
									 </a>
									 
									 <a class="btn btn-info btn-xs" href="${contextPath}/sku/productDetail/${tmp.id}/detail/${page.currentPage }/${page.limit }/${page.total }"title="查看详情">	
										查看详情
									 </a>
									 <a class="btn btn-warning btn-xs edit-role" href="javascript:disableInfo(${tmp.id },'${tmp.disable }');">
									 	 <c:if test="${tmp.disable=='n'}">启用</c:if>
								   	 	 <c:if test="${tmp.disable=='y'}">禁用</c:if>
									 </a>
									 <c:if test="${empty tmp.skuList || fn:length(tmp.skuList) < 1}">	
									 	<a class="btn btn-danger btn-xs delete-role"  href="javascript:delProduct(${tmp.id})" 
									 		  title="删除">	
									          	删除
									 	</a>
									 </c:if>
									 <a class="btn btn-info btn-xs"	href="javascript:editKeywords('${tmp.code}')" title="关键字">
									 	关键字
									  </a>
									
									 <a class="btn btn-info btn-xs"	href="javascript:editImage('${tmp.code}')" title="图文详情">
									 	图文详情
									  </a>
									
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sku/skuProductList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
