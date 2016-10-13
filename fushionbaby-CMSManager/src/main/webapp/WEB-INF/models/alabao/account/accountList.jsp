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
        <title>如意消费卡列表</title>
    </head>
    <script type="text/javascript">
    function updateStatus(id){
    	var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/memberCardConfig/updateStatus/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		}
  		$.jBox.confirm("你确定要修改该储值卡状态吗？", "修改提示",submit);
    }
    

    function configEdit(id){
    	window.location.href = "${contextPath}/memberCardConfig/configEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  deleteconfig(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/memberCardConfig/configDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该权限吗？", "删除提示",submit);
    }
    function addAccount(){
    	window.location.href = "${contextPath}/alabaoAccount/editAccountPage";
    }
    
    function deleteAccount(id){
    	
    	
    	var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/alabaoAccount/deleteAccount?id="+id+"&time="+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除该账号吗？", "删除提示",submit);
    }
    
    function editLevel(account,level){
		
		$("#level-label-"+account).hide();
		$("#level-select-"+account).show();
		$("#editLevel-"+account).hide();
		$("#editOK-"+account).show();
		
	}
	
	function editLevelOK(account){
		var value = $("#level-select-"+account).val();
		var label = $("#level-select-"+account +" option:selected").text();
		$("#level-label-"+account).html("<span>"+label+"</span>");
		$("#level-label-"+account).show();
		$("#editLevel-"+account).show();
		$("#editOK-"+account).hide();
		$("#level-select-"+account).hide();
		var url = "${contextPath}/alabaoFinace/editLevel";
		$.get(url,{account:account,level:value},function(msg){
			
			if(msg==true){
				
				jBox.tip("设置级别成功");
			}
			
		});
		
	}
	
	function updateAccount(id){
		var url = "iframe:${contextPath}/alabaoAccount/toUpdateAccount/"+id+"/"+new Date().getTime();
		$.jBox(url, {
  		    title: "如意宝账户修改", width: 800,height:300,top:"30%",
  		    buttons: { '确定':true,'关闭': false },
  		  	submit: function (v, h, f) {
              if (v == 0) {
            	  return ;
            	  
              }

            var contents = h.find("iframe").contents();
            var account = contents.find("input[name='account']").val();
           	var trueName = contents.find("input[name='trueName']").val();
           	var identityCardNo = contents.find("input[name='identityCardNo']").val();
           	if(""==trueName){
           		contents.find("input[name='trueName']").parents('.form-group').find('.error').text('请输入真实姓名');
           		return false;
           	}else if(""==identityCardNo){
           		contents.find("input[name='identityCardNo']").parents('.form-group').find('.error').text('请输入身份证号');
           		return false;
           	}else if(!isIdCardNo(identityCardNo)){
       			contents.find("input[name='identityCardNo']").parents('.form-group').find('.error').text('请输入正确身份证号');
       			return false;
           	}else{
           		$.ajax({
                    type: "POST",
                    url: "${contextPath}/alabaoAccount/updateAccount",
                    data:{account:account,trueName:trueName,identityCardNo:identityCardNo},
               		success: function(data){
                			if (data.responseCode == '0') {
                				$.jBox.tip('如意宝账户修改成功');
                        		setTimeout(function() {  
                        			$("#findForm").submit(); 
                        		}, 500);
    						} else {
    							$.jBox.tip('如意宝账户修改失败');
    						}	
                    }
                });
           	}
           	
          }

  		});
	}
	
	function listAccountDetail(id,account){
		window.location.href = "${contextPath}/alabaoAccount/listAccountDetail/"+id+"/"+new Date().getTime();
		
	}
	
	function exportAccountExcel(){
		$("#findForm").attr("action",contextPath+"/alabaoAccount/export_account_list");
		$("#findForm").submit();
		$("#findForm").attr("action",contextPath+"/alabaoAccount/accountList");
		
	}
	
	function updateAccountIsValidate(id,validateStatus){
		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/alabaoAccount/updateAccountIsValidate/"+id+"/"+validateStatus+"/"+ new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要修改认证状态吗？", "修改认证状态提示",submit);
	}
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
               <%--  <div id="menu">
                <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
                </div> --%>
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i> 如意宝账号列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                	 <label for="a" class="col-label">账号级别：</label>
                                	 <select name="level" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <option value="">全部</option>
								            <c:forEach items="${dic:getDictByType('ALABAO_ACCOUNT_LEVEL')}" var="dict">
								            	<option value="${dict.value }"  <c:if test="${dict.value==level }">selected</c:if> >${dict.label }</option>
								            </c:forEach>
								     </select>
                                </div>
                                
                                
                                 <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">账号状态：</label>
                                     <select name="status" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
								            <option value="">全部</option>
								            <c:forEach items="${dic:getDictByType('ALABAO_ACCOUNT_STATUS')}" var="dict">
								            	<option value="${dict.value }"  <c:if test="${dict.value==status }">selected</c:if> >${dict.label }</option>
								            </c:forEach>
								     </select>
                                </div>
                                
                               
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">账号：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">姓名：</label>
                                    <input type="text" class="form-control" id="trueName" name="trueName" value="${trueName}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">身份证号：</label>
                                    <input type="text" class="form-control" id="identityCardNo" name="identityCardNo" value="${identityCardNo}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">手机号：</label>
                                    <input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${mobilePhone}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">最小余额：</label>
                                    <input type="text" class="form-control" id="balanceFrom" name="balanceFrom" value="${balanceFrom}"> 
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">最大余额：</label>
                                    <input type="text" class="form-control" id="balanceTo" name="balanceTo" value="${balanceTo}">
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
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                <a href="javascript:addAccount()" class="btn btn-success speBtn">添加账号</a>
                                <a href="javascript:exportAccountExcel()" class="btn btn-success speBtn">导出账号</a>
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
                                            <th>如意宝账户名</th>
											<th>真实姓名</th>
											<th>身份证号</th>
											<th>锁定余额</th>
											<th>阿拉宝余额</th>
											<th>昨日收益</th>
											<th>总收益</th>
											<th>创建时间</th>
											<th>状态</th>
											<th>账号等级</th>
											<th>认证状态</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.account}</td>
											<td>${ list.trueName}</td>
											<td>${ list.identityCardNo}</td>
											<td>${ list.lockedBalance}</td>
											<td>${ list.balance}</td>
											<td>${ list.yesterdayIncome}</td>
											<td>${ list.totalIncome }</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
 											<td>${dic:getDictLabel(list.status, 'ALABAO_ACCOUNT_STATUS', '-')}</td> 
                                            <td id="level-${list.account }">
                                            	<span id="level-label-${list.account }" style="display:block">
                                            		${dic:getDictLabel(list.level, 'ALABAO_ACCOUNT_LEVEL', '-')}
                                            	</span>
                                            	<select id="level-select-${list.account }" style="display:none">
                                            		<c:forEach items="${dic:getDictByType('ALABAO_ACCOUNT_LEVEL')}" var="dict">
                                            			<c:if test="${dict.value!='9' }">
										            	<option value="${dict.value }"  <c:if test="${dict.value==list.level }"></c:if> >${dict.label }</option>
														</c:if>											            	
										            </c:forEach>
                                            	</select>
                                            </td>
                                         <%--    <td>
												<a class="btn btn-info btn-xs" href="${contextPath}/alabaoAccount/updateStatus/${list.id}/2" 
												${list.status==2?'disabled':'' } title="审核通过">	
													审核通过
												</a>
												<a class="btn btn-warning btn-xs" href="${contextPath}/alabaoAccount/updateStatus/${list.id}/3" 
												${list.status==3?'disabled':'' } title="审核不通过">	
													审核不通过
												</a>
												<a class="btn btn-info btn-xs" href="${contextPath}/alabaoAccount/updateStatus/${list.id}/4"
												 ${list.status==4?'disabled':'' } title="注销">	
													注销
												</a>
												<c:if test="${ list.status=='1'}">
													<a class="btn btn-info btn-xs" href="javascript:deleteAccount(${list.id })">删除</a>
												</c:if>
												<c:if test="${ list.level!='9'}">
													<a class="btn btn-info btn-xs" id="editLevel-${list.account }" 
														href="javascript:editLevel('${list.account }','${list.level }')">设置级别</a>
													<a class="btn btn-success btn-xs edit-role" id="editOK-${list.account }" style="display:none"
														href="javascript:editLevelOK('${list.account }')">确定设置</a>
												</c:if>	
												<a class="btn btn-info btn-xs" href="javascript:updateAccount(${list.id })">修改</a>
                                            </td> --%>
                                            <td>
                                            	<c:if test="${list.isValidate=='1'}">未认证</c:if>
                                            	<c:if test="${list.isValidate=='2'}">认证失败</c:if>
                                            	<c:if test="${list.isValidate=='3'}">已认证</c:if>
                                            </td>
                                            <td>
                                            	<a class="btn btn-info btn-xs" href="javascript:listAccountDetail(${list.id },${list.account })">详情</a>
                                            	<c:if test="${list.isValidate ne '3'}"><a class="btn btn-info btn-xs" href="javascript:updateAccountIsValidate('${list.id }','3')">通过认证</a></c:if>
                                            	<c:if test="${list.isValidate ne '2'}"><a class="btn btn-info btn-xs" href="javascript:updateAccountIsValidate('${list.id }','2')">不通过认证</a></c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoAccount/accountList"></tags:page>
                            <!-- 分页 end -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                </div>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.container-fluid -->
        
    </body>
</html>