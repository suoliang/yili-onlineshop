		$(document).ready(function() {
			var pepe = $.fn.fullpage({
				slidesColor: ['#superContainer', '#fff', '#fff', '#fff', '#fff'],
				anchors: ['firstPage', 'secondPage', '3rdPage', '4thpage','5thpage','lastPage'],
				menu: '#menu'
			});
/*			setInterval(function(){
				console.log($(".section.active").index());
				if($(".section.active").index()=1){
					$("#menu").fadeIn(500);
				}
			},200);*/

/*			$("#menu").mouseout(function(){
				$("#menu").slideToggle("slow");
		    });*/
		    });
			$(document).mousemove(function (e) {
				//console.log($(".section.active").index());
			if($(".section.active").index()==1){
				$("#menu").show();
			}
			/*else if(e.pageY<=100){
					$("#menu").fadeIn(500);
				} else{
					$("#menu").fadeOut(500);
					var obj = $("#menu").slideUp()
					// Throw exception, Object is not a function
					obj("slow");

				}*/
			})
			$(function () {
				$("#rank_tab li").click(function(){
					var tab = $(this);
					if(tab.hasClass("hover")) return;
					$("#rank_tab li").removeClass("hover");
					tab.addClass("hover");
					var index = tab.index();   /获得第一个匹配元素相对于其同胞元素的 index 位置。/
					$("#rank_list li").removeClass("block");
					$("#rank_list li:eq("+index+")").addClass("block");  /:eq() 选择器选取带有指定 index 值的元素。/
				});
			});
//-------------------------浮动向下滚动按钮---------------------------
$(document).ready(function () {
					setInterval("ab()", 500);
				});
				function ab() {
					var div=$(".scroll_btn");
					  div.animate({height:'85px',opacity:'0.9'},"slow");
					  div.animate({height:'72px',opacity:'0.5'},"slow");
				}
//-------------------------第一屏动画---------------------------
$(document).ready(function () {
			$(".menu_right").css('display','none');
			$(".menu_right_box").removeClass("menu_right_box1_hover");

			$('.section1').find('.topinfo').delay(300).animate({
				right:'5%',opacity:'1'
			}, 1000, 'easeOutExpo');

			$('.section1').find('#pic_1').addClass('animate1_1');
			$('.section1').find('#pic_1').css('opacity', '1');
			$('.section1').find('#pic_2').addClass('animate1_2');
			$('.section1').find('#pic_2').css('opacity', '1');
//-------------------------回调函数---------------------------
	function resetPosition(initialize){
		var init = false;
		if(typeof initialize != "undefined" && initialize){
			init = true;
		}
		console.log(init);
		/*var bofqi = $("#bofqi_embed");
		var img = $(".video img");
		var aspect = 980/620;
		bofqi.height(bofqi.width()/aspect);
		if(img.width()>=980){
			if(init){
				bofqi.delay(300).animate({"margin-left":-480,opacity:1}, 1500, 'easeOutExpo');
			} else{
				bofqi.css("margin-left",-480,"opacity",1);
			}
		}else{
			if(init){
				bofqi.delay(300).animate({"margin-left":"-39%",opacity:1}, 1500, 'easeOutExpo');
			} else{
				bofqi.css("margin-left","-39%","opacity",1);
			}
		}*/
	}
	$(window).resize(function(){
		resetPosition();
	});
	$.fn.fullpage({
		afterLoad: function(anchorLink, index){
			if(index == 1){
					$(".menu_right").css('display','none');
					$(".menu_right_box").removeClass("menu_right_box1_hover");
					$('.section1').find('.topinfo').delay(300).animate({
						right:'5%',opacity:'1'
					}, 1000, 'easeOutExpo');

					$('.section1').find('#pic_1').addClass('animate1_1');
					$('.section1').find('#pic_1').css('opacity', '1');
					$('.section1').find('#pic_2').addClass('animate1_2');
					$('.section1').find('#pic_2').css('opacity', '1');
			}
			if(index == 2){
				$(".menu_right").delay(200).fadeIn();
				$(".menu_right_box").removeClass("menu_right_box1_hover");
				$("#menu_right_box1").addClass("menu_right_box1_hover");

				$('.section2').find('.topinfo').delay(300).animate({
					right:'5%',opacity:'1'
				}, 1000, 'easeOutExpo');

				$('.section2').find('#pic_2233').addClass('animate2');
				$('.section2').find('#pic_2233').css('opacity', '1');

			}
			if(index == 3){
				$(".menu_right").delay(200).fadeIn();
				$(".menu_right_box").removeClass("menu_right_box1_hover");
				$("#menu_right_box2").addClass("menu_right_box1_hover");

				$('.section3').find('.topinfo').delay(300).animate({
						right:'5%',opacity:'1'
					}, 1000, 'easeOutExpo');
				$('.section3').find('.cloud').animate({opacity:'1'}, 2000);
				$('.section3').find('.sun').animate({opacity:'1',left: '2%'}, 1000);

			}
			if(index == 4){
				$(".menu_right").delay(200).fadeIn();
				$(".menu_right_box").removeClass("menu_right_box1_hover");
				$("#menu_right_box3").addClass("menu_right_box1_hover");

				$('.section4').find('.topinfo').delay(300).animate({
						right:'5%',opacity:'1'
					}, 1000, 'easeOutExpo');

				$('.section4').find('.ball').animate({opacity:'1'}, 1000);

				$('.section4').find('.ball_1').delay(100).animate({top:'360px'}, 500, 'easeInQuint');
				$('.section4').find('.ball_1').delay(100).animate({top:'345px'}, 500);
				$('.section4').find('.ball_3').delay(100).animate({top:'360px'}, 500, 'easeInQuint');
				$('.section4').find('.ball_3').delay(100).animate({top:'345px'}, 500);
				$('.section4').find('.ball_2').delay(100).animate({top:'343px'}, 500, 'easeInQuint');
				$('.section4').find('.ball_2').delay(100).animate({top:'300px'}, 500);
			}
			if(index == 5){
					$(".menu_right").delay(200).fadeIn();
					$(".menu_right_box").removeClass("menu_right_box1_hover");
					$("#menu_right_box4").addClass("menu_right_box1_hover");
					$('.section5').find('.topinfo').delay(300).animate({
						right:'5%',opacity:'1'
					}, 1000, 'easeOutExpo');


				$('.section5').find('.ware').animate({opacity:'1'}, 1000);
				$('.section5').find('.ware_1').delay(100).animate({'left':'-86px', 'top':'205px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_2').delay(100).animate({'left':'-132px', 'top':'95px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_3').delay(100).animate({'left':'-93px', 'top':'-9px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_4').delay(100).animate({'left':'32px', 'top':'-110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_5').delay(100).animate({'left':'166px', 'top':'-159px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_6').delay(100).animate({'left':'333px', 'top':'-45px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_7').delay(100).animate({'left':'454px', 'top':'29px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_8').delay(100).animate({'left':'426px', 'top':'211px'}, 500, 'easeInQuint');

			}
		},
		onLeave: function(index, direction){
			if(index == '1'){
				$('.section1').find('.topinfo').delay(200).animate({
						right: '-150%',opacity:'1'
					}, 500, 'easeOutExpo');

					$('.section1').find('#pic_1').removeClass('animate1_1');
					$('.section1').find('#pic_1').css('opacity', '0');
					$('.section1').find('#pic_2').removeClass('animate1_2');
					$('.section1').find('#pic_2').css('opacity', '0');

			}
			if(index == '2'){
				$('.section2').find('.top_logo').delay(200).animate({
						left: '150%',opacity:'1'
					}, 500, 'easeOutExpo');
					$('.section2').find('.topinfo').delay(200).animate({
						right: '-150%',opacity:'1'
					}, 500, 'easeOutExpo');
					$('.section2').find('#pic_2233').css('opacity', '0');
					$('.section2').find('#pic_2233').removeClass('animate2');
			}
			if(index == '3'){
				$('.section3').find('.topinfo').delay(200).animate({
						right: '-150%',opacity:'1'
					}, 500, 'easeOutExpo');
				$('.section3').find('.cloud').animate({opacity:'0'});
				$('.section3').find('.sun').animate({opacity:'1',left: '-150%'},0);
			}
			if(index == '4'){
				$('.section4').find('.topinfo').delay(200).animate({
						right: '-150%',opacity:'1'
					}, 500, 'easeOutExpo');
			}
			if(index == 5){
				$('.section5').find('.topinfo').delay(200).animate({
						right: '-150%',opacity:'1'
					}, 500, 'easeOutExpo');

				$('.section5').find('.ware').animate({opacity:'0'}, 1000);
				$('.section5').find('.ware_1').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_2').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_3').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_4').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_5').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_6').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_7').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');
				$('.section5').find('.ware_8').delay(50).animate({'left':'68px', 'top':'110px'}, 500, 'easeInQuint');

			}

		}
	});
		//弹窗三角滑动效果
		$('.open_bd li').hover(function() {
			var index=$(this).index();
			$('#tips').stop().animate({left:""+(index*196+135)+"px"}, 500);
			$(this).children('.open_ft').addClass('of_current').parent().siblings().children('.open_ft').removeClass('of_current');
		});
		//打开弹窗
		$('.content li,.top_weibo').click(function(event) {
			var index=$(this).index();
			$('.open_box').show('400');
			$('.open_wrap').show('400');

		});
		//关闭弹窗
		$('.close').click(function(event) {
			$('.open_wrap').hide();
			$('.open_box').hide();
		});
});
