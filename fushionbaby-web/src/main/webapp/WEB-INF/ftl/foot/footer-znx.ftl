<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>fushionbaby帮助</title>
		<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
		<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<link rel="stylesheet" href="${rc.contextPath}/views/css/help.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript" src="${rc.contextPath}/views/js/footer.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript">
			var _ContextPath = "${rc.contextPath}";
			$(document).ready(function() {
				$(".search .search-btn").click(function(){
					var searchKey= $(".search-text").val();
					window.location.href= _ContextPath + "/product/search-list.do?searchKey="+encodeURI(encodeURI(searchKey));
				});
			});
		</script>
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
		<!-- site-header end -->
		<div class="container">
			<div class="f-main-l">
				<dl class="footer-links">
					<dt>
					<h4>
					使用帮助
					</h4>
					</dt>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX1"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-novice.html" target="iframepage2" id="ZNX1">新手指南</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX2"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-problem.html" target="iframepage2" id="ZNX2">常见问题</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX3"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-agreement.html" target="iframepage2" id="ZNX3">用户协议</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX3"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-invoice.html" target="iframepage2" id="ZNX3-5">关于发票</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX3-5"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-customer.html" target="iframepage2" id="ZNX4">售后政策</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX5"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-againstfraud.html" target="iframepage2" id="ZNX5">反诈骗公告</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX6"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-com_sug.html" target="iframepage2" id="ZNX6">投诉与建议</a>
					</dd>
				</dl>
				<dl class="footer-links">
					<dt>
					<h4>
					售后支持
					</h4>
					</dt>
					<dd style="margin-top:6px">
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX7"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-aut_gua.html" target="iframepage2" id="ZNX7">正品保障</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX8"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-refund.html" target="iframepage2" id="ZNX8">退款说明</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX9"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-seven.html" target="iframepage2" id="ZNX9">7天无理由退货</a>
					</dd>
				</dl>
				<dl class="footer-links">
					<dt>
					<h4>
					支付方式
					</h4>
					</dt>
					<dd style="margin-top:6px">
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX10"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-online_pay.html" target="iframepage2" id="ZNX10">在线支付</a>
					</dd>
				</dl>
				<dl class="footer-links">
					<dt>
					<h4>
					配送方式
					</h4>
					</dt>
					<dd style="margin-top:6px">
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX11"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-receipt.html" target="iframepage2" id="ZNX11">验货签收</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX12"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-logistics.html" target="iframepage2" id="ZNX12">物流信息</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX13"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-distribution.html" target="iframepage2" id="ZNX13">配送说明</a>
					</dd>
				</dl>
				<dl class="footer-links">
					<dt>
					<h4>
					关于我们
					</h4>
					</dt>
					<dd style="margin-top:6px">
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX14"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-aboutus.html" target="iframepage2" id="ZNX14">了解fushionbaby</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX15"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-joinus.html" target="iframepage2" id="ZNX15">加入我们</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX16"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-contact.html" target="iframepage2" id="ZNX16">联系我们</a>
					</dd>
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX17"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-cooperation.html" target="iframepage2" id="ZNX17">招商合作</a>
					</dd>
					<!-- <dd>
						<span class="subnav-arrow">
												<span class="arrow">
													<em class="front fro-ZNX18"></em>
													<s class="behind"></s>
												</span>
											</span>
						<a href="foot-friend.html" target="iframepage2" id="ZNX18">友情链接</a>
					</dd> -->
					<dd>
					<span class="subnav-arrow">
					<span class="arrow">
					<em class="front fro-ZNX19"></em>
					<s class="behind"></s>
					</span>
					</span>
					<a href="${rc.contextPath}/views/help/foot-promise.html" target="iframepage2" id="ZNX19">fushionbaby承诺</a>
					</dd>
				</dl>
			</div>
			<div class="f-main-r">
				<iframe src="${rc.contextPath}/views/help/foot-novice.html" marginheight="0" marginwidth="0" width="918" frameborder="0" scrolling="no"  name="iframepage2"  height=100% id="iframepage2"  onLoad="iFrameHeight()"></iframe>
				<script type="text/javascript" language="javascript">
				function iFrameHeight() {
				var ifm= document.getElementById("iframepage2");
				var subWeb = document.frames ? document.frames["iframepage2"].document :ifm.contentDocument;
				if(ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
				}
				}
				</script>
			</div>
		</div>
		<#include "/common/footer.ftl" />
		<#include "/common/nav.ftl" />
	</body>
</html>