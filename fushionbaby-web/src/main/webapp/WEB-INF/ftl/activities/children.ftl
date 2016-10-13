<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>【亲子课程】-母婴知识,资讯_宝贝讲堂_宝贝成长计划</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<#include "/common/link.ftl"/>
</head>
<body>
<!--[if IE 6]>
<script src="js/iepng.js" type="text/javascript"></script>	
<script type="text/javascript">
   EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
</script>
<![endif]-->
	
	<#include "/common/topbar.ftl" />
	<!--site-topbar end-->
	<#include "/common/header.ftl" />
	
	
	<!-- site-header end -->
	<!-- main begin -->
	<div class="main container">
		<div class="breadcrumb_nav">
			<a href="#">会员增值</a>
			<i class="fa fa-angle-double-right"></i>
			<a href="#">亲子活动</a>
		</div>
		<div class="main-bd fl">
			<div class="banner container" id="banner">
			<#if pic?exists>
				<a href="${pic.url!''}">
					<img src="${pic.picture_path!''}" alt="">
				</a>
			</#if>
			</div>
		</div>
		
		
		
		<#if familyList?exists && (familyList?size > 0)>
			<#list familyList as family>
				<div class="gray-line"></div>
				<div class="pic-list">
					<a href="${rc.contextPath}/parchiart/article-content.do?familyId=${family.familyId?c}">
						<img src="${family.bannerUrl!''}" alt="${family.tital!''}">
						<!-- <div class="pic-info pic-desc">
							<p class="info-hd">开拓宝宝知识</p>
							<p class="info-bd">让您更加了解您的宝宝的相关知识</p>
							<p class="info-bd">每个宝宝都是天使</p>
							<a href="javascript:" class="more more-desc">详细>>></a>
						</div> -->
					</a>
				</div>
			</#list>
		</#if>
		
		
		
		
		
	</div>
	
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
	
</html>