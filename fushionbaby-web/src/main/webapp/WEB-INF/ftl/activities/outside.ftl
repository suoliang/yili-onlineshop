<@compress single_line=true>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>【户外活动】-亲子活动_家庭活动_宝贝互动</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}>" />
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/scroll.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>	
</head>
<body>
    <#include "/common/topbar.ftl"/>
	<#include "/common/header.ftl"/>
	<div class="main container">
		<div class="main-hd">
			会员增值&gt;&gt;
			<a href="${rc.contextPath}/activity/list.do">户外活动</a>
		</div>
		<div class="main-bd">
			<div class="banner container" id="banner">
				<a href="#"><!--${(pic.url)!''}-->
					<img src="${(pic.picture_path)!''}" alt="圣诞快乐">
				</a>
			</div>
		</div>
		<div class="gray-line"></div>
		<#if activities??>
			<#list activities as tmp>
				<div class="pic-list">
					<a href="${rc.contextPath}/activity/detail.do?id=${tmp.id?c}">
						<img src="${tmp.webBannerUrl!''}" alt="">
					</a>
				</div>
				<div class="dash-line"></div>
			</#list>
		</#if>
	</div>
	<div class="gap"></div>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
</body>
</html>
</@compress>