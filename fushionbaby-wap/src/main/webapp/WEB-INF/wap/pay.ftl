<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-结算</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-结算">
    <script type="text/javascript" src="js/public-headTag.js"></script><!-- 公共头部便签，css,js等 -->
  </head>
  <body id="pay">
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          结算
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <!-- 个人信息 -->
      <a href="address.html">
        <div class="pay_person_information public_icons">
          <div class="name_phone">
            <p class="fl"><span class="icons-user"></span>揭志勇</p>
            <p class="fr marginR10"><span class="icons-phone"></span>18116407567</p>
          </div>
          <p class="fl item_address">上海市闸北区平型关上海市闸北区平型关路280号金贸大厦2012室</p>
          <i class="icon-angle-right"></i>
        </div>
      </a>
      <!-- 购物车列表  开始-->
      <div class="public_products_wrap">
        <a href="">
          <div class="public_products_item">
            <div class="item_picture">
              <div class="img_c">
                <img src="css/images/example.jpg" alt="">
              </div>
            </div>
            <div class="item_name">
              <p>Classic Pop Mold经典冰棒模具</p>
              <span>型号：经典中号</span>
            </div>
            <div class="item_price">￥ 99.00</div>
          </div>
        </a>
        <a href="">
          <div class="public_products_item">
            <div class="item_picture">
              <div class="img_c">
                <img src="css/images/example.jpg" alt="">
              </div>
            </div>
            <div class="item_name">
              <p>Classic Pop Mold经典冰棒模具</p>
              <span>型号：经典中号</span>
            </div>
            <div class="item_price">￥ 99.00</div>
          </div>
        </a>
      </div>
      <!-- 购物车列表  结束-->
      <!-- 留言备注 -->
      <div class="fl width100 pay_message">
        <textarea class="width100" name="" id="" rows="3" placeholder="您有特殊要求可在此添加备注"></textarea>
      </div>
      <!-- 支付选项 -->
      <div class="public_list">
        <ul>
          <li>
            <a href="" class="public_icons">支付与配送<i class="icon-angle-right"></i></a>
          </li>
          <li>
            <a href="" class="public_icons">索取发票<i class="icon-angle-right"></i></a>
          </li>
          <li>
            <a href="" class="public_icons">充值礼品卡<i class="icon-angle-right"></i></a>
          </li>
          <li>
            <a class="pay_coupon" href="javascript:void(0);" class="public_icons">
              使用余额
              <span class="deduction">抵扣-30元</span>
              <input id="pay_cash_left_val" type="hidden" value="30">
              <button id="pay_cash_left_btn" class="switch" type="button"><span></span></button>
            </a>
          </li>
          <li>
              <a class="pay_coupon" href="javascript:void(0);" class="public_icons">
              <span id="pay_doumi_txt">可使用<i>5000</i>兜米，抵扣<i>50</i>元</span>
              <span class="deduction">抵扣-50元</span>
              <input id="pay_doumi_val" type="hidden" value="50">
              <button id="pay_doumi_btn" class="switch" type="button"><span></span></button>
            </a>
          </li>
          <li class="pay_coupon_btn_li">
            <a class="pay_coupon" href="javascript:void(0);" class="public_icons">
              <input class="pay_coupon_input require" type="text" name="" placeholder="请输入商品抵用码" data-toggle="tooltip" title="请输入商品抵用码">
              <span class="deduction">抵扣-30元</span>
              <input id="pay_coupon_val" type="hidden" value="30">
              <button id="pay_coupon_btn" type="button">使用</button>
            </a>
          </li>
          <li class="count">
            <a href="javascript:void(0);" class="public_icons">商品总计：</a>
            <span>￥: <i>236</i></span>
          </li>
          <li class="freight">
            <a href="javascript:void(0);" class="public_icons">运费：</a>
            <span>￥: <i>236</i></span>
          </li>
          <li class="pay_button">
            <a href="javascript:void(0);" class="public_icons">需要支付：<span>236</span></a>
            <button id="pay_button">去支付</button>
          </li>
        </ul>
      </div>
    </div>
    <script type="text/javascript" src="js/footer.js"></script>
    <script>
      $(function(){
        /*  点击使用优惠券按钮，验证输入非空 体现交互  */
        $("#pay_coupon_btn").click(function() {
          if ($('.pay_coupon_input').val() == "") {
            $('.pay_coupon_input').tooltip('show');
            $(this).parent().removeClass('has-success').addClass('has-error');
          }else {
            $(this).toggleClass("pay_coupon_yes");
            $(this).siblings('.deduction').toggle();/* 切换显示对应的折扣  */
            $('.pay_coupon_input').tooltip('hide');
            $(this).parent().removeClass('has-error').addClass('has-success');
            if ($(this).text() == "使用"){
              $(this).text("不使用");
            }else {
              $(this).text("使用");
            }
          }
        });
        /*  点击使用余额按钮，体现交互  */
        $("#pay_cash_left_btn").click(function() {
          $(this).toggleClass("pay_cash_left_yes");
          $(this).siblings('.deduction').toggle();/* 切换显示对应的折扣  */
        });
        /*  点击使用兜米按钮，体现交互  */
        $("#pay_doumi_btn").click(function() {
          $(this).toggleClass("pay_doumi_yes");
          $(this).siblings('.deduction').toggle();/* 切换显示对应的折扣  */
          var $txt = $('#pay_doumi_txt').html();
          if ($txt == "可使用<i>5000</i>兜米，抵扣<i>50</i>元") {
            $('#pay_doumi_txt').html("已使用<i>5000</i>兜米");
          } else {
            $('#pay_doumi_txt').html("可使用<i>5000</i>兜米，抵扣<i>50</i>元");
          }
        });

      })
    </script>
  </body>
</html>