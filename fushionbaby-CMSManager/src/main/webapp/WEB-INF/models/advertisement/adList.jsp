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
        <title>广告列表</title>
    </head>
    <script type="text/javascript">
    function addAd(id){
    	window.location.href="${contextPath}/ad/goEdit/"+id+"/"+new Date().getTime();
    }
    function  deleteAd(id){

  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/ad/deleteAd/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要删除该广告吗？", "删除提示",submit);
    }
    </script>

    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-flag-o"></i>广告列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">

                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">广告名称：</label>
                                    <input type="text" class="form-control" id="adName" name="adName" value="${adName}" placeholder="请输入广告名称">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">广告编码：</label>
                                    <input type="text" class="form-control" id="adCode" name="adCode" value="${adCode}" placeholder="请输入广告编码">
                                </div>

                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="${contextPath}/ad/goAdd" class="btn btn-info speBtn">新增</a>
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
											<th >广告名称</th>
											<th >广告编码</th>
											<th >广告图片</th>
											<th >广告URL</th>
											<th >开始时间</th>
											<th >结束时间</th>
											<th >显示顺序</th>
											<th >是否显示</th>
											<th >备注</th>
											<th >操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${adList}" var="adlist" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
											<td>${adlist.adName}</td>
											<td>${adlist.adCode}</td>
											<td>
												<a href="${adPath }${adlist.picturePath}" class="fancybox" rel="gallery">
												<img alt="" src="${adPath }${adlist.picturePath}" kesrc ="${adPath }${adlist.picturePath}" width="50px" height="50px">
												</a>
											<%-- <a class="link-nowrap showImg" href="${adPath}${adlist.picturePath}" target="_blank" title="${adPath}${adlist.picturePath}" >${adlist.picturePath}</a> --%>
											</td>
											<td><a class="link-nowrap" href="javascript:void(0);" title="${adlist.url }">${adlist.url }</a></td>
											<td><fmt:formatDate value="${adlist.beginTime}" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${adlist.endTime}" pattern="yyyy-MM-dd"/></td>
											<td>${adlist.showOrder}</td>
											<td>
												<c:if test="${adlist.isShow=='y' }">显示</c:if>
												<c:if test="${adlist.isShow =='n' }">不显示</c:if>
											</td>
											<td>${adlist.remark}</td>
                                            <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:addAd('${adlist.id }');">修改</button>
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteAd('${adlist.id}');">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/ad/adList"></tags:page>
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
