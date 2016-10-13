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
        <title>如意宝转出到如意宝列表</title>
    </head>
    <script type="text/javascript">
    
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
               
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意宝转出到如意宝列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post" action="${contextPath}/alaTurnToAla/transferList">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出账号：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${alabaoTurnToAlabaoDto.account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转入账户：</label>
                                    <input type="text" class="form-control" id="otherAccount" name="otherAccount" value="${alabaoTurnToAlabaoDto.otherAccount}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                   <label for="a" class="col-label">状态：</label>
                                   <select name="turnOutStatus" class="form-control lg-select">
                                      <option value="" >--请选择--</option>
                                      <option value="y" ${alabaoTurnToAlabaoDto.turnOutStatus=='y'?'selected':''}  >转出成功</option>
                                      <option value="n" ${alabaoTurnToAlabaoDto.turnOutStatus=='n'?'selected':''} >转出失败</option>
                                   </select>
                               </div>
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${alabaoTurnToAlabaoDto.createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${alabaoTurnToAlabaoDto.createTimeTo}" class="timeE form-control form_datetime" readonly>
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
                               
                                <div class="clearfix"></div>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>会员名</th>
                                            <th>转出账户名</th>
                                            <th>转移金额</th>
                                            <th>转入账户名</th>
                                            <th>状态</th>
                                            <th>流水号</th>
                                            <th>创建时间</th>
                                            <th>备注</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            	
                                            	
	                                            <td>${list.trueName}</td>
	                                            <td>${list.account}</td>
	                                            <td>${list.transferMoney}</td>
	                                            <td>${list.otherAccount}</td>
	                                            <td>
	                                            	<c:if test="${list.turnOutStatus eq 'y' }">转出成功</c:if>
											    	<c:if test="${list.turnOutStatus eq 'n' }"><font color="red">转出失败</font></c:if>
	                                            </td>
	                                            <td>${list.serialNum}</td>
	                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>${list.memo}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alaTurnToAla/transferList"></tags:page>
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