<!DOCTYPE html>
<html>
    <head>
        <title>如意消费卡</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->
    </head>
    <body id="ruyiDetail">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>如意消费卡</p>
                <a href="javascript:void(0)" class="a-right">
                    <span><div style="margin-top:-5px;">. . .</div></span>
                    <ul class="ruyiDetailMenu">
                        <span class="caret"></span>
                        <li>转入</li>
                        <li>收益</li>
                        <li>消费</li>
                        <li>转出</li>
                    </ul>
                </a>
            </div>

            <div class="ruyi-detail-wrap fl wp100">
                <ul>
                	<#if shiftToRecord?exists && shiftToRecord?size&gt;0>
                	<#list shiftToRecord as record>
                    <li>
                        <h5>转入</h5>
                        <span>${record.createTime}</span>
                        <i>+${record.tradeMoney}</i>
                    </li>
                    </#list>
                    <#else>
                    <div class="tips">暂无数据</div>
                    </#if>
                </ul>
                <ul style="display:none">
                	<#if incomeRecord?exists && incomeRecord?size&gt;0>
                	<#list incomeRecord as income>
                    <li>
                        <h5>收益</h5>
                        <span>${income.createTime}</span>
                        <i>+${income.tradeMoney}</i>
                    </li>
                   </#list>
                   <#else>
                    <div class="tips">暂无数据</div>
                    </#if>
                </ul>
                <ul style="display:none">
                	<#if consumeRecord?exists && consumeRecord?size&gt;0>
                	<#list consumeRecord as consume>
                    <li>
                        <h5>消费</h5>
                        <span>${consume.createTime}</span>
                        <i>-${consume.tradeMoney}</i>
                    </li>
                   </#list>
                   <#else>
                    <div class="tips">暂无数据</div>
                    </#if>
                </ul>
                <ul style="display:none">
                	<#if rollOffRecord?exists && rollOffRecord?size&gt;0>
                	<#list rollOffRecord as rollof>
                    <li>
                        <h5>转出</h5>
                        <span>${rollof.createTime}</span>
                        <i>-${rollof.tradeMoney}</i>
                    </li>
                    </#list>
                    <#else>
                    <div class="tips">暂无数据</div>
                    </#if>
                </ul>
            </div>

        </div><!-- /.container -->

        <script>
            $(function(){
                $('.a-right').click(function(event) {
                    $('.ruyiDetailMenu').toggle();
                });
                $('.ruyiDetailMenu').find('li').click(function(event) {
                    var thisIndex = $(this).index()-1;
                    $('.ruyi-detail-wrap').find('ul').eq(thisIndex).show().siblings().hide();
                });
            })
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
