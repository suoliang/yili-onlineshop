<div class="order-l fl">
    <div class="order-user">
        <img src="${rc.contextPath}/static/shop/picture/user.jpg" height="40" width="40" alt="">
        <p id="epoints"></p>
    </div>
    <ul class="order-l-list">
        <a class="prder-l-tit" href="javascript:void(0)">我的订单<span>-</span></a>
        <li id="statu_0"><a href="${rc.contextPath}/order/orderList?orderStatus=0&t=${time!'${EnvironmentConstant.DEPLOY_VERSION}'}">全部订单</a></li>
        <li id="statu_1"><a href="${rc.contextPath}/order/orderList?orderStatus=1&t=${time!'${EnvironmentConstant.DEPLOY_VERSION}'}">待付款</a></li>
        <li id="statu_3"><a href="${rc.contextPath}/order/orderList?orderStatus=3&t=${time!'${EnvironmentConstant.DEPLOY_VERSION}'}">待发货</a></li>
        <li id="statu_5"><a href="${rc.contextPath}/order/orderList?orderStatus=5&t=${time!'${EnvironmentConstant.DEPLOY_VERSION}'}">待收货</a></li>
        <li id="statu_6"><a href="${rc.contextPath}/order/evaluateList?orderStatus=6&t=${time!'${EnvironmentConstant.DEPLOY_VERSION}'}">待评价</a></li>
    </ul>
    <ul id="collect" class="order-l-list">
        <a class="prder-l-tit" href="${rc.contextPath}/myAld/collectList.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的收藏</a>
    </ul>
    <ul id="comment" class="order-l-list">
        <a class="prder-l-tit" href="${rc.contextPath}/myAld/commentList.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的评论</a>
    </ul>
    <ul id="address" class="order-l-list">
        <a class="prder-l-tit" href="${rc.contextPath}/address/list.html?v=${EnvironmentConstant.DEPLOY_VERSION}">收货地址</a>
    </ul>
    <!--
        <ul id="yiduobao" class="order-l-list">
        <a class="prder-l-tit" href="${rc.contextPath}/card/yiduobao.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的益多宝</a>
    </ul>-->
</div>
 <script>
	function readyFun(){
 		$(".order-l-list").find('li').removeClass('active');
 		var mar = "${marker}";
 		var state = "${orderStatus}";
 		if(mar  == ""){
 			$("#statu_"+state).addClass('active');
 		}else{
 			$("#"+mar).addClass('active');
 		}
	}
	
	function readyFun2(userDto){
		$("#epoints").html("我的积分:"+userDto.epoints);
	}
</script>