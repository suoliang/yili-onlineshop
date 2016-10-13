<!DOCTYPE html>
<html>
    <head>
        <title>商品详情</title>

        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->

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
            </div>

            <div class="detail-box-a fl wp100">
                <div id="detailBanner" class="swiper-container">
                    <div class="swiper-wrapper">
                    	<#if memberConfigList?exists && memberConfigList?size&gt;0>
                    	<#list memberConfigList as config>
                        <div class="swiper-slide">
                            <img src="${config.imgUrl}">
                        </div>
                        </#list>
                    	</#if>
                    </div>
                    <div class="swiper-pagination"></div>
                </div><!-- /.swiper-container -->

                <div class="card-detail-a fl wp100">
                    <p>阿拉丁益多宝在线购卡</p>
                    <#if memberConfigList?exists && memberConfigList?size&gt;0>
                    <#list memberConfigList as config>
                    <a <#if config_index==0>class="active"</#if> href="javascript:void(0)">
                        <div class="img" style="background:url(${config.imgUrl});"></div>
                        <span>${config.typeName}</span>
                        <i></i>
                    </a>
                    </#list>
                    </#if>
                </div>
                <div class="card-detail-b fl wp100">
                    <p>面值（元）</p>
                    <div class="modal-c fl wp100">
                        <button class="Reduce">-</button>
                        <i class="count-num count-i">1000</i>
                        <button class="Add">+</button>
                        <input type="hidden" value="1000">
                    </div>
                </div>
            </div>
        	
        	<div class="detail-box-b fl wp100">
                <img src="${rc.contextPath}/static/shop/images/card-detail.jpg">
            </div>

            <div class="detail-d wp100 fl card-detail-c">
                <span>数量：1件</span>
                <a class="disabled-btn" href="javascript:void(0)">立即购买</a>
            </div>

        </div><!-- /.container -->
		
		<div class="modal-wrap" id="iconDisabled">
            <div class="modal">
                <p>购买阿拉丁卡需要在APP端操作，前去下载APP?</p>
                <div class="btn-wrap">
                    <a class="cancel" href="javascript:void(0)">取消</a>
                    <a class="confirm" href="http://app.aladingshop.com/app-download/index.html">确定</a>
                </div>
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
                iOSEdgeSwipeDetection : true/*设置为true开启IOS的UIWebView环境下的边缘探测。如果拖动是从屏幕边缘开始则不触发swiper。*/
            });

            /*商品介绍/图文详情切换*/
            $('.menu-a').click(function(event) {
                $('body').removeClass('menu-b-active');
            });
            $('.menu-b').click(function(event) {
                $('body').addClass('menu-b-active');
            });

            $('.card-detail-a').find('a').click(function(event) {
                $(this).addClass('active').siblings().removeClass('active');
            });

        });
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
