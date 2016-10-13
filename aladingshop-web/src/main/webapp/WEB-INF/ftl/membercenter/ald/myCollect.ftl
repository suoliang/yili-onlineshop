<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的阿拉丁- 我的收藏</title>
       <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body class="collect">

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
                                <th class="w90 fl">&nbsp;</th>
                                <th class="w120 fl">单价</th>
                                <th class="w189 fl">&nbsp;</th>
                                <th class="w189 fl">操作</th>
                            </tr>
                        </thead>
                        <!-- 我的收藏 -->
                        <#if collectionSkuList??>
                        <tbody>
                        <#list collectionSkuList as collectSku>
                        <tr>
                                <td>
                                    <div class="product fl">
                                        <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${collectSku.skuCode}" target="_blank"><img src="${collectSku.imgPath}" height="122" width="122" alt=""></a>
                                        <div class="product-r">
                                            <p>${collectSku.name}</p>
                                            <span>品牌：${collectSku.brandName}</span>
                                            <span>类型：${collectSku.size}</span>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="count"></div>
                                </td>
                                <td>
                                    <div class="price">
                                        <!--<p>&yen;${collectSku.priceOld}</p>-->
                                        <span>&yen;${collectSku.priceNew}</span>
                                    </div>
                                </td>
                                <td>
                                    <div class="status"></div>
                                </td>
                                <td>
                                    <div class="action">
                                        <a class="pay" href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${collectSku.skuCode}" target="_blank">去购买</a>
                                        <a class="cancel" title="n" href="${rc.contextPath}/myAld/cancelCollect.htm/${collectSku.skuCode}/${currPage}">取消收藏</a>
                                    </div>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                       </#if><!-- end-->
                    </table>

                </div>
            </div>
        </div>

        <div class="page container oh">
            <div class="tcdPageCode fr" id="collect_page"></div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <script>
            $(".tcdPageCode").createPage({
                pageCount:${pageSize!1},
                current:${currPage!1},
                backFn:function(p){
                window.location.href = "${rc.contextPath}/myAld/collect.htm/"+p+"/"+new Date().getTime();
                    console.log(p);
                }
            });
        </script>
    </body>
</html>
