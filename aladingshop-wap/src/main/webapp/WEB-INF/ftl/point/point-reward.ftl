<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">	
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="#FF5400" /><!--改变顶部状态条颜色-->	
    <title>积分商城</title>
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
		    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		    <h1 class="mui-title">积分商城</h1>
		</header>
	</#if>
	<div class="mui-content ">
	    <div class="mui-text-center point-reward-title1">
	    	我的积分：<span class="hot">${member.epoints?c}</span>
	    </div>
	    <div class="mT10 hot-exchange">
	    	<div class="mui-text-center point-reward-title2">积分菜单</div>
	    	<ul class="mui-table-view mui-grid-view">
	    		<li class="mui-table-view-cell mui-media mui-col-xs-4">
	    			<a href="${rc.contextPath}/integral/exChange?sid=${user.sid}&type=${type}">
	    				<img class="mui-media-object" style="width:80%" src="${rc.contextPath}/static/shop/picture/to_cash.png">
                      	<div class="mui-media-body">我能兑换</div>
	    			</a>
	    		</li>
	    		<li class="mui-table-view-cell mui-media mui-col-xs-4">
	    			<a href="${rc.contextPath}/integral/jumpToRule">
	    				<img class="mui-media-object" style="width:80%" src="${rc.contextPath}/static/shop/picture/points_rule.png">
                      	<div class="mui-media-body">积分规则</div>
	    			</a>
	    		</li>
	    		<li class="mui-table-view-cell mui-media mui-col-xs-4">
	    			<a href="${rc.contextPath}/integral/epointRecord?sid=${user.sid}&type=${type}">
	    				<img class="mui-media-object" style="width:80%" src="${rc.contextPath}/static/shop/picture/cash_record.png">
                      	<div class="mui-media-body">兑换记录</div>
	    			</a>
	    		</li>
	    	</ul>
	    </div>
	    <div class="mT10 enable-exchange">
	    	<div class="mui-text-center point-reward-title2 color-blue" >兑换推荐</div>	
		    <ul class="mui-table-view mui-grid-view bgcolor-gray">		    	
		       <#list sellList as sellList>
		        <li class="mui-table-view-cell mui-media mui-col-xs-6">
		        	<div class="list-li-bg">
			            <a href="${rc.contextPath}/integral/pointSkuDetail?skuCode=${sellList.skuCode}&sid=${user.sid}">
			                <img class="mui-media-object " src="${sellList.imgPath!''}">
			                <div class="mui-media-body ">
			                	${sellList.skuName!''}
			                </div>
			                <div class="mui-media-body color-red">
			                	积分${sellList.needEpoint?c}
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