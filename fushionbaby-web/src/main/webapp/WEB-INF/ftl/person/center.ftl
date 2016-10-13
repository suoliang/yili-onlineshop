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
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/persona-order.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/iSlide.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/scroll.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/centerOrder.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" >
		function jumpMyMemberCenter(){
			window.parent.document.getElementById("iframepage").src = '${rc.contextPath}/membercenter/person.do?time='+new Date().getTime();
		}
		function jumpOrder(){
			window.parent.document.getElementById("iframepage").src = "${rc.contextPath}/membercenter/order.do?time="+new Date().getTime();
		}
		function jumpCollect(){
			window.parent.document.getElementById("iframepage").src = "${rc.contextPath}/collect/collect.do?time="+new Date().getTime();
		}
		function jumpUserInfo(){
			window.parent.document.getElementById("iframepage").src = '${rc.contextPath}/membercenter/user_info.do?time='+new Date().getTime();
		}
		function jumpListAddress(){
			window.location.href = '${rc.contextPath}/membercenter/address.do?time='+new Date().getTime();
		}
		function jumpPassMod(){
			window.parent.document.getElementById("iframepage").src = '${rc.contextPath}/membercenter/pass_mod.do?time='+new Date().getTime();
		}
		function jumpUserCards(){
			window.parent.document.getElementById("iframepage").src = '${rc.contextPath}/membercenter/userCard.do?time='+new Date().getTime();
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
					<button class="list-current if1" target="iframepage" onclick="jumpMyMemberCenter();">我的个人中心</button>
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
					<button target="iframepage" onclick="jumpListAddress();">收货地址管理</button>
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
			<iframe src="${rc.contextPath}/membercenter/person.do" marginheight="0" marginwidth="0" width="918" frameborder="0" scrolling="no"  name="iframepage"  height=100% id="iframepage"  onLoad="iFrameHeight()"></iframe>
			<script type="text/javascript">
   				function iFrameHeight() {
   				    var ifm= document.getElementById("iframepage");
   				    var subWeb = document.frames ? document.frames["iframepage"].document :ifm.contentDocument;
   				        if(ifm != null && subWeb != null) {
   				           ifm.height = subWeb.body.scrollHeight;
   				        }
    			}
    			function jump() {
       				if (location.search == "?true"){
           				document.getElementById("iframepage").src = "${rc.contextPath}/collect/collect.do?time="+new Date().getTime();
           			}
           			if (location.search == "?order"){
           				document.getElementById("iframepage").src = "${rc.contextPath}/membercenter/order.do?time="+new Date().getTime();
           			}
           			if (location.search == "?base"){
           				document.getElementById("iframepage").src = "${rc.contextPath}/membercenter/user_info.do?time="+new Date().getTime();
           			}
           			if (location.search == "?pwd"){
           				document.getElementById("iframepage").src = "${rc.contextPath}/membercenter/pass_mod.do?time="+new Date().getTime();
           			}
  				}
  				jump();
			</script>
		</div>
	</div>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
	<!-- 取消订单弹出窗 -->
	<div class="pop-up-black">
		<div class="pop-up">
			<p class="desc-info">确定要取消此订单？</p>
			<p class="desc-info-btn">
				<input type="button" value="确定" class="pay-btn" id="cancelOrder">
				<input type="button" value="取消" class="pay-btn pop-up-cancel">
			</p>
		</div>
	</div>
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
</div>
</body>
</html>
</@compress>