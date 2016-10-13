//页面最上面的的mini购物车展示
$(window).load(function() {
	cartList();
});

$(window).load(function() {
	//页面选中默认全部选中购买项
	$(".dot").each(function(){
		var id = this.id;
		var checked = $("#chk-"+id).attr("checked");
		if(checked){
			$(this).css("background-position","-2px -292px");
		}else{
			$(this).css("background-position","-2px -310px");
		}
	});

	//全选,全不选复选框,注册事件
	$(".cmf-l-wrap .dot").click(function(event){
		var checked = $("#all").attr("checked");
		if(checked == undefined || checked == null){
			//原先没有选中,选中全选单选按钮
			$("#all").attr("checked",true);
			$(".dot").css("background-position","-2px -292px");

			//循环选中购物车所有项
			var chks = $("input:checkbox[r=r]");
			var len = chks.length;
			var skuIds = "";
			for(i=0;i<len;i++){
				var chk = chks[i];
				$(chk).attr("checked",true);
				if(i!=(len-1)){
					skuIds += chk.value+",";
				}else{
					skuIds += chk.value;
				}
			}
			//同步选择结果到服务器
			cart.selected(skuIds,"y");
		}else{
			//原先选中了,取消选中单选按钮
			$("#all").attr("checked",false);
			$(".dot").css("background-position","-2px -310px");

			//循环取消选中购物车所有项
			var chks = $("input:checkbox[r=r]");
			var len = chks.length;
			var skuIds = "";
			for(i=0;i<len;i++){
				var chk = chks[i];
				$(chk).attr("checked",false);
				if(i != (len-1)){
					skuIds += chk.value+",";
				}else{
					skuIds += chk.value;
				}
			}

			//同步选择结果到服务器
			cart.selected(skuIds,"n");
		}//end if else checked
	});
});


//购物车选中不选中,单选按钮注册事件
function item_selected(obj){
	var checked = $(obj).find("input:checkbox").attr("checked");
	var selectChk = $(obj).find("input:checkbox");//dot下的checkbox对象
	var is_selected = "n";//记录是否被选中,n默认没有被选中
	var sku_id = selectChk.val();//操作按钮中的value值,这里记录的商品id
	if(checked == undefined || checked == null){
		//原先没有选中,设置为选中
		$(obj).css("background-position","-2px -292px");
		selectChk.attr("checked",true);
		is_selected = "y";
	}else{
		//原先选中了,设置为不选中
		$(obj).css("background-position","-2px -310px");
		selectChk.attr("checked",false);
		is_selected = "n";
	}

	//同步选择结果到服务器
	cart.selected(sku_id,is_selected);
}

//购物车操作
cart = {
	/**
	 * 添加单个商品到购物车
	 * @param int sku_id 商品id
	 * @param int pnum 商品数量
	 * @param String color 颜色
	 * @param String size  尺寸
	 */
	addToCart : function(sku_id, pnum){
		$.post(_ContextPath + "/cart/addToCart.do",{
			sku_id : sku_id,
			quantity : pnum,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					//修改购物车前端显示数量
					$(".sidebar-car-num").text(data.data.pnum);
					cartList();//重新加载mini购物车下拉列表
				}else{
					alert(data.msg);
				}
			}
		);//end post request
	},


	//从购物车中删除一件商品行记录
	miniRemove : function(skuId){
		var url = _ContextPath + "/cart/removeBath.do";
		$.post(url,{
			ids : skuId,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					//修改购物车前端显示数量
					$(".sidebar-car-num").text(data.data.pnum);
					cartList();//重新加载mini购物车下拉列表

					//重新设置显示隐藏mini-cart显示效果
					var num=parseInt($("#rows-num").text());
					if(num==0){
						$("#lot").hide();
						$("#none").show();
					}else{
						$("#none").hide();
						$("#lot").show();
					}
				}else{
					alert(data.msg);
				}//end if responseCode
			}//function end
		);//end post request
	},

	//从购物车中删除一件商品行记录
	remove : function(){
		var skuid = $("#get_skuId").val();
		var type = $("#get_Type").val();
		var ids= skuid;
		//删除单个
		if (type == 1) {
			var url = _ContextPath + "/cart/removeBath.do";
			$.post(url,{
				ids : ids,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						var pnumTotal=data.data.pnumTotal;
						if(pnumTotal==0 || pnumTotal=="0" || pnumTotal == null || pnumTotal == undefined){
							var urlTmp = _ContextPath+"/cart/cartList.do?time="+new Date().getTime();
							window.location.href = urlTmp;
						}else{
							//修改购物车前端显示数量
							$(".sidebar-car-num").text(pnumTotal);
							$(".total-price i").text(data.data.priceTotal);
							$("#rows-"+skuid).remove();//删除购物车中的行
							$(".de-order").hide();//弹出层的隐藏
						}
					}else{
						alert(data.msg);
					}
				}
			);//end post request
		}
		//批量删除
		if (type == 2) {
			var chks =$(".order-table input[checked=checked]");
			var len = chks.length;
			if(len==null||len==""||len<=0){
				alert("请至少选中一件商品!");
				return;
			}
			var ids="";
			for(i=0;i<len;i++){
				if(i==(len-1)){
					ids += chks[i].value;
				}else{
					ids += chks[i].value+",";
				}
			}
			//发出删除请求
			var url = _ContextPath + "/cart/removeBath.do";
			$.post(url,{
				ids : ids,
				time : new Date().getTime()},
				function(data){
					if(data.responseCode==0){
						var pnumTotal=data.data.pnumTotal;
						if(pnumTotal==0 || pnumTotal=="0" || pnumTotal == null || pnumTotal == undefined){
							var urlTmp = _ContextPath+"/cart/cartList.do?time="+new Date().getTime();
							window.location.href = urlTmp;
						}else{
							//修改购物车前端显示数量
							$(".sidebar-car-num").text(data.data.pnumTotal);
							$(".total-price i").text(data.data.priceTotal);
							//循环删除浏览器中的购物行
							for(i=0;i<len;i++){
								$("#rows-"+chks[i].value).remove();//删除购物车中的行
							}
							$(".de-order").hide();//弹出层的隐藏
						}
					}else{
						alert(data.msg);
					}//end responseCode
				}//end function
			);//end post request
		}
	},

	//选中和放弃选中单个
	selected : function(sku_id,is_selected){
		var url = _ContextPath + "/cart/modify_item_selected.do";
		$.post(url,{
			skuIds : sku_id,
			is_selected : is_selected,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					//修改购物车前端显示数量
					$(".sidebar-car-num").text(data.data.pnumTotal);
					$(".total-price i").text(data.data.priceTotal);
				}else{
					alert(data.msg);
				}
			}
		);//end post request
	}
};

//垃圾箱````
function deleOver(obj){
	$(obj).css("background-position", "0 -131px");
}
function deleOut(obj){
	$(obj).css("background-position", "-1px -101px");
}

//购物车商品列表查询,用于初始化mini-cart-list,精简的cart滑动下拉列表
function cartList(){
	var url = _ContextPath + "/cart/miniCartList.do";
	$.ajax({
		type : "POST",
		url : url,
		data : "time="+new Date().getTime(),
		async : false,// 同步请求,本次请求完成后,后边的代码才会执行
		dataType : "json",
		success : function(data) {
			if(data.responseCode==0){
				var pnum = data.data.pnumTotal;
				if(pnum == null || pnum == NaN || pnum == undefined){
					pnum = 0;
				}
				var price = data.data.priceTotal;
				if(price == null || price == NaN || price == undefined){
					price = 0;
				}
				$(".sidebar-car-num").text(pnum);
				$(".shop-num").text(pnum);
				$("#g-num-j").text(pnum);
				$("#hj-price").text(price);

				//list列表填充
				var items = data.data.items;
				var len = items.length;
				var count=0;
				var itemsHtml = "<ul>";
				for(i=0;i<len;i++){
					count++;
					var item =items[i];
					var str_item = "<li class='car-module'>" +
							"<div class='car-module-l'>" +
							"<a href='"+_ContextPath+"/product/skuDetail.do?skuId="+item.skuId+"'>"+
							"<img src='"+item.imgPath+"' alt='"+item.name+"'></a>" +
							"</div>" +
							"<div class='car-module-m'>" +
							"	<p class='cmm-title'>" +
							"		<span class='ct-txt'><a href='"+_ContextPath+"/product/skuDetail.do?skuId="+item.skuId+"'>"+item.name+"</a></span>" +
							"		<span class='ct-num'>X<em class='num'>"+item.pnum+"</em></span></p>" +
							"	<p class='desc-color'>颜色："+item.color+"&nbsp;&nbsp;&nbsp;&nbsp;尺寸："+item.size+"</p>" +
							"</div>" +
							"<div class='car-module-r'><i class='dele del-btn del-order' onclick='cart.miniRemove("+item.skuId+")' onmouseover='deleOver(this)' onmouseout='deleOut(this)'></i></div></li>";

					itemsHtml += str_item;
				}
				itemsHtml+="</ul>";

				var last="<div class='car-line'></div><div class='add-up clearfix'>" +
						"<div class='add-up-l'><p>共<span id='g-num-j'>"+pnum+"</span>件商品</p>" +
						"<p>合计：&yen;<span id='hj-price'>"+price+"</span>元</p></div>" +
						"<a href='javascript:goto_cart();' class='add-up-r'>去购物车结算</a><span id='rows-num' style='display:none'>"+count+"</span></div>";
				itemsHtml +=last;
				//lot下的.mini-cart-wrapper
				$("#lot .mini-cart-wrapper").empty();//清空原有内容
				$("#lot .mini-cart-wrapper").append(itemsHtml);//增加新的内容
			}else{
				alert(data.msg);
			}//返回成功结果状态码
		}
	});
}//cartList funciton end

//mini购物车显示,隐藏
$(".shop").hover(function() {
	var num=parseInt($("#rows-num").text());
	if(num==0){
		$("#lot").hide();
		$("#none").show();
	}else{
		$("#none").hide();
		$("#lot").show();
	}},function() {
		$(this).children('.mini-cart-list').css('display', 'none');
	});

//数量设置
setAmountCart = {
	min : 1,
	max : 9999999999,
	add : function(skuId) {
		var countEl = $("#baby-num-"+skuId);
		count = countEl.val();
		count >= this.max ? !1 : (count++, countEl.val(count));

		//同步数量到服务器,计算金额
		amountModify(skuId,countEl.val());
	},
	reduce : function(skuId) {
		var countEl = $("#baby-num-"+skuId);
		count = countEl.val();
		count <= this.min ? !1 : (count--, countEl.val(count));
		//同步数量到服务器,计算金额
		amountModify(skuId,countEl.val());
	},
	modify : function(skuId) {
		var countEl = $("#baby-num-"+skuId);
		if(""==countEl.val()){
			countEl.val(1);
		}
		count = countEl.val();
		var t = parseInt(countEl.val(), 10);
		"" == countEl.val() ? !1 : isNaN(t) || this.min > t || t > this.max ? (countEl.val(count), !1) : (count = t);

		//同步数量到服务器,计算金额
		amountModify(skuId,countEl.val());
	}
};

//修改数量异步调用
function amountModify(skuId,num){
	var url = _ContextPath + "/cart/modify_item_quantity.do";
	$.post(url,{
		skuId : skuId,
		quantity : num,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				//修改购物车前端显示数量
				$(".sidebar-car-num").text(data.data.pnumTotal);
				$(".total-price i").text(data.data.priceTotal);
				$("#rows-tprice-"+skuId).text(data.data.rowsCurrentPriceTotal);
			}else{
				alert(data.msg);
			}
		}
	);//end post request
}

//修改数量,加入购物车前使用,不进行异步调用
setAmount = {
	min : 1,
	max : 9999999999,
	count : 1,
	countEl : $("#baby-num"),
	add : function() {
		this.count >= this.max ? !1 : (this.count++, this.countEl.val(this.count));
	},
	reduce : function() {
		this.count <= this.min ? !1 : (this.count--, this.countEl.val(this.count));
	},
	modify : function() {
		var t = parseInt(this.countEl.val(), 10);
		"" == this.countEl.val() ? !1 : isNaN(t) || this.min > t || t > this.max ? (this.countEl.val(this.count), !1) : (this.count = t);
	}
};

function gotoCart(){
	var urlTmp = _ContextPath+"/cart/cartList.do?time="+new Date().getTime();
	window.location.href = urlTmp;
}

