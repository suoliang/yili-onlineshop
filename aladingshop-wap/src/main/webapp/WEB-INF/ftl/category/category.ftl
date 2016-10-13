<!DOCTYPE html>
<html>
    <head>
        <title>分类</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/farmwork/swiper/swiper.min.css"><!-- swiper CSS-->
    </head>
    <body id="Menu">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>分类</p>
            </div>

            <div class="menu-l">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                    	<#if cateVo?exists && cateVo?size&gt;0>
                    		<#list cateVo as ca>
                				<div class="swiper-slide <#if category==ca.code>active</#if>"><span>${ca.name}</span></div>
                    		</#list>
                    	</#if>
                        <div class="swiper-slide"><span></span></div>
                    </div>
                </div>
            </div>
        	<#if cateVo?exists && cateVo?size&gt;0>
        		<#list cateVo as ca>
        			<div class="menu-r fr" <#if category!=ca.code>style="display:none;"</#if>>
        			<#if ca.childCategory?exists && ca.childCategory?size&gt;0>
        				<#list ca.childCategory as secCategory>
        					<#if secCategory.childCategory?exists && secCategory.childCategory?size&gt;0>
        					 <div class="menu-box">
        					 	<h3>${secCategory.name}<span class="right"></span></h3>
        					 	<ul>
        					 		<#list secCategory.childCategory as thirdCategory>
			                        <li>
			                            <a href="${rc.contextPath}/category/cateList.htm?categoryCode=${thirdCategory.code}&v=${EnvironmentConstant.DEPLOY_VERSION}">
			                                <img src="${thirdCategory.logoUrl}">
			                                <p>${thirdCategory.name}</p>
			                            </a>
			                        </li>
			                        </#list>
			                    </ul>
        					 </div>
        					 </#if>
        				</#list>
        			</#if>
        			</div>
        		</#list>
        	</#if>
        </div><!-- /.container -->

        <script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->

        <script src="${rc.contextPath}/static/shop/farmwork/swiper/swiper.jquery.min.js"></script>
        <script>
        $(function(){
            /*左侧菜单*/
            var swiper = new Swiper('.swiper-container',{
                direction : 'vertical',
                slidesPerView: 7,
                freeMode:true
            });

            /*菜单切换*/
            $('.swiper-slide').click(function(event) {
                var thisIndex = $(this).index();
                $(this).addClass('active').siblings().removeClass('active');

                $('.menu-r').eq(thisIndex).show().siblings('.menu-r').hide();
            });
        })
        </script>
    </body>
</html>
