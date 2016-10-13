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
        <title>阿拉丁卡收益列表</title>
    </head>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 阿拉丁卡收益列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                               <div  class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉丁卡卡号：</label>
                                    <input type="text" class="form-control" name="cardNo" value="${memberCardDto.cardNo}" placeholder="请输入阿拉丁卡卡号"> 
                                </div>
                               <div  class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝账号：</label>
                                    <input type="text" class="form-control" name="acount" value="${memberCardDto.acount}" placeholder="请输入如意宝账号"> 
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">收益开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${memberCardDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">收益结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${memberCardDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
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
                                            <th>如意宝账号</th>
											<th>阿拉丁卡卡号</th>
											<th>收券金额（元）</th>
											<!-- <th>收类型</th> -->
											<th>收益时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.incomeAcount}</td>
											<td>${ list.incomeCardCode}</td>
											<td>${ list.incomeMoney}</td>
										<%-- 	<td>
												<c:if test="${list.incomeType eq '1' }">本金</c:if>
											    <c:if test="${list.incomeType eq '2' }">收益额</c:if>
											    <c:if test="${list.incomeType eq '3' }">赠券额</c:if>
											</td> --%>
                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/memberCardIncomeRecord/incomeRecordList"></tags:page>
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