<!DOCTYPE html>
<html>
    <head>
        <title>搜索结果</title>

        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <script type="text/javascript">
	     $(document).ready(function() {
	        
	          String.prototype.format=function()  
				{  
				  if(arguments.length==0) return this;  
				  for(var s=this, i=0; i<arguments.length; i++)  
				    s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);  
				  return s;  
				};
				$("#publicSortParam").val("price");
	        	$("#publicSortType").val("asc");
	        	loadData("price","asc");
	            /*加载更多*/
	            $(window).scroll(function(event) {
	                if ($('body').height()-$(window).scrollTop()-$(this).height()<1) {
	                	if(parseInt($('#allPage').val()) < parseInt($('#currentPage').val())){
	                		return;
	                	}
	                    $(".result-wrap").find('.load-more').show();
	                    // 加载更多数据,之后隐藏load
	                    $('#currentPage').val(parseInt($('#currentPage').val()) + 1);
	                    loadData($("#publicSortParam").val(),$("#publicSortType").val());
	                    $(".result-wrap").find('.load-more').hide();
	                };
	            });
	            
		       $('#price').click(function(){
		        	$("#currentPage").val("1");
		        	var thisValue = $(this).find('input').val();
		        	if(thisValue=='1'){
		        		$(this).find("span").html("&darr;");
		        		$("#publicSortParam").val("price");
		        		$("#publicSortType").val("desc");
		        		$(this).find("input").val("0");
		        		//降序操作
		        		$('.result-wrap').html('<div class="load-more textC"><p>正在加载</p></div><div class="search-none fl wp100" style="display:none"><span></span><p>抱歉，没有找到符合条件的商品！</p></div>');
		        		loadData("price","desc");
		        	} else {
		        		$(this).find("span").html("&uarr;");
		        		$("#publicSortParam").val("price");
		        		$("#publicSortType").val("asc");
		        		$(this).find("input").val("1");
		        		//升序操作
		        		$('.result-wrap').html('<div class="load-more textC"><p>正在加载</p></div><div class="search-none fl wp100" style="display:none"><span></span><p>抱歉，没有找到符合条件的商品！</p></div>');
		        		loadData("price","asc");
		        	}
		        })
		        
		        $('#default').click(function(){
		        	$('#currentPage').val(1);
		        	$("#publicSortParam").val("default");
				    $("#publicSortType").val("desc");
		       		$('.result-wrap').html('<div class="load-more textC"><p>正在加载</p></div><div class="search-none fl wp100" style="display:none"><span></span><p>抱歉，没有找到符合条件的商品！</p></div>');
		       		loadData("default","desc");
		        })  
		        $('#sales').click(function(){
		       		$('#currentPage').val(1);
		       		$("#publicSortParam").val("sales");
				    $("#publicSortType").val("desc");
		       		$('.result-wrap').html('<div class="load-more textC"><p>正在加载</p></div><div class="search-none fl wp100" style="display:none"><span></span><p>抱歉，没有找到符合条件的商品！</p></div>');
		       		loadData("sales","desc");
		        })  
		            
		            
	
	        });
	        
       		 
       		 function loadData(sortParam,sortType){
	        	$.post('${rc.contextPath}/search/skuSearch',
						{curPage:$('#currentPage').val(),keywords:$('#keywords').val(),sortParam:sortParam,sortType:sortType,time:new Date().getTime()},
						function(data){
							if (data.responseCode != "0"){
								openModal('0',data.msg);
								return;
							}
							var skuList = data.data.results;
							if(skuList.length==0&&$('#currentPage').val()=="1"){   
								$(".result-wrap").find('.search-none').show();
							}else{
								var content = "";
								var template = "<a href='${rc.contextPath}/sku/skuDetailStatic.htm?skuCode={0}&v=${EnvironmentConstant.DEPLOY_VERSION}'><img src='{1}'><p>{2}</p><span>&yen; {3}</span></a>";
								for(var i=0; i<skuList.length; i++){
									content += template.format(skuList[i].skuCode,skuList[i].imgPath,skuList[i].name,skuList[i].priceNew);
								}
								
								$('.result-wrap').append(content);
								$('#allPage').val(data.data.totalPage);
								
							}
						}
					);
       		 }
       		 
       		 function search(){
        		var keywords=$("#keywords").val();
        		if(""==keywords){
        			alert("关键字不能为空");
        			return;
        		}
        		window.location.href="${rc.contextPath}/search/toSkuSearch?keywords="+keywords;
        	}
       		
        </script>
    </head>
    <body id="">
        <div class="container">
			
            <input type="hidden" id="publicSortType" value=""/>
            <input type="hidden" id="publicSortParam" value=""/>
            <input type="hidden" id="currentPage" value="1"/>
            <input type="hidden" id="allPage" value=""/>
            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <div class="head-input-wrap">
                    <span class="search"></span>
                    <input type="text" placeholder="搜索您想要的产品"  id="keywords" value=${keywords}>
                </div>
                <a href="javascript:search()" class="a-right">
                    <span class="">搜索</span>
                </a>
            </div>

            <ul class="result-menu">
                <li id="default">综合 </li>
                <li id="sales">热销 </li>
                <li id="price">价格 <span>&uarr;</span><input type="hidden" value="1"></li><!-- &darr; -->
            </ul>
         
            
            <div class="result-wrap">
				<div class="load-more textC">
                    <p>正在加载</p>
                </div>
                <!-- 未搜索到商品时显示 -->
                <div class="search-none fl wp100" style="display:none">
                    <span></span>
                    <p>抱歉，没有找到符合条件的商品！</p>
                </div>

             </div>    

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
