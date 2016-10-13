window.onload = function() {


	//checkout-list 单击效果

	//支付变色
	$(".mode li").click(function(event) {
		$(this).find('.changecolor').css('color', '#FDD17D').parent().parent().siblings().find('.changecolor').css('color', '#757777');
		$(this).addClass("checked").siblings().removeClass("checked");
		$(".moren").attr({'checked':'checked'})
	});
	$(".moren").click(function(event) {
		$(this).find('.changecolor').css('color', '#FDD17D').parent().parent().siblings().find('.changecolor').css('color', '#757777');
		$(".mode li").addClass("checked").siblings().removeClass("checked");
	});
/*////////////////	确认订单页 配送时间  开始  jiezhiyong////////////////*/
	$('.time_choose a').click(function(event) {
		$('#sent_time').val($(this).attr('title'));
		$(this).addClass('choosed').siblings().removeClass('choosed');
	});
/*////////////////	确认订单页 配送时间  结束  ////////////////*/

/*////////////////	确认订单页 发票  开始  jiezhiyong////////////////*/
	$(".fp").click(function(event) {
		$(this).toggleClass('invoice-hide-box-open');
		$('.getfp').toggle();
	});

	$('#per1').click(function(){
		$('.invoice-hide-box').hide();
	})
	$('#com1').click(function(){
		$('.invoice-hide-box').show();
	})

	$('.invoice-btn').click(function() {
		$('.getfp').hide();
		$('.fp').removeClass('invoice-hide-box-open');
	});
/*////////////////	确认订单页 发票  结束  ////////////////*/

/*////////////////	确认订单页 优惠券等折扣弹窗  开始  jiezhiyong////////////////*/
 $('.discount-btn').click(function() {
 	$(this).siblings('.discount-box').toggle().parents('.discount-wrap').siblings('.discount-wrap').children('.discount-box').hide();
 	$(this).toggleClass('discount-btn-current').parents('.discount-wrap').siblings('.discount-wrap').children('.discount-btn').removeClass('discount-btn-current');
 });
 $('.discount-box .cancel').click(function() {
 	$('.discount-box').hide();
 });
/*////////////////	确认订单页 优惠券等折扣弹窗  结束  ////////////////*/



	//优惠券
	$(".cheaper-btn").hover(function() {
		$(this).siblings('.cheaper-title').css('color', '#31C6A9');
		$(this).css({"color": '#fff',"background-color": '#31C6A9'});
	}, function() {
		$(this).siblings('.cheaper-title').css('color', '#373737');
		$(this).css({"color": '#31C6A9',"background-color": '#fff'});
	});

	$(".cheaper-btn").bind('click', function(event) {
		if ($(this).hasClass('btn-current')) {
			$(this).siblings('.cheaper-money').hide();
			$(this).siblings('.cheaper-num').hide();
			//$(this).css({"color": '#fff',"background-color": '#31C6A9'});
			$(this).removeClass('btn-current');
			$(this).siblings('.cheaper-title').removeClass('btn-current');
			$(this).html("使用");
		}else{
			$(this).siblings('.cheaper-money').show();
			$(this).siblings('.cheaper-num').show();
			//$(this).css({"color": '#fff',"background-color": '#31C6A9'});
			$(this).addClass('btn-current');
			$(this).siblings('.cheaper-title').addClass('title-current');
			$(this).html("不使用");
		}
	});


	$(".change-c").hover(function() {
		$(this).css({"background-color":"#9E8F70","color":"#fff"});

	}, function() {
		$(this).css({"background-color":"#fff","color":"#9E8F70"});

	});
	$(".change-c").click(function(event) {
		if ($(this).hasClass('c-current')) {
			$(this).siblings('.cheaper-money').hide();
			$(this).siblings('.cheaper-num').hide();
			//$(this).css({"color": '#fff',"background-color": '#31C6A9'});
			$(this).removeClass('c-current');
			$(this).siblings('.cheaper-title').removeClass('c-current');
			$(this).html("使用");
		}else{
			$(this).siblings('.cheaper-money').show();
			$(this).siblings('.cheaper-num').show();
			//$(this).css({"color": '#fff',"background-color": '#31C6A9'});
			$(this).addClass('c-current');
			$(this).html("不使用");
		}
	});

	//确认订单弹出框
	/*$('').click(function(event) {
		$('.pop-pay').show();
	});*/

	// 新增地址按钮
	// $("#add-address-btn").click(function(){
	// 	$("#add-address").show();
	// })
	$("#address-li .address-li").click(function(){
		$(this).addClass('choosed').siblings().removeClass('choosed');
		var address_items = $(this).find("input[name='address_id']");
		if(address_items != undefined && address_items !=null && address_items.length>0){
			var address_id = address_items[0].value;
			var area_code = $(this).find("#updateProvinceId"+address_id).attr('pvalue');
			//	alert("address_id:"+address_id+",area_code:"+area_code)
			$("#address_id").val(address_id);
			$("#area_code").val(area_code);
		}
	})

};