<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>商品组合优惠 - Fushionbaby</title>
		<script src="${rc.contextPath}/views/js/publicHeadTags.js"></script><!-- 公共<head>标签 -->
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/car.css">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css">
		<script type="text/javascript" language="javascript">
			var _ContextPath = "${rc.contextPath}";
		</script>
	</head>
	<body id="car-group">
		<#include "/common/topbar.ftl" /><!-- 公共顶部 替换公共ftl文件 -->
		<div class="site-header container" style="height:auto;">
			<div class="logo">
				<h1>
				<a href="/index/indexList.do">
					<img title="fushionbaby" alt="fushionbaby" src="${rc.contextPath}/views/images/logo.jpg">
				</a>
				</h1>
			</div>
			<div style="margin-bottom:40px;" class="site-header-r">
				<ul class="progress-t">
					<li>
						<span>优惠组合</span>
						<em></em>
					</li>
					<li style="background-color: rgb(203, 203, 203);">
						<span>确认订单并付款</span>
						<em class="progress-t-n"></em>
					</li>
					<li style="margin:0 0 0 6px;background-color:#CBCBCB">
						<span>完成订单</span>
						<em class="progress-t-n"></em>
					</li>
				</ul>
				<div class="progress progress-start"></div>
			</div>
		</div>
		<div class="car-main container">
			<form action="">
				<table class="order-table" id="content">
					<tbody>
						<tr class="title-list">
							<th class="t-title col1">组合商品</th>
							<th class="t-title col2">单价（元）</th>
						</tr>
						<!-- 商品 开始 -->
					<#if skuCombinationDto?exists>
					<#list skuCombinationDto.skuDtoList as skuDto>
						<tr class="order-con">
							<td>
								<div class="order-goods">
									<a href="javascript:void(0);">
										<div class="verticalAlign_car">
											<img src="${skuDto.imgPath}" alt="${skuDto.name}">
										</div>
									</a>
								</div>
								<div class="order-tex">
									<p><a href="javascript:void(0);">${skuDto.name}</a></p>
									<div class="order-tex-bd">
										<#if skuDto.color?exists><div class="color">颜色：${skuDto.color}</div></#if>
										<#if skuDto.size?exists><div class="size">尺码：${skuDto.size}</div></#if>
									</div>
								</div>
							</td>
							<td class="con-list pri">&yen;<span>${skuDto.priceNew}</span></td>
						</tr>
					</#list>
					</#if>
					<!-- 商品 结束 -->
					</tbody>
				</table>
				<div class="car-main-ft clearfix">
					<div class="cmf-r-wrap">
						<div class="shop-cart-action">
							<p class="color_red textR">组合产品优惠金额：- ${skuCombinationDto.discount}&ensp;</p>
							<p class="total-price">
							<span>应付总金额：</span>
							<i>${skuCombinationDto.combinationPrice}</i> 元
							</p>
						</div>
						<div class="btn-wrap clearfix">
							<a class="btn btn-r checked" 
							href="${rc.contextPath}/order/carGroupGotoOrder.do?combinationId=${skuCombinationDto.combinationId}">去结算</a>
						</div>
					</div>
				</div>
			</form>
		</div>
		<#include "/common/nav.ftl" />
		<#include "/common/footer.ftl" /><!-- 公共底部 替换公共ftl文件-->
		<script type="text/javascript" src="${rc.contextPath}/views/js/base.js"></script>
	</body>
</html>