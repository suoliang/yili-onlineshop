<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<meta name="format-detection" content="telephone=no">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="apple-mobile-web-app-status-bar-style" content="#FF5400" />
		<!--改变顶部状态条颜色-->
		<title>兑换记录</title>
		<script src="${rc.contextPath}/static/shop/js/min/mui.min.js"></script>
		<link href="${rc.contextPath}/static/shop/css/mui.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/css/style1.css" />
		<script type="text/javascript" charset="UTF-8">
			mui.init();
		</script>
	</head>

	<body>
		<#if type??>
		<#if type == ''>
			<header class="mui-bar mui-bar-nav">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href=""></a>
				<h1 class="mui-title">兑换记录</h1>
			</header>
		</#if>
		</#if>
		<div class="mui-content">
			<#if epointRecords??>
			<#list epointRecords as record>
				<div class="mB20">
					<div class="mui-table-view mui-table-view-cell">
						<p>兑换时间：
							<time>${record.exChangeTime}</time> <span class="mui-pull-right">使用积分:${record.usePoint}</span></p>
					</div>
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a href="javascript:;">
								<div class="mui-border mui-pull-left mR20">
									<img class="mui-media-object " src="${record.imgPath}">
								</div>
								<div class="mui-media-body">
									<p class='mui-ellipsis'>${record.skuName}</p>
									<p class="mT20"><span class="color-red big">积分：${record.usePoint}</span><span class="mui-pull-right">数量：${record.quantity}</span></p>
								</div>
							</a>
						</li>
					</ul>
				</div>
			</#list>
			</#if>
		</div>

	</body>

</html>