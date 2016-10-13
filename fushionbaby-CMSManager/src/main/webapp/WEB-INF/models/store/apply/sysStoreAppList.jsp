<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商家加盟申请列表</title>
  	<script type="text/javascript">
  	function query(){
  		$('#findForm').submit();
  	}

  	function updateIsDeal(id){
  		window.location.href = "${contextPath }/sysStoreApp/updateIsDeal/"+ id +"/"+new Date().getTime();
  	}
  	
	function EditApplyById(id){
		var url = "iframe:${contextPath}/sysStoreApp/updateById/"+id+"/"+new Date().getTime();
		$.jBox(url, {
		    title: "处理开店申请", width: 800,height: 350,
		    buttons: { '关闭': false },
	    });
	}
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i>开店申请列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sysStoreApp/sysStoreAppList">

							<div class="form-group col-md-4 mB15">
								<label for="a" class="col-label">商户名：</label> 
								<input type="text" class="form-control" id="name" name="name" value="${sysmgrStoreApplyDto.name}">
							</div>
							<div class="form-group col-md-4 mB15">
								<label for="a" class="col-label">电话号：</label> 
								<input type="text" class="form-control" id="phone" name="phone" value="${sysmgrStoreApplyDto.phone}">
							</div>
							<div class="form-group col-md-4 mB15">
								<label for="a" class="col-label">城市：</label> 
								<input type="text" class="form-control" id="city" name="city" value="${sysmgrStoreApplyDto.city}">
							</div>
							
							<div class="form-group col-md-4 mB15">
	    						<label class="col-label">是否受理：</label>
	      						<select name="isDeal" class="form-control lg-select" >
						           	<option value="">所有</option>
						            	<option value="y" <c:if test="${sysmgrStoreApplyDto.isDeal == 'y'}">selected="selected"</c:if>  >是</option>
						            	<option value="n" <c:if test="${sysmgrStoreApplyDto.isDeal == 'n'}">selected="selected"</c:if>  >否</option>
						        </select>
    						</div>
							<div class="form-group col-md-4 mB15">
								<label for="a" class="col-label">创建开始时间：</label>
								<div class="input-group">
									<input type="text" name="createTimeFrom" value="${sysmgrStoreApplyDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
									<div class="input-group-addon form_datetime_addon">
										<i class="fa fa-times"></i>
									</div>
								</div>
							</div>
							<div class="form-group col-md-4 mB15">
								<label for="a" class="col-label">创建结束时间：</label>
								<div class="input-group">
									<input type="text" name="createTimeTo" value="${sysmgrStoreApplyDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
									<div class="input-group-addon form_datetime_addon">
										<i class="fa fa-times"></i>
									</div>
								</div>
								<button type="button" class="btn btn-info" onclick="query()">查询</button>
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
                              		<th>姓名</th>
									<th>电话号</th>
									<th>城市</th>
									<th>申请时间</th>
									<!-- <th>申请渠道</th> -->
									<th>是否受理</th>
									<th>受理人</th>
									<th>受理时间</th>
									<th>地址</th>
									<th>备注</th>
									<th>开店意向</th>
									<th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${list}" var="list" varStatus="status">
								<tr>
								  	<td>${status.count }</td>
									<td>${list.name}</td>
									<td>${list.phone}</td>
									<td>${list.city}</td>
									<td><fmt:formatDate value="${list.applyTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<%-- <td><c:out value="${sourceArray[list.sourceCode]}"></c:out></td> --%>
									<td>
										<c:if test="${list.isDeal == 'y'}">是</c:if>
										<c:if test="${list.isDeal == 'n'}">否</c:if>
									</td>
									<td>${list.dealName}</td>
									<td><fmt:formatDate value="${list.dealTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td>${list.address}</td>
									<td>${list.memo}</td>
									<td>
									    <c:if test="${list.isDeal == 'n'}">待处理</c:if>
									    <c:if test="${list.isDeal == 'y'}">
										    <c:if test="${list.intention=='1'}">测试数据</c:if>
											<c:if test="${list.intention=='2'}">有意向（考虑中）</c:if>
											<c:if test="${list.intention=='3'}">已开店</c:if>
											<c:if test="${list.intention=='4'}">废弃数据</c:if>
									    </c:if>
									    
										
									</td>
									
									<td>
											<%-- <a class="btn btn-success btn-xs delete-role" href="javascript:void(0)" onclick="updateIsDeal('${list.id }')" >标记已处理</a> --%>
											
											<a class="btn btn-success btn-xs delete-role" href="javascript:void(0)" onclick="EditApplyById('${list.id }','')" >开店申请处理</a>
									<%-- 	<c:if test="${list.isDeal == 'n'}">
										</c:if> --%>
									</td>
								</tr>
							  </c:forEach>
                            </tbody>
                         </table>
                      </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sysStoreApp/sysStoreAppList"></tags:page>
                            
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
