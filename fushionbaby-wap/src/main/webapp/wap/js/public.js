$(window).load(function() {

  $('.required').tooltip({
    // 表单工具提示显示方式：手动
    trigger: 'manual'
  })
  $('input , textarea').not('.validation_button').click(function() {
    // 点击输入框滚动到输入框位置
    $('body,html').animate({
      "scrollTop": $(this).offset().top - 30
    });
  })

  // -------  收藏夹:collect.html，购物车:car.html，地址管理：address.html，点击删除按钮删除元素等操作，当数据为空时显示的提示  开始  -------------
  show_car_none();//页面加载后，调整页面高度
  $('.list_view_ul .delete').click(function(){
    $('.current_on').parent().parent().css({'height':'0','border-bottom':'none'});//动画效果
    setTimeout(function(){
      $('.current_on').parent().parent().remove();//延迟移除元素
    },200);
    adjust_body_h(1);//调整页面高度
    if ($('.list_view_item').length==1) {
      show_car_none(0);//显示购物车为空提示
    };
  })

  function show_car_none(i){
    if ($('.list_view_item').length == 0) {
      $(".car-none").fadeIn();
    };
    // ----  解决因延迟删除元素导致元素长度计算的bug  开始
    if (i==0) {
      $('body').css({'min-height':$('.car-none').outerHeight()+$('.public_header').outerHeight()});
      $(".car-none").fadeIn();
      $('.car_btn_wrap').fadeOut();
      $('#address .public_form_wrap').fadeOut();
    };
    // ----  结束
  }
  // console.log($('.list_view_ul li').outerHeight());
  // --------------  结束

  // ------------  页面高度自动调整 开始  ------------
  adjust_body_h();//页面加载后，调整页面高度
  function adjust_body_h(i){
    // 如果 手机屏幕高度 > 页面高度 + 底部高度，页面高度 = 手机屏幕高度 - 底部高度
    var window_h = $(window).height();
    var body_h = $('body').height();
    var foot_h = $("#foot").outerHeight();

    // ----  解决因延迟删除元素导致元素长度计算的bug 开始
    var the_item_h = $('.list_view_item').outerHeight();
    if (i == 1) {
      body_h = body_h - the_item_h;
    };
    // ----  结束

    if (window_h > (body_h+foot_h)) {
      $('body').height(window_h-foot_h);
    };
  }
  // --------------  结束

  // -------------  car.html
  // 加/减 商品数量
  $('.count_num .reduce').click(function(){
    var num = $(this).siblings('input').val();
    if (num > 0 ) {
      num--;
      $(this).siblings('input').val(num);
    };
  })
  $('.count_num .add').click(function(){
      var num = $(this).siblings('input').val();
      num++;
      $(this).siblings('input').val(num);
  })

  // 按钮交互
  $('.hide_radio_click').not(".carCheckAll").click(function(){
    $(this).toggleClass('checked');
  })
  // -------------  结束

  // $("input[type=password]").iPass();//设置输入密码隐藏

  // ------------------  表单验证 开始  ---------------
  $(".required").blur(function() {
    var $parent = $(this).parent();
    var $error = $("<i class='public_form_icon icon-remove'></i>")
    var $success = $("<i class='public_form_icon icon-ok'></i>")

    $parent.find('.public_form_icon').remove();

    if ($(this).is(".required")) {
      // 非空
      if (this.value != "") {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".user_input")) {
      // 验证邮箱-手机号(邮箱或手机号)
      if ($.trim(this.value) != "" && /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,}$/.test($.trim(this.value))) {
        success();
        $('.user_input').tooltip('hide');
      } else if ($.trim(this.value).length == 11 && $.trim(this.value) != "" && /^1\d{10}$/.test($.trim(this.value))) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".passwards_input")) {
      // 验证密码(6位-16位)
      if (this.value.length > 5 && this.value.length < 17) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".phone_input")) {
      // 验证手机号(11位数字)
      if ($.trim(this.value).length == 11 && $.trim(this.value) != "" && /^\d*$/.test($.trim(this.value))) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".passwards_repeat_2")) {
      // 验证：重复密码框(与上个密码框的值相等)
      if (this.value.length > 5 && this.value == $('.passwards_repeat').val()) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".validation_input")) {
      // 验证：手机验证码(4位数字)
      if ($.trim(this.value).length == 4 && /^\d*$/.test($.trim(this.value))) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    if ($(this).is(".name_input")) {
      // 验证：姓名(2位以上)
      if (this.value.length > 1) {
        success();
        $(this).tooltip('hide');
      } else {
        error();
        $(this).tooltip('show');
      }
    };

    function error() {
      $parent.removeClass("has-success").addClass("has-error");
      $parent.append($error);
    }

    function success() {
      $parent.removeClass("has-error").addClass("has-success");
      $parent.append($success);
    }

  }).keyup(function() {
    $(this).triggerHandler("blur");
  }).focus(function() {
    $(this).triggerHandler("blur");
  }); //end blur

  // 表单提交，最终验证，发送ajax请求
  // $(".public_button-eg").click(function() {
  //   $(".required").trigger('blur');
  //   var numError = $('form .has-error').length;
  //   if (numError) {
  //     return false;
  //   }
  //   // alert("留言成功.");
  //   $(this).addClass('public_loading');
  // // 发送到数据库
  // $.post("xxx.php", {
  //     "uesr_name": $user_input.val(),
  //     "passwards": $passwards_input.val(),
  //   },
  //   function(data) {
  //     if (data.status == "ok") {
  //       // 执行提交成功操作
  //        $(this).removeClass('public_loading');
  //     }
  //   }, "json");
  // })
  // ------------------  表单验证 结束  ---------------
})