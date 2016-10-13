<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【个人中心】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
	<script type="text/javascript" >
		function listAddress(){
				window.top.location.href = '${rc.contextPath}/membercenter/address.do?time='+new Date().getTime();
		}
	</script>
<body>
	<div class="person-main">
		<div class="main-r-hd">
			<ul>
				<li class="list-user">
					<div class="pep-pic"><img src="${rc.contextPath}/views/images/showPhoto.do.png"></div>
					<div class="calculus">
						<p><span class="user-name" id="user-name">${(user.login_name)!'兜世宝'}</span></p>
						<p>我的兜米：<span class="calculus-num" id="calculus-num">${(user.epoints)!'6000'}</span></p>
					</div>
				</li>
				<li class="list-info add_li">
					<div class="list-pic add"></div>
					<a class="tex-desc" href="javascript:listAddress();">我的收货地址</a>
				</li>
				<li class="list-info pass_li">
					<div class="list-pic pass"></div>
					<a class="tex-desc" href="${rc.contextPath}/membercenter/pass_mod.do">修改我的密码</a>
				</li>
			</ul>
		</div>
		<div class="center-div-r fl">
			<div class="rank fl">
				<span>会员等级：</span>
				<a id="gradeName" href="javascript:void(0);"></a>
			</div>
			<div class="sign fl">
				<span id="signText">点击签到</span>
				<a id="sign" class="qiandao_btn qiandao_btn_enabled" href="javascript:void(0);">签到领兜米</a>
				<div class="msg_hide">
					<p>兜米 +<i></i></p>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" >
		$(document).ready(function() {	    
			$.ajax({
				type:"POST",
				url: _ContextPath+'/login/getUserInfo.do?time='+new Date().getTime(),
				async:false,
				data:new Date().getTime(),
				dataType:"json",
				success: function(data){
					if (data.responseCode == "0") {
						if (data.data.login_name != null) {
							$("#user-name").html(data.data.login_name);
							$("#calculus-num").html(data.data.epoints);
							$("#gradeName").html(data.data.gradeName);
						}
					}
				}
			});
			
			$("#sign").click(function() {
				
			});
			
			$('.qiandao_btn_enabled').click(function(){
				$('.qiandao_btn').unbind("click");
				$.ajax({
					type:"POST",
					url: _ContextPath+'/memberSign/sign.do?time='+new Date().getTime(),
					async:false,
					data:new Date().getTime(),
					dataType:"json",
					success: function(data){
						if (data.responseCode == "0") {
							/*$("#signText").html("签到成功");
							$("#calculus-num").html(data.data);*/
							var add_doumi_num = data.data;/*签到增加的兜米数量*/
							$('.msg_hide p i').html(add_doumi_num);
							$('.calculus-num').html(parseInt($('.calculus-num').html())+add_doumi_num);/*增加兜米*/
									/*开始动画*/
							$(".qiandao_btn_enabled").addClass('qiandao_btn_disabled').html('今日已签到').removeClass('qiandao_btn_enabled');
							$('.msg_hide').fadeIn();
							setTimeout(function(){
								$('.msg_hide').css({'background-position':'18px -147px'});
							},2000);
							setTimeout(function(){
								$('.msg_hide').css({'background-position':'32px -223px'});
							},2025);
							setTimeout(function(){
								$('.msg_hide').css({'background-position':'-302px -63px'});
							},2050);
							setTimeout(function(){
								$('.msg_hide').css({'background-position':'-156px -63px'});
							},2075);
							setTimeout(function(){
								$('.msg_hide').fadeOut();
							},2100);
							setTimeout(function(){
								$('.msg_hide').css({'background-position':'0px -63px'});
							},2700);
						}else {
							/*$("#signText").html(data.msg);*/
							$(".qiandao_btn_enabled").addClass('qiandao_btn_disabled').html('今日已签到');
						}
					}
				});
				
			});
			
			$.ajax({
					type:"POST",
					url: _ContextPath+'/memberSign/sign.do?type=1&time='+new Date().getTime(),
					async:false,
					data:new Date().getTime(),
					dataType:"json",
					success: function(data){
						if (data.responseCode != "0") {
							$('.qiandao_btn').unbind("click");
							$(".qiandao_btn_enabled").addClass('qiandao_btn_disabled').html('今日已签到');
						}
					}	
			});
	    });
    </script>
</body>
</html>