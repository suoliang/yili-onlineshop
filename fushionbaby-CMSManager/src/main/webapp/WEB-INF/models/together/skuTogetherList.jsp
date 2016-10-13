<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>团购管理</title>
	<script src="${pageContext.request.contextPath}/fushionbaby/js/order/order_manage_list.js" type="text/javascript"></script>
  	<script type="text/javascript">
	  
	  	function edit(id){
	  		
	  		window.location.href = "${contextPath}/skuTogether/skuTogetherDetail/"+id;
	  	}
	  	
	  	function querySkuTogether(){
	  		
	  		$("#findForm").submit();
	  	}
	  	
		function addSkuTogether(){
	  		
			window.location.href = "${contextPath}/skuTogether/toAddSkuTogether";
	  	}
	  	
	  	function findEditArea(area_type,area_code,default_code){
	  		
	  		$.post('${pageContext.request.contextPath}/order/find_area_list', {
	  			code :area_code,
	  			time : new Date().getTime()
	  		}, function(data) {
	  			var init_id="#editAddress_province";
	  			if(area_type==1){
	  				//省
	  				init_id="#editAddress_province";
	  				$("#editAddress_province").empty();//清空
	  				$("#editAddress_province").append("<option value=''>请选择</option>");
	  			}else if(area_type==2){
	  				//市
	  				init_id="#editAddress_city";
	  				$("#editAddress_city").empty();//清空
	  				$("#editAddress_city").append("<option value=''>请选择</option>");
	  			}else if(area_type==3){
	  				//区/县
	  				init_id="#editAddress_district";
	  				$("#editAddress_district").empty();//清空
	  				$("#editAddress_district").append("<option value=''>请选择</option>");
	  			}
	  			
	  			var len=data.length;
	  			for(i=0;i<len;i++){
	  				var areaObj=data[i];
	  				if(default_code==areaObj.code){
	  					$(init_id).append("<option selected='selected' value='"+areaObj.code+"'>"+areaObj.name+"</option>");
	  				}else{
	  					$(init_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
	  				}
	  			}
	  		});// ajax管理员取消订单请求end
	  	}
	  	
		//订单地址,市
		function findAreaList(obj){
			var obj_id=obj.id;
			var code=obj.value;
			var find_id="editAddress_province";
			if(obj_id=="editAddress_province"){
				//省发生改动,清空原有的市,区/县
				$("#editAddress_city").empty();//清空,原有的市
				$("#editAddress_district").empty();//清空
				$("#editAddress_city").append("<option value=''>请选择</option>");
				$("#editAddress_district").append("<option value=''>请选择</option>");
				
				find_id="editAddress_city";//加载省旁边的市,下拉框
			}else if(obj_id=="editAddress_city"){
				//市发生改动,清空原有的,区/县
				$("#editAddress_district").empty();//清空,原有的区县
				$("#editAddress_district").append("<option value=''>请选择</option>");
				find_id="editAddress_district";
			}
			
			$.post('${pageContext.request.contextPath}/order/find_area_list', {
				code :code,
				time : new Date().getTime()
			}, function(data) {
				if (data == null || data == "" || data == undefined) {
					return;
				}
				var len=data.length;
				for(i=0;i<len;i++){
					var areaObj=data[i];
					$("#"+find_id).append("<option value='"+areaObj.code+"'>"+areaObj.name+"</option>");
				}
			});// ajax管理员取消订单请求end
		}
	  	window.onload=function(){
	  		var province=$("#province").val();
		  	var city=$("#city").val();
		  	var district=$("#district").val();
		  	findEditArea(1,0,province);
			findEditArea(2,province,city);
			findEditArea(3,city,district);
		};
		
		function deleteTogether(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				var url ="${contextPath}/skuTogether/togetherDelete/"+id; 
	  				$.post(url,function(msg){
	  					
	  					if(msg!="SUCCESS"){
	  						jBox.tip("删除失败！", 'error');
		  		    	 	return;
	  					}
	  					jBox.tip("删除成功!", 'info');
 		  		       	window.setTimeout(function () {  location.reload(); }, 1500);
		  		      
	  				});
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该团购吗？", "删除提示",submit);
	  	}
		
	$(function() {	
		$('.timeS2').change(function(){
			var timeS_val2 = $(this).val();
			$('.timeE2').datetimepicker('setStartDate', timeS_val2);
		});
	    $('.timeE2').change(function(){
			var timeE_val2 = $(this).val();
			$('.timeS2').datetimepicker('setEndDate', timeE_val2);
		});
	})
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 团购列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm" method="post" action="${contextPath }/skuTogether/skuTogetherList">
				     
					   <div class="form-group col-md-4 mB15">
	    					<label for="code" class="col-label">商品编码：</label>
	      					<input type="text" id="skuCode" name="skuCode" class="form-control" value="${skuTogetherDto.skuCode}"
	      						 placeholder="商品编码">
	  					</div>
				    
					    <div class="form-group col-md-4 mB15">
	    					<label for="name" class="col-label">商品名称：</label>
	      					<input type="text" id="skuName" name="skuName" class="form-control" value="${skuTogetherDto.skuName}"  
	      						placeholder="商品名称" >
	  					</div>
	  					
				   		<div class="form-group col-md-4 mB15">
    						<label class="col-label">省：</label>
      						<select name="province" id="editAddress_province" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
					        </select>
    					</div>
    					<input type="hidden" id="province" value="${skuTogetherDto.province}">
    					<div class="form-group col-md-4 mB15">
    						<label class="col-label">市：</label>
      						<select name="city" id="editAddress_city" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
					        </select>
    					</div>
    					<input type="hidden" id="city" value="${skuTogetherDto.city}">
    					<div class="form-group col-md-4 mB15">
    						<label class="col-label">县/区：</label>
      						<select name="district" id="editAddress_district" onchange="findAreaList(this)" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
					        </select>
    					</div>
	    				<input type="hidden" id="district" value="${skuTogetherDto.district}">
	    				<div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">团购开始起始时间：</label>
                             <div class="input-group">
                                   <input type="text" name="beginTimeFrom" class="timeS form-control form_datetime" value="${skuTogetherDto.beginTimeFrom}" readonly>
                                   <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                       		 </div>
                      	</div>
                        <div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">团购开始结束时间：</label>
                             <div class="input-group">
                                <input type="text" name="beginTimeTo" class="timeE form-control form_datetime" value="${skuTogetherDto.beginTimeTo}" readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
                        </div>
                        <div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">团购结束起始时间：</label>
                             <div class="input-group">
                                   <input type="text" name="endTimeFrom" class="timeS2 form-control form_datetime" value="${skuTogetherDto.endTimeFrom}" readonly>
                                   <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                       		 </div>
                      	</div>
                        <div class="form-group col-md-4 mB15">
                             <label for="a" class="col-label">团购结束结束时间：</label>
                             <div class="input-group">
                                <input type="text" name="endTimeTo" class="timeE2 form-control form_datetime" value="${skuTogetherDto.endTimeTo}" readonly>
                                <div class="input-group-addon form_datetime_addon"><i class="fa fa-times"></i></div>
                             </div>
                        </div>
	    				
					    <div class="col-md-4">
	                        <button type="button" class="btn btn-info" onclick="querySkuTogether()">确认查询</button>
	                        <button type="button" class="btn btn-info" onclick="addSkuTogether()">添加团购商品</button>
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
							  <th>团购商品编码</th>
							  <th>团购商品名称</th>
							  <th>团购地</th>
							  <th>团购开始时间</th>
							  <th>团购结束时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${page.result}" var="tmp" varStatus="status">
								<tr>
								   <td>${status.count}</td>	
								   <td>${tmp.skuCode}</td>	
								   <td>${tmp.skuName}</td>	
								   <td>${tmp.province}${tmp.city}${tmp.district}</td>	
								   <td>${tmp.beginTime}</td>
								   <td>${tmp.endTime}</td>
								   <td>													
									 <a class="btn btn-default btn-xs edit-role" href="${contextPath}/skuTogether/togetherModify/${tmp.id}" title="修改">	
										修改
									 </a>
									 <a class="btn btn-default btn-xs edit-role" href="javascript:deleteTogether(${tmp.id})" title="删除">	
										删除
									 </a>
									  <a class="btn btn-default btn-xs edit-role" href="${contextPath}/skuTogether/togetherDetail/${tmp.id}" title="团购详情">	
										团购详情
									 </a>	
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
							<tags:page formId="findForm" url="${contextPath }/skuTogether/skuTogetherList"></tags:page>
                            <!-- 分页 end -->
                        </div>
					</div>
                </div>
                <!-- /.content -->
            </div>
		</div>
       
        <!-- /.container-fluid -->
</body>
</html>
