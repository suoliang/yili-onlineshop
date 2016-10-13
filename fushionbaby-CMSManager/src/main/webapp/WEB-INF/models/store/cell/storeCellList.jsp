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
        <title>门店小区列表</title>
        <script type="text/javascript">
        	
        
	        $(function(){	
	        		
	        	 var url =contextPath + "/store/cell/uploadProvince";
        		
	       		 $.post(url,{time : new Date().getTime()},function(data){
	       			 for(var i = 0 ; i < data.length;i++){
	       				var item = data[i];
       				 	var option = document.createElement("OPTION");
       	        		option.innerHTML = item.name;
       	        		option.value = item.code;
       	        		option.obj = item;
       	        		document.getElementById("editAddress_province").options.add(option);
	       			 }
	       			 
		       	});
	        	 
	        });	
        
        
        	function changeProvince(){
        		
        		var parentCode = $("#editAddress_province").val();
        		if(parentCode=="" || parentCode=="0"){
        			return;
        		}
        		
        		var url =contextPath + "/store/cell/uploadArea?parentCode="+parentCode;
        		
	       		 $.post(url,{time : new Date().getTime()},function(data){
	       			 
	       			 
	       			document.getElementById("editAddress_city").options.length = 1;
	       			 for(var i = 0 ; i < data.length;i++){
	       				var item = data[i];
      				 	var option = document.createElement("OPTION");
      	        		option.innerHTML = item.name;
      	        		option.value = item.code;
      	        		option.obj = item;
      	        		document.getElementById("editAddress_city").options.add(option);
	       			 }
	       			 
		       	});
        		 
        		
        	}
        function changeCity(){
        	
        	var parentCode = $("#editAddress_city").val();
    		if(parentCode=="" || parentCode=="0"){
    			return;
    		}
    		
    		var url =contextPath + "/store/cell/uploadArea?parentCode="+parentCode;
    		
       		 $.post(url,{time : new Date().getTime()},function(data){
       			 
       			 
       			document.getElementById("editAddress_district").options.length = 1;
       			 for(var i = 0 ; i < data.length;i++){
       				var item = data[i];
  				 	var option = document.createElement("OPTION");
  	        		option.innerHTML = item.name;
  	        		option.value = item.code;
  	        		option.obj = item;
  	        		document.getElementById("editAddress_district").options.add(option);
       			 }
       			 
	       	});
        }
        
     
        
	        function modiftCell(id){
	        	
	        	window.location.href="${contextPath}/store/cell/editPage?id="+id;
	        }
	        
	        function query(){
	        	$("#findForm").submit();
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>小区列表</h3>
                        </div>
                           <div class="panel-body">
	                          <form class="form-inline page" id="findForm" method="post">
	                          
	                              <input type="hidden" id="queryType" name="queryType" value="" />
                                
		                          <div class="form-group col-md-4 mB15">
										<label class=" control-label">省：</label>
											<select name="provinceCode" id="editAddress_province" onchange="changeProvince()"
											  class="form-control lg-select" data-placeholder="Choose a province" tabindex="1">
								  	      		<option value="">请选择</option>
								  	        </select>
									</div>
									<div class="form-group col-md-4 mB15">
										<label class=" control-label">市：</label>
											<select name="cityCode" id="editAddress_city" onchange="changeCity()"
											class="form-control lg-select" data-placeholder="Choose a city" tabindex="1">
								       	    	<option value="">请选择</option>
								       	    </select>
									</div>
									<div class="form-group col-md-4 mB15">
										<label class=" control-label">县/区：</label>
											<select name="countryCode" id="editAddress_district" class="form-control lg-select" data-placeholder="Choose a district" tabindex="1">
								           		<option value="">请选择</option>
								            </select>
									</div>
									<div class="form-group col-md-4 mB15">
										<label class=" control-label">小区：</label>
											<input type="text" class="form-control" id="cellName" name="cellName" value="${storeCell.cellName}" placeholder="请输入小区名称">
									</div>
									
									<div class="form-group col-md-4 mB15">
										<label class=" control-label">小区状态：</label>
										<select name="isDisable" class="form-control lg-select">
										   <option value="">全部</option>
										    <option value="y"  <c:if test="${storeCell.isDisable eq 'y'}">selected="selected" </c:if> >禁用</option>
										    <option value="n"  <c:if test="${storeCell.isDisable eq 'n'}">selected="selected" </c:if> >启用</option>
										</select>
									</div>
	                               
	                                <div class="form-group col-md-4 mB15">
	                                   <a href="javascript:query()"  class="btn btn-success speBtn" >查询</a>
	                                   <a href="${contextPath}/store/cell/editPage" class="btn btn-info speBtn">新增</a>
	                                </div>
	                                <div class="clearfix"></div>
	                                <input type="hidden" name="currentPage" value="${page.currentPage}"/>
									<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
									<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
									<input type="hidden" name="total" value="${page.total}"/>
	                            </form>
		                        <div class="panel-body">
		                            <!-- table -->
		                            <div class="table-responsive">
		                                <table class="table table-bordered ta ble-hover" id="roleTable">
		                                    <thead>
		                                        <tr>
				                                    <th>序号</th>
													<th>省</th>
													<th>市</th>
													<th>县/区</th>
													<th>小区编号</th>
													<th>小区名称</th>
													<th>创建时间</th>
													
													<th>操作</th>
		                                        </tr>
		                                    </thead>
		                                    <tbody>
		                                    <c:forEach items="${storeCellDtoList}" var="dto" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${dto.provinceName}</td>
													<td>${dto.cityName}</td>	
													<td>${dto.countryName}</td>
													<td>${dto.cellCode }</td>
													<td>${dto.cellName }</td>
													<td>${dto.createTime }</td>

		            						        <td><button type="button"  class="btn btn-success btn-xs delete-role" onclick="modiftCell(${dto.id})">修改</button></td>
		                                        </tr>
		                                    </c:forEach>
		                                    </tbody>
		                                </table>
		                            </div>
		                              <!-- 分页 -->
		                             <tags:page formId="findForm" url="${contextPath}/store/cell/findCellList"></tags:page>
		                            <!-- 分页 end -->
		                            
		                        </div>
                        <!-- /.panel-body -->
                           </div>
                        </div>
                <!-- /.content -->
                   </div>
               </div>
        <!-- /.container-fluid -->
           </div>
     
     
    </body>
</html>