$(document).ready(function() {
	$(".footer-links a").removeClass('fl-current');
	$(".front").removeClass('front-current');
	$(".footer-links a").click(function(event) {
		$(this).addClass('fl-current').parent().siblings().find("a").removeClass('fl-current');
		$(this).siblings('.subnav-arrow').find('.front').addClass('front-current').parent().parent().parent().siblings().find(".front").removeClass('front-current');
		$(this).parent().parent().siblings().find("a").removeClass('fl-current');
		$(this).parent().parent().siblings().find(".front").removeClass('front-current');
	});


	var href = window.location.href; //获取当前页面的URL
	var a = href.substring(href.lastIndexOf('?')); //截取问号后面的内容
	if (a == "?ZNX=1") {
		$("#iframepage2").attr("src", "help/foot-novice.html"); //设置iframe的src
	}
	if (a == "?ZNX=2") {
		$("#iframepage2").attr("src", "help/foot-problem.html");
	}
	if (a == "?ZNX=3") {
		$("#iframepage2").attr("src", "help/foot-agreement.html");
	}
	if (a == "?ZNX=4") {
		$("#iframepage2").attr("src", "help/foot-customer.html");
	}
	if (a == "?ZNX=5") {
		$("#iframepage2").attr("src", "help/foot-againstfraud.html");
	}
	if (a == "?ZNX=6") {
		$("#iframepage2").attr("src", "help/foot-com_sug.html");
	}
	if (a == "?ZNX=7") {
		$("#iframepage2").attr("src", "help/foot-aut_gua.html");
	}
	if (a == "?ZNX=8") {
		$("#iframepage2").attr("src", "help/foot-refund.html");
	}
	if (a == "?ZNX=9") {
		$("#iframepage2").attr("src", "help/foot-seven.html");
	}
	if (a == "?ZNX=10") {
		$("#iframepage2").attr("src", "help/foot-online_pay.html");
	}
	if (a == "?ZNX=11") {
		$("#iframepage2").attr("src", "help/foot-receipt.html");
	}
	if (a == "?ZNX=12") {
		$("#iframepage2").attr("src", "help/foot-logistics.html");
	}
	if (a == "?ZNX=13") {
		$("#iframepage2").attr("src", "help/foot-distribution.html");
	}
	if (a == "?ZNX=14") {
		$("#iframepage2").attr("src", "help/foot-aboutus.html");
	}
	if (a == "?ZNX=15") {
		$("#iframepage2").attr("src", "help/foot-joinus.html");
	}
	if (a == "?ZNX=16") {
		$("#iframepage2").attr("src", "help/foot-contact.html");
	}
	if (a == "?ZNX=17") {
		$("#iframepage2").attr("src", "help/foot-cooperation.html");
	}
	if (a == "?ZNX=18") {
		$("#iframepage2").attr("src", "help/foot-friend.html");
	}
	if (a == "?ZNX=19") {
		$("#iframepage2").attr("src", "help/foot-promise.html");
	}
	if (a == "?ZNX=20") {
		$("#iframepage2").attr("src", "help/foot-cash_pay.html");
	}



});