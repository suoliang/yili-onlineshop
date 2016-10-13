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
        <title>消息推送列表</title>
    </head>
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 推送消息列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
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
                                        
                                        	<th width="2%">序号</th>
											<th width="8%">推送设备</th>
											<th width="10%">推送标题</th>
											<th width="10%">推送url</th>
											<th width="5%">推送标签</th>
											<th width="5%">推送别名</th>
											<th width="10%">推送时间</th>
											<th width="10%">推送内容</th>
											<th width="5%">数据类型</th>
											<th width="10%">状态</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                   <c:forEach items="${pushMessageList}" var="message" varStatus="status">
										<tr>
											<td>${status.count}</td>
											<td>${message.pushDevice}</td>
											<td>${message.pushTitle}</td>
											<td>${message.pushUrl}</td>
											<td>${message.pushTag }</td>
											<td>${message.pushAlias }</td>
											<td><fmt:formatDate value="${message.pushTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
											<td>${message.pushContent }</td>
											
									 	<td>
												<c:if test="${message.messageType=='0' }">通知</c:if>
												<c:if test="${message.messageType =='1' }">推送</c:if>
											</td> 
											<td>
												<c:if test="${message.pushIsOk=='y' }">成功</c:if>
												<c:if test="${message.pushIsOk =='n' }">失败</c:if>
											</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/push/listAllPushMessage"></tags:page>
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