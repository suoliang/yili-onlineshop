<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 兜世宝 母婴用品【注册】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/register.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
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
				<input type="hidden" id="reg_who" value="ph" />
				<input type="hidden" id="register_verification"/>
					<h3>
						<span class="hav">已有FUSHIONBABY账号？</span>
						<a href="javascript:clickLogin();" class="land">立即登录</a>
					</h3>
					<div class="enroll container">
						<a href="javascript:reg_mobile();" class="enroll-now current">
							手机号注册
							<em><img src="${rc.contextPath}/views/images/enroll.png" alt=""></em>
						</a>
						<a href="javascript:reg_email();" class="enroll-now ">
							邮箱注册
							<em><img src="${rc.contextPath}/views/images/enroll.png" alt=""></em>
						</a>
					</div>
					<div class="list-item">
						<ul class="ph">
							<li class="item-all">
								<div class="clearfix">
									<span class="list-label">手机号码</span>
									<input id="register_mobile" type="text" class="textshort" placeholder="请输入您的手机号码">
								</div>
								<div id="show_register_mobile" class="error"><em>*</em><em id="check_register_mobile"></em></div>
							</li>
							<li class="item-all">
								<span class="list-label">验&ensp;证&ensp;码</span>
								<div class="clearfix">
									<input style="cursor: pointer;" type="button" id="get_sms_code" onclick="dotime(this)" class="code" value="获取验证码">
									<input id="register_smscode" type="text" class="textshort yzm" placeholder="请输入验证码">
								</div>
								<div id="show_register_smscode" class="error"><em>*</em><em id="check_register_smscode"></em></div>
							</li>
							<li class="item-all">
								<div class="clearfix">
									<span class="list-label">设置密码</span>
									<input id="register_setpassword" type="password" class="textshort" placeholder="输入密码">
								</div>
								<div id="show_register_setpassword" class="error"><em>*</em><em id="check_register_setpassword"></em></div>
							</li>
							<li class="item-all">
								<div class="clearfix">
									<span class="list-label">确认密码</span>
								<input id="register_confirmpassword" type="password" class="textshort" placeholder="请再次输入密码">
								</div>
								<div id="show_register_confirmpassword" class="error"><em>*</em><em id="check_register_confirmpassword"></em></div>							
							</li>
						</ul>
						<ul class="po" style="display:none">
							<li class="item-all">
								<div class="clearfix">
									<span class="list-label">电子邮箱</span>
									<input id="register_email" type="text" class="textshort" placeholder="输入电子邮箱">
								</div>
								<div id="show_register_email" class="error"><em>*</em><em id="check_register_email"></em></div>						
							</li>
							
							<li class="item-all">							
								<div class="clearfix">
									<span class="list-label">设置密码</span>
									<input id="register_setEmailPassword" type="password" class="textshort" placeholder="输入密码">
								</div>
								<div id="show_register_setEmailPassword" class="error"><em>*</em><em id="check_register_setEmailPassword"></em></div>
							</li>
							<li class="item-all">								
								<div class="clearfix">
									<span class="list-label">确认密码</span>
									<input id="register_confirmEmailPassword" type="password" class="textshort" placeholder="请再次输入密码">
								</div>
								<div id="show_register_confirmEmailPassword" class="error"><em>*</em><em id="check_register_confirmEmailPassword"></em></div>
							</li>
							<li class="item-all">					
								<div class="clearfix">
									<span class="list-label">验&ensp;证&ensp;码</span>
									<input id="register_emailcode" type="text" class="textshort yzm" placeholder="请输入右边的数字">
									<img id="change_picture" src="${rc.contextPath}/RandomPicture" style="cursor: pointer;" onclick="changeImage('change_picture')">
									<a href="javascript:changeImage('change_picture')">换一张</a>
								</div>
								<div id="show_register_emailcode" class="error"><em>*</em><em id="check_register_emailcode"></em></div>
							</li>
						</ul>
						
					</div>
					<input type="button" class="list-item-btn argee-btn" id="agree_register" value="同意用户协议并注册"/>
				</form>
			</div>
		</div>
		<div class="protocol">
			<p>
				点击“立即注册”，即表示您同意并且愿意遵守FUSHIONBABY<a href="javascript:">用户协议</a>和<a href="javascript:">隐私政策</a>。
			</p>
		</div>
	</div>
	<div class="pop-up ar-wrap">
		<div class="close"></div>
		<p class="desc-info">恭喜您，已注册成功<span id="go" >5</span>秒后自动跳转到个人中心</p>
		<a href="javascript:jumpMemberCenter();" class="pay-btn" style="cursor: pointer;">确定</a>	
	</div>
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/register.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</body>
</html>