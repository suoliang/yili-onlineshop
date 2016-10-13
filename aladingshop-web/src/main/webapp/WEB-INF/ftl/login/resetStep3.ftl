<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 找回密码</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body id="resetPassword">

        <div class="oh login-box">
            <div class="container">
                <a class="login-logo" href="http://www.aladingshop.com" title="返回首页">
                    <span></span>
                </a>
            </div>
        </div>

        <div id="resetPassword-wrap" class="container oh">
            <div class="tit">
                <span>找回密码</span>
                <ul>
                    <li>1.输入您的账号<span></span></li>
                    <li>2.账户确认及密码重置<span></span></li>
                    <li class="active">3.重置密码成功</li>
                </ul>
            </div>
            <form id="resetPasswordForm">
                <h2>恭喜您，密码重置成功！</h2>
                <a href="${rc.contextPath}/login/index.htm" class="btn-theme">请 登 录</a>
            </form>
        </div>
        <p class="copyright">版权所有@2015 上海一里网络科技有限公司</p>
    </body>
</html>
