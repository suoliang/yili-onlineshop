<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>短信管理</title>
  	<script type="text/javascript">
  	
	  	function querySms(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	function deleteSms(id){
	  		var result = confirm("确定要删除该短信吗？");
	  		if(result){
	  			window.location.href = "${contextPath}/sms/delete/"+id+"/"+ new Date().getTime();
	  		}
	  	}
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">

				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 短信管理</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sms/smsList">
				     
					    <div class="form-group col-md-4 mB15">
	    					<label for="label">会员名称：</label>
	      					<input type="text"  name="memberName" value="${memberName}"  class="form-control"  placeholder="请输入会员名称"/>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="label">手机号码：</label>
	      					<input type="text"  name="mobile" value="${mobile}"  class="form-control"  placeholder="请输入手机号码"/>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
    						<label class="col-label">来源：</label>
      						<select name="sourceCode" class="form-control lg-select" tabindex="1">

								<option value="">所有</option>

								<option value="1" ${param.sourceCode == 1 ?'selected':''}>用户手机ANDROID客户端</option>
								<option value="2" ${param.sourceCode == 2 ?'selected':''}>用户手机IOS客户端</option>
								<option value="3" ${param.sourceCode == 3 ?'selected':''}>商城web端</option>
								<option value="4" ${param.sourceCode == 4 ?'selected':''}>运营 后台</option>
								<option value="5" ${param.sourceCode == 5 ?'selected':''}>定时任务</option>
								<option value="6" ${param.sourceCode == 6 ?'selected':''}>CMS管理系统</option>
								<option value="7" ${param.sourceCode == 7 ?'selected':''}>ERP管理系统</option>
								

							</select>
    					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
    						<label class="col-label">短信类型：</label>
      						<select name="smsTypeId" class="form-control lg-select" tabindex="1">
								<option value="">所有</option>


								<c:forEach items="${smsTypeList}" var="stList">
									<option value="${stList.id}" ${param.smsTypeId == stList.id ?'selected':''}>${stList.smsName}</option>
								</c:forEach>

									</select>

							</select>

    					</div>

	  					   <div class="form-group col-md-4 mB15">
		                            <label for="a" class="col-label">创建时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div> -
		                        	<div class="input-group">
		                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="querySms()">查询</button>
				    	</div>
				   		<div class="clearfix"></div>
				   		 
				   		<input type="hidden" name="currentPage" value="${page.currentPage}"/>
						<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
						<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
						<input type="hidden" name="total" value="${page.total}"/>
					 
					  </form>
					  
					   <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
						    <th>短信服务商</th>
						    <th>查询状态</th>
								<th>总的短信条数</th>
								<th>可用短信条数</th>
								<th>查询描述</th>
							 </tr>
                            </thead>
                            <tbody>
								<tr>
								<td>美联软通</td>
								<td>${SmsResDto.status}</td>
								<td>${SmsResDto.totalNum}</td>
								<td>${SmsResDto.balanceNum}</td>
								<td>${SmsResDto.msg}</td>		   
								</tr>
                            </tbody>
                        </table>
                      </div>
					
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
							    <th>序号</th>
								<th>会员名称</th>
								<th class="hidden-480">手机号码</th>
								<th class="hidden-480">来源</th>
								<th class="hidden-480">短信类型</th>
								<th class="hidden-480">发送时间</th>
								<th class="hidden-480">短信内容</th>
								<th class="hidden-480">短信状态</th>
								<th class="hidden-480">短信供应商</th>
								<th class="hidden-480">操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${smsList}" var="list" varStatus="status">
								<tr>
	
											<td>${status.count}</td>
	
											<td>${list.memberName}</td>
	
											<td class="hidden-480">${list.mobile}</td>
	
											<td class="hidden-480">
											
												<c:choose>
													<c:when test="${list.sourceCode == 1}">
														用户手机ANDROID客户端
													</c:when>
													<c:when test="${list.sourceCode == 2}">
														用户手机IOS客户端
													</c:when>
													<c:when test="${list.sourceCode == 3}">
														商城web端
													</c:when>
													<c:when test="${list.sourceCode == 4}">
														运营 后台
													</c:when>
													<c:when test="${list.sourceCode == 5}">
														定时任务
													</c:when>
													<c:when test="${list.sourceCode == 6}">
														CMS管理系统
													</c:when>
													<c:when test="${list.sourceCode == 7}">
														ERP管理系统
													</c:when>
													<c:otherwise>
														未定义
													</c:otherwise>
												</c:choose>
											</td>
	
											<td class="hidden-480">${list.smsTypeConfig.smsName}</td>
	
											<td class="hidden-480">
												<fmt:formatDate value="${list.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
	
											<td class="hidden-480">${list.smsContent}</td>
	
											<td class="hidden-480">
												
												<c:choose>
													<c:when test="${list.status == 0}">
														发送成功
													</c:when>
													<c:otherwise>
														发送失败
													</c:otherwise>
												</c:choose>
											</td>
											<td class="hidden-480">
												
												<c:choose>
													<c:when test="${list.smsSendFlag == 2}">
														美联软通
													</c:when>
													<c:otherwise>
														亿美
													</c:otherwise>
												</c:choose>
											</td>
	
											<td class="hidden-480"><a class="btn btn-success btn-xs edit-role" href="javascript:deleteSms('${list.id}')">删除</a></td>
										</tr>
								 </c:forEach>
								 
					
								 
                                </tbody>
                                
               
                     
                              </table>
                              
                              
                              
                     
						
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sms/smsList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
