<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Fushionbaby 兜世宝 母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/car.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="stylesheet"  href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
	<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" src="${rc.contextPath}/views/js/persona-order.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<!--自定义-->
	<script type="text/javascript" language="javascript">
		var _ContextPath = "${rc.contextPath}";
	</script>
</head>
<body>
<!--[if IE 6]>
<script src="js/iepng.js" type="text/javascript"></script>	
<script type="text/javascript">
   EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
</script>
<![endif]-->
	<#include "/common/topbar.ftl" />
	<!--site-topbar end-->
	<!--site-header begin-->
	<div class="site-header container">
	 	<div class="logo">
	 		<h1>
	 			<a href="${rc.contextPath}/index/index.do">
	 				<img src="${rc.contextPath}/views/images/logo.jpg" alt="fushionbaby" title="fushionbaby">
	 			</a>
	 		</h1>
	 	</div>
	 	<div class="site-header-r" style="margin-bottom:40px;">
			<ul class="progress-t">
				<li>
					<span>我的购物车</span>
					<em></em>
				</li>
				<li style="background-color: rgb(203, 203, 203);">
					<span>确认订单并付款</span>
					<em class="progress-t-n"></em>
				</li>
				<li style="margin:0 0 0 6px;background-color:#CBCBCB">
					<span>完成订单</span>
					<em class="progress-t-n"></em>
				</li>
			</ul>
			<div class="progress progress-start"></div>
		</div>
	 	<!--site-header end-->

	 	<!-- header-nav begin -->
	</div>
	<!-- site-header end -->

	<!-- liuchengtu begin-->
	<div class="car-main container">
			<table class="order-table" id="content">
				<tbody>
					<tr class="title-list">
						<th class="col0"></th>
						<th class="t-title col1">商品</th>
						<!--<th class="t-title col3">编辑</th>-->
						<th class="t-title col2">单价（元）</th>
						<th class="t-title col3">数量</th>
						<th class="t-title col4">小计</th>
						<th class="t-title col3">说明</th>
						<th class="t-title col5">操作</th>
					</tr>
					<#if cartList??>
						<#list cartList as tmp>
							<tr class="order-con" id="rows-${tmp.skuId}">
								<td>
									<label for="cb_1">
										<span class="dot" id="${tmp.skuId}" onclick="item_selected(this)"><input type="checkbox" <#if (tmp.isSelected?exists && tmp.isSelected=='y')>checked="checked"</#if> id="chk-${tmp.skuId}" r="r" name="checkPath" value="${tmp.skuId}"/></span>
									</label>
								</td>			
								<td>
									<div class="order-goods">
										<!--<span class="icon-gift"></span>-->
										<a href="${rc.contextPath}/product/skuDetail.do?skuId=${tmp.skuId}">
											<img src="${tmp.imgPath!''}" alt="">
										</a>
									</div>
									<div class="order-tex">
										<p><a href="${rc.contextPath}/product/skuDetail.do?skuId=${tmp.skuId}">${tmp.name!''}</a></p>
										<div class="order-tex-bd">
											<div class="size">颜色：${tmp.color!''}</div>
											<div class="color">尺码：${tmp.size!''}</div>
										</div>
									</div>
								</td>
								<!-- 商品编辑按钮 开始  -->
								<!--<td>
									<div class="car-edit" href="javascript:void(0);void(0);">
										<i class="icon-edit"></i>
										<div class="hide-edit">
											<i class="arrow-top arrow-top-grey"></i>
											<div class="hide-edit-l fl">
												<div class="color-size fl">
													<div class="color">
														<p>可选择颜色：</p>
														<div class="color-choose">
															<a class="choose" href="javascript:void(0);">橙色<em class="gougou"></em></a>
															<a class="choose cs-current" href="javascript:void(0);">粉色<em class="gougou"></em>
																<input type="hidden" value="粉色" id="cs-current-color">
															</a>
															<a class="choose" href="javascript:void(0);">海蓝<em class="gougou"></em></a>
															<a class="choose" href="javascript:void(0);">黄色<em class="gougou"></em></a>
															<a class="choose" href="javascript:void(0);">玫红<em class="gougou"></em></a>
															<a class="choose" href="javascript:void(0);">浅蓝<em class="gougou"></em></a>
														</div>
													</div>
													<div class="size">
														<p>可选择尺寸：</p>
														<div class="size-choose">
															<a class="choose cs-current" href="javascript:void(0);">
																<span>S<b>(0-8个月)</b></span>
																<em class="gougou"></em>
															</a>
															<a class="choose" href="javascript:void(0);">
																<span>M<b>(0-8个月)</b></span>
																<em class="gougou"></em>
															</a>
														</div>
													</div>
													<div class="discount-box-btn-wrap">
														<a href="javascript:void(0);" class="discount-box-btn confirm">确认修改</a>
														<a href="javascript:void(0);" class="discount-box-btn cancel">取消修改</a>
													</div>
												</div>
											</div>
											<div class="hide-edit-r fl">
												<div class="verticalAlign-carEdit">
													<img src="http://filepic.fushionbaby.com/img/sku/QG1001/QG1001_w_h_h_s_e0cee891-aa04-4220-b922-26fc29dbcf05.png" alt="">
												</div>
												<p>￥：140.00</p>
											</div>
										</div>
									</div>
								</td>-->
								<!-- 商品编辑按钮 结束  -->
								<td class="con-list pri">&yen;<span>${tmp.price!''}</span></td>
								<td class="amount">
									<div class="amount-wrap">
										<div class="Operate reduce_num" onclick="setAmountCart.reduce('${tmp.skuId}')">-</div>
										<div class="Operate num"><input type="text" name="baby-num" id="baby-num-${tmp.skuId}" 
										onkeyup="setAmountCart.modify('${tmp.skuId}');" onblur="setAmountCart.modify('${tmp.skuId}');" 
										autocomplete="off" value="${tmp.pnum!''}" 
										style="width:32px; text-align:center; height:20px;" /></div>
										<div class="Operate add_num" onclick="setAmountCart.add('${tmp.skuId}')">+</div>
									</div>
								</td>
								<td class="con-list tot">&yen;<span id="rows-tprice-${tmp.skuId}">${tmp.rowPriceTotal!''}</span></td>
								<td class="color-error textC"><!--赠品<br/>优惠金额：&yen; 40--></td>
								<td class="con-list">
										<div onclick="delShoppingCart(1,'${tmp.skuId}')">
											<a href="javascript:" class="del-btn del-order">删除</a>
										</div>
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>	
			<input type="hidden" id="get_Type"/>
			<input type="hidden" id="get_cartRowsId"/>
			<input type="hidden" id="get_skuId" />
			<div class="car-main-ft clearfix">
				<div class="cmf-l-wrap">
					<label for="all">
						<span class="dot" checked="checked" id="all"><input type="checkbox" name="checkPath" id="all"></span>
					</label>
					<span>全选</span>
					<span onclick="delShoppingCart(2,'')">
						<a href="javascript:" class="del-btn del-order">删除</a>
					</span>
				</div>
				<div class="cmf-r-wrap">
					<div class="shop-cart-action clearfix">
						<p class="total-price">
							<span>应付总金额：</span>
							<i>${cart.priceTotal!''}</i> 元
						</p>
					</div>
					<div class="btn-wrap clearfix">
						<a class="btn btn-l" href="${rc.contextPath}/index/index.do">继续购物</a>
						<a class="btn btn-r" href="javascript:goto_order_check();">去结算</a>
					</div>
				</div>
			</div>
	</div>
	
	<!-- footer begin -->
	<#include "/common/nav.ftl" />
	<#include "/common/footer.ftl" />
	<div class="pop-up-black de-order">
		<div class="pop-up">
		<div class="close"></div>
			<p class="desc-info">确定要删除此商品？</p>
			<p class="desc-info-btn">
				<input type="button" onclick="cart.remove()" class="pay-btn" id="confirm_delete" value="确定"></input>
				<input type="button" class="pay-btn con-ec escer" value="取消"></input>
			</p>	
		</div>
	</div>
	<div class="pass-check" id="not_login">
		<div class="close"></div>
		<p class="desc-info">您好，请 <a href="${rc.contextPath}/login/index.do">登录</a></p>
	</div>
	<script type="text/javascript">
		function delShoppingCart(type,skuId){
			$("#get_Type").val(type);
			//$("#get_cartRowsId").val(cartRowsId);
			$("#get_skuId").val(skuId);
		}
		
		function goto_order_check(){
			var url = "${rc.contextPath}/order/goto_order_check.do";
			$.ajax({
				type : "POST",
				url  : url,
				data : "time="+new Date().getTime(),
				async : false,// 同步请求,本次请求完成后,后边的代码才会执行
				dataType : "json",
				success : function(data) {
					if (data == null || data == "" || data == undefined) {
						return;
					}
					if(data.responseCode==0){
						window.location.href="${rc.contextPath}/order/goto_order.do?time=" + new Date().getTime();
					}else if(data.responseCode==201){
						$("#not_login").show();//显示请登录层
					}else{
						alert(data.msg);
					}
				}//end success
			});//end ajax
		}
	</script>
</body>
</html>