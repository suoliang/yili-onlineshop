<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fushionbaby 母婴用品【使用礼品卡】</title>
    <meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
    <meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
    <link rel="stylesheet" href="${rc.contextPath}/views/css/checkout-list.css?v=${EnvironmentConstant.DEPLOY_VERSION}" />
	<script type="text/javascript" src="${rc.contextPath}/web-js/My97DatePicker/WdatePicker.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/user-info.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" src="${rc.contextPath}/web-js/uploadify/swfobject.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" src="${rc.contextPath}/web-js/uploadify/jquery.uploadify.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" src="${rc.contextPath}/views/js/checked.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
    <script type="text/javascript" >
		 var _ContextPath = "${rc.contextPath}";
	</script>
    <#include "/common/link.ftl"/>
    <script type="text/javascript">
    	function useCard() {
			var cardNo = $.trim($("#cardNo").val());
			var cardPassword = $.trim($("#cardPassword").val());
			if (cardNo.length != 20) {
				$("#show_card_no_error").html("* 请正确输入20位礼品卡卡号!").show();
				return;
			}
			if (cardPassword.length != 20) {
				$("#show_card_password_error").html("* 请正确输入20位礼品卡充值密码!").show();
				return;
			}
			$.post(_ContextPath + '/membercenter/chargeFund.do',{
					cardNo:cardNo,
					cardPassword:cardPassword,
					time:new Date().getTime()
				},function(data){
					if (data.responseCode == "0") {
						alert(data.data);
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/userCard.do?time="+new Date().getTime();
					} else {
						alert("系统异常，充值未成功，请稍后再试!");
					}
				}
			);
		}
    </script>
    
</head>
<body>
<div class="user-cards" style="min-height:330px;">
  <h3 class="center-title">我的余额</h3>
  <div class="info-list user-cards-box">
    <div class="info-list-l">我的余额：</div>
    <div class="info-list-r">￥ ${walletMoney}元</div>
  </div>
  <div class="info-list user-cards-box">
    <div class="info-list-l">当前可用余额：</div>
    <div class="info-list-r">￥ ${availableMoney}元</div>
  </div>
  <!-- 使用礼品卡 开始  -->
  <form action="">
  <div id="discount" class="marginT0 cards_discount">
    <div class="discount-wrap">
      <a class="discount-btn" href="javascript:void(0);">使用礼品卡</a>
      <div class="discount-box">
        <div class="discount-input-group fl width100">
          <label for="卡号">卡号：</label>
          <input type="text" id="cardNo" placeholder="请输入卡号...">
          <div id="show_card_no_error" class="coupon-error color-warning fl">* 代金券不存在或已经被使用！</div>
        </div>
        <div class="discount-input-group fl width100" style="margin-bottom:0;">
          <label for="密码">密码：</label>
          <input type="text" id="cardPassword" placeholder="请输入密码...">
          <div id="show_card_password_error" class="coupon-error color-warning fl">* 密码错误！</div>
        </div>
        <span class="color-green" style="margin-left:49px;font-size:12px;">※单次未使用完的金额将自动转入用户余额内</span>
        <p class="discount-box-line"></p>
        <div class="discount-box-btn-wrap">
          <a class="discount-box-btn confirm" href="javascript:useCard();">确定并充值</a>
          <a class="discount-box-btn cancel" href="javascript:void(0);">取消</a>
        </div>
      </div>
    </div>
  </div>
  </form>
  <!-- 使用礼品卡 结束  -->
</div>
</body>
</html>