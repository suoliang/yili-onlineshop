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
        <title>如意宝转出列表</title>
    </head>
    <script type="text/javascript">
    

    function Edit(id){
    	window.location.href = "${contextPath}/alabaoTurnOut/alabaoTurnOutEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  Del(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/alabaoTurnOut/alabaoTurnOutDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除吗？", "删除提示",submit);
    }
    
    function alabaoTurnOutListExport(){
		$("#findForm").attr("action",contextPath+"/alabaoTurnOut/alabaoTurnOutListExport");
		$("#findForm").submit();
		$("#findForm").attr("action",contextPath+"/alabaoTurnOut/alabaoTurnOutList");
		
	}
    
    function change_bankBranch(id,bankBranchName){
  		$('#bankBranch_'+id).html("<input id='bankBranchInput_"+id+"' value='"+bankBranchName+"'>");
  		var confirmA="<a class='btn btn-danger btn-xs' href='javascript:update_bankBranch("+id+")' title='修改'>确定修改</a>";
  		$('#changeBankBranch_'+id).html(confirmA);
  	}
  	function update_bankBranch(id){
  		var bankBranchName=$("#bankBranchInput_"+id).val();
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.post('${pageContext.request.contextPath}/alabaoTurnOut/updateBankBranch',{id:id,bankBranchName:bankBranchName,time:new Date().getTime()},
  	  					 function(data){
  	  					   if(data.result=="success"){
  	  						    jBox.tip("修改支行信息成功");
	  	  						window.setTimeout(function () {  
	  	  							$("#findForm").submit();
	  							}, 500);
	  	  						   
  	  					   }else{
  	  							jBox.tip("修改支行信息失败");
  	  					   }
  	  				});//post
  			}else{
  	  			 $("#findForm").submit();
  	  		}
  			return true;
  		} 
  		$.jBox.confirm("确定修改支行信息吗？", "修改支行信息提示",submit);
  		
  	}
  	
  	function goToAuditFail(id){
  		var url = "iframe:${contextPath}/alabaoTurnOut/goToAuditFail?id="+id;
		$.jBox(url, {
		    title: "审核不通过", width: 800,height: 350,
		    buttons: { '关闭': false },
	    });
  	}
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
               
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意宝转出列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出账号：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">持卡人姓名：</label>
                                    <input type="text" class="form-control" id="cardHolder" name="cardHolder" value="${cardHolder}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                   <label for="a" class="col-label">状态：</label>
                                   <select name="turnOutStatus" class="form-control lg-select">
                                      <option value="" >--请选择--</option>
                                      <option value="1" ${turnOutStatus=='1'?'selected':''}  >待审核</option>
                                      <option value="2" ${turnOutStatus=='2'?'selected':''} >审核通过</option>
                                      <option value="3" ${turnOutStatus=='3'?'selected':''} >审核不通过</option>
                                      <option value="4" ${turnOutStatus=='4'?'selected':''} >完成</option>
                                   </select>
                               </div>
                                <div class="form-group col-md-4 mB15">
		                            <label for="a">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
                                <div class="form-group col-md-4 mB15">
	                                <button type="submit" class="btn btn-success speBtn">查 询</button>
	                                <button type="button" onclick="alabaoTurnOutListExport()" class="btn btn-success speBtn">导出转出列表</button> 
                                </div>
                               
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
								<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
								<input type="hidden" name="total" value="${page.total}"/>
                               
                                <div class="clearfix"></div>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>如意宝账户名</th>
                                            <th>转移金额</th>
                                            <th>持卡人姓名</th>
                                            <th>银行名称</th>
                                            <th>银行支行名称</th>
                                            <th>银行卡号</th>
                                            <th>状态</th>
                                            <th>创建时间</th>
                                            <th>更新时间</th>
                                            <th>备注</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            
	                                            <td>${list.account}</td>
	                                            <td>${list.transferMoney}</td>
	                                            <td>${list.cardHolder}</td>
	                                            <td>${list.bankName}</td>
	                                            <td id="bankBranch_${list.id}">${list.bankBranchName}</td>
	                                            <td>${list.cardNo}</td>
	                                            <td>
	                                            	<c:if test="${list.turnOutStatus eq '1' }">待审核</c:if>
											    	<c:if test="${list.turnOutStatus eq '2' }">审核通过</c:if>
	                                            	<c:if test="${list.turnOutStatus eq '3' }">审核不通过</c:if>
											    	<c:if test="${list.turnOutStatus eq '4' }">完成</c:if>
	                                            </td>
	                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td><fmt:formatDate value="${list.updateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	                                            <td>${list.memo }</td>
	                                            
                                                <td>
		                                            <c:if test="${list.turnOutStatus eq '1' }">
		                                                  <a class="btn btn-info btn-xs" href="${contextPath}/alabaoTurnOut/updateStatus/${list.id}/2" ${list.turnOutStatus==2?'disabled':'' } title="审核通过">审核通过</a>
														  <a class="btn btn-info btn-xs" href="javascript:goToAuditFail('${list.id}');" title="审核不通过">审核不通过</a>
		                                            </c:if>
		                                            

		                                            <a class="btn btn-info btn-xs" href="${contextPath}/alabaoAccount/alabaoInOutList/${list.account}" title="转账详情">转账详情</a>
	                                           	    <div id="changeBankBranch_${list.id }">
														<a class="btn btn-info btn-xs" href="javascript:change_bankBranch('${list.id}','${list.bankBranchName}')" title="修改支行名称">	
															修改支行名称
														</a>
													</div>
                                           	    </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoTurnOut/alabaoTurnOutList"></tags:page>
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