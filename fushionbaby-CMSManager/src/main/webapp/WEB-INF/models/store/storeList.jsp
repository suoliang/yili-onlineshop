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
        <title>门店列表</title>
        <script type="text/javascript">
          function disableStore(code){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/store/disableStore/"+code+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要改变门店状态吗？", "提示",submit);
        }; 
        
        
        function updateStore(code){
        	window.location.href = "${contextPath}/store/goEdit?code="+code+"&time="+new Date().getTime();
        }
        
        function editStoreExtraInfo(storeCode){
        	window.location.href = "${contextPath}/storeExtraInfo/editStoreExtraInfo/"+storeCode+"/"+new Date().getTime();
        }
        
        function updateStatus(id){
        	var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href = "${contextPath}/user/updateUserStatus/"+id+"/"+new Date().getTime();
	  			}
	  			return true;
	  		}
	  		$.jBox.confirm("你确定要修改该用户状态吗？", "修改提示",submit);
        }
        
        
        function import_excel(){
        	var url = "iframe:${contextPath}/member/showImportMember";
			$.jBox(url, {
	  		    title: "EXCEL批量导入门店", width: 400,height: 200,
	  		    buttons: { '关闭': false },
			});
			
		}
        
    	/** 导入小区*/
    	function importCell(){
    		
    		var url = "iframe:${contextPath}/store/importCell";
    		$.jBox(url, {
      		    title: "使用EXCEL批量导入小区", width: 600,height: 400,
      		    buttons: { '关闭': false },
      		  	submit: function (v, h, f) {
                  if (v == 0) {
                	  return ;
                  }
      		  	}
    		});
    	}
    	
    	function testSku(storeCode){
    		
    		
    		
    		var url = "iframe:${contextPath}/store/sku/batchSkuModel?storeCode="+storeCode;
			$.jBox(url, {
	  		    title: "使用EXCEL批量导入商品信息", width: 600,height: 400,
	  		    buttons: { '关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	              }
	  		  	}
			});
    	}
    	
        </script>
        
    </head>
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
                            <h3 class="panel-title"><i class="fa fa-user"></i>门店列表</h3>
                        </div>
                           <div class="panel-body">
	                          <form class="form-inline page" id="findForm" method="post">
	                                <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">门店编号：</label>
	                                    <input type="text" class="form-control" id="number" name="number" value="${store.number}" placeholder="请输入门店编号">
	                                </div>
	                                
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">门店名称：</label>
	                                    <input type="text" class="form-control" id="name" name="name" value="${store.name}" placeholder="请输入门店名称">
	                                </div>
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">城市名称：</label>
	                                    <input type="text" class="form-control" id="cityName" name="cityName" value="${store.cityName}" placeholder="请输入城市名称">
	                                </div>
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">县级区域名称：</label>
	                                    <input type="text" class="form-control" id="countryName" name="countryName" value="${store.countryName}" placeholder="请输入县级区域名称">
	                                </div>
	                                
	                                 <div class="form-group col-md-4 mB15">
	                                    <label for="a" class="col-label">小区名称：</label>
	                                    <input type="text" class="form-control" id="cellName" name="cellName" value="${store.cellName}" placeholder="请输入小区名称">
	                                </div>
	                                
	                                <div class="form-group col-md-4 mB15">
										<label class=" control-label">运营状态：</label>
										<select name="status" class="form-control lg-select">
											<option value="">全部</option>
											<option value="1"  <c:if test="${store.status == '1'}">selected="selected" </c:if> >开通</option>
											<option value="2"  <c:if test="${store.status == '2'}">selected="selected" </c:if> >停用</option>
											<option value="4"  <c:if test="${store.status == '4'}">selected="selected" </c:if> >测试</option>
										</select>
									</div>
				                               
	                                <div class="form-group col-md-8 mB15">
	                                   <button type="submit" class="btn btn-success speBtn">查 询</button>
	                                   <a href="${contextPath}/store/goEdit" class="btn btn-info speBtn">新增</a>
	                                   
	                                  <!--  <button type="button" onclick="import_excel()" class="btn btn-warning speBtn">批量导入门店</button>
	                                   <button type="button" class="btn btn-info" onclick="importCell()">导入小区</button>
                        			   <button type="button" class="btn btn-info" onclick="downLoadExcel('cell-model.xls')">下载导入小区模板</button>
	                                   <button type="button" class="btn btn-info" onclick="downLoadExcel('store-sku-model.xlsx')">下载导入商品模板</button>
	                                    -->
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
													<th>门店编号</th>
													<th>门店名称</th>
													<th>省</th>
													<th>市</th>
													<th>县/区</th>
													<th>小区</th>
													<th>商品种数</th>
													<th>运营状态</th>
													<th>操作</th>
		                                        </tr>
		                                    </thead>
		                                    <tbody>
		                                    <c:forEach items="${storeDtoList}" var="dto" varStatus="status">
												<tr>
													<td>${status.count}</td>
													<td>${dto.number}</td>
													<td>${dto.name}</td>
													<td>${dto.provinceName}</td>
													<td>${dto.cityName}</td>	
													<td>${dto.countryName}</td>
													<td>${dto.cellName }</td>
													<td>${dto.skuNum }</td>
													<td>
														<c:if test="${dto.status == '1'}">开通</c:if>
														<c:if test="${dto.status == '2'}">停用</c:if>
														<c:if test="${dto.status == '4'}">测试</c:if>
													</td>
		                                            <td>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="updateStore('${dto.code}');">修改</button>
                                            			<button type="button"  class="btn btn-success btn-xs delete-role" onclick="editStoreExtraInfo('${dto.code}');">编辑门店信息</button>
		                                            	<c:choose>
																
															<c:when test="${dto.isDeleted == 'n'}">
																<a class="btn btn-warning btn-xs" href="javascript:disableStore('${dto.code}');" >
																	禁用	
																</a>
															</c:when>
															<c:when test="${dto.isDeleted == 'y'}">
																<a class="btn btn-warning btn-xs" href="javascript:disableStore('${dto.code}');" >
																	解禁	
																</a>
															</c:when>
														</c:choose>
		                                            	
														<c:choose>
																
															<c:when test="${!dto.setSysUser}">
																<a class="btn btn-success btn-xs" href="${contextPath}/store/goAddSysUser/${dto.code}" >
																	添加管理员
																</a>
															</c:when>
															<c:when test="${dto.setSysUser}">
																<a class="btn btn-success btn-xs" href="${contextPath}/store/goUpdateSysUser/${dto.code}" >
																	修改管理员
																</a>
															</c:when>
														</c:choose>
														<c:if test="${dto.status==4 }">
			                                            	<button type="button"  class="btn btn-success btn-xs delete-role" onclick="testSku('${dto.code}')">导入测试商品</button>
			                                       		</c:if>
		                                            </td>
		                                          
		                                        </tr>
		                                    </c:forEach>
		                                    </tbody>
		                                </table>
		                            </div>
		                              <!-- 分页 -->
		                             <tags:page formId="findForm" url="${contextPath}/store/storeList"></tags:page>
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
     
        <!-- 批量导入模态对话框-1  开始  -->
		<div id="Modal_pldr" class="modal hide fade Modal_dx">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h3>批量导入用户信息</h3>
		  </div>
			  <div class="modal-body">
			  	<input  type="file" name="member_excel" id="member_excel" onchange="fileChange(this)" /> 
				<span id="logoUrl_Wrning" style="color:red;width:5px;height:28px;line-height:28px">*</span>
			  </div>
			  <select name="isNeedSendMessage">
			  	<option value="y">发送短信</option>
			  	<option value="n">不发送短信</option>
			  </select>
			  <div class="modal-footer">
			  	<span onclick="import_excel()">
			    	<a href="javascript:" class="btn btn-success" data-dismiss="modal" data-toggle="modal">确定</a>
			    </span>
			    <a href="#" class="btn" data-dismiss="modal">关闭</a>
			  </div>
		</div> 
     
     
    </body>
</html>