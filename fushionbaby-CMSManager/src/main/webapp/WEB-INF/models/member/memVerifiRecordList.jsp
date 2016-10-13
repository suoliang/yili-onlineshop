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
        <title>会员认证记录列表</title>
       
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 会员认证记录列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">用户姓名：</label>
                                    <input type="text" class="form-control" id="trueName" name="trueName" value="${verificationRecord.trueName}" placeholder="请输入用户姓名">
                                </div>
                                 
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">认证状态：</label>
                                    <select name="verificationStatus" id="verificationStatus" class="form-control lg-select">
                                        <option value="">所有</option>
                                         <option value="y" <c:if test="${verificationRecord.verificationStatus == 'y' }"> selected="selected" </c:if> >认证成功</option>
                                         <option value="n" <c:if test="${verificationRecord.verificationStatus == 'n' }"> selected="selected" </c:if> >认证失败</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">认证详情：</label>
                                    <select name="responseStatus" id="responseStatus" class="form-control lg-select">
                                        <option value="">所有</option>
                                        <c:forEach items="${verificationResCodeEnumMap }" var="map">
                                         	<option value="${map.key }" <c:if test="${verificationRecord.responseStatus == map.key }"> selected="selected" </c:if> >${map.value }</option>
                                    	</c:forEach>
                                    </select>
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
                                            <th>真实姓名</th>
                                            <th>身份证号码</th>
                                            <th>银行卡号</th>
                                            <th>认证类型</th>
                                            <th>认证状态</th>
                                            <th>认证详情</th>
                                            <th>来源</th>
                                            <th>创建时间</th>
                                            <th>更新时间</th>
                                            
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${list.trueName}</td>
                                            <td>${list.identityCardNo}</td>
                                            <td>${list.bankCardNo}</td>
                                            <td>
                                            	<c:if test="${list.verificationType == '1'}">验证身份证号码</c:if>
                                            	<c:if test="${list.verificationType == '2'}">验证银行卡号</c:if>
                                            </td>
                                            <td>
                                            	<c:if test="${list.verificationStatus == 'y'}">认证成功</c:if>
                                            	<c:if test="${list.verificationStatus == 'n'}">认证失败</c:if>
											</td>
                                            <td>${verificationResCodeEnumMap[list.responseStatus]}</td>
                                            <td>
                                            	<c:if test="${list.sourceCode == '1'}">安卓</c:if>
                                            	<c:if test="${list.sourceCode == '2'}">IOS</c:if>
                                            </td>
                                            <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td><fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/verifiRecord/verifiRecordList"></tags:page>
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