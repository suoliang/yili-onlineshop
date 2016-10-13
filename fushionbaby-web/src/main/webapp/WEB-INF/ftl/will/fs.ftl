<@compress single_line=true>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>宝贝风采 潮流宝贝 宝贝街头秀|Fushionbaby 像在国外一样任性</title>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/children.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<#include "/common/link.ftl"/>
</head>
<body>
    <#include "/common/topbar.ftl"/>
	<#include "/common/header.ftl"/>
	<div class="main container">
		<div class="main-hd">
		</div>
		<div class="main-bd">
			<div class="banner container" id="banner">
				<ul class="banner-scroll">
				<li>
					<#if pics??>
						<a href="${pics.url}"><img src="${pics.picture_path}" alt=""></a>
					</#if>
				</li>
				</ul>
		        <ol>
				  <#if pics??>
					<li  class="circle-current" ></li>
		          </#if>
				</ol>
				
			</div>
		</div>
		<div class="gray-line"></div>
		<#if willList??>
			<#list willList as tmp>
				<div class="pic-list">
					<!--<a href="${span}/article_html/${tmp.webArticleUrl}" target="_blank"><img src="${span}/${tmp.webBannerUrl}" alt=""></a>-->
					<a href="${rc.contextPath}/will/htmlList.do?id=${tmp.id}" target="_blank"><img src="${span}/${tmp.webBannerUrl}" alt=""></a>
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