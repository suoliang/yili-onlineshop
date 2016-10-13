/** 去结算*/
function cartConfirm(){
	
	var chks = $('.checkAll[checked=checked]');
	
	var len = chks.length;
	if(len==null||len==""||len<=0){
		openAddCartModal('0',"请至少选中一件商品!");
		return;
	}
	var codes=[];
	for(i=0;i<len;i++){
		codes.push(chks[i].value);
	}
	var url = contextPath + "/order/goOrderCheck";
	$.post(url,{
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.href=contextPath+"/order/goCartConfirm?time="+new Date().getTime();
			}else if(data.responseCode==201 || data.responseCode==300){
				$('#loginBox').fadeIn();
				$('#loginBox').find('.login-left').animate({'margin-left': '-194px', 'opacity': '1'}, 300);
			}else{
				alert(data.msg);
			}
	});
}
/** 登录成功之后调这个函数*/
function loginFun(){
	window.location.href=contextPath+"/cart/list.html?time="+new Date().getTime();
}
/** 使用积分*/
function usePoint(payOffId,areaCode,obj){
	var isUseTax = "n";
	var isUsePoint = "n";
	if(obj.checked){
		isUsePoint = "y";
	}
	var url = contextPath+"/order/usePoint";
	$.post(url,{
		payOffId : payOffId,
		isUsePoint : isUsePoint,
		areaCode : areaCode,
		isUseTax : isUseTax,
		time : new Date().getTime()},
		function(data){
		
			if(data.responseCode==0){
				
				$("#totalActual").text(parseFloat(data.data.totalActual).toFixed(2));
				
			}
			
		});
	
}
/** 使用优惠券*/
function useCoupon(txtCoupon,payOffId,areaCode,obj){
	var isUseCouponCard = "n";
	if(obj.checked){
		isUseCouponCard = "y";
	}
	var couponCode = $("#"+txtCoupon).val();
	if($.trim(couponCode) ==''){
		  openAddCartModal('0',"优惠券编号不能为空！");
		  $("#checkDiscount").removeAttr('checked').parents('.check-box').removeClass('checked');
		  return ;
	}
	
	var url = contextPath + "/order/useCoupon";
	
	$.post(url,{
		couponCode : couponCode,
		payOffId : payOffId,
		areaCode : areaCode,
		isUseCouponCard : isUseCouponCard,
		time : new Date().getTime()},
		function(data){
		
			if(data.responseCode==0){
				  $("#couponMoneyTxt").html(data.data.couponMoney);
				  $("#txtFreightPrice").html(data.data.freightPrice);
				  var newTotalActual = parseFloat(data.data.totalActual);
				  if(newTotalActual<0){
						newTotalActual = "0.00";
				  }
				  $("#totalActual").text(newTotalActual.toFixed(2));
				  
			}else{
				
				 openAddCartModal('0',data.msg);
				 $("#checkDiscount").removeAttr('checked').parents('.check-box').removeClass('checked');
			}
		});
}

/** 使用益多宝收益额*/
function useInterest(interestCard,interestPassword,totalActual,payOffId,areaCode,obj){
	var cardPwd = hex_md5($("#" + interestPassword).val()).toUpperCase();
	var inCard = $("#" + interestCard).val();
	var payCount = $("#" + totalActual).text();
	if($.trim(payCount) == '0.00' && isUseInterest == "y"){
		 openAddCartModal('0',"当前订单已为0.00，无需抵消！",2000);
		  $("#checkInterest").removeAttr('checked').parents('.check-box').removeClass('checked');
		  return ;
	}
	
	if($.trim(cardPwd) ==''){
		  openAddCartModal('0',"易多宝密码不能为空！",2000);
		  $("#checkInterest").removeAttr('checked').parents('.check-box').removeClass('checked');
		  return ;
	}
	
	var isUseInterest = "n";
	$("#"+interestCard).removeAttr('disabled');
	if(obj.checked){
		isUseInterest = "y";
		$("#"+interestCard).attr('disabled',true);
	}
	
	var url = contextPath + "/order/useInterest";
	
	$.post(url,{
		cardCode : inCard,
		cardPassword : cardPwd,
		areaCode : areaCode,
		payOffId : payOffId,
		isUseInterest : isUseInterest,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				  $("#inSubduction").html("&nbsp;-&nbsp;"+data.data.interestPrice+"&nbsp;元");
				  $("#txtFreightPrice").html(data.data.freightPrice);
				  var newTotalActual = parseFloat(data.data.totalActual);
				  if(newTotalActual<0){
						newTotalActual = "0.00";
				  }
				  $("#totalActual").text(newTotalActual.toFixed(2));
				  if(isUseInterest == 'y'){
					  $("#interestPassword").hide();
				  }else{
					  $("#interestPassword").val("");
					  $("#interestPassword").show();  
				  }
			}else{
				 openAddCartModal('0',data.msg,2000);
				 $("#"+interestCard).removeAttr('disabled');
				 $("#interestPassword").val("");
				 $("#checkInterest").removeAttr('checked').parents('.check-box').removeClass('checked');
			}
		});
}


/** 使用益多宝赠券额*/
function useRebate(useRebateCard,useRebatePassword,totalActual,payOffId,areaCode,obj){
	var cardPwd = hex_md5($("#" + useRebatePassword).val()).toUpperCase();
	var reCard = $("#" + useRebateCard).val();
	var payCount = $("#" + totalActual).text();

	if($.trim(payCount) == '0.00' && isUseRebate == "y"){
		 openAddCartModal('0',"当前订单已为0.00，无需抵消！",2000);
		  $("#checkRebate").removeAttr('checked').parents('.check-box').removeClass('checked');
		  return ;
	}
	
	if($.trim(cardPwd) ==''){
		  openAddCartModal('0',"易多宝密码不能为空！",2000);
		  $("#checkRebate").removeAttr('checked').parents('.check-box').removeClass('checked');
		  return ;
	}
	
	var isUseRebate = "n";
	$("#"+useRebateCard).removeAttr('disabled');
	if(obj.checked){
		isUseRebate = "y";
		$("#"+useRebateCard).attr('disabled',true);
	}
	
	var url = contextPath + "/order/useRebate";
	
	$.post(url,{
		cardCode : reCard,
		cardPassword : cardPwd,
		areaCode : areaCode,
		payOffId : payOffId,
		isUseRebate : isUseRebate,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				  $("#reSubduction").html("&nbsp;-&nbsp;"+data.data.rebatePrice+"&nbsp;元");
				  $("#txtFreightPrice").html(data.data.freightPrice);
				  var newTotalActual = parseFloat(data.data.totalActual);
				  if(newTotalActual<0){
						newTotalActual = "0.00";
				  }
				  $("#totalActual").text(newTotalActual.toFixed(2));
				  if(isUseRebate == 'y'){
					  $("#rebatePassword").hide();
				  }else{
					  $("#rebatePassword").val("");
					  $("#rebatePassword").show();  
				  }
			}else{
				 openAddCartModal('0',data.msg,2000);
				 $("#"+useRebateCard).removeAttr('disabled');
				 $("#rebatePassword").val("");
				 $("#checkRebate").removeAttr('checked').parents('.check-box').removeClass('checked');
			}
		});
}

function createOrder(payOffId){
	var memo = $("#txtMemo").val();
	var addressCheck = $('.cart-confirm-top').find('input[checked=checked]');
	if(addressCheck.val() == '' || addressCheck.val()=='on' || addressCheck.val()== undefined){
		openAddCartModal('0',"请选择收货地址！");
		return;
	}
	var inerestCard = $("#interestCardNo").val();
	var rebateCard = $("#rebateCardNo").val();
	var addressId = addressCheck.val();
	var  usePoint = $("#txtPointCheckBox").attr('checked');
	var isUsePoint = (usePoint==null || usePoint==undefined || usePoint==''   )?false:true;
	var  useCoupon = $("#checkDiscount").attr('checked');
	var isUseCoupon = (useCoupon==null || useCoupon==undefined || useCoupon==''  )?false:true;
	var payWay = $("#payWay").val();
	if(payWay=='' || payWay==undefined){
		
		openAddCartModal('0',"请选择一种支付方式！");
		return;
	}
	if(payWay=='alipay'){
		payWay = "ZFB_WEB_JSDZ";
	}else if(payWay=='weichat'){
		payWay = 'WX_WEB';
	}else if(payWay=='bank'){
		payWay = 'ZXYL_WEB';
	}
	$("#submitOrderBtn").attr("onclick","");
	$.post(contextPath + "/order/createOrder",{
		payOffId : payOffId,
		addressId:addressId,
		isUsePoint:isUsePoint==true?'y':'n',
		isUseCoupon:isUseCoupon==true?'y':'n',
		payWay:payWay,
		memo:memo,
		inerestCard:inerestCard,
		rebateCard:rebateCard,
		time : new Date().getTime()},
			function(data){
			
				if(data.responseCode==0){
					var orderCode = data.data.orderCode;
					if(orderCode == undefined || orderCode==null || $.trim(orderCode).length<=0){
						 openAddCartModal('0',"支付失败!订单号为空!");
						window.location.href=contextPath+"/cart/list.html";
					}else{
						var sid = data.data.sid;
						var payWay = data.data.payWay;
						var orderCode = data.data.orderCode;
						var temp = document.createElement("form");
						temp.action = contextPath + "/order/goPay?time="+new Date().getTime();
						temp.method = "post";
						temp.style.display = "none";
						
						var optSid = document.createElement("input");
						optSid.name="sid";
						optSid.value=sid;
						temp.appendChild(optSid);
						
						var optPayWay = document.createElement("input");
						optPayWay.name="payWay";
						optPayWay.value=payWay;
						temp.appendChild(optPayWay);
						
						var optOrderCode = document.createElement("input");
						optOrderCode.name="orderCode";
						optOrderCode.value=orderCode;
						temp.appendChild(optOrderCode);
						
						document.body.appendChild(temp);
						temp.submit();
					}
					
				}else if(data.responseCode==201){
					window.location.href=contextPath+"/login/index.htm";
				}else{
					window.location.href=contextPath+"/cart/list.html?time="+new Date().getTime();
				}
		}); 
}




