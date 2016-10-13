<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>全局设置管理</title>
  	<script type="text/javascript">
  	
	  	
	  	function delDict(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/globalConfig/deleteGlobalConfig/"+id;
	  			}
	  			return true;
	  		} 
	  		$.jBox.confirm("你确定要删除该全局设置吗？", "删除提示",submit);
	  	}
	  	
	  	function queryDict(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
	  	function editDict(code){
	  		
	  		$.jBox("iframe:${contextPath}/globalConfig/editPage?code="+code, { 
	  		    title: "编辑全局设置值", width: 400,height: 200,
	  		    buttons: { '确定':true,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              var contents = h.find("iframe").contents();
	              var url = contents.find("#globalForm").attr("action");
	              var id = contents.find("input[name='id']").val();
	              var code = contents.find("input[name='code']").val();
	              var value = contents.find("input[name='value']").val();
	              $.post(url, {id:id,code:code,value:value,datatime:new Date().getTime()},function(data){
	            	 if(data==true){
	            		 jBox.tip("修改成功");
	            	 }else{
	            	 	jBox.tip("修改失败"); 
	            	 }
	            	 window.location.href = "${contextPath}/globalConfig/globalConfigList";
	              });

	  		  	}
			});
	  		
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 全局设置列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/globalConfig/globalConfigList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="label">名称：</label>
	      					<input type="text" id="name" name="name" class="form-control" value="${sysmgrGlobalConfigDto.name}"
	      						 placeholder="名称">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="code">编码：</label>
	      					<input type="text" id="code" name="code" class="form-control" value="${sysmgrGlobalConfigDto.code}"  
	      						placeholder="编码" >
	  					</div>
					    
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="queryDict()">确认查询</button>
	                        <a href="${contextPath }/globalConfig/golbalConfigAddPage" class="btn btn-info" >新增全局设置</a>
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
							  <th>全局设置名称</th>
							  <th>全局设置编码</th>
							  <th>全局设置值</th>
							  <th>备注</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
								   <td>${tmp.name}</td>	
								   <td>${tmp.code}</td>	
								   <td>${tmp.value}</td>
								   <td>${tmp.remark}</td>
								   <td>													
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delDict(${tmp.id})"   title="删除">	
									          删除
									 </a>
										
									 <a class="btn btn-danger btn-xs "  href="javascript:editDict('${tmp.code}')"   title="编辑">	
									          修改
									 </a>	
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/globalConfig/globalConfigList"></tags:page>
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
