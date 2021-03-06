<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>商品管理</title>
  	<script type="text/javascript">
	 	function delSku(skuCode, labelCode){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				$.post("${contextPath }/skuLabel/deleteSkuLabelRelation",{skuCode: skuCode, labelCode: labelCode },function(result){
	  		  			if(result =="SUCCESS"){	
	  		  			jBox.tip("删除成功", 'info');
	  		  				
	  		  			// 3秒后完成操作
	  		  			window.setTimeout(function () {  location.reload(); }, 1500);
	  		  			}else{
	  		  				jBox.tip("删除失败", 'info');
	  		  			}
	  		  			
	  		          });
	  			}
	  			
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该产品吗？", "删除提示",submit);

	  	}
	  	
	  	function showAddSkuLabel(labelCode){
	  		var url = "iframe:${contextPath}/skuLabel/addNoRelationSku?labelCode=" + labelCode;
			$.jBox(url, {
	  		    title: "商品标签新增关联商品", width: 980,height: 700,
	  		    buttons: { '确认添加' : true, '关闭': false},
	  		    submit: function (v, h, f) {
	              if(v){
	            	  var contents = h.find("iframe").contents();
		              var url= "${contextPath}/skuLabel/addSkuByLabel";
		              var labelCode = contents.find("input[name='labelCode']").val();
		              var skuCodes = "";
		              contents.find('input[name="checkSku"]:checked').each(function(){ 
		            	  skuCodes += $(this).val() + ","; 
		            	 }); 
		              if(skuCodes == ""){
		            	  var submit2 =  function(v,h,f){
		      	  			if(v=="ok"){
		      	  				showAddSkuLabel($("#labelCode").val());
		      	  				return true;
		      	  			}
		      	  		}
		            	  $.jBox.confirm("当前没有选中任何商品，是否重新选取？", "操作提示",submit2);
		            	  return;
		              }
		              
		              skuCodes = skuCodes.substring(0,skuCodes.length - 1);
		              $.post(url, {labelCode:labelCode,skuCodes:skuCodes},function(result){
		            	  if(result == "SUCCESS"){
		            		  	jBox.tip("添加关联商品成功", 'info');
		  		  				
			  		  			// 3秒后完成操作
			  		  			window.setTimeout(function () {  location.reload(); }, 1500);
			                }else{
			                	jBox.tip("添加关联商品失败", 'info');
			                }
		              });
	              }
	  		  	}
			});
	  	}
	  	
	  	function editSkuRel(skuCode, labelCode){
	  		
	  		var url = "iframe:${contextPath}/skuLabel/skuLabelRelationEditModel/"+labelCode+"/"+skuCode;
	  		
	  		$.jBox(url, {
	  		    title: "编辑", width: 400,height: 200,
	  		    buttons: { '确定':true,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	              var contents = h.find("iframe").contents();
 	              var url = contents.find("#editForm").attr("action");
 	              var params = contents.find("#editForm").serialize() + "&datetime="+new Date().getTime();

	              $.post(url, params,function(msg){
	            	  if(msg == 'SUCCESS'){
	            		  jBox.tip("编辑成功", 'info');
		  		  			// 3秒后完成操作
		  		  			window.setTimeout(function () {  location.reload(); }, 1500);
	            	  }else{
	            	  	//window.location.href="${contextPath}/skuImage/findSkuImage/"+skuCode;
	            		  jBox.tip("编辑失败", 'info');
	            	  }
	              });

	  		  	}
			});
	  		
	  		
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
                      <h3 class="panel-title"><i class="fa fa-cog"></i> ${labelName }商品列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post">
				     	<div class="form-group col-md-4 mB15">
                            <label for="a">商品编码：</label>
                            <input type="text" class="form-control" id="skuNo" name="skuNo" value="${skuDto.skuNo}" placeholder="请输入商品编码">
                        </div>
                        <div class="form-group col-md-4 mB15">
                            <label for="a">商品条形码：</label>
                            <input type="text" class="form-control" id="barCode" name="barCode" value="${skuDto.barCode}" placeholder="请输入商品条形码">
                        </div>
                        <div class="form-group col-md-4 mB15">
                            <label for="c">商品名称：</label>
                            <input type="text" class="form-control" id="skuName" name="skuName" value="${skuDto.skuName}" placeholder="请输入商品名称">
                        </div>
                        <button type="submit" class="btn btn-success speBtn">查询</button>
	                    <button type="button" class="btn btn-info" onclick="showAddSkuLabel('${CURRENT_LABELD_CODE}')" >新增关联商品</button>
				    	
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
							  <th>商品状态</th>
							  <th>显示顺序</th>
							  <th>会员商品</th>
							  <th>是否显示</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:if test="${not empty relDtos}">	
	                              <c:forEach items="${relDtos}" var="sku" varStatus="status">
									<tr>
									   <td scope="row">${status.count}</td>
									   <td>${sku.skuNo}</td>
									   <td>${sku.barCode }</td>	
									   <td>${sku.skuName}</td>	
									   <td>
									   		${dic:getDictLabel(sku.skuStatus, 'SKU_STATUS', '-')}
									   </td>	
									   <td>${sku.showOrder}</td>	
									   <td>${sku.isMemberSku }</td>
									   <td>${dic:getDictLabel(sku.disabled,'DISABLE','-')}</td>
									   <td><fmt:formatDate value="${sku.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									   <td>
									   	  <a class="btn btn-info btn-xs delete-role"  href="javascript:editSkuRel('${sku.unCode}', '${CURRENT_LABELD_CODE}');"   title="编辑">	
										          编辑
										 </a>
										 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delSku('${sku.unCode}', '${CURRENT_LABELD_CODE}')"   title="删除">	
										          删除
										 </a>
										 <input type="hidden" id="labelCode" value="${CURRENT_LABELD_CODE}">
									   </td>	
									  </tr>
									 </c:forEach>
								</c:if> 
                                </tbody>
                              </table>
                           </div>
                           <div class="form-group mL0 mR0">
						      	    <button class="btn btn-primary" type="button" onclick="javascript:location='${contextPath}/skuLabel/skuLabelList'">返回</button>
						   </div>
                           <tags:page formId="findForm" url="${contextPath}/skuLabel/skuListByLabel"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
</body>
</html>
