 <script type="text/javascript">
   	var contextPath = "${rc.contextPath}";
  </script>
   <script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?33f97889ccd46c93d9e7079a6be545df";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>
<div id="topbar">
            <div class="container">
                <!-- 登录信息显示 -->
                <ul id="logined" class="fl ul-l">
                </ul>
                
                <ul class="fr ul-r">
                    <li class="drop-li">
                        <a href="${rc.contextPath}/order/orderList?orderStatus=0&t=${time!''}">我的订单</a>
                        <ul>
                            <li><a href="${rc.contextPath}/order/orderList?orderStatus=1&t=${time!''}">待付款</a></li>
                            <li><a href="${rc.contextPath}/order/orderList?orderStatus=3&t=${time!''}">待发货</a></li>
                            <li><a href="${rc.contextPath}/order/orderList?orderStatus=5&t=${time!''}">待收货</a></li>
                            <li><a href="${rc.contextPath}/order/evaluateList?t=${time!''}">待评价</a></li>
                        </ul>
                    </li>
                    <li class="drop-li">
                        <a href="${rc.contextPath}/order/orderList?orderStatus=0&t=${time!''}">我的阿拉丁</a>
                        <ul>
                            <!--<li><a href="${rc.contextPath}/card/yiduobao.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的益多宝</a></li>-->
                            <li><a href="${rc.contextPath}/myAld/collectList.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的收藏</a></li>
                            <li><a href="${rc.contextPath}/myAld/commentList.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">我的评论</a></li>
                            <li><a href="${rc.contextPath}/address/list.htm?v=${EnvironmentConstant.DEPLOY_VERSION}">收货地址</a></li>
                        </ul>
                    </li>
                     <li class="drop-code">
                        <a href="${rc.contextPath}/static/shop/app.html" target="_blank">手机阿拉丁</a>
                        <div class="code">
                            <span></span>
                            <img src="${rc.contextPath}/static/shop/images/code.jpg" alt="">
                        </div>
                    </li>
                    <li><a href="${rc.contextPath}/help/register">帮助中心</a></li>
                </ul>
            </div>
</div>
<script>
$(document).ready(function(){
	$("#logined").html("");
	/****加载登录信息**/
	 $.post("${rc.contextPath}/login/getUserInfo",{time:new Date().getTime()},function(result){
	 	var templete = "<li><a href='${rc.contextPath}/login/index.htm'>登录</a></li><li><a href='${rc.contextPath}/login/toRegister.htm?v=${EnvironmentConstant.DEPLOY_VERSION}'>注册</a></li>";
	 	if(result.data != null){
	 		templete = "<li><a class='user-icon' href='' title='进入个人中心'>VIP "+result.data.loginName+"</a></li><li><a href='javascript:loginOut();'>&emsp;退出登录</a></li>";
	 	}
	 	$("#logined").html(templete);
	 	try{
				readyFun();
				readyFun2(result.data);
			}catch(err){
				/*当前无需执行后续fun*/;
			}
	 });
});

function loginOut(){
	 $.post("${rc.contextPath}/login/loginOut",function(result){
    		if(result.responseCode== "0"){
    			var templete = "<li><a href='${rc.contextPath}/login/index.htm?v=${EnvironmentConstant.DEPLOY_VERSION}'>登录</a></li><li><a href='${rc.contextPath}/login/toRegister.htm?v=${EnvironmentConstant.DEPLOY_VERSION}'>注册</a></li>";
	 			$("#logined").html(templete);
    			try{
					exitFun();
				}catch(err){
					/*当前无需执行后续fun*/;
				}
    			
				return;
    		}
  	});
}
</script>
