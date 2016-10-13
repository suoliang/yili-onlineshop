<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>短信统计</title>
</head>
<body id="index">
		<tags:message content="${message}"></tags:message>

        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 短信统计列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					    <div class="form-group col-md-4 mB15">
                            <label for="a">创建开始时间：</label>
                            <div class="input-group">
                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                        	</div>
                        </div>
                        <div class="form-group col-md-4 mB15">
                            <label for="a">创建结束时间：</label>
                            <div class="input-group">
                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                        	</div>
                        </div>
	  					
					    <div class="col-md-4">
	                        <button type="submit" class="btn btn-info" >确认查询</button>
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
                              <th>
                                <label for="checkAllBtn" class="check-label">
                                   <input type="checkbox" name="checkAllBtn" id="checkAllBtn">
                                </label>
                              </th>
                              <th>序号</th>
							  <th>短信余额</th>
							  <th>当天短信使用量</th>
							  <th>创建时间</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="sSmsList" varStatus="status">
								<tr>
								   <td>
									 <label for="checkAllBtn" class="check-label">
                                      <input type="checkbox" name="checkAllBtn" class="checkCurrentBtn">
                                     </label>
								   </td>	
								   <td scope="row">${status.count}</td>
								   <td>${sSmsList.leftAmount}</td>	
								   <td>${sSmsList.smsNumber}</td>	
								   <td><fmt:formatDate value="${sSmsList.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							  </c:forEach>
                            </tbody>
                        </table>
                           </div>
                           <tags:page formId="findForm" url="${contextPath}/statisticSms/staSmsList"></tags:page>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
