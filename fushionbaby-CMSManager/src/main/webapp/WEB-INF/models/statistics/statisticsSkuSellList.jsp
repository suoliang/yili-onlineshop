<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品销售统计</title>
</head>
<body id="index">
		<tags:message content="${message}"></tags:message>

        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品销售统计列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
	  					<div class="form-group col-md-4 mB15">
                            <label for="a">商品编码：</label>
                            <input type="text" name="skuNo" value="${skuNo}" class="form-control" id="" placeholder="请输入商品编码">
                        </div>
                        <div class="form-group col-md-4 mB15">
                            <label for="a">商品名称：</label>
                            <input type="text" name="skuName" value="${skuName}" class="form-control" id="" placeholder="请输入商品名称">
                        </div>
	  					<div class="form-group col-md-4 mB15">
                            <label for="a">开始时间：</label>
                            <div class="input-group">
                             <input type="text" name="createTimeFrom" value="${createTimeFrom}" class="timeS form-control form_datetime" readonly>
                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                        	</div>
                        </div>
                        <div class="form-group col-md-4 mB15">
                            <label for="a">结束时间：</label>
                            <div class="input-group">
                             <input type="text" name="createTimeTo" value="${createTimeTo}" class="timeE form-control form_datetime" readonly>
                             <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                        	</div>
                        </div>
	  					
					    <div class="col-md-4">
	                        <button type="submit" class="btn btn-info" >确认查询</button>
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
							  <th>商品编码</th>
							  <th>条形码</th>
							  <th>商品名称</th>
							  <th>销售数量</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${statisticsSkuSellList}" var="skuSell" varStatus="status">
								<tr>
								   <td scope="row">${status.count}</td>
								   <td>${skuSell.skuNo}</td>	
								   <td>${skuSell.skuBarCode}</td>	
								   <td>${skuSell.skuName}</td>	
								   <td>${skuSell.quantity}</td>
								</tr>
							  </c:forEach>
                            </tbody>
                        </table>
                           </div>
                           <tags:page formId="findForm" url="${contextPath}/statisticSku/statisticsSkuSellList"></tags:page>
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
        <!-- /.container-fluid -->
</body>
</html>
