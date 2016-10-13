<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    <script src="${rc.contextPath}/static/shop/js/min/mui.min.js"></script>
    <link href="${rc.contextPath}/static/shop/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/css/style1.css"/>
    <script src="${rc.contextPath}/static/shop/farmwork/jquery/jquery-2.1.3.min.js"></script><!-- 通用jQuery JS -->
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
    </script>
    <style>
    	.mui-table-view:before {
		    top: 0px;
		}
		#item2 img{width: 100%;height: auto;float:left;}
	</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title">
	    	<div id="segmentedControl" class="mui-segmented-control">
		    	<a href="#item1" class="mui-control-item mui-active" style="text-align:right;padding-right:5px;">商品介绍</a>
		    	<a href="#item2" class="mui-control-item" style="text-align:left;padding-left:5px;">图文详情</a>
	    	</div>
	    </h1>
	</header>
	
	<div class="mui-content mB100">
		<div id="item1" class="mui-control-content mui-active">
			<div id="slider" class="mui-slider" >
				<div class="mui-slider-group mui-slider-loop">
					<#if result??>
					<#if result.skuImages?exists>
						<#list result.skuImages as img>
							<#if img_index == 3>
								<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
								<div class="mui-slider-item mui-slider-item-duplicate">
									<a href="#">
										<img src="${img}">  <!--第四张-->
									</a>
								</div>
							</#if>
							
	            			<#if img_index ==0><#assign firstImage=img /></#if>
	            			<#if img_index gt 3><#break></#if>
	            			<div class="mui-slider-item">
								<a href="#">
									<img src="${img}">
								</a>
							</div>
	                		
							<#if img_index==0>
								<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
								<div class="mui-slider-item mui-slider-item-duplicate">
									<a href="#">
										<img src="${img}"> <!--第一张-->
									</a>
								</div>
							</#if>
						</#list>
					</#if>
					</#if>
				</div>
				<div class="mui-slider-indicator">
					<div class="mui-indicator mui-active"></div>
					<div class="mui-indicator"></div>
					<div class="mui-indicator"></div>
					<div class="mui-indicator"></div>
				</div>
			</div>		
			<div class="mui-table-view">
				<div class="mui-table-view-cell">
					<#if result??>
						<p class="color-black">${result.name!''}</p>
						<p>原价：<del>&yen; ${result.priceOld!''}</del></p>
						<div class="hot">积分：${result.priceIntegral!''}</div>
					</#if>
				</div>
			</div>
			<div class=" mui-table-view mT20 mui-table-view-cell">
				<big class="mui-navigate-right">商品评价</big>
				<#if mecoment??>
				<#if mecoment.allComment?exists && mecoment.allComment?size&gt;0>
					<#list mecoment.allComment as comment>
						<#if comment_index == 0>
							<p>评分：
								<#assign score= 5 - comment.score />
			                	<#if comment.score gt 0>
			                    	<#list 1..comment.score as row>
			                        	<span class="mui-icon mui-icon-star-filled hot"></span>
			                        </#list>
			                    </#if>   
			                    <#if score gt 0>
			                        <#list 1..score as row>
			                        	<span class="mui-icon mui-icon-star-filled hot"></span>
			                        </#list>
			                    </#if>
							</p>
							<p>评论：${comment.commentContent}</p>
						</#if>
					</#list>
				<#else>
				暂无评论
				</#if>
				</#if>
			</div>
		</div>
		<div id="item2" class="mui-control-content">
			<#if result??>
	    	<#if result.graphicDetails?exists && result.graphicDetails?size&gt;0>
                <!-- 图片详情 -->
            	<#list result.graphicDetails as grap>
            		<img src="${grap.get('url')}" title="">
            	</#list>
            </#if>
	    	</#if>
		</div>
	</div>
	<div class="mui-bar mui-bar-tab">
		<button class="mui-btn  mui-btn-red mui-btn-big mui-btn-block" onclick="atOnceReward('${sid}','${skuCode!''}');">立即兑换</button>
	</div>
	
</body>
</html>

<script type="text/javascript" charset="utf-8">
	mui.init({
			swipeBack:true //启用右滑关闭功能
		});
	var gallery = mui('.mui-slider');
		gallery.slider({
		  interval:3000//自动轮播周期，若为0则不自动播放，默认为0；
		});	
		
	/** 去结算*/
	function atOnceReward(sid,skuNo){
		var url = "${rc.contextPath}/integral/goIntegralCheck";
		$.post(url,{
			sid:sid,
			skuCode:skuNo,
			time : new Date().getTime()},
			function(data){
				if(data.responseCode==0){
					window.location.href="${rc.contextPath}/integral/goIntegralConfirm?skuCode=${skuCode}&sid=${sid}&time="+new Date().getTime();
				}else if(data.responseCode==201 || data.responseCode==300){
					alert(data.msg);
				}else{
					alert(data.msg);
				}
		});
	}
				
</script>