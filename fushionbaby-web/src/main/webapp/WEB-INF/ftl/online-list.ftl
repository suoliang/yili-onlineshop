<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 兜世宝 母婴用品【在线品牌】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/online-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</head>
<body>
	<#include "/common/topbar.ftl" />
	<#include "/common/header.ftl" />
	<!-- brand start -->
	<div class="brand container">
		<div class="brand-logo">
		
		<#list brandList as brand>
			<a href="${rc.contextPath}/product/productListByBrand.do?brandId=${brand.id?c}" target="_Blank"  class="brand-img">
			<img src="${brand.imgPath}" alt="${brand.brandName}"></a>
		</#list>	
		
		
		</div>
	</div>
	<!-- brand end -->
	<#include "/common/footer.ftl" />
	<#include "/common/nav.ftl" />
</body>
</html>