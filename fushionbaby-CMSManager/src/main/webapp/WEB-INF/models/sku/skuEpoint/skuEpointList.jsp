<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>积分商品列表</title>
  	<script type="text/javascript">
  		function goAddEpointSku(){
  			var url = "iframe:${contextPath}/skuEpoint/goAddEpointSku";
			$.jBox(url, {
	  		    title: "新增积分商品页面", width: 800,height: 400,
	  		    buttons: { '关闭': false }
			});
  		}
  		
  		function editEpointSku(id){
  			window.location.href = "${contextPath}/skuEpoint/editEpointSku/"+ id + "/" + new Date().getTime();
  		}
  		
  		function delEpointSku(id){
  			var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/skuEpoint/delEpointSku/"+ id + "/" + new Date().getTime();
	  		} 
	  		
  		}
	  		$.jBox.confirm("你确定要添加该积分商品吗？", "删除提示",submit);
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
                   <h3 class="panel-title"><i class="fa fa-cog"></i>积分商品列表</h3>
                </div>

                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/skuEpoint/skuEpointList">

				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">商品标签：</label>
      						<select name="labelCode" class="form-control lg-select" data-placeholder="Choose a Category" tabindex="1">
					           	<option value="">所有</option>
					            <option value="1" <c:if test="${labelCode=='1' }">selected="selected"</c:if>  >热门兑换</option>
					            <option value="2" <c:if test="${labelCode=='2' }">selected="selected"</c:if>  >可以兑换</option>
					        </select>
    					</div>
					    <div class="col-md-4">
					    	<button type="button" class="btn btn-primary speBtn" onclick="goAddEpointSku()">新增</button>
	                        <button type="submit" class="btn btn-info">确认查询</button>
	                        
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
							  
							  <th>商品名称</th>
							  <th>需要积分</th>
							  <th>商品标签</th>
							  <th>创建时间</th>
							  <th>修改时间</th>
							  <th>操作</th>
							  
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${list}" var="list" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
								  
								   <td>${list.skuName}</td>	
								   <td>${list.needEpoint}</td>	
								   <td>	
									   <c:if test="${list.labelCode eq '1'}"> 热门兑换</c:if>
									   <c:if test="${list.labelCode eq '2'}"> 可以兑换</c:if>
								   </td>
								   <td><fmt:formatDate value="${list.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td><fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td>
								   		<button type="button" class="btn btn-default btn-xs edit-role" onclick="editEpointSku('${list.id}')">编辑</button>
                                        <button type="button" class="btn btn-danger btn-xs delete-role" onclick="delEpointSku('${list.id}')">删除</button>
								   </td>
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/skuEpoint/skuEpointList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
            </div>
        </div>
        <!-- /.container-fluid -->
</body>
</html>
