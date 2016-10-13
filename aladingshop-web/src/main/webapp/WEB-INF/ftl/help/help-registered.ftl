<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script type="text/javascript" src="${rc.contextPath}/static/web-js/shoppingCart.js"></script>
        <script type="text/javascript" src="${rc.contextPath}/static/web-js/amountCart.js"></script>
    </head>
    <body class="help">
		<!-- 右侧悬浮菜单 开始-->
        <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->

        <div class="container oh">
            <div class="nav">
                <a href="javascript:void(0)">帮助中心 &gt;</a>
                <a href="javascript:void(0)">如何注册</a>
            </div>
        </div>

        <div class="container oh">
            <div class="mT20 order-wrap fl mB20">
                <div class="order-l fl">
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">新手指南<span>-</span></a>
                        <li class="active"><a href="javascript:void(0)">如何注册</a></li>
                        <li><a href="${rc.contextPath}/help/buy">如何购买</a></li>
                    </ul>
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="${rc.contextPath}/help/buy">支付方式<span>-</span></a>
                        <li><a href="javascript:void(0)">微信支付</a></li>
                        <li><a href="javascript:void(0)">快捷支付</a></li>
                        <li><a href="javascript:void(0)">支付宝支付</a></li>
                    </ul>
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">关于我们<span>-</span></a>
                        <li><a href="javascript:void(0)">公司介绍</a></li>
                    </ul>
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">联系我们<span>-</span></a>
                        <li><a href="javascript:void(0)">客服电话</a></li>
                    </ul>
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">招商加盟<span>-</span></a>
                        <li><a href="javascript:void(0)">加盟流程</a></li>
                    </ul>
                </div>
            </div>
            <div class="help-wrap">
                <div class="help-box">
                    <h3>如何注册</h3>
                    <p>1.注册分为手机注册和邮箱注册两种,如图</p>
                    <p><img src="${rc.contextPath}/static/shop/images/help-1.jpg" height="471" width="543" alt=""></p>
                    <p>2.填写登录名，填写您的手机号码或者邮箱。</p>
                    <p>3.设置您的密码，请输入密码，<span class="red">密码由6-20位字母，数字，符号组成。</span></p>
                    <p>4.确认您的密码，并且发送验证码</p>
                    <p>5.点击立即注册按钮，即可注册成功。</p>
                </div>
            </div>
        </div>
		<!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <#include "/common/other.ftl" />
    </body>
</html>
