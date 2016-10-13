<!DOCTYPE html>
<html>
    <head>
        <title>我的订单</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="order">

        <div class="container">

            <div class="head">
                <a href="javascript:jumpMemberCenter();" class="a-left">
                    <span class="back"></span>
                </a>
                <p>我的订单</p>
            </div>

            <ul class="rank-ul fl wp100">
              <li class="active first"  onclick="javascript:window.location.href='${rc.contextPath}/order/orderList?status=0'">
                <p>全部</p>
                <span  id="totalNo"></span>
                <i></i>
              </li>
              <li  onclick="javascript:window.location.href='${rc.contextPath}/order/orderList?status=1&orderStatus=1'">
                <p>待付款</p>
                <span id="toBePaidNo"></span>
                <i></i>
              </li>
              <li  onclick="javascript:window.location.href='${rc.contextPath}/order/orderList?status=2&orderStatus=3'">
                <p>待发货</p>
                <span id="toBeShippedNo"></span>
                <i></i>
              </li>
              <li  onclick="javascript:window.location.href='${rc.contextPath}/order/orderList?status=3&orderStatus=5'">
                <p>待收货</p>
                <span id="toBeReceivedNo"></span>
                <i></i> 
              </li>
              <li  onclick="javascript:window.location.href='${rc.contextPath}/order/evaluateList?status=4&evaluateStatus=n'">
                <p>待评价</p>
                <span id="toBeEvaluatedNo"></span>
                <i></i>
              </li>
            </ul>

            <!-- .order-wrap 为一种状态 --> 
            <div class="order-wrap fl wp100">
                <!-- .order-box 为一个订单 -->
                <#if orderUserRes.orderList?? >
                        <#list orderUserRes.orderList as order>
			                <div class="order-box fl wp100 mB10">
			                	<#if order.skuByOrderLines?? >
			                		<#list order.skuByOrderLines as orderLine>
					                    <a href="${rc.contextPath}/order/orderDetail?orderCode=${order.orderCode}" class="cart-confirm-b fl wp100">
					                        <img src="${orderLine.skuImg}">
					                        <p>${orderLine.skuName}</p>
					                        <#if orderLine.skuColor !="">
												<span>颜色：${orderLine.skuColor}</span>
											</#if>
					                         <#if orderLine.skuSize !="">
												<span>规格：${orderLine.skuSize}</span>
											</#if>
					                        <i>&yen; ${orderLine.skuPrice}</i>
					                        <div class="modal-c fl wp100">数量 ${orderLine.quantity}</div>
					                    </a>
			                    	 </#list> 
                 				</#if>
			                    
			                    <div class="order-wrap-b fl wp100">
			                        <span>共 ${order.orderLineTotalQuantity} 件</span>
			                        <p>合计：<i class="red">&yen;${order.paymentTotalActual}</i></p>
									
									<#if order.orderStatus= "1" >
										<a href="javascript:void(0)" 
				                            onclick="gotoPay('${sid!''}','${order.orderCode!''}');">
				                            <button>去付款</button>
				                        </a>
			                        	<button class="cancel-order" onclick="setCancelOrder('${order.orderCode}');">取消订单</button>
									</#if>
									<#if order.orderStatus= "2" >
			                        	<button  class="disabled">审核中</button>
									</#if>
			                        <#if order.orderStatus= "3" >
										<a href="javascript:remindDelivery('${order.orderCode}')"><button>提醒发货</button></a>
									</#if>
									<#if order.orderStatus= "4" >
			                        	<button  class="disabled">审核不通过</button>
									</#if>
									<#if order.orderStatus= "5" >
										<a href="javascript:confirmReceipt('${order.orderCode}')"><button>确认收货</button></a>
									</#if>
									
									<#if order.orderStatus= "7" >
			                        	<button  class="disabled">已发货</button>
									</#if>
									<#if order.orderStatus= "8" >
			                        	<button  class="disabled">交易完成</button>
									</#if>
									<#if order.orderStatus= "9" >
			                        	<button   class="disabled">会员取消订单</button>
									</#if>
									<#if order.orderStatus= "10" >
			                        	<button  class="disabled">系统取消订单</button>
									</#if>
									<#if order.orderStatus= "11" >
			                        	<button   class="disabled">用户拒收订单</button>
									</#if>
									<#if order.orderStatus= "12" >
			                        	<button   class="disabled">已退款订单</button>
									</#if>
			                    </div>
			                </div>
			             </#list> 
                 </#if>

            </div>

            

        </div><!-- /.container -->

        <div class="modal-wrap" id="cancelOrderModal">
            <div class="modal">
                <p>确认取消该订单吗？</p>
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
        	loadEachOrderQuantity();
			function loadEachOrderQuantity(){
				$.ajax({
						type: "POST",
						url : "${rc.contextPath}/order/getEachOrderQuantity",
						data: {'time':new Date().getTime()},
						async:false,
						dataType: "json",
					    success: function (data) {
					    	if(data.responseCode==0){
		                        var orderUserRes=data.data;
		                        $("#totalNo").text("("+orderUserRes.total+")");
		                     	$("#toBePaidNo").text("("+orderUserRes.toBePaidNo+")");
	                          	$("#toBeShippedNo").text("("+orderUserRes.toBeShippedNo+")");
	                          	$("#toBeReceivedNo").text("("+orderUserRes.toBeReceivedNo+")");
	                          	$("#toBeEvaluatedNo").text("("+orderUserRes.toBeEvaluatedNo+")");
	                       }
					    }
				});
		  }			
			

            var status=GetQueryString("status");
            if(status !=null && status.toString().length>0) {
                $('.rank-ul').find('li').eq(status).addClass('active').siblings('li').removeClass('active');
           }

            $('.rank-ul').find('li').click(function(event) {
                var thisIndex = $(this).index();
                $(this).addClass('active').siblings().removeClass('active');
                $('.order-wrap').eq(thisIndex).show().siblings('.order-wrap').hide();
            });

            /*取消订单*/
            $('.cancel-order').click(function(event) {
                $('#cancelOrderModal').fadeIn();
                
            });
            $('#cancelOrderModal').find('.confirm').click(function(event) {
                $('#cancelOrderModal').fadeOut();
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
		
		function jumpMemberCenter(){
        	location.href="${rc.contextPath}/memberCenter/toMemberCenter?time="+new Date().getTime();
        }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
