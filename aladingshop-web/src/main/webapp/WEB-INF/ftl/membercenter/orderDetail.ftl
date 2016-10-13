<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>订单详情 - ${orderUser.orderInfo!''}</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script type="text/javascript">
        	var contextPath="${rc.contextPath}";
        </script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/gotoPay.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/cancelOrder.js"></script>
    </head>
    <body class="order-detail">

       <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->

        <!-- 菜单 开始 -->
        <#include "/common/menu.ftl" />
        
		<#if orderUser??>
	        <div class="order-tetail-tit container">
	            <div class="detail-tit-top">
	                <span></span>
	                <p>我的订单</p>
	                <i>MY ORDER FORM</i>
	            </div>
	            <div class="nav">
	                <a href="http://www.aladingshop.com">首页 &gt;</a>
	                <a href="${rc.contextPath}/order/orderList?orderStatus=0">我的订单 &gt;</a>
	                <a href="">订单详情 &gt;</a>
	                <a href="" class="pink">订单：${orderUser.orderCode!''}</a>
	            </div>
	            <div class="detail-status">
	                <p>当前状态：${orderUser.orderInfo!''}</p>
	                <ul>
	                    <i></i>
	                    <li id="active_1">
	                        <span></span>
	                        <p>提交订单</p>
	                    </li>
	                    <li id="active_2">
	                        <span></span>
	                        <p>付款</p>
	                    </li>
	                    <li id="active_3">
	                        <span></span>
	                        <p>卖家发货</p>
	                    </li>
	                    <li id="active_4">
	                        <span></span>
	                        <p>确认收货</p>
	                    </li>
	                    <li id="active_5">
	                        <span></span>
	                        <p>评价</p>
	                    </li>
	                </ul>
	                <div class="tit-btn">
	                	<#if orderUser.orderStatus==2||orderUser.orderStatus==3||orderUser.orderStatus==4>
		                    <p>您的订单已提交，付款已成功，卖家将会发货。</p>
		                <#elseif orderUser.orderStatus==1>
		                	<p>您的订单已提交，请于 <span class="pink">${orderUser.orderEndTime!''}</span> 前付款，超时订单将失效。</p>
		                    <a class="yes" href="javascript:void(0)" 
		                    	onclick="gotoPay('${sid!''}','${orderUser.webPaymentType}','${orderUser.orderCode!''}');">去付款</a>
		                    <a class="delete-order-btn" href="javascript:void(0)">取消订单</a>
		                <#elseif orderUser.orderStatus==5||orderUser.orderStatus==7>
		                	<p>您的订单已提交，付款已成功，卖家已发货。</p>
		                <#elseif orderUser.orderStatus==6||orderUser.orderStatus==8>
		                	<p>您的订单已经完成啦。</p>
	                	</#if>
		            </div>
	            </div>
	            <div class="order-tetail-address">
	                <h3>收货信息</h3>
	                <p>收货人：${orderUser.receiver!''}</p>
	                <p>收货地址：${orderUser.province!''}${orderUser.city!''}${orderUser.district!''}${orderUser.address!''}</p>
	                <p>联系电话：${orderUser.receiverMobile!''}</p>
	                <p>买家留言：${orderUser.memo!''}</p>
	            </div>
	        </div>
	
	        <div class="cart-table container mT0">
	            <table>
	                <thead>
	                    <tr>
	                        <th class="w460">商品信息</th>
	                        <th class="w190">单价</th>
	                        <th class="w240">数量</th>
	                        <th class="w246">小计</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<#list orderUser.skuByOrderLines as skuLine>
		                    <tr>
		                        <td>
		                            <div class="product fl">
		                                <a href=""><img src="${skuLine.skuImg!''}" height="183" width="183" alt=""></a>
		                                <div class="product-r">
		                                    <p>${skuLine.skuName!''}</p>
		                                    <span>规格：${skuLine.skuColor!''}</span>
		                                    <span>类型：${skuLine.skuSize!''}</span>
		                                </div>
		                            </div>
		                        </td>
		                        <td>
		                            <div class="price">
		                                <!--<p>&yen; 188.00</p>-->
		                                <span>&yen; ${skuLine.skuPrice!''}</span>
		                            </div>
		                        </td>
		                        <td>
		                            <div class="count">${skuLine.quantity!1}</div>
		                        </td>
		                        <td>
		                            <div class="action">&yen; ${skuLine.lineTotalPrice!''}</div>
		                        </td>
		                    </tr>
	                    </#list>
	                </tbody>
	
	                <tfoot>
	                    <tr>
	                        <td>
	                            <span>订单编号：${orderUser.orderCode!''}</span>
	                            <span>下单时间：${orderUser.createOrderTime!''}</span>
	                            <p>积分：-${orderUser.epointsMoney!'0.00'} 元</p>
	                            <p>优惠券：-${orderUser.cardAmount!'0.00'} 元</p>
	                        </td>
	                        <td></td>
	                        <td>
	                            <p>运费：${orderUser.actualTransferFee!''}元</p>
	                        </td>
	                        <td>
	                            <div class="tfoot-money">
	                                <span>共 ${orderUser.skuByOrderLines?size!1}件</span>
	                                <p>实付金额：&yen; ${orderUser.paymentTotalActual!''}</p>
	                            </div>
	                        </td>
	                    </tr>
	                </tfoot>
	            </table>
	        </div>
		</#if>
        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <div class="modal_backup" id="deleteOrder">
            <div class="deleteOrder">
                <p>是否取消订单？</p>
                <a href="javascript:cancelOrder(${orderUser.orderCode!''});">确认</a>
                <a class="no" href="javascript:void(0)">否</a>
            </div>
        </div>

        <!-- js -->
        <script>
	        $(function(){
	            /*取消订单按钮*/
	            $('.delete-order-btn').click(function(event) {
	                $('#deleteOrder').fadeIn(300);
	                $('.deleteOrder').css('visibility', 'visible').stop().animate({'opacity':'1','top':'40%'}, 300);
	            });
	
	            $('.deleteOrder').find('.no').click(function(event) {
	                $('.deleteOrder').stop().animate({'opacity':'0','top':'20%'}, 200);
	                $('#deleteOrder').fadeOut(200);
	                setTimeout(function(){
	                    $('.deleteOrder').css('visibility', 'hidden');
	                },200);
	            });
	            
	        })
	        
	       function readyFun(){
	       		for(var i=1;i<${status}+1;i++){
	       			$("#active_"+i).addClass('active');
	       		}
	       }
	       
        </script>
    </body>
</html>
