<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Fushionbaby 抽奖</title>
		<meta name="keywords" content="Fushionbaby,兜世宝,像在国外一样任性_100%正品保证_中高端母婴选购平台_海外进口,口碑甄选,独家代理"/>
		<meta name="description" content="Fushionbaby母婴用品_一站式海外进口母婴专业潮流用品_100%正品保证_高端母婴选购平台_海外精品,口碑甄选,独家代理,严格把关,aden+anais,Babyhome,Baby-Monsters,Bloom,Britax,Concord,Joie,Life factory,Nuna,Sassy,Spooner,Zoku,Zoli,妮飘,三洋,湾蓝,Kaneson,Uppababy,Avent"/>
		<link rel="shortcut icon" href="${rc.contextPath}/views/images/favicon.ico?v=${EnvironmentConstant.DEPLOY_VERSION}>" /><!--浏览器标签图标-->
		<link rel="stylesheet" href="${rc.contextPath}/views/css/public.css?v=${EnvironmentConstant.DEPLOY_VERSION}" /><!--base.css-->
		<script type="text/javascript" src="${rc.contextPath}/views/js/jquery-1.11.1.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script><!--jquery.js-->
		<script type="text/javascript" src="${rc.contextPath}/views/js/base.js?v=${EnvironmentConstant.DEPLOY_VERSION}" ></script><!--base.js-->
		
		<script type="text/javascript" src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/js/jQueryRotate.2.2.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<script type="text/javascript" src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/js/jquery.easing.min.js?v=${EnvironmentConstant.DEPLOY_VERSION}"></script>
		<link rel="stylesheet" href="${rc.contextPath}/views/css/style.css?v=${EnvironmentConstant.DEPLOY_VERSION}" /><!--base.css-->
		
		<script type="text/javascript" language="javascript">
			var _ContextPath = "${rc.contextPath}";
			
			function runLottery(){
				var url = _ContextPath + "/lottery/run.do";
				$.post(url,{time : new Date().getTime()},
					function(data){
						if(data.responseCode==0){
							var resultData=data.data;
							var img_path = resultData.imgPath;
							if(img_path != null && img_path != undefined && $.trim(img_path).length>0){
								img_path = "${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/" + img_path;
								$('.result-img img').attr({'src':img_path});
								open_result();//点击抽奖延迟一会打开结果弹窗
							}
						}else{
							if(data.responseCode==201){//未登录
								$('#not_login').fadeIn(100);//弹出登录框
							}else if(data.responseCode==211){//您还没有购物满200元,不能参与抽奖
								error_001();
							}else if(data.responseCode==212){//抽奖次数已经用完
								gameover();
							}else{
								alert(data.msg);
							}
						}
					}
				);//end post request
			}
		</script>
		
		<style type="text/css">
			.img-wrap { position: relative;float:left; }
			#ZP {position: absolute;right: 84px;top:101px;background: url(images/background.png) no-repeat 0 0;width:546px;height: 543px; }
			.ZP-second {width: 450px;height: 450px;top: 43px;left: 56px;position: relative;}
			#ZP .light {position: absolute;right: 12px;top:14px;background: url(images/light.gif) no-repeat 0 0;width:508px;height: 509px; }
			@-webkit-keyframes rotate{from{-webkit-transform: rotate(0deg)}
			to{-webkit-transform: rotate(360deg)}
			}
			@-moz-keyframes rotate{from{-moz-transform: rotate(0deg)}
			to{-moz-transform: rotate(359deg)}
			}
			@-o-keyframes rotate{from{-o-transform: rotate(0deg)}
			to{-o-transform: rotate(359deg)}
			}
			@keyframes rotate{from{transform: rotate(0deg)}
			to{transform: rotate(359deg)}
			}
			#ZP .ZP-second-img {-webkit-animation: rotate 40s linear infinite;-moz-animation: rotate 40s linear infinite;-o-animation: rotate 40s linear infinite;animation: rotate 40s linear infinite;}
			.ZP-start {width:176px;height:239px;position: absolute;left: 50%;top: 50%;margin-left: -88px;margin-top: -120px;cursor: pointer;}
			.ZP-start img {position: absolute;left: 0;}
			#reward {position: absolute;top:175px;left:25px;}
			#reward ul {width: 100%;}
			#reward ul li {float: left;margin:0 10px;}
			#reward ul li a {float: left;width: 198px;height: 336px;position: relative;}
			#reward ul li a img {position: absolute;top: 0;left: 0;-webkit-transition:all 0.25s ease-out 0s;transition:all 0.25s ease-out 0s;}
			#result {position: fixed;top: 0;left: 0;background-color: rgba(0,0,0,0.5);z-index: 998;width: 100%;height: 100%;display: none;filter: progid:DXImageTransform.Microsoft.gradient(startcolorstr=#7F000000,endcolorstr=#7F000000);}
			#result .result-img {position: absolute;left: 50%;margin-left:-503px;top: 0;width: 1006px;height: auto;z-index: 999;}
			.result-img .result-close {position: absolute;right: 0;top:0;width: 55px;height: 55px;cursor: pointer;background: url(../images/result-close.jpg) no-repeat;}
			.result-img a { bottom: 22px; display: inline-block; height: 35px; left: 400px; position: absolute; width: 225px; }
			.pass-check, .pass-checked { background-color: #fff; border: 1px solid #d9d9d9; display: none; height: 174px; left: 50%; line-height: 174px; margin-left: -212px; margin-top: -87px; position: fixed; text-align: center; top: 50%; width: 424px; z-index: 9999; }
			.close { background: url("http://www.fushionbaby.com/views/images/sprite-myself.png") no-repeat scroll -1px -274px transparent; cursor: pointer; float: right; height: 23px; margin: 4px 6px; width: 22px; }
			.desc-info { color: #373737 !important; font-size: 20px !important; text-align: center !important; margin: 0 !important; }
			.desc-info a { color: #6ae0ff; }
		</style>
	</head>
	<body>
		<!--[if IE 6]>
		<script src="js/iepng.js" type="text/javascript"></script>
		<script type="text/javascript">
		EvPNG.fix('div,ul,img,li,input,span,b,h1,h2,h3,h4,a');
		</script>
		<![endif]-->
		<#include "/common/topbar.ftl" />
		<#include "/common/header.ftl" />
		<!-- ///////////////////////  抽奖  开始  //////////////////////////// -->
		<div class="container">
			<div class="img-wrap fl">
				<img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/back_1.jpg">
				<div id="ZP">
					<div class="light"></div>
					<div class="ZP-second">
						<img class="ZP-second-img" src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/ZP-second.png">
						<div class="ZP-start"><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/start.png"></div>
					</div>
				</div>
			</div>
			<div id="result">
				<div class="result-img">
					<img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/error.jpg" alt="">
					<div class="result-close"></div>
				</div>
			</div>
			<div class="img-wrap fl">
				<img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/back_2.jpg">
				<div id="reward">
					<ul>
						<li><a href=""><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/reward_1.jpg"></a></li>
						<li><a href=""><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/reward_2.jpg"></a></li>
						<li><a href=""><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/reward_3.jpg"></a></li>
						<li><a href=""><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/reward_4.jpg"></a></li>
						<li><a href=""><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/reward_5.jpg"></a></li>
					</ul>
				</div>
			</div>
			<div class="img-wrap fl"><img src="${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/back_3.jpg"></div>
		</div>
		<div id="not_login" class="pass-check">
			<div class="close"></div>
			<p class="desc-info">您好，请 <a href="http://www.fushionbaby.com/login/index.do" target="_self">登录</a></p>
		</div>
		<script type="text/javascript">
			$(function(){
				// 奖品图片鼠标悬停效果
				$('#reward img').mouseover(function(){
					$(this).animate({'top':'-15px'},100);
				});
				$('#reward img').mouseout(function(){
					$(this).animate({'top':'0px'},100);
				});
	
				// 点击抽奖按钮抽奖
				$(".ZP-start img").rotate({
					bind:{
						click:function(){
							//disabledMouseWheel();//禁用鼠标滚轮
							runLottery();
							var a = Math.floor(Math.random() * 360);//转动角度
							 $(this).rotate({
								 	duration:3000,
								 	angle: 0,
			            			animateTo:1440+a,
									easing: $.easing.easeOutSine,
									callback: function(){
										//console.log(a);
									}
							 });
						}
					}
				});
	
				$('.result-close').click(function(){
					// 关闭提示弹窗
					$('#result').fadeOut(200);
					//location.reload();
				})
				
				$('#not_login').click(function(){
					// 关闭登录弹窗
					$('#not_login').fadeOut(200);
					//location.reload();
				})
	
				var ZP_top = $('#ZP').offset().top;//获取转盘顶部值
				$('.result-img').css({'top':ZP_top});
			});
			
			//未购满200,方法名这样写是为了防止其他js文件和这个冲突error_001
			function error_001(){
				//替换错误图片
				$('.result-img img').attr({'src':'${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/error.jpg'});
				$('.result-img').find('a').remove();
				$('.result-img').append('<a href="http://www.fushionbaby.com"></a>');
				open_result();// 点击抽奖延迟一会打开结果弹窗
			}
			
			//抽奖次数用完
			function gameover(){
				//替换gamerover图片
				$('.result-img img').attr({'src':'${rc.contextPath}/views/fushionbaby_zhi/will/activity/choujiang/images/gameover.jpg'});
				open_result();// 点击抽奖延迟一会打开结果弹窗
			}

			function open_result(){
				// 点击抽奖延迟一会打开结果弹窗
				setTimeout(function(){
					$('#result').fadeIn(100);
				},1000);
			}
			
			// 禁止鼠标滚轮
			function disabledMouseWheel() {
			  if (document.addEventListener) {
			    document.addEventListener('DOMMouseScroll', scrollFunc, false);
			  }//W3C
			  window.onmousewheel = document.onmousewheel = scrollFunc;//IE/Opera/Chrome
			}
			
			function scrollFunc(evt) {
				evt = evt || window.event;
				if(evt.preventDefault) {
				    // Firefox
				    evt.preventDefault();
				    evt.stopPropagation();
			    } else {
			      // IE
			      evt.cancelBubble=true;
			      evt.returnValue = false;
			  	}
				  return false;
			}
			</script>
	
			<script type="text/javascript">
				// 除<input>与<textarea>外，禁用鼠标右键菜单
				$(document).ready(function(){
				function jQuery_isTagName(ev,arr){
				ev=$.event.fix(ev);
				var target=ev.target||ev.srcElement;
				if(arr&&$.inArray(target.tagName.toString().toUpperCase(),arr)==-1){
				return false;
				}
				return true;
				}
		
				$(document).bind("contextmenu",function(ev){
				if(!jQuery_isTagName(ev,['INPUT','TEXTAREA'])){
				ev.preventDefault();
				return false;
				}
				return true;
				})
				})
			</script>

		    <script type="text/javascript">
		    	// 禁用键盘F12
		        $(document).ready(function () {   //防止在DOM元素加载完成就执行jQuery代码，从而避免产生不必要的错误
		            $("*").keydown(function (e) {//判断按键
		                e = window.event || e || e.which;
		                if (e.keyCode == 123) {
		                    e.keyCode = 0;
		                    return false;
		                }
		            });
		            //document.onhelp = function () { return false };//
		            window.onhelp = function () { return false };//ie下面不能屏蔽f1键的补充方法,和上面的一行的效果是一样的，选其一
		        });
		    </script>
		<!-- ///////////////////////  抽奖  结束  //////////////////////////// -->
		<#include "/common/footer.ftl" />
		<#include "/common/nav.ftl" />
	</body>
</html>