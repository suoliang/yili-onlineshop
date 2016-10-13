<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>【亲子课程】-母婴知识,资讯_宝贝讲堂_宝贝成长计划</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>	
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
		<div class="main-hd">
			<a class="menu" href="javascript:">会员增值>></a>
			<a class="menu" href="javascript:">亲子课程>></a>
			<a href="javascript:">${title!''}</a>
		</div>
		<div class="main-bd">
			<div class="banner container" id="banner">
				<#if pic?exists>
					<img src="${pic!''}" alt="">
				</#if>	
			</div>
		</div>
	</div>
	<div class="par-chi-main container">
		<div class="art-main-bd">
			<h2>${title!''}</h2>
			<div class="txt-wrap content">
				${centent!''}
			</div>
		</div>
		<div class="par-chi-main-r">
			<h2>留言与交流</h2>
			<ul>
			
				
			</ul>
			<div class="page-wrap">
				<div class="page">
					<span class="page-txt">共<span id="totalPage">18</span>页</span>
					<a href="javascript:" onclick="onPageTop()" class="page-arrow iconfont"></a>
					<div id="pagego">
					</div>
					<a href="javascript:" onclick="onPageNext()" class="page-arrow iconfont">&#xe604;</a>
				</div>
			</div>
			<div class="announce-wrap clearfix">
				<div class="announce-l">
					<div class="u-photo">
					</div>
					<div class="ann-user-name" ><#if user?exists>${user.login_name}<#else>游客</#if></div>
					<input type="hidden" id="memberId" value="<#if user?exists>${user.member_id?c}</#if>" />
					<input type="hidden" id="userName" value="<#if user?exists>${user.login_name}</#if>" />
				</div>
				<div class="announce-r">
					<textarea name="content" id="eval-content" placeholder="请输入您要发表的内容"></textarea>
					<div class="error" id="show_check_content"><em>*</em><span id="check_content"></span></div> 
					<div class="ann-btn">
						<button>发表</button>
					</div>							
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="curPage" value="1" />
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
	<script type="text/javascript" >
			var familyId = ${familyId?c};
			var limit = ${FtlConstant.PAGE_LIMIT}
	</script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/par-chi-crt.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>	
</body>
</html>