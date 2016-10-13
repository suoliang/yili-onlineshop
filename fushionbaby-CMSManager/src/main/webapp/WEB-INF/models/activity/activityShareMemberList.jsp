<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>分享赚红包用户记录</title>
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i>分享赚红包用户列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">用户编码：</label>
	      					<input type="text" id="memberId" name="memberId" class="form-control" value="${memberDto.memberId}"
	      						 placeholder="请输入用户编号">
	  					</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">用户名：</label>
	      					<input type="text" id="memberName" name="memberName" class="form-control" value="${memberDto.memberName}"
	      						 placeholder="请输入用户名">
	  					</div>
	  					
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">邀请码：</label>
	      					<input type="text" id="inviteCode" name="inviteCode" class="form-control" value="${memberDto.inviteCode}"  
	      						placeholder="请输入邀请码" >
	  					</div>
	  					<div class="form-group col-md-4 mB15">
	    					<label for="flag" class="col-label">是否有邀请码：</label>
	      					<select name="flag" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">所有</option>
					            <option value="y" ${flag =='y'?'selected':''}>是</option>
					            <option value="n" ${flag =='n'?'selected':''}>否</option>
					        </select>
	  					</div>
	  					
	  					
	  					<div class="form-group col-md-4 mB15">
	    					<label for="flag" class="col-label">排序属性：</label>
	      					<select name="sortAttr" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								<option value="" selected="selected">默认</option>
					            <option value="1" ${sortAttr =='1'?'selected':''}>累计红包</option>
					            <option value="2" ${sortAttr =='2'?'selected':''}>可用红包</option>
					            <option value="3" ${sortAttr =='3'?'selected':''}>创建时间</option>
					            <option value="4" ${sortAttr =='4'?'selected':''}>修改时间</option>
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
							  <th>用户编码</th>
							  <th>用户名称</th>
							  <th>邀请码</th>
							  <th>累计获取红包金额</th>
							  <th>可用红包金额</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${memberDtos}" var="member" varStatus="status">
								<tr>
								   <td scope="row">${status.count}</td>
								   <td>${member.memberId}</td>	
								   <td>${member.memberName}</td>	
								   <td>${member.inviteCode}</td>
								   <td>${member.grandGainRedEnvelope}</td>
								   <td>${member.existingRedEnvelope}</td>
								   <td><fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								   <td><fmt:formatDate value="${member.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                           <tags:page formId="findForm" url="${contextPath}/skuBrand/skuBrandList"></tags:page>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
