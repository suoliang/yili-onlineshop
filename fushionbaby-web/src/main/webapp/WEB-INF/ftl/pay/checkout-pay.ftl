<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【支付页面】</title>
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/checked.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" language="javascript">
		var _ContextPath = "${rc.contextPath}";
		function selectPay(pay_type, url){
			$('#paymentType').val(pay_type);
			$('#order_pay').attr("action", url);
		}
		function pay_submit(){
			var paymentType = $("#paymentType").val();
			if(paymentType == null || paymentType == undefined || $.trim(paymentType).length<=0){
				alert("请选择支付类型!");
				return;
			}
			$("#ok_pay").attr("disabled", "disabled");
			$("#order_pay").submit();
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
<!--site-topbar end-->
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

	<!-- payment -->
	<div class="payment container">
		<h3>支付方式</h3>
		<ul class="mode">
			<li class="zfb clickc checked">
				<a href="javascript:selectPay('ZFB_WEB_JSDZ', '${payZfbJsdzUrl}')">
					<img src="${rc.contextPath}/views/images/zfb.png" alt="支付宝支付">
					<span class="changecolor">支付宝支付</span>
				</a>
			</li>
			<li class="wy clickc">
				<a href="javascript:selectPay('ZXYL_WEB', '${payYlUrl}')">
					<img src="${rc.contextPath}/views/images/wyzf.png" alt="银联">
					<span class="changecolor">银联</span>
				</a>
			</li>
			<!--<li class="wx clickc">
				<a href="javascript:selectPay('WX_WEB')">
					<img src="${rc.contextPath}/views/images/wxzf.png" alt="微信支付">
					<span class="changecolor">微信支付</span>
				</a>
			</li>-->
			
		</ul>
		<p style="color:#32C7A9;font-weight:bold;font-size:16px;padding-left:26px;margin:22px 0 0 0;">总金额：<span>${price!''}</span>元</p>
		<p class="btn" style="float:none;">
			<input style="margin-left:26px;" class="btn-info btn-next" id="ok_pay" type="button" value="确认并支付" onclick="pay_submit()"/>
		</p>
	</div>
	<!-- confirm -->
	
	<form action="${payZfbJsdzUrl!''}" method="post" name="order_pay" id="order_pay">
		<input type="hidden" name="sid" id="sid" value="${sid!''}"/>
		<input type="hidden" name="orderCode" id="orderCode" value="${orderCode!''}"/>
		<input type="hidden" name="paymentType" id="paymentType" value="ZFB_WEB_JSDZ"/>
		<input type="hidden" name="price" id="price" value="${price!''}"/>
	</form>

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