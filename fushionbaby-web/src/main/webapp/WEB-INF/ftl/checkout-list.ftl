<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 兜世宝 母婴用品【支付页面】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/checked.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" language="javascript">
		var _ContextPath = "${rc.contextPath}";
		function selectPay(pay_type, url){
			$('#paymentType').val(pay_type);
			$('#order_pay').attr("action", url);
		}
		$(function(){
			$(".add-ages-l li").click(function(){
				var address_items = $(this).find("input[name='address_id']");
				if(address_items != undefined && address_items !=null && address_items.length>0){
					var address_id = address_items[0].value;
					var area_code = $(this).find("#updateProvinceId"+address_id).attr('pvalue');
					
					$("#address_id").val(address_id);
					$("#area_code").val(area_code);
					
				}
			})
			
			address_onload();
		});
		
		function backCartList(){
			window.location.href="${rc.contextPath}/cart/cartList.do?time="+new Date().getTime();
		}
	</script>
</head>
<body>
<!--[if IE 6]>
<script src="js/iepng.js" type="text/javascript"></script>	
<script type="text/javascript">
   EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
</script>
<![endif]-->
<input type="hidden" name="goto_type" id="gotoType" value="${gotoType!''}" />
<input type="hidden" name="payoff_id" id="payoff_id" value="${gotoOrderDto.payOffId!''}" />
<form action="${payZfbJsdzUrl!''}" method="post" name="order_pay" id="order_pay">
	<input name="sid" id="sid" type="hidden" value="${sid!''}"/>
	<input name="orderCode" id="orderCode" type="hidden" value=""/>
	<input type="hidden" name="price" id="price" value="<#if gotoOrderDto.totalActual??>${gotoOrderDto.totalActual?c}<#else>0</#if>"/>
</form>

	<#include "/common/topbar.ftl" />
	<!--site-topbar end-->
	<div class="site-header container" style="height:179px">
	 	<div class="logo">
	 		<h1>
	 			<a href="${rc.contextPath}/index/index.do">
	 				<img src="${rc.contextPath}/views/images/logo.jpg" alt="function baby" title="function baby">
	 				<span class="logo-txt">function baby</span>
	 			</a>
	 		</h1>
	 	</div>
		<div class="site-header-r">
			<ul class="progress-t">
				<li>
					<span>我的购物车</span>
					<em></em>
				</li>
				<li>
					<span>确认订单并付款</span>
					<em></em>
				</li>
				<li style="margin:0 0 0 6px;background-color:#CBCBCB">
					<span>完成订单</span>
					<em class="progress-t-n"></em>
				</li>
			</ul>
			<div class="progress"></div>
		</div>

	</div>
	<!-- site-header end -->

	<!-- address -->
	<div class="address container">
		<div id="address-page">
			<#include "/person/add_address.ftl" />
		</div>		 
	</div>
	<!-- end address -->


	<!-- payment -->
	<div class="payment container">
		<h3>支付方式</h3>
		<input type="hidden" name="paymentType" id="paymentType" value="ZFB_WEB_JSDZ"/>
		<ul class="mode fl">
			<li class="zfb clickc checked">
				<a href="javascript:selectPay('ZFB_WEB_JSDZ', '${payZfbJsdzUrl!''}')">
					<img src="${rc.contextPath}/views/images/zfb.png">
					<span class="changecolor">支付宝支付</span>
				</a>
			</li>
			<li class="wy clickc">
				<a href="javascript:selectPay('ZXYL_WEB', '${payYlUrl!''}')">
					<img src="${rc.contextPath}/views/images/wyzf.png">
					<span class="changecolor">银联</span>
				</a>
			</li>
			<!--<li class="wx clickc">
				<a href="javascript:selectPay('WX_WEB')">
					<img src="${rc.contextPath}/views/images/wxzf.png">
					<span class="changecolor">微信支付</span>
				</a>
			</li>-->
			<!--<li class="hdfk clickc">
				<a href="javascript:selectPay('HDFK_WEB')">
					<img src="${rc.contextPath}/views/images/hdfk.jpg" alt="">
					<span class="changecolor">货到付款</span>
				</a>
			</li>-->
		</ul>
		<div class="time fl width100">
			<h3 class="public_title">配送时间</h3>
			<div class="time_choose fl width100">
				<input id="sent_time" type="hidden" value="周一至周日（任意时间）">
				<a href="javascript:void(0);" title="周一至周五（工作日）">
					<p>工作日送货</p>
					<span>周一至周五</span>
				</a>
				<a href="javascript:void(0);" title="周六至周日（休息日）">
					<p>双休日送货</p>
					<span>周六至周日</span>
				</a>
				<a class="choosed" href="javascript:void(0);" title="周一至周日（任意时间）">
					<p>不限送货时间</p>
					<span>周一至周日</span>
				</a>
			</div>
			<p class="color-warning fl">※由第三方快递发货</p>
		</div>
		<!--<div class="time">
			<label for="time" class="sent-time">配送时间</label>
			<select id="sent_time" class="sent-select" value="请选择">
				<option value="周一至周五（工作日）">周一至周五（工作日）</option>
				<option value="周六至周日（休息日）">周六至周日（休息日）</option>
				<option selected="selected" value="周一至周日（任意时间）">周一至周日（任意时间）</option>
			</select>
			<labe style="color:#be4343;font-size:15px;margin-left:20px;">顺丰快递发货</label>
		</div>-->
	</div>
	<!-- invoice start  -->
	<div class="invoice container">
		<h3>发票信息</h3>
		<div class="invoice-box fl">
			<a href="javascript:" class="fp">开具发票</a>
			<div class="getfp">
				<i></i>
				<div class="getfp-box">
					<ul>
						<li>
							<span>是否需要：</span>
							<input type="radio" value="n" onclick="orderInit.getTax()" name="chose1" checked="checked" id="per1">
							<label for="per1">不需要</label>
							<input type="radio" value="y" onclick="orderInit.getTax()" name="chose1" id="com1">
							<label for="com1" class="marginR0"><span>需要</span><span class="color-warning">（加收"商品总金额*6%的税费"）<span></label>
						</li>
						<div class="invoice-hide-box">
							<li class="getfp-box-t">
								<span>发票种类：</span>
								<input type="radio" value="1" name="chose" checked="checked">
								<label for="per">个人</label>
								<input type="radio" value="2" name="chose">
								<label for="com" class="marginR0">公司</label>
							</li>
							<li class="getfp-box-b">
								<span>发票抬头：</span>
								<input type="text" placeholder="请输入姓名" id="invoice_title" class="tit">
							</li>
							<li><p class="gbb-tip color-warning">* 发票抬头内容不能为空,一经确定,不予更改。</p></li>
							<li>
								<a href="javascript:void(0);" class="invoice-btn radius4">确定</a>
							</li>
						</div>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- invoice end  -->

	<!-- confirm -->
	<div class="confirm container">
		<h3>确认商品及优惠券</h3>
		<table class="shop-cart-goods">
			<thead>
				<tr class="shop-header">
					<th class="col-1">商品</th>
					<th class="col-2">单品价格</th>
					<th class="col-3">购买数量</th>
					<th class="col-4">小计</th>
				</tr>
			</thead>
			<tbody>
			
				<#if gotoOrderDto?? && gotoOrderDto.orderLineItems?? >
					<#list gotoOrderDto.orderLineItems as item>
						<tr class="item-row">
							<td class="col">
								<div class="g-pic">
									<a target="_blank" href="${rc.contextPath}/product/skuDetail.do?skuId=${item.skuId?c}"><img src="${item.imgPath}" alt="${item.skuName!''}"></a>
								</div>
								<div class="g-info">
									<div class="g-name">
										<a target="_blank" href="${rc.contextPath}/product/skuDetail.do?skuId=${item.skuId?c}">
											${item.skuName!''}
										</a>
									</div>
									<p class="g-type">
										<span>颜色：${item.color!''}</span>
										<span>尺码：${item.size!''}</span>
									</p>
								</div>
							</td>
							<td class="col-2 col">&yen:${item.currentPrice!''}</td>
							<td class="col-3 col">${item.requestedQty!''}</td>
							<td class="col-4 col">&yen:${item.currentPriceTotal!''}</td>
						</tr>
					</#list>
				</#if>
			</tbody>
		</table>
		<div class="shop-cart-box-ft container ">
			<div class="cheap-buy fl">
				<div class="message container">
					<span>给fushionbaby留言：</span>
					<textarea name="message" id="message" cols="30" rows="10" onblur="if(this.value=='')this.value='选填：对本次交易的说明';" onfocus="if(this.value=='选填：对本次交易的说明')this.value='';" value="选填：对本次交易的说明"></textarea>
				</div>
			</div>
			<div class="price container fl">
				<div class="price-panel">
					<ul>
						<li class="figure">
							税费：
							<span class="figure-all">
								<span class="num" id="tax_price">0</span>
								元
							</span>
						</li>
						<li class="figure">
							商品件数：
							<span class="figure-all">
								<span class="num">${gotoOrderDto.quantityTotal!''}</span>
								件
							</span>
						</li>
						<li class="figure">
							金额合计：
							<span class="figure-all">
								<span class="num" id="">${gotoOrderDto.skuTotal!''}</span>
								元
							</span>
						</li>
						<li class="figure">
							<strong>邮费：</strong>
							<span class="figure-all">
								<span class="num" id="freight_price"><strong>${gotoOrderDto.freight!''}</strong></span>
								元
							</span>
						</li>
					</ul>
				</div>
			</div>
			<!-- 代金券等折扣 开始  -->
			<div id="discount">
				<#assign rete  = EpointsUtil.EPOINTS_EXCHANGE_RATE>
				<#if (gotoOrderDto.epoints)?? && gotoOrderDto.epoints gte rete>
				<div class="discount-wrap">
					
					<a class="discount-btn discount-btn-current" href="javascript:void(0);">使用兜米</a>
					<div class="discount-box" style="display:block !important;">
						<div class="discount-input-group fl width100">
							<label for="本次使用">本次使用：</label>
							<input type="text" class="small_input" id="epointNum"
								onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="11"
   								onafterpaste="this.value=this.value.replace(/\D/g,'')"	/>
							<label for="兜米支付" class="marginL10">
								<span>兜米支付&emsp;</span><span style="font-weight:500;">抵用 <i class="color_red" id="epointsMoney">0</i> 元现金</span>
							</label>
							<div class="discount-box-btn-wrap marginT0 marginL20 ">
								<a class="discount-box-btn confirm" id="epoint-confirm" href="javascript:orderInit.useEpoints();">使用</a>
							</div>
						</div>
						<p class="discount-box-line"></p>
						<span class="marginT10">您有兜米：
						 <strong style="color:#4d4d4d;">${gotoOrderDto.epoints!''}</strong> 个，本次可用：
						 <strong style="color:#f9a565">${gotoOrderDto.canEpoints!''}</strong> 个，已使用：
						 <strong class="color_red" id="can-use-epoint-strong">0</strong> 个兜米</span>
						 <#if gotoOrderDto.canEpoints??>
						 	<input type="hidden" id="can-use-epoint" value="${gotoOrderDto.canEpoints?c}" />
						 </#if>
						<span class="marginT10" style="float:right;"><a href="${rc.contextPath}/activity/goToHelp.do#doumi" class="color-green">[了解什么是兜米]</a></span>
						<input type="hidden" id="use_integral" value="0" />
					</div>
				</div>
				</#if> 
			 <div class="discount-wrap">
				<a class="discount-btn" href="javascript:void(0);">使用礼品卡</a>
				<div class="discount-box">
					<div class="discount-input-group fl width100">
						<label for="卡号">卡号：</label>
						<input type="text" placeholder="请输入卡号..." id="giftCardNo" />
					</div>
					<div class="discount-input-group fl width100">
						<label for="密码">密码：</label>
						<input type="password" placeholder="请输入密码..." id="giftCardPwd" />
						<span class="color-green marginL10">※单次未使用完的金额将自动转入用户余额内</span>
					</div>
					<p class="discount-box-line"></p>
					<div class="discount-box-btn-wrap">
						<a class="discount-box-btn confirm" href="javascript:orderInit.chargeFund();">使用</a>
						<a class="discount-box-btn cancel" href="javascript:void(0);">取消</a>
					</div>
					<span class="coupon-error color-warning textC width100">代金券不存在或已经被使用！</span>
				</div>
			</div>
			<div class="discount-wrap">
				<a class="discount-btn" href="javascript:orderInit.loadAccountBalance();">使用账号余额</a>
				<div class="discount-box">
					<div class="discount-input-group fl width100">
						<label for="本次使用">本次使用：</label>
						<input type="text" class="small_input"  id="accountMoney"
							onkeyup="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]" maxlength="19"
   							onafterpaste="this.value=(this.value.match(/\d+(\.\d{0,2})?/)||[''])[0]"	/>
						<label for="" class="marginL10"><span>元支付</label>
						<div class="discount-box-btn-wrap marginT0 marginL20 ">
							<a class="discount-box-btn confirm" href="javascript:orderInit.useAccountMoney();">使用</a>
						</div>
						<span style="float:right;">已有礼品卡？ <a href="javascript:void(0);" class="color-green">[点击使用礼品卡充值]</a></span>
					</div>
					<p class="discount-box-line"></p>
					<span class="marginT10">您当前有可用余额 
						<strong id="card_ye" class="color_red"><#if user?exists && user.walletMoney?exists>${user.walletMoney}<#else>0</#if></strong>，最多可用 
						<strong class="color_red" id="canMoney"><#if user?exists && user.availableMoney?exists>${user.availableMoney}<#else>0</#if></strong>
						<strong class="color_black card_ye_message">&emsp;您礼品卡可用余额为零，无法使用礼品卡余额支付</strong>
					</span>
					<input type="hidden" id="accountMoneyVal" value="0" />
				</div>
				<script>
				$(window).load(function(){
					/*可用余额为0时，显示提示*/
					if ($.trim($('#card_ye').text()) == 0) {
						$('.card_ye_message').show();
					}
				})
				</script>
			</div>	
			<div class="discount-wrap" id="coupon">
				<div class="discount-input-group fr">
					<label for="优惠券" class="fl">优惠券</label>
					<input type="text" id="card_no" class="fl">
					<div class="discount-box-btn-wrap marginT0 marginL20 ">
						<a class="discount-box-btn confirm" id="change-confirm" href="javascript:orderInit.getCardno();">使用</a>
					</div>
				</div>
				<p class="fr width100 coupon_error"  id="check_cardNo">※请输入正确的代金卷号码</p>
				<input type="hidden" id="use_cardno" value="n"/>
			</div>
			<!-- 代金券等折扣 结束  -->
			<!-- 应付金额 开始  -->
			<div class="shop-cart-sesult fl width100 radius2">
				<div class="btn fr">
					<input type="button" onclick="orderInit.create();" value="确认订单并支付" id="ok_pay" class="btn-info btn-next">
				</div>
				<div class="btn fr">
					<input type="button" onclick="backCartList();" value="返回购物车" class="btn-info btn-back">
				</div>
				<div class="shop-cart-action fr">
					<p class="total-price">
						<span>应付总金额：</span>
						<i>${gotoOrderDto.totalActual!''}</i> 元
					</p>
				</div>
				<p class="width100 fl size14 textR shop-cart-sesult-p marginT20">寄送至：<span id="mailing-address">上海市 闸北区 彭浦新村 岭南路<span></p>
				<p class="width100 fl size14 textR shop-cart-sesult-p">收货人：<span id="mailing-consignee">aka 150****1234</span></p>
			</div>
			
			
			<!-- 应付金额 结束  -->
		</div>
	</div>
	
	<!--Footer-->
	<#include "/common/footer.ftl" />
	<script type="text/javascript" src="${rc.contextPath}/web-js/order/order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/web-js/address/address.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
</body>
</html>