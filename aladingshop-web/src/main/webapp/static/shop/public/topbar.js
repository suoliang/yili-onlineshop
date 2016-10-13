var topbar = ''+
'<div id="topbar">'+
    '<div class="container">'+
        '<ul class="fl ul-l">'+
            '<li><a class="user-icon" href="" title="进入个人中心">823425525</a></li>'+
            '<li><a href="">&emsp;退出登录</a></li>'+
            '<li><a class="open-loginBox" href="javascript:void(0)">登录</a></li>'+
            '<li><a href="registered.html">注册</a></li>'+
        '</ul>'+
        '<ul class="fr ul-r">'+
            '<li class="drop-li">'+
                '<a href="">我的订单</a>'+
                '<ul>'+
                    '<li><a href="">代付款</a></li>'+
                    '<li><a href="">代发货</a></li>'+
                    '<li><a href="">代收货</a></li>'+
                    '<li><a href="">代评价</a></li>'+
                '</ul>'+
            '</li>'+
            '<li class="drop-li">'+
                '<a href="">我的阿拉丁</a>'+
                '<ul>'+
                    '<li><a href="">我的收藏</a></li>'+
                    '<li><a href="">联系客服</a></li>'+
                    '<li><a href="">我的积分</a></li>'+
                    '<li><a href="">我的评论</a></li>'+
                '</ul>'+
            '</li>'+
            '<li class="drop-code">'+
                '<a href="app.html">手机阿拉丁</a>'+
                '<div class="code">'+
                    '<span></span>'+
                    '<img src="images/code.jpg" alt="">'+
                '</div>'+
            '</li>'+
            '<li><a href="">帮助中心</a></li>'+
            '<li><a id="addFavorite" href="#" onclick="AddFavorite();">收藏</a></li>'+
        '</ul>'+
    '</div>'+
'</div>';
document.write(topbar);
