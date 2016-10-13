<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="renderer" content="webkit">
	<title>礼品卡详情 - Fushionbaby</title>
	<script type="text/javascript" >
		 var _ContextPath = "${rc.contextPath}";
	</script>
	<link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico"><!-- 浏览器标签图标 -->
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet" href="${rc.contextPath}/views/css/details.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	
	<style>
		.cards_detil_ul {color: #444;}
		.cards_detil_ul p {font-weight: bold;font-size: 16px;margin-bottom: 5px;}
		.cards_detil_ul li {font-size: 14px;line-height: 26px;}
	</style>
	
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/car-fly.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/details.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		
	<!-- 加减商品数量 -->
	<script type="text/javascript">
		function goods_cut() {
			var num_val = document.getElementById('number');
			var new_num = num_val.value;
			var Num = parseInt(new_num);
			if(Num > 1)	Num = Num - 1;
			num_val.value = Num;
		}
		function goods_add() {
			var num_val = document.getElementById('number');
			var new_num = num_val.value;
			var Num = parseInt(new_num);
			Num = Num + 1;
			num_val.value = Num;
		}
	</script>
	<script>
		window._bd_share_config={"common":{"bdSnsKey":{"tsina":"1184907740","tqq":"101179105"},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
	</script>
</head>
<body>
<!--[if IE 6]>
<script src="${rc.contextPath}/views/js/iepng.js?v=${EnvironmentConstant.DEPLOY_VERSION}" type="text/javascript"></script>
<script type="text/javascript">
	EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
</script>
<![endif]-->
<#include "/common/topbar.ftl" />
<#include "/common/header.ftl" />

<div class="detail container">
  <div class="detail-meta">
    <div class="tb-property">
	  <div class="pass-check" id="not_login">
		<div class="close"></div>
		<p class="desc-info">您好，请<a href="${rc.contextPath}/login/index.do">登录</a></p>
	  </div>
	  <div class="tb-wrap-l">
		<ul class="top">
		  <#if skuDetail?exists && skuDetail.skuImagesBig?exists>
		  <#list skuDetail.skuImagesBig as image>
			<li><a href="javascript:"><div class="verticalAlign_detail_top"><img src="${image}" alt=""></div></a></li>
		  </#list>
		  </#if>
		</ul>
	  </div>
	  <div class="tb-wrap-r">
		<div class="tb-detail-hd"><h2>${skuDetail.name}</h2></div>
		<div class="price"><em class="present-price">&yen:${skuDetail.priceNew}</em></div>
		<div class="color-size fl"></div>
		<div class="buy fl">
		  <p><h3>购买数量：</h3></p>
		  <div class="amountent">
			<span class="amount">
			  <a href="javascript:" class="left goods_cut" onclick="goods_cut();changePrice();">-</a>
			  <input type="text" class="num amount-num" value="1" onblur="changePrice()" name="number" id="baby-num" />
			  <a href="javascript:" class="right goods_add" onclick="goods_add();changePrice();">+</a>
			</span>
			<em>库存:571件</em>
		  </div>
		  <div class="btn">
			<span class="cart" onclick="cart.addToCart('${skuDetail.selectedSku.skuId?c}',$('#baby-num').val());"><img src="${rc.contextPath}/views/images/cart.png" alt="" class="bcar"></span>
			<span class="buy-now" onclick="buyNowSku('${skuDetail.selectedSku.skuId?c}',$('#baby-num').val());"></span>
		  </div>
		</div>
		<div class="share fl">
		  <span>分享到：</span>
		  <div class="bdsharebuttonbox">
            <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
            <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
		    <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
		    <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
	      </div>
		</div>
	  </div>
	</div>
	<div class="main-wrap">
	  <div class="tabbarbox" id="tabbarbox">
		<ul class="tabbar">
		  <li class="tab selected ggcs"><a href="javascript:">详情与使用</a></li>
		  <li class="last">
			<div class="buycar">
			  <span>&yen:${skuDetail.priceNew}</span>
			  <img src="${rc.contextPath}/views/images/bcar.png" alt="" class="car" />
			</div>
		  </li>
		</ul>
	  </div>
	  <div class="attributes">
		<div class="attributes-list">
		  <ul class="cards_detil_ul">
			<p>商品名称：</p>
			<li>${skuDetail.name}</li>
			<p>类型和功能：</p>
			<li>Fushionbaby兜世宝礼品卡拥有多种面值，支付快捷，可用于购买Fushionbaby兜世宝网站(www.fushionbaby.com)销售的任何商品，是潮妈必备、闺蜜送礼、朋友馈赠的时尚之选。</li>
			<p>使用范围：</p>
			<li>1. Fushionbaby兜世宝礼品卡由上海兜赢国际贸易有限公司发行，可以在www.fushionbaby.com网站上购买任何销售商品及服务。</li>
			<li>2. 礼品卡不记名、不挂失、不兑现。</li>
			<p>激活方式</p>
			<li>购买礼品卡成功后，可在个人中心中点击【礼品卡充值】即可完成一键充值，并在订单结账时选择使用礼品卡即可。</li>
			<li>顾客也可选择订单结账时直接使用礼品卡付款，若礼品卡面值大于消费金额，则剩余的礼品卡金额自动转入账户余额，用于下次购物使用。</li>
			<p>注意事项</p>
			<li>*Fushionbaby兜世宝礼品卡所有设计版权归上海兜赢国际贸易有限公司所有，禁止盗用，违者必究。</li>
		  </ul>
		</div>
	  </div>
	</div>
  </div>
</div>
<#include "/common/nav.ftl" />
<#include "/common/footer.ftl" />
</body>
</html>