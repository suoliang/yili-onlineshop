/**
 * public.js
 */

$(function() {

    var win_W = $(window).width();
    var win_H = $(window).height();

    /*mobile导航菜单*/
    $(".button-collapse").sideNav();

    /*首页幻灯片*/
    if ($('.slider').length) {
        $('.slider').slider();
    };

    /*首页card高度调整*/
    if (win_W > 600) {
        if ($(".traditional").length) {
            var traditional_H = $(".traditional").find('.card').eq(1).outerHeight();
            $(".traditional").find('.card').eq(0).height(traditional_H);
        };

        if ($(".location").length) {
            var $location_card = $(".location").find('.card');
            var location_H_0 = $location_card.eq(0).outerHeight();
            $location_card.eq(1).height(location_H_0);
            var location_H_2 = $location_card.eq(2).outerHeight();
            $location_card.eq(3).height(location_H_2);
            var location_H_4 = $location_card.eq(4).outerHeight();
            $location_card.eq(5).height(location_H_4);
        };

        if ($(".supports").length) {
            var $card = $(".supports").find('.card');
            var supports_H_0 = $card.eq(0).outerHeight();
            $card.eq(1).height(supports_H_0);
            var supports_H_2 = $card.eq(2).outerHeight();
            $card.eq(3).height(supports_H_2);
            var supports_H_4 = $card.eq(4).outerHeight();
            $card.eq(5).height(supports_H_4);
            var supports_H_6 = $card.eq(6).outerHeight();
            $card.eq(7).height(supports_H_6);
            var supports_H_8 = $card.eq(8).outerHeight();
            $card.eq(9).height(supports_H_8);
        };
    };

    /*回到顶部*/
    $('#goTop').click(function(event) {
        $('body,html').animate({"scrollTop": 0});
    });

    /*ie提示框*/
    if ($('.ie9-close')) {
        $('.ie9-close').click(function(event) {
            $('.ie9-box-wrap').fadeOut();
        });
    };

    /*调整内容区域高度*/
    if ($('.scrollspy').length) {
        $('.scrollspy').css('min-height', win_H - $('nav').outerHeight() - $('footer').outerHeight() - 20);
    };

    /*菜单当前添加样式*/
    if ($('.index').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(0).find('a').addClass('active');
        });
    };
    if ($('.project').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(1).find('a').addClass('active');
        });
    };
    if ($('.about').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(2).find('a').addClass('active');
        });
    };
    if ($('.contact').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(4).find('a').addClass('active');
        });
    };
    if ($('.news').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(3).find('a').addClass('active');
        });
    };
    if ($('.honor').length) {
        $('#nav').find('ul').each(function() {
            $(this).find('li').eq(6).find('a').addClass('active');
        });
    };

    if ($('.code-box').length) {
        $('.code-box').not($('.code-box').eq(0)).hover(function() {
            $(this).find('p,img').not('.hide-img').hide();
            $(this).find('.hide-img').animate({width: 140,opacity:1,'margin-left':-70,'margin-top':-70}, 200);
        }, function() {
            $(this).find('.hide-img').animate({width: 64,opacity:0,'margin-left':-32,'margin-top':-32}, 200);
            $(this).find('p,img').not('.hide-img').show();
        });
    };

    $('.tooltipped').tooltip({delay: 50});

    // 根据窗口大小调整导航
    adjustNav();
    $(window).resize(function(event) {
        adjustNav()
    });
    function adjustNav(){
        var win_W = $(window).width();
        if (win_W < 1230) {
            $('.hide-on-med-and-down').hide();
            $('.button-collapse,.brand-logo ').show();
        } else {
            $('.hide-on-med-and-down').show();
            $('.button-collapse,.brand-logo ').hide();
        }
    }
})
