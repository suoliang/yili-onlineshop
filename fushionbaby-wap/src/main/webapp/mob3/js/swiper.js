 /*
 * 全站公共脚本,基于jquery-1.11.1脚本库
 */
$(function() {

        query_vote();

	    //微信分享到朋友圈
        var imgUrl = "http://app.fushionbaby.com/mob3/image/index_06.jpg";
        var lineLink = "http://app.fushionbaby.com/weixin/index.html";
        var descContent = '进口母婴品牌代理自营 兜世宝强势来袭，约吗？';
        var shareTitle = '进口母婴品牌代理自营 兜世宝强势来袭，约吗？ ';
        var appid = '';
         
        function shareFriend() {
            WeixinJSBridge.invoke('sendAppMessage',{
                "appid": appid,
                "img_url": imgUrl,
                "img_width": "200",
                "img_height": "200",
                "link": lineLink,
                "desc": descContent,
                "title": shareTitle
            }, function(res) {
                //_report('send_msg', res.err_msg);
            })
        }
        function shareTimeline() {
            WeixinJSBridge.invoke('shareTimeline',{
                "img_url": imgUrl,
                "img_width": "200",
                "img_height": "200",
                "link": lineLink,
                "desc": descContent,
                "title": descContent,
            }, function(res) {
                   //_report('timeline', res.err_msg);
            });
        }
        function shareWeibo() {
            WeixinJSBridge.invoke('shareWeibo',{
                "content": descContent,
                "url": lineLink,
            }, function(res) {
                //_report('weibo', res.err_msg);
            });
        }
        // 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
        document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
            // 发送给好友
            WeixinJSBridge.on('menu:share:appmessage', function(argv){
                shareFriend();
            });
            // 分享到朋友圈
            WeixinJSBridge.on('menu:share:timeline', function(argv){
                shareTimeline();
            });
            // 分享到微博
            WeixinJSBridge.on('menu:share:weibo', function(argv){
                shareWeibo();
            });
        }, false);

		$('body,.swiper-wrapper').height($(window).height()); 

		$('.btn').click(function(event) {
			$('.swiper-wrapper').addClass('num0');
		});
		var index=0;
		$('.info-img').one('click',function(event) {
			$(this).find('.select').html('已选择');
			$(this).children('.sure').show(400, function() {
				index++;
                $('.swiper-wrapper').addClass("num"+index+"").removeClass("num"+(index-1)+"");  
				//alert($(this).parent().find("#sku_id")[0].value);
                if($('.swiper-wrapper').hasClass('num4')){
                    query_vote();
                }else{
                    sku_vote($(this).parent());
                }
			}).parent().siblings('.info-img').children('.sure').remove();
		});
 
        var gao7=$('.screen-7').height();
        $('.concern img').click(function(event) {
            $('.swiper-wrapper').addClass("last");
            $('boby,html,.swiper-wrapper,.swiper-container').css({height: gao7});
           
        });
        $('.pro-list li').each(function(index, el) {
            
            var one=$(this).find('.pay_1');
            var two=$(this).find('.pay_2');
            var one_h=parseInt(one.html());
            var two_h=parseInt(two.html());

            if(one_h>two_h)
            {
                one.parent().siblings().children('img').addClass('win-current');
                one.parent().siblings().append('<i class="tips"></i>');
                if(one.parent().siblings().find('img').hasClass('jinkou'))
               {
                   one.parent().siblings().find('.tips').html('进口')
               }else
               {
                   one.parent().siblings().find('.tips').html('国产')
               }
            }else{
                two.parent().siblings().children('img').addClass('win-current');
                two.parent().siblings().append('<i class="tips"></i>');
                if(two.parent().siblings().find('img').hasClass('jinkou'))
               {
                   two.parent().siblings().find('.tips').html('进口')
               }else
               {
                   two.parent().siblings().find('.tips').html('国产')
               }
            }

        });
        $('.share').click(function(event) {
            $('.popup').show('fast',function(){
                 $('html,body').scrollTop(0);
            });
        });
})


 //投票数量增加
function sku_vote(click_obj){
    var sku_id = "";
    var sku_name = "";
    var sku_objs=$(click_obj).find("#sku_id");
    var name_objs = $(click_obj).find(".txt-info");
    if(name_objs!=null && name_objs != undefined && name_objs.length>0){
        var p_objs=$(name_objs).find("p");
        if(p_objs!=null && p_objs != undefined && p_objs.length>0){
            sku_name = p_objs.text();
        }
    }
    if(sku_objs!=null && sku_objs != undefined && sku_objs.length>0){
        sku_id = sku_objs[0].value;
    }
    var url = "http://www.fushionbaby.com/vote/update_vote.do";
    $.post(url,{
        sku_id : sku_id,
        sku_name : sku_name,
        time : new Date().getTime()},
        function(data){
            if (data == null || data == "" || data == undefined) {
                return;
            }
            if(data.responseCode!=0){
                alert("出错了!"+data.msg);
            }//end if responseCode
        }//function end
    );//end post request
}

//投票查询
function query_vote(){
    var url = "http://www.fushionbaby.com/vote/query_vote.do";
    $.post(url,{time : new Date().getTime()},
        function(data){
            if (data == null || data == "" || data == undefined) {
                return;
            }
            if(data.responseCode==0){
                var items = data.data;
                var len = items.length;
                for(i=0;i<len;i++){
                    var item = items[i];
                    $("#sku_pay_"+item.skuId).text(item.clickNum);
                }
            }else{
                alert(data.msg);
            }//end if responseCode
        }//function end
    );//end post request
}