		$(document).ready(
			function() {
				var nowimg = 0; //当前的图片编号

				//克隆
				$("#chuantong .tuul li").eq(0).clone().appendTo("#chuantong .tuul");

				// ******定时器********
				var timer = setInterval(youanniuyewu, 5000);

				$("#chuantong").mouseenter(
					function() {
						clearInterval(timer);
						$('.anniu .leftbut').animate({
							"left": "0",
							"opacity": "0.5"
						}, 200);
						$('.anniu .rightbut').animate({
							"right": "0",
							"opacity": "0.5"
						}, 200);
					}
				);

				$("#chuantong").mouseleave(
					function() {
						clearInterval(timer);
						timer = setInterval(youanniuyewu, 5000);
						$('.anniu .leftbut').animate({
							"left": "-45px",
							"opacity": "0"
						}, 200);
						$('.anniu .rightbut').animate({
							"right": "-45px",
							"opacity": "0"
						}, 200);
					}
				);
				//******定时器*******

				//右按钮的监听：
				$("#chuantong .anniu .rightbut").click(youanniuyewu);

				function youanniuyewu() {
					if (!$(".tuul").is(":animated")) {
						if (nowimg < $("#chuantong .tuul li").length - 2) {
							nowimg = nowimg + 1;
							huan(); //还没有到最后一张的时候，我们进行换图函数。
						} else {
							nowimg = 0;
							//我们要让ul先往假狮子上拉动，然后瞬间移动到0
							//由于真假狮子长得一样，所以看不出来。
							$("#chuantong .tuul").animate({
								"left": -882 * ($("#chuantong .tuul li").length - 1)
							}, 500, function() {
								$("#chuantong .tuul").css("left", 0);
							});
							//小圆点跟上
							$("#chuantong .dianul li").eq(nowimg).addClass("cur").siblings().removeClass("cur");
						}
					}
					/*播放flash*/
					/*console.log(nowimg);*/
					if (nowimg == 0) {
						play();
					}
				}
				//左按钮的监听：
				$("#chuantong .anniu .leftbut").click(
					function() {
						if (!$(".tuul").is(":animated")) {
							if (nowimg > 0) {
								nowimg = nowimg - 1;
								huan(); //还没有到第0张的时候，执行换图函数
							} else {
								nowimg = $("#chuantong .tuul li").length - 2;
								//先让真狮子瞬间替换为假狮子，然后拉动
								$("#chuantong .tuul").css("left", -882 * ($("#chuantong .tuul li").length - 1));
								$("#chuantong .tuul").animate({
									"left": -882 * ($("#chuantong .tuul li").length - 2)
								}, 500);
								//小圆点跟上
								$("#chuantong .dianul li").eq(nowimg).addClass("cur").siblings().removeClass("cur");
							}
						}
						/*播放flash*/
						/*console.log(nowimg);*/
						if (nowimg == 0) {
							play();
						}
					}
				);

				//小圆点监听
				$("#chuantong .dianul li").click(
					function() {
						//得到用户点击的小圆点的序号
						var dian = $(this).index();
						$("#chuantong .dianul li").eq(dian).addClass("cur").siblings().removeClass("cur");

						if (dian < nowimg) {
							console.log("主人~您点的小于信号量");
							//先备份上一张图片的src值！
							var beifen = $("#chuantong .tuul li img").eq(nowimg - 1).attr("src");
							//改变上一张图片的src值，为用户点击的那个图片的src
							//得到用户点击的那个小圆点对应的图图的src值
							var diansrc = $("#chuantong .tuul li img").eq(dian).attr("src");
							//我们再设置上一张图片的src为这张图片，准备进行下一步魔术
							$("#chuantong .tuul li img").eq(nowimg - 1).attr("src", diansrc);

							//此时，我们的上一张图片，已经变成了用户点击的图片了。
							//此时，打动一个身位
							$("#chuantong .tuul").animate({
								"left": -882 * (nowimg - 1)
							}, 500, function() {
								//拉动完毕一个身位之后，我们要将真的图片替换这张假图
								$("#chuantong .tuul").css("left", -882 * dian);
								//应该让上一张图片的src，恢复为备份的内容
								$("#chuantong .tuul li img").eq(nowimg - 1).attr("src", beifen);

								//最终的最终，我们改变信号量为用户点击的图片
								nowimg = dian;

							});
						} else {
							console.log("主人~您点的大于信号量");
							//备份下一张图片的src
							var beifen = $("#chuantong .tuul li img").eq(nowimg + 1).attr("src");
							//先得到用户点击的拿张图片的src
							var diansrc = $("#chuantong .tuul li img").eq(dian).attr("src");
							//让一下图片的src，变为diansrc
							$("#chuantong .tuul li img").eq(nowimg + 1).attr("src", diansrc);
							//此时猫腻已经就位，我们将它左移动一个身位
							$("#chuantong .tuul").animate({
								"left": -882 * (nowimg + 1)
							}, 500, function() {
								//拉动一个身位之后，我们让真的图片瞬间替换假的图片
								$("#chuantong .tuul").css("left", -882 * dian);

								//然后将原有的图片，回复为备份
								$("#chuantong .tuul li img").eq(nowimg + 1).attr("src", beifen);

								//最后的最后，我们要将信号量变为用户点击的那个
								nowimg = dian;
							});
						}
					}
				);

				function huan() {
					//让绝对定位的ul进行运动，运动的终点是nowimg * -560
					$("#chuantong .tuul").animate({
						"left": nowimg * -882
					}, 500);

					$("#chuantong .dianul li").eq(nowimg).addClass("cur").siblings().removeClass("cur");
				}
			}
		);