<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>品牌列表 - Fushionbaby</title>
    <meta content="Fushionbaby 像在国外一样任性_100%正品保证_中高端母婴选购平台_海外进口，口碑甄选，独家代理，严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent,顺顺儿,Bumprider,Linden Leaves,U-ZA" name="keywords">
    <meta content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent" name="description">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"><!-- IE浏览器以标准模式渲染 -->
    <meta name="renderer" content="webkit">
    <link rel="shortcut icon" type="image/x-icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}"><!-- 浏览器标签图标 -->
    <link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
    <link rel="stylesheet" href="${rc.contextPath}/views/css/style.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
    <link rel="stylesheet" href="${rc.contextPath}/views/css/brand_list.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
    <script src="http://cdn.bootcss.com/jquery/2.1.3/jquery.min.js"></script>
  </head>
  <body>
    <!--[if IE 6]>
    <script src="js/iepng.js" type="text/javascript"></script>
    <script type="text/javascript">
    EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
    </script>
    <![endif]-->
    <#include "/common/topbar.ftl" />
	<#include "/common/header.ftl" />
    <div class="container" id="brand_list">
      <div class="brand_left fl">
        <!-- 字母导航 -->
                
        
        <div class="brand_left_nav">
          <ul>
          
           <#list skuBrandGraphemes as skuBrandGrapheme>   
            	<li class="array_num${skuBrandGrapheme_index}_btn">${skuBrandGrapheme.grapheme}</li>
           </#list>
          </ul>
        </div>
        
        
        <!-- 品牌 -->
     <#list skuBrandGraphemes as skuBrandGrapheme>  
        <div class="brand_wrap  array_num${skuBrandGrapheme_index}_box">
          	<div class="h3_wrap"><h3>${skuBrandGrapheme.grapheme}</h3></div>
          	<ul>
          	  <#if skuBrandGrapheme.skuBrandList?exists && (skuBrandGrapheme.skuBrandList?size > 0)>
          		<#list skuBrandGrapheme.skuBrandList as brand>
            	<li>
	              	<a href="${rc.contextPath}/product/productListByBrand.do?brandId=${brand.id?c}" target="_blank">
		                <div class="verticalAlign_brand">
		                  	<img src="${brand.imgPath}" alt="${brand.brandName}" title="${brand.brandName}" />
		                </div>
	              	</a>
              		<p>${brand.brandName}</p>
            	</li>
            	</#list>
              </#if>
          	</ul>
        </div>
   	 </#list>
        
        
      </div>
      <!-- 热销商品 -->
      <div class="brand_right fr">
        <div class="newpro-bd-r">
          <dl class="hot-seniority">
            <dt class="hot-title">
              <h3>热销商品<span class="hot"> Hot</span></h3>
            </dt>
            
            
            <#if skuHots?exists && (skuHots?size > 0)>
            <#list skuHots as skuHot>
            <div class="dd_wrap">
              <dd class="hot-list" title="${skuHot.name}">
                <span class="item-num">${skuHot_index+1}</span>
                <span class="item-info">
                  <a target="_blank" href="${rc.contextPath}/product/skuDetail.do?skuId=${skuHot.skuId?c}" title="${skuHot.name}">${skuHot.name}</a>
                </span>
                <span class="item-thumb">
                <a target="_blank" href="${rc.contextPath}/product/skuDetail.do?skuId=${skuHot.skuId?c}" class="item-img">
                  <div class="verticalAlign_hot">
                    <img alt="${skuHot.name}" src="${skuHot.imgUrl}" />
                  </div>
                </a>
                </span>
              </dd>
            </div>
            </#list>
            </#if>
            
            
            
          </dl>
        </div>
      </div>
    </div>
    <script src="${rc.contextPath}/views/js/brand_list.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <#include "/common/footer.ftl" />
	<#include "/common/nav.ftl" />
  </body>
</html>
