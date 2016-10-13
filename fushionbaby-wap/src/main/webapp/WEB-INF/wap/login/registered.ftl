<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-注册</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-注册">
    <script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
    <!-- 公共头部便签，css -->
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js"></script>
  </head>
  <body>
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          注册 Fushionbaby
          <a class="public_btn_r" href="${rc.contextPath}/login/index.do">登录</a>
        </div>
      </div>
      <div class="public_form_wrap">
        <form action="">
          <input type="hidden" id="registerVerification">
          <div class="public_input_wrap">
          	<span class="phone_registered" style="position:absolute;top:-44px;color:#f40;width:100%;text-align:left;margin:0;display:none;"></span>
            <span class="phone"></span>
            <input class="phone_input required" type="number" placeholder="请输入您的手机号" data-toggle="tooltip" title="请输入您的手机号">
          </div>
          <div class="public_input_wrap">
            <span class="passwards-1"></span>
            <input id="passwards_repeat" class="passwards_input passwards_repeat required" type="password" name="passwards_repeat" placeholder="请设置您的密码" data-toggle="tooltip" title="请设置6-16位登录密码">
          </div>
          <div class="public_input_wrap">
            <span class="passwards-2"></span>
            <input id="passwards_repeat_2" class="passwards_repeat_2 required" type="password" name="passwards_repeat_2" placeholder="请再次输入您的密码" data-toggle="tooltip" title="请再次输入您的密码">
          </div>
          <div class="validation fl width100">
            <div class="public_input_wrap">
              <input class="validation_input required textC" type="text" placeholder="请输入验证码" data-toggle="tooltip" title="请输入4位验证码">
            </div>
            <input class="validation_button" type="button" onclick="dotime(this)" value="获取验证码">
          </div>
          <button id="registered_btn" class="public_button" type="button">立即注册</button>
        </form>
      </div>
    </div>

    <!-- 对话框 -->
    <div class="public_modal_backup">
      <div class="public_modal">
        <div class="modal_body">注册成功</div>
        <div class="modal_foot">
          <button class="modal_confirm only_confirm">好</button>
        </div>
      </div>
    </div>

    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/iPass.packed.js"></script>
    <!--自定义-->
    <script type="text/javascript" src="${rc.contextPath}/wapjs/login/wapRegister.js"></script>
  </body>
</html>