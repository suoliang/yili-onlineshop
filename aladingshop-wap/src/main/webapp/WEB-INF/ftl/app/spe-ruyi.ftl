<!DOCTYPE html>
<html>
    <head>
        <title>如意消费卡</title>
        <!-- 通用部分 开始 -->
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1.0,user-scalable=no">
        <link rel="shortcut icon" href="${rc.contextPath}/static/shop/images/favicon.ico" />
        <link rel="stylesheet" href="${rc.contextPath}/static/shop/css/style.min.css"><!-- 通用 CSS -->
        <!-- 通用部分 结束 -->
        <style type="text/css">
        	.container{text-align:center;}
        	p{font-size:2rem;color:#666;line-height:2.8rem;}
        	h2{font-size:7rem;color:#ff5400;padding-top:2rem;}
        	h3{color:#fff;font-size:2rem;}
        	.income,.services{border-top:1px solid #dbdbdb;padding:15px 0;margin:15px auto;width:90%;}
        	.income .purple{color:#8a00ff;margin-top:1rem;}
        	.income ul{overflow:hidden;width:90%;margin:15px auto 0;}
        	.income li{float:left;width:33.33%;}
        	.income li:nth-child(1) img{margin-top:97px;}
        	.income li:nth-child(2) img{margin-top:55px;}
        	.services ul{overflow:hidden;width:40rem;margin:0 auto;}
        	.services li{float:left;overflow:hidden;height:18rem;width:18rem;border-radius:10px;margin:1rem;padding:1.3rem;line-height:2.5rem;}
            .services p{text-align:left;font-size:1.7rem;color:#333;margin-top:1rem;}
        	.services li:nth-child(1){background:#69c8ff;}
        	.services li:nth-child(2){background:#ffda69;}
        	.services li:nth-child(3){background:#ec69ff;}
        	.services li:nth-child(4){background:#ff8569;}
        	.services .servicesCon{text-indent:2rem;font-size:1.7rem;color:#999;margin-bottom:5rem;padding:1rem;}
        </style>
    </head>
    <body id="">

        <div class="container">

            <!--<div class="head mB10">
                <a href="javascript:history.go(-1)" class="a-left">
                    <span class="back"></span>
                </a>
                <p>如意消费卡</p>
            </div>-->
            <div>
				<h2>7.8%~8%</h2>
				<p>年化赠券率</p>
			</div>
			<div class="income">
				<p>10000元，预计一年所得</p>
				<ul>
					<li>
						<img src="${rc.contextPath}/static/shop/images/alading-ruyi/income1.png"/>
						<p class="purple">35元</p>
						<p>银行</p>
					</li>
					<li>
						<img src="${rc.contextPath}/static/shop/images/alading-ruyi/income2.png"/>
						<p class="purple">360元</p>
						<p>某理财平台</p>
					</li>
					<li>
						<img src="${rc.contextPath}/static/shop/images/alading-ruyi/income3.png"/>
						<p class="purple">800元</p>
						<p>如意消费卡</p>
					</li>
				</ul>
			</div>
			<div class="services">
				<ul>
					<li>
						<h3 class="mT10">9折购物</h3>
						<p>支持全站商品购买支付，开通终身享9折优惠。</p>
					</li>
					<li>
						<h3 class="mT10">高额回报</h3>
						<p>100起存，可达银行活期存款利率约17倍。</p>
					</li>
					<li>
						<h3 class="mT10">方便灵活</h3>
						<p>随取随用，存取自由资金不套牢。</p>
					</li>
					<li>
						<h3 class="mT10">安全无忧</h3>
						<p>实名制验证，资金进出审核严苛，最大程度保障资金安全。</p>
					</li>
				</ul>
				<p class="servicesCon">
					此预付消费卡全国统一备案号为310115AAA0032本公司与中国建设银行股份有限公司签订了《单用途商业预付卡预收资金存管协议》，根据国家相关法律法规的规定“公司将不少于预收资金额的30%存入中国建设银行账户中”，银行对本公司资金进行存管。
				</p>
			</div>
        </div><!-- /.container -->
        <script>
	        // 获取html标签
	        var Ohtml = document.documentElement;
	        // 页面加载完执行一次
	        getSize();
	            function getSize(){
	                // 获取屏幕的宽度
	                    var screenWidth = Ohtml.clientWidth;
	                /* 当屏幕宽度小于等于320的情况下  我们就让font-size固定在4px */
	                if(screenWidth <= 320){
	                        Ohtml.style.fontSize = '7px';
	                // 当屏幕宽度大于等于640的情况下  我们就让font-size固定在14px
	                    }else if(screenWidth >= 640){
	                        Ohtml.style.fontSize = '14px';
	                /* 当屏幕宽度在我们区间之间  我们就让font-size对应这个屏幕的font-size */
	                }else{
	                        Ohtml.style.fontSize = screenWidth/(640/14) +'px';
	                }
	            }
	            window.onresize = function(){
	                    getSize();
	            }
    	</script>

    </body>
</html>
