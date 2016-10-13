<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>会员评论列表</title>
  	<script type="text/javascript">
  	
	  	function query(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	/**修改商品评论是否显示*/
		function updateDisable(disable,id){
			var result=confirm("确定要修改状态吗？");
			if(result){
				$.post("${pageContext.request.contextPath}/member/changeDisable",{
					disable:disable,id:id,time:new Date().getTime()
				},function(data){
					if(data.result=="success"){
						jBox.tip("修改成功!", 'info');
						window.setTimeout(function () { 
							query();
						}, 500);
						
					}else{
						jBox.tip("修改失败!", 'info');
					}
					
				});
			}
 		}
	  	
		/**修改商品评论是否显示*/
		function updateStatus(status,id){
			var result=confirm("确定要修改审核状态吗？");
			if(result){
				$.post("${pageContext.request.contextPath}/member/changeStatus",{
					status:status,id:id,time:new Date().getTime()
				},function(data){
					if(data.result=="success"){
						jBox.tip("修改成功!", 'info');
						window.setTimeout(function () { 
							query();
						}, 500);
						
					}else{
						jBox.tip("修改失败!", 'info');
					}
					
				});
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 会员评论列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/member/memberCommentList">
				     
					    <div class="form-group col-md-4 mB15">
	    					<label for="label">会员名称：</label>
	      					<input type="text" id="memberName" name="memberName" class="form-control"  value="${memberName}" placeholder="请输入会员名称"/>
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="label">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control"  value="${skuNo}" placeholder="请输入商品编码"/>
	  					</div>
	  					
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="query()">查询</button>
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
							    <th width="5%">序号</th>

								<th width="5%">会员名称</th>

								<th width="5%" >商品编码</th>
								
								<th width="5%" >订单号</th>

								<th width="30%" >评论内容</th>

								<th width="10%" >评论IP</th>
								
								<th width="5%" >评论分数</th>
								
								<th width="15%" >评论时间</th>
								
								<th width="5%" >点赞数</th>
								
								<th width="12%" >操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${memberCommentList}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
								   <td>${tmp.memberName}</td>	
								   <td>${tmp.skuCode}</td>	
								   <td>${tmp.orderCode}</td>	
								   <td>${tmp.commentContent}</td>	
								   <td>${tmp.ipAddress}</td>	
								   <td>${tmp.score}</td>	
								   <td><fmt:formatDate  value="${tmp.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>	
								   <td>${tmp.praiseCount}</td>	
								   <td>
								   		<c:choose>
														
											<c:when test="${tmp.status == 'y'}">
												<a class="btn btn-info btn-xs" href="javascript:updateStatus('y','${tmp.id}')" >
													审核不通过	
												</a>
											</c:when>
											<c:when test="${tmp.status == 'n'}">
												<a class="btn btn-info btn-xs" href="javascript:updateStatus('n','${tmp.id}')" >
													审核通过	
												</a>
											</c:when>
										
										</c:choose>
										<c:choose>
														
											<c:when test="${tmp.disable == 'y'}">
												<a class="btn btn-success btn-xs edit-role" href="javascript:updateDisable('y','${tmp.id}')" >
													禁用	
												</a>
											</c:when>
											<c:when test="${tmp.disable == 'n'}">
												<a class="btn btn-success btn-xs edit-role" href="javascript:updateDisable('n','${tmp.id}')" >
													解禁	
												</a>
											</c:when>
										
										</c:choose>
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/member/memberCommentList"></tags:page>
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
