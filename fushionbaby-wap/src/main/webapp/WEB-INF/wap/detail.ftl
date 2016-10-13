<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="yes" name="apple-touch-fullscreen">
		<meta content="telephone=no" name="format-detection">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
		<title>fushionbaby触屏版</title>
		<script type="text/javascript" language="javascript">
			var _ContextPath = "${rc.contextPath}";
		</script>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/cssRset.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/style_li.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/public_head.css"/>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/index.js"></script>
	</head>
	<body class="fl">
		<!-- header -->
		<div class="public_header fl width100 marginB0">
			<div class="public_header_wrap fl width100">
				<a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
				商品详情
				<a class="public_btn_r" href="car.html">
					<img src="${rc.contextPath}/wap/images/34.gif" class="g-header-img"/>
				</a>
			</div>
		</div>
		<!--商品-->
		<section id="detilstop" class="fl">
			<img src="${rc.contextPath}/wap/images/35.jpg" class="detilstopimg"/>
			<!--<#if skuDetail?exists && skuDetail.skuImagesBig?exists>
				<#list skuDetail.skuImagesBig as image>
					<img src="${rc.contextPath}/wap/images/35.jpg" class="detilstopimg"/>
				</#list>
			</#if>-->
			<div class="detilstop-bottom">
				<div class="detilsjieshao">
					<p>${skuDetail.name}</p>
					<span class="detilsjieshao-duller1">￥ ${skuDetail.priceNew}</span>
					<span class="detilsjieshao-duller2">${skuDetail.priceOld}</span>
				</div>
				<div class="detilstopr">
					<div class="detils-heart">
						<img src="${rc.contextPath}/wap/images/36.gif"/>
					</div>
					<div class="daojishi">
						<span>还有</span><div class="endtime" value="1429188800000"></div>
					</div>
				</div>
			</div>
		</section>
		<!--详情，口碑，喜欢-->
		<section id="shoppingdetils" class="fl">
			<ul>
				<li class="shoppingdetils_current">商品详情</li>
				<li>baby口碑</li>
				<li>猜你喜欢</li>
			</ul>
		</section>
		<!--详情内容-->
		<section class="shopping_pic fl">
			<#if skuDetail.productDetailImgs?exists && (skuDetail.productDetailImgs?size > 0)>
				<#list skuDetail.productDetailImgs as img>
					<#if img.imgUrl?exists && img.imgUrl!=''>
						<img src="${img.imgPath!''}"/>
					<#else>
						<img src="${rc.contextPath}/wap/images/37.gif"/>
					</#if>
				</#list>
			</#if>
		</section>
		<!--口碑内容-->
		<section class="shopping_reviewsall fl">
			<div class="shopping_reviews fl">
				<div class="shopping_reviews_pinjia">
					<h3>${skuDetail.commentAvgScore}.0</h3>
					<p>总体评价</p>
				</div>
				<div class="shopping_reviews_star">
				<#assign max = FtlConstant.SCORE_MAX>
					<ul class="star-auto">
						<#list 1..skuDetail.commentAvgScore as i>
							<li></li>
						</#list>
						<#if (max-skuDetail.commentAvgScore) gt 0 >
						<#list 1..(max-skuDetail.commentAvgScore) as i>
							<li></li>
						</#list>
						</#if>
					</ul>
				</div>
			</div>
			<div class="shopping_reviews2 fl">
				<ul>
					<#if skuComments?exists && (skuComments?size > 0)>
					<#list skuComments as comment>
						<li>
							<div class="shopping_reviews2_detils">
								<p>
									<#if comment.memberName?exists>
										${comment.memberName}
									</#if>
								</p>
								<ul>
									<#if comment?? && comment.score??>
										<#list 1..comment.score as s>
											<li></li>
										</#list>
									</#if>
								</ul>
							</div>
							<p class="shopping_reviews2_p">${comment.commentContent}</p>
							<p class="shopping_reviews2_p shopping_reviews2_p_c">颜色：${comment.skuColor!''}</p>
						</li>
					</#list>	
					</#if>	
				</ul>
			</div>
		</section>
		<!--猜你喜欢-->
		<section class="shopping_like fl">
			<#assign detailCols=FtlConstant.WAPDETAILCOLS />
			<#if linkSkuList?exists && (linkSkuList?size > 0)>
				<#list 1..((linkSkuList?size) - 1)/(detailCols + 1) as row>
					<div class="shopping_like_things">
						<ul>
							<#list linkSkuList as linkSku>
								<#if linkSku_index = (linkSku?size)?number/(detailCols + 1) * (linkSku_index + 1)>
									<#break>
								</#if>
								<#if (linkSku_index >= (row-1)*detailCols) && (linkSku_index < row * detailCols)>
									<a href="${rc.contextPath}/product/skuDetail.do?skuId=${linkSku.id?c}">
										<li class="shopping_like_things_li">
											<p class="shopping_like_things_p1">${linkSku.name}</p>
											<img src="${linkSku.imgPath}" alt="${linkSku.imgPath}"/>
											<p class="shopping_like_things_p2">&yen;${linkSku.priceNew}</p>
										</li>
									</a>
								</#if>
							</#list>
						</ul>
					</div>
				</#list>
			</#if>	
		</section>
		<!--详情，口碑，喜欢结束-->
		<!--点击内容开始-->
		<section id="detilstop_click" class="fl">
			<a href="collect.html"><img src="${rc.contextPath}/wap/images/38.gif" class="detilstop_click_pic1"/></a>
			<a href="javascript:"><img src="${rc.contextPath}/wap/images/39.gif" class="detilstop_click_pic2"/></a>
			<a href="car.html"><img src="${rc.contextPath}/wap/images/40.gif" class="detilstop_click_pic2"/></a>
		</section>
		<!--返回顶部   footer-->
		<script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
	</body>
</html>