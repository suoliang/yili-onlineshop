<@compress single_line=true>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>fushionbaby 母婴用品【密码修改】</title>
	<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
	<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
	<#include "/common/link.ftl"/>
	<!--自定义-->
	<script type="text/javascript" src="${rc.contextPath}/web-js/membercenter/passMod.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
	<script type="text/javascript" language="javascript">
	    var _ContextPath = "${rc.contextPath}";
    </script>
</head>
<body>
	<div class="pass-mod">
		<h3 class="center-title">密码修改</h3>
		<div class="info-list clearfix">
			<div class="info-list-l genera">当前密码</div>
			<div class="info-list-r">
				<input type="password" id="oldPwd" class="pet-info" placeholder="输入当前密码"/>
			</div>
			<div class="error" id="show_old_pwd"><em>*</em><span id="check_old_pwd"></span></div>
		</div>
		<div class="info-list clearfix">
			<div class="info-list-l user-name">输入新密码</div>
			<div class="info-list-r">
				<input type="password" id="newPwd1" class="pet-info" placeholder="输入新的密码"/>
			</div>
			<div class="error" id="show_new_pwd1"><em>*</em><span id="check_new_pwd1"></span></div>
		</div>
		<div class="info-list clearfix">
			<div class="info-list-l user-name">确认新密码</div>
			<div class="info-list-r">
				<input type="password" id="newPwd2" class="pet-info" placeholder="再次输入新的密码"/>
			</div>
			<div class="error" id="show_new_pwd2"><em>*</em><span id="check_new_pwd2"></span></div>
		</div>
		<a class="mod-btn info-desc" href="javascript:change();">确认修改</a>
		<div class="pass-check">
			<#-- <div class="close"></div> --> 
			<p class="desc-info" id="resMsg"></p>
			<button class="pay-btn con-ec pass-change" onClick="myOk();">确定</button>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".if6",parent.document).addClass('list-current');
			$(".if1",parent.document).removeClass('list-current');
		});	
	</script>
</body>
</html>
</@compress>