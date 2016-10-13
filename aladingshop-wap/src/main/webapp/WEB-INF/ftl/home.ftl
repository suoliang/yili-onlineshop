<!DOCTYPE html>
<html>
    <head>
        <title>阿拉丁玛特 触屏版</title>
		<!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/farmwork/swiper/swiper.min.css"><!-- swiper CSS-->
        <script src="${rc.contextPath}/static/shop/farmwork/jquery/jquery.cookie.js"></script>
        <script>
        $(function(){
            if ($.cookie('first_view')=="yes") {
                $('#indexBack').hide();
            } else {
                $.cookie('first_view', 'yes', { expires: 7 });
            }
        });
        </script>
    </head>
    <body id="home">
        <div class="container">

        	<div id="indexBack" class="wp100 modal-wrap">
                <img src="${rc.contextPath}/static/shop/images/index-back.png">
            </div>

        	<div class="download-bar wp100">
                <img class="wp100" src="${rc.contextPath}/static/shop/images/download-bar.png" alt="">
                <a class="close" href="javascript:void(0)">关闭</a>
                <a class="download" href="http://app.aladingshop.com/app-download/index.html">下载</a>
            </div>
            <div class="header">
                <span class="logo"></span>
                <a href="${rc.contextPath}/search/toSearch.htm" class="header-input-wrap">
                    <span class="search"></span>
                    <p>搜索您想要的产品</p>
                </a>
                <a href="${rc.contextPath}/login/index" class="a-right">登录</a>
            </div>

            <!-- banner -->
            <div class="swiper-container">
                <div class="swiper-wrapper">
                	<#list BANNER as ban>
                    <div class="swiper-slide">
                        <img data-src="${ban.picturePath}" alt="" class="swiper-lazy">
                        <div class="swiper-lazy-preloader swiper-lazy-preloader-white"></div>
                    </div>
                    </#list>
                </div>
                <div class="swiper-pagination"></div>
            </div><!-- /.swiper-container -->

            <div class="menuA">
                <a href="javascript:junpOrder();">
                    <span class="menuA-1"></span>
                    <p>订单查询</p>
                </a>
                <a href="${rc.contextPath}/static/nearAlading">
                    <span class="menuA-2"></span>
                    <p>身边阿拉丁</p>
                </a>
                <a href="${rc.contextPath}/skuLabel/skuList?labelCode=CXZQ">
                    <span class="menuA-3"></span>
                    <p>促销专区</p>
                </a>
                <a href="${rc.contextPath}/card/cardShow">
                    <span class="menuA-4"></span>
                    <p>阿拉丁卡</p>
                </a>
                <a href="javascript:jumpCollect();">
                    <span class="menuA-5"></span>
                    <p>我的关注</p>
                </a>
                <a href="javascript:isLogin(2);">
                    <span class="menuA-6"></span>
                    <p>如意消费卡</p>
                </a>
                <a href="javascript:isLogin(3);">
                    <span class="menuA-9"></span>
                    <p>赚红包</p>
                </a>
                <a href="${rc.contextPath}/skuLabel/skuList?labelCode=VIP_SP">
                    <span class="menuA-8"></span>
                    <p>会员专区</p>
                </a>
            </div>

            <div class="menuB">
            	<#list CATEGORYS as cate>
                <a href="${rc.contextPath}/category/cateList.htm?categoryCode=${cate.code}&v=${EnvironmentConstant.DEPLOY_VERSION}">
                    <h4>${cate.name}</h4>
                    <p><#if cate.linkUrl??>${cate.linkUrl}</#if></p>
                    <img src="${cate.logoUrl}">
                </a>
        		</#list>
            </div>

            <div class="banner-wrap">
            	<#list SEMINAR as sem>
                <a href="${rc.contextPath}/${sem.url}">
                    <img src="${sem.picturePath}">
                </a>
                </#list>
            </div>

            <div class="discount">
                <div class="title">
                    <h4>特惠街</h4>
                    <a href="${rc.contextPath}/skuLabel/skuList?labelCode=THJ">更多<span></span></a>
                </div>

                <div class="discount-wrap">
                    <ul id="skuList">
                        <!-- 循环若干li,滚动加载更多 -->
                        <#list THJ as sku>
                        <li>
                            <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${sku.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}">
                                <img src="${sku.imgPath}">
                                <p>${sku.name}</p>
                                <span>&yen; ${sku.priceNew}</span>
                            </a>
                        </li>
         				</#list>
                    </ul>
                    <div class="load-more textC">
                        <p>正在加载</p>
                    </div>
                    <div class="load-none textC">
                        <p>哎哟，没有数据了</p>
                    </div>
                </div>
            </div>

            <div class="foot fl wp100">
                <div class="foot-t fl wp100">
                    <a class="fl" href="${rc.contextPath}/login/index.htm?v=${EnvironmentConstant.DEPLOY_VERSION}" id="loginBtn">登录</a>
                    <a class="fl" href="javascript:toRegister();" id="registerBtn">注册</a>
                    <a class="fr" href="suggest.html">反馈</a>
                </div>
            </div>

            <!-- 公共底部引用-->
        	<#include "/common/foot.ftl" />

        </div><!-- /.container -->

        <div id="menu"></div>
        <div class="modal-wrap">
            <div class="menu-wrap">
                <a href="javascript:void(0)" class="icon-e-wrap">
                    <span class="icon-e"></span>
                    <p>首页</p>
                </a>
                <a href="menu.html" class="icon-b-wrap">
                    <span class="icon-b"></span>
                    <p>菜单</p>
                </a>
                <a href="search.html" class="icon-a-wrap">
                    <span class="icon-a"></span>
                    <p>搜索</p>
                </a>
                <a href="${rc.contextPath}/cart/list" class="icon-c-wrap">
                    <span class="icon-c"></span>
                    <p>购物车</p>
                </a>
                <a href="${rc.contextPath}/memberCenter/toMemberCenter" class="icon-d-wrap">
                    <span class="icon-d"></span>
                    <p>个人中心</p>
                </a>
            </div>
        </div>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
        <script src="${rc.contextPath}/static/shop/farmwork/swiper/swiper.jquery.min.js"></script><!-- swiper JS -->
        <script type="text/javascript">
        $(document).ready(function() {
            /*banner*/
            var swiper = new Swiper('.swiper-container',{
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

            /*底部悬浮菜单*/
            $('.menuA').find('span').velocity('transition.perspectiveLeftIn',{ stagger : 100 });
            $('#menu').click(function(event) {
                if ($('.modal-wrap').is(":visible")==false) {
                    $('.modal-wrap').fadeIn();
                    $('.menu-wrap').find('a').velocity('transition.bounceUpIn',{ stagger : 100, duration:600});
                } else {
                    $('.menu-wrap').find('a').velocity('transition.bounceDownOut',{ stagger : 100, duration:600});
                    $('.modal-wrap').fadeOut();
                }
            });


            $("#logined").html("");

			/****校验登录信息**/
			 $.post("${rc.contextPath}/login/isLogin",{time:new Date().getTime()},function(result){
					if(result.data =="y"){
			         	$('#loginBtn').hide();
			         	$('#registerBtn').hide();
			         	$('.a-right').hide();
		            } else {
		             	$('#loginBtn').show();
			         	$('#registerBtn').show();
			         	$('.a-right').show();
		            }
			 });

			   $('.download-bar').find('.close').click(function(event) {
                $('.download-bar').fadeOut();
            });
        });

         function toRegister(){
            	$.post(_ContextPath+'/login/clickRegister',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/login/toRegister?time="+new Date().getTime();
						} else {
							openModal('0',data.msg);
						}
					}
				);
            }

            function isLogin(index){
            	 url = "${rc.contextPath}/ruyibao/welcome";
        		 if(index ==3){
        		  	url = "${rc.contextPath}/activity/share/shareBonus";
        		 }

            	 /****校验登录信息**/
				 $.post("${rc.contextPath}/login/isLogin",{time:new Date().getTime()},function(result){
						if(result.data !="y"){
				         	 if(confirm("当前未登录，是否立即登录？")){
							 	location.href="${rc.contextPath}/login/index";
							 }
			            }else{
			            	location.href=url;
			            }
				 });
            }
            
            function jumpCollect(){
            	location.href="${rc.contextPath}/collect/collectList?time="+new Date().getTime();
            }
            
            function junpOrder(){
            	location.href="${rc.contextPath}/order/orderList?status=0?time="+new Date().getTime();
            }
        </script>

    </body>
</html>
