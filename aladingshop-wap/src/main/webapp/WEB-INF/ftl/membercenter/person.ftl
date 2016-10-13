<!DOCTYPE html>
<html>
    <head>
        <title>个人中心</title>
        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <script>
        	var _ContextPath = "${rc.contextPath}";
        	function toRegister(){
            	$.post(_ContextPath+'/login/clickRegister',
					{time:new Date().getTime()},
				function(data){
						if (data.responseCode == "0"){
							window.location.href=_ContextPath +"/login/toRegister?time="+new Date().getTime();
						} else {
							alert(data.msg);
						}
					}
				);
            }
            $(function(){
            	loadEachOrderQuantity();
	            function loadEachOrderQuantity(){
					$.ajax({
							type: "POST",
							url : "${rc.contextPath}/order/getEachOrderQuantity",
							data: {'time':new Date().getTime()},
							async:false,
							dataType: "json",
						    success: function (data) {
						    	if(data.responseCode==0){
			                        var orderUserRes=data.data;
			                     	$(".p-topay").find('i').html(orderUserRes.toBePaidNo);
		                          	$(".p-tosend").find('i').html(orderUserRes.toBeShippedNo);
		                          	$(".p-toresive").find('i').html(orderUserRes.toBeReceivedNo);
		                          	$(".p-torank").find('i').html(orderUserRes.toBeEvaluatedNo);
		                       }
						    }
					});
			    }
            })

        </script>
    </head>
    <body id="persopn">

        <div class="container">

            <div class="head">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>个人中心</p>
                <a href="${rc.contextPath}/memberCenter/toSetting" class="a-right">
                    <span class="cog"></span>
                </a>
            </div>

            <div class="person-a fl wp100 mB10">
                <img src="${rc.contextPath}/static/shop/images/p-back.png">
                <div class="status">
                    <#if isLogin=="y">
                    	<i>已登录</i>
                    <#else>
	                    <div class="logined fl wp100">
	                        <a class="status-l" href="${rc.contextPath}/login/index">登录</a>
	                        <span></span>
	                        <a class="status-r" href="javascript:toRegister();">注册</a>
	                    </div>
                    </#if>

                    <!-- 未登录 -->
                    <div class="unlogined fl">
                        <a class="status-l" href="login.html">登录</a>
                        <span></span>
                        <a class="status-r" href="registering-phone.html">注册</a>
                    </div>
                    <!-- 未登录 -->

                    <!-- 已登录 -->
                    <div class="logined fl wp100" style="display:none">
                        <p>181****7567</p>
                        <!-- 非vip显示 start -->
                        <div class="no-vip">
                            <a class="btn-a" href="">普通会员</a>
                            <a class="btn-b" href="person-open-vip.html">开通 VIP</a>
                        </div>
                        <!-- 非vip显示 end -->

                        <!-- vip显示 start -->
                        <div class="vip" style="display:none"></div>
                        <!-- vip显示 end -->
                    </div>
                    <!-- 已登录 -->
                </div>
            </div>

            <div class="person-b fl wp100 mB0">
                <ul class="list">
                    <li>
                        <h3 class="p-order"></h3>
                        <p>我的订单</p>
                        <a href="javascript:window.location.href='${rc.contextPath}/order/orderList?status=0'">
                            <span>查看全部订单</span>
                            <i class="right"></i>
                        </a>
                    </li>
                </ul>

                <div class="person-c fl wp100 mB10">
                    <a href="javascript:window.location.href='${rc.contextPath}/order/orderList?status=1&orderStatus=1'">
                        <span class="p-topay"><i></i></span>
                        <p>待付款</p>
                    </a>
                    <a href="javascript:window.location.href='${rc.contextPath}/order/orderList?status=2&orderStatus=3'">
                        <span class="p-tosend"><i></i></span>
                        <p>待发货</p>
                    </a>
                    <a href="javascript:window.location.href='${rc.contextPath}/order/orderList?status=3&orderStatus=5'">
                        <span class="p-toresive"><i></i></span>
                        <p>待收货</p>
                    </a>
                    <a href="javascript:window.location.href='${rc.contextPath}/order/evaluateList?status=4&evaluateStatus=n'">
                        <span class="p-torank"><i></i></span>
                        <p>待评价</p>
                    </a>
                </div>

                <ul class="list">
                    <a href="${rc.contextPath}/address/list">
                        <li>
                            <h3 class="p-address"></h3>
                            <p>地址管理</p>
                            <i class="right"></i>
                        </li>
                    </a>
                    <a href="${rc.contextPath}/collect/collectList">
                        <li>
                            <h3 class="p-heart"></h3>
                            <p>我的收藏</p>
                            <i class="right"></i>
                        </li>
                    </a>
                    <a href="javascript:isLogin(3);" display="none">
                        <li>
                            <h3 class="p-bonus"></h3>
                            <p>我的红包</p>
                            <i class="right"></i>
                        </li>
                    </a>
                </ul>

                <ul class="list">
                    <a href="javascript:isLogin(2);"  display="none">
                        <li>
                            <h3 class="p-ruyi"></h3>
                            <p>如意消费卡</p>
                            <i class="right"></i>
                        </li>
                    </a>
                    <a href="javascript:isLogin(1);"  display="none">
                        <li>
                            <h3 class="p-card"></h3>
                            <p>阿拉丁卡</p>
                            <i class="right"></i>
                        </li>
                    </a>
                    <a href="${rc.contextPath}/memberCenter/toContact">
                        <li>
                            <h3 class="p-contact"></h3>
                            <p>联系客服</p>
                            <i class="right"></i>
                        </li>
                    </a>
                </ul>

            </div>

        </div><!-- /.container -->
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
		<script type="text/javascript">
		function isLogin(index){
        		 var url = "${rc.contextPath}/card/cardIndex";
        		 if(index == 2){
        		 	url = "${rc.contextPath}/ruyibao/mainShow";
        		 }else if(index ==3){
        		  	url = "${rc.contextPath}/activity/share/myRedEnvlopeAmount";
        		 }

            	 /****校验登录信息**/
				 $.post("${rc.contextPath}/login/isLogin",{time:new Date().getTime()},function(result){
						if(result.data !="y"){
				         	 if(confirm("当前未登录，是否立即登录？")){
							 	location.href="${rc.contextPath}/login/index";
							 }
			            }else{
			            	location.href=url;
			            }
				 });
            }
        </script>
    </body>
</html>
