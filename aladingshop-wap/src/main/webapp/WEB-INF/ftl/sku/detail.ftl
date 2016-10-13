<!DOCTYPE html>
<html>
    <head>
        <title>商品详情</title>

       <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/farmwork/swiper/swiper.min.css"><!-- swiper CSS-->
    </head>
    <body id="detail">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p class="active menu-a"><i>商品介绍</i></p>
                <p class="menu-b"><i>图文详情</i></p>
                <a href="javascript:jumpCart();" class="a-right">
                    <span class="cart">
                        <i>${cartCount}</i>
                    </span>
                </a>
                <a href="javascript:void(0)" class="a-right" onclick="openShareModal()">
                    <span class="share"></span>
                </a>
            </div>

            <div class="detail-box-a fl wp100">
                <div id="detailBanner" class="swiper-container">
                    <div class="swiper-wrapper">
                    	<#if result.skuImages?exists>
	                		<#list result.skuImages as img>
	                			<#if img_index ==0><#assign firstImage=img /></#if>
	                			<#if img_index gt 3><#break></#if>
	                			<div class="swiper-slide">
		                            <img data-src="${img}" alt="${result.name}/${result.selectedSku.color}" class="swiper-lazy">
		                            <div class="swiper-lazy-preloader swiper-lazy-preloader-white"></div>
		                        </div>
	                		</#list>
                		</#if>
                    </div>
                    <div class="swiper-pagination"></div>
                </div><!-- /.swiper-container -->

                <div class="detail-a">
                    <p class="detail-tit">${result.name}</p>
                    <span>&yen; ${result.priceNew}</span>
                    <ul>
                        <li>规格：${result.selectedSku.size}&nbsp;${result.selectedSku.color}</li>
                    </ul>
                </div>
				
                <a href="${rc.contextPath}/sku/skuRank.htm?skuCode=${result.selectedSku.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}">
                    <div class="detail-b">
                        <p class="detail-tit">商品评价</p>
	                        <ul class="rank">
	                            <span>评分：</span>
	                            	<li class="active"></li>
	                            	<li class="active"></li>
	                            	<li class="active"></li>
	                            	<li class="active"></li>
	                            	<li class="active"></li>
	                        </ul>
                        <p>点击查看评论</p>
                        <span class="right"></span>
                    </div>
                </a>
            </div>
            <div class="detail-box-b fl wp100">
            	<#if result.graphicDetails?exists && result.graphicDetails?size&gt;0>
	            	<#list result.graphicDetails as grap>
	            		<img src="${grap.url}" title="">
	            	</#list>
            	</#if>
            </div>
			
			<#if result.linkSkus?exists && result.linkSkus?size&gt;0>
            <div class="detail-c fl wp100 like-wrap">
                <p class="detail-tit">猜你喜欢</p>
                <div id="detailBannerLike" class="swiper-container">
                    <div class="swiper-wrapper">
                    	<#list result.linkSkus as goods>
                    		<a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${goods.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}" class="swiper-slide">
	                            <img src="${goods.imgPath}">
	                            <p>${goods.name}</p>
	                            <span>&yen; ${goods.priceNew}</span>
                        	</a>
                    	</#list>
                    </div>
                </div><!-- /.swiper-container -->
            </div>
            </#if>

            <div class="detail-d wp100 fl">
                <span  class="heart"></span>
                <!--a href="cart.html">立即购买</a-->
                <a href="javascript:void(0)" onclick="openAddCartModal()">加入购物车</a>
            </div>

        </div><!-- /.container -->

        <div class="modal-wrap" id="addCartModal">
            <div class="add-cart-modal">
                <div class="close-wrap" onclick="closeAddCartModal()">
                    <span class="close"></span>
                </div>
                <div class="modal-a fl wp100">
                    <img src="${firstImage}">
                    <p>${result.name}</p>
                    <span>&yen;${result.priceNew}</span>
                </div>
                <div class="modal-b fl wp100">
                    <p class="detail-tit">规格：</p>
                    <#if result.sameCategorySkus?exists>
                		<#list result.sameCategorySkus as sku>
                			<span title="${sku.skuCode}" <#if result.selectedSku.skuCode == sku.skuCode>class="active"</#if> href="">
	                            <#if sku.color !="" || sku.size !="">${sku.color}${sku.size}</#if>
                    		</span>
                		</#list>
                	</#if>
                </div>
                <div class="modal-c fl wp100">
                    <span>数量：<i class="count-num">1</i></span>
                    <button class="Add">+</button>
                    <i class="count-num count-i">1</i>
                    <button class="Reduce">-</button>
                    <input type="hidden" value="1">
                </div>
                <div class="modal-d fl wp100">
                    <button onclick="addCart()">确定</button>
                </div>
            </div>
        </div>

        <div class="modal-wrap" id="shareModal">
            <div class="share-wrap">
                <div class="bdsharebuttonbox">
                    <div class="share-box"><a title="分享到QQ空间" href="#" class="bds_qzone" data-cmd="qzone"></a><p>QQ空间</p></div>
                    <div class="share-box"><a title="分享到新浪微博" href="#" class="bds_tsina" data-cmd="tsina"></a><p>新浪微博</p></div>
                    <div class="share-box"><a title="分享到腾讯微博" href="#" class="bds_tqq" data-cmd="tqq"></a><p>腾讯微博</p></div>
                </div>
                <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":[],"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
            </div>
        </div>

        <script src="${rc.contextPath}/static/shop/farmwork/swiper/swiper.jquery.min.js"></script><!-- swiper JS -->
        <script type="text/javascript">
        $(document).ready(function() {
            /*banner*/
            var swiper = new Swiper('#detailBanner',{
                autoplay:3000,
                speed:600,
                autoplayDisableOnInteraction : false,/*用户操作swiper之后，是否禁止autoplay。默认为true：停止。*/
                loop:true,
                pagination:'.swiper-pagination',
                setWrapperSize :true,
                roundLengths : true,/*设定为true将slide的宽和高取整(四舍五入)以防止某些分辨率的屏幕上文字模糊。*/
                iOSEdgeSwipeDetection : true,/*设置为true开启IOS的UIWebView环境下的边缘探测。如果拖动是从屏幕边缘开始则不触发swiper。*/
                lazyLoading : true,
                lazyLoadingInPrevNext : true,
                lazyLoadingOnTransitionStart : true,
                observer:true,
                observeParents:true,
            });
            
            /*加减商品数量*/
	        $('.modal-c').each(function() {
	            var countInput = $(this).find('input');
	            var countI = $(this).find('.count-num');
	
	            $(this).find('.Add').click(function() {
	                var countHtml = parseInt(countInput.val());
	                countHtml++;
	                countInput.val(countHtml);
	                countI.html(countHtml);
	            });
	            $(this).find('.Reduce').click(function() {
	                var countHtml = parseInt(countInput.val());
	                if (countHtml > 1) {countHtml--}
	                countInput.val(countHtml);
	                countI.html(countHtml);
	            });
	        });
            

            /*猜你喜欢*/
            var swiper = new Swiper('#detailBannerLike',{
                grabCursor: true,
                slidesPerView: 'auto'
            });

            /*商品介绍/图文详情切换*/
            $('.menu-a').click(function(event) {
                $('body').removeClass('menu-b-active');
            });
            $('.menu-b').click(function(event) {
                $('body').addClass('menu-b-active');
            });

             /*选择属性*/
            $('.modal-b').find('span').click(function(event) {
                var thisIndex = $(this).index() - 1;
                $(this).addClass('active').siblings('span').removeClass('active');
                $('.modal-a').find('img').eq(thisIndex).show().siblings('img').hide();
            });
            
            icCollect();
            
	        <!-- 点击收藏或取消收藏 -->
	        $('.heart').click(function() {
	    	 	var type = 0; //0:收藏 	1:取消收藏
	            if($('.heart').hasClass('on')){
	            	type = 1;
	            }
	            
	          	 /*收藏ajax*/
			    $.post("${rc.contextPath}/memberSkuCollect/collect",{skuCodes:'${result.selectedSku.skuCode}',type:type},function(result){
			         if(result.responseCode==201){
		         		   if(confirm("当前用户未登录，是否跳转到登录页面？")){
		         		   		location.href="${rc.contextPath}/login/index";
						   }
						   
			         	return;
			         }else if(result.responseCode==500){
			         	openModal('0','收藏失败');
			         	return;
			         }
			         
			         if(type==0){
			         	$('.heart').addClass('on');
			         	openModal('0','收藏成功');
			         } else {
			            $('.heart').removeClass('on');
			            openModal('0','取消收藏成功');
			         }	
				});
	        });

        });

        function openAddCartModal(){
            var wrap = $('#addCartModal');
            var box = $('.add-cart-modal');
            wrap.fadeIn();
            box.velocity({bottom:'0'});
        }
        function closeAddCartModal(){
        	var wrap = $('#addCartModal');
            var box = $('.add-cart-modal');
            wrap.fadeOut();
            box.velocity({bottom:'-100%'});        
        }
        
        function addCart(){
        	$.ajax({ 
	        	type: "post", 
				url: "${rc.contextPath}/cart/addToCart", 
				async:false,
				data: {skuCode: $(".modal-b .active").attr('title'), quantity:$(".count-i").text(), time: new Date().getTime()}, 
				dataType: "json", 
				success: function(result){ 
					if(result.responseCode!=0){
						openModal('0','添加失败，请重试！');
						return;
					}
					
					closeAddCartModal();
		            var count = parseInt($('.cart').find('i').text()) + parseInt($(".count-i").text());
		            $('.cart').find('i').text(count);	
	        	} 
			});
        }
        
        <!-- 动态加载当前用户收藏商品状态 -->
		function icCollect(){
			$.ajax({ 
	        	type: "post", 
				url: "${rc.contextPath}/memberSkuCollect/isCollect", 
				async:false,
				data: {skuCode:'${result.selectedSku.skuCode}', time: new Date().getTime()}, 
				dataType: "json", 
				success: function(result){ 
					if(result.data =="y"){
			         	$('.heart').addClass('on');
		             } else {
		             	$('.heart').removeClass('on');
		             }	
	        	} 
			});
		}
		
		function jumpCart(){
        	location.href="${rc.contextPath}/cart/list.htm?time="+new Date().getTime();
        }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
