<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>红包使用记录</title>
  	<script type="text/javascript">
	  	$(document).ready(function() {
	  		var isShowAddSuc = $("#addRemark").val();
	  		var isShowModifySuc = $("#modifyRemark").val();
	  		if(isShowAddSuc == '0'){
	  			jBox.tip("添加成功", 'info');
	  		}
	  		
	  		if(isShowModifySuc == '0'){
	  			jBox.tip("修改成功", 'info');
	  		}
	  	});
	  	
	  	function delBrand(id){
	  		var submit =  function(v,h,f){
	  			if(v == "ok"){
	  				$.post("${contextPath }/skuBrand/deleteById",{brandId:id },function(result){
	  		  			if(result =="SUCCESS"){	
	  		  			jBox.tip("删除成功", 'info');
	  		  				
	  		  			// 3秒后完成操作
	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
	  		  			}else{
	  		  				jBox.tip("删除失败", 'info');
	  		  			}
	  		  			
	  		          });	
	  			}
	 
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该品牌吗？", "删除提示",submit);
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i>红包使用记录列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">用户号：</label>
	      					<input type="text" id="memberId" name="memberId" class="form-control" value="${recordDto.memberId}"
	      						 placeholder="请输入分享者用户号" />
	  					</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">用户名：</label>
	      					<input type="text" id="memberName" name="memberName" class="form-control" value="${recordDto.memberName}"
	      						 placeholder="请输入用户名">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">订单号：</label>
	      					<input type="text" id="orderCode" name="orderCode" class="form-control" value="${recordDto.orderCode}"  
	      						placeholder="请输入订单号" />
	  					</div>
	  					
	  					
	  					 <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">使用状态：</label>
	      					<select name="useStatus" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
	      						<option value="" selected="selected">所有</option>
	      						<c:forEach items="${statusMaps }" var="status">
	      							<option value="${status.value }">${status.key }</option>
	      						</c:forEach>
	      						
	      					</select>
	  					</div>
	  					
	  					 <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">使用类型：</label>
	      					<select name="type" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
	      						<option value="" selected="selected">所有</option>
	      						<c:forEach items="${typeMaps }" var="type">
	      							<option value="${type.value }">${type.key }</option>
	      						</c:forEach>
	      					</select>
	  					</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">开始时间：</label>
	    					<div class="input-group">
								<input class="timeS form-control form_datetime" readonly name="createStartTime"
								 	 type="text" value="${recordDto.createStartTime}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>
						</div>
						
						<div class="form-group col-md-4 mB15">
							<label class="col-label">结束时间：</label>
							<div class="input-group">
								<input class="timeE form-control form_datetime"  readonly name="createEndTime" 
									 type="text" value="${recordDto.createEndTime}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
	    				    </div>
	    				</div>
	    				
	    				
	    				<div class="form-group col-md-4 mB15">
	    					<label for="flag" class="col-label">排序属性：</label>
	      					<select name="sortAttr" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">默认</option>
					            <option value="1" ${sortAttr =='1'?'selected':''}>使用红包</option>
					            <option value="2" ${sortAttr =='2'?'selected':''}>创建时间</option>
					        </select>
	  					</div>
	  					
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="flag" class="col-label">排序类型：</label>
	      					<select name="sortType" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					            <option value="asc"  ${sortType =='asc'?'selected':''}>升序</option>
					            <option value="desc" ${sortType =='desc'?'selected':''}>降序</option>
					        </select>
	  					</div>
	  					
	  					
	                     <button type="submit" class="btn btn-info" >确认查询</button>
	                      
				    	
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
							  <th>用户号</th>
							  <th>用户名称</th>
							   <th>类型</th>
							  <th>订单编号</th>
							  <th>使用红包金额</th>
							  <th>使用状态</th>
							  <th>创建时间</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${registerRecordList}" var="record" varStatus="status">
								 <tr>
								   <td scope="row">${status.count}</td>
								   <td>${record.memberId}</td>	
								   <td>${record.memberName}</td>
								   <td>${record.type}</td>
								   <td>${record.orderCode}</td>	
								   <td>${record.redEnvelopeAmount}</td>	
								   <td>${record.useStatus}</td>	
								   <td><fmt:formatDate value="${record.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								  </tr>
								 </c:forEach>
                              </tbody>
                              </table>
                           </div>
                           <div>交易总记录：${recordRedCount }</div>
                           <tags:page formId="findForm" url="${contextPath}/activityShare/registerList"></tags:page>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
