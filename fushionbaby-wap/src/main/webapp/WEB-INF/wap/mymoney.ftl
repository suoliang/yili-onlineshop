<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="yes" name="apple-touch-fullscreen">
		<meta content="telephone=no" name="format-detection">
		<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no" name="viewport" id="viewport" />
		<title>fushionbaby触屏版</title>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/cssRset.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/style_li.css"/>
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/wap/css/public_head.css"/>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/wap/js/index.js"></script>
	</head>
	<body class="beijing">
		<!-- header -->
		<div class="public_header fl width100 marginB0">
			<div class="public_header_wrap fl width100">
				<a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
				我的余额和充值
				<a class="public_btn_r disabled" href="javascript:void(0);"></a>
			</div>
		</div>
		<!--内容-->
		<section id="mymoney">
			<div class="mymoneytop">
				<div class="headerbox">
					<img src="" class="touxiang"/>
				</div>
				<div class="headerwenzi">
					<p class="p1">REBECCA LUCAS</p>
				</div>
			</div>
			<div class="mymoneyceter">
				<ul>
					<li class="first">
						<a href="javascript:">
							<span>兜米</span>
							<span class="mymoneycetersome">${epoints}米</span>
						</a>
					</li>
					<li class="lastbd2">
						<a href="javascript:">
							<span>我的余额</span>
							<span class="mymoneycetersome">￥ ${availableMoney}元</span>
						</a>
					</li>
				</ul>
			</div>
			<div id="backdl2">
				<div class="backtc2">
					<p><a href="${rc.contextPath}/membercenter/deposit.do">使用礼品卡充值</a></p>
				</div>
			</div>
		</section>
		<!--返回顶部   footer-->
		<script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
	</body>
</html>