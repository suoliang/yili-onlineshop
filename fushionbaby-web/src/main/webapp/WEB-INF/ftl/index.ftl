
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Fushionbaby 兜世宝 像在国外一样任性！</title>
		<meta property="wb:webmaster" content="b1b45731c77aa33f" />
		<meta property="qc:admins" content="35504160766653017621216375" />
		<script src="${rc.contextPath}/views/ftl/publicHeadTags.js"></script><!-- 公共<head>标签 -->
		<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/style.css">
		<script type="text/javascript" language="javascript">
			var _ContextPath = "${rc.contextPath}";
		</script>
	</head>
	<body id="index">
		<#include "/common/topbar.ftl" />
		<!--site-topbar end-->
		<#include "/common/header.ftl" />
		<!-- ///////////////////////  幻灯片  开始  //////////////////////////// -->
			<div id="all">
				<div id="chuantong">
							<ul class="tuul">
								<#if indexDto?exists && indexDto.focusPictureList?exists>
									<#list indexDto.focusPictureList as pic>
					            		
					            		<#if pic_index == 0>
						               	 	<li class="bannerdingwei">
						                <#elseif pic_index == 3>
						                	<li>
						                	<!--<div id="show_time"></div>-->
						                <#else>
						               	<li>
						                </#if>
						                	<#if pic.url !=''>
						                		<a href="${pic.url}" target="_blank"><img src="${pic.picture_path}" alt=""></a>
						                	<#else>
						                		<a href="javascript:void(0)"><img src="${pic.picture_path}" alt=""></a>
						                	</#if>

						                </li>
					         		</#list>
								</#if>  
							</ul>
							<div class="anniu">
								<span class="leftbut"></span>
								<span class="rightbut"></span>
							</div>
							<ul class="dianul">
								 <#if indexDto?exists && indexDto.focusPictureList?exists>
									<#list indexDto.focusPictureList as pic>
										<#if pic_index == 0>
										 <li class="cur"></li>
										<#else>
										 <li></li>
										</#if>
									</#list>
							  </#if>   
							</ul>
							<a class="at_arro_lf"></a>
							<a class="at_arro_ri"></a>
						</div>
						<div id="scroll_box">
							<h3>抢购专区 直击底价 <span>HOT</span></h3>
							<div class="box_163css">
								<ul>
								<#assign timelimit_cols=FtlConstant.TIMELIMIT_COLS />
								<#if indexDto?exists && indexDto.skuTimelimitList?exists>
									<#list 1..(((indexDto.skuTimelimitList?size)?number - 1)/(FtlConstant.TIMELIMIT_COLS?number) + 1) as row>
										<li>
											<#list indexDto.skuTimelimitList as sku>
												<#if sku_index = ((indexDto.skuTimelimitList?size)?number/(FtlConstant.TIMELIMIT_COLS?number) + 1) * (sku_index+1)>
													<#break>
												</#if>
												<#if (sku_index >=(row-1)*timelimit_cols) && (sku_index <  row * timelimit_cols)>
													
													<div class="banner-box">
														<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.skuId?c}"  target="_blank" class="hide-a">立即抢购</a>
														<div class="banner-box-l">
															<div class="verticalAlign">
																<img src="${sku.imgPath}" class="banner-box-img"/>
															</div>
														</div>
														<div class="banner-box-r">
															<span class="banner-box-name nowrap">${sku.skuName}</span>
															<span class="banner-box-price">￥${sku.limitPrice}</span>
															<span>剩余时间：</span>
															<div class="endtime" value="${sku.offshelvestime}"></div>
														</div>
													</div>
													
												</#if>	
												
											</#list>
										</li>
									</#list>
								</#if>
								</ul>
								<!-- <ol class="scroll_nav">
												<li class="current"><p>1</p></li>
												<li><p>2</p></li>
												<li><p>3</p></li>
												<li><p>4</p></li>
								</ol> -->
								<p class="prev" id="banner_prev"></p>
								<p class="next" id="banner_next"></p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- banner end -->
			<!-- ///////////////////////  幻灯片 结束  //////////////////////////// -->
			<div class="newpro container">
				<div class="newpro-hd">
					<h2 class="column-title">FUSHION BABY 海外同步专区</h2>
					<span class="new">Global</span>
					<div class="more">
						<a class="more-btn" target="_blank" href="${rc.contextPath}/product/product-more-list.do?label_code=global">
							<span>更多</span>
						</a>
					</div>
				</div>
				<div class="newpro-bd">
					<div class="nbw-arrow-wrapper">
						<div class="arrow-btn l-arrow iconfont naw-current"id="left">
							&#xe603;
						</div>
						<div class="arrow-btn r-arrow iconfont"id="right">
							&#xe604;
						</div>
					</div>
					<div class="newpro-bd-wrapper">
						<ul class="newpro-bd-list"id="newpro-wrapper">
							<#assign newproRows=(indexDto.globalSkuList?size-1)/FtlConstant.NEWPRO_COLS+1 />
							<#assign newproCols=FtlConstant.NEWPRO_COLS />
							<#list 1..newproRows as row>
							<li class="newpro-bd-item">
								<#if indexDto?exists && indexDto.globalSkuList?exists>
								<#list indexDto.globalSkuList as sku>
								<#if sku_index = newproRows * (sku_index+1)>
								<#break>
								</#if>
								<#if (sku_index >=(row-1)*newproCols) && (sku_index <  row * newproCols)  >
								<#if sku_index = (row-1)* newproCols >
								<div class="newpro-bd-column nbc-current <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
								<#else>
								<div class="newpro-bd-column <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
										</#if>
										<span class="item-info">
										<span class="item-title">
										<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank">${sku.name}</a>
										</span>
										<span class="item-price">
										&yen;${sku.priceNew}元
										<del>${sku.priceOld}</del>
										</span>
										<span class="item-price eva">
										已有<span><#if sku.commentCount?exists>${sku.commentCount}</#if></span>人评价
										</span>
										</span>
										<div class="item-thumb">
											<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank">
												<div class="verticalAlign">
													<img src="${sku.imgPath}" alt="">
												</div>
											</a>
										</div>
									</div>
									</#if>
									</#list>
									</#if>
								</li>
								</#list>
								<li class="newpro-bd-item">
									<#if indexDto?exists && indexDto.globalSkuList?exists>
									<#list indexDto.globalSkuList as sku>
									<#if (sku_index < newproCols)>
									<#if sku_index = 0>
									<div class="newpro-bd-column nbc-current <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
										<#else>
										<div class="newpro-bd-column <#if sku.hasGift?? && sku.hasGift=='y'>icon-gift</#if>">
											</#if>
											<span class="item-info">
											<span class="item-title">
											<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank">${sku.name}</a>
											</span>
											<span class="item-price">
											&yen;${sku.priceNew}元
											<del>${sku.priceOld}</del>
											</span>
											<span class="item-price eva">
											已有<span><#if sku.commentCount?exists>${sku.commentCount}</#if></span>人评价
											</span>
											</span>
											<div class="item-thumb">
												<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}" target="_blank">
													<div class="verticalAlign">
														<img src="${sku.imgPath}" alt="">
													</div>
												</a>
											</div>
										</div>
										</#if>
										</#list>
										</#if>
									</li>
								</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- newpro end -->
				<!-- brand-mian end -->
				<div class="preferential container">
					<div class="preferential-hd">
						<h2 class="column-title">FUSHION BABY 特惠产品</h2>
						<span class="new">Preferential</span>
						<div class="more">
							<a class="more-btn" href="${rc.contextPath}/product/product-more-list.do?label_code=preferential" target="_blank">
								<span>更多</span>
							</a>
						</div>
					</div>
					<div class="preferential-bd">
						<div class="newpro-bd-wrapper">
							<ul class="newpro-bd-list">
							<#if indexDto?exists && indexDto.discountList?exists>
								<#assign rows= (indexDto.discountList?size-1) / FtlConstant.PREFERENTIAL_COLS+1 />
								<#assign cols=FtlConstant.PREFERENTIAL_COLS />
								<#list 1..rows as row>
								<li class="newpro-bd-item">
									
									<#list indexDto.discountList as discountSku>
									<#if discountSku_index = rows * (discountSku_index+1)>
									<#break>
									</#if>
									<#if (discountSku_index >=(row-1)*cols) && (discountSku_index <  row * cols)  >
									<#if discountSku_index = (row-1)*cols >
									<div class="newpro-bd-column nbc-current <#if discountSku.hasGift?? && discountSku.hasGift=='y'>icon-gift</#if>">
										<#else>
										<div class="newpro-bd-column <#if discountSku.hasGift?? && discountSku.hasGift=='y'>icon-gift</#if>">
											</#if>
											<span class="item-info">
											<span class="item-title">
											<a href="${rc.contextPath}/product/skuDetail.do?skuId=${discountSku.id?c}" target="_blank">${discountSku.name}</a>
											</span>
											<span class="item-price">
											&yen;${discountSku.priceNew}元
											<del>${discountSku.priceOld}</del>
											</span>
											<span class="item-price eva">
											已有<span><#if discountSku.commentCount?exists>${discountSku.commentCount}</#if></span>人评价
											</span>
											</span>
											<div class="item-thumb">
												<a href="${rc.contextPath}/product/skuDetail.do?skuId=${discountSku.id?c}" target="_blank">
													<div class="verticalAlign">
														<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${discountSku.imgPath}" alt="">
													</div>
												</a>
											</div>
										</div>
										</#if>
										</#list>
										
									</li>
									</#list>
								</#if>
								</ul>
							</div>
						</div>
					</div>
					<!-- preferential end -->
					<div class="brand-main container">
						<div class="brand-hd clearfix">
							<h2 class="column-title">FUSHION BABY 品牌</h2>
							<span class="brand">Brand</span>
							<div class="more">
								<a class="more-btn" href="${rc.contextPath}/product/brand-more-list.do" target="_blank">
									<span>更多</span>
								</a>
							</div>
						</div>
						<div class="brand-bd">
							<div class="newpro-bd-l">
								<div class="brand-logo">
									<#if indexDto?exists && indexDto.brandList?exists && (indexDto.brandList?size > 0)>
									<#list indexDto.brandList as brand>
									<a class="brand-img" href="${rc.contextPath}/product/productListByBrand.do?brandId=${brand.id?c}" target="_blank">
										<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${brand.imgPath}" alt="${brand.name}">
									</a>
									</#list>
									</#if>
								</div>
							</div>
							<div class="newpro-bd-r">
								<dl class="hot-seniority">
									<dt class="hot-title">
									<h3>
									热销商品
									<span class="hot">Hot</span>
									</h3>
									</dt>
									<#if indexDto?exists && indexDto.skuHotList?exists>
									<#list indexDto.skuHotList as hot>
									<dd class="hot-list">
									<span class="item-num">${hot_index+1}</span>
									<span class="item-info">
									<a href="${rc.contextPath}/product/skuDetail.do?skuId=${hot.skuId?c}" target="_blank" title="${hot.name}">${hot.name}</a>
									</span>
									<span class="item-thumb">
									<a class="item-img" href="${rc.contextPath}/product/skuDetail.do?skuId=${hot.skuId?c}" target="_blank">
										<div class="verticalAlign_hot">
											<img src="${hot.imgUrl}" alt="${hot.name}">
										</div>
									</a>
									</span>
									</dd>
									</#list>
									</#if>
								</dl>
							</div>
						</div>
					</div>
					<#include "/common/footer.ftl" />
					<#include "/common/nav.ftl" />
					<script type="text/javascript" src="${rc.contextPath}/views/js/base.min.js" ></script>
					<script type="text/javascript" src="${rc.contextPath}/views/js/index-lunbo.min.js"></script><!-- 首页banner轮播JS -->
					<script type="text/javascript" src="${rc.contextPath}/views/js/scroll.min.js"></script>
					<script type="text/javascript" src="${rc.contextPath}/views/js/slider.min.js" ></script>
					<script type="text/javascript">
						$(window).load(function() {
							var option = {
								prev:"#banner_prev", /**上一张按钮，默认为null*/
								next:"#banner_next",/**下一张按钮，默认为null*/
								nav:'.scroll_nav',/**轮播导航，默认为null,当为null时，自动生成导航。*/
								auto:false/**是否自动轮显，默认为true*/
							};
							$("#scroll_box").baisonSlider(option);
						})
					</script>
					<script type="text/javascript" src="${rc.contextPath}/views/js/jquery.bttrlazyloading.min.js"></script><!-- 延迟加载JS文件 -->
					<!--[if lt IE8]>
						<script>
							$(function(){
								$('.bttrlazyloading').each(function(){
						    	$(this).attr({'src':$(this).attr('data-bttrlazyloading-lg-src')});
						    })
							})
						</script>
					<![endif]-->
					<script type="text/javascript">
					  $(".bttrlazyloading").bttrlazyloading({placeholder:'${rc.contextPath}/views/images/lazyload.gif'});
					</script>
					<script>
					$(window).load(function(){
						/*动态修改样式*/
						$(".box_163css li").each(function(){
							$(this).find(".banner-box").eq(0).css("border-left","none");
						})
						/*二级分类框弹出*/
					  $('.drap-wrap').hover(function(){
					  	$('.hide_nav').css({'visibility':'visible'}).animate({'left':'258px','opacity':'1'},200);
					  },function(){
					  	$('.hide_nav').animate({'left':'0','opacity':'0'},200);
					  	setTimeout(function(){
					  		$('.hide_nav').css({'visibility':'hidden'});
					  	},200);
					  });

					  /*切换分类框*/
					  $('.navl').hover(function(){
					  	$('.hide_nav ul').eq($(this).index()).show().animate({'margin-left':'0','opacity':'1'},400).siblings().hide();
					  },function(){
					  	$('.hide_nav ul').eq($(this).index()).siblings().css({'margin-left':'-100px'});
					  })

						/*一级导航添加对应的三角图标*/
					  $('.hide_nav ul').hover(function(){
					  	$('.navl').eq($(this).index()).addClass("nav_current");
					  },function(){
					  	$('.navl').eq($(this).index()).removeClass("nav_current");
					  })

					  $('.navl').hover(function(){
					  	$(this).addClass("nav_current");
					  },function(){
					  	$(this).removeClass("nav_current");
					  })

					})
				</script>
				</body>
			</html>