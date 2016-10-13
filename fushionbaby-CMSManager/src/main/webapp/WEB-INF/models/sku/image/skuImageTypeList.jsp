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
        <title>图片类型列表</title>
    </head>
    <script type="text/javascript">
    function  deleteSkuImageType(id){

  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/skuImageType/deleteSkuImageType/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要删除该图片类型吗？", "删除提示",submit);
    }
    
    
	 function updateStatus(id,status){
		 window.location.href = "${contextPath}/skuImageType/updateStatus/"+id+"/"+status+"/"+new Date().getTime();
  	} 
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-cog"></i>图片类型列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">

                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">图片类型编码：</label>
                                    <input type="text" class="form-control" id="code" name="code" value="${code}" placeholder="请输入图片类型编码">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">图片类型名称：</label>
                                    <input type="text"  class="form-control" id="name" name="name" value="${name}" placeholder="请输入图片类型名称">
                                </div>

                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath }/skuImageType/goImageTypeAdd" class="btn btn-info speBtn">新增</a>
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
											<th >图片类型编码</th>
											<th >图片类型名称</th>
											<th >是否多图片</th>
											<th >操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${skuImageTypeList}" var="SkuImageType" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${SkuImageType.code}</td>
											<td>${SkuImageType.name}</td>
										      <td>
										        <c:if test="${SkuImageType.isMultiple eq 'y' }">多图片</c:if>
										        <c:if test="${SkuImageType.isMultiple eq 'n' }">单图片</c:if>
										    </td>
                                            <td>
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteSkuImageType('${SkuImageType.id}');">删除</button>
                                            	<button type="button" class="btn btn-info btn-xs"  onclick="javascript:updateStatus('${SkuImageType.id }','${ SkuImageType.disable}');">
											        <c:if test="${SkuImageType.disable eq 'y' }">禁用</c:if>
											        <c:if test="${SkuImageType.disable eq 'n' }">开启</c:if>
										        </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/skuImageType/skuImageTypeList"></tags:page>
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
