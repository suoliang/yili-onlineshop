<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp" %><!-- 标签库引用 -->
<%@ include file="/WEB-INF/include/bootstrap.jsp" %><!--  主要的css样式和javascript的引用 -->
<%@ include file="/WEB-INF/include/dialog.jsp" %><!-- 弹出框引用 -->
<%@ include file="/WEB-INF/include/ztree.jsp" %><!-- 弹出框引用 -->


<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>产品管理</title>
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
				var zTree = $.fn.zTree.getZTreeObj("categoryTree");
				zTree.checkNode(treeNode, !treeNode.checked, null, true);
				var cityObj = $("#categorySel");
				cityObj.attr("value", treeNode.name);
				$("#categoryCode").val(treeNode.code);
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
            	/** ztree end*/
            	
            	/*表单验证*/
                var validator = $('#productForm').validate({
                	debug:true,
                    rules: {
                    	code: {
                            required: true,
                            rangelength: [2, 20],
                            remote :{
                            	url:"${contextPath}/sku/existProductCode",
                            	type:"get",
                            	dataType:"json",
                            	data: {                     //要传递的数据
                                    code: function() {
                                        return $("#code").val();
                                    },
                            		oldCode:function(){
                            			return $("#oldCode").val();
                            		}
                            	}
                            }
                        },
                        name: {
                            required: true
                        }
                     
                    },
                    messages: {
                    	code: {
                            required: "请填写产品编号！",
                            rangelength: "产品编号长度在2-20之间",
                            remote : "该产品编号已存在。"
                        },
                        name: {
                            required: "请输入产品名称！"
                        }
                       
                    },
                    
                    submitHandler: function(form) {
                        // 验证成功后操作
                        console.log(form);
                        console.log($(form).serialize());
                        console.log($("input[name='skus']").val());
                        console.log($("input[name='attrs']").val());
                        if($("input[name='skus']").val() == ""){
                        	$.jBox.tip('请至少添加一组商品');
                        	return ;
                        }
                        
                       
                        form.submit();
                    }
                });
            });
            
            
            
            function changeAttrName(obj,index){
            	var attrName = obj.attrName;
            	if(attrName ==''){
            		$.jBox.info("请先输入属性名称");
            		return;
            	}
            	var attrValue = "";
            	var showOrder = "";
            	var attrjsonstr = $("#attrjson"+index).val();
				if(attrjsonstr !=""){
            		var attrjson = eval("("+ attrjsonstr +")");
            		attrValue = attrjson.attrValue;
            		showOrder = attrjson.showOrder;
				}
				var newAttrjsonStr = "{\"attrName\":\""+obj.value+"\",\"attrValue\":\""+attrValue+"\",\"showOrder\":\""+showOrder+"\"}"; 
            	$("#attrjson"+index).val(newAttrjsonStr);
            	
            }
            
            function changeAttrValue(obj,index){
            	var attrValue= obj.attrValue;
            	if(attrValue ==''){
            		$.jBox.info("请先输入属性值");
            		return;
            	}
            	var attrName ="";
            	var showOrder ="";
            	var attrjsonstr = $("#attrjson"+index).val();
            	if(attrjsonstr!=""){
            		var attrjson = eval("("+ attrjsonstr +")");
            		attrName = attrjson.attrName;
            		showOrder = attrjson.showOrder;
            	}
            	var newAttrjsonStr = "{\"attrName\":\""+attrName+"\",\"attrValue\":\""+obj.value+"\",\"showOrder\":\""+showOrder+"\"}"; 
            	$("#attrjson"+index).val(newAttrjsonStr);
            	
            	
            }
            
            function changeShowOrder(obj,index){
            	var showOrder = obj.showOrder;
            	var attrName ="";
            	var attrValue ="";
            	var attrjsonstr = $("#attrjson"+index).val();
            	if(attrjsonstr!=""){
            		var attrjson = eval("("+ attrjsonstr +")");
            		attrName = attrjson.attrName;
            		attrValue = attrjson.attrValue;
            	}
            	
            	var newAttrjsonStr = "{\"attrName\":\""+attrName+"\",\"attrValue\":\""+attrValue+"\",\"showOrder\":\""+obj.value+"\"}"; 
            	$("#attrjson"+index).val(newAttrjsonStr);
            	
            }
            
        	
        	
        	function returnList(){
        		
        		window.location.href = "${contextPath}/sku/skuProductList";
        	}
        	
        
        	function addAttr(){
        		var timeIndex =  $.trim(new Date().getTime()) + "a";/** 唯一Id*/
        		
        		var tr="<tr id='attr-"+timeIndex+"'>";
        			tr+="<td><input name='attrName' onblur=\"changeAttrName(this,'"+timeIndex+"')\"></td>";
        			tr+="<td><input name='attrValue' onblur=\"changeAttrValue(this,'"+timeIndex+"')\"></td>";
        			tr+="<td><input name='showOrder' onblur=\"changeShowOrder(this,'"+timeIndex+"')\"></td>";
        			tr+="<td><a href=\"#\" class=\"btn btn-danger\" onclick=\"removeAttr('"+timeIndex+"')\">移除</a></td>";
        			tr+="<input type='hidden' name='attrs' id='attrjson"+timeIndex+"'  /></tr>";
        		$("#attr-tbody").append(tr);
        	}
        	
        	function removeAttr(index){
        		
        		var submit =  function(v,h,f){
    	  			if(v=="ok"){
    	  				$("#attr-"+index).remove();
    	  			}
    	  			return true;
    	  		} 
    	  		
    	  		$.jBox.confirm("你确定要移除该属性吗？", "提示",submit);
        		
        	}
        	function removeSku(index){
        		
        		var submit =  function(v,h,f){
    	  			if(v=="ok"){
    	  				var skuNo = $("#skuNo-"+index).val();
    	  				if(skuNo){
    	  					var	skuNos = $("#removeskuNo").val();
        	  				if(skuNos==''){
        	  					skuNos = skuNo;
        	  				}else{
        	  					skuNos += ","+skuNo;
        	  				}
        	  				 $("#removeskuNo").val(skuNos);
  
    	        		}
    	  				
    	  				$("#skutr-"+index).remove();
    	  			}
    	  			return true;
    	  		} 
        		
    	  		
    	  		$.jBox.confirm("你确定要移除该商品吗？", "提示",submit);
        		
        	}

		function  addSku(){
			var url = "iframe:${contextPath}/sku/skuAdd";
			$.jBox(url, {
	  		    title: "商品编辑", width: 800,height: 600,
	  		    buttons: { '确定':true,'关闭': false },
	  		  	submit: function (v, h, f) {
	              if (v == 0) {
	            	  return ;
	            	  
	              }

	            var contents = h.find("iframe").contents();
	            var skuBo = JSON.stringify(contents.find("form").serializeObject());
	           	var params = contents.find("form").serialize();
	           	var skuNo = contents.find("input[name='skuNo']").val();
	           	if(skuNo ==''){
	           		$.jBox.tip('请输入商品编号');
	           		return false;
	           	}
				var barCode = contents.find("input[name='barCode']").val();
				if(barCode ==''){
					$.jBox.tip('请输入商品条形码');
	           		return false;
				}
	           	var skuName = contents.find("input[name='skuName']").val();
	           	if(skuName ==''){
	           		$.jBox.tip('请输入商品名称');
	           		return false;
	           	}
	           	var skuDescription = contents.find("#sku-description").val();
	           	
	          
	            var timeIndex =  $.trim(new Date().getTime()) + "a";/** 唯一Id*/
	           	var skuRow = "<tr id='skutr-"+timeIndex+"'>";
	           		skuRow +="<td>"+skuNo+"</td>";
	           		skuRow +="<td>"+skuName+"</td>";
	           		skuRow +="<td>"+skuDescription+"</td>";
	           		skuRow +="<td><a href=\"javascript:editSkuDetail('"+timeIndex+"')\" class=\"btn btn-info\" >详情设置</a>";
           			skuRow +="<a href=\"javascript:removeSku('"+timeIndex+"')\" class=\"btn btn-danger\" >移除</a>";
	           		skuRow +="</td></tr><input type='hidden' id='skuParams"+timeIndex+"' value='"+params+"'>";
	           		skuRow +="<input type='hidden' name='skus' id='skuJsonParams"+timeIndex+"' value='"+skuBo+"'>";
	           		skuRow +="</tr>";
	           		
	           	$("#sku-tbody").append(skuRow);
	           	
	           	
	          }

	  		});
		}
		
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
        	
        	
        	
        	function editSkuDetail(index){
        		var skuParams = $("#skuParams"+index).val();
        		var skuJsonParams = $("#skuJsonParams"+index).val();
        		
        		var url = "iframe:${contextPath}/sku/skuEdit/"+index+"?skuJsonParams="+encodeURIComponent(encodeURIComponent(skuJsonParams)); 
    	  		$.jBox(url, {
    	  		    title: "商品编辑", width: 800,height: 600,
    	  		    buttons: { '确定':true,'关闭': false },
    	  		  	submit: function (v, h, f) {
    	              if (v == 0) {
    	            	  return ;
    	              } 
   	            	  var contents = h.find("iframe").contents();
   	            	  var skuNo = contents.find("input[name='skuNo']").val();
		   		      if(skuNo ==''){
			           		$.jBox.tip('请输入商品编号');
			           		return false;
			          }
			          var skuName = contents.find("input[name='skuName']").val();
			          if(skuName ==''){
			           		$.jBox.tip('请输入商品名称');
			           		return false;
			           }
   		           	  var skuDescription = contents.find("#sku-description").val();
   		           	  var skuBo = JSON.stringify(contents.find("form").serializeObject());
   		          	  var params = contents.find("form").serialize();
   		           	  $("#skutr-"+index).find("td").eq(0).text(skuNo);
   		           	  $("#skutr-"+index).find("td").eq(1).text(skuName);
   		           	  $("#skutr-"+index).find("td").eq(2).text(skuDescription);
   		           	  $("#skuParams"+index).val(params);
   		           	  $("#skuJsonParams"+index).val(skuBo);
    	              return ;
    	          }

    	  		});
        		
        	}
        	function querySkuDetail(index){
        		var skuNo = $("#skutr-"+index).find("input[name='code']").val();
        		if(skuNo == null || skuNo ==''){
        			$.jBox.info("请先输入该商品编号");
        			return ;
        		}
        		
        		var url = "iframe:${contextPath}/sku/skuEdit/"+skuNo;
        		$.jBox(url, {
    	  		    title: "商品详情", width: 800,height: 600,
    	  		    buttons: {'关闭': false },
    	  		  	submit: function (v, h, f) {
    	             
    	          	}

    	  		});
        	}
        	
        	function submitEdit(){
        		if($("#pageType").val() == ''){
        			$("#productForm").attr("action","${contextPath }/sku/updateProduct");
        		}
				
        		$("#productForm").submit();
        		
        	}
        	
        </script>
	
</head>
 <body id="index">
        <div class="container-fluid">
            <div class="row">
			
			  <div class="col-md-12 content">
			  
			     <div class="panel panel-info">
			     
                    <div class="panel-heading">
                          <h3 class="panel-title"><i class="fa fa-cog"></i> 产品信息编辑</h3>
                    </div>
				    <div class="panel-body">
				      <form class="form-horizontal" method="post" action="${contextPath }/sku/saveProduct" id="productForm" >
				 			<input type="hidden"  id="pageType" value="${pageType }" />
							<input name="currentPage" id="currentPage" type="hidden" class="form-control" value="${currentPage}"/>
							<input name="limit" id="limit" type="hidden" class="form-control" value="${limit}"/>
							<input name="total" id="total" type="hidden" class="form-control" value="${total}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">产品编码：</label>
							<div class="col-sm-2">
								<input name="id" type="hidden" value="${product.id }" />
								<input name="oldCode" id="oldCode" type="hidden" value="${product.code}"/>
								<input name="code" id="code" type="text" class="form-control"
						 			value="${product.code}"  <c:if test="${null!=product.code && !(product.code).isEmpty()  }">readonly</c:if>  />
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
				
						<div class="form-group">
							<label class="col-sm-2 control-label">产品名称：</label>
							<div class="col-sm-2">
								<input name="name" id="name" type="text" class="form-control" 
								 	value="${product.name}"/>
							</div>
							<div class="error col-sm-2  textL">必填项</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品分类：</label>
							<div class="col-sm-2">
								<input id="categorySel" class="form-control" type="text" readonly value="${product.categoryName }" onclick="showMenu();" />
								<div id="menuContent" class="menuContent">
									<ul id="categoryTree" class="ztree"></ul>
								</div>
							</div>
							<input id="categoryCode" name="categoryCode" type="hidden" value="${product.categoryCode }" />
						</div>
						
						
						<div class="form-group">
							<label class="col-sm-2 control-label">商品品牌：</label>
							<div class="col-sm-2">
								<input id="checkedSkuBrandName" class="form-control" type="text" readonly value="${product.brandName }" placeholder="请选择商品品牌">
							</div>
							<button type="button" class="btn btn-default" aria-label="Left Align"
									id="add-sku-product-attr" onclick="addSkuBrand()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
							</button>
							<input type="hidden" id="brandCode" name="brandCode" value="${product.brandCode}"  />
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">使用类型：</label>
							<div class="col-sm-2">
								<select name="disable" id="disable" class="form-control" data-placeholder="Choose a Category" tabindex="1">
									 <c:forEach items="${dic:getDictByType('DISABLE')}" var="dict">
										<option value="${dict.value }" <c:if test="${dict.value== product.disable}">selected="selected"</c:if> >${dict.label }</option>
					           	 	</c:forEach>
								</select>
							</div>
						</div>
			

						<div class="form-group">
					
							<label class="col-sm-2 control-label">添加商品属性：</label>
					
							<div class="col-sm-5">
						
								<button type="button" class="btn btn-default" aria-label="Left Align"
									id="add-sku-product-attr" onclick="addAttr()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
								</button>
						
						</div>
			
						<!--追加属性时从这个标签开始，这样可以保证不乱 -->
						<input id="sku-product-attr-input" value="" name="attrInput" type="hidden">
					
					</div>
					<div class="form-group">
						<div class="table-responsive col-sm-7 col-sm-offset-2" id="sku-product-attr">
							
							<table id="sku-product-attr-table"  class="table table-bordered table-hover" >
								<thead>
								<tr>
							 		<th id="attrName">属性名</th>
							 		<th id="attrValue">属性值</th>
							 		<th id="attrValue">显示顺序</th>
							 		<th>操作</th>
								</tr>
								</thead>
								<tbody id="attr-tbody">
										<c:forEach items="${attrs }" var="attr" varStatus="s">
									<tr id="attr-${attr.productCode }${s.index }">
										<td><input name='attrName' value="${attr.attrName }" 
											onblur="changeAttrName(this,'${attr.productCode }${s.index }')" /></td>
											
										<td><input name='attrValue'  value="${attr.attrValue }" 
											onblur="changeAttrValue(this,'${attr.productCode }${s.index }')" /></td>
											
										<td><input name='showOrder' value="${attr.showOrder }"  
											onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11"
   											onafterpaste="this.value=this.value.replace(/\D/g,'')"  
											onblur="changeShowOrder(this,'${attr.productCode }${s.index }')" /></td>
										<td>
											<a href="javascript:void(0)" class="btn btn-danger" onclick="removeAttr('${attr.productCode }${s.index }')">移除</a>
        									<input type='hidden' name='attrs' id="attrjson${attr.productCode }${s.index }" value='{"attrName":"${attr.attrName }","attrValue":"${attr.attrValue }","showOrder":"${attr.showOrder }","id":"${attr.id }"}' />
										</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>	
				

		
					<div class="form-group">
						<label class="col-sm-2 control-label">web图文：</label>
						<div class="col-sm-2">
							<input type="hidden"  id="graphicDetails" name="graphicDetails"  value="${picsb }"/>
					
							<tags:ckfinder input="graphicDetails" type="images" 
 								uploadPath="/sku/products/" selectMultiple="true" />
						</div>
					</div>
				
				
				
					<div class="form-group">
						<label class="col-sm-2 control-label">添加商品：</label>
						<div class="col-sm-2">
							<button type="button" class="btn btn-default" aria-label="Left Align"
							onclick="addSku()">
  								<span class="glyphicon glyphicon-plus" aria-hidden="true">+</span>
							</button>
						</div>
					</div>
					<div class="form-group">
						<div class="table-responsive col-sm-8 col-sm-offset-2" id="sku" >
							<table id="sku-table"  class="table table-bordered table-condensed" >
								<thead>
									<tr>
								 		<th>商品编号</th>
								 		<th>商品名称</th>
								 		<th>描述</th>
								 		<th>操作</th>
								 		
								 	</tr>
								 </thead>
								<tbody id="sku-tbody">
									<c:forEach items="${skus }" var="sku" varStatus="s">
									<tr id="skutr-${sku.skuNo }${s.index}">
								 		<td>${sku.skuNo }</td>
								 		<td>${sku.skuName }</td>
								 		<td>${sku.skuDescription }</td>
								 		<td>
								 		<c:if test="${sku.status=='1'  }">
								 			<a href="javascript:editSkuDetail('${sku.skuNo }${s.index }')" class="btn btn-info" >详情设置</a>
								 		</c:if>	
								 			<a href="javascript:removeSku('${sku.skuNo }${s.index }')" class="btn btn-danger" >移除</a>
								 		</td>
	 							 	</tr>
	 							 	<input type='hidden' name='skus' id='skuJsonParams${sku.skuNo }${s.index}' value='{"skuNo":"${sku.skuNo }","barCode":"${sku.barCode }","skuName":"${sku.skuName }","color":"${sku.color }","size":"${sku.size }","quotaNum":"${(empty sku.quotaNum)?0:sku.quotaNum }","keyWords":"${sku.keyWords}","costPrice":"${(empty sku.costPrice)?0:sku.costPrice }","retailPrice":"${(empty sku.retailPrice)?0:sku.retailPrice }","currentPrice":"${(empty sku.currentPrice)?0:sku.currentPrice }","prePrice":"${(empty sku.prePrice)?0:sku.prePrice }","marketPrice":"${(empty sku.marketPrice)?0: sku.marketPrice}","isVideo":"${sku.isVideo }","videoUrl":"${sku.videoUrl }","isGift":"${sku.isGift }","hasGift":"${sku.hasGift }","hasDiscount":"${sku.hasDiscount }","showorder":"${(empty sku.showorder)?0:sku.showorder }","skuDescription":"${sku.skuDescription }"}'>
	           						<input type='hidden' id='skuParams${sku.skuNo }${s.index }'   value='skuNo=${sku.skuNo }&barCode=${barCode }&skuName=${sku.skuName }&color=${sku.color }&size=${sku.size }&quotaNum=${sku.quotaNum }&costPrice=${sku.costPrice }&retailPrice=${sku.retailPrice }&currentPrice=${sku.currentPrice }&prePrice=${sku.prePrice }&marketPrice=${sku.marketPrice }&isVideo=${sku.isVideo }&videoUrl=${sku.videoUrl }&isGift=${sku.isGift }&hasGift=${sku.hasGift }&hasDiscount=${sku.hasDiscount }&showorder=${sku.showorder }&skuDescription=${sku.skuDescription }}' />
	 							 	<input type='hidden' id='skuNo-${sku.skuNo }${s.index }' value="${sku.skuNo }" />
	 							 	</c:forEach>
								</tbody>
				
							</table>
							<input type="hidden" name="skus" value="" />
							<input type="hidden" name="attrs" value="" />
							<input type="hidden" name="skuNos" id="removeskuNo" />
						</div>
					</div>
				
				  
					<div class="form-group">
				    	<div class="col-sm-offset-2 col-sm-10">
				      	
				      		<button class="btn btn-success " type="button" onclick="submitEdit()">确定</button>
							<button class="btn btn-primary" type="button" onclick="javascript:history.go(-1);">返回</button>
				   	 	</div>
					</div>
				  
				</form>
			  </div>
			  </div>
       		</div>
       	  </div>
       	</div>
   </body>
</html>