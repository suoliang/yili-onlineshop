<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title><#if fushionbaby_title?exists>${fushionbaby_title}</#if>|Fushionbaby 兜世宝 像在国外一样任性</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet" href="${rc.contextPath}/views/css/toy-product-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/style.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/index.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/iSlide.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/lunbo.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript">
		pageLineLimit = ${FtlConstant.PAGE_LIMIT}
	</script>
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
	<div id="chuantong" class="second-page">
		<ul class="tuul">
				<#if skuCategory?exists && skuCategory.focus_picture_list?exists>
					<#list skuCategory.focus_picture_list as pic>
						<#if pic_index == 0>
				           <li class="bannerdingwei">
				        <#else>
				            <li>
				        </#if>
							<a href="${pic.url}" target="_blank"><img src="${pic.picture_path}" alt=""></a>
						</li>
					</#list>
				</#if>
		</ul>
        <div class="anniu">
			<span class="leftbut"></span>
			<span class="rightbut"></span>
		</div>
		 <ul class="dianul">
			<#if skuCategory?exists && skuCategory.focus_picture_list?exists>
			<#list skuCategory.focus_picture_list as pic>
				<#if pic_index == 0>
					<li class="cur"></li>
				<#else>
					<li></li>
				</#if>
			</#list>
			</#if>
		</ul>
	</div>
	<!-- toy begin -->
	<div class="toy container">
		<div class="toy-nav">
			<div class="toy-nav-l">
				<!--<span><img src="${rc.contextPath}/views/images/seat.png" alt=""></span>-->
				<span>${skuCategory.name}</span>
			</div>
			<ul class="toy-nav-r">
				<li>
					<span>排列顺序</span>
					<img src="${rc.contextPath}/views/images/dot.png" alt="">
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&sort_param=${SortTypeConstant.MOST_NEW}">最新</a>
				</li>
				<li>
					<img src="${rc.contextPath}/views/images/sale.png" alt="">
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&sort_param=${SortTypeConstant.SALES_VOLUME}">销量</a>
				</li>
				<li>
					<img src="${rc.contextPath}/views/images/price.png" alt="">
					<a href="">价格</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&sort_param=${SortTypeConstant.PRICE}&sort_type=${SortTypeConstant.desc}" class="tip">
						<img src="${rc.contextPath}/views/images/down.png" alt="">
						<div class="tip-arrow">
							<span>从高到低</span>
							<img src="${rc.contextPath}/views/images/tip-arrow.png" alt="" class="arrow">
						</div>
					</a>
					<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&sort_param=${SortTypeConstant.PRICE}&sort_type=${SortTypeConstant.asc}" class="tip">
						<img src="${rc.contextPath}/views/images/top.png" alt="">
						<div class="tip-arrow">
							<span>从低到高</span>
							<img src="${rc.contextPath}/views/images/tip-arrow.png" alt="" class="arrow">
						</div>
					</a>
				</li>
			</ul>
		</div>
		<div class="toy-bd container">
			<ul class="toy-bd-list">
			<#assign rows=(pageset.skuList?size-1)/FtlConstant.PRODUCT_CATEGORY_COLS+1 />
			<#assign cols=FtlConstant.PRODUCT_CATEGORY_COLS>
			<#list 1..rows as row>
				<li class="toy-bd-item">
				<#list pageset.skuList as sku>
					<#if sku_index = rows * (sku_index+1)>
						<#break>
					</#if>
					<#if (sku_index >=(row-1)*cols) && (sku_index <  row * cols)  >
						<#if sku_index = (row-1)*cols >
							<div class="toy-bd-column column-cur <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
						<#else>
							<div class="toy-bd-column <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
						</#if>
								<span class="item-info">
									<span class="item-title">
										<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank" >${sku.name!''}</a>
									</span>
									<span class="item-price">
										&yen${sku.priceNew!''}
										<em>市场价:${sku.priceOld!''}元</em>
									</span>
									<span class="item-discount">
										折扣:${sku.discount!''}折
									</span>
								</span>
								<div class="item-thumb">
									<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank" >
										<div class="verticalAlign_list">
											<img src="${sku.imgPath}" alt="">
										</div>
									</a>
								</div>
							</div>
					</#if>	
				</#list>	
					
				</li>
			</#list>
				
			</ul>
			<div class="collect-page-wrap clearfix">
				<div class="collect-page">			
					<span class="page-num">共<span id="page_count"> <b>${pageset.totalPage}</b> </span>页</span>
					
					<#assign previous=(pageset.curPage-1)>  
					<#if previous gt 0> 
						<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${pageset.curPage-1}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-arrow iconfont page_prev"></a>
					<#else>
						<span style="color:red" class="page-arrow iconfont page_prev"></span>
					</#if>
					<#assign start = FtlConstant.PAGE_START />
					<#assign limit = FtlConstant.PAGE_LIMIT />
					<#if  (pageset.curPage > limit) && (pageset.totalPage gt limit) >
						<#list (pageset.curPage)..(pageset.curPage+limit-1) as i >
							<#if i <= pageset.totalPage  >
								<#if i == pageset.curPage>
									<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${i?c}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-list page-list-current">${i}</a>
								<#else>
									<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${i?c}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-list">${i}</a>
								</#if>
							</#if>
						</#list>
					<#else>
						<#list 1..limit as i>	
							<#if (pageset.totalPage >= i)  >
								<#if i == pageset.curPage>
									<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${i?c}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-list page-list-current">${i}</a>
								<#else>
									<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${i?c}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-list">${i}</a>
								</#if>
							</#if>	
						</#list>		
					</#if>
					<#assign last = (pageset.curPage+1)>  
					<#if last <= pageset.totalPage>
						 <a	href="${rc.contextPath}/product/productListByCategory.do?category_id=${skuCategory.id?c}&brand_id=${brandId!''}&cur_page=${pageset.curPage+1}&sort_param=${pageset.sortParam}&sort_type=${pageset.sortType}" class="page-arrow iconfont page_next">&#xe604;</a>
					<#else>
						<span style="color:red" class="page-arrow iconfont page_next">&#xe604;</span>	
					</#if>  
					<span class="page-num">第<span> ${pageset.curPage} </span>页</span>
				</div>
				
			</div>
		</div>
	</div>
	<!-- toy end -->
	<!-- footer begin -->
	<#include "/common/footer.ftl" />
	<#include "/common/nav.ftl" />
</body>
</html>