<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>找回密码 - Fushionbaby</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/back-password.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
</head>
<body>
	<div class="content">
		<div class="password container">
			<div class="logo">
				<a href="${rc.contextPath}/index/index.do">
					<img src="${rc.contextPath}/views/images/logo.png" alt="">
				</a>
			</div>
			<div class="list">
				<form action="">
					<h3>找回FushionBaby密码</h3>
					<input id="back_password" type="text" class="text" placeholder="请输入手机号码或者邮箱">
					<div id="show_back_password" class="error"><em>*</em><em id="check_back_password"></em></div>
					<div class="list-item">
						<input id="back_password_code" type="text" class="textshort" placeholder="请输入验证码" >
						<input style="cursor: pointer;" type="button" onclick="getBackPassword(this);" class="code" value="获取验证码" />
						<div id="show_back_password_code" class="error"><em>*</em><em id="check_back_password_code"></em></div>
					</div>
					<div class="list-item">
						<input type="button" class="list-item-btn" onclick="nextStep();" value="下一步">
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!-- 自定义 -->
	<script type="text/javascript" src="${rc.contextPath}/web-js/forgetPassword.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</body>
</html>