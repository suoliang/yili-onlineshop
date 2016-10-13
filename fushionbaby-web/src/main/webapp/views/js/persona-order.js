$(document).ready(function() {
	//点击加粗
	$(".main-l dd button").click(function(event) {
		$(this).addClass('list-current').parent().parent().siblings().find('button').removeClass('list-current');
		$(this).addClass('list-current').parent().siblings().find('button').removeClass('list-current');
		$(this).siblings().find('.front').addClass('front-current').parent().parent().parent().siblings().find('.front').removeClass('front-current');
		$(this).siblings().find('.front').addClass('front-current').parent().parent().parent().parent().siblings().find('.front').removeClass('front-current');
	});
	//取消收藏下拉
	$(".collect-main li").hover(function() {
		$(this).find('.collect-drop').stop().slideToggle();
	});

/*	//点击取消收藏
	$(".collect-drop").click(function(event) {
		$(this).children('i').css('background-position','0 -329px');
		$(this).children('span').css('color','#757575');
		//$(this).parent().parent().hide();
	});*/

	//加载当前类
	/*$(".page-list").click(function(event) {
		$(this).addClass('page-list-current').siblings().removeClass('page-list-current');
	});*/

	//确认修改反白
	$(".user-info-l .mod-btn,.pass-mod .mod-btn,.pay-btn,.ent-btn button").hover(function() {
		$(this).css({"background-color":"#fff","color":"#7DDFF5"})
	}, function() {
		$(this).css({"background-color":"#7DDFF5","color":"#fff"})
	});

	//头像修改反白
	$(".user-info-r .mod-btn").hover(function() {
		$(this).css({"background-color":"#FFB7B7"})
	}, function() {
		$(this).css({"background-color":"#BDBDBD"})
	});

	//地址管理选择
	$(".add-select").click(function(event) {
		$(this).siblings().children('i').addClass('icon-current').parent().parent().siblings().find('i').removeClass('icon-current');
		$(this).find('.add').addClass('add-current').parent().parent().parent().siblings().find('.add').removeClass('add-current');
		// $(this).siblings('.add-ages-r').show().parent().siblings().children('.add-ages-r').hide();

	});

	//添加地址弹出
	$(".add-tit").click(function(event) {
		$(this).siblings('i').addClass('each-current');
		$(".new-addr").stop().hide();
		$(this).siblings('.new-addr').stop().show();
	});
	// 编辑地址弹出
	$(".txt").click(function(event) {
		$(".new-addr").stop().hide();
		// $(".add-address i").removeClass('each-current');
		$(this).siblings('.new-addr').stop().show();
	});
	//关闭按钮
	$(".cancel").click(function(event) {
		$(".new-addr").stop().hide();
		// $(".add-address i").removeClass('each-current');
	});
	//确定按钮
//	$(".submitButton button").hover(function() {
//		$(this).css({"background-color":"#7DDFF5","color":"#fff"})
//	}, function() {
//		$(this).css({"background-color":"#fff","color":"#7DDFF5"})
//	});

	//删除效果
//	$(".rub").click(function(event) {
//		if(window.confirm("你确定要删除该地址吗？")){
//			$(this).parent().parent("li").hide();
//		}
//	});
	//跳出框架链接到父页面
	//$(".go-pay").click(function(event) {
		//window.parent.location.href="checkout-list.html";
	//});

	//$(".pass-change").click(function(event) {
	//	window.parent.location.href="login.html";
	//});




	//receive
	//确认收货弹窗
	$(".con-rec-btn").click(function(event) {
		$(".fl-order",parent.document).show();
	});
	//关闭弹窗
	$(".close,.con-ec").click(function(event) {
		$(".fl-order",parent.document).hide();
		$(".upload-pic").hide();
	});

	//删除订单提示
	$(".del-order").click(function(event) {
		$(".de-order",parent.document).show();
	});
	$(".close,.con-ec").click(function(event) {
		$(".de-order",parent.document).hide();
	});



	//修改头像
	$(".user-info-r .mod-btn").click(function(event) {
		$(".upload-pic").show();
	});
	$(".c-upload").click(function(event) {
		$(".upload-pic").hide();
	});

	//修改密码成功
	//$(".info-desc").click(function(event) {
	//	$(this).siblings('.pass-check').show();
	//});

	// 购物车编辑按钮 开始
		// 弹窗定位
		$('.hide-edit').each(function(){
			var W_he = $(this).children('.hide-edit-l').outerWidth() + $(this).children('.hide-edit-r').outerWidth();
			$(this).css({'width':W_he+3,'left':-((W_he+2)/2 - 8)})
			$(this).children('.arrow-top').css({'left':(W_he+2)/2 - 8});
		})
		$('.hide-edit').hide();
		$('.icon-edit').click(function() {
			// 点击编辑图标：打开弹窗
			$('.hide-edit').hide();
			$(this).siblings('.hide-edit').toggle();
		});
		$('.car-edit .cancel').click(function(event) {
			// 点击取消修改按钮：关闭弹窗
			$('.hide-edit').hide();
		});
		$(".color-size .choose").click(function(event) {
			// 选项切换样式
			$(this).toggleClass("cs-current").siblings().removeClass('cs-current');
		});
	// 购物车编辑按钮 结束
});