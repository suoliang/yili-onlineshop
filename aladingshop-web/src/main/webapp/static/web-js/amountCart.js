/**数量设置**/
setAmountCart = {
	min : 1,
	max : 999,
	add : function(skuCode) {
		var countEl = $("#cart-count-"+skuCode+" .num");
		count = countEl.val();
		count >= this.max ? !1 : (count++, countEl.val(count));

		//同步数量到服务器,计算金额
		amountModify(skuCode,countEl.val());
	},
	reduce : function(skuCode) {
		var countEl = $("#cart-count-"+skuCode+" .num");
		count = countEl.val();
		count <= this.min ? !1 : (count--, countEl.val(count));
		//同步数量到服务器,计算金额
		amountModify(skuCode,countEl.val());
	},
	modify : function(skuCode) {
		var countEl = $("#cart-count-"+skuCode+" .num");
		if(""==countEl.val()){
			countEl.val(1);
		}
		count = countEl.val();
		var t = parseInt(countEl.val(), 10);
		"" == countEl.val() ? !1 : isNaN(t) || this.min > t || t > this.max ? (countEl.val(count), !1) : (count = t);

		//同步数量到服务器,计算金额
		amountModify(skuCode,countEl.val());
	}
};

//修改数量异步调用
function amountModify(skuCode,num){
	
	$.post(contextPath+"/cart/modifyItemQuantity",
		{
			skuCode : skuCode,
			quantity : num,
			time : new Date().getTime()
		},
		function(data){
			if(data.responseCode==0){
				window.location.reload();
				//修改购物车前端显示数量
//				$(".sidebar-car-num").text(data.data.pnumTotal);
//				$(".total-price i").text(data.data.priceTotal);
//				$("#rows-tprice-"+skuId).text(data.data.rowsCurrentPriceTotal);
			}else{
				alert(data.msg);
			}
		});//end post request
}

//修改数量,加入购物车前使用,不进行异步调用
setAmount = {
	min : 1,
	max : 999,
	count : 1,
	add : function() {
		this.count >= this.max ? !1 : ($("#cart-num").val(++this.count));
	},
	reduce : function() {
		this.count <= this.min ? !1 : ($("#cart-num").val(--this.count));
	},
	modify : function() {
		var t = parseInt($("#cart-num").val(), 10);
		"" == $("#cart-num").val() ? !1 : isNaN(t) || this.min > t || t > this.max ? ($("#cart-num").val(this.count), !1) : (this.count = t);
	}
};