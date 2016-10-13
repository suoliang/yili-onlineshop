<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title></title>
    <script src="${rc.contextPath}/static/shop/js/mui.min.js"></script>
    <link href="${rc.contextPath}/static/shop/css/mui.min.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/css/style1.css"/>
    <script src="${rc.contextPath}/static/shop/farmwork/jquery/jquery-2.1.3.min.js"></script><!-- 通用jQuery JS -->
    <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/fonts/iconfont.css"/>
    <script type="text/javascript" charset="UTF-8">
      	mui.init();
    </script>
</head>
<body id="point-confirm-order">
	<header class="mui-bar mui-bar-nav">
	    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	    <h1 class="mui-title">确认订单</h1>
	</header>
	<div class="mui-content mB100">
		<ul class="mui-table-view">
			<#if addressList?? && addressList?size&gt;0>
            	<#list addressList as address>
            	<#if address.isDefault=='y'>
				 	<li class="mui-table-view-cell">
				 		<span class="iconfont icon-center hot"></span>
			            <span class="name mR20">${address.name}</span>
			            <span class="iconfont icon-shouji hot"></span>
		               	<span>${address.phone}</span>
		                <p class="mT10">${address.provinceName}${address.cityName}${address.districtName}${address.addressInfo}</p>
			        </li>
			        <input type="hidden" id="addressId" value="${address.addressId}">
			    </#if>
		     	</#list>
		    </#if>
		</ul>
       	<ul class="mui-table-view">
        	<li class="mui-table-view-cell ">
        		<span class="iconfont icon-p-shop hot"></span>&nbsp;阿拉丁商品
        		<span class="mui-pull-right">共计${gotoOrderDto.quantityTotal}件</span>
        	</li>
        	<li>
	        	<ul class=" mui-table-view">
	        		<li class="mui-table-view-cell">
	        			<#if gotoOrderDto??>
                		<#list gotoOrderDto.orderLineItems as item>
							<div class="mui-pull-left mR20">
								<img class="mui-media-object " src="${item.imgPath}">
							</div>
							<div class="mui-media-body">
								<p class="color-black">${item.skuName}</p>
								<p class="m10">
									<small>颜色：${item.color}  </small>
									<small class="mui-pull-right">尺码：${item.size}</small>
								</p>
								<div class="mB10">
									<del class="color-gray">￥:${item.retailPrice}</del>
									<span class="color-red mui-pull-right">积分:${item.currentPrice}</span>
								</div>
							</div>
						</#list>
            			</#if>
					</li>

	        	</ul>
        	</li>
        	<li>
        		<form class="mui-input-group mui-table-view-divider">
					<div class="mui-input-row">
						<label>留言:</label>
						<input type="text" placeholder="" id="txtMemo">
					</div>
					<div class="mui-input-row">
						<label>配送时间:</label>
						<input type="date" class="mui-input-clear" min="">
					</div>
				</form>
        	</li>
		</ul>
	</div>
	<div class="mui-bar mui-bar-tab">
		<div class="mui-table-view-cell">
			<div class="mui-pull-left">
				合计：<span class="color-red">积分${gotoOrderDto.totalActual}</span><br/>
				<p>免运费</p>
			</div>
			<button class="mui-btn mui-pull-right mui-btn-red" onclick="createOrder('${sid}','${gotoOrderDto.payOffId}','${skuCode}')">提交订单</button>
		</div>
	</div>
	<script>
		function createOrder(sid,payOffId,skuCode){
			var memo = $.trim($("#txtMemo").val());
			//var sendDate = $('.check-box.checked').find('input').val();
			var addressId = $("#addressId").val();
			if(addressId == '' || addressId== undefined){
				alert("请设置默认收货地址！");
				return;
			}
			$("#submitOrderBtn").attr("onclick","");

			$.post("${rc.contextPath}/integral/integralCreateOrder",{
				payOffId : payOffId,
				addressId: addressId,
				sid:sid,
				skuCode:skuCode,
				memo: memo,
				sendDate:1,
				time : new Date().getTime()},
				function(data){
					if (data.responseCode=="0") {
						alert("订单提交成功,请耐心等待");
						window.location.href="${rc.contextPath}/integral/homeShow?sid="+data.data.sid+"&time="+new Date().getTime();
		    		}else{
						alert(data.msg);
					}
				});
		}
	</script>
</body>
</html>
