<!DOCTYPE html>
<html>
    <head>
        <title>阿拉丁卡</title>
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
                <p>我的阿拉丁卡</p>
            </div>

            <div class="card-detail-wrap fl wp100">
                <p class="noCard">抱歉，您还没有购卡记录！</p>
                <ul>
                	<#if cardList?exists && cardList?size&gt;0>
                	<#list cardList as card>
                    <li>
                        <div class="card-detail-t fl wp100">
                            <h5>面值：${card.totalCorpus}元</h5>
                            <span>购卡时间：${card.showTime}</span>
                            <i>类型：${card.cardName}</i>
                        </div>
                        <div class="card-detail-m fl wp100">
                            <p class="fl">卡号：${card.cardNo}</p>
                            <p class="fr">累计赠券：${card.totalRebate}元</p>
                        </div>
                    </li>
                    </#list>
                    </#if>
                </ul>
                <div class="btn-wrap">
                    <a href="${rc.contextPath}/card/cardShow"><button>在线购卡</button></a>
                </div>
            </div>

        </div><!-- /.container -->

        <script>
            $(function(){
                if (!$('.card-detail-wrap').find('li').length) {
                    $('.noCard').show();
                };
            })
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
