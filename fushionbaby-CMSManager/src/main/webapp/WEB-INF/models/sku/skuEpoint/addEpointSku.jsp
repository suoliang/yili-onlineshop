<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
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
	  		function addEpointSku(uniqueCode){
	  			var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				window.location.href = "${contextPath}/skuEpoint/addEpointSku/"+ uniqueCode + "/" + new Date().getTime();
		  		} 
		  		
	  		}
		  		$.jBox.confirm("你确定要添加该商品吗？", "添加提示",submit);
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
                                    <input type="text" class="form-control" id="skuNo" name="skuNo" value="${skuNo}" placeholder="请输入商品编码">
                                </div>
                                
                                <div class="form-group col-md-3 mB15">
                                    <label for="autocomplete">商品名称：</label>
                                    <input type="text" class="form-control" id="skuName" name="skuName" value="${skuName}" placeholder="请输入商品名称">
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
                                            <th>序号</th>
                                            <th>商品编码</th>
                                            <th>商品条形码</th>
                                            <th>商品名称</th>
                                            <th>商品状态</th>
                                            <th>创建时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${list}" var="sku" varStatus="status">
                                    		 <tr>
	                                           	<td scope="row">${status.count}</td>
	                                            <td>${sku.skuNo}</td>
	                                            <td>${sku.barCode}</td>
	                                            <td>${sku.name}</td>
	                                            <td>
	                                            	<c:if test="${sku.status == '1'}">未上架</c:if>
	                                            	<c:if test="${sku.status == '2'}">已上架</c:if>
	                                            	<c:if test="${sku.status == '3'}">已下架</c:if>
	                                            </td>
	                                            <td>
	                                            	<fmt:formatDate value="${sku.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                            </td>
	                                            <td>
	                                            	<a class="btn btn-primary speBtn"  href="javascript:addEpointSku('${sku.uniqueCode}');"   title="添加积分商品">	
										          		添加积分商品		</a>
	                                            </td>
                                        	</tr>
                                    	</c:forEach>
                                   </tbody>
                                </table>
                            </div>
                            	<tags:page formId="findForm" url="${contextPath}/skuLabel/addNoRelationSku"></tags:page>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>