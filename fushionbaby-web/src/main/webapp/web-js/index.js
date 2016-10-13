//*********************** 首页banner下方轮播 开始 ******************//
	// .endtime 的value值是经过转换的unix时间
	setInterval(function() {
		$(".endtime").each(function() {
			var obj = $(this);
			var endTime = new Date(parseInt(obj.attr('value')));
			var nowTime = new Date();
			var nMS = endTime.getTime() - nowTime.getTime();
			var myD = Math.floor(nMS / (1000 * 60 * 60 * 24));
			var myH = Math.floor(nMS / (1000 * 60 * 60)) % 24; //小时
			var myM = Math.floor(nMS / (1000 * 60)) % 60; //分钟
			var myS = Math.floor(nMS / 1000) % 60; //秒
			var myMS = Math.floor(nMS / 100) % 10; //拆分秒
			if (myD >= 0) {
				var str = myD + "天" + myH + "小时" + myM + "分" + myS + "." + myMS + "秒";
			} else {
				obj.parent().siblings(".hide-a").html("活动已过期");
				var code  = obj.parent().siblings(".hide-a").attr("sku");
				$.ajax({
					   type: "POST",
					   url: _contentPath + "timelimit/recover_price.do?skuCode="+code,
					   data: "time="+new Date().getTime(),
					   error: function(data) {
			               alert('error');
			           }
					   
				});
				
				var str = "活动已过期！";
			}
			obj.html(str);
		});
	}, 100);
	//*********************** 首页banner下方轮播 结束 ******************//