<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8" />
        <title>阿拉丁玛特 - 搜索结果</title>
       <#include "/common/common.ftl" />
        <!-- 分页 -->
	   <script src="${rc.contextPath}/static/shop/js/jquery.page.js" type="text/javascript"></script>
	   
	   <script type="text/javascript" language="javascript">
	       $(function(){ 
	         $(".tcdPageCode").createPage({
		        pageCount:${pageSet.totalPage},
		        current:${pageSet.curPage},
		        backFn:function(p){
		           var searchUrl = window.location.search;
		           var surl = "";
		           if(searchUrl.indexOf("curPage") > 0){
		              surl = searchUrl.substring(0,searchUrl.indexOf("curPage"));
		           }
		           var url = "";
		           if(surl==""){
		           	  url = searchUrl+"&curPage="+p;
		           }else{
		           	  url = surl + "curPage="+p;
		           }
		           window.location.href = url;
		        }
		    });
		    
		    
		});
		/**
		function queryPrice(){
			var startPrice = $("#txtStartPrice").val();
	    	var endPrice = $("#txtEndPrice").val();
	    	if(startPrice=='' || endPrice==''){
	    		return ;
	    	}
	    	var price = startPrice+"-"+endPrice;
	    	
	    	window.location.href = "${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price="+price+"&index=${index}";
		}*/
       
    	</script>
    </head>
    <body class="fixed-header">

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


<!--
        <div class="search-opt container">
            <p class="fl wp100 mT0">
                <span class="fl"><#if index=='keyword'>全部结果 > <a class="red" href="${rc.contextPath}/search/q?keywords=${keywords}">${keywords}</a> <#elseif index='category'>${parentCategory.name} >&nbsp;<a class="red" href="${rc.contextPath}/search/category?code=${selcategory.code}">${selcategory.name}</a><#elseif index='brand'>品牌：<a class="red" href="${rc.contextPath}/search/brand?code=${selbrand.brandCode}">${selbrand.brandName}</#if></a></span>
                <span class="fr">找到 <#if pageSet.amount?exists>${pageSet.amount?c}<#else>0</#if> 件相关商品</span>
            </p>
            <div class="opt-box fl">
               <#if categoryList?exists>
                <ul class="price-ul">
                   <p>分类目录：</p>
                   <div class="li-wrap oh">
	                  <#list categoryList as category>
	               	 	<li>
	                        <a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${category.code!''}&brandCode=${selbrand.brandCode!''}&price=${price!''}&index=${index}">
	                         ${category.name}
	                        </a>
	                    </li>
	                   </#list>
                   </div>
                </ul>
              </#if>
            
            <#if brandList?exists && (brandList?size > 0)>
            
                <p class="fl wp100 mT0">
                    <span class="fl">品牌：</span>
                     <#if (brandList?size>10)> 
                       <a class="fr more" href="javascript:void(0)">更多<span></span></a> 
                     </#if>
                </p>
             
                <ul class="brand-ul">
                  
                   <#list brandList as brand>
                  
                    <li  <#if (brand_index <=5)>class='mT0'</#if>>
                        <a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${brand.brandCode!''}&price=${price!''}&index=${index}">
                            <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${brand.brandLogoWebUrl!''}" alt="${brand.brandName!''}">
                            <span class="name">${brand.brandName}</span>
                        </a>
                    </li>
                   </#list>
                 
                </ul>
              </#if>
            </div>
            <div class="opt-box fl">
                <ul class="price-ul">
                    <p>价格：</p>
                    <li><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=0-50&index=${index}">0 ~ &yen;50</a></li>
                    <li><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=50-100&index=${index}">&yen;50 ~ &yen;100</a></li>
                    <li><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=100-150&index=${index}">&yen;100 ~ &yen;150</a></li>
                </ul>
                 <div class="price-input">
                    <input type="text" id="txtStartPrice" 
							type="number"  maxlength="11"
							onkeyup="this.value=this.value.replace(/\D/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')"   />
                    <span>~</span>
                    <input type="text" id="txtEndPrice" 
                     		type="number"  maxlength="11"
							onkeyup="this.value=this.value.replace(/\D/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')"   />
                    <button onclick="queryPrice()">确定</button>
                </div>
            </div>
            <div class="choosed-opt fl">
                <p>已选条件：</p>
                <ul class="choosed-opt-ul">
                  <#if index!='brand' && selbrand?exists && selbrand.brandCode?exists && (selbrand.brandCode?size > 0) >
                    <li class="brand"><span>${selbrand.brandName}</span><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&price=${price!''}&index=${index}">X</a></li>
                  </#if>
                  
                  <#if index!='category' && selcategory?exists && selcategory.code?exists && (selcategory.code?size > 0) && selcategory.code?size!='' && keywords!=''>
                    <li class="category"><span>${selcategory.name}</span><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&brandCode=${selbrand.brandCode!''}&price=${price!''}&index=${index}">X</a></li>
                  </#if>
                    
                  <#if price?exists && (price?size > 0) && price!=''>
                    <li class="price"><span>${price}</span><a href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&index=${index}">X</a></li>
                  </#if>
                </ul>
               <#if index=='keyword'>
                 <a class="delete" href="${rc.contextPath}/search/q?keywords=${keywords}" >全部撤销</a>
			   <#elseif index=='category'>               
                 <a class="delete" href="${rc.contextPath}/search/category?code=${selcategory.code}" >全部撤销</a>
               <#elseif index=='brand'>  
                 <a class="delete" href="${rc.contextPath}/search/brand?code=${selbrand.brandCode}" >全部撤销</a>
               </#if> 
            </div>
        </div>
-->
      <!--  <p class="list-tit"><span></span></p>-->

        <div class="list container">
            <div class="search-menu">
                <!--<a <#if sortParam==''> class="active"</#if>  href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=${price!''}&index=${index}">综合</a>-->
                <a <#if sortParam==''> class="active"</#if>  href="${rc.contextPath}/search/skuSearch?keywords=${keywords}">综合</a>
                <!-- <a href="">最新</a>-->
               <!-- <a <#if sortParam=='sales'> class="active"</#if> href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=${price!''}&sortParam=sales&index=${index}">热销</a>-->
                <a <#if sortParam=='sales'> class="active"</#if> href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&sortParam=sales">热销</a>
               <!-- <a <#if sortParam=='price'> class="active"</#if> href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&categoryCode=${selcategory.code!''}&brandCode=${selbrand.brandCode!''}&price=${price!''}&index=${index}&sortParam=price&sortType=<#if sortType=='desc'>asc<#else>desc</#if>">价格<#if sortType=='desc'>&darr;<#else> &uarr;</#if></a>--><!-- &darr; -->
                <a <#if sortParam=='price'> class="active"</#if> href="${rc.contextPath}/search/skuSearch?keywords=${keywords}&price=${price!''}&index=${index}&sortParam=price&sortType=<#if sortType=='desc'>asc<#else>desc</#if>">价格<#if sortType=='desc'>&darr;<#else> &uarr;</#if></a><!-- &darr; -->
            	<span></span>
            </div>
            <ul>
              <#if pageSet.results?exists>
                <#list pageSet.results as sku>
	                <li>
	                    <a class="list-img" href="${rc.contextPath}/sku/skuDetail?skuCode=${sku.skuCode}" target="_blank" title="${sku.name}">
	                        <img  class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}" height="300" width="300" alt="${sku.name}">
	                    </a>
	                    <p>${sku.name}</p>
	                    <span class="price">&yen; ${sku.priceNew}</span>
	                    <a class="buy-btn" href="javascript:addToCart('${sku.skuCode}',1)">加入购物车<i>+1</i></a>
	                </li>
               </#list> 
              </#if>
            </ul>
        </div>

        <div class="page container">
			<div class="tcdPageCode"></div>
        </div>

        <!-- 底部 开始 -->
        <#include "/common/footer.ftl" />
        <!-- 底部 结束 -->

        <!-- 对话框 -->
     	<#include "/common/other.ftl" />
     	
     	
    </body>
</html>
