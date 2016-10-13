$(window).load(function() {

	//		详情页js
	$('#shoppingdetils ul li').click(function() {
		$(this).addClass('shoppingdetils_current').siblings().removeClass('shoppingdetils_current');

		var oneli = $('#shoppingdetils ul li:first');
		if (oneli.hasClass('shoppingdetils_current')) {
			$('.shopping_pic').show();
			$('.shopping_reviewsall').hide();
			$('.shopping_like').hide();
		}

		var secondli = $('#shoppingdetils ul li:eq(1)');
		if (secondli.hasClass('shoppingdetils_current')) {
			$('.shopping_pic').hide();
			$('.shopping_reviewsall').show();
			$('.shopping_like').hide();
		}

		var lastli = $('#shoppingdetils ul li:eq(2)');
		if (lastli.hasClass('shoppingdetils_current')) {
			$('.shopping_pic').hide();
			$('.shopping_reviewsall').hide();
			$('.shopping_like').show();
		}
	});

	//			导航跟随js
	if ($('#shoppingdetils').length != 0) {
		var dis = $('#shoppingdetils').offset().top;
	};

	$(window).scroll(function(e) {
		var num = $(window).scrollTop();

		if (num > dis) {
			$('#shoppingdetils').css('position', 'fixed').css('top', 0);
		} else {
			$('#shoppingdetils').css('position', 'static');
		}

	});

	//		我的订单js
	$('#my-order_choose ul li').click(function() {
		$(this).addClass('my-order_current').siblings().removeClass('my-order_current');

		var oneli = $('#my-order_choose ul li:first');
		if (oneli.hasClass('my-order_current')) {
			$.ajax({
				type: 'post',
				url: _ContextPath + "/membercenter/order.do?time=" + new Date().getTime(),
				async: false,
				dataType: "json",
				success: function(data) {
					if (data.responseCode == '0') {
						$('#my-order_shop').show();
						$('#my-order_shop2').hide();
						$('#my-order_shop3').hide();
						$('#my-order_shop4').hide();
					}
				}
			});
		}

		var secondli = $('#my-order_choose ul li:eq(1)');
		if (secondli.hasClass('my-order_current')) {
			$('#my-order_shop').hide();
			$('#my-order_shop2').show();
			$('#my-order_shop3').hide();
			$('#my-order_shop4').hide();
		}

		var lastli = $('#my-order_choose ul li:eq(2)');
		if (lastli.hasClass('my-order_current')) {
			$('#my-order_shop').hide();
			$('#my-order_shop2').hide();
			$('#my-order_shop3').show();
			$('#my-order_shop4').hide();
		}

		var lastli = $('#my-order_choose ul li:eq(3)');
		if (lastli.hasClass('my-order_current')) {
			$('#my-order_shop').hide();
			$('#my-order_shop2').hide();
			$('#my-order_shop3').hide();
			$('#my-order_shop4').show();
		}
	});

	//		星星评价
	$('.order-detils2_pj ul li').click(function() {
		$(this).addClass('star');
		$(this).prevAll().addClass('star');
		$(this).nextAll().removeClass('star');

	});
	//
	//		$('.shopping_reviews2_detils ul li').click(function(){
	//			$(this).addClass('star');
	//			$(this).prevAll().addClass('star');
	//			$(this).nextAll().removeClass('star');
	//
	//		})

	//		购物车js
	$('.car_dian').click(function() {
		$(this).toggleClass('car_current');

	});

	$('.car_del').click(function() {

		var car1 = $('.car_dian');
		if (car1.hasClass('car_current')) {
			$('.car_current').parent().remove();
		}

		if ($('#car_shop li').length == 0) {
			// 如果购物车为空，显示提示
			$('#car-none').fadeIn();
			$("#gopay").hide();
		};
	});

	//		地址管理js
	$('.address_dian').click(function() {
		$(this).toggleClass('address_dian_current');

	});

	// 删除地址
	$(function() {
		$(".g-header-img_del").click(function() {
			if ($('.address_dian_current').length != 0) {
				$('#Modal_address').modal('show'); //显示对话框
			};
		});
	});
	$('.Modal_address_ok').click(function() {
		$('.address_dian_current').parent().remove(); //删除地址
		$('#Modal_address').modal('hide'); //显示对话框
	});

	//		footer-js
	var window_h = $(window).height();
	var body_h = $('body').height();
	var foot_h = $("#foot").outerHeight();
	if (window_h > (body_h + foot_h)) {
		$('body').height(window_h - foot_h);
	};

	//		商品列表
	$('#products-detils li').click(function() {
		$(this).addClass('products-detils_current').siblings().removeClass('products-detils_current');

		var pli = $('#products-detils li:eq(1)');
		if (pli.hasClass('products-detils_current')) {
			$('.pimg2').attr('src', 'images/431.gif');
		} else {
			$('.pimg2').attr('src', 'images/43.gif');
		}

		var pli2 = $('#products-detils li:first');
		if (pli2.hasClass('products-detils_current')) {
			$('.pimg1').attr('src', 'images/42.gif');
		} else {
			$('.pimg1').attr('src', 'images/421.gif');
		}

		var pli3 = $('#products-detils li:eq(2)');
		if (pli3.hasClass('products-detils_current')) {
			$('.pimg3').attr('src', 'images/441.gif');
		} else {
			$('.pimg3').attr('src', 'images/44.gif');
		}
	});

});