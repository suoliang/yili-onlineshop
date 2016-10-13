<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>分类图片列表</title>
    </head>
    <script type="text/javascript">
    function  deleteSkuCategoryImage(id,code){

  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/skuCategory/deleteSkuCategoryImage/"+id+"/"+code+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要删除该分类图片吗？", "删除提示",submit);
    }
    
    function updateSortOrder(sortOrder,id,type){
    	var code=${categoryCode};
    	window.location.href = "${contextPath}/skuCategory/updateSortOrder/"+sortOrder+"/"+id+"/"+type+"/"+code+"/"+new Date().getTime();
    }
    
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>分类图片列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">

                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">图片类型编码：</label>
                                    <input type="text" class="form-control" id="imageTypeCode" name="imageTypeCode" value="${imageTypeCode}" placeholder="请输入图片类型编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">商品分类编码：</label>
                                    <input type="text" disabled="disabled" class="form-control" id="categoryCode" name="categoryCode" value="${categoryCode}" placeholder="请输入商品分类编码">
                                </div>

                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/skuCategory/goCategoryImageAdd/${categoryCode }" class="btn btn-info speBtn">新增</a>
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
                                       		<th>序号</th>
											<th >商品分类编码</th>
											<th >商品分类名称</th>
											<th >图片类型名称</th>
											<th >图片类型编码</th>
											<th >图片路径</th>
											<th >创建时间</th>
											<th >更新时间</th>
											<th >排列顺序</th>
											<th >创建者名称</th>
											<th >更新者名称</th>
											<th >操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${SkuCategoryImageList}" var="SkuCategoryImage" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${SkuCategoryImage.categoryCode}</td>
											<td>${categoryName }</td>
											<td>${SkuCategoryImage.imageTypeName}</td>
											<td>${SkuCategoryImage.imageTypeCode}</td>
											<td>
												<a href="${adPath }${SkuCategoryImage.imgUrl}" class="fancybox" rel="gallery">
												<img alt="" src="${adPath }${SkuCategoryImage.imgUrl}" kesrc ="${adPath }${SkuCategoryImage.imgUrl}" width="50px" height="50px">
											   </a>
											</td>
											<td><fmt:formatDate value="${SkuCategoryImage.createTime}" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${SkuCategoryImage.updateTime}" pattern="yyyy-MM-dd"/></td>
											<td>&nbsp&nbsp
											    <a href="javascript:updateSortOrder('${SkuCategoryImage.sortOrder }','${SkuCategoryImage.id }','down')">
											       <i class="fa fa-arrow-circle-o-down"></i>
											    </a>
											    &nbsp&nbsp${SkuCategoryImage.sortOrder}&nbsp&nbsp
											    <a onclick="javascript:updateSortOrder('${SkuCategoryImage.sortOrder }','${SkuCategoryImage.id }','up')">
											      <i class="fa fa-arrow-circle-o-up"></i>
											    </a>
											</td>
											<td>${SkuCategoryImage.createUser}</td>
											<td>${SkuCategoryImage.updateUser}</td>
                                            <td>
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteSkuCategoryImage('${SkuCategoryImage.id}','${SkuCategoryImage.categoryCode }');">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/skuCategory/skuCategoryImageList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

    </body>
</html>
