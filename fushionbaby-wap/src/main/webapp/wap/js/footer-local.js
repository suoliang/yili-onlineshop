var a = "";
  a+= "<footer id='foot'>";
  a+= "<div class='footwenzi'><ul>";
  a+= "<li class='shouye'><a href='index.html'>首页</a></li>";
  a+= "<li><a href='https://itunes.apple.com/us/app/dou-shi-bao/id953358017?mt=8'>客户端</a></li>";
  a+= "<li><a href='car.html'>购物车</a></li>";
  a+= "<li><a href='person-center.html'>我的</a></li>";
  a+= "</ul></div>";
  a+= "<p>—————&emsp;沪ICP备14049824号&emsp;—————</p>";
  a+= "</footer>";
  a+= "<div class='scrollTop'></div>";
document.write(a);
$(function(){
  /*显示/隐藏返回顶部图标，点击图标返回顶部*/
  var window_h = $(window).height();
  var $scrollTop = $(".scrollTop");
  $(window).scroll(function(){
    var H = $(window).scrollTop();
    if (H > window_h) {
      $scrollTop.fadeIn();
    }
    else {
      $scrollTop.fadeOut();
    }
  });

  $scrollTop.click(function(){
    $("html,body").animate({scrollTop:0});
  })
})