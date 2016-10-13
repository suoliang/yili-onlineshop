<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>商品编辑</title>
<script type="text/javascript">

var setting = {
		view: {
			dblClickExpand: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick
		}
	};

	var zNodes =${allCategoryJson};

	function onClick(e, treeId, treeNode) {
		if(treeNode.isParent == true){
			return false;
		}
		
		var zTree = $.fn.zTree.getZTreeObj("categoryTree");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		var cityObj = $("#categorySel");
		cityObj.attr("value", treeNode.name);
		$("#categoryCode").val(treeNode.code);
		hideMenu();
		return false;
	}
	function showMenu() {
		var cityObj = $("#categorySel");
		var cityOffset = $("#categorySel").offset();
		$("#menuContent").css({left:15 + "px", top:35 + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "categorySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}     
	}

 	$(document).ready(function() {
        // 初始化树
        $.fn.zTree.init($("#categoryTree"), setting, zNodes);	

 	});

	 
	function  addSkuBrand(){
		var url = "iframe:${contextPath}/skuBrand/selectSkuBrandList";
		$.jBox(url, {
  		    title: "商品品牌列表", width: 960,height: 600,
  		    buttons: { '确定':true,'关闭': false },
  		  	submit: function (v, h, f) {
              if (v == 0) {
            	  return ;
              }

         	var contents = h.find("iframe").contents();
           
           	var skuBrandName = contents.find("#checkSkuBrandName").val();
           	var skuBrandCode = contents.find("#checkSkuBrandCode").val();
           	$("#checkedSkuBrandName").val(skuBrandName);
           	
           	$("#brandCode").val(skuBrandCode);
          }

  		});
	}
	 
	 
$(document).ready(function() {
	$(".txt-zero").blur(function(){
		if(this.value == ''){
			this.value = 0;
		} 
	});
	
});


function checkSkuNo(){
	
	var skuNo = $("#skuNo").val();
	var oldSkuNo = $("#oldSkuNo").val();
	
	$.post("${contextPath}/store/sku/exitSkuNo",{skuNo:skuNo,oldSkuNo:oldSkuNo},function(result){
		
		if(result==false){
			$("#skuNo").val("");
   			jBox.tip("该商品编号已存在,请重新输入！");
   			return;
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
			     <form class="form-inline " enctype="multipart/form-data" id="editForm" method="post" action="${contextPath }/store/sku/updateStoreSku">
				 <div class="panel panel-info">
                    	<input type="hidden" name="currentPage" value="${page.currentPage}"/>
						<input type="hidden" name="totalPage" value="${page.totalPage}" disabled="disabled"/>  
						<input type="hidden" name="limit" value="${page.limit}"  size="3"/>
						<input type="hidden" name="total" value="${page.total}"/>
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品基本信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                		    	
                             			<input type="hidden" name="uniqueCode" value="${sku.uniqueCode }" />
                                 
                                        <li class="list-group-item">
                                            <label for="skuName" class="control-label">商品名称：</label>
											<input type="text" class="form-control" name="skuName" id="skuName" value="${sku.skuName }" placeholder="商品名称"/>
                                        </li>
                                        
                                       	<li class="list-group-item">
											 <label for="categorySel" class=" control-label">商品分类：</label>
											<input id="categorySel" class="form-control" type="text" readonly value="${sku.categoryName }" onclick="showMenu();" />
											<div id="menuContent" class="menuContent">
												<ul id="categoryTree" class="ztree"></ul>
											</div>
										</li>
										<input id="categoryCode" name="categoryCode" type="hidden" value="${sku.categoryCode }" />
                                       
                                      
                                    </ul>
                                </div>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品基本信息</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    	<li class="list-group-item">
                                        	<label class="control-label" for="currentPrice">当前价格：</label>
											<input  class="form-control lg-select txt-zero" id="currentPrice" name="currentPrice" type="text"
													onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" 
													maxlength="19"
			   										onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"
						   							value="${sku.currentPrice }"/>
                                        </li>
                                    
                                    	<li class="list-group-item">
                                            <label for="quotaNum" class="control-label txt-zero" for="sku-quotaNum">限购数量：</label>
											<input class="form-control"  id="quotaNum" name="quotaNum" type="number" min=0
														maxlength="11" 	value="${sku.quotaNum }" />
                                        </li>
                                        
                                        <li class="list-group-item">
                                        	<label class="control-label" for="showorder">库存数量：</label>
											<input  class="form-control lg-select txt-zero" id="" name="availableQty" type="text"
													onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11" min=0 
						   							onafterpaste="this.value=this.value.replace(/\D/g,'')"  
						   							value="${sku.availableQty }"/>
                                        </li>
                                        <li class="list-group-item">
                                        	<label class="control-label" for="showorder">显示顺序：</label>
											<input  class="form-control lg-select txt-zero" id="showorder" name="showorder" type="text"
													onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11"
						   							onafterpaste="this.value=this.value.replace(/\D/g,'')"  
						   							value="${sku.showorder }"/>
                                        </li>
                                        
                                    </ul>
                                </div>
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    
                    <div class="panel panel-info">
                    	
                        <div class="panel-heading">
                            <h3 class="panel-title"><i class="fa fa-shopping-cart"></i>商品图片</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <ul class="list-group">
                                    
                                    	 <li class="list-group-item">
                                        	<label class="control-label" for="skuImage1">图片：</label>
                                        	<input type="file" name="skuImage1"  class="form-control"accept="image/*" />
                                        
                                        </li>
                                        
                                         <li class="list-group-item">
                                        	<c:forEach items="${imageList }" var="img">
                                        	   <c:if test="${fn:length(img.imgUrl) gt 1  }">
                                        		<img alt="" width="200" height="200" src="${imagePath }${img.imgUrl}" />
                                        	   </c:if>
                                        	</c:forEach>
                                        </li>
                                        
                                    </ul>
                                </div>
                                
                                
                                
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    
                    
                    <button class="btn btn-success" type="submit" >确认并查看列表</button>
				
			</form>
       </div>
     </div>
  <script type="text/javascript">
            $(document).ready(function() {
                var validator = $('#editForm').validate({
                	rules: {
                        skuName:{
                     	   required: true
                        },
 					   currentPrice:{
                     	   required: true,
                     	   number:true,
                     	   isFloatGtZero:true
                        },
                        availableQty:{
                     	   required: true
                        }
                        
                    },
                    messages: {
                        skuName:{
                     	   required: "请填写商品名称！"
                        },
                        currentPrice:{
                     	   required: "请填入商品价格！",
                     	   number : "必须是数字格式！",
                     	   isFloatGtZero:"必须大于0"
                        },
  					    availableQty:{
                     	 required: "请设置库存数量！",   
                        }
                    },
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log($(form).serialize());
                        $(form).ajaxSubmit();
                    } 
                });

            });
        </script>
     
     
</body>
</html>