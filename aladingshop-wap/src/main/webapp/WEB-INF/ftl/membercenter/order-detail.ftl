<!DOCTYPE html>
<html>
    <head>
        <title>订单详情</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="orderDetail">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>订单详情</p>
                <#if orderUser.orderStatus=='1'>
	                <a href="javascript:setCancelOrder('${orderUser.orderCode}');" class="a-right cancel-order">
	                    <span>取消订单</span>
	                </a>
                </#if>
            </div>

            <div class="address">
                <div class="address-box">
                    <div class="address-detail fl">
                        <span class="name">收件人：${orderUser.receiver}</span>
                        <span class="phone">电话：${orderUser.receiverMobile}</span>
                        <p>地址：${orderUser.province}${orderUser.city}${orderUser.district}${orderUser.address}</p>
                    </div>
                </div>
            </div>

            <div class="order-detail-a fl wp100">
                <ul class="list mB0">
                    <li><p>订单状态：${orderUser.orderInfo}</p></li>
                    <li><p>订&ensp;单&ensp;号：${orderUser.orderCode}</p></li>
    				<#if orderUser.orderStatus=='1'>
    					<li><p>您的订单已提交，请在24个小时内完成支付<br>超时订单将关闭</p></li>
                        </#if>
    				<#if orderUser.orderStatus=='2'>
    					<li><p>订单支付成功</p></li>
                        </#if>
                    <#if orderUser.orderStatus=='5'||orderUser.orderStatus=='7'>
    					<li><p>卖家已发货</p></li>
                        </#if>
    				<#if orderUser.orderStatus=='6'||orderUser.orderStatus=='8'>
    					<li><p>已确认收货</p></li>
                        </#if>
                </ul>
            </div>

            <div class="cart-confirm-a fl wp100">
                <span class="icon-house"></span>
                <h4>阿拉丁商品</h4>
                <p>共计 <i class="count-num">${orderUser.orderLineTotalQuantity}</i> 件</p>
            </div>

			<#if orderUser.skuByOrderLines?? >
        		<#list orderUser.skuByOrderLines as orderLine>
                    <div class="cart-confirm-b fl wp100">
		                <a href=""><img src="${orderLine.skuImg}"></a>
		                <p>${orderLine.skuName}</p>
                        <#if orderLine.skuColor !="">
							<span>颜色：${orderLine.skuColor}</span>
						</#if>
                         <#if orderLine.skuSize !="">
							<span>规格：${orderLine.skuSize}</span>
						</#if>
                        <i>&yen; ${orderLine.skuPrice}</i>
                        <div class="modal-c fl wp100">数量 ${orderLine.quantity}</div>
		            </div>
            	 </#list>
			</#if>

            <ul class="list mB0">
                <li>
                    <p>运费</p>
                    <span>&yen; ${orderUser.actualTransferFee!'0.00'}</span>
                </li>
                <li>
                    <p>代金券</p>
                    <span>- &yen; ${orderUser.cardAmount!'0.00'}</span>
                </li>
                <li>
                    <p>实付金额（含运费）</p>
                    <span>&yen; ${orderUser.paymentTotalActual!'0.00'}</span>
                </li>
                <a href="tel:${orderUser.customerServicePhone!''}">
                    <li>
                        <p>客服电话：${orderUser.customerServicePhone!''}</p>
                    </li>
                </a>
            </ul>
            <div class="order-detail-a fl wp100">
                <p>下单时间：<span>${orderUser.createOrderTime}</span></p>
                <p>支付时间：<span>${orderUser.paymentCompleteTime}</span></p>
                <p>发货时间：<span>${orderUser.deliveryTime}</span></p>
                <p>收货时间：<span>${orderUser.lastReceiveTime}</span></p>
            </div>


			<#if orderUser.orderStatus=='1'>
	            <div class="cart-confirm-e fl wp100">
	                <p>下单成功，去付款</p>
	                <a href="javascript:void(0)" onclick="gotoPay('${sid!''}','${orderUser.orderCode!''}');">
	                    <button>去付款</button>
	                </a>
	            </div>
			</#if>
			<#if orderUser.orderStatus=='3'>
	            <div class="cart-confirm-e fl wp100">
	                <p>支付成功，提醒卖家发货</p>
	                <a href="javascript:remindDelivery('${orderUser.orderCode}')">
	                    <button>提醒发货</button>
	                </a>
	            </div>
			</#if>
			<#if orderUser.orderStatus=='5'>
	            <div class="cart-confirm-e fl wp100">
	                <p>确认收货</p>
	                <a href="javascript:confirmReceipt('${orderUser.orderCode}')">
	                    <button>确认收货</button>
	                </a>
	            </div>
			</#if>


        </div><!-- /.container -->

        <div class="modal-wrap" id="cancelOrderModal">
            <div class="modal">
                <p>确定取消该订单吗？</p>
                <div class="btn-wrap">
                    <a class="cancel" href="javascript:void(0)">取消</a>
                    <a class="confirm" href="javascript:void(0)">确定</a>
                    <input type="hidden" id="cancelOrderCode" >
                </div>
            </div>
        </div>

        <script>
        function setCancelOrder(orderCode){
        	$("#cancelOrderCode").val(orderCode);

        }
        function cancelOrder(){
        	var cancelOrderCode=$('#cancelOrderCode').val();
        	$.ajax({
					type: "POST",
					url : "${rc.contextPath}/order/cancelOrder?orderCode="+cancelOrderCode,
					async:false,
					dataType: "json",
				    success: function (data) {
				    	if(data.responseCode==0){
	                    	alert('订单取消成功!');
                   		 }else{
                   		 	alert('订单取消失败!');
                   		 }
                   		 window.location.href="${rc.contextPath}/order/orderList?status=0";
				  	}
			});
        }

        function remindDelivery(orderCode){
        	$.ajax({
					type: "POST",
					url : "${rc.contextPath}/order/remindDelivery",
					data: {'orderCode':orderCode,'time':new Date().getTime()},
					async:false,
					dataType: "json",
				    success: function (data) {
				    	if(data.responseCode==0){
	                    	alert('提醒发货成功!');
                   		 }else{
                   		 	alert('提醒发货失败!');
                   		 }
                   		 window.location.href="${rc.contextPath}/order/orderList?status=2&orderStatus=3";
				  	}
			});
        }

        function confirmReceipt(orderCode){
        	$.ajax({
					type: "POST",
					url : "${rc.contextPath}/order/confirmReceipt",
					data: {'orderCode':orderCode,'time':new Date().getTime()},
					async:false,
					dataType: "json",
				    success: function (data) {
				    	if(data.responseCode==0){
	                    	alert("确认收货成功！");
                   		 }else{
                   		 	alert(data.data);
                   		 }
                   		 window.location.href="${rc.contextPath}/order/orderList?status=3&orderStatus=5";
				  	}
			});
        }


        $(function(){
            /*取消订单按钮*/
            var cancelOrderModal = $('#cancelOrderModal');
            var confirmBtn = $('#cancelOrderModal').find('.confirm');
            $('.cancel-order').click(function(event) {
                cancelOrderModal.fadeIn();
            });

            confirmBtn.click(function(event) {
                cancelOrderModal.fadeOut();
                cancelOrder();
            });
        })

        /**去付款*/
		function gotoPay(sid,orderCode){
			var temp = document.createElement("form");
			temp.action = _ContextPath + "/order/goPay";
			temp.method = "post";
			temp.style.display = "none";

			var optSid = document.createElement("input");
			optSid.name="sid";
			optSid.value=sid;
			temp.appendChild(optSid);

			var optOrderCode = document.createElement("input");
			optOrderCode.name="orderCode";
			optOrderCode.value=orderCode;
			temp.appendChild(optOrderCode);

			document.body.appendChild(temp);
			temp.submit();
		}
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
