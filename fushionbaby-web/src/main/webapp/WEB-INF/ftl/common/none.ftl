<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="${rc.contextPath}/views/css/404-none.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/index.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</head>
<body>

	<#include "/common/topbar.ftl" />
	<#include "/common/header.ftl" />
	<!-- 404 begin -->
	<div class="container">
		<div class="not-found">
			<span class="logo-gif"></span>
			<div class="err">
				<span>没有你搜索的商品 <#if searchKey?exists>${searchKey}</#if></span>

					<em></em>


			</div>
			<div class="content">
				<p>您可以</p>
				<p>返回 <a href="${rc.contextPath}/index/index.do" class="home-page">FushionBaby首页</a></p>
				<p class="enter">快速进入</p>
				<p class="list">
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=1">安全座椅</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=2">童车</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=3">洗护用品</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=4">喂哺用品</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=5">玩具</a>
				</p>
			</div>
		</div>
	</div>
	<!-- toy end -->
	<!-- footer begin -->
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
</body>
</html>