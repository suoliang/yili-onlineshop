<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-新密码设置</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-新密码设置">
    <script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
    <!-- 公共头部便签，css -->
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js"></script>
  </head>
  <body id="new-passward">
    <div class="container">
      <div class="public_header fl width100">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          新密码设置
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <div class="public_form_wrap">
        <form action="">
          <div class="public_input_wrap">
          	<span class="showErrorMsg" style="position:absolute;top:-44px;color:#f40;width:100%;text-align:left;margin:0;display:none;"></span>
            <input id="passwards_repeat" class="passwards_input passwards_repeat required" type="password" name="passwards_repeat" placeholder="请输入新密码" data-toggle="tooltip" title="请输入新密码">
          </div>
          <div class="public_input_wrap">
            <input id="passwards_repeat_2" class="passwards_repeat_2 required" type="password" name="passwards_repeat_2" placeholder="确认密码" data-toggle="tooltip" title="确认密码">
          </div>
          <button id="new_passward_btn" class="public_button" type="button">确定</button>
        </form>
      </div>
    </div>

    <!-- 对话框 -->
    <div class="public_modal_backup">
      <div class="public_modal">
        <div class="modal_body">修改成功</div>
        <div class="modal_foot">
          <button class="modal_confirm only_confirm">好</button>
        </div>
      </div>
    </div>

    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/iPass.packed.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wapjs/login/wapNewPassward.js"></script>
  </body>
</html>