<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>字典管理</title>
  	<script type="text/javascript">
  	
	  	
	  	function delDict(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/sysmgr/deleteDict/"+id;
	  			}
	  			return true;
	  		} 
	  		$.jBox.confirm("你确定要删除该字典吗？", "删除提示",submit);
	  	}
	  	
	  	function queryDict(){
	  		
	  		$("#findForm").submit();
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 字典列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/sysmgr/dictConfigList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="label">字典标签：</label>
	      					<input type="text" id="label" name="label" class="form-control" value="${sysmgrDictConfigDto.label}"
	      						 placeholder="字典标签">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="description">类型描述：</label>
	      					<input type="text" id="description" name="description" class="form-control" value="${sysmgrDictConfigDto.description}"  
	      						placeholder="类型描述" >
	  					</div>
					    
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="queryDict()">确认查询</button>
	                        <a href="${contextPath }/sysmgr/dictAddPage" class="btn btn-info" >新增字典</a>
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
							  <th>字典标签</th>
							  <th>字典值</th>
							  <th>字典类型</th>
							  <th>类型描述</th>
							  <th>字典顺序</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="tmp" varStatus="status">
								<tr>
									<td>${status.count}</td>
								   <td>${tmp.label}</td>	
								   <td>${tmp.value}</td>	
								   <td>${tmp.type}</td>
								   <td>${tmp.description}</td>
								   <td>${tmp.sort}</td>
								   <td>													
									 <a class="btn btn-default btn-xs edit-role" href="${contextPath}/sysmgr/dictDetail/${tmp.id}" title="修改">	
										修改
									 </a>	
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delDict(${tmp.id})"   title="删除">	
									          删除
									 </a>
									
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/sysmgr/dictConfigList"></tags:page>
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
