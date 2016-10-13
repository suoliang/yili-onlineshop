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
        <title>会员删除列表</title>
        <script type="text/javascript">
	        function deleteMember(memberId,registerType){
	    	    var result;
	    		var submit =  function(v,h,f){
	      			if(v=="ok"){
	      				$.ajax({
	      					type : "post",
	      					url : contextPath+"/member/deleteMember",
	      					data : {
	      						memberId: memberId, 
	      						registerType: registerType,
	      						datatime:new Date().getTime()
	      					},
	      					success : function(data) {
	      						result  = data;
	      						if(result == "success"){
	      		  					jBox.tip("操作成功", 'info');
	      		  					window.setTimeout(function () {  location.reload(); }, 1500);
	      		  				}else{
	      		  					jBox.tip("操作失败", 'info');
	      		  	  			}
	      					},
	      					error : function() {
	      						alert("请求后台数据异常");
	      					}
	      				});
	      			}
	      			return true;
	      		} 
	    		$.jBox.confirm("确定要删除该用户吗？", "操作提示",submit);
	    	}
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 会员删除列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">用户名称：</label>
                                    <input type="text" class="form-control" id="loginName" name="loginName" value="${memberDto.loginName}" placeholder="请输入用户名称">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="b" class="col-label">手机号码：</label>
                                    <input type="text" class="form-control" id="telephone" name="telephone" value="${memberDto.telephone}" placeholder="请输入手机号码">
                                </div>    
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">注册渠道：</label>
                                    <select name="channelCode" id="channelCode" class="form-control lg-select">
                                        <option value="">所有</option>
                                      <c:forEach var="channel" items="${channelMap}">
                                             <option value="${channel.key}" <c:if test="${channel.key == memberDto.channelCode }"> selected="selected" </c:if> > ${channel.value}</option>
									   </c:forEach>  
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">注册来源：</label>
                                    <select name="sourceCode" id="sourceCode" class="form-control lg-select">
                                        <option value="">所有</option>
                                       <c:forEach var="source" items="${sourceList}">
                                       		<option value="${source.code}" <c:if test="${source.code == memberDto.sourceCode }"> selected="selected" </c:if> > ${source.name}</option>
                                       </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="d" class="col-label">注册开始时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeS form-control form_datetime" readonly name="createTimeFrom" value="${memberDto.createTimeFrom}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="e" class="col-label">注册结束时间：</label>
                                    <div class="input-group">
                                        <input type="text" class="timeE form-control form_datetime" readonly name="createTimeTo" value="${memberDto.createTimeTo}">
                                        <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                                    </div>
                                    
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="c" class="col-label">邮箱：</label>
                                    <input type="text" class="form-control" id="email" name="email" value="${memberDto.email}" placeholder="请输入邮箱">
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
                                            <th>用户名</th>
                                        <!--     <th>第三方用户标志（openid）</th> -->
                                            <th>手机号</th>
                                            <th>邮箱</th>
                                            <th>用户积分</th>
                                            <th>注册来源</th>
                                            <th>注册渠道</th>
                                            <th>注册时间</th>
                                            <th>会员状态</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${memberList}" var="member" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${member.loginName}</td>
                                      <%--       <td>
                                            	<c:choose>
                                            		<c:when test="${!empty member.openId}">${member.openId}</c:when> 
												  	<c:otherwise>非第三方登录</c:otherwise> 
                                             	</c:choose>
                                            </td> --%>
                                            <td>${member.telephone}</td>
                                            <td>${member.email}</td>
                                            <td>${member.epoints}</td>
                                            <td>${member.sourceName}</td>
                                            <td>${channelMap[member.channelCode]}</td>
                                            <td><fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${member.disable =='n'}">启用</c:when> 
												  	<c:otherwise>停用</c:otherwise> 
                                             	</c:choose>
                                            </td>
                                            <td>
                                            			<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="deleteMember('${member.id}','${member.registerType}');">删除</button>
                                            	<%-- <c:choose>
                                            		<c:when test="${member.registerType !='isOther'}">
                                            			<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="deleteMember('${member.id}','${member.registerType}');">删除</button>
                                            		</c:when> 
                                            		<c:otherwise></c:otherwise> 
                                             	</c:choose> --%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/member/memberDeleteList"></tags:page>
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