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
        <title>如意消费卡转出列表</title>
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意消费卡转出列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">账户名：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出账户类型：</label>
                                     <select name="rollOffAccountType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <option value="">全部</option>
								            <option value="1"  <c:if test="${rollOffAccountType=='1' }">selected</c:if> >如意宝</option>
								            <option value="2"  <c:if test="${rollOffAccountType=='2' }">selected</c:if> >银联</option>
								     </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">流水号：</label>
                                    <input type="text" class="form-control" id="serialNum" name="serialNum" value="${serialNum}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">批处理号：</label>
                                    <input type="text" class="form-control" id="batchNum" name="batchNum" value="${batchNum}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转账是否成功：</label>
                                     <select name="isSuccess" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <option value="">全部</option>
								            <option value="y"  <c:if test="${isSuccess=='y' }">selected</c:if> >成功</option>
								            <option value="n"  <c:if test="${isSuccess=='n' }">selected</c:if> >失败</option>
								     </select>
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
                                            
											<th>转移金额</th>
											<th>转出账户类型</th>
											<th>备注</th>
											<th>流水号</th>
											<th>批处理号</th>
											<th>是否成功</th>
											<th>创建时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
                                            <td>${ list.account}</td>
                                            
											<td>${ list.transferMoney}</td>
											<td>
												<c:if test="${list.rollOffAccountType == '1'}">如意宝</c:if>
												<c:if test="${list.rollOffAccountType == '2'}">银联</c:if>
											</td>
											<td>${ list.memo}</td>
											<td>${ list.serialNum}</td>
											<td>${ list.batchNum}</td>
											<td>${ list.isSuccess}</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoRollOffRecord/alabaoRollOffRecordList"></tags:page>
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