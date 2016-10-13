<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby 兜世宝 母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/car.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/404-none.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" language="javascript">
		var _ContextPath = "${rc.contextPath}";
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
	
	<!-- 404 begin -->
	<div class="container">
		<div class="not-found">
			<span class="logo-gif"></span>
			<div class="err">
				<span>你的购物车还没有商品</span>
					<em></em>
			</div>
			<div class="content">
				<p>您可以稍后再试，或者 <a href="${rc.contextPath}/index/index.do">返回首页</a>。</p>
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
	<!-- toy end -->
		
	<!-- footer begin -->
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
	<div class="pass-check" id="not_login">
		<div class="close"></div>
		<p class="desc-info">您好，请 <a href="${rc.contextPath}/login/index.do">登录</a></p>
	</div>
	<script type="text/javascript">
		function delShoppingCart(type,skuId){
			$("#get_Type").val(type);
			//$("#get_cartRowsId").val(cartRowsId);
			$("#get_skuId").val(skuId);
		}
		
		function goto_order_check(){
			var url = "${rc.contextPath}/order/goto_order_check.do";
			$.ajax({
				type : "POST",
				url  : url,
				data : "time="+new Date().getTime(),
				async : false,// 同步请求,本次请求完成后,后边的代码才会执行
				dataType : "json",
				success : function(data) {
					if (data == null || data == "" || data == undefined) {
						return;
					}
					if(data.responseCode==0){
						window.location.href="${rc.contextPath}/order/goto_order.do";
					}else if(data.responseCode==201){
						$("#not_login").show();//显示请登录层
					}else{
						alert(data.msg);
					}
				}//end success
			});//end ajax
		}
	</script>
</body>
</html>