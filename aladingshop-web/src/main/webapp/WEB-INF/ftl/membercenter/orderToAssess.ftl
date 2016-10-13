<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的订单 - 待评价</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
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
						
                        <!-- 待评价 -->
                        <#if evaluateOrderUserRes??>
                        	<#list evaluateOrderUserRes.skuByOrderLines as skuLine>
		                        <tbody>
		                            <tr class="order-time">
		                                <td colspan="5">
		                                    <span>下单时间：${skuLine.createOrderTime!''}</span>
		                                    <span>订单号：${skuLine.orderCode!''}</span>
		                                </td>
		                            </tr>
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
		                                <td rowspan="1">
		                                    <div class="status">
		                                        <p>已完成订单</p>
		                                        <a href="${rc.contextPath}/order/orderInfo?orderCode=${skuLine.orderCode!''}">订单详情</a>
		                                    </div>
		                                </td>
		                                <td rowspan="1">
		                                    <div class="action">
		                                        <a class="pay" href="${rc.contextPath}/memberComment/evaluating.htm/${skuLine.skuCode}/${skuLine.orderLineId}">去评价</a>
		                                        <!--<a class="cancel" href="javascript:void(0)">删除订单</a>-->
		                                        <div class="confirm-box">
		                                            <p>您确定要删除订单吗？</p>
		                                            <a class="confirm-no" href="javascript:void(0)">取消</a>
		                                            <a href="javascript:void(0)">确定</a>
		                                        </div>
		                                    </div>
		                                </td>
		                            </tr>
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

        <!-- javascript -->
        <script>
            $(".tcdPageCode").createPage({
            	pageCount:${pageSize!1},
                current:${curPage!1},
                backFn:function(p){
                	location.href = "${rc.contextPath}/order/evaluateList?curPage="+p;
                }
            });
        </script>
    </body>
</html>
