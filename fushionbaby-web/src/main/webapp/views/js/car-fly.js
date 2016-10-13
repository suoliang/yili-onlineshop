//detail.html
$(window).load(function() {

	/*	$(".cs").hover(function() {
		$(this).children('.boxa').show().siblings('.boxa').hide();
	}, function() {
		$(this).children('.boxa').hide();
	});*/

	$(".tabbar li a").click(function(event) {
		$(this).parent("li").addClass('selected').siblings("li").removeClass('selected');
	});

	$('.car').click(function(event) {
		$('.car,.bcar').not('.cart .bcar,.buycar .car').remove();
		//if ($(".size-choose a").hasClass('cs-current')&&$(".color-choose a").hasClass('cs-current')) {
		var flyElm = $('.car').clone().css('opacity', '0.5');
		flyElm.css({
			'z-index': 9000,
			'display': 'block',
			'position': 'absolute',
			'top': $('.car').offset().top + 'px',
			'left': $('.car').offset().left + 'px',
			'width': $('.car').width() + 'px',
			'height': $('.car').height() + 'px'
		});
		$('body').append(flyElm);
		flyElm.animate({
			top: $('.icar').offset().top,
			left: $('.icar').offset().left,
			width: 20,
			height: 20
		}, 'slow');
		//}else{
		//$(".csbox").css('border', '1px solid #FF8D8D');
		//$(".esc").show();
		//}
	});

	$('.bcar').click(function(event) {
		$('.car,.bcar').not('.cart .bcar,.buycar .car').remove();
		var flyElm = $('.bcar').clone().css('opacity', '0.5');
		flyElm.css({
			'z-index': 9000,
			'display': 'block',
			'position': 'absolute',
			'top': $('.bcar').offset().top + 'px',
			'left': $('.bcar').offset().left + 'px',
			'width': $('.bcar').width() + 'px',
			'height': $('.bcar').height() + 'px'
		});

		$('body').append(flyElm);
		flyElm.animate({
			top: $('.icar').offset().top,
			left: $('.icar').offset().left,
			width: 20,
			height: 20
		}, 'slow');
	});

	/*
	$('.collect').one('click', function(event){
		var flyElm = $('.collect').clone().css('opacity','0.5');

		flyElm.css({
			"background": "url("+_jsPath+"images/bcollect.png) no-repeat",
			'z-index': 9000,
			'display': 'block',
			'position': 'absolute',
			'top': $('.collect').offset().top +'px',
			'left': $('.collect').offset().left +'px',
			'width': $('.collect').width() +'px',
			'height': $('.collect').height() +'px'
		});
		$('body').append(flyElm);

		flyElm.animate({
			top:$('.icollect').offset().top,
			left:$('.icollect').offset().left,
			width:20,
			height:20
		},'slow');
	});

*/

	$(".bcar").hover(function() {
		$(this).attr('src', _jsPath + 'images/bcart.png');
	}, function() {
		$(this).attr('src', _jsPath + 'images/cart.png');
	});

	$(".car").hover(function() {
		$(this).attr('src', _jsPath + 'images/car.png');
	}, function() {
		$(this).attr('src', _jsPath + 'images/bcar.png');
	});

	/*	$(".collect").one('click', function(event){
			$(this).addClass('onclick-collect');
	});*/

});