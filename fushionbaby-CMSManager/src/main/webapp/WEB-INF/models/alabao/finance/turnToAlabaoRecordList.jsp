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
    	function cancel(){
    		
   	  		var submit =  function(v,h,f){
   	  			if(v=="ok"){
   	  				
   	  				var chks = $(".checkAllAccountSerialNum");
   	  				
   	  				var len = chks.length;
   	  				if(len==null||len==""||len<=0){
   	  					jBox.tip("请至少选中一条记录");
   	  					return;
   	  				}
   	  				var accounts=[];
   	  				for(i=0;i<len;i++){
   	  					if(chks[i].checked){
   	  						accounts.push(chks[i].value);
   	  					}
   	  				}
   	  			
   	  				var selectedAccoutSerialNums =accounts.join(",");
	   	  			if(selectedAccoutSerialNums==''){
	  					jBox.tip("请至少选中一条记录");
	  					return;
	  				}
   	  				var url="${contextPath}/alabaoFinace/cannelTurnToAccount?selectedAccountSerialNums="+selectedAccoutSerialNums;
   	  				
   					$.post(url,function(msg){
   	  					
   	  					if(msg!=true){
   	  						jBox.tip("操作失败！！", 'error');
   		  		    	 	return;
   	  					}
   	  					jBox.tip("操作成功。", 'info');
    		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
   		  		      
   	  				});
   	  				
   	  			}else{
   	  				jBox.tip("修改失败", 'info');
   	  			}
   	  			return true;
   	  		} 
   	  		
   	  		$.jBox.confirm("你确定要修改选中的账号吗？", "提示",submit);
   	  		 
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>转出如意消费卡记录 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出状态：</label>
                                	<select name="turnOutStatus" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
	                                	<option value="">全部</option>
									    <c:forEach items="${dic:getDictByType('FLAG')}" var="dict">
									       <option value="${dict.value }"  <c:if test="${dict.value==turnOutStatus }">selected</c:if> >${dict.label }</option>
									    </c:forEach>
                                	</select>
                                </div>
                                
                                 <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">对方账号：</label>
                                    <input type="text" class="form-control" id="otherAccount" name="otherAccount" value="${otherAccount}">
                                </div>
                                
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">批处理号：</label>
                                    <input type="text" class="form-control" id="batchNum" name="batchNum" value="${batchNum}">
                                </div>
                                  <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">流水号：</label>
                                    <input type="text" class="form-control" id="serialNum" name="serialNum" value="${serialNum}">
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出金额最小：</label>
                                    <input type="text" class="form-control" id="transferMoneyMin" name="transferMoneyMin" value="${transferMoneyMin}"> 
                                </div>
                                <div class="form-group col-md-4 mB15">
                                    <label for="a" class="col-label">转出金额最大：</label>
                                    <input type="text" class="form-control" id="transferMoneyMax" name="transferMoneyMax" value="${transferMoneyMax}">
                                </div>
                            
                                <div class="form-group col-md-4 mB15">
                                	<button type="submit" class="btn btn-success speBtn">查 询</button>
                                	<a href="javascript:cancel()"  class="btn btn-success speBtn">批量撤销转账</a>
                                	
                                	<a href="javascript:self.location=document.referrer;"  class="btn btn-success speBtn">返回上一页</a>
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
                                        	<th>
			                                  <label for="checkAllBtn" class="check-label">
			                                      <input type="checkbox" name="checkAllBtn" id="checkAllBtn" >
			                                  </label>
				                             </th>
                                            <th>序号</th>
                                            <th>账号</th>
                                            <th>对方账号</th>
											<th>转入余额</th>
											<th>流水号</th>
											<th>批处理号</th>
											<th>转出状态</th>
											<th>创建时间</th>
											<th>备注</th>
<!-- 											<th>操作</th> -->
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${page.result}" var="list" varStatus="status">
		                                    <tr>
			                                   <th>
			                                       <label class="check-label">
			                                           <input type="checkbox" class="checkAllAccountSerialNum" name="checkAll" value="${list.serialNum}">
			                                       </label>
			                                   </th>
                                            <td>${ status.count}</td>
											<td>${ list.account}</td>
											<td>${ list.otherAccount}</td>
											<td>${ list.transferMoney}</td>
											<td>${ list.serialNum}</td>
											<td>${ list.batchNum}</td>
											<td>${dic:getDictLabel(list.turnOutStatus, 'FLAG', '-')}</td>
											<td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        	<td>${list.memo }</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/alabaoFinace/findTurnToAlabao?account=${account }"></tags:page>
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