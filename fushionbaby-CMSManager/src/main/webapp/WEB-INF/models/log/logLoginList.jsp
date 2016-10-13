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
        <title>登录日志列表</title>
        <script type="text/javascript">
	    
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 登录日志列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post" action="${contextPath}/log/logLoginList">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">登录名：</label>
                                    <input type="text" class="form-control" id="loginName" name="loginName" value="${logCmsLoginDto.loginName}" placeholder="请输入用户名称">
                                </div>
                                 
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">登录状态：</label>
                                    <select name="loginStatus" id="loginStatus" class="form-control lg-select">
                                        <option value="">所有</option>
                                        <option value="y" <c:if test="${'y' == logCmsLoginDto.loginStatus }"> selected="selected" </c:if> > 成功</option>
                                       	<option value="n" <c:if test="${'n' == logCmsLoginDto.loginStatus }"> selected="selected" </c:if> > 失败</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="d" class="col-label">登录开始时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeS form-control form_datetime" readonly name="loginTimeFrom" value="${logCmsLoginDto.loginTimeFrom}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">登录结束时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeE form-control form_datetime" readonly name="loginTimeTo" value="${logCmsLoginDto.loginTimeTo}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                    
                                </div>
                                <div class="col-md-8">
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
                                            <th>登录名</th>
                                            <th>门店名</th>
                                            <th>登录状态</th>
                                            <th>登录时间</th>
                                            <th>ip地址</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list }" var="list" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${list.loginName}</td>
                                            <td>${list.storeName}</td>
                                             <td>
                                            	<c:choose>
                                            		<c:when test="${list.loginStatus =='y'}">成功</c:when> 
												  	<c:otherwise>失败</c:otherwise> 
                                             	</c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${list.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>${list.ipAddress}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/log/logLoginList"></tags:page>
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