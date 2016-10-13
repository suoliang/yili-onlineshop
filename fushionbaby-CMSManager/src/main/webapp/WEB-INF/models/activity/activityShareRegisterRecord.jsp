<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>分享注册用户记录</title>
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i>分享注册用户列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">分享者用户号：</label>
	      					<input type="text" id="memberShareId" name="memberShareId" class="form-control" value="${registerDto.memberShareId}"
	      						 placeholder="请输入分享者用户号">
	  					</div>
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">分享者用户名：</label>
	      					<input type="text" id="memberShareName" name="memberShareName" class="form-control" value="${registerDto.memberShareName}"
	      						 placeholder="请输入分享者用户名">
	  					</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">新注册用户号：</label>
	      					<input type="text" id="memberRegisterId" name="memberRegisterId" class="form-control" value="${registerDto.memberRegisterId}"
	      						 placeholder="请输入用户编号">
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">新注册用户名：</label>
	      					<input type="text" id="memberRegisterName" name="memberRegisterName" class="form-control" value="${registerDto.memberRegisterName}"
	      						 placeholder="请输入用户名">
	  					</div>
	  					
					    <div class="form-group col-md-4 mB15">
					    	
	    					<label class="col-label">开始时间：</label>
	    					<div class="input-group">
								<input class="timeS form-control form_datetime" readonly name="createStartTime"
								 	 type="text" value="${registerDto.createStartTime}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
							</div>
						</div>
						
						<div class="form-group col-md-4 mB15">
							<label class="col-label">结束时间：</label>
							<div class="input-group">
								<input class="timeE form-control form_datetime"  readonly name="createEndTime" 
									 type="text" value="${registerDto.createEndTime}">
								<div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
	    				    </div>
	    				</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">邀请码：</label>
	      					<input type="text" id="inviteCode" name="inviteCode" class="form-control" value="${registerDto.inviteCode}"  
	      						placeholder="请输入邀请码" >
	  					</div>
	  					
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">密码：</label>
	      					<input type="text" id="memberRegisterPwd" name="memberRegisterPwd" class="form-control" value="${registerDto.memberRegisterPwd}"  
	      						/>
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
							  <th>分享者用户编号</th>
							  <th>分享者用户名称</th>
							  <th>注册者用户编号</th>
							  <th>注册者用户名称</th>
							  <th>注册者密码</th>
							  <th>邀请码</th>
							  <th>获得红包金额</th>
							  <th>创建时间</th>
							 </tr>
                            </thead>
                            <tbody> 
                              <c:forEach items="${registerDtoList}" var="register" varStatus="status">
								<tr>
								   <td scope="row">${status.count}</td>
								   <td>${register.memberShareId}</td>	
								   <td>${register.memberShareName}</td>	
								   <td>${register.memberRegisterId}</td>	
								   <td>${register.memberRegisterName}</td>
								   <td>${register.memberRegisterPwd }	
								   <td>${register.inviteCode}</td>
								   <td>${register.gainRedEnvelope}</td>
								   <td><fmt:formatDate value="${register.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                           <div>
                           	<span>E10ADC3949BA59ABBE56E057F20F883E(加密前：123456)</span>
                           	
                           	
                           </div>
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
