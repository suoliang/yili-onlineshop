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
        <title>实体卡列表</title>
    </head>
    <script type="text/javascript">
    

/*     function Edit(id){
    	window.location.href = "${contextPath}/actEntityCard/actEntityCardEditJsp/"+id+"/"+new Date().getTime();
    } */
    
    
/*     function  Del(id){
		
  		var submit =  function(v,h,f){
  			if(v=="ok"){
  				window.location.href = "${contextPath}/actEntityCard/actEntityCardDel/"+id+"/"+new Date().getTime();
  			}
  			return true;
  		} 
  		$.jBox.confirm("你确定要删除吗？", "删除提示",submit);
    } */
    </script>
    
    <body id="" class="Cog">
           <tags:message content="${message}"></tags:message>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12 content">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-user"></i>实体卡列表 </h3>
                        </div>
                        <div class="panel-body">
                            <form class="form-inline page" id="findForm" method="post">
			  					 <div class="form-group col-md-4 mB15">
			  						<label for="name" class="col-label">状态：</label>
                                    <select name="status"  class="form-control lg-select">
                                       <option value="">--请选择--</option>
                                       <option value="0" ${queryDto.status=='0'?'selected':'' }>未卖出</option>
                                       <option value="1" ${queryDto.status=='1'?'selected':'' }>已卖出</option>
                                       <option value="2" ${queryDto.status=='2'?'selected':'' }>注销</option>
                                    </select>
                                </div>
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
			  						<label for="name" class="col-label">实体卡类型：</label>
                                     <select name="configId"  id="configId" class="form-control lg-select">
                                     
										  <option value="" >所有</option>
										  <c:forEach items="${configList}" var="config">
											  <option value="${config.id}" ${queryDto.configId==config.id?'selected':''}> ${config.name}</option>
										  </c:forEach>
									  </select>
                                </div>
              
							    <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">卡号：</label>
			      					<input type="text" id="cardNo" name="cardNo" class="form-control" value="${queryDto.cardNo}"  
			      						placeholder="卡号" >
			  					</div>
								
							    <div class="form-group col-md-4 mB15">
			    					<label for="name" class="col-label">面值：</label>
			      					<input type="text" id="faceMoney" name="faceMoney" class="form-control" value="${queryDto.faceMoney}"  
			      						placeholder="面值" >
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
	                                        <th>卡名称</th>
	                                        <th>卡号</th>
	                                        <th>原始面值</th>
	                                        <th>剩余金额</th>
	                                        <th>相关门店名称</th>
	                                        <th>创建时间</th>
	                                        <th>状态</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${list}" var="list" varStatus="status">
                                    	<tr>
                                            <td>${status.count}</td>
                                            <td>${list.configName}</td>
                                            <td>${list.cardNo}</td>
                                            <td>${list.faceMoney}</td>
                                            <td>${list.surplusMoney}</td>
                                            <td>${list.storeName}</td>
                                            <td><fmt:formatDate value="${list.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                            <td>
                                            	<c:if test="${list.status==0}">未卖出</c:if>
                                            	<c:if test="${list.status==1}">已卖出</c:if>
                                            	<c:if test="${list.status==2}">注销</c:if>
                                            </td>
                                            <%-- <td>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:Edit('${list.id}');">修改</button>
                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="javascript:Del('${list.id}');">删除</button>
                                            </td> --%>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <!-- 分页 -->
                             <tags:page formId="findForm" url="${contextPath}/actEntityCard/actEntityCardList"></tags:page>
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