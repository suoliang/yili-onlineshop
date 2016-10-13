<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="renderer" content="webkit">
	<title>礼品卡 - Fushionbaby</title>
	<script type="text/javascript" >
		 var _ContextPath = "${rc.contextPath}";
	</script>
	<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico"><!-- 浏览器标签图标 -->
	<link rel="stylesheet" href="${rc.contextPath}/views/css/style.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="${rc.contextPath}/views/css/toy-product-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<style>
		.cards_wrap,.cards_wrap * {-webkit-box-sizing:border-box;-ms-box-sizing:border-box;-moz-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
		.cards_wrap ul {width: 100%;float: left;overflow: hidden;}
		.cards_wrap ul li {width: 24%;float: left;margin-right: 1.333%;margin-bottom: 20px;margin-top: 20px;}
		.cards_wrap ul li:hover p {color: #35a6cd;}
		.cards_wrap ul li:hover span {color: #35a6cd;}
		.cards_wrap ul li:hover img {-webkit-transform:scale(1.05);-ms-transform:scale(1.05);-moz-transform:scale(1.05);-o-transform:scale(1.05);transform:scale(1.05);}
		.cards_wrap ul li:hover a {border-color:#35a6cd;}
		.cards_wrap ul li a {width: 100%;float: left;padding: 5px 20px; border: 1px dashed #ccc;-webkit-transition:all 0.25s ease-out 0s;-moz-transition:all 0.25s ease-out 0s;-o-transition:all 0.25s ease-out 0s;-ms-transition:all 0.25s ease-out 0s;transition:all 0.25s ease-out 0s;}
		.cards_wrap ul li a p {width: 100%;float: left;font-size: 16px;color: #333;text-align: center;line-height: 28px;-webkit-transition:all 0.25s ease-out 0s;-moz-transition:all 0.25s ease-out 0s;-o-transition:all 0.25s ease-out 0s;-ms-transition:all 0.25s ease-out 0s;transition:all 0.25s ease-out 0s;}
		.cards_wrap ul li a img {width: 95%;margin:0 auto;-webkit-transition:all 0.25s ease-out 0s;-moz-transition:all 0.25s ease-out 0s;-o-transition:all 0.25s ease-out 0s;-ms-transition:all 0.25s ease-out 0s;transition:all 0.25s ease-out 0s;}
		.cards_wrap ul li a span {width: 100%;float: left;font-size: 16px;color: #333;text-align: center;-webkit-transition:all 0.25s ease-out 0s;-moz-transition:all 0.25s ease-out 0s;-o-transition:all 0.25s ease-out 0s;-ms-transition:all 0.25s ease-out 0s;transition:all 0.25s ease-out 0s;}
	</style>
	
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script>
		$(window).load(function(){
			$('.cards_wrap ul li:nth-child(4n)').css({'margin-right':'0'});
		});
	</script>
</head>
<body>
<!--[if IE 6]>
	<script src="${rc.contextPath}/views/js/iepng.js?v=${EnvironmentConstant.DEPLOY_VERSION}" type="text/javascript"></script>
	<script type="text/javascript">
		EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
	</script>
<![endif]-->
<#include "/common/topbar.ftl" />
<#include "/common/header.ftl" />
<div class="toy container">
  <div class="toy-nav">
	<div class="toy-nav-l">
	  <span style="margin-top:8px;"><img src="${rc.contextPath}/views/images/credit-card.png" alt=""></span>
	  <span>礼品卡</span>
	</div>
	<!--
	<ul class="toy-nav-r">
	  <li>
		<span>排列顺序：</span>
	    <img src="${rc.contextPath}/views/images/dot.png" alt=""><a href="#">最新</a>
	  </li>
	  <li>
		<img src="${rc.contextPath}/views/images/sale.png" alt=""><a href="#">销量</a>
	  </li>
	  <li>
		<img src="${rc.contextPath}/views/images/price.png" alt=""><a href="#">价格</a>
		<a href="#" class="tip">
		  <img src="${rc.contextPath}/views/images/top.png" alt="">
		  <div class="tip-arrow">
		    <span>从高到低</span><img src="${rc.contextPath}/views/images/tip-arrow.png" alt="" class="arrow">
		  </div>
		</a>
		<a href="#" class="tip">
		  <img src="${rc.contextPath}/views/images/down.png" alt="">
		  <div class="tip-arrow">
			<span>从低到高</span><img src="${rc.contextPath}/views/images/tip-arrow.png" alt="" class="arrow">
		  </div>
		</a>
	  </li>
	</ul>
	-->
  </div>
  <div class="toy-bd container cards_wrap">
	<ul>
	  <#list cardTypeList as tmp>
	  <li>
		<a href="${rc.contextPath}/giftcard/giftCardDetail.do?skuId=${tmp.id?c}">
		  <p>${tmp.name!''}</p>
		  <img src="${tmp.imgPath!''}" alt="">
		  <span>${tmp.currentPrice!''}元</span>
		</a>
	  </li>
	  </#list>
	</ul>
  </div>
</div>
<#include "/common/nav.ftl" />
<#include "/common/footer.ftl" />
</body>
</html>