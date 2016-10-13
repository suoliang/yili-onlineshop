<!DOCTYPE html>
<html>
    <head>
        <title>红包</title>
        <!-- 公共样式js引用-->
        <#include "/common/common.ftl" />
         <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
    </head>
    <body id="">

        <div class="container">

            <div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>我的红包</p>
                <a href="javascript:jumpDetail();" class="a-right">
                    <span>详情</span>
                </a>
            </div>

            <div class="spe-bonus-wrap fl wp100">
                <a href="${rc.contextPath}/activity/share/shareBonus">
                    <h1>邀好友，赚红包</h1>
                    <div class="spe-bonus-box fl wp100">
                        <span>${result.existingRedEnvelope}元</span>
                    </div>
                    <button>点我赚红包</button>
                </a>
                <!-- <p>
                    <button class="withdraw">提现</button>
                </p> -->
            </div>

        </div><!-- /.container -->

        <div class="modal-wrap" id="withdrawModal">
            <div class="modal">
                <p>红包提现需要您开通如意消费卡并转入，如您已经开通，请直接登录。如未开通，需要您注册。</p>
                <div class="btn-wrap">
                    <a class="" href="spe-ruyi-login.html">登录</a>
                    <a class="" href="spe-ruyi-registering.html">注册</a>
                </div>
            </div>
        </div>

        <script>
        $(function(){
            /*提现按钮*/
            var withdrawModal = $('#withdrawModal');
            var withdrawBtn = $('.withdraw');
            withdrawBtn.click(function(event) {
                withdrawModal.fadeIn();
            });

            $('.modal-wrap').click(function() {
                $('.modal-wrap').fadeOut();
            });
        })

        function jumpDetail(){
        	location.href="${rc.contextPath}/redEnvelope/detailRecord?time="+new Date().getTime();
        }
        </script>
		<script src="${rc.contextPath}/static/shop/js/menu.js"></script><!-- 公共底部菜单 -->
    </body>
</html>
