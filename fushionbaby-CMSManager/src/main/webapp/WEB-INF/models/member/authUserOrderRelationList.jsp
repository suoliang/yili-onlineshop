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
        <title>会员订单列表</title>
        <script type="text/javascript">
	     
	        function showMemberAddress(memberId){
	        	location.href=contextPath+"/memberAddress/memberReceiveAddressList?memberId="+memberId;
	        }
	        
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 会员订单列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">用户名称：</label>
                                    <input type="text" class="form-control" id="userName" name="userName" value="${authUserOrderRelationDto.userName}" placeholder="请输入用户名称">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">订单号码：</label>
                                    <input type="text" class="form-control" id="orderCode" name="orderCode" value="${authUserOrderRelationDto.orderCode}" placeholder="请输入订单号码">
                                </div>    
                                <div class="col-md-8">
	                                <button type="submit" class="btn btn-info speBtn">查 询</button>
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
                                            <th>用户名</th>
                                            <th>订单号</th>
                                            <th>订单创建时间</th>
                                            <th>订单更新时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${authUserOrderRelationList}" var="authUserOrderRelation" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${authUserOrderRelation.userName}</td>
                                            <td>${authUserOrderRelation.orderCode}</td>
                                            <td><fmt:formatDate value="${authUserOrderRelation.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td><fmt:formatDate value="${authUserOrderRelation.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/userOrder/findUserOrderRelation"></tags:page>
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