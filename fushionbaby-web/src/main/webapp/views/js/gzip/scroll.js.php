<?php if ( extension_loaded('zlib') ) {ob_start('ob_gzhandler');}header("Content-Type: text/javascript"); ?>
$(document).ready(function(){function c(){a++,a>3&&(a=1,$("#newpro-wrapper").css("left",0)),$("#newpro-wrapper").stop().animate({left:1140*-a},1e3)}var a=0;$("#right").click(function(){c(),$(this).addClass("naw-current").siblings().removeClass("naw-current")}),$("#left").click(function(){a--,0>a&&(a=2,$("#newpro-wrapper").css("left","-3420px")),$("#newpro-wrapper").stop().animate({left:1140*-a},1e3),$(this).addClass("naw-current").siblings().removeClass("naw-current")});var b=setInterval(c,4e3);$(".newpro-bd-wrapper").hover(function(){clearInterval(b)},function(){clearInterval(b),b=setInterval(c,4e3)}),$(".nbw-arrow-wrapper").hover(function(){clearInterval(b)},function(){clearInterval(b),b=setInterval(c,4e3)})});
<?php if(extension_loaded('zlib')) {ob_end_flush();} ?>