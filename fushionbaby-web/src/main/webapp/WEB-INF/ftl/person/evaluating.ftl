<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="renderer" content="webkit">
		<title>【商品评价】- Fushionbaby</title>
		<link href="${rc.contextPath}/views/images/favicon.ico" type="image/x-icon" rel="shortcut icon">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}"">
		<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/envaluting.css?v=${EnvironmentConstant.DEPLOY_VERSION}"">
		<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}""></script>
	    <script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/memberComment.js?v=${EnvironmentConstant.DEPLOY_VERSION}""></script>
	    <script type="text/javascript" >
		      var _ContextPath = "${rc.contextPath}";
	     </script>
	</head>
	<body>
		<!--[if IE 6]>
		<script src="${rc.contextPath}/views/js/iepng.js?v=${EnvironmentConstant.DEPLOY_VERSION}"" type="text/javascript"></script>
		<script type="text/javascript">
		EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
		</script>
		<![endif]-->
		
		<#include "/common/topbar.ftl"/>
	    <#include "/common/header.ftl"/>
		<div class="container">
			<!-- 面包屑导航 开始 -->
			<div class="breadcrumb_nav">
				<a href="javascript:goToIndex();">首页</a>
				<i class="fa fa-angle-double-right"></i>
				<a href="javascript:goToPersonCenter();">个人中心</a>
				<i class="fa fa-angle-double-right"></i>
				<a href="javascript:goToAllOrder();">我的订单</a>
				<i class="fa fa-angle-double-right"></i>
				<a href="#">商品评价</a>
			</div>
			<!-- 面包屑导航 结束 -->
			<!-- 评价 开始 -->
			<div class="envaluating_wrap fl">
				<div class="envaluating_left fl">
				 <form id="comment_form" method="post" enctype="multipart/form-data">
					<div class="verticalAlign_envaluating">
						<a href="${rc.contextPath}/product/skuDetail.do?skuId=${soSoLine.skuId?c}" target="_parent">
								<#if orderLineDto?exists>
									<img src="${orderLineDto.img_path!''}"/>
								</#if>
							</a>
					</div>
					<p><a href="${rc.contextPath}/product/skuDetail.do?skuId=${soSoLine.skuId?c}" target="_parent">${soSoLine.skuName!''}</a></p>
					<span>已有<#if orderLineDto?exists>${orderLineDto.commentCount!''}</#if>人评论</span>
				</div>
				<div class="envaluating_right fr">
					<h3><a href="javascript:">${soSoLine.skuName!''}</a></h3>
					<div class="envaluating_rank width100 fl">
						<span>评星：</span>
						<input id="envaluating_rank_num" name="score" type="hidden" value="0">
						<ul class="rank_ul">
							<li class="unstar"></li>
							<li class="unstar"></li>
							<li class="unstar"></li>
							<li class="unstar"></li>
							<li class="unstar"></li>
						</ul>
					</div>
					<div class="envaluating_textarea width100 fl">
						<span>评论：</span>
						<textarea name="commentContent" id="content" cols="30" rows="4" placeholder="亲，请分享此商品的使用感受"></textarea>
					</div>
					<div class="envaluating_share width100 fl">
						<span>晒图：</span>
						<div id="filelist" class="fl"></div>
				    <a href="javascript:selectImage()" class="envaluating_share_btn"><i></i><b></b></a>
				    <div class="img_wrap">
				    	<input type="file" onchange="previewImage(this)" />
				    	<input type="file" onchange="previewImage(this)" />
				    	<input type="file" onchange="previewImage(this)" />
				    	<!--
				    	<input type="file" onchange="previewImage(this)" />
				    	<input type="file" onchange="previewImage(this)" />
				       -->
				    </div>
					</div>
					
						<#if soSoLine?exists>
								<input type="hidden" id="skuCode" name="skuCode" value="${soSoLine.skuCode!''}"/>
								<input type="hidden" id="skuColor" name="skuColor" value="${soSoLine.color!''}"/>
								<input type="hidden" id="skuSize" name="skuSize" value="${soSoLine.size!''}"/>
								<input type="hidden" id="soCode" name="soCode" value="${soSoLine.soCode!''}"/>
								<input type="hidden" id="soLineId" name="soLineId" value="${soSoLine.id?c}"/>
								<input type="hidden" id="skuId" name="skuId" value="${soSoLine.skuId?c}"/>
						</#if>
	                 <form>
					<button class="envaluating_btn" onclick="submitComment();">发表评价</button>
				</div>
			</div>
			<!-- 评价 结束 -->
		</div>
		<script src="${rc.contextPath}/views/js/jqthumb.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}""></script>
		<script type="text/javascript" src="${rc.contextPath}/views/js/envaluating.js?v=${EnvironmentConstant.DEPLOY_VERSION}""></script>
		
		<script>
			    function validate() {
		            return true;
		        };
		    function submitComment(){
		        for (i=0;i<$('.img_wrap input').length;i++){
					$('.img_wrap input').eq(i).attr({'name':'files'});
				}
				if ($('#envaluating_rank_num').val() == 0) {
					alert('请先给此商品评分');
				} else if ($('.envaluating_textarea textarea').val()=="") {
					alert('请填写您的使用感受');
				} else {
				    if(validate()==false) {
		                return false;
		                }
					$("#comment_form").attr("action","${rc.contextPath}/memberComment/submitComment.do");
					$("#comment_form").submit();
				}		
			}
		</script>
	<#include "/common/footer.ftl"/>
	<#include "/common/nav.ftl"/>
	</body>
</html>