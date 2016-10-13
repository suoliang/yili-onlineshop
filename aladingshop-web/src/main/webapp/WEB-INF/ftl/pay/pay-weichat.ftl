<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>微信支付页</title>
          <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body class="pay-weichat">

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <div class="status-top">
            <div class="container">
                <a href="http://www.aladingshop.com"><span></span></a>
            </div>
        </div>

        <div class="container oh">
            <div class="pay-status">
                <div class="status-l">
                    <h3 class="green">微信支付</h3>
                    <p><strong>温馨提示：</strong>您的订单已提交，请于 <span class="red">${orderUser.orderEndTime!''}</span> 前付款，超时订单将失效。</p>
                    <p>订单号:${orderCode!''}&emsp;&emsp;下单时间：${orderUser.createOrderTime!''}&emsp;&emsp;实付金额：${orderUser.paymentTotalActual!'0.00'}元</p>
                    <a href="http://www.aladingshop.com">继续逛逛</a>
                    <a target="_blank" href="${rc.contextPath}/order/orderInfo?orderCode=${orderCode!''}" class="sta-a">查看订单详情</a>
                </div>
                <div class="status-b"></div>
                <div class="pay-weichat-box">
                    <img class="img1" src="${rc.contextPath}/static/shop/images/pay-weichat-phone.png" height="481" width="336" alt="">
                    <div class="pay-weichat-box-r">
                        <h3>扫啊扫</h3>
                        <p>微信安全支付</p>
                        <span>
                            <img src="${wxPay!''}?sid=${sid!''}&orderCode=${orderCode!''}" height="223" width="223" alt="">
                        </span>
                    </div>
                </div>
            </div>
        </div>


       <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <script>
        	function readyFun(){
        		 setInterval(queryOrderState, 2000);
        	}
	
	        function queryOrderState(){
	            $.ajax({
	                type: "GET",
	                url: "${rc.contextPath}/pay/getPayState?sid=${sid!''}&orderCode=${orderCode!''}&t="+new Date().getTime(),
	                data: "",
	                dataType: "json",
	                async:false,
	                success: function(result) {
                    if(result.responseCode==0){//直接跳到成功页
                        window.location.href = "${rc.contextPath}/pay/gotoPayOk?orderCode=${orderCode!''}";
                    }
                }
              });  
            }
        </script>
    </body>
</html>
