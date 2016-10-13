<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-登录</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-登录">
    <script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
    <!-- 公共头部便签，css,js等 -->
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
  </head>
  <body>
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          登录 Fushionbaby
          <a class="public_btn_r" style="cursor:pointer;">注册</a>
        </div>
      </div>
      <div class="public_form_wrap">
        <form action="">
          <div class="public_input_wrap">
            <span class="user"></span>
            <input class="user_input required" name="loginName" type="text" placeholder="请输入手机号或邮箱" data-toggle="tooltip" title="请输入有效的手机号或邮箱">
          </div>
          <div class="public_input_wrap">
            <span class="passwards"></span>
            <input id="passwards_input" name="inputPassword" class="passwards_input required" type="password" placeholder="请输入登录密码" data-toggle="tooltip" title="请输入6-16位登录密码">
          </div>
          <p class="tips fl width100">
          	<span id="showErrorMsg"></span>
            <a id="forgotPassword" style="cursor:pointer;">忘记密码？</a>
          </p>
          <button id="login_btn" class="public_button marginT0" type="button">立即登录</button>
        </form>
      </div>
      <div class="public_line">
        <span></span>
          <p>或使用以下方式登录</p>
        <span></span>
      </div>
      <div class="other_login fl width100">
        <a href="javascript:void(0);">
          <div class="other_login_box">
            <span class="icon_QQ"></span>
            <p>QQ</p>
          </div>
        </a>
        <a href="javascript:void(0);">
          <div class="other_login_box">
            <span class="icon_weichart"></span>
            <p>微信</p>
          </div>
        </a>
        <a href="javascript:void(0);">
          <div class="other_login_box">
            <span class="icon_sina"></span>
            <p>新浪微博</p>
          </div>
        </a>
      </div>
    </div>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/iPass.packed.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <!--自定义-->
    <script type="text/javascript" src="${rc.contextPath}/wapjs/login/wapLogin.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
  </body>
</html>