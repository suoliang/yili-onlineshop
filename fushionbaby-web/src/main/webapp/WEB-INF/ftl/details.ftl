<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby 兜世宝 母婴用品【商品详情】 | ${skuDetail.name}</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/details.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/details.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/car-fly.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!-- 新增 -->
	<meta name="renderer" content="webkit">
	<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link href="${rc.contextPath}/views/css/perfect-scrollbar.css?v=${EnvironmentConstant.DEPLOY_VERSION}" rel="stylesheet" media="screen,projection">
	<script type="text/javascript">
       var _jsPath = "${rc.contextPath}"+"/views/";
       var _contentPath = "${rc.contextPath}"+"/";
       var _rootPath = "${rc.contextPath}";
       
       var not_login = ${FtlConstant.NOT_LOGIN};
       var has_add = ${FtlConstant.HAS_ADD};
       var success_add = ${FtlConstant.SUCCESS_ADD};
    </script>
    
<!--<script>window._bd_share_config={"common":{"bdSnsKey":{"tsina":"1184907740","tqq":"c722257cb127fb9d01a89b4fefd6c223"},"bdText":"","bdMini":"2","bdMiniList":["qzone","tsina","weixin","tqf","douban","sqq","fx","mail"],"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{},"image":{"viewList":["qzone","tsina","weixin","sqq"],"viewText":"分享到：","viewSize":"24"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","weixin","sqq"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>-->
    

<script>window._bd_share_config={"common":{"bdSnsKey":{"tsina":"1184907740","tqq":"101179105"},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>    
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
	<div class="detail container">
		<div class="detail-meta">
			<div class="tb-property">
				<div class="pass-check" id="not_login">
					 <div class="close"></div>
					<p class="desc-info">您好，请<a href="${rc.contextPath}/login/index.do">登录</a></p>
					 <!--<button class="pay-btn con-ec pass-change">确定</button>-->
				</div>
				<div class="pass-checked" id="has_add">
					 <div class="close"></div>
					<p class="desc-info">您好，已<a href="${rc.contextPath}/membercenter/center.do?true">收藏此商品！</a></p>
					 <!--<button class="pay-btn con-ec pass-change">确定</button>-->
				</div>
				<div class="tb-wrap-l">
					<ul class="top">
						<#if skuDetail?exists && skuDetail.skuImagesBig?exists>
							<#list skuDetail.skuImagesBig as image>
								<li><a href="javascript:"><div class="verticalAlign_detail_top"><img src="${image}" alt=""></div></a></li>
							</#list>
						</#if>
					</ul>
					<ul class="bottom">
						<#if skuDetail?exists && skuDetail.skuImagesSmall?exists>
							<#list skuDetail.skuImagesSmall as image>
								<li><a href="javascript:"><div class="verticalAlign_detail_bottom"><img src="${image}" alt=""></div></a></li>
							</#list>
						</#if>
					</ul>
				</div>
				<div class="tb-wrap-r">
					<div class="tb-detail-hd">
						<input type="hidden" id="skuId" value="${skuDetail.selectedSku.skuId?c}"/>
						<h2>${skuDetail.name}</h2>
						<p><#if skuDetail?exists && skuDetail.description?exists>(${skuDetail.description})</#if></p>
					</div>
					<div class="price">
						<span class="current-price">FushionBaby价：&yen;</span>
						<em class="present-price">${skuDetail.priceNew}</em>
						<span class="cost-price">原价：&yen;</span>
						<del>${skuDetail.priceOld}</del>
					</div>
					<div class="color-size fl">
						<div class="csbox fl">
							<em class="esc">
								<img src="${rc.contextPath}/views/images/details-esc.png " alt="">
							</em>
						<#if skuDetail?exists && skuDetail.colorList?exists && (skuDetail.colorList?size > 0 )>	
							<dl class="color fl width100">
								<dt>可选择颜色：</dt>
								<dd class="color-choose fl">
								<#list  skuDetail.colorList as color>
									<#if skuDetail?exists && skuDetail.selectedSku.color?exists && color == skuDetail.selectedSku.color>
										<a href="javascript:" class="choose cs-current" >
										<input type="hidden" id="cs-current-color" value="${skuDetail.selectedSku.color}" />
									<#else>
										<a href="javascript:" class="choose" onclick="selectSkuByColor('${skuDetail.selectedSku.skuId?c}','${color}','${skuDetail.productId}')">
									</#if>
											${color}
											<em class="gougou">
												<img src="${rc.contextPath}/views/images/gougou.png" alt="">
											</em>
										</a>
								</#list>	
									
								</dd>
							</dl>
						</#if>
						<#if skuDetail?exists && skuDetail.sizeList?exists && (skuDetail.sizeList?size > 0)>	
							<dl class="size fl">
								<dt>可选择尺寸：</dt>
								<dd class="size-choose fl">
								<#list skuDetail.sizeList as size>
									<#if skuDetail.selectedSku.size?exists && size == skuDetail.selectedSku.size>
										<a href="javascript:" class="choose cs-current" >
										<input type="hidden" id="cs-current-size" value="${skuDetail.selectedSku.size}" />
									<#else>
										<a href="javascript:" class="choose"  onclick="selectSkuBySize('${skuDetail.selectedSku.skuId?c}','${size}','${skuDetail.productId}')" >
									</#if>
										<span>${size}</span>
										<em>
											
										</em>
										<em class="gougou">
											<img src="${rc.contextPath}/views/images/gougou.png" alt="">
										</em>
									</a>
								</#list>	
								</dd>
							</dl>
						</#if>	
						</div>
					</div>
					<div class="buy fl">
						<p>
							<h3>购买数量：</h3>
						</p>
						<div class="amountent">
							<span class="amount">
								<a href="javascript:setAmount.reduce();" class="left goods_cut" >-</a>
								<input type="text" name="number" id="baby-num" onkeyup="setAmount.modify();" class="num amount-num" value="1">
								<a href="javascript:setAmount.add();" class="right goods_add" >+</a>
								
							</span>
							<em></em>
						</div>
							<div class="btn">
								<span class="cart">
								<img src="${rc.contextPath}/views/images/cart.png" 
									onclick="cart.addToCart('${skuDetail.selectedSku.skuId?c}',$('#baby-num').val());" 
									alt="加入购物车" class="bcar">
								</span>
								<!---->
								<#if skuDetail?exists && skuDetail.selectedSku.availableQty?exists>
									<span class="buy-now" onclick="buyNowSku('${skuDetail.selectedSku.skuId?c}',$('#baby-num').val())"></span>
								</#if>
								<span class="collect" onclick="collectSku(${skuDetail.selectedSku.skuId?c})">
									
								</span>
							</div>
							
					</div>
					<div class="bdsharebuttonbox">
                        <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
                        <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
						<a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
						<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 超值组合  开始 -->
		<#if skuCombination?exists>
			<div class="group_wrap fl width100">
				<h3 class="detail_tit">超值组合</h3>
				
				<#list skuCombination.skuDtoList as skuDto>
				
				<a href="" title="${skuDto.name}">
					<div class="group">
						<div class="verticalAlign_group"><img src="${skuDto.imgPath}" alt=""></div>
						<p>${skuDto.name}</p>
						<span>￥${skuDto.priceNew} 元&emsp;<i>${skuDto.priceOld}</i></span>
					</div>
				</a>
				<#if skuDto_index == skuCombination.skuDtoList?size-1>
					<span class="group_add"></span>
				<#else>
					<span class="group_add">+</span>
				</#if>
				</#list>

				<div class="group_right">
					<span>套&ensp;装&ensp;价：${skuCombination.combinationPrice}</span>
					<span>原&emsp;&emsp;价：${skuCombination.originalPrice}</span>
					<span class="color_red oh">立即节省：${skuCombination.discount}</span>
					<a class="group_right_btn" href="javascript:combinationBuy(${skuCombination.combinationId})">立即购买</a>
				</div>
				<span class="group_equal">=</span>
			</div>
		</#if>
		<!-- 超值组合 结束 -->
		<div class="main-wrap">
			<div class="tabbarbox" id="tabbarbox">
				<ul class="tabbar">
					<li class="tab selected ggcs"><a href="javascript:">规则参数</a></li>
					<li class="tab spxq"><a href="javascript:">商品详情</a></li>
					<li class="tab pjsd"><a href="javascript:">评价晒单</a></li>
					<li class="tab cnxh"><a href="javascript:">猜你喜欢</a></li>
					<li class="tab ys"><a href="javascript:">品牌优势</a></li>
					<li class="last">
						<div class="buycar">
							<span>&yen:${skuDetail.priceNew}</span>
							<img src="${rc.contextPath}/views/images/bcar.png" onclick="cart.addToCart('${skuDetail.selectedSku.skuId?c}',$('#baby-num').val());" alt="加入购物车" class="car">
						</div>
					</li>
				</ul>
			</div>
			<div class="attributes">
				<div class="attributes-list">
					<ul class="attr">
						<li>商品名称：${skuDetail.name}</li>
						<li>商品编号：${skuDetail.selectedSku.skuCode}</li>
						<#if skuDetail.brandName?exists><li>品牌：${skuDetail.brandName}</li></#if>
						<#if skuDetail?exists && skuDetail.attr?exists>
							<#list skuDetail.attr as attr>
								<#if attr.name!='' && attr.content!=''>
								<li>${attr.name}：${attr.content}</li>
								</#if>
							</#list>
						</#if>
					</ul>
				</div>
				<#if skuDetail.video_url?exists>
					<video preload="metadata" controls="controls" class="playvideo" 
					src="${skuDetail.videoUrl}" ></video>
				</#if>
				<div class="description">
					<div class="content">
					<#if skuDetail.productDetailImgs?exists && (skuDetail.productDetailImgs?size > 0)>
				
						<#list skuDetail.productDetailImgs as img>
							<#if img.imgUrl?exists && img.imgUrl!=''>
								<a href='${img.imgPath}'><img class="bttrlazyloading" data-bttrlazyloading-lg-src="${img.imgPath!''}" alt="" ></a>	
							<#else>
								<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${img.imgPath!''}" alt="" >
							</#if>
						</#list>
					</#if>
					
					</div>
				</div>
				<div class="assessment">
					<div class="reviews">
						<div class="reviews-hd">
							<h4 class="hd">评价晒单</h4>
							<div class="bd">
								<ul class="bd-l">
									<li class="bd-l-title">
										<span>${skuDetail.commentAvgScore}.0</span>
										<span class="succeed">商品满意度</span>
									</li>
									<li class="star_rank">
										<ul>
											<li class="unstar"></li>
											<li class="unstar"></li>
											<li class="unstar"></li>
											<li class="unstar"></li>
											<li class="unstar"></li>
										</ul>
									</li>
									<div class="rank_nav">
										<a class="rank_nav_a" href="javascript:void(0);"><span>${skuDetail.commentAvgScore}.0</span></a>
										<span class="rank_nav_color_1"></span>
										<span class="rank_nav_color_2"></span>
										<span class="rank_nav_color_3"></span>
										<span class="rank_nav_color_4"></span>
										<span class="rank_nav_color_5"></span>
									</div>
									<div class="rank_nav_bottom">
										<span>很不满意</span>
										<span>不满意</span>
										<span>一般</span>
										<span>满意</span>
										<span>很满意</span>
									</div>
								</ul>
							</div>
						</div>
						
						<div class="rate_grid" id="rate_grid_the">
							
						</div>
						<div class="favour">
							<p class="love">猜你喜欢</p>
							<div class="collect-main">
								<ul class="clearfix">
								<#if linkSkuList?exists && (linkSkuList?size > 0)>
								<#list linkSkuList as linkSku>
									<li>
										<div class="collect-wrap">
											<a class="collect-hd" href="${rc.contextPath}/product/skuDetail.do?skuId=${linkSku.id?c}">
												<div class="verticalAlign_detail_like">
													<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${linkSku.imgPath}" alt="">
												</div>
											</a>
											<p class="now-pic">&yen;${linkSku.priceNew}</p>
											<p class="ori-pic">&yen;${linkSku.priceOld}</p>
											<a href="${rc.contextPath}/product/skuDetail.do?skuId=${linkSku.id?c}"class="collect-desc">${linkSku.name}</a>
											
										</div>
									</li>
								</#list>
								</#if>	
								</ul>
							</div>
						</div>
						<div class="good">
							<p class="better">FushionBaby优势</p>
							<div class="pic">
								<a href="#">
									<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${rc.contextPath}/views/images/bab.jpg" alt="">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	

	<!-- footer begin -->
	
	<script src="${rc.contextPath}/views/js/jqthumb.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script>
		$(function(){
			$('.rate_grid_middle img').jqthumb({
				width: 60,
				height: 60,
				after: function(imgObj){
					imgObj.css('opacity', 0).animate({opacity: 1}, 1000);
				}
			});
		});
	</script>
	<script src="${rc.contextPath}/views/js/perfect-scrollbar.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script src="${rc.contextPath}/views/js/jquery.mousewheel.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	
	
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery.bttrlazyloading.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script><!-- 延迟加载JS文件 -->
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

<script type="text/javascript" src="${rc.contextPath}/web-js/details.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</body>
</html>