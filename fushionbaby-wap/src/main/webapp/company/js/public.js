$(function(){

  if($.browser.msie && $.browser.version < 10){
    $('body').addClass('ltie10');
  }

  // 点击新闻列表 弹出新闻框
  // $('.news_item ,.news_list_top').click(function(){
  //   $(this).siblings('.modal').modal('show');
  // })
  // 点击关闭按钮，关闭弹窗
  // $('.news_close').click(function(){
  //   $('.modal').modal('hide');
  // })

  // 翻页按钮
  setInterval("up_down()", 500);

  // ------------------  表单验证 开始  ---------------

  $(".required").blur(function() {
    var $parent = $(this).parent();

    if ($(this).is(".name")) {
      // 验证姓名(2位以上)
      if (this.value.length > 1) {
        success();
      } else {
        error();
      }
    };

    if ($(this).is(".email")) {
      // 验证邮箱
      if (this.value != "" && /.+@.+\.[a-zA-Z]{2,4}$/.test(this.value)) {
        success();
      } else {
        error();
      }
    };

    if ($(this).is(".message")) {
      // 验证留言(4位以上)
      if (this.value.length > 3) {
        success();
      } else {
        error();
      }
    };

    function error() {
      $parent.siblings('p').show();
      $parent.removeClass('has-success').addClass('has-error');
    }

    function success() {
      $parent.siblings('p').hide();
      $parent.removeClass('has-error').addClass('has-success');
    }

  }).keyup(function() {
    $(this).triggerHandler("blur");
  }).focus(function() {
    $(this).triggerHandler("blur");
  }); //end blur

  // 表单提交，最终验证，发送ajax请求
  $("#index_submit").click(function() {
    $(".required").trigger('blur');
    var numError = $('form .has-error').length;
    if (numError) {
      return false;
    }
    // alert("成功.");
    $(this).addClass('public_loading');

    // 成功后的操作 开始
    $('#Modal_index').modal('show');//显示对话框
      setTimeout(function(){
        $('#Modal_index').modal('hide');//隐藏对话框
        $("#index_submit").removeClass('public_loading');
    },2000)
    // 成功后的操作 结束

  })
  // ------------------  表单验证 结束  ---------------

  // about.html 点击地图显示大图
  $(".open_map").click(function(){
    $('.map_wrap_backup').fadeIn();
  })
  $(".map_wrap .news_close").click(function(){
    $('.map_wrap_backup').fadeOut();
  })

  adjust_background ();

  adjust_index_news_item_h();

});

// 监听浏览器窗口大小
window.onresize = function(){
  adjust_background ();
}

  function up_down() {
    $(".scroll_btn").animate({bottom:'15px',opacity:'1'},600);
    $(".scroll_btn").animate({bottom:'5px',opacity:'0.5'},600);
  }

  function adjust_background(){
    // 动态调整背景图大小
    var win_h = $(window).height();
    var win_w = $(window).width();
    console.log("win_w:"+win_w+",","win_h:"+win_h);
    // console.log(win_w/win_h,1903/978);
    if (win_w/win_h > 1903/978 ) {
      $('.section').css({'background-size':'100% auto'});
    } else {
      $('.section').css({'background-size':'auto 100%'});
    };
  }

  function adjust_index_news_item_h() {
    // 新闻图片高度设置
    var news_item_h = $('.news_item:first-child').outerHeight();
    $('.news_item').css({'height':news_item_h});
  }
