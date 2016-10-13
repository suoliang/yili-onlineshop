<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 确认支付</title>
        <#include "/common/common.ftl" />
        <script>
        	function combinSubmitPay(){
				var chks = $('.chose-pay[checked=checked]');
				var len = chks.length;
				if(len==null||len==""||len<=0){
					alert("请选择一种支付方式");
					return;
				}
				if(chks[0].value == 'WX_WEB'){
					$("#combinFormPay").attr("action","${rc.contextPath}/order/gotoWXPay");
					$("#combinFormPay").submit();
					return;
				}
				$("#combinFormPay").attr("action",chks[0].value);
				$("#combinSubmitBtn").attr('disabled','false');
			/*$("#combinFormPay").attr("action","http://localhost:8080/fushionbaby-pay/webzfbJsdz/pay.do");*/
				$("#combinFormPay").submit();
        	}
        	
        </script>
    </head>
    <body class="cart-pay">
    	

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl">
        <!-- 顶部导航 结束 -->
        <form action="" method="post" id="combinFormPay">
        	<input type="hidden" name="sid" id="sid" value="${sid}" />
            <input type="hidden" name="orderCode" id="orderCode" value="${orderCode}" />
        </form>

        <div class="cart-top container">
            <a href="${rc.contextPath}/home" class="logo"></a>
            <ul>
                <li>我的购物车<span></span></li>
                <li>&emsp;&emsp;填写订单信息<span class="cart-pay-span"></span></li>
                <li class="active">购物成功</li>
            </ul>
        </div>

        <div class="cart-pay-top container oh">
            <h3>确认收货信息<span>优惠券抵扣：-${couponMoney}元&emsp;&emsp;积分抵扣：-${epointMoney}元&emsp;&emsp;运费：${transMoney}元</span></h3>
            
            <div class="pay-top-wrap">
                <p>
                	
                    <span>订单编号：${orderCode}</span>
                    <span>下单时间：${createOrderTime}</span>
                </p>
                <h4>订单已提交成功！</h4>
                <p class="price red">实付金额：&yen; ${totalActual}</p>
            </div>
        </div>

        <div class="pay-way container">
            <input id="payWay" type="hidden">
            <h3>支付方式</h3>
            <a class="alipay" href="javascript:void(0)" title="alipay">
                <div class="check-box fl <#if payWay=='ZFB_WEB_JSDZ'>checked</#if>">
                    <i></i>
                    <input name="chose-address" class="chose-pay" type="radio" <#if payWay=='ZFB_WEB_JSDZ'>checked="checked"</#if> value="${zfbPay}">
                </div>
                <span></span>
                <p>支付宝</p>
                <i>推荐有支付宝账户者使用</i>
            </a>
         	<a class="weichat" href="javascript:void(0)" title="weichat">
                <div class="check-box fl <#if payWay=='WX_WEB'>checked</#if>">
                    <i></i>
                    <input name="chose-address" class="chose-pay" type="radio" <#if payWay=='WX_WEB'>checked="checked"</#if> value="WX_WEB">
                </div>
                <span></span>
                <p>微信支付</p>
                <i>推荐使用</i>
            </a>
            <a class="bank" href="javascript:void(0)" title="bank">
                <div class="check-box fl <#if payWay=='ZXYL_WEB'>checked</#if>">
                    <i></i>
                    <input name="chose-address" class="chose-pay" type="radio" <#if payWay=='ZXYL_WEB'>checked="checked"</#if> value="${ylPay}">
                </div>
                <span></span>
                <p>银联支付</p>
                <i>方便快捷</i>
            </a>
        </div>

        <div class="pay-btn container">
            <p class="price">实付金额：<span class="red">&yen; ${totalActual}</span></p>
           	
            <button id="combinSubmitBtn" onclick="combinSubmitPay()">确认支付</button>
            
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl" />
        <!-- 底部 结束 -->

        <script>
            $(function(){
                /*选择支付方式*/
                var payWay_check = $('.pay-way').find('input[type=radio]');
                payWay_check.click(function(event) {
                    payWay_check.removeAttr('checked').parents('.check-box').removeClass('checked');
                    this.checked = true;
                    $(this).attr('checked', 'checked').parents('.check-box').addClass('checked');
                    $('#payWay').val($(this).parents('a').attr('title'));
                });
            });
        </script>
    </body>
</html>
