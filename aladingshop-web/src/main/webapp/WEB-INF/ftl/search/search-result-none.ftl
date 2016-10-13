<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 搜索结果</title>
        <#include "/common/common.ftl" />
    </head>
    <body class="fixed-header">

        <!-- 右侧悬浮菜单 开始-->
         <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->

        <!-- 菜单 开始 -->
    	<#include "/common/menu.ftl" />
    	
        <!-- 搜索结果空 开始 -->
        <div class="search-none container">
            <span class="search-none-img"></span>
            <p>抱歉！未找到您要搜索的结果哦！换个词试试呗！</p>
            <!--
            <ul>
                <span>您可能还喜欢：</span>
                <li><a href="${rc.contextPath}/search/q?keywords=洗发水">洗发水</a></li>
                <li><a href="${rc.contextPath}/search/q?keywords=护发素">护发素</a></li>
                <li><a href="${rc.contextPath}/search/q?keywords=饮料">饮料</a></li>
                <li><a href="${rc.contextPath}/search/q?keywords=粮油">粮油</a></li>
                <li><a href="${rc.contextPath}/search/q?keywords=日用品">日用品</a></li>
            </ul>
            -->
        </div>
        <!-- 搜索结果空 结束 -->
		 <p class="list-tit"><span></span></p>

        <div class="list container">
            <div class="daily-tit">
                <span class="sellers-span">Best Sellers</span>
               <!-- <a href=""></a>-->
            </div>
            <ul>
            
            <#if hotSkuList??>
              <#list hotSkuList as hotSku>
                <li>
                    <a class="list-img" href="${rc.contextPath}/sku/skuDetail?skuCode=${hotSku.skuCode}" target="_blank" title="${hotSku.name}">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${hotSku.imgPath}" height="300" width="300" alt="">
                    </a>
                    <p>${hotSku.name}</p>
                    <span class="price">&yen; ${hotSku.priceNew}</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
              </#list>
            </#if>
            </ul>
        </div>

        <!-- 猜你喜欢 开始-->
       
        <!-- 猜你喜欢 结束-->

        <!-- 底部 开始 -->
        <div id="footer">
            <div class="footer-top fl">
                <div class="container">
                    <div class="fl footer-top-l">
                        <span></span>
                        <h3>4000-123-123</h3>
                        <p>全国统一客服电话</p>
                    </div>
                    <div class="fr footer-top-r">
                        <span></span>
                        <h3>联系地址：上海市闵行区恒西路189号904室</h3>
                        <p>Contact address: room 189 constant west road, minhang district of Shanghai</p>
                    </div>
                </div>
            </div>
            <div class="footer-bottom fl">
                <div class="container">
                    <ul>
                        <h3 class="f-b-icon1">
                            <span></span>
                        </h3>
                        <li><a href="">新手指南</a></li>
                        <li><a href="">如何注册</a></li>
                        <li><a href="">如何购买</a></li>
                    </ul>
                    <ul>
                        <h3 class="f-b-icon2">
                            <span></span>
                        </h3>
                        <li><a href="">支付方式</a></li>
                        <li><a href="">微信支付</a></li>
                        <li><a href="">网银支付</a></li>
                        <li><a href="">支付宝支付</a></li>
                    </ul>
                    <ul>
                        <h3 class="f-b-icon3">
                            <span></span>
                        </h3>
                        <li><a href="">关于我们</a></li>
                        <li><a href="">公司介绍</a></li>
                    </ul>
                    <ul>
                        <h3 class="f-b-icon4">
                            <span></span>
                        </h3>
                        <li><a href="">联系我们</a></li>
                        <li><a href="">客服电话</a></li>
                    </ul>
                    <ul>
                        <h3 class="f-b-icon5">
                            <span></span>
                        </h3>
                        <li><a href="">招商加盟</a></li>
                        <li><a href="">加盟流程</a></li>
                    </ul>
                    <div class="f-b-bottom fl">
                        <span></span>
                        <p>版权所有©2014上海一里网络科技有限公司  沪ICP备14048893号</p>
                    </div>
                </div>
            </div>
        </div>
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
        
        <#include "/common/other.ftl" />
    </body>
</html>
