<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>积分添加</title>
	 <script type="text/javascript">
	 
	 /**添加操作*/
		function addEpoint(){
			var order_code =$.trim($("#orderCode").val());
			var sku_code =$.trim($("#skuCode").val());
			var sku_name =$.trim($("#skuName").val());
			var order_status =$.trim($("#orderStatus").val());
			var total_epoint_used =$.trim($("#totalEpointUsed").val());
			
			$.post('${pageContext.request.contextPath}/integral/addEpont',
					{	order_code:orderCode,
						sku_code:skuCode,
						sku_name:skuName,
						order_status:orderStatus,
						total_epoint_used:totalEpointUsed
					},
				function(data){
					if (data.result == "success") {
						jBox.tip("添加成功!", 'info');
						window.setTimeout(function () {  
							window.parent.window.location.reload();
							window.parent.window.jBox.close();
							
						}, 500);
					}else{
						jBox.tip("添加失败!", 'info');
						window.setTimeout(function () { 
							window.parent.window.location.reload();
							window.parent.window.jBox.close();
						}, 500);
					}
			});
			
		}
	 </script>
</head>
 <body id="index"  style="background:#fff">
   	<tags:message content="${message }"></tags:message>
        <div class="container-fluid" >
            <div class="row">

			  <div class="col-md-10 content" >
			  
				      <form class="form-horizontal" method="post"  id="handleForm">
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">订单编号:</label>
							<div class="col-sm-4">
								<input name="order_code" id="orderCode" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">商品编码:</label>
							<div class="col-sm-4">
								<input name="sku_code" id="skuCode" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">商品名称:</label>
							<div class="col-sm-4">
								<input name="sku_name" id="skuName" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">商品状态:</label>
							<div class="col-sm-4">
								<input name="order_status" id="orderStatus" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						<div class="form-group mL0 mR0">
							<label class="col-sm-4 control-label">使用积分：</label>
							<div class="col-sm-4">
								<input name="total_epoint_used" id="totalEpointUsed" type="text" class="form-control" />
							</div>
							<div  class="error warning col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
					    	<div class="col-sm-offset-2 col-sm-10">
					      		<button class="btn btn-success col-md-2" type="button"  onclick="addEpoint()">确定</button>
					   	 	</div>
						</div>
					</form>
       		</div>
       	  </div>
       	</div>
   </body>
</html>