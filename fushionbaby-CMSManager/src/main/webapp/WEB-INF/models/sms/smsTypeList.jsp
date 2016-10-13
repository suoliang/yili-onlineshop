<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>短信类型列表管理</title>
  	<script type="text/javascript">
  	
	  	function querySmsType(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	function editSMS(id){
	  		window.location.href = "${contextPath}/smsType/editSms/"+id+"/"+new Date().getTime();
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 短信类型列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/smsType/findAll">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="label">短信类型：</label>
	      					<input type="text"  name="smsName" value="${smsName}"  class="form-control"  placeholder="请输入短信类型"/>
	  					</div>
	  					
						
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="querySmsType()">查询</button>
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
							  <th width="80%">短信模版</th>
							  <th width="10%">短信类型</th>
							  <th width="5%">操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${smsTypes }" var="list" varStatus="status">
								<tr>
								    <td>${status.count }</td>

									<td>${list.smsTemplate}</td>
									
									<td>${list.smsName}</td>
	
									<td>
                                      	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:editSMS('${list.id }');">编辑</button>
                                     </td>
									
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/smsType/findAll"></tags:page>
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
