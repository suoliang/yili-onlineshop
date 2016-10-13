<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/datetimepicker.jsp" %><!-- 日历控件引用 -->
<!DOCTYPE html>
<html>
<head>
	<title>产品管理</title>
  	<script type="text/javascript">
  	
  	
	  	function edit(id){
	  		
	  		window.location.href = "${contextPath}/sku/skuProductDetail/"+id;
	  	}
	  	
	  	function delSkuImage(id){
	  		var submit =  function(v,h,f){
	  			if(v=="ok"){
	  				window.location.href="${contextPath}/skuImage/"+id+"/deleteById"; 
	  			}else{
	  				jBox.tip("删除失败", 'info');
	  			}
	  			return true;
	  		} 
	  		
	  		$.jBox.confirm("你确定要删除该图片吗？", "删除提示",submit);

	  	}
		
	  	function goAddImage(code){
	  		
	  		window.location.href = "${contextPath}/skuImage/"+code+"/toAdd";
	  	}
	
  	</script>
	
</head>
<body id="index">
		<tags:message content="${message }"></tags:message>
	
        <div class="container-fluid">
            <div class="row">
			<div id="menu">
              <script src="${contextStatic }/bootstrap/js/leftMenu.js"></script><!-- 公共左侧菜单 -->
			</div>
			  <div class="col-md-12 content">
				<div class="panel panel-info">
                   <div class="panel-heading">
                      <h3 class="panel-title"><i class="fa fa-cog"></i> 商品图片列表</h3>
                   </div>
                   <div class="panel-body">
				     <form class="form-horizontal page" id="findForm" method="post">
				     
					   <div class="form-group col-md-2 mB15">
	    					<label for="skuNo">商品编码：</label>
	      					<input type="text" id="skuNo" name="skuNo" class="form-control" value="${sku.skuNo}" readonly
	      						 placeholder="商品编码">
	  					</div>
				    
					    <div class="form-group col-md-8 mB15">
	    					<label for="name">商品名称：</label>
	      					<input type="text" id="name" name="skuName" class="form-control" value="${sku.name}" readonly
	      						placeholder="商品名称" >
	  					</div>
	  					
	  					<div class="col-md-12">
	                        <button type="button"  class="btn btn-info" onclick="goAddImage('${sku.skuNo}')">上传图片</button>
	                        <button type="button"  class="btn btn-info" onclick="javascript:history.go(-1);">返回上一页</button>
				    	</div>
	  					
				   		<div class="clearfix"></div>
					  </form>
                      <!-- table -->
                      <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="roleTable">
                           <thead>
                             <tr>
							  <th>图片类型编码</th>
							  <th>图片类型名称</th>
							  <th>图片</th>
							  <th>创建时间</th>
							  <th>操作</th>
							 </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${perSkuImageList}" var="tmp" varStatus="status">
								<tr>
								 	
								   <td>${tmp.imageTypeCode}</td>	
								   <td>${tmp.imageName}</td>	
								   <td>
								   		<img alt="" src="${skuPath}${tmp.skuCode}/${tmp.imgUrl}" />
<!-- 										<img alt="" src="http://localhost:8080\fushionbaby-CMSManager\userfiles\_thumbs\Images\sku\products\2015\06\1Koala.jpg"  /> -->
								   </td>	
								   <td><fmt:formatDate value="${tmp.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
								   <td>		
									 <a class="btn btn-danger btn-xs delete-role"  href="javascript:delSkuImage(${tmp.id})"   title="删除">	
									          删除
									 </a>
									 
								   </td>	
								  </tr>
								 </c:forEach>
                                </tbody>
                              </table>
                           </div>
                            
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
