<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 兜世宝 母婴用品【找回密码】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/confirm-password.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
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
					<div class="list-wrap">
						<div class="n-pass-wrap">
							<input id="new_password" type="password" class="text" placeholder="输入新的密码">
							<div class="error" id="show_new_password"><em>*</em><span id="check_new_password"></span></div>
						</div>
						<div class="n-pass-wrap">
							<input id="confirm_password" type="password" class="text" placeholder="确认新的密码">
							<div class="error" id="show_confirm_password"><em>*</em><span id="check_confirm_password"></span></div>
						</div>
					</div>
					<div class="list-item">
						<div ><input class="list-item-btn" type="button" onclick="getbackPassword();" value="确定"></div>
						<div class="popup">
							<i class="bee">
								<img src="${rc.contextPath}/views/images/popup.png" alt="">
							</i>
							<span class="tips">密码修改成功</span>
				 			<a class="list-item-btn" href="javascript:toLogin();">确认</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!-- 自定义 -->
	<script type="text/javascript" src="${rc.contextPath}/web-js/forgetPassword.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</body>
</html>