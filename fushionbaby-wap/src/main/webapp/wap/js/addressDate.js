$(function() {

  // 1.导入省市(?)
  // 2.点击选择地址，打开省份选择
  var win_h = $(window).height();
  $("#choose_adress").click(function() {
    var city_h = $("#choose_city").outerHeight();
    $("body").height(win_h > city_h ? win_h : city_h);
    $("#choose_city").show().animate({
      "left": "0"
    });
    $("#page_address,#foot").fadeOut();
  });

  // 3.选择省份
  $("#choose_city_ul li").click(function() {
    // 4.根据选择的省份，导入对应的 市(?)
    // 5.打开市选择
    $("html,body").animate({
      scrollTop: 0
    }, 0);
    $("#choose_province").show().animate({
      "left": "0"
    });
    $("#choose_city").animate({
      "left": "-100%"
    });
    setTimeout(function() {
      $("#choose_city").hide();
    }, 600);
    var province_h = $("#choose_province").height();
    $("body").height(win_h > province_h ? win_h : province_h);
  })

  // 5.选择市
  $("#choose_province_ul li").click(function() {
    // 6.根据选择的市，导入对应的地区(?)
    // 7.判断是否有下级地区（3级地址），如果为0，选择地址完毕
    if ($("#choose_region_ul li").length == 0) {
      $("#choose_province").animate({
        "left": "100%"
      });
      $("#page_address,#foot").fadeIn();
      $("body").css("height", "auto");
      $("html,body").animate({
        scrollTop: 0
      }, 0);
      setTimeout(function() {
        $("#choose_province").hide();
        $("#choose_city,#choose_province").css("left", "100%");
      }, 600);
    } else {
      // 8.如果有下级地址，打开地区选择
      $("html,body").animate({
        scrollTop: 0
      }, 0);
      $("#choose_region").show().animate({
        "left": "0"
      });
      $("#choose_province").animate({
        "left": "-100%"
      });
      setTimeout(function() {
        $("#choose_province").hide();
      }, 600);
      var region_h = $("#choose_region").height();
      $("body").height(win_h > region_h ? win_h : region_h);
    }
  });

  // 9.选定地区
  $("#choose_region_ul li").bind("click", function() {
    var $choose_region = $("#choose_region");
    $choose_region.animate({
      "left": "100%"
    });
    $("#choose_city,#choose_province").css("left", "100%");
    $("#page_address,#foot").fadeIn();
    $("body").css("height", "auto");
    $("html,body").animate({
      scrollTop: 0
    }, 0);
    setTimeout(function() {
      $choose_region.hide();
    }, 600);
  })

  // 选择省市 返回菜单
  $("#choose_city .close_menu").click(function() {
    var self = $("#choose_city");
    self.animate({
      "left": "100%"
    });
    setTimeout(function() {
      self.hide();
    }, 600);
    $("#page_address,#foot").fadeIn();
  });
  // 选择省份 返回菜单
  $("#choose_province .close_menu").click(function() {
    var self = $("#choose_province");
    self.animate({
      "left": "100%"
    });
    $("#choose_city").show().animate({
      "left": "0"
    });
    setTimeout(function() {
      self.hide();
    }, 600);
  });
  // 选择地区 返回菜单
  $("#choose_region .close_menu").click(function() {
    var self = $("#choose_region");
    self.animate({
      "left": "100%"
    });
    $("#choose_province").show().animate({
      "left": "0"
    });
    setTimeout(function() {
      self.hide();
    }, 600);
  });

})