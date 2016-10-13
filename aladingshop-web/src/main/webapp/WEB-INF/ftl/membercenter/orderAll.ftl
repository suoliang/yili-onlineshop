<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的订单 - <#if orderStatus==0>全部订单
	        	<#elseif orderStatus==1>待付款
	        	<#elseif orderStatus==3>待发货
	        	<#elseif orderStatus==5>待收货
        	</#if>
        </title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script type="text/javascript">
        	var contextPath="${rc.contextPath}";
        </script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/gotoPay.js"></script>
		<script type="text/javascript" src="${rc.contextPath}/static/web-js/cancelOrder.js"></script>
    </head>
    <body>

        <!-- 右侧悬浮菜单 开始-->
        <#include "/common/rightMenu.ftl" />
        <!-- 右侧悬浮菜单 结束-->

        <!-- 顶部导航 开始 -->
        <#include "/common/topbar.ftl" />
        <!-- 顶部导航 结束 -->

        <!-- 头部搜索版块 开始 -->
        <#include "/common/header.ftl" />
        <!-- 头部搜索版块 结束 -->

        <!-- 菜单 开始 -->
        <#include "/common/menu.ftl" />

        <div class="container">
            <div class="mT20 order-wrap fl mB20">
            	<!-- 个人中心左侧菜单开始 -->
                <#include "/common/centerMenu.ftl" />
            	<!-- 个人中心左侧菜单开始 -->

                <div class="cart-table">
                    <table>
                        <thead>
                            <tr>
                                <th class="w380 fl">商品信息</th>
                                <th class="w90 fl">数量</th>
                                <th class="w120 fl">单价</th>
                                <th class="w189 fl">状态</th>
                                <th class="w189 fl">操作</th>
                            </tr>
                        </thead>
                        <#if orderUserRes??>
                        	<#list orderUserRes.orderList as order>
	                        <!-- 一个订单多个商品 -->
	                        <tbody class="more-goods">
	                            <tr class="order-time">
	                                <td colspan="5">
	                                    <span>下单时间：${order.createOrderTime!''}</span>
	                                    <span>订单号：${order.orderCode!''}</span>
	                                    <#if order.sourceCode==1 || order.sourceCode==2 >
	                                    	<span><b></b><s>手机订单</s></span>
	                                    </#if>
	                                </td>
	                            </tr>
	                            <#list order.skuByOrderLines as skuLine>
	                            	<#if skuLine_index == 0>
	                            		<tr>
			                                <td>
			                                    <div class="product fl">
			                                        <a href="${rc.contextPath}/sku/skuDetail?skuCode=${skuLine.skuCode}" target="_blank"><img src="${skuLine.skuImg!''}" height="122" width="122" alt=""></a>
			                                        <div class="product-r">
			                                            <p>${skuLine.skuName!''}</p>
			                                            <span>规格：${skuLine.skuColor!''}</span>
			                                            <span>类型：${skuLine.skuSize!''}</span>
			                                        </div>
			                                    </div>
			                                </td>
			                                <td>
			                                    <div class="count">${skuLine.quantity!1}</div>
			                                </td>
			                                <td>
			                                    <div class="price">
			                                        <!--<p>&yen; 188.00</p>-->
			                                        <span>&yen; ${skuLine.skuPrice!''}</span>
			                                    </div>
			                                </td>
			                                <td rowspan="${order.skuByOrderLines?size!1}" <#if (order.skuByOrderLines?size>1)>class="rows"</#if> ><!-- n个商品，添加 rowspan="n",添加 class="rows" -->
			                                    <div class="status">
			                                    	<p>${order.orderInfo!''}</p>
			                                        <a href="${rc.contextPath}/order/orderInfo?orderCode=${order.orderCode!''}&t=${time!''}">订单详情</a>
			                                    </div>
			                                </td>
			                                <td rowspan="${order.skuByOrderLines?size!1}"><!-- n个商品，添加 rowspan="n" -->
			                                    <div class="action">
				                                    <#if order.orderStatus == 1>
				                                        <a class="pay" href="javascript:void(0)" 
				                                        	onclick="gotoPay('${sid!''}','${order.webPaymentType}','${order.orderCode!''}');">付款</a>
				                                        <a class="cancel" href="javascript:void(0)">取消订单</a>
				                                        <div class="confirm-box">
				                                            <p>您确定要取消订单吗？</p>
				                                            <a class="confirm-no" href="javascript:void(0)">取消</a>
				                                            <a href="javascript:cancelOrder(${order.orderCode!''});">确定</a>
				                                        </div>
				                                    <#else>
				                                    	<a class="disabled" href="javascript:void(0)">${order.orderInfo!''}</a>
				                                    </#if>
			                                    </div>
			                                </td>
			                            </tr>
	                            	<#else>
	                            	<tr>
		                                <td>
		                                    <div class="product fl">
		                                        <a href=""><img src="${skuLine.skuImg!''}" height="122" width="122" alt=""></a>
		                                        <div class="product-r">
		                                            <p>${skuLine.skuName!''}</p>
		                                            <span>规格：${skuLine.skuColor!''}</span>
		                                            <span>类型：${skuLine.skuSize!''}</span>
		                                        </div>
		                                    </div>
		                                </td>
		                                <td>
		                                    <div class="count">${skuLine.quantity!1}</div>
		                                </td>
		                                <td>
		                                    <div class="price">
		                                        <!--<p>&yen; 188.00</p>-->
		                                        <span>&yen; ${skuLine.skuPrice!''}</span>
		                                    </div>
		                                </td>
		                            </tr>
		                           </#if>
	                            </#list>
	                        </tbody>
                          </#list>
                        </#if>
                       </table>
                </div> 
            </div>
        </div>

        <div class="page container oh">
            <div class="tcdPageCode fr"></div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <script>
        	function GetQueryString(name){ 
		      var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		      var r = window.location.search.substr(1).match(reg); 
		      if(r!=null)return  unescape(r[2]); return null; 
		 	}
 			
            $(".tcdPageCode").createPage({
            	pageCount:${pageSize!1},
                current:${curPage!1},
                backFn:function(p){
                	var orderStatus = GetQueryString("orderStatus");
                	if(orderStatus==null || orderStatus=="" || orderStatus == undefined){
                		location.href = "${rc.contextPath}/order/orderList?curPage="+p;
                		return;
                	}
                	location.href = "${rc.contextPath}/order/orderList?curPage="+p+"&orderStatus="+orderStatus;
                }
	                
            });
        </script>
    </body>
</html>
