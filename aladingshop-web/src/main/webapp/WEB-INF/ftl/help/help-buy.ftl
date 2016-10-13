<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>帮助中心 - 如何购买</title>
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
                <a href="javascript:void(0)">购买</a>
            </div>
        </div>

        <div class="container oh">
            <div class="mT20 order-wrap fl mB20">
                <div class="order-l fl">
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">新手指南<span>-</span></a>
                        <li><a href="${rc.contextPath}/help/register">如何注册</a></li>
                        <li class="active"><a href="help-buy.html">如何购买</a></li>
                    </ul>
                    <ul class="order-l-list">
                        <a class="prder-l-tit" href="javascript:void(0)">支付方式<span>-</span></a>
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
                <div class="help-box box2">
                    <h3>如何购买</h3>
                    <p>1.购买您可以在购物车中购买，或者在商品详情页去购买，如下图</p>
                    <p><img src="${rc.contextPath}/static/shop/images/help-2.jpg" height="288" width="860"></p>
                    <p>2.填写您的订单信息，确认并提交您的订单。</p>
                    <p>
                        <img src="${rc.contextPath}/static/shop/images/help-3.jpg" height="398" width="344" alt="">
                        <span>3.确认您的订单信息，并且确认付款。<br>4.付款成功。</span>
                    </p>
                </div>
            </div>
        </div>
		<!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <#include "/common/other.ftl" />
    </body>
</html>
