<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - ${category.name}</title>
        <#include "common/common.ftl" />
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
  	   <!--菜单 开始 -->
        <!-- banner 开始-->
        <div class="banner">
        	<#if category??>
        		<#list category.bannerUrl as ban>
	        		<#if ban_index == 0>
            			<a href=""><img class="wp100" src="${ban}"></a>
            		</#if>
            	</#list>
            </#if>
        </div>
        <!-- banner 结束-->
        <p class="list-tit fl"><span></span></p>
<!--
        <div id="listSlider">
            <div class="container">
                <div class="daily-tit">
                    <span class="classification-span">Classification</span>
                </div>
                <div class="list-slider unslider">
                    <ul>
	                	<#if cateGoodList??>           
	                		<#assign pagesize= 4 />
	            			<#assign page= (cateGoodList?size -1) / pagesize + 1 />
	            			<#list 1..page as row>
			                  <li>
	            				<#list cateGoodList as caGod>
	            					<#if (caGod_index >= (row-1)*pagesize) && (caGod_index < row*pagesize)>
			                        	<div class="list-slider-box">
			                                <h4>${caGod.childCategory.name}</h4>
			                                <#list caGod.thirdCategory as third>
			                                	<a href="${rc.contextPath}/search/category?code=${third.code}">
			                                      <img src="${third.logoUrl}" height="50" width="50" alt="${third.name}">
			                                      <p>${third.name}</p>
			                                 	</a>
		                                	</#list>
			                            </div>
	                				</#if>
	                			</#list>
			                  </li>
	                		</#list>	
	                	</#if>
                    </ul>
                    <a href="javascript:void(0)" class="unslider-arrow prev">Previous slide</a>
                    <a href="javascript:void(0)" class="unslider-arrow next">Next slide</a>
                </div>
            </div>
        </div>
        
        -->

        <div class="list container">
            <div class="daily-tit">
                <span class="sellers-span">Best Sellers</span>
               <!-- <h3>热销商品</h3>-->
               <!-- <a href=""></a>-->
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
               <!-- <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/picture/daily-3.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/picture/daily-3.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/static/shop/picture/daily-3.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-3.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-3.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-4.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="javascript:void(0)" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-4.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-4.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-4.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/daily-4.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>-->
            </ul>
        </div>

       <!-- <div class="page container">
            <ul>
                <li><a href="">首页</a></li>
                <li><a href="">上一页</a></li>
                <li><a>...</a></li>
                <li class="active"><a href="">1</a></li>
                <li><a href="">2</a></li>
                <li><a href="">3</a></li>
                <li><a href="">4</a></li>
                <li><a href="">5</a></li>
                <li><a>...</a></li>
                <li><a href="">下一页</a></li>
                <li><a href="">末页</a></li>
            </ul>
        </div> -->

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <!-- 对话框 -->

        <!-- javascript -->
        <script src="${rc.contextPath}/static/shop/js/unslider.js"></script><!-- banner JS -->
        <script>
            $(function(){
                var unslider = $('.list-slider').unslider({
                    speed: 600,
                    delay: 3000,
                    complete: function() {},
                    keys: true,
                    dots: true,
                    fluid: false,
                    autoplay:false
                });

                $('.unslider-arrow').click(function() {
                    var fn = this.className.split(' ')[1];
                    unslider.data('unslider')[fn]();
                });
            })
        </script>
	<#include "/common/other.ftl" />
       
    </body>
</html>
