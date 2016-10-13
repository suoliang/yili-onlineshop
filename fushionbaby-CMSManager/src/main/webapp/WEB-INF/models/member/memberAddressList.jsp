<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>会员地址列表</title>
  	<script type="text/javascript">
  	
	  	function queryAddress(){
	  		
	  		$("#findForm").submit();
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 用户收货地址列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/memberAddress/findAll">
				     
					    <div class="form-group col-md-4 mB15">
	    					<label for="label"  class="col-label">用户姓名：</label>
	      					<input type="text" id="contactor" name="contactor" value="${contactor}" class="form-control"  placeholder="请输入姓名"/>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="label"  class="col-label">所在省份：</label>
	      					<input type="text" id="province" name="province" value="${province}" class="form-control"  placeholder="请输入省份"/>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="label"  class="col-label">手机号：</label>
	      					<input type="text" id="mobile" name="mobile" value="${mobile}" class="form-control"  placeholder="请输入手机号"/>
	  					</div>
	  					
						<div class="form-group col-md-4 mB15">
                            <label for="d" class="col-label">地址创建开始时间：</label>
                             <div class="input-group">
                                 <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${createTimeFrom}">
                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
                         </div>
                         <div class="form-group col-md-4 mB15">
                             <label for="e" class="col-label">地址创建结束时间：</label>
                             <div class="input-group">
                                 <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${createTimeTo}">
                                 <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>  
                         </div>				    
					   
					    
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="queryAddress()">确认查询</button>
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
								<th>会员姓名</th>
								<th>所在省份</th>
								<th>会员所在市</th>
								<th>会员所在县</th>
								<th>会员详细地址</th>
								<th>创建时间</th>
								<th>会员手机</th>
								<th>会员电话</th>
								<th>会员邮箱</th>
							</tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${memberAddress}" var="list" varStatus="status">
										<tr>

											<td>${status.count }</td>

											<td>${list.contactor}</td>
											
											<td>${list.province}</td>
											
											<td>${list.city}</td>
											
											<td>${list.district}</td>

											<td>${list.address}</td>
											
											<td>${list.showTime}</td>

											<td>${list.mobile}</td>
											
											<td>${list.telephone}</td>
											
											<td>${list.email}</td>
										</tr>
											
										</c:forEach>		
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/memberAddress/findAll"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
