		<script type="text/javascript">
				var _ContextPath = "${rc.contextPath}";
				$(document).ready(function() {
					$(".search .search-btn").click(function(){
						var searchKey= $.trim($(".search-text").val());
						if(searchKey == ''){
							return ;
						}else{
							window.location.href= _ContextPath + "/product/search-list.do?searchKey="+encodeURI(encodeURI(searchKey));
						}
					});
					/**IE，火狐都支持  -- 回车搜索*/
					$("input[name='searchKey']").keydown(function(e){
				var curKey = e.which;
				if(curKey == 13){
				$(".search .search-btn").click();
				return false;
				}
				});
				});
		</script>
		<div class="site-header container">
			<div class="logo">
				<h1>
				<a href="${rc.contextPath}/index/index.do">
					<img src="${rc.contextPath}/views/images/logo.jpg" alt="Fushionbaby" title="Fushionbaby 兜世宝 像在国外一样任性！">
					<span class="logo-txt">fushion baby</span>
				</a>
				</h1>
			</div>
			<div class="header-info">
				<div class="search">
					<form action="#" class="search-form" method="post">
						<input type="text" class="search-text" autocomplete="off"name="searchKey" value="搜索你需要的商品" onfocus="if(this.value=='搜索你需要的商品')this.value='';" onblur="if(this.value=='')this.value='搜索你需要的商品';">
						<input type="button" class="search-btn iconfont" value="&#xe606;">
					</form>
				</div>
				<div class="shop">
					<i class="shop-car iconfont">&#xe60a;</i>
					<span class="shop-info">去购物车结算</span>
					<div class="mini-cart-list" id="lot">
						<div class="white"></div>
						<div class="mini-cart-wrapper">
						</div>
					</div>
					<div class="mini-cart-list" id="none">
						<div class="white"></div>
						<div class="mini-cart-wrapper">
							<ul>
								<li class="car-module">
									<p class="none-buy">购物车内没有商品,赶紧选购吧！</p>
								</li>
							</ul>
						</div>
					</div>
					<!-- 第一个是购物之后弹出框   第二个是没有购物弹出框 -->
					<div class="shop-num">
						0
					</div>
				</div>
			</div>
			</div>
			<div class="navall">
				<div class="header-nav clearfix">
					<div class="nav-category">
						<div class="nav-category-text">全部商品分类</div>
					   <!-- 首页分类 -->
					 	<div id="index-drap-down">
					 	<div class="oversea-a">
				          <a href="${rc.contextPath}/product/product-more-list.do?label_code=global" class="navla" title="海外同步专区"><nobr>海外同步专区</nobr></a>
				          <a href="${rc.contextPath}/product/product-more-list.do?label_code=global&brand_id=29" class="navlp"><nobr>babymonsters</nobr></a>
				        </div>
				        <div class="drap-wrap fl">
					 	<#if indexDto?exists && indexDto.categoryList?exists>
					        <ul>
					          <#list indexDto.categoryList as category>
					          <li class="navl">
					            <a href="${category.link_url!''}" class="navla"><nobr>${category.name}</nobr></a>
					            <#if category.brandList?exists && (category.brandList?size > 0)>
					            <#list category.brandList as brand >
					            	<#if (brand_index < 3)>
					            		<a href="${rc.contextPath}/product/productListByCategory.do?category_id=${category.id?c}&brand_id=${brand.brandId?c}" 
					            		class="navlp"><nobr>${brand.brandName!''}</nobr></a>
					            	</#if>
					            </#list>
					            </#if>
					            <span class="triangle-left"></span>
					          </li>
					          </#list>
					        </ul>
					        <div class="hide_nav">
					          <#list indexDto.categoryList as category>
					          <ul>
					            <li>
					          	<#list category.childCategory as child>
					              <div class="all_wrap">
					                <a href="${child.link_url!''}" class="hide_nav_tit">${child.name}</a>
					                <div class="a_wrap">
					                 <#list child.childCategory as secondChild>
					                  	<a href="${secondChild.link_url!''}">${secondChild.name}</a>
					                 </#list>
					                </div>
					              </div>
					            </#list>
					            </li>
					          </ul>
					          </#list>
					        </div>
					       </#if>
					       	</div>
					      </div>
						<!-- 其他页面分类 -->
							
							<div id="nav-drap-other">
								<ul>
									<li><a href="${rc.contextPath}/product/product-more-list.do?label_code=global" class="icon-nav1" target="_blank">海外同步专区</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=42" class="icon-nav2" target="_blank">怀孕妈妈必备</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=53" class="icon-nav3" target="_blank">宝宝用品</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=89" class="icon-nav4" target="_blank">宝宝0-1岁玩具</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=104" class="icon-nav5" target="_blank">宝宝1-3岁玩具</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=118" class="icon-nav6" target="_blank">宝宝早教</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=132" class="icon-nav7" target="_blank">宝宝服装</a></li>
									<li><a href="${rc.contextPath}/product/productListByCategory.do?category_id=198" class="icon-nav8" target="_blank">宝宝清单</a></li>
									<li><a href="${rc.contextPath}/product/product-more-list.do?label_code=jxmy"   class="icon-nav9" target="_blank">精选母婴</a></li>
									<li><a href="${rc.contextPath}/product/product-more-list.do?label_code=mmjx"   class="icon-nav10" target="_blank">妈咪精选</a></li>
									
								</ul>
							</div>
						</div>
						<div class="nav-main">
							<ul class="nav-main-list clearfix">
								<li><a class="nav-main-item" href="${rc.contextPath}/index/index.do">首页</a></li>
								<!--<li><a class="nav-main-item" href="${rc.contextPath}/activity/goCross.do" target="_blank">跨境兜拿下</a></li>-->
								<li><a class="nav-main-item" href="${rc.contextPath}/product/product-more-list.do?label_code=global" target="_blank">跨境兜拿下</a></li>
								<li><a class="nav-main-item" href="${rc.contextPath}/will/list.do" target="_blank">风尚宝贝</a></li>
								<li><a class="nav-main-item" href="${rc.contextPath}/activity/goTakePhoto.do" target="_blank">宝宝摄影</a></li>
								<li><a class="nav-main-item" href="${rc.contextPath}/activity/goInfantEducation.do" target="_blank">兜爱早教</a></li>
								<li id="vip-button">
									<a class="nav-main-item" href="javascript:void(0)">会员增值</a>
									<div id="vip-ul" class="new-nav-drop-down nav-drop-down1">
										<ul>
											<li class="vip-icon1">
												<a href="${rc.contextPath}/parchiart/children.do" target="_blank" class="navla">亲子课程</a>
											</li>
											<li class="vip-icon2">
												<a href="${rc.contextPath}/activity/list.do" target="_blank" class="navla">户外活动</a>
											</li>
											<li class="vip-icon3">
												<a href="${rc.contextPath}/giftcard/search-list.do" class="navla">礼品卡</a>
											</li>
											<li class="vip-icon4">
												<a href="${rc.contextPath}/activity/go_didi.do"  class="navla">滴滴专项活动</a>
											</li>
										</ul>
									</div>
								</li>
								<li><a class="nav-main-item" href="${rc.contextPath}/views/download-first.html" target="_blank">手机客户端</a></li>
								<li class="crown"><a class="nav-main-item" href="${rc.contextPath}/product/brand-more-list.do" target="_blank"><span></span>在线品牌</a></li>
							</ul>
						</div>
					</div>
				</div>