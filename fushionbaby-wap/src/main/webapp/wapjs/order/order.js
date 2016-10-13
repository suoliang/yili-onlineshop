//iframe加载完成,计算运费
function address_onload(){
	var address_items = $("#address-page").find("li[r='r']");
	if(address_items != undefined && address_items !=null && address_items.length>0){
		var len = address_items.length;
		for(i=0;i<len;i++){
			$(address_items[i]).bind("click", address_freight);
		}
	}
	getMailingInfo();
}

function address_freight(){
	var payoff_id = $("#payoff_id").val();
	//var address_id=$("#address_id").val();
	var area_code=$("#area_code").val();
	//alert("1address_id:"+address_id+",1area_code:"+area_code)
	if(area_code!=null && area_code != undefined && payoff_id != null && payoff_id != undefined){
		//alert("2address_id:"+address_id+",2area_code:"+area_code+",2payoff_id="+payoff_id);
		get_freight(payoff_id,area_code);
	}
	getMailingInfo();
}

orderInit = {
	//优惠券
//	useDiscount : function(id,sku_id){
//		id = "use_discount_"+id;
//		var payoff_id = $("#payoff_id").val();
//		var is_use = "n";
//		var checked = $("#"+id).attr("checked");
//		if(checked == undefined || checked == null){
//			$("#"+id).attr("checked",true);
//			is_use="y";
//		}else{
//			$("#"+id).attr("checked",false);
//			is_use="n";
//		}
//		
//		var area_code=$("#area_code").val();
//		
		/*var area_code="";
		var obj = $("#address-page").find("input[name='add']:checked");
		var address_id ="";
		if(obj != undefined && obj != null){
			address_id = obj.val();
		}
		if(address_id!=null && address_id!=undefined && address_id!=""){
			 var areaObj= $("#address-page").find("#updateProvinceId"+address_id);
			if(areaObj!=null && areaObj != undefined){
				area_code = areaObj.attr('pvalue');
			}
		}*/
		
//		var is_use_tax = "n";
//		var items = $(":radio[name='chose1']:checked");
//		var len=items.length;
//		if(len>0){//选中了,是否需要发票
//			is_use_tax = items[0].value;
//		}
		
//	},//end useDiscount
	
	
	//积分
	useEpoints : function(){
		
		var epointNum =parseInt($("#epointNum").val());
		var is_use = "y";
		if(isNaN(epointNum) ){
			is_use = "n";
			epointNum = 0; 
		}
		if(epointNum < 0){
			alert("请输入正确的兜米使用数量");
			return ;
		}
		var canUseEpoint = $("#can-use-epoint").val();
		if(epointNum > parseInt(canUseEpoint)){
			$("#epointNum").val(canUseEpoint);
			alert("您的兜米不够");
			return ;
		}
		var payoff_id = $("#payoff_id").val();
		
	/*	var use_epoints = $("#use_epoints").val();
		if("y" == use_epoints){
			is_use = "n";
			$("#use_epoints").val("n");
		}else{
			is_use = "y";
			$("#use_epoints").val("y");
		}*/
		
		
		var area_code=$("#area_code").val();
		/*var area_code="";
		var obj = $("#address-page").find("input[name='add']:checked");
		var address_id ="";
		if(obj != undefined && obj != null){
			address_id = obj.val();
		}
		if (address_id != null && address_id != undefined && address_id != "") {
			 var areaObj= $("#address-page").find("#updateProvinceId"+address_id);
			if(areaObj!=null && areaObj != undefined){
				area_code = areaObj.attr('pvalue');
			}
		}*/
		
		var is_use_tax = "n";
		var items = $(":radio[name='chose1']:checked");
		var len=items.length;
		if(len>0){//选中了,是否需要发票
			is_use_tax = items[0].value;
		}
		
		var url = _ContextPath + "/order/get_integral.do";
		$.ajax({
			type : "POST",
			url  : url,
			data : "payoff_id=" + payoff_id + "&is_use_integral="+is_use+"&area_code="+area_code+"&is_use_tax="+is_use_tax+"&epointNum="+epointNum+"&time="+new Date().getTime(),
			async : false,// 同步请求,本次请求完成后,后边的代码才会执行
			dataType : "json",
			success : function(data) {
				if (data == null || data == "" || data == undefined) {
					return;
				}
				if(data.responseCode==0){
					$("#use_integral").text(data.data.integral_price);
					$("#freight_price").text(data.data.freight_price);
					$(".total-price i").text(data.data.total_actual);
					$("#tax_price").text(data.data.tax_price);
					$("#price").val(data.data.total_actual);
					$("#can-use-epoint-strong").text(epointNum);
				}else{
					if(data.responseCode==202){
						goto_cart();
					}else{
						alert(data.msg);
					}
				}//end if responseCode
			}//end success
		});//end ajax
	},
	
	//税
	getTax : function(){
		var payoff_id = $("#payoff_id").val();
		var is_use = "n";
		var items = $(":radio[name='chose1']:checked");
		var len=items.length;
		if(len>0){//选中了,是否需要发票
			var item_value= items[0].value;
			if("y"==item_value){
				is_use = "y";
			}else{
				is_use = "n";
			}//end if item_value
			
			var url = _ContextPath + "/order/get_tax.do";
			//alert("payoff_id=" + payoff_id + "&is_use="+is_use+"&time="+new Date().getTime());
			$.ajax({
				type : "POST",
				url  : url,
				data : "payoff_id=" + payoff_id + "&is_use_tax="+is_use+"&time="+new Date().getTime(),
				async : false,// 同步请求,本次请求完成后,后边的代码才会执行
				dataType : "json",
				success : function(data) {
					if (data == null || data == "" || data == undefined) {
						return;
					}
					
					if(data.responseCode==0){
						$(".total-price i").text(data.data.total_actual);
						$("#tax_price").text(data.data.tax_price);
						$("#price").val(data.data.total_actual);
					}else{
						if(data.responseCode==202){
							goto_cart();
						}else{
							alert(data.msg);
						}
					}//end if responseCode
				}//end success
			});//end ajax
		}//end if len
	},
	
	/**
	 * 代金券
	 */
	getCardno : function(){
		var gotoType = $("#gotoType").val();
		var payoff_id = $("#payoff_id").val();
		var is_use = "n";
		var use_cardno = $("#use_cardno").val();
		if("n" == use_cardno){
			is_use = "y";
		}
		if("y" == use_cardno){
			is_use = "n";
		}
		var area_code=$("#area_code").val();
		var is_use_tax = "n";
		var items = $(":radio[name='chose1']:checked");
		var len=items.length;
		if(len>0){//选中了,是否需要发票
			is_use_tax = items[0].value;
		}
		var card_no = $.trim($("#card_no").val());
		if (card_no == null || card_no == "" || card_no == undefined) {
			$("#check_cardNo").html("请您输入代金券号码");
			$("#check_cardNo").show();
			$("#change-confirm").html("使用");
			$("#use_cardno").val("n");
			return;
		}
		var url = _ContextPath + "/order/getCardno.do";
		$.ajax({
			type : "POST",
			url  : url,
			data : "payoffId=" + payoff_id + "&cardNo="+ card_no + "&isUseCardno="+is_use+"&areaCode="+area_code+"&isUseTax="+is_use_tax+"&gotoType="+gotoType+"&time="+new Date().getTime(),
			async : false,// 同步请求,本次请求完成后,后边的代码才会执行
			dataType : "json",
			success : function(data) {
				if (data == null || data == "" || data == undefined) {
					return;
				}
				if(data.responseCode==0){
					$("#check_cardNo").show();
					$("#check_cardNo").text("￥：" +  data.data.cardno_price);
					$("#freight_price").text(data.data.freight_price);
					$(".total-price i").text(data.data.total_actual);
					$("#tax_price").text(data.data.tax_price);
					$("#price").val(data.data.total_actual);
					$("#use_cardno").val(is_use);
					if(is_use == "y"){
						$("#change-confirm").html("不使用");
					}else{
						$("#change-confirm").html("使用");
					}
				}else{
					if(data.responseCode==202){
						goto_cart();
					}else{
						$("#check_cardNo").text(data.msg);
						$("#check_cardNo").show();				
						return;
					}
				}//end if responseCode
			}//end success
		});//end ajax
	},
	
	create : function(){
		var gotoType = $("#gotoType").val();
		var address_id=$("#address_id").val();
		var payoff_id = $("#payoff_id").val();//标识码
		var is_cardno = $("#use_cardno").val();//是否使用代金券
		var card_no = $("#card_no").val();//代金券号码
		var items = $(":radio[name='chose1']:checked");
		var is_invoice= items[0].value;//是否需要发票
		var invoice_title = $.trim($("#invoice_title").val());//发票抬头的内容
		var memo = $.trim($("#message").val());
		if (memo.length > 200) {//留言长度限制
			$("#check_cardNo").html("留言请不要超过200字");
			$("#show_cardNo").show();
			$("#show_cardNo").fadeOut(5000);
			return;
		}
		if ("y" == is_invoice) {//如果要发票的话
			if (invoice_title.length == 0) {
				$("#check_cardNo").html("必须输入发票抬头");
				$("#show_cardNo").show();
				$("#show_cardNo").fadeOut(5000);
				return;
			}
			if (invoice_title.length > 100) {
				$("#check_cardNo").html("发票抬头不能多于100个字");
				$("#show_cardNo").show();
				$("#show_cardNo").fadeOut(5000);
				return;
			}
		}
		var paymentType = $("#paymentType").val();
		var sent_time = $("#sent_time").val();
		/*var coupons_ids = "";
		var chks = $("input[name='use_discount']:checked");
		for(i=0;i<chks.length;i++){
			if(i!=(len-1)){
				coupons_ids += chks.val()+"|";
			}else{
				coupons_ids += chks.val();
			}
		}*/
		var use_epoints = $("#use_epoints").val();
		if(use_epoints==null || use_epoints == undefined || use_epoints.length<=0){
			use_epoints="n";
		}
		var pointUsd = $("#epointNum").val();
		var invoice_type = $("input[name='chose']:checked").val();
		
		if(address_id==null || address_id == undefined){
			alert("请选择收货地址!");
			return;
		}
		if(paymentType == null || paymentType == undefined || $.trim(paymentType).length<=0){
			alert("请选择支付类型!");
			return;
		}
		$("#ok_pay").attr("disabled", "disabled");
		
		var total_actual_tmp = get_total_money(payoff_id);
		var total_actual_numtmp = null;
		if(total_actual_tmp!=null && total_actual_tmp!=undefined && (!isNaN(total_actual_tmp)) && $.trim(total_actual_tmp).length>0){
			total_actual_numtmp = parseFloat(total_actual_tmp);
		}
		$.post( _ContextPath + "/order/createOrder.do",{
			payOffId : payoff_id,                
			addressId : address_id,
			//couponsIds : coupons_ids,
			isCardno : is_cardno,/*是否使用代金券*/
			cardno : card_no,
			isPoint : use_epoints,/*是否使用积分*/
			isInvoice:is_invoice,/*是否要发票*/
			invoiceType : invoice_type,
			invoiceTitle : invoice_title,
			paymentType : paymentType,
			sendDate : sent_time,
			memo : memo,
			gotoType : gotoType,
			pointUsd:pointUsd,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					/*订单提交保存成功,跳转到哪个支付页面*/
					var orderCode=data.data;
					if(total_actual_numtmp!=null && total_actual_numtmp>0){
						if(orderCode==undefined || orderCode==null || $.trim(orderCode).length<=0){
							alert("支付失败!订单号为空!");
						}else{
							$("#orderCode").val(orderCode);
							$("#order_pay").submit();
						}
					}else{
						window.location.href=_ContextPath+"/membercenter/center.do?order";
					}
					
					//跳转到个人中心,我的订单
					//window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
					//window.location.href=_ContextPath+"/membercenter/center.do?order";
				}else{
					alert(data.msg);
				}
			}
		);//end post request
	}
};


/**
 * 运费计算
 */
function get_freight(payoff_id,area_code){
	if(area_code != null && area_code != undefined && area_code != ""){
		var url = _ContextPath + "/order/get_freight.do";
		$.ajax({
			type : "POST",
			url  : url,
			data : "payoff_id=" + payoff_id + "&area_code="+ area_code + "&time="+new Date().getTime(),
			async : false,// 同步请求,本次请求完成后,后边的代码才会执行
			dataType : "json",
			success : function(data) {
				if (data == null || data == "" || data == undefined) {
					return;
				}
				
				if(data.responseCode==0){
					$("#freight_price").text(data.data.freight_price);
					$(".total-price i").text(data.data.total_actual);
					$("#price").val(data.data.total_actual);
				}else{
					if(data.responseCode==202){
						goto_cart();
					}else{
						alert(data.msg);
					}
				}//end if responseCode
			}//end success
		});//end ajax
	}//end if address_id
}


//总金额,订单创建的时候使用
function get_total_money(payoff_id){
	var total_actual_tmp=null;
	var url = _ContextPath + "/order/get_total_money.do";
	$.ajax({
		type : "POST",
		url  : url,
		data : "payoff_id=" + payoff_id + "&time="+new Date().getTime(),
		async : false,// 同步请求,本次请求完成后,后边的代码才会执行
		dataType : "json",
		success : function(data) {
			if (data == null || data == "" || data == undefined) {
				return null;
			}
			if(data.responseCode==0){
				//返回订单总金额
				total_actual_tmp = data.data.total_actual;
			}else{
				alert(data.msg);
			}//end if responseCode
		}//end success
	});//end ajax
	
	return total_actual_tmp;
}
/**
 * 寄送至 ： 地址,收货人,电话
 */
function getMailingInfo(){
	var addressId = $("#address_id").val();
	if(addressId =="" || addressId ==null || addressId ==undefined){
		$("#mailing-address").html("");
		$("#mailing-consignee").html("");
		return ;
	}
	var procinceName = $("#choosed_"+addressId + " .province_name").html(); 
	var cityName = $("#choosed_"+ addressId +" .city_name").html();
	var districtName = $("#choosed_"+addressId+" .district_name").html();
	var addressTxt =  $("#choosed_"+addressId+" .address-txt").html();

	$("#mailing-address").html(procinceName + " " + cityName + " " + districtName +" " + addressTxt);
	var deliverName = $("#deliverName"+addressId).val();
	var phone = $("#phone" + addressId).val();
	$("#mailing-consignee").html(deliverName + " " +phone);


}