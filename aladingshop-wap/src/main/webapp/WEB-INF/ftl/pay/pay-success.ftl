<!DOCTYPE html>
<html>
    <head>
        <title>支付成功</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
    </head>
    <body id="">

        <div class="container">

            <div class="head">
                <p>支付成功</p>
            </div>
            <img class="wp100" src="${rc.contextPath}/static/shop/images/pay-success.png">

            <div class="pay-btn">
                <a href="${rc.contextPath}/html/home.html">
                    <button>首页</button>
                </a>
                <a href="${rc.contextPath}/order/orderDetail?orderCode=${orderCode!''}">
                    <button>订单详情</button>
                </a>
            </div>

        </div><!-- /.container -->

    </body>
</html>
