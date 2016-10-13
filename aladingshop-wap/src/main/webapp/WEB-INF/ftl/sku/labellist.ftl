<!DOCTYPE html>
<html>
    <head>
        <title>商品列表</title>
       <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <!-- 通用部分 结束 -->

    </head>
    <body id="home">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>${labelName}</p>
            </div>

            <div class="discount">

                <div class="discount-wrap">
                    <ul>
                    	<#if result?exists && result?size&gt;0>
                    	<#list result as sku>
                        <li>
                            <a href="${rc.contextPath}/sku/skuDetailStatic.htm?skuCode=${sku.skuCode}&v=${EnvironmentConstant.DEPLOY_VERSION}">
                                <img src="${sku.imgPath}">
                                <p>${sku.name}</p>
                                <span>&yen; ${sku.priceNew}</span>
                            </a>
                        </li>
                        </#list>
                        </#if>
                    </ul>
                    <div class="load-more textC">
                    	<input type="hidden" id="currentPage" value="${currentPage}"/>
                    	<input type="hidden" id="allPage" value="${allPage}"/>
                        <p>正在加载</p>
                    </div>
                    <div class="load-none textC">
                        <p>哎哟，没有数据了</p>
                    </div>
                </div>
            </div>

        </div><!-- /.container -->

        <script type="text/javascript">
        $(document).ready(function() {
            /*加载更多*/
            $(window).scroll(function(event) {
                if ($('body').height()-$(window).scrollTop()-$(this).height()<1) {
                	if(parseInt($('#allPage').val()) < parseInt($('#currentPage').val())){
                		return;
                	}
                    $(".discount-wrap").find('.load-more').show();
                    // 加载更多数据,之后隐藏load
                    loadData();
                    $(".discount-wrap").find('.load-more').hide();
                };
            });

        });
        
        String.prototype.format=function()  
			{  
			  if(arguments.length==0) return this;  
			  for(var s=this, i=0; i<arguments.length; i++)  
			    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
			  return s;  
			};
			  
        function loadData(){
        	$.post('${rc.contextPath}/skuLabel/updateList',
					{currentPage:$('#currentPage').val(),time:new Date().getTime()},
				function(data){
						if (data.responseCode != "0"){
							openModal('0',data.msg);
							return;
						}
						
						var skuList = data.data;
						var content = "";
						var template = "<li><a href='${rc.contextPath}/sku/skuDetailStatic.htm?skuCode={0}&v=${EnvironmentConstant.DEPLOY_VERSION}'><img src='{1}'><p>{2}</p><span>&yen; {3}</span></a></li>";
						for(var i=0; i<skuList.length; i++){
							content += template.format(skuList[i].skuCode,skuList[i].imgPath,skuList[i].name,skuList[i].priceNew);
						}
						
						$('.discount-wrap').find('ul').append(content);
						$('#currentPage').val(parseInt($('#currentPage').val()) + 1);
						if(parseInt($('#allPage').val()) == parseInt($('#currentPage').val())){
							$(".discount-wrap").find('.load-none').show();
						}
					}
				);
        }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>