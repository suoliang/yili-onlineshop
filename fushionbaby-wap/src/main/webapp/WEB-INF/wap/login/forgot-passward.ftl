<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-忘记密码</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-注册">
    <script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
    <!-- 公共头部便签，css -->
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
  </head>
  <body id="forgot-passward">
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          忘记密码
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <div class="public_form_wrap">
        <form action="">
          <input type="hidden" id="forgetVerification">
          <div class="public_input_wrap">
          	<span class="showErrorMsg" style="position:absolute;top:-44px;color:#f40;width:100%;text-align:left;margin:0;display:none;"></span>
            <input class="user_input required" type="text" placeholder="请输入手机号或邮箱" data-toggle="tooltip" title="请输入有效的手机号或邮箱">
          </div>
          <div class="validation fl width100">
            <div class="public_input_wrap">
              <input class="validation_input required" type="text" placeholder="请输入验证码" data-toggle="tooltip" title="请输入4位验证码">
            </div>
            <input class="validation_button" type="button" onclick="getForgotCode(this)" value="获取验证码">
          </div>
          <button id="forgot_passward_btn" class="public_button" type="button">下一步</button>
        </form>
      </div>
    </div>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <!--自定义-->
    <script type="text/javascript" src="${rc.contextPath}/wapjs/login/wapForgotPassword.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
  </body>
</html>