<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="yes" name="apple-touch-fullscreen">
		<meta content="telephone=no" name="format-detection">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
		<title>fushionbaby触屏版</title>
		<script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/cssRset.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/style_li.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/public_head.css"/>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/index.js"></script>
		<!--自定义-->
		<script type="text/javascript" src="${rc.contextPath}/wapjs/membercenter/personCenterInfo.js"></script>
	</head>
	<body>
		<!-- header -->
		<div class="public_header fl width100 marginB0">
			<div class="public_header_wrap fl width100">
				<a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
				个人中心
				<a class="public_btn_r disabled" href="javascript:void(0);"></a>
			</div>
		</div>

		<!--用户头像-->
		<section id="headimage">
			<div class="headerbox">
				<img src="" class="touxiang"/>
				<p id="gradeName">幼儿园大班</p>
			</div>
			<div class="headerwenzi">
				<p class="p1">REBECCA LUCAS</p>
				<p class="p2">兜米：<span id="userPoints">3000</span></p>
				<p class="p3"><a href="javascript:signIn();">签到送兜米</a></p>
				<span id="signMsg"></span>
			</div>
		</section>

		<!--管理开始-->
		<section id="list">
				<ul>
					<li class="first">
						<a href="${rc.contextPath}/membercenter/order.do?time=${time?c}">
							<span>全部订单</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li>
						<a href="${rc.contextPath}/membercenter/myMoney.do">
							<span>我的余额和充值</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li>
						<a href="address.html">
							<span>地址管理</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li>
						<a href="collect.html">
							<span>我的收藏</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li>
						<a href="${rc.contextPath}/changePassword/preChangePassword.do">
							<span>修改密码</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li class="lastbd">
						<a href="tel:400-888-8888">
							<span>联系客服</span>
							<span class="arrow"></span>
						</a>
					</li>
					<li class="lastbd">
						<a href="setting.html">
							<span>设置</span>
							<span class="arrow"></span>
						</a>
					</li>
				</ul>
			</section>


		<!--退出登录-->
		<section id="backdl">
			<div class="backtc">
				<a href="javascript:loginOut();"><p>退出登录</p></a>
			</div>
		</section>

		<!--返回顶部   footer-->
      <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>


	</body>
</html>
