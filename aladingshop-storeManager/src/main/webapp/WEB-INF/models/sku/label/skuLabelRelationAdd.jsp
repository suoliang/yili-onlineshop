<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>标签关联商品新增页</title>
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
        </script>
    </head>
    <body class="backwhite">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                            
                                
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">商品名称：</label>
                                    <input type="text" class="form-control" id="skuName" name="skuName" value="${skuDto.skuName}" placeholder="请输入商品名称">
                                </div>
                                <div class="form-group col-md-3 mB15">
                                    <label for="e">商品状态：</label>
                                    <select name="status" class="form-control lg-select">
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
                                
                              	<div class="form-group col-md-4 mB15">
									 <label for="categorySel" class=" control-label">商品分类：</label>
									<input id="categorySel" class="form-control" type="text" readonly name="categoryName" value="${skuDto.categoryName }" onclick="showMenu();" />
									<div id="menuContent" class="menuContent">
										<ul id="categoryTree" class="ztree"></ul>
									</div>
								</div>
								<input id="categoryCode" name="categoryCode" type="hidden" value="${skuDto.categoryCode }" />
                                
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
                                            <th>商品名称</th>
                                            <th>商品状态</th>
                                            <th>创建时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${skuList}" var="sku" varStatus="status">
                                    		 <tr>
                                    		 	<td><input type="checkbox" name="checkSku" id="checkSku" value="${sku.uniqueCode}"></td>
	                                           	<td scope="row">${status.count}</td>
	                                            <td>${sku.name}</td>
	                                            
	                                            <td>
	                                            	<c:if test="${sku.status == '1'}">初始化</c:if>
	                                            	<c:if test="${sku.status == '2'}">已上架</c:if>
	                                            	<c:if test="${sku.status == '3'}">已下架</c:if>
	                                            </td>
	                                            <td>
	                                            	<fmt:formatDate value="${sku.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                            </td>
                                        	</tr>
                                    	</c:forEach>
                                   </tbody>
                                </table>
                            </div>
                            	<tags:page formId="findForm" url="${contextPath}/store/skuLabel/addNoRelationSku"></tags:page>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>