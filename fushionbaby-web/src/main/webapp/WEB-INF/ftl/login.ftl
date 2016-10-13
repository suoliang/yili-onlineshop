<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby 兜世宝 母婴用品【登录】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/login.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
	<script src="http://qzonestyle.gtimg.cn/qzone/app/qzlike/qzopensl.js#jsdate=20111201" charset="utf-8"></script>
    <script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="100229030" charset="utf-8"></script>
    <script type="text/javascript"> 
	     if(top!=self){
	         if(top.location != self.location)
	            top.location=self.location; 
	     }
	</script>
</head>
<body>


	<div class="header container">
		<div class="logo">
	 		<h1>
	 			<a href="${rc.contextPath}/index/index.do">
	 				<!--<img src="${rc.contextPath}/login/getWebLoginAd.do" alt="fushion baby" title="fushionbaby">-->
	 				<img src="${rc.contextPath}/views/images/logo.jpg" alt="fushion baby" title="fushionbaby">
	 				<span class="logo-txt">fushionbaby</span>
	 			</a>
	 		</h1>
		</div>
	</div>
	<div class="main container clearfix">
	 	<div class="main-l">
	 	  	<!--<img src="${rc.contextPath}/login/getWebLoginAd.do" alt="fushion baby" title="fushionbaby">-->
	 	  	<input id="login_ad_url" value="${login_ad_url}" type="hidden">
	 	  	<input id="ad_link" value="${ad_link}" type="hidden">
	 	 <a href="#" id="ad_link">	<img src="${rc.contextPath}/views/images/main-l.png" alt="" id="ad_url"></a>
	 	</div>
	 	<div class="main-r">
	 		<div class="main-r-hd">
	 			登录FUSHION BABY
	 		</div>
	 		<div class="main-r-bd">
 				<div class="textbox_ui user">
 					<div class="clearfix">
						<i></i>
 						<input type="text" name="email" placeholder="用户名">
					</div>
					<div class="error" id="show_login_name"><em>*</em><span id="check_login_name"></span></div> 					
 				</div>
 				<div class="textbox_ui pass">	 					
 					<div class="clearfix">
					<i></i>
 					<input type="password" name="password" placeholder="密码">
					</div>
					<div class="error" id="show_login_password"><em>*</em><span id="check_login_password"></span></div> 
 				</div>
 				<p>
 					<a class="fn" style="cursor:pointer;">忘记密码？</a>
 				</p>
 				<div class="btn-wrap">
 					<div class="login-btn">
 						<a style="cursor:pointer;" id="login">立即登录</a>
 					</div>
 					<div class="register-btn">
						<a style="cursor:pointer;" id="register">立即注册</a>
 					</div>
 				</div>
	 		</div>
	 		<div class="main-r-fd">
	 			<p class="main-r-fd-tit">您还可以用以下方式登录FUSHION BABY</p>
	 			<ul>
	 				<li>
	 					<a href="javascript:" id="qq_login_picture" class="link-hd"></a>
	 					<a href="javascript:" class="link-bd" id="qq_login">QQ</a>
	 				</li>
	 				<li class="middle-li">
	 					<a href="javascript:" class="link-hd weixin" id="weixin_login_picture"></a>
	 					<a href="javascript:" class="link-bd">微信</a>
	 					<input type="hidden" value="${wx_redirectUrl}" id="wx_url">
	 					<input type="hidden" value="${wx_appid}" id="wx_appid">
	 				</li>
	 			     <li>
	 					<a href="javascript:" class="link-hd sina" id="sina_login_picture"></a>
	 					<a href="javascript:" class="link-bd">新浪微博</a>
	 				</li>
	 				<li>
						<a href="javascript:" class="link-hd alipay" id="ZFB_login_picture"></a>
						<a href="javascript:" class="link-bd">支付宝</a>
					</li>
	 			</ul>
	 		</div>
	 	</div>
	</div>
	<div class="footer">
	   <div class="footer-b">沪ICP备14049824号 </div>
	</div>
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/login.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/login.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script>
		$(window).load(function(){
			/*登录页调节底部位置*/
			if ($(window).height() > $('body').outerHeight()) {
				$('body').css({'height':$(window).height()});
				$('.footer').css({
					'position':'absolute',
					'bottom':'0'
				})
			};
		})
	</script>
</body>
</html>