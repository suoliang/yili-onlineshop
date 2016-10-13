$(document).ready(function() {
	//产品轮播图
		var key=0;
        $("#right").click(function(event) {//单击箭头向右走
            
            autoplay2();//调用函数
			$(this).addClass('naw-current').siblings().removeClass('naw-current');
        });

        $("#left").click(function(event) {//单击箭头向左走
              	key--;
              	if(key<0)
              	{
                	key=2;
                	$("#newpro-wrapper").css('left', "-3420px");//瞬间跳到最后一张图的顶点位置   
               	}
                $("#newpro-wrapper").stop().animate({"left":-key*1140}, 1000);//点击一次向左走一张图的距离
				$(this).addClass('naw-current').siblings().removeClass('naw-current');
        });

        var timer2=setInterval(autoplay2, 4000);//添加一个定时器
        function autoplay2()
        {
            key++;
            if(key>3)
            {
                key=1;
                $("#newpro-wrapper").css('left', 0);   
            }
            $("#newpro-wrapper").stop().animate({"left":-key*1140}, 1000);
        }

           
        $(".newpro-bd-wrapper").hover(function() {
            clearInterval(timer2);//清除定时器
        }, function() {
            clearInterval(timer2);
            timer2=setInterval(autoplay2, 4000);//启动定时器
        });
        $(".nbw-arrow-wrapper").hover(function() {
            clearInterval(timer2);//清除定时器
        }, function() {
            clearInterval(timer2);
            timer2=setInterval(autoplay2, 4000);//启动定时器
        });


});