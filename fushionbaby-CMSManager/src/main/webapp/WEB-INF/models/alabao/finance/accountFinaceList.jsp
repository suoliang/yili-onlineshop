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
    	
    	function shiftTo(account){
    		
    		
    		var url = "iframe:${contextPath}/alabaoFinace/shiftToPage?account="+account;
			$.jBox(url, {
	  		    title: "转入金额", width: 700,height: 600,
	  		    buttons: {'确定':true ,'关闭': false },
	  		  	submit: function (v, h, f) {
	  		  		
	              if (v == 0) {
	            	  return ;
	              }
	              var contents = h.find("iframe").contents();
		          var account = contents.find("input[name='account']").val();
		          var companyAccount = contents.find("select[name='companyAccount']").val();
		          var shiftToAmount = contents.find("input[name='shiftToAmount']").val();
		          var memo = contents.find("input[name='memo']").val();
		          var smsTypeId = contents.find("select[name='smsTypeId']").val();
		          if(shiftToAmount==''){
		        	  jBox.tip("请输入转入金额"); 
		        	  return ;
		          }
		          
		          var url="${contextPath}/alabaoFinace/shiftToAccout";
		          $.post(url,{account:account,amount:shiftToAmount,companyAccount:companyAccount,memo:memo,smsTypeId:smsTypeId},function(data){
		        	  if(data.responseCode=="0"){
		        		jBox.tip("成功转入"+shiftToAmount+"元"); 
		        		window.setTimeout(function () {  location.reload(); }, 1500);
		        	  }else{
		        		  jBox.tip(data.msg); 
		        	  }
		          });
	              
	  		  	}
			});
    	}
    	
    	function editBalance(account){
    		
    		var url = "iframe:${contextPath}/alabaoFinace/editBalancePage?account="+account;
			$.jBox(url, {
	  		    title: "编辑金额", width: 700,height: 550,
	  		    buttons: {'确定':true ,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              var contents = h.find("iframe").contents();
		          var account = contents.find("input[name='account']").val();
		          var lockedBalance = contents.find("input[name='lockedBalance']").val();
		          var balance = contents.find("input[name='balance']").val();
		          var url = "${contextPath}/alabaoFinace/editBalance";
		          $.post(url,{account:account,lockedBalance:lockedBalance,balance:balance},function(data){
		        	  if(data==true){
		        		jBox.tip("设置成功"); 
		        		window.setTimeout(function () {  location.reload(); }, 1500);
		        	  }else{
		        		  jBox.tip("设置失败"); 
		        	  }
		          });
		          
		          
	  		  	}
	  		});
    	}
    	
    	
    	
    	function importBalancePage(){
    		var url = "iframe:${contextPath}/alabaoFinace/importBalancePage";
			$.jBox(url, {
	  		    title: "编辑金额", width:600,height: 500,
	  		    buttons: {'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  location.reload();
	            	  return ;
	              }
	  		  	}
	  		});
    	}
    	
    	function rollOffRecord(account){
    		var url = "iframe:${contextPath}/alabaoFinace/findRollOffRecord?account="+account;
    		$.jBox(url, {
			    title: "转出记录", width: 1500,height: 800,
			    buttons: { '关闭': false },
		    });
    	}
    	
    	function shiftToRecord(account){
    		var url = "iframe:${contextPath}/alabaoFinace/findShiftToRecord?account="+account;
    		$.jBox(url, {
			    title: "转人记录", width: 1500,height: 800,
			    buttons: { '关闭': false },
		    });
    	}
    	
    	function turnToAlabao(account){
    		window.location.href= "${contextPath}/alabaoFinace/findTurnToAlabao?account="+account;
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
                            <h3 class="panel-title"><i class="fa fa-user"></i> 如意宝财务列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                	 <label for="a" class="col-label">账号级别：</label>
                                	 <select name="level" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
                                	 		<option value="">全部</option>
								            <c:forEach items="${dic:getDictByType('ALABAO_ACCOUNT_LEVEL')}" var="dict">
								            	<option value="${dict.value }"  <c:if test="${dict.value==level }"></c:if> >${dict.label }</option>
								            </c:forEach>
								     </select>
                                </div>
                               
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">账号：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">身份证号：</label>
                                    <input type="text" class="form-control" id="identityCardNo" name="identityCardNo" value="${identityCardNo}">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">姓名：</label>
                                    <input type="text" class="form-control" id="trueName" name="trueName" value="${trueName}">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">手机号：</label>
                                    <input type="text" class="form-control" id="mobilePhone" name="mobilePhone" value="${mobilePhone}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉宝余额最小：</label>
                                    <input type="text" class="form-control" id="balanceFrom" name="balanceFrom" value="${balanceFrom}"> 
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">阿拉宝余额最大：</label>
                                    <input type="text" class="form-control" id="balanceTo" name="balanceTo" value="${balanceTo}">
                                </div>
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">创建时间：</label>
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
	                                
	                                <a class="btn btn-success speBtn" onclick="importBalancePage()">导入Excel</a>
	                                
	                                <a class="btn btn-success speBtn" onclick="downLoadExcel('alabao-model.xlsx')">下载模板</a> 
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
											<th>修改时间</th>
											<th>账户级别</th>
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
                                            <td><fmt:formatDate value="${list.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
                                            <td>
	                                            <c:if test="${ list.level!='9'}">
													<a class="btn btn-info btn-xs" id="editLevel-${list.account }" 
													href="javascript:editLevel('${list.account }','${list.level }')">设置级别</a>
													<a class="btn btn-success btn-xs edit-role" id="editOK-${list.account }" style="display:none"
													href="javascript:editLevelOK('${list.account }')">确定设置</a>
												</c:if>	
												<c:if test="${list.level=='9' }">
													<a class="btn btn-info btn-xs" href="javascript:editBalance('${list.account }')">编辑金额</a>
												</c:if>
												
												<a class="btn btn-info btn-xs" href="javascript:shiftTo('${list.account }')">转入</a>
												<a class="btn btn-info btn-xs" href="javascript:rollOffRecord('${list.account }')">转出记录</a>
												<a class="btn btn-info btn-xs" href="javascript:shiftToRecord('${list.account }')">转入记录</a>
												
												<a class="btn btn-info btn-xs" href="javascript:turnToAlabao('${list.account }')">转出到如意卡记录</a>
<%-- 												<a class="btn btn-info btn-xs" href="javascript:deleteAccount(${list.id })">删除</a> --%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoFinace/accountList"></tags:page>
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