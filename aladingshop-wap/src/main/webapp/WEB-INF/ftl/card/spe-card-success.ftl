<!DOCTYPE html>
<html>
    <head>
        <title>支付成功</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>支付成功</p>
            </div>
            <img class="wp100" src="images/card-success.jpg">

            <div class="pay-btn">
                <a href="index.html">
                    <button>首页</button>
                </a>
                <a href="spe-card-buy.html">
                    <button>再次购买</button>
                </a>
            </div>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
