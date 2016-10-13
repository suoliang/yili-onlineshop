<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的益多宝</title>
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
                                <th class="w380 fl">益多宝信息</th>
                                <th class="w90 fl">本金金额</th>
                                <th class="w120 fl">收益金额</th>
                                <th class="w189 fl">赠券金额</th>
                               <!-- <th class="w189 fl">操作</th>-->
                            </tr>
                        </thead>
						
                        <!-- 待评价 -->
                        <#if cardList??>
                        	<#list cardList as card>
		                        <tbody>
		                            <tr class="order-time">
		                                <td colspan="5">
		                                    <span>生成时间：${card.showTime!''}</span>
		                                    <span></span>
		                                </td>
		                            </tr>
		                            <tr>
		                                <td>
		                                    <div class="product fl">
		                                        <a href="${rc.contextPath}/category/010.htm"><img src="${rc.contextPath}/static/shop/images/card.png" height="122" width="122" alt=""></a>
		                                        <div class="product-r">
		                                            <p>阿拉丁玛特益多宝 ,${card.cardName!''}</p>
		                                            <span>益多宝卡号：${card.cardNo!''}</span>
		                                            <span></span>
		                                        </div>
		                                    </div>
		                                </td>
		                                <td>
		                                    <div class="count">&yen;${card.totalCorpus!''}</div>
		                                </td>
		                                <td>
		                                    <div class="price">
		                                        <span>&yen; ${card.totalInterest!''}</span>
		                                    </div>
		                                </td>
		                                <td rowspan="1">
		                                    <div class="status">
		                                        <p>&yen; ${card.totalRebate!''}</p>
		                                    </div>
		                                </td>
		                                <td rowspan="1">
		                                    <div class="action">
		                                        <!--<a class="pay" href="#">去充值</a>-->
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


        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

    </body>
</html>
