<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - ${category.name}</title>
		<!-- styles -->
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

        <!-- banner 开始-->
        <div class="banner">
        	<#if category?? && category.bannerUrl>
        		<#list category.bannerUrl as ban>
	        		<#if ban_index == 0>
           				<a href=""><img class="wp100" src="${ban}"></a>
           			</#if>
            	</#list>
            </#if>
        </div>
        <!-- banner 结束-->

        <div class="list-tit-2 container">
            <ul>
                <li class="clock">
                    <div><span></span></div>
                    <p>省时</p>
                </li>
                <li class="dollar">
                    <div><span></span></div>
                    <p>省钱</p>
                </li>
                <li class="heart">
                    <div><span></span></div>
                    <p>省心</p>
                </li>
                <li class="place">
                    <div><span></span></div>
                    <p>便捷</p>
                </li>
            </ul>
        </div>
       	
	<#if cateGoodList??>
		<#list cateGoodList as cateGoood>
			<div class="list container">		
	            <div class="daily-tit mB10">
	                <span class="${cateGoood.childCategory.englishName}-span">${cateGoood.childCategory.englishName}</span>
	                <h3>${cateGoood.childCategory.name}</h3>
	                <a href=""></a>
	            </div>
	         <#if cateGoood.skuList??> 
	           
	           <div class="list-food">
               <#list cateGoood.skuList as sku>
	            <#if (sku_index < 2)>  
                   <div class="list-div <#if sku_index==0 >fl<#elseif sku_index==1>fr</#if>">
                    <a class="list-div-img" href="${rc.contextPath}/sku/skuDetail?skuCode=${sku.skuCode}" target="_blank">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}" height="254" width="254" alt="">
                    </a>
                    <div class="list-r fl">
                        <h4>${sku.name}</h4>
                      	 <span></span>
                        <p>${sku.description}</p>
                        <h6 class="price">&yen; ${sku.priceNew}</h6>
                       <!-- <i class="history-price">&yen; ${sku.priceOld}</i>-->
                        <a class="buy-btn" href="javascript:addToCart('${sku.skuCode}',1)">加入购物车</a>
                    </div>
                   </div>
                  </#if>
                 </#list>
				</div>
				
				<ul>
				  <#list cateGoood.skuList as sku>
	                <#if (sku_index >= 2)>  
	                <li>
	                    <a class="list-img" href="${rc.contextPath}/sku/skuDetail?skuCode=${sku.skuCode}" target="_blank" title="${sku.name}">
	                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}" height="300" width="300" alt="">
	                    </a>
	                    <p>${sku.name}</p>
	                    <span class="price">&yen; ${sku.priceNew}</span>
	                    <a class="buy-btn" href="javascript:addToCart('${sku.skuCode}',1)">加入购物车<i>+1</i></a>
	                </li>
	                
	                </#if>
	              </#list>
	            </ul>
	           </div>
	       </#if>  
		</#list>
	</#if>
       <!-- <div class="list container">
            <div class="daily-tit mB10">
                <span class="breakfast-span">breakfast food</span>
                <h3>早餐食品</h3>
                <a href=""></a>
            </div>
            <div class="list-food">
                <div class="list-div fl">
                    <a class="list-div-img" href="">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/list-1.jpg" height="254" width="254" alt="">
                    </a>
                    <div class="list-r fl">
                        <h4>友臣肉松饼</h4>
                        <span>香发护法清新自然美丽</span>
                        <p>传承“世界第一长发村”的养发古方，融合汉高本草养发精髓，精选上乘中草</p>
                        <h6 class="price">&yen; 49.90</h6>
                        <i class="history-price">&yen; 59.90</i>
                        <a class="buy-btn" href="">立即购买</a>
                    </div>
                </div>
                <div class="list-div fr">
                    <a class="list-div-img" href="">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/list-2.jpg" height="254" width="254" alt="">
                    </a>
                    <div class="list-r fl">
                        <h4>友臣肉松饼</h4>
                        <span>香发护法清新自然美丽</span>
                        <p>传承“世界第一长发村”的养发古方，融合汉高本草养发精髓，精选上乘中草</p>
                        <h6 class="price">&yen; 49.90</h6>
                        <i class="history-price">&yen; 59.90</i>
                        <a class="buy-btn" href="">立即购买</a>
                    </div>
                </div>
            </div>
            <ul>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
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
            </ul>
        </div>

        <div class="list container">
            <div class="daily-tit mB10">
                <span class="pastry-span">pastry</span>
                <h3>饼干点心</h3>
                <a href=""></a>
            </div>
            <div class="list-food">
                <div class="list-div fl">
                    <a class="list-div-img" href="">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/list-1.jpg" height="254" width="254" alt="">
                    </a>
                    <div class="list-r fl">
                        <h4>友臣肉松饼</h4>
                        <span>香发护法清新自然美丽</span>
                        <p>传承“世界第一长发村”的养发古方，融合汉高本草养发精髓，精选上乘中草</p>
                        <h6 class="price">&yen; 49.90</h6>
                        <i class="history-price">&yen; 59.90</i>
                        <a class="buy-btn" href="">立即购买</a>
                    </div>
                </div>
                <div class="list-div fr">
                    <a class="list-div-img" href="">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="picture/list-2.jpg" height="254" width="254" alt="">
                    </a>
                    <div class="list-r fl">
                        <h4>友臣肉松饼</h4>
                        <span>香发护法清新自然美丽</span>
                        <p>传承“世界第一长发村”的养发古方，融合汉高本草养发精髓，精选上乘中草</p>
                        <h6 class="price">&yen; 49.90</h6>
                        <i class="history-price">&yen; 59.90</i>
                        <a class="buy-btn" href="">立即购买</a>
                    </div>
                </div>
            </div>
            <ul>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
                    </a>
                    <p>宝洁海飞丝去屑清爽去油洗发露750ml</p>
                    <span class="price">&yen; 39.9</span>
                    <a class="buy-btn" href="javascript:void(0)">加入购物车<i>+1</i></a>
                </li>
                <li>
                    <a class="list-img" href="" title="宝洁海飞丝去屑清爽去油洗发露750ml">
                        <img class="bttrlazyloading" data-bttrlazyloading-lg-src="images/example.jpg" height="300" width="300" alt="">
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
            </ul>
        </div>-->

        <!-- 底部 开始 -->
       <#include "/common/footer.ftl" />
        <!-- 底部 结束 -->

        <!-- 对话框 -->
       
  		<#include "/common/other.ftl" />
      
    </body>
</html>
