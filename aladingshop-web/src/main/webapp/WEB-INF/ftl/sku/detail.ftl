<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - ${result.name}/${result.selectedSku.color}详情</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
        <script type="text/javascript" src="${rc.contextPath}/static/web-js/shoppingCart.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
        <script type="text/javascript" src="${rc.contextPath}/static/web-js/amountCart.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    </head>
    <body class="">
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
        
        <!-- 快速登陆框 -->
        <#include "/common/quickLogin.ftl" />

		<div class="container oh"> 
	        <div class="nav fl">
	        	<#list catagorys as category>
	        		 <a <#if category.level==1>href="${rc.contextPath}/category/${category.code}.htm"  target="_blank" <#else>href="${rc.contextPath}/search/category?code=${category.code}&v=${EnvironmentConstant.DEPLOY_VERSION}"</#if> >${category.name}&gt;</a>
	        	</#list>
	            ${result.name}
	        </div>
		</div>
		
        <div class="detail-box container">
            <div id="detail-l">
                <div class="detail-s-img">
                	<#if result.skuImages?exists>
                		<#list result.skuImages as img>
                			<#if img_index ==0><#assign firstImage=img /></#if>
                			<#if img_index gt 3><#break></#if>
                			<a href="javascript:void(0)"><img src="${img}" height="98" width="98" title="${result.name}/${result.selectedSku.color}" mid="${img}" big="${img}"></a>
                		</#list>
                	</#if>
                </div>
                <div class="detail-m-img">
                    <a href="${firstImage}">
                        <img class="jqzoom" src="${firstImage}" rel="${firstImage}" width="423" height="423">
                    </a>
                </div>
            </div>

            <div id="detail-r">
                <p class="name">${result.name}</p>
                <p>商品编号：${result.skuNo}</p>
                <!-- <原价：&yen; ${result.priceOld}</i> -->
                <span class="price">促销价：&yen; ${result.priceNew}</span>
                <div class="classify">
                    <span>规格：</span>
                    <div class="classify-wrap">
                    	<#if result.sameCategorySkus?exists>
                    		<#list result.sameCategorySkus as sku>
                    			<a <#if result.selectedSku.skuCode == sku.skuCode>class="active"</#if> href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${sku.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}">
		                            <img src="${sku.imgPath}" height="40" width="40" title="${sku.name}">
		                            <#if sku.color !="" || sku.size !=""><p>${sku.color}${sku.size}</p></#if>
                        		</a>
                    		</#list>
                    	</#if>
                    </div>
                </div>
                <div class="count">
                    <span>数量：</span>
                    <div class="reduce" onclick="setAmount.reduce()">-</div>
                    <input class="num" value="1" id="cart-num"
                      onkeyup="setAmount.modify();" 
                	  onblur="setAmount.modify();" 
					  autocomplete="off" value="1">
                    <div class="add" onclick="setAmount.add()">+</div>
                    <!--span>库存：200件</span-->
                </div>
                <div class="detail-r-btn">
                    <button class="add-cart" onclick="addToCart('${result.selectedSku.skuCode}')">加入购物车</button>
                   <!-- <button class="buy-now">立即购买</button>-->
                </div>
                <div class="favorite-share">
                	<!-- open-loginBox --> 
                    <a class="favorite" title="未收藏，点击收藏" href="javascript:void(0)"></a>
                    <input type="hidden" id="opeationDom" value=".favorite"/>
                    <input type="hidden" id="isNoCollect" value=""/>
                    <div class="share">
                        <a class="share-btn" href="javascript:void(0)" title="分享"></a>
                        <div class="bdsharebuttonbox"><a title="分享到QQ空间" href="#" class="bds_qzone" data-cmd="qzone"></a><a title="分享到新浪微博" href="#" class="bds_tsina" data-cmd="tsina"></a><a title="分享到腾讯微博" href="#" class="bds_tqq" data-cmd="tqq"></a><a title="分享到微信" href="#" class="bds_weixin" data-cmd="weixin"></a></div>
                        <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":[],"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
                    </div>
                </div>
            </div>
        </div>

        <div class="detail-main container">
            <!-- 左侧 开始 -->
            <div class="detail-main-l">
				<#if result.keyWords?exists && result.keyWords?size&gt;0>
                <!-- 关键词搜索 -->
                <h3 class="detail-l-tit">关键词搜索</h3>
                <ul class="detail-l-box1">
                	<#list keys as kw>
	                    <li><a href="${rc.contextPath}/search/q?keywords=${kw}&v=${EnvironmentConstant.DEPLOY_VERSION}">${kw}</a></li>
                    </#list>
                </ul>
				</#if>
                <!-- 精品推荐 -->
                <#if tjskus?exists && tjskus?size&gt;0>
                <h3 class="detail-l-tit">精品推荐</h3>
                <ul class="detail-l-box2">
                	<#list tjskus as goods>
	                    <li>
	                        <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${goods.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}" target="_blank">
	                        	<div class="verticalAlign">
	                        		<img src="${goods.imgPath}" title="${goods.name}">
                        		</div>
	                        </a>
	                        <p>${goods.name}</p>
	                        <span class="price">&yen; ${goods.priceNew}</span>
	                    </li>
                    </#list>
                </ul>
                </#if>
            </div>
            <!-- 左侧 结束 -->

            <!-- 右侧 开始 -->
            <div class="detail-main-r">
            	<#if result.linkSkus?exists && result.linkSkus?size&gt;0>
                <!-- 同类商品 -->
                <ul class="detail-r-tit">
                    <li class="active"><a href="javascript:void(0)">同类商品</a></li>
                </ul>
                <div class="detail-slider unslider">
                    <ul>
                		<#assign pagesize= 5 />
                		<#assign page= (result.linkSkus?size -1) / pagesize + 1 />
                		<#list 1..page as row>
                			<li>
                				<#list result.linkSkus as goods>
                					<#if (goods_index >= (row-1)*pagesize) && (goods_index < row*pagesize)>
			                            <div class="box">
			                                <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${goods.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}" target="_blank"><img src="${goods.imgPath}" height="216" width="216" title="${goods.name}"></a>
			                                <p>${goods.name}</p>
			                                <span class="price">&yen; ${goods.priceNew}</span>
			                            </div>
		                            </#if>
	                            </#list>
                    		</li>
                		</#list>
                    </ul>
                    <a href="javascript:void(0)" class="unslider-arrow prev">Previous slide</a>
                    <a href="javascript:void(0)" class="unslider-arrow next">Next slide</a>
                </div>
                </#if>

                <!-- 详情 -->
                <ul class="detail-r-tit" id="detailRMenu">
                    <li class="active"><a href="javascript:void(0)">商品详情</a></li>
                    <!--li><a href="javascript:void(0)">商品规格</a></li-->
                    <li><a href="javascript:void(0)">商品评论</a></li>
                </ul>

                <div id="detail-wrap">
                	<#if result.graphicDetails?exists && result.graphicDetails?size&gt;0>
                    <!-- 图片详情 -->
                    <div id="detail-img" class="detail-wrap-box">
                    	<#list result.graphicDetails as grap>
                    		<p class="textC"><img src="${grap.get('url')}" title=""></p>
                    	</#list>
                    </div>
                    </#if>
      				<!--商品规格              
					<#if result.attr?exists && result.attr?size&gt;0>
                    <div id="detail-decribe" class="detail-wrap-box">
                        <p class="detail-wrap-tit">基本信息</p>
                        <ul>
                        	<#list result.attr as at>
	                            <li <#if at_index==0>class="mT0" </#if> >
	                                <h6>${at.name}</h6>
	                                <span>${at.value}</span>
	                            </li>
                            </#list>
                        </ul>
                    </div>
					</#if>  -->
                    <!-- 商品评价 -->
                    <div id="rank" class="detail-wrap-box">
					    <p class="detail-wrap-tit">商品评价</p>
					    <div id="echarts" style="height:200px;"></div>
					    <ul class="rankWrap-menu">
					        <li class="active"><a href="javascript:void(0)">全部（${mecoment.allCommentCount!0}）</a></li>
					        <li><a href="javascript:void(0)">好评（${mecoment.goodCommentCount!0}）</a></li>
					        <li><a href="javascript:void(0)">中评（${mecoment.midCommentCount!0}）</a></li>
					        <li><a href="javascript:void(0)">差评（${mecoment.badCommentCount!0}）</a></li>
					    </ul>
					    <div id="rankWrap">
					        <div id="rankAll" class="rank-box">
					        	<#if mecoment.allComment?exists && mecoment.allComment?size&gt;0>
					            <ul id="allComment">
					            	<#list mecoment.allComment as comment>
					                <li>
					                    <div class="rank-box-a">
					                        <img src="${rc.contextPath}/static/shop/picture/user.jpg" height="40" width="40" title="">
					                        <p><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></p>
					                    </div>
					                    <div class="rank-box-b">
					                        <p>${comment.commentContent}</p>
					                        <#if comment.skuColor??><span>颜色：${comment.skuColor}</span></#if>
					                        <#if comment.skuSize??><i>尺寸：${comment.skuSize}</i></#if>
					                    </div>
					                    <ul class="ranking">
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
					                    <div class="rank-box-d">${comment.commentTime}</div>
					                </li>
					                </#list>
					            </ul>
					            <!-- 分页 -->
					            <div class="page">
					                <div class="tcdPageCode" id="allPage"></div>
					            </div>
					            </#if>
					        </div>
					        <div id="rankGood" class="rank-box">
					        	<#if mecoment.goodComment?exists && mecoment.goodComment?size&gt;0>
					            <ul id="allGood">
					            	<#list mecoment.goodComment as comment>
					                <li>
					                    <div class="rank-box-a">
					                        <img src="${rc.contextPath}/static/shop/picture/user.jpg" height="40" width="40" title="">
					                        <p><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></p>
					                    </div>
					                    <div class="rank-box-b">
					                        <p>${comment.commentContent}</p>
					                        <#if comment.skuColor??><span>颜色：${comment.skuColor}</span></#if>
					                        <#if comment.skuSize??><i>尺寸：${comment.skuSize}</i></#if>
					                    </div>
					                    <ul class="ranking">
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
					                    <div class="rank-box-d">${comment.commentTime}</div>
					                </li>
					                </#list>
					            </ul>
					            <!-- 分页 -->
					            <div class="page">
					                <div class="tcdPageCode" id="goodPage"></div>
					            </div>
					            </#if>
					        </div>
					        <div id="rankMid" class="rank-box">
					        	<#if mecoment.midComment?exists && mecoment.midComment?size&gt;0>
					            <ul id="allMid">
					            	<#list mecoment.midComment as comment>
					                <li>
					                    <div class="rank-box-a">
					                        <img src="${rc.contextPath}/static/shop/picture/user.jpg" height="40" width="40" title="">
					                        <p><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></p>
					                    </div>
					                    <div class="rank-box-b">
					                        <p>${comment.commentContent}</p>
					                        <#if comment.skuColor??><span>颜色：${comment.skuColor}</span></#if>
					                        <#if comment.skuSize??><i>尺寸：${comment.skuSize}</i></#if>
					                    </div>
					                    <ul class="ranking">
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
					                    <div class="rank-box-d">${comment.commentTime}</div>
					                </li>
					                </#list>
					            </ul>
					            <!-- 分页 -->
					            <div class="page">
					                <div class="tcdPageCode" id="midPage"></div>
					            </div>
					            </#if>
					        </div>
					        <div id="rankBad" class="rank-box">
					        	<#if mecoment.badComment?exists && mecoment.badComment?size&gt;0>
					            <ul id="allBad">
					            	<#list mecoment.badComment as comment>
					                <li>
					                    <div class="rank-box-a">
					                        <img src="${rc.contextPath}/static/shop/picture/user.jpg" height="40" width="40" title="">
					                        <p><#if comment.isAnonymous == 'y'>匿名用户<#else>${comment.memberName?substring(0,1)}**</#if></p>
					                    </div>
					                    <div class="rank-box-b">
					                        <p>${comment.commentContent}</p>
					                        <#if comment.skuColor??><span>颜色：${comment.skuColor}</span></#if>
					                        <#if comment.skuSize??><i>尺寸：${comment.skuSize}</i></#if>
					                    </div>
					                    <ul class="ranking">
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
					                    <div class="rank-box-d">${comment.commentTime}</div>
					                </li>
					                </#list>
					            </ul>
					            <!-- 分页 -->
					            <div class="page">
					                <div class="tcdPageCode" id="badPage"></div>
					            </div>
					            </#if>
					    </div>
					</div>
                </div>
            </div>
            <!-- 右侧 结束 -->
        </div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl">
        <!-- 底部 结束 -->
        <script>
        <!-- 当退出登录时，重置页面函数 -->
		function exitFun(){
            $('.favorite').attr('title', '未收藏，点击收藏');
            $('.favorite').removeClass('active');
            $("#isCollect").val("");
		}
        
        <!-- 当快速登录时，检查当前商品收藏状态函数 -->
		function loginFun(){
			icCollect();
		}
		
		<!-- 动态加载当前用户收藏商品状态 -->
		function icCollect(){
			$.ajax({ 
	        	type: "post", 
				url: "${rc.contextPath}/memberSkuCollect/isCollect", 
				async:false,
				data: {skuCode:'${result.selectedSku.skuCode}', time: new Date().getTime()}, 
				dataType: "json", 
				success: function(result){ 
					if(result.data =="y"){
						$("#isNoCollect").val("n");//用户快速登陆后如果当前商品已经收藏，则不在继续点击操作
			         	$('.favorite').addClass('active');
		                $('.favorite').attr('title', '已收藏，点击取消收藏');
		             } else {
		                $('.favorite').attr('title', '未收藏，点击收藏');
		                $('.favorite').removeClass('active');
		             }	
	        	} 
	
			});
		}
        <!-- javascript -->
        <!-- 其他交互 开始-->
        $(function(){
            /*收藏按钮*/
            $('.favorite').click(function(event) {
            	if($("#isNoCollect").val() == "n"){
            		$("#isNoCollect").val("y");
            		
            		return;
            	}
            
            	var title = $(this).attr('title');
                var type = 0; //0:收藏 	1:取消收藏
                if(title=="已收藏，点击取消收藏"){
                    type = 1;
                }
                
              	 /*收藏ajax*/
			    $.post("${rc.contextPath}/sku/collect",{skuCodes:'${result.selectedSku.skuCode}',type:type},function(result){
			         if(result.responseCode==201){
		         		$('#loginBox').fadeIn();
						$('#loginBox').find('.login-left').animate({'margin-left': '-194px', 'opacity': '1'}, 300);
						$('#opeation').val(".favorite");
			         	return;
			         }else if(result.responseCode==500){
			         	var errmsg = type == 0 ?'收藏失败':'取消收藏失败';
			         	openAddCartModal(0,errmsg);
			         	return;
			         }
			         
			         if(title=="未收藏，点击收藏"){
			         	$('.favorite').addClass('active');
			            $('.favorite').attr('title', '已收藏，点击取消收藏');
			            openAddCartModal(1,'收藏成功');
			         } else {
			            $('.favorite').attr('title', '未收藏，点击收藏');
			            $('.favorite').removeClass('active');
			            openAddCartModal(1,'取消收藏成功');
			         }	
				});
                
            });

            $('.rankWrap-menu').find('li').each(function(index, el) {
                $(this).click(function(event) {
                    $(this).addClass('active').siblings().removeClass('active');
                    $('#rankWrap').find('.rank-box').eq(index).fadeIn().siblings().hide();
                });
            });
        })
        </script>
        <!-- 其他交互 结束-->

        <!-- 图片放大镜 开始 -->
        <script type="text/javascript" src="${rc.contextPath}/static/shop/js/jquery.imagezoom.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
	            $(".jqzoom").imagezoom();
	
	                $('.detail-s-img').find('a').each(function(index, el) {
	                    $(this).hover(function() {
	                        $(this).addClass('img-on').siblings().removeClass('img-on');
	                        $(".jqzoom").attr('src',$(this).find("img").attr("mid"));
	                        $(".jqzoom").attr('rel',$(this).find("img").attr("big"));
	                    });
	                });
	                
	             icCollect();
	             $("#isNoCollect").val("");
             });
        </script>
        <!-- 图片放大镜 结束 -->
        <script src="${rc.contextPath}/static/shop/js/unslider.js"></script><!-- banner JS -->
        <script>
            $(function(){
                var unslider = $('.detail-slider').unslider({
                    speed: 600,
                    delay: 3000,
                    complete: function() {},
                    keys: true,
                    dots: true,
                    fluid: false,
                    autoplay:false
                });

                $('.unslider-arrow').click(function() {
                    var fn = this.className.split(' ')[1];
                    unslider.data('unslider')[fn]();
                });
            })
        </script>
        <!-- 底部开始 -->
        <!--#include "/common/other.ftl" /-->
        <script>
            $(function(){
                for (var i = 0; i < 6; i++) {
                    var hide_box = $(".hide-menu-box").eq(0).clone(true);
                    $('#hide-menu').append(hide_box);
                };
            })
            
        </script>
        
        <!-- 评分图表 开始-->
		<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
		<script type="text/javascript">
		    // 路径配置
		    require.config({
		        paths: {
		            echarts: 'http://echarts.baidu.com/build/dist'
		        }
		    });
		
		    // 使用
		    require(
		        [
		            'echarts',
		            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		        ],
		        function(ec) {
		            // 基于准备好的dom，初始化echarts图表
		            var myChart = ec.init(document.getElementById('echarts'));
		            var good = ${mecoment.goodCommentCount!0},bad = ${mecoment.badCommentCount!0},mid = ${mecoment.midCommentCount!0},goodColor='#ba2454';/*好评，差评，中评数量*/
					if(good == 0 && bad == 0 && mid == 0){
						good = 100;
						goodColor='#f1f1f1';
					}
		
		            var option = {
		                color:['#b8cceb', goodColor,'#c0fff8'],
		                tooltip: {
		                    trigger: 'item',
		                    formatter: "{b} ({d}%)"
		                },
		                series: [{
		                    type: 'pie',
		                    itemStyle: {
		                       normal: {
		                           label: {
		                                show: true,
		                                formatter : '{b}：{d}%',
		                                textStyle: {
		                                    color: '#333',
		                                    fontSize:14
		                                }
		                            }
		                       }
		                   },
		                    radius: '70%',
		                    center: ['50%', '50%'],
		                    data: [{
		                        value: mid,
		                        name: '中评率'
		                    }, {
		                        value: good,
		                        name: '好评率'
		                    }, {
		                        value: bad,
		                        name: '差评率'
		                    }]
		                }]
		            };
		
		            // 为echarts对象加载数据
		            myChart.setOption(option);
		        }
		    );
		</script>
		<!-- 评分图表 结束-->
		
		<script type="text/javascript">
			String.prototype.format=function()  
			{  
			  if(arguments.length==0) return this;  
			  for(var s=this, i=0; i<arguments.length; i++)  
			    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
			  return s;  
			};  
			
			//评论模板
			var content = "<li><div class='rank-box-a'><img src='${rc.contextPath}/static/shop/picture/user.jpg' height='40' width='40' title=''><p>{0}</p></div><div class='rank-box-b'><p>{1}</p><span>颜色：{2}</span><i>尺寸：{3}</i></div><ul class='ranking'>{4}{5}</ul><div class='rank-box-d'>{6}</div></li>";
			$(function(){
				 $("#allPage").createPage({
			        pageCount:${mecoment.allPage!0},
			        current:${mecoment.allCurrentPage},
			        backFn:function(p){
			        	  $.post("${rc.contextPath}/sku/commentSkipPage",{skuCode:'${result.selectedSku.skuCode}',target:p,level:0},function(result){
				         	var comments = result.data;
				         	var html = "";
				         	for(var i=0; i<comments.length; i++){
						         	var memname = comments[i].memberName.substring(0,1) + "**";
						         	if(comments[i].isAnonymous == "y"){
						         		memname = "匿名用户";
						         	}
						         	var startContent = "";
						         	var noStartContent = "";
						         	//星级拼接
						         	for(var j=comments[i].score; j>0; j--){
						         		startContent += "<li class='active'></li>";
						         	}
						         	
						         	for(var k= 5 - comments[i].score; k>0; k--){
						         		noStartContent = "<li></li>";
						         	}
					         	
					         	html+=content.format(memname,comments[i].commentContent,comments[i].skuColor,comments[i].skuSize,startContent,noStartContent,comments[i].commentTime);  
				         	}
						    
						   $("#allComment").html(html);
						});
			        }
			    }); 
			    
		         $("#goodPage").createPage({
			        pageCount:${mecoment.goodPage!0},
			        current:${mecoment.goodCurrentPage},
			        backFn:function(p){
			           $.post("${rc.contextPath}/sku/commentSkipPage",{skuCode:'${result.selectedSku.skuCode}',target:p,level:1},function(result){
				         	var comments = result.data;
				         	var html = "";
				         	for(var i=0; i<comments.length; i++){
						         	var memname = comments[i].memberName.substring(0,1) + "**";
						         	if(comments[i].isAnonymous == "y"){
						         		memname = "匿名用户";
						         	}
						         	var startContent = "";
						         	var noStartContent = "";
						         	//星级拼接
						         	for(var j=comments[i].score; j>0; j--){
						         		startContent += "<li class='active'></li>";
						         	}
						         	
						         	for(var k= 5 - comments[i].score; k>0; k--){
						         		noStartContent = "<li></li>";
						         	}
					         	
					         	html+=content.format(memname,comments[i].commentContent,comments[i].skuColor,comments[i].skuSize,startContent,noStartContent,comments[i].commentTime);  
				         	}
						    
						   $("#allGood").html(html);
						});
			        }
			    });
		    	
		    	 $("#midPage").createPage({
			        pageCount:${mecoment.midPage!0},
			        current:${mecoment.midCurrentPage},
			        backFn:function(p){
			           $.post("${rc.contextPath}/sku/commentSkipPage",{skuCode:'${result.selectedSku.skuCode}',target:p,level:2},function(result){
				         	var comments = result.data;
				         	var html = "";
				         	for(var i=0; i<comments.length; i++){
						         	var memname = comments[i].memberName.substring(0,1) + "**";
						         	if(comments[i].isAnonymous == "y"){
						         		memname = "匿名用户";
						         	}
						         	var startContent = "";
						         	var noStartContent = "";
						         	//星级拼接
						         	for(var j=comments[i].score; j>0; j--){
						         		startContent += "<li class='active'></li>";
						         	}
						         	
						         	for(var k= 5 - comments[i].score; k>0; k--){
						         		noStartContent = "<li></li>";
						         	}
					         	
					         	html+=content.format(memname,comments[i].commentContent,comments[i].skuColor,comments[i].skuSize,startContent,noStartContent,comments[i].commentTime);  
				         	}
						    
						   $("#allMid").html(html);
						 });
			        }
			    });
			    
			    $("#badPage").createPage({
			         pageCount:${mecoment.badPage!0},
			         current:${mecoment.badCurrentPage},
			        backFn:function(p){
			           $.post("${rc.contextPath}/sku/commentSkipPage",{skuCode:'${result.selectedSku.skuCode}',target:p,level:3},function(result){
				         	var comments = result.data;
				         	var html = "";
				         	for(var i=0; i<comments.length; i++){
						         	var memname = comments[i].memberName.substring(0,1) + "**";
						         	if(comments[i].isAnonymous == "y"){
						         		memname = "匿名用户";
						         	}
						         	var startContent = "";
						         	var noStartContent = "";
						         	//星级拼接
						         	for(var j=comments[i].score; j>0; j--){
						         		startContent += "<li class='active'></li>";
						         	}
						         	
						         	for(var k= 5 - comments[i].score; k>0; k--){
						         		noStartContent = "<li></li>";
						         	}
					         	
					         	html+=content.format(memname,comments[i].commentContent,comments[i].skuColor,comments[i].skuSize,startContent,noStartContent,comments[i].commentTime);  
				         	}
						    
						   $("#allBad").html(html);
						 });
			        }
			    });
		    
		   });
		</script>
    </body>
</html>
