<@compress single_line=true>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/style.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}>" />
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/persona-order.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/checked.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/centerOrder.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" language="javascript">
		function jumpMyMemberCenter(){
			window.location.href = '${rc.contextPath}/membercenter/center.do';
		}
		function jumpOrder(){
			window.location.href = '${rc.contextPath}/membercenter/center.do?order';
		}
		function jumpCollect(){
			window.location.href = '${rc.contextPath}/membercenter/center.do?true';
		}
		function jumpUserInfo(){
			window.location.href = '${rc.contextPath}/membercenter/center.do?base';
		}
		function jumpListAddress(){
			window.location.href = '${rc.contextPath}/membercenter/address.do?time='+new Date().getTime();
		}
		function jumpPassMod(){
			window.location.href = '${rc.contextPath}/membercenter/center.do?pwd';
		}
		function jumpUserCards(){
			window.location.href = '${rc.contextPath}/membercenter/userCard.do?time='+new Date().getTime();
		}
</script>
</head>
<body>
	<!--[if IE 6]>
	<script src="js/iepng.js" type="text/javascript"></script>	
	<script type="text/javascript">
	   EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
	</script>
	<![endif]-->

	<#include "/common/topbar.ftl"/>
	<#include "/common/header.ftl"/>
	<div class="main container clearfix">
		<div class="main-l">
			
			<dl>
				<dt>个人中心</dt>
				<dd>
					<button class="if1" target="iframepage" onclick="jumpMyMemberCenter();">我的个人中心</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front front-current"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
				<dd>
					<button class="if2" target="iframepage" onclick="jumpOrder();">我的订单</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
				<dd>
					<button target="iframepage" onclick="jumpCollect();" class="if3">我的收藏</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
			</dl>
			<dl>
				<dt>个人设置</dt>
				<dd>
					<button onclick="jumpUserCards();" target="iframepage">礼品卡/余额</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
				<dd>
					<button onclick="jumpUserInfo();" target="iframepage">用户信息修改</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
				<dd>
					<button class="list-current" target="iframepage" onclick="jumpListAddress();">收货地址管理</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
				<dd>
					<button target="iframepage" onclick="jumpPassMod();">修改密码</button>
					<span class="subnav-arrow">
	 					<span class="arrow">
	 						<em class="front"></em>
	 						<s class="behind"></s>
	 					</span>
	 				</span>
				</dd>
			</dl>
		</div>
		<div class="main-r">		
			<div id="address-page">
				<#include "/person/add_address.ftl" />
			</div>
		</div>
	</div>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
<div class="pop-up fl-order">
	<div class="close"></div>
	<p class="desc-info" style="cursor: pointer;">商品已收到 请确定收货</p>
	<button class="pay-btn con-ec"  id="confirm_getorder" style="cursor: pointer;">确定收货</button>
</div>
<div class="pop-up de-order">
	<div class="close"></div>
	<p class="desc-info">确定要删除此订单？</p>
	<p class="desc-info-btn">
		<button id="pay_btn_enter" class="pay-btn enter del_evaluateOrder" style="cursor: pointer;">确定</button>
		<button class="pay-btn con-ec escer" style="cursor: pointer;">取消</button>
	</p>
	<script type="text/javascript" src="${rc.contextPath}/web-js/address/address.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</div>
</body>
</html>
</@compress>