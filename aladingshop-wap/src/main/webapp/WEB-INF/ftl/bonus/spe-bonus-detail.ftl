<!DOCTYPE html>
<html>
    <head>
        <title>红包详情</title>
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
                <p>红包详情</p>
            </div>

            <div class="bonus-detail-top fl wp100">
                <h1>红包余额（元）</h1>
                <span>${result.redBalance!'0.0'}</span>
                <p>红包累计获得：${result.gainAmountCount!'0.0'}</p>
                <p>红包累计消费：${result.consumeAmount!'0.0'}</p>
            </div>

            <div class="bonus-detail-b fl wp100">
                <ul class="bonus-detail-menu">
                    <li class="active">红包获得</li>
                    <li>红包消费</li>
                </ul>
                <div class="bonus-box fl wp100">
                    <ul class="box-a">
                    	<#if result.gainRecord?exists && result.gainRecord?size&gt;0>
                    	<#list result.gainRecord as record>
                        <li>
                            <p>用户&nbsp;${record.registerMemberId?substring(0,3)}********&nbsp;注册</p>
                            <span>${record.createTime}</span>
                            <i>+${record.gainAmount}</i>
                        </li>
                        </#list>
                        </#if>
                    </ul>
                    <ul class="box-b" style="display:none">
                    	<#if result.consumeRecord?exists && result.consumeRecord?size&gt;0>
                    	<#list result.consumeRecord as cust>
                         <li>
                            <p>订单号：${cust.orderCode}消费</p>
                            <span>${cust.createTime}</span>
                            <i>-${cust.consumeAmount}</i>
                        </li>
                        </#list>
                        </#if>
                    </ul>
                </div>
            </div>

        </div><!-- /.container -->
        <script>
        $(function(){
            /*菜单切换*/
            var btn = $('.bonus-detail-menu').find('li');
            btn.click(function() {
                $(this).addClass('active').siblings().removeClass('active');
                var thisIndex = $(this).index();
                $('.bonus-box').find('ul').eq(thisIndex).show().siblings().hide();
            });

            /*无信息*/
            /*.box-a或.box-b内li长度为0时，在ul内添加提示信息*/
            var boxA = $('.box-a');
            var boxB = $('.box-b');
            if (!boxA.find('li').length) {
                boxA.append('<p class="noMessage">您还没有获得红包哦！</p>')
            };
            if (!boxB.find('li').length) {
                boxB.append('<p class="noMessage">您还没有消费红包信息！</p>')
            };
        })
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
