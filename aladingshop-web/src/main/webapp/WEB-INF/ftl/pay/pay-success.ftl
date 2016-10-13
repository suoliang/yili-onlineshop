<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>支付成功</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body>

        <!-- 登录框 开始 -->
        <script src="${rc.contextPath}/static/shop/public/loginBox.js"></script>
        <!-- 登录框 结束 -->

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <div class="status-top">
            <div class="container">
                <a href="http://www.aladingshop.com"><span></span></a>
            </div>
        </div>

        <div class="pay-status container">
            <div class="status-l">
                <span class="success"></span>
                <h3 class="green">支付成功，我们会尽快为您发货！</h3>
                <p>订单号：${orderCode!''}</p>
                <p>在线支付：${price!''}元</p>
                <a href="http://www.aladingshop.com">继续逛逛</a>
                <a href="${rc.contextPath}/order/orderInfo?orderCode=${orderCode!''}" class="sta-a">查看订单详情</a>
            </div>
            <div class="status-r">
                <p>关注阿拉丁玛特，精彩不断！</p>
                <img src="${rc.contextPath}/static/shop/images/weichat-code.png" height="165" width="165" alt="">
            </div>
            <div class="status-b">
                <strong>重要提示：</strong>
                <p>阿拉丁不会以<span class="red">订单异常</span>，<span class="red">系统升级</span>等任何方式为由，让您点击链接再付款或者退款，谨防欺骗。</p>
            </div>
        </div>

        <!-- 猜你喜欢 开始-->
      <!--  <div id="daily" class="container mB50">
            <div class="daily-tit">
                <span class="like-span"></span>
                <a href=""></a>
            </div>
            <div id="daily-banner">
                <ul>
                    <li>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-1.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-2.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="picture/daily-3.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                    </li>
                    <li>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                        <div class="daily-box">
                            <a class="daily-a" href="">
                                <div class="verticalAlign">
                                    <img src="images/example.jpg" alt="">
                                </div>
                                <span>&yen; 29.9</span>
                            </a>
                            <a class="daily-word" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                                <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                            </a>
                            <a class="buy" href="">加入购物车</a>
                        </div>
                    </li>
                </ul>
                <a href="javascript:void(0)" class="daily-unslider-arrow prev">Previous slide</a>
                <a href="javascript:void(0)" class="daily-unslider-arrow next">Next slide</a>
            </div>
        </div>-->
        <!-- 猜你喜欢 结束-->

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <!-- javascript -->
        <script src="${rc.contextPath}/static/shop/js/unslider.js"></script><!-- banner JS -->
        <script>
            $(function(){
                /*#daily-banner*/
                var unslider_daily = $('#daily-banner').unslider({
                    autoplay:false,
                    speed: 500,
                    dots: true
                });
                $('#daily-banner .prev').click(function(event) {
                    var data = unslider_daily.data('unslider');
                    data.prev();
                });
                $('#daily-banner .next').click(function(event) {
                    var data = unslider_daily.data('unslider');
                    data.next();
                });

            })
        </script>
    </body>
</html>
