$(window).load(function() {
	var time = new Date();
	console.log(time);
	var Start_time = time.valueOf();
	console.log(Start_time);
	setInterval(function() {
		$(".endtime").each(function() {
			var obj = $(this);
			var endTime = new Date(parseInt(obj.attr('value')));
			var nowTime = new Date();
			var nMS = endTime.getTime() - nowTime.getTime();
			var myD = Math.floor(nMS / (1000 * 60 * 60 * 24));
			var myH = Math.floor(nMS / (1000 * 60 * 60)) % 24; //小时
			var myM = Math.floor(nMS / (1000 * 60)) % 60; //分钟
			var myS = Math.floor(nMS / 1000) % 60; //秒
			var myMS = Math.floor(nMS / 100) % 10; //拆分秒
			if (myD >= 0) {
				var str = myD + "天" + myH + "小时" + myM + "分" + myS + "." + myMS + "秒";
			} else {
				//obj.parent().siblings(".hide-a").attr("href","javascript:void(0)");

				var str = "活动已过期！";
			}
			obj.html(str);
		});
	}, 100);


	//返回顶部


	$(window).scroll(function(e) {
		//如果滚动坐标值大于window的高度的时候证明进入的第二屏就应该出现小火箭。
		var a = $(window).scrollTop();
		var b = $(window).height();

		if (a > b) {
			$('.scolltop').stop().fadeTo(300, 1);
		} else {
			$('.scolltop').stop().fadeTo(300, 0);
		}
	});

	$(".scolltop").click(function(event) {
		$("html,body").stop().animate({
			scrollTop: 0
		}, 1000);
	});



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
  if ($('#shoppingdetils').length !=0) {
    var dis=$('#shoppingdetils').offset().top;
  };

		$(window).scroll(function(e) {
	        var num=$(window).scrollTop();

			if(num>dis){
				$('#shoppingdetils').css('position','fixed').css('top',0);
			}else{
				$('#shoppingdetils').css('position','static');
			}

	    });


	//		我的订单js
	$('#my-order_choose ul li').click(function() {
		$(this).addClass('my-order_current').siblings().removeClass('my-order_current');
	});



	//		星星评价
			$('.order-detils2_pj ul li').click(function(){
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

		if ($('#car_shop li').length == 0 ) {
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
		$('.address_dian_current').parent().remove();//删除地址
		$('#Modal_address').modal('hide'); //显示对话框
	});

	//		footer-js
	var window_h = $(window).height();
	var body_h = $('body').height();
	if (window_h > (body_h + 100)) {
		$('body').height(window_h - 100);
	};

	//删除订单
	$('.my-order_pay_del').click(function() {
		$(this).parent().addClass('my-order_pay_del_li');
	});

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