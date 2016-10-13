<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>用户意见反馈列表</title>
  	<script type="text/javascript">
  	
	  	function queryFeedback(){
	  		
	  		$("#findForm").submit();
	  	}
	  	function delFeedback(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				var url ="${pageContext.request.contextPath}/feedback/deleteById/"+id; 
	  				$.post(url,function(msg){
	  					
	  					if(msg!="SUCCESS"){
	  						jBox.tip("删除失败！", 'error');
		  		    	 	return;
	  					}
	  					jBox.tip("删除成功!", 'info');
 		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
		  		      
	  				});
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该意见反馈吗？", "删除提示",submit);
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 用户意见反馈列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/feedback/findAll">
				     
					   <div class="form-group col-md-4 mB15">
                          <label for="a" class="col-label">版本来源：</label>
                          <select name="sourceCode" class="form-control lg-select">
                            <option value="" >所有</option>
                               <c:forEach items="${sysmgrSourceList}" var="sourceList" >
									<option value="${sourceList.code}" ${sourceCode==sourceList.code?'selected':''} >${sourceList.name}</option>
							  </c:forEach>
                           </select>
                       </div>
						
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="queryFeedback()">查询</button>
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
							  <th>版本来源名称</th>
							  <th>用户名</th>
							  <th>反馈内容</th>
							  <th>联系方式</th>
							  <th>添加时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${memberFeedbackInfo }" var="list" varStatus="status">
								<tr>
								    <td>${status.count }</td>
								    <td>
								      <c:forEach items="${sysmgrSourceList}" var="list2" >
                                          <c:choose >
                                             <c:when test="${list2.code eq list.sourceCode}">${list2.name }</c:when>
                                          </c:choose>
                                       </c:forEach>
									</td>
									<td>${list.memberName}</td>

									<td>${list.content}</td>
									
									<td>${list.contactInformation}</td>

									<td>${list.showDate}</td>
																													
									<td>
										<a class="btn btn-info btn-xs" href="javascript:delFeedback(${list.id })" title="删除">	
														删除
										</a>
									</td>
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/feedback/findAll"></tags:page>
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
