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
        <title>如意消费卡会员转出限制列表</title>
        <script>
        	function add(){
        		window.location.href="${contextPath}/alabaoLimit/toAddAlabaoLimit";
        	}
        	
        	function toUpdateAlabaoLimit(account){
        		window.location.href="${contextPath}/alabaoLimit/toUpdateAlabaoLimit?account="+account+"&time="+new Date().getTime();
        	}
        	
        	function deleteAlabaoLimit(id){
          		var submit =  function(v,h,f){
          			if(v=="ok"){
          				$.post('${pageContext.request.contextPath}/alabaoLimit/deleteAlabaoLimit',{id:id,time:new Date().getTime()},
          	  					 function(data){
          	  					   if(data=="SUCCESS"){
          	  						    jBox.tip("删除成功");
        	  	  						window.setTimeout(function () {  
        	  	  							$("#findForm").submit();
        	  							}, 500);
        	  	  						   
          	  					   }else{
          	  							jBox.tip("删除失败");
          	  					   }
          	  				});//post
          			}else{
          	  			 $("#findForm").submit();
          	  		}
          			return true;
          		} 
          		$.jBox.confirm("确定删除吗？", "删除提示",submit);
          		
          	}
        </script>
    </head>
    
    <body id="" class="Cog">
           <tags:message content="${message }"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>如意消费卡会员转出限制列表</h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                            
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝账号：</label>
                                    <input type="text" class="form-control" id="account" name="account" value="${account}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">如意宝账号等级：</label>
                                    
                                    <select  name="accountLevel" class="form-control lg-select">
                                       <option value="" >全部</option>
                                       <option value="1" ${accountLevel eq '1'?'selected':'' }>普通用户</option>
                                       <option value="2" ${accountLevel eq '2'?'selected':'' }>测试用户</option>
                                       <option value="3" ${accountLevel eq '3'?'selected':'' }>理财VIP用户</option>
                                    </select>
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
		                            <label for="a" class="col-label">创建开始时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
		                        <div class="form-group col-md-4 mB15">
		                            <label for="a" class="col-label">创建结束时间：</label>
		                            <div class="input-group">
		                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
		                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
		                        	</div>
		                        </div>
                               
                                <div class="form-group col-md-4 mB15">
                                	<button type="submit" class="btn btn-success speBtn">查 询</button>
                                	<button type="button" class="btn btn-success speBtn" onclick="javascript:add();">新增</button>
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
											<th>单次转出限额</th>
											<th>转出次数限制</th>
											<th>如意宝账号等级</th>
											<th>描述</th>
											<th>创建时间</th>
											<th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.result}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${ status.count}</td>
											<td>${ list.account}</td>
											<td>${ list.moneyLimit}</td>
											<td>${ list.numberLimit}</td>
											<td>
												<c:if test="${list.accountLevel eq '1' }">普通用户</c:if>
											    <c:if test="${list.accountLevel eq '2' }">测试用户</c:if>
											    <c:if test="${list.accountLevel eq '3' }">理财VIP用户</c:if>
											</td>
											<td>
												${list.remark }
											</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        	<td>
												<a class="btn btn-info btn-xs" href="javascript:toUpdateAlabaoLimit('${list.account}')" title="修改">	
													修改
												</a>
												<a class="btn btn-danger btn-xs" href="javascript:deleteAlabaoLimit('${list.id}')" title="删除支行名称">	
														删除
												</a>
                                        	</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoLimit/alabaoLimitList"></tags:page>
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