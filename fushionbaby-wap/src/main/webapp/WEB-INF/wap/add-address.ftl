<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-新建地址</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-登录">
    <script type="text/javascript" src="js/public-headTag.js"></script><!-- 公共头部便签，css,js等 -->
  </head>
  <body>
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          新建地址
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <div class="public_form_wrap includ_label">
        <form action="">
          <div class="public_input_wrap">
            <span>收货人：</span>
            <input class="name_input required" type="text" placeholder="" data-toggle="tooltip" title="请输入收货人">
          </div>
          <div class="public_input_wrap">
            <span>手机号码：</span>
            <input class="phone_input required" type="number" placeholder="" data-toggle="tooltip" title="请输入手机号码">
          </div>
          <div class="public_input_wrap">
            <a href="choose-city.html" class="edit_address_disable"></a>
            <span>省市地区：</span>
            <input class="required uneditable-input" type="text" placeholder="" data-toggle="tooltip" title="请选择省市地区">
          </div>
          <div class="public_input_wrap detail_address">
            <span>详细地址：</span>
            <textarea class="required" type="text" placeholder="" data-toggle="tooltip" title="请输入详细地址"></textarea>
          </div>
          <button id="edit_address_btn" class="public_button" type="button">完成</button>
        </form>
      </div>
    </div>
    <script type="text/javascript" src="js/footer.js"></script>
    <script>
      $(function(){
        /* 表单提交，最终验证  */
        $("#edit_address_btn").click(function() {
          $(".required").trigger('blur');
          var numError = $('form .has-error').length;
          if (numError) {
            return false;
          }
          /* alert("成功.");  */
          $(this).addClass('public_loading');

          /* 成功后的操作 开始  */

          /* 成功后的操作 结束  */
        })
      })
    </script>
  </body>
</html>