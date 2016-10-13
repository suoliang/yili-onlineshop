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
		<script type="text/javascript" src="${rc.contextPath}/wapjs/membercenter/myOrder.js"></script>
	</head>
	<body>
		<!-- header -->
		<div class="public_header fl width100 marginB0">
			<div class="public_header_wrap fl width100">
				<a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
				我的订单
				<a class="public_btn_r disabled" href="javascript:void(0);"></a>
			</div>
		</div>
		<!--top-->
		<section id="my-order_choose">
			<ul>
				<li class="my-order_current">全部订单</li>
				<li>待付款</li>
				<li>待收货</li>
				<li>待评价</li>
			</ul>
		</section>
		<!--商品  全部订单-->
		<section id="my-order_shop">
			<#if pageset??>
				<#list pageset.orderList as order>
					<#if order.items?exists && (order.items?size>0)>
						<#list order.items as orderLine>
							<ul>
								<li>
									<a href="order_to_pay.html">
										<img src="${rc.contextPath}/wap/images/45.gif"/>
										<p>订单号：${order.code}</p><br />
										<p>金额：&yen;<span>${order.total_actual}</span></p><br />
										<p>交易状态：<span>${order.order_information}</span></p><br />
										<p class="width100">订单日期：${order.create_time}</p>
									</a>
									<#if order.order_status == '1'>
										<a href="pay.html"><span class="my-order_pay">立即支付</span></a>
									<#elseif order.order_status == '5' || order.order_status == '7'>
										<span class="my-order_pay my-order_pay_del" onclick="confirmOrder(${order.code})">确认收货</span>
									<#elseif (order.order_status == '6' || order.order_status == '8') && orderLine.is_comment == 'n'>
										<a href="evaluation.html"><span class="my-order_pay">立即评价</span></a>
									<#elseif order.order_status == '6' || order.order_status == '8' || order.order_status == '9' || order.order_status == '10' || order.order_status == '11'>
										<span class="my-order_pay my-order_pay_del" onclick="delOrder(${order.id})">删除订单</span>
									</#if>
								</li>
							</ul>
						</#list>
					</#if>
				</#list>
			</#if>
		</section>
		<!--全部订单结束-->
		<!--待付款-->
		<section id="my-order_shop2">
			<#if pageset??>
				<#list pageset.orderList as order>
					<#if order.order_status == '1'>
						<ul>
							<li>
								<a href="order_to_pay.html">
									<img src="${rc.contextPath}/wap/images/45.gif"/>
									<p>订单号：${order.code}</p><br />
									<p>金额：&yen;<span>${order.total_actual}</span></p><br />
									<p>交易状态：<span>${order.order_information}</span></p><br />
									<p>订单日期：${order.create_time}</p>
								</a>
								<a href="pay.html"><span class="my-order_pay">立即支付</span></a>
							</li>
						</ul>
					</#if>
				</#list>
			</#if>
		</section>
		<!--待付款结束-->
		<!--待收货-->
		<section id="my-order_shop3">
			<#if pageset??>
				<#list pageset.orderList as order>
					<#if order.order_status == '5'>
						<ul>
							<li>
								<img src="${rc.contextPath}/wap/images/45.gif"/>
								<p>订单号：${order.code}</p><br />
								<p>金额：&yen;<span>${order.total_actual}</span></p><br />
								<p>交易状态：<span>${order.order_information}</span></p><br />
								<p>订单日期：${order.create_time}</p>
								<a href="javascript:"><span class="my-order_pay received_btn">确认收货</span></a>
							</li>
						</ul>
					</#if>
				</#list>
			</#if>
		</section>
		<!--待评价-->
		<section id="my-order_shop4">
			<#if pageset??>
				<#list pageset.orderList as order>
					<#if order.items?exists && (order.items?size>0)>
						<#list order.items as orderLine>
							<#if (order.order_status == '6' || order.order_status == '8') && orderLine.is_comment == 'n'>
								<ul>
									<li>
										<a href="order_to_evaluation.html">
											<img src="${rc.contextPath}/wap/images/45.gif"/>
											<p>订单号：${order.code}</p><br />
											<p>金额：&yen;<span>${order.total_actual}</span></p><br />
											<p>交易状态：<span>${order.order_information}</span></p><br />
											<p>订单日期：${order.create_time}</p>
										</a>
										<a href="evaluation.html"><span class="my-order_pay">立即评价</span></a>
									</li>
								</ul>
							</#if>
						</#list>
					</#if>
				</#list>
			</#if>
		</section>
		
		<input type="hidden" id="orderOnlyMarker">
		<!-- 对话框 -->
		<div class="public_modal_backup">
			<div class="public_modal">
				<div class="modal_body"></div>
				<div class="modal_foot">
					<button class="modal_confirm">确定</button>
					<button class="modal_cancel">取消</button>
				</div>
			</div>
		</div>
		
		
		<div class="collect-page">			
			<#if pageset?exists && pageset.orderList?exists && (pageset.orderList?size > 0)	>
						<span class="page-num">共<span> ${pageset.totalPage} </span>页</span>
						
						<#assign previous=(pageset.curPage-1)>  
						<#if previous gt 0> 
							<a href="${rc.contextPath}/membercenter/order.do?cur_page=${pageset.curPage-1}&sid=${sid}&time=${time?c}" class="page-arrow iconfont"></a>
						<#else>
							<span style="color:red" class="page-arrow iconfont"></span>
						</#if>
						<#assign start = FtlConstant.PAGE_START />
						<#assign limit = FtlConstant.PAGE_LIMIT />
						<#if  (pageset.curPage > limit) && (pageset.totalPage gt limit) >
							<#list (pageset.curPage)..(pageset.curPage+limit-1) as i >
								<#if i <= pageset.totalPage  >
								
									<#if i == pageset.curPage>
										<a href="${rc.contextPath}/membercenter/order.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list page-list-current">${i}</a>
									<#else>
										<a href="${rc.contextPath}/membercenter/order.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list">${i}</a>
									</#if>
								
								</#if>
							</#list>
						<#else>
							<#list 1..limit as i>	
								<#if (pageset.totalPage >= i)  >
									<#if i == pageset.curPage>
										<a href="${rc.contextPath}/membercenter/order.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list page-list-current">${i}</a>
									<#else>
										<a href="${rc.contextPath}/membercenter/order.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list">${i}</a>
									</#if>
								</#if>	
							</#list>		
						</#if>
						<#assign last = (pageset.curPage+1)>  
						<#if last <= pageset.totalPage>
							 <a	href="${rc.contextPath}/membercenter/order.do?cur_page=${pageset.curPage+1}&sid=${sid}&time=${time?c}" class="page-arrow iconfont">&#xe604;</a>
						<#else>
							<span style="color:red" class="page-arrow iconfont">&#xe604;</span>	
						</#if>  
						<span class="page-num">第<span> ${pageset.curPage} </span>页</span>
					</div>
				
				</#if>
			
		</div>
		
		<!--返回顶部   footer-->
		<script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
	</body>
</html>