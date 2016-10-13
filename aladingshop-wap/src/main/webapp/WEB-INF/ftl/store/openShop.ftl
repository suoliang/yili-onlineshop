<!DOCTYPE html>
<html lang="en">
    <head>
        <title>阿拉丁玛特-申请开店</title>
        <meta name="description" content="阿拉丁玛特商城">
        <meta name="keywords" content="阿拉丁玛特商城">

        <!-- 通用部分 开始 -->
        <#include "/common/common.ftl" />
        <!-- 通用部分 结束 -->
        <link rel="stylesheet" type="text/css" href="${rc.contextPath}/static/shop/css/dialog.css"/><!-- 选择地址 CSS -->
		<script type="text/javascript" src="${rc.contextPath}/static/shop/js/dialog.js"></script><!-- 选择地址 js -->
		<script type="text/javascript" src="${rc.contextPath}/static/shop/js/mobile-select-area.js"></script><!-- 选择地址 js -->
        <script type="text/javascript" language="javascript">
		    var _ContextPath = "${rc.contextPath}";
	    </script>
        <style type="text/css">
        	body,html{width:100%;}
        	.head{background:rgba(0,0,0,0);margin-top:-44px;z-index:10;}
            #openShop{background:#f6f6f6;font-size:16px;}
            #openShop .form-group{margin-top:15px;}
            #openShop label{width:12%;line-height:42px;margin:0 0 0 9%;}
            #openShop input{width:70%;padding-left:10px;border: 1px solid #ff8041;border-radius:5px;}
            #openShop .input-group{margin:10px 0;}
            img{position: relative;left:50%;transform:translateX(-50%);-webkit-transform:translateX(-50%);-moz-transform:translateX(-50%);-ms-transform:translateX(-50%);}
            .btn-wrap button{margin:0 0 20px;}
        </style>
    </head>
    <body id="openShop">
        <div class="head">
            <!--<a href="javascript:history.go(-1)" class="a-left">
                <span class="back"></span>
            </a>
            <p>申请开店</p> -->
            <a href="javascript:void(0)" class="a-right" onclick="openShareModal()">
                <span class="share"></span>
            </a>
        </div>
        <img src="${rc.contextPath}/static/shop/images/openShop.png" >
        <form class="form-group" id="openShopDressForm">
            <div class="input-group">
                <label for="name">姓名</label>
                <input type="text" id="name" name="name" placeholder="请输入您的姓名">
            </div>
            <div class="input-group">
                <label for="name">电话</label>
                <input type="tel" id="phone" name="phone"  placeholder="请输入您的电话">
            </div>
            <div class="input-group" id="area">
                <label for="name">省市</label>
                <input type="text" name="area" id="txt_area2" placeholder="请选择您所在的省市区" >
            </div>
            <div class="input-group">
                <label class="control-label">地址</label>
                <div class="col-sm-10">
                    <input type="text" name="areaDetail" id="areaDetail" placeholder="请输入您的详细地址">
                </div>
            </div>
            <div class="btn-wrap">
                <button id="loginBtn" type="button" onclick="commitOpenShop();">确认提交</button>
            </div>
        </form>
        <!--分享地址-->
        <div class="modal-wrap" id="shareModal">
            <div class="share-wrap">
                <div class="bdsharebuttonbox">
                    <div class="share-box"><a title="分享到QQ空间" href="#" class="bds_qzone" data-cmd="qzone"></a><p>QQ空间</p></div>
                    <div class="share-box"><a title="分享到新浪微博" href="#" class="bds_tsina" data-cmd="tsina"></a><p>新浪微博</p></div>
                    <div class="share-box"><a title="分享到腾讯微博" href="#" class="bds_tqq" data-cmd="tqq"></a><p>腾讯微博</p></div>
                </div>
                <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":[],"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
            </div>
        </div>
        <script>
			$.getJSON("${rc.contextPath}/static/shop/data/area.json",function(data){
				var json = data;
				var selectArea2 = new MobileSelectArea();
				selectArea2.init({trigger:'#txt_area2',value:$('#txt_area2').data('value'),data:json,eventName:'click'});
			});

			function commitOpenShop(){
				var name = $.trim($("#name").val());
				var phone = $.trim($("#phone").val());
				var txt_area2 = $.trim($('#txt_area2').attr("data-value"));
				var areaDetail = $.trim($("#areaDetail").val());

				if(name==""){
					alert("请输入您的姓名");
					return;
				}
				if(phone.length == 0 || !/^1\d{10}$/.test(phone)){
					alert("手机号不正确");
					return;
				}
				if(txt_area2==""){
					alert("请选择省市区");
					return;
				}
				if(areaDetail==""){
					alert("请输入详细地址");
					return;
				}
				$.ajax({
					type:"post",
					url:_ContextPath+"/store/commitOpenShop",
					data:{name:name,phone:phone,area:txt_area2,areaDetail:areaDetail,time:new Date().getTime()},
					dataType:"json",
					success:function(data){
						if (data.responseCode=="0") {
							alert("申请成功,请耐心等待。。。");
							window.location.href = _ContextPath+"/store/toOpenShop";
						} else {
							alert("申请失败,请重试");
							window.location.href = _ContextPath+"/store/toOpenShop";
						}
					}

				});

			}

		</script>


    </body>
</html>
