/**
 * help.js 用于help.html
 * @authors jie (you@example.org)
 * @date    2015-03-11 10:42:37
 * @version $Id$
 */
$(window).load(function(){

  // 点击子菜单滚动到对应容器
  $('.help_left li').click(function(){
    var num = $(this).index('.help_left li');
    $(this).addClass('help_on').siblings().removeClass('help_on');
    $("body,html").animate({"scrollTop":$('.help_box').eq(num).offset().top});
  })

  // 滚动页面，菜单切换悬浮
  var help_left_h = $('.help_left').offset().top;
  var max_scroll_h = $('body').height() - $(window).height() - $('.footer').outerHeight();

  var h1_1 = $("#1_1").offset().top-30;
  var h1_2 = $("#1_2").offset().top-30;
  var h1_3 = $("#1_3").offset().top-30;
  var h1_4 = $("#1_4").offset().top-30;
  var h1_5 = $("#1_5").offset().top-30;
  var h1_6 = $("#1_6").offset().top-30;
  var h1_7 = $("#1_7").offset().top-30;

  var h2_1 = $("#2_1").offset().top-30;
  var h2_2 = $("#2_2").offset().top-30;
  var h2_3 = $("#2_3").offset().top-30;

  var h3_1 = $("#3_1").offset().top-30;

  var h4_1 = $("#4_1").offset().top-30;
  var h4_2 = $("#4_2").offset().top-30;
  var h4_3 = $("#4_3").offset().top-30;

  var h5_1 = $("#5_1").offset().top-30;
  var h5_2 = $("#5_2").offset().top-30;
  var h5_3 = $("#5_3").offset().top-30;
  var h5_4 = $("#5_4").offset().top-30;
  var h5_5 = $("#5_5").offset().top-30;

  var win_h = $(window).scrollTop();
  if (win_h >= help_left_h) {
    $('.help_left').addClass('fixed');
  };
  if (win_h >= max_scroll_h) {
    $('.menu_0,.menu_1,.menu_2,.menu_3').addClass('menu_close');
    $('.menu_0_h3,.menu_1_h3,.menu_2_h3,.menu_3_h3').addClass('rotate');
  };

  $(window).scroll(function(){
    var H = $(window).scrollTop();
    if ( H > help_left_h ) {
      $('.help_left').addClass('fixed');
    } else {
      $('.help_left').removeClass('fixed');
    };
    // 页面滚动到一定程度，折叠若干菜单项
    if ( H > max_scroll_h ) {
      $('.menu_0,.menu_1,.menu_2,.menu_3').addClass('menu_close');
      $('.menu_0_h3,.menu_1_h3,.menu_2_h3,.menu_3_h3').addClass('rotate');
    }

    // 页面滚动，菜单对应改变颜色
    if (H >= h1_1 && H < h1_2) {
      addC($('.1_1_btn'));
    } else if (H >= h1_2 && H < h1_3) {
      addC($('.1_2_btn'));
    } else if (H >= h1_3 && H < h1_4) {
      addC($('.1_3_btn'));
    } else if (H >= h1_4 && H < h1_5) {
      addC($('.1_4_btn'));
    } else if (H >= h1_5 && H < h1_6) {
      addC($('.1_5_btn'));
    } else if (H >= h1_6 && H < h1_7) {
      addC($('.1_6_btn'));
    } else if (H >= h1_7 && H < h2_1) {
      addC($('.1_7_btn'));
    } else if (H >= h2_1 && H < h2_2) {
      addC($('.2_1_btn'));
    } else if (H >= h2_2 && H < h2_3) {
      addC($('.2_2_btn'));
    } else if (H >= h2_3 && H < h3_1) {
      addC($('.2_3_btn'));
    } else if (H >= h3_1 && H < h4_1) {
      addC($('.3_1_btn'));
    } else if (H >= h4_1 && H < h4_2) {
      addC($('.4_1_btn'));
    } else if (H >= h4_2 && H < h4_3) {
      addC($('.4_2_btn'));
    } else if (H >= h4_3 && H < h5_1) {
      addC($('.4_3_btn'));
    } else if (H >= h5_1 && H < h5_2) {
      addC($('.5_1_btn'));
    } else if (H >= h5_2 && H < h5_3) {
      addC($('.5_2_btn'));
    } else if (H >= h5_3 && H < h5_4) {
      addC($('.5_3_btn'));
    } else if (H >= h5_4 && H < h5_5) {
      addC($('.5_4_btn'));
    } else if (H >= h5_5) {
      addC($('.5_5_btn'));
    };

  })

  // 点击标题切换折叠菜单
  $('.help_left h3').click(function(){
    var menu_num = $(this).index('.help_left h3');
    var menu_li = '.menu_'+menu_num;
    $(this).toggleClass('rotate');
    $(menu_li).toggleClass('menu_close');
  })

  // 浏览器高度过小时，折叠若干项菜单
  if ($(window).height() < $('.help_left').outerHeight()) {
    $('.menu_1,.menu_2,.menu_3,.menu_4').addClass('menu_close');
    $('.menu_1_h3,.menu_2_h3,.menu_3_h3,.menu_4_h3').addClass('rotate');
  };

})

  function addC(obj) {
    obj.addClass('help_on').siblings().removeClass('help_on');
  }
