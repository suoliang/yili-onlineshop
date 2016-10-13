<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特商城首页</title>
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/engine1/style.css">
        <!--sina user login   -->
        <meta property="wb:webmaster" content="df13ce99133b12e9" />
		<!--qq user login -->
		<meta property="qc:admins" content="35070641006141416730706375" />

        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <!--首页刷新banner图加载慢 -->
        <script>
            $(function(){
                $('.ws_images').find('img').width($(window).width());
            })

            window.onload = function() {
                var browser = navigator.userAgent;
                if(browser.indexOf('iPhone') > -1){
                    // alert('iphone');
                    window.location.href = "http://m.aladingshop.com";
                }else if(browser.indexOf('Android') > -1 || browser.indexOf('Linux') > -1){
                    // alert('andriod');
                    window.location.href = "http://m.aladingshop.com";
                }else{
                    // window.location.href = "http://www.aladingshop.com";
                };
             };
        </script>
    </head>
    <body class="fixed-header">

    	<div class="ad oh">
            <div class="container">
                <a class="ad-close" href="javascript:void(0)"></a>
            </div>
            <img src="${rc.contextPath}/static/shop/images/ad.jpg" alt="" class="fl">
        </div>

        <!-- 左侧悬浮菜单 开始-->
        <div id="leftMenu" class="fixed-menu">
            <ul>
            	<#if FLOORLIST?exists>
	        		<#list FLOORLIST as caVo>
                		<li><a href="javascript:void(0)">${caVo_index + 1}F<span>
                		<#if caVo.categoryDto.name?length gt 4>${caVo.categoryDto.name?substring(0,4)}<#else>${caVo.categoryDto.name}</#if></span></a></li>
                	</#list>
                </#if>
            </ul>
        </div>
        <!-- 左侧悬浮菜单 结束-->

        <!-- 右侧悬浮菜单 开始-->
        <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->

        <!-- 顶部导航 开始 -->
      	<#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />

        <!-- 菜单 开始 -->
        <div id="menu">
            <div class="container">
                <ul class="menu-ul-f">
                	<li class="active"><a href="">首页</a></li>
               		<#if BANNERCATEGORY?exists>
               			<#list BANNERCATEGORY as list>
               				<li><a href="${rc.contextPath}/category/01.htm?code=${list.categoryDto.code!''}" target="_blank">${list.categoryDto.name!''}</a></li>
	               		</#list>
	        		</#if>
                </ul>
            </div>
        </div>
        <!-- 二级菜单 -->
        <div id="hide-menu">
        	<#if BANNERCATEGORY?exists>
            <#list BANNERCATEGORY as list>
            <div class="hide-menu-box container">
                <div class="menu-box-l">
                    <span></span>
                    <a href="http://www.aldmt.com" target="_blank">阿拉丁玛特</a>
                </div>
                <div class="menu-box-m">
       				<h3>${list.categoryDto.name!''}系列</h3>
       				<#list list.categoryGoodVos as caGoVos>
       					<div class="menu-ul">
	                        <h4><a href="${rc.contextPath}/search/category?code=${caGoVos.childCategory.code}">${caGoVos.childCategory.name}</a></h4>
	                        <ul>
                            <#list caGoVos.thirdCategory as third>
	                            <li><a href="${rc.contextPath}/search/category?code=${third.code}">${third.name}</a></li>
                            </#list>
	                        </ul>
	                    </div>
       				</#list>
                </div>
           
           <!--     <div class="menu-box-r">
                    <span></span>
                    <h3>热销品牌</h3>
                    <ul>
                    	<#list list.brandDtos as brandDto>
	                        <li>
	                            <a href="${rc.contextPath}/search/brand?code=${brandDto.brandCode}"">
	                                <div class="verticalAlign">
	                                    <img src="${brandDto.brandLogoWebUrl}">
	                                </div>
	                                <span>${brandDto.brandName}</span>
	                            </a>
	                        </li>
                        </#list>
                    </ul>
                    
                </div>-->
            </div>
            </#list>
	        </#if>
        </div><!-- /.hide-menu -->
        <!-- 菜单 结束 -->

        <!-- banner 开始-->
        <div id="wowslider-container1">
            <div class="ws_images">
                <ul>
                	<#if BANNER?exists>
	                	<#list BANNER as banner>
	                		<li><a href="${banner.url}"><img src="${banner.picturePath}" alt="" title="" id="wows1_${banner_index?if_exists}"/></a></li>
	                	</#list>
	                </#if>
                </ul>
            </div>
            <div class="ws_bullets">
                <div>
                	<#if BANNER?exists>
	                	<#list BANNER as banner>
	                		 <a href="${banner.url}" title="banner${banner_index?if_exists+1}"><span><img src="${banner.picturePath}" width="192" height="48" alt="banner${banner_index?if_exists+1}"/>${banner_index?if_exists+1}</span></a>
	                	</#list>
	                </#if>
                </div>
            </div>
            <div class="ws_shadow"></div>
        </div>

        <!-- banner 结束-->

        <!-- 每日专场 开始-->
        <div id="daily" class="container">
            <div class="daily-tit">
                <span class="daily-span"></span>
                <a href=""></a>
            </div>
            <div id="daily-banner">
                <ul>
                <#if MRZC?exists>
                	<#assign pagesize= 4 />
                	<#assign page= (MRZC?size -1) / pagesize + 1 />
                	<#list 1..page as row>
                		 <li>
	                		<#list MRZC as goods>
		                		<#if (goods_index >= (row-1)*pagesize) && (goods_index < row*pagesize)>
				                		<div class="daily-box">
				                            <a class="daily-a" href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${goods.skuCode}" target="_blank">
				                                <div class="verticalAlign">
				                                    <img src="${goods.imgPath}" alt="${goods.name}">
				                                </div>
				                                <span>&yen; <#if goods.priceNew?length gt 5>${goods.priceNew?substring(0,goods.priceNew?index_of("."))}<#else>${goods.priceNew}</#if></span>
				                            </a>
				                            <a class="daily-word" href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${goods.skuCode}" target="_blank" title="${goods.name}">
				                                <p>${goods.name}</p>
				                            </a>
		                        		</div>
	                    		</#if>
	                    	</#list>
                    	</li>
                	</#list>
                </#if>
                </ul>
                <a href="javascript:void(0)" class="daily-unslider-arrow prev">Previous slide</a>
                <a href="javascript:void(0)" class="daily-unslider-arrow next">Next slide</a>
            </div>
        </div>
        <!-- 每日专场 结束-->

        <!-- 精品推荐 开始 -->
        <!--
        <div id="recommendation" class="container">
            <div class="daily-tit">
                <span class="recommendation-span"></span>
                <a href=""></a>
            </div>
            <div class="recommendation-box fl">
                <ul class="oh fl recommendation-ul">
                	<#if JPTJ?exists>
	                	<#list JPTJ as category>
	                		<#if category_index == 0>
	                			<li class="active"><a href="javascript:void(0)">${category.childCategory.name}</a></li>
	                			<#else>
	                			<li><a href="javascript:void(0)">${category.childCategory.name}</a></li>
	                		</#if>
	                	</#list>
                	</#if>
                    <span></span>
                </ul>
-->
                <!-- 循环 .recommendation-wrap X6 -->
               <!-- <#if JPTJ?exists>
	                <#list JPTJ as category>
		                <div class="recommendation-wrap fl" <#if category_index!=0>style="display:none;"</#if> >
		            		<div class="recommendation-l fl">
		            			<#list category.childCategory.titleUrl as logImg>
			            			<#if logImg_index == 0>
				                        <a class="recommendation-img fl" href="javascript:void(0)">
				                        	<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${logImg!''}" height="498" width="295" alt="">
				                        </a>
			                        </#if>
		                        </#list>
		                    </div>

		                    <div class="recommendation-r fl">
			                    <ul>
				                    <#list category.skuList as god>
				                    	<li>
				                    		<p>${god.name}</p><br>
			                                <span class="price">&yen; ${god.priceNew}</span>
			                                <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${god.skuCode}" target="_blank">
			                                    <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${god.imgPath}" alt="">
			                                    <div class="over">${category.childCategory.name}</div>
			                                </a>
			                            </li>
		                            </#list>
		                        </ul>
	                		</div>
		                </div>
	                </#list>
                </#if>
            </div>
        </div>
        -->
        <!-- 精品推荐 结束 -->

        <!-- floor遍历 -->
        <#if FLOORLIST?exists>
	        <#list FLOORLIST as caVo>
	        	<div id="F${caVo_index + 1}" class="container floor">
		            <div class="daily-tit">
		                <span class="F${caVo_index + 1}-span"><b>&nbsp;${caVo.categoryDto.name}</b></span>
		                <i></i>
		                <ul>
		                	<#list caVo.categoryGoodVos as caGoodVo>
		                		<#if caGoodVo_index == 0>
		                			<li class="active"><a href="javascript:void(0)">${caGoodVo.childCategory.name}</a></li>
		                			<#else>
		                			<li><a href="javascript:void(0)">${caGoodVo.childCategory.name}</a></li>
		                		</#if>
		                	</#list>
		                </ul>
		            </div>
		            <div class="floor-wrap fl">
		            	<#list caVo.categoryGoodVos as caGoodVo>
	                		<div class="floor-box fl">
				            	<a class="fl floor-banner" href="javascript:void(0)"><img class="bttrlazyloading" data-bttrlazyloading-lg-src="${caVo.categoryDto.logoUrl}" height="440" width="260" alt=""></a>

				                <ul class="floor-ul">
				                	<#if caGoodVo.skuList?exists>
					                	<#list caGoodVo.skuList as sku>
					            			<li>
						                        <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${sku.skuCode}" target="_blank">
						                            <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}" height="160" width="160" alt="">
						                            <p title="${sku.name}">${sku.name}</p>
						                            <span class="price">&yen; ${sku.priceNew}</span>
						                        </a>
					                    	</li>
					            		</#list>
				            		</#if>
				                </ul>
				                <!-- 热销排行榜
				                <div class="product-hot fl">
				                    <h3>热销排行榜</h3>
				                    <ul>
				                        <#if caGoodVo.hotGoods?exists>
						                	<#list caGoodVo.hotGoods as sku>
						                        <li>
						                            <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${sku.skuCode}" target="_blank" title="${sku.name}">
						                                <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}" height="54" width="54" alt="">
						                                <p>&nbsp;${sku.name}</p>
						                                <span class="price">&nbsp;&yen; ${sku.priceNew}</span>
						                            </a>
						                        </li>
					                        </#list>
				            			</#if>
				                    </ul>
				                </div>
				                -->
				                <!--
				                <div class="brand-hot fl">
				                    <h3>热销品牌</h3>
				                    <ul>
				                    	<#if caGoodVo.hotBrands?exists>
						                	<#list caGoodVo.hotBrands as brand>
						                        <li>
						                            <a href="${rc.contextPath}/search/brand?code=${brand.brandCode}">
						                                <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${brand.brandLogoWebUrl}" alt="">
						                                <span class="brand-name">${brand.brandName}</span>
						                            </a>
						                        </li>
					                        </#list>
				            			</#if>
				                    </ul>
				                </div>
				                -->
			                </div>
		                </#list>
		            </div>
	        	</div>
	        </#list>
        </#if>
        <!-- floor遍历 结束 -->

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <!-- 对话框 -->
        <script type="text/javascript" src="${rc.contextPath}/static/shop/engine1/wowslider.js"></script><!-- 首页banner -->
        <script type="text/javascript" src="${rc.contextPath}/static/shop/engine1/script.js"></script><!-- 首页banner -->
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

		<script type="text/javascript" src="${rc.contextPath}/static/shop/js/jquery.bttrlazyloading.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script><!-- 图片延迟加载 -->
		<!--[if IE 8]>
		    <script>
		        $(function(){
		            $('.bttrlazyloading').each(function(){
		            $(this).attr({'src':$(this).attr('data-bttrlazyloading-lg-src')});
		        })
		        })
		    </script>
		<![endif]-->
		<script type="text/javascript">
            $('.recommendation-wrap').eq(0).find(".bttrlazyloading").bttrlazyloading({
                placeholder: 'images/lazyload.gif'
            });
            $('.floor').each(function(index, el) {
                $(this).find('.floor-box').eq(0).find(".bttrlazyloading").bttrlazyloading({
                    placeholder: 'images/lazyload.gif'
                });
            });
            $('.recommendation-wrap').not('.recommendation-wrap:first').find(".bttrlazyloading").bttrlazyloading({
                placeholder: 'images/lazyload.gif',
                triggermanually: true
            });
            $('.floor').each(function(index, el) {
                $(this).find('.floor-box').not('.floor-box:first').find(".bttrlazyloading").bttrlazyloading({
                    placeholder: 'images/lazyload.gif',
                    triggermanually: true
                });
            });
            $('.recommendation-ul').find('li').not('li:first').one('click', function(event) {
                var thisIndex = $(this).index();
                $(this).parents('.recommendation-box').find('.recommendation-wrap').eq(thisIndex).find('.bttrlazyloading').trigger('bttrlazyloading.load');
            });
            $('.floor .daily-tit').each(function(index, el) {
                $(this).find('li').not('li:first').one('mouseover', function(event) {
                    var thisIndex = $(this).index();
                    $(this).addClass('a');
                    $(this).parents('.floor').find('.floor-box').eq(thisIndex).find('.bttrlazyloading').trigger('bttrlazyloading.load');
                });
            });
        </script>
    </body>
</html>
