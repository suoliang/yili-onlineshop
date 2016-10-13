<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <title>Fushionbaby触屏版-购物车</title>
    <meta name="apple-mobile-web-app-title" content="兜士宝-购物车">
    <script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/public-headTag.js"></script>
    <!-- 公共头部便签，css,js等 -->
    <script src="http://cdn.bootcss.com/zepto/1.1.4/zepto.min.js"></script>
    <script src="${rc.contextPath}/wap/js/zepto/event.js"></script>
    <script src="${rc.contextPath}/wap/js/zepto/touch.js"></script>
  </head>
  <body id="car">
    <div class="container">
      <div class="public_header fl width100 marginB0">
        <div class="public_header_wrap fl width100">
          <a class="public_back" href="javascript:void(0);"  onclick="history.go(-1);">返回</a>
          购物车
          <a class="public_btn_r disabled" href="javascript:void(0);"></a>
        </div>
      </div>
      <!-- 购物车列表  开始-->
      <div class="public_products_wrap">
        <ul class="list_view_ul OH padding0 margin0">
        	<#if cartList??>
				<#list cartList as tmp>
		          <li>
		            <a href="javascript:void(0);">
		              <div class="public_products_item list_view_item">
		                <div class="hide_radio_click"></div>
		                <div class="item_picture">
		                  <div class="img_c">
		                    <img src="${rc.contextPath}/wap/css/images/example.jpg" alt="">
		                  </div>
		                </div>
		                <div class="item_name">
		                  <p>${tmp.name!''}</p>
		                  <span>型号：${tmp.size!''}</span>
		                  <div class="count_num">
		                    <span class="reduce"></span>
		                    <input type="text" value="5" disabled>
		                    <span class="add"></span>
		                  </div>
		                </div>
		                <div class="item_price">&yen;${tmp.price!''}</div>
		              </div>
		            </a>
		            <span class="collection list_view_span">收藏</span>
		            <span class="delete list_view_span">删除</span>
		          </li>
         		</#list>
			</#if>
        </ul>
      </div>
      <!-- 购物车列表  结束-->
      <#if cartList??>
      	<div class="car_btn_wrap fl">
       		<p>总计：${cart.priceTotal!''}</p>
       		<button class="car_btn" type="button">去结算</button>
     	 </div>
      </#if>
    </div>

    <!-- 收藏列表为空提示 -->
    <section class="car-none">
      <img src="${rc.contextPath}/wap/css/images/collect.png">
      <p>您的购物车 空！空！空！</p>
      <p>赶快去购物吧！</p>
      <a href="${rc.contextPath}/index/indexList.do">去商城逛逛</a>
    </section>

    <!-- 对话框 -->
    <div class="public_modal_backup">
      <div class="public_modal">
        <div class="modal_body">请选择至少一件商品</div>
        <div class="modal_foot">
          <button class="modal_confirm only_confirm">好</button>
        </div>
      </div>
    </div>
    <script>
      $(function(){
        /*去结算按钮*/
        $(".car_btn").click(function() {
          if ($(".checked").length <= 0 ) {
            $('.public_modal_backup').fadeIn();/* 显示对话框  */
          } else {
            window.location='pay.html';
          }

          /*  确定按钮  */
          $(".modal_confirm").click(function() {
            /*  do something  */
            $('.public_modal_backup').fadeOut();
          });
        })
      })
    </script>

    <script src="${rc.contextPath}/wap/js/zepto/listview.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/wap/js/footer.js"></script>
  </body>
</html>