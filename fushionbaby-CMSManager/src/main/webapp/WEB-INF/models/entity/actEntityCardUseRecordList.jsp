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
        <title>实体卡消费列表</title>
    </head>
    <script type="text/javascript">
    

    function Edit(id){
    	window.location.href = "${contextPath}/actEntityCardUseRecord/actEntityCardUseRecordEditJsp/"+id+"/"+new Date().getTime();
    }
    
    
    function  Del(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/actEntityCardUseRecord/actEntityCardUseRecordDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除吗？", "删除提示",submit);
    }
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>实体卡消费列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
                                
							    <%-- <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">订单号：</label>
			      					<input type="text" id="orderCode" name="orderCode" class="form-control" value="${orderCode}"  
			      						placeholder="订单号" >
			  					</div> --%>
			  					
			  						<div class="form-group col-md-4 mB15">
			  						<label for="name" class="col-label">门店：</label>
                                     <select name="storeCode"  id="storeCode" class="form-control lg-select">
                                     
										  <option value="" >所有</option>
										  <c:forEach items="${storeList}" var="store">
											  <option value="${store.code}" ${queryDto.storeCode==store.code?'selected':''}> ${store.name}</option>
										  </c:forEach>
									  </select>
                                </div>
			  					
			  					
							    <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">卡号：</label>
			      					<input type="text" id="cardNo" name="cardNo" class="form-control" value="${queryDto.cardNo}"  
			      						placeholder="卡号" >
			  					</div>
			  					
			  					
			  					 <div class="form-group col-md-4 mB15">
			  						<label for="name" class="col-label">类型：</label>
                                    <select name="useType"  class="form-control lg-select">
                                       <option value="">--请选择--</option>
                                       <option value="2" ${queryDto.useType=='0'?'selected':'' }>消费</option>
                                       <option value="1" ${queryDto.useType=='1'?'selected':'' }>充值</option>
                                    </select>
                                </div>
			  					
								
								
			  					 <div class="form-group col-md-4 mB15">
			  						<label for="name" class="col-label">来源：</label>
                                    <select name="useSource"  class="form-control lg-select">
                                       <option value="">--请选择--</option>
                                       <option value="0" ${queryDto.useSource=='0'?'selected':'' }>实体店</option>
                                       <option value="1" ${queryDto.useSource=='1'?'selected':'' }>商城</option>
                                       <option value="2" ${queryDto.useSource=='2'?'selected':'' }>IOS</option>
                                       <option value="3" ${queryDto.useSource=='3'?'selected':'' }>Android</option>
                                    </select>
                                </div>
								
                                <div class="form-group col-md-4 mB15">
                                <button type="submit" class="btn btn-success speBtn">查 询</button>
                                </div>
                               
                                <div class="clearfix"></div>
                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
								<input type="hidden" name="totalPage" value="${page.totalPage}"/>  
								<input type="hidden" name="limit" value="${page.limit}"/>
								<input type="hidden" name="total" value="${page.total}"/>
                            </form>
                            <!-- table -->
                            <div class="table-responsive">
                                <table class="table table-bordered table-hover" id="roleTable">
                                    <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>卡号</th>
                                            <th>所属门店名称</th>
                                            <th>金额</th>
                                          <!--   <th>订单号</th> -->
                                            <th>来源</th>
                                            <th>类型</th>
                                            <th>操作人</th>
                                            <th>时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            
	                                            <td>${list.cardNo}</td>
	                                            <td>${list.storeName}</td>
	                                            <td>${list.money}</td>
	                                        <%--     <td>${list.orderCode}</td> --%>
	                                            <td>
	                                            	<c:if test="${list.useSource eq '0' }">实体店</c:if>
											    	<c:if test="${list.useSource eq '1' }">网站</c:if>
	                                            </td>
	                                            <td>
	                                            	<c:if test="${list.useType eq '2' }">消费</c:if>
											    	<c:if test="${list.useType eq '1' }">充值</c:if>
	                                            </td>
	                                            <td>${list.updateName}</td>
	                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/actEntityCardUseRecord/actEntityCardUseRecordList"></tags:page>
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