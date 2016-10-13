<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - ${category.name}</title>
        <!-- styles -->
        <#include "common/common.ftl" />
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/css/idangerous.swiper.css">
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/css/idangerous.swiper.scrollbar.css">
	    <script type="text/javascript" src="${rc.contextPath}/static/web-js/shoppingCart.js"></script>
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

        <!-- banner 开始-->
        <div class="list-top container">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
	                    <#if cateGoodList??>
	                    	<#list cateGoodList as caGod>
		                        <a href="${rc.contextPath}/search/category?code=${caGod.childCategory.code}">
		                            <img src="${caGod.childCategory.logoUrl!''}" height="50" width="50" alt="">
		                            <p>${caGod.childCategory.name!''}</p>
		                        </a>
	                        </#list>
	                    </#if>
                    </div>
                </div>
                <div class="swiper-scrollbar"></div>
            </div>
            <#if category?? && category.bannerUrl??>
	        	<#list category.bannerUrl as ban>
	        		<#if ban_index == 0>
		            	<a class="list-img1 fl" href=""><img src="${ban}" height="320" width="650" alt=""></a>
		            </#if>
		            <#if ban_index == 1>
		            	<a class="list-img2 fl" href=""><img src="${ban}" height="160" width="315" alt=""></a>
		            </#if>
		            <#if ban_index == 2>
		            	<a class="list-img3 fl" href=""><img src="${ban}" height="160" width="315" alt=""></a>
		            </#if>
            	</#list>
	        </#if>
        </div>
        <!-- banner 结束-->

        <p class="list-tit"><span></span></p>

        <div class="list container">
            <div class="daily-tit">
                <span class="sellers-span">Best Sellers</span>
               <!--  <h3>热销商品</h3> -->
              <!--  <a href=""></a>-->
            </div>
            <ul>
              <#if hotSkuList?exists>
               <#list hotSkuList as hotSku>
                <li>
                    <a class="list-img" href="${rc.contextPath}/sku/skuDetail?skuCode=${hotSku.skuCode}" target="_blank" title="${hotSku.name}">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${hotSku.imgPath}" height="300" width="300" alt="">
                    </a>
                    <p>${hotSku.name}</p>
                    <span class="price">&yen; ${hotSku.priceNew}</span>
                    <a class="buy-btn" href="javascript:addToCart('${hotSku.skuCode}',1)">加入购物车<i>+1</i></a>
                </li>
                </#list>
               </#if>
               
            </ul>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl" />
        <!-- 底部 结束 -->

        <!-- 对话框 -->
        <script src="${rc.contextPath}/static/shop/js/idangerous.swiper.min.js"></script>
        <script src="${rc.contextPath}/static/shop/js/idangerous.swiper.scrollbar.js"></script>
        <script>
        $(function(){
            var swiperContainer = $(".swiper-container");
            var swiperScrollbar = $(".swiper-scrollbar");
            mySwiper = new Swiper(swiperContainer[0], {
                scrollContainer:true,
                mousewheelControl : true,
                mode:'vertical',
                //Enable Scrollbar
                scrollbar: {
                  container :swiperScrollbar[0],
                  hide: true,
                  draggable: true
                }
            })
        })
        </script>
        <#include "/common/other.ftl" />
    </body>
</html>
