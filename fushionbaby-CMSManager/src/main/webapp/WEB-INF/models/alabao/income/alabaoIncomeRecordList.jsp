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
        <title>如意消费卡收益记录列表</title>
    </head>
    
   
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
                            <h3 class="panel-title"><i class="fa fa-user"></i> 如意宝收益记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                              
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">账户名：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
		                            <label for="a" class="col-label">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a" class="col-label">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">合计总收益：</label>
                                    <input type="text" class="form-control" id="totalIncomeMoney" name="totalIncomeMoney" value="${totalIncomeMoney}" readonly="readonly">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                  <button type="submit" class="btn btn-success speBtn">查 询</button>
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
                                            <th>如意宝账户名</th>
                                            <th>收益前金额</th>
											<th>收益额</th>
                                            <th>收益后金额</th>
											<th>创建时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
                                            
											<td>${ list.account}</td>
											<td>${ list.bfIncomeMoney}</td>
											
											<td>${ list.incomeMoney}</td>
											<td>${ list.afIncomeMoney}</td>
											
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoIncomeRecord/alabaoIncomeRecordList"></tags:page>
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