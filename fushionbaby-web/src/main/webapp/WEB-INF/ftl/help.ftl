<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Fushionbaby帮助</title>
		<meta name="keywords" content="母婴用品,fushionbaby,宝宝网站,婴儿玩具,购物网站 婴儿"/>
		<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
		<link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.3.0/css/font-awesome.min.css">
		<link rel="stylesheet"  href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<link rel="stylesheet" href="${rc.contextPath}/views/css/help.css?v=${EnvironmentConstant.DEPLOY_VERSION}">
		<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript" src="${rc.contextPath}/views/js/help.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript">
			var _ContextPath = "${rc.contextPath}";
			$(document).ready(function() {
				$(".search .search-btn").click(function(){
					var searchKey= $(".search-text").val();
					window.location.href= _ContextPath + "/product/search-list.do?searchKey="+encodeURI(encodeURI(searchKey));
				});
			});
		</script>
	</head>
	<body>
		<!--[if IE 6]>
		<script src="js/iepng.js" type="text/javascript"></script>
		<script type="text/javascript">
		EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
		</script>
		<![endif]-->
		<#include "/common/topbar.ftl" />
		<!--site-topbar end-->
		<#include "/common/header.ftl" />
		<!-- site-header end -->
		<div class="container">
			<div class="help_left">
				<ul>
					<h3 class="marginT0 menu_0_h3">使用帮助<i class="fa fa-angle-down"></i></h3>
					<li class="1_1_btn help_on menu_0">新手指南<i class="fa fa-angle-right"></i></li>
					<li class="1_2_btn menu_0">常见问题<i class="fa fa-angle-right"></i></li>
					<li class="1_3_btn menu_0">用户协议<i class="fa fa-angle-right"></i></li>
					<li class="1_4_btn menu_0">关于发票<i class="fa fa-angle-right"></i></li>
					<li class="1_5_btn menu_0">售后政策<i class="fa fa-angle-right"></i></li>
					<li class="1_6_btn menu_0">反诈骗公告<i class="fa fa-angle-right"></i></li>
					<li class="1_7_btn menu_0">投诉与建议<i class="fa fa-angle-right"></i></li>
					<h3 class="menu_1_h3">售后支持<i class="fa fa-angle-down"></i></h3>
					<li class="2_1_btn menu_1">正品保障<i class="fa fa-angle-right"></i></li>
					<li class="2_2_btn menu_1">退款说明<i class="fa fa-angle-right"></i></li>
					<li class="2_3_btn menu_1">7天无理由退货<i class="fa fa-angle-right"></i></li>
					<h3 class="menu_2_h3">支付方式<i class="fa fa-angle-down"></i></h3>
					<li class="3_1_btn menu_2">在线支付<i class="fa fa-angle-right"></i></li>
					<h3 class="menu_3_h3">配送方式<i class="fa fa-angle-down"></i></h3>
					<li class="4_1_btn menu_3">验货签收<i class="fa fa-angle-right"></i></li>
					<li class="4_2_btn menu_3">物流信息<i class="fa fa-angle-right"></i></li>
					<li class="4_3_btn menu_3">配送说明<i class="fa fa-angle-right"></i></li>
					<h3 class="menu_4_h3">关于我们<i class="fa fa-angle-down"></i></h3>
					<li class="5_1_btn menu_4">了解Fushionbaby<i class="fa fa-angle-right"></i></li>
					<li class="5_2_btn menu_4">加入我们<i class="fa fa-angle-right"></i></li>
					<li class="5_3_btn menu_4">联系我们<i class="fa fa-angle-right"></i></li>
					<li class="5_4_btn menu_4">招商合作<i class="fa fa-angle-right"></i></li>
					<li class="5_5_btn menu_4">Fushionbaby承诺<i class="fa fa-angle-right"></i></li>
				</ul>
			</div>
			<div class="help_right">
				<!-- 新手指南 开始  -->
				<div id="1_1" class="help_box" style="display:block">
					<h3><i class="fa fa-diamond"></i>新手指南</h3>
					<p><strong>轻松购物五步走：</strong></p>
					<p>登陆或注册→2）挑选宝贝→3）加入购物车→4）支付宝贝货款→5）收货&评价</p>
					<p><img src="${rc.contextPath}/views/images/help/1_1_1.jpg" alt=""></p>
					<p><strong>1）登陆或注册</strong></p>
					<p><strong>一、新用户注册</strong></p>
					<p>第一步：在浏览器内输入Fushionbaby网址：http://www.fushionbaby.com，点击页面左上方“快速注册”按钮注册Fushionbaby账号；</p>
					<p><img src="${rc.contextPath}/views/images/help/1_1_2.jpg" alt=""></p>
					<p>第二步：注册前请仔细阅读《用户协议》和《隐私政策》，您可以选择手机号码或电子邮箱两种方式进行账号注册，并根据相应提示在信息栏内填入您的注册信息完成相关用户注册。</p>
					<p><img src="${rc.contextPath}/views/images/help/1_1_3.jpg" alt=""></p>
					<p></p>
					<p><strong>二、老用户登录</strong></p>
					<p>如您已经拥有Fushionbaby账号，请点击页面左上方的 “登录” 按钮；</p>
					<p><img src="${rc.contextPath}/views/images/help/1_1_2.jpg" alt=""></p>
					<p>在登录页面的信息栏内填入相应信息，点击“登录”按钮进行登录。登录成功后，系统将自动跳转至Fushionbaby首页。</p>
					<p><img src="${rc.contextPath}/views/images/help/1_1_4.jpg" alt=""></p>
					<ul class="inside">
						<p><strong>2）挑选宝贝</strong></p>
						<li>搜索关键字，找到你想要的宝贝；</li>
						<li>根据商品分类，定义你的目标物；</li>
						<li>你信赖的品牌，为你独家奉上；</li>
						<li>一键分享，让朋友了解宝贝喜好。</li>
					</ul>
					<ul class="inside">
						<p><strong>3）加入购物车</strong></p>
						<li>搜索关键字，找到你想要的宝贝；</li>
						<li>添加收货地址，我们帮您叫送货小哥；</li>
						<li>安心购物，购物车不下单宝贝跑不掉。</li>
					</ul>
					<ul class="inside">
						<p><strong>4）支付宝贝货款/货到付款</strong></p>
						<li>支持支付宝、微信、银联等多种支付方式；</li>
						<p class="marginT10 marginB10"><img src="${rc.contextPath}/views/images/help/1_1_5.jpg" alt=""></p>
						<li>记得使用宝贝优惠券和会员积分，给您最实惠的选择；</li>
						<li>选择货到付款，网购无忧；</li>
						<li>订单生成，可查询宝贝发货状态。</li>
					</ul>
					<ul class="inside">
						<p><strong>5）收货&评价</strong></p>
						<li>订单发货，快递小哥加速马力，贴心奉上宝贝；</li>
						<li>对宝贝爱不释手，给出好评；</li>
						<li>不满意新宝贝，7天无忧退货。</li>
					</ul>
				</div>
				<!-- 新手指南 结束  -->
				<!-- 常见问题 开始  -->
				<div id="1_2" class="help_box">
					<h3><i class="fa fa-diamond"></i>常见问题</h3>
					<p><strong>为了减少您购物时可能的等待时间，建议您先仔细阅读下面常见问题</strong></p>
					<p><i class="fa fa-question-circle"></i><strong>1. 忘记了登陆密码怎么办？</strong></p>
					<p>您可以登陆我们官方网站（www.fushionbaby.com)，在页面左上角点击登陆，然后再点击忘记密码后通过注册时填写的邮箱或者手机取回密码。 </p>
					<p><i class="fa fa-question-circle"></i><strong>2. 收到的产品不满意，如何办理退换货手续？</strong></p>
					<p>我们的网站支持7天无理由退换货。<br>如果您在收到产品7天之内，并且产品不影响二次销售（包装、吊牌都在，未洗过、未使用）的情况下，您可以申请退货，退货运费需要自理；<br>如果您收到的产品存在质量问题，请先拍照，然后联系我们的在线QQ、提供图片及详情，核实后我们会承担您的来回运费。<br>为了确保退换货流程的进行和准确性，请在线联系我们客服办理相关手续。</p>
					<p><i class="fa fa-question-circle"></i><strong>3. 我可以选择快递公司吗？</strong></p>
					<p>Fushionbaby所有订单都会优先选择国内知名快递公司发送，包括顺丰、圆通等；如有偏于地区快递无法到达的，我们会择优选择。 </p>
					<p><i class="fa fa-question-circle"></i><strong>4. 订单什么时候发货？大概多长时间可以收到货？</strong></p>
					<p>在您下订单付款之后，我们一般会在24小时内发货，物流时间以快递方面为准，您可以通过顺丰快递的官网查询物流状态。 </p>
					<p><i class="fa fa-question-circle"></i><strong>5. 发现漏发或者错发，应该怎么办？</strong></p>
					<p>请您先准备好漏发、错发的相关商品信息及图片（实物拍摄图），需清晰能明确显示尺码，颜色，缺货等情况，然后联系我们的在线客服办理相关申请。 </p>
					<p><i class="fa fa-question-circle"></i><strong>6. 购物可以开具发票吗？</strong></p>
					<p>可以。如您在Fushionbaby网上商城购物需要开具发票，请于订单确认的时候勾选“开具发票”选项，订单会自动计算您的发票金额（包括6%消费税）。<br>如您需开具企业增值税发票，点选“开具发票”按钮，填写发票抬头，选择发票内容，具体也可咨询客服热线。<br>
由于Fushionbaby所售商品均来自海外，商品价格不包含消费税，顾客在索取发票时需要支付订单金额的6%消费税的费用。</p>
					<p><i class="fa fa-question-circle"></i><strong>7. 我没有支付宝、财付通等付款账号，可以通过网上银行支付吗？</strong></p>
					<p>我们的网站支持支付宝、微信、银联多种付款方式，只要您的银行卡开通了网上银行，也可以通过支付宝进行网上银行支付。 </p>
					<p id="doumi"><i class="fa fa-question-circle"></i><strong>8. 什么是兜米？如何使用？</strong></p>
					<p>“兜米”即网站积分，兜米可以直接当钱使用，100兜米可以当一元钱使用，在下单付款的时候直接选择抵扣即可。<br>所有订单在确认购物成功之后即可获取兜米，我们会将相应的积分以“兜米”的形式发放至顾客的账户中，顾客可在下次购物时直接使用。</p>
					<p><strong>没能解决您的问题？</strong></p>
					<p>拨打客服热线：4006-282-528，或者联系在线客服。</p>
				</div>
				<!-- 常见问题 结束  -->
				<!-- 用户协议 开始  -->
				<div id="1_3" class="help_box">
					<h3><i class="fa fa-diamond"></i>用户协议</h3>
					<p><strong>一、Fushionbaby服务条款的确认和接纳</strong></p>
					<p>Fushionbaby（以下简称本站）的各项电子服务的所有权和运作权归本站。本站提供的服务将完全按照其发布的服务条款和操作规则严格执行。用户必须完全同意所有服务条款并完成注册程序，才能成为本站的正式用户。用户确认：本协议条款是处理双方权利义务的当然约定依据，除非违反国家强制性法律，否则始终有效。在下订单的同时，您也同时承认了您拥有购买这些产品的权利能力和行为能力，并且将您对您在订单中提供的所有信息的真实性负责。</p>
					<p><strong>二、服务简介</strong></p>
					<p>本站运用自己的操作系统通过国际互联网络为用户提供网络服务。同时，用户必须： </p>
					<ul class="marginT0 marginB10">
						<li>自行配备上网的所需设备，包括个人电脑、调制解调器或其他必备上网装置。 </li>
						<li>自行负担个人上网所支付的与此服务有关的电话费用、网络费用。 </li>
					</ul>
					<p>基于本站所提供的网络服务的重要性，用户应同意： </p>
					<ul class="marginT0 marginB10">
						<li>提供详尽、准确的个人资料。</li>
						<li>不断更新注册资料，符合及时、详尽、准确的要求。 </li>
					</ul>
					<p>本站对用户的电子邮件、手机号等隐私资料进行保护，承诺不会在未获得用户许可的情况下擅自将用户的个人资料信息出租或出售给任何第三方，但以下情况除外：</p>
					<ul class="marginT0 marginB10">
						<li>用户同意让第三方共享资料； </li>
						<li>用户同意公开其个人资料，享受为其提供的产品和服务； </li>
						<li>本站需要听从法庭传票、法律命令或遵循法律程序； </li>
						<li>本站发现用户违反了本站服务条款或本站其它使用规定。 </li>
					</ul>
					<p>关于用户隐私的具体协议以本站的隐私声明为准。 </p>
					<p>如果用户提供的资料包含有不正确的信息，本站保留结束用户使用网络服务资格的权利。</p>
					<p><strong>三、价格和数量</strong></p>
					<p>本站将尽最大努力保证您所购商品与网站上公布的价格一致，但价目表和声明并不构成要约。本站有权在发现了其网站上显现的产品及订单的明显错误或缺货的情况下，单方面撤回任何承诺。同时，本站保留对产品订购的数量的限制权。</p>
					<p>产品的价格和可获性都在本站上指明。这类信息将随时更改且不发任何通知。商品的价格都包含了增值税。如果发生了意外情况，在确认了您的订单后，由于供应商提价，税额变化引起的价格变化，或是由于网站的错误等造成商品价格变化，您有权取消您的订单，并希望您能及时通过电子邮件或电话通知本站客户服务部。 </p>
					<p><strong>四、服务条款的修改</strong></p>
					<p>本站将可能不定期的修改本用户协议的有关条款，一旦条款及服务内容产生变动，本站将会在重要页面上提示修改内容。如果不同意本站对条款内容所做的修改，用户可以主动取消获得的网络服务。如果用户继续使用本站的服务，则视为接受服务条款的变动。本站保留随时修改或中断服务而不需告知用户的权利。本站行使修改或中断服务的权利，不需对用户或第三方负责。 </p>
					<p><strong>五、用户的帐户，密码和安全性</strong></p>
					<p>用户一旦注册成功，成为本站的合法用户，将得到一个密码和用户名。您可随时根据指示改变您的密码。用户将对用户名和密码安全负全部责任。另外，每个用户都要对以其用户名进行的所有活动和事件负全责。用户若发现任何非法使用用户帐户或存在安全漏洞的情况，请立即通告本站。 </p>
					<p><strong>六、拒绝提供担保</strong></p>
					<p>用户个人对网络服务的使用承担风险。本站对此不作任何类型的担保，不论是明确的或隐含的，但是不对商业性的隐含担保、特定目的和不违反规定的适当担保作限制。本站不担保服务一定能满足用户的要求，也不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保。本站对在本站上得到的任何商品购物服务或交易进程，不作担保。 </p>
					<p><strong>七、有限责任</strong></p>
					<p>本站对任何直接、间接、偶然、特殊及继起的损害不负责任，这些损害可能来自：不正当使用网络服务，在网上购买商品或进行同类型服务，在网上进行交易，非法使用网络服务或用户传送的信息有所变动。这些行为都有可能会导致本站的形象受损，所以本站事先提出这种损害的可能性。 </p>
					<p><strong>八、对用户信息的存储和限制</strong></p>
					<p>本站不对用户所发布信息的删除或储存失败负责。本站有判定用户的行为是否符合本站服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，本站有中断对其提供网络服务的权利。 </p>
					<p><strong>九、保障用户</strong></p>
					<p>同意保障和维护本站全体成员的利益，负责支付由用户使用超出业务范围引起的律师费用，违反服务条款的损害补偿费用等。 </p>
					<p><strong>十、结束服务</strong></p>
					<p>用户或本站可随时根据实际情况中断一项或多项网络服务。本站不需对任何个人或第三方负责而随时中断服务。用户对后来的条款修改有异议，或对本站的服务不满，可以行使如下权利： </p>
					<ul class="marginT0 marginB10">
						<li>停止使用本站的网络服务。 </li>
						<li>通告本站停止对该用户的服务。 </li>
					</ul>
					<p>结束用户服务后，用户使用网络服务的权利马上中止。从那时起，用户有权利，本站也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。 </p>
					<p><strong>十一、通告</strong></p>
					<p>所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。本站的活动信息也将定期通过页面公告及电子邮件方式向用户发送。用户协议条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。 </p>
					<p><strong>十二、参与广告策划</strong></p>
					<p>用户在他们发表的信息中加入宣传资料或参与广告策划，在本站的免费服务上展示他们的产品，任何这类促销方法，包括运输货物、付款、服务、商业条件、担保及与广告有关的描述都只是在相应的用户和广告销售商之间发生。本站不承担任何责任，本站没有义务为这类广告销售负任何一部分的责任。</p>
					<p><strong>十三、网络服务内容的所有权</strong></p>
					<p>本站定义的网络服务内容包括：文字、软件、声音、图片、录象、图表、广告中的全部内容；电子邮件的全部内容；本站为用户提供的其他信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在本站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。本站所有的文章版权归原文作者和本站共同所有，任何人需要转载本站的文章，必须征得原文作者或本站授权。 </p>
					<p><strong>十四、责任限制</strong></p>
					<p>如因不可抗力或其他本站无法控制的原因使本站销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等，本站不承担责任。但是本站会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失。</p>
					<p>除了本站的使用条件中规定的其它限制和除外情况之外，在中国法律法规所允许的限度内，对于因交易而引起的或与之有关的任何直接的、间接的、特殊的、附带的、后果性的或惩罚性的损害，或任何其它性质的损害，本站、本站的董事、管理人员、雇员、代理或其它代表在任何情况下都不承担责任。本站的全部责任，不论是合同、保证、侵权（包括过失）项下的还是其它的责任，均不超过您所购买的与该索赔有关的商品价值额。 </p>
					<p><strong>十五、法律管辖和适用</strong></p>
					<p>本协议的订立、执行和解释及争议的解决均应适用中国法律。 </p>
					<p>如发生本站服务条款与中国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它合法条款则依旧保持对用户产 生法律效力和影响。</p>
					<p>本协议的规定是可分割的，如本协议任何规定被裁定为无效或不可执行，该规定可被删除而其余条款应予以执行。 </p>
					<p>如双方就本协议内容或其执行发生任何争议，双方应尽力友好协商解决；协商不成时，任何一方均可向本站所在地的人民 法院提起诉讼。 </p>
				</div>
				<!-- 用户协议 结束  -->
				<!-- 关于发票 开始  -->
				<div id="1_4" class="help_box">
					<h3><i class="fa fa-diamond"></i> 关于发票</h3>
					<p>Fushionbaby所售商品，如顾客需要开具发票的，只需在订单确认时选择“开具发票”，并填写发票抬头，选择发票内容。该发票为商品专用发票，会随订单包裹一同基础。 </p>
					<p>由于Fushionbaby所售商品均来自海外，商品价格不包含消费税，顾客在索取发票时需要支付订单金额的6%消费税的费用。</p>
					<p>一个包裹对应一张发票，发票金额与订单金额一致，网站不支持一个订单对应多张发票或多个订单合并一张发票。</p>
					<p><i class="fa fa-question-circle"></i><strong>何为消费税？</strong></p>
					<p>消费税是对在我国境内生产、委托加工和进口应税消费品的单位和个人就其应税消费品的销售额或销售数量征收的一种税。</p>
					<p>订单状态显示已发货，您就可以开始跟踪快递的物流信息了。 </p>
					<p><strong>温馨提示：</strong></p>
					<p>点击“查看物流”时查看到的物流单号跟踪不到物流信息，可能是物流公司还没来得及录入或者品牌商填写了错误的物流单号，请不用着急，或联系Fushionbaby客服，我们会帮您联系品牌商核实实际发货情况。 </p>
				</div>
				<!-- 关于发票 结束  -->
				<!-- 售后政策 开始  -->
				<div id="1_5" class="help_box">
					<h3><i class="fa fa-diamond"></i>售后政策</h3>
					<p>本政策依据《中华人民共和国消费者权益保护法》、《中华人民共和国产品质量法》及其他相关法规制定。凡是在Fushionbaby网上商城购买商品的客户，请仔细阅读此政策的详细介绍。如果商品详情页面无特殊退换货规定，则将按以下条款执行。 </p>
					<p><strong>7天无理由退货</strong></p>
					<p>自客户签收商品之日起7日内，在商品完好的情况下，可享受无理由退货。由于商品的特殊性质，以下商品不享有7天无理由退货： </p>
					<ul class="marginT0 marginB10">
						<li>1、消费者定制商品； </li>
						<li>2、在线下载或消费者拆封的音像制品、计算机软件等数字化商品； </li>
						<li>3、食用类商品，例如食品、保健品、饮料、药品、酒类、奶粉、婴儿辅食等； </li>
						<li>4、个人护理、贴身类商品，例如美容、美护、内衣裤、贴身袜裤等； </li>
						<li>5、其他根据商品性质不宜退货的商品； </li>
						<li>6、临近保质期商品。 </li>
					</ul>
					<p><strong>退换货注意事项</strong></p>
					<ul class="marginT0 marginB10">
						<li>1、Fushionbaby网上商城销售商品均为正品，可按国家三包和厂商售后政策，享受相应的退货、更换、维修和保养服务； </li>
						<li>2、Fushionbaby网上商城会为所有客户开具发票作为客户的质保凭证，请客户妥善保留原件作为后续质保之凭证；</li>
						<li>3、换货的定义是结合国家三包和具体厂商售后政策而定； </li>
						<li>4、选择退货的客户必须提供对应商品的发票，并保证商品的完好，商品完好是指退回商品需保持和签收时包装内的所有物品数量一致，且均无人为损害，包括原来的包装物、 用户手册（如果有）和其他由厂商或Fushionbaby提供的一切附件、赠品。如果某一部分丢失或损坏，可能会影响您的退货； </li>
						<li>5、由于商品的不同，各厂商服务政策的差异，在实施细则上，如保修操作、售后服务、技术支持等方面为客户提供服务可能会有所差异。 </li>
					</ul>
					<p><strong>以下情况将不提供退换货服务： </strong></p>
					<ul class="marginT0 marginB10">
						<li>1、任何非Fushionbaby官方网站出售的商品； </li>
						<li>2、过保商品（超过三包保修期的商品）； </li>
						<li>3、未经授权的维修、误用、碰撞、疏忽、滥用、进液、事故、改动、不正确的安装所造成的商品质量问题，或撕毁、涂改标贴、机器序号、防伪标记；</li>
						<li>4、无法提供商品的发票、保修卡等三包凭证或者三包凭证信息与商品不符及被涂改的； </li>
						<li>5、因拍照、灯光、各显示器显示效果不同,您在电脑上看到的商品图片可能会与您收到的商品存在颜色差异,实际颜色以商品为准,色差不在退换服务范围内。（符合上述第一条的情况除外）； </li>
						<li>6、对于顾客要求定制或修改加工的商品； </li>
						<li>7、其他依法无法办理退换货的。 </li>
						<li>8、因批次不同而导致产品色差，包装印刷排版变更，配件变更、运输过导致外包装有褶皱但不影响使用情况等不可抗因素，不属于商品品质问题。 </li>
					</ul>
					<p><strong>以上规则的最终解释权由Fushionbaby网上商城所有。<br>有任何疑问请咨询在线客服，或拨打客服电话4006-282-528，或发送邮件至<a href="mailto:cs@fushionbaby.com">cs@fushionbaby.com</a>。 </strong></p>
				</div>
				<!-- 售后政策 结束  -->
				<!-- 反诈骗公告 开始  -->
				<div id="1_6" class="help_box">
					<h3><i class="fa fa-diamond"></i>反诈骗公告</h3>
					<p><strong>Fushionbaby网上商城严厉打击冒用Fushionbaby名义进行诈骗的行为 </strong></p>
					<p>近日，我司接到一些商家与消费者反映：有不法分子冒用Fushionbaby网上商城名义进行诈骗活动，经了解，其使用骗术主要有以下形式：</p>
					<ul class="marginT0 marginB10">
						<li>1、冒充Fushionbaby销售人员，以Fushionbaby名义开展推广活动，向商家骗取广告推广费用； </li>
						<li>2、以Fushionbaby客服名义向消费者发布中奖等消息，以收取手续费等名义，骗取消费者钱财；</li>
						<li>3、以类似“Fushionbaby网络联盟”或Fushionbaby旗下团购网站等名义进行宣传，牟取不正当利益。 </li>
						<li>4、Fushionbaby销售人员不会单独以广告推广为理由以个人名义向商家收取任何推广费用； </li>
						<li>5、冒充Fushionbaby客服或内部人员要求顾客去银行汇款等汇款操作；</li>
					</ul>
					<p>Fushionbaby将尽最大努力保护客户的合法权益不受侵害。对于日前出现的冒充Fushionbaby名义进行诈骗活动的不法行为，敬请广大Fushionbaby商家与消费者提高防范意识，如发现受骗后第一时间亲自向公安机关报案。</p>
					<p>如果您对Fushionbaby有任何疑问，可拨打电话4006-282-528咨询解决。特此声明。 </p>
					<p>Fushionbaby 2014年12月 </p>
				</div>
				<!-- 反诈骗公告 结束  -->
				<!-- 投诉与建议 开始  -->
				<div id="1_7" class="help_box">
					<h3><i class="fa fa-diamond"></i>投诉与建议</h3>
					<p><strong>如果您购买的Fushionbaby产品在使用过程中遇到任何的问题，您可以随时反馈给我们。</strong></p>
					<p>如果您付款或消费时遇到问题，请联系我们的客服热线：4006-282-528</p>
					<p>投诉时，为了能更快的给您解决问题，请您务必提交以下内容：</p>
					<ul class="marginT0 marginB10">
						<li>1、您购买订单编号及产品名称；</li>
						<li>2、您的姓名及联系方式，方便我们的工作人员及时与您联系; </li>
						<li>3、具体投诉内容描述。 </li>
					</ul>
					<p>请先"登录"或"注册"再提问,谢谢。 </p>
					<p>如您对Fushionbaby网上商城有任何建议或意见，您可以通过以下方式与我们取得联系，谢谢。</p>
					<p>在线客服：如客服不在线可留言，客服看到后会第一时间给予您回复。</p>
					<p>客服热线：4006-282-528 </p>
					<p>邮件地址：<a href="mailto:cs@fushionbaby.com">cs@fushionbaby.com</a> </p>
				</div>
				<!-- 投诉与建议 结束  -->
				<!-- 正品保障 开始  -->
				<div id="2_1" class="help_box">
					<h3><i class="fa fa-diamond"></i>正品保障</h3>
					<p><strong>品牌授权</strong></p>
					<p>Fushionbaby所售的商品均从正规品牌渠道进货并授权，供应商类型包括品牌生产商、品牌授权总代理商、品牌授权总经销商，公司对所有品牌供应商资质进行严格审核，确保供应商品牌授权资质。目前，Fushionbaby已取得300多个国内外品牌授权，部分品牌为国内独家总代理。</p>
					<p><strong>正品采购</strong></p>
					<p>Fushionbaby所有商品均来自于原产地、品牌经销商、全国总代理等正规采购渠道，并与之签订长期战略正品采购协议。不仅如此，对于供应商的资质、商品品质、相关产品证书及质检报告都严格把控，确保消费者放心购买。 </p>
					<p><strong>金牌服务</strong></p>
					<p>先验货后签收、付款方式多样化、7天无理由退货、7*11小时客服服务.....为您多花心思，用最专业最贴心的服务带给您舒适的购物体验。 </p>
					<p><strong>先验货后签收</strong></p>
					<p>开箱验货，必须的！请与配送员当面核对清单商品，验收货物，确认没有问题后再当场签收。如果不让验货，就拒签吧。</p>
					<p><strong>7天无理由退货</strong></p>
					<p>退货不需理由。无论您因为什么原因放弃了您精挑细选的宝贝，我们都承诺在收到商品的7天内为您办理退货。 </p>
					<p><strong>多种支付方式</strong></p>
					<p>我们提供财富通、支付宝、微信、等多家支付平台在线支付，信用卡储蓄卡随心选，多种方式满足您不同需求。</p>
					<p><strong>7*11小时客服服务</strong></p>
					<p>365天客服热线，7*11小时贴心服务，用心回答您每个提问，让你获得最满意的网购体验。 </p>
					<p><strong>独家代理</strong></p>
					<p>Fushionbaby取得了国内外多个品牌的独家代理权，严格把控和审核供应商的资质，为您精心挑选，值得您100%信赖。 </p>
				</div>
				<!-- 正品保障 结束  -->
				<!-- 退款说明 开始  -->
				<div id="2_2" class="help_box">
					<h3><i class="fa fa-diamond"></i>退款说明</h3>
					<p><strong>温馨提示：退款周期仅供您参考，具体退款周期可能会受银行、支付机构等相关因素影响。</strong></p>
					<table class="refund-tab">
						<tbody>
							<tr>
								<th rowspan="2" colspan="2">支付方式</th>
								<th rowspan="2">退款方式</th>
								<th colspan="2">退款说明</th>
							</tr>
							<tr>
								<td>Fushionbaby处理周期</td>
								<td>银行退款处理周期</td>
							</tr>
							<tr>
								<td rowspan="2">在线支付</td>
								<td>储蓄卡</td>
								<td>退回原支付卡</td>
								<td>3个工作日</td>
								<td>1-7个工作日</td>
							</tr>
							<tr>
								<td>信用卡</td>
								<td>退回原支付卡</td>
								<td>3个工作日</td>
								<td>1-15个工作日</td>
							</tr>
							<tr>
								<td rowspan="3">在线支付--手机支付平台</td>
								<td>银行卡</td>
								<td>退回原支付卡</td>
								<td>3个工作日</td>
								<td>1-15个工作日</td>
							</tr>
							<tr>
								<td rowspan="2">银行卡+电子券</td>
								<td>银行卡支付款项退回手机支付平台账户余额</td>
								<td>3个工作日</td>
								<td>1-2个工作日</td>
							</tr>
							<tr>
								<td>电子券自动原返至手机支付平台账户</td>
								<td>3个工作日</td>
								<td>1-2个工作日</td>
							</tr>
						</tbody>
					</table>
					<p></p>
					<ul class="marginT0 marginB10">
						<li>1、Fushionbaby在线支付及POS机刷卡支付订单退款，如涉及到银行信息Fushionbaby会依据银行及相关机构已经建立的条例处理退款，为了保证客户账户金额的安全，我们均会安排原卡原退。 </li>
						<li>2、由于快捷支付的款项来源为信用卡支付，所以快捷支付的订单只支持原路退款，不支持返还至余额。 </li>
						<li>3、公司转账或支票支付的订单，需与客服人员确认公司相关信息后进行公司转账,目前Fushionbaby只支持原路退回至客户原支付的公司账户中，给您带来的不便请您谅解。</li>
						<li>4、因质量问题退换货产生的运费由客户先行垫付，退换货手续完成后，Fushionbaby网上商城会将运费返还客户；非质量问题退换货产生的运费由客户自理。 </li>
						<li>5、因银行系统升级，退银行卡服务不支持信用卡退款。 </li>
					</ul>
					<p><i class="fa fa-question-circle"></i><strong>如何补贴回寄运费？</strong></p>
					<p>会员需选择快递方式将商品寄回，并支付快递费用，Fushionbaby 不接受平邮或以到付形式寄回包裹，Fushionbaby 将在退款时将运费退回您的账户，运费补贴有效期为1年。多张订单同一包裹寄回，仅补贴一次运费。Fushionbaby有权对无正当理由多次退货或拒收商品的用户保留不再给予退货补贴的权利。原满额免运费的订单，发生整单退货后，退款金额为当时交易的总金额。 </p>
					<p><i class="fa fa-question-circle"></i><strong>如何办理退款方式和时效？</strong></p>
					<p>Fushionbaby将在收到回寄商品的三天内办理退款。为了缩短到帐时间，订单退货产生的金额，将直接原路返回退到您当时支付的账户中，如无法原路返回，则会退到您当时填写的账户。办理后需要3~5个工作日到账。 </p>
					<p><strong>如有疑问请与Fushionbaby 服务中心联系。 </strong></p>
					<p>服务热线：4006-282-528 </p>
					<p>受理时间：9:00-20:00 </p>
				</div>
				<!-- 退款说明 结束  -->
				<!-- 7天无理由退货 开始  -->
				<div id="2_3" class="help_box">
					<h3><i class="fa fa-diamond"></i>7天无理由退货</h3>
					<p><strong>一、Fushionbaby网上商城销售的均为正品，我们承诺自您收到商品之日起七日内无需说明理由，可为您办理退货，下列商品除外： </strong></p>
					<ul class="marginT0 marginB10">
						<li>1、消费者定制商品； </li>
						<li>2、在线下载或消费者拆封的音像制品、计算机软件等数字化商品； </li>
						<li>3、食用类商品，例如食品、保健品、饮料、药品、酒类、奶粉、婴儿辅食等； </li>
						<li>4、个人护理、贴身类商品，例如美容、美护、内衣裤、贴身袜裤等； </li>
						<li>5、其他根据商品性质不宜退货的商品；</li>
						<li>6、临近保质期商品。 </li>
					</ul>
					<p>顾客退货的商品应当包装完好。（如有赠品，则需包括赠品）及相关发票寄回我们指定退货地址，我们在收到您发票及商品后为您办理退款。</p>
					<p><strong>二、以下情况不予办理退换货：</strong></p>
					<ul class="marginT0 marginB10">
						<li>1、任何非Fushionbaby官方网站出售的商品； </li>
						<li>2、过保商品（超过三包保修期的商品）； </li>
						<li>3、未经授权的维修、误用、碰撞、疏忽、滥用、进液、事故、改动、不正确的安装所造成的商品质量问题，或撕毁、涂改标贴、机器序号、防伪标记；</li>
						<li>4、无法提供商品的发票、保修卡等三包凭证或者三包凭证信息与商品不符及被涂改的；</li>
						<li>5、因拍照、灯光、各显示器显示效果不同,您在电脑上看到的商品图片可能会与您收到的商品存在颜色差异,实际颜色以商品为准,色差不在退换服务范围内。（符合上述第一条的情况除外）； </li>
						<li>6、对于顾客要求定制或修改加工的商品；</li>
						<li>7、其他依法无法办理退换货的。 </li>
						<li>8、因批次不同而导致产品色差，包装印刷排版变更，配件变更、运输过导致外包装有褶皱但不影响使用情况等不可抗因素，不属于商品品质问题。</li>
					</ul>
					<p><strong>三、因质量问题退换货产生的运费由客户先行垫付，退换货手续完成后，Fushionbaby网上商城会将运费返还客户；非质量问题退换货产生的运费由客户自理。</strong></p>
					<p><strong>四、如果成套商品（含赠品）中有部分商品存在质量问题，在办理退换货时，必须提供成套商品（含赠品）；</strong></p>
					<p><strong>五、非同款同型号商品无法进行换货；</strong></p>
					<p><strong>六、特别声明：</strong></p>
					<ul class="marginT0 marginB10">
						<li>1、为保障您的权益，请与配送员当面核对商品种类、数量、金额、赠品等是否正确。特别是高档商品（包括：儿童推车、安全座椅等），易损易碎品类（如儿童玩具、衣物洗涤剂等）请务必开箱验货，确认没有问题后再当场签收。</li>
						<li>2、临近保质期商品如无质量问题恕不退换。</li>
						<li>3、请妥善保管好您的发票，办理退换货时，发票需随包裹一并寄回。</li>
						<li>4、寄回退换货商品前请务必与客服联系，否则仓库无法正常接收退货。</li>
						<li>5、符合Fushionbaby网上商城退换货标准的商品，请采用普通快递方式寄回，建议先与客服联系了解退换货流程，我们客服将指导您商品退回时的操作。</li>
						<li>6、不接受平邮、到付、货运的方式寄回商品。</li>
						<li>7、在退货时优惠券等非现金的形式支付将不做返还，我们将只返还您所使用的现金部分。</li>
						<li>8、我们退货需要一定的时间及流程，如寄回的商品、发票等相关信息齐全，我们将在收到您商品后的3-15个工作日退款至您与我们客服联系时所登记的退款账户（请顾客提供的退款账户为当初购物时所支付的付款账号或银行账户）。符合上述第一条的退货的订单，我们将在七日内退款。 </li>
					</ul>
					<p><strong>请顾客在七天内提出退换货申请并在期限内寄回商品，逾期提出退换申请无效，感谢您的配合。</strong></p>
				</div>
				<!-- 7天无理由退货 结束  -->
				<!-- 在线支付 开始  -->
				<div id="3_1" class="help_box">
					<h3><i class="fa fa-diamond"></i>在线支付</h3>
					<p><strong>网上支付说明</strong></p>
					<p>Fushionbaby支持支付宝、微信、银联支付等三家支付平台在线支付，您可以通过信用卡或储蓄卡都能在Fushionbaby购物。</p>
					<p><strong>网上支付</strong></p>
					<p>如果您在支付过程中遇到问题，可以联系Fushionbaby服务热线4006-282-528获得帮助，或联系相关支付平台的官方客服进行电话咨询。</p>
					<p>支付宝官方客服电话：95188</p>
					<p>中国银联官方客服电话：95516</p>
				</div>
				<!-- 在线支付 结束  -->
				<!-- 验货签收 开始  -->
				<div id="4_1" class="help_box">
					<h3><i class="fa fa-diamond"></i>验货签收</h3>
					<p><strong>商品验货与签收</strong></p>
					<p>请您签收商品时，尽量在快递人员在场时当面检查商品；如果快递人员坚持先签收再验货，可以在签收之后立刻检查，或者直接拒签。</p>
					<p><strong>注意事项：</strong></p>
					<ul class="marginT0 marginB10">
						<li>1、请您务必当面核对商品。</li>
						<li>2、买家或者非买家本人进行签收的，Fushionbaby视等同于买家进行验货签收。一旦签收后Fushionbaby视为您已经认可并接受产品。</li>
						<li>3、如果发现上述验货过程中出现问题，请及时联系Fushionbaby在线客服，客服会及时联系快递公司进行交涉，妥善处理，维护买家利益。</li>
					</ul>
				</div>
				<!-- 验货签收 结束  -->
				<!-- 物流信息 开始  -->
				<div id="4_2" class="help_box">
					<h3><i class="fa fa-diamond"></i>物流信息</h3>
					<p><strong>配送方式：</strong></p>
					<p>Fushionbaby所有订单都会优先选择国内知名快递公司发送，包括顺丰、圆通等。如有偏远地区无法抵达，我们会择优选择。</p>
					<p><strong>物流状态查询：</strong></p>
					<p>下单成功之后，可以随时查看订单更新状态，如订单状态显示已发货，您就可以使用快递编号前往对应快递主页跟踪快递的物流信息了。</p>
					<p>订单状态显示已发货，您就可以开始跟踪快递的物流信息了。</p>
					<p><strong>温馨提示：</strong></p>
					<p>点击“查看物流”时查看到的物流单号跟踪不到物流信息，可能是物流公司还没来得及录入或者品牌商填写了错误的物流单号，请不用着急，或联系Fushionbaby客服，我们会帮您联系品牌商核实实际发货情况。 </p>
				</div>
				<!-- 物流信息 结束  -->
				<!-- 配送说明 开始  -->
				<div id="4_3" class="help_box">
					<h3><i class="fa fa-diamond"></i>配送说明</h3>
					<p><strong>我们一直致力于提供优质的配送服务，目前服务的范围及快递标准如下：</strong></p>
					<p>Fushionbaby 提供的配送方式是快递。</p>
					<p>您在Fushionbaby成功购买商品的每一个订单（限一个邮寄地址），系统默认生成一个包裹。</p>
					<p><strong>配送范围：</strong></p>
					<p>Fushionbaby配送范围覆盖全国大部分地区（港澳台地区除外）。</p>
					<p><strong>配送费用：</strong></p>
					<p>购物金额满199元及以上，全国免运费；购物金额＜199元，系统会按顺丰快递实际运费计算，向您收取相应快递费用。</p>
					<p><strong>运费查询（顺丰）：</strong></p>
					<p><a href="http://www.sf-express.com/cn/sc/dynamic_functions/price/">http://www.sf-express.com/cn/sc/dynamic_functions/price/</a></p>
					<p><strong>发货时间：</strong></p>
					<p>您下单成功后，Fushionbaby在核对您所订购的商品、邮寄地址、款项支付等信息无误后确认订单，订单确认后通常会在24小时内将包裹发出；包裹发出后，您可以使用快递编号前往对应快递主页跟踪快递的物流信息了。</p>
					<p><strong>配送时间：</strong></p>
					<p>配送时间以各地区预计配送时间为准，订单发货当天不计算在配送时间内。如：北京地区配送服务预计送货时间为商品发出货后2-4天送达。您的订单10日发货，则预计到货时间为12日-14日。有特别说明的送货时间，配送将按照实际情况顺延。</p>
				</div>
				<!-- 配送说明 结束  -->
				<!-- 了解Fushionbaby 开始  -->
				<div id="5_1" class="help_box">
					<h3><i class="fa fa-diamond"></i>了解Fushionbaby</h3>
					<p><strong>关于我们</strong></p>
					<p>Fushionbaby是一家致力于母婴产品的专业网上购物商城，公司正式成立于2014年8月，产品涉及到婴儿用品、童装、童鞋、早教玩具、车床座椅、妈妈用品及主题家具等，关爱新生婴儿的健康成长，同时也满足热衷品质生活孕妈妈消费者的需求。</p>
					<p>Fushionbaby产品系列以品种多样、品牌丰富、品质优良而著称。 </p>
					<p>Fushionbaby是专门设立为母亲介绍优质婴儿用品的服务平台。我们深知现代母亲对婴幼儿产品选择严格要求，Fushionbaby从世界各地搜罗高品质婴幼儿产品，以确保婴幼儿安全健康使用为宗旨，就是我们对为人母者之服务承诺。</p>
					<p>浏览我们网站，是您给予心爱宝贝的最佳选择。我们精挑细选、严格把关，为您和您的小宝贝寻找最佳之选，我们拥有庞大的产品资源，以期满足您的需求，达到您的要求。 </p>
					<p>何必到处寻觅。Fushionbaby就是您的购物首选！</p>
					<p>Fushionbaby的股东之一倪先生在电视购物平台有着丰富的经验。其作为创始人兼股东发起人中国七个星控股有限公司,该公司已经在香港证券交易所上市(0245.港元)后来被Landune公司收购。2014年，他投资了电子商务平台,建立了这家国际进出口贸易公司，并且推出www.fushionbaby.com网上购物平台，致力于打造国内一流的母婴网上购物商城。</p>
					<p><strong>选择Fushionbaby的八大理由：</strong></p>
					<p><strong>1、正品保证</strong></p>
					<p>Fushionbaby所售商品均来自正规品牌渠道供应商，并取得了相关授权；同时，公司对于所有品牌供应商进行严格的资质审核，保证品质。</p>
					<p><strong>2、国内外多个品牌独家授权</strong></p>
					<p>Fushionbaby在成立初期取得了国内外多家品牌的授权，部分品牌为国内独家总代理；后续会有更多的品牌入驻Fushionbaby，我们通过严选优质品牌、创新产品、有竞争力的价格，帮助顾客选择更好的产品。</p>
					<p><strong>3、具有竞争力的价格</strong></p>
					<p>Fushionbaby降低了上百种商品的价格，实时监测上千种商品的市场价格，确保您以最具竞争力的价格购买到知名品牌商品，有效降低您的家庭支出成本。</p>
					<p><strong>4、丰富的商品结构</strong></p>
					<p>Fushionbaby的产品涉及婴儿用品、奶粉辅食、童装童鞋、早教玩具、车床座椅、纸尿裤等；同时我们也为孕妈妈提供专业的孕妇产品，不仅满足宝宝每个成长阶段的需求，更关爱宝贝家庭的健康成长。</p>
					<p><strong>5、优质的购物体验</strong></p>
					<p>Fushionbaby提供您简洁新颖的网站页面，丰富多样的产品信息，简单快捷的下单方式，安全快速的物流配送，专业贴心的售后服务；同时，您可以通过手机端和Web两种方式来随心购Fushionbaby。 </p>
					<p><strong>6、独一无二的风尚宝贝志</strong></p>
					<p>风尚宝贝志作为Fushionbaby独有的会员增值服务，拥有亲子课堂和户外活动两大板块，除了提供您丰富的产品选择，同时您还能在网上找到宝贝成长课堂，也可以来Fushionbaby为小宝贝找寻新的小伙伴。Fushionbaby用心为新爸爸新妈妈提供一手资讯，为宝贝成长保驾护航。</p>
					<p><strong>7、符合质量要求的物流配送</strong></p>
					<p>Fushionbaby在全国范围（除港澳台）内，所有订单优先选择顺丰作为我们的第一服务商，提供您更快速、舒适、安全的购物体验。</p>
					<p><strong>8、专业一对一的服务</strong></p>
					<p>Fushionbaby设立的专门的客服团队，他们专门为顾客服务，亲自拜访或打电话联系顾客，通过这些方式顾客可以收到有针对性的相关产品、供货和服务的信息以及推荐商品。</p>
				</div>
				<!-- 了解Fushionbaby 结束  -->
				<!-- 加入我们 开始  -->
				<div id="5_2" class="help_box">
          <h3><i class="fa fa-diamond"></i>加入我们</h3>
          <p>2014Fushionbaby起航，如果你热爱生活，充满激情和梦想。</p>
          <p>顾客至尚，开放，分享，拥抱变化。</p>
          <p>创新的互联网思维。</p>
          <p>专业型人才，或行业里手，或技术专精。</p>
          <p>我们欢迎您加Fushionbaby，有意者请投递简历至邮箱：hr@fushionbaby.com</p>
          <p>我们正在寻找:</p>
          <p></p>
          <p><strong>一、Android开发工程师</strong></p>
          <p>岗位职责：</p>
          <ul class="marginT0 marginB10">
            <li>1、负责移动Android产品的详细设计，开发工作； </li>
            <li>2、根据项目任务计划按时完成软件编码和单元测试工作；</li>
            <li>3、按照开发流程编写相应模块的设计文档； </li>
            <li>4、解决研发过程中的关键问题和技术难题； </li>
            <li>5、协助开发经理保证研发工作的质量和进度。 </li>
          </ul>
          <p>岗位要求： </p>
          <ul class="marginT0 marginB10">
            <li>1、有热情，有良好的计划、沟通、组织协调能力、语言表达能力；较强的逻辑分析能力和应变能力；良好的团队合作精神； </li>
            <li>2、本科或以上学历，计算机软件、通讯、数学相关专业优先; </li>
            <li>3、3年以上Java应用开发经验，2年Android平台应用软件开发; </li>
            <li>4、精通Android应用软件的编程架构；熟练使用Eclipse等Android软件开发工具; </li>
            <li>5、精通Android各类UI组件、图形图像、网络、文件系统等模块的使用, 熟悉TeeChart以及相关图表控件的开发; </li>
            <li>6、能独立开展各项工作，并承受工作压力； </li>
            <li>7、思路清晰，思维敏捷，快速的学习能力，良好的英文资料阅读能力; </li>
            <li>8、能承担较大工作压力，具备良好的沟通能力和团队合作精神; </li>
            <li>9、熟悉Html5以及PhoneGap；</li>
            <li>10、有Scrum敏捷开发经验优先。 </li>
          </ul>
          <p></p>
          <p><strong>二、资深视觉设计师</strong></p>
          <p>岗位职责：</p>
          <ul class="marginT0 marginB10">
            <li>1、和团队一起构想和合作完成垂直电商平台（WEB端和移动端），从前期视觉用户研究、设计流行趋势分析到最终的整体视觉设计；</li>
            <li>2、负责推广页面、广告创意设计，活动专题及其他创意性设计；</li>
            <li>3、输出并分享设计观点，提升视觉设计的流程、方法，对团队设计氛围的建设和整体提升做出贡献。</li>
          </ul>
          <p>岗位要求： </p>
          <ul class="marginT0 marginB10">
            <li>1、品牌设计，能理解并善于表现公司要传达的品牌商业理念；</li>
            <li>2、创意能力，敏锐的感觉流行趋势，并将趋势和设计结合，转化为界面的方案；</li>
            <li>3、视觉设计，精通色彩设计，图形设计，信息设计，排版设计的原则和方法；</li>
            <li>4、设计决策，为项目的特性设计提供合理的系统化解决方案； </li>
            <li>5、善于沟通，能直白的讲述视觉设计的价值，心态平和富于耐心，有不倦的传道精神；</li>
            <li>6、艺术类设计或计算机相关专业，国家统招专科以上学历；</li>
            <li>7、相关行业、职位，工作3年以上。 </li>
          </ul>
          <p></p>
          <p><strong>三、资深交互设计师</strong></p>
          <p>岗位职责：</p>
          <ul class="marginT0 marginB10">
            <li>1、研究用户使用习惯，对现有产品的可用性/易用性进行改进，优化产品的用户体验；</li>
            <li>2、负责B2C电子商务平台的交互设计工作和流程规划工作； </li>
            <li>3、根据产品规划，研究、理解用户需求，以用户体验为核心进行各类产品交互设计，输出高保真原型、设计说明书等； </li>
            <li>4、分析业务需求，并加以分解，归纳产品人机交互界面需求； </li>
            <li>5、设计网站及手机产品人机交互界面结构、用户操作流程等； </li>
            <li>6、跟踪产品开发流程并推动落实，制定并输出相关设计规范。</li>
          </ul>
          <p>岗位要求： </p>
          <ul class="marginT0 marginB10">
            <li>1、人机交互、软件工程、工业设计、视觉传达或相关设计专业毕业； </li>
            <li>2、具备3年以上网站、软件或手持设备交互设计工作经验，有B2C网站经验者优先； </li>
            <li>3、对交互设计过程有深入的了解，可以独立完成整个交互设计过程（对流程图、线框图等等交互设计方法能熟练应用）；</li>
            <li>4、具备良好的软件用户界面交互设计和分析能力，有丰富网页设计、软件界面设计的工作经验, 有成熟实践案例； </li>
            <li>5、有图形表现、HTML、CSS技能及Flash制作经验者优先考虑；</li>
            <li>6、沟通能力优秀，可完整清晰阐述设计理念； </li>
            <li>7、有很强的责任心和职业精神，做事认真负责，积极进取，善于沟通。 </li>
          </ul>
          <p></p>
          <p><strong>四、前端开发工程师</strong></p>
          <p>岗位职责：</p>
          <ul class="marginT0 marginB10">
            <li>1、精通JavaScript、AJAX、HTML、CSS等WEB前端技术以及浏览器兼容性有丰富的实战经验，３年以上的开发经验； </li>
            <li>2、对优化页面加载速度，用户可用性、易用性、用户体验等相关知识有深入了解； </li>
            <li>3、有调试技巧并能够独立解决问题的能力，熟悉YSlow、PageSpeed等调试工具；</li>
            <li>4、对互联网产品有独到的理解，了解常用PHP框架模版语法; </li>
            <li>5、热爱前端开发，同时具备良好的团队合作精神。 </li>
          </ul>
          <p><strong>面试携带个人成熟作品。 </strong></p>
          <p></p>
          <p><strong>五、CRM专员/主管</strong></p>
          <p>岗位职责：</p>
          <ul class="marginT0 marginB10">
            <li>1、负责公司的会员管理工作，建立网站的会员体系，并推动实施和不断优化； </li>
            <li>2、策划网站会员活动，对会员进行培养、激活，提高用户的忠诚度和满意度；</li>
            <li>3、建立会员数据库，进行会员分析和分层，实现精准化营销； </li>
            <li>4、不断推动CRM系统的完善及优化，与供应商保持良好沟通和协作关系。</li>
          </ul>
          <p>岗位要求： </p>
          <ul class="marginT0 marginB10">
            <li>1、市场营销、管理学等本科及以上学历，2年以上会员管理和营销相关工作经验； </li>
            <li>2、熟悉主流的CRM系统、短信系统和邮件系统，熟练运用EXCEL、PPT等office办公软件； </li>
            <li>3、对母婴行业感兴趣，擅长会员活动策划，具有优秀的执行力和控制力，应变能力强；</li>
            <li>4、具备良好的内外部沟通能力和较强的抗压能力，有团队管理和项目管理经验者优先。 </li>
          </ul>
          <p></p>
          <p><strong>福利待遇</strong></p>
          <ul class="marginT0 marginB10">
            <li>1、五险一金：保障养老、医疗、生育、工伤、住房 </li>
            <li>2、享受带薪年假，度假释放心灵 </li>
            <li>3、法定节日的节日福利，给您带来惊喜 </li>
            <li>4、一年一度的旅游福利 </li>
          </ul>
        </div>
				<!-- 加入我们 结束  -->
				<!-- 联系我们 开始  -->
				<div id="5_3" class="help_box">
					<h3><i class="fa fa-diamond"></i>联系我们</h3>
					<p>客户服务热线：&nbsp;&nbsp;4006-282-528&nbsp;&nbsp;（工作时间：每天 9:00 至 17:00）</p>
					<p>在线客服QQ：
						<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2815764341&amp;site=qq&amp;menu=yes">2815764341</a>&nbsp;&nbsp;
						<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2326234406&amp;site=qq&amp;menu=yes">2326234406</a>&nbsp;&nbsp;
						（在线时间：每天 9:00 至 22:00）
					</p>
					<p>客户服务邮箱：&nbsp;&nbsp;<a href="mailto:cs@fushionbaby.com">cs@fushionbaby.com</a>&nbsp;&nbsp;（收到邮件后24小时内回复）</p>
				</div>
				<!-- 联系我们 结束  -->
				<!-- 招商合作 开始  -->
				<div id="5_4" class="help_box">
					<h3><i class="fa fa-diamond"></i>招商合作</h3>
					<p><strong>商务合作中心</strong></p>
					<p>负责Fushionbaby的品牌合作和市场拓展工作。</p>
					<p>总裁信箱：&nbsp;&nbsp;<a href="mailto:victoria.chan@fushionbaby.com">victoria.chan@fushionbaby.com</a></p>
					<p>市场部门商务联络邮箱：&nbsp;&nbsp;<a href="mailto:bd@fushionbaby.com">bd@fushionbaby.com</a></p>
					<p><strong>Fushionbaby招商</strong></p>
					<p>我们在全球范围内征询优质供应商</p>
					<ul class="marginT0 marginB10">
						<li>1、招商范围：童装、童鞋、婴儿用品、洗护用品、纺织品、玩具早教、车床座椅、妈妈用品及纸尿裤商家。 </li>
						<li>2、招商对象：母婴相关的国内外品牌厂家、品牌代理商、商标持有者等。 </li>
          </ul>
					<p>欢迎携手Fushionbaby，共创辉煌</p>
				</div>
				<!-- 招商合作 结束  -->
				<!-- Fushionbaby承诺 开始  -->
				<div id="5_5" class="help_box">
					<h3><i class="fa fa-diamond"></i>Fushionbaby承诺</h3>
					<p><strong>进入发货流程前，无条件退款</strong></p>
					<ul class="inside marginT0 marginB10">
						<li>您下单后，一般下一个工作日上午进入发货流程； </li>
						<li>在进入发货流程前，如果您改变了意图，不再需要您购买的产品，均可以自行取消订单；</li>
						<li>进入发货流程后，为避免流程混乱，不再允许直接退款，订单将会安排及时发货；</li>
						<li>Fushionbaby 承诺：您的订单会在付款后的1-2个工作日内发货，一般发货后3-5天到货，偏远地区请耐心等待。</li>
					</ul>
					<p><strong>有问题请拒收</strong></p>
					<ul class="inside marginT0 marginB10">
						<li>Fushionbaby 的商品支持并鼓励先验货再签收，如果有破损或其他问题，可以拒绝签收；</li>
						<li>如果发现商品数量、重量不足，在确认收货前先拍下照片登录辣妈汇填写退款申请，可以申请补发或赔偿，确认问题后再签收；</li>
						<li>验货时有其他疑问请先咨询客服人员。</li>
					</ul>
					<p><strong>7天无理由退货</strong></p>
					<ul class="inside marginT0 marginB10">
						<li>Fushionbaby 所有产品均提供“7天无理由退货”服务（不影响二次销售，需保留吊牌、价格标签等），时间从您的订单签收之日开始计算；</li>
						<li>如果是质量问题、或商家的问题（如错发、漏发），商家承担来回运费； </li>
						<li>如果非质量问题，顾客承担将商品寄回商家的运费。</li>
					</ul>
					<p><strong>正品保证</strong></p>
					<ul class="inside marginT0 marginB10">
						<li>Fushionbaby 坚持以客户为中心，所有项目均检查过样品后才会安排上线，我们保证所出售的产品全部为正品，支持验货。</li>
						<li>如有问题请及时联系客服；</li>
						<li>客服联系方式（周一到周日 9:00 - 20:00，节假日或特殊情况以公告为准）；</li>
						<li>客服电话：4006-282-528 </li>
					</ul>
				</div>
				<!-- Fushionbaby承诺 结束  -->
			</div>
		</div>
		<#include "/common/footer.ftl" />
		<#include "/common/nav.ftl" />
	</body>
</html>