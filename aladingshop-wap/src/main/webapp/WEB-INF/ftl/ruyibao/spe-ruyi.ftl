<!DOCTYPE html>
<html>
    <head>
        <title>如意消费卡</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>如意消费卡</p>
            </div>

            <div class="ruyi-a fl wp100">
                <img src="${rc.contextPath}/static/shop/images/ruyi-a.png">
                <p>好礼送不停，每天按年化的8%得到返券</p>
                <div class="btn-wrap ruyi-btn">
                    <a href="${rc.contextPath}/ruyibao/index">
                        <button>立即体验</button>
                    </a>
                </div>
            </div>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
