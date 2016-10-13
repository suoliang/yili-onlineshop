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
	.not-found .err{background:none;color:#27C9A7;width:400px;top: 26px;text-align: left;left: 500px;}
	.not-found .logo-gif{background:url('${rc.contextPath}/views/images/ok.gif') no-repeat;}
	.content .wrap{clear: both;overflow:visible;width: 425px;height: 33px;}
	.content .wrap .time{float: left;}
	.num{color: #B7251E; margin-right:5px;}
	.not-found .content .my-order{width: 92px;height: 29px;border-radius: 3px;text-align: center;line-height: 29px;background-color: #7DDFF5;float: left;margin-left: 10px;color: #fff;}
	.not-found .content a.home-page{font-size: 18px;}
	</style>
	<script type="text/javascript">
	window.onload=function(){
		var go=document.getElementById("go").innerHTML;
		var i=parseInt(go);
		function fun(){
			i--; // 这是变量
		   if(i>0)  // 如果这个i大于0 就不能跳转
		   {
		   		setTimeout(fun,1000); // 如果大于0 那么递归调用fun
		   }
		   else{
		   	window.location.href="${rc.contextPath}/membercenter/center.do?order"; //如果不满足，就转到新页面
		   }
		   document.getElementById("go").innerHTML=i;
		}
		fun();//开始调用函数
	}
	</script>
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
			<li style="margin:0 0 0 6px;">
				<span>完成订单</span>
				<em></em>
			</li>
		</ul>
		<div class="progress progress-ok"></div>
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
						<span>支付成功，您支付的金额为<span id="price">${price!''}<span>元！</span>
					</div>
					<div class="content">
						<div class="wrap"><p class="time"><span class="num" id="go">10</span>秒后跳转到个人中心</p><a class="my-order" href="${rc.contextPath}/membercenter/center.do?order">立即跳转</a></div>
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