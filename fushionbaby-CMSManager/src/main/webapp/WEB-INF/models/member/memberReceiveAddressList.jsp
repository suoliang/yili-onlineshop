<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>用户收货地址明细</title>
</head>

<body id="index">
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">

				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 会员收货地址列表</h3>
                   </div>
                   <div class="panel-body">
                       <!-- table -->
		              <div class="table-responsive">
		                <table class="table table-bordered table-hover" id="roleTable">
		                  
		                     <tr>
								 <th>序号</th>
								 <th>收货人姓名</th>
								 <th>收货人电话</th>
								 <th>收货人地址</th>
								 <th>是否默认地址</th>
							</tr>
							                   
							<tbody>
							    <c:forEach items="${memberAddressDtoList}" var="item" varStatus="status">
									<tr>
									    <td>${status.count}</td>	
										<td>${item.contactor}</td>
										<td>${item.mobile}</td>		
										<td>${item.province}${item.city}${item.district}${item.address}</td>
										<td>
											<c:choose> 
												<c:when test="${item.id eq defaultAddressId}">是</c:when>
												<c:otherwise>否</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</c:forEach>
		                   </tbody>
		                 </table>
		              </div>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
