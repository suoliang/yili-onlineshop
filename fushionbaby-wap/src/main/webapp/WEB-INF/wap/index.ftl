<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-首页</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-首页">
    <script type="text/javascript" language="javascript">
			var _ContextPath = "${rc.contextPath}";
	</script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js"></script><!-- 公共头部便签，css,js等 -->
  </head>
  <body id="index">
    <div id="page_index" class="container">
      <!-- logo 头部 -->
      <div class="header">
        <div class="logo">
          <img src="${rc.contextPath}/wap/css/images/logo.png" alt="logo">
        </div>
        <span class="menu open_menu">分类</span>
        <a href="search.html">
          <div class="search">搜索商品<span></span></div>
        </a>
      </div>
      <!-- banner 轮播 -->
      <div class="banner">
        <ul>
         <#if indexDto?exists && indexDto.focusPictureList?exists>
			<#list indexDto.focusPictureList as pic>
				<#if pic.url !=''>
					<li><a href="${pic.url}"><img src="${pic.picture_path}"></a></li>
            	<#else>
            		<li><a href="javascript:void(0)"><img src="${pic.picture_path}"></a></li>
            	</#if>
        	</#list>
			</#if>
        </ul>
      </div>
      <!-- 导航 -->
      <div class="navgation fl">
        <a class="car" href="${rc.contextPath}/cart/cartList.do?time="+new Date().getTime()>
          <span></span>
          <p>购物车</p>
        </a>
        <a class="my" href="${rc.contextPath}/membercenter/center.do?time="+new Date().getTime()>
          <span></span>
          <p>我的</p>
        </a>
        <a class="contact" href="tel:400-888-8888">
          <span></span>
          <p>联系我们</p>
        </a>
        <a class="menu open_menu" href="javascript:void(0);">
          <span></span>
          <p>分类</p>
        </a>
      </div>
      <!-- 抢购专区 -->
      <h3 class="index_tit">抢购专区，直击底价</h3>
      <div id="scroll" class="scroll fl width100">
        <ul>
       <#assign timelimit_cols=FtlConstant.TIMELIMIT_COLS />
			<#if indexDto?exists && indexDto.skuTimelimitList?exists>
				<#list 1..(((indexDto.skuTimelimitList?size)?number - 1)/(FtlConstant.TIMELIMIT_COLS?number) + 1) as row>
					<li>
						<#list indexDto.skuTimelimitList as sku>
							<#if sku_index = ((indexDto.skuTimelimitList?size)?number/(FtlConstant.TIMELIMIT_COLS?number) + 1) * (sku_index+1)>
							  <#break>
							</#if>
							<#if (sku_index >=(row-1)*timelimit_cols) && (sku_index <  row * timelimit_cols)>
								
								<a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.skuId?c}">
                                  <p class="endtime" value="${sku.offshelvestime}"></p>
                                   <div class="img_c">
                                     <img src="${sku.imgPath}">
                                  </div>
                                    <span>&yen;：${sku.limitPrice}</span>
                                </a>
						 </#if>	
					</#list>
				</li>
			</#list>
		</#if>
        </ul>
      </div>
      <!-- 海外同步专区 -->
      <h3 class="index_tit">海外同步专区
        <a href="${rc.contextPath}/product/product-more-list.do?label_code=global"><i class="icon-double-angle-right"></i></a>
      </h3>
      <div class="oversea fl width100">
        <ul><!-- 循环li x6 -->
        	<#assign newproRows=(indexDto.globalSkuList?size-1)/FtlConstant.NEWPRO_COLS+1 />
			<#assign newproCols=FtlConstant.NEWPRO_COLS />
				<#list 1..newproRows as row>
					<li>
						<#if indexDto?exists && indexDto.globalSkuList?exists>
							<#list indexDto.globalSkuList as sku>
									<#if sku_index = newproRows * (sku_index+1)>
									    <#break>
									</#if>
								    <#if (sku_index >=(row-1)*newproCols) && (sku_index <  row * newproCols)  >
										      <a href="${rc.contextPath}/product/skuDetail.do?skuId=${sku.id?c}"">
									              <div class="img_c">
									                <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${sku.imgPath}">
									              </div>
									              <p class="oversea_name">${sku.name}</p>
									              <p class="oversea_price">
									                  <span>&yen;：${sku.priceNew}</span>&ensp;
									                  <del>${sku.priceOld}</del>
									               &nbsp;&nbsp;&nbsp;已有<span><#if sku.commentCount?exists>${sku.commentCount}</#if></span>人评价
								                  </p>
							                  </a>
									</#if>
							</#list>
						</#if>
					</li>
			 </#list>
        </ul>
      </div>
      <!-- FushionBaby 特惠 -->
      <h3 class="index_tit">FushionBaby 特惠
        <a href="${rc.contextPath}/product/product-more-list.do?label_code=preferential"><i class="icon-double-angle-right"></i></a>
      </h3>
      <div class="oversea fl width100">
        <ul><!-- 循环li x8 -->
        <#if indexDto?exists && indexDto.discountList?exists>
			<#assign rows= (indexDto.discountList?size-1) / FtlConstant.PREFERENTIAL_COLS+1 />
			<#assign cols=FtlConstant.PREFERENTIAL_COLS />
				<#list 1..rows as row>
					<li>
						<#list indexDto.discountList as discountSku>
							<#if discountSku_index = rows * (discountSku_index+1)>
								<#break>
							</#if>
							<#if (discountSku_index >=(row-1)*cols) && (discountSku_index <  row * cols)  >
								<a href="${rc.contextPath}/product/skuDetail.do?skuId=${discountSku.id?c}">
					              <div class="img_c">
					                <img class="bttrlazyloading" data-bttrlazyloading-lg-src="${discountSku.imgPath}">
					              </div>
					              <p class="oversea_name">${discountSku.name}</p>
					              <p class="oversea_price">
					                <span>&yen;：${discountSku.priceNew}</span>&ensp;
					                <del>${discountSku.priceOld}</del>
					              </p>
					            </a>
							</#if>
						</#list>
					</li>
				</#list>
			</#if>
        </ul>
      </div>
      <!-- FushionBaby 品牌购 -->
	      <h3 class="index_tit">FushionBaby 品牌购
	          <a href="${rc.contextPath}/product/brand-more-list.do"><i class="icon-double-angle-right"></i></a>
	      </h3>
	      <div class="brand fl width100"><!-- 循环a x12 -->
	      	<#if indexDto?exists && indexDto.brandList?exists && (indexDto.brandList?size > 0)>
				<#list indexDto.brandList as brand>
					<a  href="${rc.contextPath}/product/productListByBrand.do?brandId=${brand.id?c}" target="_blank">
						<img class="bttrlazyloading" data-bttrlazyloading-lg-src="${brand.imgPath}" alt="${brand.name}">
					</a>
				</#list>
			</#if>
	      </div>
    </div>
    
    <div id="menu" class="container">
	       <div class="public_header fl width100">
	          <div class="public_header_wrap fl width100">
		           <a class="public_back close_menu" href="javascript:void(0);">返回</a>
		               商品分类
		           <a class="public_btn_r disabled" href="javascript:void(0);"></a>
	          </div>
	      </div>
	      
      <div class="public_list">
        <ul>
	          <li>
		            <a href="${rc.contextPath}/product/product-more-list.do?label_code=global" class="public_icons">
		              <span class="menu_icon menu_icon-oversea"></span>
		                                   海外同步专区
		              <i class="icon-angle-right"></i>
		            </a>
	          </li>
	          
	          <li>
		            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=1" class="public_icons">
		                <span class="menu_icon menu_icon-zy"></span>
		                       安全座椅
		                <i class="icon-angle-right"></i>
		              </a>
	          </li>
	          
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=2" class="public_icons">
              <span class="menu_icon menu_icon-car"></span>
                        童车
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=3" class="public_icons">
              <span class="menu_icon menu_icon-xh"></span>
              洗护用品/纸尿裤
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=4" class="public_icons">
              <span class="menu_icon menu_icon-wb"></span>
              喂哺用品
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=5" class="public_icons">
              <span class="menu_icon menu_icon-toy"></span>
              玩具
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=6" class="public_icons">
              <span class="menu_icon menu_icon-ju"></span>
              主题家居
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/productListByCategory.do?category_id=7" class="public_icons">
              <span class="menu_icon menu_icon-fz"></span>
              服装
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/product-more-list.do?label_code=jxmy" class="public_icons">
              <span class="menu_icon menu_icon-jx"></span>
              精选母婴分类
              <i class="icon-angle-right"></i>
            </a>
          </li>
          <li>
            <a href="${rc.contextPath}/product/product-more-list.do?label_code=mmjx" class="public_icons">
              <span class="menu_icon menu_icon-mm"></span>
              妈咪精选
              <i class="icon-angle-right"></i>
            </a>
          </li>
        </ul>
      </div>
    </div>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/yxMobileSlider.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/swipe.js"></script>
    <script>
      $(function(){
        /*样式修改*/
        $(".oversea ul li:lt(2)").css({"border-top":"none"});
        $(".oversea ul li:odd").css({"border-right":"none"});
        /*菜单*/
        $("#menu").height($(window).height());
        $(".open_menu").click(function(){
          $("#menu").show().animate({"left":"0"});
          $("#page_index,#foot").fadeOut();
        })
        $(".close_menu").click(function(){
          $("#menu").animate({"left":"100%"});
          $("#page_index,#foot").fadeIn();
          setTimeout(function(){
            $("#menu").hide();
          },600)
        })

        /*banner 轮播*/
        $(".banner").yxMobileSlider();

        /*抢购专区轮播*/
        var slider =
        Swipe(document.getElementById('scroll'),{
          auto: 3000,// 每3秒切换图片
          continuous: true
        });

        /*抢购专区倒计时*/
        var time = new Date();
        var Start_time = time.valueOf();
        setInterval(function() {
          $(".endtime").each(function() {
            var obj = $(this);
            var endTime = new Date(parseInt(obj.attr('value')));
            var nowTime = new Date();
            var nMS = endTime.getTime() - nowTime.getTime();
            var myD = Math.floor(nMS / (1000 * 60 * 60 * 24));
            var myH = Math.floor(nMS / (1000 * 60 * 60)) % 24; //小时
            var myM = Math.floor(nMS / (1000 * 60)) % 60; //分钟
            var myS = Math.floor(nMS / 1000) % 60; //秒
            var myMS = Math.floor(nMS / 100) % 10; //拆分秒
            if (myD >= 0) {
              var str = myD + "天" + myH + "时" + myM + "分<br/>" + myS + "." + myMS + "秒";
            } else {
              var str = "活动已过期！";
            }
            obj.html(str);
          });
        }, 100);
      })
    </script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/jquery.bttrlazyloading.min.js"></script>
    <script type="text/javascript">
      $(".bttrlazyloading").bttrlazyloading({placeholder:'${rc.contextPath}/wap/images/lazyload.gif'});
    </script>
  </body>
</html>