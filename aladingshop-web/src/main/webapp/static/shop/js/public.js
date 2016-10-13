/**
 * public.js
 */

$(function() {

    var win_H = $(window).height();
    var win_W = $(window).width();

    /*topbar AD*/
    if ($('.ad')) {
        setTimeout(function(){
            $('.ad').animate({'height':(win_W/19.2)},800);
        },1500);
        $('.ad-close').click(function(event) {
            $('.ad').animate({'height':0},600);
        });
    };

    /*daily-banner*/
    if ($('#daily-banner').length) {
        $('#daily-banner').find('li').each(function(index, el) {
            $(this).find('.daily-box').eq(3).css('margin-right', '0');
        });
    };

    /*floor*/
    if ($('.floor').length) {
        $('.floor-box').find('.floor-ul').each(function(index, el) {
/*            $(this).find('li').eq(0).addClass('c-style');
            $(this).find('li').eq(3).addClass('c-style');
            $(this).find('li').eq(3).children('a').addClass('border-t-n');*/    //-2016-1-27
        });
        /*隐藏空楼层*/
        $('.floor').each(function(index, el) {
            if (!$(this).find('.floor-box').length) {
                $(this).hide();
                $('#leftMenu').find('li').eq(index).hide();
            };
            if ($(this).find('.floor-ul li').length < 2) {
                $(this).hide();
                $('#leftMenu').find('li').eq(index).hide();
            };
        });
        $('.menu-ul-f').find('a').each(function(index, el) {
            if(this.text=="运费差价"){
                $(this).parents('li').hide();
            }
        });
    };

    /*.brand-hot*/
    if ($('.brand-hot').length) {
        $('.brand-hot').find('ul').each(function(index, el) {
            $(this).find('li').eq(2).css('margin-right', '0');
            $(this).find('li').eq(5).css('margin-right', '0');
        });
    };

    /*.drop-li*/
    if ($('#topbar').length) {
        $('.drop-li').hover(function() {
            $(this).find('ul').stop().slideToggle(250);
        });
    };

    /*fixed-header*/
    if ($('#header').length) {
        $(window).scroll(function(event) {
            var scroll_H = $(window).scrollTop();
            if( scroll_H > 140 ) {
                $('.fixed-header').addClass('fixed');
            } else {
                $('.fixed-header').removeClass('fixed');
            }
        });
        $('.close').click(function(event) {
            $('#header').animate({'top':'-100%'}, 600);
            setTimeout(function(){
                $('body').removeClass('fixed-header fixed');
            },200);
        });
    };

    if ($('.fixed-menu').length) {
        $(window).scroll(function(event) {
            var scroll_H = $(window).scrollTop();
            if( scroll_H > win_H ) {
                $('.fixed-menu').addClass('menu-active');
            } else {
                $('.fixed-menu').removeClass('menu-active');
            }
        });
    }

    if ($('#leftMenu').length) {
        leftMenuScroll();
    };

    if ($('.floor').length) {
        /*floor菜单切换*/
        $('.floor').each(function(index, el) {
            $(this).find('.daily-tit li').hover(function() {
                $(this).addClass('active').siblings().removeClass('active');
                var this_index = $(this).index();
                $(this).parents('.floor').find('.floor-box').eq(this_index).show().siblings().hide();
            });
        });
    };

    if ($('#rightMenu').length) {
        /*回到顶部*/
        $('.r-top').click(function(event) {
            $("body,html").animate({"scrollTop":0});
        });
    };


    /*菜单显示*/

    // var index = str.indexOf(key);
    // var result = str.substr(index + 1,str.length);
    var list_index=GetQueryString("categoryCode");
    function GetQueryString(name){
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if(r!=null)return  unescape(r[2]); return null;
        }


   $('#menu li').eq(list_index-1).addClass('active').siblings().stop().removeClass('active');


    $('#menu').find('li').not('#menu li:first').each(function(index, el) {
        $(this).hover(function() {
            $('#hide-menu').find('.hide-menu-box').eq(index).stop().animate({'top':0,'opacity':'1'}, 400);
        }, function() {
            $('#hide-menu').find('.hide-menu-box').eq(index).stop().animate({'top':'-800px','opacity':'0'}, 300);
        });
    });


    $('.hide-menu-box').hover(function() {
        var thisIndex = $(this).index();
        $('#menu').find('li').not('#menu li:first').eq(thisIndex).addClass('active-on');
        $(this).stop().animate({'top':0,'opacity':'1'}, 400);
    }, function() {
        $(this).stop().animate({'top':'-800px','opacity':'0'});
        $('#menu').find('li').removeClass('active-on');
    });

    $('.hide-menu-box').each(function(index, el) {
        var thisUl = $(this).find('.menu-ul').length;
        if (thisUl==0) {
            $(this).css('display', 'none');
        };
    });

    /*关闭弹窗*/
    $('.modal_confirm').click(function(event) {
        $('.public_modal_backup').fadeOut(200);
    });

    /*精品推荐*/
    $('.recommendation-ul').find('li').each(function(index, el) {
        $(this).click(function(event) {
            $(this).addClass('active').siblings().removeClass('active');
            $('.recommendation-wrap').eq(index).show().siblings('.recommendation-wrap').hide();
        });
    });

    /*登录框*/
    $('.open-loginBox').click(function(event) {
        $('#loginBox').fadeIn();
        $('#loginBox').find('.login-left').animate({'margin-left': '-194px', 'opacity': '1'}, 300)
    });
    $('#loginBox').find('#close').click(function(event) {
        $('#loginBox').find('.login-left').animate({'margin-left': '-30%', 'opacity': '0'}, 300)
        $('#loginBox').fadeOut(300);
    });

    /*list-style-1*/
    $('#listSlider').find('li').each(function(index, el) {
        $(this).find('.list-slider-box').eq(3).addClass('list-bn');
    });

    $('.list').find('li:nth-child(5n)').addClass('mr0');

    /*search-result.html*/
    if ($('.search-opt').length) {
        $('.opt-box').find('.more').click(function() {
            $(this).toggleClass('extend-btn');
            $(this).parents('.opt-box').find('.brand-ul').toggleClass('extend');
            if ($(this).text()=="更多") {
                $(this).html("收起<span></span>");
            } else {
                $(this).html("更多<span></span>");
            }
        });
        $('.brand-ul').find('li').eq(6).prevAll().addClass('mT0');
    };

    /*detail.html*/

    /*商品详情滚动*/
    if ($('#detailRMenu').length) {
        var $detailRMenu = $('#detailRMenu');
        var thisOffsetTop = $detailRMenu.offset().top;
        var detailWrapBox = $('.detail-wrap-box');
        $(window).scroll(function(event) {
            var thisScrollTop = $(window).scrollTop();
            if ( thisScrollTop > thisOffsetTop ) {
                $('body').addClass('detailRMenu-fixed');
            } else {
                $('body').removeClass('detailRMenu-fixed');
            };
        });
        $('#detailRMenu').find('li').each(function(index, el) {
            $(this).click(function(event) {
                $("body,html").animate({"scrollTop":$('#detail-wrap').find('.detail-wrap-box').eq(index).offset().top-40});
            });
        });

        var h = [];
        for(i=0;i<detailWrapBox.length;i++){
            h[i] = detailWrapBox.eq(i).offset().top-50;
        }

        $(window).scroll(function(){
            var H = $(window).scrollTop();
            for(i=0;i<detailWrapBox.length;i++){
                if(H >=h[i]  && H<h[i+1]){
                    $('#detailRMenu').find('li').eq(i).addClass('active').siblings().removeClass('active');
                }
            };
            if(H > h[2]) {
                $('#detailRMenu').find('li').eq(2).addClass('active').siblings().removeClass('active');
            };
            if(H < h[0]) {
                $('#detailRMenu').find('li').removeClass('active');
            };
        });
    };

    /*购买数量*/
    if ($('.count').length) {
        $('.count').each(function(index, el) {
            var num = parseInt($(this).find('.num').val());
            var $num = $(this).find('.num');
            $(this).find('.add').click(function(event) {
                if (num > 0) {
                    num ++;
                    $num.val(num) ;
                };
            });
            $(this).find('.reduce').click(function(event) {
                if (num > 1) {
                    num --;
                    $num.val(num) ;
                };
            });
        });
    };

    /*隐藏所选条件*/
    if ($('.choosed-opt').length) {
        if ($('.choosed-opt-ul').find('li').length == 0) {
            $('.choosed-opt').fadeOut();
        };
    };

    // placeholder兼容IE8,IE9
    if (!('placeholder' in document.createElement('input'))) {
        $('input[type=text]').each(function(index, el) {
            $(this).val($(this).attr('placeholder'));
            $(this).focusin(function() {
                if ($(this).val()==$(this).attr('placeholder')) {
                    $(this).val("");
                };
            });
            $(this).focusout(function() {
                if ($(this).val()=="") {
                    $(this).val($(this).attr('placeholder'));
                };
            });
        });
    };

    /*评论空*/
    if ($('.rank-box').length) {
        var rankNone = '<div class="rank-none"><span></span><p>暂无评论！</p></div>';
        var rankBox = $('.rank-box');
        rankBox.each(function(index, el) {
            if ($(this).find('li').length==0) {
                $(this).append(rankNone);
            };
        });
    };

    /*order.html*/
    if ($('.order-wrap').length) {
        $('tbody').each(function(index, el) {
            if($(this).find('tr').length>2){
                $(this).addClass('more-goods');
            }
        });
    };

    /*确认收货等弹窗*/
    $('.cancel').click(function(event) {
        $(this).siblings('.confirm-box').css('visibility', 'visible').stop().animate({'bottom':'-88px','opacity':'1'}, 300);
    });
    $('.confirm-no').click(function(event) {
        $('.confirm-box').stop().animate({'bottom':'-48px','opacity':'0'}, 200);
        setTimeout(function(){
            $('.confirm-box').css('visibility', 'hidden');
        },200);
    });

    if ($('.order-l').length) {
        if ($('.cart-table').length) {
            $('.order-l').height($('.cart-table').outerHeight()-2);
        };
        $('.prder-l-tit').each(function(index, el) {
            $(this).click(function(event) {
                $(this).siblings('li').stop().slideToggle(200);
                var spanHtml = $(this).find('span').html();
                var $thisSpan = $(this).find('span');
                if(spanHtml=="-"){
                    $thisSpan.html("+");
                } else {
                    $thisSpan.html("-");
                }
            });
        });
        $('.order-l-list').each(function(index, el) {
            $(this).find('li:first').css('margin-top', '10px');
            $(this).find('li:last').css('margin-bottom', '10px');
        });
    };

    /*order*/
    $('.cart-table').find('tbody:last').css({'margin-bottom': '-1px','padding-top':'1px'});

})

//加入收藏夹
function AddFavorite(title, url) {
    try {
      window.external.addFavorite(url, title);
    } catch (e) {
      try {
        window.sidebar.addPanel(title, url, "");
      } catch (e) {
        openAddCartModal(0,'收藏失败，请使用 Ctrl+D 进行添加！');
      }
    }
}

function leftMenuScroll(length) {
    $('#leftMenu').find('li').each(function(index, el) {
        $(this).click(function(event) {
            var thisId = '#F' + (index + 1);
            $("body,html").animate({"scrollTop":$(thisId).offset().top-60});
        });
    });
}

function openAddCartModal(status,text,speed) {
    var modal="";
    if (status=="0") {
        if (text==undefined) {
            text="操作失败！"
        };
        var modal = '<div class="modal_backup" id="modalDetail"><div class="modal-detail modal-body error"><h3>温馨提示</h3><div class="modal-body-wrap"><span></span><p>' + text + '</p></div></div></div>';
    } else {
        if (text==undefined) {
            text="操作成功！"
        }
        var modal = '<div class="modal_backup" id="modalDetail"><div class="modal-detail modal-body success"><h3>温馨提示</h3><div class="modal-body-wrap"><span></span><p>' + text + '</p></div></div></div>';
    };
    if (speed==undefined) {
        speed=600;
    };

    $('body').append(modal);
    $('#modalDetail').fadeIn(400);
    $('.modal-body').css('visibility', 'visible').stop().animate({'opacity':'1','top':'40%'}, 400);
    setTimeout(function(){
        $('#modalDetail').fadeOut(300);
        $('.modal-body').stop().animate({'opacity':'0','top':'20%'}, 300);
    },speed);

    setTimeout(function(){
        $('body').find('#modalDetail').remove();
    },speed+200);
}

/*表单动画*/
var shake = function(o) {
    $(o).velocity("callout.shake");
}
