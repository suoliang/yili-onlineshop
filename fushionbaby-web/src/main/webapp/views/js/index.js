$(document).ready(function() {
	//banner轮播图
	$("#banner ol li").mouseover(function(event) {
		var $index=$(this).index(); // 首先获取索引值
		$(this).addClass('circle-current').siblings().removeClass();//当前自己添加新类，兄弟移除
		$("#banner ul").stop().animate({left:-$index*1140},1000);
		$key=$index;// 我们得出$key和鼠标经过的那个ol的li存在加1的关系
			});

		/*开始添加定时器*/
		var $key=0; //这个变量来控制往下播放的关键
		var timer=setInterval(autoplay, 3000);
		function autoplay(){
		$key++; //先自加
		if($key>3) //如果在第5张上，索引值是4，需要回头
		{
			$key=0;// 归零
		}

		$("#banner ol li").eq($key).addClass('circle-current').siblings().removeClass();
		$("#banner ul").stop().animate({left:-$key*1140},1000);

		}
		$("#banner").hover(function() {
			clearInterval(timer);
		}, function() {
			clearInterval(timer);
			timer=setInterval(autoplay, 3000);
		});
	//产品轮播图
	function right(){
		
		$("#newpro-wrapper").stop().animate({"left":"-1140px"}, 500);
	}
	function left(){
		$("#newpro-wrapper").stop().animate({"left":"0"}, 500);
	}
	$("#right").click(function(event) {
		right();
		$(this).addClass('naw-current').siblings().removeClass('naw-current');
	});
	$("#left").click(function(event) {
		left();
		$(this).addClass('naw-current').siblings().removeClass('naw-current');
	});
	/*开始添加定时器*/
	var $key1=0; //这个变量来控制往下播放的关键
	var timer1=setInterval(autoplay1, 3000);
	function autoplay1(){
	$key1++; //先自加
	if($key1>1) //如果在第5张上，索引值是4，需要回头
	{
		$key1=0;// 归零
	}

	$(".nbw-arrow-wrapper div").eq($key1).addClass('naw-current').siblings().removeClass("naw-current");
	$("#newpro-wrapper").stop().animate({left:-$key1*1140},1000);

	}
	$(".newpro-bd-wrapper").hover(function() {
		clearInterval(timer1);
	}, function() {
		clearInterval(timer1);
		timer1=setInterval(autoplay1, 3000);
	});
















});