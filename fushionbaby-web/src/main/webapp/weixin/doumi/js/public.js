$(window).load(function(){
  $('.animation_0').animate({'width':'40%'},800,'easeOutBounce');
  $('.animation_1').animate({'width':'99%'},1000,'easeOutBounce');

  setInterval("up_down()", 500);
  setInterval("question()", 600);
  setInterval("animation14()", 600);
  setInterval("animation15()", 500);
  setInterval("animation19()", 800);

  var animation_5_h = $('.animation_5').offset().top;
  var animation_7_h = $('.animation_7').offset().top;
  var animation_10_h = $('.animation_10').offset().top;
  var animation_12_h = $('.animation_12').offset().top;
  var animation_18_h = $('.animation_18').offset().top;

  $(window).scroll(function(){
    var win_h = $(window).scrollTop();

    if (win_h >= animation_5_h - 300) {
      $('.animation_3').addClass('current');
      $('.animation_4').addClass('current');
      $('.animation_5').addClass('current');
    };

    if (win_h >= animation_7_h - 300) {
      $('.animation_7').addClass('current');
      $('.animation_8').addClass('current');
    };

    if (win_h >= animation_10_h - 300) {
      $('.animation_10').addClass('current');
    };

    if (win_h >= animation_12_h - 300) {
      $('.animation_12').addClass('current');
      $('.animation_13').addClass('current');
    };

    if (win_h >= animation_18_h - 300) {
      $('.animation_18').addClass('current');
    };

    if (win_h >= $('body').height() - $(window).height()) {
      $('.animation_20').addClass('current');
    };

  })

})

  function up_down() {
    $(".animation_21").animate({bottom:'10px'},600);
    $(".animation_21").animate({bottom:'5px'},600);
  }

  function question() {
    $(".animation_11").animate({width:'14%'},800);
    $(".animation_11").animate({width:'16%'},800);
  }

  function animation14() {
    $(".animation_14").animate({opacity:'0.2'},600);
    $(".animation_14").animate({opacity:'1'},600);
  }
  function animation15() {
    $(".animation_15").animate({opacity:'0'},800);
    $(".animation_15").animate({opacity:'1'},800);
  }
  function animation19() {
    $(".animation_19").animate({right:'-3%'},1000);
    $(".animation_19").animate({right:'0'},1000);
  }