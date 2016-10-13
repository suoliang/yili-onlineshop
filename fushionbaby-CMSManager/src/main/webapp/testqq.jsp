<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- qq官方给的测试id，要提换为自己的 -->
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="100229030" charset="utf-8"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/common-js.jsp" flush="true"></jsp:include>
<title>你好，fushionbaby</title>
</head>
<body>
<span id="qqLoginBtn"></span>
<script type="text/javascript">
	QC.Login({
		btnId:"qqLoginBtn"	//插入按钮的节点id
	});
</script>

<script type="text/javascript">
var paras = {};

QC.api("get_user_info", paras)
	.success(function(s){//成功回调
		alert("获取用户信息成功！当前用户昵称为："+s.data.nickname);
	})
	.error(function(f){//失败回调
		alert("获取用户信息失败！");
	})
	.complete(function(c){//完成请求回调
		alert("获取用户信息完成！");
	});
</script>


<script type="text/javascript">

function check_isLogin(){
	if(QC.Login.check()){//如果已登录
		QC.Login.getMe(function(openId, accessToken){
			alert(["当前登录用户的", "openId为："+openId, "accessToken为："+accessToken].join("\n"));
		alert(openId);
		alert(accessToken);
		//可以通过Ajax将数据传递到后台
		//有可能是Ajax和现在有冲突？
		$.post("${pageContext.request.contextPath }/user/getQQUserInfo.do",{openId:openId,accessToken:accessToken,time:new Date().getTime()},
				
				function(data){
			
			alert(data);
		});
	//	alert("nihao");
		});//...
	}
}
</script>


<script type="text/javascript">


function LoginAsSina(){
	window.document.location.href="${pageContext.request.contextPath }/user/LoginAsSina.do";
}

</script>
	
<a href="javascript:check_isLogin()">传值进到后台</a>


<a href="javascript:LoginAsSina()"> sina微博</a>

<div class="bdsharebuttonbox">
<a href="#" class="bds_more" data-cmd="more"></a>
<a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
<a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博">
</a><a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
</div>




</body>

<script>window._bd_share_config={"common":{"bdSnsKey":{"tqq":"mPWB4QP9FNxqCeba"},"bdText":"","bdMini":"2","bdMiniList":["qzone","tsina","weixin","renren","tqq","sqq","fx","mail","isohu","fbook","twi","copy"],"bdPic":"","bdStyle":"0","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>


</html>