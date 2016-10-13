/**
 *
 * @authors Your Name (you@example.org)
 * @date    2015-03-18 14:55:38
 * @version $Id$
 */
;(function($) {
  $(window).load(function() {
    // 修改样式
    $('.brand_wrap ul li:nth-child(5n)').css({
      'margin-right': '0'
    });
    $('.item-num').eq(9).css({
      'margin-left': '-10px',
      'margin-right': '10px'
    });
    // console.log($('.brand_wrap ul li a').outerWidth());

    var brand_left_nav_h = $('.brand_left_nav').offset().top;
    var brand_right_h = $('.newpro-bd-r').offset().top;
    var brand_right_l = $('.brand_right').offset().left;
    var brand_right_max_h = $('body').height()-$('.footer').height()-$(window).height()-($('.newpro-bd-r').outerHeight()-$(window).height())-10;
    var brand_left_max_h = $('body').height()-$(window).height()-($('.brand_left_nav').outerHeight()+6+$('.footer').height()-$(window).height());
    $(window).scroll(function(){
      var H = $(window).scrollTop();
      // 字母导航定位
      if (H >= brand_left_nav_h && H < brand_left_max_h) {
        $('.brand_left_nav').css({
          'position':'fixed',
          'left':$('.brand_right').offset().left-33,
          'top':'20px'
        });
      } else if (H >= brand_left_max_h) {
          $('.brand_left_nav').css({
          'position':'fixed',
          'left':$('.brand_right').offset().left-33,
          'top':brand_left_max_h-H,
        });
      } else {
        $('.brand_left_nav').css({
          'position':'absolute',
          'left':'822px',
          'top':'100px'
        });
      }

      // 热销商品定位
      if (H > brand_right_h && H < brand_right_max_h) {
        $('.newpro-bd-r').css({
          'position':'fixed',
          'top':'1px',
          'left':brand_right_l
        });
      } else if (H >= brand_right_max_h) {
          $('.newpro-bd-r').css({
          'position':'fixed',
          'top':brand_right_max_h-H,
          'left':brand_right_l
        });
      } else {
        $('.newpro-bd-r').css({
          'position':'',
        });
      }
    })

    // 点击子母菜单滚动到对应品牌
    $('.brand_left_nav li').click(function(){
      var brand_left_nav_li_index = $(this).index();
      $(this).addClass('index_on').siblings().removeClass('index_on');
      $("body,html").animate({"scrollTop":$('.brand_wrap').eq(brand_left_nav_li_index).offset().top});
    })

    var brand_wrap_length = $('.brand_wrap').length;
    var h = [];
    for(i=0;i<brand_wrap_length;i++){
    	h[i] = $(".array_num"+i+"_box").offset().top;
    }
    $(window).scroll(function(){
    	var H = $(window).scrollTop();
    	for(i=0;i<brand_wrap_length;i++){

    		if(i == brand_wrap_length-1){
    			if(H >=h[i]){
    				h3_fix(i);
    			}
    		}
    		if(H >=h[i]  && H<h[i+1]){
    			h3_fix(i);
    		}
    	}
    	if(H < h[0]) {
           $('.h3_wrap h3').removeClass('h3_fixed').css({'left':'0'});
         }
    });

  })
})(jQuery);

function h3_fix(obj){
  var self = $(".array_num"+obj+"_box .h3_wrap h3");
  $('.h3_wrap h3').removeClass('h3_fixed').css({'left':'0'});
  self.addClass('h3_fixed').css({'top':'0','left':$('.brand_left').offset().left});
}