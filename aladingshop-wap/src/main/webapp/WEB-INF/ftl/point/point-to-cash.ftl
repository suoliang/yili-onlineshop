<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">	
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="#FF5400" /><!--改变顶部状态条颜色-->	
    <title>我能兑换</title>
    <script src="${rc.contextPath}/static/shop/js/min/mui.min.js"></script>
    <link href="${rc.contextPath}/static/shop/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/css/style1.css"/>
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
    </script>
</head>
<body id="point-reward">
	<#if type == ''>
		<header class="mui-bar mui-bar-nav">
		    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" href=""></a>
		    <h1 class="mui-title">我能兑换</h1>
		</header>
	</#if>
	<div class="mui-content ">
	    <div class="enable-exchange">	
		    <ul class="mui-table-view mui-grid-view bgcolor-gray">		    	
		        <#list exChangeList as changeList>
		        	<li class="mui-table-view-cell mui-media mui-col-xs-6">
			        	<div class="list-li-bg">
				            <a href="${rc.contextPath}/integral/pointSkuDetail?skuCode=${changeList.skuCode}&sid=${user.sid}">
				                <img class="mui-media-object " src="${changeList.imgPath!''}">
				                <div class="mui-media-body ">
				                	${changeList.skuName!''}
				                </div>
				                <div class="mui-media-body color-red">
				                	积分${changeList.needEpoint?c}
				                </div>
				            </a>
			            </div>
			        </li>
		        </#list>
		    </ul>
		</div>
	</div>
	
</body>
</html>
