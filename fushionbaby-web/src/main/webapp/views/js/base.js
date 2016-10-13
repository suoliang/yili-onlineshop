var pageLineLimit = 3;
$(window).load(function() {

  $(".section_gar .wrap").hover(function() {
    $(".at_arro_lf,.at_arro_ri").stop().fadeTo(1000, 0.5);
  }, function() {
    $(".at_arro_lf,.at_arro_ri").stop().fadeOut();
  });

  // ---- 首页外其他页面：全部商品分类展开/收缩
  $(".nav-category").hover(function() {
    $(this).children('#nav-drap-other').stop().slideToggle();
  });
  // ---- end

  // ---- 右侧导航 提示弹出
  $(".sidebar-column,.gotop").hover(function() {
    $(this).find('.sc-tooltip').show();
    $(this).find('.sc-tooltip').stop().animate({
      left: "-57px"
    }, 300);
    $(this).find('.sc-letter').show();
  }, function() {
    $(this).find('.sc-tooltip').css({left: "-124px"});
    $(this).find('.sc-tooltip').hide();
    $(this).find('.sc-letter').hide();
  });
  $(".sidebar-column:last-child").css('border', '0 none');
  // ---- end

  // ---- 会员增值等 菜单下拉
  $(".nav-main-list li").hover(function() {
    $(this).children('.nav-drop-down').stop().slideToggle();
  });
  // ---- end

  // ---- 返回顶部
  $(".gotop").click(function(event) {
    $("html,body").stop().animate({
      scrollTop: 0
    }, 1000);
  });
  // ---- end

  // ---- 顶部个人中心下拉菜单
  $(".uesrname-wrapper").hover(function() {
    $('.user-drop').stop().slideToggle();
  });
  // ---- end

  $(".tip").hover(function() {
    $(this).children('.tip-arrow').show();
  }, function() {
    $(this).children('.tip-arrow').hide();
  });

  // ---- 注册页 切换手机/邮箱注册
  $(".enroll-now").click(function() {
    $(this).addClass('current').siblings().removeClass('current');
    $(".list-item ul").eq($(this).index()).show().siblings().hide();
  });
  // ---- end

  // ---- 列表页 价格排序图标显示
  $(".tip").hover(function() {
    $(this).children('.tip-arrow').show();
  }, function() {
    $(this).children('.tip-arrow').hide();
  });
  // ---- end

  //back-phone
  $(".retrieve").click(function(event) {
    $(this).siblings('.drop').toggle();
  });

  //confirm-password
  $(".list-item-btn").click(function(event) {
    $(this).siblings('.popup').show();
  });

  //购物车提示登录框
  $(".close").click(function(event) {
    $("#not_login").hide();
  });

  // ---- 首页banner下方轮播
  // .endtime 的value值是经过转换的unix时间
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
        obj.parent().siblings(".hide-a").html("抢完啦");
        var str = "抢完啦";
      }
      obj.html(str);
    });
  }, 100);
  // ---- end

  // ---- 商品列表页 分页按钮显示...
  var page_count = $('#page_count b').text();
  var page_more = '<a class="page-list page_more" href="javascript:void(0);">...</a>';
  var url = window.location.href;
  var cur_page = GetQueryString("cur_page");
  if (!cur_page) {
    cur_page = 1;
    url = url + "&cur_page=1";
  }
  var page_line_limit = parseInt(pageLineLimit);
  re = new RegExp('cur_page=' + cur_page, "g");
  var page_first_url = url.replace(re, "cur_page=1");
  var page_last_url = url.replace(re, 'cur_page=' + page_count);
  var page_first = '<a class="page-list page_first" href="' + page_first_url + '">1</a>';
  var page_last = '<a class="page-list page_last" href="' + page_last_url + '">' + page_count + '</a>';

  if (page_count == (page_line_limit + 1)) {
    $('.page_next').before(page_last);
    if(cur_page == (page_line_limit + 1)){
    	$('.page_prev').after(page_first);
    	$('.page_first').after(page_more);
    	$('.page_last').hide();
    }
  } else if (page_count == (page_line_limit + 2)) {
    if (cur_page == (page_line_limit + 1)) {
      $('.page_prev').after(page_first);
      $('.page_first').after(page_more);
      $('.page_next').before(page_last);
    };
  } else if (page_count >= (page_line_limit * 2)) {
    if (cur_page > page_line_limit) {
      $('.page_prev').after(page_first);
      $('.page_first').after(page_more);
    };

    if ((page_count - cur_page) > (page_line_limit-1)) {
      $('.page_next').before(page_last);
      $('.page_last').before(page_more);
    };
  };

  function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  }
  // ---- end

  // ---- 左侧导航 定位
  if ($("body").height() < $(window).height()+$(".footer").height()) {
    var nav_max_h = $(window).height() - ($('body').height() - $('.footer').outerHeight()) + 20;
    $('#left-nav').css({
      'bottom': nav_max_h
    });
  };

  var footer_T = $('.footer').offset().top;
  var footer_H = $('.footer').outerHeight();
  var footer_max_h = $('body').height()-$(window).height()-$('.footer').height();
  $(window).scroll(function(){
    $(window).scrollTop()>400?$(".left-nav-list,.left-nav-middle").fadeIn():$(".left-nav-list,.left-nav-middle").fadeOut();
    if ($(window).scrollTop() >= footer_max_h) {
      $('#left-nav').css({'bottom':$(window).scrollTop()-footer_max_h+20});
    }else {
      $('#left-nav').css({'bottom':'20px'});
    }
  })
  // ---- end
});