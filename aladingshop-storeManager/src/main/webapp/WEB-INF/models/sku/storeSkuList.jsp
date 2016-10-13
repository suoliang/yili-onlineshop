<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
  	<script type="text/javascript">
  	

  	var setting = {
  			view: {
  				dblClickExpand: true
  			},
  			data: {
  				simpleData: {
  					enable: true
  				}
  			},
  			callback: {
  				onClick: onClick
  			}
  		};

  		var zNodes =${allCategoryJson};

  		function onClick(e, treeId, treeNode) {
  			
  			if(treeNode.isParent == true){
  				return false;
  			}
  			var zTree = $.fn.zTree.getZTreeObj("categoryTree");
  			zTree.checkNode(treeNode, !treeNode.checked, null, true);
  			var cityObj = $("#categorySel");
  			cityObj.attr("value", treeNode.name);
  			$("#categoryCode").val(treeNode.code);
  			hideMenu();
  			return false;
  		}
  		function showMenu() {
  			var cityObj = $("#categorySel");
  			var cityOffset = $("#categorySel").offset();
  			$("#menuContent").css({left:15 + "px", top:35 + "px"}).slideDown("fast");

  			$("body").bind("mousedown", onBodyDown);
  		}
  		function hideMenu() {
  			$("#menuContent").fadeOut("fast");
  			$("body").unbind("mousedown", onBodyDown);
  		}
  		function onBodyDown(event) {
  			if (!(event.target.id == "menuBtn" || event.target.id == "categorySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
  				hideMenu();
  			}     
  		}

  	 	$(document).ready(function() {
  	        // 初始化树
  	        $.fn.zTree.init($("#categoryTree"), setting, zNodes);	

  	 	});
  	
  	
  	
  	
	  	 function query(){
	   		$('#findForm').submit();
	   		
	   	}
  	
  		function edit(id){
	  		
	  		window.location.href = "${contextPath}/sku/skuProductDetail/"+id;
	  	}
	  	
	  	function delSku(skuCode){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				var url ="${contextPath}/store/sku/deleteStoreSku?skuCode=" + skuCode; 
	  				$.post(url,function(msg){
	  					
	  					if(msg==false){
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
			  		var url = "${contextPath}/store/sku/modifySkuStatus";
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

	function addSku(){
		
		var url = "iframe:${contextPath}/store/sku/queryNoRelationSku";
		$.jBox(url, {
  		    title: "添加关联商品", width: 980,height: 700,
  		    buttons: { '确认添加' : true, '关闭': false},
  		    submit: function (v, h, f) {
              if(v){
            	  var contents = h.find("iframe").contents();
	              var url= "${contextPath}/store/sku/addSkuToStore";
	              var skuCodes = "";
	              
	              contents.find('input[name="checkAll"]:checked').each(function(){ 
	            	  skuCodes += $(this).val() + ","; 
	            	 }); 
	              console.log(skuCodes);
	              if(skuCodes == "")
	              {
	            	  var submit2 =  function(v,h,f){
	      	  			if(v=="ok"){
	      	  				addSku();
	      	  				return true;
	      	  			}
	      	  		  }
	            	  $.jBox.confirm("当前没有选中任何商品，是否重新选取？", "操作提示",submit2);
	            	  return;
	              }
	              
	              skuCodes = skuCodes.substring(0,skuCodes.length - 1);
	              $.post(url, {skuCodes:skuCodes},function(result){
	            	  if(result == "SUCCESS"){
	            		  	jBox.tip("添加关联商品成功", 'info');
	  		  				
		  		  			// 1.5秒后完成操作
		  		  			window.setTimeout(function () {  location.reload(); }, 1500);
		                }else{
		                	jBox.tip("添加关联商品失败", 'info');
		                }
	              });
              }
  		  	}
		});
	}
	  	
	function skuInnerEdit(){
		
		window.location.href = "${contextPath}/store/sku/skuInnerEdit";
		
	}
	
	
	/** 导入商品*/
	function importSku(){
		
		var url = "iframe:${contextPath}/store/sku/importSku";
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
	
	
	function putaway(status){
		
		var selectedItem = $("input[name='checkAll']:checked");
	
		var len = selectedItem.length;
		if(len==null||len==""||len<=0){
			$.jBox.alert("请至少选中一件商品","提示");
			return;
		}
		var codes = [];
		for(var i = 0 ; i < len;i++){
			codes.push(selectedItem[i].value);
		} 
		
		
		$.post('modifySkusStatus',
				{codes : codes.join(","),status : status, time : new Date().getTime()},
				
				function(data) {
					if (data.result == "success") {
						alert(data.msg);
						query();// 重新查询
  						
					} else {
						alert(data.msg);
					}
		});
		
		
	}
	
	function moveSku(uniqueCode){
		var submit =  function(v,h,f){
  			if(v=="ok"){	
				$.post('modifySkuStatus',
						{code : uniqueCode,status : 9, time : new Date().getTime()},
						
						function(data) {
							if (data==true) {
								alert("删除成功");
								query();// 重新查询
		  						
							} else {
								alert("删除失败");
							}
				});
		
  			}
		}
	  		$.jBox.confirm("你确定要删除该件商品吗？", "操作提示",submit);
	}
	
	

  	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/store/sku/findList">
				     
				     		<input type="hidden" name="storeCode" value="${skuDto.storeCode }" />
				      <div class="form-group col-md-4 mB15">
	    					<label for="storeCode" class="col-label">门店编码：</label>
	      					<input type="text" id="storeNumber" name="storeNumber" class="form-control" disabled="disabled" value="${skuDto.storeNumber}"
	      						 placeholder="门店编码" />
	      				
	  					</div>
				     
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">商品名称：</label>
	      					<input type="text" id="name" name="skuName" class="form-control" value="${skuDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">商品状态：</label>
      						<select name="status" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					           	<option value="">所有</option>
					            <c:forEach items="${dic:getDictByType('SKU_STATUS')}" var="dict">
					            	<option value="${dict.value }" <c:if test="${dict.value==skuDto.status }">selected="selected"</c:if>  >
					            		<c:choose>
					            			<c:when test="${dict.value=='1' }">初始化</c:when>
					            			<c:otherwise>${dict.label }</c:otherwise>
					            		</c:choose> 
					            	</option>
					            </c:forEach>
					        </select>
    					</div> 
    					
<%--    					    <c:if test="${not empty categorys}">  --%>
<!--                             <div class="form-group col-md-4 mB15"> -->
<!--                                 <label class="col-label">分类：</label> -->
<!--                                 <select name="categoryCode" class="form-control lg-select" data-placeholder="Choose a Category"  tabindex="1"> -->
<!--                                 	<option value="">所有</option> -->
<%--                                 	<c:forEach items="${categorys}" var="category"> --%>
<%--                                 		<option value="${category.code}" <c:if test="${storeSkuDto.categoryCode == category.code}">selected="selected"</c:if> >${category.name}</option> --%>
<%--                                 	</c:forEach> --%>
<!--                                 </select> -->
<!--                             </div> -->
<%--                          </c:if> --%>
 							<div class="form-group col-md-4 mB15">
								 <label for="categorySel" class=" control-label">商品分类：</label>
								<input id="categorySel" class="form-control" type="text" readonly name="categoryName" value="${skuDto.categoryName }" onclick="showMenu();" />
								<div id="menuContent" class="menuContent">
									<ul id="categoryTree" class="ztree"></ul>
								</div>
							</div>
							<input id="categoryCode" name="categoryCode" type="hidden" value="${skuDto.categoryCode }" />

    					
					    <div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">创建起始时间：</label>
	    					<div class="input-group">
								<input class="form-control form_datetime" readonly name="createTimeFrom"
								 	 type="text" value="${skuDto.createTimeFrom}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>-
							<div class="input-group">
								<input class="form-control form_datetime"  readonly name="createTimeTo" 
									 type="text" value="${skuDto.createTimeTo}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
	    				    </div>
						</div>
						
                        <button type="submit" class="btn btn-info">确认查询</button>
                        
<!--                         <button type="button" class="btn btn-info" onclick="importSku()">导入商品</button> -->
<!--                         <button type="button" class="btn btn-info" onclick="downLoadExcel('sku-models.xls')">下载导入商品模板</button> -->
                        
                        <button type="button" class="btn btn-info" onclick="putaway(2)">上架</button>
                        
                        <button type="button" class="btn btn-info" onclick="putaway(3)">下架</button>
                        
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
							  <th>商品名称</th>
							  <th>商品状态</th>
							  <th>分类名称</th>
							  <th>价格</th>
							  <th>库存</th>
							  <th>限购</th>
							  <th>排序</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${storeSkuDtoList}" var="tmp" varStatus="status">
								<tr>
								  <th>
                                       <label class="check-label">
                                           <input type="checkbox" name="checkAll" value="${tmp.uniqueCode }" />
                                       </label>
                                   </th>
								   <td>${status.count}</td>	
								   <td>${tmp.skuName}</td>	
								   <td>
								   	<c:if test="${ tmp.status==2 }">
								   		已上架
								  	</c:if>
								  	<c:if test="${ tmp.status==3 }">
								   		已下架
								  	</c:if>
								  	<c:if test="${ tmp.status==1}">
								  		初始化
								  	</c:if>
								   </td>	
								   <td>${empty tmp.categoryName ? '-' : tmp.categoryName }</td>
								   <td>${tmp.currentPrice }</td>
								   <td>${tmp.availableQty }</td>
								   <td>${tmp.quotaNum }</td>
								    <td>${tmp.showorder }</td>
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${tmp.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
								   	<a class="btn btn-default btn-xs edit-role" href="${contextPath}/store/sku/skuEditPage?skuCode=${tmp.uniqueCode }&currentPage=${page.currentPage }&limit=${page.limit }&total=${page.total }" title="编辑">	
										编辑
									 </a>
									 <a class="btn btn-default btn-xs " href="javascript:moveSku('${tmp.uniqueCode }')"  title="删除">	
										删除
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
