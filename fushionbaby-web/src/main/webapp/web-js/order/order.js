/**iframe加载完成,计算运费*/
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
/** 地址获得的运费*/
function address_freight(){

	var payoff_id = $("#payoff_id").val();
	var area_code=$("#area_code").val();
	if(area_code!=null && area_code != undefined && payoff_id != null && payoff_id != undefined){
		get_freight(payoff_id,area_code);
	}
	getMailingInfo();
}
/** 寄送至 ： 地址,收货人,电话*/
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

orderInit = {
	/**积分*/
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
			alert("您最多可用兜米数量为："+canUseEpoint);
			return ;
		}
		var payoff_id = $("#payoff_id").val();
		
		var area_code=$("#area_code").val();
		
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
					$("#use_integral").val(epointNum);
					$("#freight_price").text(data.data.freight_price);
					$(".total-price i").text(data.data.total_actual);
					$("#tax_price").text(data.data.tax_price);
					$("#price").val(data.data.total_actual);
					$("#can-use-epoint-strong").text(epointNum);
					$("#epointsMoney").text((parseFloat(epointNum) * 0.01).toFixed(2) );
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
	
	/**税*/
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
	
	
	/** 礼品卡充值*/
	chargeFund : function(){
		
		var giftCardNo = $("#giftCardNo").val();
		var giftCardPwd = $("#giftCardPwd").val();
		var url =_ContextPath + "/membercenter/chargeFund.do";
		$.ajax({
			type : "POST",
			url  : url,
			data : "cardNo="+giftCardNo+"&cardPassword="+giftCardPwd+"&time="+new Date().getTime(),
			async : false,
			dataType : "json",
			success : function(data) {
				if(data.responseCode==0){
					$(".coupon-error").show();
					$(".coupon-error").html(data.data);
				}
				
			}
		
		});
	},
	/**
	 * 更新账号余额
	 */
	loadAccountBalance : function(){
		var url = _ContextPath + "/membercenter/accountBalance.do";
		$.ajax({
			type : "POST",
			url  : url,
			async : false,
			dataType : "json",
			success : function(data) {	
				if (data == null || data == "" || data == undefined) {
					return;
				}
				if(data.responseCode==0){
					$("#card_ye").text(data.data.walletMoney);
					$("#canMoney").text(data.data.availableMoney);

				}else{
					alert(data.msg);
				}
			}
		});
	},
	/**
	 * 使用账号余额
	 */
	useAccountMoney : function(){
		var accountMoney =$("#accountMoney").val();
		if(accountMoney=="" || accountMoney==null){
			accountMoney = 0;
		}
		var canMoney = $("#canMoney").text();
		if(parseFloat(accountMoney) > parseFloat(canMoney)){
			$("#accountMoney").val(canMoney);
			alert("您最多可用金额为："+canMoney);
			return ;
		}
		var orderTotalPrice = $("#price").val();
		if(orderTotalPrice == ""){
			return ;
		}
		if(accountMoney >  parseFloat(orderTotalPrice)){
			$("#accountMoney").val(orderTotalPrice);
			accountMoney = orderTotalPrice;
			
		}
		$("#accountMoneyVal").val(accountMoney);
		var useAccountMoney = $("#accountMoneyVal").val(); 
		var isUseTax = "n";
		var items = $(":radio[name='chose1']:checked");
		var len=items.length;
		if(len>0){
			isUseTax = items[0].value;
		}
		var payoffId = $("#payoff_id").val();
		var areaCode=$("#area_code").val();
		
		var url = _ContextPath + "/order/useAccountBalance.do";

		$.ajax({
			type : "POST",
			url  : url,
			data : "payoffId=" + payoffId + "&useAccountMoney="+ useAccountMoney +"&areaCode="+areaCode+"&isUseTax="+isUseTax+"&time="+new Date().getTime(),
			async : false,
			dataType : "json",
			success : function(data) {				
				if (data == null || data == "" || data == undefined) {
					return;
				}
				if(data.responseCode==0){
				/*	$("#use_integral").text(data.data.integral_price);*/
					$("#freight_price").text(data.data.freight_price);
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
				
			}
		});
	},
	/** 
	 * 代金券
	 */
	getCardno : function(){
		var gotoType = $("#gotoType").val();
		if(gotoType==undefined){/* IE8的情况下*/
			gotoType = $("input[name=goto_type]").val();
		}
		var payoffId = $("#payoff_id").val();
		if(payoffId==undefined){/* IE8的情况下*/
			payoffId = $("input[name=payoff_id]").val();
		}
		
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
			data : "payoffId=" + payoffId + "&cardNo="+ card_no + "&isUseCardno="+is_use+"&areaCode="+area_code+"&isUseTax="+is_use_tax+"&gotoType="+gotoType+"&time="+new Date().getTime(),
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
		if(gotoType==undefined){/* IE8的情况下*/
			gotoType = $("input[name=goto_type]").val();
		}
		var payoffId = $("#payoff_id").val();
		if(payoffId==undefined){/* IE8的情况下*/
			payoffId = $("input[name=payoff_id]").val();
		}
		var addressId=$("#address_id").val();
		var isCardno = $("#use_cardno").val();//是否使用代金券
		var cardNo = $("#card_no").val();//代金券号码
		var items = $(":radio[name='chose1']:checked");
		var isInvoice= items[0].value;//是否需要发票
		var invoiceTitle = $.trim($("#invoice_title").val());//发票抬头的内容
		var memo = $.trim($("#message").val());
		if (memo.length > 200) {//留言长度限制
			$("#check_cardNo").html("留言请不要超过200字");
			$("#show_cardNo").show();
			$("#show_cardNo").fadeOut(5000);
			return;
		}
		if ("y" == isInvoice) {//如果要发票的话
			if (invoiceTitle.length == 0) {
				$("#check_cardNo").html("必须输入发票抬头");
				$("#show_cardNo").show();
				$("#show_cardNo").fadeOut(5000);
				return;
			}
			if (invoiceTitle.length > 100) {
				$("#check_cardNo").html("发票抬头不能多于100个字");
				$("#show_cardNo").show();
				$("#show_cardNo").fadeOut(5000);
				return;
			}
		}
		var paymentType = $("#paymentType").val();
		var sentTime = $("#sent_time").val();
		var isPoint = "n";
		var pointUsd = $("#use_integral").val();
		if(pointUsd !="" && (parseInt(pointUsd) > 0)){
			isPoint = "y";
		}
		var accountMoneyUsd = $("#accountMoney").val();
		var invoiceType = $("input[name='chose']:checked").val();
		
		if(addressId==null || addressId == undefined){
			alert("请选择收货地址!");
			return;
		}
		if(paymentType == null || paymentType == undefined || $.trim(paymentType).length<=0){
			alert("请选择支付类型!");
			return;
		}
		$("#ok_pay").attr("disabled", "disabled");
		
		$.post( _ContextPath + "/order/createOrder.do",{
			payOffId : payoffId,                
			addressId : addressId,
			isCardNo : isCardno,/*是否使用代金券*/
			cardNo : cardNo,
			isPoint : isPoint,/*是否使用积分*/
			isInvoice:isInvoice,/*是否要发票*/
			invoiceType : invoiceType,
			invoiceTitle : invoiceTitle,
			paymentType : paymentType,
			sendDate : sentTime,
			memo : memo,
			gotoType : gotoType,
			pointUsd:pointUsd,
			accountMoneyUsd:accountMoneyUsd,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					/*订单提交保存成功,跳转到哪个支付页面*/
					var orderCode=data.data;
					var totalActualTmp = getTotalMoney(payoffId);
					if(totalActualTmp!=null && totalActualTmp!=undefined && (!isNaN(totalActualTmp)) && $.trim(totalActualTmp).length>0){
						if(orderCode == undefined || orderCode==null || $.trim(orderCode).length<=0){
							alert("支付失败!订单号为空!");
						}else{
							$("#orderCode").val(orderCode);
							if($("#order_pay").html() == undefined){
								$("form[name=order_pay]").submit();
							}else{
								$("#order_pay").submit();
							}
						}
					}else{
						window.location.href=_ContextPath+"/membercenter/center.do?order";
					}
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
			data : "payoffId=" + payoff_id + "&areaCode="+ area_code + "&time="+new Date().getTime(),
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
function getTotalMoney(payoffId){
	var totalActualTmp=null;
	var url = _ContextPath + "/order/getTotalMoney.do";
	$.ajax({
		type : "POST",
		url  : url,
		data : "payoffId=" + payoffId + "&time="+new Date().getTime(),
		async : false,// 同步请求,本次请求完成后,后边的代码才会执行
		dataType : "json",
		success : function(data) {
			if (data == null || data == "" || data == undefined) {
				return null;
			}
			if(data.responseCode==0){
				//返回订单总金额
				totalActualTmp = data.data.total_actual;
			}else{
				alert(data.msg);
			}//end if responseCode
		}//end success
	});//end ajax
	
	return totalActualTmp;
}
