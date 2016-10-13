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
        <title>广告配置列表</title>
    </head>
    <script type="text/javascript">
    function editAdConfig(id){
    	window.location.href="${contextPath}/adConfig/goEdit/"+id+"/"+new Date().getTime();
    }
    function changeIsUse(id,isUse){
    	window.location.href="${contextPath}/adConfig/changeIsUse/"+id+"/"+isUse+"/"+new Date().getTime();
    }
    function  deleteAdConfig(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/adConfig/deleteAdConfig/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该广告配置吗？", "删除提示",submit);
    }
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
               <%--  <div id="menu">
                <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-flag-o"></i>广告配置列表</h3>
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
                                <a href="${contextPath}/adConfig/goAdd" class="btn btn-info speBtn">新增</a>
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
											<th>广告编码</th>
											<th>广告名称</th>
											<!-- <th>图片宽度</th>
											<th>图片高度</th>
											<th>最多显示数量</th> -->
											<th>显示位置</th>
											<th>是否显示</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${adConfigList}" var="aclist" varStatus="status">
                                    	<tr>
                                    	
                                    	    <td>${status.count }</td>
											<td>${aclist.adCode }</td>
											<td>${aclist.adName }</td>
											<%-- <td>${aclist.pictureWeight }</td>
											<td>${aclist.pictureHeight }</td> 
											<td>${aclist.pictureNumber }</td> --%>
											<td>
												<c:forEach items="${sysSourceList }" var="source">
												   <c:if test="${source.code== aclist.sourceCode}">${source.name}</c:if>
												</c:forEach>
											</td>
											
											<td>
												<c:if test="${aclist.isUse eq 'y'}"><a href="javascript:changeIsUse('${aclist.id }','y');">显示</a></c:if>
												<c:if test="${aclist.isUse eq 'n'}"><a href="javascript:changeIsUse('${aclist.id }','n');">不显示</a></c:if>
											</td>
                                    	
                                            <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:editAdConfig('${aclist.id }');">修改</button>
												<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="javascript:deleteAdConfig('${aclist.id}');">删除</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/adConfig/adConfigList"></tags:page>
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