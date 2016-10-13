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


</script>
</head>
<body id="index">
	<tags:message content="${message }"></tags:message>
       <div class="container-fluid">
           <div class="row">
		  <div class="col-md-12 content">
		     <div class="panel panel-info">
                <div class="panel-heading">
                   <h3 class="panel-title"><i class="fa fa-cog"></i> 新建商品</h3>
                </div>
			 <div class="panel-body">
			     <form class="form-horizontal" id="editForm" method="post" action="${contextPath }/sku/addSku">
<%-- 			     <form:form class="form-horizontal" id="editForm" action="${contextPath }/sku/modifySku" modelAttribute="skuDto" method="put"> --%>
					
					<div class="form-group">
							<label class="col-sm-3 control-label">产品编码：</label>
							<div class="col-sm-5">
								<input name="productCode" id="productCode" type="text" class="form-control"
						 			value="${sku.productCode}"   />
							</div>
							<div class="error col-sm-2  textL">必填项</div>
					</div>
			     
			     	<div class="form-group">
						<label class="col-sm-3 control-label">商品编号：</label>
						<div class="col-sm-5">
							<input name="skuNo" id="skuNo" type="text" class="form-control" value="${sku.skuNo}" onblur="javascript:checkSkuNo();"/>
						</div>
						<div id="errSkuNo" class="error col-sm-3  textL">必填项</div>
					</div>
					
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品条形码：</label>
						<div class="col-sm-5">
							<input name="barCode" id="barCode" type="text" class="form-control" value="${sku.barCode}"  onblur="javascript:checkBarCode();"/>
						</div>
						<div  id="errBarCode" class="error col-sm-3  textL">必填项</div>
					</div>
					
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品名称：</label>
						<div class="col-sm-5">
							<input name="skuName" id="skuName" type="text" class="form-control" value="${sku.skuName}"/>
						</div>
						<div id="errSkuName"  class="error col-sm-3  textL">必填项</div>
					</div>
					
					
							
						<div class="form-group">
							<label class="col-sm-3 control-label">商品分类：</label>
							<div class="col-sm-5">
								<input id="categorySel" class="form-control" type="text" readonly value="${sku.categoryName }" onclick="showMenu();" />
								<div id="menuContent" class="menuContent">
									<ul id="categoryTree" class="ztree"></ul>
								</div>
							</div>
							<input id="categoryCode" name="categoryCode" type="hidden" value="${sku.categoryCode }" />
						</div>
						 
						<div class="form-group">
							<label class="col-sm-3 control-label">商品品牌：</label>
							<div class="col-sm-5">
								<input id="checkedSkuBrandName" class="form-control" type="text" readonly value="${sku.brandName }" placeholder="请选择商品品牌">
							</div>
							<button type="button" class="btn btn-default" aria-label="Left Align"
									id="add-sku-product-attr" onclick="addSkuBrand()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
							</button>
							<input type="hidden" id="brandCode" name="brandCode" value="${sku.brandCode}"  />
						</div>
			     
					
					<div class="form-group">
						<label class="col-sm-3 control-label">商品颜色：</label>
						<div class="col-sm-5">
							<input name="color" id="color" type="text" class="form-control" value="${sku.color}"/>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">商品规格：</label>
						<div class="col-sm-5">
							<input name="size" id="size" type="text" class="form-control" value="${sku.size}"/>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label txt-zero" for="sku-quotaNum">限购数量：</label>
						<div class="col-sm-5">
							<input id="sku-quotaNum" class="form-control" name="quotaNum" type="number" min=0
									onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11" 
   									onafterpaste="this.value=this.value.replace(/\D/g,'')"
   									value="${(empty sku.quotaNum)?'0':sku.quotaNum }">
						</div>
						

					</div>


				<div class="form-group">

					<label class="col-sm-3 control-label" for="sku-showorder">显示顺序：</label>

						<div class="col-sm-5">

							<input id="sku-showorder" class="form-control lg-select txt-zero" name="showorder" type="number"
									onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11"
   									onafterpaste="this.value=this.value.replace(/\D/g,'')"  
   									value="${(empty sku.showorder)?'0':sku.showorder }"/>
						</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="sku-description">商品描述：</label>
					<div class="col-sm-5">
						<textarea id="sku-description" rows="5" cols="90" name="skuDescription">${sku.skuDescription}</textarea>
						
					</div>
				</div>
				
				
				<div class="form-group">
			    	<div class="col-sm-offset-2 col-sm-10">
			      	
			      		<button class="btn btn-success" type="submit" >创建并继续</button>
						<a class="btn btn-primary" type="button" href="${contextPath }/sku/findSkuList">返回到列表</a>
			      	
			   	 	</div>
				</div>
				
			</form>
		    </div>
		   </div>
		  </div>
       </div>
     </div>
     <script type="text/javascript">
            $(document).ready(function() {
                var validator = $('#editForm').validate({
                	rules: {
                	   skuNo: {
                           required: true,
                           remote:{
                        	   url:"${contextPath}/sku/exitSkuNo",
                        	   type:"get",
	       	                   dataType:"json",
	       	                   data: {                     //要传递的数据
	       	                	skuNo: function() {
	       	                             return $("#skuNo").val();
	       	                         }
	       	                    }
                           }//end remote
                       },
                       barCode: {
                           required: true,
                           remote:{
                        	   url:"${contextPath}/sku/exitBarCode",
                        	   type:"get",
	       	                   dataType:"json",
	       	                   data: {                     //要传递的数据
	       	                	skuNo: function() {
	       	                             return $("#barCode").val();
	       	                         }
	       	                    }
                           }//end remote
                       },
                       skuName:{
                    	   required: true
                       },
                       productCode:{
                    	   required: true
                       }
                       
                   },
                   messages: {
                	   skuNo: {
                           required: "请填写商品编号！",
                           remote:"该商品编号已存在"
                       },
                       barCode: {
                       		required: "请填写条形码！",
                       		remote:"该条形码已存在"
                       },
                       skuName:{
                    	   required: "请填写商品名称！"
                       },
                       productCode:{
                    	   required: "请填写产品编号！"
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