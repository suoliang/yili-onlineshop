<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>门店配置信息</title>
  	<script type="text/javascript">
	  	function addStoreConfig(storeCode){
	  		if(storeCode == null || storeCode =='' ){
	  			window.location.href="${contextPath }/storeConfig/goAddStoreConfig/"+new Date().getTime();
	  		}else{
	  			window.location.href="${contextPath }/storeConfig/goUpdateStoreConfig/"+storeCode+"/" + new Date().getTime();
	  		}
	  	}
  	</script>
	
</head>
<body>
		<tags:message content="${message }"></tags:message>
            <div class="row">
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 门店配置信息</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-inline page" id="findForm"  >

				   		<div class="clearfix"></div>
				   		
					  </form>
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
                           	  <th>门店名称</th>
                           	  <th>开始营业时间</th>
							  <th>结束营业时间</th>
							  <th>免运费最低订单金额</th>
							  <th>运费</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            
                            <tbody>
								<tr>
								   <td>${storeName }</td>
								   <td>${storeConfig.businessStartTime}</td>	
								   <td>${storeConfig.businessEndTime}</td>	
								   <td>${storeConfig.freeFreightAmount}元</td>	
								   <td>${storeConfig.freightAmount}元</td>	
								   <td>
									 <a class="btn btn-warning btn-xs edit-role" href="javascript:void(0)"  onclick="addStoreConfig('${storeConfig.storeCode }')">
									 	<c:if test="${empty  storeConfig.storeCode }">添加门店配置</c:if>
									 	<c:if test="${not empty  storeConfig.storeCode }">修改门店配置</c:if> 
									 </a>
								   </td>	
								  </tr>
                                </tbody>
                              </table>
                           </div>
                            
                        </div>
					</div>
                </div>
            </div>
        <!-- /.container-fluid -->
</body>
</html>
