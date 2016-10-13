<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Fushionbaby 满赠</title>
		<meta name="keywords" content="Fushionbaby,兜世宝,像在国外一样任性_100%正品保证_中高端母婴选购平台_海外进口,口碑甄选,独家代理"/>
		<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
		<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}>" /><!--浏览器标签图标-->
		<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}" /><!--base.css-->
		<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script><!--jquery.js-->
		<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script><!--base.js-->
	</head>
	<body>
		<!--[if IE 6]>
		<script src="js/iepng.js" type="text/javascript"></script>
		<script type="text/javascript">
		EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
		</script>
		<![endif]-->
		<#include "../../common/topbar.ftl" />
		<#include "../../common/header.ftl" />
		<!-- ///////////////////////  预热定金  开始  //////////////////////////// -->
		<div class="container">
			<iframe src="${rc.contextPath}/views/fushionbaby_zhi/${result}/index.html" id="iframepage" name="iframepage" frameBorder=0 scrolling=no width="100%" onLoad="iFrameHeight()" ></iframe>
			<script type="text/javascript" language="javascript">
			//iframe高度自适应
			function iFrameHeight() {
			var ifm= document.getElementById("iframepage");
			var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;
			if(ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
			}
			}
			</script> 
		</div>
		<!-- ///////////////////////  预热定金  结束  //////////////////////////// -->
		<#include "../../common/footer.ftl" />
		<#include "../../common/nav.ftl" />
	</body>
</html>