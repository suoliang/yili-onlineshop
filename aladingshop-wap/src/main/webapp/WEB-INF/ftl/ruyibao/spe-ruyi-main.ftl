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

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>如意消费卡</p>
                <a href="${rc.contextPath}/ruyibao/getAllRecord" class="a-right">
                    <span>详情</span>
                </a>
            </div>

            <div class="ruyi-main fl wp100">
                <img src="${rc.contextPath}/static/shop/images/ruyi-main.png">
                <div class="ruyi-main-box">
                    <p>总金额</p>
                    <span>${result.balance}</span>
                </div>
            </div>
            <ul class="fl wp100 ruyi-main-ul">
                <li>昨日收益：${result.yesterdayIncome}元</li>
                <li>累计收益：${result.totalIncome}元</li>
            </ul>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
