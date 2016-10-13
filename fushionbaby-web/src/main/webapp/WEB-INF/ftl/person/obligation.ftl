<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【待付款】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
	<style>
		.order-info .send {background-position: -34px -118px; color: #0089A8;}
	</style>
	<!--自定义-->
	<script type="text/javascript" >
		var _ContextPath = "${rc.contextPath}";
	</script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/myOrder.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</head>
<body>
	<div class="order-main">
		<div class="main-r-bd">
			<dl>
				<dd>
					<#if !pageset?exists>
					您还未登录或订单列表显示有误，请<a href='${rc.contextPath}/login/index.do' target='_parent' style='color:red;cursor: pointer;'>重新登录</a>
					</#if>
				</dd>
				<dd>
					<div class="order-info">
						<a class="pay-wrap" href="javascript:gotoAllOrder();">
							<div class="pep-pic pic-info pay">
							</div>
							<p class="order-info-desc">全部订单</p>
						</a>
					</div>
				</dd>
				<dd>
					<div class="order-info">
						<a class="send-wrap" href="javascript:gotoObligation();">
							<div class="pep-pic pic-info send">
							</div>
							<p class="order-info-desc send">待付款</p>
						</a>
					</div>
				</dd>
				<dd>
					<div class="order-info">
						<a class="sended-wrap" href="javascript:gotoReceive();">
							<div class="pep-pic pic-info sended">
							</div>
							<p class="order-info-desc">待收货</p>
						</a>
					</div>
				</dd>
				<dd>
					<div class="order-info">
						<a class="discuss-wrap" href="javascript:gotoEvaluate();">
							<div class="pep-pic pic-info discuss">
							</div>
							<p class="order-info-desc">待评价</p>
						</a>
					</div>
				</dd>
			</dl>
		</div>
		<table class="order-table">
			<tbody>
				<tr class="title-list">
					<th class="col0"></th>
					<th class="t-title col1">商品</th>
					<th class="t-title col2">单价(元）</th>
					<th class="t-title col3">数量</th>
					<th class="t-title col4">总计（元）</th>
					<th class="t-title col5">订单状态</th>
					<th class="t-title col6">操作</th>
				</tr>				
			</tbody>
			<#if pageset?exists>
				<#list pageset.orderList as order>
					<tbody class="empty">
						<tr>
							<td colspan="7"></td>
						</tr>
					</tbody>
					<tbody>
						<tr class="order-list">
							<td colspan="6">
								<span class="order-oder-num">
									订单编号：${order.code}
								</span>
								<span class="order-oder-time">
									下单日期：${order.create_time}
								</span>
							</td>
							<td class="order-detail"></td>
						</tr>
					<#list order.items as orderLine>
						<tr class="order-con">
							<td colspan="2" class="con-1">
								<div class="order-goods">
									<a href="${rc.contextPath}/product/skuDetail.do?skuId=${orderLine.sku_id?c}" target="_parent">
										<img src="${orderLine.img_path!''}" alt="${orderLine.sku_name!''}">
									</a>
								</div>
								<div class="order-tex">
									<p><a href="${rc.contextPath}/product/skuDetail.do?skuId=${orderLine.sku_id?c}" target="_parent">${orderLine.sku_name!''}</a></p>
									<div class="order-tex-bd">
										<div class="size">尺寸:${orderLine.sku_size}</div>
										<div class="color">颜色:${orderLine.sku_color}</div>
									</div>
								</div>
							</td>
							<td class="con-list pri">${orderLine.unit_price}</td>
							<td class="con-list amount">${orderLine.quantity}</td>
							<td class="con-list tot">${orderLine.rowsPriceTotal}</td>
							<td class="con-list status status-hold">
								${order.order_information}
							</td>
							<td class="con-list order-detail">
								<#if orderLine_index == 0>
									<a href="${rc.contextPath}/order/orderDetail.do?order_code=${order.code}&time=${time?c}" target="iframepage">订单详情</a>
								</#if>
							</td>
						</tr>
					</#list>
					</tbody>
						<tr class="order-ft">
							<td colspan="7">
								<div class="order-ft-l">
									<div class="time-wrap">
										24小时后订单将自动关闭,请尽快完成支付！
									</div>
								</div>
								<div class="order-ft-r">
									<a class="pay-btn go-pay" target="_blank" href="${rc.contextPath}/pay/goto_pay_page.do?order_code=${order.code!''}">去付款</a>
									<span onclick="cancelOrder(${order.code})">
										<a class="pay-btn cancel-order" href="javascript:void(0);">取消订单</a>
									</span>
									<div class="act-pay">
										<span class="amount">实付款：</span>
										<span class="tot">&yen;</span>
										<span class="tot">${order.total_actual}</span>
									</div>
								</div>
							</td>
						</tr>
				</#list>
			</#if>
		</table>
		<input type="hidden" id="order_code"/>
	</div>
	<div class="collect-page-wrap ord-det clearfix">
		<div class="collect-page">			
			<#if pageset?exists && pageset.orderList?exists && (pageset.orderList?size > 0)	>
						<span class="page-num">共<span> ${pageset.totalPage} </span>页</span>
						
						<#assign previous=(pageset.curPage-1)>  
						<#if previous gt 0> 
							<a href="${rc.contextPath}/membercenter/obligation.do?cur_page=${pageset.curPage-1}&sid=${sid}&time=${time?c}" class="page-arrow iconfont"></a>
						<#else>
							<span style="color:red" class="page-arrow iconfont"></span>
						</#if>
						<#assign start = FtlConstant.PAGE_START />
						<#assign limit = FtlConstant.PAGE_LIMIT />
						<#if  (pageset.curPage > limit) && (pageset.totalPage gt limit) >
							<#list (pageset.curPage)..(pageset.curPage+limit-1) as i >
								<#if i <= pageset.totalPage  >
								
									<#if i == pageset.curPage>
										<a href="${rc.contextPath}/membercenter/obligation.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list page-list-current">${i}</a>
									<#else>
										<a href="${rc.contextPath}/membercenter/obligation.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list">${i}</a>
									</#if>
								
								</#if>
							</#list>
						<#else>
							<#list 1..limit as i>	
								<#if (pageset.totalPage >= i)  >
									<#if i == pageset.curPage>
										<a href="${rc.contextPath}/membercenter/obligation.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list page-list-current">${i}</a>
									<#else>
										<a href="${rc.contextPath}/membercenter/obligation.do?cur_page=${i?c}&sid=${sid}&time=${time?c}" class="page-list">${i}</a>
									</#if>
								</#if>	
							</#list>		
						</#if>
						<#assign last = (pageset.curPage+1)>  
						<#if last <= pageset.totalPage>
							 <a	href="${rc.contextPath}/membercenter/obligation.do?cur_page=${pageset.curPage+1}&sid=${sid}&time=${time?c}" class="page-arrow iconfont">&#xe604;</a>
						<#else>
							<span style="color:red" class="page-arrow iconfont">&#xe604;</span>	
						</#if>  
						<span class="page-num">第<span> ${pageset.curPage} </span>页</span>
					</div>
				
				</#if>
			
		</div>
	</div>
	<script>
		<!--会员取消订单-->
		function cancelOrder(orderCode){
			$("#order_code").val(orderCode);
		}
		
		$(function(){
			$(".cancel-order").click(function(){
				$(".pop-up-black",parent.document).fadeIn();
			});
			$(".pop-up-cancel",parent.document).click(function(){
				$(".pop-up-black",parent.document).fadeOut();
			});
		})
	</script>
</body>
</html>