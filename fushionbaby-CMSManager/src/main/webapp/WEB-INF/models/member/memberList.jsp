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
        <title>会员列表</title>
        <script type="text/javascript">
	        function updateMemberDisabled(root,msg,memberId,disabled){
	    	    var result;
	    		var submit =  function(v,h,f){
	      			if(v=="ok"){
	      				$.ajax({
	      					type : "post",
	      					url : root+"/member/changeMemberDisable",
	      					//dataType:"text",
	      					data : {
	      						memberId: memberId, 
	      						disabled: disabled
	      					},
	      					success : function(data) {
	      						if(data == "success"){
	      							location.reload();
	      		  					jBox.tip("操作成功", 'info');
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
	    		$.jBox.confirm(msg, "操作提示",submit);
	    	}
	        
	        function showMemberAddress(memberId){
	        	location.href=contextPath+"/memberAddress/memberReceiveAddressList?memberId="+memberId;
	        }
	        
	        function exportMemberExcel(){
				$("#findForm").attr("action",contextPath+"/member/export_excel_member_list");
				$("#findForm").submit();
				$("#findForm").attr("action",contextPath+"/member/memberList");
				
			}
	        
	        function import_excel(){
	        	var url = "iframe:${contextPath}/member/showImportMember";
				$.jBox(url, {
		  		    title: "EXCEL批量导入会员", width: 400,height: 200,
		  		    buttons: { '关闭': false },
				});
				
			}
	        
	        function addMember(){
	        	var url = "iframe:${contextPath}/member/showAddMember";
				$.jBox(url, {
		  		    title: "添加会员", width: 600,height: 360,
		  		    buttons: { '关闭': false },
				});
				
			}
	    
	        function change_memberType(memberId,memberType){
				var o1="1"==memberType?"selected":"";
				var o2="2"==memberType?"selected":"";
				var o3="3"==memberType?"selected":"";
				var memberTypeSelect=
				 	 "<select  style='width: 100px' name='memberType' id='newMemberType_"+memberId+"'" + " class='form-control lg-select'>"+
		             "	<option value='1' "+o1+">一般用户</option>"+
		             "	<option value='2' "+o2+">忠诚度用户</option>"+
		             "	<option value='3' "+o3+">内部用户</option>"+
           		     "</select>";
		  		$('#memberType_'+memberId).html(memberTypeSelect);
		  		$('#memberTypeUpdate_'+memberId).html('<a class="btn btn-danger btn-xs" href="javascript:update_memberType('+memberId+')" title="修改用户类型">确定修改</a>');
		  	}
		  	function update_memberType(memberId){
		  		var memberType=$("#newMemberType_"+memberId).val();
		  		var submit =  function(v,h,f){
		  			if(v=="ok"){
		  				$.post('${pageContext.request.contextPath}/member/updateMemberType',{memberId:memberId,memberType:memberType,time:new Date().getTime()},
		  	  					 function(data){
		  	  					   if(data.result=="success"){
		  	  						    jBox.tip(data.msg);
			  	  						window.setTimeout(function () {  
			  	  							$("#findForm").submit();
			  							}, 500);
			  	  						   
		  	  					   }else{
		  	  							jBox.tip(data.msg);
		  	  					   }
		  	  				});//post
		  			}else{
		  	  			 $("#findForm").submit();
		  	  		}
		  			return true;
		  		} 
		  		$.jBox.confirm("确定修改用户类型吗？", "修改用户类型提示",submit);
		  		
		  	}
        </script>
    </head>
    <body id="" class="User">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 会员列表</h3>
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
                                    
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label class="col-label">会员状态：</label>
                                    <select name="disable" id="disable" class="form-control lg-select">
                                        <option value="">所有</option>
                                       	<option value="y" <c:if test="${memberDto.disable == 'y' }"> selected="selected" </c:if> >禁用</option>
                                       	<option value="n" <c:if test="${memberDto.disable == 'n' }"> selected="selected" </c:if> >启用</option>
                                    </select>
                                </div>
                                <div class="col-md-8">
	                                <button type="submit" class="btn btn-info speBtn">查 询</button>
	                                <button type="button" onclick="addMember()" class="btn btn-primary speBtn">添加会员</button> 
	                                <button type="button" onclick="exportMemberExcel()" class="btn btn-success speBtn">导出会员表</button> 
	                                <button type="button" onclick="import_excel()" class="btn btn-warning speBtn">批量导入会员</button> 
                                	<button type="button" class="btn btn-success speBtn" onclick="downLoadExcel('批量导入会员模板\.xls')">下载批量导入会员模板</button>
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
                                            <th>手机号</th>
                                            <th>邮箱</th>
                                            <th>用户积分</th>
                                            <th>注册来源</th>
                                            <th>注册渠道</th>
                                            <th>注册时间</th>
                                            <th>会员状态</th>
                                            <th>会员类型</th>
                                            <th width="150px">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${memberList}" var="member" varStatus="status">
                                    	<tr>
                                            <td scope="row">${status.count}</td>
                                            <td>${member.loginName}</td>
                                            <td>${member.telephone}</td>
                                            <td>${member.email}</td>
                                            <td>${member.epoints}</td>
                                            <td>${member.sourceName}</td>
                                            <td>${channelMap[member.channelCode]}</td>
                                            <td><fmt:formatDate value="${member.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${member.disable =='n'}">启用</c:when> 
												  	<c:otherwise>禁用</c:otherwise> 
                                             	</c:choose>
                                            </td>
                                            
                                            <td id="memberType_${member.id}">
	                                               	<c:choose>
	                                            		<c:when test="${member.memberType =='1'}">一般用户</c:when>
	                                            		<c:when test="${member.memberType =='2'}">忠诚度用户</c:when>
	                                            		<c:when test="${member.memberType =='3'}">内部用户</c:when> 
	                                             	</c:choose>
											</td>
                                            <td>
                                            	<c:choose>
                                            		<c:when test="${member.disable =='y'}">
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="updateMemberDisabled('${contextPath}','确定要启用当前用户？',${member.id},'n');">启用</button>
                                            		</c:when> 
												  	<c:otherwise>
												  		<button type="button" class="btn btn-danger btn-xs delete-role"  onclick="updateMemberDisabled('${contextPath}','确定要停用当前用户？',${member.id},'y');">禁用</button>
												  	</c:otherwise> 
                                             	</c:choose>
                                             	<button type="button" class="btn  btn-xs "  onclick="showMemberAddress(${member.id});">收货地址</button>
                                             
                                            	<div id="memberTypeUpdate_${member.id}">
													<a class="btn btn-info btn-xs" href="javascript:change_memberType(${member.id},'${member.memberType}')" title="修改用户类型">	
														修改类型
													</a>
												</div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/member/memberList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->

        <!-- 删除角色提示框 -->
        <div class="modal fade" id="modalDeleteRole">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title"><i class="fa fa-info-circle"></i> 确认操作</h4>
                    </div>
                    <div class="modal-body text-danger">
                        是否确认删除此角色？
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-danger">确认删除</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.modal -->
        
        <!-- 批量导入模态对话框-1  开始  -->
		<div id="Modal_pldr" class="modal hide fade Modal_dx">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h3>批量导入用户信息</h3>
		  </div>
			  <div class="modal-body">
			  	<input  type="file" name="member_excel" id="member_excel" onchange="fileChange(this)" /> 
				<span id="logoUrl_Wrning" style="color:red;width:5px;height:28px;line-height:28px">*</span>
			  </div>
			  <select name="isNeedSendMessage">
			  	<option value="y">发送短信</option>
			  	<option value="n">不发送短信</option>
			  </select>
			  <div class="modal-footer">
			  	<span onclick="import_excel()">
			    	<a href="javascript:" class="btn btn-success" data-dismiss="modal" data-toggle="modal">确定</a>
			    </span>
			    <a href="#" class="btn" data-dismiss="modal">关闭</a>
			  </div>
		</div>
		<!-- 批量导入模态对话框-1  结束  -->
    </body>
</html>