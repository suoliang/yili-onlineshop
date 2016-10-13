<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【订单详情】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
</head>
<body>
	<div class="od-main">
		<h2>订单详情</h2>
		<!--<div class="flow-chart"></div> 订单进行状态-->
		<#if orderDetailsDto?exists>
			<ul class="order-info">
				<li>订单编号：<span>${orderDetailsDto.code!''}</span></li>
				<li>下单时间：<span>${orderDetailsDto.create_time!''}</span></li>
				<li class="clearfix">
					<div class="od-status">
						<span>当前状态:</span>
						<span class="pay-over pinker">
							${orderDetailsDto.order_information}
						</span>
						<#if orderDetailsDto.order_status == '5' || orderDetailsDto.order_status == '6' || orderDetailsDto.order_status == '7' || orderDetailsDto.order_status == '8'>
							<span class="acc-time">
								<span>配送信息:</span>
								<span class="dispatch-info">${orderDetailsDto.trans_name!''}</span>
								<span class="ml">快递单号：</span>
								<span class="dispatch-info">${orderDetailsDto.trans_code!''}</span>
							</span>
						</#if>
					</div>
				<#if orderDetailsDto.order_status == '1'>
					<div class="order-ft-l">
						<div class="time-wrap">
							<div class="acc-time">
								<span>
									24小时后订单将自动关闭,请尽快完成支付！
								</span>
							</div>
						</div>
					</div>
				</li>
				<li>
					<a class="btn go-pay" target="_blank" href="${rc.contextPath}/pay/goto_pay_page.do?order_code=${orderDetailsDto.code!''}">去付款</a>
				</li>
				<#elseif orderDetailsDto.order_status == '5' || orderDetailsDto.order_status == '7'>
					<li>
						<input type="hidden" id="orderDetails_id" value="${orderDetailsDto.code}">
						<a class="pay-btn con-rec-btn" style="cursor: pointer;">确定收货</a>
					</li>
				</#if>
				<li class="add-wrap">
					<h4>收货地址</h4>
					<p class="add-info">
						<span class="user">${orderDetailsDto.receiver!''}</span>
						<span class="mob">${orderDetailsDto.receiver_mobile!''}</span>
						<span>
						${orderDetailsDto.province!''}
						${orderDetailsDto.city!''}
						${orderDetailsDto.district!''}
						${orderDetailsDto.address!''}
						</span>
					</p>
				</li>
			</ul>
			<div class="mer-list-wrap">
			<h4>商品清单</h4>
			<table class="order-table evaluate">
				<tbody style="border:0 none;">
					<tr class="title-list">
						<th class="t-title col1">商品</th>
						<th class="t-title col2">单价</th>
						<th class="t-title col3">数量</th>
						<th class="t-title col4">小计</th>
						<th class="t-title col3">说明</th>
					</tr>			
				</tbody>
				<tbody>
				
					<#list orderDetailsDto.items as orderLine >
						<tr class="order-con">
							<td colspan="2" class="con-1">
								<div class="order-goods">
									<!--<span class="icon-gift"></span>-->
									<a href="${rc.contextPath}/product/skuDetail.do?skuId=${orderLine.sku_id?c}" target="_parent">
										<img src="${orderLine.img_path!''}" alt="${orderLine.sku_name!''}">
									</a>
								</div>
								<div class="order-tex">
									<p><a href="${rc.contextPath}/product/skuDetail.do?skuId=${orderLine.sku_id?c}" target="_parent">${orderLine.sku_name!''}</a></p>
									<div class="order-tex-bd">
										<div class="size">尺寸:${orderLine.sku_size!''}</div>
										<div class="color">颜色:${orderLine.sku_color!''}</div>
									</div>
								</div>
							</td>
							<td class="con-list pri">${orderLine.unit_price!''}</td>
							<td class="con-list amount">${orderLine.quantity!''}</td>
							<td class="con-list tot">${orderLine.rowsPriceTotal!''}</td>
							<td class="con-list pri color-error"><!--赠品<br/>优惠金额：&yen; 40--></td>
							<!--<td class="con-list status pinker" colspan="2">-->
								<!--${orderDetailsDto.order_information}-->
							<!--</td>-->				
						</tr>
					</#list>
				</tbody>
			</table>
			<dl class="tick">
				<dt>发票信息:</dt>
				<dd class="tick-info">
					<span>抬头：</span>
					<span>${orderDetailsDto.invoice_title!''}</span>
				</dd>
				<dd class="tick-info">
					<span>类型：</span>
					<span>${orderDetailsDto.invoice_type!''}</span>
				</dd>
			</dl>
		</#if>
		</div>
	</div>
</body>
</html>