<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-地址管理</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-地址管理">
    <script type="text/javascript" src="js/public-headTag.js"></script><!-- 公共头部便签，css,js等 -->
    <script src="http://cdn.bootcss.com/zepto/1.1.4/zepto.min.js"></script>
    <script src="js/zepto/event.js"></script>
    <script src="js/zepto/touch.js"></script>
  </head>
  <body id="address">
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          地址管理
          <a class="public_btn_r edit" href="add-address.html"></a>
        </div>
      </div>
      <!-- 地址管理 -->
      <ul class="list_view_ul OH padding0 margin0">
        <li>
          <a href="javascript:void(0);">
            <div class="pay_person_information public_icons list_view_item">
              <div class="hide_radio_click"></div>
              <div class="name_phone">
                <p class="fl"><span class="icons-user"></span>揭志勇</p>
                <p class="fr marginR10"><span class="icons-phone"></span>18116407567</p>
              </div>
              <p class="fl item_address">上海市闸北区平型关上海市闸北区平型关路280号金贸大厦2012室</p>
            </div>
          </a>
          <a href="edit-address.html"><span class="edit list_view_span">编辑</span></a>
          <span class="delete list_view_span">删除</span>
        </li>
        <li>
          <a href="javascript:void(0);">
            <div class="pay_person_information public_icons list_view_item">
              <div class="hide_radio_click"></div>
              <div class="name_phone">
                <p class="fl"><span class="icons-user"></span>揭志勇</p>
                <p class="fr marginR10"><span class="icons-phone"></span>18116407567</p>
              </div>
              <p class="fl item_address">上海市闸北区平型关上海市闸北区平型关路280号金贸大厦2012室</p>
            </div>
          </a>
          <a href="edit-address.html"><span class="edit list_view_span">编辑</span></a>
          <span class="delete list_view_span">删除</span>
        </li>
      </ul>
      <div class="public_form_wrap">
        <form action="">
          <a href="add-address.html">
            <button id="" class="public_button" type="button">新建地址</button>
          </a>
        </form>
      </div>
    </div>

    <!-- 地址列表为空提示 -->
    <section class="car-none">
      <img src="css/images/collect.png">
      <p>您的地址栏 空！空！空！</p>
      <p>赶快去新建吧！</p>
      <a href="add-address.html">新建地址</a>
    </section>

    <script src="js/zepto/listview.js"></script>
    <script type="text/javascript" src="js/footer.js"></script>
  </body>
</html>