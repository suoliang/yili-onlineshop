<!DOCTYPE html>
<html>
    <head>
        <title>订单支付</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
    </head>
    <body id="">
    	
		<form action="" method="post" id="combinFormPay">
        	<input type="hidden" name="sid" id="sid" value="${sid}" />
            <input type="hidden" name="orderCode" id="orderCode" value="${orderCode}" />
        </form>
        
        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>订单支付</p>
            </div>

            <div class="pay-wrap fl wp100 radio">
                <ul>
                    <li>
                        <span class="ali-pay"></span>
                        <h3>支付宝</h3>
                        <p>推荐有支付宝账号者使用</p>
                        <div class="check-box fl checked">
                            <i></i>
                            <input name="chose-address" class="checkItem" type="radio" checked="checked" value="${wapZfbPay}">
                        </div>
                    </li>
                </ul>
            </div>

            <div class="pay-btn">
                <p>实付金额：<span class="red">&yen; ${totalActual}</span></p>
                <a href="javascript:void(0)">
                    <button id="combinSubmitBtn" onclick="combinSubmitPay()">去付款</button>
                </a>
            </div>

        </div><!-- /.container -->

    </body>
    <script>
	    function combinSubmitPay(){
			var chks = $('.checkItem[checked=checked]');
			var len = chks.length;
			if(len==null||len==""||len<=0){
				alert("请选择一种支付方式");
				return;
			}
			$("#combinFormPay").attr("action",chks[0].value);
			$("#combinSubmitBtn").attr('disabled','false');
			$("#combinFormPay").submit();
		}
    </script>
</html>
