<!DOCTYPE html>
<html>
    <head>
        <title>商品评论</title>
       <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="">

        <div class="container">
            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>商品评论</p>
            </div>

            <ul class="rank-ul fl wp100">
              <li class="active first">
                <p>全部</p>
                <span>（<#if mecoment??>${mecoment.allCommentCount}<#else>0</#if>）</span>
                <i></i>
              </li>
              <li>
                <p>好评</p>
                <span>（<#if mecoment??>${mecoment.goodCommentCount}<#else>0</#if>）</span>
                <i></i>
              </li>
              <li>
                <p>中评</p>
                <span>（<#if mecoment??>${mecoment.midCommentCount}<#else>0</#if>）</span>
                <i></i>
              </li>
              <li>
                <p>差评</p>
                <span>（<#if mecoment??>${mecoment.badCommentCount}<#else>0</#if>）</span>
                <i></i>
              </li>
            </ul>

            <div class="rank-wrap fl wp100">

                <div class="rank-box fl wp100" id="rankAll">
                	<#if mecoment?? && mecoment.allComment?exists && mecoment.allComment?size&gt;0>
		            	<#list mecoment.allComment as comment>
			            	<div class="rank-item">
			            		<img src="${rc.contextPath}/static/shop/picture/1.jpg">
			            		<span><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></span>
			            		<i>${comment.commentTime}</i>
			            		<ul class="rank">
		                        <span>评分：</span>
		                        <#assign score= 5 - comment.score />
			                    	<#if comment.score gt 0>
			                        	<#list 1..comment.score as row>
			                            	<li class="active"></li>
			                            </#list>
			                        </#if>   
			                        <#if score gt 0>
			                            <#list 1..score as row>
			                            	<li></li>
			                            </#list>
		                        </#if>
		                    </ul>
			            	<p>${comment.commentContent}</p>	
			                </div>
		                </#list>
		            </#if>
                </div>

                <div class="rank-box" id="rankGood" style="display:none;">
                    <#if mecoment?? && mecoment.goodComment?exists && mecoment.goodComment?size&gt;0>
		            	<#list mecoment.goodComment as comment>
			            	<div class="rank-item">
			            		<img src="${rc.contextPath}/static/shop/picture/1.jpg">
			            		<span><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></span>
			            		<i>${comment.commentTime}</i>
			            		<ul class="rank">
		                        <span>评分：</span>
		                        <#assign score= 5 - comment.score />
			                    	<#if comment.score gt 0>
			                        	<#list 1..comment.score as row>
			                            	<li class="active"></li>
			                            </#list>
			                        </#if>   
			                        <#if score gt 0>
			                            <#list 1..score as row>
			                            	<li></li>
			                            </#list>
		                        </#if>
		                        <li></li>
		                    </ul>
			            	<p>${comment.commentContent}</p>	
			                </div>
		                </#list>
		            </#if>
                </div>

                <div class="rank-box" id="rankMid" style="display:none;">
                    <#if mecoment?? && mecoment.midComment?exists && mecoment.midComment?size&gt;0>
		            	<#list mecoment.midComment as comment>
			            	<div class="rank-item">
			            		<img src="${rc.contextPath}/static/shop/picture/1.jpg">
			            		<span><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></span>
			            		<i>${comment.commentTime}</i>
			            		<ul class="rank">
		                        <span>评分：</span>
		                        <#assign score= 5 - comment.score />
			                    	<#if comment.score gt 0>
			                        	<#list 1..comment.score as row>
			                            	<li class="active"></li>
			                            </#list>
			                        </#if>   
			                        <#if score gt 0>
			                            <#list 1..score as row>
			                            	<li></li>
			                            </#list>
		                        	</#if>
		                    </ul>
			            	<p>${comment.commentContent}</p>	
			                </div>
		                </#list>
		            </#if>
                </div>

                <div class="rank-box" id="rankBad" style="display:none;">
                    <#if mecoment?? && mecoment.badComment?exists && mecoment.badComment?size&gt;0>
		            	<#list mecoment.badComment as comment>
			            	<div class="rank-item">
			            		<img src="${rc.contextPath}/static/shop/picture/1.jpg">
			            		<span><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></span>
			            		<i>${comment.commentTime}</i>
			            		<ul class="rank">
		                        <span>评分：</span>
		                        <#assign score= 5 - comment.score />
			                    	<#if comment.score gt 0>
			                        	<#list 1..comment.score as row>
			                            	<li class="active"></li>
			                            </#list>
			                        </#if>   
			                        <#if score gt 0>
			                            <#list 1..score as row>
			                            	<li></li>
			                            </#list>
		                        	</#if>
		                    </ul>
			            	<p>${comment.commentContent}</p>	
			                </div>
		                </#list>
		            </#if>
                </div>

            </div>

        </div><!-- /.container -->

        <script>
        $(function(){
            $('.rank-ul').find('li').click(function(event) {
                var thisIndex = $(this).index();
                $(this).addClass('active').siblings().removeClass('active');
                $('.rank-wrap').find('.rank-box').eq(thisIndex).show().siblings().hide();
            });
        })
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>