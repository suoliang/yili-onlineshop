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
        <title>如意消费卡列表</title>
    </head>
    <script type="text/javascript">
    	
    </script>
    
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>转出记录 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出类型：</label>
                                	<select name="rollOffAccountType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
	                                	<option value="">全部</option>
									    <c:forEach items="${dic:getDictByType('ROLL_OFF_ACCOUNT_TYPE')}" var="dict">
									       <option value="${dict.value }"  <c:if test="${dict.value==rollOffAccountType }">selected</c:if> >${dict.label }</option>
									    </c:forEach>
                                	</select>
                                
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转账状态：</label>
                                	<select name="isSuccess" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
	                                	<option value="">全部</option>
									    <c:forEach items="${dic:getDictByType('FLAG')}" var="dict">
									       <option value="${dict.value }"  <c:if test="${dict.value==isSuccess }">selected</c:if> >${dict.label }</option>
									    </c:forEach>
                                	</select>
                                
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">批处理号：</label>
                                    <input type="text" class="form-control" id="batchNum" name="batchNum" value="${batchNum}">
                                </div>
                                  <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">流水号：</label>
                                    <input type="text" class="form-control" id="serialNum" name="serialNum" value="${serialNum}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出金额最小：</label>
                                    <input type="text" class="form-control" id="transferMoneyMin" name="transferMoneyMin" value="${transferMoneyMin}"> 
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出金额最大：</label>
                                    <input type="text" class="form-control" id="transferMoneyMax" name="transferMoneyMax" value="${transferMoneyMax}">
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
                                            <th>账号</th>
											<th>转出余额</th>
											<th>转出账户类型</th>
											<th>流水号</th>
											<th>批处理号</th>
											<th>是否成功</th>
											<th>创建时间</th>
											<th>备注</th>
<!-- 											<th>操作</th> -->
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.result}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.account}</td>
											<td>${ list.transferMoney}</td>
											<td>${dic:getDictLabel(list.rollOffAccountType, 'ROLL_OFF_ACCOUNT_TYPE', '-')}</td>
											<td>${ list.serialNum}</td>
											<td>${ list.batchNum}</td>
											<td>${dic:getDictLabel(list.isSuccess, 'FLAG', '-')}</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        	<td>${list.memo }</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoFinace/findRollOffRecord?account=${account }"></tags:page>
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