function selectSkuByColor(skuId,color,productId){	
	var size = $("#cs-current-size").val();	
	var selectColor = encodeURIComponent(encodeURIComponent(color)); 
	var selectSize = encodeURIComponent(encodeURIComponent(size)); 
	if(size !=null && size!="" && size !=undefined){
		
		window.location.href = _contentPath + "product/skuDetail.do?skuId="+skuId+"&productId="+productId+"&color="+selectColor+"&size="+selectSize;
		return;
	}
	window.location.href = _contentPath + "product/skuDetail.do?skuId="+skuId+"&productId="+productId+"&color="+selectColor;
	
}
function selectSkuBySize(skuId,size,productId){
	var color = $("#cs-current-color").val();
	var selectColor = encodeURIComponent(encodeURIComponent(color)); 
	var selectSize = encodeURIComponent(encodeURIComponent(size)); 
	if(color != null && color !="" && color != undefined){
		window.location.href = _contentPath + "product/skuDetail.do?skuId="+skuId+"&productId="+productId+"&color="+selectColor+"&size="+selectSize;
		return;
	}
	window.location.href = _contentPath + "product/skuDetail.do?skuId="+skuId+"&productId="+productId+"&size="+selectSize;
}
/**
 * 收藏商品
 * @param skuId 商品序号
 */
function collectSku(skuId){
	$.ajax({
		   type: "POST",
		   url: _contentPath + "collect/add-colloct.do?skuId="+skuId,
		   data: "time="+new Date().getTime(),
		   success: function(msg){
			   if(msg == not_login){
				  $("#not_login").show();
			   }
			   else if(msg == has_add){
				   $("#has_add").show();
				   $(".collect").addClass('onclick-collect');
			   }else{
					$(".collect").addClass('onclick-collect');
			   }
			   
		   }
	});
}
/**
 * 立即支付
 * @param sku_id 商品序号
 * @param pnum 购买数量
 */
function buyNowSku(sku_id,pnum){
	$.post(_ContextPath + "/immediate/immediatePayment.do",{
		sku_id : sku_id,
		quantity : pnum,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				var nskuId = data.data.skuId ;
				var nquantity = data.data.quantity;
				if(data.data.skuId != null && data.data.skuId != undefined && data.data.skuId != ""){
					window.location.href = _ContextPath +"/immediate/paymentPage.do?skuId="+nskuId+"&quantity="+nquantity;
				}else{
					alert(data.msg);
				}
			}else if(data.responseCode==201){
				$("#not_login").show();
			}else{
				alert(data.msg);
			}
		}
	);
}
/** 优惠商品组合立即购买
 * @param 组合编号
 * */
function combinationBuy(combinationId){
	
	$.post(_ContextPath + "/product/checkLogin.do",{
		time : new Date().getTime()
		},function(data){
			if(data.responseCode==0){
				window.location.href = _ContextPath + "/product/carGroup.do?combinationId="+combinationId;
			}else{
				$("#not_login").show();
			}
		});
}


	