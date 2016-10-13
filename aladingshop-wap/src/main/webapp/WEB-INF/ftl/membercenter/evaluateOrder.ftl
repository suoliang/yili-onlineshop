<!DOCTYPE html>
<html>
    <head>
        <title>我的订单</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="order">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
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
            <div class="order-wrap fl wp100">
				<#if evaluateOrderUserRes.skuByOrderLines?? >
					<#list evaluateOrderUserRes.skuByOrderLines as orderLine>
            		<!-- .order-wrap 为一种状态 --> 
               			 <!-- .order-box 为一个订单 -->
			                	<div class="order-box fl wp100 mB10">
					                    <a href="${rc.contextPath}/order/orderDetail?orderCode=${orderLine.orderCode}"  class="cart-confirm-b fl wp100">
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
					                    <div class="order-wrap-b fl wp100"> 
					                        <span>共 ${orderLine.quantity} 件</span>
					                        <p>合计：<i class="red">&yen;${orderLine.lineTotalPrice}</i></p> 
											<a href="${rc.contextPath}/order/toEvaluate?skuCode=${orderLine.skuCode}&orderLineId=${orderLine.orderLineId}"><button>去评价</button></a>
					                    </div>
			                    </div>
           			 </#list> 
            	 </#if>
			</div>
        </div><!-- /.container -->


        <script>
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

           
        })

        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
