<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【我的收藏】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
	<script type="text/javascript">
		function collectDrop(skuId){
			window.location.href = "${rc.contextPath}/collect/drop-collect.do?sku_id="+skuId;
		}
    </script>

</head>
<body>
	<div class="collect-main">
		<ul class="clearfix">
		<#if pageset?exists>
		<#if pageset.skuList?exists && (pageset.skuList?size > 0)>
			<#list pageset.skuList as sku>
			
				<li>
					<div class="collect-wrap">
						<a class="collect-hd" target="_parent" href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}">
							<div class="verticalAlign_collect">
								<img src="${sku.imgPath}" alt="">
							</div>
						</a>
						<p class="now-pic">&yen;${sku.priceNew}</p>
						<p class="ori-pic">&yen;${sku.priceOld}</p>
						<a target="_parent" href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}"class="collect-desc">${sku.name}</a>
						<div class="collect-drop" onclick="collectDrop(${sku.id?c})">
							<i></i>
							<span>取消收藏</span>
						</div>
					</div>
				</li>
				
			</#list>
		</#if>		
		</#if>		
				
					
		</ul>
		<div class="collect-page-wrap clearfix">
			<div class="collect-page">
			<#if pageset?exists>
			<#if pageset.skuList?exists && (pageset.skuList?size > 0)>			
				<span class="page-num">共<span> ${pageset.totalPage} </span>页</span>
					<#assign previous=(pageset.curPage-1)>  
					<#if previous gt 0> 
						<a href="${rc.contextPath}/collect/collect.do?cur_page=${pageset.curPage-1}&time=${time?c}" class="page-arrow iconfont"></a>
					<#else>
						<span style="color:red" class="page-arrow iconfont"></span>
					</#if>
					<#assign start = 1 />
					<#assign limit = 3 />
					<#if  (pageset.curPage > limit) && (pageset.totalPage gt limit) >
						<#list (pageset.curPage)..(pageset.curPage+limit-1) as i >
							<#if i <= pageset.totalPage  >
							
								<#if i == pageset.curPage>
									<a href="${rc.contextPath}/collect/collect.do?cur_page=${i?c}&time=${time?c}" class="page-list page-list-current">${i}</a>
								<#else>
									<a href="${rc.contextPath}/collect/collect.do?cur_page=${i?c}&time=${time?c}" class="page-list">${i}</a>
								</#if>
							</#if>
						</#list>
					<#else>
						<#list 1..limit as i>	
							<#if (pageset.totalPage >= i)  >
								<#if i == pageset.curPage>
									<a href="${rc.contextPath}/collect/collect.do?cur_page=${i?c}&time=${time?c}" class="page-list page-list-current">${i}</a>
								<#else>
									<a href="${rc.contextPath}/collect/collect.do?cur_page=${i?c}&time=${time?c}" class="page-list">${i}</a>
								</#if>
							</#if>	
						</#list>		
					</#if>
					<#assign last = (pageset.curPage+1)>  
					<#if last <= pageset.totalPage>
						 <a	href="${rc.contextPath}/collect/collect.do?cur_page=${pageset.curPage+1}&time=${time?c}" class="page-arrow iconfont">&#xe604;</a>
					<#else>
						<span style="color:red" class="page-arrow iconfont">&#xe604;</span>	
					</#if>  
					<span class="page-num">第<span> ${pageset.curPage} </span>页</span>
				</#if>
				</#if>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".if3",parent.document).addClass('list-current');
			$(".if1",parent.document).removeClass('list-current');
		});	
	</script>
</body>
</html>