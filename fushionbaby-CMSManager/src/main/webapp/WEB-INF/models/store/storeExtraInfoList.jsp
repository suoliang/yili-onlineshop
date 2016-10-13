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
        <title>门店额外信息列表</title>
        <script type="text/javascript">
         	function updateStoreExtraInfo(storeCode){
         		window.location.href = "${contextPath}/storeExtraInfo/editStoreExtraInfo/"+storeCode+"/"+new Date().getTime();
         	}
    	
        </script>
        
    </head>
    <body id="" class="Cog">
    
      <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>门店额外信息列表</h3>
                        </div>
                           <div class="panel-body">
	                          <form class="form-inline page" id="findForm" method="post">
	                                
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">店主姓名：</label>
	                                    <input type="text" class="form-control" id="linkMan" name="linkMan" value="${storeExtraInfoDto.linkMan}" placeholder="请输入店主姓名">
	                                </div>
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">手机号码：</label>
	                                    <input type="text" class="form-control" id="telephone" name="telephone" value="${storeExtraInfoDto.telephone}" placeholder="请输入手机号码">
	                                </div>
				                               
	                                <div class="form-group col-md-8 mB15">
	                                   <button type="submit" class="btn btn-success speBtn">查 询</button>
	                                </div>
	                                <div class="clearfix"></div>
	                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
									<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
									<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
									<input type="hidden" name="total" value="${page.total}"/>
	                            </form>
		                        <div class="panel-body">
		                            <!-- table -->
		                            <div class="table-responsive">
		                                <table class="table table-bordered ta ble-hover" id="roleTable">
		                                    <thead>
		                                        <tr>
				                                    <th>序号</th>
													<th>门店编号</th>
													<th>门店名称</th>
													<th>店主姓名</th>
													<th>身份证号</th>
													<th>门店固定电话</th>
													<th>手机号码</th>
													<th>邮编</th>
													<th>更新时间 </th>
													<th>门店地址</th>
													<th>操作</th>
		                                        </tr>
		                                    </thead>
		                                    <tbody>
		                                    <c:forEach items="${list}" var="list" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${list.storeNumber}</td>
													<td>${list.storeName}</td>
													<td>${list.linkMan}</td>
													<td>${list.identityCardNo}</td>
													<td>${list.telephone}</td>	
													<td>${list.mobile}</td>	
													<td>${list.zip}</td>
													<td><fmt:formatDate value="${list.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													<td>${list.address }</td>
		                                            <td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="updateStoreExtraInfo('${list.storeCode}');">修改</button>
		                                            </td>
		                                          
		                                        </tr>
		                                    </c:forEach>
		                                    </tbody>
		                                </table>
		                            </div>
		                              <!-- 分页 -->
		                             <tags:page formId="findForm" url="${contextPath}/storeExtraInfo/storeExtraInfoList"></tags:page>
		                            <!-- 分页 end -->
		                            
		                        </div>
                        <!-- /.panel-body -->
                           </div>
                        </div>
                <!-- /.content -->
                   </div>
               </div>
        <!-- /.container-fluid -->
           </div>
     
    </body>
</html>