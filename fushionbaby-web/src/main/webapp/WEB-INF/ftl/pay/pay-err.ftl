<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【支付页面】</title>
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/404-none.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/checked.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<style>
	.not-found{margin-bottom: 100px;}
	.not-found .err{background:none;color:#27C9A7;width:400px;color: #BA2323;top: 26px;text-align: left;left: 500px;}
	.content .wrap{clear: both;overflow:visible;width: 425px;height: 33px;}
	.content .wrap .time{float: left;}
	.not-found .content .my-order{width: 92px;height: 29px;border-radius: 3px;text-align: center;line-height: 29px;background-color: #7DDFF5;float: left;margin-left: 10px;color: #fff;}
	.not-found .content a.home-page{font-size: 18px;}
	</style>
</head>
<body>
<!--[if IE 6]>
<script src="${rc.contextPath}/views/js/iepng.js" type="text/javascript"></script>	
<script type="text/javascript">
   EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
</script>
<![endif]-->
<#include "/common/topbar.ftl" />
<div class="site-header container" style="height:179px">
 	<div class="logo">
 		<h1>
 			<a href="${rc.contextPath}/index/index.do">
 				<img src="${rc.contextPath}/views/images/logo.jpg" alt="fushion baby" title="fushion baby">
 				<span class="logo-txt">fushion baby</span>
 			</a>
 		</h1>
 	</div>
	<div class="site-header-r">
		<ul class="progress-t">
			<li>
				<span>我的购物车</span>
				<em></em>
			</li>
			<li>
				<span>确认订单并支付</span>
				<em></em>
			</li>
			<li style="margin:0 0 0 6px;background-color:#CBCBCB">
				<span>完成订单</span>
				<em class="progress-t-n"></em>
			</li>
		</ul>
		<div class="progress"></div>
	</div>

</div>
<!-- site-header end -->

	<!-- address -->
	<div class="address container">
		<form action="#">
			<!-- 404 begin -->
			<div class="container">
				<div class="not-found">
					<span class="logo-gif"></span>
					<div class="err">
						<span>支付失败</span><span id="err_msg">${errmsg!''}</span>
					</div>
					<div class="content">
						<p>您可以 稍后再试，或者返回 <a href="${rc.contextPath}/membercenter/center.do?order">个人中心</a>。</p>
						<p class="enter">快速通道：</p>
						<p class="list">
							<a href="${rc.contextPath}/product/productListByCategory.do?category_id=1">安全座椅</a>
							<a href="${rc.contextPath}/product/productListByCategory.do?category_id=2">童车</a>
							<a href="${rc.contextPath}/product/productListByCategory.do?category_id=3">洗护用品</a>
							<a href="${rc.contextPath}/product/productListByCategory.do?category_id=4">喂哺用品</a>
							<a href="${rc.contextPath}/product/productListByCategory.do?category_id=5">玩具</a>
						</p>
					</div>
				</div>
			</div>
		</form>
	</div>

	<!--Footer-->
	<#include "/common/footer.ftl" />

	<!-- 支付提示弹出框 -->
	<div class="pop-pay">
		<h5>支付提示</h5>
		<div class="pp-wrap clearfix">
			<i></i>
			<div class="pp-r">
				<p>支付完成前，请不要关闭此支付验证窗口。</p>
				<p class="mt">支付完成后，请根据您支付的情况点击下面按钮。</p>
				<div class="btn"><button class="btn-no">支付遇到问题</button><button class="btn-ok">支付完成</button></div>
			</div>
		</div>
	</div>
</body>
</html>