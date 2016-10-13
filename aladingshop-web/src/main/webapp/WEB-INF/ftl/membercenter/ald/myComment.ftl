<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>我的阿拉丁- 我的评论</title>
       <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
    </head>
    <body class="assess">

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
                                <th class="w350 fl">评论</th>
                                <th class="w118 fl" colspan="2">评论时间</th>
                               <!-- <th class="w120 fl">操作</th>-->
                            </tr>
                        </thead>
                        <!-- 我的评论 -->
                        <#if memberCommentList ??>
                        <tbody>
                        <#list memberCommentList as commentSku>
                         <tr>
                                <td>
                                    <div class="product fl mT20">
                                        <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${commentSku.skuCode}" target="_blank"><img src="${commentSku.skuImg}" height="122" width="122" alt=""></a>
                                        <div class="product-r">
                                            <p title="${commentSku.skuName}">${commentSku.skuName}</p>
                                            <span>品牌：${commentSku.skuColor}</span>
                                            <span>类型：${commentSku.skuSize}</span>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="rank-box">
                                        <span class="fl">评分：</span>
                                        <ul class="ranking oh">
                                        <#assign score= 5 - commentSku.score />
					                    	<#if commentSku.score gt 0>
					                        	<#list 1..commentSku.score as row>
					                            	<li class="active"></li>
					                            </#list>
					                        </#if>   
					                        <#if score gt 0>
					                            <#list 1..score as row>
					                            	<li></li>
					                            </#list>
					                        </#if>
                                        </ul>
                                        <p>评论：${commentSku.commentContent}</p>
                                    </div>
                                </td>
                                <td>
                                    <div class="price">${commentSku.commentTime}</div>
                                </td>
                                <td rowspan="1">
                                    <div class="action">
                                      <!--  <a class="pay" href="javascript:void(0)">去评论</a>
                                        <a class="disabled" href="javascript:void(0)">已评论</a>
                                        -->
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
            <div class="tcdPageCode fr" ></div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->

        <!-- javascript -->
        <script>
            $(".tcdPageCode").createPage({
                pageCount:${pageSize!1},
                current:${currPage!1},
                backFn:function(p){
                window.location.href = "${rc.contextPath}/myAld/comment.htm/"+p+"/"+new Date().getTime();
                    console.log(p);
                }
            });
        </script>
    </body>
</html>
