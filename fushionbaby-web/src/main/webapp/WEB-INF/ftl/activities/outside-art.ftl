<@compress single_line=true>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>【户外活动】-亲子活动_家庭活动_宝贝互动</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/scroll.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/activities/outside-art.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</head>
<body>
    <#include "/common/topbar.ftl"/>
	<#include "/common/header.ftl"/>
	<#if ac?exists>
	<div class="main container">
		<div class="main-hd">
			会员增值&gt;&gt;
			<a class="menu" href="${rc.contextPath}/activity/list.do">户外活动&gt;&gt;</a>
			<a href="${ac.webIntroduceUrl!''}">${ac.name!''}</a>
		</div>
		<div class="main-bd">
			<div class="banner container" id="banner">
				<#if pic?exists>
					<a href="#"><img src="${(pic.picture_path)!''}" alt=""></a>	
				</#if>			
			</div>
		</div>
		<div class="gray-line"></div>
	</div>
	<div class="art-main container clearfix">
		<div class="art-main-hd clearfix">
			<div class="art-main-l">
			<a href="javascript:" class="big-wrap">
				<img src="${ac.webIntroduceUrl!''}" alt="">
			</a>
			</div>
			<div class="art-main-r">
			<h6><!--这里有一句话的--></h6>
			<div class="art-main-r-bd">
				<p class="timer clearfix">
					<span class="time-beg">
						<span class="tim-1">活动时间</span>
						<span class="tim-2">：${ac.time?string('yyyy-MM-dd HH:mm')}</span>
					</span>
					<span class="time-ove">
						<span class="tim-1">报名截止</span>
						<span class="tim-2">：${ac.limitApplyTime?string('yyyy-MM-dd')}</span>
					</span>
				</p>
				<p class="location clearfix">
					<span class="location-txt">活动地点：<span class="loc-are">${ac.place!''}<!--${ac.placePictureUrl!''}--></span></span>
					<span class="map"></span>
				</p>
				<div class="activity-wrap">
					<form action="" id="act-user-info">
						<div class="user-info mob clearfix">
							<span>您的手机号码:</span>
							<input type="text" placeholder="请输入您的手机号" id="phone" name="phone"/>
						</div>
						<div class="user-info num clearfix">
							<span>请选择参加人数:</span>
							<select name="number" id="number" class="address-prov">
								<option selected="selected" value="">1</option>
								<option value="">2</option>
								<option value="">3</option>
								<option value="">4</option>
								<option value="">5</option>
							</select>
						</div>
					</form>
				</div>
				<div class="ent-btn">
					<button id="applyActivity" onclick="outside_art();">确定</button>
				</div>
			</div>
			</div>
		</div>
		<div class="art-main-bd">
			<p class="tit">活动目的</p>
			<div class="txt-wrap">${ac.introduce!''}</div>
        </div>
	</div>	
	<input type="hidden" id="activitiesId" value="${ac.id?c}">
	<input type="hidden" id="activitiesName" value="${ac.name!''}">
	<div class="gap"></div>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
	<div class="mapinfo">
		<i></i>
		<img src="${ac.placePictureUrl!''}" alt="地图">
	</div>
	</#if>
</body>
</html>
</@compress>
